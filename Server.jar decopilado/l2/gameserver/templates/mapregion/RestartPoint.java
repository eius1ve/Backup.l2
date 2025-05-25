/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.templates.mapregion;

import java.util.List;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.utils.Location;

public class RestartPoint {
    private final String gw;
    private final int GX;
    private final SystemMsg e;
    private final List<Location> dy;
    private final List<Location> dz;

    public RestartPoint(String string, int n, int n2, List<Location> list, List<Location> list2) {
        this.gw = string;
        this.GX = n;
        this.e = SystemMsg.valueOf(n2);
        this.dy = list;
        this.dz = list2;
    }

    public String getName() {
        return this.gw;
    }

    public int getBbs() {
        return this.GX;
    }

    public SystemMsg getMessage() {
        return this.e;
    }

    public List<Location> getRestartPoints() {
        return this.dy;
    }

    public List<Location> getPKrestartPoints() {
        return this.dz;
    }
}
