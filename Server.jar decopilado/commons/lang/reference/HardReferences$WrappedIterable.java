/*
 * Decompiled with CFR 0.152.
 */
package l2.commons.lang.reference;

import java.util.Iterator;
import l2.commons.lang.reference.HardReference;

private static class HardReferences.WrappedIterable<T>
implements Iterable<T> {
    final Iterable<HardReference<T>> refs;

    HardReferences.WrappedIterable(Iterable<HardReference<T>> iterable) {
        this.refs = iterable;
    }

    @Override
    public Iterator<T> iterator() {
        return new WrappedIterator<T>(this.refs.iterator());
    }

    private static class WrappedIterator<T>
    implements Iterator<T> {
        final Iterator<HardReference<T>> iterator;

        WrappedIterator(Iterator<HardReference<T>> iterator) {
            this.iterator = iterator;
        }

        @Override
        public boolean hasNext() {
            return this.iterator.hasNext();
        }

        @Override
        public T next() {
            return this.iterator.next().get();
        }

        @Override
        public void remove() {
            this.iterator.remove();
        }
    }
}
