/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.skills.effects;

import l2.gameserver.model.Effect;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.LockType;
import l2.gameserver.skills.effects.EffectTemplate;
import l2.gameserver.stats.Env;

public class EffectLockInventory
extends Effect {
    private LockType a;
    private int[] aN;

    public EffectLockInventory(Env env, EffectTemplate effectTemplate) {
        super(env, effectTemplate);
        this.a = effectTemplate.getParam().getEnum("lockType", LockType.class);
        this.aN = effectTemplate.getParam().getIntegerArray("lockItems");
    }

    @Override
    public void onStart() {
        super.onStart();
        Player player = this._effector.getPlayer();
        player.getInventory().lockItems(this.a, this.aN);
    }

    @Override
    public void onExit() {
        super.onExit();
        Player player = this._effector.getPlayer();
        player.getInventory().unlock();
    }

    @Override
    protected boolean onActionTime() {
        return false;
    }
}
