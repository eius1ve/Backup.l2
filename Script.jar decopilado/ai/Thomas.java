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

public class Thomas
extends Fighter {
    private long v;
    private static final String[] j = new String[]{"ThomasSay1", "ThomasSay2", "ThomasSay3", "ThomasSay4"};
    private static final String[] k = new String[]{"ThomasAttacked1", "ThomasAttacked2", "ThomasAttacked3", "ThomasAttacked4", "ThomasAttacked5", "ThomasAttacked6", "ThomasAttacked7"};

    public Thomas(NpcInstance npcInstance) {
        super(npcInstance);
    }

    protected boolean thinkActive() {
        NpcInstance npcInstance = this.getActor();
        if (npcInstance.isDead()) {
            return true;
        }
        if (!npcInstance.isInCombat() && System.currentTimeMillis() - this.v > 10000L) {
            Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)j[Rnd.get((int)j.length)], (Object[])new Object[0]);
            this.v = System.currentTimeMillis();
        }
        return super.thinkActive();
    }

    protected void onEvtAttacked(Creature creature, int n) {
        NpcInstance npcInstance = this.getActor();
        if (creature == null || creature.getPlayer() == null) {
            return;
        }
        if (System.currentTimeMillis() - this.v > 5000L) {
            Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)k[Rnd.get((int)k.length)], (Object[])new Object[0]);
            this.v = System.currentTimeMillis();
        }
        super.onEvtAttacked(creature, n);
    }
}
