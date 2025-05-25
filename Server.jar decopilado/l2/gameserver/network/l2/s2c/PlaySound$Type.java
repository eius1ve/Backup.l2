/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

public static final class PlaySound.Type
extends Enum<PlaySound.Type> {
    public static final /* enum */ PlaySound.Type SOUND = new PlaySound.Type();
    public static final /* enum */ PlaySound.Type MUSIC = new PlaySound.Type();
    public static final /* enum */ PlaySound.Type VOICE = new PlaySound.Type();
    private static final /* synthetic */ PlaySound.Type[] a;

    public static PlaySound.Type[] values() {
        return (PlaySound.Type[])a.clone();
    }

    public static PlaySound.Type valueOf(String string) {
        return Enum.valueOf(PlaySound.Type.class, string);
    }

    private static /* synthetic */ PlaySound.Type[] a() {
        return new PlaySound.Type[]{SOUND, MUSIC, VOICE};
    }

    static {
        a = PlaySound.Type.a();
    }
}
