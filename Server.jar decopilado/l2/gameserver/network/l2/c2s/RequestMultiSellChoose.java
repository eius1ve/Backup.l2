/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.tuple.Pair
 */
package l2.gameserver.network.l2.c2s;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import l2.commons.math.SafeMath;
import l2.commons.util.RandomUtils;
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.data.xml.holder.MultiSellHolder;
import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.model.Player;
import l2.gameserver.model.base.MultiSellEntry;
import l2.gameserver.model.base.MultiSellIngredient;
import l2.gameserver.model.entity.residence.Castle;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.items.ItemAttributes;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.items.PcInventory;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.utils.ItemFunctions;
import l2.gameserver.utils.Log;
import org.apache.commons.lang3.tuple.Pair;

public class RequestMultiSellChoose
extends L2GameClientPacket {
    private int fq;
    private int kM;
    private long cP;

    @Override
    protected void readImpl() {
        this.fq = this.readD();
        this.kM = this.readD();
        this.cP = this.readD();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * WARNING - void declaration
     */
    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null || this.cP < 1L) {
            return;
        }
        MultiSellHolder.MultiSellListContainer multiSellListContainer = player.getMultisell();
        if (multiSellListContainer == null) {
            player.sendActionFailed();
            player.setMultisell(null);
            return;
        }
        if (multiSellListContainer.getListId() != this.fq) {
            Log.add("Player " + player.getName() + " trying to change multisell list id, ban this player!", "illegal-actions");
            player.sendActionFailed();
            player.setMultisell(null);
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
        if (player.isOlyParticipant()) {
            player.sendActionFailed();
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
        if (!(multiSellListContainer.getListId() < 0 || multiSellListContainer.isNoMerchant() || player.isGM() || NpcInstance.canBypassCheck(player, npcInstance))) {
            player.setMultisell(null);
            return;
        }
        MultiSellEntry multiSellEntry = null;
        for (MultiSellEntry multiSellEntry2 : multiSellListContainer.getEntries()) {
            if (multiSellEntry2.getEntryId() != this.kM) continue;
            multiSellEntry = multiSellEntry2;
            break;
        }
        if (multiSellEntry == null) {
            return;
        }
        boolean bl = multiSellListContainer.isKeepEnchant();
        boolean bl2 = multiSellListContainer.isNoTax();
        ArrayList<ItemData> arrayList = new ArrayList<ItemData>();
        PcInventory pcInventory = player.getInventory();
        long l = 0L;
        Castle castle = npcInstance != null ? npcInstance.getCastle(player) : null;
        pcInventory.writeLock();
        try {
            Object object;
            long l2;
            long l3 = SafeMath.mulAndCheck(multiSellEntry.getTax(), this.cP);
            long l4 = 0L;
            long l5 = 0L;
            for (MultiSellIngredient object22 : multiSellEntry.getProduction()) {
                if (object22.getItemId() <= 0) continue;
                ItemTemplate itemTemplate = ItemHolder.getInstance().getTemplate(object22.getItemId());
                l5 = SafeMath.addAndCheck(l5, SafeMath.mulAndCheck(SafeMath.mulAndCheck(object22.getItemCount(), this.cP), (long)itemTemplate.getWeight()));
                if (itemTemplate.isStackable()) {
                    if (pcInventory.getItemByItemId(object22.getItemId()) != null) continue;
                    ++l4;
                    continue;
                }
                l4 = SafeMath.addAndCheck(l4, SafeMath.mulAndCheck(object22.getItemCount(), this.cP));
            }
            if (!pcInventory.validateWeight(l5)) {
                player.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_EXCEEDED_THE_WEIGHT_LIMIT);
                player.sendActionFailed();
                return;
            }
            if (!pcInventory.validateCapacity(l4)) {
                player.sendPacket((IStaticPacket)SystemMsg.YOUR_INVENTORY_IS_FULL);
                player.sendActionFailed();
                return;
            }
            if (multiSellEntry.getIngredients().size() == 0) {
                player.sendActionFailed();
                player.setMultisell(null);
                return;
            }
            for (MultiSellIngredient multiSellIngredient : multiSellEntry.getIngredients()) {
                int n = multiSellIngredient.getItemId();
                long l6 = multiSellIngredient.getItemCount();
                int n2 = multiSellIngredient.getItemEnchant();
                long l7 = l2 = !multiSellIngredient.getMantainIngredient() ? SafeMath.mulAndCheck(l6, this.cP) : l6;
                if (n == -200) {
                    if (player.getClan() == null) {
                        player.sendPacket((IStaticPacket)SystemMsg.YOU_ARE_NOT_A_CLAN_MEMBER_AND_CANNOT_PERFORM_THIS_ACTION);
                        return;
                    }
                    if ((long)player.getClan().getReputationScore() < l2) {
                        player.sendPacket((IStaticPacket)SystemMsg.THE_CLAN_REPUTATION_SCORE_IS_TOO_LOW);
                        return;
                    }
                    if (player.getClan().getLeaderId() != player.getObjectId()) {
                        player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_IS_NOT_A_CLAN_LEADER).addString(player.getName()));
                        return;
                    }
                    if (!multiSellIngredient.getMantainIngredient()) {
                        arrayList.add(new ItemData(n, l2, null));
                    }
                } else if (n == -100) {
                    if ((long)player.getPcBangPoints() < l2) {
                        player.sendPacket((IStaticPacket)SystemMsg.YOU_ARE_SHORT_OF_ACCUMULATED_POINTS);
                        return;
                    }
                    if (!multiSellIngredient.getMantainIngredient()) {
                        arrayList.add(new ItemData(n, l2, null));
                    }
                } else if (n == -500) {
                    if ((long)player.getRaidBossPoints() < l2) {
                        player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.NOT_ENOUGH_RAID_POINTS));
                        return;
                    }
                    if (!multiSellIngredient.getMantainIngredient()) {
                        arrayList.add(new ItemData(n, l2, null));
                    }
                } else {
                    object = ItemHolder.getInstance().getTemplate(n);
                    if (!((ItemTemplate)object).isStackable()) {
                        int n3 = 0;
                        while ((long)n3 < l6 * this.cP) {
                            List<ItemInstance> list = pcInventory.getItemsByItemId(n);
                            ItemInstance itemInstance = null;
                            if (bl) {
                                for (ItemInstance itemInstance2 : list) {
                                    ItemData itemData = new ItemData(itemInstance2.getItemId(), itemInstance2.getCount(), itemInstance2);
                                    if (itemInstance2.getEnchantLevel() != n2 && itemInstance2.getTemplate().isEquipment() || arrayList.contains(itemData) || !itemInstance2.canBeExchanged(player)) continue;
                                    itemInstance = itemInstance2;
                                    break;
                                }
                            } else {
                                for (ItemInstance itemInstance2 : list) {
                                    if (!(arrayList.contains(new ItemData(itemInstance2.getItemId(), itemInstance2.getCount(), itemInstance2)) || itemInstance != null && itemInstance2.getEnchantLevel() >= itemInstance.getEnchantLevel() || (itemInstance2.isAugmented() || itemInstance2.isEnsouled()) && !Config.ALT_ALLOW_TRADE_AUGMENTED || !ItemFunctions.checkIfCanDiscard(player, itemInstance2) || (itemInstance = itemInstance2).getEnchantLevel() != 0)) break;
                                }
                            }
                            if (itemInstance == null || this.cP > 1L && (itemInstance.isAugmented() || itemInstance.isEnsouled())) {
                                player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_REQUIRED_ITEMS);
                                return;
                            }
                            if (!multiSellIngredient.getMantainIngredient()) {
                                arrayList.add(new ItemData(itemInstance.getItemId(), 1L, itemInstance));
                            }
                            ++n3;
                        }
                    } else {
                        ItemInstance itemInstance;
                        if (n == 57) {
                            l = SafeMath.addAndCheck(l, SafeMath.mulAndCheck(l6, this.cP));
                        }
                        if ((itemInstance = pcInventory.getItemByItemId(n)) == null || itemInstance.getCount() < l2) {
                            player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_REQUIRED_ITEMS);
                            return;
                        }
                        if (!multiSellIngredient.getMantainIngredient()) {
                            arrayList.add(new ItemData(itemInstance.getItemId(), l2, itemInstance));
                        }
                    }
                }
                if (player.getAdena() >= l) continue;
                player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
                return;
            }
            int n = 0;
            Object var19_22 = null;
            int n4 = 0;
            int n5 = 0;
            for (ItemData itemData : arrayList) {
                l2 = itemData.getCount();
                if (l2 <= 0L) continue;
                if (itemData.getId() == -200) {
                    player.getClan().incReputation((int)(-l2), false, "MultiSell");
                    player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_POINTS_HAVE_BEEN_DEDUCTED_FROM_THE_CLANS_REPUTATION_SCORE).addNumber(l2));
                    continue;
                }
                if (itemData.getId() == -500) {
                    player.reduceRaidBossPoints((int)l2);
                    continue;
                }
                if (itemData.getId() == -100) {
                    player.reducePcBangPoints((int)l2);
                    continue;
                }
                object = itemData.getItem();
                if (pcInventory.destroyItem(itemData.getItem(), l2)) {
                    if (bl && itemData.getItem().canBeEnchanted(true)) {
                        n = itemData.getItem().getEnchantLevel();
                        ItemAttributes itemAttributes = itemData.getItem().getAttributes();
                        n4 = itemData.getItem().getVariationStat1();
                        n5 = itemData.getItem().getVariationStat2();
                    }
                    player.sendPacket((IStaticPacket)SystemMessage.removeItems(itemData.getId(), l2));
                    Log.LogItem(player, Log.ItemLog.MultiSellIngredient, (ItemInstance)object, l2, 0L, this.fq);
                    continue;
                }
                return;
            }
            if (l3 > 0L && !bl2 && castle != null) {
                player.sendMessage(new CustomMessage("trade.HavePaidTax", player, new Object[0]).addNumber(l3));
                if (npcInstance != null && npcInstance.getReflection() == ReflectionManager.DEFAULT) {
                    castle.addToTreasury(l3, true, false);
                }
            }
            for (MultiSellIngredient multiSellIngredient : this.a(multiSellListContainer.isChancedList(), multiSellEntry)) {
                if (multiSellIngredient.getItemId() <= 0) {
                    if (multiSellIngredient.getItemId() == -200) {
                        player.getClan().incReputation((int)(multiSellIngredient.getItemCount() * this.cP), false, "MultiSell");
                        player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOUR_CLAN_HAS_ADDED_S1_POINTS_TO_ITS_CLAN_REPUTATION_SCORE).addNumber(multiSellIngredient.getItemCount() * this.cP));
                        continue;
                    }
                    if (multiSellIngredient.getItemId() == -100) {
                        player.addPcBangPoints((int)(multiSellIngredient.getItemCount() * this.cP), false);
                        continue;
                    }
                    if (multiSellIngredient.getItemId() != -500) continue;
                    player.addRaidBossPoints((int)(multiSellIngredient.getItemCount() * this.cP));
                    continue;
                }
                if (ItemHolder.getInstance().getTemplate(multiSellIngredient.getItemId()).isStackable()) {
                    long l8 = SafeMath.mulAndLimit(multiSellIngredient.getItemCount(), this.cP);
                    pcInventory.addItem(multiSellIngredient.getItemId(), l8);
                    player.sendPacket((IStaticPacket)SystemMessage.obtainItems(multiSellIngredient.getItemId(), l8, 0));
                    Log.LogItem(player, Log.ItemLog.MultiSellProduct, multiSellIngredient.getItemId(), l8, 0L, this.fq);
                    continue;
                }
                int n6 = 0;
                while ((long)n6 < this.cP) {
                    int n7 = 0;
                    while ((long)n7 < multiSellIngredient.getItemCount()) {
                        object = ItemFunctions.createItem(multiSellIngredient.getItemId());
                        if (bl) {
                            if (((ItemInstance)object).canBeEnchanted(true)) {
                                void var19_23;
                                ((ItemInstance)object).setEnchantLevel(n);
                                if (var19_23 != null) {
                                    ((ItemInstance)object).setAttributes(var19_23.clone());
                                }
                                if (n4 != 0 || n5 != 0) {
                                    ((ItemInstance)object).setVariationStat1(n4);
                                    ((ItemInstance)object).setVariationStat2(n5);
                                }
                            }
                        } else {
                            ((ItemInstance)object).setEnchantLevel(multiSellIngredient.getItemEnchant());
                            ((ItemInstance)object).setAttributes(multiSellIngredient.getItemAttributes().clone());
                        }
                        pcInventory.addItem((ItemInstance)object);
                        player.sendPacket((IStaticPacket)SystemMessage.obtainItems((ItemInstance)object));
                        Log.LogItem(player, Log.ItemLog.MultiSellProduct, (ItemInstance)object, ((ItemInstance)object).getCount(), 0L, this.fq);
                        ++n7;
                    }
                    ++n6;
                }
            }
        }
        catch (ArithmeticException arithmeticException) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_EXCEEDED_THE_QUANTITY_THAT_CAN_BE_INPUTTED);
            return;
        }
        finally {
            pcInventory.writeUnlock();
        }
        player.sendChanges();
        if (!multiSellListContainer.isShowAll()) {
            MultiSellHolder.getInstance().SeparateAndSend(multiSellListContainer, player, castle == null ? 0.0 : castle.getTaxRate());
        }
    }

    private List<MultiSellIngredient> a(boolean bl, MultiSellEntry multiSellEntry) {
        if (!bl) {
            return multiSellEntry.getProduction();
        }
        int n = 0;
        ArrayList arrayList = new ArrayList();
        for (MultiSellIngredient multiSellIngredient : multiSellEntry.getProduction()) {
            int n2 = multiSellIngredient.getItemChance();
            if (n2 <= 0) continue;
            n += n2;
            arrayList.add(Pair.of((Object)multiSellIngredient, (Object)n2));
        }
        return Collections.singletonList((MultiSellIngredient)RandomUtils.pickRandomSortedGroup(arrayList, n));
    }

    private class ItemData {
        private final int rv;
        private final long cS;
        private final ItemInstance f;

        public ItemData(int n, long l, ItemInstance itemInstance) {
            this.rv = n;
            this.cS = l;
            this.f = itemInstance;
        }

        public int getId() {
            return this.rv;
        }

        public long getCount() {
            return this.cS;
        }

        public ItemInstance getItem() {
            return this.f;
        }

        public boolean equals(Object object) {
            if (!(object instanceof ItemData)) {
                return false;
            }
            ItemData itemData = (ItemData)object;
            return this.rv == itemData.rv && this.cS == itemData.cS && this.f == itemData.f;
        }
    }
}
