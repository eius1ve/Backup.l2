/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.AggroList$AggroInfo
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.instances.NpcInstance
 */
package achievements;

import achievements.AchievementCondition;
import l2.gameserver.model.AggroList;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.instances.NpcInstance;

@AchievementCondition.AchievementConditionName(value="target_npc_min_hate_to_me")
public static class AchievementCondition.AchTargetAggroMinHateToMe
extends AchievementCondition {
    private final int g;

    public AchievementCondition.AchTargetAggroMinHateToMe(String string) {
        this.g = Integer.parseInt(string);
    }

    @Override
    public boolean test(Player player, Object ... objectArray) {
        for (Object object : objectArray) {
            if (object == null || !(object instanceof NpcInstance)) continue;
            NpcInstance npcInstance = (NpcInstance)object;
            AggroList.AggroInfo aggroInfo = npcInstance.getAggroList().get((Creature)player);
            if (aggroInfo == null) {
                return false;
            }
            return aggroInfo.hate >= this.g;
        }
        return false;
    }
}
