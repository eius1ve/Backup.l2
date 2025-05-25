/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.listener.Listener
 *  l2.gameserver.model.actor.listener.CharListenerList
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
import config.GabConfig;
import dressmeEngine.A0;
import java.util.Base64;
import l2.commons.listener.Listener;
import l2.gameserver.model.actor.listener.CharListenerList;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GabrielModsInit
extends Functions
implements ScriptFile {
    private static final Logger F = LoggerFactory.getLogger(GabrielModsInit.class);
    protected static GabrielModsInit Z;

    public static GabrielModsInit getInstance() {
        if (Z == null) {
            Z = new GabrielModsInit();
        }
        return Z;
    }

    public static String A(String str) {
        return new String(Base64.getDecoder().decode(str));
    }

    public void onLoad() {
        F.info("Wardrobe Mod Init \ud83d\udc57");
        GabConfig.getInstance().load();
        CharListenerList.addGlobal((Listener)new WardrobePlayer());
        A0.F();
    }

    public void onReload() {
    }

    public void onShutdown() {
    }
}
