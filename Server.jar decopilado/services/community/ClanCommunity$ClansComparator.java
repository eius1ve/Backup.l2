/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.pledge.Clan
 */
package services.community;

import java.util.Comparator;
import l2.gameserver.model.pledge.Clan;

private static class ClanCommunity.ClansComparator<T>
implements Comparator<T> {
    private ClanCommunity.ClansComparator() {
    }

    @Override
    public int compare(Object object, Object object2) {
        if (object instanceof Clan && object2 instanceof Clan) {
            Clan clan = (Clan)object;
            Clan clan2 = (Clan)object2;
            return clan.getName().compareTo(clan2.getName());
        }
        return 0;
    }
}
