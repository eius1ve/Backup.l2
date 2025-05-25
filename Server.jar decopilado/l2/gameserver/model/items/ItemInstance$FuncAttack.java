/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.items;

import l2.gameserver.model.base.Element;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.funcs.Func;

public class ItemInstance.FuncAttack
extends Func {
    private final Element a;

    public ItemInstance.FuncAttack(Element element, int n, Object object) {
        super(element.getAttack(), n, object);
        this.a = element;
    }

    @Override
    public void calc(Env env) {
        env.value += (double)ItemInstance.this.getAttributeElementValue(this.a, true);
    }
}
