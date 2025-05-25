/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.model.Request;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SendTradeRequest;
import l2.gameserver.network.l2.s2c.SystemMessage;

public class TradeRequest
extends L2GameClientPacket {
    private int fW;

    @Override
    protected void readImpl() {
        this.fW = this.readD();
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        if (player.isOutOfControl()) {
            player.sendActionFailed();
            return;
        }
        if (!player.getPlayerAccess().UseTrade) {
            player.sendPacket((IStaticPacket)SystemMsg.SOME_LINEAGE_II_FEATURES_HAVE_BEEN_LIMITED_FOR_FREE_TRIALS_);
            player.sendActionFailed();
            return;
        }
        if (player.isInStoreMode()) {
            player.sendPacket((IStaticPacket)SystemMsg.WHILE_OPERATING_A_PRIVATE_STORE_OR_WORKSHOP_YOU_CANNOT_DISCARD_DESTROY_OR_TRADE_AN_ITEM);
            return;
        }
        if (player.isFishing()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_DO_THAT_WHILE_FISHING);
            return;
        }
        if (player.isInTrade()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_ARE_ALREADY_TRADING_WITH_SOMEONE);
            return;
        }
        if (player.isProcessingRequest()) {
            player.sendPacket((IStaticPacket)SystemMsg.WAITING_FOR_ANOTHER_REPLY);
            return;
        }
        if (player.isTradeBannedByGM()) {
            player.sendPacket((IStaticPacket)SystemMsg.SOME_LINEAGE_II_FEATURES_HAVE_BEEN_LIMITED_FOR_FREE_TRIALS_);
            return;
        }
        GameObject gameObject = player.getVisibleObject(this.fW);
        if (gameObject == null || !gameObject.isPlayer() || gameObject == player) {
            player.sendPacket((IStaticPacket)SystemMsg.THAT_IS_AN_INCORRECT_TARGET);
            return;
        }
        if (!gameObject.isInActingRange(player) || !gameObject.isInRangeZ(player, 185L)) {
            player.sendPacket((IStaticPacket)SystemMsg.YOUR_TARGET_IS_OUT_OF_RANGE);
            return;
        }
        if (gameObject.getPlayer().getVarB("blocktrade@")) {
            player.sendMessage(new CustomMessage("voicecommands.trade_request_blocked", player, new Object[0]).addCharName(gameObject.getPlayer()));
            return;
        }
        Player player2 = (Player)gameObject;
        if (!player2.getPlayerAccess().UseTrade) {
            player.sendPacket((IStaticPacket)SystemMsg.THAT_IS_AN_INCORRECT_TARGET);
            return;
        }
        if (player2.isTradeBannedByGM()) {
            player.sendPacket((IStaticPacket)SystemMsg.SOME_LINEAGE_II_FEATURES_HAVE_BEEN_LIMITED_FOR_FREE_TRIALS_);
            return;
        }
        if (player2.getBlockList().isInBlockList(player)) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_BEEN_BLOCKED_FROM_CHATTING_WITH_THAT_CONTACT);
            return;
        }
        if (player2.getTradeRefusal() || player2.isBusy()) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.C1_IS_ON_ANOTHER_TASK).addString(player2.getName()));
            return;
        }
        new Request(Request.L2RequestType.TRADE_REQUEST, player, player2).setTimeout(10000L);
        player2.sendPacket((IStaticPacket)new SendTradeRequest(player.getObjectId()));
        player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_HAVE_REQUESTED_A_TRADE_WITH_C1).addString(player2.getName()));
    }
}
