/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.ai.CtrlEvent
 *  l2.gameserver.ai.Fighter
 *  l2.gameserver.data.xml.holder.NpcHolder
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.entity.Reflection
 *  l2.gameserver.model.instances.MonsterInstance
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.MyTargetSelected
 *  l2.gameserver.templates.StatsSet
 *  l2.gameserver.utils.Location
 *  l2.gameserver.utils.NpcUtils
 */
package ai;

import l2.commons.util.Rnd;
import l2.gameserver.ai.CtrlEvent;
import l2.gameserver.ai.Fighter;
import l2.gameserver.data.xml.holder.NpcHolder;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.entity.Reflection;
import l2.gameserver.model.instances.MonsterInstance;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.MyTargetSelected;
import l2.gameserver.templates.StatsSet;
import l2.gameserver.utils.Location;
import l2.gameserver.utils.NpcUtils;

public class TransformationFighter
extends Fighter {
    private final int aq;
    private final int ar;
    private final int as;
    private final int at;
    private final int au;
    private final int av;

    public TransformationFighter(NpcInstance npcInstance) {
        super(npcInstance);
        StatsSet statsSet = npcInstance.getTemplate().getAIParams();
        this.aq = statsSet.getInteger((Object)"transformOnDead", 0);
        this.ar = statsSet.getInteger((Object)"transformOnUnderAttack", 0);
        this.as = statsSet.getInteger((Object)"transformSpawnAmount", 1);
        this.at = statsSet.getInteger((Object)"transformSpawnRndRadius", 0);
        this.au = statsSet.getInteger((Object)"transformChance", 100);
        this.av = statsSet.getInteger((Object)"transformDespawnTime", 0);
    }

    protected void onEvtDead(Creature creature) {
        NpcInstance npcInstance = this.getActor();
        if (this.aq > 0 && Rnd.chance((int)this.au)) {
            for (int i = 0; i < this.as; ++i) {
                Location location = npcInstance.getLoc();
                if (this.at > 0) {
                    location = Location.findPointToStay((Location)location, (int)this.at, (int)creature.getGeoIndex());
                }
                NpcInstance npcInstance2 = NpcUtils.spawnSingle((int)this.aq, (Location)location, (Reflection)npcInstance.getReflection(), (long)this.av);
                if (creature == null || !creature.isPlayable()) continue;
                npcInstance2.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)creature, (Object)100);
                creature.sendPacket((IStaticPacket)npcInstance2.makeStatusUpdate(new int[]{9, 10}));
            }
        }
        super.onEvtDead(creature);
    }

    protected void onEvtAttacked(Creature creature, int n) {
        NpcInstance npcInstance = this.getActor();
        if (creature == null || npcInstance.isDead()) {
            return;
        }
        if (this.ar > 0 && (this.au == 100 || ((MonsterInstance)npcInstance).getChampion() == 0 && npcInstance.getCurrentHpPercents() > 50.0 && Rnd.chance((int)this.au))) {
            MonsterInstance monsterInstance = (MonsterInstance)NpcHolder.getInstance().getTemplate(this.ar).getNewInstance();
            monsterInstance.setSpawnedLoc(npcInstance.getLoc());
            monsterInstance.setReflection(npcInstance.getReflection());
            monsterInstance.setCurrentHpMp((double)monsterInstance.getMaxHp(), (double)monsterInstance.getMaxMp(), true);
            monsterInstance.spawnMe(monsterInstance.getSpawnedLoc());
            monsterInstance.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, (Object)creature, (Object)100);
            npcInstance.decayOrDelete();
            creature.setTarget((GameObject)monsterInstance);
            creature.sendPacket((IStaticPacket)new MyTargetSelected(monsterInstance.getObjectId(), creature.getLevel() - monsterInstance.getLevel()));
            creature.sendPacket((IStaticPacket)monsterInstance.makeStatusUpdate(new int[]{9, 10}));
            return;
        }
        super.onEvtAttacked(creature, n);
    }
}
