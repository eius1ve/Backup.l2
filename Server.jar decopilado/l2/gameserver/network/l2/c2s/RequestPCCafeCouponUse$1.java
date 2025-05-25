/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.data.xml.holder.PromoCodeHolder;

static class RequestPCCafeCouponUse.1 {
    static final /* synthetic */ int[] $SwitchMap$l2$gameserver$data$xml$holder$PromoCodeHolder$ActivateResult;

    static {
        $SwitchMap$l2$gameserver$data$xml$holder$PromoCodeHolder$ActivateResult = new int[PromoCodeHolder.ActivateResult.values().length];
        try {
            RequestPCCafeCouponUse.1.$SwitchMap$l2$gameserver$data$xml$holder$PromoCodeHolder$ActivateResult[PromoCodeHolder.ActivateResult.ALREADY.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            RequestPCCafeCouponUse.1.$SwitchMap$l2$gameserver$data$xml$holder$PromoCodeHolder$ActivateResult[PromoCodeHolder.ActivateResult.NOT_FOR_YOU.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            RequestPCCafeCouponUse.1.$SwitchMap$l2$gameserver$data$xml$holder$PromoCodeHolder$ActivateResult[PromoCodeHolder.ActivateResult.OK.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            RequestPCCafeCouponUse.1.$SwitchMap$l2$gameserver$data$xml$holder$PromoCodeHolder$ActivateResult[PromoCodeHolder.ActivateResult.OUT_OF_DATE.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            RequestPCCafeCouponUse.1.$SwitchMap$l2$gameserver$data$xml$holder$PromoCodeHolder$ActivateResult[PromoCodeHolder.ActivateResult.OUT_OF_LIMIT.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            RequestPCCafeCouponUse.1.$SwitchMap$l2$gameserver$data$xml$holder$PromoCodeHolder$ActivateResult[PromoCodeHolder.ActivateResult.HWID_LIMITED.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            RequestPCCafeCouponUse.1.$SwitchMap$l2$gameserver$data$xml$holder$PromoCodeHolder$ActivateResult[PromoCodeHolder.ActivateResult.IP_LIMITED.ordinal()] = 7;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            RequestPCCafeCouponUse.1.$SwitchMap$l2$gameserver$data$xml$holder$PromoCodeHolder$ActivateResult[PromoCodeHolder.ActivateResult.MIN_LEVEL_LIMITED.ordinal()] = 8;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            RequestPCCafeCouponUse.1.$SwitchMap$l2$gameserver$data$xml$holder$PromoCodeHolder$ActivateResult[PromoCodeHolder.ActivateResult.MAX_LEVEL_LIMITED.ordinal()] = 9;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            RequestPCCafeCouponUse.1.$SwitchMap$l2$gameserver$data$xml$holder$PromoCodeHolder$ActivateResult[PromoCodeHolder.ActivateResult.INVALID_TRY_LATER.ordinal()] = 10;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
