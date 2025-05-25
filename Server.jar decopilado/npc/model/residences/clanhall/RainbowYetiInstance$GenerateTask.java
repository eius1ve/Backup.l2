/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.commons.util.Rnd
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.World
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage$ScreenMessageAlign
 */
package npc.model.residences.clanhall;

import java.util.List;
import l2.commons.threading.RunnableImpl;
import l2.commons.util.Rnd;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.model.World;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExShowScreenMessage;
import npc.model.residences.clanhall.RainbowYetiInstance;

private class RainbowYetiInstance.GenerateTask
extends RunnableImpl {
    private RainbowYetiInstance.GenerateTask() {
    }

    public void runImpl() throws Exception {
        RainbowYetiInstance.this.IJ = Rnd.get((int)a.length);
        RainbowYetiInstance.Word word = a[RainbowYetiInstance.this.IJ];
        List list = World.getAroundPlayers((GameObject)RainbowYetiInstance.this, (int)750, (int)100);
        ExShowScreenMessage exShowScreenMessage = new ExShowScreenMessage(word.getName(), 5000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true);
        for (Player player : list) {
            player.sendPacket((IStaticPacket)exShowScreenMessage);
        }
    }
}
