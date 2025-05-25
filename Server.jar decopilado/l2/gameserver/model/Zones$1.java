/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import java.util.Iterator;
import java.util.NoSuchElementException;
import l2.gameserver.model.Zone;

class Zones.1
implements Iterator<Zone> {
    private final Zone[] b;
    private int cursor;

    Zones.1() {
        this.b = Zones.this.b.get();
        this.cursor = 0;
    }

    @Override
    public boolean hasNext() {
        return this.cursor < this.b.length;
    }

    @Override
    public Zone next() {
        try {
            return this.b[this.cursor++];
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            throw new NoSuchElementException();
        }
    }
}
