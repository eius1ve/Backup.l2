/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.telegram.impl;

import l2.gameserver.handler.telegram.ITelegramCommandHandler;
import l2.gameserver.handler.telegram.TelegramApi;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.oly.HeroController;
import l2.gameserver.network.l2.s2c.UserInfoType;

public class SetHeroCommand
implements ITelegramCommandHandler {
    private final String[] al = new String[]{"/sethero"};

    @Override
    public void handle(String string, String string2) throws Exception {
        if (string2 == null || string2.trim().isEmpty()) {
            TelegramApi.sendForceReplyMessage(string, "Usage: /sethero <player_name>", "Enter time player name");
            return;
        }
        String string3 = string2.trim();
        Player player = GameObjectsStorage.getPlayer(string3);
        if (player == null) {
            TelegramApi.sendMessage(string, "Player \"" + string3 + "\" not found.");
            return;
        }
        if (player.isHero()) {
            player.setHero(false);
            player.updatePledgeClass();
            HeroController.removeSkills(player, true);
            TelegramApi.sendMessage(string, "Hero status removed for " + string3);
        } else {
            player.setHero(true);
            player.updatePledgeClass();
            HeroController.addSkills(player, true);
            TelegramApi.sendMessage(string, "Hero status granted for " + string3);
        }
        player.sendSkillList();
        player.sendMessage("Your hero status has been updated via Telegram command.");
        player.broadcastUserInfo(false, new UserInfoType[0]);
    }

    @Override
    public String[] getCommands() {
        return this.al;
    }

    @Override
    public boolean requiresParams() {
        return true;
    }
}
