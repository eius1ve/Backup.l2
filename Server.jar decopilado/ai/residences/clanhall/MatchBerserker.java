/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.tables.SkillTable
 */
package ai.residences.clanhall;

import ai.residences.clanhall.MatchFighter;
import l2.commons.util.Rnd;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Skill;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.tables.SkillTable;

public class MatchBerserker
extends MatchFighter {
    public static final Skill ATTACK_SKILL = SkillTable.getInstance().getInfo(4032, 6);

    public MatchBerserker(NpcInstance npcInstance) {
        super(npcInstance);
    }

    public void onEvtAttacked(Creature creature, int n) {
        super.onEvtAttacked(creature, n);
        if (Rnd.chance((int)10)) {
            this.addTaskCast(creature, ATTACK_SKILL);
        }
    }
}
