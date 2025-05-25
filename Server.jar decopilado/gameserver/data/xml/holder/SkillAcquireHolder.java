/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.TIntObjectHashMap
 *  gnu.trove.TIntObjectIterator
 */
package l2.gameserver.data.xml.holder;

import gnu.trove.TIntObjectHashMap;
import gnu.trove.TIntObjectIterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import l2.commons.data.xml.AbstractHolder;
import l2.gameserver.Config;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.SkillLearn;
import l2.gameserver.model.SubClass;
import l2.gameserver.model.base.AcquireType;
import l2.gameserver.model.base.ClassId;
import l2.gameserver.model.base.ClassType2;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.model.pledge.SubUnit;

public final class SkillAcquireHolder
extends AbstractHolder {
    private static final SkillAcquireHolder a = new SkillAcquireHolder();
    private TIntObjectHashMap<List<SkillLearn>> n = new TIntObjectHashMap();
    private TIntObjectHashMap<List<SkillLearn>> o = new TIntObjectHashMap();
    private TIntObjectHashMap<List<SkillLearn>> p = new TIntObjectHashMap();
    private List<SkillLearn> ai = new ArrayList<SkillLearn>();
    private List<SkillLearn> aj = new ArrayList<SkillLearn>();
    private List<SkillLearn> ak = new ArrayList<SkillLearn>();
    private List<SkillLearn> al = new ArrayList<SkillLearn>();
    private List<SkillLearn> am = new ArrayList<SkillLearn>();

    public static SkillAcquireHolder getInstance() {
        return a;
    }

    public int getMinLevelForNewSkill(ClassId classId, int n, AcquireType acquireType) {
        List list;
        switch (acquireType) {
            case NORMAL: {
                list = (List)this.n.get(classId.getId());
                if (list != null) break;
                this.info("skill tree for class " + classId.getId() + " is not defined !");
                return 0;
            }
            default: {
                return 0;
            }
        }
        int n2 = 0;
        for (SkillLearn skillLearn : list) {
            if (skillLearn.getMinLevel() <= n || n2 != 0 && skillLearn.getMinLevel() >= n2) continue;
            n2 = skillLearn.getMinLevel();
        }
        return n2;
    }

    public Collection<SkillLearn> getAvailableSkills(Player player, AcquireType acquireType) {
        return this.getAvailableSkills(player, player.getClassId(), acquireType, null, 0);
    }

    public Collection<SkillLearn> getAvailableSkills(Player player, AcquireType acquireType, int n) {
        return this.getAvailableSkills(player, player.getClassId(), acquireType, null, n);
    }

    public Collection<SkillLearn> getAvailableSkills(Player player, ClassId classId, AcquireType acquireType, SubUnit subUnit, int n) {
        switch (acquireType) {
            case NORMAL: {
                Collection collection = (Collection)this.n.get(classId.getId());
                if (collection == null) {
                    this.info("skill tree for class " + classId + " is not defined !");
                    return Collections.emptyList();
                }
                return this.a(collection, player.getAllSkillsArray(), player.getLevel() + n);
            }
            case FISHING: {
                Collection collection = (Collection)this.o.get(player.getRace().ordinal());
                if (collection == null) {
                    this.info("skill tree for race " + player.getRace().ordinal() + " is not defined !");
                    return Collections.emptyList();
                }
                return this.a(collection, player.getAllSkillsArray(), player.getLevel());
            }
            case CERTIFICATION: {
                Collection collection = (Collection)this.p.get(player.getRace().ordinal());
                if (collection == null) {
                    this.info("custom skill tree for race " + player.getRace().ordinal() + " is not defined !");
                    return Collections.emptyList();
                }
                return this.a(collection, player.getAllSkillsArray(), player.getLevel());
            }
            case CLAN: {
                List<SkillLearn> list = this.ai;
                Collection<Skill> collection = player.getClan().getSkills();
                return this.a(list, collection.toArray(new Skill[collection.size()]), player.getClan().getLevel());
            }
            case NOBLES: {
                List<SkillLearn> list = this.aj;
                if (list == null) {
                    this.info("Nobles skill tree is not defined !");
                    return Collections.emptyList();
                }
                return this.a(list, player.getAllSkillsArray(), player.getLevel(), player);
            }
            case HERO: {
                List<SkillLearn> list = this.ak;
                if (list == null) {
                    this.info("Hero skill tree is not defined !");
                    return Collections.emptyList();
                }
                return this.a(list, player.getAllSkillsArray(), player.getLevel(), player);
            }
            case PREMIUM_ACCOUNT: {
                List<SkillLearn> list = this.am;
                if (list == null) {
                    this.info("Premium skill tree is not defined !");
                    return Collections.emptyList();
                }
                return this.a(list, player.getAllSkillsArray(), player.getLevel(), player);
            }
            case CLAN_LEADER: {
                List<SkillLearn> list = this.al;
                if (list == null) {
                    this.info("Hero skill tree is not defined !");
                    return Collections.emptyList();
                }
                return this.a(list, player.getAllSkillsArray(), player.getLevel(), player);
            }
        }
        return Collections.emptyList();
    }

    private Collection<SkillLearn> a(Collection<SkillLearn> collection, Skill[] skillArray, int n) {
        return this.a(collection, skillArray, n, null);
    }

    private Collection<SkillLearn> a(Collection<SkillLearn> collection, Skill[] skillArray, int n, Player player) {
        TreeMap<Integer, SkillLearn> treeMap = new TreeMap<Integer, SkillLearn>();
        for (SkillLearn skillLearn : collection) {
            boolean bl;
            if (skillLearn.getMinLevel() > n) continue;
            if (player != null && skillLearn.getClassType2() != ClassType2.None) {
                bl = false;
                for (Map.Entry<Integer, SubClass> entry : player.getSubClasses().entrySet()) {
                    if (entry.getValue().isBase()) continue;
                    for (ClassId classId : ClassId.values()) {
                        if (classId.getId() != entry.getKey().intValue() || classId.getType2() != skillLearn.getClassType2()) continue;
                        bl = true;
                    }
                }
                if (!bl) continue;
            }
            bl = false;
            for (int i = 0; i < skillArray.length && !bl; ++i) {
                if (skillArray[i].getId() != skillLearn.getId()) continue;
                bl = true;
                if (skillArray[i].getLevel() != skillLearn.getLevel() - 1) continue;
                treeMap.put(skillLearn.getId(), skillLearn);
            }
            if (bl || skillLearn.getLevel() != 1) continue;
            treeMap.put(skillLearn.getId(), skillLearn);
        }
        return treeMap.values();
    }

    public List<SkillLearn> getAllSkillLearn(Player player, ClassId classId, AcquireType acquireType) {
        switch (acquireType) {
            case NORMAL: {
                return (List)this.n.get(classId.getId());
            }
            case FISHING: {
                return (List)this.o.get(player.getRace().ordinal());
            }
            case CERTIFICATION: {
                return (List)this.p.get(player.getRace().ordinal());
            }
            case CLAN: {
                return this.ai;
            }
            case NOBLES: {
                return this.aj;
            }
            case HERO: {
                return this.ak;
            }
            case PREMIUM_ACCOUNT: {
                return this.am;
            }
            case CLAN_LEADER: {
                return this.al;
            }
        }
        return Collections.emptyList();
    }

    public SkillLearn getSkillLearn(Player player, ClassId classId, int n, int n2, AcquireType acquireType) {
        List<SkillLearn> list = this.getAllSkillLearn(player, classId, acquireType);
        if (list == null || list.isEmpty()) {
            return null;
        }
        for (SkillLearn skillLearn : list) {
            if (skillLearn.getLevel() != n2 || skillLearn.getId() != n) continue;
            return skillLearn;
        }
        return null;
    }

    public boolean isSkillPossible(Player player, Skill skill, AcquireType acquireType) {
        List list;
        Clan clan = null;
        switch (acquireType) {
            case NORMAL: {
                list = (List)this.n.get(player.getActiveClassId());
                break;
            }
            case FISHING: {
                list = (List)this.o.get(player.getRace().ordinal());
                break;
            }
            case CERTIFICATION: {
                if (!Config.ALT_ALLOW_CUSTOM_SKILL_LEARN) {
                    return false;
                }
                list = (List)this.p.get(player.getRace().ordinal());
                break;
            }
            case CLAN: {
                clan = player.getClan();
                if (clan == null) {
                    return false;
                }
                list = this.ai;
                break;
            }
            case NOBLES: {
                if (!player.isNoble()) {
                    return false;
                }
                list = this.aj;
                break;
            }
            case HERO: {
                if (!player.isHero()) {
                    return false;
                }
                list = this.ak;
                break;
            }
            case PREMIUM_ACCOUNT: {
                if (!player.hasBonus()) {
                    return false;
                }
                list = this.am;
                break;
            }
            case CLAN_LEADER: {
                if (!player.isClanLeader()) {
                    return false;
                }
                list = this.al;
                break;
            }
            default: {
                return false;
            }
        }
        return this.a(list, skill);
    }

    public boolean isSkillPossible(Player player, ClassId classId, Skill skill, AcquireType acquireType) {
        List list;
        Clan clan = null;
        switch (acquireType) {
            case NORMAL: {
                list = (List)this.n.get(classId.getId());
                break;
            }
            case FISHING: {
                list = (List)this.o.get(player.getRace().ordinal());
                break;
            }
            case CERTIFICATION: {
                if (!Config.ALT_ALLOW_CUSTOM_SKILL_LEARN) {
                    return false;
                }
                list = (List)this.p.get(player.getRace().ordinal());
                break;
            }
            case CLAN: {
                clan = player.getClan();
                if (clan == null) {
                    return false;
                }
                list = this.ai;
                break;
            }
            default: {
                return false;
            }
        }
        return this.a(list, skill);
    }

    private boolean a(Collection<SkillLearn> collection, Skill skill) {
        for (SkillLearn skillLearn : collection) {
            if (skillLearn.getId() != skill.getId() || skillLearn.getLevel() > skill.getLevel()) continue;
            return true;
        }
        return false;
    }

    public boolean isSkillPossible(Player player, Skill skill) {
        for (AcquireType acquireType : AcquireType.VALUES) {
            if (!this.isSkillPossible(player, skill, acquireType)) continue;
            return true;
        }
        return false;
    }

    public boolean isSkillPossible(Player player, ClassId classId, Skill skill) {
        for (AcquireType acquireType : AcquireType.VALUES) {
            if (!this.isSkillPossible(player, classId, skill, acquireType)) continue;
            return true;
        }
        return false;
    }

    public List<SkillLearn> getSkillLearnListByItemId(Player player, int n) {
        List list = (List)this.n.get(player.getActiveClassId());
        if (list == null) {
            return Collections.emptyList();
        }
        ArrayList<SkillLearn> arrayList = new ArrayList<SkillLearn>(1);
        for (SkillLearn skillLearn : list) {
            if (skillLearn.getItemId() != n) continue;
            arrayList.add(skillLearn);
        }
        return arrayList;
    }

    public List<SkillLearn> getAllNormalSkillTreeWithForgottenScrolls() {
        ArrayList<SkillLearn> arrayList = new ArrayList<SkillLearn>();
        TIntObjectIterator tIntObjectIterator = this.n.iterator();
        while (tIntObjectIterator.hasNext()) {
            tIntObjectIterator.advance();
            for (SkillLearn skillLearn : (List)tIntObjectIterator.value()) {
                if (skillLearn.getItemId() <= 0 || !skillLearn.isClicked()) continue;
                arrayList.add(skillLearn);
            }
        }
        return arrayList;
    }

    public void addAllNormalSkillLearns(TIntObjectHashMap<List<SkillLearn>> tIntObjectHashMap) {
        for (ClassId classId : ClassId.VALUES) {
            if (classId.getRace() == null) continue;
            int n = classId.getId();
            List list = (List)tIntObjectHashMap.get(n);
            if (list == null) {
                this.info("Not found NORMAL skill learn for class " + n);
                continue;
            }
            this.n.put(classId.getId(), (Object)list);
            ClassId classId2 = classId.getParent();
            if (classId2 == classId.getParent()) {
                classId2 = null;
            }
            classId = classId.getParent();
            while (classId != null) {
                List list2 = (List)this.n.get(classId.getId());
                list.addAll(list2);
                if ((classId = classId.getParent()) != null || classId2 == null) continue;
                classId = classId2;
                classId2 = classId2.getParent();
            }
        }
    }

    public void addAllFishingLearns(int n, List<SkillLearn> list) {
        this.o.put(n, list);
    }

    public void addAllCustomLearns(int n, List<SkillLearn> list) {
        this.p.put(n, list);
    }

    public void addAllPledgeLearns(List<SkillLearn> list) {
        this.ai.addAll(list);
    }

    public void addAllNoblesseLearns(List<SkillLearn> list) {
        this.aj.addAll(list);
    }

    public void addAllHeroLearns(List<SkillLearn> list) {
        this.ak.addAll(list);
    }

    public void addAllClanLeaderLearns(List<SkillLearn> list) {
        this.al.addAll(list);
    }

    public void addPremiumAccountLearns(List<SkillLearn> list) {
        this.am.addAll(list);
    }

    @Override
    public void log() {
        this.info("load " + this.a(this.n) + " normal learns for " + this.n.size() + " classes.");
        this.info("load " + this.a(this.o) + " fishing learns for " + this.o.size() + " races.");
        this.info("load " + this.ai.size() + " pledge learns.");
        this.info("load " + this.aj.size() + " nobles skills.");
        this.info("load " + this.ak.size() + " hero skills.");
        this.info("load " + this.al.size() + " clan leader skills.");
        this.info("load " + this.am.size() + " premium account skills.");
        this.info("load " + this.a(this.p) + " custom skills learns for " + this.p.size() + " races.");
    }

    @Override
    @Deprecated
    public int size() {
        return 0;
    }

    @Override
    public void clear() {
        this.n.clear();
        this.o.clear();
        this.ai.clear();
        this.aj.clear();
        this.ak.clear();
        this.al.clear();
        this.am.clear();
        this.p.clear();
    }

    private int a(TIntObjectHashMap<List<SkillLearn>> tIntObjectHashMap) {
        int n = 0;
        TIntObjectIterator tIntObjectIterator = tIntObjectHashMap.iterator();
        while (tIntObjectIterator.hasNext()) {
            tIntObjectIterator.advance();
            n += ((List)tIntObjectIterator.value()).size();
        }
        return n;
    }
}
