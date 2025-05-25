/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.listener.Listener
 *  l2.gameserver.instancemanager.ReflectionManager
 *  l2.gameserver.listener.zone.OnZoneEnterLeaveListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Zone
 *  l2.gameserver.scripts.ScriptFile
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package zones;

import java.util.Collection;
import l2.commons.listener.Listener;
import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.listener.zone.OnZoneEnterLeaveListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.Zone;
import l2.gameserver.scripts.ScriptFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChatMuteZone
implements OnZoneEnterLeaveListener,
ScriptFile {
    private static final Logger ew = LoggerFactory.getLogger(ChatMuteZone.class);
    private static final ChatMuteZone a = new ChatMuteZone();
    private static final String iv = "NoChatZone";
    private static final String iw = "NoChatZoneMinutes";

    public void onLoad() {
        int n = 0;
        Collection collection = ReflectionManager.DEFAULT.getZones();
        for (Zone zone : collection) {
            boolean bl;
            if (!zone.getParams().getBool((Object)iv, false) || !(bl = zone.getParams().isSet((Object)iw))) continue;
            int n2 = zone.getParams().getInteger((Object)iw);
            if (n2 == 0) {
                ew.warn("ChatMuteZone ERROR: for zone {} is 0.", (Object)zone);
                continue;
            }
            zone.addListener((Listener)a);
            ++n;
        }
        if (n > 0) {
            ew.info("ChatMuteZone: added {} chat mute zone(s).", (Object)n);
        }
    }

    public void onReload() {
        this.onShutdown();
        this.onLoad();
    }

    public void onShutdown() {
        Collection collection = ReflectionManager.DEFAULT.getZones();
        for (Zone zone : collection) {
            if (!zone.getParams().getBool((Object)iv, false)) continue;
            zone.removeListener((Listener)a);
        }
    }

    public void onZoneEnter(Zone zone, Creature creature) {
        int n = zone.getParams().getInteger((Object)iw);
        if (!creature.isPlayer()) {
            return;
        }
        Player player = creature.getPlayer();
        if (player.isGM() || player.getNoChannel() > (long)n * 60000L) {
            return;
        }
        player.updateNoChannel((long)n * 60000L);
    }

    public void onZoneLeave(Zone zone, Creature creature) {
        int n = zone.getParams().getInteger((Object)iw);
        if (!creature.isPlayer()) {
            return;
        }
        Player player = creature.getPlayer();
        if (player.isGM() || player.getNoChannel() > (long)n * 60000L) {
            return;
        }
        player.updateNoChannel(0L);
    }
}
