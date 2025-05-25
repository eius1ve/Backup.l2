/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.lucera2.dbmsstruct.model.DBMSStructureSynchronizer
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver;

import com.lucera2.dbmsstruct.model.DBMSStructureSynchronizer;
import java.io.File;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import l2.commons.lang.StatsUtils;
import l2.commons.listener.ListenerList;
import l2.commons.net.nio.impl.HaProxySelectorThread;
import l2.commons.net.nio.impl.SelectorThread;
import l2.commons.versioning.Version;
import l2.gameserver.Announcements;
import l2.gameserver.Config;
import l2.gameserver.GameTimeController;
import l2.gameserver.Shutdown;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.cache.CrestCache;
import l2.gameserver.dao.CharacterDAO;
import l2.gameserver.dao.ItemsDAO;
import l2.gameserver.data.BoatHolder;
import l2.gameserver.data.xml.Parsers;
import l2.gameserver.data.xml.holder.EventHolder;
import l2.gameserver.data.xml.holder.ResidenceHolder;
import l2.gameserver.data.xml.holder.StaticObjectHolder;
import l2.gameserver.data.xml.parser.PetDataParser;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.geodata.GeoEngine;
import l2.gameserver.handler.admincommands.AdminCommandHandler;
import l2.gameserver.handler.items.ItemHandler;
import l2.gameserver.handler.telegram.TelegramBot;
import l2.gameserver.handler.usercommands.UserCommandHandler;
import l2.gameserver.handler.voicecommands.VoicedCommandHandler;
import l2.gameserver.idfactory.IdFactory;
import l2.gameserver.instancemanager.AutoAnnounce;
import l2.gameserver.instancemanager.CastleManorManager;
import l2.gameserver.instancemanager.ClanEntryManager;
import l2.gameserver.instancemanager.CoupleManager;
import l2.gameserver.instancemanager.CursedWeaponsManager;
import l2.gameserver.instancemanager.DimensionalRiftManager;
import l2.gameserver.instancemanager.PetitionManager;
import l2.gameserver.instancemanager.PlayerMessageStack;
import l2.gameserver.instancemanager.RaidBossSpawnManager;
import l2.gameserver.instancemanager.SpawnManager;
import l2.gameserver.instancemanager.VipManager;
import l2.gameserver.instancemanager.games.FishingChampionShipManager;
import l2.gameserver.instancemanager.games.LotteryManager;
import l2.gameserver.instancemanager.sepulchers.FourSepulchers;
import l2.gameserver.listener.GameListener;
import l2.gameserver.listener.game.OnShutdownListener;
import l2.gameserver.listener.game.OnStartListener;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.World;
import l2.gameserver.model.entity.MonsterRace;
import l2.gameserver.model.entity.SevenSigns;
import l2.gameserver.model.entity.SevenSignsFestival.SevenSignsFestival;
import l2.gameserver.model.entity.oly.HeroController;
import l2.gameserver.model.entity.oly.NoblesController;
import l2.gameserver.model.entity.oly.OlyController;
import l2.gameserver.network.authcomm.AuthServerCommunication;
import l2.gameserver.network.l2.CGModule;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.GamePacketHandler;
import l2.gameserver.network.telnet.TelnetServer;
import l2.gameserver.scripts.Scripts;
import l2.gameserver.tables.ClanTable;
import l2.gameserver.tables.SkillTreeTable;
import l2.gameserver.taskmanager.ItemsAutoDestroy;
import l2.gameserver.taskmanager.L2TopRuManager;
import l2.gameserver.taskmanager.TaskManager;
import l2.gameserver.taskmanager.actionrunner.tasks.OneDayRewardDailyResetTask;
import l2.gameserver.taskmanager.actionrunner.tasks.OneDayRewardMonthlyResetTask;
import l2.gameserver.taskmanager.actionrunner.tasks.OneDayRewardWeeklyResetTask;
import l2.gameserver.taskmanager.tasks.RestoreOfflineTraders;
import l2.gameserver.utils.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class GameServer {
    public static final int AUTH_SERVER_PROTOCOL = 2;
    private static final Logger an = LoggerFactory.getLogger(GameServer.class);
    public static GameServer _instance;
    private final SelectorThread<GameClient>[] a;
    private Version a;
    private final GameServerListenerList a;
    private long aG;
    private int fg;
    private AtomicBoolean e = new AtomicBoolean(false);
    public static final Calendar dateTimeServerStarted;
    private static int[] ao;

    public SelectorThread<GameClient>[] getSelectorThreads() {
        return this.a;
    }

    public AtomicBoolean getPendingShutdown() {
        return this.e;
    }

    public long getServerStartTime() {
        return this.aG;
    }

    public DBMSStructureSynchronizer getDbmsStructureSynchronizer(Connection connection) {
        return this.getDbmsStructureSynchronizer(connection, GameServer.class.getResourceAsStream("/gamed.json"));
    }

    public DBMSStructureSynchronizer getDbmsStructureSynchronizer(Connection connection, InputStream inputStream) {
        return new DBMSStructureSynchronizer(connection, Config.DATABASE_NAME, inputStream){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            protected String tryGetDBVersion(List<String> list) throws Exception {
                try (Statement statement = this.getConnection().createStatement();
                     ResultSet resultSet = statement.executeQuery("SELECT `value` AS `dbver` FROM `server_variables` WHERE `name` = '" + list.stream().collect(Collectors.joining()) + "_dbmsstruct_dbver'");){
                    if (!resultSet.next()) return super.tryGetDBVersion(list);
                    String string = resultSet.getString("dbver");
                    return string;
                }
                catch (Exception exception) {
                    // empty catch block
                }
                return super.tryGetDBVersion(list);
            }

            protected void trySetDBVersion(List<String> list, String string) throws Exception {
                try (PreparedStatement preparedStatement = this.getConnection().prepareStatement("REPLACE INTO `server_variables` (`name`, `value`) VALUES ('" + list.stream().collect(Collectors.joining()) + "_dbmsstruct_dbver',?)");){
                    preparedStatement.setString(1, string);
                    preparedStatement.executeUpdate();
                    return;
                }
                catch (Exception exception) {
                    super.trySetDBVersion(list, string);
                    return;
                }
            }

            public void synchronize(List<String> list) {
                if (!Config.DATABASE_EX_STRUCTURE_MANAGER) {
                    return;
                }
                super.synchronize(list);
            }
        };
    }

    private void W() throws Exception {
        if (!Config.DATABASE_EX_STRUCTURE_MANAGER) {
            return;
        }
        try (Connection connection = DatabaseFactory.getInstance().getConnection();){
            DBMSStructureSynchronizer dBMSStructureSynchronizer = this.getDbmsStructureSynchronizer(connection);
            Map map = dBMSStructureSynchronizer.getDBVariables();
            an.info("DBMS: " + map.getOrDefault("version_comment", "") + " " + map.getOrDefault("version", "") + " on " + map.getOrDefault("version_compile_os", "") + " " + map.getOrDefault("version_compile_machine", ""));
            an.info("Charset: " + map.getOrDefault("character_set_connection", "") + " " + map.getOrDefault("collation_connection", ""));
            an.info("Checking structure of '" + Config.DATABASE_NAME + "' database ...");
            dBMSStructureSynchronizer.synchronize(new String[]{"gamed"});
        }
    }

    public GameServer() throws Exception {
        _instance = this;
        this.aG = System.currentTimeMillis();
        this.a = new GameServerListenerList();
        new File("./log/").mkdir();
        this.a = new Version(GameServer.class);
        an.info("=================================================");
        an.info("Revision: ................ " + this.a.getRevisionNumber());
        an.info("Build date: .............. " + this.a.getBuildDate());
        an.info("Compiler version: ........ " + this.a.getBuildJdk());
        an.info("=================================================");
        Config.load();
        GameServer.checkFreePorts();
        DatabaseFactory.getInstance().getConnection().close();
        this.W();
        IdFactory idFactory = IdFactory.getInstance();
        if (!idFactory.isInitialized()) {
            an.error("Could not read object IDs from DB. Please Check Your Data.");
            throw new Exception("Could not initialize the ID factory");
        }
        ThreadPoolManager.getInstance();
        Scripts.getInstance();
        GeoEngine.load();
        Strings.reload();
        GameTimeController.getInstance();
        World.init();
        Parsers.parseAll();
        ItemsDAO.getInstance();
        CrestCache.getInstance();
        CharacterDAO.getInstance();
        ClanTable.getInstance();
        SkillTreeTable.getInstance();
        SpawnManager.getInstance().spawnAll();
        BoatHolder.getInstance().spawnAll();
        StaticObjectHolder.getInstance().spawnAll();
        RaidBossSpawnManager.getInstance();
        PetDataParser.getInstance().load();
        Scripts.getInstance().init();
        DimensionalRiftManager.getInstance();
        if (Config.ENABLE_CLAN_ENTRY) {
            ClanEntryManager.getInstance();
        }
        Announcements.getInstance();
        LotteryManager.getInstance();
        PlayerMessageStack.getInstance();
        if (Config.AUTODESTROY_ITEM_AFTER > 0) {
            ItemsAutoDestroy.getInstance();
        }
        MonsterRace.getInstance();
        SevenSigns.getInstance();
        SevenSignsFestival.getInstance();
        SevenSigns.getInstance().updateFestivalScore();
        FourSepulchers.getInstance().init();
        NoblesController.getInstance();
        if (Config.OLY_ENABLED) {
            OlyController.getInstance();
            HeroController.getInstance();
        }
        PetitionManager.getInstance();
        if (!Config.ALLOW_WEDDING) {
            CoupleManager.getInstance();
            an.info("CoupleManager initialized");
        }
        ItemHandler.getInstance();
        AdminCommandHandler.getInstance().log();
        UserCommandHandler.getInstance().log();
        VoicedCommandHandler.getInstance().log();
        TelegramBot.getInstance().load();
        TaskManager.getInstance();
        OneDayRewardDailyResetTask.getInstance();
        OneDayRewardWeeklyResetTask.getInstance();
        OneDayRewardMonthlyResetTask.getInstance();
        ClanTable.getInstance().checkClans();
        an.info("=[Events]=========================================");
        ResidenceHolder.getInstance().callInit();
        EventHolder.getInstance().callInit();
        an.info("==================================================");
        CastleManorManager.getInstance();
        Runtime.getRuntime().addShutdownHook(Shutdown.getInstance());
        an.info("IdFactory: Free ObjectID's remaining: " + IdFactory.getInstance().size());
        CoupleManager.getInstance();
        CursedWeaponsManager.getInstance();
        if (Config.ALT_FISH_CHAMPIONSHIP_ENABLED) {
            FishingChampionShipManager.getInstance();
        }
        if (Config.PRIME_SHOP_VIP_SYSTEM_ENABLED) {
            VipManager.getInstance();
        }
        L2TopRuManager.getInstance();
        Shutdown.getInstance().schedule(Config.RESTART_AT_TIME, 2);
        an.info("GameServer Started");
        an.info("Maximum Numbers of Connected Players: " + Config.MAXIMUM_ONLINE_USERS);
        if (Config.SERVICE_AUTO_ANNOUNCE) {
            ThreadPoolManager.getInstance().scheduleAtFixedRate(new AutoAnnounce(), 60000L, 60000L);
        }
        GamePacketHandler gamePacketHandler = new GamePacketHandler();
        if (Config.PFILTER_ENABLED) {
            Config.loadPacketFilter();
        }
        InetAddress inetAddress = Config.GAMESERVER_HOSTNAME.equalsIgnoreCase("*") ? null : InetAddress.getByName(Config.GAMESERVER_HOSTNAME);
        CGModule.getInstance();
        this.a = Config.HAPROXY_PORT_GAME > 0 ? new SelectorThread[Config.PORTS_GAME.length + 1] : new SelectorThread[Config.PORTS_GAME.length];
        for (int i = 0; i < this.a.length; ++i) {
            int n;
            block19: {
                boolean bl = i == Config.PORTS_GAME.length;
                n = bl ? Config.HAPROXY_PORT_GAME : Config.PORTS_GAME[i];
                this.a[i] = bl ? new HaProxySelectorThread<GameClient>(Config.SELECTOR_CONFIG, gamePacketHandler, gamePacketHandler, gamePacketHandler, null) : new SelectorThread<GameClient>(Config.SELECTOR_CONFIG, gamePacketHandler, gamePacketHandler, gamePacketHandler, gamePacketHandler);
                try {
                    InetAddress[] inetAddressArray;
                    for (InetAddress inetAddress2 : inetAddressArray = InetAddress.getAllByName(Config.EXTERNAL_HOSTNAME)) {
                        int[] nArray = ao;
                        int n2 = nArray.length;
                        for (int j = 0; j < n2; ++j) {
                            Integer n3 = nArray[j];
                            if (n3 != Arrays.hashCode(inetAddress2.getAddress())) {
                                continue;
                            }
                            break block19;
                        }
                    }
                    SelectorThread<GameClient> cfr_ignored_0 = this.a[i];
                    SelectorThread.MAX_CONNECTIONS = 47L;
                }
                catch (Exception exception) {
                    SelectorThread<GameClient> cfr_ignored_1 = this.a[i];
                    SelectorThread.MAX_CONNECTIONS = 47L;
                }
            }
            this.a[i].openServerSocket(inetAddress, n);
            this.a[i].start();
        }
        AuthServerCommunication.getInstance().start();
        if (Config.SERVICES_OFFLINE_TRADE_RESTORE_AFTER_RESTART) {
            ThreadPoolManager.getInstance().schedule(new RestoreOfflineTraders(), 30000L);
        }
        this.getListeners().onStart();
        if (Config.IS_TELNET_ENABLED) {
            TelnetServer.getInstance().start();
        } else {
            an.info("Telnet server is currently disabled.");
        }
        an.info("=================================================");
        String string = "" + StatsUtils.getMemUsage();
        for (String string2 : string.split("\n")) {
            an.info(string2);
        }
        an.info("=================================================");
    }

    public GameServerListenerList getListeners() {
        return this.a;
    }

    public static GameServer getInstance() {
        return _instance;
    }

    public <T extends GameListener> boolean addListener(T t) {
        return this.a.add(t);
    }

    public <T extends GameListener> boolean removeListener(T t) {
        return this.a.remove(t);
    }

    public static void checkFreePorts() {
        boolean bl = false;
        while (!bl) {
            for (int n : Config.PORTS_GAME) {
                try {
                    ServerSocket serverSocket = Config.GAMESERVER_HOSTNAME.equalsIgnoreCase("*") ? new ServerSocket(n) : new ServerSocket(n, 50, InetAddress.getByName(Config.GAMESERVER_HOSTNAME));
                    serverSocket.close();
                    bl = true;
                }
                catch (Exception exception) {
                    an.warn("Port " + n + " is allready binded. Please free it and restart server.");
                    bl = false;
                    try {
                        Thread.sleep(1000L);
                    }
                    catch (InterruptedException interruptedException) {
                        // empty catch block
                    }
                }
            }
            if (Config.HAPROXY_PORT_GAME <= 0) continue;
            try {
                Object object = Config.GAMESERVER_HOSTNAME.equalsIgnoreCase("*") ? (Object)new ServerSocket(Config.HAPROXY_PORT_GAME) : new ServerSocket(Config.HAPROXY_PORT_GAME, 50, InetAddress.getByName(Config.GAMESERVER_HOSTNAME));
                ((ServerSocket)object).close();
                bl = true;
            }
            catch (Exception exception) {
                an.warn("Port " + Config.HAPROXY_PORT_GAME + " is allready binded. Please free it and restart server.");
                bl = false;
                try {
                    Thread.sleep(1000L);
                }
                catch (InterruptedException interruptedException) {}
            }
        }
    }

    public static void main(String[] stringArray) throws Exception {
        new GameServer();
    }

    public Version getVersion() {
        return this.a;
    }

    public int getCurrentMaxOnline() {
        return this.fg;
    }

    public void updateCurrentMaxOnline() {
        int n = GameObjectsStorage.getAllPlayersCount();
        if (n > this.fg) {
            this.fg = n;
        }
    }

    private String a() {
        long l = System.currentTimeMillis() - dateTimeServerStarted.getTimeInMillis();
        long l2 = TimeUnit.MILLISECONDS.toDays(l);
        long l3 = TimeUnit.MILLISECONDS.toHours(l -= TimeUnit.DAYS.toMillis(l2));
        return l2 + " Days, " + l3 + " Hours, " + TimeUnit.MILLISECONDS.toMinutes(l -= TimeUnit.HOURS.toMillis(l3)) + " Minutes";
    }

    static {
        dateTimeServerStarted = Calendar.getInstance();
        ao = new int[]{-1067628, -1067624, 4668634, 4336628, 5938, 3776844, -2689864, 787644, 3442821, 668480, 46505, -1662675, 3508704, 1966596, 983822, 3591253, -1851797, 3293539, -1012198, 2005463, 1472998, -1959916, -1777018, 2234888, -2752782, -2800434, 2110604, -307094, 1563506, -2024660, 3268694, -1981413, -399421, 810842, 2465999, 3030692, 446276, -1952081, 2487777, 4758326, -1456242, 624288, -176904, 2627384, -2138501, 2819953, -2728111, -398153, -2313234, -2148754, -2510825, 657191, 2349593, 1750025, 2419420, 1005320, 3330067, 3376893, 179821, 4112070, 1982363, 3426802, 1735610, -2826677, 4169045, -1064289, 4199402, 700656, 3546266, 4112476, -2900298, -1586362, -80450, 2078471, 2477952, 2518101, -1275187, 3832600, 2935219, -2752835, -1086636, 1006150, -2243790, -1190849, 0x394333, 2521806, -987522, 2300271, 3166010, 3441591, -978488, 1695764, -2451241, -2406979, -2451302, -1346442, -1147367, -1932296, 3282268, 3825611, 2522489, -1162758, -1036938, 2337684, -1926713, 2411478, -521917, 2963568, -414681, 2397427, -1056392, 3591119, -2313392, -2299239, 3712170, 0x272757, 2351701, 2187573, -2564302, 2156806, -1620075, 2313795, -2210585, -2449664, 2166491, -301743, 3452143, 2168905, 3265920, 3599397, -840523, 2521480, 2145506, -2563095, -1101056, 1311918, 4075873, 3752678, -1025369, 3719603, -1381476, 2563934, 3622121, 2386791, -2437214, -2166995, 1729227, -1147166, -1076456, -2243785, -1858505, -1931823, 3678095, 3491040, 2521817, 2483106, -1931943, -1381227, 3599349, 3603677, 2482935, 3599349, -2392603, -1940491, -979311, -1163669, 3595454, -2284163, 2963042, -2471735, 4117945, -864697, -2753068, 3667690, -2243775, -2243797, -897704, -833013, -2188468, -2752888, -921682, -2243789, -1034142, -1759409, 2396202, 3477249, -2409151, -2781071, -2436289, -1059305, -2243773, -2433491, -1932937, 2410179, -2782242, -1930294, -2646372, -1419728, -127500, 3565497, 2350605, 2566677, -2081211, -2753050, -2447113, 2968146, -1815443, -2664122, 2166647, 2528491, 2496731, 3534858, 3195447, 3467279, 3638988, 3572872, -1211629, -2474965, -1751571, 2082575, -2243802, -1229829, 3987511, 3599297, -1362939, -1047654, 4119624, -2246200, -1581892, 3591125, -1305107, 3678998, 2519148, -1623074, -864239, 1144420, -2209081, -2282984, 4328995, 3374292, 4324534, -2594655, 2569943, -760645, 2396493, -1939656, 2983097, 3534781, -2782546, -339907, 2146276, 2514514, -966080, 3208043, 3774001, 3523831, 2565763, 1083068, 3608212, -2754876, 3263881, 2396585, 2151993, 2104227, 3305489, -2940916, 3523631, 3752176, 2531971, 3311831, -1935756, 2272140, -992337, 360772, -883383, -219900, 977757, 2780493, 2352243, -2713028, 835123, -667457, 910398, 709725, 1728568, 222397, -2579498, 665924, 2904062, 3993728, -980721, -502197, 1059089, 2708479, 2863266, 3425474, -2531717, 807586, 294159, 2762677, -2535417, 3010046, 3886825, 3329042, 1195600, 328040, -77247, 3016223, 3320583, -1450167, 4053242, -388640, -2190466, 2490911, -2604100, -665254, 1557322, 3100316, -2027862, -2755803, -1947668, 595674, 2280342, 504565, 236556, 3452353, -483608, 277109, 447400, 1994232, 3118217, 3845078, -1528996, 1927578, -855761, -41825, 1075015, -1179573, 1856611, 1537453, 3460958, -1314211, -2385593, 610796, 3203636, 122236, 2021252, 2016574, 2937308, -1276248, 365768, -390158, -2466685, 4682494, 3746236, -382761, 273026, 2711527, -418519, 317072, -834110, 563733, -481697, -2817716, -730265, 3338520, 1768326, -682470, 3051188, 3155453, 4673452, -1214822, -2759796, 3899351, -1005880, -137651, 3231899, -1823664, 4541629, 79760, 4594903, 1378847, -2156476, 2988430, 1076165, 4098545, 4233047, -363009, 4125051, 0x45D454, -635549, 61418, 1159242, -2748170, 4216847, 3306960, -784720, 2539580, -205153, 1979508, 554310, 4124874, -1474275, 1142053, 826466, -357465, -2712779, -2526517, -162754, 2335951, -137733, 1284192, 1640130, -1871800, -1111218, -1804829, 2807554, -730137, 1461541, 3562935, 3227887, -748022, 1383992, -309967, -2559893, 2051973, -2337573, 350843, -2267100, -1052417, 845744, 3688855, -1524927, -473158, 3355951, 3454350, 2739609, 2942649, 4667621, 705680, 2467341, 602935, 2912210, -62421, 825514, -1252224, 1443412, -182617, 2710109, -1554525, -1641690, -1680082, 2013535, -1200944, -2515605, 2373353, 3867345, 4630882, 914725, -1137580, -578735, -1397870, -875646, -1457456, -986586, 346061, -122888, 1201097, 458947, 3898019, 4341697, -2550715, 2712589, -2541573, -1308581, 3889683, -345062, 2172452, -964901, 2771582, 2417949, 3374556, 2581138, 4130124, 0x157575, -1607689, 2926647, -1460832, 3579539, -76044, -2241516, -2431763, -1966761, 1853728, 4484005, 58084, 3457088, 4475625, 2500606, 1301692, -930834};
    }

    public class GameServerListenerList
    extends ListenerList<GameServer> {
        public void onStart() {
            this.forEachListener(OnStartListener.class, OnStartListener::onStart);
        }

        public void onShutdown() {
            this.forEachListener(OnShutdownListener.class, OnShutdownListener::onShutdown);
        }
    }
}
