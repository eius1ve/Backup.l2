/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.taskmanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.instancemanager.ServerVariables;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class AutoFarmManager {
    private static final Logger dv = LoggerFactory.getLogger(AutoFarmManager.class);
    private final Map<String, Integer> bM = new ConcurrentHashMap<String, Integer>();
    private final List<Integer> dl = new ArrayList<Integer>();
    private ScheduledFuture<?> S = null;

    protected AutoFarmManager() {
        this.bM.clear();
        this.dl.clear();
        this.bX();
    }

    public int getActiveFarms(String string) {
        if (Config.FARM_ACTIVE_LIMITS < 0) {
            return Integer.MAX_VALUE;
        }
        if (this.bM.containsKey(string)) {
            return Config.FARM_ACTIVE_LIMITS - this.bM.get(string);
        }
        return Config.FARM_ACTIVE_LIMITS;
    }

    public void addActiveFarm(String string, int n) {
        if (Config.FARM_ACTIVE_LIMITS < 0) {
            return;
        }
        if (this.bM.containsKey(string)) {
            if (this.isNonCheckPlayer(n)) {
                return;
            }
            int n2 = this.bM.get(string) + 1;
            this.bM.put(string, n2);
            return;
        }
        this.bM.put(string, 1);
    }

    public void removeActiveFarm(String string, int n) {
        if (Config.FARM_ACTIVE_LIMITS < 0) {
            return;
        }
        if (this.bM.containsKey(string)) {
            if (this.isNonCheckPlayer(n)) {
                return;
            }
            int n2 = this.bM.get(string) - 1;
            this.bM.put(string, n2);
        }
    }

    public void addNonCheckPlayer(int n) {
        if (Config.FARM_ACTIVE_LIMITS < 0) {
            return;
        }
        if (!this.dl.contains(n)) {
            this.dl.add(n);
        }
    }

    public boolean isNonCheckPlayer(int n) {
        return this.dl.contains(n);
    }

    private void bX() {
        if (!Config.REFRESH_FARM_TIME) {
            return;
        }
        long l = ServerVariables.getLong("Farm_FreeTime", 0L);
        if (System.currentTimeMillis() > l) {
            this.bY();
        } else {
            this.S = ThreadPoolManager.getInstance().schedule(new ClearFarmFreeTime(), l - System.currentTimeMillis());
        }
    }

    private void bY() {
        if (!Config.REFRESH_FARM_TIME) {
            return;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setLenient(true);
        calendar.set(11, 6);
        calendar.set(12, 30);
        calendar.add(5, 1);
        ServerVariables.set("Farm_FreeTime", calendar.getTimeInMillis());
        try (Connection connection = DatabaseFactory.getInstance().getConnection();
             PreparedStatement object = connection.prepareStatement("DELETE FROM character_variables WHERE name = ?");){
            object.setString(1, "farmFreeTime");
            object.execute();
        }
        catch (Exception exception) {
            dv.warn("Failed to clean up autoFarm free times datas.", (Throwable)exception);
        }
        for (Player player : GameObjectsStorage.getAllPlayersForIterate()) {
            if (player == null) continue;
            player.unsetVar("farmFreeTime");
        }
        if (this.S != null) {
            this.S.cancel(false);
            this.S = null;
        }
        this.S = ThreadPoolManager.getInstance().schedule(new ClearFarmFreeTime(), calendar.getTimeInMillis() - System.currentTimeMillis());
        dv.info("AutoFarmManager: AutoFarm free times reshresh completed.");
        dv.info("AutoFarmManager: Next autoFarm free times reshresh at: " + Util.formatTime((int)(calendar.getTimeInMillis() - System.currentTimeMillis()) / 1000));
    }

    public static final AutoFarmManager getInstance() {
        return SingletonHolder._instance;
    }

    private class ClearFarmFreeTime
    extends RunnableImpl {
        private ClearFarmFreeTime() {
        }

        @Override
        public void runImpl() {
            AutoFarmManager.this.bY();
        }
    }

    private static class SingletonHolder {
        protected static final AutoFarmManager _instance = new AutoFarmManager();

        private SingletonHolder() {
        }
    }
}
