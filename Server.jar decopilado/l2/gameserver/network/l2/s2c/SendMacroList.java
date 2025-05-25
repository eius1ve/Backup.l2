/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.actor.instances.player.Macro;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.MacroUpdateType;

public class SendMacroList
extends L2GameServerPacket {
    private final MacroUpdateType a;
    private final int Bc;
    private final Macro b;

    public SendMacroList(MacroUpdateType macroUpdateType, int n, Macro macro) {
        this.a = macroUpdateType;
        this.Bc = n;
        this.b = macro;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(232);
        this.writeC(this.a.getId());
        this.writeD(this.a != MacroUpdateType.LIST ? this.b.id : 0);
        this.writeC(this.Bc);
        this.writeC(this.b != null ? 1 : 0);
        if (this.b != null && this.a != MacroUpdateType.DELETE) {
            this.writeD(this.b.id);
            this.writeS(this.b.name);
            this.writeS(this.b.descr);
            this.writeS(this.b.acronym);
            this.writeD(this.b.icon);
            this.writeC(this.b.commands.length);
            for (int i = 0; i < this.b.commands.length; ++i) {
                Macro.L2MacroCmd l2MacroCmd = this.b.commands[i];
                this.writeC(i + 1);
                this.writeC(l2MacroCmd.type);
                this.writeD(l2MacroCmd.d1);
                this.writeC(l2MacroCmd.d2);
                this.writeS(l2MacroCmd.cmd);
            }
        }
    }
}
