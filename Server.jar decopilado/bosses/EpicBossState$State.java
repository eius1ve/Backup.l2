/*
 * Decompiled with CFR 0.152.
 */
package bosses;

public static final class EpicBossState.State
extends Enum<EpicBossState.State> {
    public static final /* enum */ EpicBossState.State NOTSPAWN = new EpicBossState.State();
    public static final /* enum */ EpicBossState.State ALIVE = new EpicBossState.State();
    public static final /* enum */ EpicBossState.State DEAD = new EpicBossState.State();
    public static final /* enum */ EpicBossState.State INTERVAL = new EpicBossState.State();
    private static final /* synthetic */ EpicBossState.State[] a;

    public static EpicBossState.State[] values() {
        return (EpicBossState.State[])a.clone();
    }

    public static EpicBossState.State valueOf(String string) {
        return Enum.valueOf(EpicBossState.State.class, string);
    }

    private static /* synthetic */ EpicBossState.State[] a() {
        return new EpicBossState.State[]{NOTSPAWN, ALIVE, DEAD, INTERVAL};
    }

    static {
        a = EpicBossState.State.a();
    }
}
