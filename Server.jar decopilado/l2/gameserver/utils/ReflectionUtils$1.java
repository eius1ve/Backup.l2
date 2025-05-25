/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.utils;

import l2.gameserver.templates.InstantZoneEntryType;

static class ReflectionUtils.1 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$templates$InstantZoneEntryType;

    static {
        $SwitchMap$l2$gameserver$templates$InstantZoneEntryType = new int[InstantZoneEntryType.values().length];
        try {
            ReflectionUtils.1.$SwitchMap$l2$gameserver$templates$InstantZoneEntryType[InstantZoneEntryType.SOLO.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            ReflectionUtils.1.$SwitchMap$l2$gameserver$templates$InstantZoneEntryType[InstantZoneEntryType.PARTY.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            ReflectionUtils.1.$SwitchMap$l2$gameserver$templates$InstantZoneEntryType[InstantZoneEntryType.COMMAND_CHANNEL.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
