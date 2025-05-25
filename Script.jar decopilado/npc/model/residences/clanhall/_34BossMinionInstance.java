/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.templates.npc.NpcTemplate
 */
package npc.model.residences.clanhall;

import l2.gameserver.model.Creature;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.scripts.Functions;
import l2.gameserver.templates.npc.NpcTemplate;
import npc.model.residences.SiegeGuardInstance;
import npc.model.residences.clanhall._34SiegeGuard;

public abstract class _34BossMinionInstance
extends SiegeGuardInstance
implements _34SiegeGuard {
    public _34BossMinionInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    public void onDeath(Creature creature) {
        this.setCurrentHp(1.0, true);
    }

    public void onSpawn() {
        super.onSpawn();
        Functions.npcShout((NpcInstance)this, (String)this.spawnChatSay());
    }

    public abstract String spawnChatSay();

    @Override
    public abstract String teleChatSay();
}
