/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 */
package services;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;

class Buffer.BuffTemplate.1
extends RunnableImpl {
    final /* synthetic */ Player val$player;

    Buffer.BuffTemplate.1(Player player) {
        this.val$player = player;
    }

    public void runImpl() throws Exception {
        Creature creature = BuffTemplate.this.a((Creature)this.val$player);
        if (creature == null) {
            this.val$player.sendPacket((IStaticPacket)SystemMsg.THAT_IS_AN_INCORRECT_TARGET);
            return;
        }
        if (!BuffTemplate.this.z(this.val$player)) {
            this.val$player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_REQUIRED_ITEMS);
            return;
        }
        BuffTemplate.this.k(creature);
    }
}
