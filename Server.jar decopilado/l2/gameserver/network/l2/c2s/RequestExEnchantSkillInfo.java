/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import java.util.Map;
import l2.gameserver.data.xml.holder.EnchantSkillHolder;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.base.Experience;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.templates.SkillEnchant;

public class RequestExEnchantSkillInfo
extends L2GameClientPacket {
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
        } else if (skillEnchant2.getEnchantLevel() != 1) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.THERE_IS_NO_SKILL_THAT_ENABLES_ENCHANT));
            return;
        }
        int[] nArray = skillEnchant2.getChances();
        int n2 = Experience.LEVEL.length - nArray.length - 1;
        if (player.getLevel() < n2) {
            this.sendPacket((L2GameServerPacket)new SystemMessage(SystemMsg.YOU_DO_NOT_HAVE_ANY_FURTHER_SKILLS_TO_LEARN__COME_BACK_WHEN_YOU_HAVE_REACHED_LEVEL_S1).addNumber(n2));
            return;
        }
        int n3 = Math.max(0, Math.min(player.getLevel() - n2, nArray.length - 1));
        int n4 = (int)Math.min(100L, Math.round((double)nArray[n3] * player.getEnchantSkillBonusMul()));
    }
}
