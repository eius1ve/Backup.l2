/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import l2.gameserver.data.xml.holder.SkillAcquireHolder;
import l2.gameserver.model.Player;
import l2.gameserver.model.SkillLearn;
import l2.gameserver.model.base.AcquireType;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class AcquireSkillList
extends L2GameServerPacket {
    private final List<SkillLearn> bT;

    public AcquireSkillList(Player player) {
        Collection<SkillLearn> collection = SkillAcquireHolder.getInstance().getAvailableSkills(player, AcquireType.NORMAL, 9);
        this.bT = new ArrayList<SkillLearn>(collection.size());
        for (SkillLearn skillLearn : collection) {
            if (skillLearn.isClicked()) continue;
            this.bT.add(skillLearn);
        }
    }

    @Override
    protected final void writeImpl() {
        this.writeC(144);
        this.writeH(this.bT.size());
        for (SkillLearn skillLearn : this.bT) {
            this.writeD(skillLearn.getId());
            this.writeH(skillLearn.getLevel());
            this.writeQ(skillLearn.getCost());
            this.writeC(skillLearn.getMinLevel());
            this.writeH(0);
            if (skillLearn.getItemId() > 0) {
                this.writeC(1);
                this.writeD(skillLearn.getItemId());
                this.writeQ(skillLearn.getItemCount());
            } else {
                this.writeC(0);
            }
            this.writeC(0);
        }
    }
}
