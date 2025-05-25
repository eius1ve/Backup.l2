/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.entity.residence.Residence
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.pledge.Clan
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.NpcHtmlMessage
 *  l2.gameserver.templates.npc.NpcTemplate
 *  l2.gameserver.utils.ReflectionUtils
 */
package npc.model.residences;

import l2.gameserver.model.Player;
import l2.gameserver.model.entity.residence.Residence;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.templates.npc.NpcTemplate;
import l2.gameserver.utils.ReflectionUtils;

public abstract class DoormanInstance
extends NpcInstance {
    protected static final int COND_OWNER = 0;
    protected static final int COND_SIEGE = 1;
    protected static final int COND_FAIL = 2;
    protected String _siegeDialog;
    protected String _mainDialog;
    protected String _failDialog;
    protected int[] _doors;

    public DoormanInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
        this.setDialogs();
        this._doors = npcTemplate.getAIParams().getIntegerArray((Object)"doors");
    }

    public void setDialogs() {
        this._siegeDialog = this.getTemplate().getAIParams().getString((Object)"siege_dialog");
        this._mainDialog = this.getTemplate().getAIParams().getString((Object)"main_dialog");
        this._failDialog = this.getTemplate().getAIParams().getString((Object)"fail_dialog");
    }

    public void onBypassFeedback(Player player, String string) {
        if (!DoormanInstance.canBypassCheck((Player)player, (NpcInstance)this)) {
            return;
        }
        int n = this.getCond(player);
        switch (n) {
            case 0: {
                if (string.equalsIgnoreCase("openDoors")) {
                    for (int n2 : this._doors) {
                        ReflectionUtils.getDoor((int)n2).openMe();
                    }
                } else {
                    if (!string.equalsIgnoreCase("closeDoors")) break;
                    for (int n3 : this._doors) {
                        ReflectionUtils.getDoor((int)n3).closeMe();
                    }
                }
                break;
            }
            case 1: {
                player.sendPacket((IStaticPacket)new NpcHtmlMessage(player, (NpcInstance)this, this._siegeDialog, 0));
                break;
            }
            case 2: {
                player.sendPacket((IStaticPacket)new NpcHtmlMessage(player, (NpcInstance)this, this._failDialog, 0));
            }
        }
    }

    public void showChatWindow(Player player, int n, Object ... objectArray) {
        String string = null;
        int n2 = this.getCond(player);
        switch (n2) {
            case 0: {
                string = this._mainDialog;
                break;
            }
            case 1: {
                string = this._siegeDialog;
                break;
            }
            case 2: {
                string = this._failDialog;
            }
        }
        player.sendPacket((IStaticPacket)new NpcHtmlMessage(player, (NpcInstance)this, string, n));
    }

    protected int getCond(Player player) {
        Residence residence = this.getResidence();
        Clan clan = residence.getOwner();
        if (clan != null && player.getClan() == clan && (player.getClanPrivileges() & this.getOpenPriv()) == this.getOpenPriv()) {
            if (residence.getSiegeEvent().isInProgress()) {
                return 1;
            }
            return 0;
        }
        return 2;
    }

    public abstract int getOpenPriv();

    public abstract Residence getResidence();
}
