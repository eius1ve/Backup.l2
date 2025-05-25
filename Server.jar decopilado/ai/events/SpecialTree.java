/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.math.random.RndSelector
 *  l2.commons.util.Rnd
 *  l2.gameserver.ai.DefaultAI
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.network.l2.s2c.MagicSkillUse
 *  l2.gameserver.tables.SkillTable
 */
package ai.events;

import l2.commons.math.random.RndSelector;
import l2.commons.util.Rnd;
import l2.gameserver.ai.DefaultAI;
import l2.gameserver.model.Creature;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.MagicSkillUse;
import l2.gameserver.tables.SkillTable;

public class SpecialTree
extends DefaultAI {
    private static final RndSelector<Integer> a = new RndSelector(5);
    private boolean K = false;
    private int _timer = 0;

    public SpecialTree(NpcInstance npcInstance) {
        super(npcInstance);
    }

    protected boolean thinkActive() {
        if (this.K) {
            ++this._timer;
            if (this._timer >= 180) {
                this._timer = 0;
                NpcInstance npcInstance = this.getActor();
                if (npcInstance == null) {
                    return false;
                }
                this.addTaskBuff((Creature)npcInstance, SkillTable.getInstance().getInfo(2139, 1));
                if (Rnd.chance((int)33)) {
                    npcInstance.broadcastPacketToOthers(new L2GameServerPacket[]{new MagicSkillUse((Creature)npcInstance, (Creature)npcInstance, ((Integer)a.select()).intValue(), 1, 500, 0L)});
                }
            }
        }
        return super.thinkActive();
    }

    protected void onEvtSpawn() {
        super.onEvtSpawn();
        this.K = !this.getActor().isInZonePeace();
        this._timer = 0;
    }

    static {
        a.add((Object)2140, 20);
        a.add((Object)2142, 20);
        a.add((Object)2145, 20);
        a.add((Object)2147, 20);
        a.add((Object)2149, 20);
    }
}
