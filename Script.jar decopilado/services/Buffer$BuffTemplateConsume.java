/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.templates.item.ItemTemplate
 *  l2.gameserver.utils.ItemFunctions
 */
package services;

import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.utils.ItemFunctions;

private static class Buffer.BuffTemplateConsume {
    private final ItemTemplate e;
    private final long es;
    private final int bFB;
    private final boolean hy;

    public Buffer.BuffTemplateConsume(ItemTemplate itemTemplate, long l, int n, boolean bl) {
        this.e = itemTemplate;
        this.es = l;
        this.bFB = n;
        this.hy = bl;
    }

    public ItemTemplate getItem() {
        return this.e;
    }

    public long getAmount() {
        return this.es;
    }

    public int getFromLevel() {
        return this.bFB;
    }

    public boolean isBonusRequired() {
        return this.hy;
    }

    public boolean mayConsume(Player player) {
        long l = player.getInventory().getCountOf(this.getItem().getItemId());
        if (l < this.getAmount()) {
            if (this.getItem().isAdena()) {
                player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
            } else {
                player.sendMessage(new CustomMessage("scripts.npc.model.L2NpcBufferInstance.RequiresS1S2", player, new Object[]{this.getItem().getName(), this.getAmount()}));
            }
            return false;
        }
        return true;
    }

    public boolean consume(Player player) {
        if (this.getAmount() == 0L) {
            return player.getInventory().getCountOf(this.getItem().getItemId()) > 0L;
        }
        return ItemFunctions.removeItem((Playable)player, (int)this.getItem().getItemId(), (long)this.getAmount(), (boolean)true, (boolean)false) >= this.getAmount();
    }
}
