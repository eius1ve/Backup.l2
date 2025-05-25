/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.TDoubleArrayList
 *  org.dom4j.Element
 */
package l2.gameserver.data.xml.parser;

import gnu.trove.TDoubleArrayList;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Objects;
import l2.commons.data.xml.AbstractFileParser;
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.CrystalGradeDataHolder;
import l2.gameserver.data.xml.holder.EnchantItemHolder;
import l2.gameserver.templates.item.support.EnchantCatalyzer;
import l2.gameserver.templates.item.support.EnchantChanceType;
import l2.gameserver.templates.item.support.EnchantItem;
import l2.gameserver.templates.item.support.EnchantScroll;
import l2.gameserver.templates.item.support.EnchantScrollOnFailAction;
import l2.gameserver.templates.item.support.EnchantTargetType;
import l2.gameserver.templates.item.support.Grade;
import org.dom4j.Element;

public class EnchantItemParser
extends AbstractFileParser<EnchantItemHolder> {
    private static EnchantItemParser a = new EnchantItemParser();

    public static EnchantItemParser getInstance() {
        return a;
    }

    private EnchantItemParser() {
        super(EnchantItemHolder.getInstance());
    }

    @Override
    public File getXMLFile() {
        return new File(Config.DATAPACK_ROOT, "data/enchant_items.xml");
    }

    @Override
    public String getDTDFileName() {
        return "enchant_items.dtd";
    }

    @Override
    protected void readData(Element element) throws Exception {
        Iterator iterator = element.elementIterator();
        while (iterator.hasNext()) {
            int n;
            Element element2 = (Element)iterator.next();
            if (element2.getName().equals("scroll")) {
                n = Integer.parseInt(element2.attributeValue("id"));
                boolean bl = Boolean.parseBoolean(element2.attributeValue("infallible", "false"));
                EnchantScrollOnFailAction enchantScrollOnFailAction = EnchantScrollOnFailAction.NONE;
                int n2 = 0;
                if (!bl) {
                    enchantScrollOnFailAction = EnchantScrollOnFailAction.valueOf(element2.attributeValue("on_fail"));
                    n2 = Integer.parseInt(element2.attributeValue("reset_lvl", "0"));
                }
                double d = Double.parseDouble(element2.attributeValue("chance_bonus", "0"));
                Grade grade = Objects.requireNonNull(CrystalGradeDataHolder.getInstance().getGrade(element2.attributeValue("grade")), "Unknown enchant item grade");
                int n3 = 0;
                int n4 = Config.ENCHANT_MAX;
                int n5 = Integer.parseInt(element2.attributeValue("increment", "1"));
                int n6 = Integer.parseInt(element2.attributeValue("increment_max", String.valueOf(n5)));
                boolean bl2 = Boolean.parseBoolean(element2.attributeValue("close_enchant_window_on_fail", "false"));
                EnchantTargetType enchantTargetType = EnchantTargetType.ALL;
                ArrayList<Integer> arrayList = new ArrayList<Integer>();
                LinkedHashMap<EnchantChanceType, double[]> linkedHashMap = new LinkedHashMap<EnchantChanceType, double[]>();
                Object object = element2.elementIterator();
                while (object.hasNext()) {
                    Element element3 = (Element)object.next();
                    if (element3.getName().equals("levels")) {
                        n3 = Integer.parseInt(element3.attributeValue("min"));
                        n4 = Integer.parseInt(element3.attributeValue("max"));
                        continue;
                    }
                    if (element3.getName().equals("items_restrict")) {
                        enchantTargetType = EnchantTargetType.valueOf(element3.attributeValue("type"));
                        Iterator iterator2 = element3.elementIterator("item");
                        while (iterator2.hasNext()) {
                            arrayList.add(Integer.valueOf(((Element)iterator2.next()).attributeValue("id")));
                        }
                        continue;
                    }
                    if (!element3.getName().equals("chances")) continue;
                    EnchantChanceType enchantChanceType = EnchantChanceType.valueOf(element3.attributeValue("type"));
                    TDoubleArrayList tDoubleArrayList = new TDoubleArrayList();
                    Iterator iterator3 = element3.elementIterator("chance");
                    while (iterator3.hasNext()) {
                        tDoubleArrayList.add(Double.valueOf(((Element)iterator3.next()).attributeValue("val")).doubleValue());
                    }
                    linkedHashMap.put(enchantChanceType, tDoubleArrayList.toNativeArray());
                }
                object = new EnchantScroll(n, n5, n6, d, grade, n3, n4, enchantTargetType, enchantScrollOnFailAction, n2, bl, bl2, false, linkedHashMap);
                if (!arrayList.isEmpty()) {
                    for (Integer n7 : arrayList) {
                        ((EnchantScroll)object).addItemRestrict(n7);
                    }
                }
                ((EnchantItemHolder)this.getHolder()).addEnchantItem((EnchantItem)object);
                continue;
            }
            if (element2.getName().equals("catalyzer")) {
                n = Integer.parseInt(element2.attributeValue("id"));
                EnchantScrollOnFailAction enchantScrollOnFailAction = element2.attributeValue("on_fail") == null ? EnchantScrollOnFailAction.CRYSTALIZE : EnchantScrollOnFailAction.valueOf(element2.attributeValue("on_fail").toUpperCase());
                double d = Double.parseDouble(element2.attributeValue("chance_bonus"));
                Grade grade = Objects.requireNonNull(CrystalGradeDataHolder.getInstance().getGrade(element2.attributeValue("grade")), "Unknown enchant item grade");
                int n7 = Integer.parseInt(element2.attributeValue("min"));
                int n8 = Integer.parseInt(element2.attributeValue("max"));
                EnchantTargetType enchantTargetType = EnchantTargetType.valueOf(element2.attributeValue("type"));
                EnchantCatalyzer enchantCatalyzer = new EnchantCatalyzer(n, d, enchantScrollOnFailAction, grade, n7, n8, enchantTargetType);
                ((EnchantItemHolder)this.getHolder()).addEnchantItem(enchantCatalyzer);
                continue;
            }
            this.error("Unknown entry " + element2.getName());
        }
    }
}
