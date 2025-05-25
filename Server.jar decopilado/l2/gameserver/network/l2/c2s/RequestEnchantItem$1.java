/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.templates.item.support.EnchantScrollOnFailAction;

static class RequestEnchantItem.1 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$templates$item$support$EnchantScrollOnFailAction;

    static {
        $SwitchMap$l2$gameserver$templates$item$support$EnchantScrollOnFailAction = new int[EnchantScrollOnFailAction.values().length];
        try {
            RequestEnchantItem.1.$SwitchMap$l2$gameserver$templates$item$support$EnchantScrollOnFailAction[EnchantScrollOnFailAction.CRYSTALIZE.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            RequestEnchantItem.1.$SwitchMap$l2$gameserver$templates$item$support$EnchantScrollOnFailAction[EnchantScrollOnFailAction.RESET.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            RequestEnchantItem.1.$SwitchMap$l2$gameserver$templates$item$support$EnchantScrollOnFailAction[EnchantScrollOnFailAction.NONE.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
