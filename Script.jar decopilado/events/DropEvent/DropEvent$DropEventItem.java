/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  org.apache.commons.lang3.builder.EqualsBuilder
 *  org.apache.commons.lang3.builder.HashCodeBuilder
 */
package events.DropEvent;

import java.util.Arrays;
import l2.commons.util.Rnd;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

private static class DropEvent.DropEventItem {
    private final int _itemId;
    private final long W;
    private final long X;
    private final double c;
    private final int[] E;
    private final int bK;
    private final int _maxLvl;

    private DropEvent.DropEventItem(int n, long l, long l2, double d, int[] nArray, int n2, int n3) {
        Arrays.sort(nArray);
        this._itemId = n;
        this.W = l;
        this.X = l2;
        this.c = d;
        this.E = nArray;
        this.bK = n2;
        this._maxLvl = n3;
    }

    public double getChance() {
        return this.c;
    }

    public long getItemCountMin() {
        return this.W;
    }

    public long getItemCountMax() {
        return this.X;
    }

    public long getItemCount() {
        if (this.getItemCountMin() == this.getItemCountMax()) {
            return this.getItemCountMin();
        }
        return Rnd.get((long)this.getItemCountMin(), (long)this.getItemCountMax());
    }

    public int getItemId() {
        return this._itemId;
    }

    public int getMaxLvl() {
        return this._maxLvl;
    }

    public int getMinLvl() {
        return this.bK;
    }

    public int[] getNpcIds() {
        return this.E;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        DropEvent.DropEventItem dropEventItem = (DropEvent.DropEventItem)object;
        return new EqualsBuilder().append(this._itemId, dropEventItem._itemId).append(this.W, dropEventItem.W).append(this.X, dropEventItem.X).append(this.c, dropEventItem.c).append(this.bK, dropEventItem.bK).append(this._maxLvl, dropEventItem._maxLvl).append(this.E, dropEventItem.E).isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(this._itemId).append(this.W).append(this.X).append(this.c).append(this.E).append(this.bK).append(this._maxLvl).toHashCode();
    }
}
