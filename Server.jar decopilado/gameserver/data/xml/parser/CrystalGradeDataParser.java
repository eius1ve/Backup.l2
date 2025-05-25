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
import l2.gameserver.data.xml.holder.CrystalGradeDataHolder;
import org.dom4j.Element;

public final class CrystalGradeDataParser
extends AbstractFileParser<CrystalGradeDataHolder> {
    private static final CrystalGradeDataParser a = new CrystalGradeDataParser();

    private CrystalGradeDataParser() {
        super(CrystalGradeDataHolder.getInstance());
    }

    public static CrystalGradeDataParser getInstance() {
        return a;
    }

    @Override
    public File getXMLFile() {
        return new File(Config.DATAPACK_ROOT, "data/crystal_grades.xml");
    }

    @Override
    public String getDTDFileName() {
        return "crystal_grades.dtd";
    }

    @Override
    protected void readData(Element element) throws Exception {
        Iterator iterator = element.elementIterator();
        while (iterator.hasNext()) {
            Element element2 = (Element)iterator.next();
            String string = element2.attributeValue("id");
            int n = Integer.parseInt(element2.attributeValue("crystal"));
            int n2 = Integer.parseInt(element2.attributeValue("crystalMod"));
            int n3 = Integer.parseInt(element2.attributeValue("externalOrdinal"));
            int n4 = Integer.parseInt(element2.attributeValue("experienceLevel"));
            double d = 2.0;
            double d2 = 2.0;
            double d3 = 2.0;
            double d4 = 2.0;
            double d5 = 2.0;
            double d6 = 4.0;
            double d7 = 2.0;
            double d8 = 1.5;
            double d9 = 1.5;
            double d10 = 1.5;
            double d11 = 1.5;
            double d12 = 1.5;
            Iterator iterator2 = element2.elementIterator("enchant_mod");
            while (iterator2.hasNext()) {
                Element element3 = (Element)iterator2.next();
                String string2 = element3.attributeValue("stat");
                double d13 = Double.parseDouble(element3.attributeValue("multiplier"));
                switch (string2) {
                    case "shieldDef": {
                        d = d13;
                        break;
                    }
                    case "mDef": {
                        d2 = d13;
                        break;
                    }
                    case "pDef": {
                        d3 = d13;
                        break;
                    }
                    case "mAtk": {
                        d4 = d13;
                        break;
                    }
                    case "pAtk": {
                        d5 = d13;
                        break;
                    }
                    case "pAtkBow": {
                        d6 = d13;
                        break;
                    }
                    case "pAtkDoubleSword": {
                        d7 = d13;
                        break;
                    }
                    case "pAtkSpd": {
                        d8 = d13;
                        break;
                    }
                    case "mAtkSpd": {
                        d9 = d13;
                        break;
                    }
                    case "maxHp": {
                        d10 = d13;
                        break;
                    }
                    case "maxMp": {
                        d11 = d13;
                        break;
                    }
                    case "maxCp": {
                        d12 = d13;
                    }
                }
            }
            ((CrystalGradeDataHolder)this.getHolder()).addGrade(string, n, n2, n3, d, d2, d3, d4, d5, d6, d7, d8, d9, d10, d11, d12, n4);
        }
    }
}
