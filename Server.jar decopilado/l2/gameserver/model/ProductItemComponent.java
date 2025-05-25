/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.templates.item.ItemTemplate;

public class ProductItemComponent {
    private final int jc;
    private final int jd;
    private final int je;
    private final boolean cn;

    public ProductItemComponent(int n, int n2) {
        this.jc = n;
        this.jd = n2;
        ItemTemplate itemTemplate = ItemHolder.getInstance().getTemplate(n);
        if (itemTemplate != null) {
            this.je = itemTemplate.getWeight();
            this.cn = itemTemplate.isDropable();
        } else {
            this.je = 0;
            this.cn = true;
        }
    }

    public int getItemId() {
        return this.jc;
    }

    public int getCount() {
        return this.jd;
    }

    public int getWeight() {
        return this.je;
    }

    public boolean isDropable() {
        return this.cn;
    }
}
