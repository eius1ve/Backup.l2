/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.BlockListPacket;
import l2.gameserver.skills.AbnormalEffect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestBlock
extends L2GameClientPacket {
    private static final Logger cN = LoggerFactory.getLogger(RequestBlock.class);
    private static final int qm = 0;
    private static final int qn = 1;
    private static final int qo = 2;
    private static final int qp = 3;
    private static final int qq = 4;
    private Integer d;
    private String ea = null;

    @Override
    protected void readImpl() {
        this.d = this.readD();
        if (this.d == 0 || this.d == 1) {
            this.ea = this.readS(16);
        }
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        switch (this.d) {
            case 0: {
                player.getBlockList().addToBlockList(this.ea);
                break;
            }
            case 1: {
                player.getBlockList().removeFromBlockList(this.ea);
                break;
            }
            case 2: {
                player.sendPacket((IStaticPacket)new BlockListPacket(player));
                break;
            }
            case 3: {
                player.setBlockAll(true);
                player.startAbnormalEffect(AbnormalEffect.NO_CHAT);
                player.sendPacket((IStaticPacket)SystemMsg.YOU_ARE_NOW_BLOCKING_EVERYTHING);
                player.sendEtcStatusUpdate();
                break;
            }
            case 4: {
                player.setBlockAll(false);
                player.stopAbnormalEffect(AbnormalEffect.NO_CHAT);
                player.sendPacket((IStaticPacket)SystemMsg.YOU_ARE_NO_LONGER_BLOCKING_EVERYTHING);
                player.sendEtcStatusUpdate();
                break;
            }
            default: {
                cN.info("Unknown 0x0a block type: " + this.d);
            }
        }
    }
}
