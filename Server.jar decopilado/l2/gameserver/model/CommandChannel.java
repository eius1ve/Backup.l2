/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import l2.commons.collections.JoinedIterator;
import l2.gameserver.Config;
import l2.gameserver.model.Party;
import l2.gameserver.model.Player;
import l2.gameserver.model.PlayerGroup;
import l2.gameserver.model.entity.Reflection;
import l2.gameserver.model.instances.NpcFriendInstance;
import l2.gameserver.model.matching.MatchingRoom;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExMPCCClose;
import l2.gameserver.network.l2.s2c.ExMPCCOpen;
import l2.gameserver.network.l2.s2c.ExMPCCPartyInfoUpdate;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.SystemMessage;

public class CommandChannel
implements PlayerGroup {
    public static final int STRATEGY_GUIDE_ID = 8871;
    public static final int CLAN_IMPERIUM_ID = 391;
    private final List<Party> aL = new CopyOnWriteArrayList<Party>();
    private Player d;
    private int gp;
    private Reflection _reflection;
    private MatchingRoom a;

    public CommandChannel(Player player) {
        this.d = player;
        this.aL.add(player.getParty());
        this.gp = player.getParty().getLevel();
        player.getParty().setCommandChannel(this);
        this.broadCast(ExMPCCOpen.STATIC);
    }

    public void addParty(Party party) {
        this.broadCast(new ExMPCCPartyInfoUpdate(party, 1));
        this.aL.add(party);
        this.aS();
        party.setCommandChannel(this);
        for (Player player : party) {
            player.sendPacket((IStaticPacket)ExMPCCOpen.STATIC);
            if (this.a == null) continue;
            this.a.broadcastPlayerUpdate(player);
        }
    }

    public void removeParty(Party party) {
        this.aL.remove(party);
        this.aS();
        party.setCommandChannel(null);
        party.broadCast(ExMPCCClose.STATIC);
        Reflection reflection = this.getReflection();
        if (reflection != null) {
            for (Player player : party.getPartyMembers()) {
                player.teleToLocation(reflection.getReturnLoc(), 0);
            }
        }
        if (this.aL.size() < 2) {
            this.disbandChannel();
        } else {
            for (Player player : party) {
                player.sendPacket((IStaticPacket)new ExMPCCPartyInfoUpdate(party, 0));
                if (this.a == null) continue;
                this.a.broadcastPlayerUpdate(player);
            }
        }
    }

    public void disbandChannel() {
        this.broadCast(SystemMsg.THE_COMMAND_CHANNEL_HAS_BEEN_DISBANDED);
        for (Party party : this.aL) {
            party.setCommandChannel(null);
            party.broadCast(ExMPCCClose.STATIC);
            if (!this.isInReflection()) continue;
            party.broadCast(new IStaticPacket[]{new SystemMessage(SystemMsg.THIS_DUNGEON_WILL_EXPIRE_IN_S1_MINUTES).addNumber(1)});
        }
        Reflection reflection = this.getReflection();
        if (reflection != null) {
            reflection.startCollapseTimer(60000L);
            this.setReflection(null);
        }
        if (this.a != null) {
            this.a.disband();
        }
        this.aL.clear();
        this.d = null;
    }

    public int getMemberCount() {
        int n = 0;
        for (Party party : this.aL) {
            n += party.getMemberCount();
        }
        return n;
    }

    @Override
    public void broadCast(IStaticPacket ... iStaticPacketArray) {
        for (Party party : this.aL) {
            party.broadCast(iStaticPacketArray);
        }
    }

    public void broadcastToChannelPartyLeaders(L2GameServerPacket l2GameServerPacket) {
        for (Party party : this.aL) {
            Player player = party.getPartyLeader();
            if (player == null) continue;
            player.sendPacket((IStaticPacket)l2GameServerPacket);
        }
    }

    public List<Party> getParties() {
        return this.aL;
    }

    @Deprecated
    public List<Player> getMembers() {
        ArrayList<Player> arrayList = new ArrayList<Player>(this.aL.size());
        for (Party party : this.getParties()) {
            arrayList.addAll(party.getPartyMembers());
        }
        return arrayList;
    }

    @Override
    public Iterator<Player> iterator() {
        ArrayList<Iterator<Iterator<Player>>> arrayList = new ArrayList<Iterator<Iterator<Player>>>(this.aL.size());
        for (Party party : this.getParties()) {
            arrayList.add(party.getPartyMembers().iterator());
        }
        return new JoinedIterator<Player>(arrayList);
    }

    public int getLevel() {
        return this.gp;
    }

    public void setChannelLeader(Player player) {
        this.d = player;
        this.broadCast(new IStaticPacket[]{new SystemMessage(SystemMsg.COMMAND_CHANNEL_AUTHORITY_HAS_BEEN_TRANSFERRED_TO_C1).addString(player.getName())});
    }

    public Player getChannelLeader() {
        return this.d;
    }

    public boolean meetRaidWarCondition(NpcFriendInstance npcFriendInstance) {
        if (!npcFriendInstance.isRaid()) {
            return false;
        }
        int n = npcFriendInstance.getNpcId();
        switch (n) {
            case 29001: 
            case 29006: 
            case 29014: 
            case 29022: {
                return this.getMemberCount() > 36;
            }
            case 29020: {
                return this.getMemberCount() > 56;
            }
            case 29019: {
                return this.getMemberCount() > 225;
            }
            case 29028: {
                return this.getMemberCount() > 99;
            }
        }
        return this.getMemberCount() > 18;
    }

    private void aS() {
        this.gp = 0;
        for (Party party : this.aL) {
            if (party.getLevel() <= this.gp) continue;
            this.gp = party.getLevel();
        }
    }

    public boolean isInReflection() {
        return this._reflection != null;
    }

    public void setReflection(Reflection reflection) {
        this._reflection = reflection;
    }

    public Reflection getReflection() {
        return this._reflection;
    }

    public static boolean checkAuthority(Player player) {
        boolean bl;
        if (player.getClan() == null || !player.isInParty() || !player.getParty().isLeader(player) || Config.CHECK_CLAN_RANK_ON_COMMAND_CHANNEL_CREATE && player.getPledgeClass() < 4) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_THE_AUTHORITY_TO_USE_THE_COMMAND_CHANNEL);
            return false;
        }
        boolean bl2 = player.getSkillLevel(391) > 0;
        boolean bl3 = bl = player.getInventory().getItemByItemId(8871) != null;
        if (!bl2 && !bl) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_THE_AUTHORITY_TO_USE_THE_COMMAND_CHANNEL);
            return false;
        }
        return true;
    }

    public MatchingRoom getMatchingRoom() {
        return this.a;
    }

    public void setMatchingRoom(MatchingRoom matchingRoom) {
        this.a = matchingRoom;
    }
}
