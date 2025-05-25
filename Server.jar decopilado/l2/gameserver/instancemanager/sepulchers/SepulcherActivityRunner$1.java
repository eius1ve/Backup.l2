/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.instancemanager.sepulchers;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.instancemanager.sepulchers.SepulcherActivityRunner;

class SepulcherActivityRunner.1
extends RunnableImpl {
    final /* synthetic */ SepulcherActivityRunner.State val$state;

    SepulcherActivityRunner.1(SepulcherActivityRunner.State state) {
        this.val$state = state;
    }

    @Override
    public void runImpl() throws Exception {
        SepulcherActivityRunner.this.a(this.val$state);
    }
}
