/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.tuple.Pair
 */
package l2.gameserver.data.xml.holder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import l2.commons.math.SafeMath;
import l2.commons.util.RandomUtils;
import l2.commons.util.Rnd;
import l2.gameserver.data.xml.holder.CapsuleItemHolder;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.handler.items.IItemHandler;
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
import org.apache.commons.lang3.tuple.Pair;

public static class CapsuleItemHolder.CapsuleItemsHandler
implements IItemHandler {
    private final int[] ap;

    public CapsuleItemHolder.CapsuleItemsHandler(int[] nArray) {
        this.ap = nArray;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean useItem(Playable playable, ItemInstance itemInstance, boolean bl) {
        int n = itemInstance.getItemId();
        Player player = playable.getPlayer();
        List<CapsuleItemHolder.CapsuledItem> list = CapsuleItemHolder.getInstance().getCapsuledItems(n);
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
            CapsuleItemHolder.CapsuledItem capsuledItem = null;
            CapsuleItemHolder.CapsuledItem capsuledItem2 = null;
            Object object = list.iterator();
            while (object.hasNext()) {
                CapsuleItemHolder.CapsuledItem capsuledItem3 = object.next();
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
            if (!arrayList.isEmpty() && (object = (CapsuleItemHolder.CapsuledItem)RandomUtils.pickRandomGroup(arrayList, 100.0)) != null) {
                long l2 = ((CapsuleItemHolder.CapsuledItem)object).getMax() > ((CapsuleItemHolder.CapsuledItem)object).getMin() ? Rnd.get(((CapsuleItemHolder.CapsuledItem)object).getMin(), ((CapsuleItemHolder.CapsuledItem)object).getMax()) : ((CapsuleItemHolder.CapsuledItem)object).getMin();
                ItemTemplate itemTemplate = ItemHolder.getInstance().getTemplate(((CapsuleItemHolder.CapsuledItem)object).getItemId());
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
                    object2 = (CapsuleItemHolder.CapsuledItem)entry.getKey();
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
                        if (((CapsuleItemHolder.CapsuledItem)object2).getMaxEnchant() > ((CapsuleItemHolder.CapsuledItem)object2).getMinEnchant()) {
                            itemInstance2.setEnchantLevel(Rnd.get(((CapsuleItemHolder.CapsuledItem)object2).getMinEnchant(), ((CapsuleItemHolder.CapsuledItem)object2).getMaxEnchant()));
                        } else {
                            itemInstance2.setEnchantLevel(((CapsuleItemHolder.CapsuledItem)object2).getMinEnchant());
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
