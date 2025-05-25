/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.telegram.impl;

import l2.gameserver.handler.telegram.ITelegramCommandHandler;
import l2.gameserver.handler.telegram.TelegramApi;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.model.base.CategoryData;
import l2.gameserver.model.entity.oly.NoblesController;
import l2.gameserver.network.l2.s2c.UserInfoType;

public class SetNobleCommand
implements ITelegramCommandHandler {
    private final String[] ao = new String[]{"/setnoble"};

    @Override
    public void handle(String string, String string2) throws Exception {
        if (string2 == null || string2.trim().isEmpty()) {
            TelegramApi.sendForceReplyMessage(string, "Usage: /setnoble <player_name>", "Enter time player name");
            return;
        }
        String string3 = string2.trim();
        Player player = GameObjectsStorage.getPlayer(string3);
        if (player == null) {
            TelegramApi.sendMessage(string, "Player \"" + string3 + "\" not found in game.");
            return;
        }
        if (!CategoryData.fourth_class_group.isPlayerBaseClassBelong(player)) {
            TelegramApi.sendMessage(string, string3 + " did not receive the 3nd profession to obtain the status of a Nobles");
        }
        if (player.isNoble()) {
            player.setNoble(false);
            NoblesController.getInstance().removeNoble(player);
            player.sendMessage("Your noble status has been removed via Telegram command.");
            TelegramApi.sendMessage(string, "Noble status removed for " + string3);
        } else {
            player.setNoble(true);
            NoblesController.getInstance().addNoble(player);
            player.sendMessage("You have been granted noble status via Telegram command.");
            TelegramApi.sendMessage(string, "Noble status granted for " + string3);
        }
        player.updatePledgeClass();
        player.updateNobleSkills();
        player.sendSkillList();
        player.broadcastUserInfo(false, new UserInfoType[0]);
    }

    @Override
    public String[] getCommands() {
        return this.ao;
    }

    @Override
    public boolean requiresParams() {
        return true;
    }
}
