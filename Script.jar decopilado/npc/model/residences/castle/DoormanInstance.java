/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.entity.residence.Castle
 *  l2.gameserver.model.entity.residence.Residence
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.pledge.Clan
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.NpcHtmlMessage
 *  l2.gameserver.templates.npc.NpcTemplate
 *  l2.gameserver.utils.Location
 *  l2.gameserver.utils.ReflectionUtils
 */
package npc.model.residences.castle;

import l2.gameserver.model.Player;
import l2.gameserver.model.entity.residence.Castle;
import l2.gameserver.model.entity.residence.Residence;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.templates.npc.NpcTemplate;
import l2.gameserver.utils.Location;
import l2.gameserver.utils.ReflectionUtils;

public class DoormanInstance
extends npc.model.residences.DoormanInstance {
    private Location[] p = new Location[2];

    public DoormanInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
        for (int i = 0; i < this.p.length; ++i) {
            String string = npcTemplate.getAIParams().getString((Object)("tele_loc" + i), null);
            if (string == null) continue;
            this.p[i] = Location.parseLoc((String)string);
        }
    }

    @Override
    public void onBypassFeedback(Player player, String string) {
        if (!DoormanInstance.canBypassCheck((Player)player, (NpcInstance)this)) {
            return;
        }
        int n = this.getCond(player);
        switch (n) {
            case 0: {
                int n2;
                Location location;
                if (string.equalsIgnoreCase("openDoors")) {
                    for (int n3 : this._doors) {
                        ReflectionUtils.getDoor((int)n3).openMe(player, true);
                    }
                    break;
                }
                if (string.equalsIgnoreCase("closeDoors")) {
                    for (int n4 : this._doors) {
                        ReflectionUtils.getDoor((int)n4).closeMe(player, true);
                    }
                    break;
                }
                if (!string.startsWith("tele") || (location = this.p[n2 = Integer.parseInt(string.substring(4, 5))]) == null) break;
                player.teleToLocation(location);
                break;
            }
            case 1: {
                if (string.startsWith("tele")) {
                    int n5 = Integer.parseInt(string.substring(4, 5));
                    Location location = this.p[n5];
                    if (location == null) break;
                    player.teleToLocation(location);
                    break;
                }
                player.sendPacket((IStaticPacket)new NpcHtmlMessage(player, (NpcInstance)this, this._siegeDialog, 0));
                break;
            }
            case 2: {
                player.sendPacket((IStaticPacket)new NpcHtmlMessage(player, (NpcInstance)this, this._failDialog, 0));
            }
        }
    }

    @Override
    public void showChatWindow(Player player, int n, Object ... objectArray) {
        String string = null;
        int n2 = this.getCond(player);
        switch (n2) {
            case 0: 
            case 1: {
                string = this._mainDialog;
                break;
            }
            case 2: {
                string = this._failDialog;
            }
        }
        player.sendPacket((IStaticPacket)new NpcHtmlMessage(player, (NpcInstance)this, string, n));
    }

    @Override
    protected int getCond(Player player) {
        Castle castle = this.getCastle();
        Clan clan = castle.getOwner();
        if (clan != null && player.getClan() == clan && (player.getClanPrivileges() & this.getOpenPriv()) == this.getOpenPriv()) {
            if (castle.getSiegeEvent().isInProgress()) {
                return 1;
            }
            return 0;
        }
        return 2;
    }

    @Override
    public int getOpenPriv() {
        return 65536;
    }

    @Override
    public Residence getResidence() {
        return this.getCastle();
    }

    protected boolean canInteractWithKarmaPlayer() {
        return true;
    }

    protected boolean canInteractWithCursedWeaponPlayer() {
        return true;
    }
}
