/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.dom4j.Element
 */
package l2.gameserver.data.xml.parser;

import java.io.File;
import java.util.Iterator;
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.data.xml.holder.OptionDataHolder;
import l2.gameserver.data.xml.parser.StatParser;
import l2.gameserver.model.Skill;
import l2.gameserver.stats.StatTemplate;
import l2.gameserver.stats.conditions.Condition;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.templates.OptionDataTemplate;
import l2.gameserver.templates.StatsSet;
import l2.gameserver.templates.item.ArmorTemplate;
import l2.gameserver.templates.item.Bodypart;
import l2.gameserver.templates.item.EtcItemTemplate;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.templates.item.WeaponTemplate;
import org.dom4j.Element;

public final class ItemParser
extends StatParser<ItemHolder> {
    private static final ItemParser a = new ItemParser();

    public static ItemParser getInstance() {
        return a;
    }

    protected ItemParser() {
        super(ItemHolder.getInstance());
    }

    @Override
    public File getXMLDir() {
        return new File(Config.DATAPACK_ROOT, "data/items/");
    }

    @Override
    public boolean isIgnored(File file) {
        return false;
    }

    @Override
    public String getDTDFileName() {
        return "item.dtd";
    }

    @Override
    protected void readData(Element element) throws Exception {
        Iterator iterator = element.elementIterator();
        while (iterator.hasNext()) {
            Object object;
            String string;
            Object object2;
            Element element2 = (Element)iterator.next();
            StatsSet statsSet = new StatsSet();
            statsSet.set("item_id", element2.attributeValue("id"));
            statsSet.set("name", element2.attributeValue("name"));
            statsSet.set("add_name", element2.attributeValue("add_name", ""));
            long l = 0L;
            Object object3 = element2.elementIterator();
            while (object3.hasNext()) {
                object2 = (Element)object3.next();
                string = object2.getName();
                if (string.equalsIgnoreCase("set")) {
                    statsSet.set(object2.attributeValue("name"), object2.attributeValue("value"));
                    continue;
                }
                if (!string.equalsIgnoreCase("equip")) continue;
                object = object2.elementIterator();
                while (object.hasNext()) {
                    Element element3 = (Element)object.next();
                    Bodypart bodypart = Bodypart.valueOf(element3.attributeValue("id"));
                    if (bodypart.getReal() != null) {
                        l = bodypart.mask();
                        continue;
                    }
                    l |= bodypart.mask();
                }
            }
            statsSet.set("bodypart", l);
            object3 = null;
            try {
                if (element2.getName().equalsIgnoreCase("weapon")) {
                    if (!statsSet.containsKey("class")) {
                        if ((l & 0x100L) > 0L) {
                            statsSet.set("class", ItemTemplate.ItemClass.ARMOR);
                        } else {
                            statsSet.set("class", ItemTemplate.ItemClass.WEAPON);
                        }
                    }
                    object3 = new WeaponTemplate(statsSet);
                } else if (element2.getName().equalsIgnoreCase("armor")) {
                    if (!statsSet.containsKey("class")) {
                        if ((l & 0x2BF40L) > 0L) {
                            statsSet.set("class", ItemTemplate.ItemClass.ARMOR);
                        } else if ((l & 0x3EL) > 0L) {
                            statsSet.set("class", ItemTemplate.ItemClass.JEWELRY);
                        } else {
                            statsSet.set("class", ItemTemplate.ItemClass.ACCESSORY);
                        }
                    }
                    object3 = new ArmorTemplate(statsSet);
                } else {
                    object3 = new EtcItemTemplate(statsSet);
                }
            }
            catch (Exception exception) {
                this.warn("Fail create item: " + statsSet.get("item_id"), exception);
                continue;
            }
            object2 = element2.elementIterator();
            while (object2.hasNext()) {
                Skill skill;
                Object object4;
                int n;
                string = (Element)object2.next();
                object = string.getName();
                if (((String)object).equalsIgnoreCase("for")) {
                    this.getStatProcessor().parseFor((Element)string, (StatTemplate)object3);
                    continue;
                }
                if (((String)object).equalsIgnoreCase("triggers")) {
                    this.getStatProcessor().parseTriggers((Element)string, (StatTemplate)object3);
                    continue;
                }
                if (((String)object).equalsIgnoreCase("skills")) {
                    Iterator iterator2 = string.elementIterator();
                    while (iterator2.hasNext()) {
                        Element element4 = (Element)iterator2.next();
                        int n2 = Integer.parseInt(element4.attributeValue("id"));
                        n = Integer.parseInt(element4.attributeValue("level"));
                        object4 = SkillTable.getInstance().getInfo(n2, n);
                        if (object4 != null) {
                            ((ItemTemplate)object3).attachSkill((Skill)object4);
                            continue;
                        }
                        this.info("Skill not found(" + n2 + "," + n + ") for item:" + statsSet.getObject("item_id") + "; file:" + this.getCurrentFileName());
                    }
                    continue;
                }
                if (((String)object).equalsIgnoreCase("enchant4_skill")) {
                    int n3 = Integer.parseInt(string.attributeValue("id"));
                    int n4 = Integer.parseInt(string.attributeValue("level"));
                    skill = SkillTable.getInstance().getInfo(n3, n4);
                    if (skill == null) continue;
                    ((ItemTemplate)object3).setEnchant4Skill(skill);
                    continue;
                }
                if (((String)object).equalsIgnoreCase("enchant6_skill")) {
                    int n5 = Integer.parseInt(string.attributeValue("id"));
                    int n6 = Integer.parseInt(string.attributeValue("level"));
                    skill = SkillTable.getInstance().getInfo(n5, n6);
                    if (skill == null) continue;
                    ((ItemTemplate)object3).setEnchant6Skill(skill);
                    continue;
                }
                if (((String)object).equalsIgnoreCase("cond")) {
                    Condition condition = this.getStatProcessor().parseFirstCond((Element)string);
                    if (condition != null) {
                        String string2;
                        String string3 = string.attributeValue("msgId");
                        if (string3 != null) {
                            try {
                                int n7 = this.getStatProcessor().parseNumber(string3).intValue();
                                condition.setSystemMsg(n7);
                            }
                            catch (NumberFormatException numberFormatException) {
                                this._log.warn("Invalid msgId format in condition for item " + statsSet.getObject("item_id") + ": " + string3, (Throwable)numberFormatException);
                            }
                        }
                        if ((string2 = string.attributeValue("customMessage")) != null && !string2.isEmpty()) {
                            condition.setCustomMessage(string2);
                        }
                        ((ItemTemplate)object3).addCondition(condition);
                        continue;
                    }
                    this._log.warn("Failed to parse condition for item " + statsSet.getObject("item_id"));
                    continue;
                }
                if (((String)object).equalsIgnoreCase("attributes")) {
                    int[] nArray = new int[6];
                    Iterator iterator3 = string.elementIterator();
                    while (iterator3.hasNext()) {
                        Element element5 = (Element)iterator3.next();
                        if (!element5.getName().equalsIgnoreCase("attribute")) continue;
                        l2.gameserver.model.base.Element element6 = l2.gameserver.model.base.Element.getElementByName(element5.attributeValue("element"));
                        nArray[element6.getId()] = Integer.parseInt(element5.attributeValue("value"));
                    }
                    ((ItemTemplate)object3).setBaseAtributeElements(nArray);
                    continue;
                }
                if (!((String)object).equalsIgnoreCase("enchant_options")) continue;
                Iterator iterator4 = string.elementIterator();
                while (iterator4.hasNext()) {
                    Element element7 = (Element)iterator4.next();
                    if (!element7.getName().equalsIgnoreCase("level")) continue;
                    int n8 = Integer.parseInt(element7.attributeValue("val"));
                    n = 0;
                    object4 = new int[3];
                    for (Element element8 : element7.elements()) {
                        OptionDataTemplate optionDataTemplate = OptionDataHolder.getInstance().getTemplate(Integer.parseInt(element8.attributeValue("id")));
                        if (optionDataTemplate == null) {
                            this.error("Not found option_data for id: " + element8.attributeValue("id") + "; item_id: " + statsSet.get("item_id"));
                            continue;
                        }
                        object4[n++] = optionDataTemplate.getId();
                    }
                    ((ItemTemplate)object3).addEnchantOptions(n8, (int[])object4);
                }
            }
            ((ItemHolder)this.getHolder()).addItem((ItemTemplate)object3);
        }
    }
}
