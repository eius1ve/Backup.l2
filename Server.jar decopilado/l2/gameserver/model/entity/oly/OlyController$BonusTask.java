/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.oly;

import l2.gameserver.model.entity.oly.OlyController;

private class OlyController.BonusTask
implements Runnable {
    private int lJ;
    private int lK;

    public OlyController.BonusTask(int n, int n2) {
        this.lJ = n;
        this.lK = n2;
    }

    @Override
    public void run() {
        OlyController.getInstance().e(this.lJ, this.lK);
    }
}
