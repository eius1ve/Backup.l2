/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.items;

public class ManufactureItem {
    private final int oD;
    private final long cA;

    public ManufactureItem(int n, long l) {
        this.oD = n;
        this.cA = l;
    }

    public int getRecipeId() {
        return this.oD;
    }

    public long getCost() {
        return this.cA;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (object.getClass() != this.getClass()) {
            return false;
        }
        return ((ManufactureItem)object).getRecipeId() == this.getRecipeId();
    }
}
