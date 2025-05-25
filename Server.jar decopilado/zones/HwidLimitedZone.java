/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.listener.Listener
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.instancemanager.ReflectionManager
 *  l2.gameserver.listener.zone.OnZoneEnterLeaveListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Zone
 *  l2.gameserver.network.l2.GameClient
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage$ScreenMessageAlign
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.utils.Location
 *  org.apache.commons.lang3.StringUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package zones;

import java.util.Collection;
import l2.commons.listener.Listener;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.listener.zone.OnZoneEnterLeaveListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.Zone;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExShowScreenMessage;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.utils.Location;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HwidLimitedZone
implements ScriptFile {
    private static final Logger eB = LoggerFactory.getLogger(HwidLimitedZone.class);
    private static final String iS = "uniqHwidLimit";
    private static final String iT = "uniqHwidBackLoc";

    public void onLoad() {
        int n = 0;
        Collection collection = ReflectionManager.DEFAULT.getZones();
        for (Zone zone : collection) {
            final int n2 = zone.getParams().getInteger((Object)iS, 0);
            if (n2 <= 0) continue;
            String string = zone.getParams().getString((Object)iT, null);
            final Location location = !StringUtils.isEmpty((CharSequence)string) ? Location.parseLoc((String)string) : null;
            OnZoneEnterLeaveListener onZoneEnterLeaveListener = new OnZoneEnterLeaveListener(){

                private void aC(Player player) {
                    if (location != null) {
                        player.teleToLocation(location);
                    } else {
                        player.teleToClosestTown();
                    }
                }

                public void onZoneEnter(Zone zone, Creature creature) {
                    String string;
                    if (creature == null || !creature.isPlayer() || creature.getPlayer().isGM()) {
                        return;
                    }
                    final Player player = creature.getPlayer();
                    GameClient gameClient = player.getNetConnection();
                    if (gameClient == null || StringUtils.isBlank((CharSequence)(string = gameClient.getHwid()))) {
                        return;
                    }
                    int n = 1;
                    for (Player player2 : zone.getInsidePlayers()) {
                        String string2;
                        GameClient gameClient2;
                        if (player2 == null || player2 == player || (gameClient2 = player2.getNetConnection()) == null || StringUtils.isBlank((CharSequence)(string2 = gameClient2.getHwid())) || !StringUtils.equals((CharSequence)string, (CharSequence)string2) || ++n <= n2) continue;
                        ThreadPoolManager.getInstance().execute((Runnable)new RunnableImpl(){

                            public void runImpl() throws Exception {
                                String string = new CustomMessage("zone.services.HWIDLimitZone", player, new Object[0]).toString();
                                player.sendPacket((IStaticPacket)new ExShowScreenMessage(string, 10000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, false));
                                this.aC(player);
                            }
                        });
                    }
                }

                public void onZoneLeave(Zone zone, Creature creature) {
                }
            };
            zone.addListener((Listener)onZoneEnterLeaveListener);
            ++n;
        }
        if (n > 0) {
            eB.info("HwidLimitedZone: added {} uniq HWID zone(s).", (Object)n);
        }
    }

    public void onReload() {
        this.onShutdown();
        this.onLoad();
    }

    public void onShutdown() {
    }
}
