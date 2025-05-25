/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats;

import l2.gameserver.model.base.Element;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.funcs.Func;

private static class StatFunctions.FuncAttributeAttackInit
extends Func {
    static final Func[] func = new StatFunctions.FuncAttributeAttackInit[Element.VALUES.length];
    private Element a;

    static Func getFunc(Element element) {
        return func[element.getId()];
    }

    private StatFunctions.FuncAttributeAttackInit(Element element) {
        super(element.getAttack(), 1, null);
        this.a = element;
    }

    @Override
    public void calc(Env env) {
        env.value += (double)env.character.getTemplate().baseAttributeAttack[this.a.getId()];
    }

    static {
        for (int i = 0; i < Element.VALUES.length; ++i) {
            StatFunctions.FuncAttributeAttackInit.func[i] = new StatFunctions.FuncAttributeAttackInit(Element.VALUES[i]);
        }
    }
}
