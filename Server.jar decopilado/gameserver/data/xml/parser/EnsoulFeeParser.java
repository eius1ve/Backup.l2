/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.tuple.Pair
 *  org.dom4j.Element
 */
package l2.gameserver.data.xml.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import l2.commons.data.xml.AbstractFileParser;
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.CrystalGradeDataHolder;
import l2.gameserver.data.xml.holder.EnsoulFeeHolder;
import l2.gameserver.templates.item.support.EnsoulFeeSlotType;
import l2.gameserver.templates.item.support.Grade;
import org.apache.commons.lang3.tuple.Pair;
import org.dom4j.Element;

public class EnsoulFeeParser
extends AbstractFileParser<EnsoulFeeHolder> {
    private static final EnsoulFeeParser a = new EnsoulFeeParser();
    private static final File d = new File(Config.DATAPACK_ROOT, "data/ensoul_fee.xml");
    private static final String bI = "ensoul_fee.dtd";

    public static EnsoulFeeParser getInstance() {
        return a;
    }

    private EnsoulFeeParser() {
        super(EnsoulFeeHolder.getInstance());
    }

    @Override
    public File getXMLFile() {
        return d;
    }

    @Override
    public String getDTDFileName() {
        return bI;
    }

    @Override
    protected void readData(Element element) throws Exception {
        Iterator iterator = element.elementIterator();
        while (iterator.hasNext()) {
            Element element2 = (Element)iterator.next();
            Grade grade = Objects.requireNonNull(CrystalGradeDataHolder.getInstance().getGrade(element2.attributeValue("grade")), "Unknown enchant item grade");
            Iterator iterator2 = element2.elementIterator();
            while (iterator2.hasNext()) {
                Pair<EnsoulFeeSlotType, List<Pair<Integer, Long>>> pair;
                Element element3 = (Element)iterator2.next();
                String string = element3.getName();
                if ("insert_fee".equalsIgnoreCase(string)) {
                    pair = this.a(element3);
                    ((EnsoulFeeHolder)this.getHolder()).addInsertFee(grade, (EnsoulFeeSlotType)((Object)pair.getLeft()), (List)pair.getRight());
                    continue;
                }
                if ("replace_fee".equalsIgnoreCase(string)) {
                    pair = this.a(element3);
                    ((EnsoulFeeHolder)this.getHolder()).addReplaceFee(grade, (EnsoulFeeSlotType)((Object)pair.getLeft()), (List)pair.getRight());
                    continue;
                }
                if (!"remove_fee".equalsIgnoreCase(string)) continue;
                pair = this.a(element3);
                ((EnsoulFeeHolder)this.getHolder()).addRemoveFee(grade, (EnsoulFeeSlotType)((Object)pair.getLeft()), (List)pair.getRight());
            }
        }
    }

    private Pair<EnsoulFeeSlotType, List<Pair<Integer, Long>>> a(Element element) throws Exception {
        return Pair.of((Object)((Object)EnsoulFeeSlotType.getSlotType(Integer.parseInt(element.attributeValue("type")))), this.a(element));
    }

    private List<Pair<Integer, Long>> a(Element element) throws Exception {
        ArrayList<Pair<Integer, Long>> arrayList = new ArrayList<Pair<Integer, Long>>();
        Iterator iterator = element.elementIterator();
        while (iterator.hasNext()) {
            Element element2 = (Element)iterator.next();
            if (!"item".equalsIgnoreCase(element2.getName())) continue;
            int n = Integer.parseInt(element2.attributeValue("id"));
            long l = Long.parseLong(element2.attributeValue("amount"));
            arrayList.add((Pair<Integer, Long>)Pair.of((Object)n, (Object)l));
        }
        return arrayList;
    }
}
