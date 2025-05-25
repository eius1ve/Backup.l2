/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.utils;

import java.util.concurrent.atomic.AtomicLong;
import l2.gameserver.instancemanager.ServerVariables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameStats {
    private static final Logger dG = LoggerFactory.getLogger(GameStats.class);
    private static AtomicLong q = new AtomicLong(0L);
    private static AtomicLong r = new AtomicLong(0L);
    private static AtomicLong s = new AtomicLong(0L);
    private static long ef;
    private static AtomicLong t;
    private static long eg;
    private static AtomicLong u;

    public static void increaseUpdatePlayerBase() {
        q.incrementAndGet();
    }

    public static long getUpdatePlayerBase() {
        return q.get();
    }

    public static void incrementPlayerEnterGame() {
        r.incrementAndGet();
    }

    public static long getPlayerEnterGame() {
        return r.get();
    }

    public static void addTax(long l) {
        long l2 = s.addAndGet(l);
        if (System.currentTimeMillis() - ef < 10000L) {
            return;
        }
        ef = System.currentTimeMillis();
        ServerVariables.set("taxsum", l2);
    }

    public static void addRoulette(long l) {
        long l2 = t.addAndGet(l);
        if (System.currentTimeMillis() - eg < 10000L) {
            return;
        }
        eg = System.currentTimeMillis();
        ServerVariables.set("rouletteSum", l2);
    }

    public static long getTaxSum() {
        return s.get();
    }

    public static long getRouletteSum() {
        return t.get();
    }

    public static void addAdena(long l) {
        u.addAndGet(l);
    }

    public static long getAdena() {
        return u.get();
    }

    static {
        t = new AtomicLong(0L);
        u = new AtomicLong(0L);
        s.set(ServerVariables.getLong("taxsum", 0L));
        t.set(ServerVariables.getLong("rouletteSum", 0L));
    }
}
