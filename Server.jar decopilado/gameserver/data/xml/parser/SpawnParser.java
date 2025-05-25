/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.dom4j.Element
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.data.xml.parser;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import l2.commons.collections.MultiValueSet;
import l2.commons.data.xml.AbstractDirParser;
import l2.commons.geometry.AbstractShape;
import l2.commons.geometry.Point2D;
import l2.commons.geometry.Polygon;
import l2.commons.time.cron.AddPattern;
import l2.commons.time.cron.NextTime;
import l2.commons.time.cron.SchedulingPattern;
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.SpawnHolder;
import l2.gameserver.model.Territory;
import l2.gameserver.templates.StatsSet;
import l2.gameserver.templates.spawn.PeriodOfDay;
import l2.gameserver.templates.spawn.SpawnNpcInfo;
import l2.gameserver.templates.spawn.SpawnTemplate;
import l2.gameserver.utils.Location;
import l2.gameserver.utils.SpawnMesh;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class SpawnParser
extends AbstractDirParser<SpawnHolder> {
    private static final Logger ba = LoggerFactory.getLogger(SpawnParser.class);
    private static final SpawnParser a = new SpawnParser();

    public static SpawnParser getInstance() {
        return a;
    }

    protected SpawnParser() {
        super(SpawnHolder.getInstance());
    }

    @Override
    public File getXMLDir() {
        return new File(Config.DATAPACK_ROOT, "data/spawn/");
    }

    @Override
    public boolean isIgnored(File file) {
        return false;
    }

    @Override
    public String getDTDFileName() {
        return "spawn.dtd";
    }

    @Override
    protected void readData(Element element) throws Exception {
        Iterator iterator = element.elementIterator();
        while (iterator.hasNext()) {
            Element element2 = (Element)iterator.next();
            if (!"spawn".equalsIgnoreCase(element2.getName())) continue;
            String string = element2.attributeValue("name");
            String string2 = element2.attributeValue("event_name");
            SpawnMesh spawnMesh = null;
            Iterator iterator2 = element2.elementIterator();
            while (iterator2.hasNext()) {
                Object object;
                Object object2;
                NextTime nextTime;
                long l;
                long l2;
                int n;
                int n2;
                Element element3;
                block17: {
                    element3 = (Element)iterator2.next();
                    if ("mesh".equalsIgnoreCase(element3.getName())) {
                        spawnMesh = this.a(element3);
                        continue;
                    }
                    if (!"npc".equalsIgnoreCase(element3.getName())) continue;
                    n2 = Integer.parseInt(element3.attributeValue("id", "0"));
                    n = Integer.parseInt(element3.attributeValue("count"));
                    l2 = Long.parseLong(element3.attributeValue("respawn", "60"));
                    l = Long.parseLong(element3.attributeValue("respawn_rand", "0"));
                    if (l > l2) {
                        throw new RuntimeException("Invalid respawn respawn_rand > respawn of " + element2.asXML());
                    }
                    String string3 = element3.attributeValue("respawn_cron");
                    nextTime = null;
                    if (string3 != null) {
                        try {
                            nextTime = new SchedulingPattern(string3);
                        }
                        catch (SchedulingPattern.InvalidPatternException invalidPatternException) {
                            try {
                                nextTime = new AddPattern(string3);
                            }
                            catch (Exception exception) {
                                throw new RuntimeException("Invalid respawn data of " + element2.asXML(), exception);
                            }
                            if (nextTime != null) break block17;
                            throw new RuntimeException("Invalid respawn data of " + element2.asXML(), invalidPatternException);
                        }
                    }
                }
                PeriodOfDay periodOfDay = PeriodOfDay.valueOf(element3.attributeValue("period_of_day", PeriodOfDay.ALL.name()));
                Location location = null;
                if (element3.attributeValue("pos") != null) {
                    location = Location.parseLoc(element3.attributeValue("pos"));
                } else if (spawnMesh == null) {
                    throw new RuntimeException("Neither mesh nor pos defined " + element2.asXML());
                }
                MultiValueSet multiValueSet = StatsSet.EMPTY;
                Iterator iterator3 = element3.elementIterator();
                while (iterator3.hasNext()) {
                    object2 = (Element)iterator3.next();
                    if (!"ai_params".equalsIgnoreCase(object2.getName())) continue;
                    object = object2.elementIterator();
                    while (object.hasNext()) {
                        Element element4 = (Element)object.next();
                        if (!"set".equalsIgnoreCase(element4.getName())) continue;
                        if (multiValueSet == StatsSet.EMPTY) {
                            multiValueSet = new MultiValueSet();
                        }
                        multiValueSet.set(element4.attributeValue("name"), element4.attributeValue("val"));
                    }
                }
                try {
                    object2 = new SpawnTemplate(string, string2, periodOfDay, n, l2, l, nextTime);
                    object = new SpawnNpcInfo(n2, n, multiValueSet);
                    ((SpawnTemplate)object2).addNpc((SpawnNpcInfo)object);
                    ((SpawnTemplate)object2).addSpawnRange(location != null ? location : spawnMesh);
                    ((SpawnHolder)this.getHolder()).addSpawn(string2 != null ? string2 : PeriodOfDay.ALL.name(), (SpawnTemplate)object2);
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    private Territory a(String string, Element element) {
        Territory territory = new Territory();
        territory.add(this.a(string, element));
        Iterator iterator = element.elementIterator("banned_territory");
        while (iterator.hasNext()) {
            territory.addBanned(this.a(string, (Element)iterator.next()));
        }
        return territory;
    }

    private Polygon a(String string, Element element) {
        Polygon polygon = new Polygon();
        Iterator iterator = element.elementIterator("add");
        while (iterator.hasNext()) {
            Element element2 = (Element)iterator.next();
            int n = Integer.parseInt(element2.attributeValue("x"));
            int n2 = Integer.parseInt(element2.attributeValue("y"));
            int n3 = Integer.parseInt(element2.attributeValue("zmin"));
            int n4 = Integer.parseInt(element2.attributeValue("zmax"));
            polygon.add(n, n2).setZmin(n3).setZmax(n4);
        }
        if (!polygon.validate()) {
            this.error("Invalid polygon: " + string + "{" + polygon + "}. File: " + this.getCurrentFileName());
        }
        return polygon;
    }

    private SpawnMesh a(Element element) {
        Object object;
        int n = Short.MAX_VALUE;
        int n2 = Short.MIN_VALUE;
        Iterator iterator = element.elementIterator("vertex");
        LinkedList<Point2D> linkedList = new LinkedList<Point2D>();
        while (iterator.hasNext()) {
            object = (Element)iterator.next();
            int n3 = Integer.parseInt(object.attributeValue("x"));
            int n4 = Integer.parseInt(object.attributeValue("y"));
            n = (short)Math.min(n, Short.parseShort(object.attributeValue("minz")));
            n2 = (short)Math.max(n2, Short.parseShort(object.attributeValue("maxz")));
            linkedList.add(new Point2D(n3, n4));
        }
        object = new SpawnMesh();
        for (Point2D point2D : linkedList) {
            ((Polygon)object).add(point2D.getX(), point2D.getY());
        }
        assert (n2 >= n);
        ((Polygon)object).setZmax(n2);
        ((Polygon)object).setZmin(n);
        if (!((Polygon)object).validate() || ((AbstractShape)object).getZmin() > ((AbstractShape)object).getZmax()) {
            throw new RuntimeException("Invalid spawn mesh " + (SpawnMesh)object + " defined for the node " + element.asXML());
        }
        return object;
    }
}
