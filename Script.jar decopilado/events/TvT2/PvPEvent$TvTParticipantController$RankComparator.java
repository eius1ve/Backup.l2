/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.tuple.ImmutablePair
 */
package events.TvT2;

import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.lang3.tuple.ImmutablePair;

private static class PvPEvent.TvTParticipantController.RankComparator
implements Comparator<Map.Entry<Integer, ImmutablePair<AtomicInteger, AtomicInteger>>> {
    private PvPEvent.TvTParticipantController.RankComparator() {
    }

    @Override
    public int compare(Map.Entry<Integer, ImmutablePair<AtomicInteger, AtomicInteger>> entry, Map.Entry<Integer, ImmutablePair<AtomicInteger, AtomicInteger>> entry2) {
        try {
            int n;
            if (entry == null && entry2 == null) {
                return 1;
            }
            if (entry == null) {
                return 1;
            }
            if (entry2 == null) {
                return -1;
            }
            int n2 = entry.getKey();
            int n3 = entry2.getKey();
            int n4 = ((AtomicInteger)entry.getValue().getLeft()).get();
            return n4 != (n = ((AtomicInteger)entry2.getValue().getLeft()).get()) ? n - n4 : n3 - n2;
        }
        catch (Exception exception) {
            return 0;
        }
    }
}
