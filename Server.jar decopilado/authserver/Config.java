/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.ArrayUtils
 *  org.dom4j.Document
 *  org.dom4j.Element
 *  org.dom4j.io.SAXReader
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.authserver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import l2.authserver.crypt.CryptManager;
import l2.authserver.crypt.PasswordHash;
import l2.authserver.crypt.ScrambledKeyPair;
import l2.commons.configuration.ExProperties;
import org.apache.commons.lang3.ArrayUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Config {
    private static final Logger K = LoggerFactory.getLogger(Config.class);
    public static final String LOGIN_CONFIGURATION_FILE = "config/authserver.properties";
    public static final String SERVER_NAMES_FILE = "config/servername.xml";
    public static final String PROXY_SERVERS_FILE = "config/proxyservers.xml";
    public static String LOGIN_HOST;
    public static int PORT_LOGIN;
    public static String HAPROXY_LOGIN_HOST;
    public static int HAPROXY_PORT_LOGIN;
    public static String GAME_SERVER_LOGIN_HOST;
    public static int GAME_SERVER_LOGIN_PORT;
    public static long GAME_SERVER_PING_DELAY;
    public static int GAME_SERVER_PING_RETRY;
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
    public static String DEFAULT_PASSWORD_HASH;
    public static String LEGACY_PASSWORD_HASH;
    public static boolean ENABLE_CMD_LINE_LOGIN;
    public static boolean ONLY_CMD_LINE_LOGIN;
    public static int LOGIN_BLOWFISH_KEYS;
    public static int LOGIN_RSA_KEYPAIRS;
    public static boolean ACCEPT_NEW_GAMESERVER;
    public static boolean AUTO_CREATE_ACCOUNTS;
    public static String ANAME_TEMPLATE;
    public static String APASSWD_TEMPLATE;
    public static final Map<Integer, String> SERVER_NAMES;
    public static final long LOGIN_TIMEOUT = 60000L;
    public static int LOGIN_TRY_BEFORE_BAN;
    public static long LOGIN_TRY_TIMEOUT;
    public static long IP_BAN_TIME;
    public static Set<String> WHITE_IPS;
    public static Set<String> BLACK_IPS;
    public static long BLACKLIST_CLEAN_INTERVAL;
    private static ScrambledKeyPair[] a;
    private static byte[][] a;
    public static PasswordHash DEFAULT_CRYPT;
    public static PasswordHash[] LEGACY_CRYPT;
    public static boolean LOGIN_LOG;
    public static ProxyServerConfig[] PROXY_SERVERS_CONFIGS;
    public static String RESTART_AT_TIME;
    public static boolean IS_ENGLISH_SYSTEM;
    public static boolean REQUIRE_EULA;

    private Config() {
    }

    public static final void load() {
        Config.loadConfiguration();
        Config.loadServerNames();
        Config.loadServerProxies();
    }

    public static final void initCrypt() throws Throwable {
        CryptManager.getInstance().init();
        DEFAULT_CRYPT = new PasswordHash(DEFAULT_PASSWORD_HASH);
        ArrayList<PasswordHash> arrayList = new ArrayList<PasswordHash>();
        for (String string : LEGACY_PASSWORD_HASH.split(";")) {
            if (string.equalsIgnoreCase(DEFAULT_PASSWORD_HASH)) continue;
            arrayList.add(new PasswordHash(string));
        }
        LEGACY_CRYPT = arrayList.toArray(new PasswordHash[arrayList.size()]);
        K.info("Loaded " + DEFAULT_PASSWORD_HASH + " as default crypt.");
    }

    public static final void loadServerNames() {
        SERVER_NAMES.clear();
        try {
            SAXReader sAXReader = new SAXReader(true);
            Document document = sAXReader.read(new File(SERVER_NAMES_FILE));
            Element element = document.getRootElement();
            Iterator iterator = element.elementIterator();
            while (iterator.hasNext()) {
                Element element2 = (Element)iterator.next();
                if (!element2.getName().equalsIgnoreCase("server")) continue;
                Integer n = Integer.valueOf(element2.attributeValue("id"));
                String string = element2.attributeValue("name");
                SERVER_NAMES.put(n, string);
            }
            K.info("Loaded " + SERVER_NAMES.size() + " server names");
        }
        catch (Exception exception) {
            K.error("", (Throwable)exception);
        }
    }

    public static void loadServerProxies() {
        ArrayList<ProxyServerConfig> arrayList = new ArrayList<ProxyServerConfig>();
        try {
            SAXReader sAXReader = new SAXReader(true);
            Document document = sAXReader.read(new File(PROXY_SERVERS_FILE));
            Element element = document.getRootElement();
            Iterator iterator = element.elementIterator();
            while (iterator.hasNext()) {
                Element element2 = (Element)iterator.next();
                if (!element2.getName().equalsIgnoreCase("proxyServer")) continue;
                int n = Integer.parseInt(element2.attributeValue("origId"));
                int n2 = Integer.parseInt(element2.attributeValue("proxyId"));
                String string = element2.attributeValue("proxyHost");
                int n3 = Integer.parseInt(element2.attributeValue("proxyPort"));
                boolean bl = Boolean.parseBoolean(element2.attributeValue("hideMain", "false"));
                int n4 = Integer.parseInt(element2.attributeValue("minAccessLevel", "0"));
                int n5 = Integer.parseInt(element2.attributeValue("maxAccessLevel", "9999"));
                ProxyServerConfig proxyServerConfig = new ProxyServerConfig(n, n2, string, n3).setHideMain(bl).setMinAccessLevel(n4).setMaxAccessLevel(n5);
                arrayList.add(proxyServerConfig);
            }
        }
        catch (Exception exception) {
            K.error("Can't load proxy server's config", (Throwable)exception);
        }
        PROXY_SERVERS_CONFIGS = arrayList.toArray(new ProxyServerConfig[arrayList.size()]);
    }

    public static final void loadConfiguration() {
        ExProperties exProperties = Config.load(LOGIN_CONFIGURATION_FILE);
        LOGIN_HOST = exProperties.getProperty("LoginserverHostname", "127.0.0.1");
        PORT_LOGIN = exProperties.getProperty("LoginserverPort", 2106);
        HAPROXY_LOGIN_HOST = exProperties.getProperty("HAProxyLoginserverHostname", "");
        HAPROXY_PORT_LOGIN = exProperties.getProperty("HAProxyLoginserverPort", -1);
        GAME_SERVER_LOGIN_HOST = exProperties.getProperty("LoginHost", "127.0.0.1");
        GAME_SERVER_LOGIN_PORT = exProperties.getProperty("LoginPort", 9014);
        DATABASE_HOST = exProperties.getProperty("DatabaseHost", "127.0.0.1");
        DATABASE_PORT = exProperties.getProperty("DatabasePort", 3306);
        DATABASE_NAME = exProperties.getProperty("DatabaseName", "l2db");
        DATABASE_USER = exProperties.getProperty("DatabaseUser", "root");
        DATABASE_PASS = exProperties.getProperty("DatabasePassword", "");
        DATABASE_MAX_CONN = exProperties.getProperty("DatabaseMaxConnections", 8);
        DATABASE_TIMEOUT = exProperties.getProperty("DatabaseConnectionTimeout", 30);
        DATABASE_EX_PROPERTIES = exProperties.getProperty("DatabaseExProperties", "");
        DATABASE_EX_STRUCTURE_MANAGER = exProperties.getProperty("DatabaseStructureManager", true);
        DATABASE_DUMP_TABLES = exProperties.getProperty("DatabaseDumpTables", ArrayUtils.EMPTY_STRING_ARRAY);
        DATABASE_DUMP_FILENAME_FORMAT = exProperties.getProperty("DatabaseDumpFilenameFormat", "backup/%table_name%_%date%_%time%.sql");
        DATABASE_DUMP_ZIP_OUT_FILENAME_FORMAT = exProperties.getProperty("DatabaseDumpZipOutFilenameFormat", "backup/%date%_%time%.zip");
        LOGIN_BLOWFISH_KEYS = exProperties.getProperty("BlowFishKeys", 20);
        LOGIN_RSA_KEYPAIRS = exProperties.getProperty("RSAKeyPairs", 10);
        ACCEPT_NEW_GAMESERVER = exProperties.getProperty("AcceptNewGameServer", true);
        DEFAULT_PASSWORD_HASH = exProperties.getProperty("PasswordHash", "sha1");
        LEGACY_PASSWORD_HASH = exProperties.getProperty("LegacyPasswordHash", "whirlpool2");
        ENABLE_CMD_LINE_LOGIN = exProperties.getProperty("EnableCmdLineLogin", false);
        ONLY_CMD_LINE_LOGIN = exProperties.getProperty("OnlyCmdLineLogin", false);
        AUTO_CREATE_ACCOUNTS = exProperties.getProperty("AutoCreateAccounts", true);
        ANAME_TEMPLATE = exProperties.getProperty("AccountTemplate", "[A-Za-z0-9]{4,14}");
        APASSWD_TEMPLATE = exProperties.getProperty("PasswordTemplate", "[A-Za-z0-9]{4,16}");
        LOGIN_TRY_BEFORE_BAN = exProperties.getProperty("LoginTryBeforeBan", 10);
        LOGIN_TRY_TIMEOUT = (long)exProperties.getProperty("LoginTryTimeout", 5) * 1000L;
        IP_BAN_TIME = (long)exProperties.getProperty("IpBanTime", 300) * 1000L;
        BLACKLIST_CLEAN_INTERVAL = (long)(exProperties.getProperty("BlackListCleanInterval", 10) * 60) * 1000L;
        WHITE_IPS.addAll(Arrays.asList(exProperties.getProperty("WhiteIpList", new String[]{"127.0.0.1"})));
        BLACK_IPS.addAll(Arrays.asList(exProperties.getProperty("BlackIpList", ArrayUtils.EMPTY_STRING_ARRAY)));
        GAME_SERVER_PING_DELAY = (long)exProperties.getProperty("GameServerPingDelay", 30) * 1000L;
        GAME_SERVER_PING_RETRY = exProperties.getProperty("GameServerPingRetry", 4);
        LOGIN_LOG = exProperties.getProperty("LoginLog", true);
        RESTART_AT_TIME = exProperties.getProperty("AutoRestartAt", "");
        IS_ENGLISH_SYSTEM = exProperties.getProperty("EnglishServerSystem", false);
        REQUIRE_EULA = exProperties.getProperty("RequireEULA", true);
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
            K.error("", (Throwable)iOException);
        }
        return exProperties;
    }

    static {
        SERVER_NAMES = new HashMap<Integer, String>();
        WHITE_IPS = new HashSet<String>();
        BLACK_IPS = new HashSet<String>();
    }

    public static class ProxyServerConfig {
        private final int da;
        private final int db;
        private final String ar;
        private final int dc;
        private int dd;
        private int de;
        private boolean ac;

        public ProxyServerConfig(int n, int n2, String string, int n3) {
            this.da = n;
            this.db = n2;
            this.ar = string;
            this.dc = n3;
        }

        public int getOrigServerId() {
            return this.da;
        }

        public int getProxyId() {
            return this.db;
        }

        public String getPorxyHost() {
            return this.ar;
        }

        public int getProxyPort() {
            return this.dc;
        }

        public boolean isHideMain() {
            return this.ac;
        }

        public ProxyServerConfig setHideMain(boolean bl) {
            this.ac = bl;
            return this;
        }

        public int getMinAccessLevel() {
            return this.dd;
        }

        public ProxyServerConfig setMinAccessLevel(int n) {
            this.dd = n;
            return this;
        }

        public int getMaxAccessLevel() {
            return this.de;
        }

        public ProxyServerConfig setMaxAccessLevel(int n) {
            this.de = n;
            return this;
        }
    }
}
