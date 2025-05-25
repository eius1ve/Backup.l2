/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.matching;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import l2.gameserver.instancemanager.MatchingRoomManager;
import l2.gameserver.listener.actor.player.OnPlayerPartyInviteListener;
import l2.gameserver.listener.actor.player.OnPlayerPartyLeaveListener;
import l2.gameserver.model.Player;
import l2.gameserver.model.PlayerGroup;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.SystemMessage;

public abstract class MatchingRoom
implements PlayerGroup {
    public static int PARTY_MATCHING = 0;
    public static int CC_MATCHING = 1;
    public static int WAIT_PLAYER = 0;
    public static int ROOM_MASTER = 1;
    public static int PARTY_MEMBER = 2;
    public static int UNION_LEADER = 3;
    public static int UNION_PARTY = 4;
    public static int WAIT_PARTY = 5;
    public static int WAIT_NORMAL = 6;
    private final int oM;
    private int b;
    private int c;
    private int oN;
    private int oO;
    private String dI;
    private final PartyListenerImpl a = new PartyListenerImpl();
    protected final Player _leader;
    protected Set<Player> _members = new CopyOnWriteArraySet<Player>();

    public MatchingRoom(Player player, int n, int n2, int n3, int n4, String string) {
        this._leader = player;
        this.oM = MatchingRoomManager.getInstance().addMatchingRoom(this);
        this.b = n;
        this.c = n2;
        this.oN = n3;
        this.oO = n4;
        this.dI = string;
        this.a(player, null);
    }

    public boolean addMember(Player player) {
        if (this._members.contains(player)) {
            return true;
        }
        if (player.getLevel() < this.getMinLevel() || player.getLevel() > this.getMaxLevel() || this.getPlayers().size() >= this.getMaxMembersSize()) {
            player.sendPacket((IStaticPacket)this.notValidMessage());
            return false;
        }
        return this.a(player, (L2GameServerPacket)new SystemMessage(this.enterMessage()).addName(player));
    }

    private boolean a(Player player, L2GameServerPacket l2GameServerPacket) {
        if (!this._members.isEmpty()) {
            player.addListener(this.a);
        }
        this._members.add(player);
        player.setMatchingRoom(this);
        for (Player player2 : this) {
            if (player2 == player) continue;
            player2.sendPacket(l2GameServerPacket, this.addMemberPacket(player2, player));
        }
        MatchingRoomManager.getInstance().removeFromWaitingList(player);
        player.sendPacket(this.infoRoomPacket(), this.membersPacket(player));
        player.sendChanges();
        return true;
    }

    public void removeMember(Player player, boolean bl) {
        if (!this._members.remove(player)) {
            return;
        }
        player.removeListener(this.a);
        player.setMatchingRoom(null);
        if (this._members.isEmpty()) {
            this.disband();
        } else {
            L2GameServerPacket l2GameServerPacket = this.infoRoomPacket();
            SystemMsg systemMsg = this.exitMessage(true, bl);
            Object var5_5 = systemMsg != null ? new SystemMessage(systemMsg).addName(player) : null;
            for (Player player2 : this) {
                player2.sendPacket(l2GameServerPacket, this.removeMemberPacket(player2, player), var5_5);
            }
        }
        player.sendPacket(this.closeRoomPacket(), this.exitMessage(false, bl));
        MatchingRoomManager.getInstance().addToWaitingList(player);
        player.sendChanges();
    }

    public void broadcastPlayerUpdate(Player player) {
        for (Player player2 : this) {
            player2.sendPacket((IStaticPacket)this.updateMemberPacket(player2, player));
        }
    }

    public void disband() {
        for (Player player : this) {
            player.removeListener(this.a);
            player.sendPacket((IStaticPacket)this.closeRoomMessage());
            player.sendPacket((IStaticPacket)this.closeRoomPacket());
            player.setMatchingRoom(null);
            player.sendChanges();
            MatchingRoomManager.getInstance().addToWaitingList(player);
        }
        this._members.clear();
        MatchingRoomManager.getInstance().removeMatchingRoom(this);
    }

    public abstract SystemMsg notValidMessage();

    public abstract SystemMsg enterMessage();

    public abstract SystemMsg exitMessage(boolean var1, boolean var2);

    public abstract SystemMsg closeRoomMessage();

    public abstract L2GameServerPacket closeRoomPacket();

    public abstract L2GameServerPacket infoRoomPacket();

    public abstract L2GameServerPacket addMemberPacket(Player var1, Player var2);

    public abstract L2GameServerPacket removeMemberPacket(Player var1, Player var2);

    public abstract L2GameServerPacket updateMemberPacket(Player var1, Player var2);

    public abstract L2GameServerPacket membersPacket(Player var1);

    public abstract int getType();

    public abstract int getMemberType(Player var1);

    @Override
    public void broadCast(IStaticPacket ... iStaticPacketArray) {
        for (Player player : this) {
            player.sendPacket(iStaticPacketArray);
        }
    }

    public int getId() {
        return this.oM;
    }

    public int getMinLevel() {
        return this.b;
    }

    public int getMaxLevel() {
        return this.c;
    }

    public String getTopic() {
        return this.dI;
    }

    public int getMaxMembersSize() {
        return this.oN;
    }

    public int getLocationId() {
        return MatchingRoomManager.getInstance().getLocation(this._leader);
    }

    public Player getLeader() {
        return this._leader;
    }

    public Collection<Player> getPlayers() {
        return this._members;
    }

    public int getLootType() {
        return this.oO;
    }

    @Override
    public Iterator<Player> iterator() {
        return this._members.iterator();
    }

    public void setMinLevel(int n) {
        this.b = n;
    }

    public void setMaxLevel(int n) {
        this.c = n;
    }

    public void setTopic(String string) {
        this.dI = string;
    }

    public void setMaxMemberSize(int n) {
        this.oN = n;
    }

    public void setLootType(int n) {
        this.oO = n;
    }

    private class PartyListenerImpl
    implements OnPlayerPartyInviteListener,
    OnPlayerPartyLeaveListener {
        private PartyListenerImpl() {
        }

        @Override
        public void onPartyInvite(Player player) {
            MatchingRoom.this.broadcastPlayerUpdate(player);
        }

        @Override
        public void onPartyLeave(Player player) {
            MatchingRoom.this.broadcastPlayerUpdate(player);
        }
    }
}
