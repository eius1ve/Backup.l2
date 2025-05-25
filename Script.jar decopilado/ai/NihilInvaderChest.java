/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.ai.DefaultAI
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.network.l2.s2c.MagicSkillUse
 */
package ai;

import l2.commons.util.Rnd;
import l2.gameserver.ai.DefaultAI;
import l2.gameserver.model.Creature;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.MagicSkillUse;

public class NihilInvaderChest
extends DefaultAI {
    private static int[] e = new int[]{4039, 4040, 4041, 4042, 4043, 4044};
    private static int[] f = new int[]{9628, 9629, 9630};

    public NihilInvaderChest(NpcInstance npcInstance) {
        super(npcInstance);
        npcInstance.startImmobilized();
    }

    protected void onEvtAttacked(Creature creature, int n) {
        NpcInstance npcInstance = this.getActor();
        if (npcInstance.getNpcId() == 18820) {
            if (Rnd.chance((int)40)) {
                npcInstance.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)npcInstance, (Creature)npcInstance, 2025, 1, 0, 10L)});
                npcInstance.dropItem(creature.getPlayer(), e[Rnd.get((int)0, (int)(e.length - 1))], (long)Rnd.get((int)10, (int)20));
                npcInstance.doDie(null);
            }
        } else if (npcInstance.getNpcId() == 18823 && Rnd.chance((int)40)) {
            npcInstance.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)npcInstance, (Creature)npcInstance, 2025, 1, 0, 10L)});
            npcInstance.dropItem(creature.getPlayer(), f[Rnd.get((int)0, (int)(f.length - 1))], (long)Rnd.get((int)10, (int)20));
            npcInstance.doDie(null);
        }
        for (NpcInstance npcInstance2 : npcInstance.getReflection().getNpcs()) {
            if (npcInstance2.getNpcId() != npcInstance.getNpcId()) continue;
            npcInstance2.deleteMe();
        }
        super.onEvtAttacked(creature, n);
    }

    protected void onEvtAggression(Creature creature, int n) {
    }
}
