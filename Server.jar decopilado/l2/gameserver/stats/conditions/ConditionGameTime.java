/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats.conditions;

import l2.gameserver.GameTimeController;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.conditions.Condition;

public class ConditionGameTime
extends Condition {
    private final CheckGameTime a;
    private final boolean gj;

    public ConditionGameTime(CheckGameTime checkGameTime, boolean bl) {
        this.a = checkGameTime;
        this.gj = bl;
    }

    @Override
    protected boolean testImpl(Env env) {
        switch (this.a) {
            case NIGHT: {
                return GameTimeController.getInstance().isNowNight() == this.gj;
            }
        }
        return !this.gj;
    }

    public static final class CheckGameTime
    extends Enum<CheckGameTime> {
        public static final /* enum */ CheckGameTime NIGHT = new CheckGameTime();
        private static final /* synthetic */ CheckGameTime[] a;

        public static CheckGameTime[] values() {
            return (CheckGameTime[])a.clone();
        }

        public static CheckGameTime valueOf(String string) {
            return Enum.valueOf(CheckGameTime.class, string);
        }

        private static /* synthetic */ CheckGameTime[] a() {
            return new CheckGameTime[]{NIGHT};
        }

        static {
            a = CheckGameTime.a();
        }
    }
}
