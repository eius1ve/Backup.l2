/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.UserInfoType;

static class UserInfo.1 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$network$l2$s2c$UserInfoType;

    static {
        $SwitchMap$l2$gameserver$network$l2$s2c$UserInfoType = new int[UserInfoType.values().length];
        try {
            UserInfo.1.$SwitchMap$l2$gameserver$network$l2$s2c$UserInfoType[UserInfoType.BASIC_INFO.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            UserInfo.1.$SwitchMap$l2$gameserver$network$l2$s2c$UserInfoType[UserInfoType.CLAN.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
