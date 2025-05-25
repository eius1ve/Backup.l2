/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import java.util.Iterator;
import l2.gameserver.model.GameObject;

private class WorldRegion.InternalIterator
implements Iterator<GameObject> {
    final GameObject[] objects;
    int cursor = 0;

    public WorldRegion.InternalIterator(GameObject[] gameObjectArray) {
        this.objects = gameObjectArray;
    }

    @Override
    public boolean hasNext() {
        if (this.cursor < this.objects.length) {
            return this.objects[this.cursor] != null;
        }
        return false;
    }

    @Override
    public GameObject next() {
        return this.objects[this.cursor++];
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
