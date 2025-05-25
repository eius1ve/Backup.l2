/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import java.util.ArrayList;

public class GWAutoAnnounce {
    private final int ha;
    private ArrayList<String> b;
    private int hb;
    private long be;
    private boolean bw;

    public GWAutoAnnounce(int n) {
        this.ha = n;
    }

    public int getId() {
        return this.ha;
    }

    public void setScreenAnnounce(boolean bl) {
        this.bw = bl;
    }

    public boolean isScreenAnnounce() {
        return this.bw;
    }

    public void setAnnounce(int n, int n2, ArrayList<String> arrayList) {
        this.be = System.currentTimeMillis() + (long)(n * 1000);
        this.hb = n2;
        this.b = arrayList;
    }

    public void updateRepeat() {
        this.be = System.currentTimeMillis() + (long)(this.hb * 1000);
    }

    public boolean canAnnounce() {
        return System.currentTimeMillis() > this.be;
    }

    public ArrayList<String> getMessage() {
        return this.b;
    }
}
