/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.ai.DefaultAI
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.network.l2.s2c.MagicSkillUse
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.utils.Location
 */
package ai;

import l2.commons.util.Rnd;
import l2.gameserver.ai.DefaultAI;
import l2.gameserver.model.Creature;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.MagicSkillUse;
import l2.gameserver.scripts.Functions;
import l2.gameserver.utils.Location;

public class Taurin
extends DefaultAI {
    static final Location[] points = new Location[]{new Location(80752, 146400, -3533), new Location(80250, 146988, -3559), new Location(80070, 146942, -3559), new Location(80048, 146705, -3547), new Location(79784, 146561, -3546), new Location(79476, 146800, -3547), new Location(79490, 147480, -3559), new Location(79812, 148310, -3559), new Location(79692, 148564, -3559), new Location(77569, 148495, -3623), new Location(77495, 148191, -3622), new Location(77569, 148495, -3623), new Location(79819, 148740, -3559), new Location(79773, 149110, -3559), new Location(79291, 149523, -3559), new Location(79569, 150214, -3548), new Location(79679, 150717, -3543), new Location(80106, 150630, -3547), new Location(81207, 150276, -3559), new Location(81820, 150666, -3559), new Location(82038, 150589, -3559), new Location(82394, 149943, -3559), new Location(82038, 150589, -3559), new Location(81820, 150666, -3559), new Location(81582, 150590, -3559), new Location(81535, 149653, -3495), new Location(83814, 148630, -3420), new Location(87001, 148637, -3428), new Location(83814, 148630, -3420), new Location(82921, 148467, -3495), new Location(82060, 148070, -3495), new Location(82060, 148070, -3495), new Location(82060, 148070, -3495), new Location(82060, 148070, -3495), new Location(81544, 147514, -3491), new Location(81691, 146578, -3559), new Location(83190, 146687, -3491), new Location(81691, 146578, -3559), new Location(81331, 146915, -3559), new Location(81067, 146925, -3559), new Location(80752, 146400, -3533)};
    private int F = -1;
    private long n = 0L;
    private boolean k = false;

    public Taurin(NpcInstance npcInstance) {
        super(npcInstance);
        this.AI_TASK_ATTACK_DELAY = 250L;
    }

    public boolean isGlobalAI() {
        return true;
    }

    protected boolean thinkActive() {
        NpcInstance npcInstance = this.getActor();
        if (npcInstance.isDead()) {
            return true;
        }
        if (this._def_think) {
            this.doTask();
            return true;
        }
        if (System.currentTimeMillis() > this.n && (this.F > -1 || Rnd.chance((int)5))) {
            if (!this.k) {
                switch (this.F) {
                    case 4: {
                        this.n = System.currentTimeMillis() + 10000L;
                        this.k = true;
                        return true;
                    }
                    case 10: {
                        this.n = System.currentTimeMillis() + 10000L;
                        this.k = true;
                        return true;
                    }
                    case 14: {
                        this.n = System.currentTimeMillis() + 10000L;
                        this.k = true;
                        return true;
                    }
                    case 16: {
                        this.n = System.currentTimeMillis() + 10000L;
                        this.k = true;
                        return true;
                    }
                    case 21: {
                        this.n = System.currentTimeMillis() + 10000L;
                        this.k = true;
                        return true;
                    }
                    case 27: {
                        this.n = System.currentTimeMillis() + 10000L;
                        this.k = true;
                        return true;
                    }
                    case 30: {
                        this.n = System.currentTimeMillis() + 10000L;
                        Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"taurin.1", (Object[])new Object[0]);
                        this.k = true;
                        return true;
                    }
                    case 31: {
                        this.n = System.currentTimeMillis() + 15000L;
                        Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"taurin.2", (Object[])new Object[0]);
                        this.k = true;
                        return true;
                    }
                    case 32: {
                        this.n = System.currentTimeMillis() + 15000L;
                        Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"taurin.3", (Object[])new Object[0]);
                        this.k = true;
                        return true;
                    }
                    case 33: {
                        npcInstance.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)npcInstance, (Creature)npcInstance, 2025, 1, 500, 0L)});
                        this.n = System.currentTimeMillis() + 1000L;
                        this.k = true;
                        return true;
                    }
                    case 35: {
                        this.n = System.currentTimeMillis() + 10000L;
                        this.k = true;
                        return true;
                    }
                    case 37: {
                        this.n = System.currentTimeMillis() + 30000000L;
                        this.k = true;
                        return true;
                    }
                }
            }
            this.n = 0L;
            this.k = false;
            ++this.F;
            if (this.F >= points.length) {
                this.F = 0;
            }
            this.addTaskMove(points[this.F], true);
            this.doTask();
            return true;
        }
        return this.randomAnimation();
    }

    protected void onEvtAttacked(Creature creature, int n) {
    }

    protected void onEvtAggression(Creature creature, int n) {
    }
}
