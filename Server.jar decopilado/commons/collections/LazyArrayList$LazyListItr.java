/*
 * Decompiled with CFR 0.152.
 */
package l2.commons.collections;

import java.util.ListIterator;
import l2.commons.collections.LazyArrayList;

private class LazyArrayList.LazyListItr
extends LazyArrayList.LazyItr
implements ListIterator<E> {
    LazyArrayList.LazyListItr(int n) {
        super(LazyArrayList.this);
        this.cursor = n;
    }

    @Override
    public boolean hasPrevious() {
        return this.cursor > 0;
    }

    @Override
    public E previous() {
        int n = this.cursor - 1;
        Object e = LazyArrayList.this.get(n);
        this.lastRet = this.cursor = n;
        return e;
    }

    @Override
    public int nextIndex() {
        return this.cursor;
    }

    @Override
    public int previousIndex() {
        return this.cursor - 1;
    }

    @Override
    public void set(E e) {
        if (this.lastRet == -1) {
            throw new IllegalStateException();
        }
        LazyArrayList.this.set(this.lastRet, e);
    }

    @Override
    public void add(E e) {
        LazyArrayList.this.add(this.cursor++, e);
        this.lastRet = -1;
    }
}
