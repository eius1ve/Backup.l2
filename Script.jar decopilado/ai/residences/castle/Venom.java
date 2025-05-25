/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.ai.Fighter
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.utils.NpcUtils
 */
package ai.residences.castle;

import l2.gameserver.ai.Fighter;
import l2.gameserver.model.Creature;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.scripts.Functions;
import l2.gameserver.utils.NpcUtils;

public class Venom
extends Fighter {
    public Venom(NpcInstance npcInstance) {
        super(npcInstance);
    }

    public void onEvtSpawn() {
        super.onEvtSpawn();
        Functions.npcShoutCustomMessage((NpcInstance)this.getActor(), (String)"1010623", (Object[])new Object[0]);
    }

    public void onEvtDead(Creature creature) {
        super.onEvtDead(creature);
        Functions.npcShoutCustomMessage((NpcInstance)this.getActor(), (String)"1010626", (Object[])new Object[0]);
        NpcUtils.spawnSingle((int)29055, (int)12589, (int)-49044, (int)-3008, (long)120000L);
    }
}
