/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.NpcHolder;
import l2.gameserver.instancemanager.CursedWeaponsManager;
import l2.gameserver.model.Player;
import l2.gameserver.model.base.ClassId;
import l2.gameserver.model.base.Element;
import l2.gameserver.model.base.Experience;
import l2.gameserver.model.base.TeamType;
import l2.gameserver.model.entity.events.GlobalEvent;
import l2.gameserver.model.matching.MatchingRoom;
import l2.gameserver.model.pledge.Alliance;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.network.l2.s2c.UserInfoType;
import l2.gameserver.network.l2.s2c.mask.AbstractMaskPacket;
import l2.gameserver.utils.Location;

public class UserInfo
extends AbstractMaskPacket<UserInfoType> {
    private boolean can_writeImpl = false;
    private boolean fr;
    private int sS;
    private int sT;
    private int Cv;
    private int Cw;
    private int sV;
    private int sW;
    private int sX;
    private int sY;
    private int tb;
    private double ac;
    private double ad;
    private double V;
    private double W;
    private Location _loc;
    private Location x;
    private int tG;
    private int Cx;
    private int fZ;
    private int sex;
    private int sZ;
    private int level;
    private int curCp;
    private int maxCp;
    private int tl;
    private int tm;
    private int Cy;
    private long _exp;
    private int curHp;
    private int maxHp;
    private int curMp;
    private int maxMp;
    private int xT;
    private int xU;
    private int yv;
    private int ta;
    private int yo;
    private int yp;
    private int yq;
    private int yr;
    private int ys;
    private int yt;
    private int _sp;
    private int Cz;
    private int CA;
    private int yw;
    private int yx;
    private int yy;
    private int yz;
    private int yA;
    private int yB;
    private int yD;
    private int yE;
    private int yF;
    private int pvp_flag;
    private int karma;
    private int tc;
    private int td;
    private int te;
    private int yG;
    private int yQ;
    private int vitality;
    private int yP;
    private int clan_id;
    private int clan_crest_id;
    private int ally_id;
    private int ally_crest_id;
    private int tf;
    private int tk;
    private int CB;
    private int yL;
    private int yM;
    private int class_id;
    private int CC;
    private int CD;
    private int yI;
    private int yJ;
    private int tv;
    private int tu;
    private int yK;
    private int running;
    private int yO;
    private int ts;
    private int yH;
    private int og;
    private int oh;
    private int oi;
    private int oj;
    private int ok;
    private int ol;
    private int tq;
    private String _name;
    private String title;
    private Element c;
    private int of;
    private boolean fs;
    private boolean ft;
    private int yR;
    private int CE;
    private int CF;
    private double ae;
    private double al;
    private TeamType _team;
    private int CG;
    private boolean eh;
    private final byte[] z = new byte[]{0, 0, 0};
    private int sw = 5;

    public UserInfo(Player player, UserInfoType ... userInfoTypeArray) {
        if (player.getTransformationName() != null) {
            this._name = player.getTransformationName();
            this.title = "";
            this.clan_crest_id = 0;
            this.ally_crest_id = 0;
            this.tf = 0;
            this.tu = CursedWeaponsManager.getInstance().getLevel(player.getCursedWeaponEquippedId());
        } else {
            this._name = player.getName();
            Clan object = player.getClan();
            Alliance alliance = object == null ? null : object.getAlliance();
            this.clan_id = object == null ? 0 : object.getClanId();
            this.clan_crest_id = object == null ? 0 : object.getCrestId();
            this.tf = object == null ? 0 : object.getCrestLargeId();
            this.ally_id = alliance == null ? 0 : alliance.getAllyId();
            this.ally_crest_id = alliance == null ? 0 : alliance.getAllyCrestId();
            this.tu = 0;
            this.title = player.getTitle();
        }
        if (player.getPlayerAccess().GodMode && player.isInvisible()) {
            this.title = this.title + "(Invisible)";
        }
        if (player.isPolymorphed()) {
            this.title = NpcHolder.getInstance().getTemplate(player.getPolyId()) != null ? this.title + " - " + NpcHolder.getInstance().getTemplate((int)player.getPolyId()).name : this.title + " - Polymorphed";
        }
        if (player.isMounted()) {
            this.tl = 0;
            this.tm = 0;
            this.tv = player.getMountNpcId() + 1000000;
            this.tq = player.getMountType();
        } else {
            this.tl = player.getWeaponEnchantEffect();
            this.tm = Config.ALT_ALLOW_GLOW_ARMOR_SET ? player.getArmorSetEnchantLevel() : 0;
            this.tv = 0;
            this.tq = 0;
        }
        this.Cy = player.getPhysicalAttackRange();
        this.ac = player.getMovementSpeedMultiplier();
        this.sS = player.getTemplate().baseRunSpd;
        this.sT = player.getTemplate().baseWalkSpd;
        this.eh = !player.hideHeadAccessories();
        this.sV = 0;
        this.sW = 0;
        if (player.isFlying()) {
            this.sX = this.sS;
            this.sY = this.sT;
        } else {
            this.sX = 0;
            this.sY = 0;
        }
        this.Cv = player.getSwimSpeed();
        this.Cw = player.getSwimSpeed();
        if (player.getClan() != null) {
            this.tb |= 0x20;
            if (player.isClanLeader()) {
                this.ft = true;
                this.tb |= 0x40;
            }
        }
        for (GlobalEvent globalEvent : player.getEvents()) {
            this.tb = globalEvent.getUserRelation(player, this.tb);
        }
        this._loc = player.getLoc();
        this.tG = player.getObjectId();
        this.Cx = player.isInBoat() ? player.getBoat().getObjectId() : 0;
        this.fZ = player.getRace().ordinal();
        this.sex = player.getSex();
        this.sZ = player.getBaseClassId();
        this.level = player.getLevel();
        this._exp = player.getExp();
        this.ae = Experience.getExpPercent(player.getLevel(), player.getExp());
        this.yo = player.getSTR();
        this.yq = player.getDEX();
        this.yp = player.getCON();
        this.yr = player.getINT();
        this.ys = player.getWIT();
        this.yt = player.getMEN();
        this.curHp = (int)player.getCurrentHp();
        this.maxHp = player.getMaxHp();
        this.curMp = (int)player.getCurrentMp();
        this.maxMp = player.getMaxMp();
        this.xT = player.getCurrentLoad();
        this.xU = player.getMaxLoad();
        this._sp = player.getIntSp();
        this.yw = player.getPAtk(null);
        this.yx = player.getPAtkSpd();
        this.yy = player.getPDef(null);
        this.yz = player.getEvasionRate(null);
        this.yA = player.getAccuracy();
        this.yB = player.getCriticalHit(null, null);
        this.yD = player.getMAtk(null, null);
        this.yE = player.getMAtkSpd();
        this.yF = player.getMDef(null, null);
        this.pvp_flag = player.getPvpFlag();
        this.karma = -Math.min(player.getKarma(), 999999);
        this.ad = player.getAttackSpeedMultiplier();
        this.V = player.getColRadius();
        this.W = player.getColHeight();
        this.tc = player.getHairStyle();
        this.td = player.getHairColor();
        this.te = player.getFace();
        this.yG = player.isGM() || player.getPlayerAccess().CanUseGMCommand ? 1 : 0;
        this.clan_id = player.getClanId();
        this.ally_id = player.getAllyId();
        this.tk = player.getPrivateStoreType();
        this.CB = player.getSkillLevel(248) > 0 ? 1 : 0;
        this.yL = player.getPkKills();
        this.yM = player.getPvpKills();
        this.Cz = player.getClanPrivileges();
        this.yv = player.getGivableRec();
        this.ta = player.getReceivedRec();
        this.CA = player.getInventoryLimit();
        this.class_id = player.getClassId().getId();
        this.CC = ClassId.getClassById(this.class_id).getRootClassId().getId();
        this.maxCp = player.getMaxCp();
        this.curCp = (int)player.getCurrentCp();
        this._team = player.getTeam();
        this.yI = player.isNoble() || player.isGM() && Config.GM_HERO_AURA ? 2 : 0;
        this.yJ = player.isHero() || player.isGM() && Config.GM_HERO_AURA ? 2 : 0;
        this.x = player.getFishLoc();
        this.yK = player.getNameColor();
        this.running = player.isRunning() ? 1 : 0;
        this.yO = player.getPledgeClass();
        this.ts = player.getPledgeType();
        this.yH = player.getTitleColor();
        this.c = player.getAttackElement();
        this.of = player.getAttack(this.c);
        this.og = player.getDefence(Element.FIRE);
        this.oh = player.getDefence(Element.WATER);
        this.oi = player.getDefence(Element.WIND);
        this.oj = player.getDefence(Element.EARTH);
        this.ok = player.getDefence(Element.HOLY);
        this.ol = player.getDefence(Element.UNHOLY);
        this.CD = player.getAgathionId();
        this.vitality = (int)player.getVitality();
        this.yP = player.getRaidBossPoints();
        boolean bl = this.fr = player.getMatchingRoom() != null && player.getMatchingRoom().getType() == MatchingRoom.PARTY_MATCHING && player.getMatchingRoom().getLeader() == player;
        this.CG = player.isInFlyingTransform() ? 2 : (player.isInWater() ? 1 : 0);
        this.yR = player.getTalismanCount();
        this.CE = player.getBroochCount();
        this.CF = player.getAgathionCharmCount();
        this.al = player.getMagicCriticalRate(null, null);
        for (UserInfoType userInfoType : userInfoTypeArray) {
            this.addComponentType(new UserInfoType[]{userInfoType});
        }
        this.can_writeImpl = true;
    }

    @Override
    protected byte[] getMasks() {
        return this.z;
    }

    @Override
    protected void onNewMaskAdded(UserInfoType userInfoType) {
        this.a(userInfoType);
    }

    private void a(UserInfoType userInfoType) {
        switch (userInfoType) {
            case BASIC_INFO: {
                this.sw += userInfoType.getBlockLength() + this._name.length() * 2;
                break;
            }
            case CLAN: {
                this.sw += userInfoType.getBlockLength() + this.title.length() * 2;
                break;
            }
            default: {
                this.sw += userInfoType.getBlockLength();
            }
        }
    }

    @Override
    protected final void writeImpl() {
        if (!this.can_writeImpl) {
            return;
        }
        this.writeC(50);
        this.writeD(this.tG);
        this.writeD(this.sw);
        this.writeH(24);
        this.writeB(this.z);
        if (this.containsMask(UserInfoType.RELATION)) {
            this.writeD(this.tb);
        }
        if (this.containsMask(UserInfoType.BASIC_INFO)) {
            this.writeH(16 + this._name.length() * 2);
            this.writeStringWithSize(this._name);
            this.writeC(this.yG);
            this.writeC(this.fZ);
            this.writeC(this.sex);
            this.writeD(this.sZ);
            this.writeD(this.class_id);
            this.writeC(this.level);
        }
        if (this.containsMask(UserInfoType.BASE_STATS)) {
            this.writeH(18);
            this.writeH(this.yo);
            this.writeH(this.yq);
            this.writeH(this.yp);
            this.writeH(this.yr);
            this.writeH(this.ys);
            this.writeH(this.yt);
            this.writeH(0);
            this.writeH(0);
        }
        if (this.containsMask(UserInfoType.MAX_HPCPMP)) {
            this.writeH(14);
            this.writeD(this.maxHp);
            this.writeD(this.maxMp);
            this.writeD(this.maxCp);
        }
        if (this.containsMask(UserInfoType.CURRENT_HPMPCP_EXP_SP)) {
            this.writeH(38);
            this.writeD(this.curHp);
            this.writeD(this.curMp);
            this.writeD(this.curCp);
            this.writeQ(this._sp);
            this.writeQ(this._exp);
            this.writeF(this.ae);
        }
        if (this.containsMask(UserInfoType.ENCHANTLEVEL)) {
            this.writeH(4);
            this.writeC(this.tl);
            this.writeC(this.tm);
        }
        if (this.containsMask(UserInfoType.APPAREANCE)) {
            this.writeH(15);
            this.writeD(this.tc);
            this.writeD(this.td);
            this.writeD(this.te);
            this.writeC(this.eh ? 1 : 0);
        }
        if (this.containsMask(UserInfoType.STATUS)) {
            this.writeH(6);
            this.writeC(this.tq);
            this.writeC(this.tk);
            this.writeC(this.CB);
            this.writeC(0);
        }
        if (this.containsMask(UserInfoType.STATS)) {
            this.writeH(56);
            this.writeH(this.Cy);
            this.writeD(this.yw);
            this.writeD(this.yx);
            this.writeD(this.yy);
            this.writeD(this.yz);
            this.writeD(this.yA);
            this.writeD(this.yB);
            this.writeD(this.yD);
            this.writeD(this.yE);
            this.writeD(this.yx);
            this.writeD(0);
            this.writeD(this.yF);
            this.writeD(0);
            this.writeD((int)this.al);
        }
        if (this.containsMask(UserInfoType.ELEMENTALS)) {
            this.writeH(14);
            this.writeH(this.og);
            this.writeH(this.oh);
            this.writeH(this.oi);
            this.writeH(this.oj);
            this.writeH(this.ok);
            this.writeH(this.ol);
        }
        if (this.containsMask(UserInfoType.POSITION)) {
            this.writeH(18);
            this.writeD(this._loc.x);
            this.writeD(this._loc.y);
            this.writeD(this._loc.z + Config.CLIENT_Z_SHIFT);
            this.writeD(this.Cx);
        }
        if (this.containsMask(UserInfoType.SPEED)) {
            this.writeH(18);
            this.writeH(this.sS);
            this.writeH(this.sT);
            this.writeH(this.Cv);
            this.writeH(this.Cw);
            this.writeH(this.sV);
            this.writeH(this.sW);
            this.writeH(this.sX);
            this.writeH(this.sY);
        }
        if (this.containsMask(UserInfoType.MULTIPLIER)) {
            this.writeH(18);
            this.writeF(this.ac);
            this.writeF(this.ad);
        }
        if (this.containsMask(UserInfoType.COL_RADIUS_HEIGHT)) {
            this.writeH(18);
            this.writeF(this.V);
            this.writeF(this.W);
        }
        if (this.containsMask(UserInfoType.ATK_ELEMENTAL)) {
            this.writeH(5);
            this.writeC(this.c.getId());
            this.writeH(this.of);
        }
        if (this.containsMask(UserInfoType.CLAN)) {
            this.writeH(32 + this.title.length() * 2);
            this.writeStringWithSize(this.title);
            this.writeH(this.ts);
            this.writeD(this.clan_id);
            this.writeD(this.tf);
            this.writeD(this.clan_crest_id);
            this.writeD(this.Cz);
            this.writeC(this.ft ? 1 : 0);
            this.writeD(this.ally_id);
            this.writeD(this.ally_crest_id);
            this.writeC(this.fr ? 1 : 0);
        }
        if (this.containsMask(UserInfoType.SOCIAL)) {
            this.writeH(22);
            this.writeC(this.pvp_flag);
            this.writeD(this.karma);
            this.writeC(this.yI);
            this.writeC(this.yJ);
            this.writeC(this.yO);
            this.writeD(this.yL);
            this.writeD(this.yM);
            this.writeH(this.yv);
            this.writeH(this.ta);
        }
        if (this.containsMask(UserInfoType.VITA_FAME)) {
            this.writeH(15);
            this.writeD(this.vitality);
            this.writeC(0);
            this.writeD(0);
            this.writeD(this.yP);
        }
        if (this.containsMask(UserInfoType.SLOTS)) {
            this.writeH(12);
            this.writeC(this.yR);
            this.writeC(this.CE);
            this.writeC(this._team.ordinal());
            this.writeD(0);
            this.writeC(this.CF > 0);
            this.writeC(this.CF - 1);
            this.writeC(0);
        }
        if (this.containsMask(UserInfoType.MOVEMENTS)) {
            this.writeH(4);
            this.writeC(this.CG);
            this.writeC(this.running);
        }
        if (this.containsMask(UserInfoType.COLOR)) {
            this.writeH(10);
            this.writeD(this.yK);
            this.writeD(this.yH);
        }
        if (this.containsMask(UserInfoType.INVENTORY_LIMIT)) {
            this.writeH(9);
            this.writeH(0);
            this.writeH(0);
            this.writeH(this.CA);
            this.writeC(this.tu);
        }
        if (this.containsMask(UserInfoType.TRUE_HERO)) {
            this.writeH(9);
            this.writeD(0);
            this.writeH(0);
            this.writeC(0);
        }
        if (this.containsMask(UserInfoType.ATT_SPIRITS)) {
            this.writeH(26);
            this.writeD(0);
            this.writeD(0);
            this.writeD(0);
            this.writeD(0);
            this.writeD(0);
            this.writeD(0);
        }
    }
}
