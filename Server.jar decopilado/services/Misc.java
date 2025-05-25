/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Party
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.utils.Location
 *  l2.gameserver.utils.MapRegionUtils
 */
package services;

import java.util.ArrayList;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Party;
import l2.gameserver.model.Player;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.scripts.Functions;
import l2.gameserver.utils.Location;
import l2.gameserver.utils.MapRegionUtils;

public class Misc
extends Functions {
    private static final Location aj = new Location(11416, -23432, -3640);

    public void doSummon() {
        Player player = this.getSelf();
        NpcInstance npcInstance = this.getNpc();
        if (player == null || npcInstance == null || !NpcInstance.canBypassCheck((Player)player, (NpcInstance)player.getLastNpc())) {
            return;
        }
        Party party = player.getParty();
        ArrayList<Player> arrayList = new ArrayList<Player>();
        if (party != null) {
            for (Player player2 : party.getPartyMembers()) {
                if (!player2.isDead() || MapRegionUtils.regionX((GameObject)player2) != 20 || MapRegionUtils.regionY((GameObject)player2) != 17) continue;
                arrayList.add(player2);
            }
        }
        if (arrayList.isEmpty() || !player.reduceAdena(200000L, true)) {
            this.show("default/32104-fail.htm", player);
            return;
        }
        for (Player player2 : arrayList) {
            player2.teleToLocation(aj);
        }
        this.show("default/32104-success.htm", player);
    }
}
