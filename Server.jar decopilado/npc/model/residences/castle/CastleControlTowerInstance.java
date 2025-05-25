/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Spawner
 *  l2.gameserver.model.instances.residences.SiegeToggleNpcInstance
 *  l2.gameserver.templates.npc.NpcTemplate
 */
package npc.model.residences.castle;

import java.util.HashSet;
import java.util.Set;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Spawner;
import l2.gameserver.model.instances.residences.SiegeToggleNpcInstance;
import l2.gameserver.templates.npc.NpcTemplate;

public class CastleControlTowerInstance
extends SiegeToggleNpcInstance {
    private Set<Spawner> E = new HashSet<Spawner>();

    public CastleControlTowerInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    public void onDeathImpl(Creature creature) {
        for (Spawner spawner : this.E) {
            spawner.stopRespawn();
        }
        this.E.clear();
    }

    public void register(Spawner spawner) {
        this.E.add(spawner);
    }
}
