/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 */
package l2.gameserver.model;

import l2.commons.lang.reference.HardReference;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.Reflection;
import l2.gameserver.utils.Location;
import org.apache.commons.lang3.StringUtils;

public static class GameObjectTasks.UnJailTask
extends RunnableImpl {
    private final HardReference<Player> N;

    public GameObjectTasks.UnJailTask(Player player) {
        this.N = player.getRef();
    }

    @Override
    public void runImpl() {
        Player player = this.N.get();
        if (player == null) {
            return;
        }
        String string = player.getVar("jailed");
        if (StringUtils.isBlank((CharSequence)string)) {
            player.teleToClosestTown();
            return;
        }
        Location location = Location.parseLoc(string.substring(string.indexOf(59) + 1));
        Reflection reflection = null;
        if (location.getH() != 0) {
            reflection = ReflectionManager.getInstance().get(location.getH());
        }
        player.standUp();
        if (reflection != null) {
            if (reflection.isCollapseStarted()) {
                player.teleToClosestTown();
            } else {
                player.teleToLocation(location.getX(), location.getY(), location.getZ(), reflection);
            }
        } else {
            player.teleToLocation(location.getX(), location.getY(), location.getZ(), ReflectionManager.DEFAULT);
        }
        player.unblock();
        player.unsetVar("jailed");
    }
}
