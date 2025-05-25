/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;
import l2.gameserver.model.Summon;
import l2.gameserver.model.base.TeamType;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.skills.AbnormalEffect;
import l2.gameserver.utils.Location;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public class PetInfo
extends L2GameServerPacket {
    private int sS;
    private int sT;
    private int zW;
    private int zX;
    private int pvp_flag;
    private int karma;
    private int zY;
    private int _type;
    private int tG;
    private int wq;
    private int zZ;
    private int incombat;
    private int dead;
    private int _sp;
    private int level;
    private int Aa;
    private int Ab;
    private int curHp;
    private int maxHp;
    private int curMp;
    private int maxMp;
    private int xT;
    private int xU;
    private int Ac;
    private int Ad;
    private int Ae;
    private int Af;
    private int Ag;
    private int Ah;
    private int Ai;
    private int Aj;
    private int Ak;
    private int _showSpawnAnimation;
    private Location _loc;
    private double af;
    private double W;
    private long dm;
    private long dn;
    private long do;
    private String _name;
    private String title;
    private TeamType _team;
    private AbnormalEffect[] abnormalEffects;
    private int Al;
    private double ag;
    private double ah;

    public PetInfo(Summon summon) {
        this._type = summon.getSummonType();
        this.tG = summon.getObjectId();
        this.wq = summon.getTemplate().npcId;
        this._loc = summon.getLoc();
        this.zW = summon.getMAtkSpd();
        this.zX = summon.getPAtkSpd();
        this.sS = summon.getRunSpeed();
        this.sT = summon.getWalkSpeed();
        this.af = summon.getColRadius();
        this.W = summon.getColHeight();
        this.zZ = summon.isRunning() ? 1 : 0;
        this.incombat = summon.isInCombat() ? 1 : 0;
        this.dead = summon.isAlikeDead() ? 1 : 0;
        this._name = summon.getName().equalsIgnoreCase(summon.getTemplate().name) ? "" : summon.getName();
        this.title = summon.getTitle();
        this.pvp_flag = summon.getPvpFlag();
        this.karma = summon.getKarma();
        this.Aa = summon.getCurrentFed();
        this.Ab = summon.getMaxFed();
        this.curHp = (int)summon.getCurrentHp();
        this.maxHp = summon.getMaxHp();
        this.curMp = (int)summon.getCurrentMp();
        this.maxMp = summon.getMaxMp();
        this._sp = summon.getSp();
        this.level = summon.getLevel();
        this.dm = summon.getExp();
        this.dn = summon.getExpForThisLevel();
        this.do = summon.getExpForNextLevel();
        this.xT = summon.isPet() ? summon.getInventory().getTotalWeight() : 0;
        this.xU = summon.getMaxLoad();
        this.Ac = summon.getPAtk(null);
        this.Ad = summon.getPDef(null);
        this.Ae = summon.getMAtk(null, null);
        this.Af = summon.getMDef(null, null);
        this.Ag = summon.getAccuracy();
        this.Ah = summon.getEvasionRate(null);
        this.Ai = summon.getCriticalHit(null, null);
        this.abnormalEffects = summon.getAbnormalEffects();
        this.ag = summon.getMovementSpeedMultiplier();
        this.ah = summon.getAttackSpeedMultiplier();
        this.zY = summon.getPlayer().getTransformation() != 0 ? 0 : (summon.isMountable() ? 1 : 0);
        this._team = summon.getTeam();
        this.Ak = summon.getSoulshotConsumeCount();
        this.Aj = summon.getSpiritshotConsumeCount();
        this._showSpawnAnimation = summon.getSpawnAnimation();
        Player player = summon.getPlayer();
        if (summon.isAutoAttackable(player)) {
            this.Al |= 1;
        }
        this.Al |= 2;
        if (summon.isRunning()) {
            this.Al |= 4;
        }
        if (summon.isInCombat()) {
            this.Al |= 8;
        }
        if (summon.isAlikeDead()) {
            this.Al |= 0x10;
        }
        if (summon.isMountable()) {
            this.Al |= 0x20;
        }
    }

    public PetInfo update() {
        this._showSpawnAnimation = 1;
        return this;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(178);
        this.writeC(this._type);
        this.writeD(this.tG);
        this.writeD(this.wq + 1000000);
        this.writeD(this._loc.x);
        this.writeD(this._loc.y);
        this.writeD(this._loc.z);
        this.writeD(this._loc.h);
        this.writeD(this.zW);
        this.writeD(this.zX);
        this.writeH(this.sS);
        this.writeH(this.sT);
        this.writeH(this.sS);
        this.writeH(this.sT);
        this.writeH(this.sS);
        this.writeH(this.sT);
        this.writeH(this.sS);
        this.writeH(this.sT);
        this.writeF(this.ag);
        this.writeF(this.ah);
        this.writeF(this.af);
        this.writeF(this.W);
        this.writeD(0);
        this.writeD(0);
        this.writeD(0);
        this.writeC(this._showSpawnAnimation);
        this.writeD(-1);
        this.writeS(this._name);
        this.writeD(-1);
        this.writeS(this.title);
        this.writeC(this.pvp_flag);
        this.writeD(this.karma);
        this.writeD(this.Aa);
        this.writeD(this.Ab);
        this.writeD(this.curHp);
        this.writeD(this.maxHp);
        this.writeD(this.curMp);
        this.writeD(this.maxMp);
        this.writeQ(this._sp);
        this.writeC(this.level);
        this.writeQ(this.dm);
        this.writeQ(this.dn);
        this.writeQ(this.do);
        this.writeD(this.xT);
        this.writeD(this.xU);
        this.writeD(this.Ac);
        this.writeD(this.Ad);
        this.writeD(this.Ag);
        this.writeD(this.Ah);
        this.writeD(this.Ai);
        this.writeD(this.Ae);
        this.writeD(this.Af);
        this.writeD(0);
        this.writeD(0);
        this.writeD(0);
        this.writeD(this.sS);
        this.writeD(this.zX);
        this.writeD(this.zW);
        this.writeC(this.zY);
        this.writeC(this._team.ordinal());
        this.writeC(this.Ak);
        this.writeC(this.Aj);
        this.writeD(0);
        this.writeD(0);
        this.writeC(0);
        this.writeC(0);
        this.writeH(this.abnormalEffects.length);
        for (AbnormalEffect abnormalEffect : this.abnormalEffects) {
            this.writeH(abnormalEffect.getClientId());
        }
        this.writeC(this.Al);
    }
}
