/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.instances.MerchantInstance
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.templates.npc.NpcTemplate
 */
package npc.model;

import l2.gameserver.model.Player;
import l2.gameserver.model.instances.MerchantInstance;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.templates.npc.NpcTemplate;
import services.Buffer;

public class NpcBufferInstance
extends MerchantInstance {
    public NpcBufferInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    public void showChatWindow(Player player, int n, Object ... objectArray) {
        if (n == 0) {
            Buffer.showPage(player, "npc-" + this.getNpcId(), (NpcInstance)this);
            return;
        }
        super.showChatWindow(player, n, objectArray);
    }
}
