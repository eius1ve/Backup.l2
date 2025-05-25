/*
 * Decompiled with CFR 0.152.
 */
package events.TvT2;

import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

private static class PvPEvent.DMParticipantController.RankComparator
implements Comparator<Map.Entry<Integer, AtomicInteger>> {
    private PvPEvent.DMParticipantController.RankComparator() {
    }

    @Override
    public int compare(Map.Entry<Integer, AtomicInteger> entry, Map.Entry<Integer, AtomicInteger> entry2) {
        try {
            int n;
            if (entry == null && entry2 == null) {
                return 0;
            }
            if (entry == null) {
                return 1;
            }
            if (entry2 == null) {
                return -1;
            }
            int n2 = entry.getKey();
            int n3 = entry2.getKey();
            int n4 = entry.getValue().get();
            return n4 != (n = entry2.getValue().get()) ? n - n4 : n3 - n2;
        }
        catch (Exception exception) {
            return 0;
        }
    }
}
