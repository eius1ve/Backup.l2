/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.utils;

import java.util.Comparator;
import l2.gameserver.model.Effect;

public class EffectsComparator
implements Comparator<Effect> {
    private static final EffectsComparator a = new EffectsComparator(1, -1);
    private static final EffectsComparator b = new EffectsComparator(-1, 1);
    protected final int _greater;
    protected final int _smaller;

    public static EffectsComparator getInstance() {
        return a;
    }

    public static EffectsComparator getReverseInstance() {
        return b;
    }

    protected EffectsComparator(int n, int n2) {
        this._greater = n;
        this._smaller = n2;
    }

    @Override
    public int compare(Effect effect, Effect effect2) {
        boolean bl = effect.getSkill().isToggle();
        boolean bl2 = effect2.getSkill().isToggle();
        if (bl && bl2) {
            return this.compareStartTime(effect, effect2);
        }
        if (bl || bl2) {
            if (bl) {
                return this._greater;
            }
            return this._smaller;
        }
        boolean bl3 = effect.getSkill().isMusic();
        boolean bl4 = effect2.getSkill().isMusic();
        if (bl3 && bl4) {
            return this.compareStartTime(effect, effect2);
        }
        if (bl3 || bl4) {
            if (bl3) {
                return this._greater;
            }
            return this._smaller;
        }
        boolean bl5 = effect.isOffensive();
        boolean bl6 = effect2.isOffensive();
        if (bl5 && bl6) {
            return this.compareStartTime(effect, effect2);
        }
        if (bl5 || bl6) {
            if (!bl5) {
                return this._greater;
            }
            return this._smaller;
        }
        boolean bl7 = effect.getSkill().isTrigger();
        boolean bl8 = effect2.getSkill().isTrigger();
        if (bl7 && bl8) {
            return this.compareStartTime(effect, effect2);
        }
        if (bl7 || bl8) {
            if (bl7) {
                return this._greater;
            }
            return this._smaller;
        }
        return this.compareStartTime(effect, effect2);
    }

    protected int compareStartTime(Effect effect, Effect effect2) {
        if (effect.getStartTime() > effect2.getStartTime()) {
            return this._greater;
        }
        if (effect.getStartTime() < effect2.getStartTime()) {
            return this._smaller;
        }
        return 0;
    }
}
