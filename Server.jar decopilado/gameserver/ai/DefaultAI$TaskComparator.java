/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.ai;

import java.util.Comparator;
import l2.gameserver.ai.DefaultAI;

private static class DefaultAI.TaskComparator
implements Comparator<DefaultAI.Task> {
    private static final Comparator<DefaultAI.Task> c = new DefaultAI.TaskComparator();

    private DefaultAI.TaskComparator() {
    }

    public static final Comparator<DefaultAI.Task> getInstance() {
        return c;
    }

    @Override
    public int compare(DefaultAI.Task task, DefaultAI.Task task2) {
        if (task == null || task2 == null) {
            return 0;
        }
        return task2.weight - task.weight;
    }
}
