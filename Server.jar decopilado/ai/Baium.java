/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.TIntObjectHashMap
 *  l2.commons.util.Rnd
 *  l2.gameserver.ai.CtrlEvent
 *  l2.gameserver.ai.DefaultAI
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.Skill$SkillTargetType
 *  l2.gameserver.model.instances.NpcInstance
 */
package ai;

import bosses.BaiumManager;
import gnu.trove.TIntObjectHashMap;
import java.util.HashMap;
import l2.commons.util.Rnd;
import l2.gameserver.ai.CtrlEvent;
import l2.gameserver.ai.DefaultAI;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Skill;
import l2.gameserver.model.instances.NpcInstance;

public class Baium
extends DefaultAI {
    private boolean j = true;
    private final Skill a;
    private final Skill b;
    private final Skill c;
    private final Skill d;
    private final Skill e;

    public Baium(NpcInstance npcInstance) {
        super(npcInstance);
        TIntObjectHashMap tIntObjectHashMap = this.getActor().getTemplate().getSkills();
        this.a = (Skill)tIntObjectHashMap.get(4127);
        this.b = (Skill)tIntObjectHashMap.get(4128);
        this.c = (Skill)tIntObjectHashMap.get(4129);
        this.d = (Skill)tIntObjectHashMap.get(4130);
        this.e = (Skill)tIntObjectHashMap.get(4131);
    }

    public boolean isGlobalAI() {
        return true;
    }

    protected void onEvtAttacked(Creature creature, int n) {
        BaiumManager.setLastAttackTime();
        for (Playable playable : BaiumManager.getZone().getInsidePlayables()) {
            this.notifyEvent(CtrlEvent.EVT_AGGRESSION, playable, 1);
        }
        super.onEvtAttacked(creature, n);
    }

    protected boolean createNewTask() {
        NpcInstance npcInstance = this.getActor();
        if (npcInstance == null) {
            return true;
        }
        if (!BaiumManager.getZone().checkIfInZone((Creature)npcInstance)) {
            this.teleportHome();
            return false;
        }
        this.clearTasks();
        Creature creature = this.prepareTarget();
        if (creature == null) {
            return false;
        }
        if (!BaiumManager.getZone().checkIfInZone(creature)) {
            npcInstance.getAggroList().remove(creature, false);
            return false;
        }
        int n = 20;
        int n2 = 20;
        int n3 = npcInstance.getCurrentHpPercents() > 50.0 ? 0 : 20;
        int n4 = npcInstance.getCurrentHpPercents() > 25.0 ? 0 : 20;
        Skill skill = null;
        if (npcInstance.isMovementDisabled()) {
            skill = this.d;
        } else if (!Rnd.chance((int)(100 - n4 - n3 - n - n2))) {
            HashMap hashMap = new HashMap();
            double d = npcInstance.getDistance((GameObject)creature);
            this.addDesiredSkill(hashMap, creature, d, this.b);
            this.addDesiredSkill(hashMap, creature, d, this.c);
            if (n3 > 0) {
                this.addDesiredSkill(hashMap, creature, d, this.e);
            }
            if (n4 > 0) {
                this.addDesiredSkill(hashMap, creature, d, this.d);
            }
            skill = this.selectTopSkill(hashMap);
        }
        if (skill == null) {
            skill = this.a;
        } else if (skill.getTargetType() == Skill.SkillTargetType.TARGET_SELF) {
            creature = npcInstance;
        }
        this.addTaskCast(creature, skill);
        skill = null;
        return true;
    }

    protected boolean maybeMoveToHome() {
        NpcInstance npcInstance = this.getActor();
        if (npcInstance != null && !BaiumManager.getZone().checkIfInZone((Creature)npcInstance)) {
            this.teleportHome();
        }
        return false;
    }

    protected void onEvtDead(Creature creature) {
        this.j = true;
        super.onEvtDead(creature);
    }
}
