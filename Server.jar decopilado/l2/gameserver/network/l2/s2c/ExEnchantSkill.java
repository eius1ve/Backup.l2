/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.network.l2.s2c;

import java.util.Map;
import l2.commons.util.Rnd;
import l2.gameserver.data.xml.holder.EnchantSkillHolder;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.actor.instances.player.ShortCut;
import l2.gameserver.model.base.Experience;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExEnchantSkillList;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.ShortCutRegister;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.scripts.Functions;
import l2.gameserver.skills.TimeStamp;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.templates.SkillEnchant;
import l2.gameserver.utils.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExEnchantSkill {
    private static final Logger db = LoggerFactory.getLogger(ExEnchantSkill.class);
    public static String EX_ENCHANT_SKILL_BYPASS = "ExEnchantSkill";

    public static L2GameServerPacket packetFor(Player player, NpcInstance npcInstance, String ... stringArray) {
        if (player.getClassId().getLevel() < 4 || player.getLevel() < 76 || stringArray.length < 2) {
            return new SystemMessage(SystemMsg.THERE_IS_NO_SKILL_THAT_ENABLES_ENCHANT);
        }
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        try {
            n = Integer.parseInt(stringArray[0]);
            n2 = Integer.parseInt(stringArray[1]);
            if (stringArray.length > 2) {
                n3 = Integer.parseInt(stringArray[2]);
            }
        }
        catch (Exception exception) {
            return new SystemMessage(SystemMsg.THERE_IS_NO_SKILL_THAT_ENABLES_ENCHANT);
        }
        Skill skill = player.getKnownSkill(n);
        if (skill == null) {
            return new SystemMessage(SystemMsg.THERE_IS_NO_SKILL_THAT_ENABLES_ENCHANT);
        }
        int n4 = skill.getLevel();
        int n5 = skill.getBaseLevel();
        Map<Integer, SkillEnchant> map = EnchantSkillHolder.getInstance().getLevelsOf(n);
        if (map == null || map.isEmpty()) {
            return new SystemMessage(SystemMsg.THERE_IS_NO_SKILL_THAT_ENABLES_ENCHANT);
        }
        SkillEnchant skillEnchant = map.get(n4);
        SkillEnchant skillEnchant2 = map.get(n2);
        if (skillEnchant2 == null) {
            return new SystemMessage(SystemMsg.THERE_IS_NO_SKILL_THAT_ENABLES_ENCHANT);
        }
        if (skillEnchant != null) {
            if (skillEnchant.getRouteId() != skillEnchant2.getRouteId() || skillEnchant2.getEnchantLevel() != skillEnchant.getEnchantLevel() + 1) {
                return new SystemMessage(SystemMsg.THERE_IS_NO_SKILL_THAT_ENABLES_ENCHANT);
            }
        } else if (skillEnchant2.getEnchantLevel() != 1 || n4 != n5) {
            db.warn("Player \"" + player.toString() + "\" trying to use enchant  exploit" + skill.toString() + " to " + n2 + "(enchant level " + skillEnchant2.getEnchantLevel() + ")");
            return new SystemMessage(SystemMsg.THERE_IS_NO_SKILL_THAT_ENABLES_ENCHANT);
        }
        int[] nArray = skillEnchant2.getChances();
        int n6 = Experience.LEVEL.length - nArray.length - 1;
        if (player.getLevel() < n6) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_DO_NOT_HAVE_ANY_FURTHER_SKILLS_TO_LEARN__COME_BACK_WHEN_YOU_HAVE_REACHED_LEVEL_S1).addNumber(n6));
            return ExEnchantSkillList.packetFor(player, player.getLastNpc(), n3);
        }
        if (player.getSp() < (long)skillEnchant2.getSp()) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_SP_TO_ENCHANT_THAT_SKILL));
            return ExEnchantSkillList.packetFor(player, player.getLastNpc(), n3);
        }
        if (player.getExp() < skillEnchant2.getExp()) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.EXP_REQUIRED_FOR_SKILL_ENCHANT_IS_INSUFFICIENT));
            return ExEnchantSkillList.packetFor(player, player.getLastNpc(), n3);
        }
        if (skillEnchant2.getItemId() > 0 && skillEnchant2.getItemCount() > 0L && Functions.removeItem(player, skillEnchant2.getItemId(), skillEnchant2.getItemCount()) < skillEnchant2.getItemCount()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ALL_OF_THE_ITEMS_NEEDED_TO_ENCHANT_THAT_SKILL);
            return ExEnchantSkillList.packetFor(player, player.getLastNpc(), n3);
        }
        int n7 = Math.max(0, Math.min(player.getLevel() - n6, nArray.length - 1));
        int n8 = (int)Math.min(100L, Math.round((double)nArray[n7] * player.getEnchantSkillBonusMul()));
        player.addExpAndSp(-1L * skillEnchant2.getExp(), -1 * skillEnchant2.getSp());
        TimeStamp timeStamp = player.getSkillReuse(skill);
        Skill skill2 = null;
        if (Rnd.chance(n8)) {
            skill2 = SkillTable.getInstance().getInfo(skillEnchant2.getSkillId(), skillEnchant2.getSkillLevel());
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.SKILL_ENCHANT_WAS_SUCCESSFUL_S1_HAS_BEEN_ENCHANTED).addSkillName(n, n2));
            Log.add(player.getName() + "|Successfully enchanted|" + n + "|to+" + n2 + "|" + n8, "enchant_skills");
        } else {
            if (skillEnchant2.isResetOnFailure()) {
                skill2 = SkillTable.getInstance().getInfo(skill.getId(), skill.getBaseLevel());
                player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.SKILL_ENCHANT_FAILED));
            } else {
                skill2 = SkillTable.getInstance().getInfo(skill.getId(), skill.getLevel());
                player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.SKILL_ENCHANT_FAILED_S1).addSkillName(n, n2));
            }
            Log.add(player.getName() + "|Failed to enchant|" + n + "|to+" + n2 + "|" + n8, "enchant_skills");
        }
        if (timeStamp != null && timeStamp.hasNotPassed()) {
            player.disableSkill(skill2, timeStamp.getReuseCurrent());
        }
        player.addSkill(skill2, true);
        player.sendSkillList();
        ExEnchantSkill.updateSkillShortcuts(player, n, n2);
        return ExEnchantSkillList.packetFor(player, player.getLastNpc(), n3);
    }

    protected static void updateSkillShortcuts(Player player, int n, int n2) {
        for (ShortCut shortCut : player.getAllShortCuts()) {
            if (shortCut.getId() != n || shortCut.getType() != 2) continue;
            ShortCut shortCut2 = new ShortCut(shortCut.getSlot(), shortCut.getPage(), shortCut.getType(), shortCut.getId(), n2, 1);
            player.sendPacket((IStaticPacket)new ShortCutRegister(player, shortCut2));
            player.registerShortCut(shortCut2);
        }
    }
}
