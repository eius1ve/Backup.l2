/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.instancemanager.games;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.instancemanager.games.FishingChampionShipManager;

private class FishingChampionShipManager.finishChamp
extends RunnableImpl {
    private FishingChampionShipManager.finishChamp() {
    }

    @Override
    public void runImpl() throws Exception {
        FishingChampionShipManager.this.aE.clear();
        for (FishingChampionShipManager.Fisher fisher : FishingChampionShipManager.this.aD) {
            fisher.setRewardType(1);
            FishingChampionShipManager.this.aE.add(fisher);
        }
        FishingChampionShipManager.this.aD.clear();
        FishingChampionShipManager.this.aG();
        FishingChampionShipManager.this.aC();
        FishingChampionShipManager.this.shutdown();
        _log.info("Fishing Championship Manager : start new event period.");
        ThreadPoolManager.getInstance().schedule(new FishingChampionShipManager.finishChamp(), FishingChampionShipManager.this._enddate - System.currentTimeMillis());
    }
}
