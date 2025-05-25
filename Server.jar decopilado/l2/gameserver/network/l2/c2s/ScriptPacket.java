/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.ArrayUtils
 *  org.apache.commons.lang3.StringUtils
 */
package l2.gameserver.network.l2.c2s;

import java.util.List;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.scripts.Scripts;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class ScriptPacket
extends L2GameClientPacket {
    private List<Scripts.ScriptClassAndMethod> bQ;
    private Integer g;
    private byte[] r = ArrayUtils.EMPTY_BYTE_ARRAY;

    @Override
    protected void readImpl() throws Exception {
        int n = Math.min(this.getAvaliableBytes(), 16);
        if (n > 0) {
            this.r = new byte[n];
            this.readB(this.r);
        }
    }

    public List<Scripts.ScriptClassAndMethod> getHandlers() {
        return this.bQ;
    }

    public ScriptPacket setHandlers(List<Scripts.ScriptClassAndMethod> list) {
        this.bQ = list;
        return this;
    }

    public Integer getOp() {
        return this.g;
    }

    public ScriptPacket setOp(Integer n) {
        this.g = n;
        return this;
    }

    @Override
    protected void runImpl() throws Exception {
        List<Scripts.ScriptClassAndMethod> list = this.getHandlers();
        if (list == null) {
            return;
        }
        for (Scripts.ScriptClassAndMethod scriptClassAndMethod : list) {
            Scripts.getInstance().callScripts(scriptClassAndMethod.className, scriptClassAndMethod.methodName, new Object[]{this});
        }
    }

    @Override
    public String getType() {
        if (this.bQ == null) {
            return super.getType();
        }
        return super.getType() + "[" + StringUtils.join(this.bQ, (char)',') + "]";
    }
}
