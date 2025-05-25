/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.dbutils.DbUtils
 *  l2.gameserver.data.htm.HtmCache
 *  l2.gameserver.database.DatabaseFactory
 *  l2.gameserver.listener.actor.player.OnPlayerEnterListener
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.pledge.Clan
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.NpcHtmlMessage
 */
package services.community;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import l2.commons.dbutils.DbUtils;
import l2.gameserver.data.htm.HtmCache;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.listener.actor.player.OnPlayerEnterListener;
import l2.gameserver.model.Player;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;

private class ClanCommunity.Listener
implements OnPlayerEnterListener {
    private ClanCommunity.Listener() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    public void onPlayerEnter(Player player) {
        Object object;
        Clan clan;
        block7: {
            int n;
            String string;
            ResultSet resultSet;
            PreparedStatement preparedStatement;
            block6: {
                clan = player.getClan();
                if (clan == null || clan.getLevel() < 2) {
                    return;
                }
                if (clan.getNotice() != null) break block7;
                object = null;
                preparedStatement = null;
                resultSet = null;
                string = "";
                n = 0;
                try {
                    object = DatabaseFactory.getInstance().getConnection();
                    preparedStatement = object.prepareStatement("SELECT * FROM `bbs_clannotice` WHERE `clan_id` = ? and type != 2");
                    preparedStatement.setInt(1, clan.getClanId());
                    resultSet = preparedStatement.executeQuery();
                    if (!resultSet.next()) break block6;
                    string = resultSet.getString("notice");
                    n = resultSet.getInt("type");
                }
                catch (Exception exception) {
                    DbUtils.closeQuietly((Connection)object, (Statement)preparedStatement, resultSet);
                    catch (Throwable throwable) {
                        DbUtils.closeQuietly((Connection)object, preparedStatement, resultSet);
                        throw throwable;
                    }
                }
            }
            DbUtils.closeQuietly((Connection)object, (Statement)preparedStatement, (ResultSet)resultSet);
            clan.setNotice(n == 1 ? string.replace("\n", "<br1>\n") : "");
        }
        if (!clan.getNotice().isEmpty()) {
            object = HtmCache.getInstance().getNotNull("scripts/services/community/clan_popup.htm", player);
            object = ((String)object).replace("%pledge_name%", clan.getName());
            object = ((String)object).replace("%content%", clan.getNotice());
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(0).setHtml((String)object));
        }
    }
}
