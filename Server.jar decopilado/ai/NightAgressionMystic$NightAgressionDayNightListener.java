/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.GameTimeController
 *  l2.gameserver.listener.game.OnDayNightChangeListener
 */
package ai;

import l2.gameserver.GameTimeController;
import l2.gameserver.listener.game.OnDayNightChangeListener;

private class NightAgressionMystic.NightAgressionDayNightListener
implements OnDayNightChangeListener {
    private NightAgressionMystic.NightAgressionDayNightListener() {
        if (GameTimeController.getInstance().isNowNight()) {
            this.onNight();
        } else {
            this.onDay();
        }
    }

    public void onDay() {
        NightAgressionMystic.this.getActor().setAggroRange(0);
    }

    public void onNight() {
        NightAgressionMystic.this.getActor().setAggroRange(-1);
    }
}
