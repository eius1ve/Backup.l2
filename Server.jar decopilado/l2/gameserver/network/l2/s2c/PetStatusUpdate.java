/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Summon;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.utils.Location;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public class PetStatusUpdate
extends L2GameServerPacket {
    private int type;
    private int tG;
    private int level;
    private int Ab;
    private int Aa;
    private int maxHp;
    private int curHp;
    private int maxMp;
    private int curMp;
    private long dm;
    private long dn;
    private long do;
    private Location _loc;
    private String title;

    public PetStatusUpdate(Summon summon) {
        this.type = summon.getSummonType();
        this.tG = summon.getObjectId();
        this._loc = summon.getLoc();
        this.title = summon.getTitle();
        this.curHp = (int)summon.getCurrentHp();
        this.maxHp = summon.getMaxHp();
        this.curMp = (int)summon.getCurrentMp();
        this.maxMp = summon.getMaxMp();
        this.Aa = summon.getCurrentFed();
        this.Ab = summon.getMaxFed();
        this.level = summon.getLevel();
        this.dm = summon.getExp();
        this.dn = summon.getExpForThisLevel();
        this.do = summon.getExpForNextLevel();
    }

    @Override
    protected final void writeImpl() {
        this.writeC(182);
        this.writeD(this.type);
        this.writeD(this.tG);
        this.writeD(this._loc.x);
        this.writeD(this._loc.y);
        this.writeD(this._loc.z);
        this.writeS(this.title);
        this.writeD(this.Aa);
        this.writeD(this.Ab);
        this.writeD(this.curHp);
        this.writeD(this.maxHp);
        this.writeD(this.curMp);
        this.writeD(this.maxMp);
        this.writeD(this.level);
        this.writeQ(this.dm);
        this.writeQ(this.dn);
        this.writeQ(this.do);
    }
}
