/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.ai.CtrlEvent
 *  l2.gameserver.ai.Fighter
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.tables.SkillTable
 */
package ai.freya;

import bosses.AntharasManager;
import l2.gameserver.ai.CtrlEvent;
import l2.gameserver.ai.Fighter;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.tables.SkillTable;

public class AntharasMinion
extends Fighter {
    public AntharasMinion(NpcInstance npcInstance) {
        super(npcInstance);
        npcInstance.startDebuffImmunity();
    }

    protected void onEvtSpawn() {
        super.onEvtSpawn();
        for (Player player : AntharasManager.getZone().getInsidePlayers()) {
            this.notifyEvent(CtrlEvent.EVT_AGGRESSION, player, 5000);
        }
    }

    protected void onEvtDead(Creature creature) {
        this.getActor().doCast(SkillTable.getInstance().getInfo(5097, 1), (Creature)this.getActor(), true);
        super.onEvtDead(creature);
    }

    protected void returnHome(boolean bl, boolean bl2) {
    }
}
