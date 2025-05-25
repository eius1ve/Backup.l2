/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.listener.Listener
 *  l2.gameserver.Config
 *  l2.gameserver.dao.CharacterVariablesDAO
 *  l2.gameserver.data.xml.holder.ItemHolder
 *  l2.gameserver.handler.voicecommands.IVoicedCommandHandler
 *  l2.gameserver.handler.voicecommands.VoicedCommandHandler
 *  l2.gameserver.listener.actor.player.OnPlayerEnterListener
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.actor.listener.PlayerListenerList
 *  l2.gameserver.network.l2.GameClient
 *  l2.gameserver.network.l2.c2s.ScriptExPacket
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.SystemMessage
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.templates.item.ItemTemplate
 *  l2.gameserver.utils.ItemFunctions
 *  org.apache.commons.lang3.ArrayUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.lang3.tuple.Pair
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package events.Attendance;

import events.Attendance.ExVipAttendanceItemListPacket;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import l2.commons.listener.Listener;
import l2.gameserver.Config;
import l2.gameserver.dao.CharacterVariablesDAO;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.handler.voicecommands.IVoicedCommandHandler;
import l2.gameserver.handler.voicecommands.VoicedCommandHandler;
import l2.gameserver.listener.actor.player.OnPlayerEnterListener;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.actor.listener.PlayerListenerList;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.ScriptExPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.utils.ItemFunctions;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Attendance
extends Functions
implements IVoicedCommandHandler,
OnPlayerEnterListener,
ScriptFile {
    private static final Logger i = LoggerFactory.getLogger(Attendance.class);
    private static final String u = "Attendance";
    private static final String v = "Attendance_MRChkDay";
    private static final String w = "Attendance_MRChkTS";
    private static boolean R = false;
    private final String[] o = new String[]{"attendance"};

    private static boolean isActive() {
        return Attendance.IsActive((String)u);
    }

    private static long a(long l) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(l);
        calendar.set(13, 0);
        calendar.set(12, Config.EVENT_Attendance_ResetTime % 100);
        calendar.set(11, Config.EVENT_Attendance_ResetTime / 100);
        if (calendar.getTimeInMillis() < l) {
            calendar.add(6, 1);
        }
        return calendar.getTimeInMillis();
    }

    public void startEvent() {
        Player player = this.getSelf();
        if (!player.getPlayerAccess().IsEventGm) {
            return;
        }
        if (Attendance.SetActive((String)u, (boolean)true)) {
            if (!R) {
                PlayerListenerList.addGlobal((Listener)this);
            }
            player.sendMessage("Event 'Attendance' started.");
        } else {
            player.sendMessage("Event 'Attendance' already started.");
        }
        R = true;
        this.show("admin/events/events.htm", player);
    }

    public void stopEvent() {
        Player player = this.getSelf();
        if (!player.getPlayerAccess().IsEventGm) {
            return;
        }
        if (Attendance.SetActive((String)u, (boolean)false)) {
            if (R) {
                PlayerListenerList.removeGlobal((Listener)this);
            }
            System.out.println("Event: 'Attendance' stopped.");
        } else {
            player.sendMessage("Event: 'Attendance' not started.");
        }
        R = false;
        this.show("admin/events/events.htm", player);
    }

    private int a(Player player) {
        Map map = player.getAccountChars();
        int n = player.getVarInt(v, 0);
        if (Config.EVENT_Attendance_Global) {
            Iterator iterator = map.keySet().iterator();
            while (iterator.hasNext()) {
                int n2;
                int n3 = (Integer)iterator.next();
                String string = CharacterVariablesDAO.getVarForPlayer((int)n3, (String)v);
                if (StringUtils.isEmpty((CharSequence)string) || (n2 = Integer.parseInt(string)) <= n) continue;
                n = n2;
            }
        }
        return n;
    }

    private void a(Player player, int n) {
        player.setVar(v, n, -1L);
    }

    private long a(Player player) {
        Map map = player.getAccountChars();
        long l = player.getVarLong(w, 0L);
        if (Config.EVENT_Attendance_Global) {
            Iterator iterator = map.keySet().iterator();
            while (iterator.hasNext()) {
                long l2;
                int n = (Integer)iterator.next();
                String string = CharacterVariablesDAO.getVarForPlayer((int)n, (String)w);
                if (StringUtils.isEmpty((CharSequence)string) || (l2 = (long)Integer.parseInt(string)) <= l) continue;
                l = l2;
            }
        }
        return l * 1000L;
    }

    private void a(Player player, long l) {
        player.setVar(w, l / 1000L, -1L);
    }

    public void onPlayerEnter(Player player) {
        if (!Attendance.isActive() || !Config.EVENT_Attendance_OnEnterWorld) {
            return;
        }
        if (player.getLevel() >= Config.EVENT_Attendance_MinLevel) {
            this.j(player);
        }
    }

    public void onLoad() {
        if (Attendance.isActive()) {
            PlayerListenerList.addGlobal((Listener)this);
            R = true;
            i.info("Loaded Event: 'Attendance' [state: activated]");
            if (Config.EVENT_Attendance_Voice_Command) {
                VoicedCommandHandler.getInstance().registerVoicedCommandHandler((IVoicedCommandHandler)this);
            }
        } else {
            i.info("Loaded Event: 'Attendance' [state: deactivated]");
        }
    }

    public void onReload() {
    }

    public void onShutdown() {
        if (R) {
            PlayerListenerList.removeGlobal((Listener)this);
            R = false;
        }
    }

    private boolean a(Player player) {
        List list;
        List list2 = list = player.hasBonus() ? Config.EVENT_Attendance_Rewards_For_Premium : Config.EVENT_Attendance_Rewards;
        if (player.getLevel() < Config.EVENT_Attendance_MinLevel) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.ONLY_CHARACTERS_OF_LEVEL_S1_OR_HIGHER_ARE_ELIGIBLE_FOR_REWARDS).addNumber(Config.EVENT_Attendance_MinLevel));
            return false;
        }
        long l = System.currentTimeMillis();
        long l2 = l - player.getOnlineBeginTime();
        int n = (int)TimeUnit.MILLISECONDS.toMinutes(Math.max(0L, TimeUnit.MINUTES.toMillis(Config.EVENT_Attendance_InGameTime) - l2));
        if (n > 0) {
            player.sendPacket((IStaticPacket)((SystemMessage)new SystemMessage(SystemMsg.YOU_CAN_REDEEM_YOUR_REWARD_S1_MIN__AFTER_LOGGING_IN__YOU_HAVE_S2_MIN_LEFT).addNumber(Config.EVENT_Attendance_InGameTime)).addNumber(n));
            return false;
        }
        int n2 = this.a(player);
        long l3 = this.a(player);
        if (Attendance.a(l3) > l) {
            return false;
        }
        if (!Config.EVENT_Attendance_Looped && n2 >= list.size()) {
            return false;
        }
        int n3 = n2 % list.size();
        Pair pair = (Pair)list.get(n3);
        ItemTemplate itemTemplate = ItemHolder.getInstance().getTemplate(((Integer)pair.getLeft()).intValue());
        if (!ItemFunctions.canAddItem((Player)player, (ItemTemplate)itemTemplate, (long)((Long)pair.getRight()))) {
            return false;
        }
        this.a(player, l);
        this.a(player, n2 + 1);
        ItemFunctions.addItem((Playable)player, (ItemTemplate)itemTemplate, (long)((Long)pair.getRight()), (boolean)true);
        return true;
    }

    public void OnReceiveExPacket_0x0106(ScriptExPacket scriptExPacket) {
        if (!Attendance.isActive()) {
            return;
        }
        GameClient gameClient = (GameClient)scriptExPacket.getClient();
        Player player = gameClient.getActiveChar();
        if (player == null) {
            return;
        }
        this.j(player);
    }

    private void j(Player player) {
        List list = player.hasBonus() ? Config.EVENT_Attendance_Rewards_For_Premium : Config.EVENT_Attendance_Rewards;
        int n = this.a(player) % list.size();
        if (Config.EVENT_Attendance_Looped) {
            n %= list.size();
        }
        if (n >= list.size()) {
            return;
        }
        long l = this.a(player);
        int n2 = n;
        boolean bl = false;
        long l2 = System.currentTimeMillis();
        if (Attendance.a(l) < l2) {
            bl = true;
            ++n2;
        }
        ExVipAttendanceItemListPacket exVipAttendanceItemListPacket = new ExVipAttendanceItemListPacket(n2, n, 0, bl, Config.EVENT_Attendance_MinLevel);
        int n3 = list.size();
        for (int i = 0; i < n3; ++i) {
            Pair pair = (Pair)list.get(i);
            boolean bl2 = ArrayUtils.contains((int[])Config.EVENT_Attendance_Highlights, (int)(i + 1));
            exVipAttendanceItemListPacket = exVipAttendanceItemListPacket.addItem((Integer)pair.getLeft(), (Long)pair.getRight(), 1, bl2 ? 1 : 0);
        }
        player.sendPacket((IStaticPacket)exVipAttendanceItemListPacket);
    }

    public void OnReceiveExPacket_0x0107(ScriptExPacket scriptExPacket) {
        if (!Attendance.isActive()) {
            return;
        }
        GameClient gameClient = (GameClient)scriptExPacket.getClient();
        Player player = gameClient.getActiveChar();
        if (player == null) {
            return;
        }
        this.a(player);
        this.j(player);
        this.j(player);
    }

    public boolean useVoicedCommand(String string, Player player, String string2) {
        if (!Attendance.isActive()) {
            return false;
        }
        if (player == null) {
            return false;
        }
        this.j(player);
        return true;
    }

    public String[] getVoicedCommandList() {
        return this.o;
    }
}
