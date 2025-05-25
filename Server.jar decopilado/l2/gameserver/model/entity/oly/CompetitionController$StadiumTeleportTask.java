/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.oly;

import l2.gameserver.Config;
import l2.gameserver.model.entity.oly.Competition;
import l2.gameserver.model.entity.oly.CompetitionController;
import l2.gameserver.model.entity.oly.CompetitionState;
import l2.gameserver.model.entity.oly.OlyController;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;

public class CompetitionController.StadiumTeleportTask
implements Runnable {
    private Competition a;
    private int lB;

    public CompetitionController.StadiumTeleportTask(Competition competition) {
        this(competition, Config.OLYMPIAD_STADIUM_TELEPORT_DELAY);
    }

    public CompetitionController.StadiumTeleportTask(Competition competition, int n) {
        this.a = competition;
        this.lB = n;
        if (this.a.getState() == null) {
            this.a.setState(CompetitionState.INIT);
        }
    }

    @Override
    public void run() {
        if (this.lB > 0) {
            this.a.broadcastPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_WILL_BE_MOVED_TO_THE_OLYMPIAD_STADIUM_IN_S1_SECONDS).addNumber(this.lB), true, false);
            long l = 1000L;
            switch (this.lB) {
                case 30: 
                case 45: {
                    this.lB -= 15;
                    l = 15000L;
                    break;
                }
                case 15: {
                    this.lB = 5;
                    l = 5000L;
                    break;
                }
                case 1: 
                case 2: 
                case 3: 
                case 4: 
                case 5: {
                    --this.lB;
                    l = 1000L;
                }
            }
            if (this.a.ValidateParticipants()) {
                return;
            }
            this.a.scheduleTask(new CompetitionController.StadiumTeleportTask(this.a, this.lB), this.lB > 0 ? l : 1000L);
        } else {
            if (this.a.ValidateParticipants()) {
                return;
            }
            this.a.getStadium().setZonesActive(false);
            this.a.teleportParticipantsOnStadium();
            this.a.setState(CompetitionState.STAND_BY);
            CompetitionController.getInstance().scheduleCompetitionPreparation(this.a);
            OlyController.getInstance().announceCompetition(this.a.getType(), this.a.getStadium().getStadiumId());
        }
    }
}
