/*
 * Decompiled with CFR 0.152.
 */
package handler.admincommands;

import handler.admincommands.AdminClientSupport;

static class AdminClientSupport.1 {
    static final /* synthetic */ int[] $SwitchMap$handler$admincommands$AdminClientSupport$Commands;

    static {
        $SwitchMap$handler$admincommands$AdminClientSupport$Commands = new int[AdminClientSupport.Commands.values().length];
        try {
            AdminClientSupport.1.$SwitchMap$handler$admincommands$AdminClientSupport$Commands[AdminClientSupport.Commands.admin_setskill.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            AdminClientSupport.1.$SwitchMap$handler$admincommands$AdminClientSupport$Commands[AdminClientSupport.Commands.admin_summon.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
