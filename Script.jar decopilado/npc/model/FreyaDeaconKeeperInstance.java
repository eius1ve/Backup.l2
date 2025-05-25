/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.listener.Listener
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.instancemanager.SpawnManager
 *  l2.gameserver.listener.actor.OnDeathListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Party
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.Spawner
 *  l2.gameserver.model.Zone
 *  l2.gameserver.model.instances.DoorInstance
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage$ScreenMessageAlign
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.tables.SkillTable
 *  l2.gameserver.templates.npc.NpcTemplate
 *  l2.gameserver.utils.Location
 *  l2.gameserver.utils.ReflectionUtils
 */
package npc.model;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.concurrent.Future;
import l2.commons.listener.Listener;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.instancemanager.SpawnManager;
import l2.gameserver.listener.actor.OnDeathListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Party;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.Spawner;
import l2.gameserver.model.Zone;
import l2.gameserver.model.instances.DoorInstance;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExShowScreenMessage;
import l2.gameserver.scripts.Functions;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.templates.npc.NpcTemplate;
import l2.gameserver.utils.Location;
import l2.gameserver.utils.ReflectionUtils;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class FreyaDeaconKeeperInstance
extends NpcInstance {
    private static final int Hp = 8057;
    private static final long ek = 10L;
    private static final int Hq = 8379;
    private static final long el = 3L;
    private static final String gS = "freya_deacon001.htm";
    private static final String gT = "freya_deacon002.htm";
    private static final String gU = "[schuttgart13_mb2314_05m1]";
    private static final String[] aU = new String[]{"[schuttgart13_mb2314_01m1]", "[schuttgart13_mb2314_02m1]", "[schuttgart13_mb2314_03m1]", "[schuttgart13_mb2314_04m1]"};
    private static String gV = "[ice_fairy_sirr_epic]";
    private static final Location ad = new Location(113533, -126159, -3488);
    private static final Location ae = new Location(115792, -125760, -3373);
    private static final int Hr = 4479;
    private static final int Hs = 1;
    private static final int Ht = 0x16116A1;
    private static final int Hu = 23140002;
    private static final int Hv = 10025;
    private static final int Hw = 10026;
    private static final int Hx = 10027;
    private static final int Hy = 11038;
    private static final int Hz = 11039;
    private static final int HA = 11040;
    private final Listner a;
    private Future<?> R;
    private SoftReference<Party> a = new Listner();
    private int HB;

    public FreyaDeaconKeeperInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    public void onBypassFeedback(Player player, String string) {
        if (!FreyaDeaconKeeperInstance.canBypassCheck((Player)player, (NpcInstance)this)) {
            return;
        }
        if (string.equalsIgnoreCase("request_entrance_freya_castle")) {
            this.ah(player);
        } else {
            super.onBypassFeedback(player, string);
        }
    }

    private synchronized void ah(Player player) {
        if (this.a() == null) {
            Party party = player.getParty();
            if (party == null) {
                this.showChatWindow(player, 6, new Object[0]);
                return;
            }
            if (party.getPartyLeader() != player) {
                this.showChatWindow(player, 2, new Object[0]);
                return;
            }
            for (Player player2 : party.getPartyMembers()) {
                if (player2.getInventory().getCountOf(8057) >= 10L) continue;
                this.showChatWindow(player, 3, new Object[]{"<?name?>", player2.getName()});
                return;
            }
            if (!player.isQuestContinuationPossible(true)) {
                this.showChatWindow(player, 4, new Object[0]);
                return;
            }
            for (Player player2 : party.getPartyMembers()) {
                Functions.removeItem((Playable)player2, (int)8057, (long)10L);
            }
            this.c(party);
            this.showChatWindow(player, 5, new Object[0]);
            Functions.npcShoutCustomMessage((NpcInstance)this, (String)"scripts.ice_castle.1121005", (Object[])new Object[0]);
        } else {
            this.showChatWindow(player, 1, new Object[0]);
        }
    }

    private void clear() {
        this.ce();
        if (this.a != null) {
            ((Reference)((Object)this.a)).clear();
            this.a = null;
        }
        this.HB = 0;
        if (this.R != null) {
            this.R.cancel(false);
            this.R = null;
        }
    }

    private void cc() {
        Zone zone = ReflectionUtils.getZone((String)gV);
        if (zone != null) {
            for (Player player : zone.getInsidePlayers()) {
                player.teleToLocation(ae);
            }
        }
    }

    private void cd() {
        for (DoorInstance doorInstance : ReflectionUtils.getDoors((int[])new int[]{0x16116A1, 23140002})) {
            doorInstance.closeMe();
        }
    }

    private void ce() {
        for (DoorInstance doorInstance : ReflectionUtils.getDoors((int[])new int[]{0x16116A1, 23140002})) {
            doorInstance.openMe();
        }
    }

    private Party a() {
        if (this.a == null) {
            return null;
        }
        return (Party)((SoftReference)((Object)this.a)).get();
    }

    private void cf() {
        for (Spawner spawner : SpawnManager.getInstance().getSpawners(gU)) {
            NpcInstance npcInstance = spawner.doSpawn(true);
            spawner.stopRespawn();
            if (!npcInstance.isMonster()) continue;
            npcInstance.addListener((Listener)this.a);
        }
    }

    private void cg() {
        for (Spawner spawner : SpawnManager.getInstance().getSpawners(gU)) {
            for (NpcInstance npcInstance : spawner.getAllSpawned()) {
                if (!npcInstance.isMonster()) continue;
                npcInstance.removeListener((Listener)this.a);
            }
            spawner.deleteAll();
        }
    }

    private void ch() {
        for (String string : aU) {
            for (Spawner spawner : SpawnManager.getInstance().getSpawners(string)) {
                spawner.init();
                spawner.stopRespawn();
            }
        }
    }

    private void ci() {
        for (String string : aU) {
            for (Spawner spawner : SpawnManager.getInstance().getSpawners(string)) {
                spawner.deleteAll();
            }
        }
    }

    private void a(int n, String string, Object ... objectArray) {
        Party party = this.a();
        if (party != null && party.getMemberCount() > 0) {
            for (Player player : this.a()) {
                CustomMessage customMessage = new CustomMessage(string, player, objectArray);
                ExShowScreenMessage exShowScreenMessage = new ExShowScreenMessage(customMessage.toString(), n, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true, 1, -1, false);
                player.sendPacket((IStaticPacket)exShowScreenMessage);
            }
        }
    }

    private void c(Party party) {
        this.a = new SoftReference<Party>(party);
        this.HB = party.getMemberCount();
        this.R = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new IceCastleRunner(1000)), 120000L);
        this.a(100000, "scripts.ice_castle.1121000", new Object[0]);
    }

    private class Listner
    implements OnDeathListener {
        private Listner() {
        }

        public void onDeath(Creature creature, Creature creature2) {
            if (FreyaDeaconKeeperInstance.this.R != null) {
                FreyaDeaconKeeperInstance.this.R.cancel(false);
                FreyaDeaconKeeperInstance.this.R = null;
            }
            ThreadPoolManager.getInstance().execute((Runnable)((Object)new IceCastleRunner(1007)));
        }
    }

    private class IceCastleRunner
    extends RunnableImpl {
        private final int HC;

        private IceCastleRunner(int n) {
            this.HC = n;
        }

        public void runImpl() throws Exception {
            switch (this.HC) {
                case 1000: {
                    Party party = FreyaDeaconKeeperInstance.this.a();
                    boolean bl = true;
                    if (party == null || party.getMemberCount() != FreyaDeaconKeeperInstance.this.HB) {
                        bl = false;
                    }
                    if (bl && party != null) {
                        for (Player player : party.getPartyMembers()) {
                            if (!(FreyaDeaconKeeperInstance.this.getDistance((GameObject)player) > 512.0)) continue;
                            bl = false;
                        }
                    }
                    if (!bl) {
                        FreyaDeaconKeeperInstance.this.clear();
                        Functions.npcShoutCustomMessage((NpcInstance)FreyaDeaconKeeperInstance.this, (String)"scripts.ice_castle.1121007", (Object[])new Object[0]);
                        return;
                    }
                    FreyaDeaconKeeperInstance.this.cd();
                    FreyaDeaconKeeperInstance.this.cc();
                    FreyaDeaconKeeperInstance.this.R = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new IceCastleRunner(1001)), 5000L);
                    Functions.teleportParty((Party)party, (Location)ad, (int)64);
                    break;
                }
                case 1001: {
                    Party party = FreyaDeaconKeeperInstance.this.a();
                    if (party == null || party.getMemberCount() != FreyaDeaconKeeperInstance.this.HB) {
                        FreyaDeaconKeeperInstance.this.clear();
                        return;
                    }
                    FreyaDeaconKeeperInstance.this.a(10000, "scripts.ice_castle.1121001", new Object[0]);
                    FreyaDeaconKeeperInstance.this.cf();
                    FreyaDeaconKeeperInstance.this.ch();
                    FreyaDeaconKeeperInstance.this.R = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new IceCastleRunner(1002)), 30000L);
                    break;
                }
                case 1002: {
                    Party party = FreyaDeaconKeeperInstance.this.a();
                    if (party != null) {
                        Skill skill = SkillTable.getInstance().getInfo(4479, 1);
                        for (Player player : party.getPartyMembers()) {
                            skill.getEffects((Creature)player, (Creature)player, false, false);
                        }
                    }
                    FreyaDeaconKeeperInstance.this.R = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new IceCastleRunner(1003)), 300000L);
                    break;
                }
                case 1003: {
                    FreyaDeaconKeeperInstance.this.a(10000, "scripts.ice_castle.1121008", new Object[0]);
                    FreyaDeaconKeeperInstance.this.R = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new IceCastleRunner(1004)), 600000L);
                    break;
                }
                case 1004: {
                    FreyaDeaconKeeperInstance.this.a(10000, "scripts.ice_castle.1121009", new Object[0]);
                    FreyaDeaconKeeperInstance.this.R = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new IceCastleRunner(1005)), 600000L);
                    break;
                }
                case 1005: {
                    FreyaDeaconKeeperInstance.this.a(10000, "scripts.ice_castle.1121002", new Object[0]);
                    FreyaDeaconKeeperInstance.this.R = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new IceCastleRunner(1006)), 540000L);
                    break;
                }
                case 1006: {
                    FreyaDeaconKeeperInstance.this.ci();
                    FreyaDeaconKeeperInstance.this.cg();
                    FreyaDeaconKeeperInstance.this.ce();
                    FreyaDeaconKeeperInstance.this.a(10000, "scripts.ice_castle.1121003", new Object[0]);
                    FreyaDeaconKeeperInstance.this.clear();
                    break;
                }
                case 1007: {
                    FreyaDeaconKeeperInstance.this.ci();
                    FreyaDeaconKeeperInstance.this.ce();
                    FreyaDeaconKeeperInstance.this.a(10000, "scripts.ice_castle.1121004", new Object[0]);
                    FreyaDeaconKeeperInstance.this.R = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new IceCastleRunner(1008)), 30000L);
                    break;
                }
                case 1008: {
                    FreyaDeaconKeeperInstance.this.cg();
                    FreyaDeaconKeeperInstance.this.clear();
                }
            }
        }
    }
}
