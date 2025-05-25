/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.Collection;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.tables.SkillTable;

public class GMViewSkillInfo
extends L2GameServerPacket {
    private final String fx;
    private final Collection<Skill> d;
    private final Player p;

    public GMViewSkillInfo(Player player) {
        this.fx = player.getName();
        this.d = player.getAllSkills();
        this.p = player;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(151);
        this.writeS(this.fx);
        this.writeD(this.d.size());
        for (Skill skill : this.d) {
            this.writeD(skill.isPassive() ? 1 : 0);
            this.writeH(skill.getLevelForPacket());
            this.writeH(skill.getSubLvl());
            this.writeD(skill.getId());
            this.writeD(0);
            this.writeC(this.p.isUnActiveSkill(skill.getId()) ? 1 : 0);
            this.writeC(SkillTable.getInstance().getMaxLevel(skill.getId()) > 100 ? 1 : 0);
        }
    }
}
