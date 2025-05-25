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

public class TriolsMirrorInstance
extends NpcInstance {
    public TriolsMirrorInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    public void showChatWindow(Player player, int n, Object ... objectArray) {
        if (this.getNpcId() == 32040) {
            player.teleToLocation(-12766, -35840, -10856);
        } else if (this.getNpcId() == 32039) {
            player.teleToLocation(35079, -49758, -760);
        }
    }
}
