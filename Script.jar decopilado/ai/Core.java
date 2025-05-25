/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.ai.Fighter
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.network.l2.s2c.PlaySound
 *  l2.gameserver.network.l2.s2c.PlaySound$Type
 *  l2.gameserver.scripts.Functions
 */
package ai;

import l2.commons.util.Rnd;
import l2.gameserver.ai.Fighter;
import l2.gameserver.model.Creature;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.PlaySound;
import l2.gameserver.scripts.Functions;

public class Core
extends Fighter {
    private boolean j = true;

    public Core(NpcInstance npcInstance) {
        super(npcInstance);
    }

    protected void onEvtAttacked(Creature creature, int n) {
        NpcInstance npcInstance = this.getActor();
        if (this.j) {
            Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"1000001", (Object[])new Object[0]);
            Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"1000002", (Object[])new Object[0]);
            this.j = false;
        } else if (Rnd.chance((int)1)) {
            Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"1000003", (Object[])new Object[0]);
        }
    }

    protected void onEvtDead(Creature creature) {
        NpcInstance npcInstance = this.getActor();
        npcInstance.broadcastPacket(new L2GameServerPacket[]{new PlaySound(PlaySound.Type.MUSIC, "BS02_D", 1, 0, npcInstance.getLoc())});
        Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"1000004", (Object[])new Object[0]);
        Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"1000005", (Object[])new Object[0]);
        Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"1000006", (Object[])new Object[0]);
        this.j = true;
        super.onEvtDead(creature);
    }

    protected void onEvtSpawn() {
        this.getActor().broadcastPacket(new L2GameServerPacket[]{new PlaySound(PlaySound.Type.MUSIC, "BS01_A", 1, this.getActor().getObjectId(), this.getActor().getLoc())});
        super.onEvtSpawn();
    }
}
