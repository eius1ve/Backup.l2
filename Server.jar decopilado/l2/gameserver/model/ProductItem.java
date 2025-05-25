/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import java.util.ArrayList;
import java.util.Calendar;
import l2.gameserver.model.ProductItemComponent;

public class ProductItem {
    public static final long NOT_LIMITED_START_TIME = 315547200000L;
    public static final long NOT_LIMITED_END_TIME = 2127445200000L;
    public static final int NOT_LIMITED_START_HOUR = 0;
    public static final int NOT_LIMITED_END_HOUR = 23;
    public static final int NOT_LIMITED_START_MIN = 0;
    public static final int NOT_LIMITED_END_MIN = 59;
    private final int iS;
    private final int iT;
    private final int _points;
    private final int iU;
    private final int iV;
    private final int iW;
    private final boolean cm;
    private final int iX;
    private final long bE;
    private final long bF;
    private final int iY;
    private final int iZ;
    private final int ja;
    private final int jb;
    private ArrayList<ProductItemComponent> c;

    public ProductItem(int n, int n2, int n3, int n4, int n5, boolean bl, int n6, int n7, long l, long l2) {
        Calendar calendar;
        this.iS = n;
        this.iT = n2;
        this.iV = n4;
        this.iU = n5;
        this._points = n3;
        this.iW = n7;
        this.cm = bl;
        this.iX = n6;
        if (l > 0L) {
            calendar = Calendar.getInstance();
            calendar.setTimeInMillis(l);
            this.bE = l;
            this.iY = calendar.get(11);
            this.ja = calendar.get(12);
        } else {
            this.bE = 315547200000L;
            this.iY = 0;
            this.ja = 0;
        }
        if (l2 > 0L) {
            calendar = Calendar.getInstance();
            calendar.setTimeInMillis(l2);
            this.bF = l2;
            this.iZ = calendar.get(11);
            this.jb = calendar.get(12);
        } else {
            this.bF = 2127445200000L;
            this.iZ = 23;
            this.jb = 59;
        }
    }

    public void setComponents(ArrayList<ProductItemComponent> arrayList) {
        this.c = arrayList;
    }

    public ArrayList<ProductItemComponent> getComponents() {
        if (this.c == null) {
            this.c = new ArrayList();
        }
        return this.c;
    }

    public int getProductId() {
        return this.iS;
    }

    public int getCategory() {
        return this.iT;
    }

    public int getPoints() {
        return this._points;
    }

    public int getSilverCoins() {
        return this.iV;
    }

    public int getGoldCoins() {
        return this.iU;
    }

    public int getTabId() {
        return this.iW;
    }

    public long getStartTimeSale() {
        return this.bE;
    }

    public int getStartHour() {
        return this.iY;
    }

    public int getStartMin() {
        return this.ja;
    }

    public long getEndTimeSale() {
        return this.bF;
    }

    public int getEndHour() {
        return this.iZ;
    }

    public int getEndMin() {
        return this.jb;
    }

    public boolean isVipItem() {
        return this.cm;
    }

    public int getVipLevelRequire() {
        return this.iX;
    }
}
