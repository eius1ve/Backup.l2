/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.oly;

import l2.gameserver.model.entity.oly.OlyController;

private class OlyController.CompetitionEndTask
implements Runnable {
    private int lJ;
    private int lL;

    public OlyController.CompetitionEndTask(int n, int n2) {
        this.lJ = n;
        this.lL = n2;
    }

    @Override
    public void run() {
        OlyController.getInstance().d(this.lJ, this.lL);
    }
}
