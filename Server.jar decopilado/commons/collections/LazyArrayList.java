/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.pool.ObjectPool
 *  org.apache.commons.pool.PoolableObjectFactory
 *  org.apache.commons.pool.impl.GenericObjectPool
 */
package l2.commons.collections;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;

public class LazyArrayList<E>
implements Serializable,
Cloneable,
List<E>,
RandomAccess {
    private static final long am = 8683452581122892189L;
    private static final int ek = Integer.parseInt(System.getProperty("lazyarraylist.poolsize", "-1"));
    private static final ObjectPool a = new GenericObjectPool((PoolableObjectFactory)new PoolableLazyArrayListFactory(), ek, 2, 0L, -1);
    private static final int el = 8;
    private static final int em = 1024;
    protected transient Object[] elementData;
    protected transient int size = 0;
    protected transient int capacity = 8;

    public static <E> LazyArrayList<E> newInstance() {
        try {
            return (LazyArrayList)a.borrowObject();
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return new LazyArrayList<E>();
        }
    }

    public static <E> void recycle(LazyArrayList<E> lazyArrayList) {
        try {
            a.returnObject(lazyArrayList);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public LazyArrayList(int n) {
        if (n < 1024) {
            while (this.capacity < n) {
                this.capacity <<= 1;
            }
        } else {
            this.capacity = n;
        }
    }

    public LazyArrayList() {
        this(8);
    }

    @Override
    public boolean add(E e) {
        this.ensureCapacity(this.size + 1);
        this.elementData[this.size++] = e;
        return true;
    }

    @Override
    public E set(int n, E e) {
        Object object = null;
        if (n >= 0 && n < this.size) {
            object = this.elementData[n];
            this.elementData[n] = e;
        }
        return (E)object;
    }

    @Override
    public void add(int n, E e) {
        if (n >= 0 && n < this.size) {
            this.ensureCapacity(this.size + 1);
            System.arraycopy(this.elementData, n, this.elementData, n + 1, this.size - n);
            this.elementData[n] = e;
            ++this.size;
        }
    }

    @Override
    public boolean addAll(int n, Collection<? extends E> collection) {
        if (collection == null || collection.isEmpty()) {
            return false;
        }
        if (n >= 0 && n < this.size) {
            Object[] objectArray = collection.toArray();
            int n2 = objectArray.length;
            this.ensureCapacity(this.size + n2);
            int n3 = this.size - n;
            if (n3 > 0) {
                System.arraycopy(this.elementData, n, this.elementData, n + n2, n3);
            }
            System.arraycopy(objectArray, 0, this.elementData, n, n2);
            this.size += n2;
            return true;
        }
        return false;
    }

    protected void ensureCapacity(int n) {
        if (n > this.capacity) {
            if (n < 1024) {
                while (this.capacity < n) {
                    this.capacity <<= 1;
                }
            } else {
                while (this.capacity < n) {
                    this.capacity = this.capacity * 3 / 2;
                }
            }
            Object[] objectArray = new Object[this.capacity];
            if (this.elementData != null) {
                System.arraycopy(this.elementData, 0, objectArray, 0, this.size);
            }
            this.elementData = objectArray;
        } else if (this.elementData == null) {
            this.elementData = new Object[this.capacity];
        }
    }

    @Override
    public E remove(int n) {
        Object object = null;
        if (n >= 0 && n < this.size) {
            --this.size;
            object = this.elementData[n];
            this.elementData[n] = this.elementData[this.size];
            this.elementData[this.size] = null;
            this.trim();
        }
        return (E)object;
    }

    @Override
    public boolean remove(Object object) {
        if (this.size == 0) {
            return false;
        }
        int n = -1;
        for (int i = 0; i < this.size; ++i) {
            if (this.elementData[i] != object) continue;
            n = i;
            break;
        }
        if (n == -1) {
            return false;
        }
        --this.size;
        this.elementData[n] = this.elementData[this.size];
        this.elementData[this.size] = null;
        this.trim();
        return true;
    }

    @Override
    public boolean contains(Object object) {
        if (this.size == 0) {
            return false;
        }
        for (int i = 0; i < this.size; ++i) {
            if (this.elementData[i] != object) continue;
            return true;
        }
        return false;
    }

    @Override
    public int indexOf(Object object) {
        if (this.size == 0) {
            return -1;
        }
        int n = -1;
        for (int i = 0; i < this.size; ++i) {
            if (this.elementData[i] != object) continue;
            n = i;
            break;
        }
        return n;
    }

    @Override
    public int lastIndexOf(Object object) {
        if (this.size == 0) {
            return -1;
        }
        int n = -1;
        for (int i = 0; i < this.size; ++i) {
            if (this.elementData[i] != object) continue;
            n = i;
        }
        return n;
    }

    protected void trim() {
    }

    @Override
    public E get(int n) {
        if (this.size > 0 && n >= 0 && n < this.size) {
            return (E)this.elementData[n];
        }
        return null;
    }

    public Object clone() {
        LazyArrayList<E> lazyArrayList = new LazyArrayList<E>();
        if (this.size > 0) {
            lazyArrayList.capacity = this.capacity;
            lazyArrayList.elementData = new Object[this.elementData.length];
            System.arraycopy(this.elementData, 0, lazyArrayList.elementData, 0, this.size);
        }
        return lazyArrayList;
    }

    @Override
    public void clear() {
        if (this.size == 0) {
            return;
        }
        for (int i = 0; i < this.size; ++i) {
            this.elementData[i] = null;
        }
        this.size = 0;
        this.trim();
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    public int capacity() {
        return this.capacity;
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        if (collection == null || collection.isEmpty()) {
            return false;
        }
        Object[] objectArray = collection.toArray();
        int n = objectArray.length;
        this.ensureCapacity(this.size + n);
        System.arraycopy(objectArray, 0, this.elementData, this.size, n);
        this.size += n;
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        if (collection == null) {
            return false;
        }
        if (collection.isEmpty()) {
            return true;
        }
        Iterator<?> iterator = collection.iterator();
        while (iterator.hasNext()) {
            if (this.contains(iterator.next())) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        if (collection == null) {
            return false;
        }
        boolean bl = false;
        Iterator<E> iterator = this.iterator();
        while (iterator.hasNext()) {
            if (collection.contains(iterator.next())) continue;
            iterator.remove();
            bl = true;
        }
        return bl;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        if (collection == null || collection.isEmpty()) {
            return false;
        }
        boolean bl = false;
        Iterator<E> iterator = this.iterator();
        while (iterator.hasNext()) {
            if (!collection.contains(iterator.next())) continue;
            iterator.remove();
            bl = true;
        }
        return bl;
    }

    @Override
    public Object[] toArray() {
        Object[] objectArray = new Object[this.size];
        if (this.size > 0) {
            System.arraycopy(this.elementData, 0, objectArray, 0, this.size);
        }
        return objectArray;
    }

    @Override
    public <T> T[] toArray(T[] TArray) {
        T[] TArray2;
        Object[] objectArray = TArray2 = TArray.length >= this.size ? TArray : (Object[])Array.newInstance(TArray.getClass().getComponentType(), this.size);
        if (this.size > 0) {
            System.arraycopy(this.elementData, 0, TArray2, 0, this.size);
        }
        if (TArray2.length > this.size) {
            TArray2[this.size] = null;
        }
        return TArray2;
    }

    @Override
    public Iterator<E> iterator() {
        return new LazyItr();
    }

    @Override
    public ListIterator<E> listIterator() {
        return new LazyListItr(0);
    }

    @Override
    public ListIterator<E> listIterator(int n) {
        return new LazyListItr(n);
    }

    public String toString() {
        if (this.size == 0) {
            return "[]";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        for (int i = 0; i < this.size; ++i) {
            Object object = this.elementData[i];
            stringBuilder.append(object == this ? "this" : object);
            if (i == this.size - 1) {
                stringBuilder.append(']').toString();
                continue;
            }
            stringBuilder.append(", ");
        }
        return stringBuilder.toString();
    }

    @Override
    public List<E> subList(int n, int n2) {
        throw new UnsupportedOperationException();
    }

    private class LazyItr
    implements Iterator<E> {
        int cursor = 0;
        int lastRet = -1;

        private LazyItr() {
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

    private class LazyListItr
    extends LazyItr
    implements ListIterator<E> {
        LazyListItr(int n) {
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

    private static class PoolableLazyArrayListFactory
    implements PoolableObjectFactory {
        private PoolableLazyArrayListFactory() {
        }

        public Object makeObject() throws Exception {
            return new LazyArrayList();
        }

        public void destroyObject(Object object) throws Exception {
            ((LazyArrayList)object).clear();
        }

        public boolean validateObject(Object object) {
            return true;
        }

        public void activateObject(Object object) throws Exception {
        }

        public void passivateObject(Object object) throws Exception {
            ((LazyArrayList)object).clear();
        }
    }
}
