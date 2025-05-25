/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.lang.reference.HardReference
 *  l2.commons.lang.reference.HardReferences
 *  l2.gameserver.instancemanager.ServerVariables
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.network.l2.components.ChatType
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.NpcHtmlMessage
 *  l2.gameserver.network.l2.s2c.Say2
 *  l2.gameserver.scripts.Scripts
 */
package handler.admincommands;

import events.TvT2.PvPEvent;
import events.TvT2.PvPEventProperties;
import handler.admincommands.ScriptAdminCommand;
import l2.commons.lang.reference.HardReference;
import l2.commons.lang.reference.HardReferences;
import l2.gameserver.instancemanager.ServerVariables;
import l2.gameserver.model.Player;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.components.ChatType;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.network.l2.s2c.Say2;
import l2.gameserver.scripts.Scripts;

public class AdminPvPEvent
extends ScriptAdminCommand {
    public HardReference<Player> self = HardReferences.emptyRef();
    public HardReference<NpcInstance> npc = HardReferences.emptyRef();

    public Enum[] getAdminCommandEnum() {
        return Commands.values();
    }

    public boolean useAdminCommand(Enum enum_, String[] stringArray, String string, Player player) {
        Commands commands = (Commands)enum_;
        if (!player.getPlayerAccess().IsEventGm) {
            return false;
        }
        switch (commands) {
            case admin_pvpevent: {
                if (stringArray.length > 1) {
                    if (stringArray[1].equalsIgnoreCase("tvt")) {
                        this.a(player, "tvt");
                        break;
                    }
                    if (stringArray[1].equalsIgnoreCase("ctf")) {
                        this.a(player, "ctf");
                        break;
                    }
                    if (!stringArray[1].equalsIgnoreCase("dm")) break;
                    this.a(player, "dm");
                    break;
                }
                this.p(player);
                break;
            }
            case admin_pvpevent_active: {
                if (stringArray.length > 1) {
                    boolean bl = Boolean.parseBoolean(stringArray[1]);
                    boolean bl2 = PvPEventProperties.getBooleanProperty("PvP", "EventEnabled", false);
                    PvPEvent.getInstance().LoadVars();
                    if (bl) {
                        PvPEvent.getInstance().Activate();
                        if (!bl2) {
                            player.sendPacket((IStaticPacket)new Say2(0, ChatType.CRITICAL_ANNOUNCE, "", "[GMInfo] You activate it manually! After a restart, it will reset to Disabled. Activate the event in pvp_events.properties if you need to keep it On."));
                        }
                    } else {
                        PvPEvent.getInstance().Deativate();
                        if (bl2) {
                            player.sendPacket((IStaticPacket)new Say2(0, ChatType.CRITICAL_ANNOUNCE, "", "[GMInfo] You Deactivate it manually! After a restart, it will reset to Activate. Deactivate the event in pvp_events.properties if you need to keep it Off."));
                        }
                    }
                }
                this.p(player);
                break;
            }
            case admin_pvpevent_setanntime: {
                if (stringArray.length > 1) {
                    PvPEvent.getInstance().LoadVars();
                }
                this.p(player);
                break;
            }
            case admin_pvpevent_setannredu: {
                if (stringArray.length > 1) {
                    PvPEvent.getInstance().LoadVars();
                }
                this.p(player);
                break;
            }
            case admin_pvpevent_countdown: {
                if (stringArray.length > 1) {
                    PvPEvent.getInstance().LoadVars();
                }
                this.p(player);
                break;
            }
            case admin_pvpevent_reg_type: {
                if (stringArray.length > 1) {
                    PvPEvent.getInstance().LoadVars();
                }
                this.p(player);
                break;
            }
            case admin_pvpevent_settime: {
                if (stringArray.length > 1 && !stringArray[1].isEmpty()) {
                    PvPEvent.getInstance().LoadVars();
                } else {
                    PvPEvent.getInstance().LoadVars();
                }
                this.p(player);
                break;
            }
            case admin_pvpevent_setinstsid: {
                if (stringArray.length > 1) {
                    PvPEvent.getInstance().LoadVars();
                }
                this.p(player);
                break;
            }
            case admin_pvpevent_enable: {
                if (stringArray.length <= 2) break;
                try {
                    PvPEvent.PvPEventRule pvPEventRule = Enum.valueOf(PvPEvent.PvPEventRule.class, stringArray[1].toUpperCase());
                    ServerVariables.set((String)("PvP_" + pvPEventRule.name() + "_enabled"), (boolean)Boolean.parseBoolean(stringArray[2]));
                    this.a(player, pvPEventRule.name().toLowerCase());
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
                break;
            }
            case admin_pvpevent_capcha: {
                if (stringArray.length <= 2) break;
                try {
                    PvPEvent.PvPEventRule pvPEventRule = Enum.valueOf(PvPEvent.PvPEventRule.class, stringArray[1].toUpperCase());
                    ServerVariables.set((String)("PvP_" + pvPEventRule.name() + "_use_capcha"), (boolean)Boolean.parseBoolean(stringArray[2]));
                    this.a(player, pvPEventRule.name().toLowerCase());
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
                break;
            }
            case admin_pvpevent_hwid_restrict: {
                PvPEvent.PvPEventRule pvPEventRule;
                if (stringArray.length > 2) {
                    try {
                        pvPEventRule = Enum.valueOf(PvPEvent.PvPEventRule.class, stringArray[1].toUpperCase());
                        ServerVariables.set((String)("PvP_" + pvPEventRule.name() + "_use_hwid_restrict"), (boolean)Boolean.parseBoolean(stringArray[2]));
                        this.a(player, pvPEventRule.name().toLowerCase());
                    }
                    catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
            case admin_pvpevent_ip_restrict: {
                PvPEvent.PvPEventRule pvPEventRule;
                if (stringArray.length <= 2) break;
                try {
                    pvPEventRule = Enum.valueOf(PvPEvent.PvPEventRule.class, stringArray[1].toUpperCase());
                    ServerVariables.set((String)("PvP_" + pvPEventRule.name() + "_use_ip_restrict"), (boolean)Boolean.parseBoolean(stringArray[2]));
                    this.a(player, pvPEventRule.name().toLowerCase());
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
                break;
            }
            case admin_pvpevent_hide_identiti: {
                if (stringArray.length <= 2) break;
                try {
                    PvPEvent.PvPEventRule pvPEventRule = Enum.valueOf(PvPEvent.PvPEventRule.class, stringArray[1].toUpperCase());
                    ServerVariables.set((String)("PvP_" + pvPEventRule.name() + "_hide_identiti"), (boolean)Boolean.parseBoolean(stringArray[2]));
                    this.a(player, pvPEventRule.name().toLowerCase());
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
                break;
            }
            case admin_pvpevent_dispell: {
                if (stringArray.length <= 2) break;
                try {
                    PvPEvent.PvPEventRule pvPEventRule = Enum.valueOf(PvPEvent.PvPEventRule.class, stringArray[1].toUpperCase());
                    ServerVariables.set((String)("PvP_" + pvPEventRule.name() + "_dispell"), (boolean)Boolean.parseBoolean(stringArray[2]));
                    this.a(player, pvPEventRule.name().toLowerCase());
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
                break;
            }
            case admin_pvpevent_dispell_after: {
                if (stringArray.length <= 2) break;
                try {
                    PvPEvent.PvPEventRule pvPEventRule = Enum.valueOf(PvPEvent.PvPEventRule.class, stringArray[1].toUpperCase());
                    ServerVariables.set((String)("PvP_" + pvPEventRule.name() + "_dispell_after"), (boolean)Boolean.parseBoolean(stringArray[2]));
                    this.a(player, pvPEventRule.name().toLowerCase());
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
                break;
            }
            case admin_kill_count_protection: {
                if (stringArray.length <= 2) break;
                try {
                    PvPEvent.PvPEventRule pvPEventRule = Enum.valueOf(PvPEvent.PvPEventRule.class, stringArray[1].toUpperCase());
                    ServerVariables.set((String)("PvP_" + pvPEventRule.name() + "_min_kill_reward"), (String)stringArray[2]);
                    this.a(player, pvPEventRule.name().toLowerCase());
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
                break;
            }
            case admin_pvpevent_reqpart: {
                if (stringArray.length <= 2) break;
                try {
                    PvPEvent.PvPEventRule pvPEventRule = Enum.valueOf(PvPEvent.PvPEventRule.class, stringArray[1].toUpperCase());
                    ServerVariables.set((String)("PvP_" + pvPEventRule.name() + "_req_parts"), (String)stringArray[2]);
                    this.a(player, pvPEventRule.name().toLowerCase());
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
                break;
            }
            case admin_pvpevent_maxpart: {
                if (stringArray.length <= 2) break;
                try {
                    PvPEvent.PvPEventRule pvPEventRule = Enum.valueOf(PvPEvent.PvPEventRule.class, stringArray[1].toUpperCase());
                    ServerVariables.set((String)("PvP_" + pvPEventRule.name() + "_max_parts"), (String)stringArray[2]);
                    this.a(player, pvPEventRule.name().toLowerCase());
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
                break;
            }
            case admin_pvpevent_minlvl: {
                if (stringArray.length <= 2) break;
                try {
                    PvPEvent.PvPEventRule pvPEventRule = Enum.valueOf(PvPEvent.PvPEventRule.class, stringArray[1].toUpperCase());
                    ServerVariables.set((String)("PvP_" + pvPEventRule.name() + "_min_lvl"), (String)stringArray[2]);
                    this.a(player, pvPEventRule.name().toLowerCase());
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
                break;
            }
            case admin_pvpevent_maxlvl: {
                if (stringArray.length <= 2) break;
                try {
                    PvPEvent.PvPEventRule pvPEventRule = Enum.valueOf(PvPEvent.PvPEventRule.class, stringArray[1].toUpperCase());
                    ServerVariables.set((String)("PvP_" + pvPEventRule.name() + "_max_lvl"), (String)stringArray[2]);
                    this.a(player, pvPEventRule.name().toLowerCase());
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
                break;
            }
            case admin_pvpevent_evetime: {
                if (stringArray.length <= 2) break;
                try {
                    PvPEvent.PvPEventRule pvPEventRule = Enum.valueOf(PvPEvent.PvPEventRule.class, stringArray[1].toUpperCase());
                    ServerVariables.set((String)("PvP_" + pvPEventRule.name() + "_time"), (String)stringArray[2]);
                    this.a(player, pvPEventRule.name().toLowerCase());
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
                break;
            }
            case admin_pvpevent_prohclid: {
                if (stringArray.length <= 2) break;
                try {
                    PvPEvent.PvPEventRule pvPEventRule = Enum.valueOf(PvPEvent.PvPEventRule.class, stringArray[1].toUpperCase());
                    ServerVariables.set((String)("PvP_" + pvPEventRule.name() + "_prohibited_class_ids"), (String)stringArray[2]);
                    this.a(player, pvPEventRule.name().toLowerCase());
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
                break;
            }
            case admin_pvpevent_revdel: {
                if (stringArray.length <= 2) break;
                try {
                    PvPEvent.PvPEventRule pvPEventRule = Enum.valueOf(PvPEvent.PvPEventRule.class, stringArray[1].toUpperCase());
                    ServerVariables.set((String)("PvP_" + pvPEventRule.name() + "_revive_delay"), (String)stringArray[2]);
                    this.a(player, pvPEventRule.name().toLowerCase());
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
                break;
            }
            case admin_pvpevent_buffprotect: {
                if (stringArray.length <= 2) break;
                try {
                    PvPEvent.PvPEventRule pvPEventRule = Enum.valueOf(PvPEvent.PvPEventRule.class, stringArray[1].toUpperCase());
                    ServerVariables.set((String)("PvP_" + pvPEventRule.name() + "_buff_protection"), (boolean)Boolean.parseBoolean(stringArray[2]));
                    this.a(player, pvPEventRule.name().toLowerCase());
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
                break;
            }
            case admin_pvpevent_afkprotect: {
                if (stringArray.length <= 2) break;
                try {
                    PvPEvent.PvPEventRule pvPEventRule = Enum.valueOf(PvPEvent.PvPEventRule.class, stringArray[1].toUpperCase());
                    ServerVariables.set((String)("PvP_" + pvPEventRule.name() + "_afk_protection"), (boolean)Boolean.parseBoolean(stringArray[2]));
                    this.a(player, pvPEventRule.name().toLowerCase());
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
                break;
            }
            case admin_pvpevent_ipkill: {
                if (stringArray.length <= 2) break;
                try {
                    PvPEvent.PvPEventRule pvPEventRule = Enum.valueOf(PvPEvent.PvPEventRule.class, stringArray[1].toUpperCase());
                    ServerVariables.set((String)("PvP_" + pvPEventRule.name() + "_item_per_kill"), (String)stringArray[2]);
                    this.a(player, pvPEventRule.name().toLowerCase());
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
                break;
            }
            case admin_pvpevent_herorevhours: {
                if (stringArray.length <= 2) break;
                try {
                    PvPEvent.PvPEventRule pvPEventRule = Enum.valueOf(PvPEvent.PvPEventRule.class, stringArray[1].toUpperCase());
                    ServerVariables.set((String)("PvP_" + pvPEventRule.name() + "_herorevhours"), (String)stringArray[2]);
                    this.a(player, pvPEventRule.name().toLowerCase());
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
                break;
            }
            case admin_pvpevent_reward_team: {
                if (stringArray.length <= 2) break;
                try {
                    PvPEvent.PvPEventRule pvPEventRule = Enum.valueOf(PvPEvent.PvPEventRule.class, stringArray[1].toUpperCase());
                    ServerVariables.set((String)("PvP_" + pvPEventRule.name() + "_rev_team"), (String)stringArray[2]);
                    this.a(player, pvPEventRule.name().toLowerCase());
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
                break;
            }
            case admin_pvpevent_reward_lose_team: {
                if (stringArray.length <= 2) break;
                try {
                    PvPEvent.PvPEventRule pvPEventRule = Enum.valueOf(PvPEvent.PvPEventRule.class, stringArray[1].toUpperCase());
                    ServerVariables.set((String)("PvP_" + pvPEventRule.name() + "_rev_lose_team"), (String)stringArray[2]);
                    this.a(player, pvPEventRule.name().toLowerCase());
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
                break;
            }
            case admin_pvpevent_reward_tie: {
                if (stringArray.length <= 2) break;
                try {
                    PvPEvent.PvPEventRule pvPEventRule = Enum.valueOf(PvPEvent.PvPEventRule.class, stringArray[1].toUpperCase());
                    ServerVariables.set((String)("PvP_" + pvPEventRule.name() + "_rev_tie_team"), (String)stringArray[2]);
                    this.a(player, pvPEventRule.name().toLowerCase());
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
                break;
            }
            case admin_pvpevent_reward_top: {
                if (stringArray.length <= 2) break;
                try {
                    PvPEvent.PvPEventRule pvPEventRule = Enum.valueOf(PvPEvent.PvPEventRule.class, stringArray[1].toUpperCase());
                    ServerVariables.set((String)("PvP_" + pvPEventRule.name() + "_rev_top"), (String)stringArray[2]);
                    this.a(player, pvPEventRule.name().toLowerCase());
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
                break;
            }
            case admin_pvpevent_start: {
                if (stringArray.length <= 1) break;
                try {
                    PvPEvent.PvPEventRule pvPEventRule = Enum.valueOf(PvPEvent.PvPEventRule.class, stringArray[1].toUpperCase());
                    if (PvPEvent.getInstance().isActive()) {
                        PvPEvent.getInstance().LoadVars();
                        PvPEvent.getInstance().setRule(pvPEventRule);
                        PvPEvent.getInstance().goRegistration();
                    } else {
                        player.sendMessage("PvP Events disabled. Enable it firstly");
                    }
                    this.a(player, pvPEventRule.name().toLowerCase());
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
                break;
            }
            case admin_capt1: {
                Scripts.getInstance().callScripts(player, "Util", "RequestCapcha", new Object[]{"handler.admincommands.AdminPvPEvent:CapchaAccepted", player.getStoredId(), 60});
                return true;
            }
            case admin_tvt1: {
                PvPEvent.getInstance().LoadVars();
                PvPEvent.getInstance().setRule(PvPEvent.PvPEventRule.TVT);
                PvPEvent.getInstance().goRegistration();
                break;
            }
            case admin_dm1: {
                PvPEvent.getInstance().LoadVars();
                PvPEvent.getInstance().setRule(PvPEvent.PvPEventRule.DM);
                PvPEvent.getInstance().goRegistration();
                break;
            }
            case admin_ctf1: {
                PvPEvent.getInstance().LoadVars();
                PvPEvent.getInstance().setRule(PvPEvent.PvPEventRule.CTF);
                PvPEvent.getInstance().goRegistration();
            }
        }
        return false;
    }

    private void p(Player player) {
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(5);
        StringBuilder stringBuilder = new StringBuilder("<html><title>PvPEvent</title><body>");
        this.a(stringBuilder);
        this.b(stringBuilder);
        this.b(stringBuilder, "Active", PvPEvent.getInstance().isActive(), "admin_pvpevent_active");
        this.b(stringBuilder, "Announce time", PvPEventProperties.getStringProperty("PvP", "EventAnnounceTime", "5"), "anntime", "admin_pvpevent_setanntime");
        this.b(stringBuilder, "Announce reduce", PvPEventProperties.getStringProperty("PvP", "EventAnnounceCountdown", "1"), "annredu", "admin_pvpevent_setannredu");
        this.a(stringBuilder, "Event Countdown", PvPEventProperties.getBooleanProperty("PvP", "EventCountdown", false), "admin_pvpevent_countdown");
        this.a(stringBuilder, "Reg Window", PvPEventProperties.getBooleanProperty("PvP", "EventRegistrationWindow", false), "admin_pvpevent_reg_type");
        this.a(stringBuilder, "Start time", PvPEventProperties.getStringProperty("PvP", "EventStartTime", ""), "sttime", "admin_pvpevent_settime");
        this.a(stringBuilder, "TvT Instances", PvPEventProperties.getStringProperty("TVT", "EventInstancesIds", ""), "insid", "admin_pvpevent_setinstsid");
        this.a(stringBuilder, "CTF Instances", PvPEventProperties.getStringProperty("CTF", "EventInstancesIds", ""), "insid", "admin_pvpevent_setinstsid");
        this.a(stringBuilder, "DM Instances", PvPEventProperties.getStringProperty("DM", "EventInstancesIds", ""), "insid", "admin_pvpevent_setinstsid");
        this.b(stringBuilder);
        PvPEvent.PvPEventRule pvPEventRule = PvPEvent.getInstance().getNextRule(PvPEvent.getInstance().getRule());
        stringBuilder.append("<center>Next event: " + (pvPEventRule != null ? pvPEventRule.name() : "none") + "</center><br>");
        stringBuilder.append("</body></html>");
        npcHtmlMessage.setHtml(stringBuilder.toString());
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    private void a(Player player, String string) {
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(5);
        StringBuilder stringBuilder = new StringBuilder("<html><title>PvPEvent - " + string + "</title><body>");
        this.a(stringBuilder);
        this.b(stringBuilder);
        this.a(stringBuilder, string);
        this.b(stringBuilder);
        if (string.equalsIgnoreCase("tvt")) {
            this.a(stringBuilder, "tvt", true, true);
        } else if (string.equalsIgnoreCase("ctf")) {
            this.a(stringBuilder, "ctf", true, false);
        } else if (string.equalsIgnoreCase("dm")) {
            this.a(stringBuilder, "dm", false, true);
        }
        this.b(stringBuilder);
        stringBuilder.append("<center><button value=\"Start " + string + "\" action=\"bypass -h admin_pvpevent_start " + string + "\" width=100 height=20 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></center>");
        stringBuilder.append("</body></html>");
        npcHtmlMessage.setHtml(stringBuilder.toString());
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    private void a(StringBuilder stringBuilder) {
        stringBuilder.append("<center><table width=265><tr>");
        stringBuilder.append("<td><button value=\"Main\" action=\"bypass -h admin_pvpevent\" width=60 height=20 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>");
        stringBuilder.append("<td><button value=\"TVT\" action=\"bypass -h admin_pvpevent tvt\" width=60 height=20 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>");
        stringBuilder.append("<td><button value=\"CTF\" action=\"bypass -h admin_pvpevent ctf\" width=60 height=20 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>");
        stringBuilder.append("<td><button value=\"DM\" action=\"bypass -h admin_pvpevent dm\" width=60 height=20 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>");
        stringBuilder.append("</tr></table></center>");
    }

    private void a(StringBuilder stringBuilder, String string, String string2, String string3, String string4) {
        stringBuilder.append("<center>" + string + ":</center><br1>");
        stringBuilder.append("<center><table width=265><tr>");
        stringBuilder.append("<td width=40>Now: </td><td width=210>").append(string2).append("</td>");
        stringBuilder.append("</tr></table></center><br1><center><tr>");
        stringBuilder.append("</tr></table></center><br>");
    }

    private void b(StringBuilder stringBuilder) {
        stringBuilder.append("<br><center><img src=\"L2UI.SquareWhite\" width=260 height=1></center><br>");
    }

    private void b(StringBuilder stringBuilder, String string, String string2, String string3, String string4) {
        stringBuilder.append("<center><table width=260><tr><td width=120><font color=\"LEVEL\">");
        stringBuilder.append(string);
        stringBuilder.append(":</font></td><td width=60 align=left>");
        stringBuilder.append(string2);
        stringBuilder.append("</td></tr></table></center><br>");
    }

    private void a(StringBuilder stringBuilder, String string, boolean bl, String string2) {
        stringBuilder.append("<center><table width=260><tr><td width=140 align=left><font color=\"LEVEL\">");
        stringBuilder.append(string);
        stringBuilder.append(":</font></td><td width=80 align=left>");
        stringBuilder.append(bl ? "on" : "off");
        stringBuilder.append("</td></tr></table></center><br>");
    }

    private void b(StringBuilder stringBuilder, String string, boolean bl, String string2) {
        stringBuilder.append("<center><table width=260><tr><td width=140 align=left><font color=\"LEVEL\">");
        stringBuilder.append(string);
        stringBuilder.append(":</font></td><td width=80 align=left>");
        stringBuilder.append(bl ? "on" : "off");
        stringBuilder.append(":</td><td width=30 align=right>");
        stringBuilder.append("<button value=\"on\" action=\"bypass -h ");
        stringBuilder.append(string2);
        stringBuilder.append(" true\" width=30 height=20 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td><td width=30 align=left>");
        stringBuilder.append("<button value=\"off\" action=\"bypass -h ");
        stringBuilder.append(string2);
        stringBuilder.append(" false\" width=30 height=20 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td></tr></table></center><br>");
    }

    private void a(StringBuilder stringBuilder, String string) {
        this.a(stringBuilder, "Enabled", PvPEventProperties.getBooleanProperty(string.toUpperCase(), "Enabled", false), "admin_pvpevent_enable " + string.toLowerCase());
        this.a(stringBuilder, "Captcha", PvPEventProperties.getBooleanProperty(string.toUpperCase(), "UseCaptcha", false), "admin_pvpevent_capcha " + string.toLowerCase());
        this.a(stringBuilder, "Hide identity", PvPEventProperties.getBooleanProperty(string.toUpperCase(), "HideIdentity", false), "admin_pvpevent_hide_identiti " + string.toLowerCase());
        this.a(stringBuilder, "Remove Buff To", PvPEventProperties.getBooleanProperty(string.toUpperCase(), "DispelBuffs", false), "admin_pvpevent_dispell " + string.toLowerCase());
        this.a(stringBuilder, "Remove Buff From", PvPEventProperties.getBooleanProperty(string.toUpperCase(), "DispelBuffsAfter", false), "admin_pvpevent_dispell_after " + string.toLowerCase());
        if (string.equalsIgnoreCase("ctf")) {
            this.a(stringBuilder, "Afk Protect", PvPEventProperties.getBooleanProperty(string.toUpperCase(), "AfkProtection", false), "admin_pvpevent_afkprotect " + string.toLowerCase());
        }
        this.a(stringBuilder, "Buff Protect", PvPEventProperties.getBooleanProperty(string.toUpperCase(), "BuffProtection", false), "admin_pvpevent_buffprotect " + string.toLowerCase());
        this.b(stringBuilder, "Min. Participants", PvPEventProperties.getStringProperty(string.toUpperCase(), "MinParticipants", "10"), "reqpart", "admin_pvpevent_reqpart " + string.toLowerCase());
        this.b(stringBuilder, "Max. Participants", PvPEventProperties.getStringProperty(string.toUpperCase(), "MaxParticipants", "100"), "maxpart", "admin_pvpevent_maxpart " + string.toLowerCase());
        this.b(stringBuilder, "Min level", PvPEventProperties.getStringProperty(string.toUpperCase(), "MinLevel", "10"), "minlvl", "admin_pvpevent_minlvl " + string.toLowerCase());
        this.b(stringBuilder, "Max level", PvPEventProperties.getStringProperty(string.toUpperCase(), "MaxLevel", "80"), "maxlvl", "admin_pvpevent_maxlvl " + string.toLowerCase());
        this.b(stringBuilder, "Time at the Event", PvPEventProperties.getStringProperty(string.toUpperCase(), "EventTime", "10"), "evetime", "admin_pvpevent_evetime " + string.toLowerCase());
        if (string.equalsIgnoreCase("tvt") || string.equalsIgnoreCase("dm")) {
            this.b(stringBuilder, "Per Kill ItemId", PvPEventProperties.getStringProperty(string.toUpperCase(), "RewardForEveryKill", "0"), "iperkill", "admin_pvpevent_ipkill " + string.toLowerCase());
        }
        this.b(stringBuilder, "Revive delay", PvPEventProperties.getStringProperty(string.toUpperCase(), "ReviveDelay", "1"), "revdel", "admin_pvpevent_revdel " + string.toLowerCase());
    }

    private void a(StringBuilder stringBuilder, String string, boolean bl, boolean bl2) {
        if (bl) {
            this.a(stringBuilder, "Start time", PvPEventProperties.getStringProperty(string.toUpperCase(), "EventStartTime", ""), "sttime", "admin_pvpevent_settime");
            this.a(stringBuilder, "Team reward list: ", PvPEventProperties.getStringProperty(string.toUpperCase(), "TeamReward", ""), "trev", "admin_pvpevent_reward_team " + string.toLowerCase());
            this.a(stringBuilder, "Loose team reward: ", PvPEventProperties.getStringProperty(string.toUpperCase(), "LoseTeamReward", ""), "lsr", "admin_pvpevent_reward_lose_team " + string.toLowerCase());
            this.a(stringBuilder, "Tie rewards: ", PvPEventProperties.getStringProperty(string.toUpperCase(), "TieTeamReward", ""), "rti", "admin_pvpevent_reward_tie " + string.toLowerCase());
        }
        if (bl2) {
            this.a(stringBuilder, "Start time", PvPEventProperties.getStringProperty(string.toUpperCase(), "EventStartTime", ""), "sttime", "admin_pvpevent_settime");
            this.a(stringBuilder, "Top Player Reward: ", PvPEventProperties.getStringProperty(string.toUpperCase(), "TopKillerReward", ""), "srev", "admin_pvpevent_reward_top " + string.toLowerCase());
            this.b(stringBuilder, "Hero Status Time Reward", PvPEventProperties.getStringProperty(string.toUpperCase(), "HeroRewardTime", "0"), "hhrev", "admin_pvpevent_herorevhours " + string.toLowerCase());
        }
    }

    public void CapchaAccepted() {
        Player player = (Player)this.self.get();
        if (player == null) {
            return;
        }
        player.sendMessage("Captcha confirmed.");
    }

    private static final class Commands
    extends Enum<Commands> {
        public static final /* enum */ Commands admin_pvpevent = new Commands();
        public static final /* enum */ Commands admin_pvpevent_active = new Commands();
        public static final /* enum */ Commands admin_pvpevent_setanntime = new Commands();
        public static final /* enum */ Commands admin_pvpevent_setannredu = new Commands();
        public static final /* enum */ Commands admin_pvpevent_countdown = new Commands();
        public static final /* enum */ Commands admin_pvpevent_settime = new Commands();
        public static final /* enum */ Commands admin_pvpevent_setinstsid = new Commands();
        public static final /* enum */ Commands admin_pvpevent_enable = new Commands();
        public static final /* enum */ Commands admin_pvpevent_capcha = new Commands();
        public static final /* enum */ Commands admin_pvpevent_reg_type = new Commands();
        public static final /* enum */ Commands admin_pvpevent_hwid_restrict = new Commands();
        public static final /* enum */ Commands admin_pvpevent_ip_restrict = new Commands();
        public static final /* enum */ Commands admin_pvpevent_hide_identiti = new Commands();
        public static final /* enum */ Commands admin_pvpevent_dispell = new Commands();
        public static final /* enum */ Commands admin_pvpevent_dispell_after = new Commands();
        public static final /* enum */ Commands admin_kill_count_protection = new Commands();
        public static final /* enum */ Commands admin_pvpevent_reqpart = new Commands();
        public static final /* enum */ Commands admin_pvpevent_maxpart = new Commands();
        public static final /* enum */ Commands admin_pvpevent_minlvl = new Commands();
        public static final /* enum */ Commands admin_pvpevent_maxlvl = new Commands();
        public static final /* enum */ Commands admin_pvpevent_evetime = new Commands();
        public static final /* enum */ Commands admin_pvpevent_ipkill = new Commands();
        public static final /* enum */ Commands admin_pvpevent_herorevhours = new Commands();
        public static final /* enum */ Commands admin_pvpevent_prohclid = new Commands();
        public static final /* enum */ Commands admin_pvpevent_revdel = new Commands();
        public static final /* enum */ Commands admin_pvpevent_buffprotect = new Commands();
        public static final /* enum */ Commands admin_pvpevent_afkprotect = new Commands();
        public static final /* enum */ Commands admin_pvpevent_reward_team = new Commands();
        public static final /* enum */ Commands admin_pvpevent_reward_lose_team = new Commands();
        public static final /* enum */ Commands admin_pvpevent_reward_tie = new Commands();
        public static final /* enum */ Commands admin_pvpevent_reward_top = new Commands();
        public static final /* enum */ Commands admin_pvpevent_start = new Commands();
        public static final /* enum */ Commands admin_capt1 = new Commands();
        public static final /* enum */ Commands admin_tvt1 = new Commands();
        public static final /* enum */ Commands admin_dm1 = new Commands();
        public static final /* enum */ Commands admin_ctf1 = new Commands();
        private static final /* synthetic */ Commands[] a;

        public static Commands[] values() {
            return (Commands[])a.clone();
        }

        public static Commands valueOf(String string) {
            return Enum.valueOf(Commands.class, string);
        }

        private static /* synthetic */ Commands[] a() {
            return new Commands[]{admin_pvpevent, admin_pvpevent_active, admin_pvpevent_setanntime, admin_pvpevent_setannredu, admin_pvpevent_countdown, admin_pvpevent_settime, admin_pvpevent_setinstsid, admin_pvpevent_enable, admin_pvpevent_capcha, admin_pvpevent_reg_type, admin_pvpevent_hwid_restrict, admin_pvpevent_ip_restrict, admin_pvpevent_hide_identiti, admin_pvpevent_dispell, admin_pvpevent_dispell_after, admin_kill_count_protection, admin_pvpevent_reqpart, admin_pvpevent_maxpart, admin_pvpevent_minlvl, admin_pvpevent_maxlvl, admin_pvpevent_evetime, admin_pvpevent_ipkill, admin_pvpevent_herorevhours, admin_pvpevent_prohclid, admin_pvpevent_revdel, admin_pvpevent_buffprotect, admin_pvpevent_afkprotect, admin_pvpevent_reward_team, admin_pvpevent_reward_lose_team, admin_pvpevent_reward_tie, admin_pvpevent_reward_top, admin_pvpevent_start, admin_capt1, admin_tvt1, admin_dm1, admin_ctf1};
        }

        static {
            a = Commands.a();
        }
    }
}
