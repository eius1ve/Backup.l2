/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.handler.voicecommands.IVoicedCommandHandler
 *  l2.gameserver.model.Player
 *  l2.gameserver.scripts.Scripts
 *  org.apache.commons.lang3.ArrayUtils
 */
package achievements;

import achievements.AchievementUI;
import achievements.Achievements;
import l2.gameserver.handler.voicecommands.IVoicedCommandHandler;
import l2.gameserver.model.Player;
import l2.gameserver.scripts.Scripts;
import org.apache.commons.lang3.ArrayUtils;

private static class Achievements.AchVoicedCommandHandler
implements IVoicedCommandHandler {
    private Achievements.AchVoicedCommandHandler() {
    }

    public boolean useVoicedCommand(String string, Player player, String string2) {
        if (!Achievements.getInstance().isEnabled()) {
            return false;
        }
        for (String string3 : Achievements.getInstance().a()) {
            if (string3.isEmpty() || !string3.equalsIgnoreCase(string)) continue;
            Scripts.getInstance().callScripts(player, AchievementUI.class.getName(), "achievements");
            return true;
        }
        return false;
    }

    public String[] getVoicedCommandList() {
        if (Achievements.getInstance().isEnabled()) {
            return Achievements.getInstance().a();
        }
        return ArrayUtils.EMPTY_STRING_ARRAY;
    }
}
