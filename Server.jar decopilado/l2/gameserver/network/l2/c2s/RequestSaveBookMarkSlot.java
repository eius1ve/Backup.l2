/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.Config;
import l2.gameserver.dao.CharacterTPBookmarkDAO;
import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.model.Player;
import l2.gameserver.model.Zone;
import l2.gameserver.model.actor.instances.player.TpBookMark;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExGetBookMarkInfo;

public class RequestSaveBookMarkSlot
extends L2GameClientPacket {
    private String _name;
    private String cX;
    private int kp;

    @Override
    protected void readImpl() {
        this._name = this.readS(32);
        this.kp = this.readD();
        this.cX = this.readS(4);
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        if (player.getTpBookMarks().size() >= player.getTpBookmarkSize()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOUR_NUMBER_OF_MY_TELEPORTS_SLOTS_HAS_REACHED_ITS_MAXIMUM_LIMIT);
            return;
        }
        if (player.isActionBlocked("save_bookmark") || player.isInBoat() || player.isInZone(Zone.ZoneType.epic) || player.isInZone(Zone.ZoneType.no_summon) || player.isInZone(Zone.ZoneType.no_escape) || player.isInZone(Zone.ZoneType.no_restart)) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_USE_MY_TELEPORTS_IN_THIS_AREA);
            return;
        }
        if (player.getActiveWeaponFlagAttachment() != null) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_TELEPORT_WHILE_IN_POSSESSION_OF_A_WARD);
            return;
        }
        if (player.isOlyParticipant()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_USE_MY_TELEPORTS_WHILE_PARTICIPATING_IN_AN_OLYMPIAD_MATCH);
            return;
        }
        if (player.getReflection() != ReflectionManager.DEFAULT) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_USE_MY_TELEPORTS_IN_AN_INSTANT_ZONE);
            return;
        }
        if (player.isInDuel()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_USE_MY_TELEPORTS_DURING_A_DUEL);
            return;
        }
        if (player.isInCombat() || player.getPvpFlag() != 0) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_USE_MY_TELEPORTS_DURING_A_BATTLE);
            return;
        }
        if (player.isOnSiegeField() || player.isInZoneBattle()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_USE_MY_TELEPORTS_WHILE_PARTICIPATING_A_LARGESCALE_BATTLE_SUCH_AS_A_CASTLE_SIEGE_FORTRESS_SIEGE_OR_HIDEOUT_SIEGE);
            return;
        }
        if (player.isFlying()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_USE_MY_TELEPORTS_WHILE_FLYING);
            return;
        }
        if (player.isInWater()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_USE_MY_TELEPORTS_UNDERWATER);
            return;
        }
        TpBookMark tpBookMark = new TpBookMark(player.getX(), player.getY(), player.getZ(), this.kp, this._name, this.cX);
        if (player.getTpBookMarks().contains(tpBookMark)) {
            return;
        }
        if (Config.EX_USE_TELEPORT_FLAG && !player.consumeItem(Config.EX_USE_TELEPORT_BOOL_SCROLL_SAVE_ITEM, 1L)) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_BOOKMARK_THIS_LOCATION_BECAUSE_YOU_DO_NOT_HAVE_A_MY_TELEPORT_FLAG);
            return;
        }
        player.getTpBookMarks().add(tpBookMark);
        CharacterTPBookmarkDAO.getInstance().insert(player, tpBookMark);
        player.sendPacket((IStaticPacket)new ExGetBookMarkInfo(player));
    }
}
