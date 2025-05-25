/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 */
package l2.gameserver.handler.admincommands.impl;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.ThreadMXBean;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import l2.gameserver.Config;
import l2.gameserver.GameServer;
import l2.gameserver.GameTimeController;
import l2.gameserver.ai.CharacterAI;
import l2.gameserver.ai.DefaultAI;
import l2.gameserver.data.htm.HtmCache;
import l2.gameserver.data.xml.holder.NpcHolder;
import l2.gameserver.handler.admincommands.IAdminCommandHandler;
import l2.gameserver.instancemanager.ServerVariables;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.model.SimpleSpawner;
import l2.gameserver.model.WorldRegion;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.instances.RaidBossInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.scripts.Functions;
import l2.gameserver.templates.npc.NpcTemplate;
import l2.gameserver.utils.Util;
import org.apache.commons.lang3.StringUtils;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class AdminServer
implements IAdminCommandHandler {
    private static final SimpleDateFormat b = new SimpleDateFormat("hh:mm a");
    private static final MemoryMXBean b = ManagementFactory.getMemoryMXBean();
    private static final ThreadMXBean b = ManagementFactory.getThreadMXBean();

    @Override
    public boolean useAdminCommand(Enum enum_, String[] stringArray, String string, Player player) {
        Commands commands = (Commands)enum_;
        if (!player.getPlayerAccess().Menu) {
            return false;
        }
        switch (commands) {
            case admin_server: {
                try {
                    String string2 = string.substring(13);
                    AdminServer.showHelpPage(player, string2);
                }
                catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {}
                break;
            }
            case admin_server_info: {
                int[] nArray = new int[]{0, 0, 0, 0, 0};
                MemoryUsage memoryUsage = b.getHeapMemoryUsage();
                long l = memoryUsage.getMax() - memoryUsage.getUsed();
                int n = b.getThreadCount();
                int n2 = b.getDaemonThreadCount();
                int n3 = n - n2;
                int n4 = b.getPeakThreadCount();
                long l2 = b.getTotalStartedThreadCount();
                HashSet<String> hashSet = new HashSet<String>();
                int n5 = 0;
                int n6 = 0;
                for (Player player2 : GameObjectsStorage.getAllPlayersForIterate()) {
                    if (player2 == null || !player2.isOnline()) continue;
                    if (player2.getAccountName().equals("bot_account") || player2.getAccountName().startsWith("phantom_bot_")) {
                        ++n6;
                    }
                    if (player2.getNetConnection() == null) continue;
                    if (StringUtils.isNotBlank((CharSequence)player2.getNetConnection().getHwid())) {
                        hashSet.add(player2.getNetConnection().getHwid());
                    }
                    if (player2.isGM()) {
                        ++n5;
                    }
                    switch (player2.getRace()) {
                        case human: {
                            nArray[0] = nArray[0] + 1;
                            break;
                        }
                        case darkelf: {
                            nArray[1] = nArray[1] + 1;
                            break;
                        }
                        case dwarf: {
                            nArray[2] = nArray[2] + 1;
                            break;
                        }
                        case elf: {
                            nArray[3] = nArray[3] + 1;
                            break;
                        }
                        case orc: {
                            nArray[4] = nArray[4] + 1;
                        }
                    }
                }
                Object object = HtmCache.getInstance().getNotNull("admin/server_info.htm", player);
                int n7 = ServerVariables.getInt("maxTotalOnline", 0);
                long l3 = System.currentTimeMillis();
                long l4 = l3 - ServerVariables.getLong("server_first_start", l3);
                int n8 = (int)TimeUnit.MILLISECONDS.toDays(l4);
                object = ((String)object).replace("%os_name%", System.getProperty("os.name"));
                object = ((String)object).replace("%os_ver%", System.getProperty("os.version"));
                object = ((String)object).replace("%gameTime%", GameTimeController.getInstance().getGameHour() + ":" + GameTimeController.getInstance().getGameMin());
                object = ((String)object).replace("%dayNight%", GameTimeController.getInstance().isNowNight() ? "Night" : "Day");
                object = ((String)object).replace("%geodata%", Config.ALLOW_GEODATA ? "Enabled" : "Disabled");
                object = ((String)object).replace("%serverTime%", b.format(new Date(System.currentTimeMillis())));
                object = ((String)object).replace("%serverUpTime%", this.a());
                object = ((String)object).replace("%usedMem%", b.getHeapMemoryUsage().getUsed() / 0x100000L + " Mb");
                object = ((String)object).replace("%freeMem%", l / 0x100000L + " Mb");
                object = ((String)object).replace("%totalMem%", b.getHeapMemoryUsage().getMax() / 0x100000L + " Mb");
                object = ((String)object).replace("%live%", String.valueOf(n));
                object = ((String)object).replace("%nondaemon%", String.valueOf(n3));
                object = ((String)object).replace("%daemon%", String.valueOf(n2));
                object = ((String)object).replace("%peak%", String.valueOf(n4));
                object = ((String)object).replace("%totalstarted%", String.valueOf(l2));
                object = ((String)object).replace("%slots%", String.valueOf(Config.MAXIMUM_ONLINE_USERS - GameObjectsStorage.getAllPlayersCount()));
                object = ((String)object).replace("%server_alive%", String.valueOf(n8));
                for (GarbageCollectorMXBean garbageCollectorMXBean : ManagementFactory.getGarbageCollectorMXBeans()) {
                    object = ((String)object).replace("%gcol%", garbageCollectorMXBean.getName());
                    object = ((String)object).replace("%colcount%", String.valueOf(garbageCollectorMXBean.getCollectionCount()));
                    object = ((String)object).replace("%coltime%", String.valueOf(garbageCollectorMXBean.getCollectionTime()));
                }
                object = ((String)object).replace("%online%", Util.formatAdena(GameObjectsStorage.getAllPlayersCount() - GameObjectsStorage.getAllOfflineCount() - n6));
                object = ((String)object).replace("%dirty_online%", Util.formatAdena(GameObjectsStorage.getAllPlayersCount()));
                object = ((String)object).replace("%offline_online%", Util.formatAdena(GameObjectsStorage.getAllOfflineCount()));
                object = ((String)object).replace("%day_online%", Util.formatAdena(GameServer.getInstance().getCurrentMaxOnline()));
                object = ((String)object).replace("%all_online%", Util.formatAdena(Math.max(n7, GameServer.getInstance().getCurrentMaxOnline())));
                object = ((String)object).replace("%hwids%", Util.formatAdena(hashSet.size()));
                object = ((String)object).replace("%gms%", Util.formatAdena(n5));
                object = ((String)object).replace("%bots%", Util.formatAdena(n6));
                object = ((String)object).replace("%race_1%", Util.formatAdena(nArray[0]));
                object = ((String)object).replace("%race_2%", Util.formatAdena(nArray[1]));
                object = ((String)object).replace("%race_3%", Util.formatAdena(nArray[2]));
                object = ((String)object).replace("%race_4%", Util.formatAdena(nArray[3]));
                object = ((String)object).replace("%race_5%", Util.formatAdena(nArray[4]));
                Functions.show((String)object, player, null, new Object[0]);
                break;
            }
            case admin_check_actor: {
                GameObject gameObject = player.getTarget();
                if (gameObject == null) {
                    player.sendMessage("target == null");
                    return false;
                }
                if (!gameObject.isCreature()) {
                    player.sendMessage("target is not a character");
                    return false;
                }
                Creature creature = (Creature)gameObject;
                CharacterAI characterAI = creature.getAI();
                if (characterAI == null) {
                    player.sendMessage("ai == null");
                    return false;
                }
                Creature creature2 = characterAI.getActor();
                if (creature2 == null) {
                    player.sendMessage("actor == null");
                    return false;
                }
                player.sendMessage("actor: " + creature2);
                break;
            }
            case admin_setvar: {
                if (stringArray.length != 3) {
                    player.sendMessage("Incorrect argument count!!!");
                    return false;
                }
                ServerVariables.set(stringArray[1], stringArray[2]);
                player.sendMessage("Value changed.");
                break;
            }
            case admin_set_ai_interval: {
                if (stringArray.length != 2) {
                    player.sendMessage("Incorrect argument count!!!");
                    return false;
                }
                int n = Integer.parseInt(stringArray[1]);
                int n9 = 0;
                int n10 = 0;
                for (NpcInstance npcInstance : GameObjectsStorage.getAllNpcsForIterate()) {
                    CharacterAI characterAI;
                    if (npcInstance == null || npcInstance instanceof RaidBossInstance || !((characterAI = npcInstance.getAI()) instanceof DefaultAI)) continue;
                    try {
                        Field field = DefaultAI.class.getDeclaredField("AI_TASK_DELAY");
                        field.setAccessible(true);
                        field.set(characterAI, n);
                        if (!characterAI.isActive()) continue;
                        characterAI.stopAITask();
                        ++n9;
                        WorldRegion worldRegion = npcInstance.getCurrentRegion();
                        if (worldRegion == null || !worldRegion.isActive()) continue;
                        characterAI.startAITask();
                        ++n10;
                    }
                    catch (Exception exception) {}
                }
                player.sendMessage(n9 + " AI stopped, " + n10 + " AI started");
                break;
            }
            case admin_spawn2: {
                StringTokenizer stringTokenizer = new StringTokenizer(string, " ");
                try {
                    stringTokenizer.nextToken();
                    String string3 = stringTokenizer.nextToken();
                    int n = 30;
                    int n11 = 1;
                    if (stringTokenizer.hasMoreTokens()) {
                        n11 = Integer.parseInt(stringTokenizer.nextToken());
                    }
                    if (stringTokenizer.hasMoreTokens()) {
                        n = Integer.parseInt(stringTokenizer.nextToken());
                    }
                    this.a(player, string3, n, n11);
                    break;
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
        }
        return true;
    }

    @Override
    public Enum[] getAdminCommandEnum() {
        return Commands.values();
    }

    public static void showHelpPage(Player player, String string) {
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(5);
        npcHtmlMessage.setFile("admin/" + string);
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    private void a(Player player, String string, int n, int n2) {
        NpcTemplate npcTemplate;
        Pattern pattern;
        Matcher matcher;
        GameObject gameObject = player.getTarget();
        if (gameObject == null) {
            gameObject = player;
        }
        if ((matcher = (pattern = Pattern.compile("[0-9]*")).matcher(string)).matches()) {
            int n3 = Integer.parseInt(string);
            npcTemplate = NpcHolder.getInstance().getTemplate(n3);
        } else {
            string = string.replace('_', ' ');
            npcTemplate = NpcHolder.getInstance().getTemplateByName(string);
        }
        if (npcTemplate == null) {
            player.sendMessage("Incorrect monster template.");
            return;
        }
        try {
            SimpleSpawner simpleSpawner = new SimpleSpawner(npcTemplate);
            simpleSpawner.setLoc(gameObject.getLoc());
            simpleSpawner.setAmount(n2);
            simpleSpawner.setHeading(player.getHeading());
            simpleSpawner.setRespawnDelay(n);
            simpleSpawner.setReflection(player.getReflection());
            simpleSpawner.init();
            if (n == 0) {
                simpleSpawner.stopRespawn();
            }
            player.sendMessage("Created " + npcTemplate.name + " on " + gameObject.getObjectId() + ".");
        }
        catch (Exception exception) {
            player.sendMessage("Target is not ingame.");
        }
    }

    private String a() {
        long l = System.currentTimeMillis() - GameServer.dateTimeServerStarted.getTimeInMillis();
        long l2 = TimeUnit.MILLISECONDS.toDays(l);
        long l3 = TimeUnit.MILLISECONDS.toHours(l -= TimeUnit.DAYS.toMillis(l2));
        return l2 + " Days, " + l3 + " Hours, " + TimeUnit.MILLISECONDS.toMinutes(l -= TimeUnit.HOURS.toMillis(l3)) + " Minutes";
    }

    private static final class Commands
    extends Enum<Commands> {
        public static final /* enum */ Commands admin_server = new Commands();
        public static final /* enum */ Commands admin_server_info = new Commands();
        public static final /* enum */ Commands admin_check_actor = new Commands();
        public static final /* enum */ Commands admin_setvar = new Commands();
        public static final /* enum */ Commands admin_set_ai_interval = new Commands();
        public static final /* enum */ Commands admin_spawn2 = new Commands();
        private static final /* synthetic */ Commands[] a;

        public static Commands[] values() {
            return (Commands[])a.clone();
        }

        public static Commands valueOf(String string) {
            return Enum.valueOf(Commands.class, string);
        }

        private static /* synthetic */ Commands[] a() {
            return new Commands[]{admin_server, admin_server_info, admin_check_actor, admin_setvar, admin_set_ai_interval, admin_spawn2};
        }

        static {
            a = Commands.a();
        }
    }
}
