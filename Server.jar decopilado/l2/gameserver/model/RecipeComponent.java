/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

public class RecipeComponent {
    private final int jm;
    private final int jn;

    public RecipeComponent(int n, int n2) {
        this.jm = n;
        this.jn = n2;
    }

    public int getItemId() {
        return this.jm;
    }

    public int getQuantity() {
        return this.jn;
    }
}
