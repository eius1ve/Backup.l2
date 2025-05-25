/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity;

import l2.gameserver.model.entity.MonsterRace;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.DeleteObject;
import l2.gameserver.network.l2.s2c.MonRaceInfo;
import l2.gameserver.network.l2.s2c.SystemMessage;

private class MonsterRace.Announcement
implements Runnable {
    private MonsterRace.Announcement() {
    }

    @Override
    public void run() {
        if (lf > 1200) {
            lf = 0;
        }
        switch (lf) {
            case 0: {
                MonsterRace.this.newRace();
                a = MonsterRace.RaceState.ACCEPTING_BETS;
                a = new MonRaceInfo(i[0][0], i[0][1], MonsterRace.this.getMonsters(), MonsterRace.this.getSpeeds());
                SystemMessage systemMessage = new SystemMessage(SystemMsg.TICKETS_ARE_NOW_AVAILABLE_FOR_MONSTER_RACE_S1);
                systemMessage.addNumber(le);
                MonsterRace.this.sendPacketToPlayersInZones(systemMessage);
                break;
            }
            case 30: 
            case 60: 
            case 90: 
            case 120: 
            case 150: 
            case 180: 
            case 210: 
            case 240: 
            case 270: 
            case 330: 
            case 360: 
            case 390: 
            case 420: 
            case 450: 
            case 480: 
            case 510: 
            case 540: 
            case 570: 
            case 630: 
            case 660: 
            case 690: 
            case 720: 
            case 750: 
            case 780: 
            case 810: 
            case 870: {
                SystemMessage systemMessage = new SystemMessage(SystemMsg.TICKETS_ARE_NOW_AVAILABLE_FOR_MONSTER_RACE_S1);
                a = new MonRaceInfo(i[0][0], i[0][1], MonsterRace.this.getMonsters(), MonsterRace.this.getSpeeds());
                systemMessage.addNumber(le);
                MonsterRace.this.sendPacketToPlayersInZones(systemMessage, a);
                break;
            }
            case 300: {
                SystemMessage systemMessage = new SystemMessage(SystemMsg.NOW_SELLING_TICKETS_FOR_MONSTER_RACE_S1);
                systemMessage.addNumber(le);
                MonsterRace.this.sendPacketToPlayersInZones(systemMessage);
                break;
            }
            case 600: {
                SystemMessage systemMessage = new SystemMessage(SystemMsg.NOW_SELLING_TICKETS_FOR_MONSTER_RACE_S1);
                systemMessage.addNumber(le);
                MonsterRace.this.sendPacketToPlayersInZones(systemMessage);
                break;
            }
            case 840: {
                SystemMessage systemMessage = new SystemMessage(SystemMsg.NOW_SELLING_TICKETS_FOR_MONSTER_RACE_S1);
                systemMessage.addNumber(le);
                MonsterRace.this.sendPacketToPlayersInZones(systemMessage);
                break;
            }
            case 900: {
                a = MonsterRace.RaceState.WAITING;
                MonsterRace.this.bw();
                SystemMessage systemMessage = new SystemMessage(SystemMsg.TICKETS_ARE_NOW_AVAILABLE_FOR_MONSTER_RACE_S1);
                systemMessage.addNumber(le);
                SystemMessage systemMessage2 = new SystemMessage(SystemMsg.TICKETS_SALES_ARE_CLOSED_FOR_MONSTER_RACE_S1_ODDS_ARE_POSTED);
                systemMessage2.addNumber(le);
                MonsterRace.this.sendPacketToPlayersInZones(systemMessage, systemMessage2);
                break;
            }
            case 960: 
            case 1020: {
                int n = lf == 960 ? 2 : 1;
                SystemMessage systemMessage = new SystemMessage(SystemMsg.MONSTER_RACE_S2_WILL_BEGIN_IN_S1_MINUTES);
                systemMessage.addNumber(n);
                systemMessage.addNumber(le);
                MonsterRace.this.sendPacketToPlayersInZones(systemMessage);
                break;
            }
            case 1050: {
                SystemMessage systemMessage = new SystemMessage(SystemMsg.MONSTER_RACE_S1_WILL_BEGIN_IN_30_SECONDS);
                systemMessage.addNumber(le);
                MonsterRace.this.sendPacketToPlayersInZones(systemMessage);
                break;
            }
            case 1070: {
                SystemMessage systemMessage = new SystemMessage(SystemMsg.MONSTER_RACE_S1_IS_ABOUT_TO_BEGIN_COUNTDOWN_IN_FIVE_SECONDS);
                systemMessage.addNumber(le);
                MonsterRace.this.sendPacketToPlayersInZones(systemMessage);
                break;
            }
            case 1075: 
            case 1076: 
            case 1077: 
            case 1078: 
            case 1079: {
                int n = 1080 - lf;
                SystemMessage systemMessage = new SystemMessage(SystemMsg.THE_RACE_WILL_BEGIN_IN_S1_SECONDS);
                systemMessage.addNumber(n);
                MonsterRace.this.sendPacketToPlayersInZones(systemMessage);
                break;
            }
            case 1080: {
                a = MonsterRace.RaceState.STARTING_RACE;
                a = new MonRaceInfo(i[1][0], i[1][1], MonsterRace.this.getMonsters(), MonsterRace.this.getSpeeds());
                MonsterRace.this.sendPacketToPlayersInZones(new SystemMessage(SystemMsg.THEYRE_OFF), a, b, a);
                break;
            }
            case 1085: {
                a = new MonRaceInfo(i[2][0], i[2][1], MonsterRace.this.getMonsters(), MonsterRace.this.getSpeeds());
                MonsterRace.this.sendPacketToPlayersInZones(a);
                break;
            }
            case 1115: {
                a = MonsterRace.RaceState.RACE_END;
                MonsterRace.HistoryInfo historyInfo = bv.get(bv.size() - 1);
                historyInfo.setFirst(MonsterRace.this.getFirstPlace());
                historyInfo.setSecond(MonsterRace.this.getSecondPlace());
                historyInfo.setOddRate(bw.get(MonsterRace.this.getFirstPlace() - 1));
                MonsterRace.this.a(historyInfo);
                MonsterRace.this.bv();
                SystemMessage systemMessage = new SystemMessage(SystemMsg.FIRST_PRIZE_GOES_TO_THE_PLAYER_IN_LANE_S1_SECOND_PRIZE_GOES_TO_THE_PLAYER_IN_LANE_S2);
                systemMessage.addNumber(MonsterRace.this.getFirstPlace());
                systemMessage.addNumber(MonsterRace.this.getSecondPlace());
                SystemMessage systemMessage3 = new SystemMessage(SystemMsg.MONSTER_RACE_S1_IS_FINISHED);
                systemMessage3.addNumber(le);
                MonsterRace.this.sendPacketToPlayersInZones(systemMessage, systemMessage3);
                ++le;
                break;
            }
            case 1140: {
                MonsterRace.this.sendPacketToPlayersInZones(new DeleteObject(MonsterRace.this.getMonsters()[0]), new DeleteObject(MonsterRace.this.getMonsters()[1]), new DeleteObject(MonsterRace.this.getMonsters()[2]), new DeleteObject(MonsterRace.this.getMonsters()[3]), new DeleteObject(MonsterRace.this.getMonsters()[4]), new DeleteObject(MonsterRace.this.getMonsters()[5]), new DeleteObject(MonsterRace.this.getMonsters()[6]), new DeleteObject(MonsterRace.this.getMonsters()[7]));
            }
        }
        ++lf;
    }
}
