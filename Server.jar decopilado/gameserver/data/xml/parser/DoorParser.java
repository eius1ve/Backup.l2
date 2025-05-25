/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.dom4j.Element
 */
package l2.gameserver.data.xml.parser;

import java.io.File;
import java.util.Iterator;
import l2.commons.data.xml.AbstractDirParser;
import l2.commons.geometry.Polygon;
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.DoorHolder;
import l2.gameserver.templates.DoorTemplate;
import l2.gameserver.templates.StatsSet;
import l2.gameserver.utils.Location;
import org.dom4j.Element;

public final class DoorParser
extends AbstractDirParser<DoorHolder> {
    private static final DoorParser a = new DoorParser();

    public static DoorParser getInstance() {
        return a;
    }

    protected DoorParser() {
        super(DoorHolder.getInstance());
    }

    @Override
    public File getXMLDir() {
        return new File(Config.DATAPACK_ROOT, "data/doors/");
    }

    @Override
    public boolean isIgnored(File file) {
        return false;
    }

    @Override
    public String getDTDFileName() {
        return "doors.dtd";
    }

    private StatsSet a() {
        StatsSet statsSet = new StatsSet();
        statsSet.set("level", 0);
        statsSet.set("baseSTR", 0);
        statsSet.set("baseCON", 0);
        statsSet.set("baseDEX", 0);
        statsSet.set("baseINT", 0);
        statsSet.set("baseWIT", 0);
        statsSet.set("baseMEN", 0);
        statsSet.set("baseShldDef", 0);
        statsSet.set("baseShldRate", 0);
        statsSet.set("baseAccCombat", 38);
        statsSet.set("baseEvasRate", 38);
        statsSet.set("baseCritRate", 38);
        statsSet.set("baseAtkRange", 0);
        statsSet.set("baseMpMax", 0);
        statsSet.set("baseCpMax", 0);
        statsSet.set("basePAtk", 0);
        statsSet.set("baseMAtk", 0);
        statsSet.set("basePAtkSpd", 0);
        statsSet.set("baseMAtkSpd", 0);
        statsSet.set("baseWalkSpd", 0);
        statsSet.set("baseRunSpd", 0);
        statsSet.set("baseHpReg", 0);
        statsSet.set("baseCpReg", 0);
        statsSet.set("baseMpReg", 0);
        return statsSet;
    }

    @Override
    protected void readData(Element element) throws Exception {
        Iterator iterator = element.elementIterator();
        while (iterator.hasNext()) {
            Element element2 = (Element)iterator.next();
            if (!"door".equals(element2.getName())) continue;
            StatsSet statsSet = this.a();
            StatsSet statsSet2 = null;
            statsSet.set("door_type", element2.attributeValue("type"));
            Element element3 = element2.element("pos");
            int n = Integer.parseInt(element3.attributeValue("x"));
            int n2 = Integer.parseInt(element3.attributeValue("y"));
            int n3 = Integer.parseInt(element3.attributeValue("z"));
            Location location = new Location(n, n2, n3);
            statsSet.set("pos", location);
            Polygon polygon = new Polygon();
            int n4 = 0;
            int n5 = 0;
            Element element4 = element2.element("shape");
            n4 = Integer.parseInt(element4.attributeValue("minz"));
            n5 = Integer.parseInt(element4.attributeValue("maxz"));
            polygon.add(Integer.parseInt(element4.attributeValue("ax")), Integer.parseInt(element4.attributeValue("ay")));
            polygon.add(Integer.parseInt(element4.attributeValue("bx")), Integer.parseInt(element4.attributeValue("by")));
            polygon.add(Integer.parseInt(element4.attributeValue("cx")), Integer.parseInt(element4.attributeValue("cy")));
            polygon.add(Integer.parseInt(element4.attributeValue("dx")), Integer.parseInt(element4.attributeValue("dy")));
            polygon.setZmin(n4);
            polygon.setZmax(n5);
            statsSet.set("shape", polygon);
            location.setZ(n4 + 32);
            Object object = element2.elementIterator();
            while (object.hasNext()) {
                Element element5 = (Element)object.next();
                if ("set".equals(element5.getName())) {
                    statsSet.set(element5.attributeValue("name"), element5.attributeValue("value"));
                    continue;
                }
                if (!"ai_params".equals(element5.getName())) continue;
                if (statsSet2 == null) {
                    statsSet2 = new StatsSet();
                    statsSet.set("ai_params", statsSet2);
                }
                Iterator iterator2 = element5.elementIterator();
                while (iterator2.hasNext()) {
                    Element element6 = (Element)iterator2.next();
                    statsSet2.set(element6.attributeValue("name"), element6.attributeValue("value"));
                }
            }
            statsSet.set("uid", element2.attributeValue("id"));
            statsSet.set("name", element2.attributeValue("name"));
            statsSet.set("baseHpMax", element2.attributeValue("hp"));
            statsSet.set("basePDef", element2.attributeValue("pdef"));
            statsSet.set("baseMDef", element2.attributeValue("mdef"));
            statsSet.set("collision_height", n5 - n4 & 0xFFF0);
            statsSet.set("collision_radius", Math.max(50, Math.min(location.x - polygon.getXmin(), location.y - polygon.getYmin())));
            object = new DoorTemplate(statsSet);
            ((DoorHolder)this.getHolder()).addTemplate((DoorTemplate)object);
        }
    }
}
