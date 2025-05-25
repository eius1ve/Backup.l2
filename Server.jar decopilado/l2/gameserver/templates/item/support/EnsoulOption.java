/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.templates.item.support;

import l2.gameserver.model.Skill;
import l2.gameserver.templates.item.support.EnsoulFeeSlotType;

public class EnsoulOption {
    private final int Gz;
    private final int GA;
    private final Skill x;
    private final int GB;
    private final EnsoulFeeSlotType a;

    public EnsoulOption(int n, int n2, Skill skill, int n3, EnsoulFeeSlotType ensoulFeeSlotType) {
        this.Gz = n;
        this.GA = n2;
        this.x = skill;
        this.GB = n3;
        this.a = ensoulFeeSlotType;
    }

    public int getOptionId() {
        return this.Gz;
    }

    public int getStoneId() {
        return this.GA;
    }

    public Skill getSkill() {
        return this.x;
    }

    public int getOptionType() {
        return this.GB;
    }

    public EnsoulFeeSlotType getEnsoulFeeSlotType() {
        return this.a;
    }
}
