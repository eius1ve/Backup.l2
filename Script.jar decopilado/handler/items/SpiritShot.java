/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.Config
 *  l2.gameserver.data.xml.holder.ItemHolder
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.items.ItemInstance
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.ExAutoSoulShot
 *  l2.gameserver.network.l2.s2c.SystemMessage
 *  l2.gameserver.tables.SkillTable
 *  l2.gameserver.templates.item.EtcItemTemplate$EtcItemType
 *  l2.gameserver.templates.item.ItemTemplate
 *  l2.gameserver.templates.item.WeaponTemplate
 *  l2.gameserver.templates.item.support.Grade
 *  org.apache.commons.lang3.ArrayUtils
 *  org.napile.primitive.collections.IntCollection
 *  org.napile.primitive.lists.impl.CArrayIntList
 *  org.napile.primitive.sets.impl.CArrayIntSet
 */
package handler.items;

import handler.items.ShotsItemHandler;
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExAutoSoulShot;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.templates.item.EtcItemTemplate;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.templates.item.WeaponTemplate;
import l2.gameserver.templates.item.support.Grade;
import org.apache.commons.lang3.ArrayUtils;
import org.napile.primitive.collections.IntCollection;
import org.napile.primitive.lists.impl.CArrayIntList;
import org.napile.primitive.sets.impl.CArrayIntSet;

public class SpiritShot
extends ShotsItemHandler {
    private static final int cG = 59151;
    private static final Skill[] c = new Skill[]{null, null, null, null, null, null, null, SkillTable.getInstance().getInfo(17819, 2), SkillTable.getInstance().getInfo(17820, 2), SkillTable.getInstance().getInfo(17821, 2)};

    public boolean useItem(Playable playable, ItemInstance itemInstance, boolean bl) {
        if (playable == null || !playable.isPlayer()) {
            return false;
        }
        Player player = (Player)playable;
        ItemInstance itemInstance2 = player.getActiveWeaponInstance();
        WeaponTemplate weaponTemplate = player.getActiveWeaponItem();
        int n = itemInstance.getItemId();
        Grade grade = itemInstance.getCrystalType();
        boolean bl2 = false;
        if (player.getAutoSoulShot().contains(n)) {
            bl2 = true;
        }
        if (itemInstance2 == null) {
            if (!bl2) {
                player.sendPacket((IStaticPacket)SystemMsg.YOU_MAY_NOT_USE_SPIRITSHOTS);
            }
            return false;
        }
        if (itemInstance2.getChargedSpiritshot() != 0) {
            return false;
        }
        Grade grade2 = weaponTemplate.getCrystalType();
        int n2 = weaponTemplate.getSpiritShotCount();
        if (n2 == 0) {
            if (bl2) {
                player.removeAutoSoulShot(Integer.valueOf(n));
                player.sendPacket(new IStaticPacket[]{new ExAutoSoulShot(n, false, 0), new SystemMessage(SystemMsg.THE_AUTOMATIC_USE_OF_S1_HAS_BEEN_DEACTIVATED).addItemName(n)});
                return false;
            }
            player.sendPacket((IStaticPacket)SystemMsg.YOU_MAY_NOT_USE_SPIRITSHOTS);
            return false;
        }
        if (grade2 != grade && !ArrayUtils.contains((int[])Config.ALT_UNIVERSAL_SPIRIT_SHOTS, (int)n)) {
            if (bl2) {
                return false;
            }
            player.sendPacket((IStaticPacket)SystemMsg.YOUR_SPIRITSHOT_DOES_NOT_MATCH_THE_WEAPONS_GRADE);
            return false;
        }
        if (Config.ALT_CONSUME_SPIRITSHOTS && (!player.hasBonus() || Config.ALT_PA_CONSUME_SPIRITSHOTS) && !player.getInventory().destroyItem(itemInstance, (long)n2)) {
            if (bl2) {
                player.removeAutoSoulShot(Integer.valueOf(n));
                player.sendPacket(new IStaticPacket[]{new ExAutoSoulShot(n, false, 0), new SystemMessage(SystemMsg.THE_AUTOMATIC_USE_OF_S1_HAS_BEEN_DEACTIVATED).addItemName(n)});
                return false;
            }
            player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_SPIRITSHOT_FOR_THAT);
            return false;
        }
        itemInstance2.setChargedSpiritshot(1);
        player.sendPacket((IStaticPacket)SystemMsg.YOUR_SPIRITSHOT_HAS_BEEN_ENABLED);
        for (Skill skill : SpiritShot.a(itemInstance, player)) {
            this.broadcastShotSkill((Creature)player, skill.getDisplayId(), skill.getDisplayLevel());
        }
        return true;
    }

    private static Skill[] a(ItemInstance itemInstance, Player player) {
        int n = player.getSkillLevel(Integer.valueOf(59151));
        if (n > 0 && c[n - 1] != null) {
            return new Skill[]{c[n - 1]};
        }
        return itemInstance.getTemplate().getAttachedSkills();
    }

    public final int[] getItemIds() {
        CArrayIntSet cArrayIntSet = new CArrayIntSet();
        for (ItemTemplate itemTemplate : ItemHolder.getInstance().getAllTemplates()) {
            if (itemTemplate == null || itemTemplate.getItemType() != EtcItemTemplate.EtcItemType.SPIRITSHOT) continue;
            cArrayIntSet.add(itemTemplate.getItemId());
        }
        cArrayIntSet.addAll((IntCollection)new CArrayIntList(Config.ALT_UNIVERSAL_SPIRIT_SHOTS));
        return cArrayIntSet.toArray();
    }
}
