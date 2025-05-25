/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.ArrayList;
import java.util.List;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class StatusUpdate
extends L2GameServerPacket {
    public static final int CUR_HP = 9;
    public static final int MAX_HP = 10;
    public static final int CUR_MP = 11;
    public static final int MAX_MP = 12;
    public static final int CUR_LOAD = 14;
    public static final int MAX_LOAD = 15;
    public static final int PVP_FLAG = 26;
    public static final int KARMA = 27;
    public static final int CUR_CP = 33;
    public static final int MAX_CP = 34;
    private final int BV;
    private final List<Attribute> dd = new ArrayList<Attribute>();
    private boolean fm;
    private int BW;

    public StatusUpdate(Creature creature) {
        this.BV = creature.getObjectId();
    }

    public StatusUpdate(int n) {
        this.BV = n;
    }

    public StatusUpdate addAttribute(int n, int n2) {
        this.dd.add(new Attribute(n, n2));
        switch (n) {
            case 9: 
            case 11: 
            case 33: {
                this.fm = true;
            }
        }
        return this;
    }

    public StatusUpdate setAttackerObjectId(int n) {
        this.BW = n;
        return this;
    }

    public StatusUpdate setAttackerObject(GameObject gameObject) {
        this.BW = gameObject.getObjectId();
        return this;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(24);
        this.writeD(this.BV);
        this.writeD(this.fm ? this.BW : 0);
        this.writeC(this.fm ? 1 : 0);
        this.writeC(this.dd.size());
        for (Attribute attribute : this.dd) {
            this.writeC(attribute.id);
            this.writeD(attribute.value);
        }
    }

    public boolean hasAttributes() {
        return !this.dd.isEmpty();
    }

    class Attribute {
        public final int id;
        public final int value;

        Attribute(int n, int n2) {
            this.id = n;
            this.value = n2;
        }
    }
}
