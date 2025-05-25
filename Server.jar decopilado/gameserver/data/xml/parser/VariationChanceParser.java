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
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import l2.commons.data.xml.AbstractFileParser;
import l2.commons.util.RandomUtils;
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.VariationChanceHolder;
import l2.gameserver.templates.item.support.VariationChanceData;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.dom4j.Element;

public class VariationChanceParser
extends AbstractFileParser<VariationChanceHolder> {
    private static final VariationChanceParser a = new VariationChanceParser();

    public static VariationChanceParser getInstance() {
        return a;
    }

    private VariationChanceParser() {
        super(VariationChanceHolder.getInstance());
    }

    @Override
    public File getXMLFile() {
        return new File(Config.DATAPACK_ROOT, "data/variation_data.xml");
    }

    @Override
    public String getDTDFileName() {
        return "variation_data.dtd";
    }

    private List<Pair<List<Pair<Integer, Double>>, Double>> c(Element element) throws Exception {
        ArrayList<Pair<List<Pair<Integer, Double>>, Double>> arrayList = new ArrayList<Pair<List<Pair<Integer, Double>>, Double>>();
        Iterator iterator = element.elementIterator();
        while (iterator.hasNext()) {
            Element element2 = (Element)iterator.next();
            if (!"group".equalsIgnoreCase(element2.getName())) continue;
            double d = Double.parseDouble(element2.attributeValue("chance"));
            ArrayList<ImmutablePair> arrayList2 = new ArrayList<ImmutablePair>();
            Iterator iterator2 = element2.elementIterator();
            while (iterator2.hasNext()) {
                Element element3 = (Element)iterator2.next();
                int n = Integer.parseInt(element3.attributeValue("id"));
                double d2 = Double.parseDouble(element3.attributeValue("chance"));
                arrayList2.add(new ImmutablePair((Object)n, (Object)d2));
            }
            Collections.sort(arrayList2, RandomUtils.DOUBLE_GROUP_COMPARATOR);
            arrayList.add((Pair<List<Pair<Integer, Double>>, Double>)new ImmutablePair(arrayList2, (Object)d));
        }
        return arrayList;
    }

    @Override
    protected void readData(Element element) throws Exception {
        Iterator iterator = element.elementIterator();
        while (iterator.hasNext()) {
            Element element2 = (Element)iterator.next();
            int n = Integer.parseInt(element2.attributeValue("mineralId"));
            boolean bl = false;
            VariationChanceData variationChanceData = null;
            VariationChanceData variationChanceData2 = null;
            Iterator iterator2 = element2.elementIterator();
            while (iterator2.hasNext()) {
                Element element3 = (Element)iterator2.next();
                String string = element3.attributeValue("type");
                List<Pair<List<Pair<Integer, Double>>, Double>> list = null;
                List<Pair<List<Pair<Integer, Double>>, Double>> list2 = null;
                Iterator iterator3 = element3.elementIterator();
                while (iterator3.hasNext()) {
                    List<Pair<List<Pair<Integer, Double>>, Double>> list3;
                    Element element4 = (Element)iterator3.next();
                    if ("variation1".equalsIgnoreCase(element4.getName())) {
                        list3 = this.c(element4);
                        Collections.sort(list3, RandomUtils.DOUBLE_GROUP_COMPARATOR);
                        list = list3;
                        continue;
                    }
                    if (!"variation2".equalsIgnoreCase(element4.getName())) continue;
                    list3 = this.c(element4);
                    Collections.sort(list3, RandomUtils.DOUBLE_GROUP_COMPARATOR);
                    list2 = list3;
                }
                if (string.equalsIgnoreCase("WARRIOR")) {
                    variationChanceData = new VariationChanceData(n, list != null ? list : Collections.emptyList(), list2 != null ? list2 : Collections.emptyList());
                    continue;
                }
                if (string.equalsIgnoreCase("MAGE")) {
                    variationChanceData2 = new VariationChanceData(n, list != null ? list : Collections.emptyList(), list2 != null ? list2 : Collections.emptyList());
                    continue;
                }
                throw new RuntimeException("Unknown type " + string);
            }
            ((VariationChanceHolder)this.getHolder()).add((Pair<VariationChanceData, VariationChanceData>)new ImmutablePair(variationChanceData, variationChanceData2));
        }
    }
}
