/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.ChatType;
import l2.gameserver.network.l2.components.NpcString;
import l2.gameserver.network.l2.components.SysString;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.NpcStringContainer;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Say2
extends NpcStringContainer {
    private static final int AV = 1;
    private static final int AW = 2;
    private static final int AX = 4;
    private static final int AY = 8;
    private static final int AZ = 16;
    private ChatType b;
    private SysString a;
    private SystemMsg a;
    private int fW;
    private String eM;
    private int pa;
    private int yl = -1;

    public Say2(int n, ChatType chatType, SysString sysString, SystemMsg systemMsg) {
        super(NpcString.NONE, new String[0]);
        this.fW = n;
        this.b = chatType;
        this.a = sysString;
        this.a = systemMsg;
    }

    public Say2(int n, ChatType chatType, String string, String string2) {
        this(n, chatType, string, NpcString.NONE, string2);
    }

    public Say2(int n, ChatType chatType, String string, NpcString npcString, String ... stringArray) {
        super(npcString, stringArray);
        this.fW = n;
        this.b = chatType;
        this.eM = string;
    }

    public void setCharName(String string) {
        this.eM = string;
    }

    public void setSenderInfo(Player player, Player player2) {
        this.yl = player.getLevel();
        if (player2.getFriendList().contains(player.getObjectId())) {
            this.pa |= 1;
        }
        if (player2.getClanId() > 0 && player2.getClanId() == player.getClanId()) {
            this.pa |= 2;
        }
        if (player2.getAllyId() > 0 && player2.getAllyId() == player.getAllyId()) {
            this.pa |= 8;
        }
        if (player.isGM()) {
            this.pa |= 0x10;
        }
    }

    @Override
    protected final void writeImpl() {
        this.writeC(74);
        this.writeD(this.fW);
        this.writeD(this.b.ordinal());
        switch (this.b) {
            case SYSTEM_MESSAGE: {
                this.writeD(this.a.getId());
                this.writeD(this.a.getId());
                break;
            }
            case TELL: {
                this.writeS(this.eM);
                this.writeD(this.getNpcString().getId());
                this.writeS(this.getParameters().length > 0 ? this.getParameters()[0] : "");
                this.writeC(this.pa);
                if ((this.pa & 0x10) != 0) break;
                this.writeC(this.yl);
                break;
            }
            case CLAN: 
            case ALLIANCE: {
                this.writeS(this.eM);
                this.writeElements();
                this.writeC(0);
                break;
            }
            default: {
                this.writeS(this.eM);
                this.writeElements();
            }
        }
    }
}
