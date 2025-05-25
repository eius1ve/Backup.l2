/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.dbutils.DbUtils
 *  l2.gameserver.Config
 *  l2.gameserver.data.StringHolder
 *  l2.gameserver.data.htm.HtmCache
 *  l2.gameserver.database.DatabaseFactory
 *  l2.gameserver.model.GameObjectsStorage
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.NpcHtmlMessage
 *  l2.gameserver.network.l2.s2c.ShowBoard
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 *  org.apache.commons.lang3.tuple.Pair
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicReference;
import l2.commons.dbutils.DbUtils;
import l2.gameserver.Config;
import l2.gameserver.data.StringHolder;
import l2.gameserver.data.htm.HtmCache;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.network.l2.s2c.ShowBoard;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TopPvPPKService
extends Functions
implements ScriptFile {
    private static final Logger eh = LoggerFactory.getLogger(TopPvPPKService.class);

    private static void a(TopRecordHolder topRecordHolder, Player player, NpcInstance npcInstance, String string) {
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, npcInstance).setFile("scripts/services/" + string);
        StringBuilder stringBuilder = new StringBuilder();
        Collection<TopRecord> collection = topRecordHolder.getTopRecords();
        for (TopRecord topRecord : collection) {
            stringBuilder.append(topRecord.formatHtml(player));
        }
        npcHtmlMessage.replace("%content%", stringBuilder.toString());
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    private static void a(TopRecordHolder topRecordHolder, Player player, String string) {
        String string2 = HtmCache.getInstance().getNotNull("scripts/services/community/services/" + string, player);
        StringBuilder stringBuilder = new StringBuilder();
        Collection<TopRecord> collection = topRecordHolder.getTopRecords();
        for (TopRecord topRecord : collection) {
            stringBuilder.append(topRecord.formatHtml(player));
        }
        string2 = string2.replace("%content%", stringBuilder.toString());
        ShowBoard.separateAndSend((String)string2, (Player)player);
    }

    public void topPvP() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        if (!Config.SERVICES_PVP_PK_STATISTIC) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_disabled.htm"));
            return;
        }
        TopPvPPKService.a(TopPvPRecordHolder.getInstance(), player, this.getNpc(), "top_pvp.htm");
    }

    public void topPK() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        if (!Config.SERVICES_PVP_PK_STATISTIC) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_disabled.htm"));
            return;
        }
        TopPvPPKService.a(TopPKRecordHolder.getInstance(), player, this.getNpc(), "top_pk.htm");
    }

    public void topPvPCb() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        if (!Config.SERVICES_PVP_PK_STATISTIC) {
            ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/services_disabled.htm");
            return;
        }
        TopPvPPKService.a(TopPvPRecordHolder.getInstance(), player, "top_pvp.htm");
    }

    public void topPKCb() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        if (!Config.SERVICES_PVP_PK_STATISTIC) {
            ShowBoard.separateAndSend((Player)player, (String)"scripts/services/community/services/services_disabled.htm");
            return;
        }
        TopPvPPKService.a(TopPKRecordHolder.getInstance(), player, "top_pk.htm");
    }

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    private static abstract class TopRecordHolder {
        private final AtomicReference<Pair<Long, Collection<TopRecord>>> e;
        private final int bGj;
        private final long ez;

        protected TopRecordHolder(int n, long l) {
            this.ez = l;
            this.e = new AtomicReference<Pair>(Pair.of((Object)0L, Collections.emptyList()));
            this.bGj = n;
        }

        protected int getLimit() {
            return this.bGj;
        }

        protected abstract Collection<TopRecord> fetchTopOnlineRecords();

        protected abstract Collection<TopRecord> fetchTopDbRecords();

        protected Collection<TopRecord> fetchTopRecords() {
            Collection<TopRecord> collection = this.fetchTopOnlineRecords();
            Collection<TopRecord> collection2 = this.fetchTopDbRecords();
            ArrayList<TopRecord> arrayList = new ArrayList<TopRecord>(collection.size() + collection2.size());
            for (TopRecord topRecord : collection) {
                arrayList.add(topRecord);
            }
            for (TopRecord topRecord : collection2) {
                if (arrayList.contains(topRecord)) continue;
                arrayList.add(topRecord);
            }
            Collections.sort(arrayList);
            return new ArrayList<TopRecord>(arrayList.subList(0, Math.min(this.bGj, arrayList.size())));
        }

        public Collection<TopRecord> getTopRecords() {
            Pair<Long, Collection<TopRecord>> pair;
            while ((Long)(pair = this.e.get()).getLeft() + this.ez < System.currentTimeMillis()) {
                Collection<TopRecord> collection = this.fetchTopRecords();
                Pair pair2 = Pair.of((Object)System.currentTimeMillis(), collection);
                if (!this.e.compareAndSet(pair, (Pair<Long, Collection<TopRecord>>)pair2)) continue;
                return Collections.unmodifiableCollection((Collection)pair2.getRight());
            }
            return Collections.unmodifiableCollection((Collection)pair.getRight());
        }
    }

    private static class TopRecord
    implements Comparable<TopRecord> {
        private final int bGh;
        private final int bGi;
        protected String _playerName;

        private TopRecord(int n, String string, int n2) {
            this.bGh = n;
            this._playerName = string;
            this.bGi = n2;
        }

        protected int getPlayerObjectId() {
            return this.bGh;
        }

        protected Player getPlayer() {
            return GameObjectsStorage.getPlayer((int)this.bGh);
        }

        public String getPlayerName() {
            return this._playerName;
        }

        public boolean isOnline() {
            Player player = this.getPlayer();
            return player != null && player.isOnline();
        }

        public int getTopValue() {
            return this.bGi;
        }

        public String formatHtml(Player player) {
            String string = StringHolder.getInstance().getNotNull(player, this.isOnline() ? "services.TopPvPPKService.TopRecord.RecordHtmlPlayerOnline" : "services.TopPvPPKService.TopRecord.RecordHtmlPlayerOffline");
            string = string.replace("%name%", this.getPlayerName());
            string = string.replace("%val%", String.valueOf(this.getTopValue()));
            return string;
        }

        @Override
        public int compareTo(TopRecord topRecord) {
            if (this.getPlayerObjectId() == topRecord.getPlayerObjectId()) {
                return 0;
            }
            return topRecord.getTopValue() - this.getTopValue();
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object == null || !(object instanceof TopRecord)) {
                return false;
            }
            return this.getPlayerObjectId() == ((TopRecord)object).getPlayerObjectId();
        }
    }

    private static class TopPvPRecordHolder
    extends TopRecordHolder {
        private static final TopPvPRecordHolder a = new TopPvPRecordHolder();

        private TopPvPRecordHolder() {
            super(Config.PVP_PK_STAT_RECORD_LIMIT, Config.PVP_PK_STAT_CACHE_UPDATE_INTERVAL);
        }

        public static TopPvPRecordHolder getInstance() {
            return a;
        }

        @Override
        protected Collection<TopRecord> fetchTopOnlineRecords() {
            LinkedList<TopRecord> linkedList = new LinkedList<TopRecord>();
            for (Player player : GameObjectsStorage.getAllPlayersForIterate()) {
                linkedList.add(new TopRecord(player.getObjectId(), player.getName(), player.getPvpKills()));
            }
            return linkedList;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        protected Collection<TopRecord> fetchTopDbRecords() {
            LinkedList<TopRecord> linkedList = new LinkedList<TopRecord>();
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            try {
                connection = DatabaseFactory.getInstance().getConnection();
                preparedStatement = connection.prepareStatement("SELECT  `characters`.`obj_Id` AS `playerObjectId`,  `characters`.`char_name` AS `playerName`,  `characters`.`pvpkills` AS `pvpKills` FROM  `characters` ORDER BY  `characters`.`pvpkills` DESC LIMIT ?");
                preparedStatement.setInt(1, this.getLimit());
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    linkedList.add(new TopRecord(resultSet.getInt("playerObjectId"), resultSet.getString("playerName"), resultSet.getInt("pvpKills")));
                }
            }
            catch (SQLException sQLException) {
                try {
                    eh.error("Can't fetch top PvP records.", (Throwable)sQLException);
                }
                catch (Throwable throwable) {
                    DbUtils.closeQuietly((Connection)connection, preparedStatement, resultSet);
                    throw throwable;
                }
                DbUtils.closeQuietly((Connection)connection, (Statement)preparedStatement, resultSet);
            }
            DbUtils.closeQuietly((Connection)connection, (Statement)preparedStatement, (ResultSet)resultSet);
            return linkedList;
        }
    }

    private static class TopPKRecordHolder
    extends TopRecordHolder {
        private static final TopPKRecordHolder a = new TopPKRecordHolder();

        private TopPKRecordHolder() {
            super(Config.PVP_PK_STAT_RECORD_LIMIT, Config.PVP_PK_STAT_CACHE_UPDATE_INTERVAL);
        }

        public static TopPKRecordHolder getInstance() {
            return a;
        }

        @Override
        protected Collection<TopRecord> fetchTopOnlineRecords() {
            LinkedList<TopRecord> linkedList = new LinkedList<TopRecord>();
            for (Player player : GameObjectsStorage.getAllPlayersForIterate()) {
                linkedList.add(new TopRecord(player.getObjectId(), player.getName(), player.getPkKills()));
            }
            return linkedList;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        protected Collection<TopRecord> fetchTopDbRecords() {
            LinkedList<TopRecord> linkedList = new LinkedList<TopRecord>();
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            try {
                connection = DatabaseFactory.getInstance().getConnection();
                preparedStatement = connection.prepareStatement("SELECT  `characters`.`obj_Id` AS `playerObjectId`,  `characters`.`char_name` AS `playerName`,  `characters`.`pkkills` AS `pkKills` FROM  `characters` ORDER BY  `characters`.`pkkills` DESC LIMIT ?");
                preparedStatement.setInt(1, this.getLimit());
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    linkedList.add(new TopRecord(resultSet.getInt("playerObjectId"), resultSet.getString("playerName"), resultSet.getInt("pkKills")));
                }
            }
            catch (SQLException sQLException) {
                try {
                    eh.error("Can't fetch top PK records.", (Throwable)sQLException);
                }
                catch (Throwable throwable) {
                    DbUtils.closeQuietly((Connection)connection, preparedStatement, resultSet);
                    throw throwable;
                }
                DbUtils.closeQuietly((Connection)connection, (Statement)preparedStatement, resultSet);
            }
            DbUtils.closeQuietly((Connection)connection, (Statement)preparedStatement, (ResultSet)resultSet);
            return linkedList;
        }
    }
}
