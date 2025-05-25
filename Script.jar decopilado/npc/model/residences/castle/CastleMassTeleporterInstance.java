/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.World
 *  l2.gameserver.model.entity.events.impl.SiegeEvent
 *  l2.gameserver.model.entity.events.objects.SiegeToggleNpcObject
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.templates.npc.NpcTemplate
 *  l2.gameserver.utils.Location
 */
package npc.model.residences.castle;

import java.util.List;
import java.util.concurrent.Future;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.model.World;
import l2.gameserver.model.entity.events.impl.SiegeEvent;
import l2.gameserver.model.entity.events.objects.SiegeToggleNpcObject;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.scripts.Functions;
import l2.gameserver.templates.npc.NpcTemplate;
import l2.gameserver.utils.Location;

public class CastleMassTeleporterInstance
extends NpcInstance {
    private Future<?> S = null;
    private final Location ag;
    private final long en;
    private final long eo;

    public CastleMassTeleporterInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
        this.ag = Location.parseLoc((String)npcTemplate.getAIParams().getString((Object)"teleport_loc"));
        this.en = npcTemplate.getAIParams().getLong((Object)"teleport_delay", 30000L);
        this.eo = npcTemplate.getAIParams().getLong((Object)"teleport_delay_crystal_dead", 480000L);
    }

    public void onBypassFeedback(Player player, String string) {
        if (!CastleMassTeleporterInstance.canBypassCheck((Player)player, (NpcInstance)this)) {
            return;
        }
        if (this.S != null) {
            this.showChatWindow(player, "residence2/castle/CastleTeleportDelayed.htm", new Object[0]);
            return;
        }
        this.S = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new TeleportTask()), this.x() ? this.eo : this.en);
        this.showChatWindow(player, "residence2/castle/CastleTeleportDelayed.htm", new Object[0]);
    }

    public void showChatWindow(Player player, int n, Object ... objectArray) {
        if (this.S != null) {
            this.showChatWindow(player, "residence2/castle/CastleTeleportDelayed.htm", new Object[0]);
        } else if (this.x()) {
            this.showChatWindow(player, "residence2/castle/gludio_mass_teleporter002.htm", new Object[0]);
        } else {
            this.showChatWindow(player, "residence2/castle/gludio_mass_teleporter001.htm", new Object[0]);
        }
    }

    private boolean x() {
        SiegeEvent siegeEvent = (SiegeEvent)this.getEvent(SiegeEvent.class);
        if (siegeEvent == null || !siegeEvent.isInProgress()) {
            return false;
        }
        List list = siegeEvent.getObjects("control_towers");
        for (SiegeToggleNpcObject siegeToggleNpcObject : list) {
            if (!siegeToggleNpcObject.isAlive()) continue;
            return false;
        }
        return true;
    }

    protected boolean canInteractWithKarmaPlayer() {
        return true;
    }

    protected boolean canInteractWithCursedWeaponPlayer() {
        return true;
    }

    private class TeleportTask
    extends RunnableImpl {
        private TeleportTask() {
        }

        public void runImpl() throws Exception {
            Functions.npcShoutCustomMessage((NpcInstance)CastleMassTeleporterInstance.this, (String)"NpcString.THE_DEFENDERS_OF_S1_CASTLE_WILL_BE_TELEPORTED_TO_THE_INNER_CASTLE", (Object[])new Object[]{CastleMassTeleporterInstance.this.getCastle().getName()});
            for (Player player : World.getAroundPlayers((GameObject)CastleMassTeleporterInstance.this, (int)650, (int)50)) {
                player.teleToLocation(Location.findPointToStay((Location)CastleMassTeleporterInstance.this.ag, (int)10, (int)100, (int)player.getGeoIndex()));
            }
            CastleMassTeleporterInstance.this.S = null;
        }
    }
}
