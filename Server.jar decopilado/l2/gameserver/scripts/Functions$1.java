/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.scripts;

import java.util.Map;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.Player;
import l2.gameserver.scripts.Functions;

static class Functions.1
extends RunnableImpl {
    final /* synthetic */ Player val$caller;
    final /* synthetic */ String val$className;
    final /* synthetic */ String val$methodName;
    final /* synthetic */ Object[] val$args;
    final /* synthetic */ Map val$variables;

    Functions.1(Player player, String string, String string2, Object[] objectArray, Map map) {
        this.val$caller = player;
        this.val$className = string;
        this.val$methodName = string2;
        this.val$args = objectArray;
        this.val$variables = map;
    }

    @Override
    public void runImpl() throws Exception {
        Functions.callScripts(this.val$caller, this.val$className, this.val$methodName, this.val$args, this.val$variables);
    }
}
