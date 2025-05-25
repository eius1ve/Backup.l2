/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

static class ExMultiPartyCommandChannelInfo.ChannelPartyInfo {
    public String Leader_name;
    public int Leader_obj_id;
    public int MemberCount;

    public ExMultiPartyCommandChannelInfo.ChannelPartyInfo(String string, int n, int n2) {
        this.Leader_name = string;
        this.Leader_obj_id = n;
        this.MemberCount = n2;
    }
}
