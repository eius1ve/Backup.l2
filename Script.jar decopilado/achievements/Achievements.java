/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.time.cron.NextTime
 *  l2.commons.time.cron.SchedulingPattern
 *  l2.gameserver.Config
 *  l2.gameserver.GameServer
 *  l2.gameserver.database.DatabaseFactory
 *  l2.gameserver.handler.voicecommands.IVoicedCommandHandler
 *  l2.gameserver.handler.voicecommands.VoicedCommandHandler
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.reward.RewardData
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.scripts.Scripts
 *  org.apache.commons.lang3.ArrayUtils
 *  org.dom4j.Document
 *  org.dom4j.Element
 *  org.dom4j.io.SAXReader
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package achievements;

import achievements.AchievementCondition;
import achievements.AchievementInfo;
import achievements.AchievementMetricListeners;
import achievements.AchievementMetricType;
import achievements.AchievementUI;
import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import l2.commons.time.cron.NextTime;
import l2.commons.time.cron.SchedulingPattern;
import l2.gameserver.Config;
import l2.gameserver.GameServer;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.handler.voicecommands.IVoicedCommandHandler;
import l2.gameserver.handler.voicecommands.VoicedCommandHandler;
import l2.gameserver.model.Player;
import l2.gameserver.model.reward.RewardData;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.scripts.Scripts;
import org.apache.commons.lang3.ArrayUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Achievements
implements ScriptFile {
    private static final Achievements a = new Achievements();
    private static final Logger b = LoggerFactory.getLogger(Achievements.class);
    private static final SAXReader a = new SAXReader(true);
    private static final AchVoicedCommandHandler a = new AchVoicedCommandHandler();
    private static final File a = new File(Config.DATAPACK_ROOT, "data/achievements.xml");
    private boolean i;
    private String[] a = ArrayUtils.EMPTY_STRING_ARRAY;
    private Map<AchievementMetricType, List<AchievementInfo>> b = Collections.emptyMap();
    private Map<AchievementInfo.AchievementInfoCategory, List<AchievementInfo>> c = Collections.emptyMap();
    private List<AchievementInfo.AchievementInfoCategory> h = Collections.emptyList();
    private List<AchievementInfo> i = Collections.emptyList();

    public static Achievements getInstance() {
        return a;
    }

    public boolean isEnabled() {
        return this.i;
    }

    private String[] a() {
        return this.a;
    }

    public List<AchievementInfo> getAchievementInfosByMetric(AchievementMetricType achievementMetricType) {
        return (List)this.b.get((Object)achievementMetricType);
    }

    public List<AchievementInfo> getAchievementInfosByCategory(AchievementInfo.AchievementInfoCategory achievementInfoCategory) {
        return this.c.get(achievementInfoCategory);
    }

    public AchievementInfo getAchievementInfoById(int n) {
        for (AchievementInfo achievementInfo : this.i) {
            if (achievementInfo.getId() != n) continue;
            return achievementInfo;
        }
        return null;
    }

    public List<AchievementInfo.AchievementInfoCategory> getCategories() {
        return this.h;
    }

    public void parse() {
        try {
            Document document = a.read(a);
            HashMap<AchievementMetricType, ArrayList<AchievementInfo>> hashMap = new HashMap<AchievementMetricType, ArrayList<AchievementInfo>>();
            HashMap<AchievementInfo.AchievementInfoCategory, List<AchievementInfo>> hashMap2 = new HashMap<AchievementInfo.AchievementInfoCategory, List<AchievementInfo>>();
            TreeMap<String, Object> treeMap = new TreeMap<String, Object>();
            LinkedList<AchievementInfo.AchievementInfoCategory> linkedList = new LinkedList<AchievementInfo.AchievementInfoCategory>();
            ArrayList<AchievementInfo> arrayList = new ArrayList<AchievementInfo>();
            Element element = document.getRootElement();
            this.i = Boolean.parseBoolean(element.attributeValue("enabled", "false"));
            String string = element.attributeValue("voice_commands");
            String[] stringArray = this.a = string != null && !string.trim().isEmpty() ? string.split("[^\\w\\d_]+") : ArrayUtils.EMPTY_STRING_ARRAY;
            if (this.i) {
                Iterator iterator = document.getRootElement().elementIterator();
                while (iterator.hasNext()) {
                    Object object;
                    Object object2;
                    String string2;
                    Element element2 = (Element)iterator.next();
                    if ("category".equalsIgnoreCase(element2.getName())) {
                        string2 = element2.attributeValue("name");
                        object2 = new AchievementInfo.AchievementInfoCategory(string2, element2.attributeValue("title_address"));
                        treeMap.put(string2, object2);
                        linkedList.add((AchievementInfo.AchievementInfoCategory)object2);
                        continue;
                    }
                    if (!"achievement".equalsIgnoreCase(element2.getName())) continue;
                    string2 = element2.attributeValue("name_address");
                    object2 = element2.attributeValue("category");
                    AchievementMetricType achievementMetricType = AchievementMetricType.valueOf(element2.attributeValue("type"));
                    String string3 = element2.attributeValue("expire_cron");
                    long l = Long.parseLong(element2.attributeValue("metric_stage_notify_delay", String.valueOf(0L)));
                    AchievementInfo achievementInfo = new AchievementInfo(Integer.parseInt(element2.attributeValue("id")), achievementMetricType, l, string2, (NextTime)(string3 != null ? new SchedulingPattern(string3) : null));
                    achievementInfo.setCategory((AchievementInfo.AchievementInfoCategory)treeMap.get(object2));
                    String string4 = element2.attributeValue("icon", "Icon.NOIMAGE");
                    achievementInfo.setIcon(string4);
                    int n = 0;
                    List<AchievementInfo> list = element2.elementIterator();
                    while (list.hasNext()) {
                        Object object3;
                        object = (Element)list.next();
                        if ("conds".equalsIgnoreCase(object.getName())) {
                            Iterator iterator2 = object.elementIterator();
                            while (iterator2.hasNext()) {
                                object3 = (Element)iterator2.next();
                                if (!"cond".equalsIgnoreCase(object3.getName())) continue;
                                AchievementCondition achievementCondition = AchievementCondition.makeCond(object3.attributeValue("name"), object3.attributeValue("value"));
                                if (achievementCondition == null) {
                                    throw new RuntimeException("Unknown condition \"" + object3.getName() + " of achievement " + string2 + "(" + achievementInfo.getId() + ")");
                                }
                                achievementInfo.addCond(achievementCondition);
                            }
                            continue;
                        }
                        if (!"stage".equalsIgnoreCase(object.getName())) continue;
                        int n2 = n = Integer.parseInt(object.attributeValue("level", String.valueOf(n + 1)));
                        object3 = object.attributeValue("desc_address");
                        int n3 = Integer.parseInt(object.attributeValue("value"));
                        boolean bl = Boolean.parseBoolean(object.attributeValue("reset_metric", String.valueOf(Boolean.TRUE)));
                        if (n2 - achievementInfo.getMaxLevel() > 1) {
                            b.warn("Inconsistent level \"" + n2 + "\" of achievement \"" + string2 + "\"(" + achievementInfo.getId() + ")");
                        }
                        AchievementInfo.AchievementInfoLevel achievementInfoLevel = achievementInfo.addLevel(n2, n3, (String)object3, bl);
                        Iterator iterator3 = object.elementIterator();
                        while (iterator3.hasNext()) {
                            Element element3;
                            Iterator iterator4;
                            Element element4 = (Element)iterator3.next();
                            if ("rewards".equals(element4.getName())) {
                                iterator4 = element4.elementIterator();
                                while (iterator4.hasNext()) {
                                    element3 = (Element)iterator4.next();
                                    if (!"reward".equalsIgnoreCase(element3.getName())) continue;
                                    int n4 = Integer.parseInt(element3.attributeValue("item_id"));
                                    long l2 = Long.parseLong(element3.attributeValue("min"));
                                    long l3 = Long.parseLong(element3.attributeValue("max"));
                                    int n5 = (int)(Double.parseDouble(element3.attributeValue("chance")) * 10000.0);
                                    RewardData rewardData = new RewardData(n4);
                                    rewardData.setChance((double)n5);
                                    rewardData.setMinDrop(l2);
                                    rewardData.setMaxDrop(l3);
                                    achievementInfoLevel.addRewardData(rewardData);
                                }
                                continue;
                            }
                            if (!"conds".equalsIgnoreCase(element4.getName())) continue;
                            iterator4 = element4.elementIterator();
                            while (iterator4.hasNext()) {
                                element3 = (Element)iterator4.next();
                                if (!"cond".equalsIgnoreCase(element3.getName())) continue;
                                AchievementCondition achievementCondition = AchievementCondition.makeCond(element3.attributeValue("name"), element3.attributeValue("value"));
                                if (achievementCondition == null) {
                                    throw new RuntimeException("Unknown condition \"" + element3.getName() + " of achievement " + string2 + "(" + achievementInfo.getId() + ")");
                                }
                                achievementInfoLevel.addCond(achievementCondition);
                            }
                        }
                    }
                    list = (List)hashMap.get((Object)achievementInfo.getMetricType());
                    if (list == null) {
                        list = new ArrayList<AchievementInfo>();
                        hashMap.put(achievementInfo.getMetricType(), (ArrayList<AchievementInfo>)list);
                    }
                    list.add(achievementInfo);
                    object = (List)hashMap2.get(achievementInfo.getCategory());
                    if (object == null) {
                        object = new ArrayList();
                        hashMap2.put(achievementInfo.getCategory(), (List<AchievementInfo>)object);
                    }
                    object.add(achievementInfo);
                    arrayList.add(achievementInfo);
                }
            }
            this.h = linkedList;
            this.b = hashMap;
            this.c = hashMap2;
            this.i = arrayList;
            b.info("Achievements: Loaded " + this.i.size() + " achievement(s) for " + this.h.size() + " category(ies).");
        }
        catch (Exception exception) {
            b.warn("Can't parse achievements", (Throwable)exception);
        }
    }

    private static void a() {
        try (Connection connection = DatabaseFactory.getInstance().getConnection();){
            GameServer.getInstance().getDbmsStructureSynchronizer(connection).synchronize(new String[]{"ex_achievements"});
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public void onLoad() {
        Achievements.getInstance().parse();
        if (Achievements.getInstance().isEnabled()) {
            Achievements.a();
            AchievementMetricListeners.getInstance().init();
            if (a.getVoicedCommandList().length > 0) {
                VoicedCommandHandler.getInstance().registerVoicedCommandHandler((IVoicedCommandHandler)a);
            }
        }
    }

    public void onReload() {
        this.onShutdown();
        this.onLoad();
    }

    public void onShutdown() {
        if (Achievements.getInstance().isEnabled()) {
            AchievementMetricListeners.getInstance().done();
        }
    }

    private static class AchVoicedCommandHandler
    implements IVoicedCommandHandler {
        private AchVoicedCommandHandler() {
        }

        public boolean useVoicedCommand(String string, Player player, String string2) {
            if (!Achievements.getInstance().isEnabled()) {
                return false;
            }
            for (String string3 : Achievements.getInstance().a()) {
                if (string3.isEmpty() || !string3.equalsIgnoreCase(string)) continue;
                Scripts.getInstance().callScripts(player, AchievementUI.class.getName(), "achievements");
                return true;
            }
            return false;
        }

        public String[] getVoicedCommandList() {
            if (Achievements.getInstance().isEnabled()) {
                return Achievements.getInstance().a();
            }
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
    }
}
