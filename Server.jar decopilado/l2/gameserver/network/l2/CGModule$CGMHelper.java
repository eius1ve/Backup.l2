/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.tuple.Pair
 */
package l2.gameserver.network.l2;

import java.util.HashSet;
import java.util.ServiceLoader;
import java.util.Set;
import l2.gameserver.Config;
import l2.gameserver.network.l2.CGModule;
import org.apache.commons.lang3.tuple.Pair;

protected static class CGModule.CGMHelper {
    private static final CGModule a = CGModule.CGMHelper.a();

    protected CGModule.CGMHelper() {
    }

    private static CGModule a() {
        return CGModule.CGMHelper.getModuleAndInit(CGModule.CGMHelper.getHelperConfig(Config.ALT_CG_MODULE));
    }

    public static CGModule getModuleAndInit(Pair<String, String> pair) {
        CGModule cGModule = CGModule.CGMHelper.getModule(pair);
        if (cGModule != null) {
            return cGModule.init((String)pair.getRight());
        }
        throw new IllegalStateException("CGModule \"" + Config.ALT_CG_MODULE + "\" not found");
    }

    public static CGModule getModule(Pair<String, String> pair) {
        for (CGModule cGModule : CGModule.CGMHelper.getAllHelpers()) {
            if (!((String)pair.getKey()).equalsIgnoreCase(cGModule.getName())) continue;
            return cGModule;
        }
        return null;
    }

    public static Pair<String, String> getHelperConfig(String string) {
        int n = string.indexOf(40);
        int n2 = string.lastIndexOf(41);
        if (n >= 0 && n2 > n) {
            return Pair.of((Object)string.substring(0, n), (Object)string.substring(n + 1, n2).trim());
        }
        return Pair.of((Object)string, null);
    }

    public static Set<CGModule> getAllHelpers() {
        ServiceLoader<CGModule> serviceLoader = ServiceLoader.load(CGModule.class);
        HashSet<CGModule> hashSet = new HashSet<CGModule>();
        hashSet.add(new CGModule.CGDefaultModule());
        for (CGModule cGModule : serviceLoader) {
            if (cGModule == null) continue;
            hashSet.add(cGModule);
        }
        return hashSet;
    }
}
