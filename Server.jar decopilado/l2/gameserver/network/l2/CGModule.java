/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.tuple.Pair
 */
package l2.gameserver.network.l2;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.ServiceLoader;
import java.util.Set;
import l2.commons.logging.LoggerObject;
import l2.gameserver.Config;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.network.authcomm.AuthServerCommunication;
import l2.gameserver.network.authcomm.gs2as.ChangeAccessLevelMulti;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.GameCrypt;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import org.apache.commons.lang3.tuple.Pair;

public abstract class CGModule<CGCtx extends CGContext, CGKey>
extends LoggerObject {
    public static CGModule getInstance() {
        return CGMHelper.a;
    }

    public abstract String getName();

    public abstract CGModule init(String var1);

    public abstract void done();

    public abstract boolean isActive();

    public abstract CGCtx initClient(GameClient var1);

    public abstract void doneClient(CGCtx var1);

    public abstract GameCrypt<CGCtx, CGKey> initCrypt(CGCtx var1);

    final L2GameClientPacket handlePacket(GameClient gameClient, int n) {
        return this.handlePacket(gameClient.getCGMContext(), n, null);
    }

    final L2GameClientPacket handlePacket(GameClient gameClient, int n, Integer n2) {
        return this.handlePacket(gameClient.getCGMContext(), n, n2);
    }

    public abstract L2GameClientPacket handlePacket(CGCtx var1, int var2, Integer var3);

    public abstract String getHWIDText(CGCtx var1);

    public final void addHWIDBan(GameClient gameClient, String string, String string2) {
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<String>();
        if (gameClient == null || !gameClient.isConnected()) {
            for (Player player : GameObjectsStorage.getAllPlayersForIterate()) {
                GameClient gameClient2;
                if (player == null || (gameClient2 = player.getNetConnection()) == null || !gameClient2.isConnected() || !string.equals(gameClient2.getHwid())) continue;
                this.addHWIDBan(gameClient2, string, string2);
                return;
            }
            this.warn("No client to ban by HWID \"" + string + "\"");
            return;
        }
        if (string != null && !string.isEmpty()) {
            for (Player player : GameObjectsStorage.getAllPlayersForIterate()) {
                GameClient gameClient3;
                if (player == null || (gameClient3 = player.getNetConnection()) == null || !gameClient3.isConnected() || gameClient3.getHwid() == null || gameClient3.getHwid().isEmpty() || !string.equals(gameClient3.getHwid()) || gameClient3.getLogin() == null || gameClient3.getLogin().isEmpty()) continue;
                linkedHashSet.add(gameClient3.getLogin());
            }
        }
        if (!linkedHashSet.isEmpty()) {
            AuthServerCommunication.getInstance().sendPacket(new ChangeAccessLevelMulti(linkedHashSet, -100, 0));
        }
        String string3 = gameClient.getLogin();
        this.addHWIDBan(gameClient.getCGMContext(), string, gameClient.getIpAddr(), string3, string2);
    }

    public abstract void addHWIDBan(CGCtx var1, String var2, String var3, String var4, String var5);

    protected static class CGMHelper {
        private static final CGModule a = CGMHelper.a();

        protected CGMHelper() {
        }

        private static CGModule a() {
            return CGMHelper.getModuleAndInit(CGMHelper.getHelperConfig(Config.ALT_CG_MODULE));
        }

        public static CGModule getModuleAndInit(Pair<String, String> pair) {
            CGModule cGModule = CGMHelper.getModule(pair);
            if (cGModule != null) {
                return cGModule.init((String)pair.getRight());
            }
            throw new IllegalStateException("CGModule \"" + Config.ALT_CG_MODULE + "\" not found");
        }

        public static CGModule getModule(Pair<String, String> pair) {
            for (CGModule cGModule : CGMHelper.getAllHelpers()) {
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
            hashSet.add(new CGDefaultModule());
            for (CGModule cGModule : serviceLoader) {
                if (cGModule == null) continue;
                hashSet.add(cGModule);
            }
            return hashSet;
        }
    }

    public static class CGContext {
        private GameClient b;

        public CGContext(GameClient gameClient) {
            this.b = gameClient;
        }

        public GameClient getGameClient() {
            return this.b;
        }

        public void setGameClient(GameClient gameClient) {
            this.b = gameClient;
        }
    }

    public static class CGDefaultModule
    extends CGModule<CGContext, byte[]> {
        @Override
        public String getName() {
            return "None";
        }

        @Override
        public CGModule init(String string) {
            this.info("Initializing default CGModule.");
            return this;
        }

        @Override
        public void done() {
        }

        @Override
        public boolean isActive() {
            return false;
        }

        @Override
        public CGContext initClient(GameClient gameClient) {
            return new CGContext(gameClient);
        }

        @Override
        public void doneClient(CGContext cGContext) {
            cGContext.setGameClient(null);
        }

        @Override
        public GameCrypt<CGContext, byte[]> initCrypt(CGContext cGContext) {
            return new GameCrypt<CGContext, byte[]>(){};
        }

        @Override
        public L2GameClientPacket handlePacket(CGContext cGContext, int n, Integer n2) {
            return null;
        }

        @Override
        public String getHWIDText(CGContext cGContext) {
            return null;
        }

        @Override
        public void addHWIDBan(CGContext cGContext, String string, String string2, String string3, String string4) {
        }
    }
}
