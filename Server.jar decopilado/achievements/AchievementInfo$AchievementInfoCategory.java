/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.data.StringHolder
 *  l2.gameserver.model.Player
 */
package achievements;

import l2.gameserver.data.StringHolder;
import l2.gameserver.model.Player;

public static class AchievementInfo.AchievementInfoCategory {
    private final String _name;
    private final String f;

    public AchievementInfo.AchievementInfoCategory(String string, String string2) {
        this._name = string;
        this.f = string2;
    }

    public String getName() {
        return this._name;
    }

    public String getTitle(Player player) {
        return StringHolder.getInstance().getNotNull(player, this.f);
    }
}
