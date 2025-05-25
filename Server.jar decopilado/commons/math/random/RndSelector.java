/*
 * Decompiled with CFR 0.152.
 */
package l2.commons.math.random;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import l2.commons.util.Rnd;

public class RndSelector<E> {
    private int ep = 0;
    private final List<RndNode<E>> T;

    public RndSelector() {
        this.T = new ArrayList<RndNode<E>>();
    }

    public RndSelector(int n) {
        this.T = new ArrayList<RndNode<E>>(n);
    }

    public void add(E e, int n) {
        if (e == null || n <= 0) {
            return;
        }
        this.ep += n;
        this.T.add(new RndNode<E>(e, n));
    }

    public E chance(int n) {
        if (n <= 0) {
            return null;
        }
        Collections.sort(this.T);
        int n2 = Rnd.get(n);
        int n3 = 0;
        for (int i = 0; i < this.T.size(); ++i) {
            if ((n3 += this.T.get((int)i).weight) <= n2) continue;
            return (E)this.T.get((int)i).b;
        }
        return null;
    }

    public E chance() {
        return this.chance(100);
    }

    public E select() {
        return this.chance(this.ep);
    }

    public void clear() {
        this.ep = 0;
        this.T.clear();
    }

    private class RndNode<T>
    implements Comparable<RndNode<T>> {
        private final T b;
        private final int weight;

        public RndNode(T t, int n) {
            this.b = t;
            this.weight = n;
        }

        @Override
        public int compareTo(RndNode<T> rndNode) {
            return this.weight - this.weight;
        }
    }
}
