/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.actor.player;

import l2.gameserver.Config;
import l2.gameserver.model.actor.instances.player.AutoFarmContext;

public class AutoFarmEndTask
implements Runnable {
    private final AutoFarmContext b;

    public AutoFarmEndTask(AutoFarmContext autoFarmContext) {
        this.b = autoFarmContext;
    }

    @Override
    public void run() {
        if (this.b != null) {
            this.b.setAutoFarmEndTask(0L);
            this.b.stopFarmTask(false);
            if (Config.AUTOFARM_TIME_TRACK_USAGE_ONLY) {
                this.b.checkFarmTask();
            }
        }
    }
}
