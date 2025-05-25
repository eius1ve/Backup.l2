/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.network.l2.s2c;

import java.util.Iterator;
import l2.gameserver.Config;
import l2.gameserver.instancemanager.CursedWeaponsManager;
import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.base.TeamType;
import l2.gameserver.model.entity.events.GlobalEvent;
import l2.gameserver.model.matching.MatchingRoom;
import l2.gameserver.model.pledge.Alliance;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.skills.AbnormalEffect;
import l2.gameserver.skills.effects.EffectCubic;
import l2.gameserver.utils.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CharInfo
extends L2GameServerPacket {
    public static final int[] PAPERDOLL_ORDER = new int[]{0, 1, 5, 7, 10, 6, 11, 12, 28, 5, 2, 3};
    public static int[] PAPERDOLL_ORDER_AUGMENT = new int[]{5, 7, 5};
    public static final int[] PAPERDOLL_ORDER_VISUAL_ID = new int[]{5, 7, 5, 10, 6, 11, 12, 2, 3};
    public static final int[] PAPERDOLL_SET_ORDER = new int[0];
    private static final Logger cZ = LoggerFactory.getLogger(CharInfo.class);
    private int[][] l;
    private int _mAtkSpd;
    private int _pAtkSpd;
    private int sS;
    private int sT;
    private int sU;
    private int sV;
    private int sW;
    private int sX;
    private int sY;
    private Location _loc;
    private Location x;
    private String _name;
    private String _title;
    private int sR;
    private int fZ;
    private int gg;
    private int sZ;
    private int pvp_flag;
    private int karma;
    private int ta;
    private int tb;
    private double T;
    private double U;
    private double V;
    private double W;
    private int tc;
    private int td;
    private int te;
    private int clan_id;
    private int clan_crest_id;
    private int tf;
    private int ally_id;
    private int ally_crest_id;
    private int class_id;
    private int tg;
    private int th;
    private int ti;
    private int tj;
    private int tk;
    private int tl;
    private int tm;
    private int tn;
    private int to;
    private int tp;
    private int tq;
    private int tr;
    private int ts;
    private int tt;
    private int tu;
    private int tv;
    private int hR;
    private int tw;
    private int tx;
    private int ty;
    private int tz;
    private EffectCubic[] a;
    private boolean ef;
    private boolean eg;
    private int tA;
    private int maxHp;
    private int maxMp;
    private int currentHp;
    private int currentMp;
    private TeamType _team;
    private AbnormalEffect[] abnormalEffects;
    private boolean eh;

    public CharInfo(Player player) {
        this((Creature)player);
    }

    public CharInfo(Creature creature) {
        if (creature == null) {
            System.out.println("CharInfo: cha is null!");
            Thread.dumpStack();
            return;
        }
        if (creature.isInvisible()) {
            return;
        }
        if (creature.isDeleted()) {
            return;
        }
        Player player = creature.getPlayer();
        if (player == null) {
            return;
        }
        if (player.isInBoat()) {
            this._loc = player.getInBoatPosition();
        }
        if (this._loc == null) {
            this._loc = creature.getLoc();
        }
        this.sR = creature.getObjectId();
        if (player.getTransformationName() != null || player.getReflection() == ReflectionManager.GIRAN_HARBOR && player.getPrivateStoreType() != 0) {
            this._name = player.getTransformationName() != null ? player.getTransformationName() : player.getName();
            this._title = "";
            this.clan_id = 0;
            this.clan_crest_id = 0;
            this.ally_id = 0;
            this.ally_crest_id = 0;
            this.tf = 0;
            if (player.isCursedWeaponEquipped()) {
                this.tu = CursedWeaponsManager.getInstance().getLevel(player.getCursedWeaponEquippedId());
            }
        } else {
            this._name = player.getName();
            if (player.getPrivateStoreType() != 0) {
                this._title = "";
            } else if (!player.isConnected()) {
                this._title = player.getDisconnectedTitle();
                this.tw = player.getDisconnectedTitleColor();
            } else {
                this._title = player.getTitle();
                this.tw = player.getTitleColor();
            }
            Iterator<GlobalEvent> iterator = player.getClan();
            Alliance alliance = iterator == null ? null : ((Clan)((Object)iterator)).getAlliance();
            this.clan_id = iterator == null ? 0 : ((Clan)((Object)iterator)).getClanId();
            this.clan_crest_id = iterator == null ? 0 : ((Clan)((Object)iterator)).getCrestId();
            this.tf = iterator == null ? 0 : ((Clan)((Object)iterator)).getCrestLargeId();
            this.ally_id = alliance == null ? 0 : alliance.getAllyId();
            this.ally_crest_id = alliance == null ? 0 : alliance.getAllyCrestId();
            this.tu = 0;
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
        this.l = new int[59][4];
        for (Object object : (Iterator<GlobalEvent>)PAPERDOLL_ORDER) {
            this.l[object][0] = player.getInventory().getPaperdollItemId((int)object);
            int n = player.getInventory().getPaperdollAugmentationId((int)object);
            this.l[object][1] = n & 0xFFFF;
            this.l[object][2] = n >> 16;
        }
        this.maxHp = player.getMaxHp();
        this.maxMp = player.getMaxMp();
        this.tA = (int)player.getCurrentCp();
        this.currentHp = (int)player.getCurrentHp();
        this.currentMp = (int)player.getCurrentMp();
        this.abnormalEffects = player.getAbnormalEffects();
        this._mAtkSpd = player.getMAtkSpd();
        this._pAtkSpd = player.getPAtkSpd();
        this.T = player.getMovementSpeedMultiplier();
        this.sS = player.getTemplate().baseRunSpd;
        this.sT = player.getTemplate().baseWalkSpd;
        this.sV = 0;
        this.sW = 0;
        if (player.isFlying()) {
            this.sX = this.sS;
            this.sY = this.sT;
        } else {
            this.sX = 0;
            this.sY = 0;
        }
        this.sU = player.getSwimSpeed();
        this.fZ = player.getBaseTemplate().race.ordinal();
        this.gg = player.getSex();
        this.sZ = player.getBaseClassId();
        this.pvp_flag = player.getPvpFlag();
        this.karma = -player.getKarma();
        this.U = player.getAttackSpeedMultiplier();
        this.V = player.getColRadius();
        this.W = player.getColHeight();
        this.tc = player.getHairStyle();
        this.td = player.getHairColor();
        this.te = player.getFace();
        this.tt = this.clan_id > 0 && player.getClan() != null ? player.getClan().getReputationScore() : 0;
        this.tg = player.isSitting() ? 0 : 1;
        this.th = player.isRunning() ? 1 : 0;
        this.ti = player.isInCombat() ? 1 : 0;
        this.tj = player.isAlikeDead() ? 1 : 0;
        this.tk = player.isInObserverMode() ? 7 : player.getPrivateStoreType();
        this.a = player.getCubics().toArray(new EffectCubic[player.getCubics().size()]);
        this.ta = player.getReceivedRec();
        this.class_id = player.getClassId().getId();
        this._team = player.getTeam();
        this.eh = !player.hideHeadAccessories();
        this.tn = player.isNoble() ? 1 : 0;
        this.to = player.isHero() || player.isGM() && Config.GM_HERO_AURA ? 2 : 0;
        this.tp = player.isFishing() ? 1 : 0;
        this.x = player.getFishLoc();
        this.hR = player.getNameColor();
        this.tr = player.getPledgeClass();
        this.ts = player.getPledgeType();
        this.tx = player.getTransformation();
        this.ty = player.getAgathionId();
        this.ef = player.getMatchingRoom() != null && player.getMatchingRoom().getType() == MatchingRoom.PARTY_MATCHING && player.getMatchingRoom().getLeader() == player;
        this.eg = player.isInFlyingTransform();
        this.tb = player.isClanLeader() ? 64 : 0;
        for (GlobalEvent globalEvent : player.getEvents()) {
            this.tb = globalEvent.getUserRelation(player, this.tb);
        }
    }

    @Override
    protected final void writeImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        if (this.sR == 0) {
            return;
        }
        if (player.getObjectId() == this.sR) {
            cZ.error("You cant send CharInfo about his character to active user!!!");
            return;
        }
        this.writeC(49);
        this.writeC(0);
        this.writeD(this._loc.x);
        this.writeD(this._loc.y);
        this.writeD(this._loc.z + Config.CLIENT_Z_SHIFT);
        this.writeD(this.tz);
        this.writeD(this.sR);
        this.writeS(this._name);
        this.writeH(this.fZ);
        this.writeC(this.gg);
        this.writeD(this.sZ);
        for (int n : PAPERDOLL_ORDER) {
            this.writeD(this.l[n][0]);
        }
        for (int n : PAPERDOLL_ORDER_AUGMENT) {
            this.writeD(this.l[n][1]);
            this.writeD(this.l[n][2]);
        }
        this.writeC(this.tm);
        for (int n : PAPERDOLL_ORDER_VISUAL_ID) {
            this.writeD(this.l[n][3]);
        }
        this.writeC(this.pvp_flag);
        this.writeD(this.karma);
        this.writeD(this._mAtkSpd);
        this.writeD(this._pAtkSpd);
        this.writeH(this.sS);
        this.writeH(this.sT);
        this.writeH(this.sU);
        this.writeH(this.sU);
        this.writeH(this.sV);
        this.writeH(this.sW);
        this.writeH(this.sX);
        this.writeH(this.sY);
        this.writeF(this.T);
        this.writeF(this.U);
        this.writeF(this.V);
        this.writeF(this.W);
        this.writeD(this.tc);
        this.writeD(this.td);
        this.writeD(this.te);
        this.writeS(this._title);
        this.writeD(this.clan_id);
        this.writeD(this.clan_crest_id);
        this.writeD(this.ally_id);
        this.writeD(this.ally_crest_id);
        this.writeC(this.tg);
        this.writeC(this.th);
        this.writeC(this.ti);
        this.writeC(this.tj);
        this.writeC(0);
        this.writeC(this.tq);
        this.writeC(this.tk);
        this.writeH(this.a.length);
        for (EffectCubic effectCubic : this.a) {
            this.writeH(effectCubic == null ? 0 : effectCubic.getId());
        }
        this.writeC(this.ef ? 1 : 0);
        this.writeC(this.eg ? 2 : 0);
        this.writeH(this.ta);
        this.writeD(this.tv);
        this.writeD(this.class_id);
        this.writeD(0);
        this.writeC(this.tl);
        this.writeC(this._team.ordinal());
        this.writeD(this.tf);
        this.writeC(this.tn);
        this.writeC(this.to);
        this.writeC(this.tp);
        this.writeD(this.x.x);
        this.writeD(this.x.y);
        this.writeD(this.x.z);
        this.writeD(this.hR);
        this.writeD(this._loc.h);
        this.writeC(this.tr);
        this.writeH(this.ts);
        this.writeD(this.tw);
        this.writeC(this.tu);
        this.writeD(this.tt);
        this.writeD(this.tx);
        this.writeD(this.ty);
        this.writeC(this.tb);
        this.writeD(this.tA);
        this.writeD(this.maxHp);
        this.writeD(this.currentHp);
        this.writeD(this.maxMp);
        this.writeD(this.currentMp);
        this.writeC(0);
        this.writeD(this.abnormalEffects.length);
        for (AbnormalEffect abnormalEffect : this.abnormalEffects) {
            this.writeH(abnormalEffect.getClientId());
        }
        this.writeC(0);
        this.writeC(this.eh ? 1 : 0);
        this.writeC(0);
    }
}
