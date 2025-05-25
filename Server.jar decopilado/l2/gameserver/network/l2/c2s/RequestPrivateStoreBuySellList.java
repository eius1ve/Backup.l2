/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.ArrayUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.network.l2.c2s;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import l2.commons.math.SafeMath;
import l2.gameserver.Config;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.items.TradeItem;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExPrivateStoreBuyingResult;
import l2.gameserver.utils.Log;
import l2.gameserver.utils.TradeHelper;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestPrivateStoreBuySellList
extends L2GameClientPacket {
    private static final Logger cV = LoggerFactory.getLogger(RequestPrivateStoreBuySellList.class);
    private int rR;
    private int gT;
    private int[] aP;
    private long[] c;
    private long[] d;

    @Override
    protected void readImpl() {
        this.rR = this.readD();
        this.gT = this.readD();
        if (this.gT * 28 > this._buf.remaining() || this.gT > Short.MAX_VALUE || this.gT < 1) {
            this.gT = 0;
            return;
        }
        this.aP = new int[this.gT];
        this.c = new long[this.gT];
        this.d = new long[this.gT];
        for (int i = 0; i < this.gT; ++i) {
            int n;
            this.aP[i] = this.readD();
            this.readD();
            this.readH();
            this.readH();
            this.c[i] = this.readQ();
            this.d[i] = this.readQ();
            if (this.c[i] < 1L || this.d[i] < 1L || ArrayUtils.indexOf((int[])this.aP, (int)this.aP[i]) < i) {
                this.gT = 0;
                break;
            }
            this.readD();
            this.readD();
            this.readD();
            int n2 = this.readC();
            for (n = 0; n < n2; ++n) {
                this.readD();
            }
            n2 = this.readC();
            for (n = 0; n < n2; ++n) {
                this.readD();
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) return;
        if (this.gT == 0) {
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
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_DO_THAT_WHILE_FISHING_2);
            return;
        }
        if (player.isTradeBannedByGM()) {
            player.sendPacket((IStaticPacket)SystemMsg.SOME_LINEAGE_II_FEATURES_HAVE_BEEN_LIMITED_FOR_FREE_TRIALS_);
            player.sendActionFailed();
            return;
        }
        if (!player.getPlayerAccess().UseTrade) {
            player.sendPacket((IStaticPacket)SystemMsg.SOME_LINEAGE_II_FEATURES_HAVE_BEEN_LIMITED_FOR_FREE_TRIALS_____);
            return;
        }
        Player player2 = (Player)player.getVisibleObject(this.rR);
        if (player2 == null || player2.getPrivateStoreType() != 3 || !player.isInActingRange(player2)) {
            player.sendPacket((IStaticPacket)SystemMsg.THE_ATTEMPT_TO_SELL_HAS_FAILED);
            player.sendActionFailed();
            return;
        }
        List<TradeItem> list = player2.getBuyList();
        if (list.isEmpty()) {
            player.sendPacket((IStaticPacket)SystemMsg.THE_ATTEMPT_TO_SELL_HAS_FAILED);
            player.sendActionFailed();
            return;
        }
        ArrayList<TradeItem> arrayList = new ArrayList<TradeItem>();
        long l = 0L;
        int n = 0;
        long l2 = 0L;
        player2.getInventory().writeLock();
        player.getInventory().writeLock();
        try {
            long l3;
            Serializable serializable = new ArrayList<TradeItem>(list);
            block25: for (int i = 0; i < this.gT; ++i) {
                int n2 = this.aP[i];
                l3 = this.c[i];
                long l4 = this.d[i];
                ItemInstance itemInstance = player.getInventory().getItemByObjectId(n2);
                if (itemInstance == null || itemInstance.getCount() < l3 || !itemInstance.canBeTraded(player)) break;
                TradeItem tradeItem = null;
                Iterator iterator = serializable.iterator();
                while (iterator.hasNext()) {
                    TradeItem tradeItem2 = (TradeItem)iterator.next();
                    if (tradeItem2.getItemId() != itemInstance.getItemId() || tradeItem2.getOwnersPrice() != l4 || Config.PRIVATE_BUY_MATCH_ENCHANT && itemInstance.getEnchantLevel() != tradeItem2.getEnchantLevel() || !Config.PRIVATE_BUY_MATCH_ENCHANT && itemInstance.getEnchantLevel() < tradeItem2.getEnchantLevel()) continue;
                    if (l3 > tradeItem2.getCount()) break block25;
                    l = SafeMath.addAndCheck(l, SafeMath.mulAndCheck(l3, l4));
                    l2 = SafeMath.addAndCheck(l2, SafeMath.mulAndCheck(l3, (long)itemInstance.getTemplate().getWeight()));
                    if (!itemInstance.isStackable() || player2.getInventory().getItemByItemId(itemInstance.getItemId()) == null) {
                        ++n;
                    }
                    tradeItem = new TradeItem();
                    tradeItem.setObjectId(n2);
                    tradeItem.setItemId(itemInstance.getItemId());
                    tradeItem.setEnchantLevel(itemInstance.getEnchantLevel());
                    tradeItem.setCount(l3);
                    tradeItem.setOwnersPrice(l4);
                    arrayList.add(tradeItem);
                    iterator.remove();
                    continue block25;
                }
            }
            try {
                if (arrayList.size() != this.gT) {
                    player.sendPacket((IStaticPacket)SystemMsg.THE_ATTEMPT_TO_SELL_HAS_FAILED);
                    player.sendActionFailed();
                    return;
                }
                if (!player2.getInventory().validateWeight(l2)) {
                    player2.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_EXCEEDED_THE_WEIGHT_LIMIT);
                    player.sendPacket((IStaticPacket)SystemMsg.THE_ATTEMPT_TO_SELL_HAS_FAILED);
                    player.sendActionFailed();
                    return;
                }
                if (!player2.getInventory().validateCapacity(n)) {
                    player2.sendPacket((IStaticPacket)SystemMsg.YOUR_INVENTORY_IS_FULL);
                    player.sendPacket((IStaticPacket)SystemMsg.THE_ATTEMPT_TO_SELL_HAS_FAILED);
                    player.sendActionFailed();
                    return;
                }
                if (!player2.reduceAdena(l)) {
                    player2.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
                    player.sendPacket((IStaticPacket)SystemMsg.THE_ATTEMPT_TO_SELL_HAS_FAILED);
                    player.sendActionFailed();
                    return;
                }
                for (TradeItem tradeItem : arrayList) {
                    serializable = player.getInventory().removeItemByObjectId(tradeItem.getObjectId(), tradeItem.getCount());
                    for (TradeItem tradeItem3 : list) {
                        if (tradeItem3.getItemId() != tradeItem.getItemId() || tradeItem3.getOwnersPrice() != tradeItem.getOwnersPrice() || Config.PRIVATE_BUY_MATCH_ENCHANT && ((ItemInstance)serializable).getEnchantLevel() != tradeItem3.getEnchantLevel() || !Config.PRIVATE_BUY_MATCH_ENCHANT && ((ItemInstance)serializable).getEnchantLevel() < tradeItem3.getEnchantLevel()) continue;
                        tradeItem3.setCount(tradeItem3.getCount() - tradeItem.getCount());
                        if (tradeItem3.getCount() >= 1L) break;
                        list.remove(tradeItem3);
                        break;
                    }
                    Log.LogItem(player, Log.ItemLog.PrivateStoreSell, serializable);
                    Log.LogItem(player2, Log.ItemLog.PrivateStoreBuy, serializable);
                    l3 = ((ItemInstance)serializable).getCount();
                    ItemInstance itemInstance = player2.getInventory().addItem((ItemInstance)serializable);
                    player2.sendPacket((IStaticPacket)new ExPrivateStoreBuyingResult(itemInstance.getObjectId(), l3, player.getName()));
                    TradeHelper.purchaseItem(player2, player, tradeItem);
                }
                long l5 = TradeHelper.getTax(player, l);
                if (l5 > 0L) {
                    l -= l5;
                    player.sendMessage(new CustomMessage("trade.HavePaidTax", player, new Object[0]).addNumber(l5));
                }
                player.addAdena(l);
                player2.saveTradeList();
            }
            finally {
                player.getInventory().writeUnlock();
                player2.getInventory().writeUnlock();
            }
        }
        catch (ArithmeticException arithmeticException) {
            arrayList.clear();
            player.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_EXCEEDED_THE_QUANTITY_THAT_CAN_BE_INPUTTED);
            return;
        }
        finally {
            try {
                if (arrayList.size() != this.gT) {
                    player.sendPacket((IStaticPacket)SystemMsg.THE_ATTEMPT_TO_SELL_HAS_FAILED);
                    player.sendActionFailed();
                    return;
                }
                if (!player2.getInventory().validateWeight(l2)) {
                    player2.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_EXCEEDED_THE_WEIGHT_LIMIT);
                    player.sendPacket((IStaticPacket)SystemMsg.THE_ATTEMPT_TO_SELL_HAS_FAILED);
                    player.sendActionFailed();
                    return;
                }
                if (!player2.getInventory().validateCapacity(n)) {
                    player2.sendPacket((IStaticPacket)SystemMsg.YOUR_INVENTORY_IS_FULL);
                    player.sendPacket((IStaticPacket)SystemMsg.THE_ATTEMPT_TO_SELL_HAS_FAILED);
                    player.sendActionFailed();
                    return;
                }
                if (!player2.reduceAdena(l)) {
                    player2.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
                    player.sendPacket((IStaticPacket)SystemMsg.THE_ATTEMPT_TO_SELL_HAS_FAILED);
                    player.sendActionFailed();
                    return;
                }
                for (TradeItem tradeItem : arrayList) {
                    ItemInstance itemInstance = player.getInventory().removeItemByObjectId(tradeItem.getObjectId(), tradeItem.getCount());
                    for (TradeItem tradeItem4 : list) {
                        if (tradeItem4.getItemId() != tradeItem.getItemId() || tradeItem4.getOwnersPrice() != tradeItem.getOwnersPrice() || Config.PRIVATE_BUY_MATCH_ENCHANT && itemInstance.getEnchantLevel() != tradeItem4.getEnchantLevel() || !Config.PRIVATE_BUY_MATCH_ENCHANT && itemInstance.getEnchantLevel() < tradeItem4.getEnchantLevel()) continue;
                        tradeItem4.setCount(tradeItem4.getCount() - tradeItem.getCount());
                        if (tradeItem4.getCount() >= 1L) break;
                        list.remove(tradeItem4);
                        break;
                    }
                    Log.LogItem(player, Log.ItemLog.PrivateStoreSell, itemInstance);
                    Log.LogItem(player2, Log.ItemLog.PrivateStoreBuy, itemInstance);
                    long l6 = itemInstance.getCount();
                    ItemInstance itemInstance2 = player2.getInventory().addItem(itemInstance);
                    player2.sendPacket((IStaticPacket)new ExPrivateStoreBuyingResult(itemInstance2.getObjectId(), l6, player.getName()));
                    TradeHelper.purchaseItem(player2, player, tradeItem);
                }
                long l7 = TradeHelper.getTax(player, l);
                if (l7 > 0L) {
                    l -= l7;
                    player.sendMessage(new CustomMessage("trade.HavePaidTax", player, new Object[0]).addNumber(l7));
                }
                player.addAdena(l);
                player2.saveTradeList();
            }
            finally {
                player.getInventory().writeUnlock();
                player2.getInventory().writeUnlock();
            }
        }
        if (list.isEmpty()) {
            TradeHelper.cancelStore(player2);
        }
        player.sendChanges();
        player2.sendChanges();
        player.sendActionFailed();
    }
}
