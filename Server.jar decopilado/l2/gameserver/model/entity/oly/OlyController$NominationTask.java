/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.oly;

import l2.gameserver.model.entity.oly.OlyController;

private class OlyController.NominationTask
implements Runnable {
    private int lJ;

    public OlyController.NominationTask(int n) {
        this.lJ = n;
    }

    @Override
    public void run() {
        OlyController.getInstance().n(this.lJ);
    }
}
