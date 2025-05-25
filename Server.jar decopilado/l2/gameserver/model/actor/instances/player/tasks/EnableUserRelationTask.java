/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.actor.instances.player.tasks;

import l2.commons.lang.reference.HardReference;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.events.impl.SiegeEvent;
import l2.gameserver.network.l2.s2c.UserInfoType;

public class EnableUserRelationTask
extends RunnableImpl {
    private HardReference<Player> a;
    private SiegeEvent<?, ?> _siegeEvent;

    public EnableUserRelationTask(Player player, SiegeEvent<?, ?> siegeEvent) {
        this._siegeEvent = siegeEvent;
        this.a = player.getRef();
    }

    @Override
    public void runImpl() throws Exception {
        Player player = this.a.get();
        if (player == null) {
            return;
        }
        player.stopEnableUserRelationTask();
        player.broadcastUserInfo(true, UserInfoType.RELATION);
    }
}
