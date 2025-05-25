/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.ai.DefaultAI
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.Earthquake
 */
package ai;

import java.util.List;
import l2.gameserver.ai.DefaultAI;
import l2.gameserver.model.Creature;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.Earthquake;

public class BaiumNpc
extends DefaultAI {
    private long f = 0L;
    private static final int u = 900000;

    public BaiumNpc(NpcInstance npcInstance) {
        super(npcInstance);
    }

    public boolean isGlobalAI() {
        return true;
    }

    protected boolean thinkActive() {
        NpcInstance npcInstance = this.getActor();
        if (this.f < System.currentTimeMillis()) {
            this.f = System.currentTimeMillis() + 900000L;
            Earthquake earthquake = new Earthquake(npcInstance.getLoc(), 40, 10);
            List list = npcInstance.getAroundCharacters(5000, 10000);
            for (Creature creature : list) {
                if (!creature.isPlayer()) continue;
                creature.sendPacket((IStaticPacket)earthquake);
            }
        }
        return false;
    }

    protected boolean randomWalk() {
        return false;
    }
}
