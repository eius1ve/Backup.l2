/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.listener.Listener
 *  l2.gameserver.instancemanager.ReflectionManager
 *  l2.gameserver.listener.zone.OnZoneEnterLeaveListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Party
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
import l2.gameserver.model.Party;
import l2.gameserver.model.Player;
import l2.gameserver.model.Zone;
import l2.gameserver.scripts.ScriptFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoPartyZone
implements OnZoneEnterLeaveListener,
ScriptFile {
    private static final Logger eH = LoggerFactory.getLogger(NoPartyZone.class);
    private static final NoPartyZone a = new NoPartyZone();
    private static final String jg = "noParty";

    public void onLoad() {
        int n = 0;
        Collection collection = ReflectionManager.DEFAULT.getZones();
        for (Zone zone : collection) {
            if (!zone.getParams().getBool((Object)jg, false)) continue;
            zone.addListener((Listener)a);
            ++n;
        }
        if (n > 0) {
            eH.info("NoPartyZone: added {} no party zone(s).", (Object)n);
        }
    }

    public void onReload() {
        this.onShutdown();
        this.onLoad();
    }

    public void onShutdown() {
        Collection collection = ReflectionManager.DEFAULT.getZones();
        for (Zone zone : collection) {
            if (!zone.getParams().getBool((Object)jg, false)) continue;
            zone.removeListener((Listener)a);
        }
    }

    public void onZoneEnter(Zone zone, Creature creature) {
        if (!creature.isPlayer()) {
            return;
        }
        Player player = creature.getPlayer();
        Party party = player.getParty();
        if (party != null) {
            party.removePartyMember(player, true);
        }
        player.setPartyRefusal(true);
    }

    public void onZoneLeave(Zone zone, Creature creature) {
        if (!creature.isPlayer()) {
            return;
        }
        Player player = creature.getPlayer();
        player.setPartyRefusal(false);
    }
}
