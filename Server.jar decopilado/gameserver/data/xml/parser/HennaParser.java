/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.TIntArrayList
 *  org.dom4j.Element
 */
package l2.gameserver.data.xml.parser;

import gnu.trove.TIntArrayList;
import java.io.File;
import java.util.Iterator;
import l2.commons.data.xml.AbstractFileParser;
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.HennaHolder;
import l2.gameserver.templates.Henna;
import org.dom4j.Element;

public final class HennaParser
extends AbstractFileParser<HennaHolder> {
    private static final HennaParser a = new HennaParser();

    public static HennaParser getInstance() {
        return a;
    }

    protected HennaParser() {
        super(HennaHolder.getInstance());
    }

    @Override
    public File getXMLFile() {
        return new File(Config.DATAPACK_ROOT, "data/hennas.xml");
    }

    @Override
    public String getDTDFileName() {
        return "hennas.dtd";
    }

    @Override
    protected void readData(Element element) throws Exception {
        Iterator iterator = element.elementIterator();
        while (iterator.hasNext()) {
            Element element2 = (Element)iterator.next();
            int n = Integer.parseInt(element2.attributeValue("symbol_id"));
            int n2 = Integer.parseInt(element2.attributeValue("dye_id"));
            long l = Integer.parseInt(element2.attributeValue("price"));
            long l2 = element2.attributeValue("draw_count") == null ? 10L : (long)Integer.parseInt(element2.attributeValue("draw_count"));
            int n3 = Integer.parseInt(element2.attributeValue("wit"));
            int n4 = Integer.parseInt(element2.attributeValue("str"));
            int n5 = Integer.parseInt(element2.attributeValue("int"));
            int n6 = Integer.parseInt(element2.attributeValue("con"));
            int n7 = Integer.parseInt(element2.attributeValue("dex"));
            int n8 = Integer.parseInt(element2.attributeValue("men"));
            TIntArrayList tIntArrayList = new TIntArrayList();
            Object object = element2.elementIterator("class");
            while (object.hasNext()) {
                Element element3 = (Element)object.next();
                tIntArrayList.add(Integer.parseInt(element3.attributeValue("id")));
            }
            object = new Henna(n, n2, l, l2, n3, n5, n6, n4, n7, n8, tIntArrayList);
            ((HennaHolder)this.getHolder()).addHenna((Henna)object);
        }
    }
}
