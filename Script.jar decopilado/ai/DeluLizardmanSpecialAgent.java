/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.ai.Ranger
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.scripts.Functions
 */
package ai;

import l2.commons.util.Rnd;
import l2.gameserver.ai.Ranger;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.scripts.Functions;

public class DeluLizardmanSpecialAgent
extends Ranger {
    private boolean j = true;

    public DeluLizardmanSpecialAgent(NpcInstance npcInstance) {
        super(npcInstance);
    }

    protected void onEvtSpawn() {
        this.j = true;
        super.onEvtSpawn();
    }

    protected void onEvtAttacked(Creature creature, int n) {
        NpcInstance npcInstance = this.getActor();
        Player player = creature.getPlayer();
        if (this.j) {
            this.j = false;
            if (Rnd.chance((int)25)) {
                Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"1000288", (Object[])new Object[]{player.getName()});
            }
        } else if (Rnd.chance((int)10)) {
            Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"1000388", (Object[])new Object[]{player.getName()});
        }
        super.onEvtAttacked(creature, n);
    }
}
