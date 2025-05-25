/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.oly;

import l2.gameserver.model.entity.oly.OlyController;

private class OlyController.SeasonEndTask
implements Runnable {
    private int lJ;

    public OlyController.SeasonEndTask(int n) {
        this.lJ = n;
    }

    @Override
    public void run() {
        OlyController.getInstance().m(this.lJ);
    }
}
