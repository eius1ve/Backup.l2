/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.ai.DefaultAI
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.tables.SkillTable
 */
package ai.primeval_isle;

import l2.gameserver.ai.DefaultAI;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Skill;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.tables.SkillTable;

public class SprigantPoison
extends DefaultAI {
    private final Skill i = SkillTable.getInstance().getInfo(5086, 1);
    private long O;
    private static final int bk = 15000;

    public SprigantPoison(NpcInstance npcInstance) {
        super(npcInstance);
    }

    protected boolean thinkActive() {
        if (System.currentTimeMillis() > this.O) {
            NpcInstance npcInstance = this.getActor();
            npcInstance.doCast(this.i, (Creature)npcInstance, false);
            this.O = System.currentTimeMillis() + 15000L;
            return true;
        }
        return false;
    }
}
