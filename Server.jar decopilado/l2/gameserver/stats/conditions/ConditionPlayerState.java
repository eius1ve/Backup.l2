/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats.conditions;

import l2.gameserver.model.Player;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.conditions.Condition;

public class ConditionPlayerState
extends Condition {
    private final CheckPlayerState a;
    private final boolean gu;

    public ConditionPlayerState(CheckPlayerState checkPlayerState, boolean bl) {
        this.a = checkPlayerState;
        this.gu = bl;
    }

    @Override
    protected boolean testImpl(Env env) {
        switch (this.a) {
            case RESTING: {
                if (env.character.isPlayer()) {
                    return ((Player)env.character).isSitting() == this.gu;
                }
                return !this.gu;
            }
            case MOVING: {
                return env.character.isMoving() == this.gu;
            }
            case RUNNING: {
                return (env.character.isMoving() && env.character.isRunning()) == this.gu;
            }
            case STANDING: {
                if (env.character.isPlayer()) {
                    return ((Player)env.character).isSitting() != this.gu && env.character.isMoving() != this.gu;
                }
                return env.character.isMoving() != this.gu;
            }
            case FLYING: {
                if (env.character.isPlayer()) {
                    return env.character.isFlying() == this.gu;
                }
                return !this.gu;
            }
            case FLYING_TRANSFORM: {
                if (env.character.isPlayer()) {
                    return ((Player)env.character).isInFlyingTransform() == this.gu;
                }
                return !this.gu;
            }
        }
        return !this.gu;
    }

    public static final class CheckPlayerState
    extends Enum<CheckPlayerState> {
        public static final /* enum */ CheckPlayerState RESTING = new CheckPlayerState();
        public static final /* enum */ CheckPlayerState MOVING = new CheckPlayerState();
        public static final /* enum */ CheckPlayerState RUNNING = new CheckPlayerState();
        public static final /* enum */ CheckPlayerState STANDING = new CheckPlayerState();
        public static final /* enum */ CheckPlayerState FLYING = new CheckPlayerState();
        public static final /* enum */ CheckPlayerState FLYING_TRANSFORM = new CheckPlayerState();
        private static final /* synthetic */ CheckPlayerState[] a;

        public static CheckPlayerState[] values() {
            return (CheckPlayerState[])a.clone();
        }

        public static CheckPlayerState valueOf(String string) {
            return Enum.valueOf(CheckPlayerState.class, string);
        }

        private static /* synthetic */ CheckPlayerState[] a() {
            return new CheckPlayerState[]{RESTING, MOVING, RUNNING, STANDING, FLYING, FLYING_TRANSFORM};
        }

        static {
            a = CheckPlayerState.a();
        }
    }
}
