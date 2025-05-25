/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.ai.CharacterAI
 *  l2.gameserver.instancemanager.sepulchers.spawn.SepulcherParser
 *  l2.gameserver.instancemanager.sepulchers.spawn.SepulcherSpawnDefine
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.instances.SepulcherVictimInstance
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.utils.Location
 */
package ai;

import java.util.concurrent.ScheduledFuture;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.ai.CharacterAI;
import l2.gameserver.instancemanager.sepulchers.spawn.SepulcherParser;
import l2.gameserver.instancemanager.sepulchers.spawn.SepulcherSpawnDefine;
import l2.gameserver.model.Creature;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.instances.SepulcherVictimInstance;
import l2.gameserver.scripts.Functions;
import l2.gameserver.utils.Location;

public class SepulcherVictim
extends CharacterAI {
    private static final int al = 31455;
    private final SepulcherVictimInstance a;
    private ScheduledFuture b;
    private ScheduledFuture c;
    private volatile boolean t = false;

    public SepulcherVictim(Creature creature) {
        super(creature);
        this.a = (SepulcherVictimInstance)creature;
    }

    protected void onEvtSpawn() {
        super.onEvtSpawn();
        this.a.setRunning();
        this.b = ThreadPoolManager.getInstance().scheduleAtFixedRate(() -> {
            Functions.npcSayCustomMessage((NpcInstance)this.a, (String)"ROYAL_RUSH_10484_HELP_ME", (Object[])new Object[0]);
            this.a.moveToLocation(Location.findPointToStay((Location)this.a.getSpawnedLoc(), (int)500, (int)this.a.getGeoIndex()), 50, false);
        }, 500L, 5000L);
        this.c = ThreadPoolManager.getInstance().schedule(() -> {
            this.t = true;
            this.a(this.b);
            Functions.npcSayCustomMessage((NpcInstance)this.a, (String)"ROYAL_RUSH_503_THX_FOR_SAVING_ME", (Object[])new Object[0]);
            this.a.getSepulcherSpawnDefine().getHandler().spawnCustomSingle(31455, this.a.getLoc());
        }, 300000L);
    }

    public void onEvtDeSpawn() {
        super.onEvtDeSpawn();
        this.a(this.b);
        this.a(this.c);
    }

    private void a(ScheduledFuture scheduledFuture) {
        if (scheduledFuture != null && !scheduledFuture.isDone()) {
            scheduledFuture.cancel(false);
        }
    }

    protected void onEvtDead(Creature creature) {
        super.onEvtDead(creature);
        if (!this.t) {
            SepulcherSpawnDefine sepulcherSpawnDefine = this.a.getSepulcherSpawnDefine();
            int n = (Integer)SepulcherParser.getInstance().getVictimBossData().get(sepulcherSpawnDefine.getHandler().getName());
            sepulcherSpawnDefine.getHandler().spawnCustomSingle(n, this.a.getLoc());
        }
    }
}
