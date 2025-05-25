/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.tuple.Pair
 */
package achievements;

import java.util.LinkedList;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;

public static abstract class AchievementUI.Paginator<ItemType> {
    private final int p;
    private List<Pair<ItemType, Integer>> g = new LinkedList<Pair<ItemType, Integer>>();
    private int q;

    public AchievementUI.Paginator(int n, int n2) {
        this.p = n;
        this.q = n2;
    }

    public AchievementUI.Paginator(int n) {
        this(n, 0);
    }

    public int getPageIdx() {
        return this.q;
    }

    public AchievementUI.Paginator<ItemType> setPageIdx(int n) {
        this.q = n;
        return this;
    }

    public AchievementUI.Paginator<ItemType> addItem(ItemType ItemType, int n) {
        this.g.add(Pair.of(ItemType, (Object)n));
        return this;
    }

    private int c() {
        int n = 0;
        for (Pair<ItemType, Integer> pair : this.g) {
            n += ((Integer)pair.getRight()).intValue();
        }
        return n;
    }

    public List<Pair<ItemType, Pair<Integer, Integer>>> getItems(int n) {
        int n2 = n * this.p;
        int n3 = n2 + this.p;
        int n4 = 0;
        LinkedList<Pair<ItemType, Pair<Integer, Integer>>> linkedList = new LinkedList<Pair<ItemType, Pair<Integer, Integer>>>();
        for (Pair<ItemType, Integer> pair : this.g) {
            int n5 = (Integer)pair.getRight();
            if (n4 < n3 && n4 + n5 > n2) {
                linkedList.add(Pair.of((Object)pair.getLeft(), (Object)Pair.of((Object)Math.max(n2 - n4, 0), (Object)Math.min(n5, n3 - n4))));
            }
            n4 += n5;
        }
        return linkedList;
    }

    public List<Pair<ItemType, Pair<Integer, Integer>>> getItems() {
        return this.getItems(this.q);
    }

    protected abstract String getBypassForPageOrdinal(int var1, int var2);

    public String toHtml() {
        int n = this.c();
        int n2 = (n + this.p - 1) / this.p;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<table border=0 cellspacing=0 cellpadding=0><tr>");
        for (int i = 0; i < n2; ++i) {
            int n3 = i + 1;
            stringBuilder.append("<td>");
            if (i == this.q) {
                stringBuilder.append(n3);
            } else {
                stringBuilder.append("<a action=\"bypass -h ").append(this.getBypassForPageOrdinal(i, n3)).append("\">").append(n3).append("</a>");
            }
            stringBuilder.append("</td>");
        }
        stringBuilder.append("</tr></table>");
        return stringBuilder.toString();
    }
}
