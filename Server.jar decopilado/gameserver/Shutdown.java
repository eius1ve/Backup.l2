/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.lucera2.dbmsstruct.model.DBMSStructureSynchronizer
 *  org.apache.commons.io.IOUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver;

import com.lucera2.dbmsstruct.model.DBMSStructureSynchronizer;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import l2.commons.net.nio.impl.SelectorThread;
import l2.commons.time.cron.SchedulingPattern;
import l2.gameserver.Announcements;
import l2.gameserver.Config;
import l2.gameserver.GameServer;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.data.xml.holder.ResidenceHolder;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.instancemanager.CoupleManager;
import l2.gameserver.instancemanager.CursedWeaponsManager;
import l2.gameserver.instancemanager.ServerVariables;
import l2.gameserver.instancemanager.games.FishingChampionShipManager;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.SevenSigns;
import l2.gameserver.model.entity.SevenSignsFestival.SevenSignsFestival;
import l2.gameserver.model.entity.oly.HeroController;
import l2.gameserver.model.entity.oly.NoblesController;
import l2.gameserver.model.entity.oly.OlyController;
import l2.gameserver.model.entity.oly.ParticipantPool;
import l2.gameserver.model.entity.oly.StadiumPool;
import l2.gameserver.model.entity.residence.Residence;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.authcomm.AuthServerCommunication;
import l2.gameserver.network.l2.CGModule;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.scripts.Scripts;
import l2.gameserver.utils.Util;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Shutdown
extends Thread {
    public static final int SHUTDOWN = 0;
    public static final int RESTART = 2;
    public static final int NONE = -1;
    private static final Logger ap = LoggerFactory.getLogger(Shutdown.class);
    private static final Shutdown a = new Shutdown();
    private Timer a;
    private int fh;
    private int fi;

    private Shutdown() {
        this.setName(this.getClass().getSimpleName());
        this.setDaemon(true);
        this.fh = -1;
    }

    public static final Shutdown getInstance() {
        return a;
    }

    public int getSeconds() {
        return this.fh == -1 ? -1 : this.fi;
    }

    public int getMode() {
        return this.fh;
    }

    public synchronized void schedule(int n, int n2) {
        if (n < 0) {
            return;
        }
        if (this.a != null) {
            this.a.cancel();
        }
        this.fh = n2;
        this.fi = n;
        ap.info("Scheduled server " + (n2 == 0 ? "shutdown" : "restart") + " in " + Util.formatTime(n) + ".");
        this.a = new Timer("ShutdownCounter", true);
        this.a.scheduleAtFixedRate((TimerTask)new ShutdownCounter(), 0L, 1000L);
    }

    public void schedule(String string, int n) {
        SchedulingPattern schedulingPattern;
        try {
            schedulingPattern = new SchedulingPattern(string);
        }
        catch (SchedulingPattern.InvalidPatternException invalidPatternException) {
            return;
        }
        int n2 = (int)(schedulingPattern.next(System.currentTimeMillis()) / 1000L - System.currentTimeMillis() / 1000L);
        this.schedule(n2, n);
    }

    public synchronized void cancel() {
        this.fh = -1;
        if (this.a != null) {
            this.a.cancel();
        }
        this.a = null;
    }

    @Override
    public void run() {
        this.al();
        if (!GameServer.getInstance().getPendingShutdown().compareAndSet(false, true)) {
            return;
        }
        ap.info("Shutting down LS/GS communication...");
        AuthServerCommunication.getInstance().shutdown();
        ap.info("Shutting down scripts...");
        Scripts.getInstance().shutdown();
        ap.info("Disconnecting players...");
        this.ak();
        this.aj();
        ap.info("Saving data...");
        this.saveData();
        try {
            if (CGModule.getInstance().isActive()) {
                CGModule.getInstance().done();
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        try {
            ap.info("Shutting down thread pool...");
            ThreadPoolManager.getInstance().shutdown();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        ap.info("Shutting down selector...");
        if (GameServer.getInstance() != null) {
            for (SelectorThread<GameClient> selectorThread : GameServer.getInstance().getSelectorThreads()) {
                try {
                    selectorThread.shutdown();
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
        try {
            ThreadPoolManager.getInstance().shutdownNow();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        this.dumpTables();
        try {
            ap.info("Shutting down database communication...");
            DatabaseFactory.getInstance().shutdown();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        ap.info("Shutdown finished.");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void dumpTables() {
        long l = System.currentTimeMillis();
        if (Config.DATABASE_DUMP_TABLES == null || Config.DATABASE_DUMP_TABLES.length == 0 || StringUtils.isBlank((CharSequence)Config.DATABASE_DUMP_FILENAME_FORMAT)) {
            return;
        }
        try {
            ap.info("Database backup ...");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HH-mm-ss");
            try (Connection connection = DatabaseFactory.getInstance().getConnection();){
                Object object;
                Object object2;
                Object object3;
                DBMSStructureSynchronizer dBMSStructureSynchronizer = GameServer.getInstance().getDbmsStructureSynchronizer(connection);
                ArrayList<String> arrayList = new ArrayList<String>();
                for (String object4 : Config.DATABASE_DUMP_TABLES) {
                    if (StringUtils.equals((CharSequence)object4, (CharSequence)"*")) {
                        arrayList.addAll(dBMSStructureSynchronizer.getStructTableNames());
                        continue;
                    }
                    arrayList.add(object4);
                }
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                ArrayList<Object> arrayList2 = new ArrayList<Object>();
                for (String string : arrayList) {
                    object3 = Config.DATABASE_DUMP_FILENAME_FORMAT;
                    object3 = StringUtils.replaceIgnoreCase((String)object3, (String)"%table_name%", (String)string);
                    object3 = StringUtils.replaceIgnoreCase((String)object3, (String)"%date%", (String)simpleDateFormat.format(l));
                    object3 = StringUtils.replaceIgnoreCase((String)object3, (String)"%time%", (String)simpleDateFormat2.format(l));
                    boolean bl = ((String)object3).endsWith("gz");
                    object2 = new File((String)object3);
                    ap.info("Dumping '{}' into {} ...", (Object)string, (Object)((File)object2).toString());
                    ((File)object2).getParentFile().mkdirs();
                    try (FileOutputStream fileOutputStream = new FileOutputStream((File)object2);){
                        long l2 = 0L;
                        object = bl ? new GZIPOutputStream(fileOutputStream) : new BufferedOutputStream(fileOutputStream);
                        try {
                            l2 = dBMSStructureSynchronizer.dump(string, (OutputStream)object);
                        }
                        finally {
                            ((OutputStream)object).close();
                        }
                        if (l2 > 0L) {
                            linkedHashMap.put(string, object2);
                            continue;
                        }
                        arrayList2.add(object2);
                    }
                    catch (Exception exception) {
                        ap.warn("{}", (Object)(exception.getCause() != null ? exception.getCause().getMessage() : exception.getMessage()));
                        arrayList2.add(object2);
                    }
                }
                if (!StringUtils.isBlank((CharSequence)Config.DATABASE_DUMP_ZIP_OUT_FILENAME_FORMAT)) {
                    Object object5 = Config.DATABASE_DUMP_ZIP_OUT_FILENAME_FORMAT;
                    object5 = StringUtils.replaceIgnoreCase(object5, (String)"%date%", (String)simpleDateFormat.format(l));
                    object5 = StringUtils.replaceIgnoreCase((String)object5, (String)"%time%", (String)simpleDateFormat2.format(l));
                    File file = new File((String)object5);
                    file.getParentFile().mkdirs();
                    object3 = new FileOutputStream(file);
                    try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream((OutputStream)object3, 0x1000000);){
                        object2 = new ZipOutputStream((OutputStream)bufferedOutputStream, StandardCharsets.UTF_8);
                        try {
                            for (Map.Entry entry : linkedHashMap.entrySet()) {
                                File file2 = (File)entry.getValue();
                                if (!file2.exists()) continue;
                                ap.info("Zipping {} into {} ...", (Object[])new String[]{file2.toString(), file.toString()});
                                object = new ZipEntry(file2.getName());
                                ((ZipEntry)object).setMethod(8);
                                ((ZipEntry)object).setTime(l);
                                ((ZipEntry)object).setComment((String)entry.getKey());
                                ((ZipOutputStream)object2).putNextEntry((ZipEntry)object);
                                try {
                                    try (FileInputStream fileInputStream = new FileInputStream(file2);
                                         BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream, 0x1000000);){
                                        IOUtils.copyLarge((InputStream)bufferedInputStream, (OutputStream)object2);
                                    }
                                    arrayList2.add(file2);
                                }
                                finally {
                                    ((ZipOutputStream)object2).closeEntry();
                                }
                            }
                        }
                        finally {
                            ((ZipOutputStream)object2).close();
                        }
                    }
                    finally {
                        ((FileOutputStream)object3).close();
                    }
                }
                if (!arrayList2.isEmpty()) {
                    for (File file : arrayList2) {
                        try {
                            file.delete();
                        }
                        catch (Exception exception) {}
                    }
                }
            }
            catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void saveData() {
        try {
            if (!SevenSigns.getInstance().isSealValidationPeriod()) {
                SevenSignsFestival.getInstance().saveFestivalData(false);
                ap.info("SevenSignsFestival: Data saved.");
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        try {
            SevenSigns.getInstance().saveSevenSignsData(0, true);
            ap.info("SevenSigns: Data saved.");
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        if (Config.ALLOW_WEDDING) {
            try {
                CoupleManager.getInstance().store();
                ap.info("CoupleManager: Data saved.");
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        try {
            FishingChampionShipManager.getInstance().shutdown();
            ap.info("FishingChampionShipManager: Data saved.");
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        try {
            HeroController.getInstance().saveHeroes();
            ap.info("Hero: Data saved.");
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        try {
            Collection<Residence> collection = ResidenceHolder.getInstance().getResidences();
            for (Residence residence : collection) {
                residence.update();
            }
            ap.info("Residences: Data saved.");
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        if (Config.ALLOW_CURSED_WEAPONS) {
            try {
                CursedWeaponsManager.getInstance().saveData();
                ap.info("CursedWeaponsManager: Data saved.");
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        try {
            NoblesController.getInstance().SaveNobleses();
            ap.info("NoblesController: Data saved.");
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        if (Config.OLY_ENABLED) {
            try {
                ParticipantPool.getInstance().FreePools();
                StadiumPool.getInstance().FreeStadiums();
                OlyController.getInstance().shutdown();
                ap.info("Olympiad System: Oly cleaned and data saved!");
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    private void aj() {
        int n = 0;
        for (NpcInstance npcInstance : GameObjectsStorage.getAllNpcs()) {
            if (npcInstance == null || !npcInstance.isRaid() && !npcInstance.isBoss() || !npcInstance.isDead() || npcInstance.isDeleted()) continue;
            ++n;
        }
        if (n > 0) {
            ap.info("Waiting decoy of " + n + " object(s).");
            try {
                Thread.sleep(20000L);
            }
            catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        }
    }

    private void ak() {
        for (Player player : GameObjectsStorage.getAllPlayersForIterate()) {
            try {
                player.logout();
            }
            catch (Exception exception) {
                ap.error("Error while disconnecting: " + player + "!");
                exception.printStackTrace();
            }
        }
    }

    private void al() {
        int n;
        int n2 = GameServer.getInstance().getCurrentMaxOnline();
        if (n2 > (n = ServerVariables.getInt("maxTotalOnline", 0))) {
            ServerVariables.set("maxTotalOnline", n2);
        }
    }

    private class ShutdownCounter
    extends TimerTask {
        private ShutdownCounter() {
        }

        @Override
        public void run() {
            switch (Shutdown.this.fi) {
                case 60: 
                case 120: 
                case 180: 
                case 240: 
                case 300: 
                case 600: 
                case 900: 
                case 1800: {
                    switch (Shutdown.this.fh) {
                        case 0: {
                            Announcements.getInstance().announceByCustomMessage("THE_SERVER_WILL_BE_COMING_DOWN_IN_S1_MINUTES", new String[]{String.valueOf(Shutdown.this.fi / 60)});
                            break;
                        }
                        case 2: {
                            Announcements.getInstance().announceByCustomMessage("THE_SERVER_WILL_BE_COMING_RESTARTED_IN_S1_MINUTES", new String[]{String.valueOf(Shutdown.this.fi / 60)});
                        }
                    }
                    break;
                }
                case 5: 
                case 10: 
                case 20: 
                case 30: {
                    Announcements.getInstance().announceToAll((SystemMessage)new SystemMessage(SystemMsg.THE_SERVER_WILL_BE_COMING_DOWN_IN_S1_SECONDS__PLEASE_FIND_A_SAFE_PLACE_TO_LOG_OUT).addNumber(Shutdown.this.fi));
                    break;
                }
                case 0: {
                    switch (Shutdown.this.fh) {
                        case 0: {
                            Runtime.getRuntime().exit(0);
                            break;
                        }
                        case 2: {
                            Runtime.getRuntime().exit(2);
                        }
                    }
                    this.cancel();
                    return;
                }
            }
            --Shutdown.this.fi;
        }
    }
}
