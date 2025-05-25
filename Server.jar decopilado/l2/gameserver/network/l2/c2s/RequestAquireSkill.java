/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.SkillAcquireHolder;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.SkillLearn;
import l2.gameserver.model.base.AcquireType;
import l2.gameserver.model.base.ClassId;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.instances.VillageMasterInstance;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.c2s.RequestExEnchantSkill;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.tables.SkillTable;

public class RequestAquireSkill
extends L2GameClientPacket {
    private AcquireType a;
    private int _id;
    private int _level;

    @Override
    protected void readImpl() {
        this._id = this.readD();
        this._level = this.readD();
        this.a = AcquireType.getById(this.readD());
    }

    @Override
    protected void runImpl() {
        ClassId classId;
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null || player.getTransformation() != 0 || player.isCursedWeaponEquipped() || this.a == null) {
            return;
        }
        NpcInstance npcInstance = null;
        if (!(this.a == AcquireType.NORMAL || (npcInstance = player.getLastNpc()) != null && npcInstance.isInActingRange(player))) {
            return;
        }
        Skill skill = SkillTable.getInstance().getInfo(this._id, this._level);
        if (skill == null) {
            return;
        }
        int n = Config.ALT_WEAK_SKILL_LEARN ? player.getVarInt("AcquireSkillClassId", player.getClassId().getId()) : player.getClassId().getId();
        ClassId classId2 = classId = n >= 0 && n < ClassId.VALUES.length ? ClassId.VALUES[n] : null;
        if (!SkillAcquireHolder.getInstance().isSkillPossible(player, classId, skill, this.a)) {
            return;
        }
        SkillLearn skillLearn = SkillAcquireHolder.getInstance().getSkillLearn(player, classId, this._id, this._level, this.a);
        if (skillLearn == null) {
            return;
        }
        if (!RequestAquireSkill.a(player, skillLearn)) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_THE_NECESSARY_MATERIALS_OR_PREREQUISITES_TO_LEARN_THIS_SKILL);
            return;
        }
        switch (this.a) {
            case NORMAL: {
                if (!Config.ENABLE_MAX_SKILL_TRAINING) {
                    RequestAquireSkill.a(this.a, player, skillLearn, skill);
                    break;
                }
                RequestAquireSkill.c(player, skillLearn, skill);
                break;
            }
            case FISHING: {
                RequestAquireSkill.a(player, skillLearn, skill);
                if (npcInstance == null) break;
                NpcInstance.showFishingSkillList(player);
                break;
            }
            case CERTIFICATION: {
                RequestAquireSkill.a(player, skillLearn, skill);
                if (npcInstance == null) break;
                NpcInstance.showCustomSkillList(player);
                break;
            }
            case CLAN: {
                RequestAquireSkill.a(player, skillLearn, npcInstance, skill);
            }
        }
    }

    private static void a(AcquireType acquireType, Player player, SkillLearn skillLearn, Skill skill) {
        int n = player.getSkillLevel(skillLearn.getId(), 0);
        if (n != skillLearn.getLevel() - 1) {
            return;
        }
        RequestAquireSkill.b(acquireType, player, skillLearn, skill);
    }

    private static void a(Player player, SkillLearn skillLearn, Skill skill) {
        int n = player.getSkillLevel(skillLearn.getId(), 0);
        if (n != skillLearn.getLevel() - 1) {
            return;
        }
        RequestAquireSkill.b(player, skillLearn, skill);
    }

    private static void b(AcquireType acquireType, Player player, SkillLearn skillLearn, Skill skill) {
        if (player.getSp() < (long)skillLearn.getCost()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_SP_TO_LEARN_THIS_SKILL);
            return;
        }
        if (!Config.ALT_DISABLE_SPELLBOOKS && skillLearn.getItemId() > 0 && !player.consumeItem(skillLearn.getItemId(), skillLearn.getItemCount())) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_THE_NECESSARY_MATERIALS_OR_PREREQUISITES_TO_LEARN_THIS_SKILL);
            return;
        }
        player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_HAVE_EARNED_S1_SKILL).addSkillName(skill.getId(), skill.getLevel()));
        player.setSp(player.getSp() - (long)skillLearn.getCost());
        player.addSkill(skill, true);
        player.sendUserInfo(false);
        player.updateStats();
        if (acquireType == AcquireType.NORMAL) {
            player.sendSkillList(skill.getId());
        } else {
            player.sendSkillList();
        }
        RequestExEnchantSkill.updateSkillShortcuts(player, skill.getId(), skill.getLevel());
    }

    private static void b(Player player, SkillLearn skillLearn, Skill skill) {
        if (player.getSp() < (long)skillLearn.getCost()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_SP_TO_LEARN_THIS_SKILL);
            return;
        }
        if (skillLearn.getItemId() > 0 && !player.consumeItem(skillLearn.getItemId(), skillLearn.getItemCount())) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_THE_NECESSARY_MATERIALS_OR_PREREQUISITES_TO_LEARN_THIS_SKILL);
            return;
        }
        player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_HAVE_EARNED_S1_SKILL).addSkillName(skill.getId(), skill.getLevel()));
        player.setSp(player.getSp() - (long)skillLearn.getCost());
        player.addSkill(skill, true);
        player.sendUserInfo(false);
        player.updateStats();
        player.sendSkillList();
        RequestExEnchantSkill.updateSkillShortcuts(player, skill.getId(), skill.getLevel());
    }

    private static void a(Player player, SkillLearn skillLearn, NpcInstance npcInstance, Skill skill) {
        if (!(npcInstance instanceof VillageMasterInstance)) {
            return;
        }
        if (!player.isClanLeader()) {
            player.sendPacket((IStaticPacket)SystemMsg.ONLY_THE_CLAN_LEADER_IS_ENABLED);
            return;
        }
        Clan clan = player.getClan();
        int n = clan.getSkillLevel(skillLearn.getId(), 0);
        if (n != skillLearn.getLevel() - 1) {
            return;
        }
        if (clan.getReputationScore() < skillLearn.getCost()) {
            player.sendPacket((IStaticPacket)SystemMsg.THE_CLAN_REPUTATION_SCORE_IS_TOO_LOW);
            return;
        }
        if (skillLearn.getItemId() != 0 && !player.consumeItem(skillLearn.getItemId(), skillLearn.getItemCount())) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_THE_NECESSARY_MATERIALS_OR_PREREQUISITES_TO_LEARN_THIS_SKILL);
            return;
        }
        clan.incReputation(-skillLearn.getCost(), false, "AquireSkill: " + skillLearn.getId() + ", lvl " + skillLearn.getLevel());
        clan.addSkill(skill, true);
        clan.broadcastToOnlineMembers(new L2GameServerPacket[]{new SystemMessage(SystemMsg.THE_CLAN_SKILL_S1_HAS_BEEN_ADDED).addSkillName(skill)});
        NpcInstance.showClanSkillList(player);
    }

    private static boolean a(Player player, SkillLearn skillLearn) {
        if (Config.ALT_DISABLE_SPELLBOOKS) {
            return true;
        }
        if (skillLearn.getItemId() == 0) {
            return true;
        }
        if (skillLearn.isClicked()) {
            return false;
        }
        return player.getInventory().getCountOf(skillLearn.getItemId()) >= skillLearn.getItemCount();
    }

    private static void c(Player player, SkillLearn skillLearn, Skill skill) {
        int n = skillLearn.getLevel();
        Skill skill2 = skill;
        for (SkillLearn skillLearn2 : SkillAcquireHolder.getInstance().getAllSkillLearn(player, player.getClassId(), AcquireType.NORMAL)) {
            if (skillLearn2.getId() != skillLearn.getId() || skillLearn2.getLevel() <= n) continue;
            n = skillLearn2.getLevel();
            skill2 = SkillTable.getInstance().getInfo(skillLearn2.getId(), n);
        }
        if (player.getSp() < (long)skillLearn.getCost()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_SP_TO_LEARN_THIS_SKILL);
            return;
        }
        if (!Config.ALT_DISABLE_SPELLBOOKS && skillLearn.getItemId() > 0 && !player.consumeItem(skillLearn.getItemId(), skillLearn.getItemCount())) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_THE_NECESSARY_MATERIALS_OR_PREREQUISITES_TO_LEARN_THIS_SKILL);
            return;
        }
        player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_HAVE_EARNED_S1_SKILL).addSkillName(skill2.getId(), n));
        player.setSp(player.getSp() - (long)skillLearn.getCost());
        player.addSkill(skill2, true);
        player.sendUserInfo(false);
        player.updateStats();
        player.sendSkillList(skill.getId());
        RequestExEnchantSkill.updateSkillShortcuts(player, skill2.getId(), skill2.getLevel());
    }
}
