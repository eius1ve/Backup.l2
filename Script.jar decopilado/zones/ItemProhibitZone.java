/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.listener.Listener
 *  l2.gameserver.instancemanager.ReflectionManager
 *  l2.gameserver.listener.zone.OnZoneEnterLeaveListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Zone
 *  l2.gameserver.model.items.Inventory
 *  l2.gameserver.model.items.ItemInstance
 *  l2.gameserver.model.items.LockType
 *  l2.gameserver.model.items.PcInventory
 *  l2.gameserver.scripts.ScriptFile
 *  org.apache.commons.lang3.ArrayUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package zones;

import java.util.Collection;
import l2.commons.listener.Listener;
import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.listener.zone.OnZoneEnterLeaveListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.Zone;
import l2.gameserver.model.items.Inventory;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.items.LockType;
import l2.gameserver.model.items.PcInventory;
import l2.gameserver.scripts.ScriptFile;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItemProhibitZone
implements OnZoneEnterLeaveListener,
ScriptFile {
    private static final Logger eE = LoggerFactory.getLogger(ItemProhibitZone.class);
    private static final String iX = "zoneProhibitedItemIds";

    public void onZoneEnter(Zone zone, Creature creature) {
        if (creature == null || !creature.isPlayable()) {
            return;
        }
        int[] nArray = zone.getParams().getIntegerArray((Object)iX);
        Playable playable = (Playable)creature;
        if (nArray == null || nArray.length == 0) {
            return;
        }
        Inventory inventory = playable.getInventory();
        if (inventory != null) {
            if (playable.isPlayer()) {
                ((PcInventory)inventory).lockItems(LockType.INCLUDE, nArray);
            }
            for (ItemInstance itemInstance : inventory.getPaperdollItems()) {
                if (itemInstance == null || !ArrayUtils.contains((int[])nArray, (int)itemInstance.getItemId())) continue;
                inventory.unEquipItem(itemInstance);
            }
        }
    }

    public void onZoneLeave(Zone zone, Creature creature) {
        if (creature == null || !creature.isPlayable()) {
            return;
        }
        Playable playable = (Playable)creature;
        int[] nArray = zone.getParams().getIntegerArray((Object)iX);
        if (nArray == null || nArray.length == 0) {
            return;
        }
        if (playable.isPlayer()) {
            ((Player)playable).getInventory().unlock();
        }
    }

    public void onLoad() {
        int n = 0;
        Collection collection = ReflectionManager.DEFAULT.getZones();
        for (Zone zone : collection) {
            int[] nArray = zone.getParams().getIntegerArray((Object)iX, ArrayUtils.EMPTY_INT_ARRAY);
            if (nArray == null || nArray.length == 0) continue;
            zone.addListener((Listener)this);
            ++n;
        }
        if (n > 0) {
            eE.info("ItemProhibitZone: added {} item(s) prohibited zone(s).", (Object)n);
        }
    }

    public void onReload() {
        this.onShutdown();
        this.onLoad();
    }

    public void onShutdown() {
        for (Zone zone : ReflectionManager.DEFAULT.getZones()) {
            zone.removeListener((Listener)this);
        }
    }
}
