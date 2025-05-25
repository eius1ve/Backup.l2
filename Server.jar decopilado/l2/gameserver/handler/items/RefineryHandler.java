/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.tuple.Pair
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.handler.items;

import java.util.List;
import l2.commons.util.RandomUtils;
import l2.gameserver.GameServer;
import l2.gameserver.data.xml.holder.VariationChanceHolder;
import l2.gameserver.data.xml.holder.VariationGroupHolder;
import l2.gameserver.handler.items.IRefineryHandler;
import l2.gameserver.model.Player;
import l2.gameserver.model.actor.instances.player.ShortCut;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ActionFail;
import l2.gameserver.network.l2.s2c.ExPutCommissionResultForVariationMake;
import l2.gameserver.network.l2.s2c.ExPutIntensiveResultForVariationMake;
import l2.gameserver.network.l2.s2c.ExPutItemResultForVariationCancel;
import l2.gameserver.network.l2.s2c.ExPutItemResultForVariationMake;
import l2.gameserver.network.l2.s2c.ExShowRefineryInterface;
import l2.gameserver.network.l2.s2c.ExShowVariationCancelWindow;
import l2.gameserver.network.l2.s2c.ExVariationCancelResult;
import l2.gameserver.network.l2.s2c.ExVariationResult;
import l2.gameserver.network.l2.s2c.InventoryUpdate;
import l2.gameserver.network.l2.s2c.ShortCutRegister;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.scripts.Functions;
import l2.gameserver.templates.item.support.VariationChanceData;
import l2.gameserver.templates.item.support.VariationGroupData;
import l2.gameserver.utils.Log;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RefineryHandler
implements IRefineryHandler {
    private static final RefineryHandler a = new RefineryHandler();
    protected static final Logger LOG = LoggerFactory.getLogger(RefineryHandler.class);

    public static RefineryHandler getInstance() {
        return a;
    }

    private RefineryHandler() {
    }

    @Override
    public void onInitRefinery(Player player) {
        if (!Functions.CheckPlayerConditions(player)) {
            player.sendActionFailed();
            return;
        }
        player.sendPacket(SystemMsg.SELECT_THE_ITEM_TO_BE_AUGMENTED, ExShowRefineryInterface.STATIC);
    }

    @Override
    public void onPutTargetItem(Player player, ItemInstance itemInstance) {
        if (!Functions.CheckPlayerConditions(player)) {
            player.sendActionFailed();
            return;
        }
        if (itemInstance.isAugmented()) {
            player.sendPacket(SystemMsg.ONCE_AN_ITEM_IS_AUGMENTED_IT_CANNOT_BE_AUGMENTED_AGAIN, ActionFail.STATIC);
            return;
        }
        List<VariationGroupData> list = VariationGroupHolder.getInstance().getDataForItemId(itemInstance.getItemId());
        if (list == null || list.isEmpty()) {
            player.sendPacket(SystemMsg.THIS_IS_NOT_A_SUITABLE_ITEM, ActionFail.STATIC);
            return;
        }
        player.sendPacket(SystemMsg.SELECT_THE_CATALYST_FOR_AUGMENTATION, new ExPutItemResultForVariationMake(itemInstance.getObjectId(), true));
    }

    @Override
    public void onPutMineralItem(Player player, ItemInstance itemInstance, ItemInstance itemInstance2) {
        if (!Functions.CheckPlayerConditions(player)) {
            player.sendPacket((IStaticPacket)ActionFail.STATIC);
            return;
        }
        if (itemInstance.isAugmented()) {
            player.sendPacket(SystemMsg.ONCE_AN_ITEM_IS_AUGMENTED_IT_CANNOT_BE_AUGMENTED_AGAIN, ActionFail.STATIC);
            return;
        }
        List<VariationGroupData> list = VariationGroupHolder.getInstance().getDataForItemId(itemInstance.getItemId());
        if (list == null || list.isEmpty()) {
            player.sendPacket(SystemMsg.THIS_IS_NOT_A_SUITABLE_ITEM, ActionFail.STATIC);
            return;
        }
        int n = itemInstance2.getItemId();
        VariationGroupData variationGroupData = null;
        Pair<VariationChanceData, VariationChanceData> pair = VariationChanceHolder.getInstance().getVariationChanceDataForMineral(n);
        for (VariationGroupData variationGroupData2 : list) {
            if (variationGroupData2.getMineralItemId() != n) continue;
            variationGroupData = variationGroupData2;
            break;
        }
        if (null == variationGroupData || null == pair) {
            player.sendPacket(SystemMsg.THIS_IS_NOT_A_SUITABLE_ITEM, ActionFail.STATIC);
            return;
        }
        if (itemInstance.getTemplate().isMageItem() && pair.getRight() == null) {
            LOG.warn("No mage variation for item " + itemInstance.getItemId() + " and mineral " + itemInstance2.getItemId());
            player.sendPacket(SystemMsg.THIS_IS_NOT_A_SUITABLE_ITEM, ActionFail.STATIC);
            return;
        }
        if (!itemInstance.getTemplate().isMageItem() && pair.getLeft() == null) {
            LOG.warn("No warrior variation for item " + itemInstance.getItemId() + " and mineral " + itemInstance2.getItemId());
            player.sendPacket(SystemMsg.THIS_IS_NOT_A_SUITABLE_ITEM, ActionFail.STATIC);
            return;
        }
        if (!itemInstance2.getTemplate().testCondition(player, itemInstance2, true)) {
            player.sendPacket((IStaticPacket)ActionFail.STATIC);
            return;
        }
        player.sendPacket(new IStaticPacket[]{new ExPutIntensiveResultForVariationMake(itemInstance2.getObjectId(), n, variationGroupData.getGemstoneItemId(), variationGroupData.getGemstoneItemCnt(), true), ((SystemMessage)new SystemMessage(SystemMsg.REQUIRES_S2_S1).addNumber(variationGroupData.getGemstoneItemCnt())).addItemName(variationGroupData.getGemstoneItemId())});
    }

    @Override
    public void onPutGemstoneItem(Player player, ItemInstance itemInstance, ItemInstance itemInstance2, ItemInstance itemInstance3, long l) {
        if (!Functions.CheckPlayerConditions(player)) {
            player.sendPacket((IStaticPacket)ActionFail.STATIC);
            return;
        }
        if (itemInstance.isAugmented()) {
            player.sendPacket(SystemMsg.ONCE_AN_ITEM_IS_AUGMENTED_IT_CANNOT_BE_AUGMENTED_AGAIN, ActionFail.STATIC);
            return;
        }
        List<VariationGroupData> list = VariationGroupHolder.getInstance().getDataForItemId(itemInstance.getItemId());
        if (list == null || list.isEmpty()) {
            player.sendPacket(SystemMsg.THIS_IS_NOT_A_SUITABLE_ITEM, ActionFail.STATIC);
            return;
        }
        int n = itemInstance2.getItemId();
        int n2 = itemInstance3.getItemId();
        VariationGroupData variationGroupData = null;
        Pair<VariationChanceData, VariationChanceData> pair = VariationChanceHolder.getInstance().getVariationChanceDataForMineral(n);
        for (VariationGroupData variationGroupData2 : list) {
            if (variationGroupData2.getMineralItemId() != n || variationGroupData2.getGemstoneItemId() != n2) continue;
            variationGroupData = variationGroupData2;
            break;
        }
        if (null == variationGroupData || pair == null) {
            player.sendPacket(SystemMsg.THIS_IS_NOT_A_SUITABLE_ITEM, ActionFail.STATIC);
            return;
        }
        if (pair.getLeft() != null && ((VariationChanceData)pair.getLeft()).getMineralItemId() != variationGroupData.getMineralItemId() || pair.getRight() != null && ((VariationChanceData)pair.getRight()).getMineralItemId() != variationGroupData.getMineralItemId()) {
            player.sendPacket(SystemMsg.THIS_IS_NOT_A_SUITABLE_ITEM, ActionFail.STATIC);
            return;
        }
        if (itemInstance.getTemplate().isMageItem() && pair.getRight() == null) {
            LOG.warn("No mage variation for item " + itemInstance.getItemId() + " and mineral " + itemInstance2.getItemId());
            player.sendPacket(SystemMsg.THIS_IS_NOT_A_SUITABLE_ITEM, ActionFail.STATIC);
            return;
        }
        if (!itemInstance.getTemplate().isMageItem() && pair.getLeft() == null) {
            LOG.warn("No warrior variation for item " + itemInstance.getItemId() + " and mineral " + itemInstance2.getItemId());
            player.sendPacket(SystemMsg.THIS_IS_NOT_A_SUITABLE_ITEM, ActionFail.STATIC);
            return;
        }
        if (!itemInstance2.getTemplate().testCondition(player, itemInstance2, true)) {
            player.sendPacket((IStaticPacket)ActionFail.STATIC);
            return;
        }
        if (variationGroupData.getGemstoneItemCnt() > l || player.getInventory().getCountOf(n2) < variationGroupData.getGemstoneItemCnt()) {
            player.sendPacket(SystemMsg.GEMSTONE_QUANTITY_IS_INCORRECT, ActionFail.STATIC);
            return;
        }
        player.sendPacket(new ExPutCommissionResultForVariationMake(itemInstance3.getObjectId(), variationGroupData.getGemstoneItemCnt()), SystemMsg.PRESS_THE_AUGMENT_BUTTON_TO_BEGIN);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void onRequestRefine(Player player, ItemInstance itemInstance, ItemInstance itemInstance2, ItemInstance itemInstance3, long l) {
        Integer n;
        void var13_16;
        if (!Functions.CheckPlayerConditions(player)) {
            player.sendPacket((IStaticPacket)ActionFail.STATIC);
            return;
        }
        if (itemInstance.isAugmented()) {
            player.sendPacket(SystemMsg.ONCE_AN_ITEM_IS_AUGMENTED_IT_CANNOT_BE_AUGMENTED_AGAIN, ActionFail.STATIC);
            return;
        }
        List<VariationGroupData> list = VariationGroupHolder.getInstance().getDataForItemId(itemInstance.getItemId());
        if (list == null || list.isEmpty()) {
            player.sendPacket(SystemMsg.THIS_IS_NOT_A_SUITABLE_ITEM, ActionFail.STATIC);
            return;
        }
        int n2 = itemInstance2.getItemId();
        int n3 = itemInstance3.getItemId();
        VariationGroupData variationGroupData = null;
        Pair<VariationChanceData, VariationChanceData> pair = VariationChanceHolder.getInstance().getVariationChanceDataForMineral(n2);
        for (VariationGroupData object2 : list) {
            if (object2.getMineralItemId() != n2 || object2.getGemstoneItemId() != n3) continue;
            variationGroupData = object2;
            break;
        }
        if (null == variationGroupData || pair == null) {
            player.sendPacket(SystemMsg.THIS_IS_NOT_A_SUITABLE_ITEM, ActionFail.STATIC);
            return;
        }
        if (pair.getLeft() != null && ((VariationChanceData)pair.getLeft()).getMineralItemId() != variationGroupData.getMineralItemId() || pair.getRight() != null && ((VariationChanceData)pair.getRight()).getMineralItemId() != variationGroupData.getMineralItemId()) {
            player.sendPacket(SystemMsg.THIS_IS_NOT_A_SUITABLE_ITEM, ActionFail.STATIC);
            return;
        }
        if (itemInstance.getTemplate().isMageItem() && pair.getRight() == null) {
            LOG.warn("No mage variation for item " + itemInstance.getItemId() + " and mineral " + itemInstance2.getItemId());
            player.sendPacket(SystemMsg.THIS_IS_NOT_A_SUITABLE_ITEM, ActionFail.STATIC);
            return;
        }
        if (!itemInstance.getTemplate().isMageItem() && pair.getLeft() == null) {
            LOG.warn("No warrior variation for item " + itemInstance.getItemId() + " and mineral " + itemInstance2.getItemId());
            player.sendPacket(SystemMsg.THIS_IS_NOT_A_SUITABLE_ITEM, ActionFail.STATIC);
            return;
        }
        if (!itemInstance2.getTemplate().testCondition(player, itemInstance2, true)) {
            player.sendPacket((IStaticPacket)ActionFail.STATIC);
            return;
        }
        if (variationGroupData.getGemstoneItemCnt() > l || player.getInventory().getCountOf(n3) < variationGroupData.getGemstoneItemCnt()) {
            player.sendPacket(SystemMsg.GEMSTONE_QUANTITY_IS_INCORRECT, ActionFail.STATIC);
            return;
        }
        List list2 = null;
        Object var13_13 = null;
        if (itemInstance.getTemplate().isMageItem()) {
            list2 = ((VariationChanceData)pair.getRight()).getVariation1();
            List<Pair<List<Pair<Integer, Double>>, Double>> list3 = ((VariationChanceData)pair.getRight()).getVariation2();
        } else {
            list2 = ((VariationChanceData)pair.getLeft()).getVariation1();
            List<Pair<List<Pair<Integer, Double>>, Double>> list4 = ((VariationChanceData)pair.getLeft()).getVariation2();
        }
        List list5 = (List)RandomUtils.pickRandomSortedGroup(list2, 100.0);
        List list6 = (List)RandomUtils.pickRandomSortedGroup(var13_16, 100.0);
        Integer n4 = list5 != null ? (Integer)RandomUtils.pickRandomSortedGroup(list5, 100.0) : Integer.valueOf(0);
        Integer n5 = n = list6 != null ? (Integer)RandomUtils.pickRandomSortedGroup(list6, 100.0) : Integer.valueOf(0);
        if (!player.getInventory().destroyItem(itemInstance3, variationGroupData.getGemstoneItemCnt())) {
            return;
        }
        if (!player.getInventory().destroyItem(itemInstance2, 1L)) {
            return;
        }
        boolean bl = itemInstance.isEquipped();
        if (bl) {
            player.getInventory().unEquipItem(itemInstance);
        }
        itemInstance.setVariationStat1(n4);
        itemInstance.setVariationStat2(n);
        if (bl) {
            player.getInventory().equipItem(itemInstance);
        }
        player.sendPacket((IStaticPacket)new InventoryUpdate().addModifiedItem(itemInstance));
        for (ShortCut shortCut : player.getAllShortCuts()) {
            if (shortCut.getId() != itemInstance.getObjectId() || shortCut.getType() != 1) continue;
            player.sendPacket((IStaticPacket)new ShortCutRegister(player, shortCut));
        }
        player.sendChanges();
        player.sendPacket((IStaticPacket)new ExVariationResult(n4, n, 1));
        GameServer.getInstance().getListeners().fireEvent("augmentSuccess", player, itemInstance, n4, n);
        Log.LogItem(player, Log.ItemLog.AugmentAdded, itemInstance);
    }

    @Override
    public void onInitRefineryCancel(Player player) {
        if (!Functions.CheckPlayerConditions(player)) {
            return;
        }
        player.sendPacket(SystemMsg.SELECT_THE_ITEM_FROM_WHICH_YOU_WISH_TO_REMOVE_AUGMENTATION, ExShowVariationCancelWindow.STATIC);
    }

    @Override
    public void onPutTargetCancelItem(Player player, ItemInstance itemInstance) {
        if (!Functions.CheckPlayerConditions(player)) {
            player.sendPacket((IStaticPacket)ActionFail.STATIC);
            return;
        }
        List<VariationGroupData> list = VariationGroupHolder.getInstance().getDataForItemId(itemInstance.getItemId());
        if (list == null || list.isEmpty()) {
            player.sendPacket(SystemMsg.THIS_IS_NOT_A_SUITABLE_ITEM, ActionFail.STATIC);
            return;
        }
        if (!itemInstance.isAugmented()) {
            player.sendPacket(SystemMsg.AUGMENTATION_REMOVAL_CAN_ONLY_BE_DONE_ON_AN_AUGMENTED_ITEM, ActionFail.STATIC);
            return;
        }
        VariationGroupData variationGroupData = list.get(0);
        if (variationGroupData == null) {
            player.sendPacket((IStaticPacket)ActionFail.STATIC);
            return;
        }
        player.sendPacket((IStaticPacket)new ExPutItemResultForVariationCancel(itemInstance, variationGroupData.getCancelPrice(), true));
    }

    @Override
    public void onRequestCancelRefine(Player player, ItemInstance itemInstance) {
        if (!Functions.CheckPlayerConditions(player)) {
            player.sendPacket((IStaticPacket)ActionFail.STATIC);
            return;
        }
        List<VariationGroupData> list = VariationGroupHolder.getInstance().getDataForItemId(itemInstance.getItemId());
        if (list == null || list.isEmpty()) {
            player.sendPacket(SystemMsg.THIS_IS_NOT_A_SUITABLE_ITEM, ActionFail.STATIC);
            return;
        }
        if (!itemInstance.isAugmented()) {
            player.sendPacket(SystemMsg.AUGMENTATION_REMOVAL_CAN_ONLY_BE_DONE_ON_AN_AUGMENTED_ITEM, ActionFail.STATIC);
            return;
        }
        VariationGroupData variationGroupData = list.get(0);
        if (variationGroupData == null) {
            player.sendPacket((IStaticPacket)ActionFail.STATIC);
            return;
        }
        long l = variationGroupData.getCancelPrice();
        if (l < 0L) {
            player.sendPacket((IStaticPacket)ExVariationCancelResult.FAIL_PACKET);
        }
        if (!player.reduceAdena(l, true)) {
            player.sendPacket(ActionFail.STATIC, SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
            return;
        }
        boolean bl = itemInstance.isEquipped();
        if (bl) {
            player.getInventory().unEquipItem(itemInstance);
        }
        itemInstance.setVariationStat1(0);
        itemInstance.setVariationStat2(0);
        if (bl) {
            player.getInventory().equipItem(itemInstance);
        }
        InventoryUpdate inventoryUpdate = new InventoryUpdate().addModifiedItem(itemInstance);
        SystemMessage systemMessage = new SystemMessage(SystemMsg.AUGMENTATION_HAS_BEEN_SUCCESSFULLY_REMOVED_FROM_YOUR_S1);
        systemMessage.addItemName(itemInstance.getItemId());
        player.sendPacket(new ExVariationCancelResult(1), inventoryUpdate, systemMessage);
        for (ShortCut shortCut : player.getAllShortCuts()) {
            if (shortCut.getId() != itemInstance.getObjectId() || shortCut.getType() != 1) continue;
            player.sendPacket((IStaticPacket)new ShortCutRegister(player, shortCut));
        }
        player.sendChanges();
        itemInstance.save();
    }
}
