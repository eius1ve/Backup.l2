/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.oly;

import l2.gameserver.model.entity.oly.CompetitionController;
import l2.gameserver.model.entity.oly.CompetitionType;
import l2.gameserver.model.entity.oly.OlyController;

private class CompetitionController.CompetitionStarterTask
implements Runnable {
    private CompetitionController.CompetitionStarterTask() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        try {
            if (!OlyController.getInstance().isRegAllowed()) {
                return;
            }
            for (CompetitionType competitionType : CompetitionType.values()) {
                if (CompetitionController.this.a(competitionType)) {
                    CompetitionController.getInstance().lA = 0;
                    continue;
                }
                if (CompetitionController.getInstance().lA >= 5) continue;
                ++CompetitionController.getInstance().lA;
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        finally {
            CompetitionController.getInstance().scheduleStartTask();
        }
    }
}
