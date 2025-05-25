/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.ai.Fighter
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.entity.Reflection
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.utils.Location
 *  l2.gameserver.utils.NpcUtils
 */
package ai;

import l2.gameserver.ai.Fighter;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.entity.Reflection;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.utils.Location;
import l2.gameserver.utils.NpcUtils;

public class EtisEtina
extends Fighter {
    private boolean m = false;
    private NpcInstance a;
    private NpcInstance b;

    public EtisEtina(NpcInstance npcInstance) {
        super(npcInstance);
    }

    protected void onEvtAttacked(Creature creature, int n) {
        NpcInstance npcInstance = this.getActor();
        if (npcInstance.getCurrentHpPercents() < 70.0 && !this.m) {
            this.m = true;
            this.a = NpcUtils.spawnSingle((int)18950, (Location)Location.findAroundPosition((GameObject)npcInstance, (int)150), (Reflection)npcInstance.getReflection());
            this.b = NpcUtils.spawnSingle((int)18951, (Location)Location.findAroundPosition((GameObject)npcInstance, (int)150), (Reflection)npcInstance.getReflection());
        }
        super.onEvtAttacked(creature, n);
    }

    protected void onEvtDead(Creature creature) {
        if (this.a != null && !this.a.isDead()) {
            this.a.decayMe();
        }
        if (this.b != null && !this.b.isDead()) {
            this.b.decayMe();
        }
        super.onEvtDead(creature);
    }
}
