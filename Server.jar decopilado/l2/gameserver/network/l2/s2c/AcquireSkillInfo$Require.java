/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

private static class AcquireSkillInfo.Require {
    public int itemId;
    public long count;
    public int type;
    public int unk;

    public AcquireSkillInfo.Require(int n, int n2, long l, int n3) {
        this.itemId = n2;
        this.type = n;
        this.count = l;
        this.unk = n3;
    }
}
