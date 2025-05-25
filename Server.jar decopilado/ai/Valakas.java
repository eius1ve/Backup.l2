/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.ai.CtrlEvent
 *  l2.gameserver.ai.DefaultAI
 *  l2.gameserver.ai.DefaultAI$MadnessTask
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.Zone$ZoneType
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.tables.SkillTable
 */
package ai;

import bosses.ValakasManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import l2.commons.util.Rnd;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.ai.CtrlEvent;
import l2.gameserver.ai.DefaultAI;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Skill;
import l2.gameserver.model.Zone;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.tables.SkillTable;

public class Valakas
extends DefaultAI {
    final Skill s_lava_skin = this.a(4680, 1);
    final Skill s_fear = this.a(4689, 1);
    final Skill s_regen1 = this.a(4691, 1);
    final Skill s_regen2 = this.a(4691, 2);
    final Skill s_regen3 = this.a(4691, 3);
    final Skill s_regen4 = this.a(4691, 4);
    final Skill s_tremple_left = this.a(4681, 1);
    final Skill s_tremple_right = this.a(4682, 1);
    final Skill s_tail_stomp_a = this.a(4685, 1);
    final Skill s_tail_lash = this.a(4688, 1);
    final Skill s_meteor = this.a(4690, 1);
    final Skill s_breath_low = this.a(4683, 1);
    final Skill s_breath_high = this.a(4684, 1);
    private long B = Long.MAX_VALUE;
    private final long C = 120000L;
    private double b;
    private int s = 0;
    private List<NpcInstance> j = new ArrayList<NpcInstance>();

    public Valakas(NpcInstance npcInstance) {
        super(npcInstance);
    }

    protected void onEvtAttacked(Creature creature, int n) {
        NpcInstance npcInstance = this.getActor();
        ValakasManager.setLastAttackTime();
        for (Playable playable : ValakasManager.getZone().getInsidePlayables()) {
            this.notifyEvent(CtrlEvent.EVT_AGGRESSION, playable, 1);
        }
        if (n > 100 && creature.getDistance((GameObject)npcInstance) > 400.0) {
            this.b += (double)n / 1000.0;
        }
        super.onEvtAttacked(creature, n);
    }

    public boolean isGlobalAI() {
        return true;
    }

    protected boolean createNewTask() {
        Object object;
        this.clearTasks();
        Creature creature = this.prepareTarget();
        if (creature == null) {
            return false;
        }
        NpcInstance npcInstance = this.getActor();
        if (npcInstance.isDead()) {
            return false;
        }
        double d = npcInstance.getDistance((GameObject)creature);
        double d2 = npcInstance.getCurrentHpPercents();
        if (this.s == 0) {
            npcInstance.altOnMagicUseTimer((Creature)npcInstance, this.s_regen1);
            this.s = 1;
        } else if (d2 < 80.0 && this.s == 1) {
            npcInstance.altOnMagicUseTimer((Creature)npcInstance, this.s_regen2);
            this.B = System.currentTimeMillis();
            this.s = 2;
        } else if (d2 < 50.0 && this.s == 2) {
            npcInstance.altOnMagicUseTimer((Creature)npcInstance, this.s_regen3);
            this.s = 3;
        } else if (d2 < 30.0 && this.s == 3) {
            npcInstance.altOnMagicUseTimer((Creature)npcInstance, this.s_regen4);
            this.s = 4;
        }
        if (this.b > 2000.0) {
            if (Rnd.chance((int)60)) {
                object = npcInstance.getAggroList().getRandomHated();
                if (object != null && Math.abs(npcInstance.getZ() - object.getZ()) < 400) {
                    this.setAttackTarget((Creature)object);
                    if (this._madnessTask == null && !npcInstance.isConfused()) {
                        npcInstance.startConfused();
                        this._madnessTask = ThreadPoolManager.getInstance().schedule((Runnable)new DefaultAI.MadnessTask((DefaultAI)this), 20000L);
                    }
                }
                ValakasManager.broadcastCustomScreenMessage("ValakasRangedAttack");
                this.b = 0.0;
            }
        } else if (this.B < System.currentTimeMillis()) {
            ValakasManager.broadcastCustomScreenMessage("ValakasAttaksKeepIt");
            this.B = System.currentTimeMillis() + 120000L + (long)Rnd.get((int)60) * 1000L;
            return this.chooseTaskAndTargets(this.s_fear, creature, d);
        }
        if (Rnd.chance((int)50)) {
            return this.chooseTaskAndTargets(Rnd.chance((int)50) ? this.s_tremple_left : this.s_tremple_right, creature, d);
        }
        object = new HashMap();
        switch (this.s) {
            case 1: {
                this.addDesiredSkill((Map)object, creature, d, this.s_breath_low);
                this.addDesiredSkill((Map)object, creature, d, this.s_tail_stomp_a);
                this.addDesiredSkill((Map)object, creature, d, this.s_meteor);
                this.addDesiredSkill((Map)object, creature, d, this.s_fear);
                break;
            }
            case 2: 
            case 3: {
                this.addDesiredSkill((Map)object, creature, d, this.s_breath_low);
                this.addDesiredSkill((Map)object, creature, d, this.s_tail_stomp_a);
                this.addDesiredSkill((Map)object, creature, d, this.s_breath_high);
                this.addDesiredSkill((Map)object, creature, d, this.s_tail_lash);
                this.addDesiredSkill((Map)object, creature, d, this.s_meteor);
                this.addDesiredSkill((Map)object, creature, d, this.s_fear);
                break;
            }
            case 4: {
                this.addDesiredSkill((Map)object, creature, d, this.s_breath_low);
                this.addDesiredSkill((Map)object, creature, d, this.s_tail_stomp_a);
                this.addDesiredSkill((Map)object, creature, d, this.s_breath_high);
                this.addDesiredSkill((Map)object, creature, d, this.s_tail_lash);
                this.addDesiredSkill((Map)object, creature, d, this.s_meteor);
                this.addDesiredSkill((Map)object, creature, d, this.s_fear);
            }
        }
        Skill skill = this.selectTopSkill((Map)object);
        if (skill != null && !skill.isOffensive()) {
            creature = npcInstance;
        }
        return this.chooseTaskAndTargets(skill, creature, d);
    }

    protected void thinkAttack() {
        NpcInstance npcInstance = this.getActor();
        if (npcInstance.isInZone(Zone.ZoneType.poison) && npcInstance.getEffectList() != null && npcInstance.getEffectList().getEffectsBySkill(this.s_lava_skin) == null) {
            npcInstance.altOnMagicUseTimer((Creature)npcInstance, this.s_lava_skin);
        }
        super.thinkAttack();
    }

    private Skill a(int n, int n2) {
        return SkillTable.getInstance().getInfo(n, n2);
    }

    private int d() {
        int n = 0;
        for (NpcInstance npcInstance : this.j) {
            if (npcInstance == null || npcInstance.isDead()) continue;
            ++n;
        }
        return n;
    }

    protected void onEvtDead(Creature creature) {
        if (this.j != null && !this.j.isEmpty()) {
            for (NpcInstance npcInstance : this.j) {
                npcInstance.deleteMe();
            }
        }
        super.onEvtDead(creature);
    }
}
