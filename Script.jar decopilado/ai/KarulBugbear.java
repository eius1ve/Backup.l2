/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.ai.Fighter
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.scripts.Functions
 */
package ai;

import l2.commons.util.Rnd;
import l2.gameserver.ai.Fighter;
import l2.gameserver.model.Creature;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.scripts.Functions;

public class KarulBugbear
extends Fighter {
    private boolean j = true;

    public KarulBugbear(NpcInstance npcInstance) {
        super(npcInstance);
    }

    protected void onEvtSpawn() {
        this.j = true;
        super.onEvtSpawn();
    }

    protected void onEvtAttacked(Creature creature, int n) {
        NpcInstance npcInstance = this.getActor();
        if (this.j) {
            this.j = false;
            if (Rnd.chance((int)25)) {
                Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"1000382", (Object[])new Object[0]);
            }
        } else if (Rnd.chance((int)10)) {
            Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"1000381", (Object[])new Object[]{creature.getName()});
        }
        super.onEvtAttacked(creature, n);
    }
}
