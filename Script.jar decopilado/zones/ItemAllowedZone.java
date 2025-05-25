/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.listener.Listener
 *  l2.gameserver.instancemanager.ReflectionManager
 *  l2.gameserver.listener.zone.OnZoneEnterLeaveListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Zone
 *  l2.gameserver.model.items.ItemInstance
 *  l2.gameserver.model.items.LockType
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
import l2.gameserver.model.Player;
import l2.gameserver.model.Zone;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.items.LockType;
import l2.gameserver.scripts.ScriptFile;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItemAllowedZone
implements OnZoneEnterLeaveListener,
ScriptFile {
    private static final Logger eD = LoggerFactory.getLogger(ItemAllowedZone.class);
    private static final String iW = "zoneAllowedItemIds";

    public void onZoneEnter(Zone zone, Creature creature) {
        if (creature == null || !creature.isPlayer()) {
            return;
        }
        int[] nArray = zone.getParams().getIntegerArray((Object)iW);
        Player player = creature.getPlayer();
        if (nArray == null || nArray.length == 0) {
            return;
        }
        for (ItemInstance itemInstance : player.getInventory().getPaperdollItems()) {
            if (itemInstance == null || ArrayUtils.contains((int[])nArray, (int)itemInstance.getItemId())) continue;
            player.getInventory().unEquipItem(itemInstance);
        }
        player.getInventory().lockItems(LockType.EXCLUDE, nArray);
    }

    public void onZoneLeave(Zone zone, Creature creature) {
        Player player = creature.getPlayer();
        int[] nArray = zone.getParams().getIntegerArray((Object)iW);
        if (nArray == null || nArray.length == 0 || player == null) {
            return;
        }
        player.getInventory().unlock();
    }

    public void onLoad() {
        int n = 0;
        Collection collection = ReflectionManager.DEFAULT.getZones();
        for (Zone zone : collection) {
            int[] nArray = zone.getParams().getIntegerArray((Object)iW, ArrayUtils.EMPTY_INT_ARRAY);
            if (nArray == null || nArray.length == 0) continue;
            zone.addListener((Listener)this);
            ++n;
        }
        if (n > 0) {
            eD.info("ItemAllowedZone: added {} item(s) allowed zone(s).", (Object)n);
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
