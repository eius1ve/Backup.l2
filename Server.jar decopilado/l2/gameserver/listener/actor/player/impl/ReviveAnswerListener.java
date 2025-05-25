/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.listener.actor.player.impl;

import l2.commons.lang.reference.HardReference;
import l2.gameserver.listener.actor.player.OnAnswerListener;
import l2.gameserver.model.Player;
import l2.gameserver.model.instances.PetInstance;

public class ReviveAnswerListener
implements OnAnswerListener {
    private HardReference<Player> a;
    private double _power;
    private boolean bk;
    private final long aS;

    public ReviveAnswerListener(Player player, double d, boolean bl, int n) {
        this.a = player.getRef();
        this.bk = bl;
        this._power = d;
        this.aS = n > 0 ? System.currentTimeMillis() + (long)n : Long.MAX_VALUE;
    }

    @Override
    public void sayYes() {
        Player player = this.a.get();
        if (player == null) {
            return;
        }
        if (System.currentTimeMillis() > this.aS) {
            return;
        }
        if (!player.isDead() && !this.bk || this.bk && player.getPet() != null && !player.getPet().isDead()) {
            return;
        }
        if (!this.bk) {
            player.doRevive(this._power);
        } else if (player.getPet() != null) {
            ((PetInstance)player.getPet()).doRevive(this._power);
        }
    }

    @Override
    public void sayNo() {
    }

    public double getPower() {
        return this._power;
    }

    public boolean isForPet() {
        return this.bk;
    }
}
