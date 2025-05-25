/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.napile.primitive.maps.IntObjectMap
 *  org.napile.primitive.maps.impl.CHashIntObjectMap
 */
package l2.gameserver.instancemanager;

import l2.gameserver.model.matching.MatchingRoom;
import org.napile.primitive.maps.IntObjectMap;
import org.napile.primitive.maps.impl.CHashIntObjectMap;

private class MatchingRoomManager.RoomsHolder {
    private int _id = 1;
    private IntObjectMap<MatchingRoom> h = new CHashIntObjectMap();

    private MatchingRoomManager.RoomsHolder() {
    }

    public int addRoom(MatchingRoom matchingRoom) {
        int n = this._id++;
        this.h.put(n, (Object)matchingRoom);
        return n;
    }
}
