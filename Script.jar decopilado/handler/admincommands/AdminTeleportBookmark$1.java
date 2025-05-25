/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.utils.Location
 *  org.apache.commons.lang3.tuple.Pair
 */
package handler.admincommands;

import java.util.Comparator;
import l2.gameserver.utils.Location;
import org.apache.commons.lang3.tuple.Pair;

class AdminTeleportBookmark.1
implements Comparator<Pair<String, Location>> {
    AdminTeleportBookmark.1() {
    }

    @Override
    public int compare(Pair<String, Location> pair, Pair<String, Location> pair2) {
        return ((String)pair.getLeft()).compareTo((String)pair2.getLeft());
    }
}
