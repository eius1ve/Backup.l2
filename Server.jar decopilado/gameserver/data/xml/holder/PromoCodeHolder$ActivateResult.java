/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.data.xml.holder;

public static final class PromoCodeHolder.ActivateResult
extends Enum<PromoCodeHolder.ActivateResult> {
    public static final /* enum */ PromoCodeHolder.ActivateResult ALREADY = new PromoCodeHolder.ActivateResult();
    public static final /* enum */ PromoCodeHolder.ActivateResult OUT_OF_DATE = new PromoCodeHolder.ActivateResult();
    public static final /* enum */ PromoCodeHolder.ActivateResult OUT_OF_LIMIT = new PromoCodeHolder.ActivateResult();
    public static final /* enum */ PromoCodeHolder.ActivateResult NOT_FOR_YOU = new PromoCodeHolder.ActivateResult();
    public static final /* enum */ PromoCodeHolder.ActivateResult HWID_LIMITED = new PromoCodeHolder.ActivateResult();
    public static final /* enum */ PromoCodeHolder.ActivateResult IP_LIMITED = new PromoCodeHolder.ActivateResult();
    public static final /* enum */ PromoCodeHolder.ActivateResult MIN_LEVEL_LIMITED = new PromoCodeHolder.ActivateResult();
    public static final /* enum */ PromoCodeHolder.ActivateResult MAX_LEVEL_LIMITED = new PromoCodeHolder.ActivateResult();
    public static final /* enum */ PromoCodeHolder.ActivateResult INVALID_TRY_LATER = new PromoCodeHolder.ActivateResult();
    public static final /* enum */ PromoCodeHolder.ActivateResult OK = new PromoCodeHolder.ActivateResult();
    private static final /* synthetic */ PromoCodeHolder.ActivateResult[] a;

    public static PromoCodeHolder.ActivateResult[] values() {
        return (PromoCodeHolder.ActivateResult[])a.clone();
    }

    public static PromoCodeHolder.ActivateResult valueOf(String string) {
        return Enum.valueOf(PromoCodeHolder.ActivateResult.class, string);
    }

    private static /* synthetic */ PromoCodeHolder.ActivateResult[] a() {
        return new PromoCodeHolder.ActivateResult[]{ALREADY, OUT_OF_DATE, OUT_OF_LIMIT, NOT_FOR_YOU, HWID_LIMITED, IP_LIMITED, MIN_LEVEL_LIMITED, MAX_LEVEL_LIMITED, INVALID_TRY_LATER, OK};
    }

    static {
        a = PromoCodeHolder.ActivateResult.a();
    }
}
