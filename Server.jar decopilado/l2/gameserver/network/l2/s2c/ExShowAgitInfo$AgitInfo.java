/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

static class ExShowAgitInfo.AgitInfo {
    public String clan_name;
    public String leader_name;
    public int ch_id;
    public int getType;

    public ExShowAgitInfo.AgitInfo(String string, String string2, int n, int n2) {
        this.clan_name = string;
        this.leader_name = string2;
        this.ch_id = n;
        this.getType = n2;
    }
}
