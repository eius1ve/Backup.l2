/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.base;

import java.util.ArrayList;
import java.util.List;
import l2.gameserver.model.base.MultiSellIngredient;

public class MultiSellEntry {
    private int kM;
    private List<MultiSellIngredient> bs = new ArrayList<MultiSellIngredient>();
    private List<MultiSellIngredient> bt = new ArrayList<MultiSellIngredient>();
    private long bT;

    public MultiSellEntry() {
    }

    public MultiSellEntry(int n) {
        this.kM = n;
    }

    public MultiSellEntry(int n, int n2, int n3, int n4, int n5) {
        this.kM = n;
        this.addProduct(new MultiSellIngredient(n2, n3, n4, n5));
    }

    public void setEntryId(int n) {
        this.kM = n;
    }

    public int getEntryId() {
        return this.kM;
    }

    public void addIngredient(MultiSellIngredient multiSellIngredient) {
        if (multiSellIngredient.getItemCount() > 0L) {
            this.bs.add(multiSellIngredient);
        }
    }

    public List<MultiSellIngredient> getIngredients() {
        return this.bs;
    }

    public void addProduct(MultiSellIngredient multiSellIngredient) {
        this.bt.add(multiSellIngredient);
    }

    public List<MultiSellIngredient> getProduction() {
        return this.bt;
    }

    public long getTax() {
        return this.bT;
    }

    public void setTax(long l) {
        this.bT = l;
    }

    public int hashCode() {
        return this.kM;
    }

    public MultiSellEntry clone() {
        MultiSellEntry multiSellEntry = new MultiSellEntry(this.kM);
        for (MultiSellIngredient multiSellIngredient : this.bs) {
            multiSellEntry.addIngredient(multiSellIngredient.clone());
        }
        for (MultiSellIngredient multiSellIngredient : this.bt) {
            multiSellEntry.addProduct(multiSellIngredient.clone());
        }
        return multiSellEntry;
    }
}
