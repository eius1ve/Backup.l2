/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.ai.Mystic
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.World
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.tables.SkillTable
 */
package ai;

import java.util.Collections;
import java.util.List;
import l2.commons.util.Rnd;
import l2.gameserver.ai.Mystic;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.World;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.tables.SkillTable;

public class TriolsRevelation
extends Mystic {
    private static int aC = 3;
    private static int aD = 500;
    private static Skill g = SkillTable.getInstance().getInfo(4088, 8);

    public TriolsRevelation(NpcInstance npcInstance) {
        super(npcInstance);
        npcInstance.startImmobilized();
        this.AI_TASK_ATTACK_DELAY = 1250L;
    }

    protected boolean thinkActive() {
        NpcInstance npcInstance = this.getActor();
        if (npcInstance.isDead() || !Rnd.chance((int)aC)) {
            return false;
        }
        List list = World.getAroundPlayers((GameObject)npcInstance, (int)aD, (int)200);
        if (list == null || list.isEmpty()) {
            return false;
        }
        Collections.shuffle(list);
        for (Player player : list) {
            if (player.getEffectList().getEffectsCountForSkill(g.getId()) >= 1) continue;
            g.getEffects((Creature)npcInstance, (Creature)player, false, false);
            break;
        }
        return true;
    }

    protected boolean randomWalk() {
        return false;
    }
}
