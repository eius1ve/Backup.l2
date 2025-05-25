/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.skills;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import l2.gameserver.data.xml.holder.EnchantSkillHolder;
import l2.gameserver.model.Skill;
import l2.gameserver.skills.DocumentBase;
import l2.gameserver.stats.conditions.Condition;
import l2.gameserver.templates.SkillEnchant;
import l2.gameserver.templates.StatsSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

@Deprecated
public final class DocumentSkill
extends DocumentBase {
    private static final Logger dk = LoggerFactory.getLogger(DocumentSkill.class);
    private static final String fV = "enchant";
    private static final Comparator<Integer> f = new Comparator<Integer>(){

        @Override
        public int compare(Integer n, Integer n2) {
            return n - n2;
        }
    };
    protected Map<String, Map<Integer, Object>> tables = new LinkedHashMap<String, Map<Integer, Object>>();
    private SkillLoad a = null;
    private Set<String> D = new HashSet<String>();
    private List<Skill> dh = new LinkedList<Skill>();

    DocumentSkill(File file) {
        super(file);
    }

    protected void resetTable() {
        if (!this.D.isEmpty()) {
            for (String string : this.tables.keySet()) {
                if (this.D.contains(string)) continue;
                dk.warn("Unused table " + string + " for skill " + this.a.id);
            }
        }
        this.D.clear();
        this.tables.clear();
    }

    private void a(SkillLoad skillLoad) {
        this.a = skillLoad;
    }

    protected List<Skill> getSkills() {
        return this.dh;
    }

    @Override
    protected Object getTableValue(String string) {
        Map<Integer, Object> map = this.tables.get(string);
        if (map == null) {
            dk.error("No table " + string + " for skill " + this.a.id);
            return 0;
        }
        if (!map.containsKey(this.a.currentLevel)) {
            dk.error("No value in table " + string + " for skill " + this.a.id + " at level " + this.a.currentLevel);
            return 0;
        }
        this.D.add(string);
        return map.get(this.a.currentLevel);
    }

    @Override
    protected Object getTableValue(String string, int n) {
        Map<Integer, Object> map = this.tables.get(string);
        if (map == null) {
            dk.error("No table " + string + " for skill " + this.a.id);
            return 0;
        }
        if (!map.containsKey(n)) {
            dk.error("No value in table " + string + " for skill " + this.a.id + " at level " + n);
            return 0;
        }
        this.D.add(string);
        return map.get(n);
    }

    @Override
    protected void parseDocument(Document document) {
        for (Node node = document.getFirstChild(); node != null; node = node.getNextSibling()) {
            if ("list".equalsIgnoreCase(node.getNodeName())) {
                for (Node node2 = node.getFirstChild(); node2 != null; node2 = node2.getNextSibling()) {
                    if (!"skill".equalsIgnoreCase(node2.getNodeName())) continue;
                    this.parseSkill(node2);
                    this.dh.addAll(this.a.skills);
                    this.resetTable();
                }
                continue;
            }
            if (!"skill".equalsIgnoreCase(node.getNodeName())) continue;
            this.parseSkill(node);
            this.dh.addAll(this.a.skills);
        }
    }

    private void a(Node node, int n, int n2) {
        NamedNodeMap namedNodeMap = node.getAttributes();
        String string = namedNodeMap.getNamedItem("name").getNodeValue();
        Object[] objectArray = this.a(this.parseTable(node), n2);
        Map<Integer, Object> map = this.tables.get(string);
        if (map == null) {
            map = new TreeMap<Integer, Object>(f);
            this.tables.put(string, map);
        }
        for (int i = 0; i < objectArray.length; ++i) {
            int n3 = n + i;
            if (map.containsKey(n3)) {
                dk.error("Duplicate skill level " + n3 + " in table " + string + " in skill " + this.a.id);
                return;
            }
            map.put(n3, objectArray[i]);
        }
    }

    protected void parseSkill(Node node) {
        NamedNodeMap namedNodeMap = node.getAttributes();
        int n = Integer.parseInt(namedNodeMap.getNamedItem("id").getNodeValue());
        String string = namedNodeMap.getNamedItem("name").getNodeValue();
        int n2 = Integer.parseInt(namedNodeMap.getNamedItem("levels").getNodeValue());
        this.a(new SkillLoad(n, string));
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        HashMap<Integer, String> hashMap = new HashMap<Integer, String>();
        try {
            String string2;
            Node node2;
            Object object;
            Object object2;
            for (int i = 1; i <= n2; ++i) {
                arrayList.add(i);
            }
            Node node3 = node.cloneNode(true);
            int n3 = node3.getChildNodes().getLength();
            for (int i = 0; i < n3; ++i) {
                Object object3;
                int n4;
                int n5 = 0;
                object2 = node3.getChildNodes().item(i);
                object = object2.getNodeName();
                if (!((String)object).startsWith(fV)) continue;
                try {
                    n4 = Integer.parseInt(((String)object).substring(fV.length()));
                }
                catch (NumberFormatException numberFormatException) {
                    dk.error("Wrong enchant " + (String)object + " in skill " + n);
                    break;
                }
                int n6 = EnchantSkillHolder.getInstance().getFirstSkillLevelOf(n, n4);
                node2 = object2.getAttributes().getNamedItem("levels");
                if (node2 != null) {
                    n5 = Integer.parseInt(node2.getNodeValue());
                } else {
                    dk.warn("Skill " + n + " have no enchant levels in route " + n4 + ".");
                    n5 = EnchantSkillHolder.getInstance().getMaxEnchantLevelOf(n);
                }
                string2 = null;
                Node node4 = object2.getAttributes().getNamedItem("name");
                if (node2 == null) {
                    throw new RuntimeException("Skill " + n + " has no name for enchant route " + n4);
                }
                string2 = node4.getNodeValue();
                int n7 = EnchantSkillHolder.getInstance().getMaxEnchantLevelOf(n);
                if (n5 != n7) {
                    dk.warn("Unknown enchant levels " + n5 + " for skill " + n + ". Actual " + n7);
                }
                for (int j = 1; j <= n5; ++j) {
                    object3 = EnchantSkillHolder.getInstance().getSkillEnchant(n, n4, j);
                    if (object3 == null) {
                        dk.error("No enchant level " + j + " in route " + n4 + " for skill " + n);
                        break;
                    }
                    arrayList.add(((SkillEnchant)object3).getSkillLevel());
                    hashMap.put(((SkillEnchant)object3).getSkillLevel(), string2);
                }
                for (Node node5 = object2.getFirstChild(); node5 != null; node5 = node5.getNextSibling()) {
                    if ("table".equalsIgnoreCase(node5.getNodeName())) {
                        object3 = node5;
                        this.a(node5, n6, n5);
                        continue;
                    }
                    if (node5.getNodeType() != 1) continue;
                    dk.error("Unknown element of enchant \"" + node5.getNodeName() + "\" in skill " + n);
                }
            }
            for (Iterator<Object> iterator = node.getFirstChild(); iterator != null; iterator = iterator.getNextSibling()) {
                if (!"table".equalsIgnoreCase(iterator.getNodeName())) continue;
                this.a((Node)((Object)iterator), 1, n2);
            }
            for (Map.Entry entry : this.tables.entrySet()) {
                Map map = (Map)entry.getValue();
                object2 = map.get(n2);
                for (Integer n8 : arrayList) {
                    if (n8 <= n2 || map.containsKey(n8)) continue;
                    map.put(n8, object2);
                }
            }
            for (Integer n9 : arrayList) {
                StatsSet statsSet = new StatsSet();
                statsSet.set("skill_id", this.a.id);
                statsSet.set("level", n9);
                statsSet.set("name", this.a.name);
                statsSet.set("base_level", n2);
                statsSet.set("enchantRouteName", (String)hashMap.get(n9));
                this.a.sets.put(n9, statsSet);
            }
            for (Integer n10 : arrayList) {
                for (Node node6 = node.getFirstChild(); node6 != null; node6 = node6.getNextSibling()) {
                    if (!"set".equalsIgnoreCase(node6.getNodeName())) continue;
                    object2 = this.a.sets.get(n10);
                    this.a.currentLevel = n10;
                    this.parseBeanSet(node6, (StatsSet)object2, n10);
                }
            }
            for (StatsSet statsSet : this.a.sets.values()) {
                Skill skill = statsSet.getEnum("skillType", Skill.SkillType.class).makeSkill(statsSet);
                this.a.currentSkills.put(skill.getLevel(), skill);
            }
            for (Integer n11 : arrayList) {
                this.a.currentLevel = n11;
                Skill skill = this.a.currentSkills.get(n11);
                if (skill == null) {
                    dk.error("Undefined skill id " + n + " level " + n11);
                    return;
                }
                skill.setDisplayLevel(n11);
                for (object2 = node.getFirstChild(); object2 != null; object2 = object2.getNextSibling()) {
                    object = object2.getNodeName();
                    if ("cond".equalsIgnoreCase((String)object)) {
                        Condition condition = this.parseCondition(object2.getFirstChild());
                        if (condition == null) continue;
                        Node node7 = object2.getAttributes().getNamedItem("msgId");
                        if (node7 != null) {
                            int n12 = this.parseNumber(node7.getNodeValue()).intValue();
                            condition.setSystemMsg(n12);
                        }
                        if ((node2 = object2.getAttributes().getNamedItem("customMessage")) != null) {
                            string2 = node2.getNodeValue();
                            condition.setCustomMessage(string2);
                        }
                        skill.attach(condition);
                        continue;
                    }
                    if ("for".equalsIgnoreCase((String)object)) {
                        this.parseTemplate((Node)object2, skill);
                        continue;
                    }
                    if (!"triggers".equalsIgnoreCase((String)object)) continue;
                    this.parseTrigger((Node)object2, skill);
                }
            }
            this.a.skills.addAll(this.a.currentSkills.values());
        }
        catch (Exception exception) {
            dk.error("Error loading skill " + n, (Throwable)exception);
        }
    }

    protected Object[] parseTable(Node node) {
        NamedNodeMap namedNodeMap = node.getAttributes();
        String string = namedNodeMap.getNamedItem("name").getNodeValue();
        if (string.charAt(0) != '#') {
            throw new IllegalArgumentException("Table name must start with #");
        }
        StringTokenizer stringTokenizer = new StringTokenizer(node.getFirstChild().getNodeValue());
        ArrayList<String> arrayList = new ArrayList<String>();
        while (stringTokenizer.hasMoreTokens()) {
            arrayList.add(stringTokenizer.nextToken());
        }
        Object[] objectArray = arrayList.toArray(new Object[arrayList.size()]);
        return objectArray;
    }

    private Object[] a(Object[] objectArray, int n) {
        if (objectArray.length < n) {
            Object[] objectArray2 = new Object[n];
            System.arraycopy(objectArray, 0, objectArray2, 0, objectArray.length);
            objectArray = objectArray2;
        }
        for (int i = 1; i < n; ++i) {
            if (objectArray[i] != null) continue;
            objectArray[i] = objectArray[i - 1];
        }
        return objectArray;
    }

    public class SkillLoad {
        public final int id;
        public final String name;
        public final Map<Integer, StatsSet> sets;
        public final List<Skill> skills;
        public final Map<Integer, Skill> currentSkills;
        public int currentLevel;

        public SkillLoad(int n, String string) {
            this.id = n;
            this.name = string;
            this.sets = new TreeMap<Integer, StatsSet>(f);
            this.skills = new ArrayList<Skill>();
            this.currentSkills = new TreeMap<Integer, Skill>(f);
        }
    }
}
