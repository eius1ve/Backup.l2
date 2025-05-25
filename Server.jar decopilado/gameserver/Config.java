/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.TIntHash
 *  gnu.trove.TIntHashSet
 *  gnu.trove.TIntIntHashMap
 *  gnu.trove.TIntObjectHashMap
 *  org.apache.commons.lang3.ArrayUtils
 *  org.apache.commons.lang3.BooleanUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.lang3.math.NumberUtils
 *  org.apache.commons.lang3.reflect.FieldUtils
 *  org.apache.commons.lang3.tuple.Pair
 *  org.apache.commons.lang3.tuple.Triple
 *  org.dom4j.Document
 *  org.dom4j.Element
 *  org.dom4j.io.SAXReader
 *  org.napile.primitive.collections.IntCollection
 *  org.napile.primitive.lists.impl.CArrayIntList
 *  org.napile.primitive.sets.IntSet
 *  org.napile.primitive.sets.impl.HashIntSet
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver;

import gnu.trove.TIntHash;
import gnu.trove.TIntHashSet;
import gnu.trove.TIntIntHashMap;
import gnu.trove.TIntObjectHashMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.xml.parsers.DocumentBuilderFactory;
import l2.commons.configuration.ExProperties;
import l2.commons.net.nio.impl.SelectorConfig;
import l2.gameserver.data.xml.parser.ChatFilterParser;
import l2.gameserver.model.actor.instances.player.Bonus;
import l2.gameserver.model.base.Experience;
import l2.gameserver.model.base.PlayerAccess;
import l2.gameserver.model.quest.QuestRates;
import l2.gameserver.network.authcomm.ServerType;
import l2.gameserver.network.l2.components.ChatType;
import l2.gameserver.network.pfilter.PacketFilterParser;
import l2.gameserver.skills.AbnormalEffect;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.utils.Location;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.napile.primitive.collections.IntCollection;
import org.napile.primitive.lists.impl.CArrayIntList;
import org.napile.primitive.sets.IntSet;
import org.napile.primitive.sets.impl.HashIntSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

public class Config {
    private static final Logger am = LoggerFactory.getLogger(Config.class);
    public static final int NCPUS = Runtime.getRuntime().availableProcessors();
    public static final String OTHER_CONFIG_FILE = "config/other.properties";
    public static final String RESIDENCE_CONFIG_FILE = "config/residence.properties";
    public static final String SPOIL_CONFIG_FILE = "config/spoil.properties";
    public static final String CLAN_CONFIG_FILE = "config/clan.properties";
    public static final String ALT_SETTINGS_FILE = "config/altsettings.properties";
    public static final String BOSS_SETTINGS_FILE = "config/bosses.properties";
    public static final String FORMULAS_CONFIGURATION_FILE = "config/formulas.properties";
    public static final String EXPERIENCE_CONFIG_FILE = "config/experience.csv";
    public static final String PVP_CONFIG_FILE = "config/pvp.properties";
    public static final String TELNET_CONFIGURATION_FILE = "config/telnet.properties";
    public static final String CONFIGURATION_FILE = "config/server.properties";
    public static final String AI_CONFIG_FILE = "config/ai.properties";
    public static final String GEODATA_CONFIG_FILE = "config/geodata.properties";
    public static final String EVENTS_CONFIG_FILE = "config/events.properties";
    public static final String SERVICES_FILE = "config/services.properties";
    public static final String SERVICES_RATE_BONUS_XML_FILE = "config/services_rate_bonus.xml";
    public static final String OLYMPIAD = "config/olympiad.properties";
    public static final String QUEST_RATE_FILE = "config/quest_rates.properties";
    public static final String AUTO_FARM_FILE = "config/auto_farm.properties";
    public static final String CHATFILTERS_CONFIG_FILE = "config/chatfilters.xml";
    public static final String PACKET_FILTER_CONFIG_FILE = "config/packetfilter.xml";
    public static final String GM_PERSONAL_ACCESS_FILE = "config/GMAccess.xml";
    public static final String GM_ACCESS_FILES_DIR = "config/GMAccess.d/";
    public static int HTM_CACHE_MODE;
    public static int[] PORTS_GAME;
    public static String GAMESERVER_HOSTNAME;
    public static int HAPROXY_PORT_GAME;
    public static Set<String> VALID_IPS_LIST;
    public static boolean REJECT_INVALID_CONNECTIONS;
    public static String DATABASE_HOST;
    public static int DATABASE_PORT;
    public static String DATABASE_NAME;
    public static String DATABASE_USER;
    public static String DATABASE_PASS;
    public static int DATABASE_MAX_CONN;
    public static int DATABASE_TIMEOUT;
    public static String DATABASE_EX_PROPERTIES;
    public static boolean DATABASE_EX_STRUCTURE_MANAGER;
    public static String[] DATABASE_DUMP_TABLES;
    public static String DATABASE_DUMP_FILENAME_FORMAT;
    public static String DATABASE_DUMP_ZIP_OUT_FILENAME_FORMAT;
    public static boolean AUTOSAVE;
    public static boolean AUTOSAVE_ITEMS;
    public static boolean ALLOW_MULILOGIN;
    public static long USER_INFO_INTERVAL;
    public static boolean BROADCAST_STATS_INTERVAL;
    public static long BROADCAST_CHAR_INFO_INTERVAL;
    public static int EFFECT_TASK_MANAGER_COUNT;
    public static int MAXIMUM_ONLINE_USERS;
    public static boolean DONTLOADSPAWN;
    public static boolean DONTLOADQUEST;
    public static int[] IGNORE_QUESTS;
    public static int MAX_REFLECTIONS_COUNT;
    public static int SHIFT_BY;
    public static int SHIFT_BY_Z;
    public static int MAP_MIN_Z;
    public static int MAP_MAX_Z;
    public static int CHAT_MESSAGE_MAX_LEN;
    public static ChatType[] BAN_CHANNEL_LIST;
    public static boolean BANCHAT_ANNOUNCE;
    public static boolean BANCHAT_ANNOUNCE_FOR_ALL_WORLD;
    public static boolean BANCHAT_ANNOUNCE_NICK;
    public static boolean NOSPAM_FILTER_HWID;
    public static boolean NOSPAM_FILTER_IP;
    public static boolean SAVING_SPS;
    public static boolean MANAHEAL_SPS_BONUS;
    public static int ALT_ADD_RECIPES;
    public static int ALT_PARTY_DISTRIBUTION_RANGE;
    public static int ALT_PARTY_DISTRIBUTION_DIFF_LEVEL_LIMIT;
    public static double[] ALT_PARTY_BONUS;
    public static boolean ALT_PARTY_BONUS_FOR_QUESTS;
    public static double ALT_ABSORB_DAMAGE_MODIFIER;
    public static Map<Integer, Integer> ALT_PARTY_CLASS_LIMIT;
    public static double ALT_POLE_DAMAGE_MODIFIER;
    public static int ALT_REMOVE_SKILLS_ON_DELEVEL;
    public static int[] ALT_BASIC_ACTIONS;
    public static int[] ALT_TRANSFORMATION_ACTIONS;
    public static boolean ALT_TELEPORT_FROM_SEVEN_SING_MONSTER;
    public static boolean ALT_ENABLE_SEVEN_SING_TELEPORTER_PROTECTION;
    public static boolean ALT_MAMONS_CHECK_SEVEN_SING_STATUS;
    public static boolean ALT_CABAL_SEVEN_SING_SHOUT;
    public static boolean ALT_SEVEN_SING_STATIC_EVENT_PERIOD_SPAWN;
    public static boolean ALT_3_JOB_QUEST_IS_PARY;
    public static boolean EX_USE_TELEPORT_FLAG;
    public static int EX_USE_TELEPORT_BOOK_SCROLL;
    public static int EX_USE_TELEPORT_BOOL_SCROLL_SAVE_ITEM;
    public static int EX_MAX_TELEPORT_BOOKMARK_SIZE;
    public static long EX_TELEPORT_BOOK_SCROLL;
    public static long L2TOPRU_DELAY;
    public static String L2TOPRU_PREFIX;
    public static String L2TOPRU_WEB_VOTE_URL;
    public static String L2TOPRU_SMS_VOTE_URL;
    public static int L2TOPRU_WEB_REWARD_ITEMID;
    public static int L2TOPRU_WEB_REWARD_ITEMCOUNT;
    public static int L2TOPRU_SMS_REWARD_ITEMID;
    public static int L2TOPRU_SMS_REWARD_ITEMCOUNT;
    public static boolean L2TOPRU_SMS_REWARD_VOTE_MULTI;
    public static String MMO_TOP_CRON_TASK;
    public static String MMO_TOP_VOTES_LINK;
    public static String MMO_TOP_REWARD;
    public static int MMO_TOP_VOTE_WINDOW_DAYS;
    public static int MMO_TOP_VOTE_REWARD_HOURS;
    public static boolean L2TOPZONE_ENABLED;
    public static long L2TOPZONE_VOTE_TIME_TO_LIVE;
    public static String L2TOPZONE_API_KEY;
    public static int[] L2TOPZONE_REWARD_ITEM_ID;
    public static int[] L2TOPZONE_REWARD_ITEM_COUNT;
    public static int[] L2TOPZONE_REWARD_CHANCE;
    public static boolean L2HOPZONE_ENABLED;
    public static long L2HOPZONE_VOTE_TIME_TO_LIVE;
    public static String L2HOPZONE_API_KEY;
    public static int[] L2HOPZONE_REWARD_ITEM_ID;
    public static int[] L2HOPZONE_REWARD_ITEM_COUNT;
    public static int[] L2HOPZONE_REWARD_CHANCE;
    public static long L2HOPZONE_REUSE_TIME;
    public static String L2HOPZONE_ADD_COMMAND;
    public static boolean L2JBRAZIL_ENABLED;
    public static long L2JBRAZIL_VOTE_TIME_TO_LIVE;
    public static String L2JBRAZIL_API_KEY;
    public static int[] L2JBRAZIL_REWARD_ITEM_ID;
    public static int[] L2JBRAZIL_REWARD_ITEM_COUNT;
    public static int[] L2JBRAZIL_REWARD_CHANCE;
    public static Calendar CASTLE_VALIDATION_DATE;
    public static boolean ALT_PCBANG_POINTS_ENABLED;
    public static double ALT_PCBANG_POINTS_BONUS_DOUBLE_CHANCE;
    public static double ALT_PCBANG_POINTS_BONUS_DOUBLE_RATE;
    public static double ALT_PCBANG_BONUS_WITH_PREMIUM_RATE;
    public static int ALT_PCBANG_POINTS_BONUS;
    public static int ALT_PCBANG_POINTS_DELAY;
    public static int ALT_PCBANG_POINTS_MIN_LVL;
    public static boolean ALT_PCBANG_CHECK_HWID;
    public static boolean ALT_PCBANG_POINTS_COMMAND;
    public static boolean ALT_DEBUG_ENABLED;
    public static boolean ALT_DEBUG_PVP_ENABLED;
    public static boolean ALT_DEBUG_PVP_DUEL_ONLY;
    public static boolean ALT_DEBUG_PVE_ENABLED;
    public static int ALT_LIMIT_HWID_WINDOW;
    public static int ALT_LIMIT_HWID_PREMIUM_WINDOW;
    public static long ALT_LIMIT_HWID_REPORT_TIMER;
    public static int ALT_LIMIT_IP_WINDOW;
    public static int ALT_LIMIT_IP_PREMIUM_WINDOW;
    public static long ALT_LIMIT_IP_REPORT_TIMER;
    public static boolean ALT_MULTISELL_DEBUG;
    public static List<String> ALT_SIMPLE_BEGINNINGS;
    public static List<String> ALT_SIMPLE_BBS_BEGINNINGS;
    public static boolean ALT_CHECK_CERTIFICATION_ITEMS;
    public static int SCHEDULED_THREAD_POOL_SIZE;
    public static int EXECUTOR_THREAD_POOL_SIZE;
    public static boolean ENABLE_RUNNABLE_STATS;
    public static SelectorConfig SELECTOR_CONFIG;
    public static boolean AUTO_LOOT;
    public static boolean AUTO_LOOT_HERBS;
    public static boolean AUTO_LOOT_INDIVIDUAL;
    public static boolean AUTO_LOOT_ONLY_FOR_PREMIUM;
    public static int[] AUTO_LOOT_ONLY_WITH_IDS;
    public static boolean AUTO_LOOT_FROM_RAIDS;
    public static boolean AUTO_LOOT_ADENA;
    public static int[] AUTO_LOOT_EXCLUDE_ITEM_IDS;
    public static int[] AUTO_LOOT_MONEY_ITEM_IDS;
    public static TIntHashSet DISABLE_AUTO_LOOT_FOR_MONSTER_IDS;
    public static boolean XP_FREEZ_ONLY_FOR_PREMIUM;
    public static boolean AUTO_LOOT_PK;
    public static String CNAME_TEMPLATE;
    public static String CNAME_FORBIDDEN_PATTERN;
    public static String[] CNAME_FORBIDDEN_NAMES;
    public static String CUSTOM_CNAME_TEMPLATE;
    public static int CNAME_MAXLEN;
    public static String CLAN_NAME_TEMPLATE;
    public static String CLAN_TITLE_TEMPLATE;
    public static String ALLY_NAME_TEMPLATE;
    public static boolean GLOBAL_SHOUT;
    public static int GLOBAL_SHOUT_MIN_LEVEL;
    public static int GLOBAL_SHOUT_MIN_PVP_COUNT;
    public static boolean GLOBAL_TRADE_CHAT;
    public static int GLOBAL_TRADE_CHAT_MIN_LEVEL;
    public static int GLOBAL_TRADE_MIN_PVP_COUNT;
    public static int CHAT_RANGE;
    public static int SHOUT_OFFSET;
    public static boolean ENABLE_WORLD_CHAT;
    public static int WORLD_CHAT_MESSAGE_COUNT;
    public static int WORLD_CHAT_MIN_LEVEL;
    public static boolean WORLD_CHAT_FOR_PREMIUM_ONLY;
    public static long WORLD_CHAT_INTERVAL;
    public static boolean ENABLE_CLAN_ENTRY;
    public static int ENABLE_CLAN_ENTRY_PLAYER_LOCK_TIME;
    public static int ENABLE_CLAN_ENTRY_CLAN_LOCK_TIME;
    public static boolean ENABLE_PRIME_SHOP;
    public static boolean ENABLE_PRIME_SHOP_REWARD_COIN_TAB;
    public static int PRIME_SHOP_VIP_POINT_ITEM_ID;
    public static int PRIME_SHOP_SILVER_ITEM_ID;
    public static int PRIME_SHOP_GOLD_ITEM_ID;
    public static boolean PRIME_SHOP_VIP_SYSTEM_ENABLED;
    public static int PRIME_SHOP_VIP_SYSTEM_MAX_LEVEL;
    public static boolean PRIME_SHOP_PURCHASING_ADD_VIP_POINTS;
    public static double PRIME_SHOP_PURCHASING_ADD_VIP_COEFFICIENT;
    public static boolean EVERYBODY_HAS_ADMIN_RIGHTS;
    public static boolean RAID_TELE_TO_HOME_FROM_PVP_ZONES;
    public static boolean RAID_TELE_TO_HOME_FROM_TOWN_ZONES;
    public static double ALT_RAID_RESPAWN_MULTIPLIER;
    public static double ALT_MOBS_RESPAWN_MULTIPLIER;
    public static long FWA_LIMITUNTILSLEEPANTHARAS;
    public static long FWA_LIMITMAXUNTILSLEEPANTHARAS;
    public static long VALAKAS_CLEAR_ZONE_IF_ALL_DIE;
    public static long FWA_FIXTIMEINTERVALOFANTHARAS;
    public static long FWA_RANDOMINTERVALOFANTHARAS;
    public static String FWA_FIXTIMEPATTERNOFANTHARAS;
    public static boolean FWA_ALLOW_ANTHARAS_MINION;
    public static long FWA_APPTIMEOFANTHARAS;
    public static long FWV_LIMITUNTILSLEEPVALAKAS;
    public static String FWA_FIXTIMEPATTERNOFVALAKAS;
    public static long ANTHARAS_CLEAR_ZONE_IF_ALL_DIE;
    public static long SAILREN_CLEAR_ZONE_IF_ALL_DIE;
    public static long BAIUM_CLEAR_ZONE_IF_ALL_DIE;
    public static long FWV_APPTIMEOFVALAKAS;
    public static long FWA_LIMITMAXUNTILSLEEPVALAKAS;
    public static long FWV_FIXINTERVALOFVALAKAS;
    public static long FWV_RANDOMINTERVALOFVALAKAS;
    public static long FWB_LIMITUNTILSLEEPBAIUM;
    public static long FWB_FIXINTERVALOFBAIUM;
    public static long FWB_RANDOMINTERVALOFBAIUM;
    public static long FWA_LIMITMAXUNTILSLEEPBAIUM;
    public static String FWA_FIXTIMEPATTERNOFBAIUM;
    public static long FWS_ACTIVITYTIMEOFMOBS;
    public static long FWS_FIXINTERVALOFSAILRENSPAWN;
    public static long FWS_RANDOMINTERVALOFSAILRENSPAWN;
    public static String FWA_FIXTIMEPATTERNOFSAILREN;
    public static long FWS_INTERVALOFNEXTMONSTER;
    public static long FWF_FIXINTERVALOFFRINTEZZA;
    public static long FWF_RANDOMINTERVALOFFRINTEZZA;
    public static String FWA_FIXTIMEPATTERNOFFRINTEZZA;
    public static int FRINTEZZA_TOMB_TIMEOUT;
    public static int FRINTEZZA_MIN_PARTY_IN_CC;
    public static int FRINTEZZA_MAX_PARTY_IN_CC;
    public static int FRINTEZZA_MIN_DISTANCE_FOR_ENTRANCE;
    public static boolean FRINTEZZA_LOCK_FOR_DEAD_PLAYERS;
    public static boolean ENABLE_ON_TIME_DOOR_OPEN;
    public static int ON_TIME_OPEN_DOOR_ID;
    public static String ON_TIME_OPEN_PATTERN;
    public static boolean ZAKEN_USE_TELEPORT;
    public static boolean ORFEN_USE_TELEPORT;
    public static boolean CAN_ATTACK_FROM_ANOTHER_ZONE_TO_EPIC;
    public static boolean ALLOW_BAIUM_RAID;
    public static boolean VALAKAS_ANNOUNCE_PREPARING_SPAWN;
    public static boolean ANTHARAS_ANNOUNCE_PREPARING_SPAWN;
    public static boolean BAIUM_CHECK_QUEST_ON_AWAKE;
    public static boolean SHOW_BOSS_SCENES;
    public static boolean ALT_ALLOW_DROP_AUGMENTED;
    public static boolean ALT_ALLOW_TRADE_AUGMENTED;
    public static boolean ALT_ALLOW_TRADE_APPAREANCED;
    public static boolean ALT_ALLOW_DROP_APPAREANCED;
    public static boolean ALT_GAME_UNREGISTER_RECIPE;
    public static int SS_ANNOUNCE_PERIOD;
    public static boolean PETITIONING_ALLOWED;
    public static boolean CAN_PETITION_TO_OFFLINE_GM;
    public static int MAX_PETITIONS_PER_PLAYER;
    public static int MAX_PETITIONS_PENDING;
    public static boolean ALT_GAME_SHOW_DROPLIST;
    public static boolean ALT_GAME_SHOW_DROPLIST_TREASURE_CHEST;
    public static boolean ALT_FULL_NPC_STATS_PAGE;
    public static boolean ALLOW_NPC_SHIFTCLICK;
    public static int ALT_NPC_SHIFTCLICK_ITEM_COUNT;
    public static boolean ALT_GAME_SHOW_DROPLIST_WEAK_MOBS;
    public static boolean ALT_ALLOW_SELL_COMMON;
    public static boolean ALT_ALLOW_SHADOW_WEAPONS;
    public static boolean ALT_ALLOW_REMOTE_SELL_ITEMS_TO_SHOP;
    public static boolean ALT_ALLOW_REMOTE_USE_CARGO_BOX;
    public static int[] ALT_DISABLED_MULTISELL;
    public static int[] ALT_SHOP_PRICE_LIMITS;
    public static int[] ALT_SHOP_UNALLOWED_ITEMS;
    public static long ALT_SHOP_REFUND_SELL_DIVISOR;
    public static double ALT_PRIVATE_STORE_BUY_COST_DIVISOR;
    public static double ALT_SHOP_BUY_LIST_MULTIPLIER;
    public static double ALT_MULTISELL_ADENA_MULTIPLAYER;
    public static int[] ALT_ALLOWED_PET_POTIONS;
    public static boolean REMOVE_FORCE_CHARGE_ON_DEAD;
    public static int[] ALT_RAID_BOSS_SPAWN_ANNOUNCE_IDS;
    public static int ALT_RAID_BOSS_SPAWN_ANNOUNCE_DELAY;
    public static int ALT_RAID_BOSS_SPAWN_ANNOUNCE_RANDOM_DELAY;
    public static TIntHash ALT_RAID_DEATH_ANNOUNCE_IDS;
    public static boolean ALT_SEND_BOSS_SPAWN_INFO;
    public static boolean ALT_RAID_BOSS_SPAWN_AND_TELEPORT;
    public static long[] EXPERIENCE;
    public static double SKILLS_CHANCE_MOD;
    public static double SKILLS_CHANCE_MIN;
    public static double SKILLS_CHANCE_POW;
    public static double SKILLS_CHANCE_CAP;
    public static boolean ALT_SAVE_UNSAVEABLE;
    public static int ALT_SAVE_EFFECTS_REMAINING_TIME;
    public static boolean ALT_SUBLASS_SKILL_TRANSFER;
    public static boolean ALT_ALLOW_CUSTOM_SKILL_LEARN;
    public static boolean ALT_SHOW_REUSE_MSG;
    public static boolean ALT_DELETE_SA_BUFFS;
    public static boolean ALT_ALLOW_DISPEL_AT_OLY;
    public static boolean BUFF_STICK_FOR_ALL;
    public static int ALT_PASSIVE_NOBLESS_ID;
    public static int SKILLS_CAST_TIME_MIN;
    public static int SKILLS_DISPEL_MOD_MAX;
    public static int SKILLS_DISPEL_MOD_MIN;
    public static int SKILLS_BANE_MOD_MIN;
    public static int SKILLS_BANE_MOD_MAX;
    public static boolean CHAR_TITLE;
    public static String ADD_CHAR_TITLE;
    public static String DISCONNECTED_PLAYER_TITLE;
    public static int DISCONNECTED_PLAYER_TITLE_COLOR;
    public static boolean ALT_SOCIAL_ACTION_REUSE;
    public static boolean ALT_DISABLE_SPELLBOOKS;
    public static boolean ALT_WEAK_SKILL_LEARN;
    public static boolean ALT_GAME_DELEVEL;
    public static double[] LOOSE_EXP_ON_DEATH;
    public static boolean ALT_ALLOW_CUSTOM_HERO;
    public static boolean ALT_ALLOW_CUSTOM_HERO_SKILLS;
    public static boolean ALT_ALLOW_CUSTOM_HERO_WEAPON_REWARD;
    public static long CUSTOM_HERO_STATUS_TIME;
    public static boolean ALT_HAIR_TO_ACC_SLOT;
    public static int ALT_NEW_CHARACTER_LEVEL;
    public static boolean ALT_GAME_SUBCLASS_WITHOUT_QUESTS;
    public static boolean ALT_GAME_SUBCLASS_NOT_CHECK_QUEST_234;
    public static int[] ALT_FIRST_JOB_QUEST_EXTRA_REWARD;
    public static int[] ALT_SECOND_JOB_QUEST_EXTRA_REWARD;
    public static int[] ALT_THIRD_JOB_QUEST_EXTRA_REWARD;
    public static int ALT_SAGA_SUPER_CLASS_DROP_RATE;
    public static boolean ALT_ALLIANCE_KETRA_RAID_KILL_CHECK;
    public static boolean ALT_ALLIANCE_KETRA_MOBS_KILL_CHECK;
    public static boolean ALT_ALLIANCE_VARKA_RAID_KILL_CHECK;
    public static boolean ALT_ALLIANCE_VARKA_MOBS_KILL_CHECK;
    public static boolean ALT_ALLOW_SUBCLASS_FOR_CUSTOM_ITEM;
    public static int[] ALT_SUBCLASS_FOR_CUSTOM_ITEM_ID;
    public static int[] ALT_SUBCLASS_FOR_CUSTOM_ITEM_COUNT;
    public static boolean ALTSUBCLASS_LIST_ALL;
    public static boolean ALT_ALLOW_ALLCLASS_SKILLENCHANT;
    public static boolean ALT_ALLOW_ALLCLASS_SKILL_LEARN;
    public static boolean ALTSUBCLASS_ALLOW_OVER_AND_WARSMITH_TO_ALL;
    public static boolean ALTSUBCLASS_ALLOW_FOR_ELF_TO_DARK_ELF;
    public static int ALT_GAME_LEVEL_TO_GET_SUBCLASS;
    public static int ALT_LEVEL_AFTER_GET_SUBCLASS;
    public static int ALT_MAX_LEVEL;
    public static int ALT_MAX_SUB_LEVEL;
    public static boolean ALT_ALLOW_HERO_SKILLS_ON_SUB_CLASS;
    public static int ALT_GAME_BASE_SUB;
    public static boolean ALT_NO_LASTHIT;
    public static boolean ALT_DROP_LASTHIT;
    public static boolean ALT_KAMALOKA_NIGHTMARES_PREMIUM_ONLY;
    public static boolean ALT_KAMALOKA_NIGHTMARE_REENTER;
    public static boolean ALT_KAMALOKA_ABYSS_REENTER;
    public static boolean ALT_KAMALOKA_LAB_REENTER;
    public static boolean ALT_PET_HEAL_BATTLE_ONLY;
    public static boolean ALT_SIMPLE_SIGNS;
    public static int[] ALT_SIMPLE_SIGNS_NPC_ID;
    public static boolean ALT_TELE_TO_CATACOMBS;
    public static int[] ALT_TELE_TO_CATACOMBS_NPC_ID;
    public static boolean ALT_BS_CRYSTALLIZE;
    public static int[] ALT_BS_CRYSTALLIZE_NPC_ID;
    public static boolean ALT_ALLOW_TATTOO;
    public static int[] ALT_ALLOW_TATTOO_NPC_ID;
    public static boolean ALT_BUFF_SLOT_SEPARATE;
    public static int ALT_BUFF_LIMIT;
    public static int ALT_DEBUFF_LIMIT;
    public static int ALT_MUSIC_LIMIT;
    public static int ALT_TRIGGER_LIMIT;
    public static List<Pair<Integer, Integer>> OTHER_MAGE_BUFF_ON_CHAR_CREATE;
    public static List<Pair<Integer, Integer>> OTHER_WARRIOR_BUFF_ON_CHAR_CREATE;
    public static long OTHER_BUFF_ON_CHAR_CREATE_TIME_MODIFIER;
    public static long ALT_NPC_BUFFER_EFFECT_TIME;
    public static double ALT_NPC_BUFFER_PREMIUM_EFFECT_MUL;
    public static long ALT_NPC_BUFFER_REUSE_DELAY;
    public static boolean ALT_NPC_BUFFER_PREMIUM_HTML_PREFIX;
    public static int[] ALT_NPC_BUFFER_PREMIUM_ITEM_IDS;
    public static boolean ALT_BUFFER_FOR_CURSED_WEAPON;
    public static boolean ALT_ALLOW_DELAY_NPC_TALK;
    public static int ALT_MAX_PARTY_SIZE;
    public static int[] ALT_BUFFER_CANCEL_PRICE;
    public static int[] ALT_BUFFER_CLEANS_PRICE;
    public static int[] ALT_BUFFER_HP_CP_MP_PRICE;
    public static int[] ALT_BUFFER_CLEANS_AND_RESTORE_PRICE;
    public static boolean PRIVATE_BUY_MATCH_ENCHANT;
    public static int MULTISELL_SIZE;
    public static boolean SERVICES_CHANGE_NICK_ENABLED;
    public static int SERVICES_CHANGE_NICK_PRICE;
    public static int SERVICES_CHANGE_NICK_ITEM;
    public static boolean QUEST_SELL_ENABLE;
    public static String QUEST_SELL_QUEST_PRICES;
    public static int APPEARANCE_APPLY_ITEM_ID;
    public static int APPEARANCE_SUPPORT_ITEM_ID;
    public static long APPEARANCE_SUPPORT_ITEM_CNT;
    public static int APPEARANCE_CANCEL_ITEM_ID;
    public static long APPEARANCE_CANCEL_PRICE;
    public static int[] APPEARANCE_PROHIBITED_BE_CHANGED;
    public static int[] APPEARANCE_PROHIBITED_CHANGE;
    public static boolean SERVICES_CHANGE_CLAN_NAME_ENABLED;
    public static int SERVICES_CHANGE_CLAN_NAME_PRICE;
    public static int SERVICES_CHANGE_CLAN_NAME_ITEM;
    public static boolean SERVICES_CHANGE_PET_NAME_ENABLED;
    public static int[] SERVICES_CHANGE_PET_NAME_NPC_ID;
    public static int SERVICES_CHANGE_PET_NAME_PRICE;
    public static int SERVICES_CHANGE_PET_NAME_ITEM;
    public static boolean SERVICES_EXCHANGE_BABY_PET_ENABLED;
    public static int SERVICES_EXCHANGE_BABY_PET_PRICE;
    public static int SERVICES_EXCHANGE_BABY_PET_ITEM;
    public static boolean SERVICES_CHANGE_SEX_ENABLED;
    public static int SERVICES_CHANGE_SEX_PRICE;
    public static int SERVICES_CHANGE_SEX_ITEM;
    public static boolean SERVICES_CHANGE_BASE_ENABLED;
    public static boolean SERVICES_CHANGE_BASE_LIST_UNCOMPATABLE;
    public static int SERVICES_CHANGE_BASE_PRICE;
    public static int SERVICES_CHANGE_BASE_ITEM;
    public static boolean SERVICES_SEPARATE_SUB_ENABLED;
    public static int SERVICES_SEPARATE_SUB_PRICE;
    public static int SERVICES_SEPARATE_SUB_ITEM;
    public static int SERVICES_SEPARATE_SUB_MIN_LEVEL;
    public static boolean SERVICES_CHANGE_NICK_COLOR_ENABLED;
    public static int[] SERVICES_CHANGE_NICK_COLOR_PRICE;
    public static int[] SERVICES_CHANGE_NICK_COLOR_ITEM;
    public static String[] SERVICES_CHANGE_NICK_COLOR_LIST;
    public static boolean SERVICES_CHANGE_TITLE_COLOR_ENABLED;
    public static int[] SERVICES_CHANGE_TITLE_COLOR_PRICE;
    public static int[] SERVICES_CHANGE_TITLE_COLOR_ITEM;
    public static String[] SERVICES_CHANGE_TITLE_COLOR_LIST;
    public static boolean SERVICE_CAPTCHA_BOT_CHECK;
    public static int CAPTCHA_MONSTER_KILLS_THRESHOLD;
    public static int CAPTCHA_PENALTY_SKILL_ID;
    public static int CAPTCHA_PENALTY_SKILL_LEVEL;
    public static boolean AUTO_FARM_PA_UNLIMITED;
    public static boolean ALLOW_AUTO_FARM;
    public static boolean AUTOFARM_TIME_TRACK_USAGE_ONLY;
    public static boolean AUTO_FARM_FOR_PREMIUM;
    public static boolean AUTO_FARM_ALLOW_FOR_CURSED_WEAPON;
    public static boolean AUTO_FARM_UNLIMITED;
    public static Map<Integer, Pair<Integer, Long>> AUTO_FARM_PRICES;
    public static int ATTACK_SKILL_CHANCE;
    public static int ATTACK_SKILL_PERCENT;
    public static int CHANCE_SKILL_CHANCE;
    public static int CHANCE_SKILL_PERCENT;
    public static int SELF_SKILL_CHANCE;
    public static int SELF_SKILL_PERCENT;
    public static int HEAL_SKILL_CHANCE;
    public static int HEAL_SKILL_PERCENT;
    public static int SUMMON_ATTACK_SKILL_CHANCE;
    public static int SUMMON_ATTACK_SKILL_PERCENT;
    public static int SUMMON_SELF_SKILL_CHANCE;
    public static int SUMMON_SELF_SKILL_PERCENT;
    public static int SUMMON_HEAL_SKILL_CHANCE;
    public static int SUMMON_HEAL_SKILL_PERCENT;
    public static long SKILLS_EXTRA_DELAY;
    public static long KEEP_LOCATION_DELAY;
    public static long RUN_CLOSE_UP_DELAY;
    public static int RUN_CLOSE_UP_DISTANCE;
    public static int SHORTCUT_PAGE;
    public static int SEARCH_DISTANCE;
    public static int FARM_TYPE;
    public static int FARM_INTERVAL_TASK;
    public static boolean ALLOW_FARM_FREE_TIME;
    public static boolean REFRESH_FARM_TIME;
    public static int FARM_FREE_TIME;
    public static boolean ALLOW_CHECK_HWID_LIMIT;
    public static int FARM_ACTIVE_LIMITS;
    public static int[] FARM_EXPEND_LIMIT_PRICE;
    public static int[] AUTO_FARM_IGNORED_NPC_ID;
    public static AbnormalEffect SERVICES_AUTO_FARM_ABNORMAL;
    public static boolean SERVICE_AUTO_FARM_SET_RED_RING;
    public static Set<String> AUTO_FARM_LIMIT_ZONE_NAMES;
    public static boolean AUTO_FARM_STOP_ON_PVP_FLAG;
    public static boolean SERVICES_RATE_ENABLED;
    public static boolean SERVICES_RATE_COMMAND_ENABLED;
    public static boolean SERVICES_RATE_EXPIRE_TIME_AT_ENTER_WORLD;
    public static RateBonusInfo[] SERVICES_RATE_BONUS_INFO;
    public static boolean SERVICES_NOBLESS_SELL_ENABLED;
    public static boolean SERVICES_NOBLESS_SELL_WITHOUT_SUBCLASS;
    public static int SERVICES_NOBLESS_SELL_PRICE;
    public static int SERVICES_NOBLESS_SELL_ITEM;
    public static int NOBLESS_LEVEL_FOR_SELL;
    public static boolean SERVICES_ITEM_NOBLESS_SELL_ENABLED;
    public static int[] SERVICES_ITEM_NOBLESS_SELL_ITEM_ID;
    public static boolean SERVICES_ITEM_NOBLESS_SELL_WITHOUT_SUBCLASS;
    public static int NOBLESS_ITEM_LEVEL_FOR_SELL;
    public static boolean SERVICES_DELEVEL_SELL_ENABLED;
    public static int SERVICES_DELEVEL_SELL_PRICE;
    public static int SERVICES_DELEVEL_SELL_ITEM;
    public static int SERVICES_DELEVEL_REDUCTION;
    public static boolean SERVICES_DELEVEL_ALLOW_FOR_SUBCLASS;
    public static boolean SERVICE_RESET_LEVEL_ENABLED;
    public static int[] SERVICE_RESET_LEVEL_ITEM_ID;
    public static int[] SERVICE_RESET_LEVEL_ITEM_COUNT;
    public static int SERVICE_RESET_LEVEL_NEED;
    public static int SERVICE_RESET_LEVEL_REMOVE;
    public static int SERVICE_RESET_LEVEL_LIMIT;
    public static long SERVICE_RESET_REUSE_DELAY;
    public static boolean SERVICE_RESET_ALLOW_FOR_SUBCLASS;
    public static boolean SERVICES_LEVEL_UP_SELL_ENABLED;
    public static int SERVICES_LEVEL_UP_SELL_PRICE;
    public static int SERVICES_LEVEL_UP_SELL_ITEM;
    public static int SERVICES_LEVEL_UP_COUNT;
    public static int SERVICES_LEVEL_MAX_LEVEL_FOR_MAIN_CLASS;
    public static int SERVICES_LEVEL_MAX_LEVEL_FOR_SUB_CLASS;
    public static boolean SERVICES_HERO_SELL_ENABLED;
    public static boolean SERVICE_HERO_ALLOW_ON_SUB_CLASS;
    public static boolean SERVICE_HERO_ALLOW_WITHOUT_NOBLE;
    public static int[] SERVICES_HERO_SELLER_ITEM_IDS;
    public static long[] SERVICES_HERO_SELLER_ITEM_COUNTS;
    public static long[] SERVICE_HERO_STATUS_DURATIONS;
    public static boolean SERVICES_OLY_POINTS_RESET;
    public static int SERVICES_OLY_POINTS_RESET_ITEM_ID;
    public static int SERVICES_OLY_POINTS_RESET_ITEM_AMOUNT;
    public static int SERVICES_OLY_POINTS_THRESHOLD;
    public static int SERVICES_OLY_POINTS_REWARD;
    public static boolean SERVICES_PK_CLEAN_ENABLED;
    public static int SERVICES_PK_CLEAN_SELL_ITEM;
    public static long SERVICES_PK_CLEAN_SELL_PRICE;
    public static boolean SERVICE_PROMOCODE_ENABLED;
    public static boolean SERVICE_PROMOCODE_COMMAND_ENABLED;
    public static boolean SERVICE_PROMOCODE_NEW_SYSTEM;
    public static int SERVICES_PK_ANNOUNCE;
    public static boolean SERVICES_PVP_RANKING_BONUS;
    public static boolean SERVICES_PVP_RANKING_BONUS_COMMAND;
    public static boolean SERVICES_ALLOW_WYVERN_RIDE;
    public static int[] SERVICES_WYVERN_RIDE_NPC_ID;
    public static int SERVICES_WYVERN_ITEM_ID;
    public static boolean SERVICES_KARMA_CLEAN_ENABLED;
    public static int SERVICES_KARMA_CLEAN_SELL_ITEM;
    public static long SERVICES_KARMA_CLEAN_SELL_PRICE;
    public static boolean SERVICES_CLANLEVEL_SELL_ENABLED;
    public static long[] SERVICES_CLANLEVEL_SELL_PRICE;
    public static int[] SERVICES_CLANLEVEL_SELL_ITEM;
    public static boolean SERVICES_CLAN_REPUTATION_ENABLE;
    public static int SERVICES_CLAN_REPUTATION_ITEM_ID;
    public static int SERVICES_CLAN_REPUTATION_ITEM_COUNT;
    public static int SERVICES_CLAN_REPUTATION_AMOUNT;
    public static boolean SERVICES_CHAR_RECOMMENDATION_ENABLE;
    public static int SERVICES_CHAR_RECOMMENDATION_ITEM_ID;
    public static int SERVICES_CHAR_RECOMMENDATION_ITEM_COUNT;
    public static int SERVICES_CHAR_RECOMMENDATION_AMOUNT;
    public static boolean SERVICES_CLANHELPER_ENABLED;
    public static String SERVICES_CLANHELPER_CONFIG;
    public static boolean SERVICES_CLANHELPER_ADD_FULL_CLAN_SKILLS;
    public static boolean SERVICES_CLAN_BUFF_ENABLED;
    public static int SERVICES_CLAN_BUFF_SKILL_ID;
    public static String SERVICES_CLAN_BUFF_LEVELS_MIN_ONLINE;
    public static long SERVICES_CLAN_BUFF_TASK_DELAY;
    public static boolean SERVICES_EXPAND_INVENTORY_ENABLED;
    public static int SERVICES_EXPAND_INVENTORY_PRICE;
    public static int SERVICES_EXPAND_INVENTORY_ITEM;
    public static int SERVICES_EXPAND_INVENTORY_MAX;
    public static int SERVICES_EXPAND_INVENTORY_SLOT_AMOUNT;
    public static boolean SERVICES_EXPAND_WAREHOUSE_ENABLED;
    public static int SERVICES_EXPAND_WAREHOUSE_PRICE;
    public static int SERVICES_EXPAND_WAREHOUSE_ITEM;
    public static int SERVICES_EXPAND_WAREHOUSE_MAX;
    public static int SERVICES_EXPAND_WAREHOUSE_SLOT_AMOUNT;
    public static boolean SERVICES_EXPAND_CWH_ENABLED;
    public static int SERVICES_EXPAND_CWH_PRICE;
    public static int SERVICES_EXPAND_CWH_SLOT_AMOUNT;
    public static int SERVICES_EXPAND_CWH_ITEM;
    public static int SEVICES_EXPAND_CWH_SLOT_LIMITS;
    public static boolean SERVICES_AUTO_HEAL_ACTIVE;
    public static String SERVICES_SELLPETS;
    public static boolean SERVICES_OFFLINE_TRADE_ALLOW;
    public static boolean ALLOW_TRADE_ON_THE_MOVE;
    public static boolean SERVICES_OFFLINE_TRADE_ALLOW_OFFSHORE;
    public static int SERVICES_OFFLINE_TRADE_MIN_LEVEL;
    public static int SERVICES_OFFLINE_TRADE_NAME_COLOR;
    public static boolean SERVICES_OFFLINE_TRADE_NAME_COLOR_CHANGE;
    public static AbnormalEffect SERVICES_OFFLINE_TRADE_ABNORMAL;
    public static int SERVICES_OFFLINE_TRADE_PRICE;
    public static int SERVICES_OFFLINE_TRADE_PRICE_ITEM;
    public static long SERVICES_OFFLINE_TRADE_SECONDS_TO_KICK;
    public static boolean SERVICES_OFFLINE_TRADE_RESTORE_AFTER_RESTART;
    public static boolean SERVICES_GIRAN_HARBOR_ENABLED;
    public static int[] SERVICES_GIRAN_HARBOR_NPC_ID;
    public static int[] SERVICES_GIRAN_HARBOR_EXIT_NPC_ID;
    public static boolean SERVICES_GIRAN_HARBOR_NOTAX;
    public static int SERVICES_GIRAN_HARBOR_PRICE;
    public static int SERVICES_GIRAN_HARBOR_MP_REG_PRICE;
    public static boolean SERVICES_ALLOW_LOTTERY;
    public static int SERVICE_LOTTERY_ITEM_ID;
    public static int SERVICES_LOTTERY_PRIZE;
    public static int SERVICES_ALT_LOTTERY_PRICE;
    public static int SERVICES_LOTTERY_TICKET_PRICE;
    public static double SERVICES_LOTTERY_5_NUMBER_RATE;
    public static double SERVICES_LOTTERY_4_NUMBER_RATE;
    public static double SERVICES_LOTTERY_3_NUMBER_RATE;
    public static int SERVICES_LOTTERY_2_AND_1_NUMBER_PRIZE;
    public static String SERVICES_LOTTERY_END_CRON;
    public static boolean SERVICE_MONSTER_RACE_ENABLED;
    public static int SERVICE_MONSTER_RACE_CURRENCY;
    public static int SERVICE_MONSTER_RACE_TICKET_ID;
    public static String[] SERVICE_MONSTER_RACE_ALLOWED_ZONES;
    public static int[] SERVICE_MONSTER_RACE_BID;
    public static boolean SERVICES_ALLOW_ROULETTE;
    public static int SERVICES_ROULETTE_ITEM_ID;
    public static int SERVICES_ROULETTE_MIN_BET;
    public static int SERVICES_ROULETTE_MAX_BET;
    public static int[] SERVICES_ROULETTE_NPC_ID;
    public static boolean SERVICE_ANNOUNCE_SERVER_LIFE_TIME;
    public static Location SERVICE_JAIL_COORDINATES;
    public static boolean SERVICE_JAIL_BLOCK_CHARACTER;
    public static boolean SELLBUFF_ENABLED;
    public static int SELLBUFF_MAX_BUFFS;
    public static boolean SELLBUFF_OFFLINE_TRADE;
    public static String SELLBUFF_PREFIX;
    public static boolean ITEM_CLAN_REPUTATION_ENABLE;
    public static int[] ITEM_CLAN_REPUTATION_ID;
    public static int[] ITEM_CLAN_REPUTATION_AMOUNT;
    public static boolean ITEM_PREMIUM_ACCOUNT_ENABLE;
    public static int[] ITEM_PREMIUM_ACCOUNT_ITEM_ID;
    public static int[] ITEM_PREMIUM_ACCOUNT_BONUS_ID;
    public static boolean ALT_ALLOW_OTHERS_WITHDRAW_FROM_CLAN_WAREHOUSE;
    public static boolean ALT_ALLOW_CLAN_COMMAND_ONLY_FOR_CLAN_LEADER;
    public static boolean ALT_ALLOW_CLAN_COMMAND_ALLOW_WH;
    public static boolean ALT_ALLOW_MENU_COMMAND;
    public static boolean ALT_ALLOW_RELOG_COMMAND;
    public static boolean ALT_ALLOW_RELOG_AT_EPIC_ZONE;
    public static boolean ALT_ALLOW_LANG_COMMAND;
    public static boolean ALT_ALLOW_HELP_COMMAND;
    public static boolean ALT_ALLOW_SERVER_INFO_COMMAND;
    public static boolean ALT_ALLOW_MAMMON_SEARCH_COMMAND;
    public static boolean ALT_ALLOW_SERVICES_COMMAND;
    public static boolean EVENT_PVP_REGISTRATION_COMMAND;
    public static boolean ALT_ALLOW_JUMP_COMMAND;
    public static String ALT_CHANGE_LANG_NAME_1;
    public static String ALT_CHANGE_LANG_NAME_2;
    public static boolean ALT_ALLOW_PING_COMMAND;
    public static boolean ALT_GAME_REQUIRE_CLAN_CASTLE;
    public static boolean ALT_GAME_REQUIRE_CASTLE_DAWN;
    public static boolean ALT_GAME_ALLOW_ADENA_DAWN;
    public static boolean ALT_REVIVE_WINDOW_TO_TOWN;
    public static Location ALT_REVIVE_WINDOW_TO_TOWN_LOCATION;
    public static long NONOWNER_ITEM_PICKUP_DELAY;
    public static long NONOWNER_ITEM_PICKUP_DELAY_RAID;
    public static TIntHashSet IGNORE_ITEM_PICKUP_DELAY_MONSTER_IDS;
    public static long IGNORE_ITEM_PICKUP_DELAY;
    public static boolean LOG_CHAT;
    public static boolean LOG_GM;
    public static boolean LOG_ITEM;
    public static boolean LOG_SERVICES;
    public static Map<Integer, PlayerAccess> gmlist;
    public static double RATE_XP;
    public static double RATE_SP;
    public static double RATE_OVERHIT;
    public static double RATE_QUESTS_REWARD;
    public static double RATE_QUESTS_ADENA_REWARD;
    public static double RATE_QUESTS_REWARD_EXP_SP;
    public static double RATE_QUESTS_DROP;
    public static double RATE_CLAN_REP_SCORE;
    public static int RATE_CLAN_REP_SCORE_MAX_AFFECTED;
    public static boolean ALT_MULTI_DROP;
    public static double RATE_DROP_ADENA;
    public static double RATE_DROP_ITEMS;
    public static double RATE_DROP_SEAL_STONES;
    public static long MAXIMUM_CONTRIBUTION_SEAL_STONES;
    public static double RATE_DROP_HERBS;
    public static double RATE_DROP_RAIDBOSS;
    public static double RATE_RAIDBOSS_XP;
    public static double RATE_RAIDBOSS_SP;
    public static double RATE_DROP_SPOIL;
    public static int[] NO_RATE_ITEMS;
    public static int[] NO_DROP_ITEMS;
    public static int[] NO_DROP_ITEMS_FOR_SWEEP;
    public static int[] ALLOW_ONLY_THESE_DROP_ITEMS_ID;
    public static boolean NO_RATE_EQUIPMENT;
    public static boolean NO_RATE_KEY_MATERIAL;
    public static boolean NO_RATE_RECIPES;
    public static double RATE_DROP_SIEGE_GUARD;
    public static double RATE_MANOR;
    public static double RATE_FISH_DROP_COUNT;
    public static boolean RATE_PARTY_MIN;
    public static int SKILL_COST_RATE;
    public static long ITEMS_MAX_AMMOUNT;
    public static int RATE_MOB_SPAWN;
    public static int RATE_MOB_SPAWN_MIN_LEVEL;
    public static int RATE_MOB_SPAWN_MAX_LEVEL;
    public static boolean KARMA_DROP_GM;
    public static boolean KARMA_NEEDED_TO_DROP;
    public static int ITEM_ANTIDROP_FROM_PK;
    public static long SERVICES_PK_KILL_BONUS_INTERVAL;
    public static boolean SERVICES_PK_KILL_BONUS_ENABLE;
    public static int[] SERVICES_PVP_KILL_BONUS_REWARD_ITEM;
    public static int[] SERVICES_PVP_KILL_BONUS_REWARD_COUNT;
    public static float[] SERVICES_PVP_KILL_BONUS_REWARD_CHANCE;
    public static boolean SERVICES_PVP_KILL_BONUS_ENABLE;
    public static int[] SERVICES_PK_KILL_BONUS_REWARD_COUNT;
    public static int[] SERVICES_PK_KILL_BONUS_REWARD_ITEM;
    public static float[] SERVICES_PK_KILL_BONUS_REWARD_CHANCE;
    public static boolean SERVICES_PK_PVP_BONUS_TIE_IF_SAME_IP;
    public static boolean SERVICES_PK_PVP_BONUS_TIE_IF_SAME_HWID;
    public static int PVP_COUNT_TO_CHAT;
    public static boolean PVP_HERO_CHAT_SYSTEM;
    public static boolean PVP_INCREASE_SAME_IP_CHECK;
    public static boolean PVP_INCREASE_SAME_HWID_CHECK;
    public static int KARMA_DROP_ITEM_LIMIT;
    public static int KARMA_RANDOM_DROP_LOCATION_LIMIT;
    public static double KARMA_DROPCHANCE_BASE;
    public static double KARMA_DROPCHANCE_MOD;
    public static double NORMAL_DROPCHANCE_BASE;
    public static int DROPCHANCE_EQUIPMENT;
    public static int DROPCHANCE_EQUIPPED_WEAPON;
    public static int DROPCHANCE_ITEM;
    public static int PVP_POINTS_AMOUNT_ADD;
    public static boolean PVP_POINTS_ADD_ON_WAR_SUMMON_KILL;
    public static boolean FUN_ZONE_PVP_COUNT;
    public static boolean BATTLE_ZONE_PVP_COUNT;
    public static boolean SIEGE_ZONE_PVP_COUNT;
    public static boolean FUN_ZONE_FLAG_ON_ENTER;
    public static int AUTODESTROY_ITEM_AFTER;
    public static int AUTODESTROY_PLAYER_ITEM_AFTER;
    public static double DELETE_DAYS;
    public static int PURGE_BYPASS_TASK_FREQUENCY;
    public static File DATAPACK_ROOT;
    public static double CLANHALL_BUFFTIME_MODIFIER;
    public static double SONGDANCETIME_MODIFIER;
    public static boolean SONGDANCE_CAN_BE_DISPELL;
    public static int CLNHALL_REWARD_CYCLE;
    public static int CLAN_MIN_LEVEL_FOR_BID;
    public static Map<Integer, Integer> SKILL_DURATION_MOD;
    public static boolean SKILL_DURATION_MOD_AT_OLY;
    public static double MAXLOAD_MODIFIER;
    public static Map<Integer, Float> GATEKEEPER_MODIFIER;
    public static boolean ALT_IMPROVED_PETS_LIMITED_USE;
    public static int GATEKEEPER_CATOCOMBS_FREE_LEVEL;
    public static int CRUMA_TELEPORT_MAX_LEVEL;
    public static double ALT_CHAMPION_CHANCE1;
    public static double ALT_CHAMPION_CHANCE2;
    public static boolean ALT_CHAMPION_CAN_BE_AGGRO;
    public static boolean ALT_CHAMPION_CAN_BE_SOCIAL;
    public static boolean ALT_CHAMPION_CAN_BE_SPECIAL_MONSTERS;
    public static boolean ALT_CHAMPION_CAN_BE_SEVEN_SIGN_MONSTERS;
    public static int ALT_CHAMPION_TOP_LEVEL;
    public static int ALT_CHAMPION_MIN_LEVEL;
    public static Set<Integer> ALT_CHAMPIONS_LIST;
    public static Set<Integer> ALT_IGNORE_CHAMPIONS_LIST;
    public static Map<Integer, int[]> ALT_CHAMPION_DROP_ITEM_IDS;
    public static Map<Integer, float[]> ALT_CHAMPION_DROP_CHANCES;
    public static Map<Integer, long[]> ALT_CHAMPION_DROP_COUNTS;
    public static int ALT_CHAMPION_DROP_LEVEL_DIFF;
    public static boolean ALT_PVP_ITEMS_TREDABLE;
    public static boolean ALT_PVP_ITEMS_ATTRIBUTABLE;
    public static boolean ALT_PVP_ITEMS_AUGMENTABLE;
    public static int[] ALT_INITIAL_QUESTS;
    public static boolean ALLOW_DISCARDITEM;
    public static boolean ALLOW_WAREHOUSE;
    public static boolean ALLOW_WATER;
    public static boolean ALLOW_CURSED_WEAPONS;
    public static boolean DROP_CURSED_WEAPONS_ON_KICK;
    public static boolean DROP_CURSED_WEAPONS_ON_LOGOUT;
    public static boolean ALLOW_NOBLE_TP_TO_ALL;
    public static boolean ALLOW_MAIL;
    public static int MAIL_SEND_PRICE;
    public static int MAIL_SEND_COUNT_MULTIPLIER;
    public static int MAIL_INBOX_LIMIT;
    public static int MAIL_MIN_LEVEL_SENDER;
    public static boolean MAIL_ALLOW_AT_PEACE_ZONE;
    public static int MAIL_SEND_COUNT_PER_DAY;
    public static long MAIL_SEND_INTERVAL;
    public static int MAIL_CACHE_SIZE;
    public static int SWIMING_SPEED;
    public static int MIN_PROTOCOL_REVISION;
    public static int MAX_PROTOCOL_REVISION;
    public static String ALT_CG_MODULE;
    public static boolean PFILTER_ENABLED;
    public static int MIN_NPC_ANIMATION;
    public static int MAX_NPC_ANIMATION;
    public static String DEFAULT_LANG;
    public static String RESTART_AT_TIME;
    public static int GAME_SERVER_LOGIN_PORT;
    public static boolean GAME_SERVER_LOGIN_CRYPT;
    public static String GAME_SERVER_LOGIN_HOST;
    public static String INTERNAL_HOSTNAME;
    public static String EXTERNAL_HOSTNAME;
    public static boolean SERVER_SIDE_NPC_NAME;
    public static boolean SERVER_SIDE_NPC_TITLE;
    public static boolean SERVER_SIDE_MONSTER_LEVEL_TITLE;
    public static boolean SERVER_SIDE_AGRO_MONSTER_RED_NAME;
    public static boolean TEST_SERVER_HELPER_ENABLED;
    public static String CLASS_MASTERS_CLASSES;
    public static boolean ALLOW_EVENT_GATEKEEPER;
    public static boolean ALLOW_GLOBAL_GK;
    public static boolean ALLOW_BUFFER;
    public static boolean ALLOW_GMSHOP;
    public static boolean ALLOW_AUCTIONER;
    public static boolean ALLOW_GLOBAL_SERVICES;
    public static boolean ALLOW_PVP_EVENT_MANAGER;
    public static boolean ALLOW_TREASURE_BOX;
    public static boolean SERVICE_FEATHER_REVIVE_ENABLE;
    public static boolean SERVICE_DISABLE_FEATHER_ON_SIEGES_AND_EPIC;
    public static int SERVICE_FEATHER_ITEM_ID;
    public static boolean COMMAND_CLASS_MASTER_ENABLED;
    public static String COMMAND_CLASS_MASTER_CLASSES;
    public static int COMMAND_CLASS_POPUP_LIMIT;
    public static String[] COMMAND_CLASS_MASTER_VOICE_COMMANDS;
    public static boolean PAWNSHOP_ENABLED;
    public static ItemTemplate.ItemClass[] PAWNSHOP_ITEMS_CLASSES;
    public static int PAWNSHOP_MIN_ENCHANT_WEAPON_LEVEL;
    public static int PAWNSHOP_MIN_ENCHANT_ARMOR_LEVEL;
    public static int PAWNSHOP_MIN_ENCHANT_ACCESSORY_LEVEL;
    public static boolean PAWNSHOP_ALLOW_SELL_AUGMENTED_ITEMS;
    public static String PAWNSHOP_MIN_GRADE;
    public static int PAWNSHOP_ITEMS_PER_PAGE;
    public static int[] PAWNSHOP_CURRENCY_ITEM_IDS;
    public static int[] PAWNSHOP_PROHIBITED_ITEM_IDS;
    public static int PAWNSHOP_MIN_QUERY_LENGTH;
    public static int PAWNSHOP_TAX_ITEM_ID;
    public static long PAWNSHOP_TAX_ITEM_COUNT;
    public static int PAWNSHOP_TAX_AUGMENTED_ITEM_ID;
    public static long PAWNSHOP_TAX_AUGMENTED_ITEM_COUNT;
    public static double PAWNSHOP_TAX_AUGMENTED_ITEM_PERCENT;
    public static double PAWNSHOP_TAX_ITEM_PERCENT;
    public static int PAWNSHOP_REFUND_ITEM_ID;
    public static long PAWNSHOP_REFUND_ITEM_COUNT;
    public static boolean PAWNSHOP_PRICE_SORT;
    public static int PAWNSHOP_ITEM_TERM;
    public static boolean ITEM_BROKER_ITEM_SEARCH;
    public static long ITEM_BROKER_UPDATE_TIME;
    public static int[] ITEM_BROKER_NPC_ID;
    public static boolean AUGMENTATION_TRANSFER_ENABLE;
    public static int AUGMENTATION_TRANSFER_REQUIRED_ITEM_ID;
    public static int AUGMENTATION_TRANSFER_REQUIRED_ITEM_COUNT;
    public static int AUGMENTATION_TRANSFER_MAX_LIST_LENGTH;
    public static int[] AUGMENTATION_TRANSFER_PROHIBITED_ITEM_IDS;
    public static int INVENTORY_MAXIMUM_NO_DWARF;
    public static int INVENTORY_MAXIMUM_DWARF;
    public static boolean DWARF_AUTOMATICALLY_CRYSTALLIZE_ON_ITEM_DELETE;
    public static boolean CRYSTALLIZE_BONUS_AT_ENCHANT;
    public static int INVENTORY_MAXIMUM_GM;
    public static int INVENTORY_MAXIMUM_PERMISSIBLE_LIMIT;
    public static int QUEST_INVENTORY_MAXIMUM;
    public static int WAREHOUSE_SLOTS_NO_DWARF;
    public static int WAREHOUSE_SLOTS_DWARF;
    public static int WAREHOUSE_SLOTS_CLAN;
    public static int FREIGHT_SLOTS;
    public static boolean FREIGHT_SEND_TYPE;
    public static int FOLLOW_ARRIVE_DISTANCE;
    public static boolean SEND_LINEAGE2_WELCOME_MESSAGE;
    public static boolean SEND_SSQ_WELCOME_MESSAGE;
    public static boolean SEND_SSQ_SEAL_STATUS;
    public static boolean SEND_EFFECT_LIST_ON_TARGET;
    public static boolean SEND_EFFECT_LIST_ON_TARGET_NPC;
    public static boolean SEND_BUFF_LIST_ON_TARGET;
    public static boolean SEND_DEBUFF_LIST_ON_TARGET;
    public static double MINIMUM_SPOIL_RATE;
    public static int[] SPOIL_ITEMS_ID_FOR_RATE;
    public static double SPOIL_ITEMS_CHANCE_RATE;
    public static double MANOR_SOWING_BASIC_SUCCESS;
    public static double MANOR_SOWING_ALT_BASIC_SUCCESS;
    public static double MANOR_HARVESTING_BASIC_SUCCESS;
    public static double MANOR_HARVESTING_REWARD_RATE;
    public static int MANOR_DIFF_PLAYER_TARGET;
    public static double MANOR_DIFF_PLAYER_TARGET_PENALTY;
    public static int MANOR_DIFF_SEED_TARGET;
    public static double MANOR_DIFF_SEED_TARGET_PENALTY;
    public static int KARMA_MIN_INCREASE;
    public static int KARMA_STATIC_INCREASES;
    public static int KARMA_STATIC_LOST_ON_DEATH;
    public static int RATE_KARMA_LOST_STATIC;
    public static int KARMA_RATE_LOST;
    public static int MIN_PK_TO_ITEMS_DROP;
    public static boolean DROP_ITEMS_ON_DIE;
    public static boolean DROP_ITEMS_AUGMENTED;
    public static List<Integer> KARMA_LIST_NONDROPPABLE_ITEMS;
    public static int PVP_TIME;
    public static int PVP_BLINKING_UNFLAG_TIME;
    public static int PVP_FLAG_ON_UN_FLAG_TIME;
    public static boolean ALT_GAME_KARMA_PLAYER_CAN_SHOP;
    public static boolean ALT_GAME_CURSED_WEAPON_PLAYER_CAN_SHOP;
    public static boolean ALT_CAN_ATTACK_NPC_AT_PEACE_ZONE;
    public static int ENCHANT_MAX;
    public static int ENCHANT_ATTRIBUTE_STONE_CHANCE;
    public static int ENCHANT_ATTRIBUTE_CRYSTAL_CHANCE;
    public static int ENCHANT_ATTRIBUTE_ENERGY_CHANCE;
    public static int ARMOR_OVERENCHANT_HPBONUS_LIMIT;
    public static boolean SHOW_ENCHANT_EFFECT_RESULT;
    public static boolean SHOW_ENCHANT_EFFECT_RESULT_EVERY_NEXT_SUCCESS;
    public static int WEAPON_FIRST_ENCHANT_EFFECT_LEVEL;
    public static int WEAPON_SECOND_ENCHANT_EFFECT_LEVEL;
    public static int ARMOR_ENCHANT_EFFECT_LEVEL;
    public static boolean REGEN_SIT_WAIT;
    public static double RATE_RAID_REGEN;
    public static double RATE_RAID_DEFENSE;
    public static double RATE_RAID_ATTACK;
    public static int RATE_MOD_MIN_LEVEL_LIMIT;
    public static int RATE_MOD_MAX_LEVEL_LIMIT;
    public static double RATE_EPIC_DEFENSE;
    public static double RATE_EPIC_ATTACK;
    public static int RAID_MAX_LEVEL_DIFF;
    public static boolean PARALIZE_ON_RAID_DIFF;
    public static double ALT_PK_DEATH_RATE;
    public static int STARTING_ADENA;
    public static int[] STARTING_ITEMS_MAGE;
    public static int[] STARTING_ITEMS_FIGHTER;
    public static boolean STARTING_PREMIUM;
    public static String STARTING_PREMIUM_PROMO_NAME;
    public static boolean ALT_CONSUME_ARROWS;
    public static boolean ALT_CONSUME_SOULSHOTS;
    public static boolean ALT_CONSUME_SPIRITSHOTS;
    public static boolean ALT_CONSUME_BEAST_SHOTS;
    public static boolean ALT_PA_CONSUME_ARROWS;
    public static boolean ALT_PA_CONSUME_SOULSHOTS;
    public static boolean ALT_PA_CONSUME_SPIRITSHOTS;
    public static boolean ALT_PA_CONSUME_BEAST_SHOTS;
    public static int[] ALT_UNIVERSAL_SHOTS;
    public static int[] ALT_UNIVERSAL_SPIRIT_SHOTS;
    public static int[] ALT_UNIVERSAL_BLESSED_SPIRIT_SHOTS;
    public static boolean DEEPBLUE_DROP_RULES;
    public static int DEEPBLUE_DROP_MAXDIFF;
    public static int DEEPBLUE_DROP_RAID_MAXDIFF;
    public static int DEEPRED_DROP_MAXDIFF;
    public static int DEEPRED_DROP_RAID_MAXDIFF;
    public static int EXP_SP_DIFF_LIMIT;
    public static int THRESHOLD_LEVEL_DIFF;
    public static int PARTY_PENALTY_EXP_SP_MAX_LEVEL;
    public static int PARTY_PENALTY_EXP_SP_MIN_LEVEL;
    public static boolean UNSTUCK_SKILL;
    public static boolean BLOCK_BUFF_SKILL;
    public static boolean NOBLES_BUFF_SKILL;
    public static int[] ADDITIONALS_SKILLS;
    public static IntSet BLOCK_BUFF_EXCLUDE;
    public static boolean IS_TELNET_ENABLED;
    public static String TELNET_DEFAULT_ENCODING;
    public static String TELNET_PASSWORD;
    public static String TELNET_HOSTNAME;
    public static int TELNET_PORT;
    public static int TELNET_SIMULTANEOUS_CONNECTIONS;
    public static Set<String> TELNET_ALLOWED_IPS;
    public static double RESPAWN_RESTORE_CP;
    public static double RESPAWN_RESTORE_HP;
    public static double RESPAWN_RESTORE_MP;
    public static int MAX_PVTSTORE_SLOTS_DWARF;
    public static int MAX_PVTSTORE_SLOTS_DWARF_FIRST_JOB;
    public static int MAX_PVTSTORE_SLOTS_OTHER;
    public static int MAX_PVTSTORE_SLOTS_OTHER_FIRST_JOB;
    public static int MAX_PVTCRAFT_SLOTS;
    public static boolean SENDSTATUS_TRADE_JUST_OFFLINE;
    public static double SENDSTATUS_TRADE_MOD;
    public static double MUL_PLAYERS_ONLINE;
    public static int CH_BID_CURRENCY_ITEM_ID;
    public static double RESIDENCE_LEASE_FUNC_MULTIPLIER;
    public static boolean RESIDENCE_CH_ALL_BUFFS;
    public static boolean RESIDENCE_CH_BUFFS_APPLY_ON_PET;
    public static TIntHashSet CH_DISPLAY_IDS;
    public static boolean ACCEPT_ALTERNATE_ID;
    public static int REQUEST_ID;
    public static boolean ANNOUNCE_MAMMON_SPAWN;
    public static int GM_NAME_COLOUR;
    public static boolean GM_HERO_AURA;
    public static int NORMAL_NAME_COLOUR;
    public static int CLANLEADER_NAME_COLOUR;
    public static boolean SHOW_HTML_WELCOME;
    public static int AI_TASK_MANAGER_COUNT;
    public static long AI_TASK_ATTACK_DELAY;
    public static long AI_TASK_ACTIVE_DELAY;
    public static boolean BLOCK_ACTIVE_TASKS;
    public static boolean ALWAYS_TELEPORT_HOME;
    public static boolean RND_WALK;
    public static int RND_WALK_RATE;
    public static int RND_ANIMATION_RATE;
    public static int AGGRO_CHECK_INTERVAL;
    public static long NONAGGRO_TIME_ONTELEPORT;
    public static long NONAGGRO_TIME_ONLOGIN;
    public static long GLOBAL_AGGRO_CHECK_INTERVAL;
    public static long FACTION_NOTIFY_INTERVAL;
    public static boolean RESET_HATE_ONLY_WHEN_LEAVING_OR_DYING;
    public static boolean RESET_HATE_ONLY_AFTER_PURSUE_RANGE;
    public static boolean ALT_TELEPORT_PROTECTION;
    public static long ALT_TELEPORT_PROTECTION_TIME;
    public static boolean ALT_SPREADING_AFTER_TELEPORT;
    public static boolean NO_CARRIER_PROTECTION;
    public static long NO_CARRIER_PROTECTION_TIME;
    public static int MAX_DRIFT_RANGE;
    public static int MAX_PURSUE_RANGE;
    public static int MAX_PURSUE_UNDERGROUND_RANGE;
    public static int MAX_PURSUE_RANGE_RAID;
    public static boolean RESTORE_HP_MP_ON_TELEPORT_HOME;
    public static int[] RESTORE_HP_MP_ON_EXCLUDED_IDS;
    public static boolean MONSTER_TELEPORT_TO_PLAYER;
    public static boolean ALT_DEATH_PENALTY;
    public static boolean ALLOW_DEATH_PENALTY_C5;
    public static int ALT_DEATH_PENALTY_C5_CHANCE;
    public static boolean ALT_DEATH_PENALTY_C5_CHAOTIC_RECOVERY;
    public static int ALT_DEATH_PENALTY_C5_EXPERIENCE_PENALTY;
    public static int ALT_DEATH_PENALTY_C5_KARMA_PENALTY;
    public static int ALT_MUSIC_COST_GUARD_INTERVAL;
    public static boolean ALT_ADDITIONAL_DANCE_SONG_MANA_CONSUME;
    public static boolean DISABLE_GRADE_PENALTY;
    public static boolean HIDE_GM_STATUS;
    public static boolean SHOW_GM_LOGIN;
    public static boolean SAVE_GM_EFFECTS;
    public static boolean AUTO_LEARN_SKILLS;
    public static boolean AUTO_LEARN_FORGOTTEN_SKILLS;
    public static boolean AUTO_LEARN_DIVINE_INSPIRATION;
    public static boolean ENABLE_MAX_SKILL_TRAINING;
    public static int MOVE_TASK_QUANTUM_PC;
    public static int MOVE_TASK_QUANTUM_NPC;
    public static boolean MOVE_OFFLOAD_MTL_PC;
    public static boolean MOVE_TO_TOWN_ON_FAIL_VALIDATE;
    public static int MOVE_TO_TOWN_ON_FAIL_TRY_COUNT;
    public static int ATTACK_PACKET_DELAY;
    public static int ATTACK_END_DELAY;
    public static int USE_ITEM_PACKET_DELAY;
    public static int UNKNOWN_PACKET_AMOUNT_LIMIT;
    public static int FAIL_PACKET_AMOUNT_LIMIT;
    public static int RESEND_MSU_AFTER_TELEPORT;
    public static int ITEM_LINK_CACHE_SIZE;
    public static boolean DAMAGE_FROM_FALLING;
    public static boolean COMMUNITYBOARD_ENABLED;
    public static String BBS_DEFAULT;
    public static boolean BBS_PEACE_ONLY;
    public static boolean ALLOW_WEDDING;
    public static int WEDDING_PRICE;
    public static int WEDDING_ITEM_ID_PRICE;
    public static boolean WEDDING_PUNISH_INFIDELITY;
    public static boolean WEDDING_TELEPORT;
    public static int WEDDING_TELEPORT_PRICE;
    public static int WEDDING_TELEPORT_INTERVAL;
    public static boolean WEDDING_SAMESEX;
    public static boolean WEDDING_FORMALWEAR;
    public static int WEDDING_DIVORCE_COSTS;
    public static boolean WEDDING_GIVE_SALVATION_BOW;
    public static boolean WEDDING_USE_COLOR;
    public static int WEDDING_NORMAL_COLOR;
    public static int WEDDING_GAY_COLOR;
    public static int WEDDING_LESBIAN_COLOR;
    public static boolean WEDDING_ANNOUNCE;
    public static boolean ALT_FISH_CHAMPIONSHIP_ENABLED;
    public static int ALT_FISH_CHAMPIONSHIP_REWARD_ITEM;
    public static int ALT_FISH_CHAMPIONSHIP_REWARD_1;
    public static int ALT_FISH_CHAMPIONSHIP_REWARD_2;
    public static int ALT_FISH_CHAMPIONSHIP_REWARD_3;
    public static int ALT_FISH_CHAMPIONSHIP_REWARD_4;
    public static int ALT_FISH_CHAMPIONSHIP_REWARD_5;
    public static boolean ALT_HBCE_FAIR_PLAY;
    public static int ALT_PET_INVENTORY_LIMIT;
    public static boolean ALT_SET_TITLE_OWNER_TO_SUMMON;
    public static boolean ALT_SAVE_SERVITOR_BUFF;
    public static int ALT_SERVITOR_ACTION_MAX_DISTANCE;
    public static int LIM_PATK;
    public static int LIM_MATK;
    public static int LIM_PDEF;
    public static int LIM_MDEF;
    public static int LIM_MATK_SPD;
    public static int LIM_PATK_SPD;
    public static int LIM_CRIT_DAM;
    public static int LIM_CRIT;
    public static int LIM_MCRIT;
    public static double MCRITICAL_CRIT_POWER;
    public static int BASE_MAGE_CAST_SPEED;
    public static int BASE_WARRIOR_CAST_SPEED;
    public static int LIM_ACCURACY;
    public static int LIM_EVASION;
    public static int LIM_MOVE;
    public static int LIM_MOVE_GM;
    public static int LIM_MAX_CP;
    public static int LIM_MAX_HP;
    public static int LIM_MAX_MP;
    public static int LIM_FAME;
    public static int LIM_PC_BANG_POINTS;
    public static double MCRITICAL_BASE_STAT;
    public static boolean MDAM_CRIT_POSSIBLE;
    public static boolean HEAL_CRIT_POSSIBLE;
    public static boolean ALT_DISABLE_MAGICFAILURES;
    public static int MAGIC_FAIL_LEVEL_MOD;
    public static double BLOW_RATE_CHANCE_LIMIT;
    public static double BLOW_SKILLS_MAX_CHANCE_LIMIT;
    public static double POLE_ATTACK_ANGLE;
    public static int LIMIT_HENNA_INT;
    public static int LIMIT_HENNA_STR;
    public static int LIMIT_HENNA_MEN;
    public static int LIMIT_HENNA_CON;
    public static int LIMIT_HENNA_WIT;
    public static int LIMIT_HENNA_DEX;
    public static boolean CALC_EFFECT_TIME_YIELD_AND_RESIST;
    public static int COUNTERATTACK_MAX_SKILL_RANGE;
    public static boolean MUSIC_REUSE_TIME_BASED_ON_MATK_SPD;
    public static boolean MAGIC_REUSE_TIME_BASED_ON_MATK_SPD;
    public static boolean PHYSIC_REUSE_TIME_BASED_ON_ATK_SPD;
    public static int MIN_ATK_DELAY;
    public static boolean AUTO_ATTACK_ON_WEAPON_CHANGE;
    public static double ALT_NPC_PATK_MODIFIER;
    public static double ALT_NPC_MATK_MODIFIER;
    public static double ALT_NPC_MAXHP_MODIFIER;
    public static double ALT_NPC_MAXMP_MODIFIER;
    public static double ALT_NPC_PDEF_MODIFIER;
    public static double ALT_NPC_MDEF_MODIFIER;
    public static double ALT_NPC_SPD_MODIFIER;
    public static double ALT_NPC_CAST_SPD_MODIFIER;
    public static int ALT_NPC_MIN_LEVEL_MODIFIER;
    public static int ALT_NPC_MAX_LEVEL_MODIFIER;
    public static double ALT_EPIC_BOSS_PATK_MODIFIER;
    public static double ALT_EPIC_BOSS_MATK_MODIFIER;
    public static double ALT_EPIC_BOSS_MAXHP_MODIFIER;
    public static double ALT_EPIC_BOSS_MAXMP_MODIFIER;
    public static double ALT_EPIC_BOSS_PDEF_MODIFIER;
    public static double ALT_EPIC_BOSS_MDEF_MODIFIER;
    public static double ALT_EPIC_BOSS_SPD_MODIFIER;
    public static double ALT_EPIC_BOSS_CAST_SPD_MODIFIER;
    public static int ALT_EPIC_BOSS_MIN_LEVEL_MODIFIER;
    public static int ALT_EPIC_BOSS_MAX_LEVEL_MODIFIER;
    public static double ALT_RAID_BOSS_PATK_MODIFIER;
    public static double ALT_RAID_BOSS_MATK_MODIFIER;
    public static double ALT_RAID_BOSS_MAXHP_MODIFIER;
    public static double ALT_RAID_BOSS_MAXMP_MODIFIER;
    public static double ALT_RAID_BOSS_PDEF_MODIFIER;
    public static double ALT_RAID_BOSS_MDEF_MODIFIER;
    public static double ALT_RAID_BOSS_SPD_MODIFIER;
    public static double ALT_RAID_BOSS_CAST_SPD_MODIFIER;
    public static int ALT_RAID_BOSS_MIN_LEVEL_MODIFIER;
    public static int ALT_RAID_BOSS_MAX_LEVEL_MODIFIER;
    public static int ALT_NPC_LIM_MCRIT;
    public static int FESTIVAL_MIN_PARTY_SIZE;
    public static double FESTIVAL_RATE_PRICE;
    public static int RIFT_MIN_PARTY_SIZE;
    public static int RIFT_SPAWN_DELAY;
    public static int RIFT_MAX_JUMPS;
    public static int RIFT_AUTO_JUMPS_TIME;
    public static int RIFT_AUTO_JUMPS_TIME_RAND;
    public static int RIFT_ENTER_COST_RECRUIT;
    public static int RIFT_ENTER_COST_SOLDIER;
    public static int RIFT_ENTER_COST_OFFICER;
    public static int RIFT_ENTER_COST_CAPTAIN;
    public static int RIFT_ENTER_COST_COMMANDER;
    public static int RIFT_ENTER_COST_HERO;
    public static int RIFT_BOSS_ROOM_CHANCE;
    public static boolean FOUR_SEPULCHER_ENABLE;
    public static int FOUR_SEPULCHER_MIN_PARTY_MEMBERS;
    public static int FOUR_SEPULCHER_NEW_CYCLE_MIN;
    public static boolean ALLOW_TALK_WHILE_SITTING;
    public static boolean PARTY_LEADER_ONLY_CAN_INVITE;
    public static long EXPELLED_MEMBER_PENALTY;
    public static long NEW_CLAN_CREATE_PENALTY;
    public static long CLAN_LEAVE_TIME_PERNALTY;
    public static long ALLY_LEAVE_TIME_PENALTY;
    public static long CLAN_DISBAND_TIME;
    public static long CLAN_DISBAND_PENALTY;
    public static boolean ALLY_ALLOW_BUFF_DEBUFFS;
    public static int ALT_NPC_CLAN;
    public static boolean ALLOW_TEMPORARILY_ALLY_ON_FIRST_SIEGE;
    public static long LEAVED_ALLY_PENALTY;
    public static long DISSOLVED_ALLY_PENALTY;
    public static boolean ALLOW_CLANSKILLS;
    public static int CLAN_INIT_LEVEL;
    public static int CLAN_REPUTATION_BONUS_ON_CREATE;
    public static boolean FULL_CLAN_SKILLS_ON_CREATE;
    public static int CHARACTER_MIN_LEVEL_FOR_CLAN_CREATE;
    public static boolean CLAN_LEADER_CHANGE_METHOD;
    public static int LIMIT_CLAN_LEVEL0;
    public static int LIMIT_CLAN_LEVEL1;
    public static int LIMIT_CLAN_LEVEL2;
    public static int LIMIT_CLAN_LEVEL3;
    public static int LIMIT_CLAN_LEVEL_4_AND_HIGH;
    public static int MIN_CLAN_LEVEL_FOR_DECLARED_WAR;
    public static int MIN_CLAN_MEMBER_FOR_DECLARED_WAR;
    public static int CRP_REWARD_ON_WAR_KILL_OVER_LEVEL;
    public static int CRP_REWARD_ON_WAR_KILL;
    public static int LIMIT_CLAN_HIGH_UNITS;
    public static int LIMIT_CLAN_LOW_UNITS;
    public static int LIMIT_CLAN_ACADEMY;
    public static int CLAN_FIRST_LEVEL_SP;
    public static int CLAN_FIRST_LEVEL_ADENA;
    public static int CLAN_SECOND_LEVEL_SP;
    public static int CLAN_SECOND_LEVEL_ADENA;
    public static int CLAN_THIRD_LEVEL_SP;
    public static int CLAN_FOUR_LEVEL_SP;
    public static int CLAN_FIVE_LEVEL_SP;
    public static int CLAN_SIX_LEVEL_CLAN_REPUTATION;
    public static int CLAN_SIX_LEVEL_CLAN_MEMBER_COUNT;
    public static int CLAN_SEVEN_LEVEL_CLAN_REPUTATION;
    public static int CLAN_SEVEN_LEVEL_CLAN_MEMBER_COUNT;
    public static int CLAN_EIGHT_LEVEL_CLAN_REPUTATION;
    public static int CLAN_EIGHT_LEVEL_CLAN_MEMBER_COUNT;
    public static boolean CHECK_CLAN_RANK_ON_COMMAND_CHANNEL_CREATE;
    public static int COMMAND_CHANNEL_PARY_COUNT_LIMIT;
    public static int MIN_CLAN_LEVEL_FOR_REPUTATION;
    public static int MIN_CLAN_LEVEL_FOR_SIEGE_REGISTRATION;
    public static int MAX_ALLY_SIZE;
    public static int SIEGE_CLAN_REPUTATION_REWARD;
    public static int SIEGE_CLAN_REPUTATION_DEFEND_REWARD;
    public static int SIEGE_CLAN_REPUTATION_DEFEND_CONSUME;
    public static int ROYAL_SUBUNIT_CRP_PRICE;
    public static int KNIGHT_SUBUNIT_CRP_PRICE;
    public static boolean ALLOW_MANOR;
    public static int MANOR_REFRESH_TIME;
    public static int MANOR_REFRESH_MIN;
    public static int MANOR_APPROVE_TIME;
    public static int MANOR_APPROVE_MIN;
    public static int MANOR_MAINTENANCE_PERIOD;
    public static double EVENT_CofferOfShadowsPriceRate;
    public static int[] EVENT_CofferOfShadowsNpcId;
    public static double EVENT_APIL_FOOLS_DROP_CHANCE;
    public static int EVENT_LastHeroItemID;
    public static double EVENT_LastHeroItemCOUNT;
    public static int EVENT_LastHeroTime;
    public static boolean EVENT_LastHeroRate;
    public static double EVENT_LastHeroItemCOUNTFinal;
    public static boolean EVENT_LastHeroRateFinal;
    public static int EVENT_LastHeroChanceToStart;
    public static boolean EX_ONE_DAY_REWARD;
    public static int EVENT_TvTItemID;
    public static double EVENT_TvTItemCOUNT;
    public static int EVENT_TvTTime;
    public static boolean EVENT_TvT_rate;
    public static int EVENT_TvTChanceToStart;
    public static int EVENT_CtFItemID;
    public static double EVENT_CtFItemCOUNT;
    public static int EVENT_CtFTime;
    public static boolean EVENT_CtF_rate;
    public static int EVENT_CtFChanceToStart;
    public static String EVENT_DropEvent_Items;
    public static String EVENT_DropEvent_PartyItems;
    public static boolean EVENT_DropEvent_PartyItems_Distribute_Random;
    public static boolean EVENT_DropEvent_Rate;
    public static int Event_DropEvent_Level_Penalty;
    public static boolean Event_DropEvent_Check_Hwid;
    public static String Event_DropEvent_Time_Period;
    public static double EVENT_TFH_POLLEN_CHANCE;
    public static double EVENT_GLITTMEDAL_NORMAL_CHANCE;
    public static double EVENT_GLITTMEDAL_GLIT_CHANCE;
    public static int[] EVENT_L2DAY_LETTER_NPC_ID;
    public static double EVENT_CHANGE_OF_HEART_CHANCE;
    public static double EVENT_CHRISTMAS_CHANCE;
    public static double EVENT_TRICK_OF_TRANS_CHANCE;
    public static double EVENT_MARCH8_DROP_CHANCE;
    public static double EVENT_MARCH8_PRICE_RATE;
    public static int GVG_REWARD_ID;
    public static long GVG_REWARD_AMOUNT;
    public static int[] EVENT_StraightHands_Items;
    public static String[] EVENT_FinderHostageStartTime;
    public static int EVENT_FINDER_REWARD_ID;
    public static int EVENT_FINDER_ITEM_COUNT;
    public static List<Location> EVENT_FINDER_LOCATIONS;
    public static long EVENT_FINDER_CAPTURE_TIME;
    public static int EVENT_Attendance_ResetTime;
    public static int EVENT_Attendance_MinLevel;
    public static List<Pair<Integer, Long>> EVENT_Attendance_Rewards;
    public static List<Pair<Integer, Long>> EVENT_Attendance_Rewards_For_Premium;
    public static int[] EVENT_Attendance_Highlights;
    public static int EVENT_Attendance_InGameTime;
    public static boolean EVENT_Attendance_Looped;
    public static boolean EVENT_Attendance_Global;
    public static boolean EVENT_Attendance_OnEnterWorld;
    public static boolean EVENT_Attendance_Voice_Command;
    public static String EVENT_GVG_START_TIME;
    public static int EVENT_GVG_MIN_LEVEL;
    public static int EVENT_GVG_MAX_LEVEL;
    public static int EVENT_GVG_GROUPS_LIMIT;
    public static int EVENT_GVG_MIN_PARTY_SIZE;
    public static long EVENT_GVG_REG_TIME;
    public static int EVENT_PUMPKIN_GHOST_ID;
    public static int[] EVENT_SKOOLDIE_REWARDER;
    public static int EVENT_PUMPKIN_DELAY;
    public static int EVENT_PUMPKIN_GHOST_SHOW_TIME;
    public static int EVENT_SKOOLDIE_TIME;
    public static int EVENT_HALLOWEEN_CANDY;
    public static int EVENT_HALLOWEEN_CANDY_ITEM_COUNT_NEEDED;
    public static int EVENT_HALLOWEEN_TOY_CHEST_REWARD_AMOUNT;
    public static int EVENT_HALLOWEEN_TOY_CHEST;
    public static int EVENT_PUMPKIN_DROP_CHANCE;
    public static int[] EVENT_PUMPKIN_DROP_ITEMS;
    public static boolean PVP_EVENTS_RESTRICT_ENCHANT;
    public static int PVP_EVENTS_RESTRICT_ENCHANT_ARMOR_LEVEL;
    public static int PVP_EVENTS_RESTRICT_ENCHANT_WEAPON_MAGE;
    public static int PVP_EVENTS_RESTRICT_ENCHANT_WEAPON_PHYS;
    public static int PVP_EVENTS_RESTRICT_ENCHANT_ACCESSORY;
    public static long SAVING_SNOWMEN_CAPTURE_TIME;
    public static long SAVING_SNOWMEN_EVENT_INTERVAL;
    public static long SAVING_SNOWMEN_SHOUT_INTERVAL;
    public static long SAVING_SNOWMEN_THOMAS_EAT_DELAY;
    public static long SAVING_SNOWMEN_SANTA_SAY_INTERVAL;
    public static long SAVING_SNOWMEN_ACTION_SPAWN_INTERVAL;
    public static List<Location> SAVING_SNOWMEN_LOCATIONS;
    public static int[] SAVING_SNOWMAN_EVENT_DROP_ID;
    public static int[] SAVING_SNOWMAN_EVENT_DROP_COUNT;
    public static double SAVING_SNOWMAN_EVENT_DROP_CHANCE;
    public static int SAVING_SNOWMAN_SNOWMAN_LITTLE_GIFT;
    public static int SAVING_SNOWMAN_SNOWMAN_GIFT_FROM_SANTA;
    public static long SAVING_SNOWMAN_BUFF_DELAY;
    public static int SAVING_SNOWMAN_BUFF_SKILL_ID;
    public static int SAVING_SNOWMAN_EVENT_MANAGER_ID;
    public static int SAVING_SNOWMAN_EVENT_FLYING_SANTA_ID;
    public static int SAVING_SNOWMAN_EVENT_SNOWMAN_ID;
    public static int SAVING_SNOWMAN_EVENT_THOMAS_ID;
    public static int EVENT_EXCALIBUR_WEAPON_ID;
    public static int EVENT_EXCALIBUR_WEAPON_PRICE_ITEM_ID;
    public static long EVENT_EXCALIBUR_WEAPON_PRICE_ITEM_AMOUNT;
    public static int EVENT_EXCALIBUR_SCROLL_ID;
    public static int EVENT_EXCALIBUR_TEMPORAL_SCROLL_PRICE;
    public static long EVENT_EXCALIBUR_TEMPORAL_SCROLL_REUSE;
    public static int EVENT_EXCALIBUR_ONE_SCROLL_PRICE_PRICE;
    public static int EVENT_EXCALIBUR_TEN_SCROLL_PRICE_PRICE;
    public static int EVENT_EXCALIBUR_MIN_ENCHANT_LEVEL_REWARD;
    public static Map<Integer, List<Triple<Integer, Long, Double>>> EVENT_EXCALIBUR_REWARD;
    public static Set<String> ENCHANT_LIMIT_ZONE_NAMES;
    public static int ENCHANT_LIMIT_ZONE_ARMOR_LEVEL;
    public static int ENCHANT_LIMIT_ZONE_WEAPON_MAGE;
    public static int ENCHANT_LIMIT_ZONE_WEAPON_PHYS;
    public static int ENCHANT_LIMIT_ZONE_ACCESSORY;
    public static boolean SERVICES_TOP_CLANS_STATISTIC;
    public static int SERVICES_TOP_CLANS_STATISTIC_LIST;
    public static Map<Integer, Integer> EVENT_CLAN_CASTLE_POINTS;
    public static int EVENT_CLAN_HERO_POINTS;
    public static int EVENT_CLAN_ACADEMY_POINTS;
    public static int EVENT_CLAN_WAR_POINTS;
    public static Map<Integer, Integer> EVENT_CLAN_RAID_BOSS_POINTS;
    public static boolean SERVICES_TOP_CLANS_STATISTIC_ANNOUNCE;
    public static boolean SERVICES_NO_TRADE_ONLY_OFFLINE;
    public static double SERVICES_TRADE_TAX;
    public static double SERVICES_OFFSHORE_TRADE_TAX;
    public static boolean SERVICES_OFFSHORE_NO_CASTLE_TAX;
    public static boolean SERVICES_TRADE_TAX_ONLY_OFFLINE;
    public static boolean SERVICES_TRADE_ONLY_FAR;
    public static int SERVICES_TRADE_RADIUS;
    public static int SERVICES_TRADE_MIN_LEVEL;
    public static boolean SERVICES_CLANSKILL_SELL_ENABLED;
    public static int SERVICES_CLAN_SKILL_SELL_ITEM;
    public static int SERVICES_CLAN_SKILL_SELL_PRICE;
    public static int SERVICES_CLANSKIL_SELL_MIN_LEVEL;
    public static boolean SERVICES_ENABLE_NO_CARRIER;
    public static int SERVICES_NO_CARRIER_DEFAULT_TIME;
    public static int SERVICES_NO_CARRIER_MAX_TIME;
    public static int SERVICES_NO_CARRIER_MIN_TIME;
    public static int SERVICES_CLAN_MAX_SELL_LEVEL;
    public static boolean SERVICES_HPACP_ENABLE;
    public static boolean SERVICES_HPACP_WORK_IN_PEACE_ZONE;
    public static int SERVICES_HPACP_MIN_PERCENT;
    public static int SERVICES_HPACP_MAX_PERCENT;
    public static int SERVICES_HPACP_DEF_PERCENT;
    public static int[] SERVICES_HPACP_POTIONS_ITEM_IDS;
    public static boolean SERVICES_HPACP_AVAILABLE_ONLY_PREMIUM;
    public static boolean ENABLE_ACP_ON_CHARACTER_CREATE;
    public static boolean SERVICES_ONLINE_COMMAND_ENABLE;
    public static double SERVICE_COMMAND_MULTIPLIER;
    public static int[] SERVICE_OPEN_BOX_ITEMS_ID;
    public static String[] SERVICE_OPEN_BOX_COMMAND_NAME;
    public static boolean SERVICES_BANKING_ENABLED;
    public static int SERVICES_DEPOSIT_ITEM_ID_NEEDED;
    public static int SERVICES_DEPOSIT_ITEM_COUNT_NEEDED;
    public static int SERVICES_DEPOSIT_ITEM_ID_GIVED;
    public static int SERVICES_DEPOSIT_ITEM_COUNT_GIVED;
    public static int SERVICES_WITHDRAW_ITEM_ID_NEEDED;
    public static int SERVICES_WITHDRAW_ITEM_COUNT_NEEDED;
    public static int SERVICES_WITHDRAW_ITEM_ID_GIVED;
    public static int SERVICES_WITHDRAW_ITEM_COUNT_GIVED;
    public static boolean SERVICES_CLAN_SUMMON_COMMAND_ENABLE;
    public static int SERVICES_CLAN_SUMMON_COMMAND_SELL_ITEM;
    public static int SERVICES_CLAN_SUMMON_COMMAND_SELL_PRICE;
    public static int SERVICES_CLAN_SUMMON_COMMAND_SUMMON_CRYSTAL_COUNT;
    public static long REUSE_DELAY_FOR_CLAN_SUMMON;
    public static boolean SERVICES_WHOIAM_COMMAND_ENABLE;
    public static boolean SERVICES_CPACP_ENABLE;
    public static int SERVICES_CPACP_MIN_PERCENT;
    public static int SERVICES_CPACP_MAX_PERCENT;
    public static int SERVICES_CPACP_DEF_PERCENT;
    public static int[] SERVICES_CPACP_POTIONS_ITEM_IDS;
    public static boolean SERVICES_MPACP_ENABLE;
    public static int SERVICES_MPACP_MIN_PERCENT;
    public static int SERVICES_MPACP_MAX_PERCENT;
    public static int SERVICES_MPACP_DEF_PERCENT;
    public static int[] SERVICES_MPACP_POTIONS_ITEM_IDS;
    public static int[] SERVICES_BOSS_STATUS_ADDITIONAL_IDS;
    public static boolean SERVICES_BOSS_STATUS_ENABLE;
    public static String SERVICES_BOSS_STATUS_FORMAT;
    public static int[] SERVICES_RAID_STATUS_ADDITIONAL_IDS;
    public static boolean SERVICES_RAID_STATUS_ENABLE;
    public static String SERVICES_RAID_STATUS_FORMAT;
    public static int SERVICES_RAID_STATUS_BOSS_PER_PAGE;
    public static boolean SERVICES_PVP_PK_STATISTIC;
    public static long PVP_PK_STAT_CACHE_UPDATE_INTERVAL;
    public static int PVP_PK_STAT_RECORD_LIMIT;
    public static boolean SERVICE_AUTO_ANNOUNCE;
    public static boolean ALT_SHOW_SERVER_TIME;
    public static boolean ALT_OPEN_CLOAK_SLOT;
    public static boolean ALT_ALLOW_GLOW_ARMOR_SET;
    public static boolean ALT_VITALITY_ENABLED;
    public static double ALT_VITALITY_RATE;
    public static double ALT_VITALITY_CONSUME_RATE;
    public static int ALT_VITALITY_RAID_BONUS;
    public static int ALT_VITALITY_POINTS_PER_MINUTE;
    public static int[] ALT_VITALITY_LEVELS;
    public static int GEO_X_FIRST;
    public static int GEO_Y_FIRST;
    public static int GEO_X_LAST;
    public static int GEO_Y_LAST;
    public static String GEOFILES_PATTERN;
    public static boolean ALLOW_GEODATA;
    public static boolean ALLOW_FALL_FROM_WALLS;
    public static boolean ALLOW_KEYBOARD_MOVE;
    public static boolean COMPACT_GEO;
    public static int CLIENT_Z_SHIFT;
    public static int MAX_Z_DIFF;
    public static int MIN_LAYER_HEIGHT;
    public static int PATHFIND_BOOST;
    public static boolean PATHFIND_DIAGONAL;
    public static boolean PATH_CLEAN;
    public static int PATHFIND_MAX_Z_DIFF;
    public static long PATHFIND_MAX_TIME;
    public static String PATHFIND_BUFFERS;
    public static boolean ALLOW_PAWN_PATHFIND;
    public static boolean GEODATA_DEBUG;
    public static boolean DEBUG;
    public static int WEAR_DELAY;
    public static int WEAING_D_GRADE_PRICE;
    public static int WEAING_C_GRADE_PRICE;
    public static int WEAING_B_GRADE_PRICE;
    public static int WEAING_A_GRADE_PRICE;
    public static int WEAING_S_GRADE_PRICE;
    public static int WEAING_NON_GRADE_PRICE;
    public static boolean GOODS_INVENTORY_ENABLED;
    public static boolean AUTH_SERVER_GM_ONLY;
    public static boolean AUTH_SERVER_BRACKETS;
    public static boolean AUTH_SERVER_IS_PVP;
    public static int AUTH_SERVER_AGE_LIMIT;
    public static int AUTH_SERVER_SERVER_TYPE;
    public static LocalDateTime OPEN_SERVER_TIME;
    public static List<Integer> OPEN_SERVER_ALLOWED_CHARS;
    public static boolean USE_SECOND_PASSWORD_AUTH;
    public static long SECOND_AUTH_BLOCK_TIME;
    public static int SECOND_AUTH_MAX_TRYS;
    public static int SECOND_AUTH_MIN_LENG;
    public static int SECOND_AUTH_MAX_LENG;
    public static int REC_FLUSH_HOUR;
    public static int REC_FLUSH_MINUTE;
    public static boolean OLY_ENABLED;
    public static boolean OLY_SPECTATION_ALLOWED;
    public static boolean OLY_PARTICIPANT_TYPE_SELECTION;
    public static boolean NPC_OLYMPIAD_GAME_ANNOUNCE;
    public static boolean ANNOUNCE_OLYMPIAD_GAME_END;
    public static boolean OLY_RECALC_NEW_SEASON;
    public static boolean OLY_RESTRICT_HWID;
    public static boolean OLY_RESTRICT_IP;
    public static boolean OLY_RESTORE_HPCPMP_ON_START_MATCH;
    public static long OLYMPIAD_COMPETITION_TIME;
    public static int OLYMPIAD_STADIUM_TELEPORT_DELAY;
    public static int OLY_MAX_SPECTATORS_PER_STADIUM;
    public static int[] OLY_RESTRICT_CLASS_IDS;
    public static OlySeasonTimeCalcMode OLY_SEASON_TIME_CALC_MODE;
    public static String OLY_SEASON_START_TIME;
    public static String OLY_SEASON_END_TIME;
    public static String OLY_COMPETITION_START_TIME;
    public static String OLY_COMPETITION_END_TIME;
    public static int OLY_COMPETITION_CUSTOM_START_TIME;
    public static int OLY_COMPETITION_CUSTOM_END_TIME;
    public static String OLY_BONUS_TIME;
    public static String OLY_NOMINATE_TIME;
    public static boolean OLY_STATISTIC_PAST_PERIOD;
    public static Set<Integer> ALT_OLY_COMPETITION_DAYS;
    public static int OLY_MIN_HERO_COMPS;
    public static int OLY_MIN_HERO_WIN;
    public static int OLY_MIN_NOBLE_COMPS;
    public static int OLY_SEASON_START_POINTS;
    public static int[] OLY_POINTS_SETTLEMENT;
    public static TIntObjectHashMap<TIntIntHashMap> OLY_BUFFS;
    public static int OLY_HERO_POINT_BONUS;
    public static int OLY_DEFAULT_POINTS;
    public static int OLY_WBONUS_POINTS;
    public static int OLY_HERO_REWARD_RITEMID;
    public static int[] OLY_VICTORY_RITEMID;
    public static int[] OLY_VICTORY_CFREE_RITEMCNT;
    public static int[] OLY_VICTORY_CBASE_RITEMCNT;
    public static int[] OLY_VICTORY_3TEAM_RITEMCNT;
    public static int[] OLY_VICTORY_RITEMID_CHANCE;
    public static int[] OLY_HERO_CUSTOM_REWARD_ID;
    public static int[] OLY_HERO_CUSTOM_REWARD_AMOUNT;
    public static int[] OLY_HERO_CUSTOM_REWARD_CHANCE;
    public static boolean OLY_PAID_REGISTRATION_FOR_CLASS_FREE;
    public static int OLY_PAID_REGISTRATION_FOR_CLASS_FREE_ITEM_ID;
    public static int OLY_PAID_REGISTRATION_FOR_CLASS_FREE_ITEM_COUNT;
    public static boolean OLY_PAID_REGISTRATION_FOR_CLASS_INDIVIDUAL;
    public static int OLY_PAID_REGISTRATION_FOR_CLASS_INDIVIDUAL_ITEM_ID;
    public static int OLY_PAID_REGISTRATION_FOR_CLASS_INDIVIDUAL_ITEM_COUNT;
    public static boolean OLY_PAID_REGISTRATION_FOR_TEAM_CLASS_FREE;
    public static int OLY_PAID_REGISTRATION_FOR_TEAM_CLASS_FREE_ITEM_ID;
    public static int OLY_PAID_REGISTRATION_FOR_TEAM_CLASS_FREE_ITEM_COUNT;
    public static double OLY_LOOSE_POINTS_MUL;
    public static int OLY_ITEMS_SETTLEMENT_PER_POINT;
    public static int OLY_MAX_TOTAL_MATCHES;
    public static int OLY_CF_MATCHES;
    public static int OLY_CB_MATCHES;
    public static int OLY_TB_MATCHES;
    public static int OLY_MIN_CF_START;
    public static int OLY_MIN_CB_START;
    public static int OLY_MIN_TB_START;
    public static int OLY_LIMIT_ENCHANT_STAT_LEVEL_ARMOR;
    public static int OLY_LIMIT_ENCHANT_STAT_LEVEL_WEAPON_MAGE;
    public static int OLY_LIMIT_ENCHANT_STAT_LEVEL_WEAPON_PHYS;
    public static int OLY_LIMIT_ENCHANT_STAT_LEVEL_ACCESSORY;
    public static boolean OLYMPIAD_WINNER_ANNOUCE;
    public static String OLYMPIAD_WINNER_MESSAGE;
    public static String OLYMPIAD_WINNER_REWARDS;
    public static boolean OLYMPIAD_REMOVE_SKILL_REUSE_ON_STADIUM;
    public static boolean OLYMPIAD_REMOVE_SKILL_REUSE_FROM_STADIUM;
    public static boolean ADD_CLAN_REPUTATION_POINT_ON_HERO_GAIN;
    public static int AMOUNT_CLAN_REPUTATION_POINT_ON_HERO_GAIN;
    public static boolean OLY_REMOVE_FORCE_BUFFS;
    public static int[] OLY_RESTRICTED_SKILL_IDS;
    public static boolean OLY_REMOVE_AUTOSHOTS;
    public static boolean OLYMPIAD_NEW_STADIUMS;
    public static Map<Integer, QuestRates> QUEST_RATES;

    public static void loadServerConfig() {
        ExProperties exProperties = Config.load(CONFIGURATION_FILE);
        GAME_SERVER_LOGIN_HOST = exProperties.getProperty("LoginHost", "127.0.0.1");
        GAME_SERVER_LOGIN_PORT = exProperties.getProperty("LoginPort", 9013);
        GAME_SERVER_LOGIN_CRYPT = exProperties.getProperty("LoginUseCrypt", true);
        AUTH_SERVER_AGE_LIMIT = exProperties.getProperty("ServerAgeLimit", 0);
        AUTH_SERVER_GM_ONLY = exProperties.getProperty("ServerGMOnly", false);
        AUTH_SERVER_BRACKETS = exProperties.getProperty("ServerBrackets", false);
        AUTH_SERVER_IS_PVP = exProperties.getProperty("PvPServer", false);
        USE_SECOND_PASSWORD_AUTH = exProperties.getProperty("UseSecondPasswordAuth", false);
        SECOND_AUTH_BLOCK_TIME = exProperties.getProperty("SecondAuthBlockTime", 28800);
        SECOND_AUTH_MAX_TRYS = exProperties.getProperty("SecondAuthBlockMaxTry", 5);
        SECOND_AUTH_MIN_LENG = exProperties.getProperty("SecondAuthPinMinLength", 6);
        SECOND_AUTH_MAX_LENG = exProperties.getProperty("SecondAuthPinMaxLength", 8);
        for (String object2 : exProperties.getProperty("ServerType", ArrayUtils.EMPTY_STRING_ARRAY)) {
            if (object2.trim().isEmpty()) continue;
            ServerType serverType = ServerType.valueOf(object2.toUpperCase());
            AUTH_SERVER_SERVER_TYPE |= serverType.getMask();
        }
        INTERNAL_HOSTNAME = exProperties.getProperty("InternalHostname", "*");
        EXTERNAL_HOSTNAME = exProperties.getProperty("ExternalHostname", "*");
        REQUEST_ID = exProperties.getProperty("RequestServerID", 0);
        ACCEPT_ALTERNATE_ID = exProperties.getProperty("AcceptAlternateID", true);
        GAMESERVER_HOSTNAME = exProperties.getProperty("GameserverHostname");
        PORTS_GAME = exProperties.getProperty("GameserverPort", new int[]{7777});
        HAPROXY_PORT_GAME = exProperties.getProperty("HAProxyGameserverPort", -1);
        REJECT_INVALID_CONNECTIONS = exProperties.getProperty("RejectInvalidConnections", true);
        VALID_IPS_LIST.addAll(Arrays.asList(exProperties.getProperty("ValidIPAddresses", ArrayUtils.EMPTY_STRING_ARRAY)));
        EVERYBODY_HAS_ADMIN_RIGHTS = exProperties.getProperty("EverybodyHasAdminRights", false);
        HIDE_GM_STATUS = exProperties.getProperty("HideGMStatus", false);
        SHOW_GM_LOGIN = exProperties.getProperty("ShowGMLogin", true);
        SAVE_GM_EFFECTS = exProperties.getProperty("SaveGMEffects", false);
        CNAME_TEMPLATE = exProperties.getProperty("CnameTemplate", "[A-Za-z0-9\u0410-\u042f\u0430-\u044f]{2,16}");
        CLAN_NAME_TEMPLATE = exProperties.getProperty("ClanNameTemplate", "[A-Za-z0-9\u0410-\u042f\u0430-\u044f]{3,16}");
        CLAN_TITLE_TEMPLATE = exProperties.getProperty("ClanTitleTemplate", "[A-Za-z0-9\u0410-\u042f\u0430-\u044f \\p{Punct}]{1,16}");
        ALLY_NAME_TEMPLATE = exProperties.getProperty("AllyNameTemplate", "[A-Za-z0-9\u0410-\u042f\u0430-\u044f]{3,16}");
        CNAME_FORBIDDEN_PATTERN = exProperties.getProperty("ForbiddenCharName", "(adm)|(admin)");
        CNAME_FORBIDDEN_NAMES = exProperties.getProperty("ForbiddenCharNames", ArrayUtils.EMPTY_STRING_ARRAY);
        GLOBAL_SHOUT = exProperties.getProperty("GlobalShout", false);
        GLOBAL_SHOUT_MIN_LEVEL = exProperties.getProperty("GlobalShoutMinLevel", 40);
        GLOBAL_SHOUT_MIN_PVP_COUNT = exProperties.getProperty("GlobalShoutMinPvPCount", 25);
        GLOBAL_TRADE_CHAT = exProperties.getProperty("GlobalTradeChat", false);
        GLOBAL_TRADE_CHAT_MIN_LEVEL = exProperties.getProperty("GlobalTradeChatMinLevel", 40);
        GLOBAL_TRADE_MIN_PVP_COUNT = exProperties.getProperty("GlobalTradeChatMinPvPCount", 25);
        CHAT_RANGE = exProperties.getProperty("ChatRange", 1250);
        SHOUT_OFFSET = exProperties.getProperty("ShoutOffset", 0);
        ENABLE_WORLD_CHAT = exProperties.getProperty("WorldChat", true);
        WORLD_CHAT_MESSAGE_COUNT = exProperties.getProperty("WorldChatMsgCountPerDay", 10);
        WORLD_CHAT_MIN_LEVEL = exProperties.getProperty("WorldChatMinLevel", 20);
        WORLD_CHAT_FOR_PREMIUM_ONLY = exProperties.getProperty("WorldChatForPremiumOnly", false);
        WORLD_CHAT_INTERVAL = TimeUnit.SECONDS.toMillis(exProperties.getProperty("WorldChatUseInterval", 20L));
        ENABLE_CLAN_ENTRY = exProperties.getProperty("ClanEntry", true);
        ENABLE_CLAN_ENTRY_PLAYER_LOCK_TIME = exProperties.getProperty("PlayerApplicationPenaltyTime", 5);
        ENABLE_CLAN_ENTRY_CLAN_LOCK_TIME = exProperties.getProperty("ClanApplicationPenaltyTime", 5);
        ENABLE_PRIME_SHOP = exProperties.getProperty("PrimeShopEnabled", false);
        ENABLE_PRIME_SHOP_REWARD_COIN_TAB = exProperties.getProperty("PrimeShopEnableRewardCoinTab", false);
        PRIME_SHOP_VIP_POINT_ITEM_ID = exProperties.getProperty("PrimeShopGamePointItemId", 29520);
        PRIME_SHOP_SILVER_ITEM_ID = exProperties.getProperty("PrimeShopSilverCoinItemId", 29983);
        PRIME_SHOP_GOLD_ITEM_ID = exProperties.getProperty("PrimeShopGoldCoinItemId", 29984);
        PRIME_SHOP_VIP_SYSTEM_ENABLED = exProperties.getProperty("PrimeShopVipEnabled", false);
        PRIME_SHOP_VIP_SYSTEM_MAX_LEVEL = exProperties.getProperty("PrimeShopVipMaxLevel", 7);
        PRIME_SHOP_PURCHASING_ADD_VIP_POINTS = exProperties.getProperty("PrimeShopPurchasingAddVipPoints", true);
        PRIME_SHOP_PURCHASING_ADD_VIP_COEFFICIENT = exProperties.getProperty("PrimeShopPurchasingAddVipPointsCoefficient", 1.0);
        LOG_CHAT = exProperties.getProperty("LogChat", false);
        LOG_GM = exProperties.getProperty("LogGMActions", false);
        LOG_ITEM = exProperties.getProperty("LogItems", false);
        LOG_SERVICES = exProperties.getProperty("LogServices", false);
        RATE_XP = exProperties.getProperty("RateXp", 1.0);
        RATE_SP = exProperties.getProperty("RateSp", 1.0);
        RATE_QUESTS_REWARD = exProperties.getProperty("RateQuestsReward", 1.0);
        RATE_QUESTS_REWARD_EXP_SP = exProperties.getProperty("RateQuestsRewardExpSp", 1.0);
        RATE_QUESTS_ADENA_REWARD = exProperties.getProperty("RateQuestsAdenaReward", 1.0);
        RATE_QUESTS_DROP = exProperties.getProperty("RateQuestsDrop", 1.0);
        RATE_CLAN_REP_SCORE = exProperties.getProperty("RateClanRepScore", 1.0);
        RATE_CLAN_REP_SCORE_MAX_AFFECTED = exProperties.getProperty("RateClanRepScoreMaxAffected", 2);
        RATE_DROP_ADENA = exProperties.getProperty("RateDropAdena", 1.0);
        RATE_DROP_ITEMS = exProperties.getProperty("RateDropItems", 1.0);
        RATE_DROP_HERBS = exProperties.getProperty("RateDropHerbs", 1.0);
        ALLOW_TREASURE_BOX = exProperties.getProperty("TreasureBox", true);
        RATE_DROP_RAIDBOSS = exProperties.getProperty("RateRaidBoss", 1.0);
        RATE_RAIDBOSS_XP = exProperties.getProperty("RateExpRaidBoss", RATE_XP);
        RATE_RAIDBOSS_SP = exProperties.getProperty("RateSpRaidBoss", RATE_SP);
        RATE_DROP_SPOIL = exProperties.getProperty("RateDropSpoil", 1.0);
        RATE_DROP_SEAL_STONES = exProperties.getProperty("RateDropSealStones", 1.0);
        MAXIMUM_CONTRIBUTION_SEAL_STONES = exProperties.getProperty("SealStonesMaximumContribution", 1000000L);
        ITEMS_MAX_AMMOUNT = exProperties.getProperty("LimitItemsAmount", 25600000000L);
        ALT_MULTI_DROP = exProperties.getProperty("AltMultiDrop", true);
        NO_RATE_ITEMS = exProperties.getProperty("NoRateItemIds", new int[]{6660, 6662, 6661, 6659, 6656, 6658, 8191, 6657});
        NO_DROP_ITEMS = exProperties.getProperty("NoDropItems", new int[0]);
        NO_DROP_ITEMS_FOR_SWEEP = exProperties.getProperty("NoDropItemsForSweep", new int[0]);
        ALLOW_ONLY_THESE_DROP_ITEMS_ID = exProperties.getProperty("DropOnlyTheseItemsId", new int[0]);
        NO_RATE_EQUIPMENT = exProperties.getProperty("NoRateEquipment", true);
        NO_RATE_KEY_MATERIAL = exProperties.getProperty("NoRateKeyMaterial", true);
        NO_RATE_RECIPES = exProperties.getProperty("NoRateRecipes", true);
        RATE_DROP_SIEGE_GUARD = exProperties.getProperty("RateSiegeGuard", 1.0);
        RATE_MANOR = exProperties.getProperty("RateManor", 1.0);
        RATE_FISH_DROP_COUNT = exProperties.getProperty("RateFishDropCount", 1.0);
        RATE_PARTY_MIN = exProperties.getProperty("RatePartyMin", false);
        RATE_MOB_SPAWN = exProperties.getProperty("RateMobSpawn", 1);
        RATE_MOB_SPAWN_MIN_LEVEL = exProperties.getProperty("RateMobMinLevel", 1);
        RATE_MOB_SPAWN_MAX_LEVEL = exProperties.getProperty("RateMobMaxLevel", 100);
        RATE_RAID_REGEN = exProperties.getProperty("RateRaidRegen", 1.0);
        RATE_RAID_DEFENSE = exProperties.getProperty("RateRaidDefense", 1.0);
        RATE_RAID_ATTACK = exProperties.getProperty("RateRaidAttack", 1.0);
        RATE_MOD_MIN_LEVEL_LIMIT = exProperties.getProperty("RateRaidMinLevelLimit", 1);
        RATE_MOD_MAX_LEVEL_LIMIT = exProperties.getProperty("RateRaidMaxLevelLimit", 90);
        RATE_EPIC_DEFENSE = exProperties.getProperty("RateEpicDefense", 1.0);
        RATE_EPIC_ATTACK = exProperties.getProperty("RateEpicAttack", 1.0);
        RAID_MAX_LEVEL_DIFF = exProperties.getProperty("RaidMaxLevelDiff", 8);
        PARALIZE_ON_RAID_DIFF = exProperties.getProperty("ParalizeOnRaidLevelDiff", true);
        SKILL_COST_RATE = exProperties.getProperty("SkillCostRate", 1);
        AUTODESTROY_ITEM_AFTER = exProperties.getProperty("AutoDestroyDroppedItemAfter", 0);
        AUTODESTROY_PLAYER_ITEM_AFTER = exProperties.getProperty("AutoDestroyPlayerDroppedItemAfter", 0);
        DELETE_DAYS = exProperties.getProperty("DeleteCharAfterDays", 7.0);
        PURGE_BYPASS_TASK_FREQUENCY = exProperties.getProperty("PurgeTaskFrequency", 60);
        try {
            DATAPACK_ROOT = new File(exProperties.getProperty("DatapackRoot", ".")).getCanonicalFile();
        }
        catch (IOException iOException) {
            am.error("", (Throwable)iOException);
        }
        ALLOW_MAIL = exProperties.getProperty("AllowMail", true);
        MAIL_MIN_LEVEL_SENDER = exProperties.getProperty("SendMailMinLevel", 0);
        MAIL_SEND_COUNT_PER_DAY = exProperties.getProperty("SendMailCountPerDay", 240);
        MAIL_SEND_INTERVAL = TimeUnit.SECONDS.toMillis(exProperties.getProperty("SendMailInterval", 20L));
        MAIL_SEND_PRICE = exProperties.getProperty("SendMailPrice", 100);
        MAIL_SEND_COUNT_MULTIPLIER = exProperties.getProperty("SendMailPricePerItem", 1000);
        MAIL_INBOX_LIMIT = exProperties.getProperty("SendMailInboxLimit", 240);
        MAIL_ALLOW_AT_PEACE_ZONE = exProperties.getProperty("SendMailManageAtPeaceOnly", true);
        MAIL_CACHE_SIZE = exProperties.getProperty("MailCacheSize", 20000);
        ALLOW_DISCARDITEM = exProperties.getProperty("AllowDiscardItem", true);
        ALLOW_WAREHOUSE = exProperties.getProperty("AllowWarehouse", true);
        ALLOW_WATER = exProperties.getProperty("AllowWater", true);
        ALLOW_CURSED_WEAPONS = exProperties.getProperty("AllowCursedWeapons", false);
        DROP_CURSED_WEAPONS_ON_KICK = exProperties.getProperty("DropCursedWeaponsOnKick", false);
        DROP_CURSED_WEAPONS_ON_LOGOUT = exProperties.getProperty("DropCursedWeaponsOnLogout", false);
        MIN_PROTOCOL_REVISION = exProperties.getProperty("MinProtocolRevision", 740);
        MAX_PROTOCOL_REVISION = exProperties.getProperty("MaxProtocolRevision", 770);
        MIN_NPC_ANIMATION = exProperties.getProperty("MinNPCAnimation", 5);
        MAX_NPC_ANIMATION = exProperties.getProperty("MaxNPCAnimation", 90);
        SERVER_SIDE_NPC_NAME = exProperties.getProperty("ServerSideNpcName", false);
        SERVER_SIDE_NPC_TITLE = exProperties.getProperty("ServerSideNpcTitle", false);
        SERVER_SIDE_MONSTER_LEVEL_TITLE = exProperties.getProperty("ServerSideMonsterTitleLvl", false);
        SERVER_SIDE_AGRO_MONSTER_RED_NAME = exProperties.getProperty("AgroMonsterNameRed", false);
        AUTOSAVE = exProperties.getProperty("Autosave", true);
        AUTOSAVE_ITEMS = exProperties.getProperty("AutosaveWithItems", false);
        MAXIMUM_ONLINE_USERS = exProperties.getProperty("MaximumOnlineUsers", 3000);
        DATABASE_HOST = exProperties.getProperty("DatabaseHost", "127.0.0.1");
        DATABASE_PORT = exProperties.getProperty("DatabasePort", 3306);
        DATABASE_NAME = exProperties.getProperty("DatabaseName", "l2db");
        DATABASE_USER = exProperties.getProperty("DatabaseUser", "root");
        DATABASE_PASS = exProperties.getProperty("DatabasePassword", "");
        DATABASE_MAX_CONN = exProperties.getProperty("DatabaseMaxConnections", 64);
        DATABASE_TIMEOUT = exProperties.getProperty("DatabaseConnectionTimeout", 30);
        DATABASE_EX_PROPERTIES = exProperties.getProperty("DatabaseExProperties", "");
        DATABASE_EX_STRUCTURE_MANAGER = exProperties.getProperty("DatabaseStructureManager", true);
        DATABASE_DUMP_TABLES = exProperties.getProperty("DatabaseDumpTables", ArrayUtils.EMPTY_STRING_ARRAY);
        DATABASE_DUMP_FILENAME_FORMAT = exProperties.getProperty("DatabaseDumpFilenameFormat", "backup/%table_name%_%date%_%time%.sql");
        DATABASE_DUMP_ZIP_OUT_FILENAME_FORMAT = exProperties.getProperty("DatabaseDumpZipOutFilenameFormat", "backup/%date%_%time%.zip");
        USER_INFO_INTERVAL = exProperties.getProperty("UserInfoInterval", 100L);
        BROADCAST_STATS_INTERVAL = exProperties.getProperty("BroadcastStatsInterval", true);
        BROADCAST_CHAR_INFO_INTERVAL = exProperties.getProperty("BroadcastCharInfoInterval", 100L);
        EFFECT_TASK_MANAGER_COUNT = exProperties.getProperty("EffectTaskManagers", 2);
        SCHEDULED_THREAD_POOL_SIZE = exProperties.getProperty("ScheduledThreadPoolSize", NCPUS * 4);
        EXECUTOR_THREAD_POOL_SIZE = exProperties.getProperty("ExecutorThreadPoolSize", NCPUS * 2);
        ENABLE_RUNNABLE_STATS = exProperties.getProperty("EnableRunnableStats", false);
        Config.SELECTOR_CONFIG.SLEEP_TIME = exProperties.getProperty("SelectorSleepTime", 10L);
        Config.SELECTOR_CONFIG.INTEREST_DELAY = exProperties.getProperty("InterestDelay", 30L);
        Config.SELECTOR_CONFIG.MAX_SEND_PER_PASS = exProperties.getProperty("MaxSendPerPass", 32);
        Config.SELECTOR_CONFIG.READ_BUFFER_SIZE = exProperties.getProperty("ReadBufferSize", 65536);
        Config.SELECTOR_CONFIG.WRITE_BUFFER_SIZE = exProperties.getProperty("WriteBufferSize", 131072);
        Config.SELECTOR_CONFIG.HELPER_BUFFER_COUNT = exProperties.getProperty("BufferPoolSize", 64);
        CHAT_MESSAGE_MAX_LEN = exProperties.getProperty("ChatMessageLimit", 1000);
        StringTokenizer stringTokenizer = new StringTokenizer(exProperties.getProperty("ChatBanChannels", "ALL,SHOUT,TELL,TRADE,HERO_VOICE"), ",");
        ArrayList<ChatType> arrayList = new ArrayList<ChatType>();
        while (stringTokenizer.hasMoreTokens()) {
            arrayList.add(ChatType.valueOf(stringTokenizer.nextToken()));
        }
        BAN_CHANNEL_LIST = arrayList.toArray(new ChatType[arrayList.size()]);
        BANCHAT_ANNOUNCE = exProperties.getProperty("BANCHAT_ANNOUNCE", true);
        BANCHAT_ANNOUNCE_FOR_ALL_WORLD = exProperties.getProperty("BANCHAT_ANNOUNCE_FOR_ALL_WORLD", true);
        BANCHAT_ANNOUNCE_NICK = exProperties.getProperty("BANCHAT_ANNOUNCE_NICK", true);
        NOSPAM_FILTER_HWID = exProperties.getProperty("NoSpamFilterHwid", false);
        NOSPAM_FILTER_IP = exProperties.getProperty("NoSpamFilterIp", false);
        DEFAULT_LANG = exProperties.getProperty("DefaultLang", "ru");
        RESTART_AT_TIME = exProperties.getProperty("AutoRestartAt", "0 5 * * *");
        SHIFT_BY = exProperties.getProperty("HShift", 12);
        SHIFT_BY_Z = exProperties.getProperty("VShift", 11);
        MAP_MIN_Z = exProperties.getProperty("MapMinZ", Short.MIN_VALUE);
        MAP_MAX_Z = exProperties.getProperty("MapMaxZ", Short.MAX_VALUE);
        MOVE_TASK_QUANTUM_PC = exProperties.getProperty("MoveTaskQuantumForPC", 400);
        MOVE_TASK_QUANTUM_NPC = exProperties.getProperty("MoveTaskQuantumForNPC", 800);
        MOVE_OFFLOAD_MTL_PC = exProperties.getProperty("OffloadMTLForPC", false);
        MOVE_TO_TOWN_ON_FAIL_VALIDATE = exProperties.getProperty("MoveToTownOnFailValidate", true);
        MOVE_TO_TOWN_ON_FAIL_TRY_COUNT = exProperties.getProperty("MoveToTownOnFailValidateTryCount", 10);
        ATTACK_PACKET_DELAY = exProperties.getProperty("AttackPacketDelay", 300);
        ATTACK_END_DELAY = exProperties.getProperty("AttackEndTime", 50);
        USE_ITEM_PACKET_DELAY = exProperties.getProperty("UseItemPacketDelay", 200);
        UNKNOWN_PACKET_AMOUNT_LIMIT = exProperties.getProperty("UnknownPacketAmountLimit", 10);
        FAIL_PACKET_AMOUNT_LIMIT = exProperties.getProperty("FailPacketAmountLimit", 10);
        RESEND_MSU_AFTER_TELEPORT = exProperties.getProperty("ResendMSUPacketAfterTeleport", -1);
        ITEM_LINK_CACHE_SIZE = exProperties.getProperty("ItemLinkCacheSize", 10000);
        DAMAGE_FROM_FALLING = exProperties.getProperty("DamageFromFalling", true);
        DONTLOADSPAWN = exProperties.getProperty("StartWithoutSpawn", false);
        DONTLOADQUEST = exProperties.getProperty("StartWithoutQuest", false);
        IGNORE_QUESTS = exProperties.getProperty("DisableQuestId", ArrayUtils.EMPTY_INT_ARRAY);
        MAX_REFLECTIONS_COUNT = exProperties.getProperty("MaxReflectionsCount", 300);
        COMMUNITYBOARD_ENABLED = exProperties.getProperty("AllowCommunityBoard", true);
        BBS_PEACE_ONLY = exProperties.getProperty("AllowCommunityBoardAtPeaceZoneOnly", false);
        BBS_DEFAULT = exProperties.getProperty("BBSDefault", "_bbshome");
        HTM_CACHE_MODE = exProperties.getProperty("HtmCacheMode", 1);
        ALLOW_MULILOGIN = exProperties.getProperty("AllowMultilogin", false);
        PFILTER_ENABLED = exProperties.getProperty("EnablePacketFilter", true);
        ALT_CG_MODULE = exProperties.getProperty("AltClientGuard", "NONE");
        DEBUG = exProperties.getProperty("ServerDebug", false);
        String string2 = exProperties.getProperty("ServerOpenTime", "");
        if (!string2.isEmpty()) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm,dd.MM.yyyy");
            OPEN_SERVER_TIME = LocalDateTime.parse(string2, dateTimeFormatter);
        }
        String string3 = exProperties.getProperty("ServerOpenAllowedObjIds", "");
        OPEN_SERVER_ALLOWED_CHARS = Arrays.stream(string3.split(",")).filter(string -> !string.isEmpty()).map(Integer::parseInt).collect(Collectors.toList());
    }

    public static void loadTelnetConfig() {
        ExProperties exProperties = Config.load(TELNET_CONFIGURATION_FILE);
        IS_TELNET_ENABLED = exProperties.getProperty("EnableTelnet", false);
        TELNET_DEFAULT_ENCODING = exProperties.getProperty("TelnetEncoding", "UTF-8");
        TELNET_PORT = exProperties.getProperty("Port", 7000);
        TELNET_HOSTNAME = exProperties.getProperty("BindAddress", "127.0.0.1");
        TELNET_PASSWORD = exProperties.getProperty("Password", "");
        TELNET_SIMULTANEOUS_CONNECTIONS = exProperties.getProperty("MaxSimultaneousConnections", 1);
        TELNET_ALLOWED_IPS.addAll(Arrays.asList(exProperties.getProperty("AllowedConnectIP", new String[]{"127.0.0.1"})));
    }

    public static void loadResidenceConfig() {
        ExProperties exProperties = Config.load(RESIDENCE_CONFIG_FILE);
        CH_BID_CURRENCY_ITEM_ID = exProperties.getProperty("ClanHallCurrencyItemId", 57);
        RESIDENCE_LEASE_FUNC_MULTIPLIER = exProperties.getProperty("ResidenceLeaseFuncMultiplier", 1.0);
        RESIDENCE_CH_ALL_BUFFS = exProperties.getProperty("ResidenceAllBuffsButton", false);
        RESIDENCE_CH_BUFFS_APPLY_ON_PET = exProperties.getProperty("ResidenceAllBuffsPets", false);
        CLNHALL_REWARD_CYCLE = exProperties.getProperty("ClanHallSellingCycle", 168);
        CLAN_MIN_LEVEL_FOR_BID = exProperties.getProperty("ClanMinLevelForBid", 2);
        CH_DISPLAY_IDS = new TIntHashSet();
        CH_DISPLAY_IDS.addAll(exProperties.getProperty("ClanHallSellListIds", new int[]{22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61}));
        int[] nArray = exProperties.getProperty("CastleValidationDate", new int[]{2, 4, 2003});
        CASTLE_VALIDATION_DATE = Calendar.getInstance();
        CASTLE_VALIDATION_DATE.set(5, nArray[0]);
        CASTLE_VALIDATION_DATE.set(2, nArray[1] - 1);
        CASTLE_VALIDATION_DATE.set(1, nArray[2]);
        CASTLE_VALIDATION_DATE.set(11, 0);
        CASTLE_VALIDATION_DATE.set(12, 0);
        CASTLE_VALIDATION_DATE.set(13, 0);
        CASTLE_VALIDATION_DATE.set(14, 0);
    }

    public static void loadOtherConfig() {
        String[] stringArray;
        ExProperties exProperties = Config.load(OTHER_CONFIG_FILE);
        DEEPBLUE_DROP_RULES = exProperties.getProperty("UseDeepBlueDropRules", true);
        DEEPBLUE_DROP_MAXDIFF = exProperties.getProperty("DeepBlueDropMaxDiff", 8);
        DEEPBLUE_DROP_RAID_MAXDIFF = exProperties.getProperty("DeepBlueDropRaidMaxDiff", 2);
        DEEPRED_DROP_MAXDIFF = exProperties.getProperty("DeepRedDropMaxDiff", 80);
        DEEPRED_DROP_RAID_MAXDIFF = exProperties.getProperty("DeepRedDropRaidMaxDiff", 80);
        EXP_SP_DIFF_LIMIT = exProperties.getProperty("ExpSpMaxDiffLimit", 0);
        SWIMING_SPEED = exProperties.getProperty("SwimmingSpeedTemplate", 50);
        THRESHOLD_LEVEL_DIFF = exProperties.getProperty("ExpSpThresholdLevelDiff", 5);
        PARTY_PENALTY_EXP_SP_MAX_LEVEL = exProperties.getProperty("ExpSpPartyPenaltyLevelMax", 10);
        PARTY_PENALTY_EXP_SP_MIN_LEVEL = exProperties.getProperty("ExpSpPartyPenaltyLevelMin", 14);
        INVENTORY_MAXIMUM_NO_DWARF = exProperties.getProperty("MaximumSlotsForNoDwarf", 80);
        INVENTORY_MAXIMUM_DWARF = exProperties.getProperty("MaximumSlotsForDwarf", 100);
        DWARF_AUTOMATICALLY_CRYSTALLIZE_ON_ITEM_DELETE = exProperties.getProperty("DwarfAutoCrystallizeOnItemDelete", false);
        CRYSTALLIZE_BONUS_AT_ENCHANT = exProperties.getProperty("CrystallizeBonusAtEnchantLevel", true);
        INVENTORY_MAXIMUM_GM = exProperties.getProperty("MaximumSlotsForGMPlayer", 250);
        INVENTORY_MAXIMUM_PERMISSIBLE_LIMIT = exProperties.getProperty("MaximumSlotPermissibleLimit", 250);
        QUEST_INVENTORY_MAXIMUM = exProperties.getProperty("MaximumSlotsForQuests", 100);
        MULTISELL_SIZE = exProperties.getProperty("MultisellPageSize", 10);
        WAREHOUSE_SLOTS_NO_DWARF = exProperties.getProperty("BaseWarehouseSlotsForNoDwarf", 100);
        WAREHOUSE_SLOTS_DWARF = exProperties.getProperty("BaseWarehouseSlotsForDwarf", 120);
        WAREHOUSE_SLOTS_CLAN = exProperties.getProperty("MaximumWarehouseSlotsForClan", 200);
        FREIGHT_SLOTS = exProperties.getProperty("MaximumFreightSlots", 10);
        FREIGHT_SEND_TYPE = exProperties.getProperty("FreightSendType", true);
        FOLLOW_ARRIVE_DISTANCE = exProperties.getProperty("FollowArriveDistance", 150);
        SEND_LINEAGE2_WELCOME_MESSAGE = exProperties.getProperty("SendLineage2WelcomeMessage", true);
        SEND_SSQ_WELCOME_MESSAGE = exProperties.getProperty("SendSSQWelcomeMessage", true);
        SEND_SSQ_SEAL_STATUS = exProperties.getProperty("SendSSQSealValidationStatus", true);
        SEND_EFFECT_LIST_ON_TARGET_NPC = exProperties.getProperty("SendEffectListOnNpcTarget", true);
        SEND_EFFECT_LIST_ON_TARGET = exProperties.getProperty("SendEffectListOnPlayerTarget", true);
        SEND_BUFF_LIST_ON_TARGET = exProperties.getProperty("SendBuffListOnPlayerTarget", true);
        SEND_DEBUFF_LIST_ON_TARGET = exProperties.getProperty("SendDebuffListOnPlayerTarget", true);
        ENCHANT_MAX = exProperties.getProperty("EnchantMax", 20);
        ARMOR_OVERENCHANT_HPBONUS_LIMIT = exProperties.getProperty("ArmorOverEnchantHPBonusLimit", 10) - 3;
        SHOW_ENCHANT_EFFECT_RESULT = exProperties.getProperty("ShowEnchantEffectResult", false);
        SHOW_ENCHANT_EFFECT_RESULT_EVERY_NEXT_SUCCESS = exProperties.getProperty("ShowEffectResultEveryNextSuccess", false);
        WEAPON_FIRST_ENCHANT_EFFECT_LEVEL = exProperties.getProperty("WeaponFirstEnchantEffectLevel", 7);
        WEAPON_SECOND_ENCHANT_EFFECT_LEVEL = exProperties.getProperty("WeaponSecondEnchantEffectLevel", 15);
        ARMOR_ENCHANT_EFFECT_LEVEL = exProperties.getProperty("ArmorEnchantEffectLevel", 6);
        ENCHANT_ATTRIBUTE_STONE_CHANCE = exProperties.getProperty("EnchantAttributeChance", 50);
        ENCHANT_ATTRIBUTE_CRYSTAL_CHANCE = exProperties.getProperty("EnchantAttributeCrystalChance", 30);
        ENCHANT_ATTRIBUTE_ENERGY_CHANCE = exProperties.getProperty("EnchantAttributeEnergyChance", 100);
        REGEN_SIT_WAIT = exProperties.getProperty("RegenSitWait", false);
        STARTING_ADENA = exProperties.getProperty("StartingAdena", 0);
        STARTING_ITEMS_MAGE = exProperties.getProperty("StartingMageItems", ArrayUtils.EMPTY_INT_ARRAY);
        STARTING_ITEMS_FIGHTER = exProperties.getProperty("StartingFighterItems", ArrayUtils.EMPTY_INT_ARRAY);
        if (STARTING_ITEMS_MAGE.length % 2 != 0 || STARTING_ITEMS_FIGHTER.length % 2 != 0) {
            throw new RuntimeException("ERROR: CONFIG StartingItems WRONG SIZE");
        }
        UNSTUCK_SKILL = exProperties.getProperty("UnstuckSkill", false);
        BLOCK_BUFF_SKILL = exProperties.getProperty("BlockBuffSkill", false);
        BLOCK_BUFF_EXCLUDE = new HashIntSet((IntCollection)new CArrayIntList(exProperties.getProperty("BlockBuffExcludeSkill", new int[]{1323})));
        NOBLES_BUFF_SKILL = exProperties.getProperty("NoblesBuffSkill", false);
        ADDITIONALS_SKILLS = exProperties.getProperty("AdditionalDefaultSkills", ArrayUtils.EMPTY_INT_ARRAY);
        if (ADDITIONALS_SKILLS.length % 2 != 0) {
            throw new RuntimeException("ERROR: CONFIG AdditionalDefaultSkills WRONG SIZE");
        }
        RESPAWN_RESTORE_CP = exProperties.getProperty("RespawnRestoreCP", 0.0) / 100.0;
        RESPAWN_RESTORE_HP = exProperties.getProperty("RespawnRestoreHP", 65.0) / 100.0;
        RESPAWN_RESTORE_MP = exProperties.getProperty("RespawnRestoreMP", 0.0) / 100.0;
        MAX_PVTSTORE_SLOTS_DWARF = exProperties.getProperty("MaxPvtStoreSlotsDwarf", 5);
        MAX_PVTSTORE_SLOTS_DWARF_FIRST_JOB = exProperties.getProperty("MaxPvtStoreSlotsDwarfOnFirstProf", 3);
        MAX_PVTSTORE_SLOTS_OTHER = exProperties.getProperty("MaxPvtStoreSlotsOther", 4);
        MAX_PVTSTORE_SLOTS_OTHER_FIRST_JOB = exProperties.getProperty("MaxPvtStoreSlotsOtherOnFirstProf", 2);
        MAX_PVTCRAFT_SLOTS = exProperties.getProperty("MaxPvtManufactureSlots", 20);
        PRIVATE_BUY_MATCH_ENCHANT = exProperties.getProperty("AltPrivateBuyMatchEnchant", true);
        SENDSTATUS_TRADE_JUST_OFFLINE = exProperties.getProperty("SendStatusTradeJustOffline", false);
        SENDSTATUS_TRADE_MOD = exProperties.getProperty("SendStatusTradeMod", 1.0);
        MUL_PLAYERS_ONLINE = exProperties.getProperty("MulOnlinePlayers", 1);
        ANNOUNCE_MAMMON_SPAWN = exProperties.getProperty("AnnounceMammonSpawn", true);
        GM_NAME_COLOUR = Integer.decode("0x" + exProperties.getProperty("GMNameColour", "FFFFFF"));
        GM_HERO_AURA = exProperties.getProperty("GMHeroAura", false);
        NORMAL_NAME_COLOUR = Integer.decode("0x" + exProperties.getProperty("NormalNameColour", "FFFFFF"));
        CLANLEADER_NAME_COLOUR = Integer.decode("0x" + exProperties.getProperty("ClanleaderNameColour", "FFFFFF"));
        SHOW_HTML_WELCOME = exProperties.getProperty("ShowHTMLWelcome", false);
        OTHER_MAGE_BUFF_ON_CHAR_CREATE = new ArrayList<Pair<Integer, Integer>>();
        for (String string : StringUtils.split((String)exProperties.getProperty("StartMageBuff", "1303-1"), (String)";,")) {
            stringArray = StringUtils.split((String)string, (String)"-:");
            OTHER_MAGE_BUFF_ON_CHAR_CREATE.add((Pair<Integer, Integer>)Pair.of((Object)Integer.parseInt(stringArray[0]), (Object)Integer.parseInt(stringArray[1])));
        }
        OTHER_WARRIOR_BUFF_ON_CHAR_CREATE = new ArrayList<Pair<Integer, Integer>>();
        for (String string : StringUtils.split((String)exProperties.getProperty("StartWarriorBuff", "1086-1"), (String)";,")) {
            stringArray = StringUtils.split((String)string, (String)"-:");
            OTHER_WARRIOR_BUFF_ON_CHAR_CREATE.add((Pair<Integer, Integer>)Pair.of((Object)Integer.parseInt(stringArray[0]), (Object)Integer.parseInt(stringArray[1])));
        }
        OTHER_BUFF_ON_CHAR_CREATE_TIME_MODIFIER = exProperties.getProperty("StartBuffEffectTime", 0L) * 1000L;
    }

    public static void loadClanConfig() {
        ExProperties exProperties = Config.load(CLAN_CONFIG_FILE);
        CLAN_LEAVE_TIME_PERNALTY = exProperties.getProperty("ClanLeaveTimePenalty", 24L) * 3600000L;
        ALLY_LEAVE_TIME_PENALTY = exProperties.getProperty("AllyLeaveTimePenalty", 24L) * 3600000L;
        EXPELLED_MEMBER_PENALTY = exProperties.getProperty("ExpelledClanPenalty", 24L) * 3600000L;
        LEAVED_ALLY_PENALTY = exProperties.getProperty("AllyLeavedPenalty", 24L) * 3600000L;
        DISSOLVED_ALLY_PENALTY = exProperties.getProperty("DissolvedAllyPenalty", 24L) * 3600000L;
        NEW_CLAN_CREATE_PENALTY = exProperties.getProperty("DissolvedClanPenalty", 240L) * 3600000L;
        CLAN_DISBAND_TIME = exProperties.getProperty("ClanDisbandTimeDelay", 48L) * 3600000L;
        CLAN_DISBAND_PENALTY = exProperties.getProperty("ClanDisbandCancelPenalty", 168L) * 3600000L;
        CLAN_INIT_LEVEL = exProperties.getProperty("ClanInitLevel", 0);
        CLAN_REPUTATION_BONUS_ON_CREATE = exProperties.getProperty("ClanReputationBonusOnCreate", 0);
        FULL_CLAN_SKILLS_ON_CREATE = exProperties.getProperty("FullClanSkillsOnCreate", false);
        CHARACTER_MIN_LEVEL_FOR_CLAN_CREATE = exProperties.getProperty("CharacterMinLevelForClanCreate", 10);
        CLAN_LEADER_CHANGE_METHOD = exProperties.getProperty("ClanLeaderChangeMethod", false);
        LIMIT_CLAN_LEVEL0 = exProperties.getProperty("MainClanMembersLimitOnLevel0", 10);
        LIMIT_CLAN_LEVEL1 = exProperties.getProperty("MainClanMembersLimitOnLevel1", 15);
        LIMIT_CLAN_LEVEL2 = exProperties.getProperty("MainClanMembersLimitOnLevel2", 20);
        LIMIT_CLAN_LEVEL3 = exProperties.getProperty("MainClanMembersLimitOnLevel3", 30);
        LIMIT_CLAN_LEVEL_4_AND_HIGH = exProperties.getProperty("MainClanMembersLimitOnLevel4", 40);
        LIMIT_CLAN_HIGH_UNITS = exProperties.getProperty("SubUnitRoyalLimitMembers", 20);
        LIMIT_CLAN_LOW_UNITS = exProperties.getProperty("SubUnitKnightLimitMembers", 10);
        LIMIT_CLAN_ACADEMY = exProperties.getProperty("ClanAcademyLimit", 20);
        MIN_CLAN_LEVEL_FOR_DECLARED_WAR = exProperties.getProperty("MinClanLevelForDeclaredWar", 3);
        MIN_CLAN_MEMBER_FOR_DECLARED_WAR = exProperties.getProperty("MinClanMembersForDeclaredWar", 15);
        CRP_REWARD_ON_WAR_KILL_OVER_LEVEL = exProperties.getProperty("OnWarKillCRPRewardOverLevel", 2);
        CRP_REWARD_ON_WAR_KILL = exProperties.getProperty("OnWarKillCRPReward", 1);
        CLAN_FIRST_LEVEL_SP = exProperties.getProperty("FirstClanLevelUpSP", 20000);
        CLAN_FIRST_LEVEL_ADENA = exProperties.getProperty("FirstClanLevelUpAdena", 650000);
        CLAN_SECOND_LEVEL_SP = exProperties.getProperty("SecondClanLevelUpSP", 100000);
        CLAN_SECOND_LEVEL_ADENA = exProperties.getProperty("SecondClanLevelUpAdena", 2500000);
        CLAN_THIRD_LEVEL_SP = exProperties.getProperty("ThirdClanLevelUpSP", 350000);
        CLAN_FOUR_LEVEL_SP = exProperties.getProperty("FourClanLevelUpSP", 1000000);
        CLAN_FIVE_LEVEL_SP = exProperties.getProperty("FiveClanLevelUpSP", 2500000);
        CLAN_SIX_LEVEL_CLAN_REPUTATION = exProperties.getProperty("SixClanLevelUpReputationCount", 10000);
        CLAN_SIX_LEVEL_CLAN_MEMBER_COUNT = exProperties.getProperty("SixClanLevelUpMemberCount", 30);
        CLAN_SEVEN_LEVEL_CLAN_REPUTATION = exProperties.getProperty("SevenClanLevelUpReputationCount", 20000);
        CLAN_SEVEN_LEVEL_CLAN_MEMBER_COUNT = exProperties.getProperty("SevenClanLevelUpMemberCount", 50);
        CLAN_EIGHT_LEVEL_CLAN_REPUTATION = exProperties.getProperty("EightClanLevelUpReputationCount", 40000);
        CLAN_EIGHT_LEVEL_CLAN_MEMBER_COUNT = exProperties.getProperty("EightClanLevelUpMemberCount", 80);
        ROYAL_SUBUNIT_CRP_PRICE = exProperties.getProperty("RoyalSubunitCrpPrice", 5000);
        KNIGHT_SUBUNIT_CRP_PRICE = exProperties.getProperty("KnightSubunitCrpPrice", 10000);
        ALLY_ALLOW_BUFF_DEBUFFS = exProperties.getProperty("AllyRelationLikeClan", false);
        ALLOW_TEMPORARILY_ALLY_ON_FIRST_SIEGE = exProperties.getProperty("AllowTemporarilyAllyOnFirstSiege", false);
        ALT_NPC_CLAN = exProperties.getProperty("NpcClanCrestDisplay", -1);
        CHECK_CLAN_RANK_ON_COMMAND_CHANNEL_CREATE = exProperties.getProperty("CheckClanRankForCommandChannelCreate", true);
        COMMAND_CHANNEL_PARY_COUNT_LIMIT = exProperties.getProperty("CommandChannelPartyCountLimit", 64);
        MIN_CLAN_LEVEL_FOR_REPUTATION = exProperties.getProperty("MinClanLevelForReputationReceiving", 5);
        MIN_CLAN_LEVEL_FOR_SIEGE_REGISTRATION = exProperties.getProperty("MinClanLevelForSiegeRegistration", 5);
        MAX_ALLY_SIZE = exProperties.getProperty("MaxAllianceSize", 3);
        SIEGE_CLAN_REPUTATION_REWARD = exProperties.getProperty("SiegeClanReputationReward", 1000);
        SIEGE_CLAN_REPUTATION_DEFEND_REWARD = exProperties.getProperty("DefendSiegeClanReputationReward", 1500);
        SIEGE_CLAN_REPUTATION_DEFEND_CONSUME = exProperties.getProperty("DefendSiegeClanReputationConsume", -1500);
    }

    public static void loadSpoilConfig() {
        ExProperties exProperties = Config.load(SPOIL_CONFIG_FILE);
        MINIMUM_SPOIL_RATE = exProperties.getProperty("MinimumPercentChanceOfSpoilSuccess", 1.0);
        MANOR_SOWING_BASIC_SUCCESS = exProperties.getProperty("BasePercentChanceOfSowingSuccess", 100.0);
        MANOR_SOWING_ALT_BASIC_SUCCESS = exProperties.getProperty("BasePercentChanceOfSowingAltSuccess", 10.0);
        MANOR_HARVESTING_BASIC_SUCCESS = exProperties.getProperty("BasePercentChanceOfHarvestingSuccess", 90.0);
        MANOR_HARVESTING_REWARD_RATE = exProperties.getProperty("HarvestingRewardRate", 1.0);
        MANOR_DIFF_PLAYER_TARGET = exProperties.getProperty("MinDiffPlayerMob", 5);
        MANOR_DIFF_PLAYER_TARGET_PENALTY = exProperties.getProperty("DiffPlayerMobPenalty", 5.0);
        MANOR_DIFF_SEED_TARGET = exProperties.getProperty("MinDiffSeedMob", 5);
        MANOR_DIFF_SEED_TARGET_PENALTY = exProperties.getProperty("DiffSeedMobPenalty", 5.0);
        ALLOW_MANOR = exProperties.getProperty("AllowManor", true);
        MANOR_REFRESH_TIME = exProperties.getProperty("AltManorRefreshTime", 20);
        MANOR_REFRESH_MIN = exProperties.getProperty("AltManorRefreshMin", 0);
        MANOR_APPROVE_TIME = exProperties.getProperty("AltManorApproveTime", 6);
        MANOR_APPROVE_MIN = exProperties.getProperty("AltManorApproveMin", 0);
        MANOR_MAINTENANCE_PERIOD = exProperties.getProperty("AltManorMaintenancePeriod", 360000);
        SPOIL_ITEMS_ID_FOR_RATE = exProperties.getProperty("AltSpoilItemsIdForRate", new int[0]);
        SPOIL_ITEMS_CHANCE_RATE = exProperties.getProperty("AltSpoilItemChanceRate", 1.0);
    }

    public static void loadExperienceConfig() {
        FileInputStream fileInputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            int n;
            File file = new File(EXPERIENCE_CONFIG_FILE);
            fileInputStream = new FileInputStream(file);
            inputStreamReader = new InputStreamReader((InputStream)fileInputStream, StandardCharsets.UTF_8);
            bufferedReader = new BufferedReader(inputStreamReader);
            int n2 = 0;
            HashMap<Integer, Long> hashMap = new HashMap<Integer, Long>();
            Object object = bufferedReader.readLine();
            while (object != null) {
                n = ((String)object).indexOf("//");
                if (n > -1) {
                    object = ((String)object).substring(0, n);
                }
                long l = Long.parseLong(((String)object).trim());
                hashMap.put(n2, l);
                object = bufferedReader.readLine();
                ++n2;
            }
            object = new long[n2];
            for (n = 0; n < n2; ++n) {
                object[n] = hashMap.getOrDefault(n, 0L);
            }
            EXPERIENCE = (long[])object;
        }
        catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
        finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            }
            catch (Exception exception) {}
        }
    }

    public static void loadFormulasConfig() {
        ExProperties exProperties = Config.load(FORMULAS_CONFIGURATION_FILE);
        SKILLS_CHANCE_MOD = exProperties.getProperty("SkillsChanceMod", 11.0);
        SKILLS_CHANCE_POW = exProperties.getProperty("SkillsChancePow", 0.5);
        SKILLS_CHANCE_MIN = exProperties.getProperty("SkillsChanceMin", 5.0);
        SKILLS_CHANCE_CAP = exProperties.getProperty("SkillsChanceCap", 95.0);
        SKILLS_CAST_TIME_MIN = exProperties.getProperty("SkillsCastTimeMin", 333);
        SKILLS_DISPEL_MOD_MAX = exProperties.getProperty("DispelMaxChance", 75);
        SKILLS_DISPEL_MOD_MIN = exProperties.getProperty("DispelMinChance", 25);
        SKILLS_BANE_MOD_MIN = exProperties.getProperty("BaneMinChance", 40);
        SKILLS_BANE_MOD_MAX = exProperties.getProperty("BaneMaxChance", 90);
        ALT_ABSORB_DAMAGE_MODIFIER = exProperties.getProperty("AbsorbDamageModifier", 1.0);
        RATE_OVERHIT = exProperties.getProperty("RateOverHitXp", 1.0);
        LIM_PATK = exProperties.getProperty("LimitPatk", 20000);
        LIM_MATK = exProperties.getProperty("LimitMAtk", 25000);
        LIM_PDEF = exProperties.getProperty("LimitPDef", 15000);
        LIM_MDEF = exProperties.getProperty("LimitMDef", 15000);
        LIM_PATK_SPD = exProperties.getProperty("LimitPatkSpd", 1500);
        LIM_MATK_SPD = exProperties.getProperty("LimitMatkSpd", 1999);
        LIM_CRIT_DAM = exProperties.getProperty("LimitCriticalDamage", 2000);
        LIM_CRIT = exProperties.getProperty("LimitCritical", 500);
        LIM_MCRIT = exProperties.getProperty("LimitMCritical", 20);
        MCRITICAL_CRIT_POWER = exProperties.getProperty("mCritDamagePower", 4.0);
        BASE_MAGE_CAST_SPEED = exProperties.getProperty("BaseMageCastSpeed", 333);
        BASE_WARRIOR_CAST_SPEED = exProperties.getProperty("BaseWarriorCastSpeed", 333);
        LIM_ACCURACY = exProperties.getProperty("LimitAccuracy", 200);
        LIM_EVASION = exProperties.getProperty("LimitEvasion", 200);
        LIM_MOVE = exProperties.getProperty("LimitMove", 250);
        LIM_MOVE_GM = exProperties.getProperty("LimitGmMove", 250);
        LIM_MAX_CP = exProperties.getProperty("LimitMaxCP", 100000);
        LIM_MAX_HP = exProperties.getProperty("LimitMaxHP", 40000);
        LIM_MAX_MP = exProperties.getProperty("LimitMaxMP", 40000);
        MCRITICAL_BASE_STAT = exProperties.getProperty("MCriticalBaseStat", 1);
        MDAM_CRIT_POSSIBLE = exProperties.getProperty("MDamCritPossibale", true);
        HEAL_CRIT_POSSIBLE = exProperties.getProperty("HealCritPossibale", false);
        ALT_DISABLE_MAGICFAILURES = exProperties.getProperty("DisableMagicFailures", false);
        MAGIC_FAIL_LEVEL_MOD = exProperties.getProperty("MagicFailuresLevelMod", 14);
        BLOW_RATE_CHANCE_LIMIT = exProperties.getProperty("LimitBlowRateChance", 10.0);
        BLOW_SKILLS_MAX_CHANCE_LIMIT = exProperties.getProperty("BlowSkillsMaxChance", 80.0);
        POLE_ATTACK_ANGLE = exProperties.getProperty("PoleAttackAngel", 180.0);
        LIMIT_HENNA_INT = exProperties.getProperty("HennaLimitINT", 5);
        LIMIT_HENNA_STR = exProperties.getProperty("HennaLimitSTR", 5);
        LIMIT_HENNA_MEN = exProperties.getProperty("HennaLimitMEN", 5);
        LIMIT_HENNA_CON = exProperties.getProperty("HennaLimitCON", 5);
        LIMIT_HENNA_WIT = exProperties.getProperty("HennaLimitWIT", 5);
        LIMIT_HENNA_DEX = exProperties.getProperty("HennaLimitDEX", 5);
        LIM_FAME = exProperties.getProperty("LimitFame", 50000);
        LIM_PC_BANG_POINTS = exProperties.getProperty("LimitPcBangPoints", 200000);
        ALT_NPC_PATK_MODIFIER = exProperties.getProperty("NpcPAtkModifier", 1.0);
        ALT_NPC_MATK_MODIFIER = exProperties.getProperty("NpcMAtkModifier", 1.0);
        ALT_NPC_MAXHP_MODIFIER = exProperties.getProperty("NpcMaxHpModifier", 1.58);
        ALT_NPC_MAXMP_MODIFIER = exProperties.getProperty("NpcMaxMpModifier", 1.11);
        ALT_NPC_PDEF_MODIFIER = exProperties.getProperty("NpcPDefModifier", 1.0);
        ALT_NPC_MDEF_MODIFIER = exProperties.getProperty("NpcMDefModifier", 1.0);
        ALT_NPC_SPD_MODIFIER = exProperties.getProperty("NpcRunSpdModifier", 1.0);
        ALT_NPC_CAST_SPD_MODIFIER = exProperties.getProperty("NpcCastSpdModifier", 1.0);
        ALT_NPC_MIN_LEVEL_MODIFIER = exProperties.getProperty("NpcMinLevelModifier", 1);
        ALT_NPC_MAX_LEVEL_MODIFIER = exProperties.getProperty("NpcMaxLevelModifier", 90);
        ALT_EPIC_BOSS_PATK_MODIFIER = exProperties.getProperty("EpicBossPAtkModifier", 1.0);
        ALT_EPIC_BOSS_MATK_MODIFIER = exProperties.getProperty("EpicBossMAtkModifier", 1.0);
        ALT_EPIC_BOSS_MAXHP_MODIFIER = exProperties.getProperty("EpicBossMaxHpModifier", 1.58);
        ALT_EPIC_BOSS_MAXMP_MODIFIER = exProperties.getProperty("EpicBossMaxMpModifier", 1.11);
        ALT_EPIC_BOSS_PDEF_MODIFIER = exProperties.getProperty("EpicBossPDefModifier", 1.0);
        ALT_EPIC_BOSS_MDEF_MODIFIER = exProperties.getProperty("EpicBossMDefModifier", 1.0);
        ALT_EPIC_BOSS_SPD_MODIFIER = exProperties.getProperty("EpicBossRunSpdModifier", 1.0);
        ALT_EPIC_BOSS_CAST_SPD_MODIFIER = exProperties.getProperty("EpicBossCastSpdModifier", 1.0);
        ALT_EPIC_BOSS_MIN_LEVEL_MODIFIER = exProperties.getProperty("EpicBossMinLevelModifier", 1);
        ALT_EPIC_BOSS_MAX_LEVEL_MODIFIER = exProperties.getProperty("EpicBossMaxLevelModifier", 90);
        ALT_RAID_BOSS_PATK_MODIFIER = exProperties.getProperty("RaidBossPAtkModifier", 1.0);
        ALT_RAID_BOSS_MATK_MODIFIER = exProperties.getProperty("RaidBossMAtkModifier", 1.0);
        ALT_RAID_BOSS_MAXHP_MODIFIER = exProperties.getProperty("RaidBossMaxHpModifier", 1.58);
        ALT_RAID_BOSS_MAXMP_MODIFIER = exProperties.getProperty("RaidBossMaxMpModifier", 1.11);
        ALT_RAID_BOSS_PDEF_MODIFIER = exProperties.getProperty("RaidBossPDefModifier", 1.0);
        ALT_RAID_BOSS_MDEF_MODIFIER = exProperties.getProperty("RaidBossMDefModifier", 1.0);
        ALT_RAID_BOSS_SPD_MODIFIER = exProperties.getProperty("RaidBossRunSpdModifier", 1.0);
        ALT_RAID_BOSS_CAST_SPD_MODIFIER = exProperties.getProperty("RaidBossCastSpdModifier", 1.0);
        ALT_RAID_BOSS_MIN_LEVEL_MODIFIER = exProperties.getProperty("RaidBossMinLevelModifier", 1);
        ALT_RAID_BOSS_MAX_LEVEL_MODIFIER = exProperties.getProperty("RaidBossMaxLevelModifier", 90);
        ALT_NPC_LIM_MCRIT = exProperties.getProperty("NpcLimitNpcMCritical", 20);
        ALT_POLE_DAMAGE_MODIFIER = exProperties.getProperty("PoleDamageModifier", 1.0);
        MIN_ATK_DELAY = exProperties.getProperty("MinAttackDelay", 333);
        AUTO_ATTACK_ON_WEAPON_CHANGE = exProperties.getProperty("StopAutoAttackOnWeaponChange", true);
        CALC_EFFECT_TIME_YIELD_AND_RESIST = exProperties.getProperty("CalcEffectTimeYieldAndResist", false);
        COUNTERATTACK_MAX_SKILL_RANGE = exProperties.getProperty("CounterAttackMaxSkillRange", 200);
        MUSIC_REUSE_TIME_BASED_ON_MATK_SPD = exProperties.getProperty("MusicSkillReuseBasedOnCastSpeed", true);
        MAGIC_REUSE_TIME_BASED_ON_MATK_SPD = exProperties.getProperty("MagicSkillReuseBasedOnCastSpeed", true);
        PHYSIC_REUSE_TIME_BASED_ON_ATK_SPD = exProperties.getProperty("PhysicSkillReuseBasedOnAttackSpeed", true);
    }

    public static void loadAltSettings() {
        int n;
        ExProperties exProperties = Config.load(ALT_SETTINGS_FILE);
        ALT_GAME_DELEVEL = exProperties.getProperty("Delevel", true);
        ALT_SAVE_UNSAVEABLE = exProperties.getProperty("AltSaveUnsaveable", false);
        ALT_SAVE_EFFECTS_REMAINING_TIME = exProperties.getProperty("AltSaveEffectsRemainingTime", 5);
        ALT_SHOW_REUSE_MSG = exProperties.getProperty("AltShowSkillReuseMessage", true);
        ALT_DELETE_SA_BUFFS = exProperties.getProperty("AltDeleteSABuffs", false);
        ALT_ALLOW_DISPEL_AT_OLY = exProperties.getProperty("AltAllowDispelEffectsOnOly", true);
        BUFF_STICK_FOR_ALL = exProperties.getProperty("AltBuffStickUse", false);
        AUTO_LOOT = exProperties.getProperty("AutoLoot", false);
        AUTO_LOOT_HERBS = exProperties.getProperty("AutoLootHerbs", false);
        AUTO_LOOT_ADENA = exProperties.getProperty("AutoLootAdena", false);
        AUTO_LOOT_INDIVIDUAL = exProperties.getProperty("AutoLootIndividual", false);
        AUTO_LOOT_FROM_RAIDS = exProperties.getProperty("AutoLootFromRaids", false);
        AUTO_LOOT_PK = exProperties.getProperty("AutoLootPK", false);
        AUTO_LOOT_EXCLUDE_ITEM_IDS = exProperties.getProperty("AutoLootExcludeItemIds", ArrayUtils.EMPTY_INT_ARRAY);
        AUTO_LOOT_MONEY_ITEM_IDS = exProperties.getProperty("AutoLootMoneyItemIds", new int[]{57});
        DISABLE_AUTO_LOOT_FOR_MONSTER_IDS = new TIntHashSet(exProperties.getProperty("AutoLootExcludeMobsId", ArrayUtils.EMPTY_INT_ARRAY));
        AUTO_LOOT_ONLY_FOR_PREMIUM = exProperties.getProperty("AutoLootOnlyForPremium", false);
        AUTO_LOOT_ONLY_WITH_IDS = exProperties.getProperty("AutoLootOnlyWithItems", ArrayUtils.EMPTY_INT_ARRAY);
        ALT_CAN_ATTACK_NPC_AT_PEACE_ZONE = exProperties.getProperty("AltCanAttackNpcAtPeaceZone", true);
        ALT_GAME_KARMA_PLAYER_CAN_SHOP = exProperties.getProperty("AltKarmaPlayerCanShop", false);
        ALT_GAME_CURSED_WEAPON_PLAYER_CAN_SHOP = exProperties.getProperty("AltCursedWeaponPlayerCanShop", false);
        XP_FREEZ_ONLY_FOR_PREMIUM = exProperties.getProperty("ExpFreezingLootOnlyForPremium", false);
        SAVING_SPS = exProperties.getProperty("SavingSpS", false);
        MANAHEAL_SPS_BONUS = exProperties.getProperty("ManahealSpSBonus", false);
        ALT_RAID_RESPAWN_MULTIPLIER = exProperties.getProperty("AltRaidRespawnMultiplier", 1.0);
        ALT_MOBS_RESPAWN_MULTIPLIER = exProperties.getProperty("AltMobsRespawnMultiplier", 1.0);
        ALT_RAID_BOSS_SPAWN_AND_TELEPORT = exProperties.getProperty("AltRaidBossTeleportAndAnnounce", false);
        ALT_RAID_BOSS_SPAWN_ANNOUNCE_IDS = exProperties.getProperty("AltRaidBossSpawnAnnounceIds", ArrayUtils.EMPTY_INT_ARRAY);
        ALT_RAID_BOSS_SPAWN_ANNOUNCE_DELAY = exProperties.getProperty("AltRaidBossSpawnAnnounceDelay", 5) * 60;
        ALT_RAID_BOSS_SPAWN_ANNOUNCE_RANDOM_DELAY = exProperties.getProperty("AltRaidBossSpawnAnnounceRandomDelay", 1) * 60;
        ALT_RAID_DEATH_ANNOUNCE_IDS = new TIntHashSet(exProperties.getProperty("AltRaidDeathAnnounceIds", ArrayUtils.EMPTY_INT_ARRAY));
        ALT_SEND_BOSS_SPAWN_INFO = exProperties.getProperty("AltSendBossInfoByServerPacket", true);
        ALT_ALLOW_DROP_AUGMENTED = exProperties.getProperty("AllowDropAugmented", false);
        ALT_ALLOW_TRADE_AUGMENTED = exProperties.getProperty("AllowTradeAugmented", false);
        ALT_ALLOW_TRADE_APPAREANCED = exProperties.getProperty("AllowTradeAppearancedItem", true);
        ALT_ALLOW_DROP_APPAREANCED = exProperties.getProperty("AllowDropAppearancedItem", true);
        ALT_ALLOW_REMOTE_SELL_ITEMS_TO_SHOP = exProperties.getProperty("AltAllowRemoteSellItems", false);
        ALT_ALLOW_REMOTE_USE_CARGO_BOX = exProperties.getProperty("AltAllowRemoteUseCargoBox", false);
        ALT_GAME_UNREGISTER_RECIPE = exProperties.getProperty("AltUnregisterRecipe", true);
        ALT_GAME_SHOW_DROPLIST = exProperties.getProperty("AltShowDroplist", true);
        ALT_GAME_SHOW_DROPLIST_TREASURE_CHEST = exProperties.getProperty("AltShowTreasureChestDroplist", false);
        ALLOW_NPC_SHIFTCLICK = exProperties.getProperty("AllowShiftClick", true);
        ALT_NPC_SHIFTCLICK_ITEM_COUNT = exProperties.getProperty("AltShowDropListItemCountPerPage", 15);
        ALT_GAME_SHOW_DROPLIST_WEAK_MOBS = exProperties.getProperty("AltShowWeakMobsDroplist", false);
        ALT_FULL_NPC_STATS_PAGE = exProperties.getProperty("AltFullStatsPage", false);
        ALT_GAME_SUBCLASS_WITHOUT_QUESTS = exProperties.getProperty("AltAllowSubClassWithoutQuest", false);
        ALT_GAME_SUBCLASS_NOT_CHECK_QUEST_234 = exProperties.getProperty("AltAllowSubClassNotCheckFatesWhisperQuest", false);
        ALT_FIRST_JOB_QUEST_EXTRA_REWARD = exProperties.getProperty("AltFirstJobQuestExtraReward", ArrayUtils.EMPTY_INT_ARRAY);
        ALT_SECOND_JOB_QUEST_EXTRA_REWARD = exProperties.getProperty("AltSecondJobQuestExtraReward", ArrayUtils.EMPTY_INT_ARRAY);
        ALT_THIRD_JOB_QUEST_EXTRA_REWARD = exProperties.getProperty("AltThirdJobQuestExtraReward", ArrayUtils.EMPTY_INT_ARRAY);
        if (ALT_FIRST_JOB_QUEST_EXTRA_REWARD.length % 2 != 0 || ALT_SECOND_JOB_QUEST_EXTRA_REWARD.length % 2 != 0 || ALT_THIRD_JOB_QUEST_EXTRA_REWARD.length % 2 != 0) {
            throw new RuntimeException("ERROR: CONFIG AltFirstJobQuestExtraReward or AltSecondJobQuestExtraReward or AltThirdJobQuestExtraReward WRONG SIZE");
        }
        ALT_SAGA_SUPER_CLASS_DROP_RATE = exProperties.getProperty("AltSagaSuperClassDropRate", 1);
        ALT_ALLIANCE_KETRA_RAID_KILL_CHECK = exProperties.getProperty("AltKetraAllianceKillingRaidsCheck", true);
        ALT_ALLIANCE_KETRA_MOBS_KILL_CHECK = exProperties.getProperty("AltKetraAllianceKillingMobsCheck", true);
        ALT_ALLIANCE_VARKA_RAID_KILL_CHECK = exProperties.getProperty("AltVarkaAllianceKillingRaidsCheck", true);
        ALT_ALLIANCE_VARKA_MOBS_KILL_CHECK = exProperties.getProperty("AltVarkaAllianceKillingMobsCheck", true);
        ALT_ALLOW_SUBCLASS_FOR_CUSTOM_ITEM = exProperties.getProperty("AltAllowSubClassForCustomItem", false);
        ALT_SUBCLASS_FOR_CUSTOM_ITEM_ID = exProperties.getProperty("AltSubClassForCustomItemId", ArrayUtils.EMPTY_INT_ARRAY);
        ALT_SUBCLASS_FOR_CUSTOM_ITEM_COUNT = exProperties.getProperty("AltSubClassForCustomItemCount", ArrayUtils.EMPTY_INT_ARRAY);
        ALTSUBCLASS_LIST_ALL = exProperties.getProperty("AltAllowShowAllSubclassesList", false);
        ALT_ALLOW_ALLCLASS_SKILLENCHANT = exProperties.getProperty("AltAllowAllNpcEnchantSkill", false);
        ALT_ALLOW_ALLCLASS_SKILL_LEARN = exProperties.getProperty("AltAllowAllNpcLearnSkill", false);
        ALTSUBCLASS_ALLOW_OVER_AND_WARSMITH_TO_ALL = exProperties.getProperty("AltAllowLearnOverlordAndWarsmithToAll", false);
        ALTSUBCLASS_ALLOW_FOR_ELF_TO_DARK_ELF = exProperties.getProperty("AltAllowLearnSubElfToDarkElf", false);
        ALT_GAME_LEVEL_TO_GET_SUBCLASS = exProperties.getProperty("AltLevelToGetSubclass", 75);
        ALT_LEVEL_AFTER_GET_SUBCLASS = exProperties.getProperty("AltLevelAfterGetSubClass", 40);
        ALT_NEW_CHARACTER_LEVEL = exProperties.getProperty("AltNewCharacterLevel", 0);
        ALT_GAME_BASE_SUB = exProperties.getProperty("AltSubLimit", 4);
        ALT_MAX_LEVEL = Math.min(exProperties.getProperty("AltMaxLevel", 80), Experience.LEVEL.length - 1);
        ALT_MAX_SUB_LEVEL = Math.min(exProperties.getProperty("AltMaxSubLevel", 80), Experience.LEVEL.length - 1);
        ALT_ALLOW_HERO_SKILLS_ON_SUB_CLASS = exProperties.getProperty("AltAllowHeroSkillsonSubClass", false);
        ALT_ALLOW_OTHERS_WITHDRAW_FROM_CLAN_WAREHOUSE = exProperties.getProperty("AltAllowOthersWithdrawFromClanWarehouse", false);
        ALT_ALLOW_CLAN_COMMAND_ONLY_FOR_CLAN_LEADER = exProperties.getProperty("AltAllowClanCommandOnlyForClanLeader", true);
        ALT_ALLOW_CLAN_COMMAND_ALLOW_WH = exProperties.getProperty("AltClanCommandAllowPrivToWH", false);
        ALT_ALLOW_MENU_COMMAND = exProperties.getProperty("AltAllowMenuCommand", true);
        ALT_ALLOW_RELOG_COMMAND = exProperties.getProperty("AltAllowRestartChatCommand", false);
        ALT_ALLOW_RELOG_AT_EPIC_ZONE = exProperties.getProperty("AltAllowRestartChatCommandAtEpicZone", false);
        ALT_ALLOW_HELP_COMMAND = exProperties.getProperty("AltAllowHelpCommand", true);
        ALT_ALLOW_MAMMON_SEARCH_COMMAND = exProperties.getProperty("AltAllowMammonSearchCommand", true);
        ALT_ALLOW_SERVICES_COMMAND = exProperties.getProperty("AltAllowServicesCommand", true);
        ALT_ALLOW_SERVER_INFO_COMMAND = exProperties.getProperty("AltAllowServerInfoCommand", true);
        ALT_ALLOW_JUMP_COMMAND = exProperties.getProperty("AltAllowJumpCommand", false);
        ALT_ALLOW_LANG_COMMAND = exProperties.getProperty("AltAllowChangeLangCommand", true);
        ALT_CHANGE_LANG_NAME_1 = exProperties.getProperty("AltChangeLangCommand1", "en");
        ALT_CHANGE_LANG_NAME_2 = exProperties.getProperty("AltChangeLangCommand2", "ru");
        ALT_ALLOW_PING_COMMAND = exProperties.getProperty("AltAllowPingCommand", false);
        ALT_SUBLASS_SKILL_TRANSFER = exProperties.getProperty("AltTransferSubSkillsToMain", false);
        ALT_ALLOW_CUSTOM_SKILL_LEARN = exProperties.getProperty("AltAllowCustomSkillLearnSystem", false);
        ALT_GAME_REQUIRE_CLAN_CASTLE = exProperties.getProperty("AltRequireClanCastle", false);
        ALT_GAME_REQUIRE_CASTLE_DAWN = exProperties.getProperty("AltRequireCastleDawn", true);
        ALT_GAME_ALLOW_ADENA_DAWN = exProperties.getProperty("AltAllowAdenaDawn", true);
        ALT_ADD_RECIPES = exProperties.getProperty("AltAddRecipes", 0);
        SS_ANNOUNCE_PERIOD = exProperties.getProperty("SSAnnouncePeriod", 0);
        PETITIONING_ALLOWED = exProperties.getProperty("PetitioningAllowed", true);
        CAN_PETITION_TO_OFFLINE_GM = exProperties.getProperty("CanMakePetitionToOfflineGM", false);
        MAX_PETITIONS_PER_PLAYER = exProperties.getProperty("MaxPetitionsPerPlayer", 5);
        MAX_PETITIONS_PENDING = exProperties.getProperty("MaxPetitionsPending", 25);
        AUTO_LEARN_SKILLS = exProperties.getProperty("AutoLearnSkills", false);
        AUTO_LEARN_FORGOTTEN_SKILLS = exProperties.getProperty("AutoLearnForgottenSkills", false);
        AUTO_LEARN_DIVINE_INSPIRATION = exProperties.getProperty("AutoLearnDivineInspiration", false);
        ENABLE_MAX_SKILL_TRAINING = exProperties.getProperty("EnableMaxAvailableLevelSkillLearn", false);
        ALT_SOCIAL_ACTION_REUSE = exProperties.getProperty("AltSocialActionReuse", false);
        ALT_DISABLE_SPELLBOOKS = exProperties.getProperty("AltDisableSpellbooks", false);
        ALT_WEAK_SKILL_LEARN = exProperties.getProperty("AltWeakSkillLearn", false);
        ALT_SIMPLE_SIGNS = exProperties.getProperty("SignsOptionsAtBlacksmith", false);
        ALT_SIMPLE_SIGNS_NPC_ID = exProperties.getProperty("SignsOptionsNpcId", new int[]{30300});
        ALT_TELE_TO_CATACOMBS = exProperties.getProperty("TeleToCatacombs", false);
        ALT_TELE_TO_CATACOMBS_NPC_ID = exProperties.getProperty("TeleToCatacombsNpcId", new int[]{31212, 31213, 31214, 31215, 31216, 31217, 31218, 31219, 31220, 31221, 31222, 31223, 31224, 31767, 31768, 32048});
        ALT_BS_CRYSTALLIZE = exProperties.getProperty("BSCrystallize", false);
        ALT_BS_CRYSTALLIZE_NPC_ID = exProperties.getProperty("BSCrystallizeNpcId", new int[]{30283, 30298, 30300, 30307, 30317, 30363, 30458, 30471, 30526, 30527, 30564, 30678, 30688, 30846, 30898, 31271, 31316, 31583, 31960, 31990});
        ALT_ALLOW_TATTOO = exProperties.getProperty("AllowTattoo", false);
        ALT_ALLOW_TATTOO_NPC_ID = exProperties.getProperty("TattooNpcId", new int[]{30098});
        ALT_ALLOW_CUSTOM_HERO = exProperties.getProperty("AltAllowCustomHero", false);
        ALT_ALLOW_CUSTOM_HERO_SKILLS = exProperties.getProperty("AltAllowCustomHeroSkills", false);
        ALT_ALLOW_CUSTOM_HERO_WEAPON_REWARD = exProperties.getProperty("AltAllowCustomHeroWeaponReward", true);
        ALT_BUFF_SLOT_SEPARATE = exProperties.getProperty("BuffSlotSeparate", false);
        ALT_BUFF_LIMIT = exProperties.getProperty("BuffLimit", 20);
        ALT_DEBUFF_LIMIT = exProperties.getProperty("DebuffLimit", 8);
        ALT_MUSIC_LIMIT = exProperties.getProperty("DanceSongLimit", 12);
        ALT_TRIGGER_LIMIT = exProperties.getProperty("AltTriggerLimit", 12);
        ALT_NPC_BUFFER_EFFECT_TIME = exProperties.getProperty("AltNpcBufferEffectTime", 0L) * 1000L;
        ALT_NPC_BUFFER_PREMIUM_EFFECT_MUL = exProperties.getProperty("AltNpcPremiumBufferEffectMul", 1.0);
        ALT_NPC_BUFFER_REUSE_DELAY = exProperties.getProperty("AltNpcBufferReuseBuffDelay", 300L);
        ALT_NPC_BUFFER_PREMIUM_HTML_PREFIX = exProperties.getProperty("AltNpcBufferPremiumHtmlPrefix", false);
        ALT_NPC_BUFFER_PREMIUM_ITEM_IDS = exProperties.getProperty("AltNpcBufferPremiumItemPrefix", ArrayUtils.EMPTY_INT_ARRAY);
        ALT_BUFFER_FOR_CURSED_WEAPON = exProperties.getProperty("AltAllowBufferForCursedWeapon", false);
        ALT_BUFFER_CANCEL_PRICE = exProperties.getProperty("AltNpcBufferCancelPrice", ArrayUtils.EMPTY_INT_ARRAY);
        ALT_BUFFER_CLEANS_PRICE = exProperties.getProperty("AltNpcBufferCleansPrice", ArrayUtils.EMPTY_INT_ARRAY);
        ALT_BUFFER_HP_CP_MP_PRICE = exProperties.getProperty("AltNpcBufferHpCpMpPrice", ArrayUtils.EMPTY_INT_ARRAY);
        ALT_BUFFER_CLEANS_AND_RESTORE_PRICE = exProperties.getProperty("AltNpcBufferRestoreAndCleansPrice", ArrayUtils.EMPTY_INT_ARRAY);
        if (ALT_BUFFER_CANCEL_PRICE.length % 2 != 0 || ALT_BUFFER_CLEANS_PRICE.length % 2 != 0 || ALT_BUFFER_HP_CP_MP_PRICE.length % 2 != 0 || ALT_BUFFER_CLEANS_AND_RESTORE_PRICE.length % 2 != 0) {
            throw new RuntimeException("ERROR: CONFIG AltNpcBuffer cleans or cancel or HpCpMp WRONG SIZE");
        }
        ALT_DEATH_PENALTY = exProperties.getProperty("EnableAltDeathPenalty", false);
        ALLOW_DEATH_PENALTY_C5 = exProperties.getProperty("EnableDeathPenaltyC5", true);
        ALT_DEATH_PENALTY_C5_CHANCE = exProperties.getProperty("DeathPenaltyC5Chance", 10);
        ALT_DEATH_PENALTY_C5_CHAOTIC_RECOVERY = exProperties.getProperty("ChaoticCanUseScrollOfRecovery", false);
        ALT_DEATH_PENALTY_C5_EXPERIENCE_PENALTY = exProperties.getProperty("DeathPenaltyC5RateExpPenalty", 1);
        ALT_DEATH_PENALTY_C5_KARMA_PENALTY = exProperties.getProperty("DeathPenaltyC5RateKarma", 1);
        ALT_PK_DEATH_RATE = exProperties.getProperty("AltPKDeathRate", 0.0);
        DISABLE_GRADE_PENALTY = exProperties.getProperty("AltDisableGradePenalty", false);
        ALT_HAIR_TO_ACC_SLOT = exProperties.getProperty("AltEnchantHairAccessory", false);
        ALT_CONSUME_ARROWS = exProperties.getProperty("ConsumeArrows", true);
        ALT_CONSUME_SOULSHOTS = exProperties.getProperty("ConsumeSoulShots", true);
        ALT_CONSUME_SPIRITSHOTS = exProperties.getProperty("ConsumeSpiritShots", true);
        ALT_CONSUME_BEAST_SHOTS = exProperties.getProperty("ConsumeBeastShots", true);
        ALT_PA_CONSUME_ARROWS = exProperties.getProperty("PremiumAccountConsumeArrows", true);
        ALT_PA_CONSUME_SOULSHOTS = exProperties.getProperty("PremiumAccountConsumeSoulShots", true);
        ALT_PA_CONSUME_SPIRITSHOTS = exProperties.getProperty("PremiumAccountConsumeSpiritShots", true);
        ALT_PA_CONSUME_BEAST_SHOTS = exProperties.getProperty("PremiumAccountConsumeBeastShots", true);
        ALT_UNIVERSAL_SHOTS = exProperties.getProperty("UniversalSoulShotIds", ArrayUtils.EMPTY_INT_ARRAY);
        ALT_UNIVERSAL_SPIRIT_SHOTS = exProperties.getProperty("UniversalSpiritShotIds", ArrayUtils.EMPTY_INT_ARRAY);
        ALT_UNIVERSAL_BLESSED_SPIRIT_SHOTS = exProperties.getProperty("UniversalBlessedSpiritShotIds", ArrayUtils.EMPTY_INT_ARRAY);
        NONOWNER_ITEM_PICKUP_DELAY = exProperties.getProperty("NonOwnerItemPickupDelay", 15L) * 1000L;
        NONOWNER_ITEM_PICKUP_DELAY_RAID = exProperties.getProperty("NonOwnerItemFromRaidPickupDelay", 300L) * 1000L;
        IGNORE_ITEM_PICKUP_DELAY_MONSTER_IDS = new TIntHashSet(exProperties.getProperty("IgnoreDropDelayMonsterIds", ArrayUtils.EMPTY_INT_ARRAY));
        IGNORE_ITEM_PICKUP_DELAY = exProperties.getProperty("IgnoreDropPickupDelay", 0L) * 1000L;
        ALT_NO_LASTHIT = exProperties.getProperty("NoLasthitOnRaid", false);
        ALT_DROP_LASTHIT = exProperties.getProperty("DropOnLastHit", false);
        ALT_KAMALOKA_NIGHTMARES_PREMIUM_ONLY = exProperties.getProperty("KamalokaNightmaresPremiumOnly", false);
        ALT_KAMALOKA_NIGHTMARE_REENTER = exProperties.getProperty("SellReenterNightmaresTicket", true);
        ALT_KAMALOKA_ABYSS_REENTER = exProperties.getProperty("SellReenterAbyssTicket", true);
        ALT_KAMALOKA_LAB_REENTER = exProperties.getProperty("SellReenterLabyrinthTicket", true);
        ALT_PET_HEAL_BATTLE_ONLY = exProperties.getProperty("PetsHealOnlyInBattle", true);
        CHAR_TITLE = exProperties.getProperty("CharTitle", false);
        ADD_CHAR_TITLE = exProperties.getProperty("CharAddTitle", "");
        ALT_ALLOW_SELL_COMMON = exProperties.getProperty("AllowSellCommon", true);
        ALT_ALLOW_SHADOW_WEAPONS = exProperties.getProperty("AllowShadowWeapons", true);
        ALT_DISABLED_MULTISELL = exProperties.getProperty("DisabledMultisells", ArrayUtils.EMPTY_INT_ARRAY);
        ALT_SHOP_PRICE_LIMITS = exProperties.getProperty("ShopPriceLimits", ArrayUtils.EMPTY_INT_ARRAY);
        ALT_SHOP_UNALLOWED_ITEMS = exProperties.getProperty("ShopUnallowedItems", ArrayUtils.EMPTY_INT_ARRAY);
        ALT_SHOP_REFUND_SELL_DIVISOR = exProperties.getProperty("ShopRefundSellDivisor", 2L);
        ALT_PRIVATE_STORE_BUY_COST_DIVISOR = exProperties.getProperty("PrivateStoreBuyCostDivisor", 1.0);
        ALT_SHOP_BUY_LIST_MULTIPLIER = exProperties.getProperty("ShopBuyItemMultiplier", 1.0);
        ALT_MULTISELL_ADENA_MULTIPLAYER = exProperties.getProperty("MultisellAdenaPriceMultiplier", 1.0);
        ALT_REVIVE_WINDOW_TO_TOWN = exProperties.getProperty("AltReviveWindowToTown", false);
        ALT_REVIVE_WINDOW_TO_TOWN_LOCATION = Location.parseLoc(exProperties.getProperty("AltReviveWindowToTownLoc", "81749,149171,-3464"));
        REMOVE_FORCE_CHARGE_ON_DEAD = exProperties.getProperty("RemoveForceChargesOnDead", true);
        ALT_ALLOWED_PET_POTIONS = exProperties.getProperty("AllowedPetPotions", new int[]{735, 1060, 1061, 1062, 1374, 1375, 1539, 1540, 6035, 6036});
        FESTIVAL_MIN_PARTY_SIZE = exProperties.getProperty("FestivalMinPartySize", 5);
        FESTIVAL_RATE_PRICE = exProperties.getProperty("FestivalRatePrice", 1.0);
        RIFT_MIN_PARTY_SIZE = exProperties.getProperty("RiftMinPartySize", 5);
        RIFT_SPAWN_DELAY = exProperties.getProperty("RiftSpawnDelay", 10000);
        RIFT_MAX_JUMPS = exProperties.getProperty("MaxRiftJumps", 4);
        RIFT_AUTO_JUMPS_TIME = exProperties.getProperty("AutoJumpsDelay", 8);
        RIFT_AUTO_JUMPS_TIME_RAND = exProperties.getProperty("AutoJumpsDelayRandom", 120000);
        RIFT_ENTER_COST_RECRUIT = exProperties.getProperty("RecruitFC", 18);
        RIFT_ENTER_COST_SOLDIER = exProperties.getProperty("SoldierFC", 21);
        RIFT_ENTER_COST_OFFICER = exProperties.getProperty("OfficerFC", 24);
        RIFT_ENTER_COST_CAPTAIN = exProperties.getProperty("CaptainFC", 27);
        RIFT_ENTER_COST_COMMANDER = exProperties.getProperty("CommanderFC", 30);
        RIFT_ENTER_COST_HERO = exProperties.getProperty("HeroFC", 33);
        RIFT_BOSS_ROOM_CHANCE = exProperties.getProperty("RiftRoomBossChance", 0);
        FOUR_SEPULCHER_ENABLE = exProperties.getProperty("FourSepulcherEnable", true);
        FOUR_SEPULCHER_MIN_PARTY_MEMBERS = exProperties.getProperty("FourSepulcherMinPartyMembers", 4);
        FOUR_SEPULCHER_NEW_CYCLE_MIN = exProperties.getProperty("FourSepulcherNewCycleMinutesStart", 55);
        ALLOW_CLANSKILLS = exProperties.getProperty("AllowClanSkills", true);
        PARTY_LEADER_ONLY_CAN_INVITE = exProperties.getProperty("PartyLeaderOnlyCanInvite", true);
        ALLOW_TALK_WHILE_SITTING = exProperties.getProperty("AllowTalkWhileSitting", true);
        ALLOW_NOBLE_TP_TO_ALL = exProperties.getProperty("AllowNobleTPToAll", false);
        CLANHALL_BUFFTIME_MODIFIER = exProperties.getProperty("ClanHallBuffTimeModifier", 1.0);
        SONGDANCETIME_MODIFIER = exProperties.getProperty("SongDanceTimeModifier", 1.0);
        SONGDANCE_CAN_BE_DISPELL = exProperties.getProperty("SongDanceCanBeDispelled", false);
        ALT_MUSIC_COST_GUARD_INTERVAL = exProperties.getProperty("AltMusicCostInterval", 0);
        ALT_ADDITIONAL_DANCE_SONG_MANA_CONSUME = exProperties.getProperty("AltAddMusicCost", true);
        MAXLOAD_MODIFIER = exProperties.getProperty("MaxLoadModifier", 1.0);
        GATEKEEPER_MODIFIER = new HashMap<Integer, Float>();
        String[] stringArray = new StringTokenizer(exProperties.getProperty("GatekeeperCostMultiplier", "0-40:0;41-:1"), ";");
        while (stringArray.hasMoreTokens()) {
            String string = stringArray.nextToken();
            int n2 = string.indexOf(58);
            if (n2 < 1) {
                am.warn("Can't process mod teleport \"" + string + "\"");
                continue;
            }
            float f = Float.parseFloat(string.substring(n2 + 1).trim());
            int n3 = string.indexOf(45);
            if (n3 < 1 || n3 >= n2) {
                am.warn("Can't process mod teleport \"" + string + "\"");
                continue;
            }
            int n4 = Integer.parseInt(string.substring(0, n3).trim());
            n = (string = string.substring(n3 + 1, n2).trim()).isEmpty() ? ALT_MAX_LEVEL + 1 : Integer.parseInt(string) + 1;
            for (int i = n4; i < n; ++i) {
                if (GATEKEEPER_MODIFIER.containsKey(i)) {
                    am.warn("Mod for lvl " + i + " redefined.");
                }
                GATEKEEPER_MODIFIER.put(i, Float.valueOf(f));
            }
        }
        GATEKEEPER_CATOCOMBS_FREE_LEVEL = exProperties.getProperty("GatekeeperFreeToCatacombs", 40);
        CRUMA_TELEPORT_MAX_LEVEL = exProperties.getProperty("GatekeeperMozellaMaxLevelToCrumaTower", 55);
        ALT_IMPROVED_PETS_LIMITED_USE = exProperties.getProperty("ImprovedPetsLimitedUse", false);
        SKILL_DURATION_MOD_AT_OLY = exProperties.getProperty("SkillDurationModWorkAtOlympiad", true);
        for (String string : exProperties.getProperty("SkillDurationMod", new String[]{""}, ";")) {
            String[] stringArray2 = string.trim().split("-");
            if (stringArray2.length != 2) continue;
            SKILL_DURATION_MOD.put(Integer.parseInt(stringArray2[0].trim()), Integer.parseInt(stringArray2[1].trim()) * 1000);
        }
        ALT_CHAMPION_CHANCE1 = exProperties.getProperty("AltChampionChance1", 0.0);
        ALT_CHAMPION_CHANCE2 = exProperties.getProperty("AltChampionChance2", 0.0);
        ALT_CHAMPION_CAN_BE_AGGRO = exProperties.getProperty("AltChampionAggro", false);
        ALT_CHAMPION_CAN_BE_SOCIAL = exProperties.getProperty("AltChampionSocial", false);
        ALT_CHAMPION_CAN_BE_SPECIAL_MONSTERS = exProperties.getProperty("AltChampionCanBeSpecialMonster", false);
        ALT_CHAMPION_CAN_BE_SEVEN_SIGN_MONSTERS = exProperties.getProperty("AltChampionCanBeSevenSignMonster", false);
        ALT_CHAMPION_TOP_LEVEL = exProperties.getProperty("AltChampionTopLevel", 75);
        ALT_CHAMPION_MIN_LEVEL = exProperties.getProperty("AltChampionMinLevel", 1);
        ALT_CHAMPIONS_LIST = new HashSet<Integer>(Arrays.asList(ArrayUtils.toObject((int[])exProperties.getProperty("AltChampionListId", ArrayUtils.EMPTY_INT_ARRAY))));
        ALT_IGNORE_CHAMPIONS_LIST = new HashSet<Integer>(Arrays.asList(ArrayUtils.toObject((int[])exProperties.getProperty("AltChampionIgnoreListId", ArrayUtils.EMPTY_INT_ARRAY))));
        ALT_CHAMPION_DROP_LEVEL_DIFF = exProperties.getProperty("AltChampionDropLevelDiff", 9);
        for (int i = 1; i <= 2; ++i) {
            ALT_CHAMPION_DROP_ITEM_IDS.put(i, exProperties.getProperty("AltChampionDropItemId." + i, ArrayUtils.EMPTY_INT_ARRAY));
            ALT_CHAMPION_DROP_COUNTS.put(i, exProperties.getProperty("AltChampionDropItemCount." + i, ArrayUtils.EMPTY_LONG_ARRAY));
            ALT_CHAMPION_DROP_CHANCES.put(i, exProperties.getProperty("AltChampionDropItemChance." + i, ArrayUtils.EMPTY_FLOAT_ARRAY));
        }
        ALT_PCBANG_POINTS_ENABLED = exProperties.getProperty("AltPcBangPointsEnabled", false);
        ALT_PCBANG_POINTS_BONUS_DOUBLE_CHANCE = exProperties.getProperty("AltPcBangPointsDoubleChance", 10.0);
        ALT_PCBANG_POINTS_BONUS_DOUBLE_RATE = exProperties.getProperty("AltPcBangPointsDoubleRate", 2.0);
        ALT_PCBANG_BONUS_WITH_PREMIUM_RATE = exProperties.getProperty("AltPcBangPointsBonusForPremiumAccount", 1.0);
        ALT_PCBANG_POINTS_BONUS = exProperties.getProperty("AltPcBangPointsBonus", 0);
        ALT_PCBANG_POINTS_DELAY = exProperties.getProperty("AltPcBangPointsDelay", 20);
        ALT_PCBANG_POINTS_MIN_LVL = exProperties.getProperty("AltPcBangPointsMinLvl", 1);
        ALT_PCBANG_CHECK_HWID = exProperties.getProperty("AltPcBangPointsCheckHwid", false);
        ALT_PCBANG_POINTS_COMMAND = exProperties.getProperty("AltPCBangVoiceCommand", false);
        ALT_DEBUG_ENABLED = exProperties.getProperty("AltDebugEnabled", false);
        ALT_DEBUG_PVP_ENABLED = exProperties.getProperty("AltDebugPvPEnabled", false);
        ALT_DEBUG_PVP_DUEL_ONLY = exProperties.getProperty("AltDebugPvPDuelOnly", true);
        ALT_DEBUG_PVE_ENABLED = exProperties.getProperty("AltDebugPvEEnabled", false);
        ALT_LIMIT_HWID_WINDOW = exProperties.getProperty("LimitingGameWindows", 0);
        ALT_LIMIT_HWID_PREMIUM_WINDOW = exProperties.getProperty("LimitingGameWindowsForPremium", 0);
        ALT_LIMIT_HWID_REPORT_TIMER = exProperties.getProperty("LimitingGameWindowsTimer", 30L) * 1000L;
        ALT_LIMIT_IP_WINDOW = exProperties.getProperty("LimitingIpGameWindows", 0);
        ALT_LIMIT_IP_PREMIUM_WINDOW = exProperties.getProperty("LimitingIpGameWindowsForPremium", 0);
        ALT_LIMIT_IP_REPORT_TIMER = exProperties.getProperty("LimitingIpGameWindowsTimer", 30L) * 1000L;
        ALT_MULTISELL_DEBUG = exProperties.getProperty("AltDebugMultisellEnabled", true);
        String string = exProperties.getProperty("AltSimpleBypassAllowed", "_mrsl,_diary,_match,manor_menu_select,bypass -h npc_%objectId%_lang ru,bypass -h npc_%objectId%_lang en,_olympiad,_dispel");
        ALT_SIMPLE_BEGINNINGS = Arrays.asList(string.split(","));
        String string2 = exProperties.getProperty("AltBBSBypassAllowed", "_bbshome,_bbsgetfav,_bbslink,_bbsloc,_bbsclan,_bbsmemo,_maillist_0_1_0_,_bbsaddfav");
        ALT_SIMPLE_BBS_BEGINNINGS = Arrays.asList(string2.split(","));
        ALT_PARTY_DISTRIBUTION_RANGE = exProperties.getProperty("AltPartyDistributionRange", 1500);
        ALT_PARTY_DISTRIBUTION_DIFF_LEVEL_LIMIT = exProperties.getProperty("AltPartyDistributionLevelLimit", 20);
        ALT_PARTY_BONUS = exProperties.getProperty("AltPartyBonus", new double[]{1.0, 1.1, 1.2, 1.3, 1.4, 1.5, 2.0, 2.1, 2.2});
        ALT_PARTY_BONUS_FOR_QUESTS = exProperties.getProperty("AltPartyBonusForQuestDrop", false);
        ALT_PARTY_CLASS_LIMIT = new LinkedHashMap<Integer, Integer>();
        for (String string3 : StringUtils.split((String)exProperties.getProperty("AltPartyClassLimits", ""), (char)';')) {
            n = StringUtils.indexOfAny((CharSequence)string3, (char[])new char[]{':', ',', '='});
            if (n < 0) continue;
            ALT_PARTY_CLASS_LIMIT.put(Integer.parseInt(string3.substring(0, n).trim()), Integer.parseInt(string3.substring(n + 1).trim()));
        }
        ALT_REMOVE_SKILLS_ON_DELEVEL = exProperties.getProperty("AltRemoveSkillsOnDelevelThreshold", 10);
        ALT_BASIC_ACTIONS = exProperties.getProperty("AltBasicActions", new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 66, 67, 68, 69, 70, 74, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 92, 93, 94, 96, 1000, 1001, 1002, 1003, 1004, 1005, 1006, 1007, 1008, 1009, 1010, 1011, 1012, 1013, 1014, 1015, 1016, 1017, 1018, 1019, 1020, 1021, 1022, 1023, 1024, 1025, 1026, 1027, 1028, 1029, 1030, 1031, 1032, 1033, 1034, 1035, 1036, 1037, 1038, 1039, 1040, 1041, 1042, 1043, 1044, 1045, 1046, 1047, 1048, 1049, 1050, 1051, 1052, 1053, 1054, 1055, 1056, 1057, 1058, 1059, 1060, 1061, 1062, 1063, 1064, 1065, 1066, 1067, 1068, 1069, 1070, 1071, 1072, 1073, 1074, 1075, 1076, 1077, 1078, 1079, 1080, 1081, 1082, 1083, 1084, 1086, 1087, 1088, 1089, 1090, 1091, 1092, 5000, 5001, 5002, 5003, 5004, 5005, 5006, 5007, 5008, 5009, 5010, 5011, 5012, 5013, 5014, 5015});
        ALT_TRANSFORMATION_ACTIONS = exProperties.getProperty("AltTransformationActions", new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 15, 16, 17, 18, 19, 21, 22, 23, 40, 50, 52, 53, 54, 55, 56, 57, 63, 64, 67, 68, 69, 70, 1000, 1001, 1002, 1003, 1004, 1005, 1006, 1007, 1008, 1009, 1010, 1011, 1012, 1013, 1014, 1015, 1016, 1017, 1018, 1019, 1020, 1021, 1022, 1023, 1024, 1025, 1026, 1027, 1028, 1029, 1030, 1031, 1032, 1033, 1034, 1035, 1036, 1037, 1038, 1039, 1040, 1041, 1042, 1043, 1044, 1045, 1046, 1047, 1048, 1049, 1050, 1051, 1052, 1053, 1054, 1055, 1056, 1057, 1058, 1059, 1060, 1061, 1062, 1063, 1064, 1065, 1066, 1067, 1068, 1069, 1070, 1071, 1072, 1073, 1074, 1075, 1076, 1077, 1078, 1079, 1080, 1081, 1082, 1083, 1084, 1086, 1087, 1088, 1089, 1090, 1091, 1092, 5000, 5001, 5002, 5003, 5004, 5005, 5006, 5007, 5008, 5009, 5010, 5011, 5012, 5013, 5014, 5015});
        ALT_TELEPORT_FROM_SEVEN_SING_MONSTER = exProperties.getProperty("SeventSingMobProtection", true);
        ALT_ENABLE_SEVEN_SING_TELEPORTER_PROTECTION = exProperties.getProperty("SeventSingTeleporterProtection", true);
        ALT_MAMONS_CHECK_SEVEN_SING_STATUS = exProperties.getProperty("MammonCheckRegistrationOnSevenSing", true);
        ALT_CABAL_SEVEN_SING_SHOUT = exProperties.getProperty("SevenSingOratorCanTalk", true);
        ALT_SEVEN_SING_STATIC_EVENT_PERIOD_SPAWN = exProperties.getProperty("SevenSingStaticEventPeriodSpawn", true);
        ALT_SHOW_SERVER_TIME = exProperties.getProperty("ShowServerTime", false);
        ALT_FISH_CHAMPIONSHIP_ENABLED = exProperties.getProperty("AltFishChampionshipEnabled", true);
        ALT_FISH_CHAMPIONSHIP_REWARD_ITEM = exProperties.getProperty("AltFishChampionshipRewardItemId", 57);
        ALT_FISH_CHAMPIONSHIP_REWARD_1 = exProperties.getProperty("AltFishChampionshipReward1", 800000);
        ALT_FISH_CHAMPIONSHIP_REWARD_2 = exProperties.getProperty("AltFishChampionshipReward2", 500000);
        ALT_FISH_CHAMPIONSHIP_REWARD_3 = exProperties.getProperty("AltFishChampionshipReward3", 300000);
        ALT_FISH_CHAMPIONSHIP_REWARD_4 = exProperties.getProperty("AltFishChampionshipReward4", 200000);
        ALT_FISH_CHAMPIONSHIP_REWARD_5 = exProperties.getProperty("AltFishChampionshipReward5", 100000);
        ALT_PVP_ITEMS_TREDABLE = exProperties.getProperty("AltPvPItemsTredable", false);
        ALT_PVP_ITEMS_ATTRIBUTABLE = exProperties.getProperty("AltPvPItemsAttributable", false);
        ALT_PVP_ITEMS_AUGMENTABLE = exProperties.getProperty("AltPvPItemsAugmentable", false);
        ALT_HBCE_FAIR_PLAY = exProperties.getProperty("HBCEFairPlay", false);
        ALT_PET_INVENTORY_LIMIT = exProperties.getProperty("AltPetInventoryLimit", 12);
        ALT_SET_TITLE_OWNER_TO_SUMMON = exProperties.getProperty("AltOwnerNameToSummonTitle", true);
        ALT_SAVE_SERVITOR_BUFF = exProperties.getProperty("AltSaveServitorBuffs", false);
        ALT_SERVITOR_ACTION_MAX_DISTANCE = exProperties.getProperty("AltServitorMaxActionDistance", 0);
        String[] stringArray3 = exProperties.getProperty("RecommendationFlushTime", new String[]{"13", "00"}, ":");
        REC_FLUSH_HOUR = Integer.parseInt(stringArray3[0]);
        REC_FLUSH_MINUTE = Integer.parseInt(stringArray3[1]);
        ALT_CHECK_CERTIFICATION_ITEMS = exProperties.getProperty("AltCheckCertificationItems", true);
        ALT_PASSIVE_NOBLESS_ID = exProperties.getProperty("NoblessPassiveSkillId", 0);
        CUSTOM_HERO_STATUS_TIME = exProperties.getProperty("CustomHeroExpireTime", 1L) * 60L * 60L;
        ALT_ALLOW_DELAY_NPC_TALK = exProperties.getProperty("AltDelayOnMoveAfterTalkWithNPC", false);
        ALT_SPREADING_AFTER_TELEPORT = exProperties.getProperty("AltSpreadingTeleportPoints", true);
        ALT_TELEPORT_PROTECTION = exProperties.getProperty("AltTeleportProtection", false);
        ALT_TELEPORT_PROTECTION_TIME = exProperties.getProperty("AltTeleportProtectionTime", 10);
        ALT_3_JOB_QUEST_IS_PARY = exProperties.getProperty("AltThirdJobisPartyQuest", true);
        EX_USE_TELEPORT_FLAG = exProperties.getProperty("TeleportBookMarkSystem", true);
        EX_USE_TELEPORT_BOOK_SCROLL = exProperties.getProperty("TeleportBookMarkTeleportScrollItemId", 13016);
        EX_USE_TELEPORT_BOOL_SCROLL_SAVE_ITEM = exProperties.getProperty("TeleportBookMarkSlotSaveItemId", 20033);
        EX_MAX_TELEPORT_BOOKMARK_SIZE = exProperties.getProperty("TeleportBookMarkSlotsLimit", 12);
        EX_TELEPORT_BOOK_SCROLL = TimeUnit.SECONDS.toMillis(exProperties.getProperty("TeleportBookMarkReuse", 0L));
        ALT_INITIAL_QUESTS = exProperties.getProperty("AltInitialQuests", new int[]{255});
        ALT_MAX_PARTY_SIZE = exProperties.getProperty("AltMaxPartySize", 9);
        ALT_OPEN_CLOAK_SLOT = exProperties.getProperty("OpenCloakSlot", false);
        ALT_ALLOW_GLOW_ARMOR_SET = exProperties.getProperty("ArmorSetAllowGlowOnEnchant", true);
        ALT_VITALITY_ENABLED = exProperties.getProperty("AltVitalityEnabled", false);
        ALT_VITALITY_RATE = exProperties.getProperty("AltVitalityRate", 1.0);
        ALT_VITALITY_CONSUME_RATE = exProperties.getProperty("AltVitalityConsumeRate", 1.0);
        ALT_VITALITY_RAID_BONUS = exProperties.getProperty("AltVitalityRaidBonus", 2000);
        ALT_VITALITY_LEVELS = exProperties.getProperty("AltVitalityLevels", new int[]{5000, 10000, 70000, 110000, 140000});
        ALT_VITALITY_POINTS_PER_MINUTE = exProperties.getProperty("AltVitalityPointsPerMinute", 1);
        LOOSE_EXP_ON_DEATH = new double[127];
        double d = 0.0;
        for (int i = 1; i < LOOSE_EXP_ON_DEATH.length; ++i) {
            double d2;
            Config.LOOSE_EXP_ON_DEATH[i] = d2 = exProperties.getProperty("LossExpUponDeathLevel" + i, d);
            if (d2 == d) continue;
            d = d2;
        }
    }

    public static void loadBossSettings() {
        ExProperties exProperties = Config.load(BOSS_SETTINGS_FILE);
        FWA_LIMITUNTILSLEEPANTHARAS = exProperties.getProperty("AntharasSleepTime", 20L) * 60000L;
        FWA_FIXTIMEINTERVALOFANTHARAS = exProperties.getProperty("AntharasRespawnTimeInterval", 264L) * 3600000L;
        FWA_RANDOMINTERVALOFANTHARAS = exProperties.getProperty("AntharasRandomSpawnAddTime", 0L) * 60000L;
        ANTHARAS_CLEAR_ZONE_IF_ALL_DIE = exProperties.getProperty("AntharasClearZoneifAllDie", 1L) * 60000L;
        FWA_FIXTIMEPATTERNOFANTHARAS = exProperties.getProperty("AntharasRespawnTimePattern", "");
        FWA_APPTIMEOFANTHARAS = exProperties.getProperty("AntharasSpawnIntervalInZone", 10L) * 60000L;
        FWA_LIMITMAXUNTILSLEEPANTHARAS = exProperties.getProperty("AntharasMaxTime", -1L) * 60000L;
        FWA_ALLOW_ANTHARAS_MINION = exProperties.getProperty("AntharasAllowMinions", false);
        FWV_LIMITUNTILSLEEPVALAKAS = exProperties.getProperty("ValakasSleepTime", 20L) * 60000L;
        FWV_FIXINTERVALOFVALAKAS = exProperties.getProperty("ValakasRespawnTimeInterval", 264L) * 3600000L;
        FWV_RANDOMINTERVALOFVALAKAS = exProperties.getProperty("ValakasRandomSpawnAddTime", 0L) * 60000L;
        FWA_FIXTIMEPATTERNOFVALAKAS = exProperties.getProperty("ValakasRespawnTimePattern", "");
        VALAKAS_CLEAR_ZONE_IF_ALL_DIE = exProperties.getProperty("ValakasClearZoneifAllDie", 1L) * 60000L;
        FWA_LIMITMAXUNTILSLEEPVALAKAS = exProperties.getProperty("ValakasMaxTime", -1L) * 60000L;
        FWF_FIXINTERVALOFFRINTEZZA = exProperties.getProperty("FrintezzaRespawnTimeInterval", 48L) * 3600000L;
        FWF_RANDOMINTERVALOFFRINTEZZA = exProperties.getProperty("FrintezzaRandomSpawnAddTime", 0L) * 60000L;
        FWA_FIXTIMEPATTERNOFFRINTEZZA = exProperties.getProperty("FrintezzaRespawnTimePattern", "");
        FRINTEZZA_TOMB_TIMEOUT = exProperties.getProperty("FrintezzaTombPassTime", 35);
        FRINTEZZA_MIN_PARTY_IN_CC = exProperties.getProperty("FrintezzaMinPartyInCC", 4);
        FRINTEZZA_MAX_PARTY_IN_CC = exProperties.getProperty("FrintezzaMaxPartyInCC", 5);
        FRINTEZZA_MIN_DISTANCE_FOR_ENTRANCE = exProperties.getProperty("FrintezzaMinDistanceForEntrance", 300);
        FRINTEZZA_LOCK_FOR_DEAD_PLAYERS = exProperties.getProperty("FrintezzaEntranceLockForDeadPlayers", false);
        FWV_APPTIMEOFVALAKAS = exProperties.getProperty("ValakasSpawnIntervalInZone", 10L) * 60000L;
        RAID_TELE_TO_HOME_FROM_PVP_ZONES = exProperties.getProperty("ReturnToHomeBossesFromPvPZones", false);
        RAID_TELE_TO_HOME_FROM_TOWN_ZONES = exProperties.getProperty("ReturnToHomeBossesFromTownZones", true);
        FWB_LIMITUNTILSLEEPBAIUM = exProperties.getProperty("BaiumSleepTime", 30L) * 60000L;
        FWB_FIXINTERVALOFBAIUM = exProperties.getProperty("BaiumSpawnTimeInterval", 120L) * 3600000L;
        FWA_FIXTIMEPATTERNOFBAIUM = exProperties.getProperty("BaiumRespawnTimePattern", "");
        BAIUM_CLEAR_ZONE_IF_ALL_DIE = exProperties.getProperty("BaiumClearZoneifAllDie", 1L) * 60000L;
        FWB_RANDOMINTERVALOFBAIUM = exProperties.getProperty("BaiumRandomSpawnAddTime", 480L) * 60000L;
        FWA_LIMITMAXUNTILSLEEPBAIUM = exProperties.getProperty("BaiumMaxTime", -1L) * 60000L;
        FWS_ACTIVITYTIMEOFMOBS = exProperties.getProperty("SailrenActivityMobsTime", 120L) * 60000L;
        SAILREN_CLEAR_ZONE_IF_ALL_DIE = exProperties.getProperty("SailrenClearZoneifAllDie", 1L) * 60000L;
        FWA_FIXTIMEPATTERNOFSAILREN = exProperties.getProperty("SailrenRespawnTimePattern", "");
        FWS_FIXINTERVALOFSAILRENSPAWN = exProperties.getProperty("RespawnSailrenInterval", 24L) * 3600000L;
        FWS_RANDOMINTERVALOFSAILRENSPAWN = exProperties.getProperty("RandomRespawnSailrenInterval", 1440L) * 60000L;
        FWS_INTERVALOFNEXTMONSTER = exProperties.getProperty("SailrenMobsRespawnInterval", 1L) * 60000L;
        ENABLE_ON_TIME_DOOR_OPEN = exProperties.getProperty("EnableOnTimeDoorOpen", false);
        ON_TIME_OPEN_DOOR_ID = exProperties.getProperty("EnableOnTimeDoorId", 21240006);
        ON_TIME_OPEN_PATTERN = exProperties.getProperty("DoorInTimePattern", "* 22 * * *");
        ZAKEN_USE_TELEPORT = exProperties.getProperty("ZakenUseTeleport", true);
        ORFEN_USE_TELEPORT = exProperties.getProperty("OrfenUseTeleport", true);
        CAN_ATTACK_FROM_ANOTHER_ZONE_TO_EPIC = exProperties.getProperty("CanAttackFromAnotherZoneToEpic", false);
        ALLOW_BAIUM_RAID = exProperties.getProperty("AllowBaiumRaid", true);
        VALAKAS_ANNOUNCE_PREPARING_SPAWN = exProperties.getProperty("ValakasAnnouncePreparing", false);
        ANTHARAS_ANNOUNCE_PREPARING_SPAWN = exProperties.getProperty("AntharasAnnouncePreparing", false);
        BAIUM_CHECK_QUEST_ON_AWAKE = exProperties.getProperty("BaiumCheckQuestForAwake", true);
        SHOW_BOSS_SCENES = exProperties.getProperty("ShowBossScenes", true);
    }

    public static void loadFarmConfig() {
        ExProperties exProperties = Config.load(AUTO_FARM_FILE);
        ALLOW_AUTO_FARM = exProperties.getProperty("AllowAutoFarm", false);
        AUTOFARM_TIME_TRACK_USAGE_ONLY = exProperties.getProperty("AutoFarmTimeTrackUsageOnly", true);
        AUTO_FARM_FOR_PREMIUM = exProperties.getProperty("AutoFarmOnlyForPremium", false);
        AUTO_FARM_UNLIMITED = exProperties.getProperty("AutoFarmIsFree", false);
        AUTO_FARM_PA_UNLIMITED = exProperties.getProperty("AutoFarmIsFreeForPremium", false);
        AUTO_FARM_ALLOW_FOR_CURSED_WEAPON = exProperties.getProperty("AutoFarmAllowForCursedWeapon", false);
        String[] stringArray = exProperties.getProperty("AutoFarmPriceList", "").split(";");
        AUTO_FARM_PRICES = new HashMap<Integer, Pair<Integer, Long>>(stringArray.length);
        for (String string : stringArray) {
            int n = string.indexOf(44);
            int n2 = string.indexOf(58, n);
            if (n <= -1 || n2 <= n) continue;
            try {
                AUTO_FARM_PRICES.put(Integer.parseInt(string.substring(0, n)), (Pair<Integer, Long>)Pair.of((Object)Integer.parseInt(string.substring(n + 1, n2)), (Object)Long.parseLong(string.substring(n2 + 1))));
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
        }
        ATTACK_SKILL_CHANCE = exProperties.getProperty("AttackSkillChance", 100);
        ATTACK_SKILL_PERCENT = exProperties.getProperty("AttackSkillPercent", 5);
        CHANCE_SKILL_CHANCE = exProperties.getProperty("ChanceSkillChance", 100);
        CHANCE_SKILL_PERCENT = exProperties.getProperty("ChanceSkillPercent", 5);
        SELF_SKILL_CHANCE = exProperties.getProperty("SelfSkillChance", 100);
        SELF_SKILL_PERCENT = exProperties.getProperty("SelfSkillPercent", 5);
        HEAL_SKILL_CHANCE = exProperties.getProperty("HealSkillChance", 100);
        HEAL_SKILL_PERCENT = exProperties.getProperty("HealSkillPercent", 30);
        SUMMON_ATTACK_SKILL_CHANCE = exProperties.getProperty("SummonAttackSkillChance", 100);
        SUMMON_ATTACK_SKILL_PERCENT = exProperties.getProperty("SummonAttackSkillPercent", 5);
        SUMMON_SELF_SKILL_CHANCE = exProperties.getProperty("SummonSelfSkillChance", 100);
        SUMMON_SELF_SKILL_PERCENT = exProperties.getProperty("SummonSelfSkillPercent", 5);
        SUMMON_HEAL_SKILL_CHANCE = exProperties.getProperty("SummonHealSkillChance", 100);
        SUMMON_HEAL_SKILL_PERCENT = exProperties.getProperty("SummonHealSkillPercent", 30);
        SHORTCUT_PAGE = exProperties.getProperty("ShortCutPage", 10);
        SEARCH_DISTANCE = exProperties.getProperty("SearchDistance", 2000);
        FARM_TYPE = exProperties.getProperty("AutoFarmType", 0);
        FARM_INTERVAL_TASK = exProperties.getProperty("AutoFarmIntervalTask", 500);
        SKILLS_EXTRA_DELAY = (long)exProperties.getProperty("SkillsExtraDelay", 5) * 1000L;
        KEEP_LOCATION_DELAY = (long)exProperties.getProperty("KeepLocationDelay", 5) * 1000L;
        RUN_CLOSE_UP_DELAY = (long)exProperties.getProperty("RunCloseUpDelay", 2) * 1000L;
        RUN_CLOSE_UP_DISTANCE = exProperties.getProperty("RunCloseUpDistance", 100);
        ALLOW_FARM_FREE_TIME = exProperties.getProperty("AllowFarmFreeTime", false);
        REFRESH_FARM_TIME = exProperties.getProperty("AllowRefreshFarmTime", false);
        FARM_FREE_TIME = exProperties.getProperty("FarmFreeTime", 3);
        ALLOW_CHECK_HWID_LIMIT = exProperties.getProperty("AllowCheckHwidLimits", false);
        FARM_ACTIVE_LIMITS = exProperties.getProperty("FarmActiveLimits", 3);
        AUTO_FARM_IGNORED_NPC_ID = exProperties.getProperty("AutoFarmIgnoreMobIds", ArrayUtils.EMPTY_INT_ARRAY);
        SERVICES_AUTO_FARM_ABNORMAL = AbnormalEffect.getByName(exProperties.getProperty("AutoFarmAbnormalEffectName", "null"));
        SERVICE_AUTO_FARM_SET_RED_RING = exProperties.getProperty("AutoFarmSetRedRing", false);
        AUTO_FARM_LIMIT_ZONE_NAMES = new HashSet<String>(Arrays.asList(exProperties.getProperty("AutoFarmProhibitedZones", ArrayUtils.EMPTY_STRING_ARRAY)));
        AUTO_FARM_STOP_ON_PVP_FLAG = exProperties.getProperty("AutoFarmStopOnPvPFlag", true);
        String[] stringArray2 = exProperties.getProperty("FarmExpendLimitPrice", "4037,1").split(",");
        try {
            Config.FARM_EXPEND_LIMIT_PRICE[0] = Integer.parseInt(stringArray2[0]);
            Config.FARM_EXPEND_LIMIT_PRICE[1] = Integer.parseInt(stringArray2[1]);
        }
        catch (NumberFormatException numberFormatException) {
            // empty catch block
        }
    }

    private static RateBonusInfo[] a() {
        ArrayList<RateBonusInfo> arrayList = new ArrayList<RateBonusInfo>();
        try {
            SAXReader sAXReader = new SAXReader(true);
            Document document = sAXReader.read(new File(SERVICES_RATE_BONUS_XML_FILE));
            Element element = document.getRootElement();
            Iterator iterator = element.elementIterator();
            while (iterator.hasNext()) {
                Element element2 = (Element)iterator.next();
                if (!"rate_bonus".equalsIgnoreCase(element2.getName())) continue;
                int n = Integer.parseInt(element2.attributeValue("id"));
                int n2 = Integer.parseInt(element2.attributeValue("consume_item_id"));
                long l = Long.parseLong(element2.attributeValue("consume_item_amount"));
                RateBonusInfo rateBonusInfo = new RateBonusInfo(n, n2, l);
                Iterator iterator2 = element2.elementIterator();
                while (iterator2.hasNext()) {
                    Element element3 = (Element)iterator2.next();
                    if ("exp".equalsIgnoreCase(element3.getName())) {
                        rateBonusInfo.rateXp = Float.parseFloat(element3.attributeValue("value"));
                        continue;
                    }
                    if ("sp".equalsIgnoreCase(element3.getName())) {
                        rateBonusInfo.rateSp = Float.parseFloat(element3.attributeValue("value"));
                        continue;
                    }
                    if ("exp_raid".equalsIgnoreCase(element3.getName())) {
                        rateBonusInfo.rateRaidXp = Float.parseFloat(element3.attributeValue("value"));
                        continue;
                    }
                    if ("sp_raid".equalsIgnoreCase(element3.getName())) {
                        rateBonusInfo.rateRaidSp = Float.parseFloat(element3.attributeValue("value"));
                        continue;
                    }
                    if ("quest_reward".equalsIgnoreCase(element3.getName())) {
                        rateBonusInfo.questRewardRate = Float.parseFloat(element3.attributeValue("value"));
                        continue;
                    }
                    if ("quest_adena_reward".equalsIgnoreCase(element3.getName())) {
                        rateBonusInfo.questAdenaRewardRate = Float.parseFloat(element3.attributeValue("value"));
                        continue;
                    }
                    if ("quest_drop".equalsIgnoreCase(element3.getName())) {
                        rateBonusInfo.questDropRate = Float.parseFloat(element3.attributeValue("value"));
                        continue;
                    }
                    if ("drop_adena".equalsIgnoreCase(element3.getName())) {
                        rateBonusInfo.dropAdena = Float.parseFloat(element3.attributeValue("value"));
                        continue;
                    }
                    if ("drop_items".equalsIgnoreCase(element3.getName())) {
                        rateBonusInfo.dropItems = Float.parseFloat(element3.attributeValue("value"));
                        continue;
                    }
                    if ("drop_spoil".equalsIgnoreCase(element3.getName())) {
                        rateBonusInfo.dropSpoil = Float.parseFloat(element3.attributeValue("value"));
                        continue;
                    }
                    if ("drop_seal_stones".equalsIgnoreCase(element3.getName())) {
                        rateBonusInfo.dropSealStones = Float.parseFloat(element3.attributeValue("value"));
                        continue;
                    }
                    if ("drop_raid_item".equalsIgnoreCase(element3.getName())) {
                        rateBonusInfo.dropRaidItems = Float.parseFloat(element3.attributeValue("value"));
                        continue;
                    }
                    if ("enchant_item_mul".equalsIgnoreCase(element3.getName())) {
                        rateBonusInfo.enchantItemMul = Float.parseFloat(element3.attributeValue("value"));
                        continue;
                    }
                    if ("enchant_skill_mul".equalsIgnoreCase(element3.getName())) {
                        rateBonusInfo.enchantSkillMul = Float.parseFloat(element3.attributeValue("value"));
                        continue;
                    }
                    if ("bonus_days".equalsIgnoreCase(element3.getName())) {
                        String string = element3.attributeValue("value");
                        int n3 = string.indexOf(46);
                        if (n3 > -1) {
                            rateBonusInfo.bonusTimeSeconds += TimeUnit.HOURS.toSeconds(Long.parseLong(string.substring(n3 + 1)));
                            string = string.substring(0, n3);
                        }
                        rateBonusInfo.bonusTimeSeconds += TimeUnit.DAYS.toSeconds(Long.parseLong(string));
                        continue;
                    }
                    if ("reward".equalsIgnoreCase(element3.getName())) {
                        rateBonusInfo.rewardItem.add((Pair<Integer, Long>)Pair.of((Object)Integer.parseInt(element3.attributeValue("item_id")), (Object)Long.parseLong(element3.attributeValue("item_count"))));
                        continue;
                    }
                    if ("name_color".equalsIgnoreCase(element3.getName())) {
                        rateBonusInfo.nameColor = Integer.decode("0x" + element3.attributeValue("value"));
                        continue;
                    }
                    if (!"hwid_limits".equalsIgnoreCase(element3.getName())) continue;
                    rateBonusInfo.hwidLimits = Integer.parseInt(element3.attributeValue("value"));
                }
                arrayList.add(rateBonusInfo);
            }
        }
        catch (Exception exception) {
            am.error("", (Throwable)exception);
        }
        return arrayList.toArray(new RateBonusInfo[arrayList.size()]);
    }

    public static void loadServicesSettings() {
        String[] stringArray;
        ExProperties exProperties = Config.load(SERVICES_FILE);
        TEST_SERVER_HELPER_ENABLED = exProperties.getProperty("TestServerHelperEnabled", false);
        CLASS_MASTERS_CLASSES = exProperties.getProperty("ClassMasterClasses", "");
        COMMAND_CLASS_MASTER_ENABLED = exProperties.getProperty("ChatClassMasterEnabled", false);
        COMMAND_CLASS_MASTER_CLASSES = exProperties.getProperty("ChatClassMasterId", "");
        COMMAND_CLASS_POPUP_LIMIT = exProperties.getProperty("ChatClassMasterPopUpOnExp", 0);
        COMMAND_CLASS_MASTER_VOICE_COMMANDS = exProperties.getProperty("ChatClassMasterVoiceCommands", new String[]{"prof", "class"});
        SERVICES_CHANGE_NICK_ENABLED = exProperties.getProperty("NickChangeEnabled", false);
        SERVICES_CHANGE_NICK_PRICE = exProperties.getProperty("NickChangePrice", 100);
        SERVICES_CHANGE_NICK_ITEM = exProperties.getProperty("NickChangeItem", 4037);
        CUSTOM_CNAME_TEMPLATE = exProperties.getProperty("NickNameCustomTemplate", "[A-Za-z0-9\u0410-\u042f\u0430-\u044f]{2,16}");
        QUEST_SELL_ENABLE = exProperties.getProperty("QuestSellEnabled", false);
        QUEST_SELL_QUEST_PRICES = exProperties.getProperty("QuestSellQuestPrices", "");
        APPEARANCE_APPLY_ITEM_ID = exProperties.getProperty("ChangeAppearanceItemId", 3435);
        APPEARANCE_SUPPORT_ITEM_ID = exProperties.getProperty("ChangeAppearanceCostItemId", 57);
        APPEARANCE_SUPPORT_ITEM_CNT = exProperties.getProperty("ChangeAppearanceCostCount", 2000000);
        APPEARANCE_CANCEL_ITEM_ID = exProperties.getProperty("CancelAppearanceCostItemId", 3434);
        APPEARANCE_CANCEL_PRICE = exProperties.getProperty("CancelAppearanceAdenaPrice", 1000);
        APPEARANCE_PROHIBITED_CHANGE = exProperties.getProperty("ForbiddenToChangedAppearanceItemId", ArrayUtils.EMPTY_INT_ARRAY);
        APPEARANCE_PROHIBITED_BE_CHANGED = exProperties.getProperty("ForbiddenToBeChangedAppearanceItemId", ArrayUtils.EMPTY_INT_ARRAY);
        SERVICES_CHANGE_CLAN_NAME_ENABLED = exProperties.getProperty("ClanNameChangeEnabled", false);
        SERVICES_CHANGE_CLAN_NAME_PRICE = exProperties.getProperty("ClanNameChangePrice", 100);
        SERVICES_CHANGE_CLAN_NAME_ITEM = exProperties.getProperty("ClanNameChangeItem", 4037);
        SERVICES_CHANGE_PET_NAME_ENABLED = exProperties.getProperty("PetNameChangeEnabled", false);
        SERVICES_CHANGE_PET_NAME_NPC_ID = exProperties.getProperty("PetNameChangeNpcId", new int[]{30731, 30827, 30828, 30829, 30830, 30831, 30869, 31067, 31265, 31309, 31954});
        SERVICES_CHANGE_PET_NAME_PRICE = exProperties.getProperty("PetNameChangePrice", 100);
        SERVICES_CHANGE_PET_NAME_ITEM = exProperties.getProperty("PetNameChangeItem", 4037);
        SERVICES_EXCHANGE_BABY_PET_ENABLED = exProperties.getProperty("BabyPetExchangeEnabled", false);
        SERVICES_EXCHANGE_BABY_PET_PRICE = exProperties.getProperty("BabyPetExchangePrice", 100);
        SERVICES_EXCHANGE_BABY_PET_ITEM = exProperties.getProperty("BabyPetExchangeItem", 4037);
        SERVICES_CHANGE_SEX_ENABLED = exProperties.getProperty("SexChangeEnabled", false);
        SERVICES_CHANGE_SEX_PRICE = exProperties.getProperty("SexChangePrice", 100);
        SERVICES_CHANGE_SEX_ITEM = exProperties.getProperty("SexChangeItem", 4037);
        SERVICES_CHANGE_BASE_ENABLED = exProperties.getProperty("BaseChangeEnabled", false);
        SERVICES_CHANGE_BASE_PRICE = exProperties.getProperty("BaseChangePrice", 100);
        SERVICES_CHANGE_BASE_ITEM = exProperties.getProperty("BaseChangeItem", 4037);
        SERVICES_CHANGE_BASE_LIST_UNCOMPATABLE = exProperties.getProperty("BaseChangeForAnySubFromList", false);
        SERVICES_SEPARATE_SUB_ENABLED = exProperties.getProperty("SeparateSubEnabled", false);
        SERVICES_SEPARATE_SUB_PRICE = exProperties.getProperty("SeparateSubPrice", 100);
        SERVICES_SEPARATE_SUB_ITEM = exProperties.getProperty("SeparateSubItem", 4037);
        SERVICES_SEPARATE_SUB_MIN_LEVEL = exProperties.getProperty("SeparateSubMinLevel", 1);
        SERVICES_CHANGE_NICK_COLOR_ENABLED = exProperties.getProperty("NickColorChangeEnabled", false);
        SERVICES_CHANGE_NICK_COLOR_PRICE = exProperties.getProperty("NickColorChangePrice", new int[]{100});
        SERVICES_CHANGE_NICK_COLOR_ITEM = exProperties.getProperty("NickColorChangeItem", new int[]{100});
        SERVICES_CHANGE_NICK_COLOR_LIST = exProperties.getProperty("NickColorChangeList", new String[]{"00FF00"});
        SERVICES_CHANGE_TITLE_COLOR_ENABLED = exProperties.getProperty("TitleColorChangeEnabled", false);
        SERVICES_CHANGE_TITLE_COLOR_LIST = exProperties.getProperty("TitleColorChangeList", new String[]{"00FF00"});
        SERVICES_CHANGE_TITLE_COLOR_ITEM = exProperties.getProperty("TitleColorChangeItem", new int[]{4037});
        SERVICES_CHANGE_TITLE_COLOR_PRICE = exProperties.getProperty("TitleColorChangePrice", new int[]{100});
        SERVICE_CAPTCHA_BOT_CHECK = exProperties.getProperty("CaptchaBotCheckService", false);
        CAPTCHA_MONSTER_KILLS_THRESHOLD = exProperties.getProperty("CaptchaMonsterCheckThreshold", 100);
        CAPTCHA_PENALTY_SKILL_ID = exProperties.getProperty("CaptchaPenaltySkillId", 5098);
        CAPTCHA_PENALTY_SKILL_LEVEL = exProperties.getProperty("CaptchaPenaltySkillLevel", 1);
        SERVICES_RATE_ENABLED = exProperties.getProperty("RateBonusEnabled", false);
        SERVICES_RATE_COMMAND_ENABLED = exProperties.getProperty("RateBonusVoiceCommandEnabled", false);
        SERVICES_RATE_EXPIRE_TIME_AT_ENTER_WORLD = exProperties.getProperty("RateBonusReportExpireTime", false);
        SERVICES_RATE_BONUS_INFO = SERVICES_RATE_ENABLED ? Config.a() : new RateBonusInfo[]{};
        SERVICES_NOBLESS_SELL_ENABLED = exProperties.getProperty("NoblessSellEnabled", false);
        SERVICES_NOBLESS_SELL_WITHOUT_SUBCLASS = exProperties.getProperty("NoblessSellWithoutSubClass", true);
        SERVICES_NOBLESS_SELL_PRICE = exProperties.getProperty("NoblessSellPrice", 1000);
        SERVICES_NOBLESS_SELL_ITEM = exProperties.getProperty("NoblessSellItem", 4037);
        NOBLESS_LEVEL_FOR_SELL = exProperties.getProperty("NoblessLevelForSell", 76);
        SERVICES_ITEM_NOBLESS_SELL_ENABLED = exProperties.getProperty("ItemNoblessSellEnabled", false);
        SERVICES_ITEM_NOBLESS_SELL_ITEM_ID = exProperties.getProperty("ItemNoblessSellItemId", new int[]{6641});
        SERVICES_ITEM_NOBLESS_SELL_WITHOUT_SUBCLASS = exProperties.getProperty("ItemNoblessSellWithoutSubClass", true);
        NOBLESS_ITEM_LEVEL_FOR_SELL = exProperties.getProperty("ItemNoblessLevelForSell", 76);
        SERVICES_CLAN_MAX_SELL_LEVEL = exProperties.getProperty("ClanMaxLevelForSell", 8);
        SERVICES_CLANLEVEL_SELL_ENABLED = exProperties.getProperty("ClanLevelSellEnabled", false);
        SERVICES_CLANLEVEL_SELL_ITEM = exProperties.getProperty("ClanLevelSellItem", new int[]{4037, 4037, 4037, 4037, 4037, 4037, 4037});
        SERVICES_CLANLEVEL_SELL_PRICE = exProperties.getProperty("ClanLevelSellPrice", new long[]{100L, 200L, 300L, 400L, 500L, 600L, 700L});
        SERVICES_CLAN_REPUTATION_ENABLE = exProperties.getProperty("ClanReputationSellEnable", false);
        SERVICES_CLAN_REPUTATION_ITEM_ID = exProperties.getProperty("ClanReputationItemId", 4037);
        SERVICES_CLAN_REPUTATION_ITEM_COUNT = exProperties.getProperty("ClanReputationItemCount", 1000);
        SERVICES_CLAN_REPUTATION_AMOUNT = exProperties.getProperty("ClanReputationAmountAdd", 10);
        SERVICES_CHAR_RECOMMENDATION_ENABLE = exProperties.getProperty("CharRecommendationSellEnable", false);
        SERVICES_CHAR_RECOMMENDATION_ITEM_ID = exProperties.getProperty("CharRecommendationItemId", 4037);
        SERVICES_CHAR_RECOMMENDATION_ITEM_COUNT = exProperties.getProperty("CharRecommendationItemCount", 1000);
        SERVICES_CHAR_RECOMMENDATION_AMOUNT = exProperties.getProperty("CharRecommendationAmountAdd", 1);
        ITEM_CLAN_REPUTATION_ENABLE = exProperties.getProperty("ClanReputationItemEnable", false);
        ITEM_CLAN_REPUTATION_ID = exProperties.getProperty("ClanReputationItemHandlerId", new int[]{9299});
        ITEM_CLAN_REPUTATION_AMOUNT = exProperties.getProperty("ClanReputationItemAmount", new int[]{100});
        ITEM_PREMIUM_ACCOUNT_ENABLE = exProperties.getProperty("PremiumAccountItemEnable", false);
        ITEM_PREMIUM_ACCOUNT_ITEM_ID = exProperties.getProperty("PremiumAccountItemHandlerId", new int[]{6635});
        ITEM_PREMIUM_ACCOUNT_BONUS_ID = exProperties.getProperty("PremiumAccountBonusId", new int[]{1});
        SERVICES_CLANHELPER_ENABLED = exProperties.getProperty("ClanHelperEnable", false);
        SERVICES_CLANHELPER_CONFIG = exProperties.getProperty("ClanHelperRewardParameters", "1=2:2;2=2:3,[-200-10000,57-100500];3=2:4,[-200-20000]");
        SERVICES_CLANHELPER_ADD_FULL_CLAN_SKILLS = exProperties.getProperty("ClanHelperAddFullSkills", false);
        SERVICES_CLAN_BUFF_ENABLED = exProperties.getProperty("ClanBuffServiceEnable", false);
        SERVICES_CLAN_BUFF_SKILL_ID = exProperties.getProperty("ClanBuffServiceBuffId", 9200);
        SERVICES_CLAN_BUFF_LEVELS_MIN_ONLINE = exProperties.getProperty("ClanBuffServiceOnlineLevelUp", "10-1;20-2;30-3");
        SERVICES_CLAN_BUFF_TASK_DELAY = exProperties.getProperty("ClanBuffServiceTaskDelay", 5);
        SERVICES_CLANSKILL_SELL_ENABLED = exProperties.getProperty("ClanSkillsSellEnable", false);
        SERVICES_CLAN_SKILL_SELL_ITEM = exProperties.getProperty("ClanSkillsSellItemId", 4037);
        SERVICES_CLAN_SKILL_SELL_PRICE = exProperties.getProperty("ClanSkillsSellItemCount", 1000);
        SERVICES_CLANSKIL_SELL_MIN_LEVEL = exProperties.getProperty("ClanSkillMinLevel", 8);
        SERVICES_DELEVEL_SELL_ENABLED = exProperties.getProperty("DelevelSellEnabled", false);
        SERVICES_DELEVEL_SELL_PRICE = exProperties.getProperty("DelevelSellPrice", 1000);
        SERVICES_DELEVEL_SELL_ITEM = exProperties.getProperty("DelevelSellItem", 4037);
        SERVICES_DELEVEL_REDUCTION = exProperties.getProperty("DelevelReduction", 2);
        SERVICES_DELEVEL_ALLOW_FOR_SUBCLASS = exProperties.getProperty("DelevelAllowForSubClass", true);
        SERVICE_RESET_LEVEL_ENABLED = exProperties.getProperty("ResetLevelService", false);
        SERVICE_RESET_LEVEL_NEED = exProperties.getProperty("ResetLevelServiceLevelNeed", 80);
        SERVICE_RESET_LEVEL_REMOVE = exProperties.getProperty("ResetLevelServiceLevelRemove", 79);
        SERVICE_RESET_LEVEL_ITEM_ID = exProperties.getProperty("ResetLevelServiceRewardId", new int[]{57});
        SERVICE_RESET_LEVEL_ITEM_COUNT = exProperties.getProperty("ResetLevelServiceRewardCount", new int[]{100});
        SERVICE_RESET_LEVEL_LIMIT = exProperties.getProperty("ResetLevelServiceCountLimit", -1);
        SERVICE_RESET_REUSE_DELAY = (long)(exProperties.getProperty("ResetLevelServiceReuseDelay", 60) * 60) * 1000L;
        SERVICE_RESET_ALLOW_FOR_SUBCLASS = exProperties.getProperty("ResetLevelServiceAllowForSubclass", false);
        SERVICES_LEVEL_UP_SELL_ENABLED = exProperties.getProperty("LevelUpSellEnabled", false);
        SERVICES_LEVEL_UP_SELL_PRICE = exProperties.getProperty("LevelUpSellPrice", 1000);
        SERVICES_LEVEL_UP_SELL_ITEM = exProperties.getProperty("LevelUpSellItem", 4037);
        SERVICES_LEVEL_UP_COUNT = exProperties.getProperty("LevelUpForPurchasing", 1);
        SERVICES_LEVEL_MAX_LEVEL_FOR_MAIN_CLASS = exProperties.getProperty("LevelUpMaxLevelForMainClass", 80);
        SERVICES_LEVEL_MAX_LEVEL_FOR_SUB_CLASS = exProperties.getProperty("LevelUpMaxLevelForSubClass", 80);
        SERVICES_HERO_SELL_ENABLED = exProperties.getProperty("HeroSellEnabled", false);
        SERVICE_HERO_ALLOW_ON_SUB_CLASS = exProperties.getProperty("HeroSellAllowOnSubclass", false);
        SERVICE_HERO_ALLOW_WITHOUT_NOBLE = exProperties.getProperty("HeroSellAllowWithoutNoble", false);
        SERVICES_HERO_SELLER_ITEM_IDS = exProperties.getProperty("HeroSellItemIds", new int[]{4037});
        SERVICES_HERO_SELLER_ITEM_COUNTS = exProperties.getProperty("HeroSellItemCounts", new long[]{1000L});
        SERVICE_HERO_STATUS_DURATIONS = exProperties.getProperty("HeroSellDays", new long[]{1L});
        SERVICES_OLY_POINTS_RESET = exProperties.getProperty("ServiceOlyPointsReset", false);
        SERVICES_OLY_POINTS_RESET_ITEM_ID = exProperties.getProperty("ServiceOlyPointsResetItemId", 4037);
        SERVICES_OLY_POINTS_RESET_ITEM_AMOUNT = exProperties.getProperty("ServiceOlyPointsResetItemAmount", 1000);
        SERVICES_OLY_POINTS_THRESHOLD = exProperties.getProperty("ServiceOlyPointsResetThreshold", 18);
        SERVICES_OLY_POINTS_REWARD = exProperties.getProperty("ServiceOlyPointsResetAmount", 18);
        SERVICES_PK_CLEAN_ENABLED = exProperties.getProperty("PKCleanEnabled", false);
        SERVICES_PK_CLEAN_SELL_ITEM = exProperties.getProperty("PKCleanItemId", 4037);
        SERVICES_PK_CLEAN_SELL_PRICE = exProperties.getProperty("PKCleanPrice", 1000);
        SERVICE_PROMOCODE_ENABLED = exProperties.getProperty("PromoCodeServiceEnable", false);
        SERVICE_PROMOCODE_NEW_SYSTEM = exProperties.getProperty("PromoCodeServiceNewVersion", true);
        SERVICE_PROMOCODE_COMMAND_ENABLED = exProperties.getProperty("PromoCodeCommandEnable", false);
        STARTING_PREMIUM = exProperties.getProperty("StartingAccountReward", false);
        STARTING_PREMIUM_PROMO_NAME = exProperties.getProperty("StartingAccountRewardCodeName", "STARTER_PREMIUM");
        SERVICES_PK_ANNOUNCE = exProperties.getProperty("PvPPkKillAnnounce", 0);
        SERVICES_PVP_RANKING_BONUS = exProperties.getProperty("PvPBonusRanking", false);
        SERVICES_PVP_RANKING_BONUS_COMMAND = exProperties.getProperty("PvPBonusRankingInfoCommand", false);
        SERVICES_ALLOW_WYVERN_RIDE = exProperties.getProperty("AllowRideWyvernService", false);
        SERVICES_WYVERN_RIDE_NPC_ID = exProperties.getProperty("RideWyvernServiceNpcId", new int[]{30827});
        SERVICES_WYVERN_ITEM_ID = exProperties.getProperty("RideWyvernServiceItemId", 4037);
        SERVICES_KARMA_CLEAN_ENABLED = exProperties.getProperty("KarmaCleanServiceEnabled", false);
        SERVICES_KARMA_CLEAN_SELL_ITEM = exProperties.getProperty("KarmaCleanItemId", 4037);
        SERVICES_KARMA_CLEAN_SELL_PRICE = exProperties.getProperty("KarmaCleanPrice", 1000);
        SERVICES_EXPAND_INVENTORY_ENABLED = exProperties.getProperty("ExpandInventoryEnabled", false);
        SERVICES_EXPAND_INVENTORY_PRICE = exProperties.getProperty("ExpandInventoryPrice", 1000);
        SERVICES_EXPAND_INVENTORY_ITEM = exProperties.getProperty("ExpandInventoryItem", 4037);
        SERVICES_EXPAND_INVENTORY_MAX = exProperties.getProperty("ExpandInventoryMax", 250);
        SERVICES_EXPAND_INVENTORY_SLOT_AMOUNT = exProperties.getProperty("ExpandInventorySlotAmount", 1);
        SERVICES_EXPAND_WAREHOUSE_ENABLED = exProperties.getProperty("ExpandWarehouseEnabled", false);
        SERVICES_EXPAND_WAREHOUSE_PRICE = exProperties.getProperty("ExpandWarehousePrice", 1000);
        SERVICES_EXPAND_WAREHOUSE_ITEM = exProperties.getProperty("ExpandWarehouseItem", 4037);
        SERVICES_EXPAND_WAREHOUSE_MAX = exProperties.getProperty("ExpandWarehouseMax", 400);
        SERVICES_EXPAND_WAREHOUSE_SLOT_AMOUNT = exProperties.getProperty("ExpandWarehouseSlotAmount", 1);
        SERVICES_EXPAND_CWH_ENABLED = exProperties.getProperty("ExpandCWHEnabled", false);
        SERVICES_EXPAND_CWH_PRICE = exProperties.getProperty("ExpandCWHPrice", 1000);
        SERVICES_EXPAND_CWH_ITEM = exProperties.getProperty("ExpandCWHItem", 4037);
        SERVICES_EXPAND_CWH_SLOT_AMOUNT = exProperties.getProperty("ExpandCWHSlotAmount", 1);
        SEVICES_EXPAND_CWH_SLOT_LIMITS = exProperties.getProperty("ExpandCWHSlotLimit", 100);
        SERVICES_AUTO_HEAL_ACTIVE = exProperties.getProperty("AutoHealActive", false);
        SERVICES_SELLPETS = exProperties.getProperty("SellPets", "");
        SERVICES_OFFLINE_TRADE_ALLOW = exProperties.getProperty("AllowOfflineTrade", false);
        ALLOW_TRADE_ON_THE_MOVE = exProperties.getProperty("AllowTradeOnTheMove", true);
        SERVICES_OFFLINE_TRADE_ALLOW_OFFSHORE = exProperties.getProperty("AllowOfflineTradeOnlyOffshore", true);
        SERVICES_OFFLINE_TRADE_MIN_LEVEL = exProperties.getProperty("OfflineMinLevel", 0);
        SERVICES_OFFLINE_TRADE_NAME_COLOR = Integer.decode("0x" + exProperties.getProperty("OfflineTradeNameColor", "B0FFFF"));
        SERVICES_OFFLINE_TRADE_NAME_COLOR_CHANGE = exProperties.getProperty("OfflineTradeNameColorChange", false);
        SERVICES_OFFLINE_TRADE_ABNORMAL = AbnormalEffect.getByName(exProperties.getProperty("OfflineTradeAbnormalEffectName", "null"));
        SERVICES_OFFLINE_TRADE_PRICE_ITEM = exProperties.getProperty("OfflineTradePriceItem", 0);
        SERVICES_OFFLINE_TRADE_PRICE = exProperties.getProperty("OfflineTradePrice", 0);
        SERVICES_OFFLINE_TRADE_SECONDS_TO_KICK = (long)exProperties.getProperty("OfflineTradeDaysToKick", 14) * 86400L;
        SERVICES_OFFLINE_TRADE_RESTORE_AFTER_RESTART = exProperties.getProperty("OfflineRestoreAfterRestart", true);
        SERVICES_NO_TRADE_ONLY_OFFLINE = exProperties.getProperty("NoTradeOnlyOffline", false);
        SERVICES_TRADE_TAX = exProperties.getProperty("TradeTax", 0.0);
        SERVICES_OFFSHORE_TRADE_TAX = exProperties.getProperty("OffshoreTradeTax", 0.0);
        SERVICES_TRADE_TAX_ONLY_OFFLINE = exProperties.getProperty("TradeTaxOnlyOffline", false);
        SERVICES_OFFSHORE_NO_CASTLE_TAX = exProperties.getProperty("NoCastleTaxInOffshore", false);
        SERVICES_TRADE_ONLY_FAR = exProperties.getProperty("TradeOnlyFar", false);
        SERVICES_TRADE_MIN_LEVEL = exProperties.getProperty("MinLevelForTrade", 0);
        SERVICES_TRADE_RADIUS = exProperties.getProperty("TradeRadius", 30);
        SERVICES_GIRAN_HARBOR_ENABLED = exProperties.getProperty("GiranHarborZone", false);
        SERVICES_GIRAN_HARBOR_NPC_ID = exProperties.getProperty("GiranHarborZoneNpcId", new int[]{30059, 30080, 30177, 30233, 30256, 30320, 30848, 30878, 30899, 31210, 31275, 31320, 31964, 30006, 30134, 30146, 30576, 30540});
        SERVICES_GIRAN_HARBOR_EXIT_NPC_ID = exProperties.getProperty("GiranHarborZoneExitNpcId", new int[]{40030});
        SERVICES_GIRAN_HARBOR_NOTAX = exProperties.getProperty("GiranHarborNoTax", false);
        SERVICES_GIRAN_HARBOR_PRICE = exProperties.getProperty("GiranHarborTelePrice", 5000);
        SERVICES_GIRAN_HARBOR_MP_REG_PRICE = exProperties.getProperty("GiranHarborMpRegPricePerUnit", 5);
        SERVICES_ALLOW_LOTTERY = exProperties.getProperty("AllowLottery", false);
        SERVICE_LOTTERY_ITEM_ID = exProperties.getProperty("LotteryItemId", 57);
        SERVICES_LOTTERY_PRIZE = exProperties.getProperty("LotteryPrize", 50000);
        SERVICES_ALT_LOTTERY_PRICE = exProperties.getProperty("AltLotteryPrice", 2000);
        SERVICES_LOTTERY_TICKET_PRICE = exProperties.getProperty("LotteryTicketPrice", 2000);
        SERVICES_LOTTERY_5_NUMBER_RATE = exProperties.getProperty("Lottery5NumberRate", 0.6);
        SERVICES_LOTTERY_4_NUMBER_RATE = exProperties.getProperty("Lottery4NumberRate", 0.4);
        SERVICES_LOTTERY_3_NUMBER_RATE = exProperties.getProperty("Lottery3NumberRate", 0.2);
        SERVICES_LOTTERY_2_AND_1_NUMBER_PRIZE = exProperties.getProperty("Lottery2and1NumberPrize", 200);
        SERVICES_LOTTERY_END_CRON = exProperties.getProperty("LotteryCronPeriod", "0 19 * * 7");
        SERVICE_MONSTER_RACE_ENABLED = exProperties.getProperty("MonsterRaceEnabled", false);
        SERVICE_MONSTER_RACE_CURRENCY = exProperties.getProperty("MonsterRaceCurrency", 57);
        SERVICE_MONSTER_RACE_TICKET_ID = exProperties.getProperty("MonsterRaceTicketId", 4443);
        SERVICE_MONSTER_RACE_ALLOWED_ZONES = exProperties.getProperty("MonsterRaceAllowedZones", new String[]{"[dion_monster_race1]", "[dion_monster_race3]", "[dion_monster_race4]", "[dion_monster_race5]", "[dion_monster_race6]", "[dion_monster_race7]", "[dion_monster_race8]"});
        SERVICE_MONSTER_RACE_BID = exProperties.getProperty("MonsterRaceBidList", new int[]{100, 500, 1000, 5000, 10000, 20000, 50000, 100000});
        SERVICES_ALLOW_ROULETTE = exProperties.getProperty("AllowRoulette", false);
        SERVICES_ROULETTE_ITEM_ID = exProperties.getProperty("RouletteItemId", 57);
        SERVICES_ROULETTE_MIN_BET = exProperties.getProperty("RouletteMinBet", 1);
        SERVICES_ROULETTE_MAX_BET = exProperties.getProperty("RouletteMaxBet", Integer.MAX_VALUE);
        SERVICES_ROULETTE_NPC_ID = exProperties.getProperty("RouletteNpcId", new int[]{30990, 30991, 30992, 30993, 30994});
        SERVICES_ENABLE_NO_CARRIER = exProperties.getProperty("EnableNoCarrier", false);
        SERVICES_NO_CARRIER_MIN_TIME = exProperties.getProperty("NoCarrierMinTime", 0);
        SERVICES_NO_CARRIER_MAX_TIME = exProperties.getProperty("NoCarrierMaxTime", 90);
        SERVICES_NO_CARRIER_DEFAULT_TIME = exProperties.getProperty("NoCarrierDefaultTime", 60);
        DISCONNECTED_PLAYER_TITLE = exProperties.getProperty("NoCarrierCharTitle", "DISCONNECTED");
        DISCONNECTED_PLAYER_TITLE_COLOR = Integer.decode("0x" + exProperties.getProperty("NoCarrierCharTitleColor", "FF0000"));
        NO_CARRIER_PROTECTION = exProperties.getProperty("NoCarrierProtection", false);
        NO_CARRIER_PROTECTION_TIME = exProperties.getProperty("NoCarrierProtectionTime", 10);
        SERVICES_HPACP_ENABLE = exProperties.getProperty("ACPServiceEnableHP", false);
        SERVICES_HPACP_WORK_IN_PEACE_ZONE = exProperties.getProperty("ACPServiceWokAtPeaceZone", false);
        SERVICES_HPACP_MIN_PERCENT = Math.min(Math.max(0, exProperties.getProperty("ACPServiceHPMinPercent", 10)), 100);
        SERVICES_HPACP_MAX_PERCENT = Math.min(Math.max(0, exProperties.getProperty("ACPServiceHPMaxPercent", 90)), 100);
        SERVICES_HPACP_DEF_PERCENT = Math.min(Math.max(SERVICES_HPACP_MIN_PERCENT, exProperties.getProperty("ACPServiceHPDefaultPercent", 50)), SERVICES_HPACP_MAX_PERCENT);
        SERVICES_HPACP_POTIONS_ITEM_IDS = exProperties.getProperty("ACPServiceHPItemIds", new int[]{1539, 1540, 8627});
        ENABLE_ACP_ON_CHARACTER_CREATE = exProperties.getProperty("ACPServiceEnableOnCharacterCreate", false);
        SERVICES_HPACP_AVAILABLE_ONLY_PREMIUM = exProperties.getProperty("ACPServiceAvailableOnlyPremium", false);
        SERVICES_ONLINE_COMMAND_ENABLE = exProperties.getProperty("OnlineCommandForAll", false);
        SERVICE_COMMAND_MULTIPLIER = exProperties.getProperty("OnlineCommandForAllMultiplier", 1.0);
        SERVICES_BANKING_ENABLED = exProperties.getProperty("BankingServiceEnabled", false);
        SERVICES_DEPOSIT_ITEM_ID_NEEDED = exProperties.getProperty("DepositItemIdRemove", 57);
        SERVICES_DEPOSIT_ITEM_COUNT_NEEDED = exProperties.getProperty("DepositItemCountRemove", 1000000);
        SERVICES_DEPOSIT_ITEM_ID_GIVED = exProperties.getProperty("DepositItemIdGive", 6673);
        SERVICES_DEPOSIT_ITEM_COUNT_GIVED = exProperties.getProperty("DepositCountGive", 1);
        SERVICES_WITHDRAW_ITEM_ID_NEEDED = exProperties.getProperty("WithdrawItemIdRemove", 6673);
        SERVICES_WITHDRAW_ITEM_COUNT_NEEDED = exProperties.getProperty("WithdrawItemCountRemove", 1);
        SERVICES_WITHDRAW_ITEM_ID_GIVED = exProperties.getProperty("WithdrawItemIdGive", 57);
        SERVICES_WITHDRAW_ITEM_COUNT_GIVED = exProperties.getProperty("WithdrawCountGive", 1000000);
        SERVICES_CLAN_SUMMON_COMMAND_ENABLE = exProperties.getProperty("ClanLeaderSummonCommand", false);
        SERVICES_CLAN_SUMMON_COMMAND_SELL_ITEM = exProperties.getProperty("ClanLeaderSummonItemId", 4037);
        SERVICES_CLAN_SUMMON_COMMAND_SELL_PRICE = exProperties.getProperty("ClanLeaderSummonItemCount", 1);
        SERVICES_CLAN_SUMMON_COMMAND_SUMMON_CRYSTAL_COUNT = exProperties.getProperty("ClanSummonCrystalCount", 1);
        REUSE_DELAY_FOR_CLAN_SUMMON = exProperties.getProperty("ClanSummonReuseDelay", 30) * 60;
        SERVICE_OPEN_BOX_ITEMS_ID = exProperties.getProperty("OpenBoxServiceItemsId", ArrayUtils.EMPTY_INT_ARRAY);
        SERVICE_OPEN_BOX_COMMAND_NAME = exProperties.getProperty("OpenBoxServiceCommandName", new String[]{"open"}, ",");
        SERVICES_WHOIAM_COMMAND_ENABLE = exProperties.getProperty("WhoiameCommandForAll", true);
        SERVICES_CPACP_ENABLE = exProperties.getProperty("ACPServiceEnableCP", false);
        SERVICES_CPACP_MIN_PERCENT = Math.min(Math.max(0, exProperties.getProperty("ACPServiceCPMinPercent", 10)), 100);
        SERVICES_CPACP_MAX_PERCENT = Math.min(Math.max(0, exProperties.getProperty("ACPServiceCPMaxPercent", 90)), 100);
        SERVICES_CPACP_DEF_PERCENT = Math.min(Math.max(SERVICES_CPACP_MIN_PERCENT, exProperties.getProperty("ACPServiceCPDefaultPercent", 50)), SERVICES_CPACP_MAX_PERCENT);
        SERVICES_CPACP_POTIONS_ITEM_IDS = exProperties.getProperty("ACPServiceCPItemIds", new int[]{5592, 5591});
        SERVICES_MPACP_ENABLE = exProperties.getProperty("ACPServiceEnableMP", false);
        SERVICES_MPACP_MIN_PERCENT = Math.min(Math.max(0, exProperties.getProperty("ACPServiceMPMinPercent", 10)), 100);
        SERVICES_MPACP_MAX_PERCENT = Math.min(Math.max(0, exProperties.getProperty("ACPServiceMPMaxPercent", 90)), 100);
        SERVICES_MPACP_DEF_PERCENT = Math.min(Math.max(SERVICES_MPACP_MIN_PERCENT, exProperties.getProperty("ACPServiceMPDefaultPercent", 50)), SERVICES_MPACP_MAX_PERCENT);
        SERVICES_MPACP_POTIONS_ITEM_IDS = exProperties.getProperty("ACPServiceMPItemIds", new int[]{728});
        SERVICES_BOSS_STATUS_ENABLE = exProperties.getProperty("BossStatusServiceEnable", false);
        SERVICES_BOSS_STATUS_ADDITIONAL_IDS = exProperties.getProperty("BossStatusAdditionalIds", new int[]{29001, 29006, 29014, 29022});
        SERVICES_BOSS_STATUS_FORMAT = exProperties.getProperty("BossStatusRespawnFormat", "HH:mm dd.MM.yyyy");
        SERVICES_RAID_STATUS_ENABLE = exProperties.getProperty("RaidStatusServiceEnable", false);
        SERVICES_RAID_STATUS_ADDITIONAL_IDS = exProperties.getProperty("RaidStatusAdditionalIds", new int[]{29001});
        SERVICES_RAID_STATUS_FORMAT = exProperties.getProperty("RaidStatusRespawnFormat", "HH:mm dd.MM.yyyy");
        SERVICES_RAID_STATUS_BOSS_PER_PAGE = exProperties.getProperty("RaidStatusAmountPerPage", 18);
        SERVICES_PVP_PK_STATISTIC = exProperties.getProperty("TopPvPPKStatistic", false);
        SERVICE_AUTO_ANNOUNCE = exProperties.getProperty("ServiceAutoAnnounce", false);
        PVP_PK_STAT_CACHE_UPDATE_INTERVAL = (long)(exProperties.getProperty("TopPvPPKCacheUpdateTimeInterval", 1) * 60) * 1000L;
        PVP_PK_STAT_RECORD_LIMIT = exProperties.getProperty("TopPvPPKRecordLimit", 15);
        SERVICES_TOP_CLANS_STATISTIC = exProperties.getProperty("ClanCustomPointsStatistic", false);
        SERVICES_TOP_CLANS_STATISTIC_LIST = exProperties.getProperty("ClanCustomStatisticCount", 15);
        EVENT_CLAN_CASTLE_POINTS = new LinkedHashMap<Integer, Integer>();
        Object object = new StringTokenizer(exProperties.getProperty("ClanCastleCustomPoints", ""), ";,");
        while (((StringTokenizer)object).hasMoreTokens()) {
            stringArray = ((StringTokenizer)object).nextToken().split(":");
            EVENT_CLAN_CASTLE_POINTS.put(Integer.parseInt(stringArray[0]), Integer.parseInt(stringArray[1]));
        }
        EVENT_CLAN_HERO_POINTS = exProperties.getProperty("ClanHeroCustomPoints", 0);
        EVENT_CLAN_ACADEMY_POINTS = exProperties.getProperty("ClanAcademyCustomPoints", 0);
        EVENT_CLAN_WAR_POINTS = exProperties.getProperty("ClanWarCustomPoints", 0);
        SERVICES_TOP_CLANS_STATISTIC_ANNOUNCE = exProperties.getProperty("ClanCustomPointsAnnounce", true);
        EVENT_CLAN_RAID_BOSS_POINTS = new LinkedHashMap<Integer, Integer>();
        object = new StringTokenizer(exProperties.getProperty("ClanRaidBossCustomPoints", ""), ";,");
        while (((StringTokenizer)object).hasMoreTokens()) {
            stringArray = ((StringTokenizer)object).nextToken().split(":");
            EVENT_CLAN_RAID_BOSS_POINTS.put(Integer.parseInt(stringArray[0]), Integer.parseInt(stringArray[1]));
        }
        PAWNSHOP_ENABLED = exProperties.getProperty("PawnShopEnabled", true);
        object = new ArrayList();
        for (String string : exProperties.getProperty("PawnShopItemClasses", new String[]{"WEAPON", "ARMOR", "JEWELRY"})) {
            object.add(ItemTemplate.ItemClass.valueOf(string));
        }
        PAWNSHOP_ITEMS_CLASSES = object.toArray(new ItemTemplate.ItemClass[object.size()]);
        PAWNSHOP_ITEMS_PER_PAGE = exProperties.getProperty("PawnShopItemsPerPage", 5);
        PAWNSHOP_MIN_ENCHANT_WEAPON_LEVEL = exProperties.getProperty("PawnShopMinEnchantWeaponLevel", 0);
        PAWNSHOP_MIN_ENCHANT_ARMOR_LEVEL = exProperties.getProperty("PawnShopMinEnchantArmorLevel", 0);
        PAWNSHOP_MIN_ENCHANT_ACCESSORY_LEVEL = exProperties.getProperty("PawnShopMinEnchantAccessoryLevel", 0);
        PAWNSHOP_ALLOW_SELL_AUGMENTED_ITEMS = exProperties.getProperty("PawnShopAllowTradeAugmentedItems", false);
        PAWNSHOP_MIN_GRADE = exProperties.getProperty("PawnShopMinGrade", "A").toUpperCase();
        PAWNSHOP_CURRENCY_ITEM_IDS = exProperties.getProperty("PawnShopCurrencyItemIds", new int[]{57});
        PAWNSHOP_MIN_QUERY_LENGTH = exProperties.getProperty("PawnShopMinQueryLength", 3);
        PAWNSHOP_PRICE_SORT = exProperties.getProperty("PawnShopPriceSort", true);
        PAWNSHOP_TAX_ITEM_ID = exProperties.getProperty("PawnShopTaxItemId", 57);
        PAWNSHOP_TAX_ITEM_COUNT = exProperties.getProperty("PawnShopTaxItemCount", 2000L);
        PAWNSHOP_REFUND_ITEM_ID = exProperties.getProperty("PawnShopRefundItemId", 57);
        PAWNSHOP_TAX_AUGMENTED_ITEM_ID = exProperties.getProperty("PawnShopAugmentedTaxItemId", 57);
        PAWNSHOP_TAX_AUGMENTED_ITEM_COUNT = exProperties.getProperty("PawnShopAugmentedTaxItemCount", 2000L);
        PAWNSHOP_REFUND_ITEM_COUNT = exProperties.getProperty("PawnShopRefundItemCount", 1000L);
        PAWNSHOP_TAX_AUGMENTED_ITEM_PERCENT = exProperties.getProperty("PawnShopAugmentedTaxPercent", 0.0);
        PAWNSHOP_TAX_ITEM_PERCENT = exProperties.getProperty("PawnShopTaxPercent", 0.0);
        PAWNSHOP_PROHIBITED_ITEM_IDS = exProperties.getProperty("PawnShopProhibitedItemIds", new int[]{6611, 6612, 6613, 6614, 6615, 6616, 6617, 6618, 6619, 6620, 6621, 6842});
        PAWNSHOP_ITEM_TERM = exProperties.getProperty("PawnShopItemTerm", 14);
        ITEM_BROKER_ITEM_SEARCH = exProperties.getProperty("UseItemBrokerItemSearch", false);
        ITEM_BROKER_UPDATE_TIME = (long)(exProperties.getProperty("BrokerItemListTimeUpdate", 2) * 60) * 1000L;
        ITEM_BROKER_NPC_ID = exProperties.getProperty("ItemBrokerNpcId", new int[]{31732, 31833, 31838, 31829, 31991, 31805});
        AUGMENTATION_TRANSFER_ENABLE = exProperties.getProperty("AugmentationTransferServiceEnabled", false);
        AUGMENTATION_TRANSFER_REQUIRED_ITEM_ID = exProperties.getProperty("AugmentationTransferServiceItemId", 4037);
        AUGMENTATION_TRANSFER_REQUIRED_ITEM_COUNT = exProperties.getProperty("AugmentationTransferServiceItemCount", 10);
        AUGMENTATION_TRANSFER_MAX_LIST_LENGTH = exProperties.getProperty("AugmentationTransferServiceItemListLimit", 15);
        AUGMENTATION_TRANSFER_PROHIBITED_ITEM_IDS = exProperties.getProperty("AugmentationTransferServiceProhibitedItemIds", new int[]{6611, 6612, 6613, 6614, 6615, 6616, 6617, 6618, 6619, 6620, 6621, 6842});
        ALLOW_EVENT_GATEKEEPER = exProperties.getProperty("AllowEventGatekeeper", false);
        ALLOW_GLOBAL_GK = exProperties.getProperty("AllowSpawnGlobalGatekeeper", false);
        ALLOW_BUFFER = exProperties.getProperty("AllowSpawnBuffer", false);
        ALLOW_GMSHOP = exProperties.getProperty("AllowSpawnGMShop", false);
        ALLOW_AUCTIONER = exProperties.getProperty("AllowSpawnAuctioner", false);
        ALLOW_GLOBAL_SERVICES = exProperties.getProperty("AllowSpawnGlobalServices", false);
        ALLOW_PVP_EVENT_MANAGER = exProperties.getProperty("AllowSpawnPvPEventManager", false);
        ALLOW_WEDDING = exProperties.getProperty("AllowWedding", false);
        WEDDING_PRICE = exProperties.getProperty("WeddingPrice", 500000);
        WEDDING_ITEM_ID_PRICE = exProperties.getProperty("WeddingPriceItemId", 57);
        WEDDING_PUNISH_INFIDELITY = exProperties.getProperty("WeddingPunishInfidelity", true);
        WEDDING_TELEPORT = exProperties.getProperty("WeddingTeleport", true);
        WEDDING_TELEPORT_PRICE = exProperties.getProperty("WeddingTeleportPrice", 500000);
        WEDDING_TELEPORT_INTERVAL = exProperties.getProperty("WeddingTeleportInterval", 120);
        WEDDING_SAMESEX = exProperties.getProperty("WeddingAllowSameSex", true);
        WEDDING_FORMALWEAR = exProperties.getProperty("WeddingFormalWear", true);
        WEDDING_DIVORCE_COSTS = exProperties.getProperty("WeddingDivorceCosts", 20);
        WEDDING_GIVE_SALVATION_BOW = exProperties.getProperty("GiveWeddingBow", true);
        WEDDING_ANNOUNCE = exProperties.getProperty("WeddingAnnouncement", false);
        WEDDING_USE_COLOR = exProperties.getProperty("WeddingUseNickColor", false);
        WEDDING_NORMAL_COLOR = Integer.decode("0x" + exProperties.getProperty("WeddingNormalCoupleNickColor", "BF0000"));
        WEDDING_GAY_COLOR = Integer.decode("0x" + exProperties.getProperty("WeddingGayCoupleNickColor", "0000BF"));
        WEDDING_LESBIAN_COLOR = Integer.decode("0x" + exProperties.getProperty("WeddingLesbianCoupleNickColor", "BF00BF"));
        L2TOPRU_DELAY = exProperties.getProperty("L2TopRuDelay", 0) * 1000 * 60;
        L2TOPRU_PREFIX = exProperties.getProperty("L2TopRuServerPrefix");
        L2TOPRU_WEB_VOTE_URL = exProperties.getProperty("L2TopRuWebURL");
        L2TOPRU_SMS_VOTE_URL = exProperties.getProperty("L2TopRuSMSURL");
        L2TOPRU_WEB_REWARD_ITEMID = exProperties.getProperty("L2TopRuWebRewardItemId", 6673);
        L2TOPRU_WEB_REWARD_ITEMCOUNT = exProperties.getProperty("L2TopRuWebRewardItemCount", 1);
        L2TOPRU_SMS_REWARD_ITEMID = exProperties.getProperty("L2TopRuSMSRewardItemId", 6673);
        L2TOPRU_SMS_REWARD_ITEMCOUNT = exProperties.getProperty("L2TopRuSMSRewardItemCount", 10);
        L2TOPRU_SMS_REWARD_VOTE_MULTI = exProperties.getProperty("L2TopRuSMSRewardMultiVote", false);
        L2TOPZONE_ENABLED = exProperties.getProperty("L2TopZoneServiceEnabled", false);
        L2TOPZONE_VOTE_TIME_TO_LIVE = exProperties.getProperty("L2TopZoneVoteTimeToLive", 12) * 3600;
        L2TOPZONE_API_KEY = exProperties.getProperty("L2TopZoneApiKey", "");
        L2TOPZONE_REWARD_ITEM_ID = exProperties.getProperty("L2TopZoneRewardItemId", ArrayUtils.EMPTY_INT_ARRAY);
        L2TOPZONE_REWARD_ITEM_COUNT = exProperties.getProperty("L2TopZoneRewardItemCount", ArrayUtils.EMPTY_INT_ARRAY);
        L2TOPZONE_REWARD_CHANCE = exProperties.getProperty("L2TopZoneRewardChance", ArrayUtils.EMPTY_INT_ARRAY);
        L2HOPZONE_ENABLED = exProperties.getProperty("L2HopZoneServiceEnabled", false);
        L2HOPZONE_VOTE_TIME_TO_LIVE = exProperties.getProperty("L2HopZoneVoteTimeToLive", 12) * 3600;
        L2HOPZONE_API_KEY = exProperties.getProperty("L2HopZoneApiKey", "");
        L2HOPZONE_REWARD_ITEM_ID = exProperties.getProperty("L2HopZoneRewardItemId", ArrayUtils.EMPTY_INT_ARRAY);
        L2HOPZONE_REWARD_ITEM_COUNT = exProperties.getProperty("L2HopZoneRewardItemCount", ArrayUtils.EMPTY_INT_ARRAY);
        L2HOPZONE_REWARD_CHANCE = exProperties.getProperty("L2HopZoneRewardChance", ArrayUtils.EMPTY_INT_ARRAY);
        L2HOPZONE_REUSE_TIME = exProperties.getProperty("L2HopZoneReuseCommandRequest", 3) * 60 * 1000;
        L2HOPZONE_ADD_COMMAND = exProperties.getProperty("L2HopZoneAddCommand", "l2hopzone");
        L2JBRAZIL_ENABLED = exProperties.getProperty("L2JBrazilVoteEnabled", false);
        L2JBRAZIL_VOTE_TIME_TO_LIVE = exProperties.getProperty("L2JBrazilVoteTimeToLive", 12) * 3600;
        L2JBRAZIL_API_KEY = exProperties.getProperty("L2JBrazilServerName", "");
        L2JBRAZIL_REWARD_ITEM_ID = exProperties.getProperty("L2JBrazilRewardItemId", ArrayUtils.EMPTY_INT_ARRAY);
        L2JBRAZIL_REWARD_ITEM_COUNT = exProperties.getProperty("L2JBrazilRewardItemCount", ArrayUtils.EMPTY_INT_ARRAY);
        L2JBRAZIL_REWARD_CHANCE = exProperties.getProperty("L2JBrazilZoneRewardChance", ArrayUtils.EMPTY_INT_ARRAY);
        SERVICE_FEATHER_REVIVE_ENABLE = exProperties.getProperty("InstantReviveFeatherEnable", false);
        SERVICE_FEATHER_ITEM_ID = exProperties.getProperty("InstantReviveFeatherItemId", 9218);
        SERVICE_DISABLE_FEATHER_ON_SIEGES_AND_EPIC = exProperties.getProperty("DisableFeatherOnSiegeAndEpic", false);
        MMO_TOP_CRON_TASK = exProperties.getProperty("MMOTopRuCron", "");
        MMO_TOP_VOTES_LINK = exProperties.getProperty("MMOTopRuLink", "");
        MMO_TOP_REWARD = exProperties.getProperty("MMOTopRuReward", "");
        MMO_TOP_VOTE_WINDOW_DAYS = exProperties.getProperty("MMOTopRuWindowDays", 30);
        MMO_TOP_VOTE_REWARD_HOURS = exProperties.getProperty("MMOTopRuRewardDuration", 24);
        SERVICE_JAIL_COORDINATES = Location.parseLoc(exProperties.getProperty("JailLocationCoordinates", "-114648, -249384, -2984"));
        SERVICE_JAIL_BLOCK_CHARACTER = exProperties.getProperty("JailLockCharacter", true);
        SERVICE_ANNOUNCE_SERVER_LIFE_TIME = exProperties.getProperty("ServerLifeTimeAnnounce", false);
        WEAR_DELAY = exProperties.getProperty("WearDelay", 5);
        WEAING_NON_GRADE_PRICE = exProperties.getProperty("WearNonGradePrice", 10);
        WEAING_D_GRADE_PRICE = exProperties.getProperty("WearDGradePrice", 50);
        WEAING_C_GRADE_PRICE = exProperties.getProperty("WearCGradePrice", 100);
        WEAING_B_GRADE_PRICE = exProperties.getProperty("WearBGradePrice", 200);
        WEAING_A_GRADE_PRICE = exProperties.getProperty("WearAGradePrice", 500);
        WEAING_S_GRADE_PRICE = exProperties.getProperty("WearSGradePrice", 1000);
        ENCHANT_LIMIT_ZONE_NAMES = new HashSet<String>(Arrays.asList(exProperties.getProperty("EnchantLimitZoneNames", ArrayUtils.EMPTY_STRING_ARRAY)));
        ENCHANT_LIMIT_ZONE_ARMOR_LEVEL = exProperties.getProperty("EnchantLimitZoneArmor", -1);
        ENCHANT_LIMIT_ZONE_WEAPON_MAGE = exProperties.getProperty("EnchantLimitZoneMageWeapon", -1);
        ENCHANT_LIMIT_ZONE_WEAPON_PHYS = exProperties.getProperty("EnchantLimitZoneWarriorWeapon", -1);
        ENCHANT_LIMIT_ZONE_ACCESSORY = exProperties.getProperty("EnchantLimitZoneAccessory", -1);
        SELLBUFF_ENABLED = exProperties.getProperty("SellBuffEnable", false);
        SELLBUFF_OFFLINE_TRADE = exProperties.getProperty("SellBuffOfflineEnable", false);
        SELLBUFF_MAX_BUFFS = exProperties.getProperty("SellBuffMaxBuffs", 15);
        SELLBUFF_PREFIX = exProperties.getProperty("SellBuffTradePrefix", "BUFF SELL:");
    }

    public static void loadPvPSettings() {
        ExProperties exProperties = Config.load(PVP_CONFIG_FILE);
        KARMA_MIN_INCREASE = exProperties.getProperty("MinKarmaIncrease", 240);
        KARMA_STATIC_INCREASES = exProperties.getProperty("StaticKarmaIncrease", -1);
        KARMA_STATIC_LOST_ON_DEATH = exProperties.getProperty("StaticKarmaLostOnDeath", -1);
        RATE_KARMA_LOST_STATIC = exProperties.getProperty("KarmaLostStaticValue", -1);
        KARMA_RATE_LOST = exProperties.getProperty("RateKarmaLost", -1);
        SERVICES_PK_KILL_BONUS_INTERVAL = exProperties.getProperty("PvPPKRewardBonusInterval", 0L) * 60000L;
        SERVICES_PK_KILL_BONUS_ENABLE = exProperties.getProperty("PkKillEnable", false);
        SERVICES_PVP_KILL_BONUS_ENABLE = exProperties.getProperty("PvPKillEnable", false);
        SERVICES_PVP_KILL_BONUS_REWARD_ITEM = exProperties.getProperty("PvPkillRewardItem", ArrayUtils.EMPTY_INT_ARRAY);
        SERVICES_PVP_KILL_BONUS_REWARD_COUNT = exProperties.getProperty("PvPKillRewardCount", ArrayUtils.EMPTY_INT_ARRAY);
        SERVICES_PVP_KILL_BONUS_REWARD_CHANCE = exProperties.getProperty("PvPkillRewardChance", ArrayUtils.EMPTY_FLOAT_ARRAY);
        SERVICES_PK_KILL_BONUS_REWARD_ITEM = exProperties.getProperty("PkkillRewardItem", ArrayUtils.EMPTY_INT_ARRAY);
        SERVICES_PK_KILL_BONUS_REWARD_COUNT = exProperties.getProperty("PkKillRewardCount", ArrayUtils.EMPTY_INT_ARRAY);
        SERVICES_PK_KILL_BONUS_REWARD_CHANCE = exProperties.getProperty("PkKillRewardChance", ArrayUtils.EMPTY_FLOAT_ARRAY);
        SERVICES_PK_PVP_BONUS_TIE_IF_SAME_IP = exProperties.getProperty("PkPvPSameIPKiller", false);
        SERVICES_PK_PVP_BONUS_TIE_IF_SAME_HWID = exProperties.getProperty("PkPvPSameHWIDKiller", false);
        PVP_INCREASE_SAME_IP_CHECK = exProperties.getProperty("PvPIncreaseSameIpCheck", false);
        PVP_INCREASE_SAME_HWID_CHECK = exProperties.getProperty("PvPIncreaseSameHwidCheck", false);
        PVP_HERO_CHAT_SYSTEM = exProperties.getProperty("PvPCountHeroVoice", false);
        PVP_COUNT_TO_CHAT = exProperties.getProperty("PvPCountForHeroVoice", 2000);
        KARMA_DROP_GM = exProperties.getProperty("CanGMDropEquipment", false);
        KARMA_NEEDED_TO_DROP = exProperties.getProperty("KarmaNeededToDrop", true);
        DROP_ITEMS_ON_DIE = exProperties.getProperty("DropOnDie", false);
        DROP_ITEMS_AUGMENTED = exProperties.getProperty("DropAugmented", false);
        ITEM_ANTIDROP_FROM_PK = exProperties.getProperty("ItemAntiDropFromPK", 0);
        KARMA_DROP_ITEM_LIMIT = exProperties.getProperty("MaxItemsDroppable", 10);
        MIN_PK_TO_ITEMS_DROP = exProperties.getProperty("MinPKToDropItems", 5);
        KARMA_RANDOM_DROP_LOCATION_LIMIT = exProperties.getProperty("MaxDropThrowDistance", 70);
        KARMA_DROPCHANCE_BASE = exProperties.getProperty("ChanceOfPKDropBase", 20.0);
        KARMA_DROPCHANCE_MOD = exProperties.getProperty("ChanceOfPKsDropMod", 1.0);
        NORMAL_DROPCHANCE_BASE = exProperties.getProperty("ChanceOfNormalDropBase", 1.0);
        DROPCHANCE_EQUIPPED_WEAPON = exProperties.getProperty("ChanceOfDropWeapon", 3);
        DROPCHANCE_EQUIPMENT = exProperties.getProperty("ChanceOfDropEquippment", 17);
        DROPCHANCE_ITEM = exProperties.getProperty("ChanceOfDropOther", 80);
        PVP_POINTS_AMOUNT_ADD = exProperties.getProperty("PvPPointsAmountAdd", 1);
        PVP_POINTS_ADD_ON_WAR_SUMMON_KILL = exProperties.getProperty("PvPPointsAddOnWarSummonKill", false);
        FUN_ZONE_PVP_COUNT = exProperties.getProperty("PvPCountingInFunZone", false);
        BATTLE_ZONE_PVP_COUNT = exProperties.getProperty("PvPCountingInBattleZone", false);
        SIEGE_ZONE_PVP_COUNT = exProperties.getProperty("PvPCountingInSiegeZone", false);
        FUN_ZONE_FLAG_ON_ENTER = exProperties.getProperty("FlagOnEnterInFunZone", false);
        KARMA_LIST_NONDROPPABLE_ITEMS = new ArrayList<Integer>();
        for (int n : exProperties.getProperty("ListOfNonDroppableItems", new int[]{57, 1147, 425, 1146, 461, 10, 2368, 7, 6, 2370, 2369, 3500, 3501, 3502, 4422, 4423, 4424, 2375, 6648, 6649, 6650, 6842, 6834, 6835, 6836, 6837, 6838, 6839, 6840, 5575, 7694, 6841, 8181})) {
            KARMA_LIST_NONDROPPABLE_ITEMS.add(n);
        }
        PVP_TIME = exProperties.getProperty("PvPFlagTime", 40000);
        PVP_FLAG_ON_UN_FLAG_TIME = exProperties.getProperty("PvPFlagTimeOnFlag", 20000);
        PVP_BLINKING_UNFLAG_TIME = exProperties.getProperty("PvPBlinkingFlagTime", 10000);
    }

    public static void loadAISettings() {
        ExProperties exProperties = Config.load(AI_CONFIG_FILE);
        AI_TASK_MANAGER_COUNT = exProperties.getProperty("AiTaskManagers", 1);
        AI_TASK_ATTACK_DELAY = exProperties.getProperty("AiTaskDelay", 1000);
        AI_TASK_ACTIVE_DELAY = exProperties.getProperty("AiTaskActiveDelay", 1000);
        BLOCK_ACTIVE_TASKS = exProperties.getProperty("BlockActiveTasks", false);
        ALWAYS_TELEPORT_HOME = exProperties.getProperty("AlwaysTeleportHome", false);
        RND_WALK = exProperties.getProperty("RndWalk", true);
        RND_WALK_RATE = exProperties.getProperty("RndWalkRate", 1);
        RND_ANIMATION_RATE = exProperties.getProperty("RndAnimationRate", 2);
        AGGRO_CHECK_INTERVAL = exProperties.getProperty("AggroCheckInterval", 250);
        NONAGGRO_TIME_ONTELEPORT = exProperties.getProperty("NonAggroTimeOnTeleport", 15000);
        NONAGGRO_TIME_ONLOGIN = exProperties.getProperty("NonAggroTimeOnLogin", 15000);
        GLOBAL_AGGRO_CHECK_INTERVAL = exProperties.getProperty("GlobalAggroCheckInterval", 10000L);
        FACTION_NOTIFY_INTERVAL = exProperties.getProperty("FactionNotifyInterval", 3000L);
        MAX_DRIFT_RANGE = exProperties.getProperty("MaxDriftRange", 100);
        MAX_PURSUE_RANGE = exProperties.getProperty("MaxPursueRange", 4000);
        MAX_PURSUE_UNDERGROUND_RANGE = exProperties.getProperty("MaxPursueUndergoundRange", 2000);
        MAX_PURSUE_RANGE_RAID = exProperties.getProperty("MaxPursueRangeRaid", 5000);
        RESTORE_HP_MP_ON_TELEPORT_HOME = exProperties.getProperty("RestoreHealthOnTeleportToHome", true);
        RESTORE_HP_MP_ON_EXCLUDED_IDS = exProperties.getProperty("RestoreHealthOnExcludedIds", new int[]{29028, 29068, 29020, 29045, 29062, 29065, 29006, 29022});
        MONSTER_TELEPORT_TO_PLAYER = exProperties.getProperty("MobsCanTeleportToPlayer", true);
        RESET_HATE_ONLY_WHEN_LEAVING_OR_DYING = exProperties.getProperty("ResetHateOnlyOnPlayerLeavingOrDead", true);
        RESET_HATE_ONLY_AFTER_PURSUE_RANGE = exProperties.getProperty("ResetHateOnlyAfterPursueRange", true);
    }

    public static void loadGeodataSettings() {
        ExProperties exProperties = Config.load(GEODATA_CONFIG_FILE);
        GEO_X_FIRST = exProperties.getProperty("GeoFirstX", 15);
        GEO_Y_FIRST = exProperties.getProperty("GeoFirstY", 10);
        GEO_X_LAST = exProperties.getProperty("GeoLastX", 26);
        GEO_Y_LAST = exProperties.getProperty("GeoLastY", 26);
        GEOFILES_PATTERN = exProperties.getProperty("GeoFilesPattern", "(\\d{2}_\\d{2})\\.l2j");
        ALLOW_GEODATA = exProperties.getProperty("AllowGeodata", true);
        ALLOW_FALL_FROM_WALLS = exProperties.getProperty("AllowFallFromWalls", false);
        ALLOW_KEYBOARD_MOVE = exProperties.getProperty("AllowMoveWithKeyboard", true);
        ALLOW_PAWN_PATHFIND = exProperties.getProperty("AllowPawnPathFind", true);
        COMPACT_GEO = exProperties.getProperty("CompactGeoData", false);
        CLIENT_Z_SHIFT = exProperties.getProperty("ClientZShift", 16);
        PATHFIND_BOOST = exProperties.getProperty("PathFindBoost", 2);
        PATHFIND_DIAGONAL = exProperties.getProperty("PathFindDiagonal", true);
        PATH_CLEAN = exProperties.getProperty("PathClean", true);
        PATHFIND_MAX_Z_DIFF = exProperties.getProperty("PathFindMaxZDiff", 32);
        MAX_Z_DIFF = exProperties.getProperty("MaxZDiff", 64);
        MIN_LAYER_HEIGHT = exProperties.getProperty("MinLayerHeight", 64);
        PATHFIND_MAX_TIME = exProperties.getProperty("PathFindMaxTime", 10000000);
        PATHFIND_BUFFERS = exProperties.getProperty("PathFindBuffers", "8x96;8x128;8x160;8x192;4x224;4x256;4x288;2x320;2x384;2x352;1x512");
        GEODATA_DEBUG = exProperties.getProperty("GeodataDebug", false);
    }

    public static void loadEventsSettings() {
        int n;
        ExProperties exProperties = Config.load(EVENTS_CONFIG_FILE);
        EVENT_CofferOfShadowsPriceRate = exProperties.getProperty("CofferOfShadowsPriceRate", 1.0);
        EVENT_CofferOfShadowsNpcId = exProperties.getProperty("CofferOfShadowsNpcId", new int[]{32091});
        EVENT_LastHeroItemID = exProperties.getProperty("LastHero_bonus_id", 57);
        EVENT_LastHeroItemCOUNT = exProperties.getProperty("LastHero_bonus_count", 5000.0);
        EVENT_LastHeroTime = exProperties.getProperty("LastHero_time", 3);
        EVENT_LastHeroRate = exProperties.getProperty("LastHero_rate", true);
        EVENT_LastHeroChanceToStart = exProperties.getProperty("LastHero_ChanceToStart", 5);
        EVENT_LastHeroItemCOUNTFinal = exProperties.getProperty("LastHero_bonus_count_final", 10000.0);
        EVENT_LastHeroRateFinal = exProperties.getProperty("LastHero_rate_final", true);
        EVENT_TvTItemID = exProperties.getProperty("TvT_bonus_id", 57);
        EVENT_TvTItemCOUNT = exProperties.getProperty("TvT_bonus_count", 5000.0);
        EVENT_TvTTime = exProperties.getProperty("TvT_time", 3);
        EVENT_TvT_rate = exProperties.getProperty("TvT_rate", true);
        EVENT_TvTChanceToStart = exProperties.getProperty("TvT_ChanceToStart", 5);
        EVENT_DropEvent_Items = exProperties.getProperty("DropEvent_Items", "");
        EVENT_DropEvent_PartyItems = exProperties.getProperty("DropEvent_PartyItems", "");
        EVENT_DropEvent_PartyItems_Distribute_Random = exProperties.getProperty("DropEvent_PartyItems_Distribute_Random", true);
        EVENT_DropEvent_Rate = exProperties.getProperty("DropEvent_Rated", false);
        Event_DropEvent_Level_Penalty = exProperties.getProperty("DropEvent_LevelDiffPenalty", 9);
        Event_DropEvent_Check_Hwid = exProperties.getProperty("DropEvent_PartyHwid_Check", false);
        Event_DropEvent_Time_Period = exProperties.getProperty("DropEvent_Period", "");
        EX_ONE_DAY_REWARD = exProperties.getProperty("OneDayRewardSystem", false);
        EVENT_CtFItemID = exProperties.getProperty("CtF_bonus_id", 57);
        EVENT_CtFItemCOUNT = exProperties.getProperty("CtF_bonus_count", 5000.0);
        EVENT_CtFTime = exProperties.getProperty("CtF_time", 3);
        EVENT_CtF_rate = exProperties.getProperty("CtF_rate", true);
        EVENT_CtFChanceToStart = exProperties.getProperty("CtF_ChanceToStart", 5);
        EVENT_TFH_POLLEN_CHANCE = exProperties.getProperty("TFH_POLLEN_CHANCE", 5.0);
        EVENT_GLITTMEDAL_NORMAL_CHANCE = exProperties.getProperty("MEDAL_CHANCE", 10.0);
        EVENT_GLITTMEDAL_GLIT_CHANCE = exProperties.getProperty("GLITTMEDAL_CHANCE", 0.1);
        EVENT_L2DAY_LETTER_NPC_ID = exProperties.getProperty("L2DAY_LETTER_NPC_ID", new int[]{31230});
        EVENT_CHANGE_OF_HEART_CHANCE = exProperties.getProperty("EVENT_CHANGE_OF_HEART_CHANCE", 5.0);
        EVENT_CHRISTMAS_CHANCE = exProperties.getProperty("EVENT_CHRISTMAS_CHANCE", 1.0);
        EVENT_APIL_FOOLS_DROP_CHANCE = exProperties.getProperty("AprilFollsDropChance", 50.0);
        EVENT_FINDER_REWARD_ID = exProperties.getProperty("EventFinderRewardId", 57);
        EVENT_FINDER_ITEM_COUNT = exProperties.getProperty("EventFinderRewardCount", 10000);
        EVENT_FINDER_LOCATIONS = new ArrayList<Location>();
        for (String object2 : StringUtils.split((String)exProperties.getProperty("EventFinderLocations", ""), (char)';')) {
            EVENT_FINDER_LOCATIONS.add(Location.parseLoc(object2));
        }
        EVENT_FINDER_CAPTURE_TIME = (long)(exProperties.getProperty("EventFinderHostageFindTime", 10) * 60) * 1000L;
        EVENT_TRICK_OF_TRANS_CHANCE = exProperties.getProperty("TRICK_OF_TRANS_CHANCE", 10.0);
        EVENT_MARCH8_DROP_CHANCE = exProperties.getProperty("March8DropChance", 10.0);
        EVENT_MARCH8_PRICE_RATE = exProperties.getProperty("March8PriceRate", 1.0);
        EVENT_StraightHands_Items = exProperties.getProperty("StraightHandsRestrictedItems", new int[]{13390});
        EVENT_FinderHostageStartTime = exProperties.getProperty("FinderHostageStartTime", new String[0], ",");
        EVENT_Attendance_ResetTime = Integer.parseInt(StringUtils.replace((String)exProperties.getProperty("EventAttendance_ResetTime", "06:30"), (String)":", (String)""));
        EVENT_Attendance_Rewards = new ArrayList<Pair<Integer, Long>>();
        Object object = new StringTokenizer(exProperties.getProperty("EventAttendance_Reward", ""), ";");
        while (((StringTokenizer)object).hasMoreTokens()) {
            String string = ((StringTokenizer)object).nextToken();
            n = StringUtils.indexOfAny((CharSequence)string, (String)":,-");
            EVENT_Attendance_Rewards.add((Pair<Integer, Long>)Pair.of((Object)Integer.parseInt(string.substring(0, n)), (Object)Long.parseLong(string.substring(n + 1))));
        }
        EVENT_Attendance_Rewards_For_Premium = new ArrayList<Pair<Integer, Long>>();
        object = new StringTokenizer((String)StringUtils.defaultIfEmpty((CharSequence)exProperties.getProperty("EventAttendance_Reward_Premium", ""), (CharSequence)exProperties.getProperty("EventAttendance_Reward", "")), ";");
        while (((StringTokenizer)object).hasMoreTokens()) {
            String string = ((StringTokenizer)object).nextToken();
            n = StringUtils.indexOfAny((CharSequence)string, (String)":,-");
            EVENT_Attendance_Rewards_For_Premium.add((Pair<Integer, Long>)Pair.of((Object)Integer.parseInt(string.substring(0, n)), (Object)Long.parseLong(string.substring(n + 1))));
        }
        EVENT_Attendance_InGameTime = exProperties.getProperty("EventAttendance_InGameTime", 30);
        EVENT_Attendance_Highlights = exProperties.getProperty("EventAttendance_Highlights", new int[]{1, 7, 14, 21, 28});
        EVENT_Attendance_OnEnterWorld = exProperties.getProperty("EventAttendance_OnEnter", true);
        EVENT_Attendance_Looped = exProperties.getProperty("EventAttendance_Looped", false);
        EVENT_Attendance_Global = exProperties.getProperty("EventAttendance_Global", true);
        EVENT_Attendance_MinLevel = exProperties.getProperty("EventAttendance_MinLevel", 1);
        EVENT_Attendance_Voice_Command = exProperties.getProperty("EventAttendance_AllowVoiceCommand", false);
        GVG_REWARD_ID = exProperties.getProperty("EventGvGItemIdRewrd", 57);
        GVG_REWARD_AMOUNT = exProperties.getProperty("EventGvGRewardAmount", 100L);
        EVENT_GVG_START_TIME = exProperties.getProperty("EventGvGStartTime", "22:00:00");
        EVENT_GVG_MIN_LEVEL = exProperties.getProperty("EventGvGMinParticipantLevel", 76);
        EVENT_GVG_MAX_LEVEL = exProperties.getProperty("EventGvGMaxParticipantLevel", 81);
        EVENT_GVG_GROUPS_LIMIT = exProperties.getProperty("EventGvGMaxGroups", 50);
        EVENT_GVG_MIN_PARTY_SIZE = exProperties.getProperty("EventGvGMinPartySize", 6);
        EVENT_GVG_REG_TIME = (long)(Math.max(5, exProperties.getProperty("EventGvGRegistrationTime", 10)) * 60) * 1000L;
        EVENT_PUMPKIN_GHOST_ID = exProperties.getProperty("HalloweenEventPumpkinGhostNPC", 40028);
        EVENT_SKOOLDIE_REWARDER = exProperties.getProperty("HalloweenSkooldieRewarderNPC", new int[]{40029});
        EVENT_PUMPKIN_DELAY = exProperties.getProperty("HalloweenEventPumpkinDelay", 120) * 60 * 1000;
        EVENT_PUMPKIN_GHOST_SHOW_TIME = exProperties.getProperty("HalloweenEventPumpkinTownShowTime", 10) * 60 * 1000;
        EVENT_SKOOLDIE_TIME = exProperties.getProperty("HalloweenEventSkooldieSpawnTime", 20) * 60 * 1000;
        EVENT_HALLOWEEN_CANDY = exProperties.getProperty("HalloweenCandyId", 6633);
        EVENT_HALLOWEEN_CANDY_ITEM_COUNT_NEEDED = exProperties.getProperty("HalloweenCandyAmount", 1);
        EVENT_HALLOWEEN_TOY_CHEST = exProperties.getProperty("HalloweenRewardId", 6634);
        EVENT_HALLOWEEN_TOY_CHEST_REWARD_AMOUNT = exProperties.getProperty("HalloweenRewardAmount", 1);
        EVENT_PUMPKIN_DROP_ITEMS = exProperties.getProperty("HalloweenPumpkinGhosDropItemId", new int[]{6633, 6406, 6407});
        EVENT_PUMPKIN_DROP_CHANCE = exProperties.getProperty("HalloweenPumpkinGhosDropItemChance", 1);
        EVENT_PVP_REGISTRATION_COMMAND = exProperties.getProperty("EventPvPRegistrationCommand", false);
        PVP_EVENTS_RESTRICT_ENCHANT = exProperties.getProperty("PvPEventsRestrictEnchant", false);
        PVP_EVENTS_RESTRICT_ENCHANT_ARMOR_LEVEL = exProperties.getProperty("PvPEventsEnchantStatLevelArmor", -1);
        PVP_EVENTS_RESTRICT_ENCHANT_WEAPON_MAGE = exProperties.getProperty("PvPEventsEnchantStatLevelMageWeapon", -1);
        PVP_EVENTS_RESTRICT_ENCHANT_WEAPON_PHYS = exProperties.getProperty("PvPEventsEnchantStatLevelPhysWeapon", -1);
        PVP_EVENTS_RESTRICT_ENCHANT_ACCESSORY = exProperties.getProperty("PvPEventsEnchantStatLevelAccessory", -1);
        SAVING_SNOWMEN_CAPTURE_TIME = (long)(exProperties.getProperty("SavingSnowmenCaptureTime", 30) * 60) * 1000L;
        SAVING_SNOWMEN_EVENT_INTERVAL = (long)(exProperties.getProperty("SavingSnowmenEventInterval", 120) * 60) * 1000L;
        SAVING_SNOWMEN_SHOUT_INTERVAL = (long)(exProperties.getProperty("SnowmenShoutInterval", 1) * 60) * 1000L;
        SAVING_SNOWMEN_THOMAS_EAT_DELAY = (long)(exProperties.getProperty("SavingSnowmenThomasEatDelay", 10) * 60) * 1000L;
        SAVING_SNOWMEN_SANTA_SAY_INTERVAL = (long)(exProperties.getProperty("SavingSnowmenSantaSayInterval", 10) * 60) * 1000L;
        SAVING_SNOWMEN_ACTION_SPAWN_INTERVAL = (long)(exProperties.getProperty("SavingSnowmenActionSpawnInterval", 110) * 60) * 1000L;
        SAVING_SNOWMEN_LOCATIONS = new ArrayList<Location>();
        for (String string : StringUtils.split((String)exProperties.getProperty("SavingSnowmenCaptureLocations", ""), (char)';')) {
            SAVING_SNOWMEN_LOCATIONS.add(Location.parseLoc(string));
        }
        SAVING_SNOWMAN_EVENT_DROP_ID = exProperties.getProperty("SavingSnowmenEventDropId", new int[]{5556, 5557, 5558, 5559});
        SAVING_SNOWMAN_EVENT_DROP_COUNT = exProperties.getProperty("SavingSnowmanEventDropCount", new int[]{1, 1, 1, 1});
        SAVING_SNOWMAN_EVENT_DROP_CHANCE = exProperties.getProperty("SavingSnowmenEventDropChance", 10.0);
        SAVING_SNOWMAN_SNOWMAN_LITTLE_GIFT = exProperties.getProperty("SavingSnowmenEventLittleGift", 20901);
        SAVING_SNOWMAN_SNOWMAN_GIFT_FROM_SANTA = exProperties.getProperty("SavingSnowmenEventGiftFromSanta", 20901);
        SAVING_SNOWMAN_BUFF_SKILL_ID = exProperties.getProperty("SavingSnowmanBuffSkillId", 6106);
        SAVING_SNOWMAN_BUFF_DELAY = (long)(exProperties.getProperty("SavingSnowmanBuffDelay", 10) * 60) * 1000L;
        SAVING_SNOWMAN_EVENT_MANAGER_ID = exProperties.getProperty("SavingSnowmenEventManagerId", 13184);
        SAVING_SNOWMAN_EVENT_FLYING_SANTA_ID = exProperties.getProperty("SavingSnowmenEventFlyingSantaId", 13186);
        SAVING_SNOWMAN_EVENT_SNOWMAN_ID = exProperties.getProperty("SavingSnowmenEventSnowmanId", 13160);
        SAVING_SNOWMAN_EVENT_THOMAS_ID = exProperties.getProperty("SavingSnowmenEventThomasId", 13183);
        EVENT_EXCALIBUR_WEAPON_ID = exProperties.getProperty("EventExcaliburWeaponId", 91574);
        EVENT_EXCALIBUR_WEAPON_PRICE_ITEM_ID = exProperties.getProperty("EventExcaliburCurrencyId", 57);
        EVENT_EXCALIBUR_WEAPON_PRICE_ITEM_AMOUNT = exProperties.getProperty("EventExcaliburWeaponPriceAmount", 10000);
        EVENT_EXCALIBUR_SCROLL_ID = exProperties.getProperty("EventExcaliburEnchantScrollId", 91575);
        EVENT_EXCALIBUR_TEMPORAL_SCROLL_PRICE = exProperties.getProperty("EventExcaliburTemporalEnchantScrollPrice", 10000);
        EVENT_EXCALIBUR_TEMPORAL_SCROLL_REUSE = exProperties.getProperty("EventExcaliburTemporalEnchantScrollReuse", 360L) * 60L * 1000L;
        EVENT_EXCALIBUR_ONE_SCROLL_PRICE_PRICE = exProperties.getProperty("EventExcaliburOneEnchantScrollPrice", 77777);
        EVENT_EXCALIBUR_TEN_SCROLL_PRICE_PRICE = exProperties.getProperty("EventExcaliburTenEnchantScrollPrice", 777770);
        EVENT_EXCALIBUR_MIN_ENCHANT_LEVEL_REWARD = exProperties.getProperty("EventExcaliburRewardMinEnchantLevel", 4);
        object = exProperties.getProperty("EventExcaliburRewards", "");
        HashMap<Integer, List<Triple<Integer, Long, Double>>> hashMap = new HashMap<Integer, List<Triple<Integer, Long, Double>>>();
        for (String string : StringUtils.split((String)object, (char)';')) {
            if (string.isEmpty()) continue;
            int n2 = string.indexOf(58);
            if (n2 < 0) {
                throw new RuntimeException("No level: \"" + string + "\"");
            }
            int n3 = Integer.parseInt(StringUtils.trimToEmpty((String)string.substring(0, n2)));
            String string2 = StringUtils.trimToEmpty((String)string.substring(n2 + 1));
            int n4 = string2.indexOf(44);
            int n5 = string2.indexOf(44, n4 + 1);
            if (n4 < 0 || n5 < 0) {
                throw new RuntimeException("No item: \"" + string + "\"");
            }
            int n6 = Integer.parseInt(StringUtils.trimToEmpty((String)string2.substring(0, n4)));
            long l = Long.parseLong(StringUtils.trimToEmpty((String)string2.substring(n4 + 1, n5)));
            double d = Double.parseDouble(StringUtils.trimToEmpty((String)string2.substring(n5 + 1)));
            LinkedList<Triple> linkedList = (LinkedList<Triple>)hashMap.get(n3);
            if (linkedList == null) {
                linkedList = new LinkedList<Triple>();
                hashMap.put(n3, linkedList);
            }
            linkedList.add(Triple.of((Object)n6, (Object)l, (Object)d));
        }
        EVENT_EXCALIBUR_REWARD = hashMap;
    }

    public static void loadOlympiadSettings() {
        ExProperties exProperties = Config.load(OLYMPIAD);
        OLY_ENABLED = exProperties.getProperty("OlympiadEnabled", true);
        OLY_SPECTATION_ALLOWED = exProperties.getProperty("SpectatingAllowed", true);
        NPC_OLYMPIAD_GAME_ANNOUNCE = exProperties.getProperty("OlympiadNpcAnnounceTeleportOnStdium", true);
        ANNOUNCE_OLYMPIAD_GAME_END = exProperties.getProperty("EndOlympiadAnnounceSeason", false);
        OLY_PARTICIPANT_TYPE_SELECTION = exProperties.getProperty("RandomParticipantsSelection", true);
        OLY_RESTRICT_HWID = exProperties.getProperty("OlympiadHWIDCheck", true);
        OLY_RESTRICT_IP = exProperties.getProperty("OlympiadIPCheck", true);
        OLY_RESTORE_HPCPMP_ON_START_MATCH = exProperties.getProperty("OlympiadRestoreCPHPMPOnMatchStart", false);
        OLY_RESTRICT_CLASS_IDS = exProperties.getProperty("OlympiadProhibitClassIds", ArrayUtils.EMPTY_INT_ARRAY);
        OLYMPIAD_COMPETITION_TIME = (long)(exProperties.getProperty("OlympiadCompetitionTime", 5) * 60) * 1000L;
        OLYMPIAD_STADIUM_TELEPORT_DELAY = exProperties.getProperty("OlympiadStadiumTeleportDelay", 45);
        OLY_MAX_SPECTATORS_PER_STADIUM = exProperties.getProperty("MaxSpectatorPerStadium", 18);
        OLY_SEASON_TIME_CALC_MODE = OlySeasonTimeCalcMode.valueOf(exProperties.getProperty("SeasonTimeCalcMode", "NORMAL").toUpperCase());
        OLY_SEASON_START_TIME = exProperties.getProperty("SeasonStartTime", "2 00:00");
        OLY_SEASON_END_TIME = exProperties.getProperty("SeasonEndTime", "+1:1 00:00");
        OLY_COMPETITION_START_TIME = exProperties.getProperty("CompetitionStartTime", "18:00");
        OLY_COMPETITION_END_TIME = exProperties.getProperty("CompetitionEndTime", "+1 00:00");
        OLY_COMPETITION_CUSTOM_START_TIME = exProperties.getProperty("CompetitionCustomStartTime", 0);
        OLY_COMPETITION_CUSTOM_END_TIME = exProperties.getProperty("CompetitionCustomEndTime", 0);
        OLY_BONUS_TIME = exProperties.getProperty("WeaklyBonusTime", "+7 18:30");
        OLY_NOMINATE_TIME = exProperties.getProperty("NominateTime", "+1:1 12:00");
        ALT_OLY_COMPETITION_DAYS = new LinkedHashSet<Integer>();
        block26: for (String n : exProperties.getProperty("CustomOlyCompetitionDays", new String[]{"monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday"})) {
            switch (n.toLowerCase()) {
                case "monday": 
                case "1": {
                    ALT_OLY_COMPETITION_DAYS.add(2);
                    continue block26;
                }
                case "tuesday": 
                case "2": {
                    ALT_OLY_COMPETITION_DAYS.add(3);
                    continue block26;
                }
                case "wednesday": 
                case "3": {
                    ALT_OLY_COMPETITION_DAYS.add(4);
                    continue block26;
                }
                case "thursday": 
                case "4": {
                    ALT_OLY_COMPETITION_DAYS.add(5);
                    continue block26;
                }
                case "friday": 
                case "5": {
                    ALT_OLY_COMPETITION_DAYS.add(6);
                    continue block26;
                }
                case "saturday": 
                case "6": {
                    ALT_OLY_COMPETITION_DAYS.add(7);
                    continue block26;
                }
                case "sunday": 
                case "0": 
                case "7": {
                    ALT_OLY_COMPETITION_DAYS.add(1);
                }
            }
        }
        OLY_SEASON_START_POINTS = exProperties.getProperty("SeasonStartPoints", 10);
        OLY_MIN_HERO_COMPS = exProperties.getProperty("MinRewardableHeroComps", 15);
        OLY_MIN_HERO_WIN = exProperties.getProperty("MinWinHeroComps", 1);
        OLY_MIN_NOBLE_COMPS = exProperties.getProperty("MinRewardableNobleComps", 15);
        OLY_POINTS_SETTLEMENT = exProperties.getProperty("PointSettlement", new int[]{100, 75, 55, 40, 30});
        String string = exProperties.getProperty("OlyBuffs", "99:30-1,31-2,3050-1;100:32-1,33-2");
        OLY_BUFFS = new TIntObjectHashMap();
        StringTokenizer stringTokenizer = new StringTokenizer(string, ";", false);
        while (stringTokenizer.hasMoreTokens()) {
            String string2 = stringTokenizer.nextToken();
            if (string2.isEmpty()) continue;
            int n = string2.indexOf(58);
            if (n < 0) {
                am.warn("Can't parse \"" + string2 + "\"");
                continue;
            }
            int n2 = Integer.parseInt(string2.substring(0, n));
            TIntIntHashMap tIntIntHashMap = (TIntIntHashMap)OLY_BUFFS.get(n2);
            if (tIntIntHashMap == null) {
                tIntIntHashMap = new TIntIntHashMap();
                OLY_BUFFS.put(n2, (Object)tIntIntHashMap);
            }
            String string3 = string2.substring(n + 1);
            StringTokenizer stringTokenizer2 = new StringTokenizer(string3, ",", false);
            while (stringTokenizer2.hasMoreTokens()) {
                String string4 = stringTokenizer2.nextToken();
                int n3 = string4.indexOf("-");
                int n4 = Integer.parseInt(string4.substring(0, n3));
                int n5 = Integer.parseInt(string4.substring(n3 + 1));
                tIntIntHashMap.put(n4, n5);
            }
        }
        OLY_ITEMS_SETTLEMENT_PER_POINT = exProperties.getProperty("ItemsSettlementPerPoint", 1000);
        OLY_HERO_POINT_BONUS = exProperties.getProperty("HeroPointBonus", 200);
        OLY_DEFAULT_POINTS = exProperties.getProperty("DefaultPoints", 10);
        OLY_WBONUS_POINTS = exProperties.getProperty("WeaklyBonusPoints", 10);
        OLY_LOOSE_POINTS_MUL = exProperties.getProperty("LoosePointsMultiplier", 0.2);
        OLY_RESTRICTED_SKILL_IDS = exProperties.getProperty("OlyRestrictedSkillIds", new int[0]);
        OLY_HERO_REWARD_RITEMID = exProperties.getProperty("HeroGainRewardItemID", 6651);
        OLY_VICTORY_RITEMID = exProperties.getProperty("VictoryRewardItemID", new int[]{6651});
        OLY_VICTORY_CFREE_RITEMCNT = exProperties.getProperty("VictoryRewardClassFreeCount", new int[]{40});
        OLY_VICTORY_CBASE_RITEMCNT = exProperties.getProperty("VictoryRewardClassBasedCount", new int[]{65});
        OLY_VICTORY_3TEAM_RITEMCNT = exProperties.getProperty("VictoryRewardTeamBasedCount", new int[]{85});
        OLY_VICTORY_RITEMID_CHANCE = exProperties.getProperty("VictoryRewardItemIDChance", new int[]{100});
        OLY_HERO_CUSTOM_REWARD_ID = exProperties.getProperty("HeroAdditionalRewardId", ArrayUtils.EMPTY_INT_ARRAY);
        OLY_HERO_CUSTOM_REWARD_AMOUNT = exProperties.getProperty("HeroAdditionalRewardAmount", ArrayUtils.EMPTY_INT_ARRAY);
        OLY_HERO_CUSTOM_REWARD_CHANCE = exProperties.getProperty("HeroAdditionalRewardChance", ArrayUtils.EMPTY_INT_ARRAY);
        OLY_PAID_REGISTRATION_FOR_CLASS_FREE = exProperties.getProperty("ChargeParticipationFeeForClassFree", false);
        OLY_PAID_REGISTRATION_FOR_CLASS_FREE_ITEM_ID = exProperties.getProperty("ChargeParticipationFeeForClassFreeItemId", 4037);
        OLY_PAID_REGISTRATION_FOR_CLASS_FREE_ITEM_COUNT = exProperties.getProperty("ChargeParticipationFeeForClassFreeItemCount", 1000);
        OLY_PAID_REGISTRATION_FOR_CLASS_INDIVIDUAL = exProperties.getProperty("ChargeParticipationFeeForClassBased", false);
        OLY_PAID_REGISTRATION_FOR_CLASS_INDIVIDUAL_ITEM_ID = exProperties.getProperty("ChargeParticipationFeeForClassBasedItemId", 4037);
        OLY_PAID_REGISTRATION_FOR_CLASS_INDIVIDUAL_ITEM_ID = exProperties.getProperty("ChargeParticipationFeeForClassBasedItemCount", 1000);
        OLY_PAID_REGISTRATION_FOR_TEAM_CLASS_FREE = exProperties.getProperty("ChargeParticipationFeeForTeamFree", false);
        OLY_PAID_REGISTRATION_FOR_TEAM_CLASS_FREE_ITEM_ID = exProperties.getProperty("ChargeParticipationFeeForTeamFreeItemId", 4037);
        OLY_PAID_REGISTRATION_FOR_TEAM_CLASS_FREE_ITEM_COUNT = exProperties.getProperty("ChargeParticipationFeeForTeamFreeCount", 1000);
        OLY_MAX_TOTAL_MATCHES = exProperties.getProperty("MaxTotalMatches", 70);
        OLY_CF_MATCHES = exProperties.getProperty("MaxClassFreeMatches", 60);
        OLY_CB_MATCHES = exProperties.getProperty("MaxClassBaseMatches", 30);
        OLY_TB_MATCHES = exProperties.getProperty("MaxTeamBaseMatches", 10);
        OLY_MIN_CF_START = exProperties.getProperty("MinParticipantClassFree", 11);
        OLY_MIN_CB_START = exProperties.getProperty("MinParticipantClassBase", 11);
        OLY_MIN_TB_START = exProperties.getProperty("MinParticipantTeamBase", 6);
        OLY_LIMIT_ENCHANT_STAT_LEVEL_ARMOR = exProperties.getProperty("LimitEnchantStatLevelArmor", -1);
        OLY_LIMIT_ENCHANT_STAT_LEVEL_WEAPON_MAGE = exProperties.getProperty("LimitEnchantStatLevelMageWeapon", -1);
        OLY_LIMIT_ENCHANT_STAT_LEVEL_WEAPON_PHYS = exProperties.getProperty("LimitEnchantStatLevelPhysWeapon", -1);
        OLY_LIMIT_ENCHANT_STAT_LEVEL_ACCESSORY = exProperties.getProperty("LimitEnchantStatLevelAccessory", -1);
        OLYMPIAD_WINNER_ANNOUCE = exProperties.getProperty("OlympiadWinnerAnnounce", false);
        OLYMPIAD_WINNER_MESSAGE = exProperties.getProperty("OlympiadWinnerAnnounceMessage", "3:RAMPAGE_CUSTOM_MESSAGE;5:OTHER_MESSAGE");
        OLYMPIAD_WINNER_REWARDS = exProperties.getProperty("OlympiadWinnerReward", "");
        OLYMPIAD_REMOVE_SKILL_REUSE_ON_STADIUM = exProperties.getProperty("SkillReuseRemoveOnStadium", true);
        OLYMPIAD_REMOVE_SKILL_REUSE_FROM_STADIUM = exProperties.getProperty("SkillReuseRemoveFromStadium", true);
        ADD_CLAN_REPUTATION_POINT_ON_HERO_GAIN = exProperties.getProperty("OnHeroGainMakeClanReputationReward", true);
        OLY_REMOVE_FORCE_BUFFS = exProperties.getProperty("ForcesChargeRemoveOnStadium", false);
        AMOUNT_CLAN_REPUTATION_POINT_ON_HERO_GAIN = exProperties.getProperty("OnHeroGainAmountClanReputationReward", 1000);
        OLY_REMOVE_AUTOSHOTS = exProperties.getProperty("SoulshotsDisableOnStadium", true);
        OLY_STATISTIC_PAST_PERIOD = exProperties.getProperty("OlympiadStatisticOnPastPeriod", true);
        OLYMPIAD_NEW_STADIUMS = exProperties.getProperty("OlympiadNewStadiums", false);
    }

    public static void loadQuestRateSettings() {
        ExProperties exProperties = Config.load(QUEST_RATE_FILE);
        HashMap<Integer, QuestRates> hashMap = new HashMap<Integer, QuestRates>();
        for (Object object : exProperties.keySet()) {
            int n;
            String string = object.toString();
            int n2 = 0;
            for (n = 0; n < string.length(); ++n) {
                if (string.charAt(n) == '_') continue;
                if (!Character.isDigit(string.charAt(n))) break;
                n2 = n2 * 10 + (string.charAt(n) - 48);
            }
            if (n2 == 0) {
                am.warn("Can't parse quest id for quest rate \"" + string + "\"");
                continue;
            }
            n = string.indexOf(46);
            if (n < 0) {
                am.warn("Can't parse quest rate param \"" + string + "\"");
                continue;
            }
            String string2 = string.substring(n + 1);
            QuestRates questRates = (QuestRates)hashMap.get(n2);
            if (questRates == null) {
                questRates = new QuestRates(n2);
                hashMap.put(n2, questRates);
            }
            try {
                questRates.updateParam(string2, exProperties.getProperty(string));
            }
            catch (Exception exception) {
                am.warn("Can't process quest rate setting \"" + string + "\"", (Throwable)exception);
            }
        }
        QUEST_RATES.clear();
        QUEST_RATES.putAll(hashMap);
    }

    public static void load() {
        Config.loadServerConfig();
        Config.loadTelnetConfig();
        Config.loadResidenceConfig();
        Config.loadOtherConfig();
        Config.loadSpoilConfig();
        Config.loadClanConfig();
        Config.loadExperienceConfig();
        Config.loadFormulasConfig();
        Config.loadAltSettings();
        Config.loadBossSettings();
        Config.loadServicesSettings();
        Config.loadPvPSettings();
        Config.loadAISettings();
        Config.loadGeodataSettings();
        Config.loadEventsSettings();
        Config.loadOlympiadSettings();
        Config.loadQuestRateSettings();
        Config.loadChatFilters();
        Config.loadGMAccess();
        Config.loadFarmConfig();
    }

    private Config() {
    }

    public static void loadChatFilters() {
        ChatFilterParser.getInstance().load();
    }

    public static void loadPacketFilter() {
        PacketFilterParser.getInstance().load();
    }

    public static void loadGMAccess() {
        gmlist.clear();
        Config.loadGMAccess(new File(GM_PERSONAL_ACCESS_FILE));
        File file = new File(GM_ACCESS_FILES_DIR);
        if (!file.exists() || !file.isDirectory()) {
            am.info("Dir " + file.getAbsolutePath() + " not exists.");
            return;
        }
        for (File file2 : file.listFiles()) {
            if (file2.isDirectory() || !file2.getName().endsWith(".xml")) continue;
            Config.loadGMAccess(file2);
        }
    }

    public static void loadGMAccess(File file) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setValidating(false);
            documentBuilderFactory.setIgnoringComments(true);
            org.w3c.dom.Document document = documentBuilderFactory.newDocumentBuilder().parse(file);
            for (Node node = document.getFirstChild(); node != null; node = node.getNextSibling()) {
                for (Node node2 = node.getFirstChild(); node2 != null; node2 = node2.getNextSibling()) {
                    if (!node2.getNodeName().equalsIgnoreCase("char")) continue;
                    PlayerAccess playerAccess = new PlayerAccess();
                    for (Node node3 = node2.getFirstChild(); node3 != null; node3 = node3.getNextSibling()) {
                        Field field;
                        Class<?> clazz = playerAccess.getClass();
                        String string = node3.getNodeName();
                        if ("#text".equalsIgnoreCase(string)) continue;
                        try {
                            field = clazz.getField(string);
                        }
                        catch (NoSuchFieldException noSuchFieldException) {
                            am.info("Not found desclarate ACCESS name: " + string + " in XML Player access Object");
                            continue;
                        }
                        if (field.getType().getName().equalsIgnoreCase("boolean")) {
                            field.setBoolean(playerAccess, Boolean.parseBoolean(node3.getAttributes().getNamedItem("set").getNodeValue()));
                            continue;
                        }
                        if (!field.getType().getName().equalsIgnoreCase("int")) continue;
                        field.setInt(playerAccess, Integer.valueOf(node3.getAttributes().getNamedItem("set").getNodeValue()));
                    }
                    gmlist.put(playerAccess.PlayerID, playerAccess);
                }
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static String getField(String string) {
        Field field = FieldUtils.getField(Config.class, (String)string);
        if (field == null) {
            return null;
        }
        try {
            return String.valueOf(field.get(null));
        }
        catch (IllegalArgumentException illegalArgumentException) {
        }
        catch (IllegalAccessException illegalAccessException) {
            // empty catch block
        }
        return null;
    }

    public static boolean setField(String string, String string2) {
        block9: {
            Field field = FieldUtils.getField(Config.class, (String)string);
            if (field == null) {
                return false;
            }
            try {
                if (field.getType() == Boolean.TYPE) {
                    field.setBoolean(null, BooleanUtils.toBoolean((String)string2));
                    break block9;
                }
                if (field.getType() == Integer.TYPE) {
                    field.setInt(null, NumberUtils.toInt((String)string2));
                    break block9;
                }
                if (field.getType() == Long.TYPE) {
                    field.setLong(null, NumberUtils.toLong((String)string2));
                    break block9;
                }
                if (field.getType() == Double.TYPE) {
                    field.setDouble(null, NumberUtils.toDouble((String)string2));
                    break block9;
                }
                if (field.getType() == String.class) {
                    field.set(null, string2);
                    break block9;
                }
                return false;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                return false;
            }
            catch (IllegalAccessException illegalAccessException) {
                return false;
            }
        }
        return true;
    }

    public static ExProperties load(String string) {
        return Config.load(new File(string));
    }

    public static ExProperties load(File file) {
        ExProperties exProperties = new ExProperties();
        try {
            exProperties.load(file);
        }
        catch (IOException iOException) {
            am.error("Error loading config : " + file.getName() + "!");
        }
        return exProperties;
    }

    static {
        VALID_IPS_LIST = new HashSet<String>();
        SELECTOR_CONFIG = new SelectorConfig();
        CNAME_MAXLEN = 32;
        FARM_EXPEND_LIMIT_PRICE = new int[2];
        SERVICES_RATE_BONUS_INFO = new RateBonusInfo[0];
        gmlist = new HashMap<Integer, PlayerAccess>();
        SKILL_DURATION_MOD = new ConcurrentHashMap<Integer, Integer>();
        ALT_CHAMPION_DROP_ITEM_IDS = new HashMap<Integer, int[]>();
        ALT_CHAMPION_DROP_CHANCES = new HashMap<Integer, float[]>();
        ALT_CHAMPION_DROP_COUNTS = new HashMap<Integer, long[]>();
        KARMA_LIST_NONDROPPABLE_ITEMS = new ArrayList<Integer>();
        TELNET_ALLOWED_IPS = new HashSet<String>();
        GOODS_INVENTORY_ENABLED = false;
        QUEST_RATES = new ConcurrentHashMap<Integer, QuestRates>();
    }

    public static class RateBonusInfo {
        public final int id;
        public final int consumeItemId;
        public final long consumeItemAmount;
        public float rateXp = 1.0f;
        public float rateSp = 1.0f;
        public float rateRaidXp = 1.0f;
        public float rateRaidSp = 1.0f;
        public float questRewardRate = 1.0f;
        public float questAdenaRewardRate = 1.0f;
        public float questDropRate = 1.0f;
        public float dropAdena = 1.0f;
        public float dropItems = 1.0f;
        public float dropRaidItems = 1.0f;
        public float dropSpoil = 1.0f;
        public float dropSealStones = 1.0f;
        public float enchantItemMul = 1.0f;
        public float enchantSkillMul = 1.0f;
        public int hwidLimits = 1;
        public List<Pair<Integer, Long>> rewardItem = new ArrayList<Pair<Integer, Long>>();
        public Integer nameColor = null;
        public long bonusTimeSeconds = 0L;

        public RateBonusInfo(int n, int n2, long l) {
            this.id = n;
            this.consumeItemId = n2;
            this.consumeItemAmount = l;
        }

        public Bonus makeBonus() {
            Bonus bonus = new Bonus();
            bonus.setBonusExpire(this.bonusTimeSeconds + System.currentTimeMillis() / 1000L);
            bonus.setRateXp(this.rateXp);
            bonus.setRateSp(this.rateSp);
            bonus.setRateRaidXp(this.rateRaidXp);
            bonus.setRateRaidSp(this.rateRaidSp);
            bonus.setQuestRewardRate(this.questRewardRate);
            bonus.setQuestRewardAdenaRate(this.questAdenaRewardRate);
            bonus.setQuestDropRate(this.questDropRate);
            bonus.setDropAdena(this.dropAdena);
            bonus.setDropItems(this.dropItems);
            bonus.setDropSealStones(this.dropSealStones);
            bonus.setDropRaidItems(this.dropRaidItems);
            bonus.setDropSpoil(this.dropSpoil);
            bonus.setEnchantItem(this.enchantItemMul);
            bonus.setEnchantSkill(this.enchantSkillMul);
            bonus.setHwidsLimit(this.hwidLimits);
            return bonus;
        }
    }

    public static final class OlySeasonTimeCalcMode
    extends Enum<OlySeasonTimeCalcMode> {
        public static final /* enum */ OlySeasonTimeCalcMode NORMAL = new OlySeasonTimeCalcMode();
        public static final /* enum */ OlySeasonTimeCalcMode CUSTOM = new OlySeasonTimeCalcMode();
        private static final /* synthetic */ OlySeasonTimeCalcMode[] a;

        public static OlySeasonTimeCalcMode[] values() {
            return (OlySeasonTimeCalcMode[])a.clone();
        }

        public static OlySeasonTimeCalcMode valueOf(String string) {
            return Enum.valueOf(OlySeasonTimeCalcMode.class, string);
        }

        private static /* synthetic */ OlySeasonTimeCalcMode[] a() {
            return new OlySeasonTimeCalcMode[]{NORMAL, CUSTOM};
        }

        static {
            a = OlySeasonTimeCalcMode.a();
        }
    }
}
