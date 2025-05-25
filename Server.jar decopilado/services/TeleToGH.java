/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.listener.Listener
 *  l2.gameserver.Config
 *  l2.gameserver.handler.bypass.INpcHtmlAppendHandler
 *  l2.gameserver.instancemanager.ReflectionManager
 *  l2.gameserver.listener.zone.OnZoneEnterLeaveListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Zone
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.utils.Location
 *  l2.gameserver.utils.PositionUtils
 *  l2.gameserver.utils.ReflectionUtils
 *  org.apache.commons.lang3.ArrayUtils
 */
package services;

import l2.commons.listener.Listener;
import l2.gameserver.Config;
import l2.gameserver.handler.bypass.INpcHtmlAppendHandler;
import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.listener.zone.OnZoneEnterLeaveListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.Zone;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.utils.Location;
import l2.gameserver.utils.PositionUtils;
import l2.gameserver.utils.ReflectionUtils;
import org.apache.commons.lang3.ArrayUtils;

public class TeleToGH
extends Functions
implements INpcHtmlAppendHandler,
ScriptFile {
    private static Zone _zone = ReflectionUtils.getZone((String)"[giran_harbor_offshore]");
    private static ZoneListener a;
    private static final String hY;
    private static final String hZ;
    private static final String ia;
    private static final String ib;

    public void onLoad() {
        if (!Config.SERVICES_GIRAN_HARBOR_ENABLED) {
            return;
        }
        ReflectionManager.GIRAN_HARBOR.setCoreLoc(new Location(47416, 186568, -3480));
        a = new ZoneListener();
        _zone.addListener((Listener)a);
        _zone.setReflection(ReflectionManager.GIRAN_HARBOR);
        _zone.setActive(true);
        Zone zone = ReflectionUtils.getZone((String)"[giran_harbor_peace_alt]");
        zone.setReflection(ReflectionManager.GIRAN_HARBOR);
        zone.setActive(true);
        zone = ReflectionUtils.getZone((String)"[giran_harbor_no_trade]");
        zone.setReflection(ReflectionManager.GIRAN_HARBOR);
        zone.setActive(true);
    }

    public void onReload() {
        _zone.removeListener((Listener)a);
    }

    public void onShutdown() {
    }

    public void toGH() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        if (player.getAdena() < (long)Config.SERVICES_GIRAN_HARBOR_PRICE) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
            return;
        }
        player.reduceAdena((long)Config.SERVICES_GIRAN_HARBOR_PRICE, true);
        player.setVar("backCoords", player.getLoc().toXYZString(), -1L);
        player.teleToLocation(Location.findPointToStay((Location)_zone.getSpawn(), (int)30, (int)200, (int)ReflectionManager.GIRAN_HARBOR.getGeoIndex()), ReflectionManager.GIRAN_HARBOR);
    }

    public void fromGH() {
        Player player = this.getSelf();
        NpcInstance npcInstance = this.getNpc();
        if (player == null || npcInstance == null) {
            return;
        }
        if (!NpcInstance.canBypassCheck((Player)player, (NpcInstance)npcInstance)) {
            return;
        }
        String string = player.getVar("backCoords");
        if (string == null || string.equals("")) {
            this.teleOut();
            return;
        }
        player.teleToLocation(Location.parseLoc((String)string), 0);
    }

    public void teleOut() {
        Player player = this.getSelf();
        NpcInstance npcInstance = this.getNpc();
        if (player == null || npcInstance == null) {
            return;
        }
        player.teleToLocation(46776, 185784, -3528, 0);
        TeleToGH.show((String)(player.isLangRus() ? "\u042f \u043d\u0435 \u0437\u043d\u0430\u044e, \u043a\u0430\u043a \u0412\u044b \u043f\u043e\u043f\u0430\u043b\u0438 \u0441\u044e\u0434\u0430, \u043d\u043e \u044f \u043c\u043e\u0433\u0443 \u0412\u0430\u0441 \u043e\u0442\u043f\u0440\u0430\u0432\u0438\u0442\u044c \u0437\u0430 \u043e\u0433\u0440\u0430\u0436\u0434\u0435\u043d\u0438\u0435." : "I don't know from where you came here, but I can teleport you the another border side."), (Player)player, (NpcInstance)npcInstance, (Object[])new Object[0]);
    }

    public String getHtmlAppends(Integer n) {
        if (n != 0 || !Config.SERVICES_GIRAN_HARBOR_ENABLED) {
            return "";
        }
        Player player = this.getSelf();
        if (player == null) {
            return "";
        }
        return player.isLangRus() ? hZ : hY;
    }

    public String getHtmlAppends2(Integer n) {
        if (n != 0 || !Config.SERVICES_GIRAN_HARBOR_ENABLED) {
            return "";
        }
        Player player = this.getSelf();
        if (player == null || player.getReflectionId() != -1) {
            return "";
        }
        return player.isLangRus() ? ib : ia;
    }

    public int[] getNpcIds() {
        return ArrayUtils.addAll((int[])Config.SERVICES_GIRAN_HARBOR_NPC_ID, (int[])Config.SERVICES_GIRAN_HARBOR_EXIT_NPC_ID);
    }

    public String getAppend(Player player, int n, int n2) {
        TeleToGH teleToGH = new TeleToGH();
        teleToGH.self = player.getRef();
        if (ArrayUtils.contains((int[])Config.SERVICES_GIRAN_HARBOR_NPC_ID, (int)n)) {
            return teleToGH.getHtmlAppends(n2);
        }
        if (ArrayUtils.contains((int[])Config.SERVICES_GIRAN_HARBOR_EXIT_NPC_ID, (int)n)) {
            return teleToGH.getHtmlAppends2(n2);
        }
        return "";
    }

    static {
        hY = Config.SERVICES_GIRAN_HARBOR_PRICE > 0 ? "<br>[scripts_services.TeleToGH:toGH @811;Giran Harbor|\"Move to Giran Harbor (offshore zone) - " + Config.SERVICES_GIRAN_HARBOR_PRICE + " Adena.\"]" : "<br>[scripts_services.TeleToGH:toGH @811;Giran Harbor|\"Move to Giran Harbor (offshore zone)\"]";
        hZ = Config.SERVICES_GIRAN_HARBOR_PRICE > 0 ? "<br>[scripts_services.TeleToGH:toGH @811;Giran Harbor|\"Giran Harbor (\u0442\u043e\u0440\u0433\u043e\u0432\u0430\u044f \u0437\u043e\u043d\u0430 \u0431\u0435\u0437 \u043d\u0430\u043b\u043e\u0433\u043e\u0432) - " + Config.SERVICES_GIRAN_HARBOR_PRICE + " Adena.\"]" : "<br>[scripts_services.TeleToGH:toGH @811;Giran Harbor|\"Giran Harbor (\u0442\u043e\u0440\u0433\u043e\u0432\u0430\u044f \u0437\u043e\u043d\u0430 \u0431\u0435\u0437 \u043d\u0430\u043b\u043e\u0433\u043e\u0432)\"]";
        ia = Config.SERVICES_GIRAN_HARBOR_MP_REG_PRICE > 0 ? "Altar Gatekeeper:<center><br>[scripts_services.ManaRegen:DoManaRegen|Full MP Regeneration. (1 MP for " + Config.SERVICES_GIRAN_HARBOR_MP_REG_PRICE + " Adena)]<br>[scripts_services.TeleToGH:fromGH @811;From Giran Harbor|\"Exit the Giran Harbor.\"]<br></center>" : "Altar Gatekeeper:<center><br>[scripts_services.ManaRegen:DoManaRegen|Full MP Regeneration.]<br>[scripts_services.TeleToGH:fromGH @811;From Giran Harbor|\"Exit the Giran Harbor.\"]<br></center>";
        ib = Config.SERVICES_GIRAN_HARBOR_MP_REG_PRICE > 0 ? "Altar Gatekeeper:<center><br>[scripts_services.ManaRegen:DoManaRegen|\u041f\u043e\u043b\u043d\u043e\u0435 \u0432\u043e\u0441\u0441\u0442\u0430\u043d\u043e\u0432\u043b\u0435\u043d\u0438\u0435 MP. (1 MP \u0437\u0430 " + Config.SERVICES_GIRAN_HARBOR_MP_REG_PRICE + " Adena)]<br>[scripts_services.TeleToGH:fromGH @811;From Giran Harbor|\"\u041f\u043e\u043a\u0438\u043d\u0443\u0442\u044c Giran Harbor.\"]<br></center>" : "Altar Gatekeeper:<center><br>[scripts_services.ManaRegen:DoManaRegen|\u041f\u043e\u043b\u043d\u043e\u0435 \u0432\u043e\u0441\u0441\u0442\u0430\u043d\u043e\u0432\u043b\u0435\u043d\u0438\u0435 MP.]<br>[scripts_services.TeleToGH:fromGH @811;From Giran Harbor|\"\u041f\u043e\u043a\u0438\u043d\u0443\u0442\u044c Giran Harbor.\"]<br></center>";
    }

    public class ZoneListener
    implements OnZoneEnterLeaveListener {
        public void onZoneEnter(Zone zone, Creature creature) {
        }

        public void onZoneLeave(Zone zone, Creature creature) {
            Player player = creature.getPlayer();
            if (player != null && Config.SERVICES_GIRAN_HARBOR_ENABLED && player.getReflection() == ReflectionManager.GIRAN_HARBOR && player.isVisible()) {
                double d = PositionUtils.convertHeadingToDegree((int)creature.getHeading());
                double d2 = Math.toRadians(d - 90.0);
                creature.teleToLocation((int)((double)creature.getX() + 50.0 * Math.sin(d2)), (int)((double)creature.getY() - 50.0 * Math.cos(d2)), creature.getZ());
            }
        }
    }
}
