/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.network.l2.c2s;

import java.util.Map;
import l2.commons.util.Rnd;
import l2.gameserver.data.xml.holder.EnchantSkillHolder;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.actor.instances.player.ShortCut;
import l2.gameserver.model.base.Experience;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.CustomMessage;
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

public class RequestExEnchantSkill
extends L2GameClientPacket {
    private static final Logger cS = LoggerFactory.getLogger(RequestExEnchantSkill.class);
    private int _skillId;
    private int qN;

    @Override
    protected void readImpl() {
        this._skillId = this.readD();
        this.qN = this.readD();
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        if (player.getClassId().getLevel() < 4 || player.getLevel() < 76) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.THERE_IS_NO_SKILL_THAT_ENABLES_ENCHANT));
            return;
        }
        Skill skill = player.getKnownSkill(this._skillId);
        if (skill == null) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.THERE_IS_NO_SKILL_THAT_ENABLES_ENCHANT));
            return;
        }
        int n = skill.getLevel();
        int n2 = skill.getBaseLevel();
        Map<Integer, SkillEnchant> map = EnchantSkillHolder.getInstance().getLevelsOf(this._skillId);
        if (map == null || map.isEmpty()) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.THERE_IS_NO_SKILL_THAT_ENABLES_ENCHANT));
            return;
        }
        SkillEnchant skillEnchant = map.get(n);
        SkillEnchant skillEnchant2 = map.get(this.qN);
        if (skillEnchant2 == null) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.THERE_IS_NO_SKILL_THAT_ENABLES_ENCHANT));
            return;
        }
        if (skillEnchant != null) {
            if (skillEnchant.getRouteId() != skillEnchant2.getRouteId() || skillEnchant2.getEnchantLevel() != skillEnchant.getEnchantLevel() + 1) {
                player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.THERE_IS_NO_SKILL_THAT_ENABLES_ENCHANT));
                return;
            }
        } else if (skillEnchant2.getEnchantLevel() != 1 || n != n2) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.THERE_IS_NO_SKILL_THAT_ENABLES_ENCHANT));
            cS.warn("Player \"" + player.toString() + "\" trying to use enchant  exploit" + skill.toString() + " to " + this.qN + "(enchant level " + skillEnchant2.getEnchantLevel() + ")");
            return;
        }
        int[] nArray = skillEnchant2.getChances();
        int n3 = Experience.LEVEL.length - nArray.length - 1;
        if (player.getLevel() < n3) {
            this.sendPacket((L2GameServerPacket)new SystemMessage(SystemMsg.YOU_DO_NOT_HAVE_ANY_FURTHER_SKILLS_TO_LEARN__COME_BACK_WHEN_YOU_HAVE_REACHED_LEVEL_S1).addNumber(n3));
            return;
        }
        if (player.getSp() < (long)skillEnchant2.getSp()) {
            this.sendPacket((L2GameServerPacket)new SystemMessage(SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_SP_TO_ENCHANT_THAT_SKILL));
            return;
        }
        if (player.getExp() < skillEnchant2.getExp()) {
            this.sendPacket((L2GameServerPacket)new SystemMessage(SystemMsg.EXP_REQUIRED_FOR_SKILL_ENCHANT_IS_INSUFFICIENT));
            return;
        }
        if (skillEnchant2.getItemId() > 0 && skillEnchant2.getItemCount() > 0L && Functions.removeItem(player, skillEnchant2.getItemId(), skillEnchant2.getItemCount()) < skillEnchant2.getItemCount()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ALL_OF_THE_ITEMS_NEEDED_TO_ENCHANT_THAT_SKILL);
            return;
        }
        int n4 = Math.max(0, Math.min(player.getLevel() - n3, nArray.length - 1));
        int n5 = (int)Math.min(100L, Math.round((double)nArray[n4] * player.getEnchantSkillBonusMul()));
        player.addExpAndSp(-1L * skillEnchant2.getExp(), -1 * skillEnchant2.getSp());
        TimeStamp timeStamp = player.getSkillReuse(skill);
        Skill skill2 = null;
        if (Rnd.chance(n5)) {
            skill2 = SkillTable.getInstance().getInfo(skillEnchant2.getSkillId(), skillEnchant2.getSkillLevel());
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.SKILL_ENCHANT_WAS_SUCCESSFUL_S1_HAS_BEEN_ENCHANTED).addSkillName(this._skillId, this.qN));
            player.getListeners().onSkillEnchantSuccessListener(this._skillId, this.qN);
            Log.add(player.getName() + "|Successfully enchanted|" + this._skillId + "|to+" + this.qN + "|" + n5, "enchant_skills");
        } else {
            if (skillEnchant2.getResetToLevel() > 0) {
                int n6 = EnchantSkillHolder.getInstance().getFirstSkillLevelOf(skill.getId(), skillEnchant2.getRouteId());
                int n7 = n6 + (skillEnchant2.getResetToLevel() - 1);
                skill2 = SkillTable.getInstance().getInfo(skill.getId(), n7);
                if (skill2 != null) {
                    player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.SKILL_ENCHANT_FAILED));
                    player.sendMessage(new CustomMessage("SKILL_ENCHANT_FAILED_SKILL_S1_RESET_TO_LEVEL_S2", player, new Object[0]).addSkillName(this._skillId, n7).addNumber(skillEnchant2.getResetToLevel()));
                } else {
                    cS.error("Failed to find skill info for skillId={}, resetLevel={}, routeId={}, baseSkillLevel={}", new Object[]{skill.getId(), n7, skillEnchant2.getRouteId(), n6});
                    skill2 = SkillTable.getInstance().getInfo(skill.getId(), skill.getBaseLevel());
                }
            } else if (skillEnchant2.isResetOnFailure()) {
                skill2 = SkillTable.getInstance().getInfo(skill.getId(), skill.getBaseLevel());
                player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.SKILL_ENCHANT_FAILED));
            } else {
                skill2 = SkillTable.getInstance().getInfo(skill.getId(), skill.getLevel());
                player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.SKILL_ENCHANT_FAILED_S1).addSkillName(this._skillId, this.qN));
            }
            Log.add(player.getName() + "|Failed to enchant|" + this._skillId + "|to+" + this.qN + "|" + n5, "enchant_skills");
        }
        if (timeStamp != null && timeStamp.hasNotPassed()) {
            player.disableSkill(skill2, timeStamp.getReuseCurrent());
        }
        player.addSkill(skill2, true);
        player.sendSkillList();
        RequestExEnchantSkill.updateSkillShortcuts(player, this._skillId, this.qN);
        player.sendPacket((IStaticPacket)ExEnchantSkillList.packetFor(player, player.getLastNpc()));
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
