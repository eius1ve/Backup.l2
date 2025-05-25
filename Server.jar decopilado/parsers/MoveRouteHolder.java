/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.data.xml.AbstractHolder
 *  l2.gameserver.templates.moveroute.MoveRoute
 */
package parsers;

import java.util.HashMap;
import java.util.Map;
import l2.commons.data.xml.AbstractHolder;
import l2.gameserver.templates.moveroute.MoveRoute;
import parsers.MoveRouteParser;

public class MoveRouteHolder
extends AbstractHolder {
    private static MoveRouteHolder a;
    private Map<String, MoveRoute> cc = new HashMap<String, MoveRoute>();

    public static MoveRouteHolder getInstance() {
        if (a == null) {
            a = new MoveRouteHolder();
            MoveRouteParser.getInstance();
        }
        return a;
    }

    private MoveRouteHolder() {
    }

    public void addRoute(MoveRoute moveRoute) {
        if (moveRoute.getNodes().isEmpty()) {
            this._log.warn("Route \"" + moveRoute.getName() + "\" is empty.");
        }
        this.cc.put(moveRoute.getName(), moveRoute);
    }

    public MoveRoute getRoute(String string) {
        return this.cc.get(string);
    }

    public int size() {
        return this.cc.size();
    }

    public void clear() {
        this.cc.clear();
    }
}
