/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.residence;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.entity.residence.ResidenceFunction;

private class Residence.AutoTaskForFunctions
extends RunnableImpl {
    ResidenceFunction _function;

    public Residence.AutoTaskForFunctions(ResidenceFunction residenceFunction) {
        this._function = residenceFunction;
    }

    @Override
    public void runImpl() throws Exception {
        Residence.this.a(this._function);
    }
}
