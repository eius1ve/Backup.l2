/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.ArrayUtils
 */
package l2.gameserver.network.l2.c2s;

import java.util.ArrayList;
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

public class RequestExRefundItem
extends L2GameClientPacket {
    private int fq;
    private int gT;
    private int[] aP;

    @Override
    protected void readImpl() {
        this.fq = this.readD();
        this.gT = this.readD();
        if (this.gT * 4 > this._buf.remaining() || this.gT > Short.MAX_VALUE || this.gT < 1) {
            this.gT = 0;
            return;
        }
        this.aP = new int[this.gT];
        for (int i = 0; i < this.gT; ++i) {
            this.aP[i] = this.readD();
            if (ArrayUtils.indexOf((int[])this.aP, (int)this.aP[i]) >= i) continue;
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
        NpcInstance npcInstance = player.getLastNpc();
        boolean bl2 = bl = npcInstance != null && npcInstance.isMerchantNpc();
        if (!(player.isGM() || npcInstance != null && bl && player.isInRange(npcInstance, 400L))) {
            player.sendActionFailed();
            return;
        }
        player.getInventory().writeLock();
        try {
            int n = 0;
            long l = 0L;
            long l2 = 0L;
            ArrayList<ItemInstance> arrayList = new ArrayList<ItemInstance>();
            for (int n2 : this.aP) {
                ItemInstance itemInstance = player.getRefund().getItemByObjectId(n2);
                if (itemInstance == null) continue;
                l2 = SafeMath.addAndCheck(l2, SafeMath.mulAndCheck(itemInstance.getCount(), itemInstance.getReferencePrice()) / 2L);
                l = SafeMath.addAndCheck(l, SafeMath.mulAndCheck(itemInstance.getCount(), (long)itemInstance.getTemplate().getWeight()));
                if (!itemInstance.isStackable() || player.getInventory().getItemByItemId(itemInstance.getItemId()) == null) {
                    ++n;
                }
                arrayList.add(itemInstance);
            }
            if (arrayList.isEmpty()) {
                player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_ITEM_COUNT);
                player.sendActionFailed();
                return;
            }
            if (!player.getInventory().validateWeight(l)) {
                player.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_EXCEEDED_THE_WEIGHT_LIMIT);
                player.sendActionFailed();
                return;
            }
            if (!player.getInventory().validateCapacity(n)) {
                player.sendPacket((IStaticPacket)SystemMsg.YOUR_INVENTORY_IS_FULL);
                player.sendActionFailed();
                return;
            }
            if (!player.reduceAdena(l2)) {
                player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
                player.sendActionFailed();
                return;
            }
            Object object = arrayList.iterator();
            while (object.hasNext()) {
                ItemInstance itemInstance = (ItemInstance)object.next();
                ItemInstance itemInstance2 = player.getRefund().removeItem(itemInstance);
                Log.LogItem(player, Log.ItemLog.RefundReturn, itemInstance2);
                player.getInventory().addItem(itemInstance2);
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
