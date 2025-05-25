/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.oly;

import l2.gameserver.model.entity.oly.Competition;
import l2.gameserver.model.entity.oly.CompetitionController;
import l2.gameserver.model.entity.oly.CompetitionState;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;

public class CompetitionController.FinishCompetitionTask
implements Runnable {
    private Competition a;
    private int lB;

    public CompetitionController.FinishCompetitionTask(Competition competition, int n) {
        this.a = competition;
        this.lB = n;
    }

    @Override
    public void run() {
        if (this.a.getState() != CompetitionState.FINISH) {
            this.a.setState(CompetitionState.FINISH);
            this.a.ValidateWinner();
            this.a.scheduleTask(new CompetitionController.FinishCompetitionTask(this.a, 20), 100L);
        } else if (this.a.getState() == CompetitionState.FINISH) {
            if (this.lB > 0) {
                this.a.broadcastPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_WILL_BE_MOVED_BACK_TO_TOWN_IN_S1_SECONDS).addNumber(this.lB), true, false);
                int n = this.lB > 5 ? this.lB / 2 : 1;
                this.lB -= n;
                this.a.scheduleTask(new CompetitionController.FinishCompetitionTask(this.a, this.lB), n * 1000);
            } else {
                CompetitionController.getInstance().FinishCompetition(this.a);
            }
        }
    }
}
