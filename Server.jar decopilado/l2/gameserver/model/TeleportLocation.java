/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.utils.Location;

public class TeleportLocation
extends Location {
    private final long _price;
    private final int jB;
    private final int jC;
    private final ItemTemplate b;
    private final String cU;
    private final int jD;
    private final int jE;
    private final int jF;

    public TeleportLocation(int n, long l, int n2, int n3, String string, int n4, int n5, int n6) {
        this._price = l;
        this.jB = n2;
        this.jC = n3;
        this.cU = string;
        this.b = ItemHolder.getInstance().getTemplate(n);
        this.jD = n5;
        this.jE = n4;
        this.jF = n6;
    }

    public TeleportLocation(int n, long l, String string, int n2, int n3, int n4) {
        this._price = l;
        this.jF = n4;
        this.jB = 0;
        this.jC = 0;
        this.cU = string;
        this.b = ItemHolder.getInstance().getTemplate(n);
        this.jD = n3;
        this.jE = n2;
    }

    public int getMinLevel() {
        return this.jB;
    }

    public int getMaxLevel() {
        return this.jC;
    }

    public long getPrice() {
        return this._price;
    }

    public ItemTemplate getItem() {
        return this.b;
    }

    public String getName() {
        return this.cU;
    }

    public int getFString() {
        return this.jE;
    }

    public int getCastleId() {
        return this.jD;
    }

    public int getKeyItemId() {
        return this.jF;
    }
}
