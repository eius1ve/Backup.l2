/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.GameTimeController;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ClientSetTime;

private static class GameTimeController.CheckSunState
extends RunnableImpl {
    private GameTimeController.DayPart a;

    private GameTimeController.CheckSunState() {
    }

    private void ai() {
        Calendar calendar = Calendar.getInstance();
        long l = (long)calendar.get(14) + TimeUnit.SECONDS.toMillis(calendar.get(13)) + TimeUnit.MINUTES.toMillis(calendar.get(12));
        long l2 = TimeUnit.MINUTES.toMillis(10L) - l % TimeUnit.MINUTES.toMillis(10L);
        ThreadPoolManager.getInstance().schedule(this, l2);
    }

    @Override
    public void runImpl() throws Exception {
        GameTimeController.DayPart dayPart = GameTimeController.DayPart.now();
        if (this.a != dayPart) {
            this.a = dayPart;
            switch (this.a) {
                case DAY: {
                    ThreadPoolManager.getInstance().execute(new RunnableImpl(){

                        @Override
                        public void runImpl() throws Exception {
                            GameTimeController.getInstance().getListenerEngine().onDay();
                        }
                    });
                    break;
                }
                case NIGHT: {
                    ThreadPoolManager.getInstance().execute(new RunnableImpl(){

                        @Override
                        public void runImpl() throws Exception {
                            GameTimeController.getInstance().getListenerEngine().onNight();
                        }
                    });
                }
            }
            ThreadPoolManager.getInstance().execute(new RunnableImpl(){

                @Override
                public void runImpl() throws Exception {
                    for (Player player : GameObjectsStorage.getAllPlayersForIterate()) {
                        try {
                            player.checkDayNightMessages();
                            player.sendPacket((IStaticPacket)ClientSetTime.STATIC);
                        }
                        catch (Exception exception) {}
                    }
                }
            });
        }
        this.ai();
    }
}
