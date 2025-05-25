/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.dbutils.DbUtils
 *  l2.commons.lang.reference.HardReference
 *  l2.commons.listener.Listener
 *  l2.commons.threading.RunnableImpl
 *  l2.commons.util.Rnd
 *  l2.gameserver.Config
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.database.DatabaseFactory
 *  l2.gameserver.instancemanager.ReflectionManager
 *  l2.gameserver.listener.actor.OnDeathListener
 *  l2.gameserver.listener.actor.npc.OnSpawnListener
 *  l2.gameserver.listener.actor.player.OnAnswerListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObjectsStorage
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Zone$ZoneType
 *  l2.gameserver.model.actor.listener.NpcListenerList
 *  l2.gameserver.model.base.TeamType
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.network.l2.components.ChatType
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.ConfirmDlg
 *  l2.gameserver.network.l2.s2c.Say2
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.utils.Location
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package services;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilderFactory;
import l2.commons.dbutils.DbUtils;
import l2.commons.lang.reference.HardReference;
import l2.commons.listener.Listener;
import l2.commons.threading.RunnableImpl;
import l2.commons.util.Rnd;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.listener.actor.OnDeathListener;
import l2.gameserver.listener.actor.npc.OnSpawnListener;
import l2.gameserver.listener.actor.player.OnAnswerListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.model.Zone;
import l2.gameserver.model.actor.listener.NpcListenerList;
import l2.gameserver.model.base.TeamType;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.components.ChatType;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ConfirmDlg;
import l2.gameserver.network.l2.s2c.Say2;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.utils.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class TeleportToRaid
extends Functions
implements ScriptFile {
    private static final Logger eg = LoggerFactory.getLogger(TeleportToRaid.class);
    private static final Map<Integer, RaidData> cu = new HashMap<Integer, RaidData>();

    private static void r(int n) {
        final RaidData raidData = cu.get(n);
        if (raidData != null && raidData.hB && raidData.bGe > 0) {
            ThreadPoolManager.getInstance().schedule(() -> {
                long l = System.currentTimeMillis() / 1000L;
                int n2 = TeleportToRaid.p(n);
                int n3 = (int)((long)n2 - l);
                long l2 = (long)(n3 - raidData.bGe) * 1000L;
                if (n2 > 0 && n3 > 0 && l2 > 0L) {
                    ThreadPoolManager.getInstance().schedule((Runnable)new RunnableImpl(){

                        public void runImpl() {
                            String string = raidData.ih;
                            String string2 = raidData.ii;
                            string = string.replaceAll("%name_ru%", raidData.ic);
                            string2 = string2.replaceAll("%name_en%", raidData.ic);
                            Say2 say2 = new Say2(0, ChatType.ANNOUNCEMENT, "", string);
                            Say2 say22 = new Say2(0, ChatType.ANNOUNCEMENT, "", string2);
                            for (Player player : GameObjectsStorage.getAllPlayersForIterate()) {
                                if (player == null || !raidData.j(player.getLevel())) continue;
                                player.sendPacket((IStaticPacket)(player.isLangRus() ? say2 : say22));
                            }
                        }
                    }, l2);
                }
            }, 20000L);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static int p(int n) {
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        Connection connection;
        int n2;
        block4: {
            n2 = 0;
            connection = null;
            preparedStatement = null;
            resultSet = null;
            try {
                connection = DatabaseFactory.getInstance().getConnection();
                preparedStatement = connection.prepareStatement("SELECT `respawn_delay` FROM `raidboss_status` WHERE `id`=? LIMIT 1");
                preparedStatement.setInt(1, n);
                resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) break block4;
                n2 = resultSet.getInt("respawn_delay");
            }
            catch (Exception exception) {
                try {
                    eg.error("Could not get respawn delay by boss id:", (Throwable)exception);
                }
                catch (Throwable throwable) {
                    DbUtils.closeQuietly((Connection)connection, preparedStatement, resultSet);
                    throw throwable;
                }
                DbUtils.closeQuietly((Connection)connection, (Statement)preparedStatement, resultSet);
            }
        }
        DbUtils.closeQuietly((Connection)connection, (Statement)preparedStatement, (ResultSet)resultSet);
        return n2;
    }

    private static void s(int n) {
        RaidData raidData = cu.get(n);
        if (raidData != null) {
            String string;
            String string2;
            if (raidData.hB) {
                string2 = raidData.ij;
                string = raidData.ik;
                string2 = string2.replaceAll("%name_ru%", raidData.ic);
                string = string.replaceAll("%name_en%", raidData.ic);
                Say2 say2 = new Say2(0, ChatType.ANNOUNCEMENT, "", string2);
                Player player = new Say2(0, ChatType.ANNOUNCEMENT, "", string);
                for (Player player2 : GameObjectsStorage.getAllPlayersForIterate()) {
                    if (player2 == null || !raidData.j(player2.getLevel())) continue;
                    player2.sendPacket((IStaticPacket)(player2.isLangRus() ? say2 : player));
                }
            }
            if (raidData.hA) {
                string2 = raidData.if;
                string = raidData.ig;
                string2 = string2.replaceAll("%name_ru%", raidData.ic);
                string = string.replaceAll("%name_en%", raidData.ie);
                for (Player player : GameObjectsStorage.getAllPlayersForIterate()) {
                    if (player == null || !raidData.k(player.getLevel()) || TeleportToRaid.B(player) || player.getVarB("noaskboss@")) continue;
                    player.ask(player.isLangRus() ? (ConfirmDlg)new ConfirmDlg(SystemMsg.S1, raidData.bGd).addString(string2) : (ConfirmDlg)new ConfirmDlg(SystemMsg.S1, raidData.bGd).addString(string), (OnAnswerListener)new AnswerListener(player, raidData));
                }
            }
        }
    }

    private static boolean B(Player player) {
        return player.isInTrade() || player.isInOfflineMode() || player.isOlyParticipant() || player.isInStoreMode() || player.isInTrade() || player.isDead() || player.getReflection() == ReflectionManager.JAIL || player.getPvpFlag() != 0 || player.getTeam() != TeamType.NONE || player.isInDuel() || player.isInZone(Zone.ZoneType.no_summon) || player.isInZone(Zone.ZoneType.epic) || player.isInZone(Zone.ZoneType.SIEGE);
    }

    private static void co() {
        try {
            File file = new File(Config.DATAPACK_ROOT, "data/teleport_to_raid.xml");
            if (!file.exists()) {
                eg.error("TeleportToRaid: data/teleport_to_raid.xml doesn't exist");
                return;
            }
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setValidating(false);
            documentBuilderFactory.setIgnoringComments(true);
            Document document = documentBuilderFactory.newDocumentBuilder().parse(file);
            for (Node node = document.getFirstChild(); node != null; node = node.getNextSibling()) {
                if (!"list".equalsIgnoreCase(node.getNodeName())) continue;
                for (Node node2 = node.getFirstChild(); node2 != null; node2 = node2.getNextSibling()) {
                    NamedNodeMap namedNodeMap;
                    String string;
                    if (!"boss".equalsIgnoreCase(node2.getNodeName()) || !(string = (namedNodeMap = node2.getAttributes()).getNamedItem("enable").getNodeValue()).equalsIgnoreCase("true")) continue;
                    int n = Integer.parseInt(namedNodeMap.getNamedItem("id").getNodeValue());
                    String string2 = namedNodeMap.getNamedItem("name_ru").getNodeValue();
                    String string3 = namedNodeMap.getNamedItem("name_en").getNodeValue();
                    boolean bl = false;
                    int n2 = 0;
                    int n3 = 0;
                    int n4 = 0;
                    String string4 = "";
                    String string5 = "";
                    boolean bl2 = false;
                    int n5 = 0;
                    int n6 = 0;
                    int n7 = 0;
                    String string6 = "";
                    String string7 = "";
                    String string8 = "";
                    String string9 = "";
                    ArrayList<Location> arrayList = new ArrayList<Location>();
                    for (Node node3 = node2.getFirstChild(); node3 != null; node3 = node3.getNextSibling()) {
                        Object object;
                        if ("window".equalsIgnoreCase(node3.getNodeName())) {
                            namedNodeMap = node3.getAttributes();
                            bl = Boolean.parseBoolean(namedNodeMap.getNamedItem("show").getNodeValue());
                            n2 = Integer.parseInt(namedNodeMap.getNamedItem("lvl_min").getNodeValue());
                            n3 = Integer.parseInt(namedNodeMap.getNamedItem("lvl_max").getNodeValue());
                            n4 = Integer.parseInt(namedNodeMap.getNamedItem("time").getNodeValue());
                            string4 = namedNodeMap.getNamedItem("text_ru").getNodeValue();
                            string5 = namedNodeMap.getNamedItem("text_en").getNodeValue();
                            continue;
                        }
                        if ("announce".equalsIgnoreCase(node3.getNodeName())) {
                            namedNodeMap = node3.getAttributes();
                            bl2 = Boolean.parseBoolean(namedNodeMap.getNamedItem("show").getNodeValue());
                            n5 = Integer.parseInt(namedNodeMap.getNamedItem("delay").getNodeValue());
                            n6 = Integer.parseInt(namedNodeMap.getNamedItem("lvl_min").getNodeValue());
                            n7 = Integer.parseInt(namedNodeMap.getNamedItem("lvl_max").getNodeValue());
                            for (object = node3.getFirstChild(); object != null; object = object.getNextSibling()) {
                                if ("before".equalsIgnoreCase(object.getNodeName())) {
                                    namedNodeMap = object.getAttributes();
                                    string6 = namedNodeMap.getNamedItem("text_ru").getNodeValue();
                                    string7 = namedNodeMap.getNamedItem("text_en").getNodeValue();
                                    continue;
                                }
                                if (!"after".equalsIgnoreCase(object.getNodeName())) continue;
                                namedNodeMap = object.getAttributes();
                                string8 = namedNodeMap.getNamedItem("text_ru").getNodeValue();
                                string9 = namedNodeMap.getNamedItem("text_en").getNodeValue();
                            }
                            continue;
                        }
                        if (!"teleport".equalsIgnoreCase(node3.getNodeName())) continue;
                        namedNodeMap = node3.getAttributes();
                        object = namedNodeMap.getNamedItem("loc").getNodeValue().split(";");
                        for (String string10 : object) {
                            if (string10.isEmpty()) continue;
                            String[] stringArray = string10.split(" ");
                            arrayList.add(new Location(Integer.parseInt(stringArray[0]), Integer.parseInt(stringArray[1]), Integer.parseInt(stringArray[2])));
                        }
                    }
                    cu.put(n, new RaidData(n, string2, string3, bl, n2, n3, string4, string5, n4, bl2, n5, n6, n7, string6, string7, string8, string9, arrayList));
                }
            }
        }
        catch (Exception exception) {
            eg.error("TeleportToRaid: loadSettings() " + exception);
        }
        eg.info("TeleportToRaid: Loaded " + cu.size() + " bosses schemes.");
    }

    public void onLoad() {
        if (Config.ALT_RAID_BOSS_SPAWN_AND_TELEPORT) {
            TeleportToRaid.co();
            NpcListenerList.addGlobal((Listener)new OnSpawnImpl());
            NpcListenerList.addGlobal((Listener)new OnDeathImpl());
        }
    }

    public void onReload() {
        if (Config.ALT_RAID_BOSS_SPAWN_AND_TELEPORT) {
            TeleportToRaid.co();
        }
    }

    public void onShutdown() {
    }

    /*
     * Illegal identifiers - consider using --renameillegalidents true
     */
    public static class RaidData {
        private final int bGa;
        private final List<Location> dY;
        public int boss_id;
        private final String ic;
        private final String ie;
        private final boolean hA;
        private final int bGb;
        private final int bGc;
        private final int bGd;
        private final String if;
        private final String ig;
        private final boolean hB;
        private final int bGe;
        private final int bGf;
        private final int bGg;
        private final String ih;
        private final String ii;
        private final String ij;
        private final String ik;

        public RaidData(int n, String string, String string2, boolean bl, int n2, int n3, String string3, String string4, int n4, boolean bl2, int n5, int n6, int n7, String string5, String string6, String string7, String string8, List<Location> list) {
            this.boss_id = n;
            this.ic = string;
            this.ie = string2;
            this.hA = bl;
            this.bGb = n2;
            this.bGc = n3;
            this.bGd = n4;
            this.if = string3;
            this.ig = string4;
            this.hB = bl2;
            this.bGe = n5;
            this.bGf = n6;
            this.bGg = n7;
            this.ih = string5;
            this.ii = string6;
            this.ij = string7;
            this.ik = string8;
            this.dY = list;
            this.bGa = list.size() - 1;
        }

        private Location f() {
            return this.dY.get(Rnd.get((int)0, (int)this.bGa));
        }

        private boolean j(int n) {
            return n >= this.bGb && n <= this.bGc;
        }

        private boolean k(int n) {
            return n >= this.bGf && n <= this.bGg;
        }
    }

    private static class AnswerListener
    implements OnAnswerListener {
        private final RaidData a;
        private final HardReference<Player> af;

        public AnswerListener(Player player, RaidData raidData) {
            this.a = raidData;
            this.af = player.getRef();
        }

        public void sayYes() {
            Player player = (Player)this.af.get();
            if (player != null) {
                player.teleToLocation(this.a.f());
            }
        }

        public void sayNo() {
        }
    }

    private static class OnSpawnImpl
    implements OnSpawnListener {
        private OnSpawnImpl() {
        }

        public void onSpawn(NpcInstance npcInstance) {
            TeleportToRaid.s(npcInstance.getNpcId());
        }
    }

    private static class OnDeathImpl
    implements OnDeathListener {
        private OnDeathImpl() {
        }

        public void onDeath(Creature creature, Creature creature2) {
            TeleportToRaid.r(creature.getNpcId());
        }
    }
}
