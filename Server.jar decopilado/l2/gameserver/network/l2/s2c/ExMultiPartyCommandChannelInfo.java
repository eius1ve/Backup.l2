/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.ArrayList;
import java.util.List;
import l2.gameserver.model.CommandChannel;
import l2.gameserver.model.Party;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExMultiPartyCommandChannelInfo
extends L2GameServerPacket {
    private String eW;
    private int MemberCount;
    private List<ChannelPartyInfo> ck;

    public ExMultiPartyCommandChannelInfo(CommandChannel commandChannel) {
        this.eW = commandChannel.getChannelLeader().getName();
        this.MemberCount = commandChannel.getMemberCount();
        this.ck = new ArrayList<ChannelPartyInfo>();
        for (Party party : commandChannel.getParties()) {
            Player player = party.getPartyLeader();
            if (player == null) continue;
            this.ck.add(new ChannelPartyInfo(player.getName(), player.getObjectId(), party.getMemberCount()));
        }
    }

    @Override
    protected void writeImpl() {
        this.writeEx(49);
        this.writeS(this.eW);
        this.writeD(0);
        this.writeD(this.MemberCount);
        this.writeD(this.ck.size());
        for (ChannelPartyInfo channelPartyInfo : this.ck) {
            this.writeS(channelPartyInfo.Leader_name);
            this.writeD(channelPartyInfo.Leader_obj_id);
            this.writeD(channelPartyInfo.MemberCount);
        }
    }

    static class ChannelPartyInfo {
        public String Leader_name;
        public int Leader_obj_id;
        public int MemberCount;

        public ChannelPartyInfo(String string, int n, int n2) {
            this.Leader_name = string;
            this.Leader_obj_id = n;
            this.MemberCount = n2;
        }
    }
}
