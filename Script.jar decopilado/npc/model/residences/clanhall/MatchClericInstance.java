/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.instances.residences.clanhall.CTBBossInstance
 *  l2.gameserver.templates.npc.NpcTemplate
 */
package npc.model.residences.clanhall;

import ai.residences.clanhall.MatchCleric;
import l2.commons.util.Rnd;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Skill;
import l2.gameserver.model.instances.residences.clanhall.CTBBossInstance;
import l2.gameserver.templates.npc.NpcTemplate;

public class MatchClericInstance
extends CTBBossInstance {
    private long eq;

    public MatchClericInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    public void reduceCurrentHp(double d, Creature creature, Skill skill, boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5, boolean bl6, boolean bl7) {
        if (this.eq > System.currentTimeMillis()) {
            d = 10000.0;
            if (Rnd.chance((int)10)) {
                ((MatchCleric)this.getAI()).heal();
            }
        } else if (this.getCurrentHpPercents() > 50.0) {
            d = creature.isPlayer() ? d / (double)this.getMaxHp() / 0.05 * 100.0 : d / (double)this.getMaxHp() / 0.05 * 10.0;
        } else if (this.getCurrentHpPercents() > 30.0) {
            if (Rnd.chance((int)90)) {
                d = creature.isPlayer() ? d / (double)this.getMaxHp() / 0.05 * 100.0 : d / (double)this.getMaxHp() / 0.05 * 10.0;
            } else {
                this.eq = System.currentTimeMillis() + 5000L;
            }
        } else {
            this.eq = System.currentTimeMillis() + 5000L;
        }
        super.reduceCurrentHp(d, creature, skill, bl, bl2, bl3, bl4, bl5, bl6, bl7);
    }
}
