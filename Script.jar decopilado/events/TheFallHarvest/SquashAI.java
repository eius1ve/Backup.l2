/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.commons.util.Rnd
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.ai.CharacterAI
 *  l2.gameserver.ai.Fighter
 *  l2.gameserver.data.xml.holder.NpcHolder
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.SimpleSpawner
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.network.l2.s2c.MagicSkillUse
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.utils.Log
 */
package events.TheFallHarvest;

import java.util.concurrent.ScheduledFuture;
import l2.commons.threading.RunnableImpl;
import l2.commons.util.Rnd;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.ai.CharacterAI;
import l2.gameserver.ai.Fighter;
import l2.gameserver.data.xml.holder.NpcHolder;
import l2.gameserver.model.Creature;
import l2.gameserver.model.SimpleSpawner;
import l2.gameserver.model.Skill;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.MagicSkillUse;
import l2.gameserver.scripts.Functions;
import l2.gameserver.utils.Log;
import npc.model.SquashInstance;

public class SquashAI
extends Fighter {
    public static final int Young_Squash = 12774;
    public static final int High_Quality_Squash = 12775;
    public static final int Low_Quality_Squash = 12776;
    public static final int Large_Young_Squash = 12777;
    public static final int High_Quality_Large_Squash = 12778;
    public static final int Low_Quality_Large_Squash = 12779;
    public static final int King_Squash = 13016;
    public static final int Emperor_Squash = 13017;
    public static final int Squash_Level_up = 4513;
    public static final int Squash_Poisoned = 4514;
    private static final String[] s = new String[]{"scripts.events.TheFallHarvest.SquashAI.textOnSpawn.0", "scripts.events.TheFallHarvest.SquashAI.textOnSpawn.1", "scripts.events.TheFallHarvest.SquashAI.textOnSpawn.2"};
    private static final String[] t = new String[]{"scripts.events.TheFallHarvest.SquashAI.textOnAttack.0", "scripts.events.TheFallHarvest.SquashAI.textOnAttack.1", "scripts.events.TheFallHarvest.SquashAI.textOnAttack.2", "scripts.events.TheFallHarvest.SquashAI.textOnAttack.3", "scripts.events.TheFallHarvest.SquashAI.textOnAttack.4", "scripts.events.TheFallHarvest.SquashAI.textOnAttack.5", "scripts.events.TheFallHarvest.SquashAI.textOnAttack.6", "scripts.events.TheFallHarvest.SquashAI.textOnAttack.7", "scripts.events.TheFallHarvest.SquashAI.textOnAttack.8"};
    private static final String[] u = new String[]{"scripts.events.TheFallHarvest.SquashAI.textTooFast.0", "scripts.events.TheFallHarvest.SquashAI.textTooFast.1", "scripts.events.TheFallHarvest.SquashAI.textTooFast.2", "scripts.events.TheFallHarvest.SquashAI.textTooFast.3", "scripts.events.TheFallHarvest.SquashAI.textTooFast.4", "scripts.events.TheFallHarvest.SquashAI.textTooFast.5", "scripts.events.TheFallHarvest.SquashAI.textTooFast.6", "scripts.events.TheFallHarvest.SquashAI.textTooFast.7"};
    private static final String[] v = new String[]{"scripts.events.TheFallHarvest.SquashAI.textSuccess0.0", "scripts.events.TheFallHarvest.SquashAI.textSuccess0.1", "scripts.events.TheFallHarvest.SquashAI.textSuccess0.2", "scripts.events.TheFallHarvest.SquashAI.textSuccess0.3", "scripts.events.TheFallHarvest.SquashAI.textSuccess0.4"};
    private static final String[] w = new String[]{"scripts.events.TheFallHarvest.SquashAI.textFail0.0", "scripts.events.TheFallHarvest.SquashAI.textFail0.1", "scripts.events.TheFallHarvest.SquashAI.textFail0.2"};
    private static final String[] x = new String[]{"scripts.events.TheFallHarvest.SquashAI.textSuccess1.0", "scripts.events.TheFallHarvest.SquashAI.textSuccess1.1", "scripts.events.TheFallHarvest.SquashAI.textSuccess1.2", "scripts.events.TheFallHarvest.SquashAI.textSuccess1.3"};
    private static final String[] y = new String[]{"scripts.events.TheFallHarvest.SquashAI.textFail1.0", "scripts.events.TheFallHarvest.SquashAI.textFail1.1", "scripts.events.TheFallHarvest.SquashAI.textFail1.2", "scripts.events.TheFallHarvest.SquashAI.textFail1.3"};
    private static final String[] z = new String[]{"scripts.events.TheFallHarvest.SquashAI.textSuccess2.0", "scripts.events.TheFallHarvest.SquashAI.textSuccess2.1", "scripts.events.TheFallHarvest.SquashAI.textSuccess2.2"};
    private static final String[] A = new String[]{"scripts.events.TheFallHarvest.SquashAI.textFail2.0", "scripts.events.TheFallHarvest.SquashAI.textFail2.1", "scripts.events.TheFallHarvest.SquashAI.textFail2.2", "scripts.events.TheFallHarvest.SquashAI.textFail2.3"};
    private static final String[] B = new String[]{"scripts.events.TheFallHarvest.SquashAI.textSuccess3.0", "scripts.events.TheFallHarvest.SquashAI.textSuccess3.1", "scripts.events.TheFallHarvest.SquashAI.textSuccess3.2"};
    private static final String[] C = new String[]{"scripts.events.TheFallHarvest.SquashAI.textFail3.0", "scripts.events.TheFallHarvest.SquashAI.textFail3.1"};
    private static final String[] D = new String[]{"scripts.events.TheFallHarvest.SquashAI.textSuccess4.0", "scripts.events.TheFallHarvest.SquashAI.textSuccess4.1"};
    private static final String[] E = new String[]{"scripts.events.TheFallHarvest.SquashAI.textFail4.0", "scripts.events.TheFallHarvest.SquashAI.textFail4.1"};
    private int _npcId = this.getActor().getNpcId();
    private int bQ;
    private int bR;
    private long ab;
    private long ac;
    private ScheduledFuture<?> F;
    private static int bS = 3000;

    public SquashAI(NpcInstance npcInstance) {
        super(npcInstance);
        Functions.npcSayInRangeCustomMessage((NpcInstance)this.getActor(), (int)128, (String)s[Rnd.get((int)s.length)], (Object[])new Object[0]);
        this.ac = System.currentTimeMillis() + 120000L;
    }

    protected boolean thinkActive() {
        if (System.currentTimeMillis() > this.ac) {
            SquashInstance squashInstance;
            this.ac = Long.MAX_VALUE;
            if (this.F != null) {
                this.F.cancel(false);
                this.F = null;
            }
            if ((squashInstance = this.getActor()) != null) {
                squashInstance.deleteMe();
            }
        }
        return false;
    }

    protected void onEvtSeeSpell(Skill skill, Creature creature) {
        SquashInstance squashInstance = this.getActor();
        if (squashInstance == null || skill.getId() != 2005) {
            return;
        }
        switch (this.bR) {
            case 0: {
                ++this.bR;
                this.ab = System.currentTimeMillis();
                if (Rnd.chance((int)50)) {
                    ++this.bQ;
                    Functions.npcSayInRangeCustomMessage((NpcInstance)squashInstance, (int)128, (String)v[Rnd.get((int)v.length)], (Object[])new Object[0]);
                    squashInstance.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)squashInstance, (Creature)squashInstance, 4513, 1, bS, 0L)});
                    break;
                }
                Functions.npcSayInRangeCustomMessage((NpcInstance)squashInstance, (int)128, (String)w[Rnd.get((int)w.length)], (Object[])new Object[0]);
                squashInstance.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)squashInstance, (Creature)squashInstance, 4514, 1, bS, 0L)});
                break;
            }
            case 1: {
                if (System.currentTimeMillis() - this.ab < (long)bS) {
                    Functions.npcSayInRangeCustomMessage((NpcInstance)squashInstance, (int)128, (String)u[Rnd.get((int)u.length)], (Object[])new Object[0]);
                    return;
                }
                ++this.bR;
                this.ab = System.currentTimeMillis();
                if (Rnd.chance((int)50)) {
                    ++this.bQ;
                    Functions.npcSayInRangeCustomMessage((NpcInstance)squashInstance, (int)128, (String)x[Rnd.get((int)x.length)], (Object[])new Object[0]);
                    squashInstance.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)squashInstance, (Creature)squashInstance, 4513, 1, bS, 0L)});
                    break;
                }
                Functions.npcSayInRangeCustomMessage((NpcInstance)squashInstance, (int)128, (String)y[Rnd.get((int)y.length)], (Object[])new Object[0]);
                squashInstance.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)squashInstance, (Creature)squashInstance, 4514, 1, bS, 0L)});
                break;
            }
            case 2: {
                if (System.currentTimeMillis() - this.ab < (long)bS) {
                    Functions.npcSayInRangeCustomMessage((NpcInstance)squashInstance, (int)128, (String)u[Rnd.get((int)u.length)], (Object[])new Object[0]);
                    return;
                }
                ++this.bR;
                this.ab = System.currentTimeMillis();
                if (Rnd.chance((int)50)) {
                    ++this.bQ;
                    Functions.npcSayInRangeCustomMessage((NpcInstance)squashInstance, (int)128, (String)z[Rnd.get((int)z.length)], (Object[])new Object[0]);
                    squashInstance.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)squashInstance, (Creature)squashInstance, 4513, 1, bS, 0L)});
                    break;
                }
                Functions.npcSayInRangeCustomMessage((NpcInstance)squashInstance, (int)128, (String)A[Rnd.get((int)A.length)], (Object[])new Object[0]);
                squashInstance.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)squashInstance, (Creature)squashInstance, 4514, 1, bS, 0L)});
                break;
            }
            case 3: {
                if (System.currentTimeMillis() - this.ab < (long)bS) {
                    Functions.npcSayInRangeCustomMessage((NpcInstance)squashInstance, (int)128, (String)u[Rnd.get((int)u.length)], (Object[])new Object[0]);
                    return;
                }
                ++this.bR;
                this.ab = System.currentTimeMillis();
                if (Rnd.chance((int)50)) {
                    ++this.bQ;
                    Functions.npcSayInRangeCustomMessage((NpcInstance)squashInstance, (int)128, (String)B[Rnd.get((int)B.length)], (Object[])new Object[0]);
                    squashInstance.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)squashInstance, (Creature)squashInstance, 4513, 1, bS, 0L)});
                    break;
                }
                Functions.npcSayInRangeCustomMessage((NpcInstance)squashInstance, (int)128, (String)C[Rnd.get((int)C.length)], (Object[])new Object[0]);
                squashInstance.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)squashInstance, (Creature)squashInstance, 4514, 1, bS, 0L)});
                break;
            }
            case 4: {
                if (System.currentTimeMillis() - this.ab < (long)bS) {
                    Functions.npcSayInRangeCustomMessage((NpcInstance)squashInstance, (int)128, (String)u[Rnd.get((int)u.length)], (Object[])new Object[0]);
                    return;
                }
                ++this.bR;
                this.ab = System.currentTimeMillis();
                if (Rnd.chance((int)50)) {
                    ++this.bQ;
                    Functions.npcSayInRangeCustomMessage((NpcInstance)squashInstance, (int)128, (String)D[Rnd.get((int)D.length)], (Object[])new Object[0]);
                    squashInstance.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)squashInstance, (Creature)squashInstance, 4513, 1, bS, 0L)});
                } else {
                    Functions.npcSayInRangeCustomMessage((NpcInstance)squashInstance, (int)128, (String)E[Rnd.get((int)E.length)], (Object[])new Object[0]);
                    squashInstance.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)squashInstance, (Creature)squashInstance, 4514, 1, bS, 0L)});
                }
                if (this._npcId == 12774) {
                    this._npcId = this.bQ < 3 ? 12776 : (this.bQ == 5 ? 13016 : 12775);
                } else if (this._npcId == 12777) {
                    this._npcId = this.bQ < 3 ? 12779 : (this.bQ == 5 ? 13017 : 12778);
                }
                this.F = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new PolimorphTask()), (long)bS);
            }
        }
    }

    protected void onEvtAttacked(Creature creature, int n) {
        SquashInstance squashInstance = this.getActor();
        if (squashInstance != null && Rnd.chance((int)5)) {
            Functions.npcSayInRangeCustomMessage((NpcInstance)squashInstance, (int)128, (String)t[Rnd.get((int)t.length)], (Object[])new Object[0]);
        }
    }

    protected void onEvtDead(Creature creature) {
        this.bR = -1;
        SquashInstance squashInstance = this.getActor();
        if (squashInstance == null) {
            return;
        }
        switch (this._npcId) {
            case 12775: 
            case 12776: 
            case 12778: 
            case 12779: 
            case 13016: 
            case 13017: {
                Functions.npcSayInRangeCustomMessage((NpcInstance)squashInstance, (int)128, (String)"scripts.events.TheFallHarvest.SquashAI.textOnDead.0", (Object[])new Object[0]);
                Functions.npcSayInRangeCustomMessage((NpcInstance)squashInstance, (int)128, (String)"scripts.events.TheFallHarvest.SquashAI.textOnDead.1", (Object[])new Object[0]);
                break;
            }
            default: {
                Functions.npcSayInRangeCustomMessage((NpcInstance)squashInstance, (int)128, (String)"scripts.events.TheFallHarvest.SquashAI.textOnDeadDefault.0", (Object[])new Object[0]);
                Functions.npcSayInRangeCustomMessage((NpcInstance)squashInstance, (int)128, (String)"scripts.events.TheFallHarvest.SquashAI.textOnDeadDefault.1", (Object[])new Object[0]);
            }
        }
        super.onEvtDead((Creature)squashInstance);
        if (this.F != null) {
            this.F.cancel(false);
            this.F = null;
            Log.add((String)("TheFallHarvest :: Player " + squashInstance.getSpawner().getName() + " tried to use cheat (SquashAI clone): killed " + squashInstance + " after polymorfing started"), (String)"illegal-actions");
        }
    }

    protected boolean randomAnimation() {
        return false;
    }

    protected boolean randomWalk() {
        return false;
    }

    public SquashInstance getActor() {
        return (SquashInstance)super.getActor();
    }

    public class PolimorphTask
    extends RunnableImpl {
        public void runImpl() throws Exception {
            SquashInstance squashInstance = SquashAI.this.getActor();
            if (squashInstance == null) {
                return;
            }
            SimpleSpawner simpleSpawner = null;
            try {
                simpleSpawner = new SimpleSpawner(NpcHolder.getInstance().getTemplate(SquashAI.this._npcId));
                simpleSpawner.setLoc(squashInstance.getLoc());
                NpcInstance npcInstance = simpleSpawner.doSpawn(true);
                npcInstance.setAI((CharacterAI)new SquashAI(npcInstance));
                ((SquashInstance)npcInstance).setSpawner(squashInstance.getSpawner());
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
            SquashAI.this.ac = Long.MAX_VALUE;
            squashInstance.deleteMe();
        }
    }
}
