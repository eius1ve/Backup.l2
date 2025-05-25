/*
 * Decompiled with CFR 0.152.
 */
package l2.commons.threading;

import java.util.Comparator;
import l2.commons.threading.RunnableStatsManager;

class RunnableStatsManager.1
implements Comparator<RunnableStatsManager.ClassStat> {
    RunnableStatsManager.1() {
    }

    @Override
    public int compare(RunnableStatsManager.ClassStat classStat, RunnableStatsManager.ClassStat classStat2) {
        if (classStat.ax < classStat2.ax) {
            return 1;
        }
        if (classStat.ax == classStat2.ax) {
            return 0;
        }
        return -1;
    }
}
