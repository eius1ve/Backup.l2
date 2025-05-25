/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.dom4j.Element
 */
package l2.gameserver.data.xml.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import l2.commons.data.xml.AbstractFileParser;
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.MacroDataHolder;
import l2.gameserver.model.actor.instances.player.Macro;
import l2.gameserver.model.actor.instances.player.ShortCut;
import org.dom4j.Element;

public final class MacroDataParser
extends AbstractFileParser<MacroDataHolder> {
    private static final MacroDataParser a = new MacroDataParser();
    private boolean i = false;

    private MacroDataParser() {
        super(MacroDataHolder.getInstance());
    }

    public static MacroDataParser getInstance() {
        return a;
    }

    @Override
    public File getXMLFile() {
        return new File(Config.DATAPACK_ROOT, "data/macros_reg.xml");
    }

    @Override
    public String getDTDFileName() {
        return "macros_reg.dtd";
    }

    @Override
    protected void readData(Element element) throws Exception {
        this.i = Boolean.parseBoolean(element.attributeValue("enabled", "false"));
        if (this.i) {
            Object object;
            int n;
            Object object2;
            Iterator iterator = element.elementIterator("macro");
            while (iterator.hasNext()) {
                object2 = (Element)iterator.next();
                int n2 = Integer.parseInt(object2.attributeValue("id"));
                n = Integer.parseInt(object2.attributeValue("icon"));
                String string = object2.attributeValue("name");
                String string2 = object2.attributeValue("desc");
                String string3 = object2.attributeValue("acronym");
                ArrayList<Macro.L2MacroCmd> arrayList = new ArrayList<Macro.L2MacroCmd>();
                Object object3 = object2.elementIterator("command");
                while (object3.hasNext()) {
                    object = (Element)object3.next();
                    int n3 = Integer.parseInt(object.attributeValue("order"));
                    int n4 = Integer.parseInt(object.attributeValue("type"));
                    int n5 = Integer.parseInt(object.attributeValue("d1"));
                    int n6 = Integer.parseInt(object.attributeValue("d2"));
                    String string4 = object.attributeValue("cmd");
                    arrayList.add(new Macro.L2MacroCmd(n3, n4, n5, n6, string4));
                }
                object3 = new Macro(n2, n, string, string2, string3, arrayList.toArray(new Macro.L2MacroCmd[0]));
                ((MacroDataHolder)this.getHolder()).addMacro((Macro)object3);
            }
            iterator = element.element("shortcuts");
            if (iterator != null) {
                object2 = iterator.elementIterator("shortcut");
                while (object2.hasNext()) {
                    Element element2 = (Element)object2.next();
                    n = Integer.parseInt(element2.attributeValue("slot"));
                    int n7 = Integer.parseInt(element2.attributeValue("page"));
                    int n8 = Integer.parseInt(element2.attributeValue("type"));
                    int n9 = Integer.parseInt(element2.attributeValue("id"));
                    int n10 = Integer.parseInt(element2.attributeValue("level"));
                    int n11 = Integer.parseInt(element2.attributeValue("class"));
                    object = new ShortCut(n, n7, n8, n9, n10, n11);
                    ((MacroDataHolder)this.getHolder()).addShortcut((ShortCut)object);
                }
            }
        }
    }

    public boolean isEnabled() {
        return this.i;
    }
}
