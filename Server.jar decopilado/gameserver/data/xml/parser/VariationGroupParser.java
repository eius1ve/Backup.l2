/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.dom4j.Element
 */
package l2.gameserver.data.xml.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import l2.commons.data.xml.AbstractFileParser;
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.VariationGroupHolder;
import l2.gameserver.templates.item.support.VariationGroupData;
import org.dom4j.Element;

public class VariationGroupParser
extends AbstractFileParser<VariationGroupHolder> {
    private static final VariationGroupParser a = new VariationGroupParser();
    private HashMap<Integer, VariationGroupData> b = new HashMap();

    public static VariationGroupParser getInstance() {
        return a;
    }

    private VariationGroupParser() {
        super(VariationGroupHolder.getInstance());
    }

    @Override
    public File getXMLFile() {
        return new File(Config.DATAPACK_ROOT, "data/variation_group.xml");
    }

    @Override
    public String getDTDFileName() {
        return "variation_group.dtd";
    }

    @Override
    protected void readData(Element element) throws Exception {
        Iterator iterator = element.elementIterator();
        while (iterator.hasNext()) {
            Element element2 = (Element)iterator.next();
            String string = element2.attributeValue("name");
            ArrayList<Integer> arrayList = new ArrayList<Integer>();
            ArrayList<VariationGroupData> arrayList2 = new ArrayList<VariationGroupData>();
            Object object = element2.elementIterator();
            while (object.hasNext()) {
                Element element3 = (Element)object.next();
                String string2 = element3.getName();
                if ("items".equalsIgnoreCase(string2)) {
                    StringTokenizer stringTokenizer = new StringTokenizer(element3.getStringValue());
                    while (stringTokenizer.hasMoreTokens()) {
                        arrayList.add(Integer.parseInt(stringTokenizer.nextToken()));
                    }
                    continue;
                }
                if (!"fee".equalsIgnoreCase(string2)) continue;
                long l = Long.parseLong(element3.attributeValue("cancelPrice"));
                Iterator iterator2 = element3.elementIterator();
                while (iterator2.hasNext()) {
                    Element element4 = (Element)iterator2.next();
                    if (!"mineral".equalsIgnoreCase(element4.getName())) continue;
                    int n = Integer.parseInt(element4.attributeValue("itemId"));
                    int n2 = Integer.parseInt(element4.attributeValue("gemstoneItemId"));
                    long l2 = Long.parseLong(element4.attributeValue("gemstoneItemCnt"));
                    arrayList2.add(new VariationGroupData(n, n2, l2, l));
                }
            }
            if (arrayList2.isEmpty()) {
                throw new RuntimeException("Undefined fee for group " + string);
            }
            object = new int[arrayList.size()];
            for (int i = 0; i < arrayList.size(); ++i) {
                object[i] = (Integer)arrayList.get(i);
            }
            Arrays.sort((int[])object);
            for (VariationGroupData variationGroupData : arrayList2) {
                ((VariationGroupHolder)this.getHolder()).addSorted((int[])object, variationGroupData);
            }
        }
    }
}
