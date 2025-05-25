/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.usercommands.impl;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import l2.gameserver.Config;
import l2.gameserver.GameTimeController;
import l2.gameserver.handler.usercommands.IUserCommandHandler;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;

public class Time
implements IUserCommandHandler {
    private static final int[] az = new int[]{77};
    private static final NumberFormat d = NumberFormat.getInstance(Locale.ENGLISH);
    private static final SimpleDateFormat c = new SimpleDateFormat("H:mm");

    @Override
    public boolean useUserCommand(int n, Player player) {
        if (az[0] != n) {
            return false;
        }
        int n2 = GameTimeController.getInstance().getGameHour();
        int n3 = GameTimeController.getInstance().getGameMin();
        SystemMessage systemMessage = GameTimeController.getInstance().isNowNight() ? new SystemMessage(SystemMsg.ID928_THE_CURRENT_TIME_IS_S1S2) : new SystemMessage(SystemMsg.THE_CURRENT_TIME_IS_S1S2);
        ((SystemMessage)systemMessage.addString(d.format(n2))).addString(d.format(n3));
        player.sendPacket((IStaticPacket)systemMessage);
        if (Config.ALT_SHOW_SERVER_TIME) {
            player.sendMessage(new CustomMessage("usercommandhandlers.Time.ServerTime", player, c.format(new Date(System.currentTimeMillis()))));
        }
        return true;
    }

    @Override
    public final int[] getUserCommandList() {
        return az;
    }

    static {
        d.setMinimumIntegerDigits(2);
    }
}
