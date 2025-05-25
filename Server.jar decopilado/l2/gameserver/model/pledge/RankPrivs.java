/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.pledge;

public class RankPrivs {
    private int pb;
    private int pc;
    private int pd;

    public RankPrivs(int n, int n2, int n3) {
        this.pb = n;
        this.pc = n2;
        this.pd = n3;
    }

    public int getRank() {
        return this.pb;
    }

    public int getParty() {
        return this.pc;
    }

    public void setParty(int n) {
        this.pc = n;
    }

    public int getPrivs() {
        return this.pd;
    }

    public void setPrivs(int n) {
        this.pd = n;
    }
}
