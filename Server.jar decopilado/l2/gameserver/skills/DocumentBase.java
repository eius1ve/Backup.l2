/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.TIntHashSet
 *  org.apache.commons.lang3.StringUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.skills;

import gnu.trove.TIntHashSet;
import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.StringTokenizer;
import javax.xml.parsers.DocumentBuilderFactory;
import l2.gameserver.model.Skill;
import l2.gameserver.skills.AbnormalEffect;
import l2.gameserver.skills.EffectType;
import l2.gameserver.skills.effects.EffectTemplate;
import l2.gameserver.stats.StatTemplate;
import l2.gameserver.stats.Stats;
import l2.gameserver.stats.conditions.Condition;
import l2.gameserver.stats.conditions.ConditionClanPlayerMinClanLevel;
import l2.gameserver.stats.conditions.ConditionClanPlayerMinPledgeRank;
import l2.gameserver.stats.conditions.ConditionFirstEffectSuccess;
import l2.gameserver.stats.conditions.ConditionGameTime;
import l2.gameserver.stats.conditions.ConditionHasSkill;
import l2.gameserver.stats.conditions.ConditionIsPremium;
import l2.gameserver.stats.conditions.ConditionLogicAnd;
import l2.gameserver.stats.conditions.ConditionLogicNot;
import l2.gameserver.stats.conditions.ConditionLogicOr;
import l2.gameserver.stats.conditions.ConditionPlayerAgathion;
import l2.gameserver.stats.conditions.ConditionPlayerChargesMax;
import l2.gameserver.stats.conditions.ConditionPlayerChargesMin;
import l2.gameserver.stats.conditions.ConditionPlayerClanId;
import l2.gameserver.stats.conditions.ConditionPlayerClanLevel;
import l2.gameserver.stats.conditions.ConditionPlayerClassId;
import l2.gameserver.stats.conditions.ConditionPlayerClassIsMage;
import l2.gameserver.stats.conditions.ConditionPlayerCubic;
import l2.gameserver.stats.conditions.ConditionPlayerForbiddenClassId;
import l2.gameserver.stats.conditions.ConditionPlayerGender;
import l2.gameserver.stats.conditions.ConditionPlayerHasBuff;
import l2.gameserver.stats.conditions.ConditionPlayerHasBuffId;
import l2.gameserver.stats.conditions.ConditionPlayerInCombat;
import l2.gameserver.stats.conditions.ConditionPlayerInTeam;
import l2.gameserver.stats.conditions.ConditionPlayerInstanceZone;
import l2.gameserver.stats.conditions.ConditionPlayerIsClanLeader;
import l2.gameserver.stats.conditions.ConditionPlayerIsHero;
import l2.gameserver.stats.conditions.ConditionPlayerIsInAcademy;
import l2.gameserver.stats.conditions.ConditionPlayerIsNoble;
import l2.gameserver.stats.conditions.ConditionPlayerMaxLevel;
import l2.gameserver.stats.conditions.ConditionPlayerMaxPK;
import l2.gameserver.stats.conditions.ConditionPlayerMinLevel;
import l2.gameserver.stats.conditions.ConditionPlayerMinMaxDamage;
import l2.gameserver.stats.conditions.ConditionPlayerOlympiad;
import l2.gameserver.stats.conditions.ConditionPlayerOnPvPEvent;
import l2.gameserver.stats.conditions.ConditionPlayerPercentCp;
import l2.gameserver.stats.conditions.ConditionPlayerPercentHp;
import l2.gameserver.stats.conditions.ConditionPlayerPercentMp;
import l2.gameserver.stats.conditions.ConditionPlayerPrivateStore;
import l2.gameserver.stats.conditions.ConditionPlayerRace;
import l2.gameserver.stats.conditions.ConditionPlayerRiding;
import l2.gameserver.stats.conditions.ConditionPlayerSkillMinSeed;
import l2.gameserver.stats.conditions.ConditionPlayerState;
import l2.gameserver.stats.conditions.ConditionPlayerSummonSiegeGolem;
import l2.gameserver.stats.conditions.ConditionSlotItemId;
import l2.gameserver.stats.conditions.ConditionTargetAggro;
import l2.gameserver.stats.conditions.ConditionTargetBoss;
import l2.gameserver.stats.conditions.ConditionTargetCastleDoor;
import l2.gameserver.stats.conditions.ConditionTargetClan;
import l2.gameserver.stats.conditions.ConditionTargetDirection;
import l2.gameserver.stats.conditions.ConditionTargetForbiddenClassId;
import l2.gameserver.stats.conditions.ConditionTargetHasBuff;
import l2.gameserver.stats.conditions.ConditionTargetHasBuffId;
import l2.gameserver.stats.conditions.ConditionTargetHasForbiddenSkill;
import l2.gameserver.stats.conditions.ConditionTargetInTheSameAlly;
import l2.gameserver.stats.conditions.ConditionTargetInTheSameClan;
import l2.gameserver.stats.conditions.ConditionTargetInTheSameParty;
import l2.gameserver.stats.conditions.ConditionTargetMob;
import l2.gameserver.stats.conditions.ConditionTargetMobId;
import l2.gameserver.stats.conditions.ConditionTargetNpc;
import l2.gameserver.stats.conditions.ConditionTargetNpcClass;
import l2.gameserver.stats.conditions.ConditionTargetPercentCp;
import l2.gameserver.stats.conditions.ConditionTargetPercentHp;
import l2.gameserver.stats.conditions.ConditionTargetPercentMp;
import l2.gameserver.stats.conditions.ConditionTargetPlayable;
import l2.gameserver.stats.conditions.ConditionTargetPlayer;
import l2.gameserver.stats.conditions.ConditionTargetPlayerNotMe;
import l2.gameserver.stats.conditions.ConditionTargetPlayerRace;
import l2.gameserver.stats.conditions.ConditionTargetRace;
import l2.gameserver.stats.conditions.ConditionTargetSummon;
import l2.gameserver.stats.conditions.ConditionUsingArmor;
import l2.gameserver.stats.conditions.ConditionUsingBlowSkill;
import l2.gameserver.stats.conditions.ConditionUsingItemType;
import l2.gameserver.stats.conditions.ConditionUsingSkill;
import l2.gameserver.stats.conditions.ConditionZoneName;
import l2.gameserver.stats.conditions.ConditionZoneType;
import l2.gameserver.stats.funcs.EFunction;
import l2.gameserver.stats.funcs.FuncTemplate;
import l2.gameserver.stats.triggers.TriggerInfo;
import l2.gameserver.stats.triggers.TriggerType;
import l2.gameserver.templates.StatsSet;
import l2.gameserver.templates.item.ArmorTemplate;
import l2.gameserver.templates.item.WeaponTemplate;
import l2.gameserver.utils.PositionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

@Deprecated
abstract class DocumentBase {
    private static final Logger dj = LoggerFactory.getLogger(DocumentBase.class);
    private File f;

    DocumentBase(File file) {
        this.f = file;
    }

    Document parse() {
        Document document;
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setValidating(false);
            documentBuilderFactory.setIgnoringComments(true);
            document = documentBuilderFactory.newDocumentBuilder().parse(this.f);
        }
        catch (Exception exception) {
            dj.error("Error loading file " + this.f, (Throwable)exception);
            return null;
        }
        try {
            this.parseDocument(document);
        }
        catch (Exception exception) {
            dj.error("Error in file " + this.f, (Throwable)exception);
            return null;
        }
        return document;
    }

    protected abstract void parseDocument(Document var1);

    protected abstract Object getTableValue(String var1);

    protected abstract Object getTableValue(String var1, int var2);

    protected void parseTemplate(Node node, StatTemplate statTemplate) {
        if ((node = node.getFirstChild()) == null) {
            return;
        }
        while (node != null) {
            if (node.getNodeType() != 3) {
                String string = node.getNodeName();
                if (EFunction.VALUES_BY_LOWER_NAME.containsKey(string.toLowerCase())) {
                    this.attachFunc(node, statTemplate, EFunction.VALUES_BY_LOWER_NAME.get(string.toLowerCase()));
                } else if ("effect".equalsIgnoreCase(string)) {
                    if (statTemplate instanceof EffectTemplate) {
                        throw new RuntimeException("Nested effects");
                    }
                    this.attachEffect(node, statTemplate);
                } else if (statTemplate instanceof EffectTemplate) {
                    if ("def".equalsIgnoreCase(string)) {
                        var4_4 = (EffectTemplate)statTemplate;
                        StatsSet statsSet = ((EffectTemplate)var4_4).getParam();
                        Skill skill = (Skill)statsSet.getObject("object");
                        this.parseBeanSet(node, statsSet, skill.getLevel());
                    } else {
                        var4_4 = this.parseCondition(node);
                        if (var4_4 != null) {
                            ((EffectTemplate)statTemplate).attachCond((Condition)var4_4);
                        }
                    }
                } else {
                    throw new RuntimeException("Unknown template " + string);
                }
            }
            node = node.getNextSibling();
        }
    }

    protected void parseTrigger(Node node, StatTemplate statTemplate) {
        for (node = node.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (!"trigger".equalsIgnoreCase(node.getNodeName())) continue;
            NamedNodeMap namedNodeMap = node.getAttributes();
            int n = this.parseNumber(namedNodeMap.getNamedItem("id").getNodeValue()).intValue();
            int n2 = this.parseNumber(namedNodeMap.getNamedItem("level").getNodeValue()).intValue();
            TriggerType triggerType = TriggerType.valueOf(namedNodeMap.getNamedItem("type").getNodeValue());
            double d = this.parseNumber(namedNodeMap.getNamedItem("chance").getNodeValue()).doubleValue();
            TriggerInfo triggerInfo = new TriggerInfo(n, n2, triggerType, d);
            statTemplate.addTrigger(triggerInfo);
            for (Node node2 = node.getFirstChild(); node2 != null; node2 = node2.getNextSibling()) {
                Condition condition = this.parseCondition(node.getFirstChild());
                if (condition == null) continue;
                triggerInfo.addCondition(condition);
            }
        }
    }

    protected void attachFunc(Node node, StatTemplate statTemplate, String string) {
        Stats stats = Stats.valueOfXml(node.getAttributes().getNamedItem("stat").getNodeValue());
        String string2 = node.getAttributes().getNamedItem("order").getNodeValue();
        int n = this.parseNumber(string2).intValue();
        Condition condition = this.parseCondition(node.getFirstChild());
        double d = 0.0;
        if (node.getAttributes().getNamedItem("val") != null) {
            d = this.parseNumber(node.getAttributes().getNamedItem("val").getNodeValue()).doubleValue();
        }
        statTemplate.attachFunc(new FuncTemplate(condition, string, stats, n, d));
    }

    protected void attachFunc(Node node, StatTemplate statTemplate, EFunction eFunction) {
        Stats stats = Stats.valueOfXml(node.getAttributes().getNamedItem("stat").getNodeValue());
        String string = node.getAttributes().getNamedItem("order").getNodeValue();
        int n = this.parseNumber(string).intValue();
        Condition condition = this.parseCondition(node.getFirstChild());
        double d = 0.0;
        if (node.getAttributes().getNamedItem("val") != null) {
            d = this.parseNumber(node.getAttributes().getNamedItem("val").getNodeValue()).doubleValue();
        }
        statTemplate.attachFunc(new FuncTemplate(condition, eFunction, stats, n, d));
    }

    protected void attachEffect(Node node, Object object) {
        Object object2;
        Object object3;
        NamedNodeMap namedNodeMap = node.getAttributes();
        StatsSet statsSet = new StatsSet();
        statsSet.set("name", namedNodeMap.getNamedItem("name").getNodeValue());
        statsSet.set("object", object);
        if (namedNodeMap.getNamedItem("count") != null) {
            statsSet.set("count", this.parseNumber(namedNodeMap.getNamedItem("count").getNodeValue()).intValue());
        }
        if (namedNodeMap.getNamedItem("time") != null) {
            statsSet.set("time", this.parseNumber(namedNodeMap.getNamedItem("time").getNodeValue()).intValue());
        }
        statsSet.set("value", namedNodeMap.getNamedItem("val") != null ? this.parseNumber(namedNodeMap.getNamedItem("val").getNodeValue()).doubleValue() : 0.0);
        statsSet.set("abnormal", Collections.emptySet());
        if (namedNodeMap.getNamedItem("abnormal") != null) {
            object3 = new LinkedHashSet<AbnormalEffect>();
            object2 = StringUtils.split((String)namedNodeMap.getNamedItem("abnormal").getNodeValue(), (String)",");
            int n = ((String[])object2).length;
            for (int i = 0; i < n; ++i) {
                String string = object2[i];
                object3.add(AbnormalEffect.getByName(string));
            }
            statsSet.set("abnormal", (Collection<?>)object3);
        }
        if (namedNodeMap.getNamedItem("stackType") != null) {
            statsSet.set("stackType", namedNodeMap.getNamedItem("stackType").getNodeValue());
        }
        if (namedNodeMap.getNamedItem("stackType2") != null) {
            statsSet.set("stackType2", namedNodeMap.getNamedItem("stackType2").getNodeValue());
        }
        if (namedNodeMap.getNamedItem("stackOrder") != null) {
            statsSet.set("stackOrder", this.parseNumber(namedNodeMap.getNamedItem("stackOrder").getNodeValue()).intValue());
        }
        if (namedNodeMap.getNamedItem("applyOnCaster") != null) {
            statsSet.set("applyOnCaster", Boolean.valueOf(namedNodeMap.getNamedItem("applyOnCaster").getNodeValue()));
        }
        if (namedNodeMap.getNamedItem("applyOnSummon") != null) {
            statsSet.set("applyOnSummon", Boolean.valueOf(namedNodeMap.getNamedItem("applyOnSummon").getNodeValue()));
        }
        if (namedNodeMap.getNamedItem("displayId") != null) {
            statsSet.set("displayId", this.parseNumber(namedNodeMap.getNamedItem("displayId").getNodeValue()).intValue());
        }
        if (namedNodeMap.getNamedItem("displayLevel") != null) {
            statsSet.set("displayLevel", this.parseNumber(namedNodeMap.getNamedItem("displayLevel").getNodeValue()).intValue());
        }
        if (namedNodeMap.getNamedItem("chance") != null) {
            statsSet.set("chance", this.parseNumber(namedNodeMap.getNamedItem("chance").getNodeValue()).intValue());
        }
        if (namedNodeMap.getNamedItem("cancelOnAction") != null) {
            statsSet.set("cancelOnAction", Boolean.valueOf(namedNodeMap.getNamedItem("cancelOnAction").getNodeValue()));
        }
        if (namedNodeMap.getNamedItem("cancelOnItemSwitch") != null) {
            statsSet.set("cancelOnItemSwitch", namedNodeMap.getNamedItem("cancelOnItemSwitch").getNodeValue());
        }
        if (namedNodeMap.getNamedItem("isOffensive") != null) {
            statsSet.set("isOffensive", Boolean.valueOf(namedNodeMap.getNamedItem("isOffensive").getNodeValue()));
        }
        if (namedNodeMap.getNamedItem("isReflectable") != null) {
            statsSet.set("isReflectable", Boolean.valueOf(namedNodeMap.getNamedItem("isReflectable").getNodeValue()));
        }
        object3 = new EffectTemplate(statsSet);
        this.parseTemplate(node, (StatTemplate)object3);
        for (object2 = node.getFirstChild(); object2 != null; object2 = object2.getNextSibling()) {
            if (!"triggers".equalsIgnoreCase(object2.getNodeName())) continue;
            this.parseTrigger((Node)object2, (StatTemplate)object3);
        }
        if (object instanceof Skill) {
            ((Skill)object).attach((EffectTemplate)object3);
        }
    }

    protected Condition parseCondition(Node node) {
        while (node != null && node.getNodeType() != 1) {
            node = node.getNextSibling();
        }
        if (node == null) {
            return null;
        }
        if ("and".equalsIgnoreCase(node.getNodeName())) {
            return this.parseLogicAnd(node);
        }
        if ("or".equalsIgnoreCase(node.getNodeName())) {
            return this.parseLogicOr(node);
        }
        if ("not".equalsIgnoreCase(node.getNodeName())) {
            return this.parseLogicNot(node);
        }
        if ("player".equalsIgnoreCase(node.getNodeName())) {
            return this.parsePlayerCondition(node);
        }
        if ("target".equalsIgnoreCase(node.getNodeName())) {
            return this.parseTargetCondition(node);
        }
        if ("has".equalsIgnoreCase(node.getNodeName())) {
            return this.parseHasCondition(node);
        }
        if ("using".equalsIgnoreCase(node.getNodeName())) {
            return this.parseUsingCondition(node);
        }
        if ("game".equalsIgnoreCase(node.getNodeName())) {
            return this.parseGameCondition(node);
        }
        if ("zone".equalsIgnoreCase(node.getNodeName())) {
            return this.parseZoneCondition(node);
        }
        return null;
    }

    protected Condition parseLogicAnd(Node node) {
        ConditionLogicAnd conditionLogicAnd = new ConditionLogicAnd();
        for (node = node.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (node.getNodeType() != 1) continue;
            conditionLogicAnd.add(this.parseCondition(node));
        }
        if (conditionLogicAnd._conditions == null || conditionLogicAnd._conditions.length == 0) {
            dj.error("Empty <and> condition in " + this.f);
        }
        return conditionLogicAnd;
    }

    protected Condition parseLogicOr(Node node) {
        ConditionLogicOr conditionLogicOr = new ConditionLogicOr();
        for (node = node.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (node.getNodeType() != 1) continue;
            conditionLogicOr.add(this.parseCondition(node));
        }
        if (conditionLogicOr._conditions == null || conditionLogicOr._conditions.length == 0) {
            dj.error("Empty <or> condition in " + this.f);
        }
        return conditionLogicOr;
    }

    protected Condition parseLogicNot(Node node) {
        for (node = node.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (node.getNodeType() != 1) continue;
            return new ConditionLogicNot(this.parseCondition(node));
        }
        dj.error("Empty <not> condition in " + this.f);
        return null;
    }

    protected Condition parsePlayerCondition(Node node) {
        Condition condition = null;
        NamedNodeMap namedNodeMap = node.getAttributes();
        for (int i = 0; i < namedNodeMap.getLength(); ++i) {
            int n;
            int n2;
            Node node2 = namedNodeMap.item(i);
            String string = node2.getNodeName();
            if ("race".equalsIgnoreCase(string)) {
                condition = this.joinAnd(condition, new ConditionPlayerRace(node2.getNodeValue()));
                continue;
            }
            if ("minLevel".equalsIgnoreCase(string)) {
                int n3 = this.parseNumber(node2.getNodeValue()).intValue();
                condition = this.joinAnd(condition, new ConditionPlayerMinLevel(n3));
                continue;
            }
            if ("summon_siege_golem".equalsIgnoreCase(string)) {
                condition = this.joinAnd(condition, new ConditionPlayerSummonSiegeGolem());
                continue;
            }
            if ("maxLevel".equalsIgnoreCase(string)) {
                int n4 = this.parseNumber(node2.getNodeValue()).intValue();
                condition = this.joinAnd(condition, new ConditionPlayerMaxLevel(n4));
                continue;
            }
            if ("maxPK".equalsIgnoreCase(string)) {
                int n5 = this.parseNumber(node2.getNodeValue()).intValue();
                condition = this.joinAnd(condition, new ConditionPlayerMaxPK(n5));
                continue;
            }
            if ("resting".equalsIgnoreCase(string)) {
                boolean bl = Boolean.valueOf(node2.getNodeValue());
                condition = this.joinAnd(condition, new ConditionPlayerState(ConditionPlayerState.CheckPlayerState.RESTING, bl));
                continue;
            }
            if ("moving".equalsIgnoreCase(string)) {
                boolean bl = Boolean.valueOf(node2.getNodeValue());
                condition = this.joinAnd(condition, new ConditionPlayerState(ConditionPlayerState.CheckPlayerState.MOVING, bl));
                continue;
            }
            if ("running".equalsIgnoreCase(string)) {
                boolean bl = Boolean.valueOf(node2.getNodeValue());
                condition = this.joinAnd(condition, new ConditionPlayerState(ConditionPlayerState.CheckPlayerState.RUNNING, bl));
                continue;
            }
            if ("standing".equalsIgnoreCase(string)) {
                boolean bl = Boolean.valueOf(node2.getNodeValue());
                condition = this.joinAnd(condition, new ConditionPlayerState(ConditionPlayerState.CheckPlayerState.STANDING, bl));
                continue;
            }
            if ("flying".equalsIgnoreCase(node2.getNodeName())) {
                boolean bl = Boolean.valueOf(node2.getNodeValue());
                condition = this.joinAnd(condition, new ConditionPlayerState(ConditionPlayerState.CheckPlayerState.FLYING, bl));
                continue;
            }
            if ("flyingTransform".equalsIgnoreCase(node2.getNodeName())) {
                boolean bl = Boolean.valueOf(node2.getNodeValue());
                condition = this.joinAnd(condition, new ConditionPlayerState(ConditionPlayerState.CheckPlayerState.FLYING_TRANSFORM, bl));
                continue;
            }
            if ("olympiad".equalsIgnoreCase(node2.getNodeName())) {
                boolean bl = Boolean.valueOf(node2.getNodeValue());
                condition = this.joinAnd(condition, new ConditionPlayerOlympiad(bl));
                continue;
            }
            if ("is_in_team".equalsIgnoreCase(node2.getNodeName())) {
                boolean bl = Boolean.valueOf(node2.getNodeValue());
                condition = this.joinAnd(condition, new ConditionPlayerInTeam(bl));
                continue;
            }
            if ("on_pvp_event".equalsIgnoreCase(node2.getNodeName())) {
                boolean bl = Boolean.valueOf(node2.getNodeValue());
                condition = this.joinAnd(condition, new ConditionPlayerOnPvPEvent(bl));
                continue;
            }
            if ("forbiddenClassIds".equalsIgnoreCase(string)) {
                condition = this.joinAnd(condition, new ConditionPlayerForbiddenClassId(string.split(";")));
                continue;
            }
            if ("is_hero".equalsIgnoreCase(string)) {
                boolean bl = Boolean.valueOf(node2.getNodeValue());
                condition = this.joinAnd(condition, new ConditionPlayerIsHero(bl));
                continue;
            }
            if ("is_noble".equalsIgnoreCase(string)) {
                boolean bl = Boolean.parseBoolean(node2.getNodeValue());
                condition = this.joinAnd(condition, new ConditionPlayerIsNoble(bl));
                continue;
            }
            if ("is_clan_leader".equalsIgnoreCase(string)) {
                boolean bl = Boolean.valueOf(node2.getNodeValue());
                condition = this.joinAnd(condition, new ConditionPlayerIsClanLeader(bl));
                continue;
            }
            if ("clan_level".equalsIgnoreCase(string)) {
                int n6 = this.parseNumber(node2.getNodeValue()).intValue();
                condition = this.joinAnd(condition, new ConditionPlayerClanLevel(n6));
                continue;
            }
            if ("is_in_academy".equalsIgnoreCase(string)) {
                boolean bl = Boolean.valueOf(node2.getNodeValue());
                condition = this.joinAnd(condition, new ConditionPlayerIsInAcademy(bl));
                continue;
            }
            if ("is_premium".equalsIgnoreCase(string)) {
                boolean bl = Boolean.valueOf(node2.getNodeValue());
                condition = this.joinAnd(condition, new ConditionIsPremium(bl));
                continue;
            }
            if ("is_in_combat".equalsIgnoreCase(string)) {
                boolean bl = Boolean.valueOf(node2.getNodeValue());
                condition = this.joinAnd(condition, new ConditionPlayerInCombat(bl));
                continue;
            }
            if ("class_is_mage".equalsIgnoreCase(string)) {
                boolean bl = Boolean.valueOf(node2.getNodeValue());
                condition = this.joinAnd(condition, new ConditionPlayerClassIsMage(bl));
                continue;
            }
            if ("min_pledge_level".equalsIgnoreCase(string)) {
                int n7 = this.parseNumber(node2.getNodeValue()).intValue();
                condition = this.joinAnd(condition, new ConditionClanPlayerMinClanLevel(n7));
                continue;
            }
            if ("clanId".equalsIgnoreCase(string)) {
                condition = this.joinAnd(condition, new ConditionPlayerClanId(node2.getNodeValue().split(",")));
                continue;
            }
            if ("min_pledge_rank".equalsIgnoreCase(string)) {
                condition = this.joinAnd(condition, new ConditionClanPlayerMinPledgeRank(node2.getNodeValue()));
                continue;
            }
            if ("percentHP".equalsIgnoreCase(string)) {
                int n8 = this.parseNumber(node2.getNodeValue()).intValue();
                condition = this.joinAnd(condition, new ConditionPlayerPercentHp(n8));
                continue;
            }
            if ("percentMP".equalsIgnoreCase(string)) {
                int n9 = this.parseNumber(node2.getNodeValue()).intValue();
                condition = this.joinAnd(condition, new ConditionPlayerPercentMp(n9));
                continue;
            }
            if ("percentCP".equalsIgnoreCase(string)) {
                int n10 = this.parseNumber(node2.getNodeValue()).intValue();
                condition = this.joinAnd(condition, new ConditionPlayerPercentCp(n10));
                continue;
            }
            if ("chargesMin".equalsIgnoreCase(string)) {
                int n11 = this.parseNumber(node2.getNodeValue()).intValue();
                condition = this.joinAnd(condition, new ConditionPlayerChargesMin(n11));
                continue;
            }
            if ("chargesMax".equalsIgnoreCase(string)) {
                int n12 = this.parseNumber(node2.getNodeValue()).intValue();
                condition = this.joinAnd(condition, new ConditionPlayerChargesMax(n12));
                continue;
            }
            if ("agathion".equalsIgnoreCase(string)) {
                int n13 = this.parseNumber(node2.getNodeValue()).intValue();
                condition = this.joinAnd(condition, new ConditionPlayerAgathion(n13));
                continue;
            }
            if ("cubic".equalsIgnoreCase(string)) {
                int n14 = this.parseNumber(node2.getNodeValue()).intValue();
                condition = this.joinAnd(condition, new ConditionPlayerCubic(n14));
                continue;
            }
            if ("instance_zone".equalsIgnoreCase(string)) {
                int n15 = this.parseNumber(node2.getNodeValue()).intValue();
                condition = this.joinAnd(condition, new ConditionPlayerInstanceZone(n15));
                continue;
            }
            if ("riding".equalsIgnoreCase(string)) {
                String string2 = node2.getNodeValue();
                if ("strider".equalsIgnoreCase(string2)) {
                    condition = this.joinAnd(condition, new ConditionPlayerRiding(ConditionPlayerRiding.CheckPlayerRiding.STRIDER));
                    continue;
                }
                if ("wyvern".equalsIgnoreCase(string2)) {
                    condition = this.joinAnd(condition, new ConditionPlayerRiding(ConditionPlayerRiding.CheckPlayerRiding.WYVERN));
                    continue;
                }
                if (!"none".equalsIgnoreCase(string2)) continue;
                condition = this.joinAnd(condition, new ConditionPlayerRiding(ConditionPlayerRiding.CheckPlayerRiding.NONE));
                continue;
            }
            if ("classId".equalsIgnoreCase(string)) {
                condition = this.joinAnd(condition, new ConditionPlayerClassId(node2.getNodeValue().split(",")));
                continue;
            }
            if ("privateStoreType".equalsIgnoreCase(string)) {
                condition = this.joinAnd(condition, new ConditionPlayerPrivateStore(node2.getNodeValue().trim()));
                continue;
            }
            if ("gender".equalsIgnoreCase(string)) {
                condition = this.joinAnd(condition, new ConditionPlayerGender(node2.getNodeValue()));
                continue;
            }
            if ("hasBuffId".equalsIgnoreCase(string)) {
                StringTokenizer stringTokenizer = new StringTokenizer(node2.getNodeValue(), ";");
                n2 = Integer.parseInt(stringTokenizer.nextToken().trim());
                n = -1;
                if (stringTokenizer.hasMoreTokens()) {
                    n = Integer.parseInt(stringTokenizer.nextToken().trim());
                }
                condition = this.joinAnd(condition, new ConditionPlayerHasBuffId(n2, n));
                continue;
            }
            if ("hasBuff".equalsIgnoreCase(string)) {
                StringTokenizer stringTokenizer = new StringTokenizer(node2.getNodeValue(), ";");
                EffectType effectType = Enum.valueOf(EffectType.class, stringTokenizer.nextToken().trim());
                n = -1;
                if (stringTokenizer.hasMoreTokens()) {
                    n = Integer.parseInt(stringTokenizer.nextToken().trim());
                }
                condition = this.joinAnd(condition, new ConditionPlayerHasBuff(effectType, n));
                continue;
            }
            if ("damage".equalsIgnoreCase(string)) {
                String[] stringArray = node2.getNodeValue().split(";");
                condition = this.joinAnd(condition, new ConditionPlayerMinMaxDamage(Double.parseDouble(stringArray[0]), Double.parseDouble(stringArray[1])));
                continue;
            }
            if (!"skillMinSeed".equalsIgnoreCase(string)) continue;
            StringTokenizer stringTokenizer = new StringTokenizer(node2.getNodeValue(), ";");
            n2 = Integer.parseInt(stringTokenizer.nextToken().trim());
            n = Integer.parseInt(stringTokenizer.nextToken().trim());
            condition = this.joinAnd(condition, new ConditionPlayerSkillMinSeed(n2, n));
        }
        if (condition == null) {
            dj.error("Unrecognized <player> condition in " + this.f);
        }
        return condition;
    }

    protected Condition parseTargetCondition(Node node) {
        Condition condition = null;
        NamedNodeMap namedNodeMap = node.getAttributes();
        for (int i = 0; i < namedNodeMap.getLength(); ++i) {
            Object object;
            Object object2;
            Node node2 = namedNodeMap.item(i);
            String string = node2.getNodeName();
            String string2 = node2.getNodeValue();
            if ("aggro".equalsIgnoreCase(string)) {
                condition = this.joinAnd(condition, new ConditionTargetAggro(Boolean.valueOf(string2)));
                continue;
            }
            if ("pvp".equalsIgnoreCase(string)) {
                condition = this.joinAnd(condition, new ConditionTargetPlayable(Boolean.valueOf(string2)));
                continue;
            }
            if ("player".equalsIgnoreCase(string)) {
                condition = this.joinAnd(condition, new ConditionTargetPlayer(Boolean.valueOf(string2)));
                continue;
            }
            if ("exclude_caster".equalsIgnoreCase(string)) {
                condition = this.joinAnd(condition, new ConditionTargetPlayerNotMe(Boolean.valueOf(string2)));
                continue;
            }
            if ("summon".equalsIgnoreCase(string)) {
                condition = this.joinAnd(condition, new ConditionTargetSummon(Boolean.valueOf(string2)));
                continue;
            }
            if ("mob".equalsIgnoreCase(string)) {
                condition = this.joinAnd(condition, new ConditionTargetMob(Boolean.valueOf(string2)));
                continue;
            }
            if ("npc".equalsIgnoreCase(string)) {
                condition = this.joinAnd(condition, new ConditionTargetNpc(Boolean.valueOf(string2)));
                continue;
            }
            if ("boss".equalsIgnoreCase(string)) {
                condition = this.joinAnd(condition, new ConditionTargetBoss(Boolean.valueOf(string2)));
                continue;
            }
            if ("targetInTheSameParty".equalsIgnoreCase(string)) {
                condition = this.joinAnd(condition, new ConditionTargetInTheSameParty(Boolean.valueOf(string2)));
                continue;
            }
            if ("targetInTheSameClan".equalsIgnoreCase(string)) {
                condition = this.joinAnd(condition, new ConditionTargetInTheSameClan(Boolean.valueOf(string2)));
                continue;
            }
            if ("targetInTheSameAlly".equalsIgnoreCase(string)) {
                condition = this.joinAnd(condition, new ConditionTargetInTheSameAlly(Boolean.valueOf(string2)));
                continue;
            }
            if ("mobId".equalsIgnoreCase(string)) {
                object2 = string2.split(";");
                object = new TIntHashSet();
                for (String string3 : object2) {
                    object.add(Integer.parseInt(string3));
                }
                condition = this.joinAnd(condition, new ConditionTargetMobId((TIntHashSet)object));
                continue;
            }
            if ("race".equalsIgnoreCase(string)) {
                condition = this.joinAnd(condition, new ConditionTargetRace(string2));
                continue;
            }
            if ("npc_class".equalsIgnoreCase(string)) {
                condition = this.joinAnd(condition, new ConditionTargetNpcClass(string2));
                continue;
            }
            if ("playerRace".equalsIgnoreCase(string)) {
                condition = this.joinAnd(condition, new ConditionTargetPlayerRace(string2));
                continue;
            }
            if ("forbiddenClassIds".equalsIgnoreCase(string)) {
                condition = this.joinAnd(condition, new ConditionTargetForbiddenClassId(string2.split(";")));
                continue;
            }
            if ("playerSameClan".equalsIgnoreCase(string)) {
                condition = this.joinAnd(condition, new ConditionTargetClan(string2));
                continue;
            }
            if ("castledoor".equalsIgnoreCase(string)) {
                condition = this.joinAnd(condition, new ConditionTargetCastleDoor(Boolean.valueOf(string2)));
                continue;
            }
            if ("direction".equalsIgnoreCase(string)) {
                condition = this.joinAnd(condition, new ConditionTargetDirection(PositionUtils.TargetDirection.valueOf(string2.toUpperCase())));
                continue;
            }
            if ("percentHP".equalsIgnoreCase(string)) {
                condition = this.joinAnd(condition, new ConditionTargetPercentHp(this.parseNumber(node2.getNodeValue()).intValue()));
                continue;
            }
            if ("percentMP".equalsIgnoreCase(string)) {
                condition = this.joinAnd(condition, new ConditionTargetPercentMp(this.parseNumber(node2.getNodeValue()).intValue()));
                continue;
            }
            if ("percentCP".equalsIgnoreCase(string)) {
                condition = this.joinAnd(condition, new ConditionTargetPercentCp(this.parseNumber(node2.getNodeValue()).intValue()));
                continue;
            }
            if ("hasBuffId".equalsIgnoreCase(string)) {
                object2 = new StringTokenizer(string2, ";");
                int n = Integer.parseInt(((StringTokenizer)object2).nextToken().trim());
                int n2 = -1;
                if (((StringTokenizer)object2).hasMoreTokens()) {
                    n2 = Integer.parseInt(((StringTokenizer)object2).nextToken().trim());
                }
                condition = this.joinAnd(condition, new ConditionTargetHasBuffId(n, n2));
                continue;
            }
            if ("hasBuff".equalsIgnoreCase(string)) {
                object2 = new StringTokenizer(string2, ";");
                object = Enum.valueOf(EffectType.class, ((StringTokenizer)object2).nextToken().trim());
                int n = -1;
                if (((StringTokenizer)object2).hasMoreTokens()) {
                    n = Integer.parseInt(((StringTokenizer)object2).nextToken().trim());
                }
                condition = this.joinAnd(condition, new ConditionTargetHasBuff((EffectType)((Object)object), n));
                continue;
            }
            if (!"hasForbiddenSkill".equalsIgnoreCase(string)) continue;
            condition = this.joinAnd(condition, new ConditionTargetHasForbiddenSkill(this.parseNumber(node2.getNodeValue()).intValue()));
        }
        if (condition == null) {
            dj.error("Unrecognized <target> condition in " + this.f);
        }
        return condition;
    }

    protected Condition parseUsingCondition(Node node) {
        Condition condition = null;
        NamedNodeMap namedNodeMap = node.getAttributes();
        for (int i = 0; i < namedNodeMap.getLength(); ++i) {
            Node node2 = namedNodeMap.item(i);
            String string = node2.getNodeName();
            String string2 = node2.getNodeValue();
            if ("kind".equalsIgnoreCase(string) || "weapon".equalsIgnoreCase(string)) {
                long l = 0L;
                StringTokenizer stringTokenizer = new StringTokenizer(string2, ",");
                block1: while (stringTokenizer.hasMoreTokens()) {
                    String string3 = stringTokenizer.nextToken().trim();
                    for (WeaponTemplate.WeaponType weaponType : WeaponTemplate.WeaponType.VALUES) {
                        if (!weaponType.toString().equalsIgnoreCase(string3)) continue;
                        l |= weaponType.mask();
                        continue block1;
                    }
                    for (Enum enum_ : ArmorTemplate.ArmorType.VALUES) {
                        if (!((ArmorTemplate.ArmorType)enum_).toString().equalsIgnoreCase(string3)) continue;
                        l |= ((ArmorTemplate.ArmorType)enum_).mask();
                        continue block1;
                    }
                    dj.error("Invalid item kind: \"" + string3 + "\" in " + this.f);
                }
                if (l == 0L) continue;
                condition = this.joinAnd(condition, new ConditionUsingItemType(l));
                continue;
            }
            if ("armor".equalsIgnoreCase(string)) {
                ArmorTemplate.ArmorType armorType = ArmorTemplate.ArmorType.valueOf(string2.toUpperCase());
                condition = this.joinAnd(condition, new ConditionUsingArmor(armorType));
                continue;
            }
            if ("skill".equalsIgnoreCase(string)) {
                condition = this.joinAnd(condition, new ConditionUsingSkill(Integer.parseInt(string2)));
                continue;
            }
            if ("blowskill".equalsIgnoreCase(string)) {
                condition = this.joinAnd(condition, new ConditionUsingBlowSkill(Boolean.parseBoolean(string2)));
                continue;
            }
            if (!"slotitem".equalsIgnoreCase(string)) continue;
            StringTokenizer stringTokenizer = new StringTokenizer(string2, ";");
            int n = Integer.parseInt(stringTokenizer.nextToken().trim());
            int n2 = Integer.parseInt(stringTokenizer.nextToken().trim());
            int n3 = 0;
            if (stringTokenizer.hasMoreTokens()) {
                n3 = Integer.parseInt(stringTokenizer.nextToken().trim());
            }
            condition = this.joinAnd(condition, new ConditionSlotItemId(n2, n, n3));
        }
        if (condition == null) {
            dj.error("Unrecognized <using> condition in " + this.f);
        }
        return condition;
    }

    protected Condition parseHasCondition(Node node) {
        Condition condition = null;
        NamedNodeMap namedNodeMap = node.getAttributes();
        for (int i = 0; i < namedNodeMap.getLength(); ++i) {
            Node node2 = namedNodeMap.item(i);
            String string = node2.getNodeName();
            String string2 = node2.getNodeValue();
            if ("skill".equalsIgnoreCase(string)) {
                StringTokenizer stringTokenizer = new StringTokenizer(string2, ";");
                Integer n = this.parseNumber(stringTokenizer.nextToken().trim()).intValue();
                short s = this.parseNumber(stringTokenizer.nextToken().trim()).shortValue();
                condition = this.joinAnd(condition, new ConditionHasSkill(n, s));
                continue;
            }
            if (!"success".equalsIgnoreCase(string)) continue;
            condition = this.joinAnd(condition, new ConditionFirstEffectSuccess(Boolean.valueOf(string2)));
        }
        if (condition == null) {
            dj.error("Unrecognized <has> condition in " + this.f);
        }
        return condition;
    }

    protected Condition parseGameCondition(Node node) {
        Condition condition = null;
        NamedNodeMap namedNodeMap = node.getAttributes();
        for (int i = 0; i < namedNodeMap.getLength(); ++i) {
            Node node2 = namedNodeMap.item(i);
            if (!"night".equalsIgnoreCase(node2.getNodeName())) continue;
            boolean bl = Boolean.valueOf(node2.getNodeValue());
            condition = this.joinAnd(condition, new ConditionGameTime(ConditionGameTime.CheckGameTime.NIGHT, bl));
        }
        if (condition == null) {
            dj.error("Unrecognized <game> condition in " + this.f);
        }
        return condition;
    }

    protected Condition parseZoneCondition(Node node) {
        Condition condition = null;
        NamedNodeMap namedNodeMap = node.getAttributes();
        for (int i = 0; i < namedNodeMap.getLength(); ++i) {
            Node node2 = namedNodeMap.item(i);
            if ("type".equalsIgnoreCase(node2.getNodeName())) {
                condition = this.joinAnd(condition, new ConditionZoneType(node2.getNodeValue()));
                continue;
            }
            if (!"name".equalsIgnoreCase(node2.getNodeName())) continue;
            condition = this.joinAnd(condition, new ConditionZoneName(node2.getNodeValue()));
        }
        if (condition == null) {
            dj.error("Unrecognized <zone> condition in " + this.f);
        }
        return condition;
    }

    protected void parseBeanSet(Node node, StatsSet statsSet, int n) {
        try {
            char c;
            String string = node.getAttributes().getNamedItem("name").getNodeValue().trim();
            String string2 = node.getAttributes().getNamedItem("val").getNodeValue().trim();
            char c2 = c = string2.length() == 0 ? (char)' ' : (char)string2.charAt(0);
            if (string2.contains("#") && c != '#') {
                for (String string3 : string2.split("[;: ]+")) {
                    if (string3.charAt(0) != '#') continue;
                    string2 = string2.replace(string3, String.valueOf(this.getTableValue(string3, n)));
                }
            }
            if (c == '#') {
                Object object = this.getTableValue(string2, n);
                Number number = this.parseNumber(object.toString());
                statsSet.set(string, number == null ? object : String.valueOf(number));
            } else if (!(!Character.isDigit(c) && c != '-' || string2.contains(" ") || string2.contains(";"))) {
                statsSet.set(string, String.valueOf(this.parseNumber(string2)));
            } else {
                statsSet.set(string, string2);
            }
        }
        catch (Exception exception) {
            System.out.println(node.getAttributes().getNamedItem("name") + " " + statsSet.get("skill_id"));
            exception.printStackTrace();
        }
    }

    protected Number parseNumber(String string) {
        if (string.charAt(0) == '#') {
            string = this.getTableValue(string).toString();
        }
        try {
            if (string.equalsIgnoreCase("max")) {
                return Double.POSITIVE_INFINITY;
            }
            if (string.equalsIgnoreCase("min")) {
                return Double.NEGATIVE_INFINITY;
            }
            if (string.indexOf(46) == -1) {
                int n = 10;
                if (string.length() > 2 && string.substring(0, 2).equalsIgnoreCase("0x")) {
                    string = string.substring(2);
                    n = 16;
                }
                return Integer.valueOf(string, n);
            }
            return Double.valueOf(string);
        }
        catch (NumberFormatException numberFormatException) {
            return null;
        }
    }

    protected Condition joinAnd(Condition condition, Condition condition2) {
        if (condition == null) {
            return condition2;
        }
        if (condition instanceof ConditionLogicAnd) {
            ((ConditionLogicAnd)condition).add(condition2);
            return condition;
        }
        ConditionLogicAnd conditionLogicAnd = new ConditionLogicAnd();
        conditionLogicAnd.add(condition);
        conditionLogicAnd.add(condition2);
        return conditionLogicAnd;
    }
}
