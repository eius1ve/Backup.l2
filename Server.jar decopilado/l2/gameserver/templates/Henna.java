/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.TIntArrayList
 */
package l2.gameserver.templates;

import gnu.trove.TIntArrayList;
import l2.gameserver.model.Player;

public class Henna {
    private final int Ez;
    private final int EA;
    private final long dN;
    private final long dO;
    private final int EB;
    private final int EC;
    private final int ED;
    private final int EE;
    private final int EF;
    private final int EG;
    private final TIntArrayList a;

    public Henna(int n, int n2, long l, long l2, int n3, int n4, int n5, int n6, int n7, int n8, TIntArrayList tIntArrayList) {
        this.Ez = n;
        this.EA = n2;
        this.dN = l;
        this.dO = l2;
        this.EB = n4;
        this.EC = n6;
        this.ED = n5;
        this.EE = n8;
        this.EF = n7;
        this.EG = n3;
        this.a = tIntArrayList;
    }

    public int getSymbolId() {
        return this.Ez;
    }

    public int getDyeId() {
        return this.EA;
    }

    public long getPrice() {
        return this.dN;
    }

    public int getStatINT() {
        return this.EB;
    }

    public int getStatSTR() {
        return this.EC;
    }

    public int getStatCON() {
        return this.ED;
    }

    public int getStatMEN() {
        return this.EE;
    }

    public int getStatDEX() {
        return this.EF;
    }

    public int getStatWIT() {
        return this.EG;
    }

    public boolean isForThisClass(Player player) {
        return this.a.contains(player.getActiveClassId());
    }

    public long getDrawCount() {
        return this.dO;
    }
}
