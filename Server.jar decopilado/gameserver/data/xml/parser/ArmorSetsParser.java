/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 *  org.dom4j.Element
 */
package l2.gameserver.data.xml.parser;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.StringTokenizer;
import l2.commons.data.xml.AbstractFileParser;
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.ArmorSetsHolder;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.model.ArmorSet;
import l2.gameserver.model.Skill;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.templates.item.ItemTemplate;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;

public final class ArmorSetsParser
extends AbstractFileParser<ArmorSetsHolder> {
    private static final ArmorSetsParser a = new ArmorSetsParser();

    public static ArmorSetsParser getInstance() {
        return a;
    }

    private ArmorSetsParser() {
        super(ArmorSetsHolder.getInstance());
    }

    @Override
    public File getXMLFile() {
        return new File(Config.DATAPACK_ROOT, "data/armor_sets.xml");
    }

    @Override
    public String getDTDFileName() {
        return "armor_sets.dtd";
    }

    private Set<ItemTemplate> b(String string) {
        HashSet<ItemTemplate> hashSet = new HashSet<ItemTemplate>(1);
        StringTokenizer stringTokenizer = new StringTokenizer(string, ";");
        while (stringTokenizer.hasMoreTokens()) {
            String string2 = StringUtils.trimToEmpty((String)stringTokenizer.nextToken());
            ItemTemplate itemTemplate = ItemHolder.getInstance().getTemplate(Integer.parseInt(string2));
            hashSet.add(itemTemplate);
        }
        return hashSet;
    }

    private Set<Skill> c(String string) {
        HashSet<Skill> hashSet = new HashSet<Skill>(1);
        StringTokenizer stringTokenizer = new StringTokenizer(string, ";");
        while (stringTokenizer.hasMoreTokens()) {
            String string2 = StringUtils.trimToEmpty((String)stringTokenizer.nextToken());
            if (string2.isEmpty()) continue;
            int n = string2.indexOf(45);
            if (n < 1) {
                this._log.warn("Unknown skill: " + string2);
                continue;
            }
            Skill skill = SkillTable.getInstance().getInfo(Integer.parseInt(string2.substring(0, n)), Integer.parseInt(string2.substring(n + 1)));
            hashSet.add(skill);
        }
        return hashSet;
    }

    private void a(Element element, Set<ItemTemplate> set, String string) {
        String string2 = element.attributeValue(string);
        if (null != string2) {
            set.addAll(this.b(string2));
        }
    }

    @Override
    protected void readData(Element element) throws Exception {
        Iterator iterator = element.elementIterator("set");
        while (iterator.hasNext()) {
            String string;
            int n2;
            HashMap<Integer, Set<ItemTemplate>> hashMap = new HashMap<Integer, Set<ItemTemplate>>();
            Set<ItemTemplate> set = Collections.emptySet();
            Set<Skill> set2 = Collections.emptySet();
            Set<Skill> set3 = Collections.emptySet();
            HashMap<Integer, Set<Skill>> hashMap2 = new HashMap<Integer, Set<Skill>>();
            LinkedHashMap<Integer, Set<Skill>> linkedHashMap = new LinkedHashMap<Integer, Set<Skill>>();
            Element element2 = (Element)iterator.next();
            int n3 = Integer.parseInt(element2.attributeValue("id"));
            this.a(element2, hashMap.computeIfAbsent(6, n -> new HashSet()), "chest");
            this.a(element2, hashMap.computeIfAbsent(11, n -> new HashSet()), "legs");
            this.a(element2, hashMap.computeIfAbsent(1, n -> new HashSet()), "head");
            this.a(element2, hashMap.computeIfAbsent(10, n -> new HashSet()), "gloves");
            this.a(element2, hashMap.computeIfAbsent(12, n -> new HashSet()), "feet");
            this.a(element2, hashMap.computeIfAbsent(0, n -> new HashSet()), "underwear");
            if (element2.attributeValue("shield") != null) {
                set = this.b(element2.attributeValue("shield"));
            }
            if (element2.attributeValue("skills") != null) {
                set2 = this.c(element2.attributeValue("skills"));
            }
            if (element2.attributeValue("shield_skills") != null) {
                set3 = this.c(element2.attributeValue("shield_skills"));
            }
            for (n2 = 1; n2 < 128; ++n2) {
                string = element2.attributeValue(String.format("enchant%dskills", n2));
                if (string == null) continue;
                hashMap2.put(n2, this.c(string));
            }
            for (n2 = 1; n2 <= 7; ++n2) {
                string = element2.attributeValue(String.format("parts%dskills", n2));
                if (string == null) continue;
                linkedHashMap.put(n2, this.c(string));
            }
            ((ArmorSetsHolder)this.getHolder()).addArmorSet(new ArmorSet(n3, hashMap, set2, set, set3, hashMap2, linkedHashMap));
        }
    }
}
