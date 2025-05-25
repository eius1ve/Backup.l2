/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.skills.effects;

import l2.gameserver.model.Effect;
import l2.gameserver.model.Player;
import l2.gameserver.skills.effects.EffectTemplate;
import l2.gameserver.skills.skillclasses.Transformation;
import l2.gameserver.stats.Env;

public final class EffectTransformation
extends Effect {
    private final boolean fM;
    private final boolean fN;

    public EffectTransformation(Env env, EffectTemplate effectTemplate) {
        super(env, effectTemplate);
        int n = (int)effectTemplate._value;
        this.fM = effectTemplate.getParam().getBool("isFlyingTransform", n == 8 || n == 9 || n == 260);
        this.fN = effectTemplate.getParam().getBool("isMountTransform", false);
    }

    @Override
    public boolean checkCondition() {
        if (!this._effected.isPlayer()) {
            return false;
        }
        if (this.fM && this._effected.getX() > -166168) {
            return false;
        }
        return super.checkCondition();
    }

    @Override
    public void onStart() {
        super.onStart();
        Player player = (Player)this._effected;
        player.setTransformationTemplate(this.getSkill().getNpcId());
        if (this.getSkill() instanceof Transformation) {
            player.setTransformationName(((Transformation)this.getSkill()).transformationName);
        }
        if (this.fN) {
            player.setInMountTransform(true);
        }
        int n = (int)this.calc();
        if (this.fM) {
            boolean bl = player.isVisible();
            if (player.getPet() != null) {
                player.getPet().unSummon();
            }
            player.decayMe();
            player.setFlying(true);
            player.setLoc(player.getLoc().changeZ(300));
            player.setTransformation(n);
            if (bl) {
                player.spawnMe();
            }
        } else {
            player.setTransformation(n);
        }
    }

    @Override
    public void onExit() {
        super.onExit();
        if (this._effected.isPlayer()) {
            Player player = (Player)this._effected;
            if (this.getSkill() instanceof Transformation) {
                player.setTransformationName(null);
            }
            if (this.fN) {
                player.setInMountTransform(false);
            }
            if (this.fM) {
                boolean bl = player.isVisible();
                player.decayMe();
                player.setFlying(false);
                player.setLoc(player.getLoc().correctGeoZ());
                player.setTransformation(0);
                if (bl) {
                    player.spawnMe();
                }
            } else {
                player.setTransformation(0);
            }
        }
    }

    @Override
    public boolean onActionTime() {
        return false;
    }
}
