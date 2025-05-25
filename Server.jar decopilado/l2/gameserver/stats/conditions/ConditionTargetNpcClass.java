/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats.conditions;

import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.scripts.Scripts;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.conditions.Condition;

public class ConditionTargetNpcClass
extends Condition {
    private final Class<NpcInstance> b;

    public ConditionTargetNpcClass(String string) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName("l2.gameserver.model.instances." + string + "Instance");
        }
        catch (ClassNotFoundException classNotFoundException) {
            clazz = Scripts.getInstance().getClasses().get("npc.model." + string + "Instance");
        }
        if (clazz == null) {
            throw new IllegalArgumentException("Not found type class for type: " + string + ".");
        }
        this.b = clazz;
    }

    @Override
    protected boolean testImpl(Env env) {
        return env.target != null && env.target.getClass() == this.b;
    }
}
