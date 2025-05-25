/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.taskmanager;

public final class TaskTypes
extends Enum<TaskTypes> {
    public static final /* enum */ TaskTypes TYPE_NONE = new TaskTypes();
    public static final /* enum */ TaskTypes TYPE_TIME = new TaskTypes();
    public static final /* enum */ TaskTypes TYPE_SHEDULED = new TaskTypes();
    public static final /* enum */ TaskTypes TYPE_FIXED_SHEDULED = new TaskTypes();
    public static final /* enum */ TaskTypes TYPE_GLOBAL_TASK = new TaskTypes();
    public static final /* enum */ TaskTypes TYPE_STARTUP = new TaskTypes();
    public static final /* enum */ TaskTypes TYPE_SPECIAL = new TaskTypes();
    private static final /* synthetic */ TaskTypes[] a;

    public static TaskTypes[] values() {
        return (TaskTypes[])a.clone();
    }

    public static TaskTypes valueOf(String string) {
        return Enum.valueOf(TaskTypes.class, string);
    }

    private static /* synthetic */ TaskTypes[] a() {
        return new TaskTypes[]{TYPE_NONE, TYPE_TIME, TYPE_SHEDULED, TYPE_FIXED_SHEDULED, TYPE_GLOBAL_TASK, TYPE_STARTUP, TYPE_SPECIAL};
    }

    static {
        a = TaskTypes.a();
    }
}
