/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.configuration.ExProperties
 *  l2.gameserver.Config
 *  l2.gameserver.handler.bbs.ICommunityBoardHandler
 *  l2.gameserver.model.Player
 *  l2.gameserver.scripts.ScriptFile
 *  org.apache.commons.lang3.ArrayUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package services.community.custom;

import java.util.StringTokenizer;
import l2.commons.configuration.ExProperties;
import l2.gameserver.Config;
import l2.gameserver.handler.bbs.ICommunityBoardHandler;
import l2.gameserver.model.Player;
import l2.gameserver.scripts.ScriptFile;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ACbConfigManager
implements ICommunityBoardHandler,
ScriptFile {
    private static final Logger ep = LoggerFactory.getLogger(ACbConfigManager.class);
    public static final String PVPCB_FILE = "config/pvpcommunityboard.properties";
    public static int FIRST_CLASS_ID;
    public static int FIRST_CLASS_PRICE;
    public static int SECOND_CLASS_ID;
    public static int SECOND_CLASS_PRICE;
    public static int THRID_CLASS_ID;
    public static int THRID_CLASS_PRICE;
    public static boolean ALLOW_PVPCB_ABNORMAL;
    public static boolean ALLOW_PVPCB_AT_SIEGE_ZONES;
    public static boolean ALLOW_PVPCB_AT_PVP_ZONES;
    public static boolean ALLOW_PVPCB_AT_FUN_ZONES;
    public static boolean ALLOW_PVPCB_AT_EPIC_ZONES;
    public static boolean ALLOW_PVPCB_FOR_PK;
    public static boolean ALLOW_PVPCB_FOR_KARMA;
    public static boolean ALLOW_PVPCB_AT_EVENTS;
    public static boolean ALLOW_PVPCB_FOR_OLY;
    public static boolean ALLOW_PVPCB_SHOP;
    public static boolean ALLOW_PVPCB_ECHANT;
    public static boolean ALLOW_PVPCB_CLASSMASTER;
    public static boolean ALLOW_PVPCB_TELEPORT;
    public static boolean CLOSE_PVPCB_AFTER_TELEPORT;
    public static boolean ALLOW_PVPCB_TELEPORT_AT_TOWN_ZONE_ONLY;
    public static int[] COMMUNITY_CLASS_MASTERS_REWARD_ITEM;
    public static int[] COMMUNITY_MASTERS_REWARD_AMOUNT;
    public static StringTokenizer st;
    public static int ALT_CB_TELE_POINT_PRICE;
    public static int ALT_CB_TELE_POINT_MAX_COUNT;
    public static boolean ALT_CB_TELE_PREMIUM_ONLY;
    public static int ALT_CB_TELE_ITEM_ACCESS;
    public static boolean BBS_BUFFER_PEACE_ONLY;
    public static int BBS_BUFFER_ACCESS_ITEM;
    public static boolean BBS_BUFFER_ACCESS_PREMIUM_ONLY;
    public static boolean BBS_BUFFER_PREMIUM_HTML_PREFIX;
    public static int[] BBS_BUFFER_PREMIUM_ITEM_IDS;
    public static int ALT_CB_DELVLV_ITEM_ID;
    public static long ALT_CB_DELVL_ITEM_COUNT;
    public static int ALT_CB_NOBLES_ITEM_ID;
    public static long ALT_CB_NOBLES_ITEM_COUNT;
    public static int ALT_CB_CHANGESEX_ITEM_ID;
    public static long ALT_CB_CHANGESEX_ITEM_COUNT;
    public static int ALT_CB_CHANGENAME_ITEM_ID;
    public static long ALT_CB_CHANGENAME_ITEM_COUNT;
    public static String ALT_CB_CUSTOM_CNAME_TEMPLATE;
    public static int ALT_CB_CLANUP_ITEM_ID;
    public static long ALT_CB_CLANUP_ITEM_COUNT;
    public static int ALT_CB_CLAN_PENALTY_ITEM_ID;
    public static long ALT_CB_CLAN_PENALTY_ITEM_COUNT;
    public static boolean ALT_CB_ALLOW_AUGMENT_BBS;
    public static boolean ALT_CB_ALLOW_AUGMENT_CANCEL_BBS;
    public static boolean ALT_CB_ALLOW_ENSOUL;
    public static double ALT_CB_ONLINE_MULTIPLIER;

    public String[] getBypassCommands() {
        return null;
    }

    public void onBypassCommand(Player player, String string) {
    }

    public void onWriteCommand(Player player, String string, String string2, String string3, String string4, String string5, String string6) {
    }

    public void onLoad() {
        if (Config.COMMUNITYBOARD_ENABLED) {
            ep.info("CommunityBoard: Custom Community Config loaded.");
            ACbConfigManager.loadPvPCBSettings();
        }
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public static void loadPvPCBSettings() {
        ExProperties exProperties = Config.load((String)PVPCB_FILE);
        ALLOW_PVPCB_ABNORMAL = exProperties.getProperty("AllowBBSAbnormal", false);
        ALLOW_PVPCB_AT_SIEGE_ZONES = exProperties.getProperty("AllowBBSAtSiegeZone", true);
        ALLOW_PVPCB_AT_PVP_ZONES = exProperties.getProperty("AllowBBSAtPvPZone", true);
        ALLOW_PVPCB_AT_FUN_ZONES = exProperties.getProperty("AllowBBSAtFunZone", true);
        ALLOW_PVPCB_AT_EPIC_ZONES = exProperties.getProperty("AllowBBSAtEpicZone", true);
        ALLOW_PVPCB_FOR_PK = exProperties.getProperty("AllowBBSForPKPlayers", true);
        ALLOW_PVPCB_FOR_KARMA = exProperties.getProperty("AllowBBSForKarmaPlayers", true);
        ALLOW_PVPCB_FOR_OLY = exProperties.getProperty("AllowBBSOnOlympiad", false);
        ALLOW_PVPCB_AT_EVENTS = exProperties.getProperty("AllowBBSAtEvents", false);
        ALLOW_PVPCB_SHOP = exProperties.getProperty("AllowBBSShop", true);
        ALLOW_PVPCB_ECHANT = exProperties.getProperty("AllowBBSEnchant", true);
        ALLOW_PVPCB_CLASSMASTER = exProperties.getProperty("AllowBBSClassMaster", true);
        ALLOW_PVPCB_TELEPORT = exProperties.getProperty("AllowBBSTeleport", true);
        ALLOW_PVPCB_TELEPORT_AT_TOWN_ZONE_ONLY = exProperties.getProperty("AllowBBSTeleportOnlyTownZone", false);
        CLOSE_PVPCB_AFTER_TELEPORT = exProperties.getProperty("BBSCloseAfterTeleport", false);
        FIRST_CLASS_ID = exProperties.getProperty("FirstProffesionId", 57);
        FIRST_CLASS_PRICE = exProperties.getProperty("FirstProffesionCount", 10000000);
        SECOND_CLASS_ID = exProperties.getProperty("SecondProffesionId", 57);
        SECOND_CLASS_PRICE = exProperties.getProperty("SecondProffesionCount", 20000000);
        THRID_CLASS_ID = exProperties.getProperty("ThridProffesionId", 57);
        THRID_CLASS_PRICE = exProperties.getProperty("ThridProffesionCount", 30000000);
        COMMUNITY_CLASS_MASTERS_REWARD_ITEM = exProperties.getProperty("CommunityProffReward", new int[]{0, 0, 0});
        COMMUNITY_MASTERS_REWARD_AMOUNT = exProperties.getProperty("CommunityProffAmount", new int[]{0, 0, 0});
        ALT_CB_TELE_POINT_PRICE = exProperties.getProperty("CommunityTeleporterPointPrice", 100);
        ALT_CB_TELE_POINT_MAX_COUNT = exProperties.getProperty("CommunityTeleporterPointCount", 10);
        ALT_CB_TELE_PREMIUM_ONLY = exProperties.getProperty("CommunityTeleporterPremiumOnly", false);
        ALT_CB_TELE_ITEM_ACCESS = exProperties.getProperty("CommunityTeleporterItemAccess", 0);
        ALT_CB_DELVLV_ITEM_ID = exProperties.getProperty("CommunityDeLevelItemId", 57);
        ALT_CB_DELVL_ITEM_COUNT = exProperties.getProperty("CommunityDeLevelItemCount", 100L);
        ALT_CB_NOBLES_ITEM_ID = exProperties.getProperty("CommunityNobleItemId", 57);
        ALT_CB_NOBLES_ITEM_COUNT = exProperties.getProperty("CommunityNobleItemCount", 100L);
        ALT_CB_CHANGESEX_ITEM_ID = exProperties.getProperty("CommunityChangeSexItemId", 57);
        ALT_CB_CHANGESEX_ITEM_COUNT = exProperties.getProperty("CommunityChangeSexItemCount", 100L);
        ALT_CB_CHANGENAME_ITEM_ID = exProperties.getProperty("CommunityChangeNameItemId", 57);
        ALT_CB_CHANGENAME_ITEM_COUNT = exProperties.getProperty("CommunityChangeNameItemCount", 100L);
        ALT_CB_CUSTOM_CNAME_TEMPLATE = exProperties.getProperty("CommunityChangeNameCustomTemplate", "[A-Za-z0-9\u0410-\u042f\u0430-\u044f]{2,16}");
        ALT_CB_CLANUP_ITEM_ID = exProperties.getProperty("CommunityClanupNameItemId", 57);
        ALT_CB_CLANUP_ITEM_COUNT = exProperties.getProperty("CommunityClanupItemCount", 100000000L);
        ALT_CB_CLAN_PENALTY_ITEM_ID = exProperties.getProperty("ClanPenaltyClearItem", 4037);
        ALT_CB_CLAN_PENALTY_ITEM_COUNT = exProperties.getProperty("ClanPenaltyClearCount", 1L);
        ALT_CB_ALLOW_AUGMENT_BBS = exProperties.getProperty("AllowAugmentItemsFromCB", false);
        ALT_CB_ALLOW_AUGMENT_CANCEL_BBS = exProperties.getProperty("AllowAugmentCancelFromCB", false);
        ALT_CB_ALLOW_ENSOUL = exProperties.getProperty("AllowEnsoulFunction", false);
        ALT_CB_ONLINE_MULTIPLIER = exProperties.getProperty("BBSOnlineMultiplier", 1.0);
        BBS_BUFFER_PEACE_ONLY = exProperties.getProperty("AllowCommunityBoardBufferAtPeaceZoneOnly", false);
        BBS_BUFFER_ACCESS_ITEM = exProperties.getProperty("AllowCommunityBoardBufferByItem", 0);
        BBS_BUFFER_ACCESS_PREMIUM_ONLY = exProperties.getProperty("AllowCommunityBoardBufferPremiumOnly", false);
        BBS_BUFFER_PREMIUM_HTML_PREFIX = exProperties.getProperty("CommunityBufferPremiumHtmlPrefix", false);
        BBS_BUFFER_PREMIUM_ITEM_IDS = exProperties.getProperty("CommunityBufferPremiumItemPrefix", ArrayUtils.EMPTY_INT_ARRAY);
    }
}
