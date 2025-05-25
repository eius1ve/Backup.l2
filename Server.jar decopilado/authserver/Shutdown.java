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
package l2.authserver;

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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import l2.authserver.AuthServer;
import l2.authserver.Config;
import l2.authserver.ThreadPoolManager;
import l2.authserver.database.L2DatabaseFactory;
import l2.authserver.network.l2.BaseLoginClient;
import l2.commons.net.nio.impl.SelectorThread;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Shutdown
extends Thread {
    public static final int SHUTDOWN = 0;
    public static final int RESTART = 2;
    public static final int NONE = -1;
    private static final Logger N = LoggerFactory.getLogger(Shutdown.class);
    private static final Shutdown a = new Shutdown();

    private Shutdown() {
        this.setName(this.getClass().getSimpleName());
        this.setDaemon(true);
    }

    public static final Shutdown getInstance() {
        return a;
    }

    @Override
    public void run() {
        try {
            N.info("Shutting down thread pool...");
            ThreadPoolManager.getInstance().shutdown();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        N.info("Shutting down selector...");
        if (AuthServer.getInstance() != null && AuthServer.getInstance().getSelectorThreads() != null) {
            for (SelectorThread<BaseLoginClient> selectorThread : AuthServer.getInstance().getSelectorThreads()) {
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
            N.info("Shutting down database communication...");
            L2DatabaseFactory.getInstance().shutdown();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        N.info("Shutdown finished.");
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
            N.info("Database backup ...");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HH-mm-ss");
            try (Connection connection = L2DatabaseFactory.getInstance().getConnection();){
                Object object;
                Object object2;
                Object object3;
                DBMSStructureSynchronizer dBMSStructureSynchronizer = new DBMSStructureSynchronizer(connection, Config.DATABASE_NAME, AuthServer.class.getResourceAsStream("/authd.json"));
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
                    N.info("Dumping '{}' into {} ...", (Object)string, (Object)((File)object2).toString());
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
                        N.warn("{}", (Object)(exception.getCause() != null ? exception.getCause().getMessage() : exception.getMessage()));
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
                                N.info("Zipping {} into {} ...", (Object[])new String[]{file2.toString(), file.toString()});
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
}
