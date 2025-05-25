/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.ai.DefaultAI
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.utils.Location
 */
package ai;

import l2.commons.util.Rnd;
import l2.gameserver.ai.DefaultAI;
import l2.gameserver.model.Creature;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.utils.Location;

public class DaimonTheWhiteEyed
extends DefaultAI {
    static final Location[] points = new Location[]{new Location(191276, -49556, -2960), new Location(193537, -47182, -2984), new Location(194317, -43736, -2872), new Location(193336, -42510, -2888), new Location(194633, -40843, -2872), new Location(194498, -39516, -2912), new Location(191985, -35868, -2904), new Location(190083, -35015, -2912), new Location(187815, -36733, -3146), new Location(186256, -35136, -3072), new Location(184477, -36749, -3080), new Location(180834, -37288, -3104), new Location(179653, -38946, -3176), new Location(179854, -42412, -3248), new Location(177627, -43341, -3336), new Location(177842, -45723, -3456), new Location(180459, -47145, -3256), new Location(175858, -51288, -3496), new Location(173028, -49337, -3520), new Location(171936, -46364, -3472), new Location(173074, -44264, -3488), new Location(172575, -42937, -3464), new Location(170964, -41753, -3464), new Location(170428, -39132, -3432)};
    private int F = -1;
    private long n = 0L;
    private boolean k = false;

    public DaimonTheWhiteEyed(NpcInstance npcInstance) {
        super(npcInstance);
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
            if (!this.k && this.F == 23) {
                this.n = System.currentTimeMillis() + 5000L;
                this.k = true;
                return true;
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
