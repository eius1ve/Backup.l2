/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.ai.Fighter
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.instances.NpcInstance
 */
package ai.isle_of_prayer;

import l2.commons.util.Rnd;
import l2.gameserver.ai.Fighter;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.instances.NpcInstance;

public class Shade
extends Fighter {
    private long f = 0L;
    private boolean I = false;
    private static final int bg = 300000;
    private static final int bh = 9595;

    public Shade(NpcInstance npcInstance) {
        super(npcInstance);
    }

    protected boolean thinkActive() {
        NpcInstance npcInstance = this.getActor();
        if (npcInstance.isDead()) {
            return true;
        }
        if (this._def_think) {
            this.doTask();
            this.I = false;
            return true;
        }
        if (!this.I) {
            this.I = true;
            this.f = System.currentTimeMillis() + 300000L;
        }
        if (this.f != 0L && this.I && this.f < System.currentTimeMillis()) {
            npcInstance.deleteMe();
            return true;
        }
        return super.thinkActive();
    }

    protected boolean randomWalk() {
        return false;
    }

    protected void onEvtDead(Creature creature) {
        Player player;
        if (creature != null && (player = creature.getPlayer()) != null) {
            NpcInstance npcInstance = this.getActor();
            if (Rnd.chance((int)10)) {
                npcInstance.dropItem(player, 9595, 1L);
            }
        }
        super.onEvtDead(creature);
    }
}
