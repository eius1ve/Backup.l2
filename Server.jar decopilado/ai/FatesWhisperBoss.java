/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.ai.Fighter
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.utils.Location
 *  l2.gameserver.utils.NpcUtils
 */
package ai;

import java.util.HashMap;
import java.util.Map;
import l2.gameserver.ai.Fighter;
import l2.gameserver.model.Creature;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.utils.Location;
import l2.gameserver.utils.NpcUtils;

public class FatesWhisperBoss
extends Fighter {
    private static final Map<Integer, Integer> d = new HashMap<Integer, Integer>();

    public FatesWhisperBoss(NpcInstance npcInstance) {
        super(npcInstance);
    }

    protected void onEvtDead(Creature creature) {
        NpcInstance npcInstance = this.getActor();
        Integer n = d.get(npcInstance.getNpcId());
        if (n != null) {
            NpcUtils.spawnSingle((int)n, (Location)npcInstance.getLoc(), (long)120000L);
        }
        super.onEvtDead(creature);
    }

    static {
        d.put(25035, 31027);
        d.put(25054, 31028);
        d.put(25126, 31029);
        d.put(25220, 31030);
    }
}
