/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats.conditions;

import l2.gameserver.model.Player;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.conditions.Condition;

public class ConditionPlayerRiding
extends Condition {
    private final CheckPlayerRiding a;

    public ConditionPlayerRiding(CheckPlayerRiding checkPlayerRiding) {
        this.a = checkPlayerRiding;
    }

    @Override
    protected boolean testImpl(Env env) {
        if (!env.character.isPlayer()) {
            return false;
        }
        if (this.a == CheckPlayerRiding.STRIDER && ((Player)env.character).isRiding()) {
            return true;
        }
        if (this.a == CheckPlayerRiding.WYVERN && ((Player)env.character).isFlying()) {
            return true;
        }
        return this.a == CheckPlayerRiding.NONE && !((Player)env.character).isRiding() && !((Player)env.character).isFlying();
    }

    public static final class CheckPlayerRiding
    extends Enum<CheckPlayerRiding> {
        public static final /* enum */ CheckPlayerRiding NONE = new CheckPlayerRiding();
        public static final /* enum */ CheckPlayerRiding STRIDER = new CheckPlayerRiding();
        public static final /* enum */ CheckPlayerRiding WYVERN = new CheckPlayerRiding();
        private static final /* synthetic */ CheckPlayerRiding[] a;

        public static CheckPlayerRiding[] values() {
            return (CheckPlayerRiding[])a.clone();
        }

        public static CheckPlayerRiding valueOf(String string) {
            return Enum.valueOf(CheckPlayerRiding.class, string);
        }

        private static /* synthetic */ CheckPlayerRiding[] a() {
            return new CheckPlayerRiding[]{NONE, STRIDER, WYVERN};
        }

        static {
            a = CheckPlayerRiding.a();
        }
    }
}
