/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.tuple.Pair
 */
package l2.gameserver.handler.admincommands.impl;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import l2.commons.collections.MultiValueSet;
import l2.gameserver.handler.admincommands.IAdminCommandHandler;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExServerPrimitive;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.utils.Location;
import org.apache.commons.lang3.tuple.Pair;

public class AdminZoneBuilder
implements IAdminCommandHandler {
    private static final int fC = 12;
    private static final String bQ = "zonepoint_";

    @Override
    public boolean useAdminCommand(Enum enum_, String[] stringArray, String string, Player player) {
        Commands commands = (Commands)enum_;
        if (!player.getPlayerAccess().CanUseGMCommand) {
            return false;
        }
        switch (commands) {
            case admin_zone_panel: {
                int n = stringArray.length > 1 ? Integer.parseInt(stringArray[1]) : 1;
                this.i(player, n);
                return true;
            }
            case admin_zone_add: {
                this.N(player);
                this.i(player, 1);
                return true;
            }
            case admin_zone_del: {
                if (stringArray.length > 1) {
                    String string2 = stringArray[1].replace("<", "&lt;").replace(">", "&gt;");
                    player.unsetVar(bQ + string2);
                }
                this.i(player, 1);
                return true;
            }
            case admin_zone_visualize: {
                this.O(player);
                return true;
            }
            case admin_zone_dump: {
                this.P(player);
                return true;
            }
        }
        return false;
    }

    private void N(Player player) {
        List<Pair<String, Location>> list = this.b(player);
        String string = "Point" + (list.size() + 1);
        player.setVar(bQ + string, player.getLoc().toXYZString(), -1L);
        player.sendMessage("Added point: " + string);
    }

    private List<Pair<String, Location>> b(Player player) {
        ArrayList<Pair<String, Location>> arrayList = new ArrayList<Pair<String, Location>>();
        MultiValueSet<String> multiValueSet = player.getVars();
        for (Map.Entry entry : multiValueSet.entrySet()) {
            if (entry == null || entry.getKey() == null || !((String)entry.getKey()).startsWith(bQ)) continue;
            arrayList.add((Pair<String, Location>)Pair.of((Object)((String)entry.getKey()).substring(bQ.length()), (Object)Location.parseLoc(entry.getValue().toString())));
        }
        arrayList.sort(Comparator.comparingInt(pair -> {
            String string = ((String)pair.getLeft()).replaceAll("\\D", "");
            return string.isEmpty() ? 0 : Integer.parseInt(string);
        }));
        return arrayList;
    }

    private void O(Player player) {
        List<Pair<String, Location>> list = this.b(player);
        if (list.size() < 3) {
            player.sendMessage("You need at least 3 points to visualize a zone.");
            return;
        }
        ExServerPrimitive exServerPrimitive = new ExServerPrimitive("ZoneVisualization", player.getX(), player.getY(), player.getZ());
        int n = player.getZ();
        int n2 = n - 100;
        int n3 = n + 100;
        for (int i = n2; i <= n3; i += 10) {
            for (int j = 0; j < list.size(); ++j) {
                Location location = (Location)list.get(j).getRight();
                Location location2 = (Location)list.get((j + 1) % list.size()).getRight();
                exServerPrimitive.addLine(Color.GREEN, location.getX(), location.getY(), i, location2.getX(), location2.getY(), i);
            }
        }
        player.sendPacket((IStaticPacket)exServerPrimitive);
        player.sendMessage("Zone visualized with " + list.size() + " points.");
    }

    private void P(Player player) {
        List<Pair<String, Location>> list = this.b(player);
        if (list.size() < 3) {
            player.sendMessage("You need at least 3 points to dump a zone.");
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Pair<String, Location> object2 : list) {
            Location location = (Location)object2.getRight();
            stringBuilder.append(location.toXYZString());
        }
        String string = "[zone_" + Integer.toHexString(stringBuilder.toString().hashCode()) + "]";
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("<zone name=\"").append(string).append("\" type=\"peace_zone\">\n");
        stringBuilder2.append("    <polygon>\n");
        for (Pair pair : list) {
            Location location = (Location)pair.getRight();
            int n = location.getZ() - 100;
            int n2 = location.getZ() + 100;
            stringBuilder2.append("        <coords loc=\"").append(location.getX()).append(" ").append(location.getY()).append(" ").append(n).append(" ").append(n2).append("\" />\n");
        }
        stringBuilder2.append("    </polygon>\n");
        stringBuilder2.append("</zone>");
        player.sendMessage("Zone successfully dumped. Suggested name: " + (String)string + ". Copy the XML from the console.");
        System.out.println(stringBuilder2);
    }

    private void i(Player player, int n) {
        List<Pair<String, Location>> list = this.b(player);
        int n2 = (int)Math.ceil((double)list.size() / 12.0);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<html><body>");
        stringBuilder.append("<center><br>Zone Builder<br>");
        stringBuilder.append("<table width=270 border=0>");
        int n3 = Math.max(0, (n - 1) * 12);
        int n4 = Math.min(list.size(), n * 12);
        for (int i = n3; i < n4; ++i) {
            Pair<String, Location> pair = list.get(i);
            stringBuilder.append("<tr>");
            stringBuilder.append("<td>");
            stringBuilder.append("<a action=\"bypass admin_zone_del ").append((String)pair.getLeft()).append("\">[Del]</a> ");
            stringBuilder.append((String)pair.getLeft()).append(" ").append(((Location)pair.getRight()).toXYZString());
            stringBuilder.append("</td>");
            stringBuilder.append("</tr>");
        }
        stringBuilder.append("</table>");
        stringBuilder.append("<table width=120 border=0 cellspacing=2 cellpadding=2><tr>");
        if (n > 1) {
            stringBuilder.append("<td align=right>").append("<button value=\"Prev\" action=\"bypass admin_zone_panel ").append(n - 1).append("\" width=50 height=20 back=\"L2UI_ct1.Button_DF_Down\" fore=\"L2UI_ct1.button_df\">").append("</td>");
        }
        stringBuilder.append("<td align=center>").append("Page ").append(n).append(" of ").append(n2).append("</td>");
        if (n < n2) {
            stringBuilder.append("<td align=left>").append("<button value=\"Next\" action=\"bypass admin_zone_panel ").append(n + 1).append("\" width=50 height=20 back=\"L2UI_ct1.Button_DF_Down\" fore=\"L2UI_ct1.button_df\">").append("</td>");
        }
        stringBuilder.append("</tr></table>");
        stringBuilder.append("<button value=\"Add Point\" action=\"bypass admin_zone_add\" width=100 height=20 back=\"L2UI_ct1.Button_DF_Down\" fore=\"L2UI_ct1.button_df\">");
        stringBuilder.append("<button value=\"Visualize Zone\" action=\"bypass admin_zone_visualize\" width=100 height=20 back=\"L2UI_ct1.Button_DF_Down\" fore=\"L2UI_ct1.button_df\">");
        stringBuilder.append("<button value=\"Dump Zone\" action=\"bypass admin_zone_dump\" width=100 height=20 back=\"L2UI_ct1.Button_DF_Down\" fore=\"L2UI_ct1.button_df\">");
        stringBuilder.append("<button value=\"Back\" action=\"bypass admin_zones\" width=100 height=20 back=\"L2UI_ct1.Button_DF_Down\" fore=\"L2UI_ct1.button_df\">");
        stringBuilder.append("</center></body></html>");
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(5);
        npcHtmlMessage.setHtml(stringBuilder.toString());
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    @Override
    public Enum[] getAdminCommandEnum() {
        return Commands.values();
    }

    static final class Commands
    extends Enum<Commands> {
        public static final /* enum */ Commands admin_zone_panel = new Commands();
        public static final /* enum */ Commands admin_zone_add = new Commands();
        public static final /* enum */ Commands admin_zone_del = new Commands();
        public static final /* enum */ Commands admin_zone_visualize = new Commands();
        public static final /* enum */ Commands admin_zone_dump = new Commands();
        private static final /* synthetic */ Commands[] a;

        public static Commands[] values() {
            return (Commands[])a.clone();
        }

        public static Commands valueOf(String string) {
            return Enum.valueOf(Commands.class, string);
        }

        private static /* synthetic */ Commands[] a() {
            return new Commands[]{admin_zone_panel, admin_zone_add, admin_zone_del, admin_zone_visualize, admin_zone_dump};
        }

        static {
            a = Commands.a();
        }
    }
}
