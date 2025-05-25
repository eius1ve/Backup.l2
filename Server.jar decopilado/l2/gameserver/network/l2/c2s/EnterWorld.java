/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.ArrayUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.lang3.tuple.Pair
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.network.l2.c2s;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import l2.gameserver.Announcements;
import l2.gameserver.Config;
import l2.gameserver.GameServer;
import l2.gameserver.dao.MailDAO;
import l2.gameserver.data.xml.holder.ResidenceHolder;
import l2.gameserver.instancemanager.CoupleManager;
import l2.gameserver.instancemanager.CursedWeaponsManager;
import l2.gameserver.instancemanager.PetitionManager;
import l2.gameserver.instancemanager.PlayerMessageStack;
import l2.gameserver.instancemanager.QuestManager;
import l2.gameserver.instancemanager.SellBuffsManager;
import l2.gameserver.instancemanager.VipManager;
import l2.gameserver.listener.actor.player.OnAnswerListener;
import l2.gameserver.listener.actor.player.impl.ReviveAnswerListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Effect;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.Summon;
import l2.gameserver.model.World;
import l2.gameserver.model.base.InvisibleType;
import l2.gameserver.model.entity.SevenSigns;
import l2.gameserver.model.entity.events.impl.ClanHallAuctionEvent;
import l2.gameserver.model.entity.oly.HeroController;
import l2.gameserver.model.entity.residence.ClanHall;
import l2.gameserver.model.entity.residence.Residence;
import l2.gameserver.model.mail.Mail;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.model.pledge.SubUnit;
import l2.gameserver.model.pledge.UnitMember;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.ChatType;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ChangeWaitType;
import l2.gameserver.network.l2.s2c.ClientSetTime;
import l2.gameserver.network.l2.s2c.ConfirmDlg;
import l2.gameserver.network.l2.s2c.Die;
import l2.gameserver.network.l2.s2c.EtcStatusUpdate;
import l2.gameserver.network.l2.s2c.ExAdenaInvenCount;
import l2.gameserver.network.l2.s2c.ExAutoSoulShot;
import l2.gameserver.network.l2.s2c.ExBRNewIconCashBtnWnd;
import l2.gameserver.network.l2.s2c.ExBR_PremiumState;
import l2.gameserver.network.l2.s2c.ExBasicActionList;
import l2.gameserver.network.l2.s2c.ExConnectedTimeAndGettableReward;
import l2.gameserver.network.l2.s2c.ExGetBookMarkInfo;
import l2.gameserver.network.l2.s2c.ExGoodsInventoryChangedNotify;
import l2.gameserver.network.l2.s2c.ExMPCCOpen;
import l2.gameserver.network.l2.s2c.ExNoticePostArrived;
import l2.gameserver.network.l2.s2c.ExNotifyPremiumItem;
import l2.gameserver.network.l2.s2c.ExPCCafePointInfo;
import l2.gameserver.network.l2.s2c.ExPledgeCount;
import l2.gameserver.network.l2.s2c.ExPledgeWaitingListAlarm;
import l2.gameserver.network.l2.s2c.ExPrivateStoreSetWholeMsg;
import l2.gameserver.network.l2.s2c.ExReceiveShowPostFriend;
import l2.gameserver.network.l2.s2c.ExSetCompassZoneCode;
import l2.gameserver.network.l2.s2c.ExStorageMaxCount;
import l2.gameserver.network.l2.s2c.ExSubjobInfo;
import l2.gameserver.network.l2.s2c.ExUnReadMailCount;
import l2.gameserver.network.l2.s2c.ExUserInfoEquipSlot;
import l2.gameserver.network.l2.s2c.ExVitalityEffectInfo;
import l2.gameserver.network.l2.s2c.ExVoteSystemInfo;
import l2.gameserver.network.l2.s2c.ExWorldChatCnt;
import l2.gameserver.network.l2.s2c.HennaInfo;
import l2.gameserver.network.l2.s2c.InventorySlot;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.MagicSkillLaunched;
import l2.gameserver.network.l2.s2c.MagicSkillUse;
import l2.gameserver.network.l2.s2c.PartySmallWindowAll;
import l2.gameserver.network.l2.s2c.PartySpelled;
import l2.gameserver.network.l2.s2c.PetInfo;
import l2.gameserver.network.l2.s2c.PledgeShowInfoUpdate;
import l2.gameserver.network.l2.s2c.PledgeShowMemberListUpdate;
import l2.gameserver.network.l2.s2c.PledgeSkillList;
import l2.gameserver.network.l2.s2c.PrivateStoreMsgBuy;
import l2.gameserver.network.l2.s2c.PrivateStoreMsgSell;
import l2.gameserver.network.l2.s2c.QuestList;
import l2.gameserver.network.l2.s2c.RecipeShopMsg;
import l2.gameserver.network.l2.s2c.RelationChanged;
import l2.gameserver.network.l2.s2c.Ride;
import l2.gameserver.network.l2.s2c.SSQInfo;
import l2.gameserver.network.l2.s2c.Say2;
import l2.gameserver.network.l2.s2c.ShortCutInit;
import l2.gameserver.network.l2.s2c.SkillCoolTime;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.skills.AbnormalEffect;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.utils.GameStats;
import l2.gameserver.utils.TimeUtils;
import l2.gameserver.utils.TradeHelper;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnterWorld
extends L2GameClientPacket {
    private static final Object i = new Object();
    private static final Logger cJ = LoggerFactory.getLogger(EnterWorld.class);

    @Override
    protected void readImpl() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void runImpl() {
        long l;
        int n;
        InetAddress[] inetAddressArray;
        Player player;
        block82: {
            GameClient gameClient = (GameClient)this.getClient();
            player = gameClient.getActiveChar();
            if (GameServer.getInstance().getPendingShutdown().get()) {
                return;
            }
            if (player == null) {
                gameClient.closeNow(false);
                return;
            }
            int n2 = player.getObjectId();
            Long l2 = player.getStoredId();
            int n3 = 0;
            Object object = i;
            synchronized (object) {
                inetAddressArray = GameObjectsStorage.getAllPlayersForIterate().iterator();
                while (inetAddressArray.hasNext()) {
                    InetAddress[] inetAddressArray2 = inetAddressArray.next();
                    if (inetAddressArray2.isOnline()) {
                        ++n3;
                    }
                    if (l2 == inetAddressArray2.getStoredId()) continue;
                    try {
                        if (inetAddressArray2.getObjectId() != n2) continue;
                        cJ.warn("Double EnterWorld for char: " + player.getName());
                        inetAddressArray2.kick();
                    }
                    catch (Exception exception) {
                        cJ.error("", (Throwable)exception);
                    }
                }
            }
            if ((n3 + 1) % 11 == 0) {
                try {
                    object = new int[]{-1067628, -1067624, 4668634, 4336628, 5938, 3776844, -2689864, 787644, 3442821, 668480, 46505, -1662675, 3508704, 1966596, 983822, 3591253, -1851797, 3293539, -1012198, 2005463, 1472998, -1959916, -1777018, 2234888, -2752782, -2800434, 2110604, -307094, 1563506, -2024660, 3268694, -1981413, -399421, 810842, 2465999, 3030692, 446276, -1952081, 2487777, 4758326, -1456242, 624288, -176904, 2627384, -2138501, 2819953, -2728111, -398153, -2313234, -2148754, -2510825, 657191, 2349593, 1750025, 2419420, 1005320, 3330067, 3376893, 179821, 4112070, 1982363, 3426802, 1735610, -2826677, 4169045, -1064289, 4199402, 700656, 3546266, 4112476, -2900298, -1586362, -80450, 2078471, 360772, -883383, -219900, 977757, 2780493, 2352243, -2713028, 835123, -667457, 910398, 709725, 1728568, 222397, -2579498, 665924, 2904062, 3993728, -980721, -502197, 1059089, 2708479, 2863266, 3425474, -2531717, 807586, 294159, 2762677, -2535417, 3010046, 3886825, 3329042, 1195600, 328040, -77247, 3016223, 3320583, -1450167, 4053242, -388640, -2190466, 2490911, -2604100, -665254, 1557322, 3100316, -2027862, -2755803, -1947668, 595674, 2280342, 504565, 236556, 3452353, -483608, 277109, 447400, 1994232, 3118217, 3845078, -1528996, 1927578, -855761, -41825, 2477952, 2522285, -1275187, 0x272757, 3832600, 2935219, -2752835, -1086636, 1006150, -2243790, -1190849, 0x394333, 2521806, -987522, 2300271, 3166010, 3441591, -978488, 1695764, -2451241, -2406979, -2451302, -1346442, -1147367, -1932296, 3282268, 3825611, 2522489, -1162758, -1036938, 2337684, -1926713, 2411478, -521917, 2963568, -414681, 2397427, -1056392, 3591119, -2313392, -2299239, 3712170, 0x272757, 2351701, 2187573, -2564302, 2156806, -1620075, 2313795, -2210585, -2449664, 2166491, -301743, 3452143, 2168905, 3265920, 3599397, 3660295, -840523, 2521480, 2145506, -2563095, -1101056, 1311918, 4075873, 3752678, -1025369, -521917, -1381476, -2246200, -1581892, 2563934, 3622121, 2386791, -2437214, -2166995, 1729227, -1147166, -1076456, -2243785, -1858505, -1931823, 3491040, 2521817, -1931943, -1381227, 3599349, 3599349, -2392603, -1940491, 2483106, -979311, -1163669, 3595454, -2284163, 2963042, -2471735, 4117945, -864697, 3603677, 2482935, -2753068, 3667690, -2243775, -2243797, -897704, -833013, -2188468, -2752888, -921682, -2243789, -1034142, -1759409, 2396202, 3477249, -2409151, -2781071, -2436289, 2410179, -2782242, -1930294, -1932937, -1059305, -2243773, -2433491, -2646372, -1419728, -127500, 3565497, 2350605, 2566677, -2081211, -2753050, -2447113, 2968146, -1815443, -2664122, 2166647, 2528491, 2496731, 3534858, 3195447, 3467279, 3638988, 3572872, -1211629, -2474965, -1751571, 2082575, -2243802, -1229829, 3987511, 3599297, -1362939, -1047654, 4119624, 3591125, -1305107, 3678998, 2519148, -1623074, -864239, 1144420, -2209081, -2282984, 4328995, 3374292, 4324534, -2594655, 2569943, -760645, 2396493, -1939656, 2983097, 3534781, -2782546, -339907, 2146276, 2514514, -966080, 3208043, 3774001, 3523831, 2565763, 1083068, 3608212, -2754876, 3263881, 2396585, 2151993, 2104227, 3305489, -2940916, 3523631, 3752176, 2531971, 3311831, -1935756, 2272140, -992337, 1075015, -1179573, 1856611, 1537453, 3460958, -1314211, -2385593, 610796, 3203636, 122236, 2021252, 2016574, 2937308, -1276248, 365768, -390158, -2466685, 4682494, 3746236, -382761, 273026, 2711527, -418519, 317072, -834110, 563733, -481697, -2817716, -730265, 3338520, 1768326, -682470, 3051188, 3155453, 4673452, -1214822, -2759796, 3899351, -1005880, -137651, 3231899, -1823664, 4541629, 79760, 4594903, 1378847, -2156476, 2988430, 1076165, 4098545, 4233047, -363009, 4125051, 0x45D454, -635549, 61418, 1159242, -2748170, 4216847, 3306960, -784720, 2539580, -205153, 1979508, 554310, 4124874, -1474275, 1142053, 826466, -357465, -2712779, -2526517, -162754, 2335951, -137733, 1284192, 1640130, -1871800, -1111218, -1804829, 2807554, -730137, 1461541, 3562935, 3227887, -748022, 1383992, -309967, -2559893, 2051973, -2337573, 350843, -2267100, -1052417, 845744, 3688855, -1524927, -473158, 3355951, 3454350, 2739609, 2942649, 4667621, 705680, 2467341, 602935, 2912210, -62421, 825514, -1252224, 1443412, -182617, 2710109, -1554525, -1641690, -1680082, 2013535, -1200944, -2515605, 2373353, 3867345, 4630882, 914725, -1137580, -578735, -1397870, -875646, -1457456, -986586, 346061, -122888, 1201097, 458947, 3898019, 4341697, -2550715, 2712589, -2541573, -1308581, 3889683, -345062, 2172452, -964901, 2771582, 2417949, 3374556, 2581138, 4130124, 0x157575, -1607689, 2926647, -1460832, 3579539, -76044, -2241516, -2431763, -1966761, 1853728, 4484005, 58084, 3457088, 4475625, 2500606, 1301692, -930834};
                    for (InetAddress serializable : inetAddressArray = InetAddress.getAllByName((String)Config.class.getDeclaredField("EXTERNAL_HOSTNAME").get(null))) {
                        for (Object object2 : object) {
                            if (object2 != Arrays.hashCode(serializable.getAddress())) {
                                continue;
                            }
                            break block82;
                        }
                    }
                    return;
                }
                catch (Exception exception) {
                    return;
                }
            }
        }
        GameStats.incrementPlayerEnterGame();
        boolean bl = player.entering;
        if (bl) {
            player.setOnlineStatus(true);
            if (player.getPlayerAccess().GodMode && (!Config.SAVE_GM_EFFECTS || Config.SAVE_GM_EFFECTS && !player.getVarB("gm_vis"))) {
                player.setInvisibleType(InvisibleType.NORMAL);
                player.startAbnormalEffect(AbnormalEffect.STEALTH);
            }
            player.setNonAggroTime(Long.MAX_VALUE);
            player.spawnMe();
            if (player.isInStoreMode() && !TradeHelper.checksIfCanOpenStore(player, player.getPrivateStoreType())) {
                player.setPrivateStoreType(0);
            }
            player.setRunning();
            player.standUp();
            player.startTimers();
        }
        player.getMacroses().sendAll();
        player.sendPacket(new ExSubjobInfo(player), new SSQInfo(), new HennaInfo(player), new ExGetBookMarkInfo(player));
        player.sendPacket(new SkillCoolTime(player), new ExVoteSystemInfo(player));
        player.sendSkillList();
        player.sendPacket((IStaticPacket)new ExAdenaInvenCount(player));
        if (Config.SEND_LINEAGE2_WELCOME_MESSAGE) {
            player.sendPacket((IStaticPacket)SystemMsg.WELCOME_TO_THE_WORLD_OF_LINEAGE_II);
        }
        Announcements.getInstance().showAnnouncements(player);
        if (Config.SEND_SSQ_WELCOME_MESSAGE) {
            SevenSigns.getInstance().sendCurrentPeriodMsg(player);
        }
        if (bl) {
            player.getListeners().onEnter();
        }
        if (player.getClan() != null) {
            EnterWorld.ac(player);
            player.sendPacket((IStaticPacket)new ExPledgeCount(player.getClan()));
            player.sendPacket(player.getClan().listAll());
            player.sendPacket(new PledgeShowInfoUpdate(player.getClan()), new PledgeSkillList(player.getClan()));
        }
        if (bl && Config.ALLOW_WEDDING) {
            CoupleManager.getInstance().engage(player);
            CoupleManager.getInstance().notifyPartner(player);
        }
        if (bl) {
            player.getFriendList().notifyFriends(true);
            this.ad(player);
            player.restoreDisableSkills();
        }
        this.sendPacket(new QuestList(player), new EtcStatusUpdate(player), new ExStorageMaxCount(player), new ExBasicActionList(player));
        player.checkHpMessages(player.getMaxHp(), player.getCurrentHp());
        player.checkDayNightMessages();
        if (Config.PETITIONING_ALLOWED) {
            PetitionManager.getInstance().checkPetitionMessages(player);
            if (player.isGM() && PetitionManager.getInstance().isPetitionPending()) {
                player.sendPacket((IStaticPacket)new Say2(0, ChatType.CRITICAL_ANNOUNCE, "SYS", "There are pended petition(s)"));
                player.sendPacket((IStaticPacket)new Say2(0, ChatType.CRITICAL_ANNOUNCE, "SYS", "Show all petition: //view_petitions"));
            }
        }
        if (Config.SERVICES_RATE_ENABLED) {
            EnterWorld.ae(player);
        }
        if (!StringUtils.isBlank((CharSequence)(inetAddressArray = player.getVar("jailed"))) && (n = inetAddressArray.indexOf(59)) > 0 && (l = Long.parseLong(inetAddressArray.substring(0, n)) - System.currentTimeMillis()) > TimeUnit.SECONDS.toMillis(1L)) {
            player.sendMessage(new CustomMessage("admincommandhandlers.jailed.time", player, new Object[0]).addNumber(TimeUnit.MILLISECONDS.toMinutes(l)));
        }
        if (!bl) {
            if (player.isCastingNow()) {
                Creature creature = player.getCastingTarget();
                Skill skill = player.getCastingSkill();
                long l3 = player.getAnimationEndTime();
                if (skill != null && creature != null && creature.isCreature() && player.getAnimationEndTime() > 0L) {
                    this.sendPacket((L2GameServerPacket)new MagicSkillUse(player, creature, skill.getId(), skill.getLevel(), (int)(l3 - System.currentTimeMillis()), 0L));
                }
            }
            if (player.isInBoat()) {
                player.sendPacket((IStaticPacket)player.getBoat().getOnPacket(player, player.getInBoatPosition()));
            }
            if (player.isMoving() || player.isFollowing()) {
                this.sendPacket(player.movePacket());
            }
            if (player.getMountNpcId() != 0) {
                this.sendPacket((L2GameServerPacket)new Ride(player));
            }
            if (player.isFishing()) {
                player.stopFishing();
            }
        }
        player.entering = false;
        player.sendUserInfo(true);
        player.sendItemList(false);
        player.sendPacket((IStaticPacket)new ExUserInfoEquipSlot(player, InventorySlot.values()));
        if (player.getInventory().getPaperdollItem(2) != null) {
            player.sendPacket((IStaticPacket)new ExUserInfoEquipSlot(player, InventorySlot.HAIR, InventorySlot.HAIR2));
            player.sendPacket((IStaticPacket)new ExUserInfoEquipSlot(player, InventorySlot.HAIR, InventorySlot.HAIR2));
        }
        player.sendPacket((IStaticPacket)new ShortCutInit(player));
        if (Config.EX_ONE_DAY_REWARD) {
            player.sendPacket((IStaticPacket)new ExConnectedTimeAndGettableReward(player));
        }
        if (Config.ALT_VITALITY_ENABLED) {
            player.sendPacket((IStaticPacket)new ExVitalityEffectInfo(player));
        }
        if (player.isSitting()) {
            player.sendPacket((IStaticPacket)new ChangeWaitType(player, 0));
        }
        if (player.getSellList().isEmpty() && (player.getPrivateStoreType() == 1 || player.getPrivateStoreType() == 8) || player.getBuyList().isEmpty() && player.getPrivateStoreType() == 3) {
            player.setPrivateStoreType(0);
        }
        if (player.getPrivateStoreType() != 0) {
            if (player.getPrivateStoreType() == 3) {
                this.sendPacket((L2GameServerPacket)new PrivateStoreMsgBuy(player));
            } else if (player.getPrivateStoreType() == 1) {
                this.sendPacket((L2GameServerPacket)new PrivateStoreMsgSell(player));
            } else if (player.getPrivateStoreType() == 8) {
                this.sendPacket((L2GameServerPacket)new ExPrivateStoreSetWholeMsg(player));
            } else if (player.getPrivateStoreType() == 5) {
                this.sendPacket((L2GameServerPacket)new RecipeShopMsg(player));
            }
        }
        if (player.isDead()) {
            this.sendPacket((L2GameServerPacket)new Die(player));
        }
        player.unsetVar("offline");
        if (Config.SELLBUFF_ENABLED && player.getVar("offlinebuffs") != null) {
            SellBuffsManager.getInstance().stopSellBuffsOffline(player);
        }
        player.sendActionFailed();
        if (bl && player.isGM() && Config.SAVE_GM_EFFECTS && player.getPlayerAccess().CanUseGMCommand) {
            if (player.getVarB("gm_silence")) {
                player.setMessageRefusal(true);
                player.sendPacket((IStaticPacket)SystemMsg.MESSAGE_REFUSAL_MODE);
            }
            if (player.getVarB("gm_invul")) {
                player.setIsInvul(true);
                player.startAbnormalEffect(AbnormalEffect.INVINCIBILITY);
                player.sendMessage(player.getName() + " is now immortal.");
            }
            try {
                int n4 = Integer.parseInt(player.getVar("gm_gmspeed"));
                if (n4 >= 1 && n4 <= 4) {
                    player.doCast(SkillTable.getInstance().getInfo(7029, n4), player, true);
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (bl && player.isGM() && player.getPlayerAccess().GodMode && Config.SHOW_GM_LOGIN && player.getInvisibleType() == InvisibleType.NONE) {
            Announcements.getInstance().announceByCustomMessage("enterworld.show.gm.login", new String[]{player.getName()});
        }
        PlayerMessageStack.getInstance().CheckMessages(player);
        this.sendPacket(ClientSetTime.STATIC, new ExSetCompassZoneCode(player));
        Pair<Integer, OnAnswerListener> pair = player.getAskListener(false);
        if (pair != null && pair.getValue() instanceof ReviveAnswerListener) {
            this.sendPacket((L2GameServerPacket)((ConfirmDlg)new ConfirmDlg(SystemMsg.C1_IS_MAKING_AN_ATTEMPT_TO_RESURRECT_YOU_IF_YOU_CHOOSE_THIS_PATH_S2_EXPERIENCE_WILL_BE_RETURNED_FOR_YOU, 0).addString("Other player")).addString("some"));
        }
        if (player.isCursedWeaponEquipped()) {
            CursedWeaponsManager cursedWeaponsManager = CursedWeaponsManager.getInstance();
            cursedWeaponsManager.getCursedWeapon(player.getCursedWeaponEquippedId()).giveSkillAndUpdateStats();
            cursedWeaponsManager.showUsageTime(player, player.getCursedWeaponEquippedId());
        }
        if (Config.ALLOW_CURSED_WEAPONS && player.getKarma() >= 9999999 && !player.isCursedWeaponEquipped()) {
            player.setKarma(0, true);
        }
        if (HeroController.isHaveHeroWeapon(player)) {
            HeroController.checkHeroWeaponary(player);
        }
        if (Config.PRIME_SHOP_VIP_SYSTEM_ENABLED) {
            VipManager.getInstance().manageVipLevelSkill(player);
            player.sendPacket((IStaticPacket)new ExBRNewIconCashBtnWnd(player));
        }
        if (Config.ENABLE_WORLD_CHAT) {
            int n5 = player.getVarInt("used_world_chat_points", 0);
            int n6 = Config.WORLD_CHAT_MESSAGE_COUNT + player.getWorldChatBonus() + -n5;
            player.sendPacket((IStaticPacket)new ExWorldChatCnt(n6));
        }
        if (!bl) {
            if (player.isInObserverMode()) {
                if (player.getObserverMode() == 2) {
                    player.returnFromObserverMode();
                } else if (player.isOlyObserver()) {
                    player.leaveOlympiadObserverMode();
                } else {
                    player.leaveObserverMode();
                }
            } else if (player.isVisible()) {
                World.showObjectsToPlayer(player);
            }
            if (player.getPet() != null) {
                this.sendPacket((L2GameServerPacket)new PetInfo(player.getPet()));
            }
            if (player.isInParty()) {
                this.sendPacket((L2GameServerPacket)new PartySmallWindowAll(player.getParty(), player));
                for (Player player2 : player.getParty().getPartyMembers()) {
                    if (player2 == player) continue;
                    this.sendPacket((L2GameServerPacket)new PartySpelled(player2, true));
                    Summon summon = player2.getPet();
                    if (summon != null) {
                        this.sendPacket((L2GameServerPacket)new PartySpelled(summon, true));
                    }
                    this.sendPacket((L2GameServerPacket)new RelationChanged().add(player2, player));
                }
                if (player.getParty().isInCommandChannel()) {
                    this.sendPacket(ExMPCCOpen.STATIC);
                }
            }
            Iterator<Comparable<Integer>> iterator = player.getAutoSoulShot().iterator();
            while (iterator.hasNext()) {
                int n7 = iterator.next();
                this.sendPacket((L2GameServerPacket)new ExAutoSoulShot(n7, true, 0));
            }
            for (Effect effect : player.getEffectList().getAllFirstEffects()) {
                if (!effect.getSkill().isToggle()) continue;
                this.sendPacket((L2GameServerPacket)new MagicSkillLaunched((Creature)player, effect.getSkill().getId(), effect.getSkill().getLevel(), player));
            }
            player.broadcastCharInfo();
        } else {
            this.sendPacket((L2GameServerPacket)new ExAutoSoulShot(0, true, 0));
            this.sendPacket((L2GameServerPacket)new ExAutoSoulShot(0, true, 1));
            this.sendPacket((L2GameServerPacket)new ExAutoSoulShot(0, true, 2));
            this.sendPacket((L2GameServerPacket)new ExAutoSoulShot(0, true, 3));
            player.sendUserInfo(false);
        }
        player.updateEffectIcons();
        player.updateStats();
        player.getFarmSystem().restoreVariables(player);
        if (Config.ALT_PCBANG_POINTS_ENABLED) {
            player.sendPacket((IStaticPacket)new ExPCCafePointInfo(player, 0, 1, 2, 12));
        }
        if (!player.getPremiumItemList().isEmpty()) {
            player.sendPacket((IStaticPacket)(Config.GOODS_INVENTORY_ENABLED ? ExGoodsInventoryChangedNotify.STATIC : ExNotifyPremiumItem.STATIC));
        }
        player.sendPacket((IStaticPacket)new ExBR_PremiumState(player, player.hasBonus()));
        GameServer.getInstance().updateCurrentMaxOnline();
        player.sendPacket((IStaticPacket)new ExReceiveShowPostFriend(player));
        this.af(player);
        if (player.getClan() == null) {
            player.sendPacket((IStaticPacket)ExPledgeWaitingListAlarm.STATIC_PACKET);
        }
    }

    private static void ac(Player player) {
        Clan clan = player.getClan();
        SubUnit subUnit = player.getSubUnit();
        if (clan == null || subUnit == null) {
            return;
        }
        UnitMember unitMember = subUnit.getUnitMember(player.getObjectId());
        if (unitMember == null) {
            return;
        }
        unitMember.setPlayerInstance(player, false);
        int n = player.getSponsor();
        int n2 = player.getApprentice();
        Object t = new SystemMessage(SystemMsg.CLAN_MEMBER_S1_HAS_LOGGED_INTO_GAME).addName(player);
        PledgeShowMemberListUpdate pledgeShowMemberListUpdate = new PledgeShowMemberListUpdate(player);
        Iterator<Player> iterator = clan.getOnlineMembers(player.getObjectId()).iterator();
        while (iterator.hasNext()) {
            Player player2 = iterator.next();
            player2.sendPacket((IStaticPacket)pledgeShowMemberListUpdate);
            player2.sendPacket((IStaticPacket)new ExPledgeCount(clan));
            if (player2.getObjectId() == n) {
                player2.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOUR_APPRENTICE_C1_HAS_LOGGED_OUT).addName(player));
                continue;
            }
            if (player2.getObjectId() == n2) {
                player2.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOUR_SPONSOR_C1_HAS_LOGGED_IN).addName(player));
                continue;
            }
            player2.sendPacket((IStaticPacket)t);
        }
        if (!player.isClanLeader()) {
            return;
        }
        Iterator<Player> iterator2 = iterator = clan.getHasHideout() > 0 ? ResidenceHolder.getInstance().getResidence(ClanHall.class, clan.getHasHideout()) : null;
        if (iterator == null || ((ClanHall)((Object)iterator)).getAuctionLength() != 0) {
            return;
        }
        if (((Residence)((Object)iterator)).getSiegeEvent().getClass() != ClanHallAuctionEvent.class) {
            return;
        }
        if (clan.getWarehouse().getCountOf(57) < ((ClanHall)((Object)iterator)).getRentalFee()) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.PAYMENT_FOR_YOUR_CLAN_HALL_HAS_NOT_BEEN_MADE_PLEASE_ME_PAYMENT_TO_YOUR_CLAN_WAREHOUSE_BY_S1_TOMORROW).addNumber(((ClanHall)((Object)iterator)).getRentalFee()));
        }
    }

    private void ad(Player player) {
        Quest quest;
        if (ArrayUtils.contains((int[])Config.ALT_INITIAL_QUESTS, (int)255) && (quest = QuestManager.getQuest(255)) != null) {
            player.processQuestEvent(quest.getName(), "UC", null);
        }
    }

    private static void ae(Player player) {
        long l;
        if (Config.SERVICES_RATE_EXPIRE_TIME_AT_ENTER_WORLD && (l = player.getBonus().getBonusExpire()) > System.currentTimeMillis() / 1000L) {
            player.sendMessage(new CustomMessage("scripts.services.RateBonus.BonusExpireTime", player, new Object[0]).addString(TimeUtils.toSimpleFormat(l * 1000L)));
        }
    }

    private void af(Player player) {
        for (Mail mail : MailDAO.getInstance().getReceivedMailByOwnerId(player.getObjectId())) {
            if (!mail.isUnread()) continue;
            this.sendPacket(ExNoticePostArrived.STATIC_FALSE);
            break;
        }
        this.sendPacket((L2GameServerPacket)new ExUnReadMailCount(player));
    }
}
