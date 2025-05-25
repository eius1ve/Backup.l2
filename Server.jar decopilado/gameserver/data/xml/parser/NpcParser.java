/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.ArrayUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.dom4j.Element
 */
package l2.gameserver.data.xml.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import l2.commons.collections.MultiValueSet;
import l2.commons.data.xml.AbstractDirParser;
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.data.xml.holder.NpcHolder;
import l2.gameserver.model.Skill;
import l2.gameserver.model.TeleportLocation;
import l2.gameserver.model.base.ClassId;
import l2.gameserver.model.reward.RewardData;
import l2.gameserver.model.reward.RewardGroup;
import l2.gameserver.model.reward.RewardList;
import l2.gameserver.model.reward.RewardType;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.templates.StatsSet;
import l2.gameserver.templates.npc.AbsorbInfo;
import l2.gameserver.templates.npc.Faction;
import l2.gameserver.templates.npc.MinionData;
import l2.gameserver.templates.npc.NpcTemplate;
import l2.gameserver.utils.Location;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;

public final class NpcParser
extends AbstractDirParser<NpcHolder> {
    private static final NpcParser a = new NpcParser();

    public static NpcParser getInstance() {
        return a;
    }

    private NpcParser() {
        super(NpcHolder.getInstance());
    }

    @Override
    public File getXMLDir() {
        return new File(Config.DATAPACK_ROOT, "data/npc/");
    }

    @Override
    public boolean isIgnored(File file) {
        return false;
    }

    @Override
    public String getDTDFileName() {
        return "npc.dtd";
    }

    @Override
    protected void readData(Element element) throws Exception {
        Iterator iterator = element.elementIterator();
        while (iterator.hasNext()) {
            Object object;
            Object object2;
            Object object3;
            Object object4;
            Element element2 = (Element)iterator.next();
            int n = Integer.parseInt(element2.attributeValue("id"));
            int n2 = element2.attributeValue("template_id") == null ? 0 : Integer.parseInt(element2.attributeValue("id"));
            String string = element2.attributeValue("name");
            String string2 = element2.attributeValue("title");
            StatsSet statsSet = new StatsSet();
            statsSet.set("npcId", n);
            statsSet.set("displayId", n2);
            statsSet.set("name", string);
            statsSet.set("title", string2);
            statsSet.set("baseCpReg", 0);
            statsSet.set("baseCpMax", 0);
            Object object5 = element2.elementIterator();
            while (object5.hasNext()) {
                object4 = (Element)object5.next();
                if (object4.getName().equalsIgnoreCase("set")) {
                    statsSet.set(object4.attributeValue("name"), object4.attributeValue("value"));
                    continue;
                }
                if (object4.getName().equalsIgnoreCase("equip")) {
                    object3 = object4.elementIterator();
                    while (object3.hasNext()) {
                        object2 = (Element)object3.next();
                        int n3 = Integer.parseInt(object2.attributeValue("item_id"));
                        if (ItemHolder.getInstance().getTemplate(n3) == null) {
                            this._log.error("Undefined item " + n3 + " used in slot " + object2.getName() + " of npc " + n);
                        }
                        statsSet.set(object2.getName(), String.valueOf(n3));
                    }
                    continue;
                }
                if (object4.getName().equalsIgnoreCase("ai_params")) {
                    object3 = new StatsSet();
                    object2 = object4.elementIterator();
                    while (object2.hasNext()) {
                        Element element3 = (Element)object2.next();
                        ((MultiValueSet)object3).set(element3.attributeValue("name"), element3.attributeValue("value"));
                    }
                    statsSet.set("aiParams", object3);
                    continue;
                }
                if (!object4.getName().equalsIgnoreCase("attributes")) continue;
                object3 = new int[6];
                object2 = new int[6];
                Iterator iterator2 = object4.elementIterator();
                while (iterator2.hasNext()) {
                    l2.gameserver.model.base.Element element4;
                    object = (Element)iterator2.next();
                    if (object.getName().equalsIgnoreCase("defence")) {
                        element4 = l2.gameserver.model.base.Element.getElementByName(object.attributeValue("attribute"));
                        object2[element4.getId()] = (Element)Integer.parseInt(object.attributeValue("value"));
                        continue;
                    }
                    if (!object.getName().equalsIgnoreCase("attack")) continue;
                    element4 = l2.gameserver.model.base.Element.getElementByName(object.attributeValue("attribute"));
                    object3[element4.getId()] = Integer.parseInt(object.attributeValue("value"));
                }
                statsSet.set("baseAttributeAttack", (int[])object3);
                statsSet.set("baseAttributeDefence", (int[])object2);
            }
            object5 = new NpcTemplate(statsSet);
            object4 = new LinkedList();
            object3 = element2.elementIterator();
            while (object3.hasNext()) {
                Object object6;
                Object object7;
                int n4;
                Object object8;
                object2 = (Element)object3.next();
                String string3 = object2.getName();
                if (string3.equalsIgnoreCase("faction")) {
                    object = object2.attributeValue("name");
                    Faction faction = new Faction((String)object);
                    int n5 = Integer.parseInt(object2.attributeValue("range"));
                    faction.setRange(n5);
                    object8 = object2.elementIterator();
                    while (object8.hasNext()) {
                        Element element5 = (Element)object8.next();
                        n4 = Integer.parseInt(element5.attributeValue("npc_id"));
                        faction.addIgnoreNpcId(n4);
                    }
                    ((NpcTemplate)object5).setFaction(faction);
                    continue;
                }
                if (string3.equalsIgnoreCase("rewardlist")) {
                    object = RewardType.valueOf(object2.attributeValue("type"));
                    boolean bl = object2.attributeValue("auto_loot") != null && Boolean.parseBoolean(object2.attributeValue("auto_loot"));
                    RewardList rewardList = new RewardList((RewardType)((Object)object), bl);
                    if (object == RewardType.DISABLED) continue;
                    object8 = object2.elementIterator();
                    while (object8.hasNext()) {
                        Element element6 = (Element)object8.next();
                        String string4 = element6.getName();
                        if (string4.equalsIgnoreCase("group")) {
                            double d = element6.attributeValue("chance") == null ? 1000000.0 : Double.parseDouble(element6.attributeValue("chance")) * 10000.0;
                            RewardGroup rewardGroup = object == RewardType.SWEEP || object == RewardType.NOT_RATED_NOT_GROUPED ? null : new RewardGroup(d);
                            Iterator iterator3 = element6.elementIterator();
                            while (iterator3.hasNext()) {
                                Element element7 = (Element)iterator3.next();
                                object7 = this.a(element7, (RewardType)((Object)object));
                                if (object7 == null) continue;
                                if (object == RewardType.SWEEP || object == RewardType.NOT_RATED_NOT_GROUPED) {
                                    this.warn("Can't load rewardlist from group: " + n + "; type: " + (RewardType)((Object)object));
                                    continue;
                                }
                                rewardGroup.addData((RewardData)object7);
                            }
                            if (rewardGroup == null) continue;
                            rewardList.add(rewardGroup);
                            continue;
                        }
                        if (!string4.equalsIgnoreCase("reward")) continue;
                        if (object != RewardType.SWEEP && object != RewardType.NOT_RATED_NOT_GROUPED && object != RewardType.RATED_NOT_GROUPED) {
                            this.warn("Reward can't be without group(and not grouped): " + n + "; type: " + (RewardType)((Object)object));
                            continue;
                        }
                        RewardData rewardData = this.a(element6, (RewardType)((Object)object));
                        if (rewardData == null) continue;
                        object6 = new RewardGroup(1000000.0);
                        object6.addData(rewardData);
                        rewardList.add(object6);
                    }
                    if (!(object != RewardType.RATED_GROUPED && object != RewardType.NOT_RATED_GROUPED || rewardList.validate())) {
                        this.warn("Problems with rewardlist for npc: " + n + "; type: " + (RewardType)((Object)object));
                    }
                    ((NpcTemplate)object5).putRewardList((RewardType)((Object)object), rewardList);
                    continue;
                }
                if (string3.equalsIgnoreCase("skills")) {
                    object = object2.elementIterator();
                    while (object.hasNext()) {
                        Skill skill;
                        Element element8 = (Element)object.next();
                        int n6 = Integer.parseInt(element8.attributeValue("id"));
                        int n7 = Integer.parseInt(element8.attributeValue("level"));
                        if (SkillTable.getInstance().getInfo(n6, n7) == null) {
                            this._log.error("Undefined id " + n6 + " and level " + n7 + " of npc " + n);
                        }
                        if (n6 == 4416) {
                            ((NpcTemplate)object5).setRace(n7);
                        }
                        if ((skill = SkillTable.getInstance().getInfo(n6, n7)) == null) continue;
                        ((NpcTemplate)object5).addSkill(skill);
                    }
                    continue;
                }
                if (string3.equalsIgnoreCase("minions")) {
                    object = object2.elementIterator();
                    while (object.hasNext()) {
                        Element element9 = (Element)object.next();
                        int n8 = Integer.parseInt(element9.attributeValue("npc_id"));
                        int n9 = Integer.parseInt(element9.attributeValue("count"));
                        ((NpcTemplate)object5).addMinion(new MinionData(n8, n9));
                    }
                    continue;
                }
                if (string3.equalsIgnoreCase("teach_classes")) {
                    object = object2.elementIterator();
                    while (object.hasNext()) {
                        Element element10 = (Element)object.next();
                        int n10 = Integer.parseInt(element10.attributeValue("id"));
                        ((NpcTemplate)object5).addTeachInfo(ClassId.VALUES[n10]);
                    }
                    continue;
                }
                if (string3.equalsIgnoreCase("absorblist")) {
                    object = object2.elementIterator();
                    while (object.hasNext()) {
                        Element element11 = (Element)object.next();
                        int n11 = Integer.parseInt(element11.attributeValue("chance"));
                        int n12 = element11.attributeValue("cursed_chance") == null ? 0 : Integer.parseInt(element11.attributeValue("cursed_chance"));
                        int n13 = Integer.parseInt(element11.attributeValue("min_level"));
                        n4 = Integer.parseInt(element11.attributeValue("max_level"));
                        boolean bl = element11.attributeValue("skill") != null && Boolean.parseBoolean(element11.attributeValue("skill"));
                        object6 = AbsorbInfo.AbsorbType.valueOf(element11.attributeValue("type"));
                        ((NpcTemplate)object5).addAbsorbInfo(new AbsorbInfo(bl, (AbsorbInfo.AbsorbType)((Object)object6), n11, n12, n13, n4));
                    }
                    continue;
                }
                if (!string3.equalsIgnoreCase("teleportlist")) continue;
                object = object2.elementIterator();
                while (object.hasNext()) {
                    Element element12 = (Element)object.next();
                    int n14 = Integer.parseInt(element12.attributeValue("id"));
                    object8 = new ArrayList();
                    Iterator iterator4 = element12.elementIterator();
                    while (iterator4.hasNext()) {
                        Element element13 = (Element)iterator4.next();
                        int n15 = Integer.parseInt(element13.attributeValue("item_id", "57"));
                        long l = Integer.parseInt(element13.attributeValue("price"));
                        int n16 = Integer.parseInt(element13.attributeValue("min_level", "0"));
                        int n17 = Integer.parseInt(element13.attributeValue("max_level", "0"));
                        object7 = element13.attributeValue("name").trim();
                        int n18 = Integer.parseInt(element13.attributeValue("fstring", "0"));
                        int n19 = Integer.parseInt(element13.attributeValue("castle_id", "0"));
                        int n20 = Integer.parseInt(element13.attributeValue("key_item_id", "-1"));
                        TeleportLocation teleportLocation = new TeleportLocation(n15, l, n16, n17, (String)object7, n18, n19, n20);
                        teleportLocation.set(Location.parseLoc(element13.attributeValue("loc")));
                        if (n16 > 0 || n17 > 0) {
                            Iterator iterator5 = object4.iterator();
                            while (iterator5.hasNext()) {
                                Location location = (Location)iterator5.next();
                                if (location.x != teleportLocation.x || location.y != teleportLocation.y || location.z != teleportLocation.z) continue;
                                this._log.warn("Teleport location may intersect for " + element13.asXML());
                            }
                        }
                        object4.add(teleportLocation);
                        object8.add(teleportLocation);
                    }
                    ((NpcTemplate)object5).addTeleportList(n14, object8.toArray(new TeleportLocation[object8.size()]));
                }
            }
            ((NpcHolder)this.getHolder()).addTemplate((NpcTemplate)object5);
        }
    }

    private RewardData a(Element element, RewardType rewardType) {
        RewardData rewardData;
        int n = Integer.parseInt(element.attributeValue("item_id"));
        if (rewardType == RewardType.SWEEP ? ArrayUtils.contains((int[])Config.NO_DROP_ITEMS_FOR_SWEEP, (int)n) : ArrayUtils.contains((int[])Config.NO_DROP_ITEMS, (int)n)) {
            return null;
        }
        if (Config.ALLOW_ONLY_THESE_DROP_ITEMS_ID != null && Config.ALLOW_ONLY_THESE_DROP_ITEMS_ID.length != 0 && !ArrayUtils.contains((int[])Config.ALLOW_ONLY_THESE_DROP_ITEMS_ID, (int)n)) {
            return null;
        }
        int n2 = Integer.parseInt(element.attributeValue("min"));
        int n3 = Integer.parseInt(element.attributeValue("max"));
        String string = element.attributeValue("enchant_min");
        String string2 = element.attributeValue("enchant_max");
        int n4 = (int)(Double.parseDouble(element.attributeValue("chance")) * 10000.0);
        if (rewardType == RewardType.SWEEP && (ArrayUtils.isEmpty((int[])Config.SPOIL_ITEMS_ID_FOR_RATE) || ArrayUtils.contains((int[])Config.SPOIL_ITEMS_ID_FOR_RATE, (int)n))) {
            n4 = (int)((double)n4 * Config.SPOIL_ITEMS_CHANCE_RATE);
        }
        if ((rewardData = new RewardData(n)).getItem().isHerb()) {
            rewardData.setChance((double)n4 * Config.RATE_DROP_HERBS);
        } else {
            rewardData.setChance(n4);
        }
        if (StringUtils.isNumeric((CharSequence)string) && StringUtils.isNumeric((CharSequence)string2) && rewardData.getItem().isEnchantable()) {
            rewardData.setEnchantMin(Math.min(Integer.parseInt(string), 65535));
            rewardData.setEnchantMax(Math.min(Integer.parseInt(string2), 65535));
        }
        rewardData.setMinDrop(n2);
        rewardData.setMaxDrop(n3);
        return rewardData;
    }
}
