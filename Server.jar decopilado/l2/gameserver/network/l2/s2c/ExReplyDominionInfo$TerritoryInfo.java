/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

private class ExReplyDominionInfo.TerritoryInfo {
    public int id;
    public String terr;
    public String clan;
    public int[] flags;
    public int startTime;

    public ExReplyDominionInfo.TerritoryInfo(int n, String string, String string2, int[] nArray, int n2) {
        this.id = n;
        this.terr = string;
        this.clan = string2;
        this.flags = nArray;
        this.startTime = n2;
    }
}
