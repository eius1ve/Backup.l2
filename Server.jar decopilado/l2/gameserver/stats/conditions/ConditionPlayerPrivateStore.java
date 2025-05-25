/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 */
package l2.gameserver.stats.conditions;

import l2.gameserver.model.Player;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.conditions.Condition;
import org.apache.commons.lang3.StringUtils;

public class ConditionPlayerPrivateStore
extends Condition {
    private final int DQ;

    private static int b(String string) {
        if ("SELL".equalsIgnoreCase(string)) {
            return 1;
        }
        if ("BUY".equalsIgnoreCase(string)) {
            return 3;
        }
        if ("MANUFACTURE".equalsIgnoreCase(string)) {
            return 5;
        }
        if ("SELL_PACKAGE".equalsIgnoreCase(string)) {
            return 8;
        }
        return 0;
    }

    public ConditionPlayerPrivateStore(int n) {
        this.DQ = n;
    }

    public ConditionPlayerPrivateStore(String string) {
        this(StringUtils.isNumeric((CharSequence)string) ? Integer.parseInt(string) : ConditionPlayerPrivateStore.b(string));
    }

    @Override
    protected boolean testImpl(Env env) {
        if (!env.character.isPlayer()) {
            return false;
        }
        int n = ((Player)env.character).getPrivateStoreType();
        return n == this.DQ;
    }
}
