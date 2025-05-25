/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.instances.DoorInstance
 *  l2.gameserver.model.instances.GuardInstance
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.templates.npc.NpcTemplate
 *  l2.gameserver.utils.ReflectionUtils
 */
package npc.model;

import l2.gameserver.model.Player;
import l2.gameserver.model.instances.DoorInstance;
import l2.gameserver.model.instances.GuardInstance;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.templates.npc.NpcTemplate;
import l2.gameserver.utils.ReflectionUtils;

public class BorderOutpostDoormanInstance
extends GuardInstance {
    public BorderOutpostDoormanInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    public void onBypassFeedback(Player player, String string) {
        if (!BorderOutpostDoormanInstance.canBypassCheck((Player)player, (NpcInstance)this)) {
            return;
        }
        if (string.equals("openDoor")) {
            DoorInstance doorInstance = ReflectionUtils.getDoor((int)24170001);
            doorInstance.openMe();
        } else if (string.equals("closeDoor")) {
            DoorInstance doorInstance = ReflectionUtils.getDoor((int)24170001);
            doorInstance.closeMe();
        } else {
            super.onBypassFeedback(player, string);
        }
    }
}
