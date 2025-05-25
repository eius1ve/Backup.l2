/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.dom4j.Element
 */
package l2.gameserver.data.xml.parser;

import java.io.File;
import java.util.Iterator;
import l2.commons.data.xml.AbstractFileParser;
import l2.commons.geometry.Polygon;
import l2.gameserver.Config;
import l2.gameserver.data.xml.parser.ZoneParser;
import l2.gameserver.instancemanager.MapRegionManager;
import l2.gameserver.model.Territory;
import l2.gameserver.templates.mapregion.DomainArea;
import org.dom4j.Element;

public class DomainParser
extends AbstractFileParser<MapRegionManager> {
    private static final DomainParser a = new DomainParser();

    public static DomainParser getInstance() {
        return a;
    }

    protected DomainParser() {
        super(MapRegionManager.getInstance());
    }

    @Override
    public File getXMLFile() {
        return new File(Config.DATAPACK_ROOT, "data/mapregion/domains.xml");
    }

    @Override
    public String getDTDFileName() {
        return "domains.dtd";
    }

    @Override
    protected void readData(Element element) throws Exception {
        Iterator iterator = element.elementIterator();
        while (iterator.hasNext()) {
            Element element2 = (Element)iterator.next();
            if (!"domain".equals(element2.getName())) continue;
            int n = Integer.parseInt(element2.attributeValue("id"));
            Territory territory = null;
            Iterator iterator2 = element2.elementIterator();
            while (iterator2.hasNext()) {
                Element element3 = (Element)iterator2.next();
                if (!"polygon".equalsIgnoreCase(element3.getName())) continue;
                Polygon polygon = ZoneParser.parsePolygon(element3);
                if (!polygon.validate()) {
                    this.error("DomainParser: invalid territory data : " + polygon + "!");
                }
                if (territory == null) {
                    territory = new Territory();
                }
                territory.add(polygon);
            }
            if (territory == null) {
                throw new RuntimeException("DomainParser: empty territory!");
            }
            ((MapRegionManager)this.getHolder()).addRegionData(new DomainArea(n, territory));
        }
    }
}
