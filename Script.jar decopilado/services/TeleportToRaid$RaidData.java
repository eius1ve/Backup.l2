/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.utils.Location
 */
package services;

import java.util.List;
import l2.commons.util.Rnd;
import l2.gameserver.utils.Location;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public static class TeleportToRaid.RaidData {
    private final int bGa;
    private final List<Location> dY;
    public int boss_id;
    private final String ic;
    private final String ie;
    private final boolean hA;
    private final int bGb;
    private final int bGc;
    private final int bGd;
    private final String if;
    private final String ig;
    private final boolean hB;
    private final int bGe;
    private final int bGf;
    private final int bGg;
    private final String ih;
    private final String ii;
    private final String ij;
    private final String ik;

    public TeleportToRaid.RaidData(int n, String string, String string2, boolean bl, int n2, int n3, String string3, String string4, int n4, boolean bl2, int n5, int n6, int n7, String string5, String string6, String string7, String string8, List<Location> list) {
        this.boss_id = n;
        this.ic = string;
        this.ie = string2;
        this.hA = bl;
        this.bGb = n2;
        this.bGc = n3;
        this.bGd = n4;
        this.if = string3;
        this.ig = string4;
        this.hB = bl2;
        this.bGe = n5;
        this.bGf = n6;
        this.bGg = n7;
        this.ih = string5;
        this.ii = string6;
        this.ij = string7;
        this.ik = string8;
        this.dY = list;
        this.bGa = list.size() - 1;
    }

    private Location f() {
        return this.dY.get(Rnd.get((int)0, (int)this.bGa));
    }

    private boolean j(int n) {
        return n >= this.bGb && n <= this.bGc;
    }

    private boolean k(int n) {
        return n >= this.bGf && n <= this.bGg;
    }
}
