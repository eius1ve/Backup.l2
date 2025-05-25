/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.network.l2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PacketFloodProtector {
    private static PacketFloodProtector a;
    private static Map<Integer, PacketData> br;
    private static final Logger cF;

    public static PacketFloodProtector getInstance() {
        if (a == null) {
            a = new PacketFloodProtector();
        }
        return a;
    }

    public PacketFloodProtector() {
        this.load();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void load() {
        if (br == null) {
            br = new HashMap<Integer, PacketData>();
        }
        BufferedReader bufferedReader = null;
        try {
            String string;
            File file = new File("./config/flood_protector.properties");
            bufferedReader = new LineNumberReader(new BufferedReader(new FileReader(file)));
            cF.warn("PacketFloodProtector: initialize");
            while ((string = ((LineNumberReader)bufferedReader).readLine()) != null) {
                PacketData packetData;
                if (string.trim().length() == 0 || string.startsWith("#") || (packetData = this.a(string)) == null) continue;
                br.put(packetData.getPacketId(), packetData);
            }
            cF.info("PacketFloodProtector: Loaded " + br.size() + " packets.");
        }
        catch (FileNotFoundException fileNotFoundException) {
            cF.warn("PacketFloodProtector: config/flood_protector.properties is missing");
        }
        catch (IOException iOException) {
            cF.warn("PacketFloodProtector: error while creating packet flood table " + iOException);
        }
        finally {
            try {
                bufferedReader.close();
            }
            catch (Exception exception) {}
        }
    }

    public void reload() {
        br = null;
        this.load();
    }

    private PacketData a(String string) {
        StringTokenizer stringTokenizer = new StringTokenizer(string, ";");
        try {
            int n = Integer.decode(stringTokenizer.nextToken());
            int n2 = Integer.parseInt(stringTokenizer.nextToken());
            ActionType actionType = ActionType.valueOf(stringTokenizer.nextToken());
            return new PacketData(n, n2, actionType);
        }
        catch (Exception exception) {
            cF.warn("FP: parse error: '" + string + "' " + exception);
            return null;
        }
    }

    public PacketData getDataByPacketId(int n) {
        if (br == null || br.size() == 0) {
            return null;
        }
        return br.get(n);
    }

    static {
        cF = LoggerFactory.getLogger(PacketFloodProtector.class);
    }

    public class PacketData {
        private int pH;
        private int pI;
        private ActionType a;

        public PacketData(int n, int n2, ActionType actionType) {
            this.pH = n;
            this.pI = n2;
            this.a = actionType;
        }

        public int getDelay() {
            return this.pI;
        }

        public ActionType getAction() {
            return this.a;
        }

        public int getPacketId() {
            return this.pH;
        }
    }

    public static final class ActionType
    extends Enum<ActionType> {
        public static final /* enum */ ActionType log = new ActionType();
        public static final /* enum */ ActionType drop_log = new ActionType();
        public static final /* enum */ ActionType kick_log = new ActionType();
        public static final /* enum */ ActionType drop = new ActionType();
        public static final /* enum */ ActionType none = new ActionType();
        private static final /* synthetic */ ActionType[] a;

        public static ActionType[] values() {
            return (ActionType[])a.clone();
        }

        public static ActionType valueOf(String string) {
            return Enum.valueOf(ActionType.class, string);
        }

        private static /* synthetic */ ActionType[] a() {
            return new ActionType[]{log, drop_log, kick_log, drop, none};
        }

        static {
            a = ActionType.a();
        }
    }
}
