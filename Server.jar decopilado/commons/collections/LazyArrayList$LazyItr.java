/*
 * Decompiled with CFR 0.152.
 */
package l2.commons.collections;

import java.util.Iterator;

private class LazyArrayList.LazyItr
implements Iterator<E> {
    int cursor = 0;
    int lastRet = -1;

    private LazyArrayList.LazyItr() {
    }

    @Override
    public boolean hasNext() {
        return this.cursor < LazyArrayList.this.size();
    }

    @Override
    public E next() {
        Object e = LazyArrayList.this.get(this.cursor);
        this.lastRet = this.cursor++;
        return e;
    }

    @Override
    public void remove() {
        if (this.lastRet == -1) {
            throw new IllegalStateException();
        }
        LazyArrayList.this.remove(this.lastRet);
        if (this.lastRet < this.cursor) {
            --this.cursor;
        }
        this.lastRet = -1;
    }
}
