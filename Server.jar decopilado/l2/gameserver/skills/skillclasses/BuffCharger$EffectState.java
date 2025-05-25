/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.skills.skillclasses;

import l2.gameserver.model.Effect;

private static final class BuffCharger.EffectState {
    private final int Di;
    private final long dG;
    private final long dH;

    private BuffCharger.EffectState(int n, long l, long l2) {
        this.Di = n;
        this.dG = l;
        this.dH = l2;
    }

    public BuffCharger.EffectState(Effect effect) {
        this(effect.getCount(), effect.getTime(), effect.getPeriod());
    }

    public void restore(Effect effect) {
        effect.setCount(this.Di);
        effect.setPeriod(this.Di == 1 ? this.dH - this.dG : this.dH);
    }
}
