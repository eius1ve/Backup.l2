/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.commons.util.Rnd;
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.EnchantItemHolder;
import l2.gameserver.data.xml.holder.OneDayRewardHolder;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.oneDayReward.requirement.EnchantItemRequirement;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.items.PcInventory;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.EnchantResult;
import l2.gameserver.network.l2.s2c.InventoryUpdate;
import l2.gameserver.network.l2.s2c.MagicSkillUse;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.templates.item.support.EnchantCatalyzer;
import l2.gameserver.templates.item.support.EnchantScroll;
import l2.gameserver.templates.item.support.EnchantScrollOnFailAction;
import l2.gameserver.utils.ItemFunctions;
import l2.gameserver.utils.Log;

public class RequestEnchantItem
extends L2GameClientPacket {
    private int fW;
    private int qF;

    @Override
    protected void readImpl() {
        this.fW = this.readD();
        this.qF = this.readD();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void runImpl() {
        GameClient gameClient = (GameClient)this.getClient();
        if (gameClient == null) {
            return;
        }
        Player player = gameClient.getActiveChar();
        if (player == null) {
            return;
        }
        if (player.isActionsDisabled()) {
            player.setEnchantScroll(null);
            player.sendActionFailed();
            return;
        }
        if (player.isInTrade()) {
            player.setEnchantScroll(null);
            player.sendActionFailed();
            return;
        }
        if (player.isInStoreMode()) {
            player.setEnchantScroll(null);
            player.sendPacket((IStaticPacket)EnchantResult.CANCEL);
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_ENCHANT_WHILE_OPERATING_A_PRIVATE_STORE_OR_PRIVATE_WORKSHOP);
            player.sendActionFailed();
            return;
        }
        PcInventory pcInventory = player.getInventory();
        pcInventory.writeLock();
        try {
            ItemInstance itemInstance = pcInventory.getItemByObjectId(this.fW);
            ItemInstance itemInstance2 = this.qF > 0 ? pcInventory.getItemByObjectId(this.qF) : null;
            ItemInstance itemInstance3 = player.getEnchantScroll();
            if (itemInstance == null || itemInstance3 == null) {
                player.sendActionFailed();
                return;
            }
            EnchantScroll enchantScroll = EnchantItemHolder.getInstance().getEnchantScroll(itemInstance3.getItemId());
            if (enchantScroll == null) {
                player.sendActionFailed();
                return;
            }
            if (!itemInstance.canBeEnchanted(false)) {
                player.sendPacket((IStaticPacket)EnchantResult.CANCEL);
                player.sendPacket((IStaticPacket)SystemMsg.INAPPROPRIATE_ENCHANT_CONDITIONS);
                player.sendActionFailed();
                return;
            }
            if (!enchantScroll.isUsableWith(itemInstance, null)) {
                player.sendPacket((IStaticPacket)EnchantResult.CANCEL);
                player.sendPacket((IStaticPacket)SystemMsg.DOES_NOT_FIT_STRENGTHENING_CONDITIONS_OF_THE_SCROLL);
                player.sendActionFailed();
                return;
            }
            EnchantCatalyzer enchantCatalyzer = itemInstance2 != null ? EnchantItemHolder.getInstance().getEnchantCatalyzer(itemInstance2.getItemId()) : null;
            double d = 1.0 + enchantScroll.getChanceMod();
            int n = itemInstance.getEnchantLevel() + Rnd.get(enchantScroll.getIncrement(), enchantScroll.getIncrementMax());
            d *= player.getEnchantBonusMul();
            if (enchantCatalyzer != null) {
                d += enchantCatalyzer.getChanceMod();
            }
            if (!pcInventory.destroyItem(itemInstance3, 1L) || itemInstance2 != null && enchantCatalyzer != null && !pcInventory.destroyItem(itemInstance2, 1L)) {
                player.sendPacket((IStaticPacket)EnchantResult.CANCEL);
                player.sendPacket((IStaticPacket)SystemMsg.INAPPROPRIATE_ENCHANT_CONDITIONS);
                player.sendActionFailed();
                return;
            }
            if (enchantCatalyzer != null && !enchantCatalyzer.isUsableWith(itemInstance)) {
                enchantCatalyzer = null;
            }
            if (!enchantScroll.isUsableWith(itemInstance, enchantCatalyzer)) {
                player.sendPacket((IStaticPacket)EnchantResult.CANCEL);
                player.sendPacket((IStaticPacket)SystemMsg.DOES_NOT_FIT_STRENGTHENING_CONDITIONS_OF_THE_SCROLL);
                player.sendActionFailed();
                return;
            }
            double d2 = enchantScroll.getEnchantChance(itemInstance);
            if (enchantScroll.isInfallible() || Rnd.chance(d2 * d)) {
                if (enchantScroll.getIncrement() > 1 && n > enchantScroll.getMaxLvl()) {
                    this.a(player, itemInstance, enchantScroll.getMaxLvl());
                    return;
                }
                this.a(player, itemInstance, n);
                return;
            }
            EnchantScrollOnFailAction enchantScrollOnFailAction = enchantScroll.getOnFailAction();
            if (enchantCatalyzer != null && enchantScrollOnFailAction != EnchantScrollOnFailAction.NONE) {
                enchantScrollOnFailAction = enchantCatalyzer.getResultType();
            }
            switch (enchantScrollOnFailAction) {
                case CRYSTALIZE: {
                    this.a(player, itemInstance);
                    return;
                }
                case RESET: {
                    this.b(player, itemInstance, enchantScroll.getFailResultLevel());
                    return;
                }
                case NONE: {
                    this.a(player, itemInstance, enchantScroll.isCloseEnchantWindowOnFail());
                    return;
                }
            }
            return;
        }
        finally {
            pcInventory.writeUnlock();
            player.updateStats();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void a(Player player, ItemInstance itemInstance, int n) {
        PcInventory pcInventory = player.getInventory();
        ItemInstance itemInstance2 = player.getEnchantScroll();
        if (n >= 65535) {
            player.sendPacket(EnchantResult.CANCEL, SystemMsg.DOES_NOT_FIT_STRENGTHENING_CONDITIONS_OF_THE_SCROLL);
            player.sendActionFailed();
            return;
        }
        boolean bl = itemInstance.isEquipped();
        int n2 = itemInstance.getEquipSlot();
        if (bl) {
            itemInstance.setEquipped(false);
            pcInventory.getListeners().onUnequip(n2, itemInstance);
        }
        try {
            itemInstance.setEnchantLevel(n);
            Log.LogItem(player, Log.ItemLog.EnchantSuccess, itemInstance, 1L, itemInstance.getReferencePrice(), itemInstance2.getItemId());
        }
        finally {
            if (bl) {
                pcInventory.getListeners().onEquip(n2, itemInstance);
                itemInstance.setEquipped(true);
            }
            itemInstance.save();
        }
        player.sendPacket((IStaticPacket)new InventoryUpdate().addModifiedItem(itemInstance));
        player.sendPacket((IStaticPacket)new EnchantResult(itemInstance.getEnchantLevel()));
        player.getListeners().onItemEnchantSuccessListener(itemInstance.getItemId(), itemInstance.getEnchantLevel());
        if (Config.SHOW_ENCHANT_EFFECT_RESULT) {
            RequestEnchantItem.b(player, itemInstance);
        }
        OneDayRewardHolder.getInstance().fireRequirements(player, null, EnchantItemRequirement.class);
    }

    private void a(Player player, ItemInstance itemInstance) {
        PcInventory pcInventory = player.getInventory();
        boolean bl = itemInstance.isEquipped();
        int n = itemInstance.getItemId();
        int n2 = itemInstance.getEnchantLevel();
        int n3 = itemInstance.getCrystalItemId();
        int n4 = itemInstance.getTemplate().getCrystalCount();
        ItemInstance itemInstance2 = player.getEnchantScroll();
        if (bl) {
            player.sendDisarmMessage(itemInstance);
            pcInventory.unEquipItem(itemInstance);
        }
        Log.LogItem(player, Log.ItemLog.EnchantCrystallize, itemInstance, 1L, itemInstance.getReferencePrice(), itemInstance2.getItemId());
        if (!pcInventory.destroyItem(itemInstance, 1L)) {
            player.sendActionFailed();
            return;
        }
        if (n3 > 0 && n4 > 0) {
            int n5 = (int)((double)n4 * 0.87);
            if (n2 > 3 && Config.CRYSTALLIZE_BONUS_AT_ENCHANT) {
                n5 = (int)((double)n5 + (double)n4 * 0.25 * (double)(n2 - 3));
            }
            if (n5 < 1) {
                n5 = 1;
            }
            player.sendPacket(new IStaticPacket[]{new EnchantResult(1, n3, n5), ((SystemMessage)new SystemMessage(SystemMsg.THE_ENCHANTMENT_HAS_FAILED__YOUR_S1_S2_HAS_BEEN_CRYSTALLIZED).addNumber(n2)).addItemName(n)});
            ItemFunctions.addItem((Playable)player, n3, (long)n5, true);
        } else {
            player.sendPacket(new IStaticPacket[]{EnchantResult.FAILED_NO_CRYSTALS, new SystemMessage(SystemMsg.THE_ENCHANTMENT_HAS_FAILED_YOUR_S1_HAS_BEEN_CRYSTALLIZED).addItemName(itemInstance.getItemId())});
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void b(Player player, ItemInstance itemInstance, int n) {
        PcInventory pcInventory = player.getInventory();
        boolean bl = itemInstance.isEquipped();
        int n2 = itemInstance.getEquipSlot();
        int n3 = Math.min(itemInstance.getEnchantLevel(), n);
        ItemInstance itemInstance2 = player.getEnchantScroll();
        if (bl) {
            itemInstance.setEquipped(false);
            pcInventory.getListeners().onUnequip(n2, itemInstance);
        }
        try {
            itemInstance.setEnchantLevel(n3);
            Log.LogItem(player, Log.ItemLog.EnchantReset, itemInstance, 1L, itemInstance.getReferencePrice(), itemInstance2.getItemId());
        }
        finally {
            if (bl) {
                pcInventory.getListeners().onEquip(n2, itemInstance);
                itemInstance.setEquipped(true);
            }
            itemInstance.save();
        }
        player.sendPacket(new InventoryUpdate().addModifiedItem(itemInstance), EnchantResult.BLESSED_FAILED, SystemMsg.THE_BLESSED_ENCHANT_FAILED);
    }

    private void a(Player player, ItemInstance itemInstance, boolean bl) {
        ItemInstance itemInstance2 = player.getEnchantScroll();
        Log.LogItem(player, Log.ItemLog.EnchantFail, itemInstance, 1L, itemInstance.getReferencePrice(), itemInstance2.getItemId());
        if (!bl) {
            player.sendPacket(EnchantResult.ANCIENT_FAILED, SystemMsg.ENCHANT_FAILED_THE_ENCHANT_SKILL_FOR_THE_CORRESPONDING_ITEM_WILL_BE_EXACTLY_RETAINED);
        } else {
            player.sendPacket(EnchantResult.CANCEL, SystemMsg.ENCHANT_FAILED_THE_ENCHANT_SKILL_FOR_THE_CORRESPONDING_ITEM_WILL_BE_EXACTLY_RETAINED);
        }
    }

    private static final void b(Player player, ItemInstance itemInstance) {
        if (itemInstance.getTemplate().getType2() == 0) {
            if (Config.SHOW_ENCHANT_EFFECT_RESULT_EVERY_NEXT_SUCCESS ? itemInstance.getEnchantLevel() == Config.WEAPON_FIRST_ENCHANT_EFFECT_LEVEL || itemInstance.getEnchantLevel() >= Config.WEAPON_SECOND_ENCHANT_EFFECT_LEVEL : itemInstance.getEnchantLevel() == Config.WEAPON_FIRST_ENCHANT_EFFECT_LEVEL || itemInstance.getEnchantLevel() == Config.WEAPON_SECOND_ENCHANT_EFFECT_LEVEL) {
                player.broadcastPacket(new MagicSkillUse(player, player, 2025, 1, 500, 1500L));
                player.broadCastCustomMessage("_C1_HAS_SUCCESSFULLY_ENCHANTED_A_S2_S3", player, player, itemInstance, itemInstance.getEnchantLevel());
            }
        } else if (Config.SHOW_ENCHANT_EFFECT_RESULT_EVERY_NEXT_SUCCESS ? itemInstance.getEnchantLevel() >= Config.ARMOR_ENCHANT_EFFECT_LEVEL : itemInstance.getEnchantLevel() == Config.ARMOR_ENCHANT_EFFECT_LEVEL) {
            player.broadcastPacket(new MagicSkillUse(player, player, 2025, 1, 500, 1500L));
            player.broadCastCustomMessage("_C1_HAS_SUCCESSFULLY_ENCHANTED_A_S2_S3", player, player, itemInstance, itemInstance.getEnchantLevel());
        }
    }
}
