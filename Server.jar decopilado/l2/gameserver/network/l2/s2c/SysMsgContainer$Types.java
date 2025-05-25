/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

public static final class SysMsgContainer.Types
extends Enum<SysMsgContainer.Types> {
    public static final /* enum */ SysMsgContainer.Types TEXT = new SysMsgContainer.Types();
    public static final /* enum */ SysMsgContainer.Types NUMBER = new SysMsgContainer.Types();
    public static final /* enum */ SysMsgContainer.Types NPC_NAME = new SysMsgContainer.Types();
    public static final /* enum */ SysMsgContainer.Types ITEM_NAME = new SysMsgContainer.Types();
    public static final /* enum */ SysMsgContainer.Types SKILL_NAME = new SysMsgContainer.Types();
    public static final /* enum */ SysMsgContainer.Types RESIDENCE_NAME = new SysMsgContainer.Types();
    public static final /* enum */ SysMsgContainer.Types LONG = new SysMsgContainer.Types();
    public static final /* enum */ SysMsgContainer.Types ZONE_NAME = new SysMsgContainer.Types();
    public static final /* enum */ SysMsgContainer.Types ITEM_NAME_WITH_AUGMENTATION = new SysMsgContainer.Types();
    public static final /* enum */ SysMsgContainer.Types ELEMENT_NAME = new SysMsgContainer.Types();
    public static final /* enum */ SysMsgContainer.Types INSTANCE_NAME = new SysMsgContainer.Types();
    public static final /* enum */ SysMsgContainer.Types STATIC_OBJECT_NAME = new SysMsgContainer.Types();
    public static final /* enum */ SysMsgContainer.Types PLAYER_NAME = new SysMsgContainer.Types();
    public static final /* enum */ SysMsgContainer.Types SYSTEM_STRING = new SysMsgContainer.Types();
    public static final /* enum */ SysMsgContainer.Types NPC_STRING = new SysMsgContainer.Types();
    public static final /* enum */ SysMsgContainer.Types CLASS_ID = new SysMsgContainer.Types();
    public static final /* enum */ SysMsgContainer.Types TYPE_VISIBLE_DAMAGE = new SysMsgContainer.Types();
    public static final /* enum */ SysMsgContainer.Types U_17 = new SysMsgContainer.Types();
    public static final /* enum */ SysMsgContainer.Types U_18 = new SysMsgContainer.Types();
    public static final /* enum */ SysMsgContainer.Types U_19 = new SysMsgContainer.Types();
    public static final /* enum */ SysMsgContainer.Types BYTE = new SysMsgContainer.Types();
    private static final /* synthetic */ SysMsgContainer.Types[] a;

    public static SysMsgContainer.Types[] values() {
        return (SysMsgContainer.Types[])a.clone();
    }

    public static SysMsgContainer.Types valueOf(String string) {
        return Enum.valueOf(SysMsgContainer.Types.class, string);
    }

    private static /* synthetic */ SysMsgContainer.Types[] a() {
        return new SysMsgContainer.Types[]{TEXT, NUMBER, NPC_NAME, ITEM_NAME, SKILL_NAME, RESIDENCE_NAME, LONG, ZONE_NAME, ITEM_NAME_WITH_AUGMENTATION, ELEMENT_NAME, INSTANCE_NAME, STATIC_OBJECT_NAME, PLAYER_NAME, SYSTEM_STRING, NPC_STRING, CLASS_ID, TYPE_VISIBLE_DAMAGE, U_17, U_18, U_19, BYTE};
    }

    static {
        a = SysMsgContainer.Types.a();
    }
}
