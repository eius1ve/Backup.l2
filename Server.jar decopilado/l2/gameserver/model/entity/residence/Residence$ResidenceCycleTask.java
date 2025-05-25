/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.residence;

import l2.commons.threading.RunnableImpl;

public class Residence.ResidenceCycleTask
extends RunnableImpl {
    @Override
    public void runImpl() throws Exception {
        Residence.this.chanceCycle();
        Residence.this.update();
    }
}
