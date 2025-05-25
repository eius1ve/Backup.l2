/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.Config
 *  l2.gameserver.GameServer
 *  l2.gameserver.dao.CharacterDAO
 *  l2.gameserver.data.xml.holder.ItemHolder
 *  l2.gameserver.data.xml.parser.ItemParser
 *  l2.gameserver.database.DatabaseFactory
 *  l2.gameserver.model.GameObjectsStorage
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.tables.SkillTable
 *  l2.gameserver.taskmanager.DelayedItemsManager
 *  l2.gameserver.taskmanager.actionrunner.tasks.SchedulingPatternTask
 *  l2.gameserver.templates.item.ItemTemplate
 *  l2.gameserver.utils.ItemFunctions
 *  l2.gameserver.utils.Util
 *  org.apache.commons.io.IOUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.lang3.tuple.Pair
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package services;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import l2.gameserver.Config;
import l2.gameserver.GameServer;
import l2.gameserver.dao.CharacterDAO;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.data.xml.parser.ItemParser;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.taskmanager.DelayedItemsManager;
import l2.gameserver.taskmanager.actionrunner.tasks.SchedulingPatternTask;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.utils.ItemFunctions;
import l2.gameserver.utils.Util;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MMOTopVote
implements ScriptFile {
    private static final Logger eb = LoggerFactory.getLogger(MMOTopVote.class);
    private static final Pattern k = Pattern.compile("^(\\d+)\\s+(\\d{2}\\.\\d{2}\\.\\d{4}\\s+\\d{2}:\\d{2}:\\d{2})\\s+([0-9a-fA-F\\.\\:]+)\\s+([\\w\\W]*?)\\s+(\\d+)$", 8);
    private static final DateFormat c = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    private static final DateFormat d = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.US);
    private static Map<MMOTopVoteType, List<Pair<ItemTemplate, Long>>> cr = Collections.emptyMap();

    private static Pair<List<MMOTopVoteInstance>, Date> c() throws Exception {
        Object object;
        Object object2;
        Date date = new Date(System.currentTimeMillis());
        LinkedList<Object> linkedList = new LinkedList<Object>();
        URL uRL = new URL(Config.MMO_TOP_VOTES_LINK);
        URLConnection uRLConnection = uRL.openConnection();
        if (uRLConnection instanceof HttpsURLConnection) {
            object2 = SSLContext.getInstance("SSL");
            ((SSLContext)object2).init(null, new TrustManager[]{new X509TrustManager(){

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] x509CertificateArray, String string) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] x509CertificateArray, String string) {
                }
            }}, new SecureRandom());
            ((HttpsURLConnection)uRLConnection).setSSLSocketFactory(((SSLContext)object2).getSocketFactory());
            ((HttpsURLConnection)uRLConnection).setHostnameVerifier((string, sSLSession) -> true);
        }
        uRLConnection.addRequestProperty("Host", uRL.getHost());
        uRLConnection.addRequestProperty("Accept", "*/*");
        uRLConnection.addRequestProperty("Connection", "close");
        uRLConnection.addRequestProperty("User-Agent", "MMOTop");
        uRLConnection.setConnectTimeout(10000);
        try (Object object3 = uRLConnection.getInputStream();){
            object = new BufferedInputStream((InputStream)object3);
            try (InputStreamReader inputStreamReader = new InputStreamReader((InputStream)object, StandardCharsets.UTF_8);){
                object2 = IOUtils.toString((Reader)inputStreamReader);
            }
            finally {
                ((BufferedInputStream)object).close();
            }
        }
        try {
            object3 = uRLConnection.getHeaderField("Date");
            if (object3 != null && !((String)(object3 = ((String)object3).trim())).isEmpty()) {
                date = d.parse((String)object3);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        object3 = k.matcher((CharSequence)object2);
        while (((Matcher)object3).find()) {
            object = new MMOTopVoteInstance(Math.abs(Long.parseLong(((Matcher)object3).group(1))), c.parse(((Matcher)object3).group(2)), ((Matcher)object3).group(3), ((Matcher)object3).group(4), MMOTopVoteType.getTypeByTypeId(Integer.parseInt(((Matcher)object3).group(5))));
            linkedList.add(object);
        }
        return Pair.of(linkedList, (Object)date);
    }

    private static List<MMOTopVoteInstance> a(long l, long l2, boolean bl) throws Exception {
        LinkedList<MMOTopVoteInstance> linkedList = new LinkedList<MMOTopVoteInstance>();
        try (Connection connection = DatabaseFactory.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT `voteId`, `timestamp`, `voterIp`, `voterName`, `voteType` FROM `mmotop_votes` WHERE  `timestamp` >= ? AND `timestamp` < ? AND `isRewarded` = ? ORDER BY `timestamp` DESC");){
            preparedStatement.setLong(1, l);
            preparedStatement.setLong(2, l2);
            preparedStatement.setInt(3, bl ? 1 : 0);
            try (ResultSet resultSet = preparedStatement.executeQuery();){
                while (resultSet.next()) {
                    linkedList.add(new MMOTopVoteInstance(resultSet.getLong("voteId"), resultSet.getLong("timestamp"), resultSet.getString("voterIp"), resultSet.getString("voterName"), MMOTopVoteType.getTypeByTypeId(resultSet.getInt("voteType"))));
                }
            }
        }
        return linkedList;
    }

    private static void a(List<MMOTopVoteInstance> list, boolean bl) throws Exception {
        try (Connection connection = DatabaseFactory.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("REPLACE INTO `mmotop_votes` (`voteId`, `timestamp`, `voterIp`, `voterName`, `voteType`, `isRewarded`) VALUES (?, ?, ?, ?, ?, ?)");){
            for (MMOTopVoteInstance mMOTopVoteInstance : list) {
                preparedStatement.setLong(1, mMOTopVoteInstance.et);
                preparedStatement.setLong(2, mMOTopVoteInstance.eu);
                preparedStatement.setString(3, mMOTopVoteInstance.hE);
                preparedStatement.setString(4, mMOTopVoteInstance.hF);
                preparedStatement.setInt(5, mMOTopVoteInstance.a.getTypeId());
                preparedStatement.setInt(6, bl ? 1 : 0);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }

    private static boolean a(Player player, MMOTopVoteInstance mMOTopVoteInstance) {
        List<Pair<ItemTemplate, Long>> list = cr.get((Object)mMOTopVoteInstance.a);
        if (list == null) {
            return false;
        }
        for (Pair<ItemTemplate, Long> pair : list) {
            ItemFunctions.addItem((Playable)player, (int)((ItemTemplate)pair.getKey()).getItemId(), (long)((Long)pair.getValue()), (boolean)true);
        }
        player.sendMessage(player.isLangRus() ? "\u0412\u0430\u043c \u0431\u044b\u043b\u043e \u043d\u0430\u0447\u0438\u0441\u043b\u0435\u043d\u043e \u0432\u043e\u0437\u043d\u0430\u0433\u0440\u0430\u0436\u0434\u0435\u043d\u0438\u0435 \u0437\u0430 \u0433\u043e\u043b\u043e\u0441\u043e\u0432\u0430\u043d\u0438\u0435 \u0432 \u0440\u0435\u0439\u0442\u0438\u043d\u0433\u0435 MMOTop.ru" : "You were rewarded for a vote at MMOTop.ru");
        return true;
    }

    private static List<MMOTopVoteInstance> f(List<MMOTopVoteInstance> list) {
        Object object;
        Object object2;
        HashMap<String, MMOTopVoteInstance> hashMap = new HashMap<String, MMOTopVoteInstance>();
        LinkedList<MMOTopVoteInstance> linkedList = new LinkedList<MMOTopVoteInstance>();
        for (MMOTopVoteInstance object32 : list) {
            hashMap.put(object32.hF.toLowerCase(), object32);
        }
        for (Player player : GameObjectsStorage.getAllPlayersForIterate()) {
            object2 = player.getName().toLowerCase();
            object = (MMOTopVoteInstance)hashMap.get(object2);
            if (object == null) continue;
            if (MMOTopVote.a(player, (MMOTopVoteInstance)object)) {
                linkedList.add((MMOTopVoteInstance)object);
            }
            hashMap.remove(object2);
        }
        ArrayList arrayList = new ArrayList(hashMap.values());
        Iterator iterator = arrayList.iterator();
        while (iterator.hasNext()) {
            List<Pair<ItemTemplate, Long>> list2;
            object2 = (MMOTopVoteInstance)iterator.next();
            object = ((MMOTopVoteInstance)object2).hF;
            if (!Util.isMatchingRegexp((String)object, (String)Config.CNAME_TEMPLATE) || ((String)object).length() > Config.CNAME_MAXLEN) continue;
            linkedList.add((MMOTopVoteInstance)object2);
            int n = CharacterDAO.getInstance().getObjectIdByName((String)object);
            if (n <= 0 || (list2 = cr.get((Object)((MMOTopVoteInstance)object2).a)) == null) continue;
            for (Pair<ItemTemplate, Long> pair : list2) {
                DelayedItemsManager.getInstance().addDelayed(n, ((ItemTemplate)pair.getLeft()).getItemId(), Math.toIntExact((Long)pair.getRight()), 0, 0, 0, "MMOTop Reward Item " + ((ItemTemplate)pair.getLeft()).toString() + "(" + pair.getRight() + ") " + System.currentTimeMillis());
            }
        }
        return linkedList;
    }

    private static void cl() throws Exception {
        Pair<List<MMOTopVoteInstance>, Date> pair = MMOTopVote.c();
        List list = (List)pair.getLeft();
        Date date = (Date)pair.getRight();
        long l = (date.getTime() - TimeUnit.DAYS.toMillis(Config.MMO_TOP_VOTE_WINDOW_DAYS)) / 1000L;
        long l2 = (date.getTime() - TimeUnit.HOURS.toMillis(Config.MMO_TOP_VOTE_REWARD_HOURS)) / 1000L;
        List<MMOTopVoteInstance> list2 = MMOTopVote.a(l, l2, false);
        List<MMOTopVoteInstance> list3 = MMOTopVote.a(l, l2, true);
        LinkedList<MMOTopVoteInstance> linkedList = new LinkedList<MMOTopVoteInstance>();
        LinkedList<MMOTopVoteInstance> linkedList2 = new LinkedList<MMOTopVoteInstance>();
        block0: for (MMOTopVoteInstance mMOTopVoteInstance : list) {
            if (mMOTopVoteInstance.eu < l || mMOTopVoteInstance.eu >= l2) continue;
            for (MMOTopVoteInstance mMOTopVoteInstance2 : list3) {
                if (mMOTopVoteInstance.et == mMOTopVoteInstance2.et && mMOTopVoteInstance2.eu == mMOTopVoteInstance.eu) continue block0;
            }
            boolean bl = true;
            for (MMOTopVoteInstance mMOTopVoteInstance3 : list2) {
                if (mMOTopVoteInstance.et != mMOTopVoteInstance3.et || mMOTopVoteInstance3.eu != mMOTopVoteInstance.eu) continue;
                bl = false;
                linkedList.add(mMOTopVoteInstance);
                break;
            }
            if (!bl) continue;
            linkedList2.add(mMOTopVoteInstance);
        }
        MMOTopVote.a(linkedList2, false);
        List<MMOTopVoteInstance> list4 = MMOTopVote.f(linkedList);
        MMOTopVote.a(list4, true);
        eb.info("MMOTopVote: Rewarded {} voter(s).", (Object)list4.size());
    }

    public static void main(String ... stringArray) throws Exception {
        SkillTable.getInstance().load();
        ItemParser.getInstance().load();
        Config.MMO_TOP_VOTES_LINK = "https://mmotop.ru/votes/10ea26db579be962293082dd80195c8c1b5380ae.txt?23fd08b37db58fb1e604ede3446fa0fd";
        Map<MMOTopVoteType, List<Pair<ItemTemplate, Long>>> map = MMOTopVote.c("NORMAL:57/100;SMS10:6673/1;SMS50:6673/5;SMS100:6673/10;SMS500:6673/50");
        Pair<List<MMOTopVoteInstance>, Date> pair = MMOTopVote.c();
        System.currentTimeMillis();
    }

    private static Map<MMOTopVoteType, List<Pair<ItemTemplate, Long>>> c(String string) {
        LinkedHashMap<MMOTopVoteType, List<Pair<ItemTemplate, Long>>> linkedHashMap = new LinkedHashMap<MMOTopVoteType, List<Pair<ItemTemplate, Long>>>();
        StringTokenizer stringTokenizer = new StringTokenizer(string.trim(), ";");
        try {
            while (stringTokenizer.hasMoreTokens()) {
                String string2;
                String string3 = stringTokenizer.nextToken();
                int n = string3.indexOf(58);
                if (n <= 0) {
                    throw new IllegalArgumentException("Can't parse reward \"" + string3 + "\"");
                }
                String string4 = string3.substring(0, n).trim();
                MMOTopVoteType mMOTopVoteType = MMOTopVoteType.Unknown;
                mMOTopVoteType = StringUtils.isNumeric((CharSequence)string4) ? MMOTopVoteType.getTypeByTypeId(Integer.parseInt(string4)) : MMOTopVoteType.valueOf(string4.toUpperCase());
                if (mMOTopVoteType == null || mMOTopVoteType == MMOTopVoteType.Unknown) {
                    throw new IllegalArgumentException("Unknown vote type \"" + string4 + "\"");
                }
                LinkedList<Pair> linkedList = (LinkedList<Pair>)linkedHashMap.get((Object)mMOTopVoteType);
                if (linkedList == null) {
                    linkedList = new LinkedList<Pair>();
                    linkedHashMap.put(mMOTopVoteType, linkedList);
                }
                if ((string2 = string3.substring(n + 1).trim()).isEmpty()) continue;
                n = StringUtils.indexOfAny((CharSequence)string2, (String)",/\\");
                if (n <= 0) {
                    throw new IllegalArgumentException("Can't parse reward \"" + string3 + "\"");
                }
                int n2 = Integer.parseInt(string2.substring(0, n).trim());
                long l = Long.parseLong(string2.substring(n + 1).trim());
                ItemTemplate itemTemplate = ItemHolder.getInstance().getTemplate(n2);
                linkedList.add(Pair.of((Object)itemTemplate, (Object)l));
            }
        }
        catch (Exception exception) {
            throw new IllegalArgumentException("Can't parse rewards \"" + string + "\"", exception);
        }
        return linkedHashMap;
    }

    private static void a() {
        try (Connection connection = DatabaseFactory.getInstance().getConnection();){
            GameServer.getInstance().getDbmsStructureSynchronizer(connection).synchronize(new String[]{"mmotop_votes"});
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public void onLoad() {
        if (Config.MMO_TOP_CRON_TASK != null && !Config.MMO_TOP_CRON_TASK.isEmpty() && Config.MMO_TOP_VOTES_LINK != null && !Config.MMO_TOP_VOTES_LINK.isEmpty()) {
            MMOTopVote.a();
            cr = MMOTopVote.c(Config.MMO_TOP_REWARD);
            new SchedulingPatternTask(Config.MMO_TOP_CRON_TASK){

                protected void runTask() {
                    try {
                        MMOTopVote.cl();
                    }
                    catch (Exception exception) {
                        eb.error(exception.getMessage(), (Throwable)exception);
                    }
                }
            };
        }
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    private static class MMOTopVoteInstance {
        private final long et;
        private final long eu;
        private final String hE;
        private final String hF;
        private final MMOTopVoteType a;

        private MMOTopVoteInstance(long l, Date date, String string, String string2, MMOTopVoteType mMOTopVoteType) {
            this.et = l;
            this.eu = date.getTime() / 1000L;
            this.hE = string;
            this.hF = string2;
            this.a = mMOTopVoteType;
        }

        private MMOTopVoteInstance(long l, long l2, String string, String string2, MMOTopVoteType mMOTopVoteType) {
            this.et = l;
            this.eu = l2;
            this.hE = string;
            this.hF = string2;
            this.a = mMOTopVoteType;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object == null || this.getClass() != object.getClass()) {
                return false;
            }
            MMOTopVoteInstance mMOTopVoteInstance = (MMOTopVoteInstance)object;
            return this.et == mMOTopVoteInstance.et;
        }

        public int hashCode() {
            return Objects.hash(this.et);
        }

        public String toString() {
            return "MMOTopVoteInstance{voteId=" + this.et + ", timestamp='" + this.eu + "', voterIp='" + this.hE + "', userName='" + this.hF + "', voteType=" + this.a + "}";
        }
    }

    static final class MMOTopVoteType
    extends Enum<MMOTopVoteType> {
        public static final /* enum */ MMOTopVoteType Unknown = new MMOTopVoteType(-1);
        public static final /* enum */ MMOTopVoteType NORMAL = new MMOTopVoteType(1);
        public static final /* enum */ MMOTopVoteType SMS10 = new MMOTopVoteType(2);
        public static final /* enum */ MMOTopVoteType SMS50 = new MMOTopVoteType(3);
        public static final /* enum */ MMOTopVoteType SMS100 = new MMOTopVoteType(4);
        public static final /* enum */ MMOTopVoteType SMS500 = new MMOTopVoteType(5);
        private static final Map<Integer, MMOTopVoteType> cs;
        private final int bFH;
        private static final /* synthetic */ MMOTopVoteType[] a;

        public static MMOTopVoteType[] values() {
            return (MMOTopVoteType[])a.clone();
        }

        public static MMOTopVoteType valueOf(String string) {
            return Enum.valueOf(MMOTopVoteType.class, string);
        }

        private MMOTopVoteType(int n2) {
            this.bFH = n2;
        }

        public static MMOTopVoteType getTypeByTypeId(int n) {
            return cs.getOrDefault(n, Unknown);
        }

        public int getTypeId() {
            return this.bFH;
        }

        private static /* synthetic */ MMOTopVoteType[] a() {
            return new MMOTopVoteType[]{Unknown, NORMAL, SMS10, SMS50, SMS100, SMS500};
        }

        static {
            a = MMOTopVoteType.a();
            cs = new HashMap<Integer, MMOTopVoteType>();
            for (MMOTopVoteType mMOTopVoteType : MMOTopVoteType.values()) {
                cs.put(mMOTopVoteType.getTypeId(), mMOTopVoteType);
            }
        }
    }
}
