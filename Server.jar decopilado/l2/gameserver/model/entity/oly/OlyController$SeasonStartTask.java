/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.oly;

import l2.gameserver.model.entity.oly.OlyController;

private class OlyController.SeasonStartTask
implements Runnable {
    private int lJ;

    public OlyController.SeasonStartTask(int n) {
        this.lJ = n;
    }

    @Override
    public void run() {
        OlyController.getInstance().l(this.lJ);
    }
}
