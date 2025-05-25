/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.instancemanager.games;

import l2.commons.threading.RunnableImpl;

private class FishingChampionShipManager.needRefresh
extends RunnableImpl {
    private FishingChampionShipManager.needRefresh() {
    }

    @Override
    public void runImpl() throws Exception {
        FishingChampionShipManager.this.bh = true;
    }
}
