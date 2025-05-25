/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.instances.residences.clanhall.CTBBossInstance
 *  l2.gameserver.tables.SkillTable
 */
package ai.residences.clanhall;

import ai.residences.clanhall.MatchFighter;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Skill;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.instances.residences.clanhall.CTBBossInstance;
import l2.gameserver.tables.SkillTable;

public class MatchCleric
extends MatchFighter {
    public static final Skill HEAL = SkillTable.getInstance().getInfo(4056, 6);

    public MatchCleric(NpcInstance npcInstance) {
        super(npcInstance);
    }

    public void heal() {
        CTBBossInstance cTBBossInstance = this.getActor();
        this.addTaskCast((Creature)cTBBossInstance, HEAL);
        this.doTask();
    }
}
