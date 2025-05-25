/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats.conditions;

import l2.gameserver.model.Player;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.conditions.Condition;
import l2.gameserver.templates.item.ArmorTemplate;

public class ConditionUsingArmor
extends Condition {
    private final ArmorTemplate.ArmorType a;

    public ConditionUsingArmor(ArmorTemplate.ArmorType armorType) {
        this.a = armorType;
    }

    @Override
    protected boolean testImpl(Env env) {
        return env.character.isPlayer() && ((Player)env.character).isWearingArmor(this.a);
    }
}
