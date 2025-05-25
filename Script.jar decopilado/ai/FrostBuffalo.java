/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.ai.CtrlEvent
 *  l2.gameserver.ai.Fighter
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.entity.Reflection
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.utils.Location
 *  l2.gameserver.utils.NpcUtils
 */
package ai;

import l2.commons.util.Rnd;
import l2.gameserver.ai.CtrlEvent;
import l2.gameserver.ai.Fighter;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Skill;
import l2.gameserver.model.entity.Reflection;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.utils.Location;
import l2.gameserver.utils.NpcUtils;

public class FrostBuffalo
extends Fighter {
    private boolean p;
    private static final int P = 22093;
    private static final int Q = 4;

    public FrostBuffalo(NpcInstance npcInstance) {
        super(npcInstance);
    }

    protected void onEvtSeeSpell(Skill skill, Creature creature) {
        NpcInstance npcInstance = this.getActor();
        if (npcInstance == null || creature == null || skill == null) {
            return;
        }
        if (skill.isMagic()) {
            return;
        }
        if (this.p) {
            this.p = false;
            for (int i = 0; i < 4; ++i) {
                try {
                    NpcInstance npcInstance2 = NpcUtils.spawnSingle((int)22093, (Location)Location.findPointToStay((GameObject)npcInstance, (int)100, (int)120), (Reflection)npcInstance.getReflection());
                    if (creature.isPet() || creature.isSummon()) {
                        npcInstance2.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)creature, (Object)Rnd.get((int)2, (int)100));
                        continue;
                    }
                    npcInstance2.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)creature.getPlayer(), (Object)Rnd.get((int)1, (int)100));
                    continue;
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    protected void onEvtSpawn() {
        this.p = true;
        super.onEvtSpawn();
    }

    protected void onEvtDead(Creature creature) {
        super.onEvtDead(creature);
    }

    protected boolean randomWalk() {
        return this.p;
    }
}
