/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.instancemanager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.xml.parsers.DocumentBuilderFactory;
import l2.commons.geometry.Rectangle;
import l2.commons.util.Rnd;
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.NpcHolder;
import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.model.Player;
import l2.gameserver.model.SimpleSpawner;
import l2.gameserver.model.Territory;
import l2.gameserver.model.entity.DimensionalRift;
import l2.gameserver.model.entity.Reflection;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExTeleportToLocationActivate;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.network.l2.s2c.TeleportToLocation;
import l2.gameserver.templates.npc.NpcTemplate;
import l2.gameserver.utils.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class DimensionalRiftManager {
    private static final Logger bq = LoggerFactory.getLogger(DimensionalRiftManager.class);
    private static DimensionalRiftManager a;
    private Map<Integer, Map<Integer, DimensionalRiftRoom>> am = new ConcurrentHashMap<Integer, Map<Integer, DimensionalRiftRoom>>();
    private static final int fO = 7079;

    public static DimensionalRiftManager getInstance() {
        if (a == null) {
            a = new DimensionalRiftManager();
        }
        return a;
    }

    public DimensionalRiftManager() {
        this.load();
    }

    public DimensionalRiftRoom getRoom(int n, int n2) {
        return this.am.get(n).get(n2);
    }

    public Map<Integer, DimensionalRiftRoom> getRooms(int n) {
        return this.am.get(n);
    }

    public void load() {
        Object object;
        int n = 0;
        int n2 = 0;
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setValidating(false);
            documentBuilderFactory.setIgnoringComments(true);
            File file = new File(Config.DATAPACK_ROOT, "data/dimensional_rift.xml");
            if (!file.exists()) {
                throw new IOException();
            }
            object = documentBuilderFactory.newDocumentBuilder().parse(file);
            Location location = new Location();
            int n3 = 0;
            int n4 = 0;
            int n5 = 0;
            int n6 = 0;
            int n7 = 0;
            int n8 = 0;
            for (Node node = object.getFirstChild(); node != null; node = node.getNextSibling()) {
                if (!"rift".equalsIgnoreCase(node.getNodeName())) continue;
                for (Node node2 = node.getFirstChild(); node2 != null; node2 = node2.getNextSibling()) {
                    if (!"area".equalsIgnoreCase(node2.getNodeName())) continue;
                    NamedNodeMap namedNodeMap = node2.getAttributes();
                    int n9 = Integer.parseInt(namedNodeMap.getNamedItem("type").getNodeValue());
                    for (Node node3 = node2.getFirstChild(); node3 != null; node3 = node3.getNextSibling()) {
                        Node node4;
                        if (!"room".equalsIgnoreCase(node3.getNodeName())) continue;
                        namedNodeMap = node3.getAttributes();
                        int n10 = Integer.parseInt(namedNodeMap.getNamedItem("id").getNodeValue());
                        Node node5 = namedNodeMap.getNamedItem("isBossRoom");
                        boolean bl = node5 != null ? Boolean.parseBoolean(node5.getNodeValue()) : false;
                        Territory territory = null;
                        for (node4 = node3.getFirstChild(); node4 != null; node4 = node4.getNextSibling()) {
                            if ("teleport".equalsIgnoreCase(node4.getNodeName())) {
                                namedNodeMap = node4.getAttributes();
                                location = Location.parseLoc(namedNodeMap.getNamedItem("loc").getNodeValue());
                                continue;
                            }
                            if (!"zone".equalsIgnoreCase(node4.getNodeName())) continue;
                            namedNodeMap = node4.getAttributes();
                            n3 = Integer.parseInt(namedNodeMap.getNamedItem("xMin").getNodeValue());
                            n4 = Integer.parseInt(namedNodeMap.getNamedItem("xMax").getNodeValue());
                            n5 = Integer.parseInt(namedNodeMap.getNamedItem("yMin").getNodeValue());
                            n6 = Integer.parseInt(namedNodeMap.getNamedItem("yMax").getNodeValue());
                            n7 = Integer.parseInt(namedNodeMap.getNamedItem("zMin").getNodeValue());
                            n8 = Integer.parseInt(namedNodeMap.getNamedItem("zMax").getNodeValue());
                            territory = new Territory().add(new Rectangle(n3, n5, n4, n6).setZmin(n7).setZmax(n8));
                        }
                        if (territory == null) {
                            bq.error("DimensionalRiftManager: invalid spawn data for room id " + n10 + "!");
                        }
                        if (!this.am.containsKey(n9)) {
                            this.am.put(n9, new ConcurrentHashMap());
                        }
                        this.am.get(n9).put(n10, new DimensionalRiftRoom(territory, location, bl));
                        for (node4 = node3.getFirstChild(); node4 != null; node4 = node4.getNextSibling()) {
                            if (!"spawn".equalsIgnoreCase(node4.getNodeName())) continue;
                            namedNodeMap = node4.getAttributes();
                            int n11 = Integer.parseInt(namedNodeMap.getNamedItem("mobId").getNodeValue());
                            int n12 = Integer.parseInt(namedNodeMap.getNamedItem("delay").getNodeValue());
                            int n13 = Integer.parseInt(namedNodeMap.getNamedItem("count").getNodeValue());
                            NpcTemplate npcTemplate = NpcHolder.getInstance().getTemplate(n11);
                            if (npcTemplate == null) {
                                bq.warn("Template " + n11 + " not found!");
                            }
                            if (!this.am.containsKey(n9)) {
                                bq.warn("Type " + n9 + " not found!");
                            } else if (!this.am.get(n9).containsKey(n10)) {
                                bq.warn("Room " + n10 + " in Type " + n9 + " not found!");
                            }
                            if (npcTemplate != null && this.am.containsKey(n9) && this.am.get(n9).containsKey(n10)) {
                                SimpleSpawner simpleSpawner = new SimpleSpawner(npcTemplate);
                                simpleSpawner.setTerritory(territory);
                                simpleSpawner.setHeading(-1);
                                simpleSpawner.setRespawnDelay(n12);
                                simpleSpawner.setAmount(n13);
                                this.am.get(n9).get(n10).getSpawns().add(simpleSpawner);
                                ++n;
                                continue;
                            }
                            ++n2;
                        }
                    }
                }
            }
        }
        catch (Exception exception) {
            bq.error("DimensionalRiftManager: Error on loading dimensional rift spawns!", (Throwable)exception);
        }
        int n14 = this.am.keySet().size();
        int n15 = 0;
        object = this.am.keySet().iterator();
        while (object.hasNext()) {
            int n16 = (Integer)object.next();
            n15 += this.am.get(n16).keySet().size();
        }
        bq.info("DimensionalRiftManager: Loaded " + n14 + " room types with " + n15 + " rooms.");
        bq.info("DimensionalRiftManager: Loaded " + n + " dimensional rift spawns, " + n2 + " errors.");
    }

    public void reload() {
        for (int n : this.am.keySet()) {
            this.am.get(n).clear();
        }
        this.am.clear();
        this.load();
    }

    public boolean checkIfInRiftZone(Location location, boolean bl) {
        if (bl) {
            return this.am.get(0).get(1).checkIfInZone(location);
        }
        return this.am.get(0).get(1).checkIfInZone(location) && !this.am.get(0).get(0).checkIfInZone(location);
    }

    public boolean checkIfInPeaceZone(Location location) {
        return this.am.get(0).get(0).checkIfInZone(location);
    }

    public void teleportToWaitingRoom(Player player) {
        DimensionalRiftManager.teleToLocation(player, Location.findPointToStay(this.getRoom(0, 0).getTeleportCoords(), 0, 250, ReflectionManager.DEFAULT.getGeoIndex()), null);
    }

    public void start(Player player, int n, NpcInstance npcInstance) {
        if (!player.isInParty()) {
            this.showHtmlFile(player, "rift/NoParty.htm", npcInstance);
            return;
        }
        if (!player.isGM()) {
            if (!player.getParty().isLeader(player)) {
                this.showHtmlFile(player, "rift/NotPartyLeader.htm", npcInstance);
                return;
            }
            if (player.getParty().isInDimensionalRift()) {
                this.showHtmlFile(player, "rift/Cheater.htm", npcInstance);
                if (!player.isGM()) {
                    bq.warn("Player " + player.getName() + "(" + player.getObjectId() + ") was cheating in dimension rift area!");
                }
                return;
            }
            if (player.getParty().getMemberCount() < Config.RIFT_MIN_PARTY_SIZE) {
                this.showHtmlFile(player, "rift/SmallParty.htm", npcInstance);
                return;
            }
            for (Player object : player.getParty().getPartyMembers()) {
                if (this.checkIfInPeaceZone(object.getLoc())) continue;
                this.showHtmlFile(player, "rift/NotInWaitingRoom.htm", npcInstance);
                return;
            }
            for (Player player2 : player.getParty().getPartyMembers()) {
                ItemInstance itemInstance = player2.getInventory().getItemByItemId(7079);
                if (itemInstance != null && itemInstance.getCount() >= this.a(n)) continue;
                this.showHtmlFile(player, "rift/NoFragments.htm", npcInstance);
                return;
            }
            for (Player player2 : player.getParty().getPartyMembers()) {
                if (player2.getInventory().destroyItemByItemId(7079, this.a(n))) continue;
                this.showHtmlFile(player, "rift/NoFragments.htm", npcInstance);
                return;
            }
        }
        new DimensionalRift(player.getParty(), n, Rnd.get(1, this.am.get(n).size() - 1));
    }

    private long a(int n) {
        switch (n) {
            case 1: {
                return Config.RIFT_ENTER_COST_RECRUIT;
            }
            case 2: {
                return Config.RIFT_ENTER_COST_SOLDIER;
            }
            case 3: {
                return Config.RIFT_ENTER_COST_OFFICER;
            }
            case 4: {
                return Config.RIFT_ENTER_COST_CAPTAIN;
            }
            case 5: {
                return Config.RIFT_ENTER_COST_COMMANDER;
            }
            case 6: {
                return Config.RIFT_ENTER_COST_HERO;
            }
        }
        return Long.MAX_VALUE;
    }

    public void showHtmlFile(Player player, String string, NpcInstance npcInstance) {
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, npcInstance);
        npcHtmlMessage.setFile(string);
        npcHtmlMessage.replace("%t_name%", npcInstance.getName());
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    public static void teleToLocation(Player player, Location location, Reflection reflection) {
        if (player.isTeleporting() || player.isDeleted()) {
            return;
        }
        player.setIsTeleporting(true);
        player.setTarget(null);
        player.stopMove();
        if (player.isInBoat()) {
            player.setBoat(null);
        }
        player.breakFakeDeath();
        player.decayMe();
        player.setLoc(location);
        if (reflection == null) {
            player.setReflection(ReflectionManager.DEFAULT);
        }
        player.setLastClientPosition(null);
        player.setLastServerPosition(null);
        player.sendPacket(new TeleportToLocation(player, location), new ExTeleportToLocationActivate(player, location));
    }

    public class DimensionalRiftRoom {
        private final Territory h;
        private final Location o;
        private final boolean bg;
        private final List<SimpleSpawner> ax;

        public DimensionalRiftRoom(Territory territory, Location location, boolean bl) {
            this.h = territory;
            this.o = location;
            this.bg = bl;
            this.ax = new ArrayList<SimpleSpawner>();
        }

        public Location getTeleportCoords() {
            return this.o;
        }

        public boolean checkIfInZone(Location location) {
            return this.checkIfInZone(location.x, location.y, location.z);
        }

        public boolean checkIfInZone(int n, int n2, int n3) {
            return this.h.isInside(n, n2, n3);
        }

        public boolean isBossRoom() {
            return this.bg;
        }

        public List<SimpleSpawner> getSpawns() {
            return this.ax;
        }
    }
}
