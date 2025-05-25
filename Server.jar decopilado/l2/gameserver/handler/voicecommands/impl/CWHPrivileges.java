/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.voicecommands.impl;

import java.util.List;
import l2.gameserver.Config;
import l2.gameserver.data.htm.HtmCache;
import l2.gameserver.database.mysql;
import l2.gameserver.handler.voicecommands.IVoicedCommandHandler;
import l2.gameserver.model.Player;
import l2.gameserver.model.pledge.UnitMember;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.scripts.Functions;

public class CWHPrivileges
implements IVoicedCommandHandler {
    private String[] o = new String[]{"clan"};

    @Override
    public String[] getVoicedCommandList() {
        return this.o;
    }

    @Override
    public boolean useVoicedCommand(String string, Player player, String string2) {
        if (player.getClan() == null) {
            return false;
        }
        if (string.equals("clan") && Config.ALT_ALLOW_CLAN_COMMAND_ALLOW_WH) {
            Object object;
            if (Config.ALT_ALLOW_CLAN_COMMAND_ONLY_FOR_CLAN_LEADER && !player.isClanLeader()) {
                return false;
            }
            if ((player.getClanPrivileges() & 0x10) != 16) {
                return false;
            }
            if (string2 != null && ((String[])(object = string2.split(" "))).length > 0) {
                if (object[0].equalsIgnoreCase("allowwh") && ((String[])object).length > 1) {
                    UnitMember unitMember = player.getClan().getAnyMember(object[1]);
                    if (unitMember != null && unitMember.getPlayer() != null) {
                        if (unitMember.getPlayer().getVarB("canWhWithdraw")) {
                            unitMember.getPlayer().unsetVar("canWhWithdraw");
                            player.sendMessage(new CustomMessage("usercommandhandlers.CWHPrivileges.PrivilegeRemoved", player, new Object[0]));
                        } else {
                            unitMember.getPlayer().setVar("canWhWithdraw", "1", -1L);
                            player.sendMessage(new CustomMessage("usercommandhandlers.CWHPrivileges.PrivilegeGiven", player, new Object[0]));
                        }
                    } else if (unitMember != null) {
                        int n = mysql.simple_get_int("value", "character_variables", "obj_id=" + unitMember.getObjectId() + " AND name LIKE 'canWhWithdraw'");
                        if (n > 0) {
                            mysql.set("DELETE FROM `character_variables` WHERE obj_id=" + unitMember.getObjectId() + " AND name LIKE 'canWhWithdraw' LIMIT 1");
                            player.sendMessage(new CustomMessage("usercommandhandlers.CWHPrivileges.PrivilegeRemoved", player, new Object[0]));
                        } else {
                            mysql.set("INSERT INTO character_variables  (obj_id, type, name, value, expire_time) VALUES (" + unitMember.getObjectId() + ",'user-var','canWhWithdraw','1',-1)");
                            player.sendMessage(new CustomMessage("usercommandhandlers.CWHPrivileges.PrivilegeGiven", player, new Object[0]));
                        }
                    } else {
                        player.sendMessage(new CustomMessage("usercommandhandlers.CWHPrivileges.PlayerNotFound", player, new Object[0]));
                    }
                } else if (object[0].equalsIgnoreCase("list")) {
                    StringBuilder stringBuilder = new StringBuilder("SELECT `obj_id` FROM `character_variables` WHERE `obj_id` IN (");
                    List<UnitMember> list = player.getClan().getAllMembers();
                    for (int i = 0; i < list.size(); ++i) {
                        stringBuilder.append(list.get(i).getObjectId());
                        if (i >= list.size() - 1) continue;
                        stringBuilder.append(",");
                    }
                    stringBuilder.append(") AND `name`='canWhWithdraw'");
                    List<Object> list2 = mysql.get_array(stringBuilder.toString());
                    stringBuilder = new StringBuilder("<html><body>Clan member Warehouse privilege<br><br><table>");
                    for (Object object2 : list2) {
                        for (UnitMember unitMember : list) {
                            if (unitMember.getObjectId() != Integer.parseInt(object2.toString())) continue;
                            stringBuilder.append("<tr><td width=10></td><td width=60>").append(unitMember.getName()).append("</td><td width=20><button width=50 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\" action=\"bypass -h user_clan allowwh ").append(unitMember.getName()).append("\" value=\"Remove\">").append("<br></td></tr>");
                        }
                    }
                    stringBuilder.append("<tr><td width=10></td><td width=20><button width=60 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\" action=\"bypass -h user_clan\" value=\"Back\"></td></tr></table></body></html>");
                    Functions.show(stringBuilder.toString(), player, null, new Object[0]);
                    return true;
                }
            }
            object = HtmCache.getInstance().getNotNull("scripts/services/clan.htm", player);
            Functions.show((String)object, player, null, new Object[0]);
            return true;
        }
        return false;
    }
}
