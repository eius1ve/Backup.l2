/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import l2.gameserver.model.GameObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameObjectArray<E extends GameObject>
implements Iterable<E> {
    private static final Logger bC = LoggerFactory.getLogger(GameObjectArray.class);
    public final String name;
    public final int resizeStep;
    public final int initCapacity;
    private final List<Integer> aQ;
    private E[] a;
    private int size = 0;
    private int hc = 0;

    public GameObjectArray(String string, int n, int n2) {
        this.name = string;
        this.resizeStep = n2;
        this.initCapacity = n;
        if (n < 0) {
            throw new IllegalArgumentException("Illegal Capacity (" + this.name + "): " + n);
        }
        if (this.resizeStep < 1) {
            throw new IllegalArgumentException("Illegal resize step (" + this.name + "): " + this.resizeStep);
        }
        this.aQ = new ArrayList<Integer>(this.resizeStep);
        this.a = new GameObject[n];
    }

    public int size() {
        return this.size;
    }

    public int getRealSize() {
        return this.hc;
    }

    public int capacity() {
        return this.a.length;
    }

    public synchronized int add(E e) {
        Integer n = null;
        if (this.aQ.size() > 0) {
            n = this.aQ.remove(this.aQ.size() - 1);
        }
        if (n != null) {
            ++this.hc;
            this.a[n.intValue()] = e;
            return n;
        }
        if (this.a.length <= this.size) {
            int n2 = this.a.length + this.resizeStep;
            bC.warn("Object array [" + this.name + "] resized: " + this.a.length + " -> " + n2);
            this.a = (GameObject[])Arrays.copyOf(this.a, n2);
        }
        this.a[this.size++] = e;
        ++this.hc;
        return this.size - 1;
    }

    public synchronized E remove(int n, int n2) {
        if (n >= this.size) {
            return null;
        }
        E e = this.a[n];
        if (e == null || ((GameObject)e).getObjectId() != n2) {
            return null;
        }
        this.a[n] = null;
        --this.hc;
        if (n == this.size - 1) {
            --this.size;
        } else {
            this.aQ.add(n);
        }
        return e;
    }

    public E get(int n) {
        return n >= this.size ? null : (E)this.a[n];
    }

    public E findByObjectId(int n) {
        if (n <= 0) {
            return null;
        }
        for (int i = 0; i < this.size; ++i) {
            E e = this.a[i];
            if (e == null || ((GameObject)e).getObjectId() != n) continue;
            return e;
        }
        return null;
    }

    public E findByName(String string) {
        if (string == null) {
            return null;
        }
        for (int i = 0; i < this.size; ++i) {
            E e = this.a[i];
            if (e == null || !string.equalsIgnoreCase(((GameObject)e).getName())) continue;
            return e;
        }
        return null;
    }

    public List<E> findAllByName(String string) {
        if (string == null) {
            return null;
        }
        ArrayList<E> arrayList = new ArrayList<E>();
        for (int i = 0; i < this.size; ++i) {
            E e = this.a[i];
            if (e == null || !string.equalsIgnoreCase(((GameObject)e).getName())) continue;
            arrayList.add(e);
        }
        return arrayList;
    }

    public List<E> getAll() {
        return this.getAll(new ArrayList(this.size));
    }

    public List<E> getAll(List<E> list) {
        for (int i = 0; i < this.size; ++i) {
            E e = this.a[i];
            if (e == null) continue;
            list.add(e);
        }
        return list;
    }

    private int a(E e) {
        if (e == null) {
            return -1;
        }
        for (int i = 0; i < this.size; ++i) {
            if (!((GameObject)e).equals(this.a[i])) continue;
            return i;
        }
        return -1;
    }

    public boolean contains(E e) {
        return this.a(e) > -1;
    }

    public synchronized void clear() {
        this.a = new GameObject[0];
        this.size = 0;
        this.hc = 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    class Itr
    implements Iterator<E> {
        private int cursor = 0;
        private E a;

        Itr() {
        }

        @Override
        public boolean hasNext() {
            while (this.cursor < GameObjectArray.this.size) {
                if ((this.a = GameObjectArray.this.a[this.cursor++]) == null) continue;
                return true;
            }
            return false;
        }

        @Override
        public E next() {
            Object e = this.a;
            this.a = null;
            if (e == null) {
                throw new NoSuchElementException();
            }
            return e;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
