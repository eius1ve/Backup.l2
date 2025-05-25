/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.geodata;

import java.awt.Color;
import l2.gameserver.geodata.GeoEngine;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExServerPrimitive;

public class GeoUtils {
    public static final byte CELL_FLAG_NONE = 0;
    public static final byte CELL_FLAG_E = 1;
    public static final byte CELL_FLAG_W = 2;
    public static final byte CELL_FLAG_S = 4;
    public static final byte CELL_FLAG_N = 8;
    public static final byte CELL_FLAG_ALL = 15;

    public static void debugGrid(Player player) {
        int n = 40;
        int n2 = 0;
        ExServerPrimitive exServerPrimitive = null;
        int n3 = GeoEngine.getGeoX(player.getX());
        int n4 = GeoEngine.getGeoY(player.getY());
        for (int i = -20; i <= 20; ++i) {
            for (int j = -20; j <= 20; ++j) {
                if (n >= 40) {
                    n = 0;
                    if (exServerPrimitive != null) {
                        ++n2;
                        player.sendPacket((IStaticPacket)exServerPrimitive);
                    }
                    exServerPrimitive = new ExServerPrimitive("DebugGrid_" + n2, player.getX(), player.getY(), -16000);
                }
                int n5 = n3 + i;
                int n6 = n4 + j;
                int n7 = GeoEngine.getWorldX(n5);
                int n8 = GeoEngine.getWorldY(n6);
                int n9 = GeoEngine.NgetHeight(n5, n6, player.getZ(), 0);
                Color color = GeoUtils.a(n5, n6, n9, 8);
                exServerPrimitive.addLine(color, n7 - 1, n8 - 7, n9, n7 + 1, n8 - 7, n9);
                exServerPrimitive.addLine(color, n7 - 2, n8 - 6, n9, n7 + 2, n8 - 6, n9);
                exServerPrimitive.addLine(color, n7 - 3, n8 - 5, n9, n7 + 3, n8 - 5, n9);
                exServerPrimitive.addLine(color, n7 - 4, n8 - 4, n9, n7 + 4, n8 - 4, n9);
                color = GeoUtils.a(n5, n6, n9, 1);
                exServerPrimitive.addLine(color, n7 + 7, n8 - 1, n9, n7 + 7, n8 + 1, n9);
                exServerPrimitive.addLine(color, n7 + 6, n8 - 2, n9, n7 + 6, n8 + 2, n9);
                exServerPrimitive.addLine(color, n7 + 5, n8 - 3, n9, n7 + 5, n8 + 3, n9);
                exServerPrimitive.addLine(color, n7 + 4, n8 - 4, n9, n7 + 4, n8 + 4, n9);
                color = GeoUtils.a(n5, n6, n9, 4);
                exServerPrimitive.addLine(color, n7 - 1, n8 + 7, n9, n7 + 1, n8 + 7, n9);
                exServerPrimitive.addLine(color, n7 - 2, n8 + 6, n9, n7 + 2, n8 + 6, n9);
                exServerPrimitive.addLine(color, n7 - 3, n8 + 5, n9, n7 + 3, n8 + 5, n9);
                exServerPrimitive.addLine(color, n7 - 4, n8 + 4, n9, n7 + 4, n8 + 4, n9);
                color = GeoUtils.a(n5, n6, n9, 2);
                exServerPrimitive.addLine(color, n7 - 7, n8 - 1, n9, n7 - 7, n8 + 1, n9);
                exServerPrimitive.addLine(color, n7 - 6, n8 - 2, n9, n7 - 6, n8 + 2, n9);
                exServerPrimitive.addLine(color, n7 - 5, n8 - 3, n9, n7 - 5, n8 + 3, n9);
                exServerPrimitive.addLine(color, n7 - 4, n8 - 4, n9, n7 - 4, n8 + 4, n9);
                ++n;
            }
        }
        player.sendPacket((IStaticPacket)exServerPrimitive);
    }

    private static Color a(int n, int n2, int n3, int n4) {
        if ((GeoEngine.NgetNSWE(n, n2, n3, 0) & n4) != 0) {
            return Color.GREEN;
        }
        return Color.RED;
    }
}
