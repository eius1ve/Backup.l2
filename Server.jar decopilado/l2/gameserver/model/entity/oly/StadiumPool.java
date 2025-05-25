/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.model.entity.oly;

import java.util.ArrayDeque;
import l2.gameserver.Config;
import l2.gameserver.model.entity.oly.Stadium;
import l2.gameserver.utils.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class StadiumPool {
    private static final Logger cb = LoggerFactory.getLogger(StadiumPool.class);
    private static StadiumPool a;
    private static final StadiumTemplate[] a;
    private static final StadiumTemplate[] b;
    private final int lO;
    private final Stadium[] a;
    private final ArrayDeque<Stadium> b;
    private final StadiumTemplate[] c;

    public static final StadiumPool getInstance() {
        if (a == null) {
            a = new StadiumPool();
        }
        return a;
    }

    private StadiumPool() {
        if (Config.OLYMPIAD_NEW_STADIUMS) {
            this.lO = 160;
            this.c = b;
        } else {
            this.lO = 22;
            this.c = a;
        }
        this.a = new Stadium[this.lO];
        this.b = new ArrayDeque();
    }

    public int getReflectionsCount() {
        return this.lO;
    }

    public Stadium[] getAllStadiums() {
        return this.a;
    }

    public void AllocateStadiums() {
        int n = 0;
        for (int i = 0; i < this.lO / this.c.length; ++i) {
            for (StadiumTemplate stadiumTemplate : this.c) {
                Stadium stadium;
                this.a[n] = stadium = new Stadium(n, stadiumTemplate.zid, stadiumTemplate.oloc);
                this.b.addLast(stadium);
                ++n;
            }
        }
        cb.info("OlyStadiumPool: allocated " + n + " stadiums.");
    }

    public void FreeStadiums() {
        for (int i = 0; i < this.a.length; ++i) {
            if (this.a[i] == null) continue;
            this.a[i].collapse();
            this.a[i] = null;
        }
        this.b.clear();
        cb.info("OlyStadiumPool: stadiums cleared.");
    }

    public boolean isStadiumAvailable() {
        return this.b.size() > 0;
    }

    public synchronized Stadium pollStadium() {
        Stadium stadium = (Stadium)this.b.pollFirst();
        if (!stadium.isFree()) {
            cb.warn("Poll used stadium");
            Thread.dumpStack();
            stadium = (Stadium)this.b.pollFirst();
        }
        stadium.setFree(false);
        return stadium;
    }

    public synchronized void putStadium(Stadium stadium) {
        if (stadium.isFree()) {
            cb.warn("Put free stadium");
            Thread.dumpStack();
        }
        stadium.clear();
        stadium.setFree(true);
        this.b.addFirst(stadium);
    }

    public Stadium getStadium(int n) {
        return this.a[n];
    }

    static {
        a = new StadiumTemplate[]{new StadiumTemplate(147, new Location(-20814, -21189, -3030))};
        b = new StadiumTemplate[]{new StadiumTemplate(148, new Location(-88136, -252616, -3312)), new StadiumTemplate(149, new Location(-75464, -252472, -7712)), new StadiumTemplate(150, new Location(-88056, -239352, -8448)), new StadiumTemplate(151, new Location(-75448, -238744, -8192))};
    }

    private static class StadiumTemplate {
        public Location[] plocs;
        public Location[] blocs;
        public Location oloc;
        public int zid;

        public StadiumTemplate(int n, Location location) {
            this.oloc = location;
            this.zid = n;
        }
    }
}
