/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.voicecommands.impl;

import java.io.Serializable;
import java.util.Comparator;

private static class AutoFarm.SortTimeInfo
implements Serializable,
Comparator<Integer> {
    private static final long aO = 7691414259610932752L;

    private AutoFarm.SortTimeInfo() {
    }

    @Override
    public int compare(Integer n, Integer n2) {
        return Double.compare(n.intValue(), n2.intValue());
    }
}
