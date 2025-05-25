/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Creature;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.utils.Location;

public class FlyToLocation
extends L2GameServerPacket {
    private int yg;
    private final FlyType a;
    private Location _loc;
    private Location U;

    public FlyToLocation(Creature creature, Location location, FlyType flyType) {
        this.U = location;
        this.a = flyType;
        this.yg = creature.getObjectId();
        this._loc = creature.getLoc();
    }

    @Override
    protected void writeImpl() {
        this.writeC(212);
        this.writeD(this.yg);
        this.writeD(this.U.x);
        this.writeD(this.U.y);
        this.writeD(this.U.z);
        this.writeD(this._loc.x);
        this.writeD(this._loc.y);
        this.writeD(this._loc.z);
        this.writeD(this.a.ordinal());
    }

    public static final class FlyType
    extends Enum<FlyType> {
        public static final /* enum */ FlyType THROW_UP = new FlyType();
        public static final /* enum */ FlyType THROW_HORIZONTAL = new FlyType();
        public static final /* enum */ FlyType DUMMY = new FlyType();
        public static final /* enum */ FlyType CHARGE = new FlyType();
        public static final /* enum */ FlyType NONE = new FlyType();
        private static final /* synthetic */ FlyType[] a;

        public static FlyType[] values() {
            return (FlyType[])a.clone();
        }

        public static FlyType valueOf(String string) {
            return Enum.valueOf(FlyType.class, string);
        }

        private static /* synthetic */ FlyType[] a() {
            return new FlyType[]{THROW_UP, THROW_HORIZONTAL, DUMMY, CHARGE, NONE};
        }

        static {
            a = FlyType.a();
        }
    }
}
