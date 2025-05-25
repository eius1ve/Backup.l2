/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.ai.Mystic
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.entity.Reflection
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.network.l2.s2c.MagicSkillUse
 *  l2.gameserver.scripts.Functions
 */
package ai.custom;

import l2.commons.util.Rnd;
import l2.gameserver.ai.Mystic;
import l2.gameserver.model.Creature;
import l2.gameserver.model.entity.Reflection;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.MagicSkillUse;
import l2.gameserver.scripts.Functions;

public class SSQLilith
extends Mystic {
    private final String[] n = new String[]{"19615", "19616", "19617", "19618"};
    private long L = 0L;
    private long N = 0L;

    public SSQLilith(NpcInstance npcInstance) {
        super(npcInstance);
        npcInstance.setHasChatWindow(false);
    }

    protected boolean thinkActive() {
        if (this.L < System.currentTimeMillis()) {
            Functions.npcSayCustomMessage((NpcInstance)this.getActor(), (String)this.n[Rnd.get((int)this.n.length)], (Object[])new Object[0]);
            this.L = System.currentTimeMillis() + 15000L;
        }
        if (this.N < System.currentTimeMillis()) {
            Reflection reflection = this.getActor().getReflection();
            if (reflection != null) {
                NpcInstance npcInstance = null;
                for (NpcInstance npcInstance2 : reflection.getNpcs()) {
                    if (npcInstance2.getNpcId() != 32718) continue;
                    npcInstance = npcInstance2;
                    break;
                }
                if (npcInstance != null) {
                    this.getActor().broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)this.getActor(), (Creature)npcInstance, 6187, 1, 5000, 10L)});
                }
            }
            this.N = System.currentTimeMillis() + 6500L;
        }
        return true;
    }

    protected boolean randomWalk() {
        return false;
    }

    protected void onEvtAttacked(Creature creature, int n) {
    }

    protected void onEvtAggression(Creature creature, int n) {
    }
}
