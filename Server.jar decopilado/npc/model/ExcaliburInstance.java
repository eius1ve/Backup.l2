/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.instances.MerchantInstance
 *  l2.gameserver.templates.npc.NpcTemplate
 */
package npc.model;

import l2.gameserver.model.Player;
import l2.gameserver.model.instances.MerchantInstance;
import l2.gameserver.templates.npc.NpcTemplate;

public class ExcaliburInstance
extends MerchantInstance {
    private static final String gR = "scripts/events/MasterOfEnchanting/main.htm";

    public ExcaliburInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    public void showChatWindow(Player player, int n, Object ... objectArray) {
        this.showChatWindow(player, gR, new Object[0]);
    }
}
