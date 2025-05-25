/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.World
 *  l2.gameserver.model.entity.events.impl.ClanHallMiniGameEvent
 *  l2.gameserver.model.entity.residence.ClanHall
 *  l2.gameserver.model.instances.NpcInstance
 */
package npc.model.residences.clanhall;

import java.util.List;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.model.World;
import l2.gameserver.model.entity.events.impl.ClanHallMiniGameEvent;
import l2.gameserver.model.entity.residence.ClanHall;
import l2.gameserver.model.instances.NpcInstance;

class RainbowGourdInstance.1
extends RunnableImpl {
    final /* synthetic */ NpcInstance val$npc;
    final /* synthetic */ ClanHallMiniGameEvent val$miniGameEvent;

    RainbowGourdInstance.1(NpcInstance npcInstance, ClanHallMiniGameEvent clanHallMiniGameEvent) {
        this.val$npc = npcInstance;
        this.val$miniGameEvent = clanHallMiniGameEvent;
    }

    public void runImpl() throws Exception {
        List list = World.getAroundPlayers((GameObject)this.val$npc, (int)750, (int)100);
        this.val$npc.deleteMe();
        for (Player player : list) {
            player.teleToLocation(((ClanHall)this.val$miniGameEvent.getResidence()).getOwnerRestartPoint());
        }
        this.val$miniGameEvent.processStep(RainbowGourdInstance.this.a.getClan());
    }
}
