/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.oly;

import l2.gameserver.Config;
import l2.gameserver.model.entity.oly.Competition;
import l2.gameserver.model.entity.oly.CompetitionController;
import l2.gameserver.model.entity.oly.CompetitionState;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.PlaySound;
import l2.gameserver.network.l2.s2c.SystemMessage;

public class CompetitionController.CompetitionPreparationTask
implements Runnable {
    private Competition a;
    private int lB;

    public CompetitionController.CompetitionPreparationTask(Competition competition) {
        this(competition, 60);
    }

    public CompetitionController.CompetitionPreparationTask(Competition competition, int n) {
        this.a = competition;
        this.lB = n;
    }

    @Override
    public void run() {
        if (this.lB > 0) {
            if (this.lB < 10 || this.lB % 10 == 0) {
                this.a.broadcastPacket((IStaticPacket)new SystemMessage(SystemMsg.THE_MATCH_WILL_START_IN_S1_SECONDS).addNumber(this.lB), true, true);
            }
            long l = 1000L;
            switch (this.lB) {
                case 60: {
                    this.a.getStadium().spawnAllNpcs();
                }
                case 55: {
                    this.lB -= 5;
                    l = 5000L;
                    break;
                }
                case 20: 
                case 30: 
                case 40: 
                case 50: {
                    this.lB -= 10;
                    l = 10000L;
                    break;
                }
                case 10: {
                    this.a.getStadium().despawnAllNpcs();
                    this.a.getStadium().openDoors();
                    this.lB -= 5;
                    l = 5000L;
                    break;
                }
                case 5: {
                    this.a.applyBuffsIfNoOlympiadBufferInstance();
                }
                case 1: 
                case 2: 
                case 3: 
                case 4: {
                    --this.lB;
                    l = 1000L;
                }
            }
            this.a.scheduleTask(new CompetitionController.CompetitionPreparationTask(this.a, this.lB), this.lB > 0 ? l : 2000L);
        } else {
            this.a.getStadium().setZonesActive(true);
            if (Config.OLY_RESTORE_HPCPMP_ON_START_MATCH) {
                this.a.restoreHPCPMP();
            }
            this.a.broadcastEverybodyOlympiadUserInfo();
            this.a.broadcastEverybodyEffectIcons();
            this.a.broadcastPacket(new PlaySound("ns17_f"), true, true);
            this.a.broadcastPacket(SystemMsg.THE_MATCH_HAS_STARTED, true, true);
            this.a.setState(CompetitionState.PLAYING);
            CompetitionController.getInstance().scheduleFinishCompetition(this.a, -1, Config.OLYMPIAD_COMPETITION_TIME);
        }
    }
}
