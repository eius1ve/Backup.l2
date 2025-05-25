/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.ai.DefaultAI
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.scripts.Functions
 */
package ai;

import l2.commons.util.Rnd;
import l2.gameserver.ai.DefaultAI;
import l2.gameserver.model.Creature;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.scripts.Functions;

public class GuardianAngel
extends DefaultAI {
    static final String[] flood = new String[]{"0006552", "0006553", "0006554"};

    public GuardianAngel(NpcInstance npcInstance) {
        super(npcInstance);
    }

    protected boolean thinkActive() {
        NpcInstance npcInstance = this.getActor();
        Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)flood[Rnd.get((int)2)], (Object[])new Object[0]);
        return super.thinkActive();
    }

    protected void onEvtDead(Creature creature) {
        NpcInstance npcInstance = this.getActor();
        if (npcInstance != null) {
            Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)flood[2], (Object[])new Object[]{creature.getName()});
        }
        super.onEvtDead(creature);
    }
}
