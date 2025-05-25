/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.templates;

import l2.gameserver.templates.DoorTemplate;

public static class InstantZone.DoorInfo {
    private final DoorTemplate a;
    private final boolean gW;
    private final boolean gX;

    public InstantZone.DoorInfo(DoorTemplate doorTemplate, boolean bl, boolean bl2) {
        this.a = doorTemplate;
        this.gW = bl;
        this.gX = bl2;
    }

    public DoorTemplate getTemplate() {
        return this.a;
    }

    public boolean isOpened() {
        return this.gW;
    }

    public boolean isInvul() {
        return this.gX;
    }
}
