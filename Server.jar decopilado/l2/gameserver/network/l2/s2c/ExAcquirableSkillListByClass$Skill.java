/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

class ExAcquirableSkillListByClass.Skill {
    public int id;
    public int nextLevel;
    public int getLevel;
    public int cost;
    public int requirements;
    public int subUnit;

    ExAcquirableSkillListByClass.Skill(int n, int n2, int n3, int n4, int n5, int n6) {
        this.id = n;
        this.nextLevel = n2;
        this.getLevel = n3;
        this.cost = n4;
        this.requirements = n5;
        this.subUnit = n6;
    }
}
