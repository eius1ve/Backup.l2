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
import l2.gameserver.data.xml.holder.CombinationDataHolder;
import l2.gameserver.templates.item.support.CombinationItem;
import org.dom4j.Element;

public class CombinationDataParser
extends AbstractFileParser<CombinationDataHolder> {
    private static CombinationDataParser a = new CombinationDataParser();

    private CombinationDataParser() {
        super(CombinationDataHolder.getInstance());
    }

    public static CombinationDataParser getInstance() {
        return a;
    }

    @Override
    public File getXMLFile() {
        return new File(Config.DATAPACK_ROOT, "data/combination_item.xml");
    }

    @Override
    public String getDTDFileName() {
        return "combination_item.dtd";
    }

    @Override
    protected void readData(Element element) throws Exception {
        Iterator iterator = element.elementIterator("combinationitem_begin");
        while (iterator.hasNext()) {
            Element element2 = (Element)iterator.next();
            int n = Integer.parseInt(element2.attributeValue("slotone"));
            int n2 = Integer.parseInt(element2.attributeValue("slottwo"));
            double d = Double.parseDouble(element2.attributeValue("chance"));
            int n3 = Integer.parseInt(element2.attributeValue("on_success"));
            int n4 = Integer.parseInt(element2.attributeValue("on_fail"));
            ((CombinationDataHolder)this.getHolder()).addData(new CombinationItem(n, n2, d, n3, n4));
        }
    }
}
