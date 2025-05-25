/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.math3.random.MersenneTwister
 *  org.apache.commons.math3.random.RandomGenerator
 */
package l2.commons.util;

import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomGenerator;

static final class Rnd.ThreadLocalGeneratorHolder
extends ThreadLocal<RandomGenerator> {
    Rnd.ThreadLocalGeneratorHolder() {
    }

    @Override
    public RandomGenerator initialValue() {
        return new MersenneTwister(k.getAndIncrement() + System.nanoTime());
    }
}
