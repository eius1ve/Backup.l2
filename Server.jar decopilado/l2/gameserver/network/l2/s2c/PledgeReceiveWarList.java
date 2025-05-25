/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class PledgeReceiveWarList
extends L2GameServerPacket {
    private List<WarInfo> cF = new ArrayList<WarInfo>();
    private int _type;

    public PledgeReceiveWarList(Clan clan, int n, int n2) {
        this._type = n;
        List<Clan> list = clan.getAttackerClans();
        List<Clan> list2 = clan.getEnemyClans();
        HashSet<Clan> hashSet = new HashSet<Clan>();
        hashSet.addAll(list);
        hashSet.addAll(list2);
        for (Clan clan2 : hashSet) {
            int n3 = 0;
            if (list.contains(clan2) && list2.contains(clan2)) {
                n3 = 2;
            }
            this.cF.add(new WarInfo(clan2.getName(), n3));
        }
    }

    @Override
    protected final void writeImpl() {
        this.writeEx(64);
        this.writeD(this._type);
        this.writeD(this.cF.size());
        for (WarInfo warInfo : this.cF) {
            this.writeS(warInfo.clan_name);
            this.writeD(warInfo.state);
            this.writeD(0);
            this.writeD(0);
            this.writeD(0);
            this.writeD(0);
        }
    }

    static class WarInfo {
        public String clan_name;
        public int state;

        public WarInfo(String string, int n) {
            this.clan_name = string;
            this.state = n;
        }
    }
}
