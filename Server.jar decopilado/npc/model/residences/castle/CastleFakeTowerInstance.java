/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.templates.npc.NpcTemplate
 */
package npc.model.residences.castle;

import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.templates.npc.NpcTemplate;

public class CastleFakeTowerInstance
extends NpcInstance {
    public CastleFakeTowerInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    public boolean isAutoAttackable(Creature creature) {
        return false;
    }

    public void showChatWindow(Player player, int n, Object ... objectArray) {
    }

    public void showChatWindow(Player player, String string, Object ... objectArray) {
    }

    public boolean hasRandomAnimation() {
        return false;
    }

    public boolean isInvul() {
        return true;
    }

    public boolean isFearImmune() {
        return true;
    }

    public boolean isParalyzeImmune() {
        return true;
    }

    public boolean isLethalImmune() {
        return true;
    }
}
