/*
 * Decompiled with CFR 0.152.
 */
package l2.commons.threading;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class RunnableStatsManager {
    private static final RunnableStatsManager a = new RunnableStatsManager();
    private final Map<Class<?>, ClassStat> y = new HashMap();
    private final Lock h = new ReentrantLock();

    public static final RunnableStatsManager getInstance() {
        return a;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void handleStats(Class<?> clazz, long l) {
        try {
            this.h.lock();
            ClassStat classStat = this.y.get(clazz);
            if (classStat == null) {
                classStat = new ClassStat(clazz);
            }
            ++classStat.au;
            classStat.av += l;
            if (classStat.aw > l) {
                classStat.aw = l;
            }
            if (classStat.ax < l) {
                classStat.ax = l;
            }
        }
        finally {
            this.h.unlock();
        }
    }

    private List<ClassStat> i() {
        List<ClassStat> list = Collections.emptyList();
        try {
            this.h.lock();
            list = Arrays.asList(this.y.values().toArray(new ClassStat[this.y.size()]));
        }
        finally {
            this.h.unlock();
        }
        Collections.sort(list, new Comparator<ClassStat>(){

            @Override
            public int compare(ClassStat classStat, ClassStat classStat2) {
                if (classStat.ax < classStat2.ax) {
                    return 1;
                }
                if (classStat.ax == classStat2.ax) {
                    return 0;
                }
                return -1;
            }
        });
        return list;
    }

    public CharSequence getStats() {
        StringBuilder stringBuilder = new StringBuilder();
        List<ClassStat> list = this.i();
        for (ClassStat classStat : list) {
            stringBuilder.append(classStat.a.getName()).append(":\n");
            stringBuilder.append("\tRun: ............ ").append(classStat.au).append("\n");
            stringBuilder.append("\tTime: ........... ").append(classStat.av).append("\n");
            stringBuilder.append("\tMin: ............ ").append(classStat.aw).append("\n");
            stringBuilder.append("\tMax: ............ ").append(classStat.ax).append("\n");
            stringBuilder.append("\tAverage: ........ ").append(classStat.av / classStat.au).append("\n");
        }
        return stringBuilder;
    }

    private class ClassStat {
        private final Class<?> a;
        private long au = 0L;
        private long av = 0L;
        private long aw = Long.MAX_VALUE;
        private long ax = Long.MIN_VALUE;

        private ClassStat(Class<?> clazz) {
            this.a = clazz;
            RunnableStatsManager.this.y.put(clazz, this);
        }
    }
}
