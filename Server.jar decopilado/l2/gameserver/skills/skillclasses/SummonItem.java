/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.skills.skillclasses;

import java.util.List;
import l2.commons.util.Rnd;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Skill;
import l2.gameserver.templates.StatsSet;
import l2.gameserver.utils.ItemFunctions;

public class SummonItem
extends Skill {
    private final int Dw;
    private final int Dx;
    private final int Dy;
    private final long dI;
    private final long dJ;

    public SummonItem(StatsSet statsSet) {
        super(statsSet);
        this.Dw = statsSet.getInteger("SummonItemId", 0);
        this.Dx = statsSet.getInteger("SummonMinId", 0);
        this.Dy = statsSet.getInteger("SummonMaxId", this.Dx);
        this.dI = statsSet.getLong("SummonMinCount");
        this.dJ = statsSet.getLong("SummonMaxCount", this.dI);
    }

    @Override
    public void useSkill(Creature creature, List<Creature> list) {
        if (!creature.isPlayable()) {
            return;
        }
        for (Creature creature2 : list) {
            if (creature2 == null) continue;
            int n = this.Dx > 0 ? Rnd.get(this.Dx, this.Dy) : this.Dw;
            long l = Rnd.get(this.dI, this.dJ);
            ItemFunctions.addItem((Playable)creature, n, l, true);
            this.getEffects(creature, creature2, this.getActivateRate() > 0, false);
        }
    }
}
