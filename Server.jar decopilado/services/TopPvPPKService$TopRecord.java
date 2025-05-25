/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.data.StringHolder
 *  l2.gameserver.model.GameObjectsStorage
 *  l2.gameserver.model.Player
 */
package services;

import l2.gameserver.data.StringHolder;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;

private static class TopPvPPKService.TopRecord
implements Comparable<TopPvPPKService.TopRecord> {
    private final int bGh;
    private final int bGi;
    protected String _playerName;

    private TopPvPPKService.TopRecord(int n, String string, int n2) {
        this.bGh = n;
        this._playerName = string;
        this.bGi = n2;
    }

    protected int getPlayerObjectId() {
        return this.bGh;
    }

    protected Player getPlayer() {
        return GameObjectsStorage.getPlayer((int)this.bGh);
    }

    public String getPlayerName() {
        return this._playerName;
    }

    public boolean isOnline() {
        Player player = this.getPlayer();
        return player != null && player.isOnline();
    }

    public int getTopValue() {
        return this.bGi;
    }

    public String formatHtml(Player player) {
        String string = StringHolder.getInstance().getNotNull(player, this.isOnline() ? "services.TopPvPPKService.TopRecord.RecordHtmlPlayerOnline" : "services.TopPvPPKService.TopRecord.RecordHtmlPlayerOffline");
        string = string.replace("%name%", this.getPlayerName());
        string = string.replace("%val%", String.valueOf(this.getTopValue()));
        return string;
    }

    @Override
    public int compareTo(TopPvPPKService.TopRecord topRecord) {
        if (this.getPlayerObjectId() == topRecord.getPlayerObjectId()) {
            return 0;
        }
        return topRecord.getTopValue() - this.getTopValue();
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || !(object instanceof TopPvPPKService.TopRecord)) {
            return false;
        }
        return this.getPlayerObjectId() == ((TopPvPPKService.TopRecord)object).getPlayerObjectId();
    }
}
