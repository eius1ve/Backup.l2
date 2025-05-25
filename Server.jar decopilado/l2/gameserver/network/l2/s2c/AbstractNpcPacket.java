/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.Config;
import l2.gameserver.model.Creature;
import l2.gameserver.model.base.TeamType;
import l2.gameserver.model.instances.MonsterInstance;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.pledge.Alliance;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.network.l2.components.NpcString;
import l2.gameserver.network.l2.s2c.NpcInfoType;
import l2.gameserver.network.l2.s2c.mask.AbstractMaskPacket;
import l2.gameserver.skills.AbnormalEffect;
import l2.gameserver.tables.ClanTable;
import l2.gameserver.utils.Location;

public abstract class AbstractNpcPacket
extends AbstractMaskPacket<NpcInfoType> {
    protected boolean can_writeImpl = false;
    protected int _npcObjId;
    protected int _npcId;
    protected int running;
    protected int incombat;
    protected int dead;
    protected int _showSpawnAnimation;
    protected int _mAtkSpd;
    protected int _pAtkSpd;
    protected int _rhand;
    protected int _lhand;
    protected int _enchantEffect;
    protected int karma;
    protected int pvp_flag;
    protected int clan_id;
    protected int clan_crest_id;
    protected int clan_large_crest_id;
    protected int ally_id;
    protected int ally_crest_id;
    protected boolean _isAttackable;
    protected boolean _isNameAbove;
    protected boolean isFlying;
    protected Location _loc;
    protected String _name = "";
    protected String _title = "";
    protected boolean _showName;
    protected boolean _targetable;
    protected int _state;
    protected NpcString _nameNpcString = NpcString.NONE;
    protected NpcString _titleNpcString = NpcString.NONE;
    protected TeamType _team;
    protected boolean _isPet;
    protected int currentHp;
    protected int currentMp;
    protected int maxHp;
    protected int maxMp;
    protected float moveAnimMod;
    protected float atkSpeed;
    protected AbnormalEffect[] abnormalEffects = AbnormalEffect.EMPTY_ARRAY;
    private final byte[] s = new byte[]{0, 12, 12, 0, 0};
    private int sw = 0;
    private int sx = 0;

    protected void setValues(Creature creature, NpcInfoType ... npcInfoTypeArray) {
        this.currentHp = (int)creature.getCurrentHp();
        this.currentMp = (int)creature.getCurrentMp();
        this.maxHp = creature.getMaxHp();
        this.maxMp = creature.getMaxMp();
        this._npcObjId = creature.getObjectId();
        this._loc = creature.getLoc();
        this._mAtkSpd = creature.getMAtkSpd();
        if (Config.ALT_NPC_CLAN == 0) {
            Clan clan = creature.getClan();
            Alliance alliance = clan == null ? null : clan.getAlliance();
            this.clan_id = clan == null ? 0 : clan.getClanId();
            this.clan_crest_id = clan == null ? 0 : clan.getCrestId();
            this.ally_id = alliance == null ? 0 : alliance.getAllyId();
            this.ally_crest_id = alliance == null ? 0 : alliance.getAllyCrestId();
        } else if (creature instanceof NpcInstance && Config.ALT_NPC_CLAN > 0 && ((NpcInstance)creature).getCastle() != null) {
            Clan clan = ClanTable.getInstance().getClan(Config.ALT_NPC_CLAN);
            Alliance alliance = clan == null ? null : clan.getAlliance();
            this.clan_id = clan == null ? 0 : clan.getClanId();
            this.clan_crest_id = clan == null ? 0 : clan.getCrestId();
            this.ally_id = alliance == null ? 0 : alliance.getAllyId();
            this.ally_crest_id = alliance == null ? 0 : alliance.getAllyCrestId();
        } else {
            this.clan_id = 0;
            this.clan_crest_id = 0;
            this.ally_id = 0;
            this.ally_crest_id = 0;
        }
        this.karma = creature.getKarma();
        this.pvp_flag = creature.getPvpFlag();
        this._pAtkSpd = creature.getPAtkSpd();
        this.running = creature.isRunning() ? 1 : 0;
        this.incombat = creature.isInCombat() ? 1 : 0;
        this.dead = creature.isAlikeDead() ? 1 : 0;
        this.isFlying = creature.isFlying();
        this._team = creature.getTeam();
        this._isNameAbove = creature.isNameAbove();
        this._targetable = creature.isTargetable();
        if (creature instanceof NpcInstance) {
            if (Config.SERVER_SIDE_NPC_NAME || ((NpcInstance)creature).getTemplate().displayId != 0 || creature.getName() != ((NpcInstance)creature).getTemplate().name) {
                this._name = creature.getName();
            }
            if (Config.SERVER_SIDE_NPC_TITLE || ((NpcInstance)creature).getTemplate().displayId != 0 || creature.getTitle() != ((NpcInstance)creature).getTemplate().title) {
                this._title = creature.getTitle();
            }
            if (Config.SERVER_SIDE_MONSTER_LEVEL_TITLE && creature instanceof MonsterInstance) {
                this._title = "Lv " + creature.getLevel() + (((NpcInstance)creature).getAggroRange() > 0 ? "*" : "") + " " + creature.getTitle();
            }
            if (Config.SERVER_SIDE_AGRO_MONSTER_RED_NAME && creature instanceof MonsterInstance && ((NpcInstance)creature).getAggroRange() > 0) {
                this.karma = -1;
            }
        }
        this.moveAnimMod = creature.isRunning() ? (float)creature.getRunSpeed() / (float)creature.getTemplate().baseRunSpd : (float)creature.getWalkSpeed() / (float)creature.getTemplate().baseWalkSpd;
        this.atkSpeed = (float)creature.getAttackSpeedMultiplier();
        this.abnormalEffects = creature.getAbnormalEffects();
        this.addComponentType(npcInfoTypeArray);
        this.can_writeImpl = true;
    }

    public AbstractNpcPacket update(boolean bl) {
        this._showSpawnAnimation = bl ? 1 : (this._showSpawnAnimation == 2 ? 2 : 0);
        return this;
    }

    @Override
    protected byte[] getMasks() {
        return this.s;
    }

    @Override
    protected void onNewMaskAdded(NpcInfoType npcInfoType) {
        this.a(npcInfoType);
    }

    private void a(NpcInfoType npcInfoType) {
        switch (npcInfoType) {
            case ATTACKABLE: 
            case UNKNOWN1: {
                this.sw += npcInfoType.getBlockLength();
                break;
            }
            case TITLE: {
                this.sw += npcInfoType.getBlockLength() + this._title.length() * 2;
                break;
            }
            case NAME: {
                this.sx += npcInfoType.getBlockLength() + this._name.length() * 2;
                break;
            }
            default: {
                this.sx += npcInfoType.getBlockLength();
            }
        }
    }

    protected void writeData() {
        this.writeD(this._npcObjId);
        this.writeC(this._showSpawnAnimation);
        this.writeH(37);
        this.writeB(this.s);
        this.writeC(this.sw);
        if (this.containsMask(NpcInfoType.ATTACKABLE)) {
            this.writeC(this._isAttackable ? 1 : 0);
        }
        if (this.containsMask(NpcInfoType.UNKNOWN1)) {
            this.writeD(0);
        }
        if (this.containsMask(NpcInfoType.TITLE)) {
            this.writeS(this._title);
        }
        this.writeH(this.sx);
        if (this.containsMask(NpcInfoType.ID)) {
            this.writeD(this._npcId + 1000000);
        }
        if (this.containsMask(NpcInfoType.POSITION)) {
            this.writeD(this._loc.x);
            this.writeD(this._loc.y);
            this.writeD(this._loc.z + Config.CLIENT_Z_SHIFT);
        }
        if (this.containsMask(NpcInfoType.HEADING)) {
            this.writeD(this._loc.h);
        }
        if (this.containsMask(NpcInfoType.UNKNOWN2)) {
            this.writeD(0);
        }
        if (this.containsMask(NpcInfoType.ATK_CAST_SPEED)) {
            this.writeD(this._mAtkSpd);
            this.writeD(this._pAtkSpd);
        }
        if (this.containsMask(NpcInfoType.SPEED_MULTIPLIER)) {
            this.writeE(this.moveAnimMod);
            this.writeE(this.atkSpeed);
        }
        if (this.containsMask(NpcInfoType.EQUIPPED)) {
            this.writeD(this._rhand);
            this.writeD(0);
            this.writeD(this._lhand);
        }
        if (this.containsMask(NpcInfoType.ALIVE)) {
            this.writeC(this.dead == 0);
        }
        if (this.containsMask(NpcInfoType.RUNNING)) {
            this.writeC(this.running);
        }
        if (this.containsMask(NpcInfoType.SWIM_OR_FLY)) {
            this.writeC(this.isFlying ? 2 : 0);
        }
        if (this.containsMask(NpcInfoType.TEAM)) {
            this.writeC(this._team.ordinal());
        }
        if (this.containsMask(NpcInfoType.ENCHANT)) {
            this.writeD(this._enchantEffect);
        }
        if (this.containsMask(NpcInfoType.FLYING)) {
            this.writeD(this.isFlying ? 1 : 0);
        }
        if (this.containsMask(NpcInfoType.CLONE)) {
            this.writeD(0);
        }
        if (this.containsMask(NpcInfoType.COLOR_EFFECT)) {
            this.writeD(this._isPet);
        }
        if (this.containsMask(NpcInfoType.DISPLAY_EFFECT)) {
            this.writeD(this._state);
        }
        if (this.containsMask(NpcInfoType.TRANSFORMATION)) {
            this.writeD(0);
        }
        if (this.containsMask(NpcInfoType.CURRENT_HP)) {
            this.writeD(this.currentHp);
        }
        if (this.containsMask(NpcInfoType.CURRENT_MP)) {
            this.writeD(this.currentMp);
        }
        if (this.containsMask(NpcInfoType.MAX_HP)) {
            this.writeD(this.maxHp);
        }
        if (this.containsMask(NpcInfoType.MAX_MP)) {
            this.writeD(this.maxMp);
        }
        if (this.containsMask(NpcInfoType.SUMMONED)) {
            this.writeC(0);
        }
        if (this.containsMask(NpcInfoType.UNKNOWN12)) {
            this.writeD(0);
            this.writeD(0);
        }
        if (this.containsMask(NpcInfoType.NAME)) {
            this.writeS(this._name);
        }
        if (this.containsMask(NpcInfoType.NAME_NPCSTRINGID)) {
            this.writeD(this._nameNpcString.getId());
        }
        if (this.containsMask(NpcInfoType.TITLE_NPCSTRINGID)) {
            this.writeD(this._titleNpcString.getId());
        }
        if (this.containsMask(NpcInfoType.PVP_FLAG)) {
            this.writeC(this.pvp_flag);
        }
        if (this.containsMask(NpcInfoType.REPUTATION)) {
            this.writeD(this.karma);
        }
        if (this.containsMask(NpcInfoType.CLAN)) {
            this.writeD(this.clan_id);
            this.writeD(this.clan_crest_id);
            this.writeD(this.clan_large_crest_id);
            this.writeD(this.ally_id);
            this.writeD(this.ally_crest_id);
        }
        if (this.containsMask(NpcInfoType.VISUAL_STATE)) {
            int n = 0;
            if (this.incombat == 1) {
                n |= 1;
            }
            if (this.dead == 1) {
                n |= 2;
            }
            if (this._targetable) {
                n |= 4;
            }
            if (this._showName) {
                n |= 8;
            }
            this.writeC(n);
        }
        if (this.containsMask(NpcInfoType.ABNORMALS)) {
            this.writeH(this.abnormalEffects.length);
            for (AbnormalEffect abnormalEffect : this.abnormalEffects) {
                this.writeH(abnormalEffect.getClientId());
            }
        }
    }
}
