/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import l2.commons.geometry.Point2D;
import l2.commons.geometry.Polygon;
import l2.commons.util.Rnd;
import l2.gameserver.data.xml.holder.ResidenceHolder;
import l2.gameserver.handler.admincommands.IAdminCommandHandler;
import l2.gameserver.instancemanager.MapRegionManager;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.model.World;
import l2.gameserver.model.Zone;
import l2.gameserver.model.entity.residence.Castle;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.DropItem;
import l2.gameserver.network.l2.s2c.ExServerPrimitive;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.templates.mapregion.DomainArea;

public class AdminZone
implements IAdminCommandHandler {
    @Override
    public boolean useAdminCommand(Enum enum_, String[] stringArray, String string, Player player) {
        Commands commands = (Commands)enum_;
        if (player == null || !player.getPlayerAccess().CanTeleport) {
            return false;
        }
        switch (commands) {
            case admin_zones: {
                player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("admin/gmzone.htm"));
                break;
            }
            case admin_zone_check: {
                player.sendMessage("Current region: " + player.getCurrentRegion());
                player.sendMessage("Zone list:");
                ArrayList<Zone> arrayList = new ArrayList<Zone>();
                World.getZones(arrayList, player.getLoc(), player.getReflection());
                for (Zone zone : arrayList) {
                    player.sendMessage(zone.getType().toString() + ", name: " + zone.getName() + ", state: " + (zone.isActive() ? "active" : "not active") + ", inside: " + zone.checkIfInZone(player) + "/" + zone.checkIfInZone(player.getX(), player.getY(), player.getZ()));
                }
                player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("admin/gmzone.htm"));
                break;
            }
            case admin_region: {
                player.sendMessage("Current region: " + player.getCurrentRegion());
                player.sendMessage("Objects list:");
                for (GameObject gameObject : player.getCurrentRegion()) {
                    if (gameObject == null) continue;
                    player.sendMessage(gameObject.toString());
                }
                player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("admin/gmzone.htm"));
                break;
            }
            case admin_vis_count: {
                player.sendMessage("Current region: " + player.getCurrentRegion());
                player.sendMessage("Players count: " + World.getAroundPlayers(player).size());
                player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("admin/gmzone.htm"));
                break;
            }
            case admin_pos: {
                String string2 = player.getX() + ", " + player.getY() + ", " + player.getZ() + ", " + player.getHeading() + " Geo [" + (player.getX() - World.MAP_MIN_X >> 4) + ", " + (player.getY() - World.MAP_MIN_Y >> 4) + "] Ref " + player.getReflectionId();
                player.sendMessage("Pos: " + string2);
                player.sendPacket((IStaticPacket)new DropItem(0, 0xFFFFFF & Rnd.nextInt(), 57, player.getLoc().clone().setZ(player.getZ() + 64), false, 1));
                player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("admin/gmzone.htm"));
                break;
            }
            case admin_domain: {
                Castle castle;
                DomainArea domainArea = MapRegionManager.getInstance().getRegionData(DomainArea.class, player);
                Castle castle2 = castle = domainArea != null ? ResidenceHolder.getInstance().getResidence(Castle.class, domainArea.getId()) : null;
                if (castle != null) {
                    player.sendMessage("Domain: " + castle.getName());
                } else {
                    player.sendMessage("Domain: Unknown");
                }
                player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("admin/gmzone.htm"));
                break;
            }
            case admin_zone_visual: {
                ArrayList<Zone> arrayList = new ArrayList<Zone>();
                World.getZones(arrayList, player.getLoc(), player.getReflection());
                if (arrayList.isEmpty()) {
                    player.sendMessage("You are not in the zone.");
                    return true;
                }
                for (Zone zone : arrayList) {
                    player.sendMessage("The " + zone.getName() + " is visualized.");
                    if (zone.getType() == Zone.ZoneType.FISHING) {
                        player.sendMessage("Skipping zone type: " + zone.getType());
                        continue;
                    }
                    AdminZone.drawZone(player, zone, Color.GREEN);
                }
                player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("admin/gmzone.htm"));
                break;
            }
        }
        return true;
    }

    public static void drawZone(Player player, Zone zone, Color color) {
        int n;
        Polygon polygon = (Polygon)zone.getTemplate().getTerritory().getTerritories().get(0);
        List<Point2D> list = polygon.getPoints();
        ExServerPrimitive exServerPrimitive = new ExServerPrimitive(zone.getName(), player.getX(), player.getY(), player.getZ() - 1000);
        int n2 = player.getZ();
        int n3 = Math.max(polygon.getZmin(), n2 - 500);
        if (n3 >= (n = Math.min(polygon.getZmin() + (polygon.getZmax() - polygon.getZmin()) / 2, n2 + 500))) {
            player.sendMessage("Invalid zone height for visualization.");
            return;
        }
        for (int i = n3; i < n; i += 10) {
            for (int j = 0; j < list.size(); ++j) {
                Point2D point2D = list.get(j);
                Point2D point2D2 = j + 1 == list.size() ? list.get(0) : list.get(j + 1);
                exServerPrimitive.addLine(color, point2D.x, point2D.y, i, point2D2.x, point2D2.y, i);
            }
        }
        player.sendPacket((IStaticPacket)exServerPrimitive);
    }

    @Override
    public Enum[] getAdminCommandEnum() {
        return Commands.values();
    }

    private static final class Commands
    extends Enum<Commands> {
        public static final /* enum */ Commands admin_zones = new Commands();
        public static final /* enum */ Commands admin_zone_check = new Commands();
        public static final /* enum */ Commands admin_region = new Commands();
        public static final /* enum */ Commands admin_pos = new Commands();
        public static final /* enum */ Commands admin_vis_count = new Commands();
        public static final /* enum */ Commands admin_domain = new Commands();
        public static final /* enum */ Commands admin_zone_visual = new Commands();
        private static final /* synthetic */ Commands[] a;

        public static Commands[] values() {
            return (Commands[])a.clone();
        }

        public static Commands valueOf(String string) {
            return Enum.valueOf(Commands.class, string);
        }

        private static /* synthetic */ Commands[] a() {
            return new Commands[]{admin_zones, admin_zone_check, admin_region, admin_pos, admin_vis_count, admin_domain, admin_zone_visual};
        }

        static {
            a = Commands.a();
        }
    }
}
