/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.builder.EqualsBuilder
 *  org.apache.commons.lang3.builder.HashCodeBuilder
 */
package l2.gameserver.data.xml.holder;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public static class CapsuleItemHolder.CapsuledItem {
    private final int fn;
    private final long aJ;
    private final long aK;
    private final double e;
    private final int fo;
    private final int fp;

    public CapsuleItemHolder.CapsuledItem(int n, long l, long l2, double d, int n2, int n3) {
        this.fn = n;
        this.aJ = l;
        this.aK = l2;
        this.e = d;
        this.fo = n2;
        this.fp = n3;
    }

    public int getItemId() {
        return this.fn;
    }

    public double getChance() {
        return this.e;
    }

    public long getMax() {
        return this.aK;
    }

    public long getMin() {
        return this.aJ;
    }

    public int getMinEnchant() {
        return this.fo;
    }

    public int getMaxEnchant() {
        return this.fp;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        CapsuleItemHolder.CapsuledItem capsuledItem = (CapsuleItemHolder.CapsuledItem)object;
        return new EqualsBuilder().append(this.fn, capsuledItem.fn).append(this.aJ, capsuledItem.aJ).append(this.aK, capsuledItem.aK).append(this.e, capsuledItem.e).append(this.fo, capsuledItem.fo).append(this.fp, capsuledItem.fp).isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(this.fn).append(this.aJ).append(this.aK).append(this.e).append(this.fo).append(this.fp).toHashCode();
    }
}
