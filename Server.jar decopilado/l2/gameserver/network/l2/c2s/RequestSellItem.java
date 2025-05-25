/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.ArrayUtils
 */
package l2.gameserver.network.l2.c2s;

import l2.commons.math.SafeMath;
import l2.gameserver.Config;
import l2.gameserver.model.Player;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExBuySellList;
import l2.gameserver.utils.Log;
import org.apache.commons.lang3.ArrayUtils;

public class RequestSellItem
extends L2GameClientPacket {
    private int fq;
    private int gT;
    private int[] aP;
    private long[] c;

    @Override
    protected void readImpl() {
        this.fq = this.readD();
        this.gT = this.readD();
        if (this.gT * 16 > this._buf.remaining() || this.gT > Short.MAX_VALUE || this.gT < 1) {
            this.gT = 0;
            return;
        }
        this.aP = new int[this.gT];
        this.c = new long[this.gT];
        for (int i = 0; i < this.gT; ++i) {
            this.aP[i] = this.readD();
            this.readD();
            this.c[i] = this.readQ();
            if (this.c[i] >= 1L && ArrayUtils.indexOf((int[])this.aP, (int)this.aP[i]) >= i) continue;
            this.gT = 0;
            break;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void runImpl() {
        boolean bl;
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null || this.gT == 0) {
            return;
        }
        if (player.isActionsDisabled()) {
            player.sendActionFailed();
            return;
        }
        if (player.isInStoreMode()) {
            player.sendPacket((IStaticPacket)SystemMsg.WHILE_OPERATING_A_PRIVATE_STORE_OR_WORKSHOP_YOU_CANNOT_DISCARD_DESTROY_OR_TRADE_AN_ITEM);
            return;
        }
        if (player.isInTrade()) {
            player.sendActionFailed();
            return;
        }
        if (player.isFishing()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_DO_THAT_WHILE_FISHING);
            return;
        }
        if (!Config.ALT_GAME_KARMA_PLAYER_CAN_SHOP && player.getKarma() > 0 && !player.isGM()) {
            player.sendActionFailed();
            return;
        }
        if (!Config.ALT_GAME_CURSED_WEAPON_PLAYER_CAN_SHOP && player.isCursedWeaponEquipped() && !player.isGM()) {
            player.sendActionFailed();
            return;
        }
        NpcInstance npcInstance = player.getLastNpc();
        boolean bl2 = bl = npcInstance != null && npcInstance.isMerchantNpc();
        if (!(Config.ALT_ALLOW_REMOTE_SELL_ITEMS_TO_SHOP || player.isGM() || npcInstance != null && bl && player.isInActingRange(npcInstance))) {
            player.sendActionFailed();
            return;
        }
        player.getInventory().writeLock();
        try {
            for (int i = 0; i < this.gT; ++i) {
                int n = this.aP[i];
                long l = this.c[i];
                ItemInstance itemInstance = player.getInventory().getItemByObjectId(n);
                if (itemInstance == null || itemInstance.getCount() < l || !itemInstance.canBeSold(player)) continue;
                long l2 = SafeMath.mulAndCheck(Math.max(1L, itemInstance.getReferencePrice() / Config.ALT_SHOP_REFUND_SELL_DIVISOR), l);
                ItemInstance itemInstance2 = player.getInventory().removeItemByObjectId(n, l);
                Log.LogItem(player, Log.ItemLog.RefundSell, itemInstance2);
                player.addAdena(l2);
                player.getRefund().addItem(itemInstance2);
            }
        }
        catch (ArithmeticException arithmeticException) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_EXCEEDED_THE_QUANTITY_THAT_CAN_BE_INPUTTED);
            return;
        }
        finally {
            player.getInventory().writeUnlock();
        }
        player.sendPacket((IStaticPacket)new ExBuySellList.SellRefundList(player, true));
        player.sendChanges();
    }
}
