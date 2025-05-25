/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.templates;

import l2.gameserver.templates.ZoneTemplate;

public static class InstantZone.ZoneInfo {
    private final ZoneTemplate b;
    private final boolean gZ;

    public InstantZone.ZoneInfo(ZoneTemplate zoneTemplate, boolean bl) {
        this.b = zoneTemplate;
        this.gZ = bl;
    }

    public ZoneTemplate getTemplate() {
        return this.b;
    }

    public boolean isActive() {
        return this.gZ;
    }
}
