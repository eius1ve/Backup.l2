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
 */
package instances;

import l2.commons.lang.reference.HardReferences;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExShowScreenMessage;

public class GvGInstance.BattleEnd
extends RunnableImpl {
    public void runImpl() throws Exception {
        for (Player player : HardReferences.unwrap(GvGInstance.this.M)) {
            player.sendPacket((IStaticPacket)new ExShowScreenMessage(new CustomMessage("scripts.event.gvg.teleport1min", player, new Object[0]).toString(), 4000, ExShowScreenMessage.ScreenMessageAlign.MIDDLE_CENTER, true));
        }
        GvGInstance.this.end();
    }
}
