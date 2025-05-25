/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.dom4j.Element
 */
package l2.gameserver.data.xml.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import l2.commons.data.xml.AbstractDirParser;
import l2.commons.geometry.Circle;
import l2.commons.geometry.Polygon;
import l2.commons.geometry.Rectangle;
import l2.commons.geometry.Shape;
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.ZoneHolder;
import l2.gameserver.model.Territory;
import l2.gameserver.model.World;
import l2.gameserver.templates.StatsSet;
import l2.gameserver.templates.ZoneTemplate;
import l2.gameserver.utils.Location;
import org.dom4j.Element;

public class ZoneParser
extends AbstractDirParser<ZoneHolder> {
    private static final ZoneParser a = new ZoneParser();

    public static ZoneParser getInstance() {
        return a;
    }

    protected ZoneParser() {
        super(ZoneHolder.getInstance());
    }

    @Override
    public File getXMLDir() {
        return new File(Config.DATAPACK_ROOT, "data/zone/");
    }

    @Override
    public boolean isIgnored(File file) {
        return false;
    }

    @Override
    public String getDTDFileName() {
        return "zone.dtd";
    }

    @Override
    protected void readData(Element element) throws Exception {
        Iterator iterator = element.elementIterator();
        while (iterator.hasNext()) {
            StatsSet statsSet = new StatsSet();
            Element element2 = (Element)iterator.next();
            if (!"zone".equals(element2.getName())) continue;
            statsSet.set("name", element2.attribute("name").getValue());
            statsSet.set("type", element2.attribute("type").getValue());
            Territory territory = null;
            Object object = element2.elementIterator();
            while (object.hasNext()) {
                Location location;
                Element element3;
                Iterator iterator2;
                Object object2;
                Element element4 = (Element)object.next();
                if ("set".equals(element4.getName())) {
                    statsSet.set(element4.attributeValue("name"), element4.attributeValue("val"));
                    continue;
                }
                if ("restart_point".equals(element4.getName())) {
                    object2 = new ArrayList();
                    iterator2 = element4.elementIterator();
                    while (iterator2.hasNext()) {
                        element3 = (Element)iterator2.next();
                        if (!"coords".equalsIgnoreCase(element3.getName())) continue;
                        location = Location.parseLoc(element3.attribute("loc").getValue());
                        object2.add(location);
                    }
                    statsSet.set("restart_points", (Collection<?>)object2);
                    continue;
                }
                if ("PKrestart_point".equals(element4.getName())) {
                    object2 = new ArrayList();
                    iterator2 = element4.elementIterator();
                    while (iterator2.hasNext()) {
                        element3 = (Element)iterator2.next();
                        if (!"coords".equalsIgnoreCase(element3.getName())) continue;
                        location = Location.parseLoc(element3.attribute("loc").getValue());
                        object2.add(location);
                    }
                    statsSet.set("PKrestart_points", (Collection<?>)object2);
                    continue;
                }
                boolean bl = "rectangle".equalsIgnoreCase(element4.getName());
                if (bl || "banned_rectangle".equalsIgnoreCase(element4.getName())) {
                    object2 = ZoneParser.parseRectangle(element4);
                    if (territory == null) {
                        territory = new Territory();
                        statsSet.set("territory", territory);
                    }
                    if (bl) {
                        territory.add((Shape)object2);
                        continue;
                    }
                    territory.addBanned((Shape)object2);
                    continue;
                }
                bl = "circle".equalsIgnoreCase(element4.getName());
                if (bl || "banned_cicrcle".equalsIgnoreCase(element4.getName())) {
                    object2 = ZoneParser.parseCircle(element4);
                    if (territory == null) {
                        territory = new Territory();
                        statsSet.set("territory", territory);
                    }
                    if (bl) {
                        territory.add((Shape)object2);
                        continue;
                    }
                    territory.addBanned((Shape)object2);
                    continue;
                }
                bl = "polygon".equalsIgnoreCase(element4.getName());
                if (!bl && !"banned_polygon".equalsIgnoreCase(element4.getName())) continue;
                object2 = ZoneParser.parsePolygon(element4);
                if (!((Polygon)object2).validate()) {
                    this.error("ZoneParser: invalid territory data : " + (Polygon)object2 + ", zone: " + statsSet.getString("name") + "!");
                }
                if (territory == null) {
                    territory = new Territory();
                    statsSet.set("territory", territory);
                }
                if (bl) {
                    territory.add((Shape)object2);
                    continue;
                }
                territory.addBanned((Shape)object2);
            }
            if (territory == null || territory.getTerritories().isEmpty()) {
                this.error("Empty territory for zone: " + statsSet.get("name"));
            }
            object = new ZoneTemplate(statsSet);
            ((ZoneHolder)this.getHolder()).addTemplate((ZoneTemplate)object);
        }
    }

    public static Rectangle parseRectangle(Element element) throws Exception {
        int n = World.MAP_MIN_Z;
        int n2 = World.MAP_MAX_Z;
        Iterator iterator = element.elementIterator();
        Element element2 = (Element)iterator.next();
        String[] stringArray = element2.attributeValue("loc").split("[\\s,;]+");
        int n3 = Integer.parseInt(stringArray[0]);
        int n4 = Integer.parseInt(stringArray[1]);
        if (stringArray.length > 2) {
            n = Integer.parseInt(stringArray[2]);
            n2 = Integer.parseInt(stringArray[3]);
        }
        element2 = (Element)iterator.next();
        stringArray = element2.attributeValue("loc").split("[\\s,;]+");
        int n5 = Integer.parseInt(stringArray[0]);
        int n6 = Integer.parseInt(stringArray[1]);
        if (stringArray.length > 2) {
            n = Integer.parseInt(stringArray[2]);
            n2 = Integer.parseInt(stringArray[3]);
        }
        Rectangle rectangle = new Rectangle(n3, n4, n5, n6);
        rectangle.setZmin(n);
        rectangle.setZmax(n2);
        return rectangle;
    }

    public static Polygon parsePolygon(Element element) throws Exception {
        Polygon polygon = new Polygon();
        Iterator iterator = element.elementIterator();
        while (iterator.hasNext()) {
            Element element2 = (Element)iterator.next();
            if (!"coords".equals(element2.getName())) continue;
            String[] stringArray = element2.attributeValue("loc").split("[\\s,;]+");
            if (stringArray.length < 4) {
                polygon.add(Integer.parseInt(stringArray[0]), Integer.parseInt(stringArray[1])).setZmin(World.MAP_MIN_Z).setZmax(World.MAP_MAX_Z);
                continue;
            }
            polygon.add(Integer.parseInt(stringArray[0]), Integer.parseInt(stringArray[1])).setZmin(Integer.parseInt(stringArray[2])).setZmax(Integer.parseInt(stringArray[3]));
        }
        return polygon;
    }

    public static Circle parseCircle(Element element) throws Exception {
        String[] stringArray = element.attribute("loc").getValue().split("[\\s,;]+");
        Circle circle = stringArray.length < 5 ? new Circle(Integer.parseInt(stringArray[0]), Integer.parseInt(stringArray[1]), Integer.parseInt(stringArray[2])).setZmin(World.MAP_MIN_Z).setZmax(World.MAP_MAX_Z) : new Circle(Integer.parseInt(stringArray[0]), Integer.parseInt(stringArray[1]), Integer.parseInt(stringArray[2])).setZmin(Integer.parseInt(stringArray[3])).setZmax(Integer.parseInt(stringArray[4]));
        return circle;
    }
}
