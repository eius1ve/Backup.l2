/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.tables.SkillTable
 *  l2.gameserver.utils.PositionUtils
 */
package ai.residences.clanhall;

import ai.residences.SiegeGuardFighter;
import l2.commons.util.Rnd;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Skill;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.scripts.Functions;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.utils.PositionUtils;

public class LidiaVonHellmann
extends SiegeGuardFighter {
    private static final Skill l = SkillTable.getInstance().getInfo(4999, 1);
    private static final Skill m = SkillTable.getInstance().getInfo(4998, 1);

    public LidiaVonHellmann(NpcInstance npcInstance) {
        super(npcInstance);
    }

    public void onEvtSpawn() {
        super.onEvtSpawn();
        Functions.npcShoutCustomMessage((NpcInstance)this.getActor(), (String)"clanhall.LidiaVonHellmann.HMM_THOSE_WHO_ARE_NOT_OF_THE_BLOODLINE_ARE_COMING_THIS_WAY_TO_TAKE_OVER_THE_CASTLE__HUMPH__THE_BITTER_GRUDGES_OF_THE_DEAD", (Object[])new Object[0]);
    }

    public void onEvtDead(Creature creature) {
        super.onEvtDead(creature);
        Functions.npcShoutCustomMessage((NpcInstance)this.getActor(), (String)"clanhall.LidiaVonHellmann.GRARR_FOR_THE_NEXT_2_MINUTES_OR_SO_THE_GAME_ARENA_ARE_WILL_BE_CLEANED", (Object[])new Object[0]);
    }

    public void onEvtAttacked(Creature creature, int n) {
        NpcInstance npcInstance = this.getActor();
        super.onEvtAttacked(creature, n);
        if (Rnd.chance((double)0.22)) {
            this.addTaskCast(creature, l);
        } else if (npcInstance.getCurrentHpPercents() < 20.0 && Rnd.chance((double)0.22)) {
            this.addTaskCast(creature, l);
        }
        if (PositionUtils.calculateDistance((GameObject)npcInstance, (GameObject)creature, (boolean)false) > 300.0 && Rnd.chance((double)0.13)) {
            this.addTaskCast(creature, m);
        }
    }
}
