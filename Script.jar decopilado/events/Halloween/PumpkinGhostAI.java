/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.ai.DefaultAI
 *  l2.gameserver.idfactory.IdFactory
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.items.ItemInstance
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.network.l2.s2c.MagicSkillLaunched
 *  l2.gameserver.network.l2.s2c.MagicSkillUse
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.tables.SkillTable
 *  l2.gameserver.utils.Location
 */
package events.Halloween;

import java.util.concurrent.atomic.AtomicInteger;
import l2.commons.util.Rnd;
import l2.gameserver.ai.DefaultAI;
import l2.gameserver.idfactory.IdFactory;
import l2.gameserver.model.Creature;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.MagicSkillLaunched;
import l2.gameserver.network.l2.s2c.MagicSkillUse;
import l2.gameserver.scripts.Functions;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.utils.Location;

public class PumpkinGhostAI
extends DefaultAI {
    private Location[] g;
    private int bO;
    private int ai;
    private int[] F;
    private static String[] r = new String[]{"PumpkinGhostAI.SongText1", "PumpkinGhostAI.SongText2", "PumpkinGhostAI.SongText3", "PumpkinGhostAI.SongText4", "PumpkinGhostAI.SongText5", "PumpkinGhostAI.SongText6", "PumpkinGhostAI.SongText7", "PumpkinGhostAI.SongText8", "PumpkinGhostAI.SongText9", "PumpkinGhostAI.SongText10", "PumpkinGhostAI.SongText11", "PumpkinGhostAI.SongText12", "PumpkinGhostAI.SongText13", "PumpkinGhostAI.SongText14", "PumpkinGhostAI.SongText15", "PumpkinGhostAI.SongText16", "PumpkinGhostAI.SongText17", "PumpkinGhostAI.SongText18", "PumpkinGhostAI.SongText19", "PumpkinGhostAI.SongText20", "PumpkinGhostAI.SongText21", "PumpkinGhostAI.SongText22", "PumpkinGhostAI.SongText23", "PumpkinGhostAI.SongText24", "PumpkinGhostAI.SongText25", "PumpkinGhostAI.SongText26", "PumpkinGhostAI.SongText27", "PumpkinGhostAI.SongText28", "PumpkinGhostAI.SongText29", "PumpkinGhostAI.SongText30", "PumpkinGhostAI.SongText31", "PumpkinGhostAI.SongText32", "PumpkinGhostAI.SongText33", "PumpkinGhostAI.SongText34", "PumpkinGhostAI.SongText35", "PumpkinGhostAI.SongText36", "PumpkinGhostAI.SongText37", "PumpkinGhostAI.SongText38", "PumpkinGhostAI.SongText39", "PumpkinGhostAI.SongText40", "PumpkinGhostAI.SongText41", "PumpkinGhostAI.SongText42", "PumpkinGhostAI.SongText43", "PumpkinGhostAI.SongText44", "PumpkinGhostAI.SongText45", "PumpkinGhostAI.SongText46", "PumpkinGhostAI.SongText47", "PumpkinGhostAI.SongText48"};
    private static AtomicInteger b = new AtomicInteger(0);
    private static final int bP = 2024;
    private long aa = System.currentTimeMillis();

    public PumpkinGhostAI(NpcInstance npcInstance, Location[] locationArray, int n, int n2, int[] nArray) {
        super(npcInstance);
        this.g = locationArray;
        this.bO = n + 1;
        if (this.bO >= this.g.length) {
            this.bO = 0;
        }
        this.ai = n2;
        this.F = nArray;
    }

    protected void onEvtSpawn() {
        NpcInstance npcInstance = this.getActor();
        if (npcInstance == null) {
            return;
        }
        npcInstance.setWalking();
        this.addTaskMove(this.g[this.bO], false);
        super.onEvtSpawn();
    }

    protected boolean thinkActive() {
        NpcInstance npcInstance = this.getActor();
        if (npcInstance == null || npcInstance.isDead()) {
            return true;
        }
        if (this._def_think) {
            return this.doTask();
        }
        long l = System.currentTimeMillis();
        if (l - this.aa > 3000L) {
            if (Rnd.chance((int)30)) {
                npcInstance.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)npcInstance, (Creature)npcInstance, 2024, 1, 0, 0L), new MagicSkillLaunched((Creature)npcInstance, 2024, 1, (Creature)npcInstance)});
            } else if (this.bO == 0 && Rnd.chance((int)40)) {
                Functions.npcSayInRangeCustomMessage((NpcInstance)this.getActor(), (int)300, (String)r[b.getAndIncrement() % r.length], (Object[])new Object[0]);
            } else if (Rnd.chance((int)this.ai)) {
                ItemInstance itemInstance = new ItemInstance(IdFactory.getInstance().getNextId(), this.F[Rnd.get((int)this.F.length)]);
                itemInstance.setCount(1L);
                itemInstance.dropToTheGround((Creature)npcInstance, Location.coordsRandomize((Location)npcInstance.getLoc(), (int)10, (int)50));
            }
            if (this.f()) {
                this.addTaskMove(this.g[this.bO], false);
            }
            this.aa = l;
        }
        return true;
    }

    private boolean f() {
        NpcInstance npcInstance = this.getActor();
        if (npcInstance == null || npcInstance.isDead()) {
            return false;
        }
        return npcInstance.getLoc().distance(this.g[this.bO]) > 512.0;
    }

    protected void onEvtArrived() {
        ++this.bO;
        if (this.bO >= this.g.length) {
            this.bO = 0;
        }
        this.addTaskMove(this.g[this.bO], false);
        super.onEvtArrived();
    }

    protected void onEvtAttacked(Creature creature, int n) {
        if (creature != null) {
            SkillTable.getInstance().getInfo(4515, 1).getEffects(creature, creature, false, false);
        }
        this.doTask();
    }

    protected boolean randomAnimation() {
        return false;
    }

    protected boolean randomWalk() {
        return false;
    }

    public boolean isGlobalAI() {
        return true;
    }
}
