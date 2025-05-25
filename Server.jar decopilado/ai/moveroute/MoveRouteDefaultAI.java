/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.collections.CollectionUtils
 *  l2.commons.util.Rnd
 *  l2.gameserver.Config
 *  l2.gameserver.ai.CtrlIntention
 *  l2.gameserver.ai.DefaultAI
 *  l2.gameserver.geodata.GeoEngine
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.MinionList
 *  l2.gameserver.model.World
 *  l2.gameserver.model.instances.MinionInstance
 *  l2.gameserver.model.instances.MonsterInstance
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.network.l2.components.ChatType
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.network.l2.s2c.SocialAction
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.templates.moveroute.MoveNode
 *  l2.gameserver.templates.moveroute.MoveRoute
 *  l2.gameserver.utils.Location
 */
package ai.moveroute;

import java.util.Comparator;
import java.util.List;
import l2.commons.collections.CollectionUtils;
import l2.commons.util.Rnd;
import l2.gameserver.Config;
import l2.gameserver.ai.CtrlIntention;
import l2.gameserver.ai.DefaultAI;
import l2.gameserver.geodata.GeoEngine;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.MinionList;
import l2.gameserver.model.World;
import l2.gameserver.model.instances.MinionInstance;
import l2.gameserver.model.instances.MonsterInstance;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.components.ChatType;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.SocialAction;
import l2.gameserver.scripts.Functions;
import l2.gameserver.templates.moveroute.MoveNode;
import l2.gameserver.templates.moveroute.MoveRoute;
import l2.gameserver.utils.Location;
import parsers.MoveRouteHolder;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class MoveRouteDefaultAI
extends DefaultAI {
    private MoveRoute a;
    private MoveNode a;
    private boolean M;
    private boolean N = false;

    public MoveRouteDefaultAI(NpcInstance npcInstance) {
        super(npcInstance);
        String string = npcInstance.getParameter("moveroute", null);
        this.a = string == null ? null : MoveRouteHolder.getInstance().getRoute(string);
    }

    protected void onEvtSpawn() {
        super.onEvtSpawn();
        if (this.a == null) {
            return;
        }
        this.M = false;
        if (this.a.isRunning()) {
            this.getActor().setRunning();
        } else {
            this.getActor().setWalking();
        }
    }

    protected Location getPursueBaseLoc() {
        return this.a != null ? this.a : super.getPursueBaseLoc();
    }

    protected boolean thinkActive() {
        MonsterInstance monsterInstance;
        if (this.a == null) {
            return super.thinkActive();
        }
        NpcInstance npcInstance = this.getActor();
        if (npcInstance.isActionsDisabled()) {
            return true;
        }
        if (this._randomAnimationEnd > System.currentTimeMillis()) {
            return true;
        }
        if (this._def_think) {
            if (this.doTask()) {
                this.clearTasks();
            }
            return true;
        }
        long l = System.currentTimeMillis();
        if (l - this._checkAggroTimestamp > (long)Config.AGGRO_CHECK_INTERVAL) {
            this._checkAggroTimestamp = l;
            boolean bl = Rnd.chance((int)npcInstance.getParameter("SelfAggressive", npcInstance.isAggressive() ? 100 : 0));
            if (!npcInstance.getAggroList().isEmpty() || bl) {
                List list = World.getAroundCharacters((GameObject)npcInstance);
                CollectionUtils.eqSort((List)list, (Comparator)this._nearestTargetComparator);
                for (Creature creature : list) {
                    if (!bl && npcInstance.getAggroList().get(creature) == null || !this.checkAggression(creature)) continue;
                    npcInstance.getAggroList().addDamageHate(creature, 0, 2);
                    if (creature.isSummon()) {
                        npcInstance.getAggroList().addDamageHate((Creature)creature.getPlayer(), 0, 1);
                    }
                    this.startRunningTask(this.AI_TASK_ATTACK_DELAY);
                    this.setIntention(CtrlIntention.AI_INTENTION_ATTACK, creature);
                    return true;
                }
            }
        }
        if (npcInstance.isMinion() && (monsterInstance = ((MinionInstance)npcInstance).getLeader()) != null) {
            double d = npcInstance.getDistance(monsterInstance.getX(), monsterInstance.getY());
            if (d > 1000.0) {
                npcInstance.teleToLocation(monsterInstance.getMinionPosition());
            } else if (d > 200.0) {
                this.addTaskMove(monsterInstance.getMinionPosition(), false);
            }
            return true;
        }
        if (this.randomAnimation()) {
            return true;
        }
        if (this.randomWalk()) {
            return true;
        }
        if (!npcInstance.isMoving() && !npcInstance.isFollowing()) {
            Location location = npcInstance.getLoc();
            int n = this.e();
            MoveNode moveNode = (MoveNode)this.a.getNodes().get(n);
            double d = moveNode.distance3D(location);
            if (d > Math.max(64.0, 4.0 * npcInstance.getCollisionRadius())) {
                this.a = moveNode;
                this.returnHome(true, !GeoEngine.canMoveToCoord((int)location.getX(), (int)location.getY(), (int)location.getZ(), (int)moveNode.getX(), (int)moveNode.getY(), (int)moveNode.getZ(), (int)npcInstance.getGeoIndex()));
            } else {
                MoveNode moveNode2;
                MoveNode moveNode3 = moveNode;
                int n2 = n;
                if (moveNode3.getSocialId() > 0) {
                    npcInstance.broadcastPacketToOthers(new L2GameServerPacket[]{new SocialAction(npcInstance.getObjectId(), moveNode3.getSocialId())});
                }
                if (!this.N) {
                    if (moveNode3.getNpcMsgAddress() != null) {
                        Functions.npcSayCustomMessage((NpcInstance)npcInstance, (ChatType)moveNode3.getChatType(), (String)moveNode3.getNpcMsgAddress(), (Object[])new Object[0]);
                    }
                    if (moveNode3.getDelay() > 0L) {
                        this.N = true;
                        this.setIsInRandomAnimation(moveNode3.getDelay());
                        return true;
                    }
                }
                this.N = false;
                if (this.a.isRunning()) {
                    npcInstance.setRunning();
                } else {
                    npcInstance.setWalking();
                }
                int n3 = this.a(n2);
                if (n3 < 0) {
                    npcInstance.decayOrDelete();
                    return false;
                }
                this.a = moveNode2 = (MoveNode)this.a.getNodes().get(n3);
                this.addTaskMove((Location)moveNode2, true);
                if (npcInstance.hasMinions()) {
                    this.i();
                }
                return this.doTask();
            }
        }
        return false;
    }

    private int a(int n) {
        switch (this.a.getType()) {
            case LOOP: {
                return n + 1 < this.a.getNodes().size() ? n + 1 : 0;
            }
            case CIRCLE: {
                if (!this.M) {
                    if (n + 1 < this.a.getNodes().size()) {
                        return n + 1;
                    }
                    this.M = true;
                    return n - 1;
                }
                if (n - 1 > 0) {
                    return n - 1;
                }
                this.M = false;
                return n + 1;
            }
            case ONCE: {
                return n + 1 < this.a.getNodes().size() ? n + 1 : -1;
            }
        }
        return -1;
    }

    private void i() {
        NpcInstance npcInstance = this.getActor();
        MinionList minionList = npcInstance.getMinionList();
        if (minionList.hasAliveMinions()) {
            for (NpcInstance npcInstance2 : minionList.getAliveMinions()) {
                if (npcInstance2.isInCombat() || npcInstance2.isAfraid()) continue;
                if (this.a.isRunning()) {
                    npcInstance2.setRunning();
                } else {
                    npcInstance2.setWalking();
                }
                npcInstance2.moveToRelative((GameObject)this.getActor(), 400, 500, true);
            }
        }
    }

    private int e() {
        if (this.a == null) {
            return -1;
        }
        NpcInstance npcInstance = this.getActor();
        List list = this.a.getNodes();
        int n = -1;
        long l = Long.MAX_VALUE;
        for (int i = 0; i < list.size(); ++i) {
            MoveNode moveNode = (MoveNode)list.get(i);
            long l2 = npcInstance.getXYZDeltaSq((Location)moveNode);
            if (l2 >= l) continue;
            l = l2;
            n = i;
        }
        return n;
    }

    public boolean isGlobalAI() {
        return false;
    }
}
