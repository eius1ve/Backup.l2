/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.data.xml.holder;

import java.util.ArrayList;
import java.util.List;
import l2.gameserver.model.base.MultiSellEntry;

public static class MultiSellHolder.MultiSellListContainer {
    private int fq;
    private boolean aX = true;
    private boolean aY = false;
    private boolean aZ = false;
    private boolean ba = false;
    private boolean bb = false;
    private boolean bc = false;
    private List<MultiSellEntry> ag = new ArrayList<MultiSellEntry>();

    public void setListId(int n) {
        this.fq = n;
    }

    public int getListId() {
        return this.fq;
    }

    public void setShowAll(boolean bl) {
        this.aX = bl;
    }

    public boolean isShowAll() {
        return this.aX;
    }

    public void setNoTax(boolean bl) {
        this.ba = bl;
    }

    public boolean isNoTax() {
        return this.ba;
    }

    public void setNoKey(boolean bl) {
        this.bb = bl;
    }

    public boolean isNoKey() {
        return this.bb;
    }

    public boolean isNoMerchant() {
        return this.bc;
    }

    public void setNoMerchant(boolean bl) {
        this.bc = bl;
    }

    public void setKeepEnchant(boolean bl) {
        this.aY = bl;
    }

    public boolean isKeepEnchant() {
        return this.aY;
    }

    public void setChancedList(boolean bl) {
        this.aZ = bl;
    }

    public boolean isChancedList() {
        return this.aZ;
    }

    public void addEntry(MultiSellEntry multiSellEntry) {
        this.ag.add(multiSellEntry);
    }

    public List<MultiSellEntry> getEntries() {
        return this.ag;
    }

    public boolean isEmpty() {
        return this.ag.isEmpty();
    }
}
