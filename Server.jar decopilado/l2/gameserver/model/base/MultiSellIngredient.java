/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.base;

import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.model.base.Element;
import l2.gameserver.model.items.ItemAttributes;

public class MultiSellIngredient
implements Cloneable {
    private int _itemId;
    private long bJ;
    private int kN;
    private int ai;
    private ItemAttributes a;
    private boolean cT;

    public MultiSellIngredient(int n, long l) {
        this(n, l, 0, 0);
    }

    public MultiSellIngredient(int n, long l, int n2, int n3) {
        this._itemId = n;
        this.bJ = l;
        this.kN = n2;
        this.ai = n3;
        this.cT = false;
        this.a = new ItemAttributes();
    }

    public MultiSellIngredient clone() {
        MultiSellIngredient multiSellIngredient = new MultiSellIngredient(this._itemId, this.bJ, this.kN, this.ai);
        multiSellIngredient.setMantainIngredient(this.cT);
        multiSellIngredient.setItemAttributes(this.a.clone());
        return multiSellIngredient;
    }

    public void setItemId(int n) {
        this._itemId = n;
    }

    public int getItemId() {
        return this._itemId;
    }

    public void setItemCount(long l) {
        this.bJ = l;
    }

    public long getItemCount() {
        return this.bJ;
    }

    public boolean isStackable() {
        return this._itemId <= 0 || ItemHolder.getInstance().getTemplate(this._itemId).isStackable();
    }

    public void setItemEnchant(int n) {
        this.kN = n;
    }

    public int getItemEnchant() {
        return this.kN;
    }

    public ItemAttributes getItemAttributes() {
        return this.a;
    }

    public void setItemAttributes(ItemAttributes itemAttributes) {
        this.a = itemAttributes;
    }

    public void setItemChance(int n) {
        this.ai = n;
    }

    public int getItemChance() {
        return this.ai;
    }

    public int hashCode() {
        int n = 1;
        n = 31 * n + (int)(this.bJ ^ this.bJ >>> 32);
        for (Element element : Element.VALUES) {
            n = 31 * n + this.a.getValue(element);
        }
        n = 31 * n + this.kN;
        n = 31 * n + this._itemId;
        return n;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        MultiSellIngredient multiSellIngredient = (MultiSellIngredient)object;
        if (this._itemId != multiSellIngredient._itemId) {
            return false;
        }
        if (this.bJ != multiSellIngredient.bJ) {
            return false;
        }
        if (this.kN != multiSellIngredient.kN) {
            return false;
        }
        for (Element element : Element.VALUES) {
            if (this.a.getValue(element) == multiSellIngredient.a.getValue(element)) continue;
            return false;
        }
        return true;
    }

    public boolean getMantainIngredient() {
        return this.cT;
    }

    public void setMantainIngredient(boolean bl) {
        this.cT = bl;
    }
}
