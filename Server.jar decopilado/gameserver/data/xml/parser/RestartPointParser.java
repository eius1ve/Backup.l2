/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.tuple.ImmutablePair
 *  org.apache.commons.lang3.tuple.Pair
 *  org.dom4j.Element
 */
package l2.gameserver.data.xml.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import l2.commons.data.xml.AbstractFileParser;
import l2.commons.geometry.Polygon;
import l2.commons.geometry.Rectangle;
import l2.commons.geometry.Shape;
import l2.gameserver.Config;
import l2.gameserver.data.xml.parser.ZoneParser;
import l2.gameserver.instancemanager.MapRegionManager;
import l2.gameserver.model.Territory;
import l2.gameserver.model.World;
import l2.gameserver.model.base.Race;
import l2.gameserver.templates.mapregion.RestartArea;
import l2.gameserver.templates.mapregion.RestartPoint;
import l2.gameserver.utils.Location;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.dom4j.Element;

public class RestartPointParser
extends AbstractFileParser<MapRegionManager> {
    private static final RestartPointParser a = new RestartPointParser();

    public static RestartPointParser getInstance() {
        return a;
    }

    private RestartPointParser() {
        super(MapRegionManager.getInstance());
    }

    @Override
    public File getXMLFile() {
        return new File(Config.DATAPACK_ROOT, "data/mapregion/restart_points.xml");
    }

    @Override
    public String getDTDFileName() {
        return "restart_points.dtd";
    }

    @Override
    protected void readData(Element element) throws Exception {
        Object object;
        Object object2;
        ArrayList<ImmutablePair> arrayList = new ArrayList<ImmutablePair>();
        HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
        Iterator iterator = element.elementIterator();
        while (iterator.hasNext()) {
            Object object3;
            Object object4;
            String string;
            Object object5;
            Element element2 = (Element)iterator.next();
            if ("restart_area".equals(element2.getName())) {
                object2 = null;
                HashMap hashMap2 = new HashMap();
                Iterator iterator2 = element2.elementIterator();
                while (iterator2.hasNext()) {
                    object = (Element)iterator2.next();
                    if ("region".equalsIgnoreCase(object.getName())) {
                        object5 = object.attribute("map");
                        string = object5.getValue();
                        object4 = string.split("_");
                        int n = Integer.parseInt(object4[0]);
                        int n2 = Integer.parseInt(object4[1]);
                        int n3 = World.MAP_MIN_X + (n - Config.GEO_X_FIRST << 15);
                        int n4 = World.MAP_MIN_Y + (n2 - Config.GEO_Y_FIRST << 15);
                        int n5 = n3 + 32768 - 1;
                        int n6 = n4 + 32768 - 1;
                        object3 = new Rectangle(n3, n4, n5, n6);
                        ((Rectangle)object3).setZmin(World.MAP_MIN_Z);
                        ((Rectangle)object3).setZmax(World.MAP_MAX_Z);
                        if (object2 == null) {
                            object2 = new Territory();
                        }
                        ((Territory)object2).add((Shape)object3);
                        continue;
                    }
                    if ("polygon".equalsIgnoreCase(object.getName())) {
                        object3 = ZoneParser.parsePolygon((Element)object);
                        if (!((Polygon)object3).validate()) {
                            this.error("RestartPointParser: invalid territory data : " + (Polygon)object3 + "!");
                        }
                        if (object2 == null) {
                            object2 = new Territory();
                        }
                        ((Territory)object2).add((Shape)object3);
                        continue;
                    }
                    if (!"restart".equalsIgnoreCase(object.getName())) continue;
                    object3 = Race.valueOf(object.attributeValue("race"));
                    object5 = object.attributeValue("loc");
                    hashMap2.put(object3, object5);
                }
                if (object2 == null) {
                    throw new RuntimeException("RestartPointParser: empty territory!");
                }
                if (hashMap2.isEmpty()) {
                    throw new RuntimeException("RestartPointParser: restarts not defined!");
                }
                arrayList.add(new ImmutablePair(object2, (Object)hashMap2));
                continue;
            }
            if (!"restart_loc".equals(element2.getName())) continue;
            object2 = element2.attributeValue("name");
            int n = Integer.parseInt(element2.attributeValue("bbs", "0"));
            int n7 = Integer.parseInt(element2.attributeValue("msg_id", "0"));
            object = new ArrayList();
            object3 = new ArrayList();
            object5 = element2.elementIterator();
            while (object5.hasNext()) {
                Location location;
                Element element3;
                string = (Element)object5.next();
                if ("restart_point".equals(string.getName())) {
                    object4 = string.elementIterator();
                    while (object4.hasNext()) {
                        element3 = (Element)object4.next();
                        if (!"coords".equalsIgnoreCase(element3.getName())) continue;
                        location = Location.parseLoc(element3.attribute("loc").getValue());
                        object.add(location);
                    }
                    continue;
                }
                if (!"PKrestart_point".equals(string.getName())) continue;
                object4 = string.elementIterator();
                while (object4.hasNext()) {
                    element3 = (Element)object4.next();
                    if (!"coords".equalsIgnoreCase(element3.getName())) continue;
                    location = Location.parseLoc(element3.attribute("loc").getValue());
                    object3.add(location);
                }
            }
            if (object.isEmpty()) {
                throw new RuntimeException("RestartPointParser: restart_points not defined for restart_loc : " + (String)object2 + "!");
            }
            if (object3.isEmpty()) {
                object3 = object;
            }
            object5 = new RestartPoint((String)object2, n, n7, (List<Location>)object, (List<Location>)object3);
            hashMap.put(object2, object5);
        }
        for (Pair pair : arrayList) {
            object2 = new HashMap();
            for (Map.Entry entry : ((Map)pair.getValue()).entrySet()) {
                object = (RestartPoint)hashMap.get(entry.getValue());
                if (object == null) {
                    throw new RuntimeException("RestartPointParser: restart_loc not found : " + (String)entry.getValue() + "!");
                }
                object2.put((Race)((Object)entry.getKey()), object);
                try {
                    ((MapRegionManager)this.getHolder()).addRegionData(new RestartArea((Territory)pair.getKey(), (Map<Race, RestartPoint>)object2));
                }
                catch (Exception exception) {
                    System.out.println("Cant add restart area \"" + (String)entry.getValue() + "\"");
                    exception.printStackTrace();
                }
            }
        }
    }
}
