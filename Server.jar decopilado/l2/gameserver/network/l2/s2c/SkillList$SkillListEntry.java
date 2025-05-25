/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

static class SkillList.SkillListEntry {
    public int id;
    public int level;
    public int subLevel;
    public int reuseDelayGroup;
    public boolean passive;
    public boolean disabled;
    public boolean enchanted;

    SkillList.SkillListEntry(int n, int n2, int n3, int n4, boolean bl, boolean bl2, boolean bl3) {
        this.id = n;
        this.level = n2;
        this.subLevel = n3;
        this.reuseDelayGroup = n4;
        this.passive = bl;
        this.disabled = bl2;
        this.enchanted = bl3;
    }
}
