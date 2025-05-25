/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.tuple.Pair
 *  org.dom4j.Element
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.data.xml.parser;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import l2.commons.data.xml.AbstractFileParser;
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.CapsuleItemHolder;
import org.apache.commons.lang3.tuple.Pair;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CapsuleItemParser
extends AbstractFileParser<CapsuleItemHolder> {
    private static final Logger aX = LoggerFactory.getLogger(CapsuleItemParser.class);
    private static final CapsuleItemParser a = new CapsuleItemParser();

    public static CapsuleItemParser getInstance() {
        return a;
    }

    protected CapsuleItemParser() {
        super(CapsuleItemHolder.getInstance());
    }

    @Override
    public File getXMLFile() {
        return new File(Config.DATAPACK_ROOT, "data/capsule_items.xml");
    }

    @Override
    public String getDTDFileName() {
        return "capsule_items.dtd";
    }

    @Override
    protected void readData(Element element) throws Exception {
        Iterator iterator = element.elementIterator();
        while (iterator.hasNext()) {
            Element element2 = (Element)iterator.next();
            if (!"capsule".equalsIgnoreCase(element2.getName())) continue;
            int n = Integer.parseInt(element2.attributeValue("itemId"));
            int n2 = Integer.parseInt(element2.attributeValue("requiredItemId", "0"));
            long l = Long.parseLong(element2.attributeValue("requiredItemAmount", "0"));
            boolean bl = Boolean.parseBoolean(element2.attributeValue("consume", "true"));
            LinkedList<CapsuleItemHolder.CapsuledItem> linkedList = new LinkedList<CapsuleItemHolder.CapsuledItem>();
            Iterator iterator2 = element2.elementIterator("item");
            while (iterator2.hasNext()) {
                Element element3 = (Element)iterator2.next();
                int n3 = Integer.parseInt(element3.attributeValue("id"));
                long l2 = Long.parseLong(element3.attributeValue("min"));
                long l3 = Long.parseLong(element3.attributeValue("max"));
                double d = Double.parseDouble(element3.attributeValue("chance", "100."));
                int n4 = Integer.parseInt(element3.attributeValue("enchant_min", "0"));
                int n5 = Integer.parseInt(element3.attributeValue("enchant_max", "0"));
                if (l2 > l3) {
                    aX.error("Capsuled item " + n3 + " min > max in capsule " + n);
                    continue;
                }
                CapsuleItemHolder.CapsuledItem capsuledItem = new CapsuleItemHolder.CapsuledItem(n3, l2, l3, d, n4, n5);
                linkedList.add(capsuledItem);
            }
            if (linkedList.isEmpty()) {
                aX.warn("Capsule item " + iterator2 + " is empty.");
            }
            if (n2 > 0 && l > 0L) {
                ((CapsuleItemHolder)this.getHolder()).add(n, (Pair<Integer, Long>)Pair.of((Object)n2, (Object)l), linkedList, bl);
                continue;
            }
            ((CapsuleItemHolder)this.getHolder()).add(n, linkedList, bl);
        }
    }
}
