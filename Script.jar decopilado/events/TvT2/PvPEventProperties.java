/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.configuration.ExProperties
 *  l2.gameserver.Config
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.templates.item.ItemTemplate
 *  org.apache.commons.lang3.ArrayUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.lang3.tuple.Pair
 */
package events.TvT2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import l2.commons.configuration.ExProperties;
import l2.gameserver.Config;
import l2.gameserver.scripts.Functions;
import l2.gameserver.templates.item.ItemTemplate;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class PvPEventProperties {
    public static final String CONFIG_FILE = "config/pvp_events.properties";
    private static final Random a = new Random();
    public static int[] PVP_EVENTS_RESTRICTED_SKILL_IDS;
    public static boolean PVP_EVENTS_RESTRICTED_CLAN_SKILLS;
    public static int[] PVP_EVENT_RESTRICT_CLASS_IDS;
    public static int PVP_EVENT_GIVE_HERO_STATUS;
    public static int PVP_EVENT_CHECK_MIN_KILL_COUNT_FOR_REWARD;
    public static boolean PVP_EVENT_RESTRICT_HWID;
    public static boolean PVP_EVENT_RESTRICT_IP;
    public static List<Pair<Integer, Integer>> PVP_EVENTS_WARRIOR_BUFF;
    public static List<Pair<Integer, Integer>> PVP_EVENTS_MAGE_BUFF;
    public static long PVP_EVENTS_BUFF_TIME;
    public static List<Pair<Integer, Integer>> PVP_EVENTS_MAGE_BUFF_ON_REVIVE;
    public static List<Pair<Integer, Integer>> PVP_EVENTS_WARRIOR_BUFF_ON_REVIVE;
    public static long PVP_EVENTS_BUFF_ON_REVIVE_TIME;
    public static int[] PVP_EVENTS_REGISTRATION_ITEM_ID;
    public static int[] PVP_EVENTS_REGISTRATION_ITEM_COUNT;
    public static int CTF_EVENT_BLUE_FLAG_NPC;
    public static int CTF_EVENT_RED_FLAG_NPC;
    public static int CTF_EVENT_BLUE_FLAG_ITEM;
    public static int CTF_FLAG_MIN_ATTACK_DISTANCE;
    public static int CTF_EVENT_RED_FLAG_ITEM;
    private static ExProperties a;

    private PvPEventProperties() {
        PvPEventProperties.loadPvPEventsProperties();
    }

    public static void loadPvPEventsProperties() {
        String[] stringArray;
        a = Config.load((String)CONFIG_FILE);
        PVP_EVENTS_RESTRICTED_SKILL_IDS = a.getProperty("PvPEventsRestrictedSkillIds", new int[0]);
        PVP_EVENTS_RESTRICTED_CLAN_SKILLS = a.getProperty("PvPEventsRestrictClanSkills", false);
        PVP_EVENTS_BUFF_TIME = a.getProperty("PvPEventBuffTime", 0L) * 1000L;
        PVP_EVENTS_BUFF_ON_REVIVE_TIME = a.getProperty("PvPEventBuffTimeOnRevive", 0L) * 1000L;
        PVP_EVENT_RESTRICT_CLASS_IDS = a.getProperty("PvPEventRestrictClassId", ArrayUtils.EMPTY_INT_ARRAY);
        PVP_EVENT_GIVE_HERO_STATUS = a.getProperty("PvPEventGiveHeroStatus", 0);
        PVP_EVENT_CHECK_MIN_KILL_COUNT_FOR_REWARD = a.getProperty("PvPEventCheckMinKillCountForReward", 0);
        PVP_EVENT_RESTRICT_HWID = a.getProperty("PvPEventRestrictHWID", false);
        PVP_EVENT_RESTRICT_IP = a.getProperty("PvPEventRestrictIP", false);
        PVP_EVENTS_REGISTRATION_ITEM_ID = a.getProperty("PvPEventCostRegistrationItemId", ArrayUtils.EMPTY_INT_ARRAY);
        PVP_EVENTS_REGISTRATION_ITEM_COUNT = a.getProperty("PvPEventCostRegistrationItemCount", ArrayUtils.EMPTY_INT_ARRAY);
        CTF_EVENT_BLUE_FLAG_NPC = a.getProperty("CTF_BlueFlagNpc", 19698);
        CTF_EVENT_RED_FLAG_NPC = a.getProperty("CTF_RedFlagNpc", 19696);
        CTF_EVENT_BLUE_FLAG_ITEM = a.getProperty("CTF_BlueFlagItem", 6718);
        CTF_EVENT_RED_FLAG_ITEM = a.getProperty("CTF_RedFlagItem", 6718);
        CTF_FLAG_MIN_ATTACK_DISTANCE = a.getProperty("CTF_FlagMinAttackDistance", 300);
        PVP_EVENTS_MAGE_BUFF = new ArrayList<Pair<Integer, Integer>>();
        for (String string : a.getProperty("PvPEventMageBuff", ArrayUtils.EMPTY_STRING_ARRAY)) {
            if (string.trim().isEmpty()) continue;
            stringArray = StringUtils.split((String)string, (String)"-:");
            PVP_EVENTS_MAGE_BUFF.add((Pair<Integer, Integer>)Pair.of((Object)Integer.parseInt(stringArray[0]), (Object)Integer.parseInt(stringArray[1])));
        }
        PVP_EVENTS_WARRIOR_BUFF = new ArrayList<Pair<Integer, Integer>>();
        for (String string : a.getProperty("PvPEventWarriorBuff", ArrayUtils.EMPTY_STRING_ARRAY)) {
            if (string.trim().isEmpty()) continue;
            stringArray = StringUtils.split((String)string, (String)"-:");
            PVP_EVENTS_WARRIOR_BUFF.add((Pair<Integer, Integer>)Pair.of((Object)Integer.parseInt(stringArray[0]), (Object)Integer.parseInt(stringArray[1])));
        }
        PVP_EVENTS_MAGE_BUFF_ON_REVIVE = new ArrayList<Pair<Integer, Integer>>();
        for (String string : a.getProperty("PvPEventMageBuffOnRevive", ArrayUtils.EMPTY_STRING_ARRAY)) {
            if (string.trim().isEmpty()) continue;
            stringArray = StringUtils.split((String)string, (String)"-:");
            PVP_EVENTS_MAGE_BUFF_ON_REVIVE.add((Pair<Integer, Integer>)Pair.of((Object)Integer.parseInt(stringArray[0]), (Object)Integer.parseInt(stringArray[1])));
        }
        PVP_EVENTS_WARRIOR_BUFF_ON_REVIVE = new ArrayList<Pair<Integer, Integer>>();
        for (String string : a.getProperty("PvPEventWarriorBuffOnRevive", ArrayUtils.EMPTY_STRING_ARRAY)) {
            if (string.trim().isEmpty()) continue;
            stringArray = StringUtils.split((String)string, (String)"-:");
            PVP_EVENTS_WARRIOR_BUFF_ON_REVIVE.add((Pair<Integer, Integer>)Pair.of((Object)Integer.parseInt(stringArray[0]), (Object)Integer.parseInt(stringArray[1])));
        }
    }

    public static boolean getBooleanProperty(String string, String string2, boolean bl) {
        return a.getProperty(string + "_" + string2, bl);
    }

    public static int getIntProperty(String string, String string2, int n) {
        return a.getProperty(string + "_" + string2, n);
    }

    public static List<Pair<ItemTemplate, Long>> getRewardItems(String string, String string2) {
        String string3 = a.getProperty(string + "_" + string2, "");
        return Functions.parseItemIdAmountList((String)string3);
    }

    public static int getRandomIntFromPropertyArray(String string, String string2, int[] nArray) {
        int[] nArray2 = PvPEventProperties.getIntArrayProperty(string, string2, nArray);
        return nArray2.length > 0 ? nArray2[a.nextInt(nArray2.length)] : -1;
    }

    public static int[] getIntArrayProperty(String string, String string2, int[] nArray) {
        String string3 = a.getProperty(string + "_" + string2, "");
        if (string3 == null || string3.isEmpty()) {
            return nArray;
        }
        return Arrays.stream(string3.split(";")).mapToInt(Integer::parseInt).toArray();
    }

    public static String getStringProperty(String string, String string2, String string3) {
        return a.getProperty(string + "_" + string2, string3);
    }
}
