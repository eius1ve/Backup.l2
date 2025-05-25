/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.templates.moveroute;

import java.util.ArrayList;
import java.util.List;
import l2.gameserver.templates.moveroute.MoveNode;
import l2.gameserver.templates.moveroute.MoveRouteType;

public class MoveRoute {
    private final List<MoveNode> dA = new ArrayList<MoveNode>();
    private final String gy;
    private final MoveRouteType a;
    private final boolean hr;

    public MoveRoute(String string, MoveRouteType moveRouteType, boolean bl) {
        this.gy = string;
        this.a = moveRouteType;
        this.hr = bl;
    }

    public List<MoveNode> getNodes() {
        return this.dA;
    }

    public String getName() {
        return this.gy;
    }

    public MoveRouteType getType() {
        return this.a;
    }

    public boolean isRunning() {
        return this.hr;
    }
}
