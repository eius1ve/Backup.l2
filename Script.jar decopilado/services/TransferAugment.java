/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.Config
 *  l2.gameserver.data.StringHolder
 *  l2.gameserver.data.xml.holder.OptionDataHolder
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.actor.instances.player.ShortCut
 *  l2.gameserver.model.items.ItemInstance
 *  l2.gameserver.model.items.ItemInstance$ItemLocation
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.InventoryUpdate
 *  l2.gameserver.network.l2.s2c.NpcHtmlMessage
 *  l2.gameserver.network.l2.s2c.ShortCutRegister
 *  l2.gameserver.network.l2.s2c.SystemMessage
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.templates.OptionDataTemplate
 *  l2.gameserver.templates.item.ItemTemplate
 *  l2.gameserver.utils.ItemFunctions
 *  l2.gameserver.utils.Log
 *  l2.gameserver.utils.Log$ItemLog
 *  org.apache.commons.lang3.ArrayUtils
 *  org.apache.commons.lang3.StringUtils
 */
package services;

import java.util.LinkedList;
import java.util.List;
import l2.gameserver.Config;
import l2.gameserver.data.StringHolder;
import l2.gameserver.data.xml.holder.OptionDataHolder;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.actor.instances.player.ShortCut;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.InventoryUpdate;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.network.l2.s2c.ShortCutRegister;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.templates.OptionDataTemplate;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.utils.ItemFunctions;
import l2.gameserver.utils.Log;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import services.pawnshop.PawnShop;

public class TransferAugment
extends Functions
implements ScriptFile {
    private static final String im = "mods/lsTransfer";
    private static final String in = "-h scripts_services.TransferAugment:";

    private static List<ItemInstance> a(Player player, int n, boolean bl, boolean bl2) {
        LinkedList<ItemInstance> linkedList = new LinkedList<ItemInstance>();
        ItemInstance[] itemInstanceArray = player.getInventory().getItems();
        int n2 = 0;
        for (ItemInstance itemInstance : itemInstanceArray) {
            if (itemInstance == null || !itemInstance.isWeapon() || !itemInstance.canBeEnchanted(false) || bl && !itemInstance.canBeDropped(player, false) || bl && itemInstance.isAugmented() || bl2 && !itemInstance.isAugmented()) continue;
            if (n2++ > n) break;
            linkedList.add(itemInstance);
        }
        return linkedList;
    }

    private static void ay(Player player) {
        StringBuilder stringBuilder = new StringBuilder();
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, null);
        npcHtmlMessage.setFile("mods/lsTransfer/list_src_wpn.htm");
        for (ItemInstance itemInstance : TransferAugment.a(player, Config.AUGMENTATION_TRANSFER_MAX_LIST_LENGTH, false, true)) {
            stringBuilder.append(TransferAugment.a(player, "variationExchange.list_src_item_element", String.format("-h scripts_services.TransferAugment:listDstWpns %d", itemInstance.getObjectId()), itemInstance));
        }
        npcHtmlMessage = npcHtmlMessage.replace("%list%", stringBuilder.toString());
        npcHtmlMessage.replace("%item_id%", String.valueOf(Config.AUGMENTATION_TRANSFER_REQUIRED_ITEM_ID));
        npcHtmlMessage.replace("%item_count%", String.valueOf(Config.AUGMENTATION_TRANSFER_REQUIRED_ITEM_COUNT));
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    private static void c(Player player, ItemInstance itemInstance) {
        StringBuilder stringBuilder = new StringBuilder();
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, null);
        npcHtmlMessage.setFile("mods/lsTransfer/list_dst_wpn.htm");
        for (ItemInstance itemInstance2 : TransferAugment.a(player, Config.AUGMENTATION_TRANSFER_MAX_LIST_LENGTH, true, false)) {
            stringBuilder.append(TransferAugment.a(player, "variationExchange.list_dst_item_element", String.format("-h scripts_services.TransferAugment:exchgVari %d %d", itemInstance.getObjectId(), itemInstance2.getObjectId()), itemInstance2));
        }
        npcHtmlMessage = npcHtmlMessage.replace("%list%", stringBuilder.toString());
        npcHtmlMessage.replace("%item_id%", String.valueOf(Config.AUGMENTATION_TRANSFER_REQUIRED_ITEM_ID));
        npcHtmlMessage.replace("%item_count%", String.valueOf(Config.AUGMENTATION_TRANSFER_REQUIRED_ITEM_COUNT));
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    private static String a(Player player, String string, String string2, ItemInstance itemInstance) {
        return TransferAugment.a(player, string, string2, itemInstance, TransferAugment.b(itemInstance.getVariationStat1(), itemInstance.getVariationStat2()), itemInstance.getEnchantLevel());
    }

    private static String a(Player player, String string, String string2, ItemInstance itemInstance, Skill skill, int n) {
        ItemTemplate itemTemplate = itemInstance.getTemplate();
        String string3 = StringHolder.getInstance().getNotNull(player, string);
        string3 = string3.replace("%bypass%", string2 != null ? string2 : "");
        string3 = string3.replace("%item_id%", String.valueOf(itemInstance.getItemId()));
        string3 = string3.replace("%item_name%", itemInstance.getName());
        string3 = string3.replace("%item_add_name%", itemTemplate.getAdditionalName());
        string3 = string3.replace("%item_icon%", itemTemplate.getIcon());
        string3 = string3.replace("%item_enchant%", n > 0 ? String.format("+%d ", n) : "");
        string3 = string3.replace("%option%", PawnShop.formatOptSkillText(skill));
        return string3;
    }

    private static Skill b(int n, int n2) {
        Skill skill = null;
        if (n > 0 || n2 > 0) {
            OptionDataTemplate optionDataTemplate = OptionDataHolder.getInstance().getTemplate(n);
            OptionDataTemplate optionDataTemplate2 = OptionDataHolder.getInstance().getTemplate(n2);
            if (optionDataTemplate2 != null && !optionDataTemplate2.getSkills().isEmpty()) {
                skill = (Skill)optionDataTemplate2.getSkills().get(0);
            }
            if (optionDataTemplate != null && !optionDataTemplate.getSkills().isEmpty()) {
                skill = (Skill)optionDataTemplate.getSkills().get(0);
            }
        }
        return skill;
    }

    public void listSrcWpns() {
        this.listSrcWpns(ArrayUtils.EMPTY_STRING_ARRAY);
    }

    public void listSrcWpns(String[] stringArray) {
        Player player = this.getSelf();
        if (!Config.AUGMENTATION_TRANSFER_ENABLE) {
            return;
        }
        TransferAugment.ay(player);
    }

    public void listDstWpns(String[] stringArray) {
        Player player = this.getSelf();
        if (!Config.AUGMENTATION_TRANSFER_ENABLE) {
            return;
        }
        try {
            if (stringArray.length < 1) {
                TransferAugment.ay(player);
                return;
            }
            String string = stringArray[0];
            if (!StringUtils.isNumeric((CharSequence)string)) {
                TransferAugment.ay(player);
                return;
            }
            int n = Integer.parseInt(string);
            ItemInstance itemInstance = player.getInventory().getItemByObjectId(n);
            if (itemInstance == null || !itemInstance.isWeapon() || !itemInstance.isAugmented()) {
                TransferAugment.ay(player);
                return;
            }
            TransferAugment.c(player, itemInstance);
        }
        catch (Exception exception) {
            TransferAugment.ay(player);
        }
    }

    public void exchgVari(String[] stringArray) {
        Player player = this.getSelf();
        if (!Config.AUGMENTATION_TRANSFER_ENABLE) {
            return;
        }
        try {
            if (stringArray.length < 2) {
                TransferAugment.ay(player);
                return;
            }
            String string = stringArray[0];
            String string2 = stringArray[1];
            if (!StringUtils.isNumeric((CharSequence)string) || !StringUtils.isNumeric((CharSequence)string2)) {
                TransferAugment.ay(player);
                return;
            }
            int n = Integer.parseInt(string);
            int n2 = Integer.parseInt(string2);
            ItemInstance itemInstance = player.getInventory().getItemByObjectId(n);
            ItemInstance itemInstance2 = player.getInventory().getItemByObjectId(n2);
            if (!(itemInstance != null && itemInstance.isWeapon() && itemInstance.isAugmented() && itemInstance.getOwnerId() == player.getObjectId() && itemInstance2 != null && itemInstance2.isWeapon() && !itemInstance2.isAugmented() && itemInstance2.getOwnerId() == player.getObjectId() && itemInstance2.canBeEnchanted(false) && itemInstance2.canBeDropped(player, false))) {
                TransferAugment.ay(player);
                return;
            }
            if (!this.a(player, itemInstance, itemInstance2)) {
                TransferAugment.ay(player);
            } else {
                NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, null);
                npcHtmlMessage.setFile("mods/lsTransfer/transfer_done_wpn.htm");
            }
        }
        catch (Exception exception) {
            TransferAugment.ay(player);
        }
    }

    private boolean a(Player player, ItemInstance itemInstance, ItemInstance itemInstance2) {
        if (!player.getPlayerAccess().UseTrade || player.isTradeBannedByGM()) {
            player.sendPacket((IStaticPacket)SystemMsg.SOME_LINEAGE_II_FEATURES_HAVE_BEEN_LIMITED_FOR_FREE_TRIALS_);
            return false;
        }
        if (!TransferAugment.CheckPlayerConditions((Player)player)) {
            return false;
        }
        if (itemInstance == null || !itemInstance.isWeapon() || !itemInstance.isAugmented() || itemInstance.getOwnerId() != player.getObjectId() || itemInstance.getLocation() != ItemInstance.ItemLocation.INVENTORY && itemInstance.getLocation() != ItemInstance.ItemLocation.PAPERDOLL || ArrayUtils.contains((int[])Config.AUGMENTATION_TRANSFER_PROHIBITED_ITEM_IDS, (int)itemInstance.getItemId())) {
            return false;
        }
        if (itemInstance2 == null || !itemInstance2.isWeapon() || itemInstance2.isAugmented() || itemInstance2.getOwnerId() != player.getObjectId() || !itemInstance2.canBeEnchanted(false) || !itemInstance2.canBeDropped(player, false) || itemInstance2.getLocation() != ItemInstance.ItemLocation.INVENTORY && itemInstance2.getLocation() != ItemInstance.ItemLocation.PAPERDOLL || ArrayUtils.contains((int[])Config.AUGMENTATION_TRANSFER_PROHIBITED_ITEM_IDS, (int)itemInstance2.getItemId())) {
            return false;
        }
        if (Config.AUGMENTATION_TRANSFER_REQUIRED_ITEM_ID <= 0 || (long)Config.AUGMENTATION_TRANSFER_REQUIRED_ITEM_COUNT <= 0L || player.getInventory().getCountOf(Config.AUGMENTATION_TRANSFER_REQUIRED_ITEM_ID) < (long)Config.AUGMENTATION_TRANSFER_REQUIRED_ITEM_COUNT) {
            player.sendPacket(new IStaticPacket[]{SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_REQUIRED_ITEMS, ((SystemMessage)new SystemMessage(SystemMsg.REQUIRES_S2_S1).addNumber(Config.AUGMENTATION_TRANSFER_REQUIRED_ITEM_COUNT)).addItemName(Config.AUGMENTATION_TRANSFER_REQUIRED_ITEM_ID)});
            return false;
        }
        int n = itemInstance.getVariationStat1();
        int n2 = itemInstance.getVariationStat2();
        boolean bl = itemInstance.isEquipped();
        if (bl) {
            player.getInventory().unEquipItem(itemInstance);
        }
        itemInstance.setVariationStat1(0);
        itemInstance.setVariationStat2(0);
        if (bl) {
            player.getInventory().equipItem(itemInstance);
        }
        player.sendPacket((IStaticPacket)new InventoryUpdate().addModifiedItem(itemInstance));
        for (ShortCut shortCut : player.getAllShortCuts()) {
            if (shortCut.getId() != itemInstance.getObjectId() || shortCut.getType() != 1) continue;
            player.sendPacket((IStaticPacket)new ShortCutRegister(player, shortCut));
        }
        player.sendChanges();
        itemInstance.save();
        Log.LogItem((Player)player, (Log.ItemLog)Log.ItemLog.EnchantReset, (ItemInstance)itemInstance);
        if (ItemFunctions.removeItem((Playable)player, (int)Config.AUGMENTATION_TRANSFER_REQUIRED_ITEM_ID, (long)Config.AUGMENTATION_TRANSFER_REQUIRED_ITEM_COUNT, (boolean)true) < (long)Config.AUGMENTATION_TRANSFER_REQUIRED_ITEM_COUNT) {
            player.sendPacket(new IStaticPacket[]{SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_REQUIRED_ITEMS, ((SystemMessage)new SystemMessage(SystemMsg.REQUIRES_S2_S1).addNumber(Config.AUGMENTATION_TRANSFER_REQUIRED_ITEM_COUNT)).addItemName(Config.AUGMENTATION_TRANSFER_REQUIRED_ITEM_ID)});
            return false;
        }
        boolean bl2 = itemInstance2.isEquipped();
        if (bl2) {
            player.getInventory().unEquipItem(itemInstance2);
        }
        itemInstance2.setVariationStat1(n);
        itemInstance2.setVariationStat2(n2);
        if (bl2) {
            player.getInventory().equipItem(itemInstance2);
        }
        player.sendPacket((IStaticPacket)new InventoryUpdate().addModifiedItem(itemInstance2));
        for (ShortCut shortCut : player.getAllShortCuts()) {
            if (shortCut.getId() != itemInstance2.getObjectId() || shortCut.getType() != 1) continue;
            player.sendPacket((IStaticPacket)new ShortCutRegister(player, shortCut));
        }
        player.sendChanges();
        itemInstance2.save();
        Log.service((String)"TransferAugment", (Player)player, (String)("Transfer augment from " + itemInstance.getObjectId() + " [" + itemInstance.getName() + "]  to " + itemInstance2.getObjectId() + " [" + itemInstance2.getName() + "]"));
        return true;
    }

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }
}
