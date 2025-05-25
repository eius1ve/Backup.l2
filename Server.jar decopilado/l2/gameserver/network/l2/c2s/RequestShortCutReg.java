/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.model.Player;
import l2.gameserver.model.actor.instances.player.ShortCut;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ShortCutRegister;

public class RequestShortCutReg
extends L2GameClientPacket {
    private int _type;
    private int _id;
    private int kk;
    private int kl;
    private int sb;
    private int ko;

    @Override
    protected void readImpl() {
        this._type = this.readD();
        int n = this.readD();
        this._id = this.readD();
        this.sb = this.readD();
        this.ko = this.readD();
        this.kk = n % 12;
        this.kl = n / 12;
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        if (this.kl < 0 || this.kl > 19) {
            player.sendActionFailed();
            return;
        }
        switch (this._type) {
            case 2: {
                this.sb = player.getSkillLevel(this._id);
                break;
            }
            default: {
                this.sb = 0;
            }
        }
        ShortCut shortCut = new ShortCut(this.kk, this.kl, this._type, this._id, this.sb, this.ko);
        player.sendPacket((IStaticPacket)new ShortCutRegister(player, shortCut));
        player.registerShortCut(shortCut);
    }
}
