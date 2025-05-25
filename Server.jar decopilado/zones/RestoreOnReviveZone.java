/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.listener.Listener
 *  l2.gameserver.instancemanager.ReflectionManager
 *  l2.gameserver.listener.actor.OnReviveListener
 *  l2.gameserver.listener.zone.OnZoneEnterLeaveListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Zone
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.SkillCoolTime
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package zones;

import java.util.Collection;
import l2.commons.listener.Listener;
import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.listener.actor.OnReviveListener;
import l2.gameserver.listener.zone.OnZoneEnterLeaveListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.Zone;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.SkillCoolTime;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zones.NoPartyZone;

public class RestoreOnReviveZone
extends Functions
implements OnZoneEnterLeaveListener,
ScriptFile {
    private static final Logger eL = LoggerFactory.getLogger(NoPartyZone.class);
    private static final RestoreOnReviveZone a = new RestoreOnReviveZone();
    private static final String jk = "restoreZoneHpCpMpOnRevive";
    private static final String jl = "restoreZoneSkillCooldownOnRevive";
    final OnReviveListener onReviveHpCpMp = new OnReviveListener(){

        public void onRevive(Creature creature) {
            creature.setCurrentHpMp((double)creature.getMaxHp(), (double)creature.getMaxMp());
            creature.setCurrentCp((double)creature.getMaxCp());
            creature.sendChanges();
        }
    };
    final OnReviveListener onReviveRemoveCooldown = new OnReviveListener(){

        public void onRevive(Creature creature) {
            Player player = creature.getPlayer();
            player.resetReuse();
            player.sendPacket((IStaticPacket)new SkillCoolTime(player));
        }
    };

    public void onLoad() {
        int n = 0;
        Collection collection = ReflectionManager.DEFAULT.getZones();
        for (Zone zone : collection) {
            if (!zone.getParams().getBool((Object)jk, false) && !zone.getParams().getBool((Object)jl, false)) continue;
            zone.addListener((Listener)a);
            ++n;
        }
        if (n > 0) {
            eL.info("restoreOnReviveZone: added {} restore on Revive zone(s).", (Object)n);
        }
    }

    public void onReload() {
        this.onShutdown();
        this.onLoad();
    }

    public void onShutdown() {
        Collection collection = ReflectionManager.DEFAULT.getZones();
        for (Zone zone : collection) {
            if (!zone.getParams().getBool((Object)jk, false) && !zone.getParams().getBool((Object)jl, false)) continue;
            zone.removeListener((Listener)a);
        }
    }

    public void onZoneEnter(Zone zone, Creature creature) {
        if (!creature.isPlayer()) {
            return;
        }
        Player player = creature.getPlayer();
        if (player != null) {
            if (zone.getParams().getBool((Object)jk, false)) {
                player.addListener((Listener)this.onReviveHpCpMp);
            }
            if (zone.getParams().getBool((Object)jl, false)) {
                player.addListener((Listener)this.onReviveRemoveCooldown);
            }
        }
    }

    public void onZoneLeave(Zone zone, Creature creature) {
        if (!creature.isPlayer()) {
            return;
        }
        Player player = creature.getPlayer();
        if (player != null) {
            if (zone.getParams().getBool((Object)jk, false)) {
                creature.removeListener((Listener)this.onReviveHpCpMp);
            }
            if (zone.getParams().getBool((Object)jl, false)) {
                creature.removeListener((Listener)this.onReviveRemoveCooldown);
            }
        }
    }
}
