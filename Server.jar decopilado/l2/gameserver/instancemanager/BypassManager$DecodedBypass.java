/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.instancemanager;

import l2.gameserver.instancemanager.BypassManager;

public static class BypassManager.DecodedBypass {
    public String bypass;
    public boolean bbs;
    public BypassManager.BypassType bypassType;

    public BypassManager.DecodedBypass(String string, boolean bl, BypassManager.BypassType bypassType) {
        this.bypass = string;
        this.bbs = bl;
        this.bypassType = bypassType;
    }

    public BypassManager.DecodedBypass trim() {
        this.bypass = this.bypass.trim();
        return this;
    }
}
