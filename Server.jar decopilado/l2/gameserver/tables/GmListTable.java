/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.tables;

import java.util.ArrayList;
import java.util.List;
import l2.gameserver.Config;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.SystemMessage;

public class GmListTable {
    public static List<Player> getAllGMs() {
        ArrayList<Player> arrayList = new ArrayList<Player>();
        for (Player player : GameObjectsStorage.getAllPlayersForIterate()) {
            if (!player.isGM()) continue;
            arrayList.add(player);
        }
        return arrayList;
    }

    public static List<Player> getAllVisibleGMs() {
        ArrayList<Player> arrayList = new ArrayList<Player>();
        for (Player player : GameObjectsStorage.getAllPlayersForIterate()) {
            if (!player.isGM() || player.isInvisible()) continue;
            arrayList.add(player);
        }
        return arrayList;
    }

    public static void sendListToPlayer(Player player) {
        List<Player> list;
        List<Player> list2 = list = Config.HIDE_GM_STATUS ? GmListTable.getAllVisibleGMs() : GmListTable.getAllGMs();
        if (list.isEmpty()) {
            player.sendPacket((IStaticPacket)SystemMsg.THERE_ARE_NO_GMS_CURRENTLY_VISIBLE_IN_THE_PUBLIC_LIST_AS_THEY_MAY_BE_PERFORMING_OTHER_FUNCTIONS_AT_THE_MOMENT);
            return;
        }
        player.sendPacket((IStaticPacket)SystemMsg.GM_LIST);
        for (Player player2 : list) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.GM__C1).addString(player2.getName()));
        }
    }

    public static void broadcastToGMs(L2GameServerPacket l2GameServerPacket) {
        for (Player player : GmListTable.getAllGMs()) {
            player.sendPacket((IStaticPacket)l2GameServerPacket);
        }
    }

    public static void broadcastMessageToGMs(String string) {
        for (Player player : GmListTable.getAllGMs()) {
            player.sendMessage(string);
        }
    }
}
