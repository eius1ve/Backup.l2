/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.actor.instances.player;

import l2.commons.lang.reference.HardReference;
import l2.commons.lang.reference.HardReferences;
import l2.gameserver.model.Player;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.tables.ClanTable;

public class Friend {
    private final int ki;
    private String _name;
    private int ga;
    private int _level;
    private int fY;
    private final long bQ;
    private long aV;
    private String cW;
    private HardReference<Player> a = HardReferences.emptyRef();

    public Friend(int n, String string, int n2, int n3, int n4, long l, String string2, long l2) {
        this.ki = n;
        this._name = string;
        this.ga = n2;
        this._level = n3;
        this.fY = n4;
        this.bQ = l;
        this.cW = string2;
        this.aV = l2;
    }

    public Friend(Player player) {
        this.ki = player.getObjectId();
        this.bQ = player.getCreateTime();
        this.update(player, true);
    }

    public void update(Player player, boolean bl) {
        this._level = player.getLevel();
        this._name = player.getName();
        this.ga = player.getActiveClassId();
        this.fY = player.getClanId();
        this.a = bl ? player.getRef() : HardReferences.emptyRef();
        this.aV = System.currentTimeMillis();
    }

    public long getLastAccess() {
        if (this.isOnline()) {
            return System.currentTimeMillis();
        }
        return this.aV;
    }

    public long getCreateTime() {
        return this.bQ;
    }

    public int getClanId() {
        return this.fY;
    }

    public Clan getClan() {
        return ClanTable.getInstance().getClan(this.fY);
    }

    public String getName() {
        Player player = this.getPlayer();
        return player == null ? this._name : player.getName();
    }

    public int getObjectId() {
        return this.ki;
    }

    public int getClassId() {
        Player player = this.getPlayer();
        return player == null ? this.ga : player.getActiveClassId();
    }

    public int getLevel() {
        Player player = this.getPlayer();
        return player == null ? this._level : player.getLevel();
    }

    public boolean isOnline() {
        Player player = this.a.get();
        return player != null && !player.isInOfflineMode();
    }

    public Player getPlayer() {
        Player player = this.a.get();
        return player != null && !player.isInOfflineMode() ? player : null;
    }

    public String getMemo() {
        return this.cW;
    }

    public void setMemo(String string) {
        this.cW = string;
    }
}
