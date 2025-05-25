/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.commons.collections.CollectionUtils;
import l2.gameserver.Config;
import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.Zone;
import l2.gameserver.model.actor.instances.player.TpBookMark;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.tables.SkillTable;

public class RequestTeleportBookMark
extends L2GameClientPacket {
    private static final Skill t = SkillTable.getInstance().getInfo(2588, 1);
    private static final String eC = "bookmark_teleport_reuse";
    private int kk;

    @Override
    protected void readImpl() {
        this.kk = this.readD();
    }

    @Override
    protected void runImpl() {
        long l;
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        if (player.isActionsDisabled() || player.isTeleporting()) {
            player.sendActionFailed();
            return;
        }
        TpBookMark tpBookMark = CollectionUtils.safeGet(player.getTpBookMarks(), this.kk - 1);
        if (tpBookMark == null) {
            return;
        }
        if (player.isActionBlocked("use_bookmark") || player.isInZone(Zone.ZoneType.epic) || player.isInZone(Zone.ZoneType.no_summon) || player.isInZone(Zone.ZoneType.no_escape) || player.isInZone(Zone.ZoneType.no_restart)) {
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
        if (player.isInWater() || player.isInBoat()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_USE_MY_TELEPORTS_UNDERWATER);
            return;
        }
        if (player.getInventory().getItemByItemId(Config.EX_USE_TELEPORT_BOOK_SCROLL) == null) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_TELEPORT_BECAUSE_YOU_DO_NOT_HAVE_A_TELEPORT_ITEM);
            return;
        }
        long l2 = System.currentTimeMillis();
        long l3 = player.getVarLong(eC, 0L);
        if (Config.EX_TELEPORT_BOOK_SCROLL > 0L && (l = l2 - l3) < Config.EX_TELEPORT_BOOK_SCROLL) {
            long l4 = Config.EX_TELEPORT_BOOK_SCROLL - l;
            long l5 = l4 / 1000L;
            long l6 = l5 / 60L;
            long l7 = l5 % 60L;
            if (l5 >= 60L) {
                player.sendPacket((IStaticPacket)((SystemMessage)((SystemMessage)new SystemMessage(SystemMsg.THERE_ARE_S2_MINUTES_S3_SECONDS_REMAINING_IN_S1S_REUSE_TIME).addSkillName(t)).addNumber(l6)).addNumber(l7));
            } else {
                player.sendPacket((IStaticPacket)((SystemMessage)new SystemMessage(SystemMsg.THERE_ARE_S2_SECONDS_REMAINING_IN_S1S_REUSE_TIME).addNumber(l7)).addSkillName(t));
            }
            return;
        }
        player.setVar(eC, l2, -1L);
        player.getVars().set("teleport_bookmark", tpBookMark);
        player.getAI().Cast(t, player);
    }
}
