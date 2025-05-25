/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.instances.MonsterInstance
 *  l2.gameserver.templates.npc.NpcTemplate
 */
package npc.model;

import events.SavingSnowman.SavingSnowman;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Skill;
import l2.gameserver.model.instances.MonsterInstance;
import l2.gameserver.templates.npc.NpcTemplate;

public class ThomasInstance
extends MonsterInstance {
    private final int[] bo;
    private final int HM;
    private final int HN;

    public ThomasInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
        this.bo = npcTemplate.getAIParams().getIntegerArray((Object)"weaponWeaknessID", new int[]{4202, 5133, 5817, 7058, 8350});
        this.HM = npcTemplate.getAIParams().getInteger((Object)"weaponWeaknessDamage", 100);
        this.HN = npcTemplate.getAIParams().getInteger((Object)"normalDamage", 10);
    }

    public void reduceCurrentHp(double d, Creature creature, Skill skill, boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5, boolean bl6, boolean bl7) {
        if (creature != null && creature.getActiveWeaponInstance() != null) {
            int n = creature.getActiveWeaponInstance().getItemId();
            boolean bl8 = false;
            for (int n2 : this.bo) {
                if (n2 != n) continue;
                bl8 = true;
                break;
            }
            d = bl8 ? (double)this.HM : (double)this.HN;
        }
        super.reduceCurrentHp(d, creature, skill, bl, bl2, bl3, bl4, bl5, bl6, bl7);
    }

    protected void onDeath(Creature creature) {
        Creature creature2 = this.getAggroList().getTopDamager();
        if (creature2 == null) {
            creature2 = creature;
        }
        SavingSnowman.freeSnowman(creature2);
        super.onDeath(creature);
    }

    public boolean canChampion() {
        return false;
    }
}
