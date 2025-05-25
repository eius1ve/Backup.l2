/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.TIntHashSet
 *  l2.gameserver.data.xml.holder.DoorHolder
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.instances.DoorInstance
 *  l2.gameserver.model.items.ItemInstance
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.SystemMessage
 *  l2.gameserver.templates.DoorTemplate
 */
package handler.items;

import gnu.trove.TIntHashSet;
import handler.items.ScriptItemHandler;
import l2.gameserver.data.xml.holder.DoorHolder;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.instances.DoorInstance;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.templates.DoorTemplate;

public class Keys
extends ScriptItemHandler {
    private int[] H = null;

    public Keys() {
        TIntHashSet tIntHashSet = new TIntHashSet();
        for (DoorTemplate doorTemplate : DoorHolder.getInstance().getDoors().values()) {
            if (doorTemplate == null || doorTemplate.getKey() <= 0) continue;
            tIntHashSet.add(doorTemplate.getKey());
        }
        this.H = tIntHashSet.toArray();
    }

    public boolean useItem(Playable playable, ItemInstance itemInstance, boolean bl) {
        if (playable == null || !playable.isPlayer()) {
            return false;
        }
        Player player = playable.getPlayer();
        GameObject gameObject = player.getTarget();
        if (gameObject == null || !gameObject.isDoor()) {
            player.sendPacket((IStaticPacket)SystemMsg.THAT_IS_AN_INCORRECT_TARGET);
            return false;
        }
        DoorInstance doorInstance = (DoorInstance)gameObject;
        if (doorInstance.isOpen()) {
            player.sendPacket((IStaticPacket)SystemMsg.IT_IS_NOT_LOCKED);
            return false;
        }
        if (doorInstance.getKey() <= 0 || itemInstance.getItemId() != doorInstance.getKey()) {
            player.sendPacket((IStaticPacket)SystemMsg.THIS_DOOR_CANNOT_BE_UNLOCKED);
            return false;
        }
        if (player.getDistance((GameObject)doorInstance) > 300.0) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_CONTROL_BECAUSE_YOU_ARE_TOO_FAR);
            return false;
        }
        if (!player.getInventory().destroyItem(itemInstance, 1L)) {
            player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_ITEM_COUNT);
            return false;
        }
        player.sendPacket((IStaticPacket)SystemMessage.removeItems((int)itemInstance.getItemId(), (long)1L));
        player.sendMessage(new CustomMessage("l2p.gameserver.skills.skillclasses.Unlock.Success", player, new Object[0]));
        doorInstance.openMe(player, true);
        return true;
    }

    public int[] getItemIds() {
        return this.H;
    }
}
