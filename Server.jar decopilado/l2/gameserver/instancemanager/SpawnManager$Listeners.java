/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.instancemanager;

import l2.gameserver.Config;
import l2.gameserver.instancemanager.SpawnManager;
import l2.gameserver.listener.game.OnDayNightChangeListener;
import l2.gameserver.listener.game.OnSSPeriodListener;
import l2.gameserver.model.entity.SevenSigns;
import l2.gameserver.templates.spawn.PeriodOfDay;

private class SpawnManager.Listeners
implements OnDayNightChangeListener,
OnSSPeriodListener {
    private SpawnManager.Listeners() {
    }

    @Override
    public void onDay() {
        SpawnManager.this.despawn(PeriodOfDay.NIGHT.name());
        SpawnManager.this.spawn(PeriodOfDay.DAY.name());
    }

    @Override
    public void onNight() {
        SpawnManager.this.despawn(PeriodOfDay.DAY.name());
        SpawnManager.this.spawn(PeriodOfDay.NIGHT.name());
    }

    @Override
    public void onPeriodChange(int n) {
        SpawnManager.this.despawn(SpawnManager.cp);
        SpawnManager.this.despawn(SpawnManager.cq);
        SpawnManager.this.despawn(SpawnManager.cr);
        SpawnManager.this.despawn(SpawnManager.cs);
        SpawnManager.this.despawn(SpawnManager.ct);
        SpawnManager.this.despawn(SpawnManager.cu);
        SpawnManager.this.despawn(SpawnManager.cv);
        block0 : switch (SevenSigns.getInstance().getCurrentPeriod()) {
            case 0: 
            case 2: {
                if (!Config.ALT_SEVEN_SING_STATIC_EVENT_PERIOD_SPAWN) break;
                SpawnManager.this.spawn(SpawnManager.cp);
                break;
            }
            case 1: {
                SpawnManager.this.spawn(SpawnManager.cp);
                break;
            }
            case 3: {
                if (Config.ALT_SEVEN_SING_STATIC_EVENT_PERIOD_SPAWN) {
                    SpawnManager.this.spawn(SpawnManager.cp);
                    break;
                }
                switch (SevenSigns.getInstance().getSealOwner(1)) {
                    case 0: {
                        SpawnManager.this.spawn(SpawnManager.cq);
                        break;
                    }
                    case 1: {
                        SpawnManager.this.spawn(SpawnManager.cr);
                        break;
                    }
                    case 2: {
                        SpawnManager.this.spawn(SpawnManager.cs);
                    }
                }
                switch (SevenSigns.getInstance().getSealOwner(2)) {
                    case 0: {
                        SpawnManager.this.spawn(SpawnManager.ct);
                        break block0;
                    }
                    case 1: {
                        SpawnManager.this.spawn(SpawnManager.cu);
                        break block0;
                    }
                    case 2: {
                        SpawnManager.this.spawn(SpawnManager.cv);
                    }
                }
            }
        }
    }
}
