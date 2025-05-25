/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.oly;

import l2.gameserver.model.entity.oly.OlyController;

private class OlyController.CompetitionStartTask
implements Runnable {
    private int lJ;
    private int lL;

    public OlyController.CompetitionStartTask(int n, int n2) {
        this.lJ = n;
        this.lL = n2;
    }

    @Override
    public void run() {
        OlyController.getInstance().c(this.lJ, this.lL);
    }
}
