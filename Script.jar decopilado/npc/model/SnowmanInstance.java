/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.templates.npc.NpcTemplate
 */
package npc.model;

import l2.gameserver.model.Player;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.templates.npc.NpcTemplate;

public class SnowmanInstance
extends NpcInstance {
    public SnowmanInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    public void showChatWindow(Player player, int n, Object ... objectArray) {
        player.sendActionFailed();
    }
}
