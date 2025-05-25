/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.ai;

public static final class DefaultAI.TaskType
extends Enum<DefaultAI.TaskType> {
    public static final /* enum */ DefaultAI.TaskType MOVE = new DefaultAI.TaskType();
    public static final /* enum */ DefaultAI.TaskType ATTACK = new DefaultAI.TaskType();
    public static final /* enum */ DefaultAI.TaskType CAST = new DefaultAI.TaskType();
    public static final /* enum */ DefaultAI.TaskType BUFF = new DefaultAI.TaskType();
    private static final /* synthetic */ DefaultAI.TaskType[] a;

    public static DefaultAI.TaskType[] values() {
        return (DefaultAI.TaskType[])a.clone();
    }

    public static DefaultAI.TaskType valueOf(String string) {
        return Enum.valueOf(DefaultAI.TaskType.class, string);
    }

    private static /* synthetic */ DefaultAI.TaskType[] a() {
        return new DefaultAI.TaskType[]{MOVE, ATTACK, CAST, BUFF};
    }

    static {
        a = DefaultAI.TaskType.a();
    }
}
