/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.components;

public final class ChatType
extends Enum<ChatType> {
    public static final /* enum */ ChatType ALL = new ChatType();
    public static final /* enum */ ChatType SHOUT = new ChatType();
    public static final /* enum */ ChatType TELL = new ChatType();
    public static final /* enum */ ChatType PARTY = new ChatType();
    public static final /* enum */ ChatType CLAN = new ChatType();
    public static final /* enum */ ChatType GM = new ChatType();
    public static final /* enum */ ChatType PETITION_PLAYER = new ChatType();
    public static final /* enum */ ChatType PETITION_GM = new ChatType();
    public static final /* enum */ ChatType TRADE = new ChatType();
    public static final /* enum */ ChatType ALLIANCE = new ChatType();
    public static final /* enum */ ChatType ANNOUNCEMENT = new ChatType();
    public static final /* enum */ ChatType SYSTEM_MESSAGE = new ChatType();
    public static final /* enum */ ChatType L2FRIEND = new ChatType();
    public static final /* enum */ ChatType MSNCHAT = new ChatType();
    public static final /* enum */ ChatType PARTY_ROOM = new ChatType();
    public static final /* enum */ ChatType COMMANDCHANNEL_ALL = new ChatType();
    public static final /* enum */ ChatType COMMANDCHANNEL_COMMANDER = new ChatType();
    public static final /* enum */ ChatType HERO_VOICE = new ChatType();
    public static final /* enum */ ChatType CRITICAL_ANNOUNCE = new ChatType();
    public static final /* enum */ ChatType SCREEN_ANNOUNCE = new ChatType();
    public static final /* enum */ ChatType BATTLEFIELD = new ChatType();
    public static final /* enum */ ChatType MPCC_ROOM = new ChatType();
    public static final /* enum */ ChatType NPC_NORMAL = new ChatType();
    public static final /* enum */ ChatType NPC_SHOUT = new ChatType();
    public static final /* enum */ ChatType BLUE_UNK = new ChatType();
    public static final /* enum */ ChatType WORLD = new ChatType();
    public static final ChatType[] VALUES;
    private static final /* synthetic */ ChatType[] b;

    public static ChatType[] values() {
        return (ChatType[])b.clone();
    }

    public static ChatType valueOf(String string) {
        return Enum.valueOf(ChatType.class, string);
    }

    private static /* synthetic */ ChatType[] a() {
        return new ChatType[]{ALL, SHOUT, TELL, PARTY, CLAN, GM, PETITION_PLAYER, PETITION_GM, TRADE, ALLIANCE, ANNOUNCEMENT, SYSTEM_MESSAGE, L2FRIEND, MSNCHAT, PARTY_ROOM, COMMANDCHANNEL_ALL, COMMANDCHANNEL_COMMANDER, HERO_VOICE, CRITICAL_ANNOUNCE, SCREEN_ANNOUNCE, BATTLEFIELD, MPCC_ROOM, NPC_NORMAL, NPC_SHOUT, BLUE_UNK, WORLD};
    }

    static {
        b = ChatType.a();
        VALUES = ChatType.values();
    }
}
