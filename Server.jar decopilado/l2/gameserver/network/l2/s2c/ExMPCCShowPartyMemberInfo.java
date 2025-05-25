/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.ArrayList;
import java.util.List;
import l2.gameserver.model.Party;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExMPCCShowPartyMemberInfo
extends L2GameServerPacket {
    private List<PartyMemberInfo> cg = new ArrayList<PartyMemberInfo>();

    public ExMPCCShowPartyMemberInfo(Party party) {
        for (Player player : party.getPartyMembers()) {
            this.cg.add(new PartyMemberInfo(player.getName(), player.getObjectId(), player.getClassId().getId()));
        }
    }

    @Override
    protected final void writeImpl() {
        this.writeEx(76);
        this.writeD(this.cg.size());
        for (PartyMemberInfo partyMemberInfo : this.cg) {
            this.writeS(partyMemberInfo.name);
            this.writeD(partyMemberInfo.object_id);
            this.writeD(partyMemberInfo.class_id);
        }
        this.cg.clear();
    }

    static class PartyMemberInfo {
        public String name;
        public int object_id;
        public int class_id;

        public PartyMemberInfo(String string, int n, int n2) {
            this.name = string;
            this.object_id = n;
            this.class_id = n2;
        }
    }
}
