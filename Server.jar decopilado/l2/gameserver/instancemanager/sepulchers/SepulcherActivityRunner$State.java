/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.instancemanager.sepulchers;

import l2.commons.time.cron.SchedulingPattern;

private static final class SepulcherActivityRunner.State
extends Enum<SepulcherActivityRunner.State> {
    public static final /* enum */ SepulcherActivityRunner.State REGISTRATION_OPEN = new SepulcherActivityRunner.State("55 * * * *");
    public static final /* enum */ SepulcherActivityRunner.State ACTIVE = new SepulcherActivityRunner.State("0 * * * *");
    public static final /* enum */ SepulcherActivityRunner.State COLLAPSE = new SepulcherActivityRunner.State("50 * * * *");
    public static final /* enum */ SepulcherActivityRunner.State MOB_DESPAWN = new SepulcherActivityRunner.State("54 * * * *");
    private final SchedulingPattern b;
    private static final /* synthetic */ SepulcherActivityRunner.State[] a;

    public static SepulcherActivityRunner.State[] values() {
        return (SepulcherActivityRunner.State[])a.clone();
    }

    public static SepulcherActivityRunner.State valueOf(String string) {
        return Enum.valueOf(SepulcherActivityRunner.State.class, string);
    }

    private SepulcherActivityRunner.State(String string2) {
        this.b = new SchedulingPattern(string2);
    }

    public SchedulingPattern getCron() {
        return this.b;
    }

    private static /* synthetic */ SepulcherActivityRunner.State[] a() {
        return new SepulcherActivityRunner.State[]{REGISTRATION_OPEN, ACTIVE, COLLAPSE, MOB_DESPAWN};
    }

    static {
        a = SepulcherActivityRunner.State.a();
    }
}
