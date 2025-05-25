/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.PartySmallWindowUpdateType;
import l2.gameserver.network.l2.s2c.mask.AbstractMaskPacket;

public class PartySmallWindowUpdate
extends AbstractMaskPacket<PartySmallWindowUpdateType> {
    private int tG;
    private int class_id;
    private int level;
    private int vitality;
    private int curCp;
    private int maxCp;
    private int curHp;
    private int maxHp;
    private int curMp;
    private int maxMp;
    private String fC;
    private int _flags;

    public PartySmallWindowUpdate(Player player) {
        this(player, PartySmallWindowUpdateType.VALUES);
    }

    public PartySmallWindowUpdate(Player player, PartySmallWindowUpdateType ... partySmallWindowUpdateTypeArray) {
        this.tG = player.getObjectId();
        this.fC = player.getName();
        this.curCp = (int)player.getCurrentCp();
        this.maxCp = player.getMaxCp();
        this.curHp = (int)player.getCurrentHp();
        this.maxHp = player.getMaxHp();
        this.curMp = (int)player.getCurrentMp();
        this.maxMp = player.getMaxMp();
        this.level = player.getLevel();
        this.vitality = (int)player.getVitality();
        this.class_id = player.getClassId().getId();
        this.addComponentType(partySmallWindowUpdateTypeArray);
    }

    @Override
    protected void addMask(int n) {
        this._flags |= n;
    }

    @Override
    public boolean containsMask(PartySmallWindowUpdateType partySmallWindowUpdateType) {
        return this.containsMask(this._flags, partySmallWindowUpdateType);
    }

    @Override
    protected byte[] getMasks() {
        return new byte[0];
    }

    @Override
    protected final void writeImpl() {
        this.writeC(82);
        this.writeD(this.tG);
        this.writeH(this._flags);
        if (this.containsMask(PartySmallWindowUpdateType.CURRENT_CP)) {
            this.writeD(this.curCp);
        }
        if (this.containsMask(PartySmallWindowUpdateType.MAX_CP)) {
            this.writeD(this.maxCp);
        }
        if (this.containsMask(PartySmallWindowUpdateType.CURRENT_HP)) {
            this.writeD(this.curHp);
        }
        if (this.containsMask(PartySmallWindowUpdateType.MAX_HP)) {
            this.writeD(this.maxHp);
        }
        if (this.containsMask(PartySmallWindowUpdateType.CURRENT_MP)) {
            this.writeD(this.curMp);
        }
        if (this.containsMask(PartySmallWindowUpdateType.MAX_MP)) {
            this.writeD(this.maxMp);
        }
        if (this.containsMask(PartySmallWindowUpdateType.LEVEL)) {
            this.writeC(this.level);
        }
        if (this.containsMask(PartySmallWindowUpdateType.CLASS_ID)) {
            this.writeH(this.class_id);
        }
        if (this.containsMask(PartySmallWindowUpdateType.PARTY_SUBSTITUTE)) {
            this.writeC(0);
        }
        if (this.containsMask(PartySmallWindowUpdateType.VITALITY_POINTS)) {
            this.writeD(this.vitality);
        }
    }
}
