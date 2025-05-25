/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.collections.MultiValueSet
 *  l2.gameserver.model.Player
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.NpcHtmlMessage
 *  l2.gameserver.utils.Location
 *  org.apache.commons.lang3.tuple.Pair
 */
package handler.admincommands;

import handler.admincommands.ScriptAdminCommand;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import l2.commons.collections.MultiValueSet;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.utils.Location;
import org.apache.commons.lang3.tuple.Pair;

public class AdminTeleportBookmark
extends ScriptAdminCommand {
    private static int cD = 12;
    private static String aq = "gmbk_";
    private static Comparator<Pair<String, Location>> a = new Comparator<Pair<String, Location>>(){

        @Override
        public int compare(Pair<String, Location> pair, Pair<String, Location> pair2) {
            return ((String)pair.getLeft()).compareTo((String)pair2.getLeft());
        }
    };

    public boolean useAdminCommand(Enum enum_, String[] stringArray, String string, Player player) {
        Commands commands = (Commands)enum_;
        if (!player.getPlayerAccess().CanUseGMCommand) {
            return false;
        }
        switch (commands) {
            case admin_bkpage: {
                int n = stringArray.length > 1 ? Integer.parseInt(stringArray[1]) : 1;
                NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(5);
                npcHtmlMessage.setHtml(this.a(this.a(player), n));
                player.sendPacket((IStaticPacket)npcHtmlMessage);
                return true;
            }
            case admin_bkgo: {
                String string2;
                String string3;
                if (stringArray.length > 1 && (string3 = player.getVar(aq + (string2 = stringArray[1].replace("<", "&lt;").replace(">", "&gt;")))) != null) {
                    player.teleToLocation(Location.parseLoc((String)string3));
                }
                return true;
            }
            case admin_bk: {
                Object object;
                if (stringArray.length > 1) {
                    object = stringArray[1].replace("<", "&lt;").replace(">", "&gt;");
                    player.setVar(aq + (String)object, player.getLoc().toXYZString(), -1L);
                }
                object = new NpcHtmlMessage(5);
                object.setHtml(this.a(this.a(player), 1));
                player.sendPacket((IStaticPacket)object);
                return true;
            }
            case admin_bkdel: {
                Object object;
                if (stringArray.length > 1) {
                    object = stringArray[1].replace("<", "&lt;").replace(">", "&gt;");
                    player.unsetVar(aq + (String)object);
                }
                object = new NpcHtmlMessage(5);
                object.setHtml(this.a(this.a(player), 1));
                player.sendPacket((IStaticPacket)object);
                return true;
            }
        }
        return false;
    }

    private List<Pair<String, Location>> a(Player player) {
        ArrayList<Pair<String, Location>> arrayList = new ArrayList<Pair<String, Location>>();
        MultiValueSet multiValueSet = player.getVars();
        for (Map.Entry entry : multiValueSet.entrySet()) {
            if (entry == null || entry.getKey() == null || !((String)entry.getKey()).startsWith(aq)) continue;
            arrayList.add((Pair<String, Location>)Pair.of((Object)((String)entry.getKey()).substring(aq.length()), (Object)Location.parseLoc((String)entry.getValue().toString())));
        }
        Collections.sort(arrayList, a);
        return arrayList;
    }

    private String a(List<Pair<String, Location>> list, int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<html><body>");
        stringBuilder.append("<center><br>Bookmark list<br>");
        stringBuilder.append("<table width=270 border=0>");
        int n2 = Math.max(0, (n - 1) * cD);
        int n3 = Math.min(list.size(), n * cD);
        for (int i = n2; i < n3; ++i) {
            Pair<String, Location> pair = list.get(i);
            stringBuilder.append("<tr>");
            stringBuilder.append("<td> ");
            stringBuilder.append("<a action=\"bypass admin_bkgo ").append((String)pair.getKey()).append("\"> [");
            stringBuilder.append((String)pair.getKey()).append(", ").append(((Location)pair.getValue()).toXYZString());
            stringBuilder.append("] </a> </td><td> ");
            stringBuilder.append("<a action=\"bypass admin_bkdel ").append((String)pair.getKey()).append("\">del");
            stringBuilder.append(" </td>");
            stringBuilder.append(" </tr>");
        }
        stringBuilder.append("<tr><td align=center>");
        stringBuilder.append("<table border=0 cellspacing=2 cellpadding=2><tr>");
        n = Math.max(1, n);
        if (n > 1) {
            stringBuilder.append("<td align=right> ").append("<button value=\"&$1037;\" action=\"bypass %prev_bypass%\" width=60 height=25 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\">".replace("%prev_bypass%", "admin_bkpage " + (n - 1))).append(" </td>");
        }
        stringBuilder.append("<td> ").append(n).append(" </td>");
        if (n <= list.size() / cD) {
            stringBuilder.append("<td> ").append("<button value=\"&$1038;\" action=\"bypass %next_bypass%\" width=60 height=25 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\">".replace("%next_bypass%", "admin_bkpage " + (n + 1))).append(" </td>");
        }
        stringBuilder.append("</tr></table>");
        stringBuilder.append("</td><td></td></tr>");
        stringBuilder.append("</table>");
        stringBuilder.append("</center>");
        stringBuilder.append("</body></html>");
        return stringBuilder.toString();
    }

    public Enum[] getAdminCommandEnum() {
        return Commands.values();
    }

    static final class Commands
    extends Enum<Commands> {
        public static final /* enum */ Commands admin_bk = new Commands();
        public static final /* enum */ Commands admin_bkgo = new Commands();
        public static final /* enum */ Commands admin_bkdel = new Commands();
        public static final /* enum */ Commands admin_bkpage = new Commands();
        private static final /* synthetic */ Commands[] a;

        public static Commands[] values() {
            return (Commands[])a.clone();
        }

        public static Commands valueOf(String string) {
            return Enum.valueOf(Commands.class, string);
        }

        private static /* synthetic */ Commands[] a() {
            return new Commands[]{admin_bk, admin_bkgo, admin_bkdel, admin_bkpage};
        }

        static {
            a = Commands.a();
        }
    }
}
