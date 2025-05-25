/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.ArrayUtils
 */
package l2.gameserver.network.l2.c2s;

import java.util.ArrayList;
import java.util.List;
import l2.commons.math.SafeMath;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.items.TradeItem;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.utils.Log;
import l2.gameserver.utils.TradeHelper;
import org.apache.commons.lang3.ArrayUtils;

public class RequestPrivateStoreManageBuy
extends L2GameClientPacket {
    private int rQ;
    private int gT;
    private int[] aP;
    private long[] c;
    private long[] d;

    @Override
    protected void readImpl() {
        this.rQ = this.readD();
        this.gT = this.readD();
        if (this.gT * 12 > this._buf.remaining() || this.gT > Short.MAX_VALUE || this.gT < 1) {
            this.gT = 0;
            return;
        }
        this.aP = new int[this.gT];
        this.c = new long[this.gT];
        this.d = new long[this.gT];
        for (int i = 0; i < this.gT; ++i) {
            this.aP[i] = this.readD();
            this.c[i] = this.readD();
            this.d[i] = this.readD();
            if (this.c[i] >= 1L && this.d[i] >= 1L && ArrayUtils.indexOf((int[])this.aP, (int)this.aP[i]) >= i) continue;
            this.gT = 0;
            break;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void runImpl() {
        List<TradeItem> list;
        Player player;
        Player player2;
        block46: {
            player2 = ((GameClient)this.getClient()).getActiveChar();
            if (player2 == null || this.gT == 0) {
                return;
            }
            if (player2.isActionsDisabled()) {
                player2.sendActionFailed();
                return;
            }
            if (player2.isInStoreMode()) {
                player2.sendPacket((IStaticPacket)SystemMsg.WHILE_OPERATING_A_PRIVATE_STORE_OR_WORKSHOP_YOU_CANNOT_DISCARD_DESTROY_OR_TRADE_AN_ITEM);
                return;
            }
            if (player2.isInTrade()) {
                player2.sendActionFailed();
                return;
            }
            if (player2.isFishing()) {
                player2.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_DO_THAT_WHILE_FISHING_2);
                return;
            }
            if (player2.isTradeBannedByGM()) {
                player2.sendPacket((IStaticPacket)SystemMsg.SOME_LINEAGE_II_FEATURES_HAVE_BEEN_LIMITED_FOR_FREE_TRIALS_);
                player2.sendActionFailed();
                return;
            }
            if (!player2.getPlayerAccess().UseTrade) {
                player2.sendPacket((IStaticPacket)SystemMsg.SOME_LINEAGE_II_FEATURES_HAVE_BEEN_LIMITED_FOR_FREE_TRIALS_____);
                return;
            }
            player = (Player)player2.getVisibleObject(this.rQ);
            if (player == null || player.getPrivateStoreType() != 1 && player.getPrivateStoreType() != 8 || !player.isInActingRange(player2)) {
                player2.sendPacket((IStaticPacket)SystemMsg.THE_ATTEMPT_TO_TRADE_HAS_FAILED);
                player2.sendActionFailed();
                return;
            }
            list = player.getSellList();
            if (list.isEmpty()) {
                player2.sendPacket((IStaticPacket)SystemMsg.THE_ATTEMPT_TO_TRADE_HAS_FAILED);
                player2.sendActionFailed();
                return;
            }
            ArrayList<TradeItem> arrayList = new ArrayList<TradeItem>();
            long l = 0L;
            int n = 0;
            long l2 = 0L;
            player2.getInventory().writeLock();
            player.getInventory().writeLock();
            try {
                block24: for (int i = 0; i < this.gT; ++i) {
                    int n2 = this.aP[i];
                    long l3 = this.c[i];
                    long l4 = this.d[i];
                    TradeItem tradeItem = null;
                    for (TradeItem tradeItem2 : list) {
                        if (tradeItem2.getObjectId() != n2 || tradeItem2.getOwnersPrice() != l4) continue;
                        if (l3 > tradeItem2.getCount()) {
                            break block46;
                        }
                        ItemInstance itemInstance = player.getInventory().getItemByObjectId(n2);
                        if (itemInstance == null || itemInstance.getCount() < l3) break block46;
                        if (!itemInstance.canBeTraded(player)) {
                            break block46;
                        }
                        l = SafeMath.addAndCheck(l, SafeMath.mulAndCheck(l3, l4));
                        l2 = SafeMath.addAndCheck(l2, SafeMath.mulAndCheck(l3, (long)itemInstance.getTemplate().getWeight()));
                        if (!itemInstance.isStackable() || player2.getInventory().getItemByItemId(itemInstance.getItemId()) == null) {
                            ++n;
                        }
                        tradeItem = new TradeItem();
                        tradeItem.setObjectId(n2);
                        tradeItem.setItemId(itemInstance.getItemId());
                        tradeItem.setCount(l3);
                        tradeItem.setOwnersPrice(l4);
                        arrayList.add(tradeItem);
                        continue block24;
                    }
                }
            }
            catch (ArithmeticException arithmeticException) {
                arrayList.clear();
                player2.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_EXCEEDED_THE_QUANTITY_THAT_CAN_BE_INPUTTED);
                return;
            }
            finally {
                try {
                    if (arrayList.size() != this.gT || player.getPrivateStoreType() == 8 && arrayList.size() != list.size()) {
                        player2.sendPacket((IStaticPacket)SystemMsg.THE_ATTEMPT_TO_TRADE_HAS_FAILED);
                        player2.sendActionFailed();
                        return;
                    }
                    if (!player2.getInventory().validateWeight(l2)) {
                        player2.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_EXCEEDED_THE_WEIGHT_LIMIT);
                        player2.sendActionFailed();
                        return;
                    }
                    if (!player2.getInventory().validateCapacity(n)) {
                        player2.sendPacket((IStaticPacket)SystemMsg.YOUR_INVENTORY_IS_FULL);
                        player2.sendActionFailed();
                        return;
                    }
                    if (!player2.reduceAdena(l)) {
                        player2.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
                        player2.sendActionFailed();
                        return;
                    }
                    for (TradeItem tradeItem : arrayList) {
                        ItemInstance itemInstance = player.getInventory().removeItemByObjectId(tradeItem.getObjectId(), tradeItem.getCount());
                        for (TradeItem tradeItem3 : list) {
                            if (tradeItem3.getObjectId() != tradeItem.getObjectId()) continue;
                            tradeItem3.setCount(tradeItem3.getCount() - tradeItem.getCount());
                            if (tradeItem3.getCount() >= 1L) break;
                            list.remove(tradeItem3);
                            break;
                        }
                        Log.LogItem(player, Log.ItemLog.PrivateStoreSell, itemInstance);
                        Log.LogItem(player2, Log.ItemLog.PrivateStoreBuy, itemInstance);
                        player2.getInventory().addItem(itemInstance);
                        TradeHelper.purchaseItem(player2, player, tradeItem);
                    }
                    long l5 = TradeHelper.getTax(player, l);
                    if (l5 > 0L) {
                        l -= l5;
                        player.sendMessage(new CustomMessage("trade.HavePaidTax", player, new Object[0]).addNumber(l5));
                    }
                    player.addAdena(l);
                    player.saveTradeList();
                }
                finally {
                    player.getInventory().writeUnlock();
                    player2.getInventory().writeUnlock();
                }
            }
        }
        if (list.isEmpty()) {
            TradeHelper.cancelStore(player);
        }
        player.sendChanges();
        player2.sendChanges();
        player2.sendActionFailed();
    }
}
