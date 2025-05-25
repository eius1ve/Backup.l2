/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.instancemanager.sepulchers.spawn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.parsers.DocumentBuilderFactory;
import l2.commons.geometry.Polygon;
import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.instancemanager.sepulchers.model.WayData;
import l2.gameserver.instancemanager.sepulchers.spawn.SepulcherSpawnDefine;
import l2.gameserver.instancemanager.sepulchers.spawn.SepulcherSpawnHandler;
import l2.gameserver.model.Territory;
import l2.gameserver.model.Zone;
import l2.gameserver.model.instances.DoorInstance;
import l2.gameserver.utils.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class SepulcherParser {
    private static final Logger bz = LoggerFactory.getLogger(SepulcherParser.class);
    private static final String cE = "data/events/sepulchers/sepulcher_territories.xml";
    private static final String cF = "data/events/sepulchers/sepulcher_makers.xml";
    private static final String cG = "data/events/sepulchers/sepulcher_way_data.xml";
    private static final String cH = "data/events/sepulchers/sepulcher_victim_bosses.xml";
    private static final Pattern b = Pattern.compile("\\{(\\-?\\d+);(\\-?\\d+);(\\-?\\d+);(\\-?\\d+);?(.+?\\%)?\\}");
    private static final DocumentBuilderFactory a = DocumentBuilderFactory.newInstance();
    private static final SepulcherParser a = new SepulcherParser();
    private Map<String, Territory> at;
    private Map<String, SepulcherSpawnHandler> au;
    private Map<String, WayData> av;
    private Map<String, Integer> aw;

    public static SepulcherParser getInstance() {
        return a;
    }

    public void load() {
        try {
            this.aL();
        }
        catch (Exception exception) {
            bz.error("Failed to load sepulcher territories", (Throwable)exception);
        }
        try {
            this.aM();
        }
        catch (Exception exception) {
            bz.error("Failed to load sepulcher spawns", (Throwable)exception);
        }
        try {
            this.aN();
        }
        catch (Exception exception) {
            bz.error("Failed to load sepulcher way data", (Throwable)exception);
        }
        try {
            this.aO();
        }
        catch (Exception exception) {
            bz.error("Failed to load sepulcher victim bosses", (Throwable)exception);
        }
    }

    private void aL() throws Exception {
        this.at = new HashMap<String, Territory>();
        Document document = a.newDocumentBuilder().parse(cE);
        for (Node node = document.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (!node.getNodeName().equalsIgnoreCase("list")) continue;
            for (Node node2 = node.getFirstChild(); node2 != null; node2 = node2.getNextSibling()) {
                if (!node2.getNodeName().equalsIgnoreCase("territory")) continue;
                String string = this.a(node2, "name");
                String string2 = this.a(node2, "points");
                Matcher matcher = b.matcher(string2);
                Territory territory = new Territory();
                Polygon polygon = new Polygon();
                int n = 999999;
                int n2 = -999999;
                while (matcher.find()) {
                    int n3 = Integer.parseInt(matcher.group(1));
                    int n4 = Integer.parseInt(matcher.group(2));
                    int n5 = Integer.parseInt(matcher.group(3));
                    int n6 = Integer.parseInt(matcher.group(4));
                    polygon.add(n3, n4);
                    if (n5 < n) {
                        n = n5;
                        polygon.setZmin(n);
                    }
                    if (n6 <= n2) continue;
                    n2 = n6;
                    polygon.setZmax(n2);
                }
                territory.add(polygon);
                this.at.put(string, territory);
            }
        }
    }

    private void aM() throws Exception {
        this.au = new HashMap<String, SepulcherSpawnHandler>();
        Document document = a.newDocumentBuilder().parse(cF);
        for (Node node = document.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (!node.getNodeName().equalsIgnoreCase("list")) continue;
            for (Node node2 = node.getFirstChild(); node2 != null; node2 = node2.getNextSibling()) {
                if (!node2.getNodeName().equalsIgnoreCase("npcmaker")) continue;
                String string = this.a(node2, "name");
                String string2 = this.b(node2, "territory");
                Territory territory = string2 != null ? this.at.get(string2) : null;
                ArrayList<SepulcherSpawnDefine> arrayList = new ArrayList<SepulcherSpawnDefine>();
                SepulcherSpawnHandler sepulcherSpawnHandler = new SepulcherSpawnHandler(string, territory, arrayList);
                for (Node node3 = node2.getFirstChild(); node3 != null; node3 = node3.getNextSibling()) {
                    if (!node3.getNodeName().equalsIgnoreCase("npc")) continue;
                    int n = Integer.parseInt(this.a(node3, "id"));
                    String string3 = this.a(node3, "pos");
                    int n2 = Integer.parseInt(this.a(node3, "respawn"));
                    int n3 = Integer.parseInt(this.a(node3, "total"));
                    Location location = null;
                    Matcher matcher = b.matcher(string3);
                    if (matcher.find() && matcher.group(4) != null) {
                        location = new Location(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)));
                    }
                    arrayList.add(new SepulcherSpawnDefine(n, location, n2, n3, sepulcherSpawnHandler));
                }
                this.au.put(string, sepulcherSpawnHandler);
            }
        }
    }

    private void aN() throws Exception {
        this.av = new HashMap<String, WayData>();
        Document document = a.newDocumentBuilder().parse(cG);
        for (Node node = document.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (!node.getNodeName().equalsIgnoreCase("list")) continue;
            for (Node node2 = node.getFirstChild(); node2 != null; node2 = node2.getNextSibling()) {
                Object object;
                if (!node2.getNodeName().equalsIgnoreCase("way")) continue;
                String string = this.a(node2, "prefix");
                Location location = null;
                Location location2 = null;
                ArrayList<String> arrayList = new ArrayList<String>();
                String string2 = null;
                for (object = node2.getFirstChild(); object != null; object = object.getNextSibling()) {
                    Node node3;
                    if (object.getNodeName().equalsIgnoreCase("points")) {
                        for (node3 = object.getFirstChild(); node3 != null; node3 = node3.getNextSibling()) {
                            if (!node3.getNodeName().equalsIgnoreCase("point")) continue;
                            if (this.a(node3, "type").equalsIgnoreCase("entry")) {
                                location = this.a(node3);
                                continue;
                            }
                            if (!this.a(node3, "type").equalsIgnoreCase("escape")) continue;
                            location2 = this.a(node3);
                        }
                        continue;
                    }
                    if (object.getNodeName().equalsIgnoreCase("doors")) {
                        for (node3 = object.getFirstChild(); node3 != null; node3 = node3.getNextSibling()) {
                            if (!node3.getNodeName().equalsIgnoreCase("door")) continue;
                            arrayList.add(this.a(node3, "name"));
                        }
                        continue;
                    }
                    if (!object.getNodeName().equalsIgnoreCase("collapse_zone")) continue;
                    string2 = this.a((Node)object, "name");
                }
                object = ReflectionManager.DEFAULT.getZone(string2);
                this.av.put(string, new WayData(location, location2, this.d(arrayList), (Zone)object));
            }
        }
    }

    private void aO() throws Exception {
        this.aw = new HashMap<String, Integer>();
        Document document = a.newDocumentBuilder().parse(cH);
        for (Node node = document.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (!node.getNodeName().equalsIgnoreCase("list")) continue;
            for (Node node2 = node.getFirstChild(); node2 != null; node2 = node2.getNextSibling()) {
                if (!node2.getNodeName().equalsIgnoreCase("boss")) continue;
                this.aw.put(this.a(node2, "handler_name"), Integer.parseInt(this.a(node2, "id")));
            }
        }
    }

    private Location a(Node node) {
        return new Location(Integer.parseInt(this.a(node, "x")), Integer.parseInt(this.a(node, "y")), Integer.parseInt(this.a(node, "z")));
    }

    private List<DoorInstance> d(List<String> list) {
        ArrayList<DoorInstance> arrayList = new ArrayList<DoorInstance>();
        Collection<DoorInstance> collection = ReflectionManager.DEFAULT.getDoors();
        list.forEach(string -> arrayList.add(collection.stream().filter(doorInstance -> doorInstance.getName().equalsIgnoreCase((String)string)).findAny().orElseThrow(() -> new NullPointerException("Cannot find door named " + string))));
        return arrayList;
    }

    private String a(Node node, String string) {
        if (node == null) {
            throw new NullPointerException("Null node " + string);
        }
        Node node2 = node.getAttributes().getNamedItem(string);
        if (node2 == null) {
            throw new NullPointerException("No attribute '" + string + "' found for " + node.getNodeName());
        }
        return node2.getNodeValue();
    }

    private String b(Node node, String string) {
        if (node == null) {
            throw new NullPointerException("Null node " + string);
        }
        Node node2 = node.getAttributes().getNamedItem(string);
        if (node2 == null) {
            return null;
        }
        return node2.getNodeValue();
    }

    public Map<String, Territory> getTerritories() {
        return this.at;
    }

    public Map<String, SepulcherSpawnHandler> getSpawns() {
        return this.au;
    }

    public Map<String, WayData> getWayData() {
        return this.av;
    }

    public Map<String, Integer> getVictimBossData() {
        return this.aw;
    }

    static {
        a.setValidating(false);
        a.setIgnoringComments(true);
    }
}
