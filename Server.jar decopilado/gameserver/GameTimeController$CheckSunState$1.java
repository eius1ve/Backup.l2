/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.GameTimeController;

class GameTimeController.CheckSunState.1
extends RunnableImpl {
    GameTimeController.CheckSunState.1() {
    }

    @Override
    public void runImpl() throws Exception {
        GameTimeController.getInstance().getListenerEngine().onDay();
    }
}
