/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SysMsgContainer;

public class ConfirmDlg
extends SysMsgContainer<ConfirmDlg> {
    private final int tF;
    private int qa;

    public ConfirmDlg(SystemMsg systemMsg, int n) {
        super(systemMsg);
        this.tF = n;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(243);
        this.writeD(this._message.id());
        this.writeD(this._arguments.size());
        for (SysMsgContainer.IArgument iArgument : this._arguments) {
            this.writeD(iArgument.getType().ordinal());
            iArgument.writeData(this);
        }
        this.writeD(this.tF);
        this.writeD(this.qa);
    }

    public void setRequestId(int n) {
        this.qa = n;
    }
}
