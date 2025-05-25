/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.events.actions;

import java.util.List;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.events.EventAction;
import l2.gameserver.model.entity.events.GlobalEvent;
import l2.gameserver.network.l2.components.ChatType;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.NpcString;
import l2.gameserver.network.l2.components.SysString;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.Say2;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class SayAction
implements EventAction {
    private int lt;
    private ChatType a;
    private String df;
    private NpcString a;
    private SysString a;
    private SystemMsg a;

    protected SayAction(int n, ChatType chatType) {
        this.lt = n;
        this.a = chatType;
    }

    public SayAction(int n, ChatType chatType, SysString sysString, SystemMsg systemMsg) {
        this(n, chatType);
        this.a = sysString;
        this.a = systemMsg;
    }

    public SayAction(int n, ChatType chatType, String string, NpcString npcString) {
        this(n, chatType);
        this.a = npcString;
        this.df = string;
    }

    @Override
    public void call(GlobalEvent globalEvent) {
        List<Player> list = globalEvent.broadcastPlayers(this.lt);
        for (Player player : list) {
            this.Y(player);
        }
    }

    private void Y(Player player) {
        if (player == null) {
            return;
        }
        Say2 say2 = null;
        say2 = this.a != null ? new Say2(0, this.a, this.a, this.a) : new Say2(0, this.a, this.df, this.a, new String[0]);
        player.sendPacket((IStaticPacket)say2);
    }
}
