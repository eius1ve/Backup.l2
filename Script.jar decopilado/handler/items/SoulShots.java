/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
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
 *  l2.gameserver.stats.Stats
 *  l2.gameserver.tables.SkillTable
 *  l2.gameserver.templates.item.EtcItemTemplate$EtcItemType
 *  l2.gameserver.templates.item.ItemTemplate
 *  l2.gameserver.templates.item.WeaponTemplate
 *  l2.gameserver.templates.item.WeaponTemplate$WeaponType
 *  l2.gameserver.templates.item.support.Grade
 *  org.apache.commons.lang3.ArrayUtils
 *  org.napile.primitive.collections.IntCollection
 *  org.napile.primitive.lists.impl.CArrayIntList
 *  org.napile.primitive.sets.impl.CArrayIntSet
 */
package handler.items;

import handler.items.ShotsItemHandler;
import l2.commons.util.Rnd;
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
import l2.gameserver.stats.Stats;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.templates.item.EtcItemTemplate;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.templates.item.WeaponTemplate;
import l2.gameserver.templates.item.support.Grade;
import org.apache.commons.lang3.ArrayUtils;
import org.napile.primitive.collections.IntCollection;
import org.napile.primitive.lists.impl.CArrayIntList;
import org.napile.primitive.sets.impl.CArrayIntSet;

public class SoulShots
extends ShotsItemHandler {
    private static final int cF = 59150;
    private static final Skill[] b = new Skill[]{null, null, null, null, null, null, null, SkillTable.getInstance().getInfo(17816, 1), SkillTable.getInstance().getInfo(17815, 1), SkillTable.getInstance().getInfo(17817, 1)};

    public boolean useItem(Playable playable, ItemInstance itemInstance, boolean bl) {
        int n;
        if (playable == null || !playable.isPlayer()) {
            return false;
        }
        Player player = (Player)playable;
        WeaponTemplate weaponTemplate = player.getActiveWeaponItem();
        ItemInstance itemInstance2 = player.getActiveWeaponInstance();
        int n2 = itemInstance.getItemId();
        Grade grade = itemInstance.getCrystalType();
        boolean bl2 = false;
        if (player.getAutoSoulShot().contains(n2)) {
            bl2 = true;
        }
        if (itemInstance2 == null) {
            if (!bl2) {
                player.sendPacket((IStaticPacket)SystemMsg.CANNOT_USE_SOULSHOTS);
            }
            return false;
        }
        if (itemInstance2.getChargedSoulshot() != 0) {
            return false;
        }
        Grade grade2 = weaponTemplate.getCrystalType();
        int n3 = weaponTemplate.getSoulShotCount();
        if (n3 == 0) {
            if (bl2) {
                player.removeAutoSoulShot(Integer.valueOf(n2));
                player.sendPacket(new IStaticPacket[]{new ExAutoSoulShot(n2, false, 0), new SystemMessage(SystemMsg.THE_AUTOMATIC_USE_OF_S1_HAS_BEEN_DEACTIVATED).addItemName(n2)});
                return false;
            }
            player.sendPacket((IStaticPacket)SystemMsg.CANNOT_USE_SOULSHOTS);
            return false;
        }
        if (grade2 != grade && !ArrayUtils.contains((int[])Config.ALT_UNIVERSAL_SHOTS, (int)n2)) {
            if (bl2) {
                return false;
            }
            player.sendPacket((IStaticPacket)SystemMsg.THE_SOULSHOT_YOU_ARE_ATTEMPTING_TO_USE_DOES_NOT_MATCH_THE_GRADE_OF_YOUR_EQUIPPED_WEAPON);
            return false;
        }
        if (weaponTemplate.getItemType() == WeaponTemplate.WeaponType.BOW && (n = (int)player.calcStat(Stats.SS_USE_BOW, (double)n3, null, null)) < n3 && Rnd.chance((double)player.calcStat(Stats.SS_USE_BOW_CHANCE, (double)n3, null, null))) {
            n3 = n;
        }
        if (Config.ALT_CONSUME_SOULSHOTS && (!player.hasBonus() || Config.ALT_PA_CONSUME_SOULSHOTS) && !player.getInventory().destroyItem(itemInstance, (long)n3)) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_SOULSHOTS_FOR_THAT);
            return false;
        }
        itemInstance2.setChargedSoulshot(1);
        player.sendPacket((IStaticPacket)SystemMsg.YOUR_SOULSHOTS_ARE_ENABLED);
        for (Skill skill : SoulShots.a(itemInstance, player)) {
            this.broadcastShotSkill((Creature)player, skill.getDisplayId(), skill.getDisplayLevel());
        }
        return true;
    }

    private static Skill[] a(ItemInstance itemInstance, Player player) {
        int n = player.getSkillLevel(Integer.valueOf(59150));
        if (n > 0 && b[n - 1] != null) {
            return new Skill[]{b[n - 1]};
        }
        return itemInstance.getTemplate().getAttachedSkills();
    }

    public final int[] getItemIds() {
        CArrayIntSet cArrayIntSet = new CArrayIntSet();
        for (ItemTemplate itemTemplate : ItemHolder.getInstance().getAllTemplates()) {
            if (itemTemplate == null || itemTemplate.getItemType() != EtcItemTemplate.EtcItemType.SHOT) continue;
            cArrayIntSet.add(itemTemplate.getItemId());
        }
        cArrayIntSet.addAll((IntCollection)new CArrayIntList(Config.ALT_UNIVERSAL_SHOTS));
        return cArrayIntSet.toArray();
    }
}
