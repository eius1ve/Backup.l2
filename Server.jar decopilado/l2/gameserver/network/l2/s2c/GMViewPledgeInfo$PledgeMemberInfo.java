/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

static class GMViewPledgeInfo.PledgeMemberInfo {
    public String _name;
    public int level;
    public int class_id;
    public int online;
    public int sex;
    public int race;
    public int sponsor;

    public GMViewPledgeInfo.PledgeMemberInfo(String string, int n, int n2, int n3, int n4, int n5, int n6) {
        this._name = string;
        this.level = n;
        this.class_id = n2;
        this.online = n3;
        this.sex = n4;
        this.race = n5;
        this.sponsor = n6;
    }
}
