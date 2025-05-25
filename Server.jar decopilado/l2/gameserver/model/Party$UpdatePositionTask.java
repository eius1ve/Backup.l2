/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import l2.commons.collections.LazyArrayList;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.PartyMemberPosition;
import l2.gameserver.utils.Location;

private class Party.UpdatePositionTask
extends RunnableImpl {
    private Party.UpdatePositionTask() {
    }

    @Override
    public void runImpl() throws Exception {
        Object object;
        LazyArrayList<Player> lazyArrayList = LazyArrayList.newInstance();
        for (Player player : Party.this.aS) {
            object = player.getLastPartyPosition();
            if (object != null && !(player.getDistance((Location)object) > 256.0)) continue;
            player.setLastPartyPosition(player.getLoc());
            lazyArrayList.add(player);
        }
        if (!lazyArrayList.isEmpty()) {
            for (Player player : Party.this.aS) {
                object = new PartyMemberPosition();
                for (Player player2 : lazyArrayList) {
                    if (player2 == player) continue;
                    ((PartyMemberPosition)object).add(player2);
                }
                if (((PartyMemberPosition)object).size() <= 0) continue;
                player.sendPacket((IStaticPacket)object);
            }
        }
        LazyArrayList.recycle(lazyArrayList);
    }
}
