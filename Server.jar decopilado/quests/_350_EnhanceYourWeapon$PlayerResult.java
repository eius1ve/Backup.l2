/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Player
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 */
package quests;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;

private static class _350_EnhanceYourWeapon.PlayerResult {
    private final Player y;
    private SystemMsg _message;

    public _350_EnhanceYourWeapon.PlayerResult(Player player) {
        this.y = player;
    }

    public Player getPlayer() {
        return this.y;
    }

    public SystemMsg getMessage() {
        return this._message;
    }

    public void setMessage(SystemMsg systemMsg) {
        this._message = systemMsg;
    }

    public void send() {
        if (this._message != null) {
            this.y.sendPacket((IStaticPacket)this._message);
        }
    }
}
