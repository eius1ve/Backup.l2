/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import java.util.Iterator;
import java.util.NoSuchElementException;

class GameObjectArray.Itr
implements Iterator<E> {
    private int cursor = 0;
    private E a;

    GameObjectArray.Itr() {
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
