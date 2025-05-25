/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.Config
 *  l2.gameserver.ai.CtrlEvent
 *  l2.gameserver.ai.DefaultAI
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.tables.SkillTable
 *  l2.gameserver.utils.Location
 */
package ai;

import bosses.AntharasManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import l2.commons.util.Rnd;
import l2.gameserver.Config;
import l2.gameserver.ai.CtrlEvent;
import l2.gameserver.ai.DefaultAI;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Skill;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.scripts.Functions;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.utils.Location;

public class Antharas
extends DefaultAI {
    final Skill s_fear = this.a(4108, 1);
    final Skill s_fear2 = this.a(5092, 1);
    final Skill s_curse = this.a(4109, 1);
    final Skill s_paralyze = this.a(4111, 1);
    final Skill s_shock = this.a(4106, 1);
    final Skill s_shock2 = this.a(4107, 1);
    final Skill s_antharas_ordinary_attack = this.a(4112, 1);
    final Skill s_antharas_ordinary_attack2 = this.a(4113, 1);
    final Skill s_meteor = this.a(5093, 1);
    final Skill s_breath = this.a(4110, 1);
    final Skill s_regen1 = this.a(4239, 1);
    final Skill s_regen2 = this.a(4240, 1);
    final Skill s_regen3 = this.a(4241, 1);
    private int s = 0;
    private static long d = 0L;
    private List<NpcInstance> j = new ArrayList<NpcInstance>();

    public Antharas(NpcInstance npcInstance) {
        super(npcInstance);
    }

    protected void onEvtAttacked(Creature creature, int n) {
        AntharasManager.setLastAttackTime();
        for (Playable playable : AntharasManager.getZone().getInsidePlayables()) {
            this.notifyEvent(CtrlEvent.EVT_AGGRESSION, playable, 1);
        }
        super.onEvtAttacked(creature, n);
    }

    protected void onEvtSpawn() {
        super.onEvtSpawn();
        d = System.currentTimeMillis() + 120000L;
    }

    public boolean isGlobalAI() {
        return true;
    }

    protected boolean createNewTask() {
        NpcInstance npcInstance;
        this.clearTasks();
        Creature creature = this.prepareTarget();
        if (creature == null) {
            return false;
        }
        NpcInstance npcInstance2 = this.getActor();
        if (npcInstance2.isDead()) {
            return false;
        }
        double d = npcInstance2.getDistance((GameObject)creature);
        double d2 = npcInstance2.getCurrentHpPercents();
        if (this.s == 0) {
            npcInstance2.altOnMagicUseTimer((Creature)npcInstance2, this.s_regen1);
            this.s = 1;
        } else if (d2 < 75.0 && this.s == 1) {
            npcInstance2.altOnMagicUseTimer((Creature)npcInstance2, this.s_regen2);
            this.s = 2;
        } else if (d2 < 50.0 && this.s == 2) {
            npcInstance2.altOnMagicUseTimer((Creature)npcInstance2, this.s_regen3);
            this.s = 3;
        } else if (d2 < 30.0 && this.s == 3) {
            npcInstance2.altOnMagicUseTimer((Creature)npcInstance2, this.s_regen3);
            this.s = 4;
        }
        if (Config.FWA_ALLOW_ANTHARAS_MINION && Antharas.d < System.currentTimeMillis() && this.d() < 30 && Rnd.chance((int)5)) {
            npcInstance = Functions.spawn((Location)Location.findPointToStay((Location)npcInstance2.getLoc(), (int)400, (int)700, (int)npcInstance2.getGeoIndex()), (int)(Rnd.chance((int)50) ? 29070 : 29069));
            this.j.add(npcInstance);
            AntharasManager.addSpawnedMinion(npcInstance);
        }
        if (Rnd.chance((int)50)) {
            return this.chooseTaskAndTargets(Rnd.chance((int)50) ? this.s_antharas_ordinary_attack : this.s_antharas_ordinary_attack2, creature, d);
        }
        npcInstance = new HashMap();
        switch (this.s) {
            case 1: {
                this.addDesiredSkill((Map)npcInstance, creature, d, this.s_curse);
                this.addDesiredSkill((Map)npcInstance, creature, d, this.s_paralyze);
                this.addDesiredSkill((Map)npcInstance, creature, d, this.s_meteor);
                break;
            }
            case 2: {
                this.addDesiredSkill((Map)npcInstance, creature, d, this.s_curse);
                this.addDesiredSkill((Map)npcInstance, creature, d, this.s_paralyze);
                this.addDesiredSkill((Map)npcInstance, creature, d, this.s_meteor);
                this.addDesiredSkill((Map)npcInstance, creature, d, this.s_fear2);
                break;
            }
            case 3: {
                this.addDesiredSkill((Map)npcInstance, creature, d, this.s_curse);
                this.addDesiredSkill((Map)npcInstance, creature, d, this.s_paralyze);
                this.addDesiredSkill((Map)npcInstance, creature, d, this.s_meteor);
                this.addDesiredSkill((Map)npcInstance, creature, d, this.s_fear2);
                this.addDesiredSkill((Map)npcInstance, creature, d, this.s_shock2);
                this.addDesiredSkill((Map)npcInstance, creature, d, this.s_breath);
                break;
            }
            case 4: {
                this.addDesiredSkill((Map)npcInstance, creature, d, this.s_curse);
                this.addDesiredSkill((Map)npcInstance, creature, d, this.s_paralyze);
                this.addDesiredSkill((Map)npcInstance, creature, d, this.s_meteor);
                this.addDesiredSkill((Map)npcInstance, creature, d, this.s_fear2);
                this.addDesiredSkill((Map)npcInstance, creature, d, this.s_shock2);
                this.addDesiredSkill((Map)npcInstance, creature, d, this.s_fear);
                this.addDesiredSkill((Map)npcInstance, creature, d, this.s_shock);
                this.addDesiredSkill((Map)npcInstance, creature, d, this.s_breath);
                break;
            }
        }
        Skill skill = this.selectTopSkill((Map)npcInstance);
        if (skill != null && !skill.isOffensive()) {
            creature = npcInstance2;
        }
        return this.chooseTaskAndTargets(skill, creature, d);
    }

    private int d() {
        int n = 0;
        for (NpcInstance npcInstance : this.j) {
            if (npcInstance == null || npcInstance.isDead()) continue;
            ++n;
        }
        return n;
    }

    private Skill a(int n, int n2) {
        return SkillTable.getInstance().getInfo(n, n2);
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
