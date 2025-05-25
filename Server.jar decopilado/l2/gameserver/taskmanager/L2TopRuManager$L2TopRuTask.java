/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.taskmanager;

import l2.gameserver.taskmanager.L2TopRuManager;

private class L2TopRuManager.L2TopRuTask
implements Runnable {
    private L2TopRuManager.L2TopRuTask() {
    }

    @Override
    public void run() {
        L2TopRuManager.getInstance().bZ();
    }
}
