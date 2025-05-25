/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.utils.Location;

public class PlaySound
extends L2GameServerPacket {
    public static final L2GameServerPacket SIEGE_VICTORY = new PlaySound("Siege_Victory");
    public static final L2GameServerPacket B04_S01 = new PlaySound("B04_S01");
    public static final L2GameServerPacket HB01 = new PlaySound(Type.MUSIC, "HB01", 0, 0, 0, 0, 0);
    private Type a;
    private String fD;
    private int Am;
    private int fW;
    private int _x;
    private int _y;
    private int gl;

    public PlaySound(String string) {
        this(Type.SOUND, string, 0, 0, 0, 0, 0);
    }

    public PlaySound(Type type, String string, int n, int n2, Location location) {
        this(type, string, n, n2, location == null ? 0 : location.x, location == null ? 0 : location.y, location == null ? 0 : location.z);
    }

    public PlaySound(Type type, String string, int n, int n2, int n3, int n4, int n5) {
        this.a = type;
        this.fD = string;
        this.Am = n;
        this.fW = n2;
        this._x = n3;
        this._y = n4;
        this.gl = n5;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(158);
        this.writeD(this.a.ordinal());
        this.writeS(this.fD);
        this.writeD(this.Am);
        this.writeD(this.fW);
        this.writeD(this._x);
        this.writeD(this._y);
        this.writeD(this.gl);
    }

    public static final class Type
    extends Enum<Type> {
        public static final /* enum */ Type SOUND = new Type();
        public static final /* enum */ Type MUSIC = new Type();
        public static final /* enum */ Type VOICE = new Type();
        private static final /* synthetic */ Type[] a;

        public static Type[] values() {
            return (Type[])a.clone();
        }

        public static Type valueOf(String string) {
            return Enum.valueOf(Type.class, string);
        }

        private static /* synthetic */ Type[] a() {
            return new Type[]{SOUND, MUSIC, VOICE};
        }

        static {
            a = Type.a();
        }
    }
}
