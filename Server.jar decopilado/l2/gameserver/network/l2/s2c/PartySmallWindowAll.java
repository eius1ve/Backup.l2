/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.ArrayList;
import java.util.List;
import l2.gameserver.model.Party;
import l2.gameserver.model.Player;
import l2.gameserver.model.Summon;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class PartySmallWindowAll
extends L2GameServerPacket {
    private int zP;
    private int vi;
    private List<PartySmallWindowMemberInfo> cg = new ArrayList<PartySmallWindowMemberInfo>();

    public PartySmallWindowAll(Party party, Player player) {
        this.zP = party.getPartyLeader().getObjectId();
        this.vi = party.getLootDistribution();
        for (Player player2 : party.getPartyMembers()) {
            if (player2 == player) continue;
            this.cg.add(new PartySmallWindowMemberInfo(player2));
        }
    }

    @Override
    protected final void writeImpl() {
        this.writeC(78);
        this.writeD(this.zP);
        this.writeC(this.vi);
        this.writeC(this.cg.size());
        for (PartySmallWindowMemberInfo partySmallWindowMemberInfo : this.cg) {
            this.writeD(partySmallWindowMemberInfo._id);
            this.writeS(partySmallWindowMemberInfo._name);
            this.writeD(partySmallWindowMemberInfo.curCp);
            this.writeD(partySmallWindowMemberInfo.maxCp);
            this.writeD(partySmallWindowMemberInfo.curHp);
            this.writeD(partySmallWindowMemberInfo.maxHp);
            this.writeD(partySmallWindowMemberInfo.curMp);
            this.writeD(partySmallWindowMemberInfo.maxMp);
            this.writeD(partySmallWindowMemberInfo.vitality);
            this.writeC(partySmallWindowMemberInfo.level);
            this.writeH(partySmallWindowMemberInfo.class_id);
            this.writeC(0);
            this.writeH(partySmallWindowMemberInfo.race_id);
            this.writeD(partySmallWindowMemberInfo.pet_id != 0 ? 1 : 0);
            if (partySmallWindowMemberInfo.pet_id == 0) continue;
            this.writeD(partySmallWindowMemberInfo.pet_id);
            this.writeD(partySmallWindowMemberInfo.pet_NpcId);
            this.writeC(partySmallWindowMemberInfo.pet_type);
            this.writeS(partySmallWindowMemberInfo.pet_Name);
            this.writeD(partySmallWindowMemberInfo.pet_curHp);
            this.writeD(partySmallWindowMemberInfo.pet_maxHp);
            this.writeD(partySmallWindowMemberInfo.pet_curMp);
            this.writeD(partySmallWindowMemberInfo.pet_maxMp);
            this.writeC(partySmallWindowMemberInfo.pet_level);
        }
    }

    public static class PartySmallWindowMemberInfo {
        public String _name;
        public String pet_Name;
        public int _id;
        public int curCp;
        public int maxCp;
        public int curHp;
        public int maxHp;
        public int curMp;
        public int maxMp;
        public int level;
        public int class_id;
        public int race_id;
        public int vitality;
        public int pet_id;
        public int pet_type;
        public int pet_NpcId;
        public int pet_curHp;
        public int pet_maxHp;
        public int pet_curMp;
        public int pet_maxMp;
        public int pet_level;

        public PartySmallWindowMemberInfo(Player player) {
            this._name = player.getName();
            this._id = player.getObjectId();
            this.curCp = (int)player.getCurrentCp();
            this.maxCp = player.getMaxCp();
            this.curHp = (int)player.getCurrentHp();
            this.maxHp = player.getMaxHp();
            this.curMp = (int)player.getCurrentMp();
            this.maxMp = player.getMaxMp();
            this.level = player.getLevel();
            this.class_id = player.getClassId().getId();
            this.race_id = player.getRace().ordinal();
            this.vitality = (int)player.getVitality();
            Summon summon = player.getPet();
            if (summon != null) {
                this.pet_id = summon.getObjectId();
                this.pet_type = summon.getSummonType();
                this.pet_NpcId = summon.getNpcId() + 1000000;
                this.pet_Name = summon.getName();
                this.pet_curHp = (int)summon.getCurrentHp();
                this.pet_maxHp = summon.getMaxHp();
                this.pet_curMp = (int)summon.getCurrentMp();
                this.pet_maxMp = summon.getMaxMp();
                this.pet_level = summon.getLevel();
            } else {
                this.pet_id = 0;
            }
        }
    }
}
