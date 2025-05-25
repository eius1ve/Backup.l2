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
import l2.gameserver.model.items.PcFreight;
import l2.gameserver.model.items.PcInventory;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.utils.Log;
import org.apache.commons.lang3.ArrayUtils;

public class RequestPackageSend
extends L2GameClientPacket {
    private static final long cT = 1000L;
    private int fW;
    private int gT;
    private int[] aP;
    private long[] c;

    @Override
    protected void readImpl() {
        this.fW = this.readD();
        this.gT = this.readD();
        if (this.gT * 12 > this._buf.remaining() || this.gT > Short.MAX_VALUE || this.gT < 1) {
            this.gT = 0;
            return;
        }
        this.aP = new int[this.gT];
        this.c = new long[this.gT];
        for (int i = 0; i < this.gT; ++i) {
            this.aP[i] = this.readD();
            this.c[i] = this.readQ();
            if (this.c[i] >= 1L && ArrayUtils.indexOf((int[])this.aP, (int)this.aP[i]) >= i) continue;
            this.gT = 0;
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void runImpl() throws Exception {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null || this.gT == 0) {
            return;
        }
        if (!player.getPlayerAccess().UseWarehouse) {
            player.sendActionFailed();
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
        if (player.isTradeBannedByGM()) {
            player.sendPacket((IStaticPacket)SystemMsg.SOME_LINEAGE_II_FEATURES_HAVE_BEEN_LIMITED_FOR_FREE_TRIALS_);
            return;
        }
        NpcInstance npcInstance = player.getLastNpc();
        if (!(npcInstance != null && npcInstance.isInActingRange(npcInstance) || Config.ALT_ALLOW_REMOTE_USE_CARGO_BOX)) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_MOVED_TOO_FAR_AWAY_FROM_THE_WAREHOUSE_TO_PERFORM_THAT_ACTION);
            return;
        }
        if (!player.getAccountChars().containsKey(this.fW)) {
            return;
        }
        PcInventory pcInventory = player.getInventory();
        PcFreight pcFreight = new PcFreight(this.fW);
        pcFreight.restore();
        pcInventory.writeLock();
        pcFreight.writeLock();
        try {
            int n = 0;
            long l = 0L;
            n = Config.FREIGHT_SLOTS - pcFreight.getSize();
            int n2 = 0;
            for (int i = 0; i < this.gT; ++i) {
                ItemInstance itemInstance = pcInventory.getItemByObjectId(this.aP[i]);
                if (itemInstance == null || itemInstance.getCount() < this.c[i] || !itemInstance.canBeFreighted(player) && Config.FREIGHT_SEND_TYPE || !RequestPackageSend.CanSendItem(itemInstance) && !Config.FREIGHT_SEND_TYPE) {
                    this.aP[i] = 0;
                    this.c[i] = 0L;
                    continue;
                }
                if (!itemInstance.isStackable() || pcFreight.getItemByItemId(itemInstance.getItemId()) == null) {
                    if (n <= 0) {
                        this.aP[i] = 0;
                        this.c[i] = 0L;
                        continue;
                    }
                    --n;
                }
                if (itemInstance.getItemId() == 57) {
                    l = this.c[i];
                }
                ++n2;
            }
            if (n <= 0) {
                player.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_EXCEEDED_THE_QUANTITY_THAT_CAN_BE_INPUTTED);
            }
            if (n2 == 0) {
                player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_ITEM_COUNT);
                return;
            }
            long l2 = SafeMath.mulAndCheck((long)n2, 1000L);
            if (l2 + l > player.getAdena()) {
                player.sendPacket((IStaticPacket)SystemMsg.YOU_LACK_THE_FUNDS_NEEDED_TO_PAY_FOR_THIS_TRANSACTION);
                return;
            }
            if (!player.reduceAdena(l2, true)) {
                player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
                return;
            }
            for (int i = 0; i < this.gT; ++i) {
                if (this.aP[i] == 0) continue;
                ItemInstance itemInstance = pcInventory.removeItemByObjectId(this.aP[i], this.c[i]);
                Log.LogItem(player, Log.ItemLog.FreightDeposit, itemInstance);
                pcFreight.addItem(itemInstance);
            }
        }
        catch (ArithmeticException arithmeticException) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_EXCEEDED_THE_QUANTITY_THAT_CAN_BE_INPUTTED);
            return;
        }
        finally {
            pcFreight.writeUnlock();
            pcInventory.writeUnlock();
        }
        player.sendChanges();
        player.sendPacket((IStaticPacket)SystemMsg.THE_TRANSACTION_IS_COMPLETE);
    }

    public static boolean CanSendItem(ItemInstance itemInstance) {
        if (!itemInstance.getTemplate().isTradeable()) {
            return false;
        }
        return !itemInstance.isEquipped() && !itemInstance.getTemplate().isQuest() && !itemInstance.isAugmented() && !itemInstance.isEnsouled();
    }
}
