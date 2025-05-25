/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Player
 */
package services.community;

import java.util.Comparator;
import l2.gameserver.model.Player;

private static class RegionCommunity.PlayersComparator<T>
implements Comparator<T> {
    private RegionCommunity.PlayersComparator() {
    }

    @Override
    public int compare(Object object, Object object2) {
        if (object instanceof Player && object2 instanceof Player) {
            Player player = (Player)object;
            Player player2 = (Player)object2;
            return player.getName().compareTo(player2.getName());
        }
        return 0;
    }
}
