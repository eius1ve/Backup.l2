/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.ai;

import l2.commons.lang.reference.HardReference;
import l2.gameserver.ai.DefaultAI;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Skill;
import l2.gameserver.utils.Location;

public static class DefaultAI.Task {
    public DefaultAI.TaskType type;
    public Skill skill;
    public HardReference<? extends Creature> target;
    public Location loc;
    public boolean pathfind;
    public int weight = 10000;
}
