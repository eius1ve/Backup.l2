/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.telegram.impl;

import l2.gameserver.Config;
import l2.gameserver.dao.AccountBonusDAO;
import l2.gameserver.handler.telegram.ITelegramCommandHandler;
import l2.gameserver.handler.telegram.TelegramApi;
import l2.gameserver.model.Player;
import l2.gameserver.model.World;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExBR_PremiumState;
import l2.gameserver.network.l2.s2c.UserInfoType;
import l2.gameserver.utils.Log;

public class SetPaCommand
implements ITelegramCommandHandler {
    private final String[] ap = new String[]{"/set_pa"};

    @Override
    public void handle(String string, String string2) throws Exception {
        int n;
        if (!Config.SERVICES_RATE_ENABLED) {
            TelegramApi.sendMessage(string, "Service Premium Account is Disabled");
            return;
        }
        if (string2 == null || string2.trim().isEmpty()) {
            TelegramApi.sendForceReplyMessage(string, "Usage: /set_pa <pa_id> <player_name>", "Enter player premium id and name");
            return;
        }
        String[] stringArray = string2.split(" ");
        if (stringArray.length < 2) {
            TelegramApi.sendMessage(string, "Usage: /set_pa <pa_id> <player_name>");
            return;
        }
        try {
            n = Integer.parseInt(stringArray[0]);
        }
        catch (NumberFormatException numberFormatException) {
            TelegramApi.sendMessage(string, "PA id must be a number.");
            return;
        }
        String string3 = stringArray[1];
        Player player = World.getPlayer(string3);
        if (player == null) {
            TelegramApi.sendMessage(string, "Player \"" + string3 + "\" not found.");
            return;
        }
        Config.RateBonusInfo rateBonusInfo = null;
        for (Config.RateBonusInfo rateBonusInfo2 : Config.SERVICES_RATE_BONUS_INFO) {
            if (rateBonusInfo2.id != n) continue;
            rateBonusInfo = rateBonusInfo2;
            break;
        }
        if (rateBonusInfo == null) {
            TelegramApi.sendMessage(string, "Undefined bonus with ID " + n + ".");
            return;
        }
        AccountBonusDAO.getInstance().store(player.getAccountName(), rateBonusInfo.makeBonus());
        player.stopBonusTask();
        player.startBonusTask();
        if (player.getParty() != null) {
            player.getParty().recalculatePartyData();
        }
        player.broadcastUserInfo(false, new UserInfoType[0]);
        player.sendPacket((IStaticPacket)new ExBR_PremiumState(player, true));
        Log.add("Admin Command PA Bonus added " + player.getName() + "|" + player.getObjectId() + "|rate bonus|" + rateBonusInfo.id + "|" + rateBonusInfo.bonusTimeSeconds + "|", "services");
        TelegramApi.sendMessage(string, "Premium Account added for " + player.getName() + ". Bonus ID: " + rateBonusInfo.id);
    }

    @Override
    public String[] getCommands() {
        return this.ap;
    }

    @Override
    public boolean requiresParams() {
        return true;
    }
}
