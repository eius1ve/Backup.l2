/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats.conditions;

import l2.gameserver.model.Player;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.conditions.Condition;

public class ConditionPlayerGender
extends Condition {
    private final Gender a;

    public ConditionPlayerGender(Gender gender) {
        this.a = gender;
    }

    public ConditionPlayerGender(String string) {
        this(Gender.valueOf(string.toUpperCase()));
    }

    @Override
    protected boolean testImpl(Env env) {
        if (!env.character.isPlayer()) {
            return false;
        }
        return ((Player)env.character).getSex() == this.a.getPlayerGenderId();
    }

    public static final class Gender
    extends Enum<Gender> {
        public static final /* enum */ Gender MALE = new Gender(0);
        public static final /* enum */ Gender FEMALE = new Gender(1);
        private final int DI;
        private static final /* synthetic */ Gender[] a;

        public static Gender[] values() {
            return (Gender[])a.clone();
        }

        public static Gender valueOf(String string) {
            return Enum.valueOf(Gender.class, string);
        }

        private Gender(int n2) {
            this.DI = n2;
        }

        public int getPlayerGenderId() {
            return this.DI;
        }

        private static /* synthetic */ Gender[] a() {
            return new Gender[]{MALE, FEMALE};
        }

        static {
            a = Gender.a();
        }
    }
}
