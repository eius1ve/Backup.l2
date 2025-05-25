/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.dom4j.Attribute
 *  org.dom4j.Element
 */
package l2.gameserver.data.xml.parser;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import l2.commons.collections.MultiValueSet;
import l2.commons.data.xml.AbstractFileParser;
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.FishDataHolder;
import l2.gameserver.templates.item.support.FishGroup;
import l2.gameserver.templates.item.support.FishTemplate;
import l2.gameserver.templates.item.support.LureTemplate;
import l2.gameserver.templates.item.support.LureType;
import org.dom4j.Attribute;
import org.dom4j.Element;

public class FishDataParser
extends AbstractFileParser<FishDataHolder> {
    private static final FishDataParser a = new FishDataParser();

    public static FishDataParser getInstance() {
        return a;
    }

    private FishDataParser() {
        super(FishDataHolder.getInstance());
    }

    @Override
    public File getXMLFile() {
        return new File(Config.DATAPACK_ROOT, "data/fishdata.xml");
    }

    @Override
    public String getDTDFileName() {
        return "fishdata.dtd";
    }

    @Override
    protected void readData(Element element) throws Exception {
        Iterator iterator = element.elementIterator();
        while (iterator.hasNext()) {
            Object object;
            Object object2;
            Object object3;
            Element element2 = (Element)iterator.next();
            if ("fish".equals(element2.getName())) {
                MultiValueSet<String> multiValueSet = new MultiValueSet<String>();
                object3 = element2.attributeIterator();
                while (object3.hasNext()) {
                    object2 = (Attribute)object3.next();
                    multiValueSet.put(object2.getName(), object2.getValue());
                }
                ((FishDataHolder)this.getHolder()).addFish(new FishTemplate(multiValueSet));
                continue;
            }
            if ("lure".equals(element2.getName())) {
                MultiValueSet<String> multiValueSet = new MultiValueSet<String>();
                object3 = element2.attributeIterator();
                while (object3.hasNext()) {
                    object2 = (Attribute)object3.next();
                    multiValueSet.put(object2.getName(), object2.getValue());
                }
                object3 = new HashMap();
                object2 = element2.elementIterator();
                while (object2.hasNext()) {
                    object = (Element)object2.next();
                    object3.put(FishGroup.valueOf(object.attributeValue("type")), Integer.parseInt(object.attributeValue("value")));
                }
                multiValueSet.put("chances", object3);
                ((FishDataHolder)this.getHolder()).addLure(new LureTemplate(multiValueSet));
                continue;
            }
            if (!"distribution".equals(element2.getName())) continue;
            int n = Integer.parseInt(element2.attributeValue("id"));
            object3 = element2.elementIterator();
            while (object3.hasNext()) {
                object2 = (Element)object3.next();
                object = LureType.valueOf(object2.attributeValue("type"));
                HashMap<FishGroup, Integer> hashMap = new HashMap<FishGroup, Integer>();
                Iterator iterator2 = object2.elementIterator();
                while (iterator2.hasNext()) {
                    Element element3 = (Element)iterator2.next();
                    hashMap.put(FishGroup.valueOf(element3.attributeValue("type")), Integer.parseInt(element3.attributeValue("value")));
                }
                ((FishDataHolder)this.getHolder()).addDistribution(n, (LureType)((Object)object), hashMap);
            }
        }
    }
}
