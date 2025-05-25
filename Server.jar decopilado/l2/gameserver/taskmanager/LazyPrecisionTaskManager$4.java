/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.taskmanager;

import l2.commons.lang.reference.HardReference;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.Config;
import l2.gameserver.dao.AccountBonusDAO;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.s2c.ExBR_PremiumState;
import l2.gameserver.network.l2.s2c.ExShowScreenMessage;

class LazyPrecisionTaskManager.4
extends RunnableImpl {
    final /* synthetic */ HardReference val$playerRef;

    LazyPrecisionTaskManager.4(HardReference hardReference) {
        this.val$playerRef = hardReference;
    }

    @Override
    public void runImpl() throws Exception {
        Player player = (Player)this.val$playerRef.get();
        if (player == null) {
            return;
        }
        player.getBonus().reset();
        if (player.getParty() != null) {
            player.getParty().recalculatePartyData();
        }
        String string = new CustomMessage("scripts.services.RateBonus.LuckEnded", player, new Object[0]).toString();
        player.sendPacket(new ExShowScreenMessage(string, 10000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true), new ExBR_PremiumState(player, false));
        if (Config.AUTO_LOOT_ONLY_FOR_PREMIUM) {
            player.setAutoLoot(false);
            player.setAutoLootHerbs(false);
            player.setAutoLootAdena(false);
        }
        if (Config.XP_FREEZ_ONLY_FOR_PREMIUM) {
            player.unsetVar("NoExp");
        }
        player.sendMessage(string);
        player.updatePremiumSkills();
        player.sendSkillList();
        AccountBonusDAO.getInstance().delete(player.getAccountName());
    }
}
