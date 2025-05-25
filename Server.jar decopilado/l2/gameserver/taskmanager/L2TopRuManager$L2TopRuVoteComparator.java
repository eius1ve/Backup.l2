/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.taskmanager;

import java.util.Comparator;
import l2.gameserver.taskmanager.L2TopRuManager;

private final class L2TopRuManager.L2TopRuVoteComparator<T>
implements Comparator<L2TopRuManager.L2TopRuVote> {
    private L2TopRuManager.L2TopRuVoteComparator() {
    }

    @Override
    public int compare(L2TopRuManager.L2TopRuVote l2TopRuVote, L2TopRuManager.L2TopRuVote l2TopRuVote2) {
        if (l2TopRuVote.datetime == l2TopRuVote2.datetime) {
            return 0;
        }
        if (l2TopRuVote.datetime < l2TopRuVote2.datetime) {
            return Integer.MIN_VALUE;
        }
        if (l2TopRuVote.datetime > l2TopRuVote2.datetime) {
            return Integer.MAX_VALUE;
        }
        return -1;
    }
}
