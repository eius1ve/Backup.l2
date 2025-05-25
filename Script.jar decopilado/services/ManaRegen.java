/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.Config
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.SystemMessage
 *  l2.gameserver.scripts.Functions
 */
package services;

import l2.gameserver.Config;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.scripts.Functions;

public class ManaRegen
extends Functions {
    private static final int bFI = 57;

    public void DoManaRegen() {
        Player player = this.getSelf();
        long l = (long)Math.floor((double)player.getMaxMp() - player.getCurrentMp());
        long l2 = l * (long)Config.SERVICES_GIRAN_HARBOR_MP_REG_PRICE;
        if (l2 <= 0L) {
            player.sendPacket((IStaticPacket)SystemMsg.NOTHING_HAPPENED);
            return;
        }
        if (ManaRegen.getItemCount((Playable)player, (int)57) >= l2) {
            ManaRegen.removeItem((Playable)player, (int)57, (long)l2);
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_MP_HAS_BEEN_RESTORED).addNumber(l));
            player.setCurrentMp((double)player.getMaxMp());
        } else {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
        }
    }
}
