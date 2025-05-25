/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.Config
 *  l2.gameserver.handler.items.IRefineryHandler
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.actor.instances.player.ShortCut
 *  l2.gameserver.model.items.ItemInstance
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.ExPutCommissionResultForVariationMake
 *  l2.gameserver.network.l2.s2c.ExPutIntensiveResultForVariationMake
 *  l2.gameserver.network.l2.s2c.ExPutItemResultForVariationCancel
 *  l2.gameserver.network.l2.s2c.ExPutItemResultForVariationMake
 *  l2.gameserver.network.l2.s2c.ExShowRefineryInterface
 *  l2.gameserver.network.l2.s2c.ExShowVariationCancelWindow
 *  l2.gameserver.network.l2.s2c.ExVariationCancelResult
 *  l2.gameserver.network.l2.s2c.ExVariationResult
 *  l2.gameserver.network.l2.s2c.InventoryUpdate
 *  l2.gameserver.network.l2.s2c.ShortCutRegister
 *  l2.gameserver.network.l2.s2c.SystemMessage
 *  l2.gameserver.network.l2.s2c.UserInfoType
 *  l2.gameserver.utils.Log
 *  l2.gameserver.utils.Log$ItemLog
 *  org.apache.commons.lang3.ArrayUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package handler.items;

import handler.items.ScriptItemHandler;
import l2.gameserver.Config;
import l2.gameserver.handler.items.IRefineryHandler;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.actor.instances.player.ShortCut;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
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
import l2.gameserver.network.l2.s2c.UserInfoType;
import l2.gameserver.utils.Log;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Appearance
extends ScriptItemHandler
implements IRefineryHandler {
    private static final Logger H = LoggerFactory.getLogger(Appearance.class);
    private static final int[] L = new int[]{Config.APPEARANCE_APPLY_ITEM_ID, Config.APPEARANCE_CANCEL_ITEM_ID};

    public boolean useItem(Playable playable, ItemInstance itemInstance, boolean bl) {
        if (playable == null || !playable.isPlayer()) {
            return false;
        }
        Player player = (Player)playable;
        if (itemInstance.getItemId() == Config.APPEARANCE_APPLY_ITEM_ID) {
            player.setRefineryHandler((IRefineryHandler)this);
            this.onInitRefinery(player);
            return true;
        }
        if (itemInstance.getItemId() == Config.APPEARANCE_CANCEL_ITEM_ID) {
            player.setRefineryHandler((IRefineryHandler)this);
            this.onInitRefineryCancel(player);
            return true;
        }
        return false;
    }

    public int[] getItemIds() {
        return L;
    }

    private boolean f(Player player) {
        if (player.getLevel() < 46) {
            player.sendMessage(new CustomMessage("items.appearance.lowlevel", player, new Object[0]));
            return false;
        }
        if (player.isInStoreMode() || player.isInTrade()) {
            player.sendMessage(new CustomMessage("items.appearance.appearNoTrade", player, new Object[0]));
            return false;
        }
        if (player.isDead()) {
            player.sendMessage(new CustomMessage("items.appearance.appearDeadNo", player, new Object[0]));
            return false;
        }
        if (player.isParalyzed()) {
            player.sendMessage(new CustomMessage("items.appearance.appearParalyzed", player, new Object[0]));
            return false;
        }
        if (player.isFishing()) {
            player.sendMessage(new CustomMessage("items.appearance.appearFishing", player, new Object[0]));
            return false;
        }
        if (player.isSitting()) {
            player.sendMessage(new CustomMessage("items.appearance.appearSitting", player, new Object[0]));
            return false;
        }
        if (player.isActionsDisabled()) {
            player.sendActionFailed();
            return false;
        }
        return true;
    }

    public void onInitRefinery(Player player) {
        if (!this.f(player)) {
            return;
        }
        player.sendMessage(new CustomMessage("items.appearance.appearSelectFirstItem", player, new Object[0]));
        player.sendPacket((IStaticPacket)ExShowRefineryInterface.STATIC);
    }

    public void onPutTargetItem(Player player, ItemInstance itemInstance) {
        if (!this.f(player)) {
            player.sendPacket((IStaticPacket)ExPutItemResultForVariationMake.FAIL_PACKET);
            return;
        }
        if (itemInstance.getVisibleItemId() != itemInstance.getItemId()) {
            player.sendMessage(new CustomMessage("items.appearance.appearAlreadyAppeared", player, new Object[0]));
            return;
        }
        if (ArrayUtils.contains((int[])Config.APPEARANCE_PROHIBITED_CHANGE, (int)itemInstance.getItemId())) {
            player.sendMessage(new CustomMessage("items.appearance.cantbechange", player, new Object[0]));
            return;
        }
        if (!itemInstance.isWeapon() && !itemInstance.isArmor() || !itemInstance.canBeEnchanted(false)) {
            player.sendPacket((IStaticPacket)SystemMsg.THIS_IS_NOT_A_SUITABLE_ITEM);
            return;
        }
        player.sendMessage(new CustomMessage("items.appearance.appearSelectSecondItem", player, new Object[0]));
        player.sendPacket((IStaticPacket)new ExPutItemResultForVariationMake(itemInstance.getObjectId(), true));
    }

    public void onPutMineralItem(Player player, ItemInstance itemInstance, ItemInstance itemInstance2) {
        if (!this.f(player)) {
            player.sendActionFailed();
            return;
        }
        if (itemInstance.getVisibleItemId() != itemInstance.getItemId()) {
            player.sendMessage(new CustomMessage("items.appearance.appearAlreadyAppeared", player, new Object[0]));
            player.sendActionFailed();
            return;
        }
        if (ArrayUtils.contains((int[])Config.APPEARANCE_PROHIBITED_BE_CHANGED, (int)itemInstance2.getItemId())) {
            player.sendMessage(new CustomMessage("items.appearance.cantbechanged", player, new Object[0]));
            player.sendActionFailed();
            return;
        }
        if (itemInstance == itemInstance2 || !itemInstance.isWeapon() && !itemInstance.isArmor() || !itemInstance.canBeEnchanted(false) || !itemInstance2.isWeapon() && !itemInstance2.isArmor() || !itemInstance2.canBeEnchanted(false)) {
            player.sendPacket((IStaticPacket)SystemMsg.THIS_IS_NOT_A_SUITABLE_ITEM);
            player.sendActionFailed();
            return;
        }
        if (itemInstance.getTemplate().getBodyPart() != itemInstance2.getTemplate().getBodyPart() || itemInstance.getTemplate().getItemClass() != itemInstance2.getTemplate().getItemClass()) {
            player.sendPacket((IStaticPacket)SystemMsg.THIS_IS_NOT_A_SUITABLE_ITEM);
            player.sendActionFailed();
            return;
        }
        if (itemInstance.getTemplate().getItemType() != itemInstance2.getTemplate().getItemType()) {
            player.sendPacket((IStaticPacket)SystemMsg.THIS_IS_NOT_A_SUITABLE_ITEM);
            player.sendActionFailed();
            return;
        }
        player.sendPacket(new IStaticPacket[]{new ExPutIntensiveResultForVariationMake(itemInstance2.getObjectId(), itemInstance2.getItemId(), Config.APPEARANCE_SUPPORT_ITEM_ID, Config.APPEARANCE_SUPPORT_ITEM_CNT, true), ((SystemMessage)new SystemMessage(SystemMsg.REQUIRES_S2_S1).addNumber(Config.APPEARANCE_SUPPORT_ITEM_CNT)).addItemName(Config.APPEARANCE_SUPPORT_ITEM_ID)});
    }

    public void onPutGemstoneItem(Player player, ItemInstance itemInstance, ItemInstance itemInstance2, ItemInstance itemInstance3, long l) {
        if (!this.f(player)) {
            player.sendActionFailed();
            return;
        }
        if (itemInstance.getVisibleItemId() != itemInstance.getItemId()) {
            player.sendMessage(new CustomMessage("items.appearance.appearAlreadyAppeared", player, new Object[0]));
            player.sendActionFailed();
            return;
        }
        if (itemInstance == itemInstance2 || !itemInstance.isWeapon() && !itemInstance.isArmor() || !itemInstance.canBeEnchanted(false) || !itemInstance2.isWeapon() && !itemInstance2.isArmor() || !itemInstance2.canBeEnchanted(false)) {
            player.sendPacket((IStaticPacket)SystemMsg.THIS_IS_NOT_A_SUITABLE_ITEM);
            player.sendActionFailed();
            return;
        }
        if (itemInstance.getTemplate().getBodyPart() != itemInstance2.getTemplate().getBodyPart() || itemInstance.getTemplate().getItemClass() != itemInstance2.getTemplate().getItemClass()) {
            player.sendPacket((IStaticPacket)SystemMsg.THIS_IS_NOT_A_SUITABLE_ITEM);
            player.sendActionFailed();
            return;
        }
        if (itemInstance.getTemplate().getItemType() != itemInstance2.getTemplate().getItemType()) {
            player.sendPacket((IStaticPacket)SystemMsg.THIS_IS_NOT_A_SUITABLE_ITEM);
            player.sendActionFailed();
            return;
        }
        if (itemInstance3.getItemId() != Config.APPEARANCE_SUPPORT_ITEM_ID) {
            player.sendPacket((IStaticPacket)SystemMsg.THIS_IS_NOT_A_SUITABLE_ITEM);
            player.sendActionFailed();
            return;
        }
        if (player.getInventory().getCountOf(Config.APPEARANCE_SUPPORT_ITEM_ID) < Config.APPEARANCE_SUPPORT_ITEM_CNT) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_MATERIALS_TO_PERFORM_THAT_ACTION);
            player.sendActionFailed();
            return;
        }
        player.sendPacket(new IStaticPacket[]{new ExPutCommissionResultForVariationMake(itemInstance3.getObjectId(), Config.APPEARANCE_SUPPORT_ITEM_CNT), SystemMsg.PRESS_THE_AUGMENT_BUTTON_TO_BEGIN});
    }

    public void onRequestRefine(Player player, ItemInstance itemInstance, ItemInstance itemInstance2, ItemInstance itemInstance3, long l) {
        if (!this.f(player)) {
            player.sendActionFailed();
            return;
        }
        if (itemInstance.getVisibleItemId() != itemInstance.getItemId()) {
            player.sendMessage(new CustomMessage("items.appearance.appearAlreadyAppeared", player, new Object[0]));
            player.sendActionFailed();
            return;
        }
        if (itemInstance == itemInstance2 || !itemInstance.isWeapon() && !itemInstance.isArmor() || !itemInstance.canBeEnchanted(false) || !itemInstance2.isWeapon() && !itemInstance2.isArmor() || !itemInstance2.canBeEnchanted(false)) {
            player.sendPacket((IStaticPacket)SystemMsg.THIS_IS_NOT_A_SUITABLE_ITEM);
            player.sendActionFailed();
            return;
        }
        if (itemInstance.getTemplate().getBodyPart() != itemInstance2.getTemplate().getBodyPart() || itemInstance.getTemplate().getItemClass() != itemInstance2.getTemplate().getItemClass()) {
            player.sendPacket((IStaticPacket)SystemMsg.THIS_IS_NOT_A_SUITABLE_ITEM);
            player.sendActionFailed();
            return;
        }
        if (itemInstance.getTemplate().getItemType() != itemInstance2.getTemplate().getItemType()) {
            player.sendPacket((IStaticPacket)SystemMsg.THIS_IS_NOT_A_SUITABLE_ITEM);
            player.sendActionFailed();
            return;
        }
        if (itemInstance3.getItemId() != Config.APPEARANCE_SUPPORT_ITEM_ID) {
            player.sendPacket((IStaticPacket)SystemMsg.THIS_IS_NOT_A_SUITABLE_ITEM);
            player.sendActionFailed();
            return;
        }
        if (player.getInventory().getCountOf(Config.APPEARANCE_SUPPORT_ITEM_ID) < Config.APPEARANCE_SUPPORT_ITEM_CNT) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_MATERIALS_TO_PERFORM_THAT_ACTION);
            player.sendActionFailed();
            return;
        }
        if (!player.getInventory().destroyItem(itemInstance3, Config.APPEARANCE_SUPPORT_ITEM_CNT)) {
            return;
        }
        int n = itemInstance2.getItemId();
        if (itemInstance2.isEquipped()) {
            player.getInventory().unEquipItem(itemInstance2);
        }
        if (!player.getInventory().destroyItem(itemInstance2, 1L)) {
            return;
        }
        if (!player.getInventory().destroyItemByItemId(Config.APPEARANCE_APPLY_ITEM_ID, 1L)) {
            return;
        }
        boolean bl = itemInstance.isEquipped();
        if (bl) {
            player.getInventory().unEquipItem(itemInstance);
        }
        itemInstance.setVisibleItemId(n);
        Log.LogItem((Player)player, (Log.ItemLog)Log.ItemLog.ChangeAppearance, (ItemInstance)itemInstance, (long)1L, (long)0L, (int)n);
        if (bl) {
            player.getInventory().equipItem(itemInstance);
        }
        player.sendPacket((IStaticPacket)new InventoryUpdate().addModifiedItem(itemInstance));
        for (ShortCut shortCut : player.getAllShortCuts()) {
            if (shortCut.getId() != itemInstance.getObjectId() || shortCut.getType() != 1) continue;
            player.sendPacket((IStaticPacket)new ShortCutRegister(player, shortCut));
        }
        player.sendChanges();
        player.sendPacket((IStaticPacket)new ExVariationResult(itemInstance.getVariationStat1(), itemInstance.getVariationStat2(), 1));
        player.broadcastUserInfo(true, new UserInfoType[0]);
    }

    public void onInitRefineryCancel(Player player) {
        if (!this.f(player)) {
            return;
        }
        player.sendMessage(new CustomMessage("items.appearance.appearSelectFroCancel", player, new Object[0]));
        player.sendPacket((IStaticPacket)ExShowVariationCancelWindow.STATIC);
    }

    public void onPutTargetCancelItem(Player player, ItemInstance itemInstance) {
        if (!this.f(player)) {
            player.sendActionFailed();
            return;
        }
        if (itemInstance.getVisibleItemId() == itemInstance.getItemId()) {
            player.sendPacket((IStaticPacket)SystemMsg.THIS_IS_NOT_A_SUITABLE_ITEM);
            player.sendActionFailed();
            return;
        }
        if (!itemInstance.isWeapon() && !itemInstance.isArmor()) {
            player.sendPacket((IStaticPacket)SystemMsg.THIS_IS_NOT_A_SUITABLE_ITEM);
            player.sendActionFailed();
            return;
        }
        player.sendPacket((IStaticPacket)new ExPutItemResultForVariationCancel(itemInstance, Config.APPEARANCE_CANCEL_PRICE, true));
    }

    public void onRequestCancelRefine(Player player, ItemInstance itemInstance) {
        if (!this.f(player)) {
            player.sendActionFailed();
            return;
        }
        if (itemInstance.getVisibleItemId() == itemInstance.getItemId()) {
            player.sendPacket((IStaticPacket)SystemMsg.THIS_IS_NOT_A_SUITABLE_ITEM);
            player.sendActionFailed();
            return;
        }
        if (!itemInstance.isWeapon() && !itemInstance.isArmor()) {
            player.sendPacket((IStaticPacket)SystemMsg.THIS_IS_NOT_A_SUITABLE_ITEM);
            player.sendActionFailed();
            return;
        }
        if (!player.reduceAdena(Config.APPEARANCE_CANCEL_PRICE, true)) {
            player.sendPacket((IStaticPacket)ExVariationCancelResult.FAIL_PACKET);
            player.sendActionFailed();
            return;
        }
        if (!player.getInventory().destroyItemByItemId(Config.APPEARANCE_CANCEL_ITEM_ID, 1L)) {
            return;
        }
        boolean bl = itemInstance.isEquipped();
        if (bl) {
            player.getInventory().unEquipItem(itemInstance);
        }
        itemInstance.setVisibleItemId(0);
        if (bl) {
            player.getInventory().equipItem(itemInstance);
        }
        InventoryUpdate inventoryUpdate = new InventoryUpdate().addModifiedItem(itemInstance);
        Log.LogItem((Player)player, (Log.ItemLog)Log.ItemLog.RemoveAppearance, (ItemInstance)itemInstance);
        SystemMessage systemMessage = new SystemMessage(SystemMsg.AUGMENTATION_HAS_BEEN_SUCCESSFULLY_REMOVED_FROM_YOUR_S1);
        systemMessage.addItemName(itemInstance.getItemId());
        player.sendPacket(new IStaticPacket[]{new ExVariationCancelResult(1), inventoryUpdate, systemMessage});
        for (ShortCut shortCut : player.getAllShortCuts()) {
            if (shortCut.getId() != itemInstance.getObjectId() || shortCut.getType() != 1) continue;
            player.sendPacket((IStaticPacket)new ShortCutRegister(player, shortCut));
        }
        player.sendChanges();
        player.broadcastUserInfo(true, new UserInfoType[0]);
    }
}
