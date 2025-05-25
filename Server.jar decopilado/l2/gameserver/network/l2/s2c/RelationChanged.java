/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.ArrayList;
import java.util.List;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class RelationChanged
extends L2GameServerPacket {
    public static final int RELATION_PARTY1 = 1;
    public static final int RELATION_PARTY2 = 2;
    public static final int RELATION_PARTY3 = 4;
    public static final int RELATION_PARTY4 = 8;
    public static final int RELATION_PARTYLEADER = 16;
    public static final int RELATION_HAS_PARTY = 32;
    public static final int RELATION_CLAN_MEMBER = 64;
    public static final int RELATION_LEADER = 128;
    public static final int RELATION_CLAN_MATE = 256;
    public static final int RELATION_IN_SIEGE = 512;
    public static final int RELATION_ATTACKER = 1024;
    public static final int RELATION_ALLY = 2048;
    public static final int RELATION_ENEMY = 4096;
    public static final int RELATION_DECLARED_WAR = 16384;
    public static final int RELATION_MUTUAL_WAR = 32768;
    public static final int RELATION_ALLY_MEMBER = 65536;
    public static final int RELATION_TERRITORY_WAR = 524288;
    public static final int USER_RELATION_CLAN_MEMBER = 32;
    public static final int USER_RELATION_CLAN_LEADER = 64;
    public static final int USER_RELATION_IN_SIEGE = 128;
    public static final int USER_RELATION_ATTACKER = 256;
    public static final int USER_RELATION_IN_DOMINION_WAR = 4096;
    public static final byte SEND_ONE = 0;
    public static final byte SEND_DEFAULT = 1;
    public static final byte SEND_MULTI = 4;
    private byte f = 1;
    protected final List<RelationChangedData> _data = new ArrayList<RelationChangedData>(1);

    public RelationChanged add(Player player, Player player2) {
        RelationChangedData relationChangedData = new RelationChangedData();
        relationChangedData.objectId = player.getObjectId();
        relationChangedData.karma = -player.getKarma();
        relationChangedData.pvpFlag = player.getPvpFlag();
        relationChangedData.isAutoAttackable = player.isAutoAttackable(player2);
        relationChangedData.relation = player.getRelation(player2);
        this._data.add(relationChangedData);
        this.f = this._data.size() == 1 ? (byte)0 : (byte)4;
        return this;
    }

    @Override
    protected void writeImpl() {
        this.writeC(206);
        this.writeC(this.f);
        if (this.f == 4) {
            this.writeH(this._data.size());
            for (RelationChangedData relationChangedData : this._data) {
                this.a(relationChangedData);
            }
        } else if (this.f == 0) {
            RelationChangedData relationChangedData = this._data.get(0);
            this.a(relationChangedData);
        }
    }

    private void a(RelationChangedData relationChangedData) {
        this.writeD(relationChangedData.objectId);
        this.writeD(relationChangedData.relation);
        this.writeC(relationChangedData.isAutoAttackable ? 1 : 0);
        this.writeD(relationChangedData.karma);
        this.writeC(relationChangedData.pvpFlag);
    }

    private static class RelationChangedData {
        public int objectId;
        public boolean isAutoAttackable;
        public int relation;
        public int karma;
        public int pvpFlag;

        private RelationChangedData() {
        }
    }
}
