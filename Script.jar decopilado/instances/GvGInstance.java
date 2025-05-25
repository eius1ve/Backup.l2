/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.TIntObjectHashMap
 *  l2.commons.lang.reference.HardReference
 *  l2.commons.lang.reference.HardReferences
 *  l2.commons.listener.Listener
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.Config
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.instancemanager.ReflectionManager
 *  l2.gameserver.listener.actor.OnDeathListener
 *  l2.gameserver.listener.actor.player.OnPlayerPartyLeaveListener
 *  l2.gameserver.listener.actor.player.OnTeleportListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Party
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Zone
 *  l2.gameserver.model.entity.Reflection
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage$ScreenMessageAlign
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.network.l2.s2c.Revive
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.utils.Location
 *  org.apache.commons.lang3.mutable.MutableInt
 */
package instances;

import events.GvG.GvG;
import gnu.trove.TIntObjectHashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledFuture;
import l2.commons.lang.reference.HardReference;
import l2.commons.lang.reference.HardReferences;
import l2.commons.listener.Listener;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.listener.actor.OnDeathListener;
import l2.gameserver.listener.actor.player.OnPlayerPartyLeaveListener;
import l2.gameserver.listener.actor.player.OnTeleportListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Party;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.Zone;
import l2.gameserver.model.entity.Reflection;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExShowScreenMessage;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.Revive;
import l2.gameserver.scripts.Functions;
import l2.gameserver.utils.Location;
import org.apache.commons.lang3.mutable.MutableInt;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class GvGInstance
extends Reflection {
    private static final int cQ = 25656;
    private static final int cR = 25655;
    private static final int cS = 20;
    private static final int cT = 100;
    private static final int cU = 5;
    private static final int cV = 3;
    private int cW = 1200;
    private long af = 600000L;
    private boolean active = false;
    private Party a;
    private Party b;
    private List<HardReference<Player>> M;
    private TIntObjectHashMap<MutableInt> a;
    private int cX = 0;
    private int cY = 0;
    private long ag;
    private ScheduledFuture<?> L;
    private ScheduledFuture<?> M = new CopyOnWriteArrayList<HardReference<Player>>();
    private ScheduledFuture<?> N;
    private DeathListener a;
    private TeleportListener a;
    private PlayerPartyLeaveListener a = new PlayerPartyLeaveListener();
    private Zone w;
    private Zone x;
    private Zone y;
    private Zone z;
    private Zone A;
    private Zone B;

    public void setTeam1(Party party) {
        this.a = party;
    }

    public void setTeam2(Party party) {
        this.b = party;
    }

    public void start() {
        this.x = this.getZone("[gvg_battle_zone]");
        this.z = this.getZone("[gvg_1_peace]");
        this.B = this.getZone("[gvg_2_peace]");
        Location[] locationArray = new Location[]{new Location(78568, 92408, -2440, 0), new Location(78664, 92440, -2440, 0), new Location(78760, 92488, -2440, 0), new Location(78872, 92264, -2440, 0), new Location(78696, 92168, -2440, 0), new Location(79624, 89736, -2440, 0), new Location(79576, 89830, -2440, 0), new Location(79534, 89917, -2440, 0), new Location(79743, 90028, -2440, 0)};
        for (int i = 0; i < locationArray.length; ++i) {
            this.addSpawnWithoutRespawn(25656, locationArray[i], 0);
        }
        this.addSpawnWithoutRespawn(35423, new Location(79928, 88328, -2880), 0);
        this.addSpawnWithoutRespawn(35426, new Location(77016, 93080, -2880), 0);
        this.L = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new BossSpawn()), this.af);
        this.M = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new CountingDown()), (long)(this.cW - 1) * 1000L);
        this.N = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new BattleEnd()), (long)(this.cW - 6) * 1000L);
        for (Player player : this.a.getPartyMembers()) {
            this.M.add((HardReference<Player>)player.getRef());
            player.addListener((Listener)this.a);
            player.addListener((Listener)this.a);
            player.addListener((Listener)this.a);
        }
        for (Player player : this.b.getPartyMembers()) {
            this.M.add((HardReference<Player>)player.getRef());
            player.addListener((Listener)this.a);
            player.addListener((Listener)this.a);
            player.addListener((Listener)this.a);
        }
        this.ag = System.currentTimeMillis() + (long)this.cW * 1000L;
        for (Player player : HardReferences.unwrap(this.M)) {
            this.a.put(player.getObjectId(), (Object)new MutableInt());
            player.setCurrentCp((double)player.getMaxCp());
            player.setCurrentHp((double)player.getMaxHp(), false);
            player.setCurrentMp((double)player.getMaxMp());
            player.sendActionFailed();
        }
        this.active = true;
    }

    private void a(L2GameServerPacket l2GameServerPacket) {
        for (Player player : HardReferences.unwrap(this.M)) {
            player.sendPacket((IStaticPacket)l2GameServerPacket);
        }
    }

    private boolean isActive() {
        return this.active;
    }

    private boolean i(Player player) {
        return this.b.containsMember(player);
    }

    private void end() {
        this.active = false;
        this.startCollapseTimer(60000L);
        this.paralyzePlayers();
        ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Finish()), 55000L);
        if (this.L != null) {
            this.L.cancel(false);
            this.L = null;
        }
        if (this.M != null) {
            this.M.cancel(false);
            this.M = null;
        }
        if (this.N != null) {
            this.N.cancel(false);
            this.N = null;
        }
        boolean bl = false;
        bl = this.p() >= this.o();
        this.a(bl ? this.b : this.a);
        GvG.updateWinner(bl ? this.b.getPartyLeader() : this.a.getPartyLeader());
        this.x.setActive(false);
        this.z.setActive(false);
        this.B.setActive(false);
    }

    private void a(Party party) {
        for (Player player : party.getPartyMembers()) {
            String string = new CustomMessage("scripts.event.gvg.youpartywin", player, new Object[0]).toString();
            player.sendMessage(string);
            Functions.addItem((Playable)player, (int)Config.GVG_REWARD_ID, (long)Config.GVG_REWARD_AMOUNT);
        }
    }

    private synchronized void a(int n, int n2, int n3, boolean bl, boolean bl2, Player player) {
        int n4 = (int)((this.ag - System.currentTimeMillis()) / 1000L);
        if (n == 1) {
            if (bl) {
                this.cX -= n3;
                if (this.cX < 0) {
                    this.cX = 0;
                }
                if (bl2) {
                    this.cY += n2;
                }
            } else {
                this.cX += n2;
                if (bl2) {
                    this.cY -= n3;
                    if (this.cY < 0) {
                        this.cY = 0;
                    }
                }
            }
        } else if (n == 2) {
            if (bl) {
                this.cY -= n3;
                if (this.cY < 0) {
                    this.cY = 0;
                }
                if (bl2) {
                    this.cX += n2;
                }
            } else {
                this.cY += n2;
                if (bl2) {
                    this.cX -= n3;
                    if (this.cX < 0) {
                        this.cX = 0;
                    }
                }
            }
        }
    }

    private void q(Player player) {
        MutableInt mutableInt = (MutableInt)this.a.get(player.getObjectId());
        mutableInt.increment();
    }

    public int getPlayerScore(Player player) {
        MutableInt mutableInt = (MutableInt)this.a.get(player.getObjectId());
        return mutableInt.intValue();
    }

    public void paralyzePlayers() {
        for (Player player : HardReferences.unwrap(this.M)) {
            if (player.isDead()) {
                player.setCurrentHp((double)player.getMaxHp(), true);
                player.broadcastPacket(new L2GameServerPacket[]{new Revive((GameObject)player)});
            } else {
                player.setCurrentHp((double)player.getMaxHp(), false);
            }
            player.setCurrentMp((double)player.getMaxMp());
            player.setCurrentCp((double)player.getMaxCp());
            player.getEffectList().stopEffect(1411);
            player.block();
        }
    }

    public void unParalyzePlayers() {
        for (Player player : HardReferences.unwrap(this.M)) {
            player.unblock();
            this.a(player, true);
        }
    }

    private void cleanUp() {
        this.a = null;
        this.b = null;
        this.M.clear();
        this.cX = 0;
        this.cY = 0;
        this.a.clear();
    }

    public void resurrectAtBase(Player player) {
        if (player.isDead()) {
            player.setCurrentHp(0.7 * (double)player.getMaxHp(), true);
            player.broadcastPacket(new L2GameServerPacket[]{new Revive((GameObject)player)});
        }
        Location location = this.a.containsMember(player) ? Location.findPointToStay((Location)GvG.TEAM1_LOC, (int)0, (int)150, (int)this.getGeoIndex()) : Location.findPointToStay((Location)GvG.TEAM2_LOC, (int)0, (int)150, (int)this.getGeoIndex());
        player.teleToLocation(location, (Reflection)this);
    }

    private void a(Player player, boolean bl) {
        this.M.remove(player.getRef());
        player.removeListener((Listener)this.a);
        player.removeListener((Listener)this.a);
        player.removeListener((Listener)this.a);
        player.leaveParty();
        if (!bl) {
            player.teleToLocation(Location.findPointToStay((Location)GvG.RETURN_LOC, (int)0, (int)150, (int)ReflectionManager.DEFAULT.getGeoIndex()), 0);
        }
    }

    private void b(Party party) {
        if (party == this.a) {
            for (Player player : this.a.getPartyMembers()) {
                this.a(player, false);
            }
            var2_2 = this.b.getPartyLeader();
            this.a(2, 200, 0, false, false, (Player)var2_2);
        } else {
            for (Player player : this.b.getPartyMembers()) {
                this.a(player, false);
            }
            var2_2 = this.a.getPartyLeader();
            this.a(1, 200, 0, false, false, (Player)var2_2);
        }
        for (Player player : HardReferences.unwrap(this.M)) {
            player.sendPacket((IStaticPacket)new ExShowScreenMessage(new CustomMessage("scripts.event.gvg.partydispell", player, new Object[0]).toString(), 4000, ExShowScreenMessage.ScreenMessageAlign.MIDDLE_CENTER, true));
        }
    }

    private int o() {
        return this.cX;
    }

    private int p() {
        return this.cY;
    }

    public NpcInstance addSpawnWithoutRespawn(int n, Location location, int n2) {
        NpcInstance npcInstance = super.addSpawnWithoutRespawn(n, location, n2);
        npcInstance.addListener((Listener)this.a);
        return npcInstance;
    }

    private class DeathListener
    implements OnDeathListener {
        private DeathListener() {
        }

        public void onDeath(Creature creature, Creature creature2) {
            if (!GvGInstance.this.isActive()) {
                return;
            }
            if (creature.getReflection() != creature2.getReflection() || creature.getReflection() != GvGInstance.this) {
                return;
            }
            if (creature.isPlayer() && creature2.isPlayable()) {
                if (GvGInstance.this.a.containsMember(creature.getPlayer()) && GvGInstance.this.b.containsMember(creature2.getPlayer())) {
                    GvGInstance.this.q(creature2.getPlayer());
                    GvGInstance.this.a(1, 5, 3, true, true, creature2.getPlayer());
                } else if (GvGInstance.this.b.containsMember(creature.getPlayer()) && GvGInstance.this.a.containsMember(creature2.getPlayer())) {
                    GvGInstance.this.q(creature2.getPlayer());
                    GvGInstance.this.a(2, 5, 3, true, true, creature2.getPlayer());
                }
                GvGInstance.this.resurrectAtBase(creature.getPlayer());
            } else if (creature.isPlayer() && !creature2.isPlayable()) {
                GvGInstance.this.resurrectAtBase(creature.getPlayer());
            } else if (creature.isNpc() && creature2.isPlayable()) {
                if (creature.getNpcId() == 25656) {
                    if (GvGInstance.this.a.containsMember(creature2.getPlayer())) {
                        GvGInstance.this.a(1, 20, 0, false, false, creature2.getPlayer());
                    } else if (GvGInstance.this.b.containsMember(creature2.getPlayer())) {
                        GvGInstance.this.a(2, 20, 0, false, false, creature2.getPlayer());
                    }
                } else if (creature.getNpcId() == 25655) {
                    if (GvGInstance.this.a.containsMember(creature2.getPlayer())) {
                        GvGInstance.this.a(1, 100, 0, false, false, creature2.getPlayer());
                    } else if (GvGInstance.this.b.containsMember(creature2.getPlayer())) {
                        GvGInstance.this.a(2, 100, 0, false, false, creature2.getPlayer());
                    }
                    GvGInstance.this.a((L2GameServerPacket)new ExShowScreenMessage("Treasure guard Gerald died at the hand " + creature2.getName(), 5000, ExShowScreenMessage.ScreenMessageAlign.MIDDLE_CENTER, true));
                    GvGInstance.this.end();
                }
            }
        }
    }

    private class TeleportListener
    implements OnTeleportListener {
        private TeleportListener() {
        }

        public void onTeleport(Player player, int n, int n2, int n3, Reflection reflection) {
            if (GvGInstance.this.x.checkIfInZone(n, n2, n3, reflection) || GvGInstance.this.z.checkIfInZone(n, n2, n3, reflection) || GvGInstance.this.B.checkIfInZone(n, n2, n3, reflection)) {
                return;
            }
            GvGInstance.this.a(player, false);
            player.sendMessage(new CustomMessage("scripts.event.gvg.expelled", player, new Object[0]));
        }
    }

    private class PlayerPartyLeaveListener
    implements OnPlayerPartyLeaveListener {
        private PlayerPartyLeaveListener() {
        }

        public void onPartyLeave(Player player) {
            if (!GvGInstance.this.isActive()) {
                return;
            }
            Party party = player.getParty();
            if (party.getMemberCount() >= 3) {
                GvGInstance.this.a(player, false);
                return;
            }
            GvGInstance.this.b(party);
        }
    }

    public class BossSpawn
    extends RunnableImpl {
        public void runImpl() throws Exception {
            for (Player player : HardReferences.unwrap(GvGInstance.this.M)) {
                player.sendPacket((IStaticPacket)new ExShowScreenMessage(new CustomMessage("scripts.event.gvg.geraldguard", player, new Object[0]).toString(), 5000, ExShowScreenMessage.ScreenMessageAlign.MIDDLE_CENTER, true));
            }
            GvGInstance.this.addSpawnWithoutRespawn(25655, new Location(79128, 91000, -2880, 4836), 0);
            GvGInstance.this.openDoor(22200004);
            GvGInstance.this.openDoor(22200005);
            GvGInstance.this.openDoor(22200006);
            GvGInstance.this.openDoor(22200007);
        }
    }

    public class CountingDown
    extends RunnableImpl {
        public void runImpl() throws Exception {
            for (Player player : HardReferences.unwrap(GvGInstance.this.M)) {
                player.sendPacket((IStaticPacket)new ExShowScreenMessage(new CustomMessage("scripts.event.gvg.1mintofinish", player, new Object[0]).toString(), 4000, ExShowScreenMessage.ScreenMessageAlign.MIDDLE_CENTER, true));
            }
        }
    }

    public class BattleEnd
    extends RunnableImpl {
        public void runImpl() throws Exception {
            for (Player player : HardReferences.unwrap(GvGInstance.this.M)) {
                player.sendPacket((IStaticPacket)new ExShowScreenMessage(new CustomMessage("scripts.event.gvg.teleport1min", player, new Object[0]).toString(), 4000, ExShowScreenMessage.ScreenMessageAlign.MIDDLE_CENTER, true));
            }
            GvGInstance.this.end();
        }
    }

    public class Finish
    extends RunnableImpl {
        public void runImpl() throws Exception {
            GvGInstance.this.unParalyzePlayers();
            GvGInstance.this.cleanUp();
        }
    }
}
