/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.GameTimeController
 *  l2.gameserver.ai.Mystic
 *  l2.gameserver.listener.GameListener
 *  l2.gameserver.listener.game.OnDayNightChangeListener
 *  l2.gameserver.model.instances.NpcInstance
 */
package ai;

import l2.gameserver.GameTimeController;
import l2.gameserver.ai.Mystic;
import l2.gameserver.listener.GameListener;
import l2.gameserver.listener.game.OnDayNightChangeListener;
import l2.gameserver.model.instances.NpcInstance;

public class NightAgressionMystic
extends Mystic {
    public NightAgressionMystic(NpcInstance npcInstance) {
        super(npcInstance);
        GameTimeController.getInstance().addListener((GameListener)new NightAgressionDayNightListener());
    }

    private class NightAgressionDayNightListener
    implements OnDayNightChangeListener {
        private NightAgressionDayNightListener() {
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
}
