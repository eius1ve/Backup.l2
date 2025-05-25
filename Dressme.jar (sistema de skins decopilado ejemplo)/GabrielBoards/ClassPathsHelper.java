/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Player
 */
package GabrielBoards;

import config.ClassPathConfig;
import l2.gameserver.model.Player;

public class ClassPathsHelper {
    public static int getClassPathPoints(Player Player2) {
        return ClassPathConfig.y.containsKey(Player2.getLevel()) ? ClassPathConfig.y.get(Player2.getLevel()) : 0;
    }
}
