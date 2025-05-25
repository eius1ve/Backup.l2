/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.Config
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.data.htm.HtmCache
 *  l2.gameserver.data.xml.holder.ItemHolder
 *  l2.gameserver.handler.items.IItemHandler
 *  l2.gameserver.handler.items.ItemHandler
 *  l2.gameserver.instancemanager.ServerVariables
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Effect
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.Summon
 *  l2.gameserver.model.Zone$ZoneType
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.items.ItemInstance
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.NpcHtmlMessage
 *  l2.gameserver.network.l2.s2c.ShowBoard
 *  l2.gameserver.network.l2.s2c.SkillCoolTime
 *  l2.gameserver.network.l2.s2c.SystemMessage
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.skills.TimeStamp
 *  l2.gameserver.tables.SkillTable
 *  l2.gameserver.templates.item.ItemTemplate
 *  l2.gameserver.utils.ItemFunctions
 *  l2.gameserver.utils.Location
 *  l2.gameserver.utils.Log
 *  l2.gameserver.utils.Log$ItemLog
 *  org.dom4j.Document
 *  org.dom4j.Element
 *  org.dom4j.io.SAXReader
 *  org.napile.primitive.maps.CIntObjectMap
 *  org.napile.primitive.maps.IntObjectMap
 *  org.napile.primitive.maps.impl.CHashIntObjectMap
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package services;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.data.htm.HtmCache;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.handler.items.IItemHandler;
import l2.gameserver.handler.items.ItemHandler;
import l2.gameserver.instancemanager.ServerVariables;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Effect;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.Summon;
import l2.gameserver.model.Zone;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.network.l2.s2c.ShowBoard;
import l2.gameserver.network.l2.s2c.SkillCoolTime;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.skills.TimeStamp;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.utils.ItemFunctions;
import l2.gameserver.utils.Location;
import l2.gameserver.utils.Log;
import npc.model.NpcBufferInstance;
import npc.model.UniversalNpcInstance;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.napile.primitive.maps.CIntObjectMap;
import org.napile.primitive.maps.IntObjectMap;
import org.napile.primitive.maps.impl.CHashIntObjectMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.community.custom.ACbConfigManager;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Buffer
extends Functions
implements ScriptFile {
    private static final Logger dV = LoggerFactory.getLogger(Buffer.class);
    private static final String hs = "Buffer";
    private static final String ht = ";,";
    private static final String hu = "BuffProfRec";
    private static final String hv = "BuffProf-";
    private static final CIntObjectMap<BuffTemplate> a = new CHashIntObjectMap();
    private static final int bFq = 5;
    private static final int bFr = -100;
    private static final int bFs = -1000;
    private static final int bFt = -2000;
    private static final int bFu = -2;
    private static final int bFv = -3;
    private static final int bFw = -4;
    private static final int bFx = -5;
    private static final int bFy = -6;
    private static final SAXReader b = new SAXReader(true);
    private static final File j = new File(Config.DATAPACK_ROOT, "data/buff_templates.xml");
    private static BuffItemHandler a;
    private static int[] bU;
    public static final int SHARED_REUSE_GROUP = -99999;

    private static boolean x(Player player) {
        if (player.isSharedGroupDisabled(-99999)) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_IS_NOT_AVAILABLE_AT_THIS_TIME_BEING_PREPARED_FOR_REUSE).addString(hs));
            return false;
        }
        TimeStamp timeStamp = new TimeStamp(-99999, System.currentTimeMillis() + Config.ALT_NPC_BUFFER_REUSE_DELAY, Config.ALT_NPC_BUFFER_REUSE_DELAY);
        player.addSharedGroupReuse(-99999, timeStamp);
        return true;
    }

    private static void b(int n, Player player) {
        switch (n) {
            case -100: {
                player.setVar(hu, "", -1L);
                break;
            }
            case -2: {
                if (player.isInCombat()) {
                    player.sendMessage(new CustomMessage("scripts.npc.model.L2NpcBufferInstance.InCombat", player, new Object[0]));
                    return;
                }
                for (int i = 0; i < Config.ALT_BUFFER_CANCEL_PRICE.length; i += 2) {
                    if (Buffer.getItemCount((Playable)player, (int)Config.ALT_BUFFER_CANCEL_PRICE[i]) < (long)Config.ALT_BUFFER_CANCEL_PRICE[i + 1]) {
                        if (Config.ALT_BUFFER_CANCEL_PRICE[i] == 57) {
                            player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
                        } else {
                            player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_REQUIRED_ITEMS);
                        }
                        return;
                    }
                    int n2 = Config.ALT_BUFFER_CANCEL_PRICE[i];
                    long l = Config.ALT_BUFFER_CANCEL_PRICE[i + 1];
                    ItemFunctions.removeItem((Playable)player, (int)n2, (long)l, (boolean)true);
                }
                if (player.getPet() != null) {
                    player.getPet().getEffectList().stopAllEffects();
                }
                player.getEffectList().stopAllEffects();
                player.sendPacket((IStaticPacket)new SkillCoolTime(player));
                break;
            }
            case -6: {
                for (int i = 0; i < Config.ALT_BUFFER_CANCEL_PRICE.length; i += 2) {
                    if (Buffer.getItemCount((Playable)player, (int)Config.ALT_BUFFER_CANCEL_PRICE[i]) < (long)Config.ALT_BUFFER_CANCEL_PRICE[i + 1]) {
                        if (Config.ALT_BUFFER_CANCEL_PRICE[i] == 57) {
                            player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
                        } else {
                            player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_REQUIRED_ITEMS);
                        }
                        return;
                    }
                    int n3 = Config.ALT_BUFFER_CANCEL_PRICE[i];
                    long l = Config.ALT_BUFFER_CANCEL_PRICE[i + 1];
                    ItemFunctions.removeItem((Playable)player, (int)n3, (long)l, (boolean)true);
                }
                if (player.getPet() != null) {
                    player.getPet().getEffectList().stopAllEffects();
                }
                player.sendPacket((IStaticPacket)new SkillCoolTime(player));
                break;
            }
            case -4: {
                if (player.isInCombat()) {
                    player.sendMessage(new CustomMessage("scripts.npc.model.L2NpcBufferInstance.InCombat", player, new Object[0]));
                    return;
                }
                for (int i = 0; i < Config.ALT_BUFFER_CLEANS_PRICE.length; i += 2) {
                    if (Buffer.getItemCount((Playable)player, (int)Config.ALT_BUFFER_CLEANS_PRICE[i]) < (long)Config.ALT_BUFFER_CLEANS_PRICE[i + 1]) {
                        if (Config.ALT_BUFFER_CLEANS_PRICE[i] == 57) {
                            player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
                        } else {
                            player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_REQUIRED_ITEMS);
                        }
                        return;
                    }
                    int n4 = Config.ALT_BUFFER_CLEANS_PRICE[i];
                    long l = Config.ALT_BUFFER_CLEANS_PRICE[i + 1];
                    ItemFunctions.removeItem((Playable)player, (int)n4, (long)l, (boolean)true);
                }
                if (!Buffer.x(player)) break;
                player.getEffectList().stopAllNegateEffects();
                if (player.getPet() == null) break;
                player.getPet().getEffectList().stopAllNegateEffects();
                break;
            }
            case -3: {
                if (player.isInCombat() || player.isOlyParticipant() || player.isCursedWeaponEquipped()) {
                    player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_CANNOT_BE_USED_DUE_TO_UNSUITABLE_TERMS).addString(hs));
                    return;
                }
                for (int i = 0; i < Config.ALT_BUFFER_HP_CP_MP_PRICE.length; i += 2) {
                    if (Buffer.getItemCount((Playable)player, (int)Config.ALT_BUFFER_HP_CP_MP_PRICE[i]) < (long)Config.ALT_BUFFER_HP_CP_MP_PRICE[i + 1]) {
                        if (Config.ALT_BUFFER_HP_CP_MP_PRICE[i] == 57) {
                            player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
                        } else {
                            player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_REQUIRED_ITEMS);
                        }
                        return;
                    }
                    int n5 = Config.ALT_BUFFER_HP_CP_MP_PRICE[i];
                    long l = Config.ALT_BUFFER_HP_CP_MP_PRICE[i + 1];
                    ItemFunctions.removeItem((Playable)player, (int)n5, (long)l, (boolean)true);
                }
                if (!Buffer.x(player)) break;
                player.setCurrentHpMp((double)player.getMaxHp(), (double)player.getMaxMp());
                player.setCurrentCp((double)player.getMaxCp());
                player.sendStatusUpdate(true, false, new int[]{9, 11, 33, 10, 12, 34});
                if (player.getPet() == null) break;
                Summon summon = player.getPet();
                summon.setCurrentHpMp((double)summon.getMaxHp(), (double)summon.getMaxMp(), false);
                break;
            }
            case -5: {
                if (player.isInCombat() || player.isOlyParticipant()) {
                    player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_CANNOT_BE_USED_DUE_TO_UNSUITABLE_TERMS).addString(hs));
                    return;
                }
                for (int i = 0; i < Config.ALT_BUFFER_CLEANS_AND_RESTORE_PRICE.length; i += 2) {
                    if (Buffer.getItemCount((Playable)player, (int)Config.ALT_BUFFER_CLEANS_AND_RESTORE_PRICE[i]) < (long)Config.ALT_BUFFER_CLEANS_AND_RESTORE_PRICE[i + 1]) {
                        if (Config.ALT_BUFFER_CLEANS_AND_RESTORE_PRICE[i] == 57) {
                            player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
                        } else {
                            player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_REQUIRED_ITEMS);
                        }
                        return;
                    }
                    int n6 = Config.ALT_BUFFER_CLEANS_AND_RESTORE_PRICE[i];
                    long l = Config.ALT_BUFFER_CLEANS_AND_RESTORE_PRICE[i + 1];
                    ItemFunctions.removeItem((Playable)player, (int)n6, (long)l, (boolean)true);
                }
                if (!Buffer.x(player)) break;
                player.getEffectList().stopAllNegateEffects();
                player.setCurrentHpMp((double)player.getMaxHp(), (double)player.getMaxMp());
                player.setCurrentCp((double)player.getMaxCp());
                player.sendStatusUpdate(true, false, new int[]{9, 11, 33, 10, 12, 34});
                player.sendPacket((IStaticPacket)new SkillCoolTime(player));
                if (player.getPet() == null) break;
                Summon summon = player.getPet();
                summon.setCurrentHpMp((double)summon.getMaxHp(), (double)summon.getMaxMp(), false);
                summon.getEffectList().stopAllNegateEffects();
                break;
            }
            default: {
                if (n > 0) {
                    if (!Buffer.x(player)) break;
                    BuffTemplate buffTemplate = (BuffTemplate)a.get(n);
                    if (buffTemplate != null) {
                        buffTemplate.apply(player);
                        Buffer.u(player, n);
                        break;
                    }
                    dV.warn("Buffer: Unknown menuId \"" + n + "\" used.");
                    break;
                }
                if (n <= -2000 && n > -2005) {
                    int n7 = Math.abs(-2000 - n);
                    Buffer.t(player, n7);
                    break;
                }
                if (n > -1000 || n <= -1005) break;
                int n8 = Math.abs(-1000 - n);
                if (!Buffer.x(player)) break;
                Buffer.s(player, n8);
            }
        }
    }

    private static void s(final Player player, int n) {
        if (n > 5) {
            return;
        }
        String string = player.getVar(String.format("%s%d", hv, n));
        if (string == null || string.trim().isEmpty()) {
            player.sendMessage(new CustomMessage("scripts.npc.model.L2NpcBufferInstance.EmptyProfile", player, new Object[0]));
            return;
        }
        StringTokenizer stringTokenizer = new StringTokenizer(string.trim(), ht, false);
        final LinkedList<BuffTemplate> linkedList = new LinkedList<BuffTemplate>();
        while (stringTokenizer.hasMoreTokens()) {
            String string2 = stringTokenizer.nextToken();
            if (string2.isEmpty()) continue;
            try {
                int n2 = Integer.parseInt(string2);
                BuffTemplate buffTemplate = (BuffTemplate)a.get(n2);
                if (buffTemplate == null) continue;
                linkedList.add(buffTemplate);
            }
            catch (NumberFormatException numberFormatException) {
                dV.error("Buffer: Can't apply profile \"" + n + "\"", (Throwable)numberFormatException);
            }
        }
        ThreadPoolManager.getInstance().execute((Runnable)new RunnableImpl(){

            public void runImpl() throws Exception {
                for (BuffTemplate buffTemplate : linkedList) {
                    if (buffTemplate.applySync(player)) continue;
                    return;
                }
            }
        });
    }

    private static void t(Player player, int n) {
        String string = player.getVar(hu);
        if (n > 5 || string == null) {
            player.sendMessage(new CustomMessage("scripts.npc.model.L2NpcBufferInstance.CantSaveProfile", player, new Object[0]));
            return;
        }
        player.setVar(String.format("%s%d", hv, n), string, -1L);
        player.unsetVar(hu);
        player.sendMessage(new CustomMessage("scripts.npc.model.L2NpcBufferInstance.ProfileSaved", player, new Object[0]));
    }

    private static void u(Player player, int n) {
        Object object = player.getVar(hu);
        if (object == null) {
            return;
        }
        if (((String)object).trim().isEmpty()) {
            player.setVar(hu, String.format("%d", n), -1L);
        } else if (((String)(object = (String)object + String.format(";%d", n))).length() > 250) {
            player.sendMessage(new CustomMessage("scripts.npc.model.L2NpcBufferInstance.LimitProfile", player, new Object[0]));
            player.unsetVar(hv);
        } else {
            player.sendMessage(new CustomMessage("scripts.npc.model.L2NpcBufferInstance.BuffAdded", player, new Object[0]));
            player.setVar(hu, ((String)object).trim(), -1L);
        }
    }

    private static boolean y(Player player) {
        for (int i = 0; i < bU.length; ++i) {
            if (Functions.getItemCount((Playable)player, (int)bU[i]) <= 0L) continue;
            return true;
        }
        return false;
    }

    private static boolean a(Player player, NpcInstance npcInstance) {
        if (npcInstance == null) {
            return false;
        }
        if (!(npcInstance instanceof NpcBufferInstance) && !(npcInstance instanceof UniversalNpcInstance)) {
            return false;
        }
        return !player.isActionsDisabled() && (Config.ALLOW_TALK_WHILE_SITTING || !player.isSitting()) && npcInstance.isInActingRange((GameObject)player);
    }

    public void act(String[] stringArray) {
        Player player = this.getSelf();
        if (player == null || stringArray == null || stringArray.length == 0) {
            return;
        }
        NpcInstance npcInstance = null;
        if (!Buffer.y(player) && !Buffer.a(player, npcInstance = this.getNpc())) {
            player.sendActionFailed();
            return;
        }
        if (player.isInCombat()) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_CANNOT_BE_USED_DUE_TO_UNSUITABLE_TERMS).addString(hs));
            player.sendActionFailed();
            return;
        }
        if (player.isCursedWeaponEquipped() && !Config.ALT_BUFFER_FOR_CURSED_WEAPON) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_cursed_weapon.htm"));
            player.sendActionFailed();
            return;
        }
        Map<String, String> map = Buffer.b(stringArray[0]);
        String string = map.get("ask");
        String string2 = map.get("reply");
        if (string != null && !string.isEmpty()) {
            try {
                int n = Integer.parseInt(string);
                Buffer.b(n, player);
            }
            catch (Exception exception) {
                dV.error("Buffer: Can't apply buff of menuId = \"" + string + "\"", (Throwable)exception);
            }
        }
        if (string2 != null && !string2.isEmpty()) {
            Buffer.showPage(player, string2, npcInstance);
        } else {
            player.sendMessage(new CustomMessage("scripts.npc.model.L2NpcBufferInstance.InCombat", player, new Object[0]));
            player.sendActionFailed();
        }
    }

    public static void showPage(Player player, String string, NpcInstance npcInstance) {
        if (string == null || string.contains("..")) {
            return;
        }
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, npcInstance);
        String string2 = "mods/buffer/" + string + ".htm";
        if (player.hasBonus() && Config.ALT_NPC_BUFFER_PREMIUM_HTML_PREFIX) {
            string2 = "mods/buffer/" + string + "-pa.htm";
        } else if (Config.ALT_NPC_BUFFER_PREMIUM_ITEM_IDS != null && Config.ALT_NPC_BUFFER_PREMIUM_ITEM_IDS.length > 0) {
            for (int n : Config.ALT_NPC_BUFFER_PREMIUM_ITEM_IDS) {
                if (Buffer.getItemCount((Playable)player, (int)n) <= 0L) continue;
                string2 = "mods/buffer/" + string + "-pa.htm";
                break;
            }
        }
        npcHtmlMessage.setFile(string2);
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    public void actBBS(String[] stringArray) {
        Player player = this.getSelf();
        if (player == null || stringArray == null || stringArray.length == 0) {
            return;
        }
        if (ACbConfigManager.BBS_BUFFER_PEACE_ONLY && !player.isInZonePeace()) {
            String string = HtmCache.getInstance().getNotNull("scripts/services/community/pages/locked.htm", player);
            string = string.replace("%name%", player.getName());
            ShowBoard.separateAndSend((String)string, (Player)player);
            return;
        }
        if (ACbConfigManager.BBS_BUFFER_ACCESS_ITEM > 0 && player.getInventory().getItemByItemId(ACbConfigManager.BBS_BUFFER_ACCESS_ITEM) == null) {
            String string = HtmCache.getInstance().getNotNull("scripts/services/community/pages/premium_item_only.htm", player);
            string = string.replace("%name%", player.getName());
            string = string.replace("%item%", String.valueOf(ACbConfigManager.BBS_BUFFER_ACCESS_ITEM));
            ShowBoard.separateAndSend((String)string, (Player)player);
            return;
        }
        if (ACbConfigManager.BBS_BUFFER_ACCESS_PREMIUM_ONLY && !player.hasBonus()) {
            String string = HtmCache.getInstance().getNotNull("scripts/services/community/pages/premium_only.htm", player);
            string = string.replace("%name%", player.getName());
            ShowBoard.separateAndSend((String)string, (Player)player);
            return;
        }
        if (!ACbConfigManager.ALLOW_PVPCB_AT_SIEGE_ZONES && player.isInZone(Zone.ZoneType.SIEGE)) {
            String string = HtmCache.getInstance().getNotNull("scripts/services/community/pages/locked.htm", player);
            string = string.replace("%name%", player.getName());
            ShowBoard.separateAndSend((String)string, (Player)player);
            return;
        }
        if (!ACbConfigManager.ALLOW_PVPCB_AT_PVP_ZONES && player.isInZone(Zone.ZoneType.battle_zone)) {
            String string = HtmCache.getInstance().getNotNull("scripts/services/community/pages/locked.htm", player);
            string = string.replace("%name%", player.getName());
            ShowBoard.separateAndSend((String)string, (Player)player);
            return;
        }
        if (!ACbConfigManager.ALLOW_PVPCB_AT_FUN_ZONES && player.isInZone(Zone.ZoneType.fun)) {
            String string = HtmCache.getInstance().getNotNull("scripts/services/community/pages/locked.htm", player);
            string = string.replace("%name%", player.getName());
            ShowBoard.separateAndSend((String)string, (Player)player);
            return;
        }
        if (!ACbConfigManager.ALLOW_PVPCB_AT_EPIC_ZONES && player.isInZone(Zone.ZoneType.epic)) {
            String string = HtmCache.getInstance().getNotNull("scripts/services/community/pages/locked.htm", player);
            string = string.replace("%name%", player.getName());
            ShowBoard.separateAndSend((String)string, (Player)player);
            return;
        }
        if (player.isInCombat()) {
            player.sendMessage(new CustomMessage("scripts.npc.model.L2NpcBufferInstance.InCombat", player, new Object[0]));
            player.sendActionFailed();
            return;
        }
        if (player.isCursedWeaponEquipped() && !Config.ALT_BUFFER_FOR_CURSED_WEAPON) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_cursed_weapon.htm"));
            player.sendActionFailed();
            return;
        }
        Map<String, String> map = Buffer.b(stringArray[0]);
        String string = map.get("ask");
        String string2 = map.get("reply");
        if (string != null && !string.isEmpty()) {
            try {
                int n = Integer.parseInt(string);
                Buffer.b(n, player);
            }
            catch (Exception exception) {
                dV.error("Buffer: Can't apply buff of menuId = \"" + string + "\"", (Throwable)exception);
            }
        }
        if (string2 != null && !string2.isEmpty()) {
            Buffer.showBBSPage(player, string2);
        } else {
            player.sendActionFailed();
        }
    }

    public static void showBBSPage(Player player, String string) {
        if (string == null || string.contains("..")) {
            return;
        }
        if (player.isGM()) {
            Functions.sendDebugMessage((Player)player, (String)("HTML: mods/buffer/community_buffer/" + string + ".htm"));
        }
        String string2 = HtmCache.getInstance().getNotNull("mods/buffer/community_buffer/" + string + ".htm", player);
        if (player.hasBonus() && ACbConfigManager.BBS_BUFFER_PREMIUM_HTML_PREFIX) {
            string2 = HtmCache.getInstance().getNotNull("mods/buffer/community_buffer/" + string + "-pa.htm", player);
        } else if (ACbConfigManager.BBS_BUFFER_PREMIUM_ITEM_IDS != null && ACbConfigManager.BBS_BUFFER_PREMIUM_ITEM_IDS.length > 0) {
            for (int n : ACbConfigManager.BBS_BUFFER_PREMIUM_ITEM_IDS) {
                if (Buffer.getItemCount((Playable)player, (int)n) <= 0L) continue;
                string2 = HtmCache.getInstance().getNotNull("mods/buffer/community_buffer/" + string + "-pa.htm", player);
                break;
            }
        }
        ShowBoard.separateAndSend((String)string2, (Player)player);
    }

    public void onLoad() {
        Buffer.ck();
        Buffer.cj();
    }

    public void onReload() {
        Buffer.ck();
        Buffer.cj();
    }

    public void onShutdown() {
        a.clear();
    }

    private static void cj() {
        int n;
        String string = ServerVariables.getString((String)"BuffItemIds", (String)"3433");
        StringTokenizer stringTokenizer = new StringTokenizer(string, ht, false);
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        while (stringTokenizer.hasMoreTokens()) {
            n = Integer.parseInt(stringTokenizer.nextToken());
            arrayList.add(n);
        }
        if (a != null) {
            ItemHandler.getInstance().unregisterItemHandler((IItemHandler)a);
        }
        bU = new int[arrayList.size()];
        for (n = 0; n < arrayList.size(); ++n) {
            Buffer.bU[n] = (Integer)arrayList.get(n);
        }
        a = new BuffItemHandler();
        ItemHandler.getInstance().registerItemHandler((IItemHandler)a);
        dV.info("Buffer: Loaded " + bU.length + " buff item(s).");
    }

    private static void ck() {
        CHashIntObjectMap cHashIntObjectMap = new CHashIntObjectMap();
        try {
            Document document = b.read(j);
            Element element = document.getRootElement();
            Iterator iterator = document.getRootElement().elementIterator();
            while (iterator.hasNext()) {
                Element element2 = (Element)iterator.next();
                if (!"template".equalsIgnoreCase(element2.getName())) continue;
                int n = Integer.parseInt(element2.attributeValue("menuId"));
                BuffTarget buffTarget = BuffTarget.valueOf(element2.attributeValue("target").toUpperCase());
                int n2 = Integer.parseInt(element2.attributeValue("minLevel", "0"));
                int n3 = Integer.parseInt(element2.attributeValue("maxLevel", String.valueOf(Config.ALT_MAX_LEVEL)));
                ArrayList<BuffTemplateConsume> arrayList = new ArrayList<BuffTemplateConsume>();
                boolean bl = false;
                ArrayList<Skill> arrayList2 = new ArrayList<Skill>();
                Object object = element2.elementIterator();
                while (object.hasNext()) {
                    int n4;
                    Element element3;
                    Element element4;
                    Iterator iterator2;
                    Element element5 = (Element)object.next();
                    if ("consume".equalsIgnoreCase(element5.getName())) {
                        bl = Boolean.parseBoolean(element5.attributeValue("anyFirst", "false"));
                        iterator2 = element5.elementIterator();
                        while (iterator2.hasNext()) {
                            element4 = (Element)iterator2.next();
                            if (!"item".equalsIgnoreCase(element4.getName())) continue;
                            element3 = element4;
                            n4 = Integer.parseInt(element3.attributeValue("id"));
                            long l = Long.parseLong(element3.attributeValue("amount"));
                            int n5 = Integer.parseInt(element3.attributeValue("from_level", "0"));
                            boolean bl2 = Boolean.parseBoolean(element3.attributeValue("is_premium_required", "false"));
                            ItemTemplate itemTemplate = ItemHolder.getInstance().getTemplate(n4);
                            arrayList.add(new BuffTemplateConsume(itemTemplate, l, n5, bl2));
                        }
                        continue;
                    }
                    if (!"produce".equalsIgnoreCase(element5.getName())) continue;
                    iterator2 = element5.elementIterator();
                    while (iterator2.hasNext()) {
                        element4 = (Element)iterator2.next();
                        if (!"skill".equalsIgnoreCase(element4.getName())) continue;
                        element3 = element4;
                        n4 = Integer.parseInt(element3.attributeValue("id"));
                        int n6 = Integer.parseInt(element3.attributeValue("level"));
                        Skill skill = SkillTable.getInstance().getInfo(n4, n6);
                        arrayList2.add(skill);
                    }
                }
                object = new BuffTemplate(arrayList2, buffTarget, arrayList, bl, n2, n3);
                cHashIntObjectMap.put(n, object);
            }
            a.clear();
            a.putAll((IntObjectMap)cHashIntObjectMap);
            dV.info("Buffer: Loaded " + cHashIntObjectMap.size() + " buff template(s).");
        }
        catch (Exception exception) {
            dV.error(exception.getMessage(), (Throwable)exception);
        }
    }

    private static Map<String, String> b(String string) {
        TreeMap<String, String> treeMap = new TreeMap<String, String>();
        char[] cArray = string.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        String string2 = null;
        String string3 = null;
        for (int i = 0; i < cArray.length; ++i) {
            char c = cArray[i];
            if (c == '=' && string2 == null) {
                string2 = stringBuilder.toString();
                stringBuilder.setLength(0);
                continue;
            }
            if (c == '&') {
                string3 = stringBuilder.toString();
                treeMap.put(string2, string3);
                stringBuilder.setLength(0);
                string2 = null;
                string3 = null;
                continue;
            }
            stringBuilder.append(c);
        }
        if (string2 != null) {
            string3 = stringBuilder.toString();
            treeMap.put(string2, string3);
        }
        return treeMap;
    }

    private static List<Skill> d(String string) {
        LinkedList<Skill> linkedList = new LinkedList<Skill>();
        StringTokenizer stringTokenizer = new StringTokenizer(string, ht, false);
        while (stringTokenizer.hasMoreTokens()) {
            String string2 = stringTokenizer.nextToken();
            int n = string2.indexOf(45);
            if (n > 0) {
                try {
                    String string3 = string2.substring(0, n);
                    String string4 = string2.substring(n + 1);
                    int n2 = Integer.parseInt(string3);
                    int n3 = Integer.parseInt(string4);
                    Skill skill = SkillTable.getInstance().getInfo(n2, n3);
                    if (skill == null) {
                        dV.error("Buffer: Buff template \"" + string2 + "\" skill " + n2 + "-" + n3 + " undefined!");
                        continue;
                    }
                    linkedList.add(skill);
                }
                catch (NumberFormatException numberFormatException) {
                    dV.error("Buffer: Can't parse buff template \"" + string2 + "\"", (Throwable)numberFormatException);
                }
                continue;
            }
            dV.error("Buffer: Can't parse buff template \"" + string2 + "\"");
        }
        return linkedList;
    }

    private static class BuffTemplate {
        private final List<Skill> dJ;
        private final BuffTarget a;
        private final List<BuffTemplateConsume> dK;
        private final boolean hx;
        private final int bFz;
        private final int bFA;

        protected BuffTemplate(List<Skill> list, BuffTarget buffTarget, List<BuffTemplateConsume> list2, boolean bl, int n, int n2) {
            this.dJ = list;
            this.a = buffTarget;
            this.dK = list2;
            this.hx = bl;
            this.bFz = n;
            this.bFA = n2;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void k(Creature creature) {
            if (creature == null) {
                return;
            }
            boolean bl = false;
            creature.block();
            try {
                for (Skill skill : this.dJ) {
                    for (Effect effect : creature.getEffectList().getAllEffects()) {
                        if (effect.getSkill().getId() != skill.getId()) continue;
                        effect.exit();
                        bl = true;
                    }
                    if (bl) {
                        creature.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_HAS_WORN_OFF).addSkillName(skill.getDisplayId(), skill.getDisplayLevel()));
                    }
                    if (Config.ALT_NPC_BUFFER_EFFECT_TIME > 0L) {
                        skill.getEffects(creature, creature, false, false, (long)((double)Config.ALT_NPC_BUFFER_EFFECT_TIME * (creature.getPlayer().hasBonus() ? Config.ALT_NPC_BUFFER_PREMIUM_EFFECT_MUL : 1.0)), 1.0, false);
                        continue;
                    }
                    skill.getEffects(creature, creature, false, false);
                }
            }
            finally {
                creature.unblock();
            }
        }

        private Creature a(Creature creature) {
            switch (this.a) {
                case BUFF_PLAYER: {
                    return creature.getPlayer();
                }
                case BUFF_PET: {
                    return creature.getPet();
                }
            }
            return null;
        }

        private boolean z(Player player) {
            if (!this.hx) {
                for (BuffTemplateConsume buffTemplateConsume : this.dK) {
                    if (buffTemplateConsume.getFromLevel() > 0 && player.getLevel() < buffTemplateConsume.getFromLevel()) continue;
                    if (buffTemplateConsume.isBonusRequired() && !player.hasBonus()) {
                        return false;
                    }
                    if (buffTemplateConsume.mayConsume(player)) continue;
                    return false;
                }
            }
            for (BuffTemplateConsume buffTemplateConsume : this.dK) {
                if (buffTemplateConsume.getFromLevel() > 0 && player.getLevel() < buffTemplateConsume.getFromLevel()) continue;
                if (buffTemplateConsume.isBonusRequired() && !player.hasBonus()) {
                    return false;
                }
                if (!this.hx) {
                    if (buffTemplateConsume.consume(player)) continue;
                    return false;
                }
                if (!buffTemplateConsume.consume(player)) continue;
                return true;
            }
            return !this.hx;
        }

        public boolean canApply(Player player) {
            if (player.isInZone(Zone.ZoneType.SIEGE) || player.isInZone(Zone.ZoneType.water) || player.isInDuel()) {
                return false;
            }
            if (player.isOlyParticipant()) {
                return false;
            }
            if (player.isSitting()) {
                return false;
            }
            if (player.getLevel() < this.bFz) {
                player.sendMessage(new CustomMessage("scripts.npc.model.L2NpcBufferInstance.minLevel", player, new Object[0]).addNumber((long)this.bFz));
                return false;
            }
            if (player.getLevel() > this.bFA) {
                player.sendMessage(new CustomMessage("scripts.npc.model.L2NpcBufferInstance.maxLevel", player, new Object[0]).addNumber((long)this.bFA));
                return false;
            }
            return true;
        }

        public boolean apply(final Player player) {
            if (!this.canApply(player)) {
                player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_CANNOT_BE_USED_DUE_TO_UNSUITABLE_TERMS).addString(Buffer.hs));
                return false;
            }
            ThreadPoolManager.getInstance().execute((Runnable)new RunnableImpl(){

                public void runImpl() throws Exception {
                    Creature creature = this.a((Creature)player);
                    if (creature == null) {
                        player.sendPacket((IStaticPacket)SystemMsg.THAT_IS_AN_INCORRECT_TARGET);
                        return;
                    }
                    if (!this.z(player)) {
                        player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_REQUIRED_ITEMS);
                        return;
                    }
                    this.k(creature);
                }
            });
            return true;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public boolean applySync(Player player) {
            Player player2 = player;
            synchronized (player2) {
                if (!this.canApply(player)) {
                    player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_CANNOT_BE_USED_DUE_TO_UNSUITABLE_TERMS).addString(Buffer.hs));
                    return false;
                }
                Creature creature = this.a((Creature)player);
                if (creature == null) {
                    player.sendPacket((IStaticPacket)SystemMsg.THAT_IS_AN_INCORRECT_TARGET);
                    return false;
                }
                if (!this.z(player)) {
                    return false;
                }
                this.k(creature);
            }
            return true;
        }
    }

    private static class BuffItemHandler
    implements IItemHandler {
        private BuffItemHandler() {
        }

        public boolean useItem(Playable playable, ItemInstance itemInstance, boolean bl) {
            try {
                Player player = playable.getPlayer();
                if (player != null) {
                    String string = "item_-" + itemInstance.getItemId();
                    if (player.hasBonus() && Config.ALT_NPC_BUFFER_PREMIUM_HTML_PREFIX) {
                        string = "item-pa-" + itemInstance.getItemId();
                    } else if (Config.ALT_NPC_BUFFER_PREMIUM_ITEM_IDS != null && Config.ALT_NPC_BUFFER_PREMIUM_ITEM_IDS.length > 0) {
                        for (int n : Config.ALT_NPC_BUFFER_PREMIUM_ITEM_IDS) {
                            if (Functions.getItemCount((Playable)player, (int)n) <= 0L) continue;
                            string = "item-pa-" + itemInstance.getItemId();
                            break;
                        }
                    }
                    Buffer.showPage(playable.getPlayer(), string, null);
                    return true;
                }
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
            return false;
        }

        public void dropItem(Player player, ItemInstance itemInstance, long l, Location location) {
            if (!player.getInventory().destroyItem(itemInstance, l)) {
                player.sendActionFailed();
                return;
            }
            Log.LogItem((Player)player, (Log.ItemLog)Log.ItemLog.Delete, (ItemInstance)itemInstance);
            player.disableDrop(1000);
            player.sendChanges();
        }

        public boolean pickupItem(Playable playable, ItemInstance itemInstance) {
            return false;
        }

        public int[] getItemIds() {
            return bU;
        }
    }

    public static final class BuffTarget
    extends Enum<BuffTarget> {
        public static final /* enum */ BuffTarget BUFF_PLAYER = new BuffTarget();
        public static final /* enum */ BuffTarget BUFF_PET = new BuffTarget();
        private static final /* synthetic */ BuffTarget[] a;

        public static BuffTarget[] values() {
            return (BuffTarget[])a.clone();
        }

        public static BuffTarget valueOf(String string) {
            return Enum.valueOf(BuffTarget.class, string);
        }

        private static /* synthetic */ BuffTarget[] a() {
            return new BuffTarget[]{BUFF_PLAYER, BUFF_PET};
        }

        static {
            a = BuffTarget.a();
        }
    }

    private static class BuffTemplateConsume {
        private final ItemTemplate e;
        private final long es;
        private final int bFB;
        private final boolean hy;

        public BuffTemplateConsume(ItemTemplate itemTemplate, long l, int n, boolean bl) {
            this.e = itemTemplate;
            this.es = l;
            this.bFB = n;
            this.hy = bl;
        }

        public ItemTemplate getItem() {
            return this.e;
        }

        public long getAmount() {
            return this.es;
        }

        public int getFromLevel() {
            return this.bFB;
        }

        public boolean isBonusRequired() {
            return this.hy;
        }

        public boolean mayConsume(Player player) {
            long l = player.getInventory().getCountOf(this.getItem().getItemId());
            if (l < this.getAmount()) {
                if (this.getItem().isAdena()) {
                    player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
                } else {
                    player.sendMessage(new CustomMessage("scripts.npc.model.L2NpcBufferInstance.RequiresS1S2", player, new Object[]{this.getItem().getName(), this.getAmount()}));
                }
                return false;
            }
            return true;
        }

        public boolean consume(Player player) {
            if (this.getAmount() == 0L) {
                return player.getInventory().getCountOf(this.getItem().getItemId()) > 0L;
            }
            return ItemFunctions.removeItem((Playable)player, (int)this.getItem().getItemId(), (long)this.getAmount(), (boolean)true, (boolean)false) >= this.getAmount();
        }
    }
}
