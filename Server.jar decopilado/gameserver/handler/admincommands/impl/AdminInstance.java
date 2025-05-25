/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

import l2.gameserver.handler.admincommands.IAdminCommandHandler;
import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.Reflection;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.scripts.Functions;

public class AdminInstance
implements IAdminCommandHandler {
    @Override
    public boolean useAdminCommand(Enum enum_, String[] stringArray, String string, Player player) {
        Commands commands = (Commands)enum_;
        if (!player.getPlayerAccess().CanTeleport) {
            return false;
        }
        switch (commands) {
            case admin_instance: {
                this.x(player);
                break;
            }
            case admin_instance_id: {
                if (stringArray.length <= 1) break;
                this.g(player, stringArray[1]);
                break;
            }
            case admin_collapse: {
                if (!player.getReflection().isDefault()) {
                    player.getReflection().collapse();
                    break;
                }
                player.sendMessage("Cannot collapse default reflection!");
                break;
            }
            case admin_reset_reuse: {
                if (stringArray.length <= 1 || player.getTarget() == null || !player.getTarget().isPlayer()) break;
                Player player2 = player.getTarget().getPlayer();
                player2.removeInstanceReuse(Integer.parseInt(stringArray[1]));
                Functions.sendDebugMessage(player, "Instance reuse has been removed");
                break;
            }
            case admin_reset_reuse_all: {
                if (player.getTarget() == null || !player.getTarget().isPlayer()) break;
                Player player3 = player.getTarget().getPlayer();
                player3.removeAllInstanceReuses();
                Functions.sendDebugMessage(player, "All instance reuses has been removed");
                break;
            }
            case admin_set_reuse: {
                if (player.getReflection() == null) break;
                player.getReflection().setReenterTime(System.currentTimeMillis());
            }
        }
        return true;
    }

    @Override
    public Enum[] getAdminCommandEnum() {
        return Commands.values();
    }

    private void x(Player player) {
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(5);
        StringBuffer stringBuffer = new StringBuffer("<html><title>Instance Menu</title><body>");
        stringBuffer.append("<table width=260><tr>");
        stringBuffer.append("<td width=40><button value=\"Main\" action=\"bypass -h admin_admin\" width=40 height=20 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>");
        stringBuffer.append("<td width=180><center>List of Instances</center></td>");
        stringBuffer.append("<td width=40><button value=\"Back\" action=\"bypass -h admin_admin\" width=40 height=20 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>");
        stringBuffer.append("</tr></table><br><br>");
        for (Reflection reflection : ReflectionManager.getInstance().getAll()) {
            if (reflection == null || reflection.isDefault() || reflection.isCollapseStarted()) continue;
            int n = 0;
            if (reflection.getPlayers() != null) {
                n = reflection.getPlayers().size();
            }
            stringBuffer.append("<a action=\"bypass -h admin_instance_id ").append(reflection.getId()).append(" \">").append(reflection.getName()).append("(").append(n).append(" players). Id: ").append(reflection.getId()).append("</a><br>");
        }
        stringBuffer.append("<button value=\"Refresh\" action=\"bypass -h admin_instance\" width=50 height=20 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\">");
        stringBuffer.append("</body></html>");
        npcHtmlMessage.setHtml(stringBuffer.toString());
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    private void g(Player player, String string) {
        Reflection reflection = ReflectionManager.getInstance().get(Integer.parseInt(string));
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(5);
        StringBuffer stringBuffer = new StringBuffer("<html><title>Instance Menu</title><body><br>");
        if (reflection != null) {
            stringBuffer.append("<table width=260><tr>");
            stringBuffer.append("<td width=40><button value=\"Main\" action=\"bypass -h admin_admin\" width=40 height=20 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>");
            stringBuffer.append("<td width=180><center>List of players in ").append(reflection.getName()).append("</center></td>");
            stringBuffer.append("<td width=40><button value=\"Back\" action=\"bypass -h admin_instance\" width=40 height=20 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>");
            stringBuffer.append("</tr></table><br><br>");
            for (Player player2 : reflection.getPlayers()) {
                stringBuffer.append("<a action=\"bypass -h admin_teleportto ").append(player2.getName()).append(" \">").append(player2.getName()).append("</a><br>");
            }
        } else {
            stringBuffer.append("Instance not active.<br>");
            stringBuffer.append("<a action=\"bypass -h admin_instance\">Back to list.</a><br>");
        }
        stringBuffer.append("</body></html>");
        npcHtmlMessage.setHtml(stringBuffer.toString());
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    private static final class Commands
    extends Enum<Commands> {
        public static final /* enum */ Commands admin_instance = new Commands();
        public static final /* enum */ Commands admin_instance_id = new Commands();
        public static final /* enum */ Commands admin_collapse = new Commands();
        public static final /* enum */ Commands admin_reset_reuse = new Commands();
        public static final /* enum */ Commands admin_reset_reuse_all = new Commands();
        public static final /* enum */ Commands admin_set_reuse = new Commands();
        public static final /* enum */ Commands admin_addtiatkill = new Commands();
        private static final /* synthetic */ Commands[] a;

        public static Commands[] values() {
            return (Commands[])a.clone();
        }

        public static Commands valueOf(String string) {
            return Enum.valueOf(Commands.class, string);
        }

        private static /* synthetic */ Commands[] a() {
            return new Commands[]{admin_instance, admin_instance_id, admin_collapse, admin_reset_reuse, admin_reset_reuse_all, admin_set_reuse, admin_addtiatkill};
        }

        static {
            a = Commands.a();
        }
    }
}
