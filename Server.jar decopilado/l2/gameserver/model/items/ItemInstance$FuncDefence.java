/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.items;

import l2.gameserver.model.base.Element;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.funcs.Func;

public class ItemInstance.FuncDefence
extends Func {
    private final Element b;

    public ItemInstance.FuncDefence(Element element, int n, Object object) {
        super(element.getDefence(), n, object);
        this.b = element;
    }

    @Override
    public void calc(Env env) {
        env.value += (double)ItemInstance.this.getAttributeElementValue(this.b, true);
    }
}
