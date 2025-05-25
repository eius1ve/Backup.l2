/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.templates;

import java.util.List;
import l2.gameserver.templates.spawn.SpawnTemplate;

public static class InstantZone.SpawnInfo2 {
    private final List<SpawnTemplate> dq;
    private final boolean gY;

    public InstantZone.SpawnInfo2(List<SpawnTemplate> list, boolean bl) {
        this.dq = list;
        this.gY = bl;
    }

    public List<SpawnTemplate> getTemplates() {
        return this.dq;
    }

    public boolean isSpawned() {
        return this.gY;
    }
}
