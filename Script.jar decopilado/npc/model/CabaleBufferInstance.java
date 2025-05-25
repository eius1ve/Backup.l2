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

public final class CabaleBufferInstance
extends NpcInstance {
    public CabaleBufferInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    public void showChatWindow(Player player, int n, Object ... objectArray) {
    }

    public void showChatWindow(Player player, String string, Object ... objectArray) {
    }

    public void onBypassFeedback(Player player, String string) {
    }
}
