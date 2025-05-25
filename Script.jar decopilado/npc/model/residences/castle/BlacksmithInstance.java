/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.ai.CtrlIntention
 *  l2.gameserver.instancemanager.CastleManorManager
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.MyTargetSelected
 *  l2.gameserver.network.l2.s2c.NpcHtmlMessage
 *  l2.gameserver.network.l2.s2c.ValidateLocation
 *  l2.gameserver.templates.npc.NpcTemplate
 */
package npc.model.residences.castle;

import l2.gameserver.ai.CtrlIntention;
import l2.gameserver.instancemanager.CastleManorManager;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.MyTargetSelected;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.network.l2.s2c.ValidateLocation;
import l2.gameserver.templates.npc.NpcTemplate;

public class BlacksmithInstance
extends NpcInstance {
    protected static final int COND_ALL_FALSE = 0;
    protected static final int COND_BUSY_BECAUSE_OF_SIEGE = 1;
    protected static final int COND_OWNER = 2;

    public BlacksmithInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    public void onAction(Player player, boolean bl) {
        if (this != player.getTarget()) {
            player.setTarget((GameObject)this);
            player.sendPacket((IStaticPacket)new MyTargetSelected(this.getObjectId(), player.getLevel() - this.getLevel()));
            player.sendPacket((IStaticPacket)new ValidateLocation((Creature)this));
        } else {
            player.sendPacket((IStaticPacket)new MyTargetSelected(this.getObjectId(), player.getLevel() - this.getLevel()));
            if (!this.isInActingRange((GameObject)player)) {
                player.getAI().setIntention(CtrlIntention.AI_INTENTION_INTERACT, (Object)this);
                player.sendActionFailed();
            } else {
                if (CastleManorManager.getInstance().isDisabled()) {
                    NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, (NpcInstance)this);
                    npcHtmlMessage.setFile("npcdefault.htm");
                    player.sendPacket((IStaticPacket)npcHtmlMessage);
                } else {
                    this.o(player, 0);
                }
                player.sendActionFailed();
            }
        }
    }

    public void onBypassFeedback(Player player, String string) {
        if (!BlacksmithInstance.canBypassCheck((Player)player, (NpcInstance)this)) {
            return;
        }
        if (CastleManorManager.getInstance().isDisabled()) {
            NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, (NpcInstance)this);
            npcHtmlMessage.setFile("npcdefault.htm");
            player.sendPacket((IStaticPacket)npcHtmlMessage);
            return;
        }
        int n = this.validateCondition(player);
        if (n <= 0) {
            return;
        }
        if (n == 1) {
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
                this.o(player, n2);
            } else {
                super.onBypassFeedback(player, string);
            }
        }
    }

    private void o(Player player, int n) {
        player.sendActionFailed();
        Object object = "castle/blacksmith/castleblacksmith-no.htm";
        int n2 = this.validateCondition(player);
        if (n2 > 0) {
            if (n2 == 1) {
                object = "castle/blacksmith/castleblacksmith-busy.htm";
            } else if (n2 == 2) {
                object = n == 0 ? "castle/blacksmith/castleblacksmith.htm" : "castle/blacksmith/castleblacksmith-" + n + ".htm";
            }
        }
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, (NpcInstance)this);
        npcHtmlMessage.setFile((String)object);
        npcHtmlMessage.replace("%castleid%", Integer.toString(this.getCastle().getId()));
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
            if (this.getCastle().getOwnerId() == player.getClanId() && (player.getClanPrivileges() & 0x20000) == 131072) {
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
