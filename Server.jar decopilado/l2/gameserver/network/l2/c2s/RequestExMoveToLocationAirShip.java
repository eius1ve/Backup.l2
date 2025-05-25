/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;

public class RequestExMoveToLocationAirShip
extends L2GameClientPacket {
    private int qU;
    private int qV;
    private int qW;

    @Override
    protected void readImpl() {
        this.qU = this.readD();
        switch (this.qU) {
            case 4: {
                this.qV = this.readD() + 1;
                break;
            }
            case 0: {
                this.qV = this.readD();
                this.qW = this.readD();
                break;
            }
            case 2: {
                this.readD();
                this.readD();
                break;
            }
            case 3: {
                this.readD();
                this.readD();
            }
        }
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null || player.getBoat() == null) {
            return;
        }
    }
}
