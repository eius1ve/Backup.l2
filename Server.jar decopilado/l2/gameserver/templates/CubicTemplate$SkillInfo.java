/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.TIntIntHashMap
 */
package l2.gameserver.templates;

import gnu.trove.TIntIntHashMap;
import l2.gameserver.model.Skill;
import l2.gameserver.templates.CubicTemplate;

public static class CubicTemplate.SkillInfo {
    private final Skill u;
    private final int Eg;
    private final CubicTemplate.ActionType a;
    private final boolean gJ;
    private final int Eh;
    private final int Ei;
    private final TIntIntHashMap h;

    public CubicTemplate.SkillInfo(Skill skill, int n, CubicTemplate.ActionType actionType, boolean bl, int n2, int n3, TIntIntHashMap tIntIntHashMap) {
        this.u = skill;
        this.Eg = n;
        this.a = actionType;
        this.gJ = bl;
        this.Eh = n2;
        this.Ei = n3;
        this.h = tIntIntHashMap;
    }

    public int getChance() {
        return this.Eg;
    }

    public CubicTemplate.ActionType getActionType() {
        return this.a;
    }

    public Skill getSkill() {
        return this.u;
    }

    public boolean isCanAttackDoor() {
        return this.gJ;
    }

    public int getMinHp() {
        return this.Eh;
    }

    public int getMinHpPercent() {
        return this.Ei;
    }

    public int getChance(int n) {
        return this.h.get(n);
    }
}
