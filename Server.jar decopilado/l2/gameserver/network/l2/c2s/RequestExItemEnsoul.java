/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.tuple.Pair
 */
package l2.gameserver.network.l2.c2s;

import java.util.LinkedHashMap;
import java.util.Map;
import l2.gameserver.data.xml.holder.EnsoulFeeHolder;
import l2.gameserver.data.xml.holder.EnsoulOptionHolder;
import l2.gameserver.model.Player;
import l2.gameserver.model.actor.instances.player.ShortCut;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ActionFail;
import l2.gameserver.network.l2.s2c.ExEnsoulResult;
import l2.gameserver.network.l2.s2c.InventoryUpdate;
import l2.gameserver.network.l2.s2c.ShortCutRegister;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.templates.item.support.EnsoulFeeSlotType;
import l2.gameserver.templates.item.support.EnsoulOption;
import l2.gameserver.templates.item.support.Grade;
import l2.gameserver.utils.ItemFunctions;
import org.apache.commons.lang3.tuple.Pair;

public class RequestExItemEnsoul
extends L2GameClientPacket {
    private final ItemEnsoulRequest[] a = new ItemEnsoulRequest[3];
    private int qO;
    private int qP = 0;

    public static boolean CheckPlayerConditions(Player player) {
        if (player.isInStoreMode()) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.RUNE_INSERTION_IS_IMPOSSIBLE_WHEN_PRIVATE_STORE_AND_WORKSHOP_ARE_OPENED));
            return false;
        }
        if (player.isInTrade()) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.RUNE_INSERTION_IS_IMPOSSIBLE_DURING_EXCHANGE));
            return false;
        }
        if (player.isDead()) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.RUNE_INSERTION_IS_IMPOSSIBLE_IF_THE_CHARACTER_IS_DEAD));
            return false;
        }
        if (player.isFrozen()) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.RUNE_INSERTION_IS_IMPOSSIBLE_WHILE_IN_FROZEN_STATE));
            return false;
        }
        if (player.isParalyzed()) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.RUNE_INSERTION_IS_IMPOSSIBLE_WHILE_PETRIFIED));
            return false;
        }
        if (player.isFishing()) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.RUNE_INSERTION_IS_IMPOSSIBLE_DURING_FISHING));
            return false;
        }
        if (player.isSitting()) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.RUNE_INSERTION_IS_IMPOSSIBLE_WHILE_SITTING));
            return false;
        }
        if (player.isInCombat()) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.RUNE_INSERTION_IS_IMPOSSIBLE_WHILE_IN_COMBAT));
            return false;
        }
        if (player.isFakeDeath()) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.RUNE_INSERTION_IS_IMPOSSIBLE_WHILE_UNDER_FAKE_DEATH_SKILL_EFFECT));
            return false;
        }
        return !player.isActionsDisabled();
    }

    @Override
    protected void readImpl() throws Exception {
        this.qO = this.readD();
        int n = this.readC();
        if (this.getAvaliableBytes() < n * 10) {
            return;
        }
        this.qP = n;
        for (int i = 0; i < Math.min(this.qP, this.a.length); ++i) {
            int n2 = this.readC();
            int n3 = this.readC();
            int n4 = this.readD();
            int n5 = this.readD();
            this.a[i] = new ItemEnsoulRequest(n2, n3, n4, n5);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void runImpl() throws Exception {
        LinkedHashMap<Integer, Long> linkedHashMap;
        GameClient gameClient = (GameClient)this.getClient();
        if (gameClient == null) {
            return;
        }
        Player player = gameClient.getActiveChar();
        if (player == null) {
            return;
        }
        long l = System.currentTimeMillis();
        if (l - gameClient.getLastIncomePacketTimeStamp(RequestExItemEnsoul.class) <= 1500L) {
            player.sendActionFailed();
            return;
        }
        gameClient.setLastIncomePacketTimeStamp(RequestExItemEnsoul.class, l);
        if (!RequestExItemEnsoul.CheckPlayerConditions(player)) {
            player.sendPacket(ExEnsoulResult.ENSOUL_FAILED_STATIC, ActionFail.STATIC);
            return;
        }
        ItemInstance itemInstance = player.getInventory().getItemByObjectId(this.qO);
        boolean bl = this.a(player, itemInstance, linkedHashMap = new LinkedHashMap<Integer, Long>());
        if (!bl || linkedHashMap.isEmpty()) {
            player.sendPacket(ExEnsoulResult.ENSOUL_FAILED_STATIC, ActionFail.STATIC);
            return;
        }
        for (Map.Entry iterator : linkedHashMap.entrySet()) {
            if (player.getInventory().getCountOf((Integer)iterator.getKey()) >= (Long)iterator.getValue()) continue;
            player.sendPacket(ExEnsoulResult.ENSOUL_FAILED_STATIC, SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_REQUIRED_ITEMS_CHECK_UP_THEIR_NUMBER, ActionFail.STATIC);
            return;
        }
        boolean bl2 = false;
        try {
            for (Map.Entry entry : linkedHashMap.entrySet()) {
                if (ItemFunctions.removeItem(player, (Integer)entry.getKey(), (Long)entry.getValue(), true, true) >= (Long)entry.getValue()) continue;
                player.sendPacket(ExEnsoulResult.ENSOUL_FAILED_STATIC, ActionFail.STATIC);
                return;
            }
            bl2 = itemInstance.isEquipped();
            if (bl2) {
                player.getInventory().unEquipItem(itemInstance);
            }
            this.a(itemInstance);
        }
        finally {
            if (bl2) {
                player.getInventory().equipItem(itemInstance);
            }
        }
        for (ShortCut shortCut : player.getAllShortCuts()) {
            if (shortCut.getId() != itemInstance.getObjectId() || shortCut.getType() != 1) continue;
            player.sendPacket((IStaticPacket)new ShortCutRegister(player, shortCut));
        }
        player.sendChanges();
        player.sendPacket(new ExEnsoulResult(true, itemInstance), new InventoryUpdate().addModifiedItem(itemInstance));
    }

    private void a(ItemInstance itemInstance) {
        for (ItemEnsoulRequest itemEnsoulRequest : this.a) {
            if (itemEnsoulRequest == null) continue;
            EnsoulFeeSlotType ensoulFeeSlotType = EnsoulFeeSlotType.getSlotType(itemEnsoulRequest.slotType);
            if (ensoulFeeSlotType == EnsoulFeeSlotType.Normal) {
                if (itemEnsoulRequest.slotIndex == 1) {
                    itemInstance.setEnsoulSlotN1(itemEnsoulRequest.optionID);
                    continue;
                }
                if (itemEnsoulRequest.slotIndex != 2) continue;
                itemInstance.setEnsoulSlotN2(itemEnsoulRequest.optionID);
                continue;
            }
            if (ensoulFeeSlotType != EnsoulFeeSlotType.Bm || itemEnsoulRequest.slotIndex != 1) continue;
            itemInstance.setEnsoulSlotBm(itemEnsoulRequest.optionID);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean a(Player player, ItemInstance itemInstance, Map<Integer, Long> map) {
        Grade grade = itemInstance.getCrystalType();
        for (ItemEnsoulRequest itemEnsoulRequest : this.a) {
            int n;
            Object object;
            EnsoulOption ensoulOption;
            if (itemEnsoulRequest == null) continue;
            EnsoulFeeSlotType ensoulFeeSlotType = EnsoulFeeSlotType.getSlotType(itemEnsoulRequest.slotType);
            if (ensoulFeeSlotType == null || itemEnsoulRequest.optionID < 1 || (ensoulOption = EnsoulOptionHolder.getInstance().getOptionById(itemEnsoulRequest.optionID)) == null || ensoulOption.getEnsoulFeeSlotType() != ensoulFeeSlotType) {
                return false;
            }
            ItemInstance itemInstance2 = player.getInventory().getItemByObjectId(itemEnsoulRequest.stoneItemId);
            if (itemInstance2 == null || ensoulOption.getStoneId() != itemInstance2.getItemId()) {
                return false;
            }
            if (ensoulFeeSlotType == EnsoulFeeSlotType.Normal) {
                if (itemEnsoulRequest.slotIndex == 1) {
                    if (itemInstance.getEnsoulSlotN2() > 0 && (object = EnsoulOptionHolder.getInstance().getOptionById(itemInstance.getEnsoulSlotN2())) != null && ((EnsoulOption)object).getOptionType() == ensoulOption.getOptionType() || itemInstance.getTemplate().getEnsoulNormalSlots() < 1) {
                        return false;
                    }
                    n = itemInstance.getEnsoulSlotN1();
                } else {
                    if (itemEnsoulRequest.slotIndex != 2) return false;
                    if (itemInstance.getEnsoulSlotN1() > 0 && (object = EnsoulOptionHolder.getInstance().getOptionById(itemInstance.getEnsoulSlotN1())) != null && ((EnsoulOption)object).getOptionType() == ensoulOption.getOptionType() || itemInstance.getTemplate().getEnsoulNormalSlots() < 2) {
                        return false;
                    }
                    n = itemInstance.getEnsoulSlotN2();
                }
            } else {
                if (ensoulFeeSlotType != EnsoulFeeSlotType.Bm || itemEnsoulRequest.slotIndex != 1) return false;
                if (itemInstance.getTemplate().getEnsoulBmSlots() < 1) {
                    return false;
                }
                n = itemInstance.getEnsoulSlotBm();
            }
            Object object2 = object = n == 0 ? EnsoulFeeHolder.getInstance().getInsertFee(grade, ensoulFeeSlotType) : EnsoulFeeHolder.getInstance().getReplaceFee(grade, ensoulFeeSlotType);
            if (object == null || object.isEmpty() || itemEnsoulRequest.slotIndex < 1 || itemEnsoulRequest.slotIndex > object.size()) {
                return false;
            }
            Pair pair = (Pair)object.get(itemEnsoulRequest.slotIndex - 1);
            map.put((Integer)pair.getKey(), map.getOrDefault(pair.getKey(), 0L) + (Long)pair.getValue());
            map.put(ensoulOption.getStoneId(), map.getOrDefault(ensoulOption.getStoneId(), 0L) + 1L);
        }
        return true;
    }

    private static class ItemEnsoulRequest {
        int slotType;
        int slotIndex;
        int stoneItemId;
        int optionID;

        public ItemEnsoulRequest(int n, int n2, int n3, int n4) {
            this.slotType = n;
            this.slotIndex = n2;
            this.stoneItemId = n3;
            this.optionID = n4;
        }
    }
}
