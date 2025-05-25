/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.dbutils.DbUtils
 *  l2.gameserver.Config
 *  l2.gameserver.data.htm.HtmCache
 *  l2.gameserver.database.DatabaseFactory
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.NpcHtmlMessage
 *  l2.gameserver.network.l2.s2c.ShowBoard
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.utils.ItemFunctions
 *  l2.gameserver.utils.Log
 */
package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import l2.commons.dbutils.DbUtils;
import l2.gameserver.Config;
import l2.gameserver.data.htm.HtmCache;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.network.l2.s2c.ShowBoard;
import l2.gameserver.scripts.Functions;
import l2.gameserver.utils.ItemFunctions;
import l2.gameserver.utils.Log;

public class ChangeSex
extends Functions {
    public void changesex_page() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        if (!Config.SERVICES_CHANGE_SEX_ENABLED) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_disabled.htm"));
            return;
        }
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(5).setFile("scripts/services/sex_change.htm");
        npcHtmlMessage.replace("%item_id%", String.valueOf(Config.SERVICES_CHANGE_SEX_ITEM));
        npcHtmlMessage.replace("%item_count%", String.valueOf(Config.SERVICES_CHANGE_SEX_PRICE));
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    public void change_sex() {
        Player player = this.getSelf();
        if (player == null || !ChangeSex.CheckPlayerConditions((Player)player)) {
            return;
        }
        if (!Config.SERVICES_CHANGE_SEX_ENABLED) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_disabled.htm"));
            return;
        }
        if (!player.isInPeaceZone() || !player.getReflection().isDefault()) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_peace_zone.htm"));
            return;
        }
        if (ItemFunctions.getItemCount((Playable)player, (int)Config.SERVICES_CHANGE_SEX_ITEM) < (long)Config.SERVICES_CHANGE_SEX_PRICE) {
            if (Config.SERVICES_CHANGE_SEX_ITEM == 57) {
                player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
            } else {
                player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_ITEM_COUNT);
            }
            return;
        }
        ItemFunctions.removeItem((Playable)player, (int)Config.SERVICES_CHANGE_SEX_ITEM, (long)Config.SERVICES_CHANGE_SEX_PRICE, (boolean)true);
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("UPDATE characters SET sex = ? WHERE obj_Id = ?");
            preparedStatement.setInt(1, player.getSex() == 1 ? 0 : 1);
            preparedStatement.setInt(2, player.getObjectId());
            preparedStatement.executeUpdate();
        }
        catch (SQLException sQLException) {
            try {
                throw new RuntimeException(sQLException);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly((Connection)connection, preparedStatement);
                throw throwable;
            }
        }
        DbUtils.closeQuietly((Connection)connection, (Statement)preparedStatement);
        player.setHairColor(0);
        player.setHairStyle(0);
        player.setFace(0);
        Log.service((String)"ChangeSex", (Player)player, (String)(" sex changed to " + (player.getSex() == 1 ? "male" : "female") + " for " + Config.SERVICES_CHANGE_SEX_ITEM + " " + Config.SERVICES_CHANGE_SEX_PRICE));
        player.logout();
    }

    public void changesex_commuity_page() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        if (!Config.SERVICES_CHANGE_SEX_ENABLED) {
            ShowBoard.separateAndSend((String)HtmCache.getInstance().getNotNull("scripts/services/community/services/services_disabled.htm", player), (Player)player);
            return;
        }
        if (!player.isInPeaceZone()) {
            ShowBoard.separateAndSend((String)HtmCache.getInstance().getNotNull("scripts/services/community/services/services_peace_zone.htm", player), (Player)player);
            return;
        }
        String string = HtmCache.getInstance().getNotNull("scripts/services/community/services/sex_change.htm", player);
        string = string.replace("%item_id%", String.valueOf(Config.SERVICES_CHANGE_SEX_ITEM));
        string = string.replace("%item_count%", String.valueOf(Config.SERVICES_CHANGE_SEX_PRICE));
        ShowBoard.separateAndSend((String)string, (Player)player);
    }

    public void change_sex_community() {
        Player player = this.getSelf();
        if (player == null || !ChangeSex.CheckPlayerConditions((Player)player)) {
            return;
        }
        if (!Config.SERVICES_CHANGE_SEX_ENABLED) {
            ShowBoard.separateAndSend((String)HtmCache.getInstance().getNotNull("scripts/services/community/services/services_disabled.htm", player), (Player)player);
            return;
        }
        if (!player.isInPeaceZone()) {
            ShowBoard.separateAndSend((String)HtmCache.getInstance().getNotNull("scripts/services/community/services/services_peace_zone.htm", player), (Player)player);
            return;
        }
        if (ItemFunctions.getItemCount((Playable)player, (int)Config.SERVICES_CHANGE_SEX_ITEM) < (long)Config.SERVICES_CHANGE_SEX_PRICE) {
            ShowBoard.separateAndSend((String)HtmCache.getInstance().getNotNull("scripts/services/community/services/services_incorrect_items.htm", player), (Player)player);
            return;
        }
        ItemFunctions.removeItem((Playable)player, (int)Config.SERVICES_CHANGE_SEX_ITEM, (long)Config.SERVICES_CHANGE_SEX_PRICE, (boolean)true);
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("UPDATE characters SET sex = ? WHERE obj_Id = ?");
            preparedStatement.setInt(1, player.getSex() == 1 ? 0 : 1);
            preparedStatement.setInt(2, player.getObjectId());
            preparedStatement.executeUpdate();
        }
        catch (SQLException sQLException) {
            try {
                throw new RuntimeException(sQLException);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly((Connection)connection, preparedStatement);
                throw throwable;
            }
        }
        DbUtils.closeQuietly((Connection)connection, (Statement)preparedStatement);
        player.setHairColor(0);
        player.setHairStyle(0);
        player.setFace(0);
        Log.service((String)"ChangeSex", (Player)player, (String)(" sex changed to " + (player.getSex() == 1 ? "male" : "female") + " for " + Config.SERVICES_CHANGE_SEX_ITEM + " " + Config.SERVICES_CHANGE_SEX_PRICE));
        player.logout();
    }
}
