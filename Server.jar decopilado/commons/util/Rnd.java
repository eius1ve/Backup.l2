/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.math3.random.MersenneTwister
 *  org.apache.commons.math3.random.RandomGenerator
 */
package l2.commons.util;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomGenerator;

public class Rnd {
    private static final ThreadLocal<RandomGenerator> a = new ThreadLocalGeneratorHolder();
    private static AtomicLong k = new AtomicLong(8682522807148012L);

    private Rnd() {
    }

    private static RandomGenerator a() {
        return a.get();
    }

    public static double get() {
        return Rnd.a().nextDouble();
    }

    public static int get(int n) {
        return Rnd.a().nextInt(n);
    }

    public static long get(long l) {
        return (long)(Rnd.a().nextDouble() * (double)l);
    }

    public static int get(int n, int n2) {
        return n + Rnd.get(n2 - n + 1);
    }

    public static long get(long l, long l2) {
        return l + Rnd.get(l2 - l + 1L);
    }

    public static int nextInt() {
        return Rnd.a().nextInt();
    }

    public static double nextDouble() {
        return Rnd.a().nextDouble();
    }

    public static double nextGaussian() {
        return Rnd.a().nextGaussian();
    }

    public static boolean nextBoolean() {
        return Rnd.a().nextBoolean();
    }

    public static boolean chance(int n) {
        return n >= 1 && (n > 99 || Rnd.a().nextInt(99) + 1 <= n);
    }

    public static boolean chance(double d) {
        return Rnd.a().nextDouble() <= d / 100.0;
    }

    public static <E> E get(E[] EArray) {
        return EArray[Rnd.get(EArray.length)];
    }

    public static int get(int[] nArray) {
        return nArray[Rnd.get(nArray.length)];
    }

    public static <E> E get(List<E> list) {
        return list.get(Rnd.get(list.size()));
    }

    static final class ThreadLocalGeneratorHolder
    extends ThreadLocal<RandomGenerator> {
        ThreadLocalGeneratorHolder() {
        }

        @Override
        public RandomGenerator initialValue() {
            return new MersenneTwister(k.getAndIncrement() + System.nanoTime());
        }
    }
}
