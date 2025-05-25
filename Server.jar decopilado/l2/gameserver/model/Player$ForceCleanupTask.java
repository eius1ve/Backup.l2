/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import l2.gameserver.ThreadPoolManager;

private class Player.ForceCleanupTask
implements Runnable {
    private Player.ForceCleanupTask() {
    }

    @Override
    public void run() {
        long l = 600000L - (System.currentTimeMillis() - Player.this.by);
        if (l > 1000L) {
            Player.this.w = ThreadPoolManager.getInstance().schedule(new Player.ForceCleanupTask(), l);
            return;
        }
        Player.this.iK = 0;
        Player.this.sendEtcStatusUpdate();
        Player.this.w = null;
    }
}
