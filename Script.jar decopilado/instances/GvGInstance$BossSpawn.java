/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.lang.reference.HardReferences
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.model.Player
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage$ScreenMessageAlign
 *  l2.gameserver.utils.Location
 */
package instances;

import l2.commons.lang.reference.HardReferences;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExShowScreenMessage;
import l2.gameserver.utils.Location;

public class GvGInstance.BossSpawn
extends RunnableImpl {
    public void runImpl() throws Exception {
        for (Player player : HardReferences.unwrap(GvGInstance.this.M)) {
            player.sendPacket((IStaticPacket)new ExShowScreenMessage(new CustomMessage("scripts.event.gvg.geraldguard", player, new Object[0]).toString(), 5000, ExShowScreenMessage.ScreenMessageAlign.MIDDLE_CENTER, true));
        }
        GvGInstance.this.addSpawnWithoutRespawn(25655, new Location(79128, 91000, -2880, 4836), 0);
        GvGInstance.this.openDoor(22200004);
        GvGInstance.this.openDoor(22200005);
        GvGInstance.this.openDoor(22200006);
        GvGInstance.this.openDoor(22200007);
    }
}
