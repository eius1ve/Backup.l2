/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.io.FileUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.Future;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.ChatType;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.Say2;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.utils.MapRegionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Announcements {
    private static final Logger al = LoggerFactory.getLogger(Announcements.class);
    private static final Announcements a = new Announcements();
    private List<Announce> Y = new ArrayList<Announce>();

    public static final Announcements getInstance() {
        return a;
    }

    private Announcements() {
        this.loadAnnouncements();
    }

    public List<Announce> getAnnouncements() {
        return this.Y;
    }

    public void loadAnnouncements() {
        this.Y.clear();
        try {
            List<String> list = Arrays.asList(FileUtils.readFileToString((File)new File("config/announcements.txt"), (String)"UTF-8").split("\n"));
            for (String string : list) {
                if (StringUtils.isEmpty((CharSequence)string)) continue;
                StringTokenizer stringTokenizer = new StringTokenizer(string, "\t");
                if (stringTokenizer.countTokens() > 1) {
                    this.addAnnouncement(Integer.parseInt(stringTokenizer.nextToken()), stringTokenizer.nextToken(), false);
                    continue;
                }
                this.addAnnouncement(0, string, false);
            }
        }
        catch (Exception exception) {
            al.error("Error while loading config/announcements.txt!");
        }
    }

    public void showAnnouncements(Player player) {
        for (Announce announce : this.Y) {
            announce.showAnnounce(player);
        }
    }

    public void addAnnouncement(int n, String string, boolean bl) {
        Announce announce = new Announce(n, string);
        announce.start();
        this.Y.add(announce);
        if (bl) {
            this.ah();
        }
    }

    public void delAnnouncement(int n) {
        Announce announce = this.Y.remove(n);
        if (announce != null) {
            announce.stop();
        }
        this.ah();
    }

    private void ah() {
        try {
            File file = new File("config/announcements.txt");
            FileWriter fileWriter = new FileWriter(file, false);
            for (Announce announce : this.Y) {
                fileWriter.write(announce.getTime() + "\t" + announce.getAnnounce() + "\n");
            }
            fileWriter.close();
        }
        catch (Exception exception) {
            al.error("Error while saving config/announcements.txt!", (Throwable)exception);
        }
    }

    public void announceToAll(String string) {
        this.announceToAll(string, ChatType.ANNOUNCEMENT);
    }

    public static void shout(Creature creature, String string, ChatType chatType) {
        Say2 say2 = new Say2(creature.getObjectId(), chatType, creature.getName(), string);
        int n = MapRegionUtils.regionX(creature);
        int n2 = MapRegionUtils.regionY(creature);
        int n3 = Config.SHOUT_OFFSET;
        for (Player player : GameObjectsStorage.getAllPlayersForIterate()) {
            if (player == creature || creature.getReflection() != player.getReflection()) continue;
            int n4 = MapRegionUtils.regionX(player);
            int n5 = MapRegionUtils.regionY(player);
            if ((n4 < n - n3 || n4 > n + n3 || n5 < n2 - n3 || n5 > n2 + n3) && !creature.isInRangeZ(player, (long)Config.CHAT_RANGE)) continue;
            player.sendPacket((IStaticPacket)say2);
        }
        creature.sendPacket((IStaticPacket)say2);
    }

    public void announceToAll(String string, ChatType chatType) {
        Say2 say2 = new Say2(0, chatType, "", string);
        for (Player player : GameObjectsStorage.getAllPlayersForIterate()) {
            player.sendPacket((IStaticPacket)say2);
        }
    }

    public void announceByCustomMessage(String string, String[] stringArray) {
        for (Player player : GameObjectsStorage.getAllPlayersForIterate()) {
            this.announceToPlayerByCustomMessage(player, string, stringArray);
        }
    }

    public void announceByCustomMessage(String string, String[] stringArray, ChatType chatType) {
        for (Player player : GameObjectsStorage.getAllPlayersForIterate()) {
            this.announceToPlayerByCustomMessage(player, string, stringArray, chatType);
        }
    }

    public void announceToPlayerByCustomMessage(Player player, String string, String[] stringArray) {
        CustomMessage customMessage = new CustomMessage(string, player, new Object[0]);
        if (stringArray != null) {
            for (String string2 : stringArray) {
                customMessage.addString(string2);
            }
        }
        player.sendPacket((IStaticPacket)new Say2(0, ChatType.ANNOUNCEMENT, "", customMessage.toString()));
    }

    public void announceToPlayerByCustomMessage(Player player, String string, String[] stringArray, ChatType chatType) {
        CustomMessage customMessage = new CustomMessage(string, player, new Object[0]);
        if (stringArray != null) {
            for (String string2 : stringArray) {
                customMessage.addString(string2);
            }
        }
        player.sendPacket((IStaticPacket)new Say2(0, chatType, "", customMessage.toString()));
    }

    public void announceToAll(SystemMessage systemMessage) {
        for (Player player : GameObjectsStorage.getAllPlayersForIterate()) {
            player.sendPacket((IStaticPacket)systemMessage);
        }
    }

    public void announceToAll(IStaticPacket iStaticPacket) {
        for (Player player : GameObjectsStorage.getAllPlayersForIterate()) {
            player.sendPacket(iStaticPacket);
        }
    }

    public class Announce
    extends RunnableImpl {
        private Future<?> e;
        private final int _time;
        private final String aT;

        public Announce(int n, String string) {
            this._time = n;
            this.aT = string;
        }

        @Override
        public void runImpl() throws Exception {
            Announcements.this.announceToAll(this.aT);
        }

        public void showAnnounce(Player player) {
            Say2 say2 = new Say2(0, ChatType.ANNOUNCEMENT, player.getName(), this.aT);
            player.sendPacket((IStaticPacket)say2);
        }

        public void start() {
            if (this._time > 0) {
                this.e = ThreadPoolManager.getInstance().scheduleAtFixedRate(this, (long)this._time * 1000L, (long)this._time * 1000L);
            }
        }

        public void stop() {
            if (this.e != null) {
                this.e.cancel(false);
                this.e = null;
            }
        }

        public int getTime() {
            return this._time;
        }

        public String getAnnounce() {
            return this.aT;
        }
    }
}
