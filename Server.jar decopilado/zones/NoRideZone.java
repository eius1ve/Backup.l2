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
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.ActionFail
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
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ActionFail;
import l2.gameserver.scripts.ScriptFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoRideZone
implements OnZoneEnterLeaveListener,
ScriptFile {
    private static final Logger eI = LoggerFactory.getLogger(NoRideZone.class);
    private static final NoRideZone a = new NoRideZone();
    private static final String jh = "noRideZone";

    public void onZoneEnter(Zone zone, Creature creature) {
        Player player = creature.getPlayer();
        if (player != null && (player.isFlying() || player.isMounted())) {
            player.stopMove();
            player.sendPacket(new IStaticPacket[]{SystemMsg.YOU_ARE_NOT_ALLOWED_TO_DISMOUNT_IN_THIS_LOCATION, ActionFail.STATIC});
            player.setMount(0, 0, 0);
        }
    }

    public void onZoneLeave(Zone zone, Creature creature) {
    }

    public void onLoad() {
        int n = 0;
        Collection collection = ReflectionManager.DEFAULT.getZones();
        for (Zone zone : collection) {
            if (!zone.getParams().getBool((Object)jh, false)) continue;
            zone.addListener((Listener)a);
            ++n;
        }
        if (n > 0) {
            eI.info("NoRideZone: added {} zone(s).", (Object)n);
        }
    }

    public void onReload() {
        this.onLoad();
    }

    public void onShutdown() {
    }
}
