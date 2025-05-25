/*
 * Decompiled with CFR 0.152.
 */
package l2.commons.lang.reference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import l2.commons.lang.reference.AbstractHardReference;
import l2.commons.lang.reference.HardReference;

public class HardReferences {
    private static HardReference<?> j = new EmptyReferencedHolder((Object)null);

    private HardReferences() {
    }

    public static <T> HardReference<T> emptyRef() {
        return j;
    }

    public static <T> Collection<T> unwrap(Collection<HardReference<T>> collection) {
        ArrayList<T> arrayList = new ArrayList<T>(collection.size());
        for (HardReference<T> hardReference : collection) {
            T t = hardReference.get();
            if (t == null) continue;
            arrayList.add(t);
        }
        return arrayList;
    }

    public static <T> Iterable<T> iterate(Iterable<HardReference<T>> iterable) {
        return new WrappedIterable<T>(iterable);
    }

    private static class WrappedIterable<T>
    implements Iterable<T> {
        final Iterable<HardReference<T>> refs;

        WrappedIterable(Iterable<HardReference<T>> iterable) {
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

    private static class EmptyReferencedHolder
    extends AbstractHardReference<Object> {
        public EmptyReferencedHolder(Object object) {
            super(object);
        }
    }
}
