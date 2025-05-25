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
import l2.gameserver.data.xml.holder.SellBuffHolder;
import org.dom4j.Element;

public class SellBuffParser
extends AbstractFileParser<SellBuffHolder> {
    private static final SellBuffParser a = new SellBuffParser();

    protected SellBuffParser() {
        super(SellBuffHolder.getInstance());
    }

    public static SellBuffParser getInstance() {
        return a;
    }

    @Override
    public File getXMLFile() {
        return new File(Config.DATAPACK_ROOT, "data/sell_buff_list.xml");
    }

    @Override
    public String getDTDFileName() {
        return "sell_buff_list.dtd";
    }

    @Override
    protected void readData(Element element) throws Exception {
        Iterator iterator = element.elementIterator();
        while (iterator.hasNext()) {
            Element element2 = (Element)iterator.next();
            String string = element2.getName();
            if (!string.equalsIgnoreCase("skill")) continue;
            int n = Integer.parseInt(element2.attributeValue("id"));
            int n2 = Integer.parseInt(element2.attributeValue("priceItemId"));
            long l = Long.parseLong(element2.attributeValue("priceMin"));
            long l2 = Long.parseLong(element2.attributeValue("priceMax"));
            int n3 = Integer.parseInt(element2.attributeValue("effectTime", "0"));
            double d = Double.parseDouble(element2.attributeValue("effectTimeMultiplayer", "1"));
            double d2 = Double.parseDouble(element2.attributeValue("effectTimePremiumMultiplier", "1"));
            double d3 = Double.parseDouble(element2.attributeValue("mpConsumeMultiplayer", "1"));
            boolean bl = Boolean.parseBoolean(element2.attributeValue("applyOnPets", "false"));
            ((SellBuffHolder)this.getHolder()).addBuffInfo(n, n2, l, l2, n3, d, d2, d3, bl);
        }
    }
}
