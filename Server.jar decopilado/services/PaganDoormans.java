/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.instances.DoorInstance
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.utils.ReflectionUtils
 */
package services;

import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.instances.DoorInstance;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.scripts.Functions;
import l2.gameserver.utils.ReflectionUtils;

public class PaganDoormans
extends Functions {
    private static final int bFJ = 19160001;
    private static final int bFK = 19160011;
    private static final int bFL = 19160010;
    private static final int bFM = 8064;
    private static final int bFN = 8065;
    private static final int bFO = 8067;

    public void openMainDoor() {
        Player player = this.getSelf();
        NpcInstance npcInstance = this.getNpc();
        if (player == null || npcInstance == null) {
            return;
        }
        if (!NpcInstance.canBypassCheck((Player)player, (NpcInstance)npcInstance)) {
            return;
        }
        long l = PaganDoormans.getItemCount((Playable)player, (int)8064);
        if (l == 0L && PaganDoormans.getItemCount((Playable)player, (int)8067) == 0L) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_REQUIRED_ITEMS);
            return;
        }
        if (l > 0L) {
            PaganDoormans.removeItem((Playable)player, (int)8064, (long)l);
            PaganDoormans.addItem((Playable)player, (int)8065, (long)1L);
        }
        PaganDoormans.openDoor(19160001);
        PaganDoormans.show((String)"default/32034-1.htm", (Player)player, (NpcInstance)npcInstance, (Object[])new Object[0]);
    }

    public void openSecondDoor() {
        Player player = this.getSelf();
        NpcInstance npcInstance = this.getNpc();
        if (player == null || npcInstance == null) {
            return;
        }
        if (!NpcInstance.canBypassCheck((Player)player, (NpcInstance)npcInstance)) {
            return;
        }
        if (PaganDoormans.getItemCount((Playable)player, (int)8067) == 0L) {
            PaganDoormans.show((String)"default/32036-2.htm", (Player)player, (NpcInstance)npcInstance, (Object[])new Object[0]);
            return;
        }
        PaganDoormans.openDoor(19160011);
        PaganDoormans.openDoor(19160010);
        PaganDoormans.show((String)"default/32036-1.htm", (Player)player, (NpcInstance)npcInstance, (Object[])new Object[0]);
    }

    public void pressSkull() {
        Player player = this.getSelf();
        NpcInstance npcInstance = this.getNpc();
        if (player == null || npcInstance == null) {
            return;
        }
        if (!NpcInstance.canBypassCheck((Player)player, (NpcInstance)npcInstance)) {
            return;
        }
        PaganDoormans.openDoor(19160001);
        PaganDoormans.show((String)"default/32035-1.htm", (Player)player, (NpcInstance)npcInstance, (Object[])new Object[0]);
    }

    public void press2ndSkull() {
        Player player = this.getSelf();
        NpcInstance npcInstance = this.getNpc();
        if (player == null || npcInstance == null) {
            return;
        }
        if (!NpcInstance.canBypassCheck((Player)player, (NpcInstance)npcInstance)) {
            return;
        }
        PaganDoormans.openDoor(19160011);
        PaganDoormans.openDoor(19160010);
        PaganDoormans.show((String)"default/32037-1.htm", (Player)player, (NpcInstance)npcInstance, (Object[])new Object[0]);
    }

    private static void openDoor(int n) {
        DoorInstance doorInstance = ReflectionUtils.getDoor((int)n);
        doorInstance.openMe();
    }
}
