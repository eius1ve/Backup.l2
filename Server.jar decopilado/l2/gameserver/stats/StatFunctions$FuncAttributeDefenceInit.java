/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.stats;

import l2.gameserver.model.base.Element;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.funcs.Func;

private static class StatFunctions.FuncAttributeDefenceInit
extends Func {
    static final Func[] func = new StatFunctions.FuncAttributeDefenceInit[Element.VALUES.length];
    private Element a;

    static Func getFunc(Element element) {
        return func[element.getId()];
    }

    private StatFunctions.FuncAttributeDefenceInit(Element element) {
        super(element.getDefence(), 1, null);
        this.a = element;
    }

    @Override
    public void calc(Env env) {
        env.value += (double)env.character.getTemplate().baseAttributeDefence[this.a.getId()];
    }

    static {
        for (int i = 0; i < Element.VALUES.length; ++i) {
            StatFunctions.FuncAttributeDefenceInit.func[i] = new StatFunctions.FuncAttributeDefenceInit(Element.VALUES[i]);
        }
    }
}
