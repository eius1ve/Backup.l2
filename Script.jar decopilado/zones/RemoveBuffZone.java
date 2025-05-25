/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.TIntHashSet
 *  gnu.trove.TIntIterator
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

import gnu.trove.TIntHashSet;
import gnu.trove.TIntIterator;
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

public class RemoveBuffZone
implements OnZoneEnterLeaveListener,
ScriptFile {
    private static final Logger eK = LoggerFactory.getLogger(RemoveBuffZone.class);
    private static final String jj = "removeEffects";
    private TIntHashSet j;

    public RemoveBuffZone() {
        this.j = new TIntHashSet();
    }

    public RemoveBuffZone(int[] nArray) {
        this.j = new TIntHashSet(nArray);
    }

    public void onLoad() {
        int n = 0;
        Collection collection = ReflectionManager.DEFAULT.getZones();
        for (Zone zone : collection) {
            if (zone.getParams().getString((Object)jj, "").isEmpty()) continue;
            zone.addListener((Listener)new RemoveBuffZone(zone.getParams().getIntegerArray((Object)jj)));
            ++n;
        }
        if (n > 0) {
            eK.info("RemoveBuffZone: added {} remove effect zone(s).", (Object)n);
        }
    }

    public void onReload() {
        this.onShutdown();
        this.onLoad();
    }

    public void onShutdown() {
    }

    public void onZoneEnter(Zone zone, Creature creature) {
        if (!creature.isPlayer()) {
            return;
        }
        Player player = creature.getPlayer();
        TIntIterator tIntIterator = this.j.iterator();
        while (tIntIterator.hasNext()) {
            player.getEffectList().stopEffect(tIntIterator.next());
        }
    }

    public void onZoneLeave(Zone zone, Creature creature) {
    }
}
