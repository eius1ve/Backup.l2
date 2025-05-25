/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.templates;

public static final class DoorTemplate.DoorType
extends Enum<DoorTemplate.DoorType> {
    public static final /* enum */ DoorTemplate.DoorType DOOR = new DoorTemplate.DoorType();
    public static final /* enum */ DoorTemplate.DoorType WALL = new DoorTemplate.DoorType();
    private static final /* synthetic */ DoorTemplate.DoorType[] a;

    public static DoorTemplate.DoorType[] values() {
        return (DoorTemplate.DoorType[])a.clone();
    }

    public static DoorTemplate.DoorType valueOf(String string) {
        return Enum.valueOf(DoorTemplate.DoorType.class, string);
    }

    private static /* synthetic */ DoorTemplate.DoorType[] a() {
        return new DoorTemplate.DoorType[]{DOOR, WALL};
    }

    static {
        a = DoorTemplate.DoorType.a();
    }
}
