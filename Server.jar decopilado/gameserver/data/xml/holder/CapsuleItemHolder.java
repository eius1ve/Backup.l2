/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.builder.EqualsBuilder
 *  org.apache.commons.lang3.builder.HashCodeBuilder
 *  org.apache.commons.lang3.tuple.Pair
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.data.xml.holder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import l2.commons.data.xml.AbstractHolder;
import l2.commons.math.SafeMath;
import l2.commons.util.RandomUtils;
import l2.commons.util.Rnd;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.handler.items.IItemHandler;
import l2.gameserver.handler.items.ItemHandler;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.items.PcInventory;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.InventoryUpdate;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.utils.ItemFunctions;
import l2.gameserver.utils.Location;
import l2.gameserver.utils.Log;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class CapsuleItemHolder
extends AbstractHolder {
    private static final Logger aT = LoggerFactory.getLogger(CapsuleItemHolder.class);
    private static final CapsuleItemHolder a = new CapsuleItemHolder();
    private final Map<Integer, Pair<List<CapsuledItem>, Boolean>> F = new HashMap<Integer, Pair<List<CapsuledItem>, Boolean>>();
    private final Map<Integer, Pair<Integer, Long>> G = new HashMap<Integer, Pair<Integer, Long>>();
    private CapsuleItemsHandler a;

    public static CapsuleItemHolder getInstance() {
        return a;
    }

    public Pair<Integer, Long> getCapsuleRequiredItems(int n) {
        return this.G.get(n);
    }

    public List<CapsuledItem> getCapsuledItems(int n) {
        Pair<List<CapsuledItem>, Boolean> pair = this.F.get(n);
        if (pair == null) {
            return null;
        }
        return (List)pair.getLeft();
    }

    public boolean isDoConsume(int n) {
        Pair<List<CapsuledItem>, Boolean> pair = this.F.get(n);
        if (pair == null) {
            return true;
        }
        return (Boolean)pair.getRight();
    }

    public void add(int n, List<CapsuledItem> list, boolean bl) {
        this.add(n, null, list, bl);
    }

    public void add(int n, Pair<Integer, Long> pair, List<CapsuledItem> list, boolean bl) {
        if (this.F.containsKey(n)) {
            aT.warn("Capsule item " + n + " already defined.");
        }
        this.F.put(n, (Pair<List<CapsuledItem>, Boolean>)Pair.of(Collections.unmodifiableList(list), (Object)bl));
        if (pair != null) {
            this.G.put(n, pair);
        }
    }

    @Override
    public int size() {
        return this.F.size();
    }

    @Override
    public void clear() {
        ItemHandler.getInstance().unregisterItemHandler(this.a);
        this.F.clear();
    }

    @Override
    protected void process() {
        int[] nArray = new int[this.F.size()];
        Iterator<Integer> iterator = this.F.keySet().iterator();
        int n = 0;
        while (iterator.hasNext()) {
            nArray[n] = iterator.next();
            ++n;
        }
        this.a = new CapsuleItemsHandler(nArray);
        ItemHandler.getInstance().registerItemHandler(this.a);
    }

    public static class CapsuleItemsHandler
    implements IItemHandler {
        private final int[] ap;

        public CapsuleItemsHandler(int[] nArray) {
            this.ap = nArray;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean useItem(Playable playable, ItemInstance itemInstance, boolean bl) {
            int n = itemInstance.getItemId();
            Player player = playable.getPlayer();
            List<CapsuledItem> list = CapsuleItemHolder.getInstance().getCapsuledItems(n);
            if (list == null) {
                return false;
            }
            Pair<Integer, Long> pair = CapsuleItemHolder.getInstance().getCapsuleRequiredItems(n);
            if (pair != null && (Integer)pair.getKey() > 0 && (Long)pair.getValue() > 0L && ItemFunctions.getItemCount(player, (Integer)pair.getKey()) < (Long)pair.getValue()) {
                player.sendMessage(new CustomMessage("capsuleditems.required_item_for_open", player, new Object[0]).addItemName((Integer)pair.getKey()).addNumber((Long)pair.getValue()));
                player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_ITEM_COUNT);
                return false;
            }
            playable.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_BEGIN_TO_USE_AN_S1).addItemName(itemInstance.getItemId()));
            PcInventory pcInventory = player.getInventory();
            pcInventory.writeLock();
            try {
                long l;
                ArrayList arrayList = new ArrayList();
                LinkedHashMap<Object, Pair> linkedHashMap = new LinkedHashMap<Object, Pair>();
                CapsuledItem capsuledItem = null;
                CapsuledItem capsuledItem2 = null;
                Object object = list.iterator();
                while (object.hasNext()) {
                    CapsuledItem capsuledItem3 = object.next();
                    if (capsuledItem3.getItemId() < 0) {
                        switch (capsuledItem3.getItemId()) {
                            case -100: {
                                capsuledItem = capsuledItem3;
                                break;
                            }
                            case -200: {
                                capsuledItem2 = capsuledItem3;
                            }
                        }
                        continue;
                    }
                    if (capsuledItem3.getChance() == 100.0) {
                        l = capsuledItem3.getMax() > capsuledItem3.getMin() ? Rnd.get(capsuledItem3.getMin(), capsuledItem3.getMax()) : capsuledItem3.getMin();
                        Iterator iterator = ItemHolder.getInstance().getTemplate(capsuledItem3.getItemId());
                        linkedHashMap.put(capsuledItem3, Pair.of((Object)iterator, (Object)l));
                        continue;
                    }
                    arrayList.add(Pair.of((Object)capsuledItem3, (Object)capsuledItem3.getChance()));
                }
                if (!arrayList.isEmpty() && (object = (CapsuledItem)RandomUtils.pickRandomGroup(arrayList, 100.0)) != null) {
                    long l2 = ((CapsuledItem)object).getMax() > ((CapsuledItem)object).getMin() ? Rnd.get(((CapsuledItem)object).getMin(), ((CapsuledItem)object).getMax()) : ((CapsuledItem)object).getMin();
                    ItemTemplate itemTemplate = ItemHolder.getInstance().getTemplate(((CapsuledItem)object).getItemId());
                    linkedHashMap.put(object, Pair.of((Object)itemTemplate, (Object)l2));
                }
                if (linkedHashMap.isEmpty() && capsuledItem == null && capsuledItem2 == null) {
                    player.getInventory().destroyItem(itemInstance, 1L);
                    if (pair != null && (Integer)pair.getKey() > 0 && (Long)pair.getValue() > 0L) {
                        ItemFunctions.removeItem(player, (Integer)pair.getKey(), (Long)pair.getValue(), true);
                    }
                } else {
                    Object object2;
                    long l3 = 0L;
                    l = 0L;
                    for (Map.Entry entry : linkedHashMap.entrySet()) {
                        object2 = (ItemTemplate)((Pair)entry.getValue()).getLeft();
                        long l4 = (Long)((Pair)entry.getValue()).getRight();
                        l = SafeMath.addAndCheck(l, SafeMath.mulAndCheck(l4, (long)((ItemTemplate)object2).getWeight()));
                        if (((ItemTemplate)object2).isStackable()) {
                            ++l3;
                            continue;
                        }
                        l3 = SafeMath.addAndCheck(l3, l4);
                    }
                    if (!pcInventory.validateCapacity(l3)) {
                        player.sendPacket((IStaticPacket)SystemMsg.YOUR_INVENTORY_IS_FULL);
                        boolean bl2 = false;
                        return bl2;
                    }
                    if (!pcInventory.validateWeight(l)) {
                        player.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_EXCEEDED_THE_WEIGHT_LIMIT);
                        boolean bl3 = false;
                        return bl3;
                    }
                    if (CapsuleItemHolder.getInstance().isDoConsume(n) && !player.getInventory().destroyItem(itemInstance, 1L)) {
                        player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_ITEM_COUNT);
                        boolean bl4 = false;
                        return bl4;
                    }
                    if (pair != null && (Integer)pair.getKey() > 0 && (Long)pair.getValue() > 0L && ItemFunctions.removeItem(player, (Integer)pair.getKey(), (Long)pair.getValue(), true) < (Long)pair.getValue()) {
                        playable.sendPacket((IStaticPacket)SystemMsg.INCORRECT_ITEM_COUNT);
                        boolean bl5 = false;
                        return bl5;
                    }
                    for (Map.Entry entry : linkedHashMap.entrySet()) {
                        object2 = (CapsuledItem)entry.getKey();
                        ItemTemplate itemTemplate = (ItemTemplate)((Pair)entry.getValue()).getLeft();
                        long l5 = (Long)((Pair)entry.getValue()).getRight();
                        ArrayList<Pair> arrayList2 = new ArrayList<Pair>();
                        if (itemTemplate.isStackable()) {
                            arrayList2.add(Pair.of((Object)player.getInventory().addItem(itemTemplate.getItemId(), l5), (Object)l5));
                        } else {
                            for (long i = 0L; i < l5; ++i) {
                                arrayList2.add(Pair.of((Object)player.getInventory().addItem(itemTemplate.getItemId(), 1L), (Object)1L));
                            }
                        }
                        for (Pair pair2 : arrayList2) {
                            ItemInstance itemInstance2 = (ItemInstance)pair2.getLeft();
                            long l6 = (Long)pair2.getRight();
                            if (!itemInstance2.canBeEnchanted(true)) {
                                player.sendPacket((IStaticPacket)SystemMessage.obtainItems(itemInstance2.getItemId(), l6, 0));
                                Log.LogItem(player, Log.ItemLog.CapsuledReward, itemInstance2.getItemId(), l6);
                                continue;
                            }
                            if (((CapsuledItem)object2).getMaxEnchant() > ((CapsuledItem)object2).getMinEnchant()) {
                                itemInstance2.setEnchantLevel(Rnd.get(((CapsuledItem)object2).getMinEnchant(), ((CapsuledItem)object2).getMaxEnchant()));
                            } else {
                                itemInstance2.setEnchantLevel(((CapsuledItem)object2).getMinEnchant());
                            }
                            player.sendPacket((IStaticPacket)SystemMessage.obtainItems(itemInstance2.getItemId(), l6, itemInstance2.getEnchantLevel()));
                            player.sendPacket((IStaticPacket)new InventoryUpdate().addModifiedItem(itemInstance2));
                            Log.LogItem(player, Log.ItemLog.CapsuledReward, itemInstance2.getItemId(), l6);
                        }
                    }
                    if (capsuledItem != null && Rnd.chance(capsuledItem.getChance())) {
                        int n2 = (int)(capsuledItem.getMax() > capsuledItem.getMin() ? Rnd.get(capsuledItem.getMin(), capsuledItem.getMax()) : capsuledItem.getMin());
                        player.addPcBangPoints(n2, false);
                    }
                    if (capsuledItem2 != null && Rnd.chance(capsuledItem2.getChance())) {
                        int n3 = (int)(capsuledItem2.getMax() > capsuledItem2.getMin() ? Rnd.get(capsuledItem2.getMin(), capsuledItem2.getMax()) : capsuledItem2.getMin());
                        player.getClan().incReputation(n3, false, "CapsuleItems");
                        player.getClan().broadcastToOnlineMembers(new L2GameServerPacket[]{new SystemMessage(SystemMsg.YOUR_CLAN_HAS_ADDED_S1_POINTS_TO_ITS_CLAN_REPUTATION_SCORE).addNumber(n3)});
                    }
                }
            }
            catch (ArithmeticException arithmeticException) {
                player.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_EXCEEDED_THE_QUANTITY_THAT_CAN_BE_INPUTTED);
            }
            finally {
                pcInventory.writeUnlock();
            }
            return true;
        }

        @Override
        public void dropItem(Player player, ItemInstance itemInstance, long l, Location location) {
            IItemHandler.NULL.dropItem(player, itemInstance, l, location);
        }

        @Override
        public boolean pickupItem(Playable playable, ItemInstance itemInstance) {
            return IItemHandler.NULL.pickupItem(playable, itemInstance);
        }

        @Override
        public int[] getItemIds() {
            return this.ap;
        }
    }

    public static class CapsuledItem {
        private final int fn;
        private final long aJ;
        private final long aK;
        private final double e;
        private final int fo;
        private final int fp;

        public CapsuledItem(int n, long l, long l2, double d, int n2, int n3) {
            this.fn = n;
            this.aJ = l;
            this.aK = l2;
            this.e = d;
            this.fo = n2;
            this.fp = n3;
        }

        public int getItemId() {
            return this.fn;
        }

        public double getChance() {
            return this.e;
        }

        public long getMax() {
            return this.aK;
        }

        public long getMin() {
            return this.aJ;
        }

        public int getMinEnchant() {
            return this.fo;
        }

        public int getMaxEnchant() {
            return this.fp;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object == null || this.getClass() != object.getClass()) {
                return false;
            }
            CapsuledItem capsuledItem = (CapsuledItem)object;
            return new EqualsBuilder().append(this.fn, capsuledItem.fn).append(this.aJ, capsuledItem.aJ).append(this.aK, capsuledItem.aK).append(this.e, capsuledItem.e).append(this.fo, capsuledItem.fo).append(this.fp, capsuledItem.fp).isEquals();
        }

        public int hashCode() {
            return new HashCodeBuilder(17, 37).append(this.fn).append(this.aJ).append(this.aK).append(this.e).append(this.fo).append(this.fp).toHashCode();
        }
    }
}
