/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.instancemanager;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import l2.gameserver.model.quest.Quest;

public class QuestManager {
    public static final int TUTORIAL_QUEST = 255;
    private static Map<String, Quest> aq = new ConcurrentHashMap<String, Quest>();
    private static Map<Integer, Quest> ar = new ConcurrentHashMap<Integer, Quest>();

    public static Quest getQuest(String string) {
        return aq.get(string);
    }

    public static Quest getQuest(Class<?> clazz) {
        return QuestManager.getQuest(clazz.getSimpleName());
    }

    public static Quest getQuest(int n) {
        return ar.get(n);
    }

    public static Quest getQuest2(String string) {
        if (aq.containsKey(string)) {
            return aq.get(string);
        }
        try {
            int n = Integer.valueOf(string);
            return ar.get(n);
        }
        catch (Exception exception) {
            return null;
        }
    }

    public static void addQuest(Quest quest) {
        aq.put(quest.getName(), quest);
        ar.put(quest.getQuestIntId(), quest);
    }

    public static Collection<Quest> getQuests() {
        return aq.values();
    }
}
