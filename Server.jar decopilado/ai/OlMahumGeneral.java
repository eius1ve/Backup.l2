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

public class OlMahumGeneral
extends Fighter {
    private boolean j = true;

    public OlMahumGeneral(NpcInstance npcInstance) {
        super(npcInstance);
    }

    protected void onEvtAttacked(Creature creature, int n) {
        NpcInstance npcInstance = this.getActor();
        if (this.j) {
            this.j = false;
            if (Rnd.chance((int)25)) {
                Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"1000007", (Object[])new Object[0]);
            }
        } else if (Rnd.chance((int)10)) {
            Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"1000008", (Object[])new Object[0]);
        }
        super.onEvtAttacked(creature, n);
    }

    protected void onEvtDead(Creature creature) {
        this.j = true;
        super.onEvtDead(creature);
    }
}
