/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.GameTimeController
 *  l2.gameserver.listener.game.OnDayNightChangeListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Skill
 *  l2.gameserver.tables.SkillTable
 */
package ai;

import l2.gameserver.GameTimeController;
import l2.gameserver.listener.game.OnDayNightChangeListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Skill;
import l2.gameserver.tables.SkillTable;

private class ZakenNightly.s_zaken_regen
implements OnDayNightChangeListener {
    private final Skill h = SkillTable.getInstance().getInfo(4227, 1);

    private ZakenNightly.s_zaken_regen() {
        if (GameTimeController.getInstance().isNowNight()) {
            this.onNight();
        } else {
            this.onDay();
        }
    }

    public void onDay() {
        ZakenNightly.this.getActor().getEffectList().stopEffect(this.h);
    }

    public void onNight() {
        this.h.getEffects((Creature)ZakenNightly.this.getActor(), (Creature)ZakenNightly.this.getActor(), false, false);
    }
}
