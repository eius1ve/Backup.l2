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
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.EnsoulOptionHolder;
import l2.gameserver.model.Skill;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.templates.item.support.EnsoulFeeSlotType;
import l2.gameserver.templates.item.support.EnsoulOption;
import org.dom4j.Element;

public class EnsoulOptionParser
extends AbstractFileParser<EnsoulOptionHolder> {
    private static final EnsoulOptionParser a = new EnsoulOptionParser();
    private static final File e = new File(Config.DATAPACK_ROOT, "data/ensoul_options.xml");
    private static final String bJ = "ensoul_options.dtd";

    private EnsoulOptionParser() {
        super(EnsoulOptionHolder.getInstance());
    }

    public static EnsoulOptionParser getInstance() {
        return a;
    }

    @Override
    public File getXMLFile() {
        return e;
    }

    @Override
    public String getDTDFileName() {
        return bJ;
    }

    @Override
    protected void readData(Element element) throws Exception {
        Iterator iterator = element.elementIterator();
        while (iterator.hasNext()) {
            Element element2 = (Element)iterator.next();
            int n = Integer.parseInt(element2.attributeValue("id"));
            int n2 = Integer.parseInt(element2.attributeValue("stoneId"));
            int n3 = Integer.parseInt(element2.attributeValue("skillId", "0"));
            int n4 = Integer.parseInt(element2.attributeValue("skillLvl", "0"));
            int n5 = Integer.parseInt(element2.attributeValue("optionType"));
            EnsoulFeeSlotType ensoulFeeSlotType = EnsoulFeeSlotType.getSlotType(Integer.parseInt(element2.attributeValue("slotType")));
            Skill skill = n3 > 0 && n4 > 0 ? SkillTable.getInstance().getInfo(n3, n4) : null;
            ((EnsoulOptionHolder)this.getHolder()).add(new EnsoulOption(n, n2, skill, n5, ensoulFeeSlotType));
        }
        System.currentTimeMillis();
    }
}
