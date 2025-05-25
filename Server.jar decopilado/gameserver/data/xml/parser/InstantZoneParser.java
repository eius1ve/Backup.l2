/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.dom4j.Element
 *  org.napile.primitive.Containers
 *  org.napile.primitive.maps.IntObjectMap
 *  org.napile.primitive.maps.impl.HashIntObjectMap
 */
package l2.gameserver.data.xml.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import l2.commons.data.xml.AbstractDirParser;
import l2.commons.geometry.Polygon;
import l2.commons.geometry.Shape;
import l2.commons.time.cron.SchedulingPattern;
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.DoorHolder;
import l2.gameserver.data.xml.holder.InstantZoneHolder;
import l2.gameserver.data.xml.holder.SpawnHolder;
import l2.gameserver.data.xml.holder.ZoneHolder;
import l2.gameserver.model.Territory;
import l2.gameserver.templates.DoorTemplate;
import l2.gameserver.templates.InstantZone;
import l2.gameserver.templates.StatsSet;
import l2.gameserver.templates.ZoneTemplate;
import l2.gameserver.templates.spawn.SpawnTemplate;
import l2.gameserver.utils.Location;
import org.dom4j.Element;
import org.napile.primitive.Containers;
import org.napile.primitive.maps.IntObjectMap;
import org.napile.primitive.maps.impl.HashIntObjectMap;

public class InstantZoneParser
extends AbstractDirParser<InstantZoneHolder> {
    private static final InstantZoneParser a = new InstantZoneParser();

    public InstantZoneParser() {
        super(InstantZoneHolder.getInstance());
    }

    public static InstantZoneParser getInstance() {
        return a;
    }

    @Override
    public File getXMLDir() {
        return new File(Config.DATAPACK_ROOT, "data/instances/");
    }

    @Override
    public boolean isIgnored(File file) {
        return false;
    }

    @Override
    public String getDTDFileName() {
        return "instances.dtd";
    }

    @Override
    protected void readData(Element element) throws Exception {
        Iterator iterator = element.elementIterator();
        while (iterator.hasNext()) {
            Element element2 = (Element)iterator.next();
            SchedulingPattern schedulingPattern = new SchedulingPattern("30 6 * * *");
            int n = -1;
            int n2 = 60;
            int n3 = -1;
            int n4 = -1;
            boolean bl = false;
            boolean bl2 = true;
            int n5 = 0;
            int n6 = 0;
            int n7 = 0;
            InstantZone.SpawnInfo spawnInfo = null;
            int n8 = 0;
            int n9 = 0;
            int n10 = 0;
            int n11 = 0;
            int n12 = 0;
            int n13 = 20;
            boolean bl3 = false;
            boolean bl4 = true;
            StatsSet statsSet = new StatsSet();
            ArrayList<InstantZone.SpawnInfo> arrayList = new ArrayList<InstantZone.SpawnInfo>();
            IntObjectMap intObjectMap = Containers.emptyIntObjectMap();
            Map<String, InstantZone.ZoneInfo> map = Collections.emptyMap();
            Map<String, InstantZone.SpawnInfo2> map2 = Collections.emptyMap();
            int n14 = Integer.parseInt(element2.attributeValue("id"));
            String string = element2.attributeValue("name");
            String string2 = element2.attributeValue("timelimit");
            if (string2 != null) {
                n = Integer.parseInt(string2);
            }
            string2 = element2.attributeValue("collapseIfEmpty");
            n6 = Integer.parseInt(string2);
            string2 = element2.attributeValue("maxChannels");
            n13 = Integer.parseInt(string2);
            string2 = element2.attributeValue("dispelBuffs");
            bl = string2 != null && Boolean.parseBoolean(string2);
            int n15 = 0;
            int n16 = 0;
            int n17 = 1;
            int n18 = 9;
            List<Location> list = Collections.emptyList();
            Location location = null;
            Object object = element2.elementIterator();
            while (object.hasNext()) {
                List<SpawnTemplate> list2;
                Element element3 = (Element)object.next();
                if ("level".equalsIgnoreCase(element3.getName())) {
                    n15 = Integer.parseInt(element3.attributeValue("min"));
                    n16 = Integer.parseInt(element3.attributeValue("max"));
                    continue;
                }
                if ("collapse".equalsIgnoreCase(element3.getName())) {
                    bl2 = Boolean.parseBoolean(element3.attributeValue("on-party-dismiss"));
                    n2 = Integer.parseInt(element3.attributeValue("timer"));
                    continue;
                }
                if ("party".equalsIgnoreCase(element3.getName())) {
                    n17 = Integer.parseInt(element3.attributeValue("min"));
                    n18 = Integer.parseInt(element3.attributeValue("max"));
                    continue;
                }
                if ("return".equalsIgnoreCase(element3.getName())) {
                    location = Location.parseLoc(element3.attributeValue("loc"));
                    continue;
                }
                if ("teleport".equalsIgnoreCase(element3.getName())) {
                    if (list.isEmpty()) {
                        list = new ArrayList<Location>(1);
                    }
                    list.add(Location.parseLoc(element3.attributeValue("loc")));
                    continue;
                }
                if ("remove".equalsIgnoreCase(element3.getName())) {
                    n8 = Integer.parseInt(element3.attributeValue("itemId"));
                    n9 = Integer.parseInt(element3.attributeValue("count"));
                    bl3 = Boolean.parseBoolean(element3.attributeValue("necessary"));
                    continue;
                }
                if ("give".equalsIgnoreCase(element3.getName())) {
                    n10 = Integer.parseInt(element3.attributeValue("itemId"));
                    n11 = Integer.parseInt(element3.attributeValue("count"));
                    continue;
                }
                if ("quest".equalsIgnoreCase(element3.getName())) {
                    n12 = Integer.parseInt(element3.attributeValue("id"));
                    continue;
                }
                if ("reuse".equalsIgnoreCase(element3.getName())) {
                    schedulingPattern = new SchedulingPattern(element3.attributeValue("resetReuse"));
                    n5 = Integer.parseInt(element3.attributeValue("sharedReuseGroup"));
                    bl4 = Boolean.parseBoolean(element3.attributeValue("setUponEntry"));
                    continue;
                }
                if ("geodata".equalsIgnoreCase(element3.getName())) {
                    String[] stringArray = element3.attributeValue("map").split("_");
                    n3 = Integer.parseInt(stringArray[0]);
                    n4 = Integer.parseInt((String)stringArray[1]);
                    continue;
                }
                if ("doors".equalsIgnoreCase(element3.getName())) {
                    for (Element element4 : element3.elements()) {
                        if (intObjectMap.isEmpty()) {
                            intObjectMap = new HashIntObjectMap();
                        }
                        boolean bl5 = element4.attributeValue("opened") != null && Boolean.parseBoolean(element4.attributeValue("opened"));
                        boolean bl6 = element4.attributeValue("invul") == null || Boolean.parseBoolean(element4.attributeValue("invul"));
                        list2 = DoorHolder.getInstance().getTemplate(Integer.parseInt(element4.attributeValue("id")));
                        intObjectMap.put(((DoorTemplate)((Object)list2)).getNpcId(), (Object)new InstantZone.DoorInfo((DoorTemplate)((Object)list2), bl5, bl6));
                    }
                    continue;
                }
                if ("zones".equalsIgnoreCase(element3.getName())) {
                    for (Element element4 : element3.elements()) {
                        if (map.isEmpty()) {
                            map = new HashMap<String, InstantZone.ZoneInfo>();
                        }
                        boolean bl7 = element4.attributeValue("active") != null && Boolean.parseBoolean(element4.attributeValue("active"));
                        ZoneTemplate zoneTemplate = ZoneHolder.getInstance().getTemplate(element4.attributeValue("name"));
                        if (zoneTemplate == null) {
                            this.error("Zone: " + element4.attributeValue("name") + " not found; file: " + this.getCurrentFileName());
                            continue;
                        }
                        map.put(zoneTemplate.getName(), new InstantZone.ZoneInfo(zoneTemplate, bl7));
                    }
                    continue;
                }
                if ("add_parameters".equalsIgnoreCase(element3.getName())) {
                    for (Element element4 : element3.elements()) {
                        if (!"param".equalsIgnoreCase(element4.getName())) continue;
                        statsSet.set(element4.attributeValue("name"), element4.attributeValue("value"));
                    }
                    continue;
                }
                if (!"spawns".equalsIgnoreCase(element3.getName())) continue;
                for (Element element4 : element3.elements()) {
                    String[] stringArray;
                    if ("group".equalsIgnoreCase(element4.getName())) {
                        stringArray = element4.attributeValue("name");
                        boolean bl8 = element4.attributeValue("spawned") != null && Boolean.parseBoolean(element4.attributeValue("spawned"));
                        list2 = SpawnHolder.getInstance().getSpawn((String)stringArray);
                        if (list2 == null) {
                            this.info("not find spawn group: " + (String)stringArray + " in file: " + this.getCurrentFileName());
                            continue;
                        }
                        if (map2.isEmpty()) {
                            map2 = new Hashtable<String, InstantZone.SpawnInfo2>();
                        }
                        map2.put((String)stringArray, new InstantZone.SpawnInfo2(list2, bl8));
                        continue;
                    }
                    if (!"spawn".equalsIgnoreCase(element4.getName())) continue;
                    stringArray = element4.attributeValue("mobId").split(" ");
                    String string3 = element4.attributeValue("respawn");
                    int n19 = string3 != null ? Integer.parseInt(string3) : 0;
                    list2 = element4.attributeValue("respawnRnd");
                    int n20 = list2 != null ? Integer.parseInt((String)((Object)list2)) : 0;
                    String string4 = element4.attributeValue("count");
                    int n21 = string4 != null ? Integer.parseInt(string4) : 1;
                    ArrayList<Location> arrayList2 = new ArrayList<Location>();
                    n7 = 0;
                    String string5 = element4.attributeValue("type");
                    if (string5 == null || string5.equalsIgnoreCase("point")) {
                        n7 = 0;
                    } else if (string5.equalsIgnoreCase("rnd")) {
                        n7 = 1;
                    } else if (string5.equalsIgnoreCase("loc")) {
                        n7 = 2;
                    } else {
                        this.error("Spawn type  '" + string5 + "' is unknown!");
                    }
                    for (Object object2 : element4.elements()) {
                        if (!"coords".equalsIgnoreCase(object2.getName())) continue;
                        arrayList2.add(Location.parseLoc(object2.attributeValue("loc")));
                    }
                    Object object3 = null;
                    if (n7 == 2) {
                        Object object2;
                        object2 = new Polygon();
                        for (Location location2 : arrayList2) {
                            ((Polygon)object2).add(location2.x, location2.y).setZmin(location2.z).setZmax(location2.z);
                        }
                        if (!((Polygon)object2).validate()) {
                            this.error("invalid spawn territory for instance id : " + n14 + " - " + (Polygon)object2 + "!");
                        }
                        object3 = new Territory().add((Shape)object2);
                    }
                    for (String string6 : stringArray) {
                        int n22 = Integer.parseInt(string6);
                        spawnInfo = new InstantZone.SpawnInfo(n7, n22, n21, n19, n20, arrayList2, (Territory)object3);
                        arrayList.add(spawnInfo);
                    }
                }
            }
            object = new InstantZone.Builder().setInstanceId(n14).setName(string).setResetReuse(schedulingPattern).setSharedReuseGroup(n5).setTimelimit(n).setDispelBuffs(bl).setMinLevel(n15).setMaxLevel(n16).setMinParty(n17).setMaxParty(n18).setTimer(n2).setOnPartyDismiss(bl2).addTeleportLocs(list).setRet(location).setMapx(n3).setMapy(n4).setDoors((IntObjectMap<InstantZone.DoorInfo>)intObjectMap).setZones(map).setSpawns2(map2).setSpawns(arrayList).setCollapseIfEmpty(n6).setMaxChannels(n13).setRemovedItemId(n8).setRemovedItemCount(n9).setRemovedItemNecessity(bl3).setGiveItemId(n10).setGivedItemCount(n11).setRequiredQuestId(n12).setSetReuseUponEntry(bl4).setParams(statsSet).build();
            ((InstantZoneHolder)this.getHolder()).addInstantZone((InstantZone)object);
        }
    }
}
