/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.tuple.Pair
 */
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.commons.lang3.tuple.Pair;
import services.TopPvPPKService;

private static abstract class TopPvPPKService.TopRecordHolder {
    private final AtomicReference<Pair<Long, Collection<TopPvPPKService.TopRecord>>> e;
    private final int bGj;
    private final long ez;

    protected TopPvPPKService.TopRecordHolder(int n, long l) {
        this.ez = l;
        this.e = new AtomicReference<Pair>(Pair.of((Object)0L, Collections.emptyList()));
        this.bGj = n;
    }

    protected int getLimit() {
        return this.bGj;
    }

    protected abstract Collection<TopPvPPKService.TopRecord> fetchTopOnlineRecords();

    protected abstract Collection<TopPvPPKService.TopRecord> fetchTopDbRecords();

    protected Collection<TopPvPPKService.TopRecord> fetchTopRecords() {
        Collection<TopPvPPKService.TopRecord> collection = this.fetchTopOnlineRecords();
        Collection<TopPvPPKService.TopRecord> collection2 = this.fetchTopDbRecords();
        ArrayList<TopPvPPKService.TopRecord> arrayList = new ArrayList<TopPvPPKService.TopRecord>(collection.size() + collection2.size());
        for (TopPvPPKService.TopRecord topRecord : collection) {
            arrayList.add(topRecord);
        }
        for (TopPvPPKService.TopRecord topRecord : collection2) {
            if (arrayList.contains(topRecord)) continue;
            arrayList.add(topRecord);
        }
        Collections.sort(arrayList);
        return new ArrayList<TopPvPPKService.TopRecord>(arrayList.subList(0, Math.min(this.bGj, arrayList.size())));
    }

    public Collection<TopPvPPKService.TopRecord> getTopRecords() {
        Pair<Long, Collection<TopPvPPKService.TopRecord>> pair;
        while ((Long)(pair = this.e.get()).getLeft() + this.ez < System.currentTimeMillis()) {
            Collection<TopPvPPKService.TopRecord> collection = this.fetchTopRecords();
            Pair pair2 = Pair.of((Object)System.currentTimeMillis(), collection);
            if (!this.e.compareAndSet(pair, (Pair<Long, Collection<TopPvPPKService.TopRecord>>)pair2)) continue;
            return Collections.unmodifiableCollection((Collection)pair2.getRight());
        }
        return Collections.unmodifiableCollection((Collection)pair.getRight());
    }
}
