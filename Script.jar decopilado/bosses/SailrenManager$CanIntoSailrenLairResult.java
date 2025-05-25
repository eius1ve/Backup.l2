/*
 * Decompiled with CFR 0.152.
 */
package bosses;

public static final class SailrenManager.CanIntoSailrenLairResult
extends Enum<SailrenManager.CanIntoSailrenLairResult> {
    public static final /* enum */ SailrenManager.CanIntoSailrenLairResult Ok = new SailrenManager.CanIntoSailrenLairResult();
    public static final /* enum */ SailrenManager.CanIntoSailrenLairResult Fail_Awake = new SailrenManager.CanIntoSailrenLairResult();
    public static final /* enum */ SailrenManager.CanIntoSailrenLairResult Fail_InUse = new SailrenManager.CanIntoSailrenLairResult();
    public static final /* enum */ SailrenManager.CanIntoSailrenLairResult Fail_Reborn = new SailrenManager.CanIntoSailrenLairResult();
    public static final /* enum */ SailrenManager.CanIntoSailrenLairResult Fail_NoParty = new SailrenManager.CanIntoSailrenLairResult();
    private static final /* synthetic */ SailrenManager.CanIntoSailrenLairResult[] a;

    public static SailrenManager.CanIntoSailrenLairResult[] values() {
        return (SailrenManager.CanIntoSailrenLairResult[])a.clone();
    }

    public static SailrenManager.CanIntoSailrenLairResult valueOf(String string) {
        return Enum.valueOf(SailrenManager.CanIntoSailrenLairResult.class, string);
    }

    private static /* synthetic */ SailrenManager.CanIntoSailrenLairResult[] a() {
        return new SailrenManager.CanIntoSailrenLairResult[]{Ok, Fail_Awake, Fail_InUse, Fail_Reborn, Fail_NoParty};
    }

    static {
        a = SailrenManager.CanIntoSailrenLairResult.a();
    }
}
