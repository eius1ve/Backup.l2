/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.actor.recorder;

import l2.commons.collections.CollectionUtils;
import l2.gameserver.Config;
import l2.gameserver.model.Player;
import l2.gameserver.model.actor.recorder.CharStatsChangeRecorder;
import l2.gameserver.model.base.Element;
import l2.gameserver.model.matching.MatchingRoom;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExStorageMaxCount;
import l2.gameserver.network.l2.s2c.ExUserInfoAbnormalVisualEffect;
import l2.gameserver.network.l2.s2c.ExUserInfoCubic;
import l2.gameserver.network.l2.s2c.ExUserInfoInvenWeight;
import l2.gameserver.skills.AbnormalEffect;

public final class PlayerStatsChangeRecorder
extends CharStatsChangeRecorder<Player> {
    public static final int BROADCAST_KARMA = 8;
    public static final int SEND_STORAGE_INFO = 16;
    public static final int SEND_MAX_LOAD = 32;
    public static final int SEND_CUR_LOAD = 64;
    public static final int BROADCAST_CHAR_INFO2 = 128;
    public static final int SEND_CUBIC = 256;
    public static final int SEND_ABNORMAL_EFFECTS = 512;
    private int kq;
    private int hJ;
    private int kr;
    private final int[] aC = new int[Element.VALUES.length];
    private final int[] aD = new int[Element.VALUES.length];
    private long _exp;
    private int _sp;
    private int gh;
    private int gi;
    private int gj;
    private int ks;
    private int kt;
    private int ku;
    private int kv;
    private int kw;
    private int kx;
    private int ky;
    private AbnormalEffect[] abnormalEffects;
    private String _title = "";
    private int kz;

    public PlayerStatsChangeRecorder(Player player) {
        super(player);
    }

    @Override
    protected void refreshStats() {
        this.kq = this.set(4, this.kq, ((Player)this._activeChar).getMaxCp());
        super.refreshStats();
        this.hJ = this.set(34, this.hJ, ((Player)this._activeChar).getMaxLoad());
        this.kr = this.set(64, this.kr, ((Player)this._activeChar).getCurrentLoad());
        for (Element element : Element.VALUES) {
            this.aC[element.getId()] = this.set(2, this.aC[element.getId()], ((Player)this._activeChar).getAttack(element));
            this.aD[element.getId()] = this.set(2, this.aD[element.getId()], ((Player)this._activeChar).getDefence(element));
        }
        this._exp = this.set(2, this._exp, ((Player)this._activeChar).getExp());
        this._sp = this.set(2, this._sp, ((Player)this._activeChar).getIntSp());
        this.gi = this.set(2, this.gi, ((Player)this._activeChar).getPkKills());
        this.gj = this.set(2, this.gj, ((Player)this._activeChar).getPvpKills());
        this.gh = this.set(8, this.gh, ((Player)this._activeChar).getKarma());
        this.abnormalEffects = this.set(512, this.abnormalEffects, ((Player)this._activeChar).getAbnormalEffects());
        this.ks = this.set(16, this.ks, ((Player)this._activeChar).getInventoryLimit());
        this.kt = this.set(16, this.kt, ((Player)this._activeChar).getWarehouseLimit());
        this.ku = this.set(16, this.ku, Config.WAREHOUSE_SLOTS_CLAN);
        this.kv = this.set(16, this.kv, ((Player)this._activeChar).getTradeLimit());
        this.kw = this.set(16, this.kw, ((Player)this._activeChar).getDwarvenRecipeLimit());
        this.kx = this.set(16, this.kx, ((Player)this._activeChar).getCommonRecipeLimit());
        this.kz = this.set(256, this.kz, CollectionUtils.hashCode(((Player)this._activeChar).getCubics()));
        this.ky = this.set(1, this.ky, ((Player)this._activeChar).getMatchingRoom() != null && ((Player)this._activeChar).getMatchingRoom().getType() == MatchingRoom.PARTY_MATCHING && ((Player)this._activeChar).getMatchingRoom().getLeader() == this._activeChar ? ((Player)this._activeChar).getMatchingRoom().getId() : 0);
        this._team = this.set(128, this._team, ((Player)this._activeChar).getTeam());
        this._title = this.set(1, this._title, ((Player)this._activeChar).getTitle());
    }

    @Override
    protected void onSendChanges() {
        super.onSendChanges();
        if ((this._changes & 0x80) == 128) {
            ((Player)this._activeChar).broadcastCharInfo();
            if (((Player)this._activeChar).getPet() != null) {
                ((Player)this._activeChar).getPet().broadcastCharInfo();
            }
        }
        if ((this._changes & 1) == 1) {
            ((Player)this._activeChar).broadcastCharInfo();
        } else if ((this._changes & 2) == 2) {
            ((Player)this._activeChar).sendUserInfo(false);
        }
        if ((this._changes & 0x40) == 64 || (this._changes & 0x20) == 32) {
            ((Player)this._activeChar).sendPacket((IStaticPacket)new ExUserInfoInvenWeight((Player)this._activeChar));
        }
        if ((this._changes & 0x100) == 256) {
            ((Player)this._activeChar).sendPacket((IStaticPacket)new ExUserInfoCubic((Player)this._activeChar));
        }
        if ((this._changes & 0x200) == 512) {
            ((Player)this._activeChar).sendPacket((IStaticPacket)new ExUserInfoAbnormalVisualEffect((Player)this._activeChar));
        }
        if ((this._changes & 8) == 8) {
            ((Player)this._activeChar).sendStatusUpdate(true, false, 27);
        }
        if ((this._changes & 0x10) == 16) {
            ((Player)this._activeChar).sendPacket((IStaticPacket)new ExStorageMaxCount((Player)this._activeChar));
        }
    }
}
