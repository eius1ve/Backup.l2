/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.listener.actor.player.impl;

import l2.commons.lang.reference.HardReference;
import l2.gameserver.listener.actor.player.OnAnswerListener;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.utils.Location;

public class SummonAnswerListener
implements OnAnswerListener {
    private HardReference<Player> a;
    private Location s;
    private long aT;
    private final long aU;

    public SummonAnswerListener(Player player, Location location, long l, int n) {
        this.a = player.getRef();
        this.s = location;
        this.aT = l;
        this.aU = n > 0 ? System.currentTimeMillis() + (long)n : Long.MAX_VALUE;
    }

    @Override
    public void sayYes() {
        Player player = this.a.get();
        if (player == null) {
            return;
        }
        if (System.currentTimeMillis() > this.aU) {
            return;
        }
        player.abortAttack(true, true);
        player.abortCast(true, true);
        player.stopMove();
        if (this.aT > 0L) {
            if (player.getInventory().destroyItemByItemId(8615, this.aT)) {
                player.sendPacket((IStaticPacket)SystemMessage.removeItems(8615, this.aT));
                player.teleToLocation(this.s);
            } else {
                player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_ITEM_COUNT);
            }
        } else {
            player.teleToLocation(this.s);
        }
    }

    @Override
    public void sayNo() {
    }
}
