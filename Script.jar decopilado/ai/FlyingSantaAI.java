/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.ai.DefaultAI
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.items.ItemInstance
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.templates.StatsSet
 *  l2.gameserver.utils.ItemFunctions
 *  l2.gameserver.utils.Location
 *  org.apache.commons.lang3.ArrayUtils
 */
package ai;

import java.lang.ref.WeakReference;
import java.util.concurrent.ScheduledFuture;
import l2.commons.util.Rnd;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.ai.DefaultAI;
import l2.gameserver.model.Creature;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.scripts.Functions;
import l2.gameserver.templates.StatsSet;
import l2.gameserver.utils.ItemFunctions;
import l2.gameserver.utils.Location;
import org.apache.commons.lang3.ArrayUtils;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class FlyingSantaAI
extends DefaultAI {
    private static final long p = 300000L;
    private static final String[] f = new String[]{"FlyingSantaAI.hoho", "FlyingSantaAI.hnw", "FlyingSantaAI.gift"};
    private final int[] b;
    private final int[] c;
    private final int K;
    private final int L;
    private final int M;
    private static final Location[][] a = new Location[][]{{new Location(82632, 149128, -3472), new Location(81051, 149288, -3472), new Location(81051, 148062, -3472), new Location(82904, 148056, -3472)}, {new Location(148408, 27928, -2272), new Location(146504, 27928, -2272)}, {new Location(146400, -54903, -2807), new Location(147694, -56555, -2807), new Location(148613, -55572, -2807)}, {new Location(16018, 142978, -2696), new Location(16108, 143190, -2768), new Location(16832, 144678, -3008), new Location(18041, 145939, -3120)}};
    private int N;
    private int O;
    private ScheduledFuture<?> a;

    public FlyingSantaAI(NpcInstance npcInstance) {
        super(npcInstance);
        StatsSet statsSet = npcInstance.getTemplate().getAIParams();
        this.b = statsSet.getIntegerArray((Object)"SantaItemDropIds", ArrayUtils.EMPTY_INT_ARRAY);
        this.c = statsSet.getIntegerArray((Object)"SantaItemDropCount", ArrayUtils.EMPTY_INT_ARRAY);
        this.K = statsSet.getInteger((Object)"SantaRandomPhraseChance", 50);
        this.L = statsSet.getInteger((Object)"SantaItemDropChance", 50);
        this.M = statsSet.getInteger((Object)"SantaActorHeight", 100);
    }

    private static int a(Location location) {
        int n = -1;
        double d = Double.MAX_VALUE;
        for (int i = 0; i < a.length; ++i) {
            for (int j = 0; j < a[i].length; ++j) {
                double d2 = location.distance(a[i][j]);
                if (!(d2 < d)) continue;
                d = d2;
                n = i;
            }
        }
        return n != -1 ? n : 0;
    }

    protected void onEvtSpawn() {
        NpcInstance npcInstance = this.getActor();
        if (npcInstance == null) {
            return;
        }
        npcInstance.setCollisionHeight((double)this.M);
        npcInstance.setWalking();
        this.N = FlyingSantaAI.a(npcInstance.getSpawnedLoc());
        this.O = 0;
        this.addTaskMove(a[this.N][this.O], false);
        this.a = ThreadPoolManager.getInstance().schedule((Runnable)new UnspawnTask(npcInstance), 300000L);
        this.doTask();
    }

    private boolean a() {
        NpcInstance npcInstance = this.getActor();
        if (npcInstance == null) {
            return false;
        }
        if (this.N < a.length) {
            ++this.O;
            if (this.O < a[this.N].length) {
                if (Rnd.chance((int)this.K)) {
                    Functions.npcShoutCustomMessage((NpcInstance)npcInstance, (String)f[Rnd.get((int)f.length)], (Object[])new Object[0]);
                }
                if (Rnd.chance((int)this.L)) {
                    int n = Rnd.get((int)this.b.length);
                    ItemInstance itemInstance = ItemFunctions.createItem((int)this.b[n]);
                    itemInstance.setCount((long)this.c[n]);
                    itemInstance.dropToTheGround((Creature)npcInstance, Location.coordsRandomize((Location)npcInstance.getLoc(), (int)10, (int)50));
                }
                Location location = a[this.N][this.O];
                this.addTaskMove(location, false);
                return this.doTask();
            }
            if (this.a != null) {
                this.a.cancel(true);
                this.a = null;
            }
            npcInstance.decayMe();
            npcInstance.deleteMe();
        }
        return false;
    }

    protected boolean thinkActive() {
        NpcInstance npcInstance = this.getActor();
        if (npcInstance == null || npcInstance.isDead()) {
            return true;
        }
        if (this._def_think) {
            this.doTask();
            return true;
        }
        return this.a();
    }

    public boolean isGlobalAI() {
        return true;
    }

    protected void onEvtArrived() {
        super.onEvtArrived();
        this.a();
    }

    protected void onEvtAttacked(Creature creature, int n) {
    }

    protected void onEvtAggression(Creature creature, int n) {
    }

    private class UnspawnTask
    implements Runnable {
        private WeakReference<NpcInstance> a;

        public UnspawnTask(NpcInstance npcInstance) {
            this.a = new WeakReference<NpcInstance>(npcInstance);
        }

        @Override
        public void run() {
            if (this.a != null && this.a.get() != null) {
                ((NpcInstance)this.a.get()).decayMe();
                ((NpcInstance)this.a.get()).deleteMe();
            }
        }
    }
}
