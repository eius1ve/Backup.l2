/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver;

import l2.gameserver.GameTimeController;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.listener.game.OnStartListener;

class GameTimeController.1
implements OnStartListener {
    GameTimeController.1() {
    }

    @Override
    public void onStart() {
        ThreadPoolManager.getInstance().execute(new GameTimeController.CheckSunState());
    }
}
