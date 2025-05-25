/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import l2.commons.math.SafeMath;
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.TradeItem;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.PrivateStoreManageListBuy;
import l2.gameserver.network.l2.s2c.PrivateStoreMsgBuy;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.utils.TradeHelper;

public class SetPrivateStoreBuyList
extends L2GameClientPacket {
    private int gT;
    private List<SetPrivateStoreBuyListEntry> bR = Collections.emptyList();

    @Override
    protected void readImpl() {
        this.gT = this.readD();
        if (this.gT < 1 || this._buf.remaining() < this.gT * 54 || this.gT > 1024) {
            this.gT = 0;
            return;
        }
        ArrayList<SetPrivateStoreBuyListEntry> arrayList = new ArrayList<SetPrivateStoreBuyListEntry>(this.gT);
        for (int i = 0; i < this.gT; ++i) {
            int n = this.getByteBuffer().position();
            int n2 = this.readD();
            int n3 = this.readH();
            int n4 = this.readH();
            long l = this.readQ();
            long l2 = this.readQ();
            this.getByteBuffer().position(n + 54);
            if (l < 1L || l2 < 1L) {
                arrayList.clear();
                return;
            }
            arrayList.add(new SetPrivateStoreBuyListEntry(n2, n3, n4, l, l2));
        }
        this.bR = arrayList;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null || this.gT == 0 || this.gT != this.bR.size()) {
            return;
        }
        if (!TradeHelper.checksIfCanOpenStore(player, 3)) {
            if (player.isInStoreMode()) {
                player.setPrivateStoreType(0);
            }
            player.sendPacket(new PrivateStoreManageListBuy(true, player), new PrivateStoreManageListBuy(false, player));
            player.sendActionFailed();
            return;
        }
        player.getInventory().readLock();
        CopyOnWriteArrayList<TradeItem> copyOnWriteArrayList = new CopyOnWriteArrayList<TradeItem>();
        long l = 0L;
        int n = 0;
        try {
            for (SetPrivateStoreBuyListEntry setPrivateStoreBuyListEntry : this.bR) {
                ItemTemplate itemTemplate;
                if (setPrivateStoreBuyListEntry.getItemId() == 57 || (itemTemplate = ItemHolder.getInstance().getTemplate(setPrivateStoreBuyListEntry.getItemId())) == null || player.getInventory().getItemByItemId(itemTemplate.getItemId()) == null) continue;
                l = SafeMath.addAndCheck(l, SafeMath.mulAndCheck(setPrivateStoreBuyListEntry.getCount(), setPrivateStoreBuyListEntry.getPrice()));
                n = SafeMath.addAndCheck(n, SafeMath.mulAndCheck((int)setPrivateStoreBuyListEntry.getCount(), itemTemplate.getWeight()));
                TradeItem tradeItem = new TradeItem();
                tradeItem.setItemId(setPrivateStoreBuyListEntry.getItemId());
                tradeItem.setEnchantLevel(setPrivateStoreBuyListEntry.getEnchant());
                tradeItem.setCount(setPrivateStoreBuyListEntry.getCount());
                tradeItem.setOwnersPrice(setPrivateStoreBuyListEntry.getPrice());
                tradeItem.setReferencePrice(itemTemplate.getReferencePrice());
                if ((double)tradeItem.getStorePrice() > (double)setPrivateStoreBuyListEntry.getPrice() * Config.ALT_PRIVATE_STORE_BUY_COST_DIVISOR) {
                    player.sendMessage(new CustomMessage("l2p.gameserver.clientpackets.SetPrivateStoreBuyList.TooLowPrice", player, new Object[0]).addItemName(itemTemplate).addNumber((long)((double)itemTemplate.getReferencePrice() / Config.ALT_PRIVATE_STORE_BUY_COST_DIVISOR / 2.0)));
                    player.sendPacket((IStaticPacket)SystemMsg.THE_PURCHASE_PRICE_IS_HIGHER_THAN_THE_AMOUNT_OF_MONEY_THAT_YOU_HAVE_AND_SO_YOU_CANNOT_OPEN_A_PERSONAL_STORE);
                    return;
                }
                copyOnWriteArrayList.add(tradeItem);
            }
        }
        catch (ArithmeticException arithmeticException) {
            copyOnWriteArrayList.clear();
            player.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_EXCEEDED_THE_QUANTITY_THAT_CAN_BE_INPUTTED);
            return;
        }
        finally {
            player.getInventory().readUnlock();
        }
        if (!player.getInventory().validateWeight(n)) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_EXCEEDED_THE_WEIGHT_LIMIT);
            return;
        }
        if (!player.getInventory().validateCapacity(copyOnWriteArrayList.size())) {
            player.sendPacket((IStaticPacket)SystemMsg.YOUR_INVENTORY_IS_FULL);
            return;
        }
        if (copyOnWriteArrayList.size() > player.getTradeLimit() || l > Integer.MAX_VALUE || copyOnWriteArrayList.size() != this.gT) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_EXCEEDED_THE_QUANTITY_THAT_CAN_BE_INPUTTED);
            player.sendPacket(new PrivateStoreManageListBuy(true, player), new PrivateStoreManageListBuy(false, player));
            return;
        }
        if (l > player.getAdena()) {
            player.sendPacket((IStaticPacket)SystemMsg.THE_PURCHASE_PRICE_IS_HIGHER_THAN_THE_AMOUNT_OF_MONEY_THAT_YOU_HAVE_AND_SO_YOU_CANNOT_OPEN_A_PERSONAL_STORE);
            player.sendPacket(new PrivateStoreManageListBuy(true, player), new PrivateStoreManageListBuy(false, player));
            return;
        }
        if (!copyOnWriteArrayList.isEmpty()) {
            player.getInventory().writeLock();
            try {
                player.setBuyList(copyOnWriteArrayList);
                player.setPrivateStoreType(3);
                player.saveTradeList();
                player.broadcastPacket(new PrivateStoreMsgBuy(player));
            }
            finally {
                player.getInventory().writeUnlock();
            }
        } else {
            player.setBuyList(Collections.emptyList());
            player.setPrivateStoreType(0);
        }
        player.sendActionFailed();
    }

    private static class SetPrivateStoreBuyListEntry {
        public static final int IN_PACKET_SIZE = 54;
        private final int si;
        private final int sj;
        private final int sk;
        private final long cY;
        private final long cZ;

        private SetPrivateStoreBuyListEntry(int n, int n2, int n3, long l, long l2) {
            this.si = n;
            this.sj = n2;
            this.sk = n3;
            this.cY = l;
            this.cZ = l2;
        }

        public int getItemId() {
            return this.si;
        }

        public int getEnchant() {
            return this.sj;
        }

        public int getDamage() {
            return this.sk;
        }

        public long getCount() {
            return this.cY;
        }

        public long getPrice() {
            return this.cZ;
        }
    }
}
