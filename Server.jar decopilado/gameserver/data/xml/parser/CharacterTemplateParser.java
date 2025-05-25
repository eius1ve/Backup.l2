/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.tuple.Triple
 *  org.dom4j.Element
 */
package l2.gameserver.data.xml.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import l2.commons.data.xml.AbstractDirParser;
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.CharacterTemplateHolder;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.model.base.ClassId;
import l2.gameserver.templates.PlayerTemplate;
import l2.gameserver.templates.StatsSet;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.utils.Location;
import org.apache.commons.lang3.tuple.Triple;
import org.dom4j.Element;

public class CharacterTemplateParser
extends AbstractDirParser<CharacterTemplateHolder> {
    private static final CharacterTemplateParser a = new CharacterTemplateParser();

    public static final CharacterTemplateParser getInstance() {
        return a;
    }

    protected CharacterTemplateParser() {
        super(CharacterTemplateHolder.getInstance());
    }

    @Override
    public File getXMLDir() {
        return new File(Config.DATAPACK_ROOT, "data/stats/player/pcparams/");
    }

    @Override
    public boolean isIgnored(File file) {
        return false;
    }

    @Override
    public String getDTDFileName() {
        return "pcparams.dtd";
    }

    @Override
    protected void readData(Element element) throws Exception {
        Iterator iterator = element.elementIterator();
        while (iterator.hasNext()) {
            Element element2 = (Element)iterator.next();
            if (!"pcparams".equalsIgnoreCase(element2.getName())) continue;
            ClassId classId = null;
            String string = element2.attributeValue("classId");
            classId = string != null ? ClassId.getClassById(Integer.parseInt(string)) : ClassId.valueOf(element2.attributeValue("classType"));
            String string2 = element2.attributeValue("name");
            Iterator iterator2 = element2.elementIterator();
            while (iterator2.hasNext()) {
                StatsSet statsSet = new StatsSet();
                ArrayList<Triple> arrayList = new ArrayList<Triple>();
                ArrayList<Triple> arrayList2 = new ArrayList<Triple>();
                Location location = null;
                boolean bl = false;
                Element element3 = (Element)iterator2.next();
                if ("male".equals(element3.getName())) {
                    bl = true;
                } else if ("female".equals(element3.getName())) {
                    bl = false;
                } else {
                    throw new IllegalStateException();
                }
                Object object = element3.elementIterator();
                while (object.hasNext()) {
                    Element element4 = (Element)object.next();
                    if ("items".equals(element4.getName())) {
                        for (Element element5 : element4.elements()) {
                            if (!"item".equals(element5.getName())) continue;
                            boolean bl2 = Boolean.parseBoolean(element5.attributeValue("equipped", "false"));
                            int n = Integer.parseInt(element5.attributeValue("enchant", "0"));
                            ItemTemplate itemTemplate = ItemHolder.getInstance().getTemplate(Integer.parseInt(element5.attributeValue("id")));
                            long l = Long.parseLong(element5.attributeValue("amount", "1"));
                            if (bl2 && !itemTemplate.isEquipable()) {
                                this.warn("Item " + itemTemplate.getItemId() + " can't be equipped.");
                                bl2 = false;
                            }
                            if (n > 0 && !itemTemplate.canBeEnchanted(false)) {
                                this.warn("Item " + itemTemplate.getItemId() + " can't be enchanted.");
                                n = 0;
                            }
                            if (bl2) {
                                arrayList2.add(Triple.of((Object)itemTemplate, (Object)l, (Object)n));
                                continue;
                            }
                            arrayList.add(Triple.of((Object)itemTemplate, (Object)l, (Object)n));
                        }
                        continue;
                    }
                    if ("spawn".equals(element4.getName())) {
                        location = Location.parse(element4);
                        continue;
                    }
                    if (!"stats".equals(element4.getName())) continue;
                    for (Element element5 : element4.elements()) {
                        if (!"set".equals(element5.getName())) continue;
                        statsSet.set(element5.attributeValue("name"), element5.attributeValue("value"));
                    }
                }
                object = new PlayerTemplate(classId, statsSet, string2, location, bl);
                ((PlayerTemplate)object).getInitialItems().addAll(arrayList);
                ((PlayerTemplate)object).getInitialEquipItems().addAll(arrayList2);
                ((CharacterTemplateHolder)this.getHolder()).addTemplate((PlayerTemplate)object, bl);
            }
        }
    }
}
