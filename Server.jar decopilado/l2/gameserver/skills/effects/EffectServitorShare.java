/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.skills.effects;

import java.util.Map;
import java.util.function.BiFunction;
import l2.gameserver.model.Effect;
import l2.gameserver.model.Player;
import l2.gameserver.model.Summon;
import l2.gameserver.skills.effects.EffectTemplate;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.Stats;
import l2.gameserver.stats.funcs.Func;
import l2.gameserver.stats.funcs.FuncTemplate;
import l2.gameserver.utils.MapUtils;

public class EffectServitorShare
extends Effect {
    private static final Map<Stats, BiFunction<Player, Env, Integer>> bH = MapUtils.mapBuilder().add(Stats.POWER_ATTACK, (player, env) -> player.getPAtk(env.target)).add(Stats.POWER_DEFENCE, (player, env) -> player.getPDef(env.target)).add(Stats.MAGIC_ATTACK, (player, env) -> player.getMAtk(env.target, null)).add(Stats.MAGIC_DEFENCE, (player, env) -> player.getMDef(env.target, null)).add(Stats.MAX_HP, (player, env) -> player.getMaxHp()).add(Stats.MAX_MP, (player, env) -> player.getMaxMp()).add(Stats.CRITICAL_RATE, (player, env) -> (int)player.calcStat(Stats.CRITICAL_RATE, env.target, env.skill)).add(Stats.POWER_ATTACK_SPEED, (player, env) -> player.getPAtkSpd()).add(Stats.MAGIC_ATTACK_SPEED, (player, env) -> player.getMAtkSpd()).build();

    public EffectServitorShare(Env env, EffectTemplate effectTemplate) {
        super(env, effectTemplate);
    }

    @Override
    protected void onStart() {
        Summon summon;
        super.onStart();
        if (this._effector.isPlayer() && !this._effector.isAlikeDead() && (summon = this._effector.getPet()) != null && !summon.isAlikeDead()) {
            summon.addStatFuncs(this.a());
        }
    }

    @Override
    protected void onExit() {
        Summon summon;
        if (this._effector.isPlayer() && !this._effector.isAlikeDead() && (summon = this._effector.getPet()) != null && !summon.isAlikeDead()) {
            summon.removeStatsOwner(this);
        }
        super.onExit();
    }

    @Override
    public Func[] getStatFuncs() {
        return Func.EMPTY_FUNC_ARRAY;
    }

    private Func[] a() {
        FuncTemplate[] funcTemplateArray = this.getTemplate().getAttachedFuncs();
        Func[] funcArray = new Func[funcTemplateArray.length];
        for (int i = 0; i < funcArray.length; ++i) {
            funcArray[i] = new FuncShare(funcTemplateArray[i]._stat, funcTemplateArray[i]._order, this, funcTemplateArray[i]._value);
        }
        return funcArray;
    }

    @Override
    protected boolean onActionTime() {
        return false;
    }

    public class FuncShare
    extends Func {
        public FuncShare(Stats stats, int n, Object object, double d) {
            super(stats, n, object, d);
        }

        @Override
        public void calc(Env env) {
            BiFunction<Player, Env, Integer> biFunction = bH.get((Object)this.stat);
            if (biFunction != null) {
                env.value += (double)biFunction.apply(env.character.getPlayer(), env).intValue() * this.value;
            }
        }
    }
}
