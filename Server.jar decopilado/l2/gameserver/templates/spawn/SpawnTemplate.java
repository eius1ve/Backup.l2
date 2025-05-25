/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.templates.spawn;

import java.util.ArrayList;
import java.util.List;
import l2.commons.time.cron.NextTime;
import l2.gameserver.templates.spawn.PeriodOfDay;
import l2.gameserver.templates.spawn.SpawnNpcInfo;
import l2.gameserver.templates.spawn.SpawnRange;

public class SpawnTemplate {
    private final String gA;
    private final String gB;
    private final PeriodOfDay a;
    private final int Hg;
    private final long ed;
    private final long ee;
    private final NextTime b;
    private final List<SpawnNpcInfo> dE = new ArrayList<SpawnNpcInfo>(1);
    private final List<SpawnRange> dF = new ArrayList<SpawnRange>(1);

    public SpawnTemplate(String string, String string2, PeriodOfDay periodOfDay, int n, long l, long l2, NextTime nextTime) {
        this.gA = string;
        this.gB = string2;
        this.a = periodOfDay;
        this.Hg = n;
        this.ed = l;
        this.ee = l2;
        this.b = nextTime;
    }

    public void addSpawnRange(SpawnRange spawnRange) {
        this.dF.add(spawnRange);
    }

    public SpawnRange getSpawnRange(int n) {
        return this.dF.get(n);
    }

    public void addNpc(SpawnNpcInfo spawnNpcInfo) {
        this.dE.add(spawnNpcInfo);
    }

    public SpawnNpcInfo getNpcId(int n) {
        return this.dE.get(n);
    }

    public String getMakerName() {
        return this.gA;
    }

    public int getNpcSize() {
        return this.dE.size();
    }

    public int getSpawnRangeSize() {
        return this.dF.size();
    }

    public int getCount() {
        return this.Hg;
    }

    public long getRespawn() {
        return this.ed;
    }

    public long getRespawnRandom() {
        return this.ee;
    }

    public NextTime getRespawnCron() {
        return this.b;
    }

    public PeriodOfDay getPeriodOfDay() {
        return this.a;
    }

    public String getEventName() {
        return this.gB;
    }
}
