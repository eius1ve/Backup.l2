/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.ArrayUtils
 *  org.napile.primitive.maps.IntObjectMap
 *  org.napile.primitive.maps.impl.CHashIntObjectMap
 */
package l2.gameserver.instancemanager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import l2.gameserver.instancemanager.MapRegionManager;
import l2.gameserver.model.Player;
import l2.gameserver.model.matching.MatchingRoom;
import l2.gameserver.templates.mapregion.RestartArea;
import l2.gameserver.templates.mapregion.RestartPoint;
import org.apache.commons.lang3.ArrayUtils;
import org.napile.primitive.maps.IntObjectMap;
import org.napile.primitive.maps.impl.CHashIntObjectMap;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class MatchingRoomManager {
    private static final MatchingRoomManager a = new MatchingRoomManager();
    private RoomsHolder[] a = new RoomsHolder[2];
    private Set<Player> f = new CopyOnWriteArraySet<Player>();

    public static MatchingRoomManager getInstance() {
        return a;
    }

    public MatchingRoomManager() {
        this.a[MatchingRoom.PARTY_MATCHING] = new RoomsHolder();
        this.a[MatchingRoom.CC_MATCHING] = new RoomsHolder();
    }

    public void addToWaitingList(Player player) {
        this.f.add(player);
    }

    public void removeFromWaitingList(Player player) {
        this.f.remove(player);
    }

    public List<Player> getWaitingList(int n, int n2, int[] nArray) {
        ArrayList<Player> arrayList = new ArrayList<Player>();
        for (Player player : this.f) {
            if (player.getLevel() < n || player.getLevel() > n2 || nArray.length != 0 && !ArrayUtils.contains((int[])nArray, (int)player.getClassId().getId())) continue;
            arrayList.add(player);
        }
        return arrayList;
    }

    public List<MatchingRoom> getMatchingRooms(int n, int n2, boolean bl, Player player) {
        ArrayList<MatchingRoom> arrayList = new ArrayList<MatchingRoom>();
        for (MatchingRoom matchingRoom : this.a[n].h.values()) {
            if (n2 > 0 && matchingRoom.getLocationId() != n2 || n2 == -2 && matchingRoom.getLocationId() != MatchingRoomManager.getInstance().getLocation(player) || !bl && (matchingRoom.getMinLevel() > player.getLevel() || matchingRoom.getMaxLevel() < player.getLevel())) continue;
            arrayList.add(matchingRoom);
        }
        return arrayList;
    }

    public int addMatchingRoom(MatchingRoom matchingRoom) {
        return this.a[matchingRoom.getType()].addRoom(matchingRoom);
    }

    public void removeMatchingRoom(MatchingRoom matchingRoom) {
        this.a[matchingRoom.getType()].h.remove(matchingRoom.getId());
    }

    public MatchingRoom getMatchingRoom(int n, int n2) {
        return (MatchingRoom)this.a[n].h.get(n2);
    }

    public int getLocation(Player player) {
        if (player == null) {
            return 0;
        }
        RestartArea restartArea = MapRegionManager.getInstance().getRegionData(RestartArea.class, player);
        if (restartArea != null) {
            RestartPoint restartPoint = restartArea.getRestartPoint().get((Object)player.getRace());
            return restartPoint.getBbs();
        }
        return 0;
    }

    private class RoomsHolder {
        private int _id = 1;
        private IntObjectMap<MatchingRoom> h = new CHashIntObjectMap();

        private RoomsHolder() {
        }

        public int addRoom(MatchingRoom matchingRoom) {
            int n = this._id++;
            this.h.put(n, (Object)matchingRoom);
            return n;
        }
    }
}
