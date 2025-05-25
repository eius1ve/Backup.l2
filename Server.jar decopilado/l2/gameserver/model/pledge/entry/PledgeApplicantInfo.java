/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.pledge.entry;

import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;

public class PledgeApplicantInfo {
    private final int ph;
    private final int pi;
    private String _playerName;
    private int pj;
    private int ga;
    private final int pk;
    private final String dL;

    public PledgeApplicantInfo(int n, String string, int n2, int n3, int n4, String string2) {
        this.ph = n;
        this.pi = n4;
        this._playerName = string;
        this.pj = n2;
        this.pk = n3;
        this.dL = string2;
    }

    public int getPlayerId() {
        return this.ph;
    }

    public int getRequestClanId() {
        return this.pi;
    }

    public String getPlayerName() {
        if (this.isOnline() && !this.getPlayer().getName().equalsIgnoreCase(this._playerName)) {
            this._playerName = this.getPlayer().getName();
        }
        return this._playerName;
    }

    public int getPlayerLvl() {
        if (this.isOnline() && this.getPlayer().getLevel() != this.pj) {
            this.pj = this.getPlayer().getLevel();
        }
        return this.pj;
    }

    public int getClassId() {
        if (this.isOnline() && this.getPlayer().getBaseClassId() != this.ga) {
            this.ga = this.getPlayer().getClassId().getId();
        }
        return this.ga;
    }

    public String getMessage() {
        return this.dL;
    }

    public int getKarma() {
        return this.pk;
    }

    public Player getPlayer() {
        return GameObjectsStorage.getPlayer(this.ph);
    }

    public boolean isOnline() {
        return this.getPlayer() != null && this.getPlayer().isOnline();
    }
}
