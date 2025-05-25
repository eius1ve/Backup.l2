/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.TIntIntHashMap
 *  org.dom4j.Element
 */
package l2.gameserver.data.xml.parser;

import gnu.trove.TIntIntHashMap;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import l2.commons.data.xml.AbstractFileParser;
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.CubicHolder;
import l2.gameserver.model.Skill;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.templates.CubicTemplate;
import org.dom4j.Element;

public final class CubicParser
extends AbstractFileParser<CubicHolder> {
    private static CubicParser a = new CubicParser();

    public static CubicParser getInstance() {
        return a;
    }

    protected CubicParser() {
        super(CubicHolder.getInstance());
    }

    @Override
    public File getXMLFile() {
        return new File(Config.DATAPACK_ROOT, "data/cubics.xml");
    }

    @Override
    public String getDTDFileName() {
        return "cubics.dtd";
    }

    @Override
    protected void readData(Element element) throws Exception {
        Iterator iterator = element.elementIterator();
        while (iterator.hasNext()) {
            Element element2 = (Element)iterator.next();
            int n = Integer.parseInt(element2.attributeValue("id"));
            int n2 = Integer.parseInt(element2.attributeValue("level"));
            int n3 = Integer.parseInt(element2.attributeValue("delay"));
            CubicTemplate cubicTemplate = new CubicTemplate(n, n2, n3);
            ((CubicHolder)this.getHolder()).addCubicTemplate(cubicTemplate);
            Iterator iterator2 = element2.elementIterator();
            while (iterator2.hasNext()) {
                Element element3 = (Element)iterator2.next();
                int n4 = Integer.parseInt(element3.attributeValue("chance"));
                ArrayList<CubicTemplate.SkillInfo> arrayList = new ArrayList<CubicTemplate.SkillInfo>(1);
                Iterator iterator3 = element3.elementIterator();
                while (iterator3.hasNext()) {
                    Element element4 = (Element)iterator3.next();
                    int n5 = Integer.parseInt(element4.attributeValue("id"));
                    int n6 = Integer.parseInt(element4.attributeValue("level"));
                    String string = element4.attributeValue("chance");
                    int n7 = string == null ? 0 : Integer.parseInt(string);
                    boolean bl = Boolean.parseBoolean(element4.attributeValue("can_attack_door"));
                    string = element4.attributeValue("min_hp");
                    int n8 = string == null ? 0 : Integer.parseInt(string);
                    string = element4.attributeValue("min_hp_per");
                    int n9 = string == null ? 0 : Integer.parseInt(string);
                    CubicTemplate.ActionType actionType = CubicTemplate.ActionType.valueOf(element4.attributeValue("action_type"));
                    TIntIntHashMap tIntIntHashMap = new TIntIntHashMap();
                    Object object = element4.elementIterator();
                    while (object.hasNext()) {
                        Element element5 = (Element)object.next();
                        int n10 = Integer.parseInt(element5.attributeValue("min"));
                        int n11 = Integer.parseInt(element5.attributeValue("max"));
                        int n12 = Integer.parseInt(element5.attributeValue("value"));
                        for (int i = n10; i <= n11; ++i) {
                            tIntIntHashMap.put(i, n12);
                        }
                    }
                    if (n7 == 0 && tIntIntHashMap.isEmpty()) {
                        this.warn("Wrong skill chance. Cubic: " + n + "/" + n2);
                    }
                    if ((object = SkillTable.getInstance().getInfo(n5, n6)) == null) continue;
                    ((Skill)object).setCubicSkill(true);
                    arrayList.add(new CubicTemplate.SkillInfo((Skill)object, n7, actionType, bl, n8, n9, tIntIntHashMap));
                }
                cubicTemplate.putSkills(n4, arrayList);
            }
        }
    }
}
