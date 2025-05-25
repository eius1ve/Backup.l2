/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats.triggers;

public final class TriggerType
extends Enum<TriggerType> {
    public static final /* enum */ TriggerType ADD = new TriggerType();
    public static final /* enum */ TriggerType ATTACK = new TriggerType();
    public static final /* enum */ TriggerType RECEIVE_DAMAGE = new TriggerType();
    public static final /* enum */ TriggerType CRIT = new TriggerType();
    public static final /* enum */ TriggerType OFFENSIVE_PHYSICAL_SKILL_USE = new TriggerType();
    public static final /* enum */ TriggerType OFFENSIVE_MAGICAL_SKILL_USE = new TriggerType();
    public static final /* enum */ TriggerType SUPPORT_MAGICAL_SKILL_USE = new TriggerType();
    public static final /* enum */ TriggerType UNDER_MISSED_ATTACK = new TriggerType();
    public static final /* enum */ TriggerType DIE = new TriggerType();
    private static final /* synthetic */ TriggerType[] a;

    public static TriggerType[] values() {
        return (TriggerType[])a.clone();
    }

    public static TriggerType valueOf(String string) {
        return Enum.valueOf(TriggerType.class, string);
    }

    private static /* synthetic */ TriggerType[] a() {
        return new TriggerType[]{ADD, ATTACK, RECEIVE_DAMAGE, CRIT, OFFENSIVE_PHYSICAL_SKILL_USE, OFFENSIVE_MAGICAL_SKILL_USE, SUPPORT_MAGICAL_SKILL_USE, UNDER_MISSED_ATTACK, DIE};
    }

    static {
        a = TriggerType.a();
    }
}
