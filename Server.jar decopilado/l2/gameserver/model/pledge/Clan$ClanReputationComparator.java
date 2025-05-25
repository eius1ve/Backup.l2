/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.pledge;

import java.util.Comparator;
import l2.gameserver.model.pledge.Clan;

private static class Clan.ClanReputationComparator
implements Comparator<Clan> {
    private Clan.ClanReputationComparator() {
    }

    @Override
    public int compare(Clan clan, Clan clan2) {
        if (clan == null || clan2 == null) {
            return 0;
        }
        return clan2.getReputationScore() - clan.getReputationScore();
    }
}
