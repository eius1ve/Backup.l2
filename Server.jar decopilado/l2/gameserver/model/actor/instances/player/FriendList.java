/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 */
package l2.gameserver.model.actor.instances.player;

import java.util.Collections;
import java.util.Map;
import l2.gameserver.dao.CharacterFriendDAO;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.model.World;
import l2.gameserver.model.actor.instances.player.Friend;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExFriendDetailInfo;
import l2.gameserver.network.l2.s2c.FriendRemove;
import l2.gameserver.network.l2.s2c.FriendStatus;
import l2.gameserver.network.l2.s2c.L2Friend;
import l2.gameserver.network.l2.s2c.SystemMessage;
import org.apache.commons.lang3.StringUtils;

public class FriendList {
    private Map<Integer, Friend> aP = Collections.emptyMap();
    private final Player h;

    public FriendList(Player player) {
        this.h = player;
    }

    public void restore() {
        this.aP = CharacterFriendDAO.getInstance().select(this.h);
    }

    public void removeFriend(String string) {
        if (StringUtils.isEmpty((CharSequence)string)) {
            return;
        }
        int n = this.a(string);
        if (n > 0) {
            Player player = World.getPlayer(n);
            this.h.sendPacket(new IStaticPacket[]{new SystemMessage(SystemMsg.S1_HAS_BEEN_REMOVED_FROM_YOUR_FRIENDS_LIST).addString(string), new L2Friend(string, false, player != null, n)});
            this.h.sendPacket((IStaticPacket)new FriendRemove(string, 1));
            if (player != null) {
                player.sendPacket(new IStaticPacket[]{new SystemMessage(SystemMsg.S1_HAS_BEEN_DELETED_FROM_YOUR_FRIENDS_LIST).addName(this.h), new L2Friend(this.h, false)});
                player.sendPacket((IStaticPacket)new FriendRemove(this.h.getName(), 1));
            }
        } else {
            this.h.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.C1_IS_NOT_ON_YOUR_FRIEND_LIST).addString(string));
        }
    }

    public void notifyFriends(boolean bl) {
        for (Friend friend : this.aP.values()) {
            Friend friend2;
            Player player = GameObjectsStorage.getPlayer(friend.getObjectId());
            if (player == null || (friend2 = player.getFriendList().getList().get(this.h.getObjectId())) == null) continue;
            friend2.update(this.h, bl);
            if (bl) {
                player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOUR_FRIEND_S1_JUST_LOGGED_IN).addName(this.h));
            }
            if (bl) {
                player.sendPacket((IStaticPacket)new FriendStatus(this.h, 1, this.h.getObjectId()));
            } else {
                player.sendPacket((IStaticPacket)new FriendStatus(this.h, 0, this.h.getObjectId()));
            }
            friend.update(player, bl);
        }
    }

    public void addFriend(Player player) {
        this.aP.put(player.getObjectId(), new Friend(player));
        CharacterFriendDAO.getInstance().insert(this.h, player);
    }

    private int a(String string) {
        if (string == null) {
            return 0;
        }
        Integer n = 0;
        for (Map.Entry<Integer, Friend> entry : this.aP.entrySet()) {
            if (!string.equalsIgnoreCase(entry.getValue().getName())) continue;
            n = entry.getKey();
            break;
        }
        if (n > 0) {
            this.aP.remove(n);
            CharacterFriendDAO.getInstance().delete(this.h, n);
            return n;
        }
        return 0;
    }

    public Friend get(String string) {
        if (StringUtils.isEmpty((CharSequence)string)) {
            return null;
        }
        for (Friend friend : this.aP.values()) {
            if (!string.equalsIgnoreCase(friend.getName())) continue;
            return friend;
        }
        return null;
    }

    public void updateMemo(String string, String string2) {
        if (string2 == null) {
            return;
        }
        if ((string2 = string2.trim()).length() > 50) {
            return;
        }
        Friend friend = this.get(string);
        if (friend == null) {
            return;
        }
        if (string2.equals(friend.getMemo())) {
            return;
        }
        friend.setMemo(string2);
        this.h.sendPacket((IStaticPacket)new ExFriendDetailInfo(this.h.getObjectId(), friend));
        CharacterFriendDAO.getInstance().updateMemo(this.h, friend.getObjectId(), string2);
    }

    public boolean contains(int n) {
        return this.aP.containsKey(n);
    }

    public Map<Integer, Friend> getList() {
        return this.aP;
    }

    public String toString() {
        return "FriendList[owner=" + this.h.getName() + "]";
    }
}
