/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.TIntObjectHashMap
 *  org.dom4j.Element
 */
package l2.gameserver.data.xml.parser;

import gnu.trove.TIntObjectHashMap;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import l2.commons.data.xml.AbstractDirParser;
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.SkillAcquireHolder;
import l2.gameserver.model.SkillLearn;
import l2.gameserver.model.base.ClassType2;
import org.dom4j.Element;

public final class SkillAcquireParser
extends AbstractDirParser<SkillAcquireHolder> {
    private static final SkillAcquireParser a = new SkillAcquireParser();

    public static SkillAcquireParser getInstance() {
        return a;
    }

    protected SkillAcquireParser() {
        super(SkillAcquireHolder.getInstance());
    }

    @Override
    public File getXMLDir() {
        return new File(Config.DATAPACK_ROOT, "data/skill_tree/");
    }

    @Override
    public boolean isIgnored(File file) {
        return false;
    }

    @Override
    public String getDTDFileName() {
        return "tree.dtd";
    }

    @Override
    protected void readData(Element element) throws Exception {
        List<SkillLearn> list;
        int n;
        Object object;
        Iterator iterator;
        Element element2;
        Iterator iterator2 = element.elementIterator("pledge_skill_tree");
        while (iterator2.hasNext()) {
            ((SkillAcquireHolder)this.getHolder()).addAllPledgeLearns(this.b((Element)iterator2.next()));
        }
        iterator2 = element.elementIterator("fishing_skill_tree");
        while (iterator2.hasNext()) {
            element2 = (Element)iterator2.next();
            iterator = element2.elementIterator("race");
            while (iterator.hasNext()) {
                object = (Element)iterator.next();
                n = Integer.parseInt(object.attributeValue("id"));
                list = this.b((Element)object);
                ((SkillAcquireHolder)this.getHolder()).addAllFishingLearns(n, list);
            }
        }
        iterator2 = element.elementIterator("custom_skill_tree");
        while (iterator2.hasNext()) {
            element2 = (Element)iterator2.next();
            iterator = element2.elementIterator("race");
            while (iterator.hasNext()) {
                object = (Element)iterator.next();
                n = Integer.parseInt(object.attributeValue("id"));
                list = this.b((Element)object);
                ((SkillAcquireHolder)this.getHolder()).addAllCustomLearns(n, list);
            }
        }
        iterator2 = element.elementIterator("normal_skill_tree");
        while (iterator2.hasNext()) {
            element2 = new TIntObjectHashMap();
            iterator = (Element)iterator2.next();
            object = iterator.elementIterator("class");
            while (object.hasNext()) {
                Element element3 = (Element)object.next();
                int n2 = Integer.parseInt(element3.attributeValue("id"));
                List<SkillLearn> list2 = this.b(element3);
                element2.put(n2, list2);
            }
            ((SkillAcquireHolder)this.getHolder()).addAllNormalSkillLearns((TIntObjectHashMap<List<SkillLearn>>)element2);
        }
        iterator2 = element.elementIterator("nobles_skill_tree");
        while (iterator2.hasNext()) {
            ((SkillAcquireHolder)this.getHolder()).addAllNoblesseLearns(this.b((Element)iterator2.next()));
        }
        iterator2 = element.elementIterator("hero_skill_tree");
        while (iterator2.hasNext()) {
            ((SkillAcquireHolder)this.getHolder()).addAllHeroLearns(this.b((Element)iterator2.next()));
        }
        iterator2 = element.elementIterator("clan_leader_skill_tree");
        while (iterator2.hasNext()) {
            ((SkillAcquireHolder)this.getHolder()).addAllClanLeaderLearns(this.b((Element)iterator2.next()));
        }
        iterator2 = element.elementIterator("premium_account_skill_tree");
        while (iterator2.hasNext()) {
            ((SkillAcquireHolder)this.getHolder()).addPremiumAccountLearns(this.b((Element)iterator2.next()));
        }
    }

    private List<SkillLearn> b(Element element) {
        ArrayList<SkillLearn> arrayList = new ArrayList<SkillLearn>();
        Iterator iterator = element.elementIterator("skill");
        while (iterator.hasNext()) {
            Element element2 = (Element)iterator.next();
            int n = Integer.parseInt(element2.attributeValue("id"));
            int n2 = Integer.parseInt(element2.attributeValue("level"));
            int n3 = element2.attributeValue("cost") == null ? 0 : Integer.parseInt(element2.attributeValue("cost"));
            int n4 = Integer.parseInt(element2.attributeValue("min_level"));
            int n5 = element2.attributeValue("item_id") == null ? 0 : Integer.parseInt(element2.attributeValue("item_id"));
            long l = element2.attributeValue("item_count") == null ? 1L : Long.parseLong(element2.attributeValue("item_count"));
            boolean bl = element2.attributeValue("clicked") != null && Boolean.parseBoolean(element2.attributeValue("clicked"));
            boolean bl2 = Boolean.parseBoolean(element2.attributeValue("auto_learn", "true"));
            ClassType2 classType2 = ClassType2.valueOf(element2.attributeValue("classtype2", "None"));
            arrayList.add(new SkillLearn(n, n2, n4, n3 * Config.SKILL_COST_RATE, n5, l, bl, classType2, bl2));
        }
        return arrayList;
    }
}
