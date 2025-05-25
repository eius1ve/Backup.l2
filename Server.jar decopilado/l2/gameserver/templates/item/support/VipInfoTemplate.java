/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.templates.item.support;

public class VipInfoTemplate {
    private final byte g;
    private final long dZ;
    private final long ea;
    private int GV;
    private int _skillLevel;

    public VipInfoTemplate(byte by, long l, long l2) {
        this.g = by;
        this.dZ = l;
        this.ea = l2;
    }

    public byte getVipLevel() {
        return this.g;
    }

    public long getPointsRequired() {
        return this.dZ;
    }

    public long getPointsDepreciated() {
        return this.ea;
    }

    public int getSkill() {
        return this.GV;
    }

    public int getSkillLevel() {
        return this._skillLevel;
    }

    public void setSkill(int n, int n2) {
        this.GV = n;
        this._skillLevel = n2;
    }
}
