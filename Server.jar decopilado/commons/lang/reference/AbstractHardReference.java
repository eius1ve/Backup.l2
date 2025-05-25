/*
 * Decompiled with CFR 0.152.
 */
package l2.commons.lang.reference;

import l2.commons.lang.reference.HardReference;

public class AbstractHardReference<T>
implements HardReference<T> {
    private T a;

    public AbstractHardReference(T t) {
        this.a = t;
    }

    @Override
    public T get() {
        return this.a;
    }

    @Override
    public void clear() {
        this.a = null;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (!(object instanceof AbstractHardReference)) {
            return false;
        }
        if (((AbstractHardReference)object).get() == null) {
            return false;
        }
        return ((AbstractHardReference)object).get().equals(this.get());
    }
}
