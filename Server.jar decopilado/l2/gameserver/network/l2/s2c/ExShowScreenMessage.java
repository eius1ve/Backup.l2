/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.network.l2.components.NpcString;
import l2.gameserver.network.l2.s2c.NpcStringContainer;

public class ExShowScreenMessage
extends NpcStringContainer {
    public static final int SYSMSG_TYPE = 0;
    public static final int STRING_TYPE = 1;
    private int _type;
    private int xy;
    private boolean eU;
    private boolean eV;
    private ScreenMessageAlign a;
    private int _time;

    @Deprecated
    public ExShowScreenMessage(String string, int n, ScreenMessageAlign screenMessageAlign, boolean bl) {
        this(string, n, screenMessageAlign, bl, 1, -1, false);
    }

    @Deprecated
    public ExShowScreenMessage(String string, int n, ScreenMessageAlign screenMessageAlign, boolean bl, int n2, int n3, boolean bl2) {
        super(NpcString.NONE, string);
        this._type = n2;
        this.xy = n3;
        this._time = n;
        this.a = screenMessageAlign;
        this.eU = bl;
        this.eV = bl2;
    }

    public ExShowScreenMessage(NpcString npcString, int n, ScreenMessageAlign screenMessageAlign, String ... stringArray) {
        this(npcString, n, screenMessageAlign, true, 1, -1, false, stringArray);
    }

    public ExShowScreenMessage(NpcString npcString, int n, ScreenMessageAlign screenMessageAlign, boolean bl, String ... stringArray) {
        this(npcString, n, screenMessageAlign, bl, 1, -1, false, stringArray);
    }

    public ExShowScreenMessage(NpcString npcString, int n, ScreenMessageAlign screenMessageAlign, boolean bl, boolean bl2, String ... stringArray) {
        this(npcString, n, screenMessageAlign, bl, 1, -1, bl2, stringArray);
    }

    public ExShowScreenMessage(NpcString npcString, int n, ScreenMessageAlign screenMessageAlign, boolean bl, int n2, int n3, boolean bl2, String ... stringArray) {
        super(npcString, stringArray);
        this._type = n2;
        this.xy = n3;
        this._time = n;
        this.a = screenMessageAlign;
        this.eU = bl;
        this.eV = bl2;
    }

    @Override
    protected final void writeImpl() {
        this.writeEx(57);
        this.writeD(this._type);
        this.writeD(this.xy);
        this.writeD(this.a.ordinal() + 1);
        this.writeD(0);
        this.writeD(this.eU ? 0 : 1);
        this.writeD(0);
        this.writeD(0);
        this.writeD(this.eV ? 1 : 0);
        this.writeD(this._time);
        this.writeD(1);
        this.writeElements();
    }

    public static final class ScreenMessageAlign
    extends Enum<ScreenMessageAlign> {
        public static final /* enum */ ScreenMessageAlign TOP_LEFT = new ScreenMessageAlign();
        public static final /* enum */ ScreenMessageAlign TOP_CENTER = new ScreenMessageAlign();
        public static final /* enum */ ScreenMessageAlign TOP_RIGHT = new ScreenMessageAlign();
        public static final /* enum */ ScreenMessageAlign MIDDLE_LEFT = new ScreenMessageAlign();
        public static final /* enum */ ScreenMessageAlign MIDDLE_CENTER = new ScreenMessageAlign();
        public static final /* enum */ ScreenMessageAlign MIDDLE_RIGHT = new ScreenMessageAlign();
        public static final /* enum */ ScreenMessageAlign BOTTOM_CENTER = new ScreenMessageAlign();
        public static final /* enum */ ScreenMessageAlign BOTTOM_RIGHT = new ScreenMessageAlign();
        private static final /* synthetic */ ScreenMessageAlign[] a;

        public static ScreenMessageAlign[] values() {
            return (ScreenMessageAlign[])a.clone();
        }

        public static ScreenMessageAlign valueOf(String string) {
            return Enum.valueOf(ScreenMessageAlign.class, string);
        }

        private static /* synthetic */ ScreenMessageAlign[] a() {
            return new ScreenMessageAlign[]{TOP_LEFT, TOP_CENTER, TOP_RIGHT, MIDDLE_LEFT, MIDDLE_CENTER, MIDDLE_RIGHT, BOTTOM_CENTER, BOTTOM_RIGHT};
        }

        static {
            a = ScreenMessageAlign.a();
        }
    }
}
