/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.instances.MerchantInstance
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.PackageToList
 *  l2.gameserver.templates.npc.NpcTemplate
 *  l2.gameserver.utils.WarehouseFunctions
 */
package npc.model;

import l2.gameserver.model.Player;
import l2.gameserver.model.instances.MerchantInstance;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.PackageToList;
import l2.gameserver.templates.npc.NpcTemplate;
import l2.gameserver.utils.WarehouseFunctions;

public class FreightSenderInstance
extends MerchantInstance {
    public FreightSenderInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    public void onBypassFeedback(Player player, String string) {
        if (!FreightSenderInstance.canBypassCheck((Player)player, (NpcInstance)this)) {
            return;
        }
        if (string.equalsIgnoreCase("deposit_items")) {
            player.sendPacket((IStaticPacket)new PackageToList(player));
        } else if (string.equalsIgnoreCase("withdraw_items")) {
            WarehouseFunctions.showFreightWindow((Player)player);
        } else {
            super.onBypassFeedback(player, string);
        }
    }
}
