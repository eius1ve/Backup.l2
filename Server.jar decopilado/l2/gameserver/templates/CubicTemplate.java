/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.TIntIntHashMap
 */
package l2.gameserver.templates;

import gnu.trove.TIntIntHashMap;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import l2.gameserver.model.Skill;

public class CubicTemplate {
    private final int Ed;
    private final int Ee;
    private final int Ef;
    private List<Map.Entry<Integer, List<SkillInfo>>> _skills = new ArrayList<Map.Entry<Integer, List<SkillInfo>>>(3);

    public CubicTemplate(int n, int n2, int n3) {
        this.Ed = n;
        this.Ee = n2;
        this.Ef = n3;
    }

    public void putSkills(int n, List<SkillInfo> list) {
        this._skills.add(new AbstractMap.SimpleImmutableEntry<Integer, List<SkillInfo>>(n, list));
    }

    public Iterable<Map.Entry<Integer, List<SkillInfo>>> getSkills() {
        return this._skills;
    }

    public int getDelay() {
        return this.Ef;
    }

    public int getId() {
        return this.Ed;
    }

    public int getLevel() {
        return this.Ee;
    }

    public static final class ActionType
    extends Enum<ActionType> {
        public static final /* enum */ ActionType ATTACK = new ActionType();
        public static final /* enum */ ActionType DEBUFF = new ActionType();
        public static final /* enum */ ActionType CANCEL = new ActionType();
        public static final /* enum */ ActionType HEAL = new ActionType();
        private static final /* synthetic */ ActionType[] a;

        public static ActionType[] values() {
            return (ActionType[])a.clone();
        }

        public static ActionType valueOf(String string) {
            return Enum.valueOf(ActionType.class, string);
        }

        private static /* synthetic */ ActionType[] a() {
            return new ActionType[]{ATTACK, DEBUFF, CANCEL, HEAL};
        }

        static {
            a = ActionType.a();
        }
    }

    public static class SkillInfo {
        private final Skill u;
        private final int Eg;
        private final ActionType a;
        private final boolean gJ;
        private final int Eh;
        private final int Ei;
        private final TIntIntHashMap h;

        public SkillInfo(Skill skill, int n, ActionType actionType, boolean bl, int n2, int n3, TIntIntHashMap tIntIntHashMap) {
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

        public ActionType getActionType() {
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
}
