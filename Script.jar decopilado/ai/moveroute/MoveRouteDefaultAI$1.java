/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.templates.moveroute.MoveRouteType
 */
package ai.moveroute;

import l2.gameserver.templates.moveroute.MoveRouteType;

static class MoveRouteDefaultAI.1 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$templates$moveroute$MoveRouteType;

    static {
        $SwitchMap$l2$gameserver$templates$moveroute$MoveRouteType = new int[MoveRouteType.values().length];
        try {
            MoveRouteDefaultAI.1.$SwitchMap$l2$gameserver$templates$moveroute$MoveRouteType[MoveRouteType.LOOP.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            MoveRouteDefaultAI.1.$SwitchMap$l2$gameserver$templates$moveroute$MoveRouteType[MoveRouteType.CIRCLE.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            MoveRouteDefaultAI.1.$SwitchMap$l2$gameserver$templates$moveroute$MoveRouteType[MoveRouteType.ONCE.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
