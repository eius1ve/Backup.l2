/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.usercommands.impl;

import l2.gameserver.handler.usercommands.IUserCommandHandler;
import l2.gameserver.instancemanager.MapRegionManager;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.templates.mapregion.RestartArea;

public class LocCommand
implements IUserCommandHandler {
    private static final int[] aw = new int[]{0};

    @Override
    public boolean useUserCommand(int n, Player player) {
        SystemMsg systemMsg;
        RestartArea restartArea = MapRegionManager.getInstance().getRegionData(RestartArea.class, player);
        SystemMsg systemMsg2 = systemMsg = restartArea != null ? restartArea.getRestartPoint().get((Object)player.getRace()).getMessage() : SystemMsg.CURRENT_LOCATION__S1_S2_S3_NEAR_THE_NEUTRAL_ZONE;
        if (systemMsg.size() > 0) {
            player.sendPacket((IStaticPacket)((SystemMessage)((SystemMessage)new SystemMessage(systemMsg).addNumber(player.getX())).addNumber(player.getY())).addNumber(player.getZ()));
        } else {
            player.sendPacket((IStaticPacket)systemMsg);
        }
        return true;
    }

    @Override
    public final int[] getUserCommandList() {
        return aw;
    }
}
