/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.network.l2.c2s;

import java.util.HashMap;
import java.util.StringTokenizer;
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.MultiSellHolder;
import l2.gameserver.handler.admincommands.AdminCommandHandler;
import l2.gameserver.handler.bbs.CommunityBoardManager;
import l2.gameserver.handler.bbs.ICommunityBoardHandler;
import l2.gameserver.handler.voicecommands.IVoicedCommandHandler;
import l2.gameserver.handler.voicecommands.VoicedCommandHandler;
import l2.gameserver.instancemanager.BypassManager;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.oly.CompetitionController;
import l2.gameserver.model.entity.oly.HeroController;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExEnchantSkill;
import l2.gameserver.network.l2.s2c.ExEnchantSkillInfo;
import l2.gameserver.network.l2.s2c.ExEnchantSkillList;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.Scripts;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestBypassToServer
extends L2GameClientPacket {
    private static final Logger cP = LoggerFactory.getLogger(RequestBypassToServer.class);
    private BypassManager.DecodedBypass a = null;
    private String _bypass;

    @Override
    protected void readImpl() {
        this._bypass = this.readS();
    }

    @Override
    protected void runImpl() {
        GameClient gameClient = (GameClient)this.getClient();
        Player player = gameClient.getActiveChar();
        if (player == null) {
            return;
        }
        try {
            if (this._bypass == null || this._bypass.isEmpty() || (this.a = gameClient.decodeBypass(this._bypass)) == null) {
                return;
            }
            NpcInstance npcInstance = player.getLastNpc();
            GameObject gameObject = player.getTarget();
            if (npcInstance == null && gameObject != null && gameObject.isNpc()) {
                npcInstance = (NpcInstance)gameObject;
            }
            if (this.a.bypass.startsWith("admin_")) {
                AdminCommandHandler.getInstance().useAdminCommandHandler(player, this.a.bypass);
            } else if (this.a.bypass.equals("come_here") && player.isGM()) {
                RequestBypassToServer.a((GameClient)this.getClient());
            } else if (this.a.bypass.startsWith("player_help ")) {
                RequestBypassToServer.q(player, this.a.bypass.substring(12));
            } else if (this.a.bypass.startsWith("scripts_")) {
                String string = this.a.bypass.substring(8).trim();
                String[] stringArray = string.split("\\s+");
                String[] stringArray2 = string.substring(stringArray[0].length()).trim().split("\\s+");
                String[] stringArray3 = stringArray[0].split(":");
                if (stringArray3.length != 2) {
                    cP.warn("Bad Script bypass!");
                    return;
                }
                HashMap<String, Object> hashMap = null;
                if (npcInstance != null) {
                    hashMap = new HashMap<String, Object>(1);
                    hashMap.put("npc", npcInstance.getRef());
                }
                if (stringArray.length == 1) {
                    Scripts.getInstance().callScripts(player, stringArray3[0], stringArray3[1], hashMap);
                } else {
                    Scripts.getInstance().callScripts(player, stringArray3[0], stringArray3[1], new Object[]{stringArray2}, hashMap);
                }
            } else if (this.a.bypass.startsWith("user_")) {
                String string = this.a.bypass.substring(5).trim();
                String string2 = string.split("\\s+")[0];
                String string3 = string.substring(string2.length()).trim();
                IVoicedCommandHandler iVoicedCommandHandler = VoicedCommandHandler.getInstance().getVoicedCommandHandler(string2);
                if (iVoicedCommandHandler != null) {
                    iVoicedCommandHandler.useVoicedCommand(string2, player, string3);
                } else {
                    cP.warn("Unknown voiced command '" + string2 + "'");
                }
            } else if (this.a.bypass.startsWith("npc_")) {
                int n = this.a.bypass.indexOf(95, 5);
                String string = n > 0 ? this.a.bypass.substring(4, n) : this.a.bypass.substring(4);
                GameObject gameObject2 = player.getVisibleObject(Integer.parseInt(string));
                if (gameObject2 != null && gameObject2.isNpc() && n > 0 && gameObject2.isInActingRange(player)) {
                    player.setLastNpc((NpcInstance)gameObject2);
                    ((NpcInstance)gameObject2).onBypassFeedback(player, this.a.bypass.substring(n + 1));
                }
            } else if (this.a.bypass.startsWith("_olympiad") && this.a.bypass.contains("?command=move_op_field&field=")) {
                if (!Config.OLY_SPECTATION_ALLOWED) {
                    return;
                }
                int n = -1;
                try {
                    n = Integer.parseInt(this.a.bypass.substring(38));
                    CompetitionController.getInstance().watchCompetition(player, n);
                }
                catch (Exception exception) {
                    cP.warn("OlyObserver", (Throwable)exception);
                    exception.printStackTrace();
                }
            } else if (this.a.bypass.startsWith("_diary")) {
                String string = this.a.bypass.substring(this.a.bypass.indexOf("?") + 1);
                StringTokenizer stringTokenizer = new StringTokenizer(string, "&");
                int n = Integer.parseInt(stringTokenizer.nextToken().split("=")[1]);
                int n2 = Integer.parseInt(stringTokenizer.nextToken().split("=")[1]);
                HeroController.getInstance().showHeroDiary(player, n, n2);
            } else if (this.a.bypass.startsWith("_match")) {
                String string = this.a.bypass.substring(this.a.bypass.indexOf("?") + 1);
                StringTokenizer stringTokenizer = new StringTokenizer(string, "&");
                int n = Integer.parseInt(stringTokenizer.nextToken().split("=")[1]);
                int n3 = Integer.parseInt(stringTokenizer.nextToken().split("=")[1]);
                HeroController.getInstance().showHistory(player, n, n3);
            } else if (this.a.bypass.startsWith("manor_menu_select")) {
                GameObject gameObject3 = player.getTarget();
                if (gameObject3 != null && gameObject3.isNpc()) {
                    ((NpcInstance)gameObject3).onBypassFeedback(player, this.a.bypass);
                }
            } else if (this.a.bypass.equalsIgnoreCase("bypass -h npc_%objectId%_lang ru")) {
                GameObject gameObject4 = gameClient.getActiveChar().getTarget();
                if (gameObject4 != null && gameObject4.isNpc()) {
                    NpcInstance npcInstance2 = (NpcInstance)gameObject4;
                    player.setVar("lang@", "ru", -1L);
                    npcInstance2.showChatWindow(player, 0, new Object[0]);
                }
            } else if (this.a.bypass.equalsIgnoreCase("bypass -h npc_%objectId%_lang en")) {
                GameObject gameObject5 = gameClient.getActiveChar().getTarget();
                if (gameObject5 != null && gameObject5.isNpc()) {
                    NpcInstance npcInstance3 = (NpcInstance)gameObject5;
                    player.setVar("lang@", "en", -1L);
                    npcInstance3.showChatWindow(player, 0, new Object[0]);
                }
            } else if (this.a.bypass.startsWith(ExEnchantSkillList.EX_ENCHANT_SKILLLIST_BYPASS)) {
                String string;
                if (npcInstance != null && npcInstance.canEnchantSkills() && NpcInstance.canBypassCheck(player, npcInstance) && StringUtils.isNumeric((CharSequence)(string = this.a.bypass.substring(ExEnchantSkillList.EX_ENCHANT_SKILLLIST_BYPASS.length()).trim()))) {
                    player.sendPacket((IStaticPacket)ExEnchantSkillList.packetFor(player, npcInstance, Integer.parseInt(string)));
                }
            } else if (this.a.bypass.startsWith(ExEnchantSkillInfo.EX_ENCHANT_SKILLINFO_BYPASS)) {
                if (npcInstance != null && npcInstance.canEnchantSkills() && NpcInstance.canBypassCheck(player, npcInstance)) {
                    String string = this.a.bypass.substring(ExEnchantSkillInfo.EX_ENCHANT_SKILLINFO_BYPASS.length()).trim();
                    player.sendPacket((IStaticPacket)ExEnchantSkillInfo.packetFor(player, npcInstance, StringUtils.split((String)string, (char)' ')));
                }
            } else if (this.a.bypass.startsWith(ExEnchantSkill.EX_ENCHANT_SKILL_BYPASS)) {
                if (npcInstance != null && npcInstance.canEnchantSkills() && NpcInstance.canBypassCheck(player, npcInstance)) {
                    String string = this.a.bypass.substring(ExEnchantSkill.EX_ENCHANT_SKILL_BYPASS.length()).trim();
                    player.sendPacket((IStaticPacket)ExEnchantSkill.packetFor(player, npcInstance, StringUtils.split((String)string, (char)' ')));
                }
            } else if (this.a.bypass.startsWith("multisell ")) {
                MultiSellHolder.getInstance().SeparateAndSend(Integer.parseInt(this.a.bypass.substring(10)), player, 0.0);
            } else if (this.a.bypass.startsWith("Quest ")) {
                String string = this.a.bypass.substring(6).trim();
                int n = string.indexOf(32);
                if (n < 0) {
                    player.processQuestEvent(string, "", npcInstance);
                } else {
                    player.processQuestEvent(string.substring(0, n), string.substring(n).trim(), npcInstance);
                }
            } else if (this.a.bbs) {
                if (!Config.COMMUNITYBOARD_ENABLED) {
                    player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.THE_COMMUNITY_SERVER_IS_CURRENTLY_OFFLINE));
                } else {
                    ICommunityBoardHandler iCommunityBoardHandler;
                    if (player.isGM()) {
                        Functions.sendDebugMessage(player, "BBS Bypass: " + this.a.bypass);
                    }
                    if ((iCommunityBoardHandler = CommunityBoardManager.getInstance().getCommunityHandler(this.a.bypass, player)) != null) {
                        iCommunityBoardHandler.onBypassCommand(player, this.a.bypass);
                    }
                }
            }
        }
        catch (Exception exception) {
            GameObject gameObject;
            String string = "Bad RequestBypassToServer: " + this.a.bypass;
            GameObject gameObject6 = gameObject = player != null ? player.getTarget() : null;
            if (gameObject != null && gameObject.isNpc()) {
                string = string + " via NPC #" + ((NpcInstance)gameObject).getNpcId();
            }
            cP.error(string, (Throwable)exception);
        }
    }

    private static void a(GameClient gameClient) {
        GameObject gameObject = gameClient.getActiveChar().getTarget();
        if (gameObject != null && gameObject.isNpc()) {
            NpcInstance npcInstance = (NpcInstance)gameObject;
            Player player = gameClient.getActiveChar();
            npcInstance.setTarget(player);
            npcInstance.moveToLocation(player.getLoc(), 0, true);
        }
    }

    private static void q(Player player, String string) {
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(5);
        npcHtmlMessage.setFile(string);
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }
}
