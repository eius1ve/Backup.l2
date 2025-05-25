/*
 * Decompiled with CFR 0.152.
 */
package l2.commons.math.random;

private class RndSelector.RndNode<T>
implements Comparable<RndSelector.RndNode<T>> {
    private final T b;
    private final int weight;

    public RndSelector.RndNode(T t, int n) {
        this.b = t;
        this.weight = n;
    }

    @Override
    public int compareTo(RndSelector.RndNode<T> rndNode) {
        return this.weight - this.weight;
    }
}
