/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.World
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.utils.Location
 */
package npc.model.residences.castle;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.model.World;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.scripts.Functions;
import l2.gameserver.utils.Location;

private class CastleMassTeleporterInstance.TeleportTask
extends RunnableImpl {
    private CastleMassTeleporterInstance.TeleportTask() {
    }

    public void runImpl() throws Exception {
        Functions.npcShoutCustomMessage((NpcInstance)CastleMassTeleporterInstance.this, (String)"NpcString.THE_DEFENDERS_OF_S1_CASTLE_WILL_BE_TELEPORTED_TO_THE_INNER_CASTLE", (Object[])new Object[]{CastleMassTeleporterInstance.this.getCastle().getName()});
        for (Player player : World.getAroundPlayers((GameObject)CastleMassTeleporterInstance.this, (int)650, (int)50)) {
            player.teleToLocation(Location.findPointToStay((Location)CastleMassTeleporterInstance.this.ag, (int)10, (int)100, (int)player.getGeoIndex()));
        }
        CastleMassTeleporterInstance.this.S = null;
    }
}
