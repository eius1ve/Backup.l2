/*
 * Decompiled with CFR 0.152.
 */
package services;

public static final class Buffer.BuffTarget
extends Enum<Buffer.BuffTarget> {
    public static final /* enum */ Buffer.BuffTarget BUFF_PLAYER = new Buffer.BuffTarget();
    public static final /* enum */ Buffer.BuffTarget BUFF_PET = new Buffer.BuffTarget();
    private static final /* synthetic */ Buffer.BuffTarget[] a;

    public static Buffer.BuffTarget[] values() {
        return (Buffer.BuffTarget[])a.clone();
    }

    public static Buffer.BuffTarget valueOf(String string) {
        return Enum.valueOf(Buffer.BuffTarget.class, string);
    }

    private static /* synthetic */ Buffer.BuffTarget[] a() {
        return new Buffer.BuffTarget[]{BUFF_PLAYER, BUFF_PET};
    }

    static {
        a = Buffer.BuffTarget.a();
    }
}
