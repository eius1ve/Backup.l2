/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.instancemanager.sepulchers.spawn;

import java.util.ArrayList;
import java.util.List;
import l2.commons.util.Rnd;
import l2.gameserver.instancemanager.sepulchers.spawn.SepulcherSpawnHandler;

public class SepulcherSpawnSelector {
    private final List<SepulcherSpawnHandler> aK = new ArrayList<SepulcherSpawnHandler>();

    public SepulcherSpawnSelector() {
    }

    public SepulcherSpawnSelector(SepulcherSpawnHandler sepulcherSpawnHandler) {
        this.aK.add(sepulcherSpawnHandler);
    }

    public SepulcherSpawnHandler get() {
        return Rnd.get(this.aK);
    }

    public List<SepulcherSpawnHandler> getOptions() {
        return this.aK;
    }
}
