/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.tuple.Pair
 */
package l2.gameserver.model.entity;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.instancemanager.RaidBossSpawnManager;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.SevenSigns;
import l2.gameserver.model.entity.SevenSignsFestival.SevenSignsFestival;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SSQInfo;
import l2.gameserver.utils.Util;
import org.apache.commons.lang3.tuple.Pair;

public class SevenSigns.SevenSignsPeriodChange
extends RunnableImpl {
    @Override
    public void runImpl() throws Exception {
        _log.info("SevenSignsPeriodChange: old=" + SevenSigns.this._activePeriod);
        int n = SevenSigns.this._activePeriod++;
        switch (n) {
            case 0: {
                SevenSigns.this.sendMessageToAll(SystemMsg.SEVEN_SIGNS_THE_COMPETITION_PERIOD_HAS_BEGUN__VISIT_A_PRIEST_OF_DAWN_OR_PRIESTESS_OF_DUSK_TO_PARTICIPATE_IN_THE_EVENT);
                RaidBossSpawnManager.getInstance().distributeRewards();
                break;
            }
            case 1: {
                SevenSigns.this.sendMessageToAll(SystemMsg.SEVEN_SIGNS_THE_COMPETITION_PERIOD_HAS_ENDED_THE_NEXT_QUEST_EVENT_WILL_START_IN_ONE_WEEK);
                int n2 = SevenSigns.this.getCabalHighestScore();
                SevenSigns.this.calcNewSealOwners();
                if (n2 == 1) {
                    SevenSigns.this.sendMessageToAll(SystemMsg.SEVEN_SIGNS_THE_REVOLUTIONARIES_OF_DUSK_HAVE_WON);
                } else {
                    SevenSigns.this.sendMessageToAll(SystemMsg.SEVEN_SIGNS_THE_LORDS_OF_DAWN_HAVE_WON);
                }
                SevenSigns.this._previousWinner = n2;
                break;
            }
            case 2: {
                SevenSignsFestival.getInstance().distribAccumulatedBonus();
                SevenSignsFestival.getInstance().rewardHighestRanked();
                SevenSigns.this.initializeSeals();
                RaidBossSpawnManager.getInstance().distributeRewards();
                SevenSigns.this.sendMessageToAll(SystemMsg.SEVEN_SIGNS_THE_SEAL_VALIDATION_PERIOD_HAS_BEGUN);
                _log.info("SevenSigns: The " + SevenSigns.getCabalName(SevenSigns.this._previousWinner) + " have won the competition with " + SevenSigns.this.getCurrentScore(SevenSigns.this._previousWinner) + " points!");
                break;
            }
            case 3: {
                SevenSigns.this._activePeriod = 0;
                SevenSigns.this.sendMessageToAll(SystemMsg.SEVEN_SIGNS_THE_SEAL_VALIDATION_PERIOD_HAS_ENDED);
                SevenSigns.this.resetPlayerData();
                SevenSigns.this.resetSeals();
                SevenSigns.this._dawnStoneScore = 0L;
                SevenSigns.this._duskStoneScore = 0L;
                SevenSigns.this.c.set((Pair<Long, Long>)Pair.of((Object)0L, (Object)0L));
                ++SevenSigns.this._currentCycle;
                SevenSignsFestival.getInstance().resetFestivalData(false);
            }
        }
        SevenSigns.this.saveSevenSignsData(0, true);
        _log.info("SevenSignsPeriodChange: new=" + SevenSigns.this._activePeriod);
        try {
            _log.info("SevenSigns: Change Catacomb spawn...");
            SevenSigns.this.getListenerEngine().onPeriodChange();
            SSQInfo sSQInfo = new SSQInfo();
            for (Player player : GameObjectsStorage.getAllPlayersForIterate()) {
                player.sendPacket((IStaticPacket)sSQInfo);
            }
            _log.info("SevenSigns: Spawning NPCs...");
            _log.info("SevenSigns: The " + SevenSigns.this.getCurrentPeriodName() + " period has begun!");
            _log.info("SevenSigns: Calculating next period change time...");
            SevenSigns.this.setCalendarForNextPeriodChange();
            _log.info("SevenSignsPeriodChange: time to next change=" + Util.formatTime((int)(SevenSigns.this.getMilliToPeriodChange() / 1000L)));
            SevenSigns.SevenSignsPeriodChange sevenSignsPeriodChange = new SevenSigns.SevenSignsPeriodChange();
            SevenSigns.this.U = ThreadPoolManager.getInstance().schedule(sevenSignsPeriodChange, SevenSigns.this.getMilliToPeriodChange());
        }
        catch (Exception exception) {
            _log.error("", (Throwable)exception);
        }
    }
}
