/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.skills.effects;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.Player;

private class EffectCubic.ActionTask
extends RunnableImpl {
    private EffectCubic.ActionTask() {
    }

    @Override
    public void runImpl() throws Exception {
        Player player;
        if (!EffectCubic.this.isActive()) {
            return;
        }
        Player player2 = player = EffectCubic.this._effected != null && EffectCubic.this._effected.isPlayer() ? (Player)EffectCubic.this._effected : null;
        if (player == null) {
            return;
        }
        EffectCubic.this.doAction(player);
    }
}
