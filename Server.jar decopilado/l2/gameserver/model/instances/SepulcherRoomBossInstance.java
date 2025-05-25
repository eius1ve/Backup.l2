/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.instances;

import l2.gameserver.model.Creature;
import l2.gameserver.model.instances.SepulcherMonsterInstance;
import l2.gameserver.templates.npc.NpcTemplate;

public class SepulcherRoomBossInstance
extends SepulcherMonsterInstance {
    private static final int ns = 31455;

    public SepulcherRoomBossInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    @Override
    protected void onDeath(Creature creature) {
        super.onDeath(creature);
        this.define.getHandler().spawnCustomSingle(31455, this.getLoc());
    }
}
