/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats.conditions;

public static final class ConditionPlayerState.CheckPlayerState
extends Enum<ConditionPlayerState.CheckPlayerState> {
    public static final /* enum */ ConditionPlayerState.CheckPlayerState RESTING = new ConditionPlayerState.CheckPlayerState();
    public static final /* enum */ ConditionPlayerState.CheckPlayerState MOVING = new ConditionPlayerState.CheckPlayerState();
    public static final /* enum */ ConditionPlayerState.CheckPlayerState RUNNING = new ConditionPlayerState.CheckPlayerState();
    public static final /* enum */ ConditionPlayerState.CheckPlayerState STANDING = new ConditionPlayerState.CheckPlayerState();
    public static final /* enum */ ConditionPlayerState.CheckPlayerState FLYING = new ConditionPlayerState.CheckPlayerState();
    public static final /* enum */ ConditionPlayerState.CheckPlayerState FLYING_TRANSFORM = new ConditionPlayerState.CheckPlayerState();
    private static final /* synthetic */ ConditionPlayerState.CheckPlayerState[] a;

    public static ConditionPlayerState.CheckPlayerState[] values() {
        return (ConditionPlayerState.CheckPlayerState[])a.clone();
    }

    public static ConditionPlayerState.CheckPlayerState valueOf(String string) {
        return Enum.valueOf(ConditionPlayerState.CheckPlayerState.class, string);
    }

    private static /* synthetic */ ConditionPlayerState.CheckPlayerState[] a() {
        return new ConditionPlayerState.CheckPlayerState[]{RESTING, MOVING, RUNNING, STANDING, FLYING, FLYING_TRANSFORM};
    }

    static {
        a = ConditionPlayerState.CheckPlayerState.a();
    }
}
