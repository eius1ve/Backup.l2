/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.ArrayUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.network.l2.c2s;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import l2.commons.lang.ArrayUtils;
import l2.gameserver.Config;
import l2.gameserver.cache.ItemInfoCache;
import l2.gameserver.handler.voicecommands.IVoicedCommandHandler;
import l2.gameserver.handler.voicecommands.VoicedCommandHandler;
import l2.gameserver.instancemanager.PetitionManager;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.model.World;
import l2.gameserver.model.chat.ChatFilters;
import l2.gameserver.model.chat.chatfilter.ChatFilter;
import l2.gameserver.model.chat.chatfilter.ChatMsg;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.matching.MatchingRoom;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.ChatType;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExWorldChatCnt;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.Say2;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.utils.Log;
import l2.gameserver.utils.MapRegionUtils;
import l2.gameserver.utils.Strings;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Say2C
extends L2GameClientPacket {
    private static final Logger cW = LoggerFactory.getLogger(Say2C.class);
    private static final Pattern c = Pattern.compile("[\b]\tType=[0-9]+[\\s]+\tID=([0-9]+)[\\s]+\tColor=[0-9]+[\\s]+\tUnderline=[0-9]+[\\s]+\tClassID=[0-9]+[\\s]+\tTitle=\u001b(.[^\u001b]*)[^\b]");
    private static final Pattern d = Pattern.compile("[\b]\tType=[0-9]+(.[^\b]*)[\b]");
    private String eE;
    private ChatType b;
    private String ek;

    @Override
    protected void readImpl() {
        this.eE = this.readS(Config.CHAT_MESSAGE_MAX_LEN);
        this.b = ArrayUtils.valid(ChatType.VALUES, this.readD());
        this.ek = this.b == ChatType.TELL ? this.readS(Config.CNAME_MAXLEN) : null;
    }

    @Override
    protected void runImpl() {
        Object object;
        String string;
        Object object3;
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        if (this.b == null || this.eE == null || this.eE.length() == 0) {
            player.sendActionFailed();
            return;
        }
        this.eE = this.eE.replaceAll("\\\\n", "\n");
        if (this.eE.contains("\n")) {
            object3 = this.eE.split("\n");
            this.eE = "";
            for (int i = 0; i < ((String[])object3).length; ++i) {
                object3[i] = ((String)object3[i]).trim();
                if (((String)object3[i]).length() == 0) continue;
                if (this.eE.length() > 0) {
                    this.eE = this.eE + "\n  >";
                }
                this.eE = this.eE + (String)object3[i];
            }
        }
        if (this.eE.length() == 0) {
            player.sendActionFailed();
            return;
        }
        if (this.eE.startsWith(".")) {
            IVoicedCommandHandler iVoicedCommandHandler;
            object3 = this.eE.substring(1).trim();
            String string2 = ((String)object3).split("\\s+")[0];
            String string22 = ((String)object3).substring(string2.length()).trim();
            if (string2.length() > 0 && (iVoicedCommandHandler = VoicedCommandHandler.getInstance().getVoicedCommandHandler(string2)) != null) {
                iVoicedCommandHandler.useVoicedCommand(string2, player, string22);
                return;
            }
            return;
        }
        object3 = this.ek == null ? null : World.getPlayer(this.ek);
        long l = System.currentTimeMillis();
        if (!player.getPlayerAccess().CanAnnounce) {
            block24: for (ChatFilter object22 : ChatFilters.getinstance().getFilters()) {
                if (!object22.isMatch(player, this.b, this.eE, (Player)object3)) continue;
                switch (object22.getAction()) {
                    case 1: {
                        player.updateNoChannel((long)Integer.parseInt(object22.getValue()) * 1000L);
                        break block24;
                    }
                    case 2: {
                        player.sendMessage(new CustomMessage(object22.getValue(), player, new Object[0]));
                        return;
                    }
                    case 3: {
                        this.eE = object22.getValue();
                        break block24;
                    }
                    case 4: {
                        this.b = ChatType.valueOf(object22.getValue());
                    }
                    default: {
                        continue block24;
                    }
                }
            }
        }
        if (player.getNoChannel() > 0L && org.apache.commons.lang3.ArrayUtils.contains((Object[])Config.BAN_CHANNEL_LIST, (Object)((Object)this.b))) {
            if (player.getNoChannelRemained() > 0L) {
                long l2 = player.getNoChannelRemained() / 60000L;
                player.sendMessage(new CustomMessage("common.ChatBanned", player, new Object[0]).addNumber(l2));
                return;
            }
            player.updateNoChannel(0L);
        }
        if (this.eE.isEmpty()) {
            return;
        }
        Object object4 = c.matcher(this.eE);
        while (((Matcher)object4).find()) {
            int n = Integer.parseInt(((Matcher)object4).group(1));
            ItemInstance itemInstance = player.getInventory().getItemByObjectId(n);
            if (itemInstance == null) {
                player.sendActionFailed();
                break;
            }
            ItemInfoCache.getInstance().put(itemInstance);
        }
        if ((string = player.getVar("translit")) != null) {
            object4 = d.matcher(this.eE);
            object = new StringBuilder();
            int iterable = 0;
            while (((Matcher)object4).find()) {
                int n = iterable;
                iterable = ((Matcher)object4).start();
                ((StringBuilder)object).append(Strings.fromTranslit(this.eE.substring(n, iterable), string.equals("tl") ? 1 : 2));
                int n2 = iterable;
                iterable = ((Matcher)object4).end();
                ((StringBuilder)object).append(this.eE.substring(n2, iterable));
            }
            this.eE = ((StringBuilder)object).append(Strings.fromTranslit(this.eE.substring(iterable, this.eE.length()), string.equals("tl") ? 1 : 2)).toString();
        }
        object = new Say2(player.getObjectId(), this.b, player.getName(), this.eE);
        switch (this.b) {
            case TELL: {
                if (object3 == null) {
                    player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_IS_NOT_CURRENTLY_LOGGED_IN).addString(this.ek));
                    return;
                }
                if (((Player)object3).isInOfflineMode()) {
                    player.sendMessage(new CustomMessage("common.PlayerInOfflineTrade", player, new Object[0]));
                    return;
                }
                if (((Player)object3).getBlockList().isBlocked(player)) {
                    player.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_BEEN_BLOCKED_FROM_CHATTING_WITH_THAT_CONTACT);
                    return;
                }
                if (((Player)object3).getMessageRefusal()) {
                    player.sendPacket((IStaticPacket)SystemMsg.THAT_PERSON_IS_IN_MESSAGE_REFUSAL_MODE);
                    return;
                }
                if (player.canTalkWith((Player)object3)) {
                    ((Say2)object).setSenderInfo(player, (Player)object3);
                    ((Player)object3).sendPacket((IStaticPacket)object);
                }
                object = new Say2(player.getObjectId(), this.b, "->" + ((Creature)object3).getName(), this.eE);
                ((Say2)object).setSenderInfo(player, (Player)object3);
                player.sendPacket((IStaticPacket)object);
                break;
            }
            case SHOUT: {
                if (player.isCursedWeaponEquipped()) {
                    player.sendPacket((IStaticPacket)SystemMsg.SHOUT_AND_TRADE_CHATTING_CANNOT_BE_USED_WHILE_POSSESSING_A_CURSED_WEAPON);
                    return;
                }
                if (player.isInObserverMode()) {
                    player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_CHAT_WHILE_IN_OBSERVATION_MODE);
                    return;
                }
                if (Config.GLOBAL_SHOUT && player.getLevel() > Config.GLOBAL_SHOUT_MIN_LEVEL && player.getPvpKills() >= Config.GLOBAL_SHOUT_MIN_PVP_COUNT) {
                    Say2C.b(player, (Say2)object);
                } else {
                    Say2C.a(player, (Say2)object);
                }
                player.sendPacket((IStaticPacket)object);
                break;
            }
            case TRADE: {
                if (player.isCursedWeaponEquipped()) {
                    player.sendPacket((IStaticPacket)SystemMsg.SHOUT_AND_TRADE_CHATTING_CANNOT_BE_USED_WHILE_POSSESSING_A_CURSED_WEAPON);
                    return;
                }
                if (player.isInObserverMode()) {
                    player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_CHAT_WHILE_IN_OBSERVATION_MODE);
                    return;
                }
                if (Config.GLOBAL_TRADE_CHAT && player.getLevel() > Config.GLOBAL_TRADE_CHAT_MIN_LEVEL && player.getPvpKills() >= Config.GLOBAL_TRADE_MIN_PVP_COUNT) {
                    Say2C.b(player, (Say2)object);
                } else {
                    Say2C.a(player, (Say2)object);
                }
                player.sendPacket((IStaticPacket)object);
                break;
            }
            case ALL: {
                Iterable<Player> iterable;
                if (player.isCursedWeaponEquipped()) {
                    object = new Say2(player.getObjectId(), this.b, player.getTransformationName(), this.eE);
                }
                if ((iterable = Say2C.a(player, World.getAroundPlayers(player))) != null) {
                    for (Player player2 : iterable) {
                        if (player2 == player || player2.getReflection() != player.getReflection() || player2.getBlockList().isBlocked(player)) continue;
                        player2.sendPacket((IStaticPacket)object);
                    }
                }
                player.sendPacket((IStaticPacket)object);
                break;
            }
            case CLAN: {
                if (player.getClan() == null) break;
                player.getClan().broadcastToOnlineMembers(new L2GameServerPacket[]{object});
                break;
            }
            case ALLIANCE: {
                if (player.getClan() == null || player.getClan().getAlliance() == null) break;
                player.getClan().getAlliance().broadcastToOnlineMembers((L2GameServerPacket)object);
                break;
            }
            case PARTY: {
                if (!player.isInParty()) break;
                player.getParty().broadCast(new IStaticPacket[]{object});
                break;
            }
            case PARTY_ROOM: {
                MatchingRoom matchingRoom = player.getMatchingRoom();
                if (matchingRoom == null || matchingRoom.getType() != MatchingRoom.PARTY_MATCHING) break;
                matchingRoom.broadCast(new IStaticPacket[]{object});
                break;
            }
            case COMMANDCHANNEL_ALL: {
                if (!player.isInParty() || !player.getParty().isInCommandChannel()) {
                    player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_THE_AUTHORITY_TO_USE_THE_COMMAND_CHANNEL);
                    return;
                }
                if (player.getParty().getCommandChannel().getChannelLeader() == player) {
                    player.getParty().getCommandChannel().broadCast(new IStaticPacket[]{object});
                    break;
                }
                player.sendPacket((IStaticPacket)SystemMsg.ONLY_THE_COMMAND_CHANNEL_CREATOR_CAN_USE_THE_RAID_LEADER_TEXT);
                break;
            }
            case COMMANDCHANNEL_COMMANDER: {
                if (!player.isInParty() || !player.getParty().isInCommandChannel()) {
                    player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_THE_AUTHORITY_TO_USE_THE_COMMAND_CHANNEL);
                    return;
                }
                if (player.getParty().isLeader(player)) {
                    player.getParty().getCommandChannel().broadcastToChannelPartyLeaders((L2GameServerPacket)object);
                    break;
                }
                player.sendPacket((IStaticPacket)SystemMsg.ONLY_A_PARTY_LEADER_CAN_ACCESS_THE_COMMAND_CHANNEL);
                break;
            }
            case HERO_VOICE: {
                if (!player.isHero() && !player.getPlayerAccess().CanAnnounce && (player.getPvpKills() < Config.PVP_COUNT_TO_CHAT || !Config.PVP_HERO_CHAT_SYSTEM)) break;
                for (Player player4 : Say2C.a(player, GameObjectsStorage.getAllPlayersForIterate())) {
                    if (player4.getBlockList().isBlocked(player)) continue;
                    player4.sendPacket((IStaticPacket)object);
                }
                break;
            }
            case PETITION_PLAYER: 
            case PETITION_GM: {
                if (!PetitionManager.getInstance().isPlayerInConsultation(player)) {
                    player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_ARE_CURRENTLY_NOT_IN_A_PETITION_CHAT));
                    return;
                }
                PetitionManager.getInstance().sendActivePetitionMessage(player, this.eE);
                break;
            }
            case BATTLEFIELD: {
                if (player.getBattlefieldChatId() == 0) {
                    return;
                }
                for (Player l4 : GameObjectsStorage.getAllPlayersForIterate()) {
                    if (l4.getBlockList().isBlocked(player) || l4.getBattlefieldChatId() != player.getBattlefieldChatId()) continue;
                    l4.sendPacket((IStaticPacket)object);
                }
                break;
            }
            case MPCC_ROOM: {
                MatchingRoom matchingRoom = player.getMatchingRoom();
                if (matchingRoom == null || matchingRoom.getType() != MatchingRoom.CC_MATCHING) break;
                matchingRoom.broadCast(new IStaticPacket[]{object});
                break;
            }
            case WORLD: {
                long l2;
                if (!Config.ENABLE_WORLD_CHAT) {
                    return;
                }
                if (player.getLevel() < Config.WORLD_CHAT_MIN_LEVEL && !player.isGM()) {
                    player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_CAN_USE_WORLD_CHAT_FROM_LV_S1).addNumber(Config.WORLD_CHAT_MIN_LEVEL));
                    return;
                }
                if (!player.hasBonus() && Config.WORLD_CHAT_FOR_PREMIUM_ONLY && !player.isGM()) {
                    player.sendMessage(new CustomMessage("common.WorldChatPremiumOnly", player, new Object[0]));
                    return;
                }
                if (player.isCursedWeaponEquipped()) {
                    player.sendPacket((IStaticPacket)SystemMsg.SHOUT_AND_TRADE_CHATTING_CANNOT_BE_USED_WHILE_POSSESSING_A_CURSED_WEAPON);
                    return;
                }
                if (player.isInObserverMode()) {
                    player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_CHAT_WHILE_IN_OBSERVATION_MODE);
                    return;
                }
                long l3 = System.currentTimeMillis();
                if (l3 - (l2 = player.getVarLong("world_chat_reuse", 0L)) < Config.WORLD_CHAT_INTERVAL && !player.isGM()) {
                    SystemMessage n = new SystemMessage(SystemMsg.YOU_HAVE_S1_SEC_UNTIL_YOU_ARE_ABLE_TO_USE_WORLD_CHAT);
                    n.addNumber(TimeUnit.MILLISECONDS.toSeconds(Config.WORLD_CHAT_INTERVAL - (l3 - l2)));
                    player.sendPacket((IStaticPacket)n);
                    return;
                }
                int n = player.getVarInt("used_world_chat_points", 0) + 1;
                int n3 = Config.WORLD_CHAT_MESSAGE_COUNT + player.getWorldChatBonus() - n;
                if (n3 < 0) {
                    player.sendPacket((IStaticPacket)SystemMsg.TODAY_YOU_REACHED_THE_LIMIT_OF_USE_OF_THE_WORLD_CHAT__RESET_OF_THE_WORLD_USE_CHAT_IS_DONE_DAILY_AT_6_30_AM);
                    return;
                }
                for (Player player3 : GameObjectsStorage.getAllPlayersForIterate()) {
                    if (player3.getBlockList().isBlocked(player)) continue;
                    player3.sendPacket((IStaticPacket)object);
                }
                player.setVar("used_world_chat_points", n, -1L);
                player.setVar("world_chat_reuse", l3, -1L);
                player.sendPacket((IStaticPacket)new ExWorldChatCnt(n3));
                break;
            }
            default: {
                cW.warn("Character " + player.getName() + " used unknown chat type: " + this.b.ordinal() + ".");
            }
        }
        Log.LogChat(this.b.name(), player.getName(), this.ek, this.eE, 0);
        player.getMessageBucket().addLast(new ChatMsg(this.b, object3 == null ? 0 : ((GameObject)object3).getObjectId(), this.eE.hashCode(), (int)(l / 1000L)));
        player.getListeners().onSay(this.b, this.ek, this.eE);
    }

    private static Iterable<Player> a(Player player, Iterable<Player> iterable) {
        if (player.getAntiSpam() == 0L || player.getAntiSpamRemained() == 0L) {
            return iterable;
        }
        if (Objects.isNull(player.getNetConnection())) {
            return Collections.emptyList();
        }
        LinkedList<Player> linkedList = new LinkedList<Player>();
        for (Player player2 : iterable) {
            if (player2 == null || player2.getNetConnection() == null || (!Config.NOSPAM_FILTER_IP || !StringUtils.isNotBlank((CharSequence)player.getNetConnection().getIpAddr()) || !Objects.equals(player.getNetConnection().getIpAddr(), player2.getNetConnection().getIpAddr())) && (!Config.NOSPAM_FILTER_HWID || !StringUtils.isNotBlank((CharSequence)player.getNetConnection().getHwid()) || !Objects.equals(player.getNetConnection().getHwid(), player2.getNetConnection().getHwid()))) continue;
            linkedList.add(player2);
        }
        return linkedList;
    }

    private static void a(Player player, Say2 say2) {
        int n = MapRegionUtils.regionX(player);
        int n2 = MapRegionUtils.regionY(player);
        int n3 = Config.SHOUT_OFFSET;
        for (Player player2 : Say2C.a(player, GameObjectsStorage.getAllPlayersForIterate())) {
            if (player2 == player || player.getReflection() != player2.getReflection() || player2.getBlockList().isBlocked(player)) continue;
            int n4 = MapRegionUtils.regionX(player2);
            int n5 = MapRegionUtils.regionY(player2);
            if ((n4 < n - n3 || n4 > n + n3 || n5 < n2 - n3 || n5 > n2 + n3) && !player.isInRangeZ(player2, (long)Config.CHAT_RANGE)) continue;
            player2.sendPacket((IStaticPacket)say2);
        }
    }

    private static void b(Player player, Say2 say2) {
        for (Player player2 : Say2C.a(player, GameObjectsStorage.getAllPlayersForIterate())) {
            if (player2 == player || player.getReflection() != player2.getReflection() || player2.getBlockList().isBlocked(player)) continue;
            player2.sendPacket((IStaticPacket)say2);
        }
    }
}
