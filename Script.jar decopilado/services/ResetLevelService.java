/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.Config
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.base.Experience
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.NpcHtmlMessage
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.utils.Log
 */
package services;

import java.util.Arrays;
import l2.gameserver.Config;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.base.Experience;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.scripts.Functions;
import l2.gameserver.utils.Log;

public class ResetLevelService
extends Functions {
    private static final String hL = "ServiceResetLevelCount";
    private static final String hM = "@ResetLevelService_ReuseDelay";

    public void reset_service_page() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        if (!Config.SERVICE_RESET_LEVEL_ENABLED) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_disabled.htm"));
            return;
        }
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(5).setFile("scripts/services/reset_level_services.htm");
        npcHtmlMessage.replace("%reward_item_id%", Arrays.toString(Config.SERVICE_RESET_LEVEL_ITEM_ID));
        npcHtmlMessage.replace("%reward_item_count%", Arrays.toString(Config.SERVICE_RESET_LEVEL_ITEM_COUNT));
        npcHtmlMessage.replace("%current_level%", String.valueOf(player.getLevel()));
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    public void reset_service() {
        Player player = this.getSelf();
        if (player == null || !ResetLevelService.CheckPlayerConditions((Player)player)) {
            return;
        }
        if (!Config.SERVICE_RESET_LEVEL_ENABLED) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_disabled.htm"));
            return;
        }
        if (!player.getActiveClass().isBase() && !Config.SERVICE_RESET_ALLOW_FOR_SUBCLASS) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/changebase_err02.htm"));
            return;
        }
        if (player.getLevel() < Config.SERVICE_RESET_LEVEL_NEED || (long)player.getLevel() > player.getMaxExp()) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/reset_level_services_low.htm").replace("%required_level%", String.valueOf(Config.SERVICE_RESET_LEVEL_NEED)));
            return;
        }
        if (Config.SERVICE_RESET_LEVEL_LIMIT > 0 && player.getVarInt(hL, 0) >= Config.SERVICE_RESET_LEVEL_LIMIT) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/reset_level_services_limit.htm").replace("%reset_count_limit%", String.valueOf(Config.SERVICE_RESET_LEVEL_LIMIT)));
            return;
        }
        if (Config.SERVICE_RESET_REUSE_DELAY > 0L && player.getVarLong(hM, 0L) > System.currentTimeMillis()) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/reset_level_service_time_not_come.htm").replace("%reuse_delay%", String.valueOf(Math.max(player.getVarLong(hM, 0L) - System.currentTimeMillis(), 0L) / 60000L)));
            return;
        }
        if (Config.SERVICE_RESET_LEVEL_LIMIT >= 0) {
            player.setVar(hL, player.getVarInt(hL, 0) + 1, -1L);
        }
        if (Config.SERVICE_RESET_REUSE_DELAY > 0L) {
            player.setVar(hM, System.currentTimeMillis() + Config.SERVICE_RESET_REUSE_DELAY, -1L);
        }
        assert (Config.SERVICE_RESET_LEVEL_ITEM_ID.length == Config.SERVICE_RESET_LEVEL_ITEM_COUNT.length);
        for (int i = 0; i < Config.SERVICE_RESET_LEVEL_ITEM_ID.length; ++i) {
            ResetLevelService.addItem((Playable)player, (int)Config.SERVICE_RESET_LEVEL_ITEM_ID[i], (long)Config.SERVICE_RESET_LEVEL_ITEM_COUNT[i]);
        }
        player.addExpAndSp(Experience.LEVEL[player.getLevel() - Config.SERVICE_RESET_LEVEL_REMOVE] - player.getExp(), 0L, 0L, 0L, false, false);
        Log.service((String)"ResetLevelService", (Player)player, (String)("Level reset on " + Config.SERVICE_RESET_LEVEL_REMOVE + " and received an award" + Config.SERVICE_RESET_LEVEL_ITEM_ID + " amount " + Config.SERVICE_RESET_LEVEL_ITEM_COUNT));
    }
}
