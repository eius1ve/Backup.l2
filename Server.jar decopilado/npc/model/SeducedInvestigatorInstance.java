/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.instances.MonsterInstance
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.NpcHtmlMessage
 *  l2.gameserver.templates.npc.NpcTemplate
 */
package npc.model;

import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.instances.MonsterInstance;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.templates.npc.NpcTemplate;

public class SeducedInvestigatorInstance
extends MonsterInstance {
    public SeducedInvestigatorInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
        this.setHasChatWindow(true);
    }

    public void showChatWindow(Player player, int n, Object ... objectArray) {
        player.sendPacket((IStaticPacket)new NpcHtmlMessage(player, (NpcInstance)this, "common/seducedinvestigator.htm", n));
    }

    public boolean isAutoAttackable(Creature creature) {
        Player player = creature.getPlayer();
        if (player == null) {
            return false;
        }
        return !player.isPlayable();
    }

    public boolean isMovementDisabled() {
        return true;
    }

    public boolean canChampion() {
        return false;
    }
}
