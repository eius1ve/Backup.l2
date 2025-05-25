/*
 * Decompiled with CFR 0.152.
 */
package l2.commons.collections;

import java.util.Iterator;
import java.util.List;
import l2.commons.collections.EmptyIterator;

public class JoinedIterator<E>
implements Iterator<E> {
    private Iterator<E>[] a;
    private int ej;
    private Iterator<E> b;
    private Iterator<E> c;

    public JoinedIterator(List<Iterator<E>> list) {
        this(list.toArray(new Iterator[list.size()]));
    }

    public JoinedIterator(Iterator ... iteratorArray) {
        if (iteratorArray == null) {
            throw new NullPointerException("Unexpected NULL iterators argument");
        }
        this.a = iteratorArray;
    }

    @Override
    public boolean hasNext() {
        this.updateCurrentIterator();
        return this.b.hasNext();
    }

    @Override
    public E next() {
        this.updateCurrentIterator();
        return this.b.next();
    }

    @Override
    public void remove() {
        this.updateCurrentIterator();
        this.c.remove();
    }

    protected void updateCurrentIterator() {
        if (this.b == null) {
            this.b = this.a.length == 0 ? EmptyIterator.getInstance() : this.a[0];
            this.c = this.b;
        }
        while (!this.b.hasNext() && this.ej < this.a.length - 1) {
            ++this.ej;
            this.b = this.a[this.ej];
        }
    }
}
