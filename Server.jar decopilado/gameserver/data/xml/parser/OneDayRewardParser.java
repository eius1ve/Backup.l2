/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.tuple.Pair
 *  org.dom4j.Element
 */
package l2.gameserver.data.xml.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import l2.commons.data.xml.AbstractFileParser;
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.OneDayRewardHolder;
import l2.gameserver.data.xml.parser.StatProcessor;
import l2.gameserver.model.entity.oneDayReward.OneDayDistributionType;
import l2.gameserver.model.entity.oneDayReward.OneDayReward;
import l2.gameserver.model.entity.oneDayReward.OneDayRewardRequirement;
import l2.gameserver.model.entity.oneDayReward.requirement.BattleInCastleSiegeRequirement;
import l2.gameserver.model.entity.oneDayReward.requirement.BattleInOlympiadRequirement;
import l2.gameserver.model.entity.oneDayReward.requirement.CompleteQuestRequirement;
import l2.gameserver.model.entity.oneDayReward.requirement.EnchantItemRequirement;
import l2.gameserver.model.entity.oneDayReward.requirement.FishingRequirement;
import l2.gameserver.model.entity.oneDayReward.requirement.JoinClanRequirement;
import l2.gameserver.model.entity.oneDayReward.requirement.KillMobRequirement;
import l2.gameserver.model.entity.oneDayReward.requirement.KillRaidRequirement;
import l2.gameserver.model.entity.oneDayReward.requirement.LogInRequirement;
import l2.gameserver.model.entity.oneDayReward.requirement.ObtainLevelRequirement;
import l2.gameserver.model.entity.oneDayReward.requirement.WinInOlympiadRequirement;
import l2.gameserver.stats.conditions.Condition;
import org.apache.commons.lang3.tuple.Pair;
import org.dom4j.Element;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class OneDayRewardParser
extends AbstractFileParser<OneDayRewardHolder> {
    private static final OneDayRewardParser a = new OneDayRewardParser();
    private final StatProcessor a = new StatProcessor(){

        @Override
        protected String getSourceName() {
            return OneDayRewardParser.this.getCurrentFileName();
        }
    };

    public static OneDayRewardParser getInstance() {
        return a;
    }

    private OneDayRewardParser() {
        super(OneDayRewardHolder.getInstance());
    }

    @Override
    public File getXMLFile() {
        return new File(Config.DATAPACK_ROOT, "data/OneDayReward.xml");
    }

    @Override
    public String getDTDFileName() {
        return null;
    }

    @Override
    protected void readData(Element element) throws Exception {
        for (Element element2 : element.elements("one_day_reward")) {
            Element element32;
            Element element4;
            int n = Integer.parseInt(element2.elementText("id"));
            OneDayRewardRequirement oneDayRewardRequirement = null;
            Element element5 = element2.element("requirement");
            OneDayReward.ResetTime resetTime = OneDayReward.ResetTime.valueOf(element2.elementText("reset_time"));
            OneDayDistributionType oneDayDistributionType = OneDayDistributionType.fromString(element2.elementText("distribution_type"));
            if (element5 != null) {
                element4 = (Element)element5.elementIterator().next();
                switch (element4.getName()) {
                    case "obtain_level": {
                        oneDayRewardRequirement = new ObtainLevelRequirement(Integer.parseInt(element4.getText()));
                        break;
                    }
                    case "kill_mob": {
                        oneDayRewardRequirement = new KillMobRequirement(Integer.parseInt(element4.getText()));
                        break;
                    }
                    case "kill_raid": {
                        oneDayRewardRequirement = new KillRaidRequirement(Integer.parseInt(element4.getText()));
                        break;
                    }
                    case "enchant_item": {
                        oneDayRewardRequirement = new EnchantItemRequirement(Integer.parseInt(element4.getText()));
                        break;
                    }
                    case "battle_in_castle_siege": {
                        oneDayRewardRequirement = new BattleInCastleSiegeRequirement();
                        break;
                    }
                    case "fishing": {
                        oneDayRewardRequirement = new FishingRequirement(Integer.parseInt(element4.getText()));
                        break;
                    }
                    case "login": {
                        oneDayRewardRequirement = new LogInRequirement();
                        break;
                    }
                    case "join_clan": {
                        oneDayRewardRequirement = new JoinClanRequirement();
                        break;
                    }
                    case "battle_in_olympiad": {
                        oneDayRewardRequirement = new BattleInOlympiadRequirement(Integer.parseInt(element4.getText()));
                        break;
                    }
                    case "win_in_olympiad": {
                        oneDayRewardRequirement = new WinInOlympiadRequirement(Integer.parseInt(element4.getText()));
                        break;
                    }
                    case "complete_quest": {
                        oneDayRewardRequirement = new CompleteQuestRequirement(Integer.parseInt(element4.getText()));
                        break;
                    }
                    default: {
                        this.error("Unknown requirement: " + element4.getName());
                    }
                }
            }
            element4 = new ArrayList();
            String string = element2.element("reward_items");
            if (string != null) {
                for (Element element32 : string.elements("reward_item")) {
                    int n2 = Integer.parseInt(element32.attributeValue("id"));
                    long l = Long.parseLong(element32.attributeValue("count"));
                    element4.add(Pair.of((Object)n2, (Object)l));
                }
            }
            Condition condition = null;
            element32 = element2.element("cond");
            if (element32 != null) {
                condition = this.a.parseFirstCond(element32);
            }
            HashSet<Integer> hashSet = new HashSet<Integer>();
            Element element6 = element2.element("classIds");
            if (element6 != null) {
                for (Element element7 : element6.elements()) {
                    hashSet.add(Integer.parseInt(element7.getText()));
                }
            }
            OneDayReward oneDayReward = new OneDayReward(n, hashSet, oneDayRewardRequirement, resetTime, (List<Pair<Integer, Long>>)element4, condition, oneDayDistributionType);
            ((OneDayRewardHolder)this.getHolder()).addOneDayReward(oneDayReward);
        }
    }
}
