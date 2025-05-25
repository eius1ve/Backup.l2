/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.pledge.entry;

import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;

public class PledgeWaitingInfo {
    private int ph;
    private int lP;
    private int pj;
    private final int pn;
    private String _playerName;

    public PledgeWaitingInfo(int n, int n2, int n3, int n4, String string) {
        this.ph = n;
        this.lP = n4;
        this.pj = n2;
        this.pn = n3;
        this._playerName = string;
    }

    public int getPlayerId() {
        return this.ph;
    }

    public void setPlayerId(int n) {
        this.ph = n;
    }

    public int getPlayerClassId() {
        if (this.isOnline() && this.getPlayer().getBaseClassId() != this.lP) {
            this.lP = this.getPlayer().getClassId().getId();
        }
        return this.lP;
    }

    public int getPlayerLvl() {
        if (this.isOnline() && this.getPlayer().getLevel() != this.pj) {
            this.pj = this.getPlayer().getLevel();
        }
        return this.pj;
    }

    public int getKarma() {
        return this.pn;
    }

    public String getPlayerName() {
        if (this.isOnline() && !this.getPlayer().getName().equalsIgnoreCase(this._playerName)) {
            this._playerName = this.getPlayer().getName();
        }
        return this._playerName;
    }

    public Player getPlayer() {
        return GameObjectsStorage.getPlayer(this.ph);
    }

    public boolean isOnline() {
        return this.getPlayer() != null && this.getPlayer().isOnline();
    }
}
