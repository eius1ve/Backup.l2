/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.pledge.entry;

import l2.gameserver.model.pledge.Clan;
import l2.gameserver.tables.ClanTable;

public class PledgeRecruitInfo {
    private int fY;
    private int gh;
    private String dM;
    private String dN;
    private final Clan c;
    private final int pl;
    private final int pm;

    public PledgeRecruitInfo(int n, int n2, String string, String string2, int n3, int n4) {
        this.fY = n;
        this.gh = n2;
        this.dM = string;
        this.dN = string2;
        this.c = ClanTable.getInstance().getClan(n);
        this.pl = n3;
        this.pm = n4;
    }

    public int getClanId() {
        return this.fY;
    }

    public void setClanId(int n) {
        this.fY = n;
    }

    public String getClanName() {
        return this.c.getName();
    }

    public String getClanLeaderName() {
        return this.c.getLeaderName();
    }

    public int getClanLevel() {
        return this.c.getLevel();
    }

    public int getKarma() {
        return this.gh;
    }

    public void setKarma(int n) {
        this.gh = n;
    }

    public String getInformation() {
        return this.dM;
    }

    public void setInformation(String string) {
        this.dM = string;
    }

    public String getDetailedInformation() {
        return this.dN;
    }

    public void setDetailedInformation(String string) {
        this.dN = string;
    }

    public int getApplicationType() {
        return this.pl;
    }

    public int getRecruitType() {
        return this.pm;
    }

    public Clan getClan() {
        return this.c;
    }
}
