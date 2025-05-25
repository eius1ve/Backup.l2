/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.NpcHtmlMessage
 *  l2.gameserver.skills.skillclasses.Call
 *  l2.gameserver.templates.npc.NpcTemplate
 *  l2.gameserver.utils.Location
 */
package npc.model.residences.castle;

import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.skills.skillclasses.Call;
import l2.gameserver.templates.npc.NpcTemplate;
import l2.gameserver.utils.Location;

public class CourtInstance
extends NpcInstance {
    protected static final int COND_ALL_FALSE = 0;
    protected static final int COND_BUSY_BECAUSE_OF_SIEGE = 1;
    protected static final int COND_OWNER = 2;

    public CourtInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    public void onBypassFeedback(Player player, String string) {
        if (!CourtInstance.canBypassCheck((Player)player, (NpcInstance)this)) {
            return;
        }
        int n = this.validateCondition(player);
        if (n <= 0) {
            return;
        }
        if (n == 1) {
            return;
        }
        if ((player.getClanPrivileges() & 0x80000) != 524288) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_ARE_NOT_AUTHORIZED_TO_DO_THAT);
            return;
        }
        if (n == 2) {
            if (string.startsWith("Chat")) {
                int n2 = 0;
                try {
                    n2 = Integer.parseInt(string.substring(5));
                }
                catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                }
                catch (NumberFormatException numberFormatException) {
                    // empty catch block
                }
                this.showChatWindow(player, n2, new Object[0]);
                return;
            }
            if (string.startsWith("gotoleader")) {
                if (player.getClan() != null) {
                    Player player2 = player.getClan().getLeader().getPlayer();
                    if (player2 == null) {
                        return;
                    }
                    if (player2.getEffectList().getEffectsBySkillId(3632) != null) {
                        if (Call.canSummonHere((Player)player2) != null) {
                            return;
                        }
                        if (Call.canBeSummoned((Creature)player) == null) {
                            player.teleToLocation(Location.findAroundPosition((GameObject)player2, (int)100));
                        }
                        return;
                    }
                    this.showChatWindow(player, "castle/CourtMagician/CourtMagician-nogate.htm", new Object[0]);
                }
            } else {
                super.onBypassFeedback(player, string);
            }
        }
    }

    public void showChatWindow(Player player, int n, Object ... objectArray) {
        player.sendActionFailed();
        Object object = "castle/CourtMagician/CourtMagician-no.htm";
        int n2 = this.validateCondition(player);
        if (n2 > 0) {
            if (n2 == 1) {
                object = "castle/CourtMagician/CourtMagician-busy.htm";
            } else if (n2 == 2) {
                object = n == 0 ? "castle/CourtMagician/CourtMagician.htm" : "castle/CourtMagician/CourtMagician-" + n + ".htm";
            }
        }
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, (NpcInstance)this);
        npcHtmlMessage.setFile((String)object);
        npcHtmlMessage.replace("%objectId%", String.valueOf(this.getObjectId()));
        npcHtmlMessage.replace("%npcname%", this.getName());
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    protected int validateCondition(Player player) {
        if (player.isGM()) {
            return 2;
        }
        if (this.getCastle() != null && this.getCastle().getId() > 0 && player.getClan() != null) {
            if (this.getCastle().getSiegeEvent().isInProgress()) {
                return 1;
            }
            if (this.getCastle().getOwnerId() == player.getClanId()) {
                return 2;
            }
        }
        return 0;
    }

    protected boolean canInteractWithKarmaPlayer() {
        return true;
    }

    protected boolean canInteractWithCursedWeaponPlayer() {
        return true;
    }
}
