/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.quest;

public final class QuestEventType
extends Enum<QuestEventType> {
    public static final /* enum */ QuestEventType MOB_TARGETED_BY_SKILL = new QuestEventType();
    public static final /* enum */ QuestEventType ATTACKED_WITH_QUEST = new QuestEventType();
    public static final /* enum */ QuestEventType MOB_KILLED_WITH_QUEST = new QuestEventType();
    public static final /* enum */ QuestEventType QUEST_START = new QuestEventType();
    public static final /* enum */ QuestEventType QUEST_TALK = new QuestEventType();
    public static final /* enum */ QuestEventType NPC_FIRST_TALK = new QuestEventType();
    private static final /* synthetic */ QuestEventType[] a;

    public static QuestEventType[] values() {
        return (QuestEventType[])a.clone();
    }

    public static QuestEventType valueOf(String string) {
        return Enum.valueOf(QuestEventType.class, string);
    }

    private static /* synthetic */ QuestEventType[] a() {
        return new QuestEventType[]{MOB_TARGETED_BY_SKILL, ATTACKED_WITH_QUEST, MOB_KILLED_WITH_QUEST, QUEST_START, QUEST_TALK, NPC_FIRST_TALK};
    }

    static {
        a = QuestEventType.a();
    }
}
