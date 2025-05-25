/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.AbstractSequentialList;
import java.util.Iterator;
import java.util.LinkedList;
import l2.gameserver.model.Player;
import l2.gameserver.model.base.TeamType;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ExPVPMatchRecord
extends L2GameServerPacket {
    private PVPMatchAction a;
    private LinkedList<PVPMatchRecord> a;
    private LinkedList<PVPMatchRecord> b;
    private int vY;
    private int vZ;
    private TeamType _winner;
    private int wa;
    private int wb;

    public ExPVPMatchRecord(PVPMatchAction pVPMatchAction, TeamType teamType, int n, int n2) {
        this.a = pVPMatchAction;
        this.a = new LinkedList();
        this.b = new LinkedList();
        this._winner = teamType;
        this.wa = n;
        this.wb = n2;
    }

    public void addRecord(Player player, int n, int n2) {
        if (player.getTeam() == TeamType.RED) {
            ((LinkedList)((Object)this.a)).add(new PVPMatchRecord(player.getName(), n, n2));
            ++this.vZ;
        } else if (player.getTeam() == TeamType.BLUE) {
            this.b.add(new PVPMatchRecord(player.getName(), n, n2));
            ++this.vY;
        }
    }

    @Override
    protected void writeImpl() {
        this.writeEx(127);
        this.writeD(this.a.getVal());
        if (this._winner == TeamType.RED) {
            this.writeD(2);
            this.writeD(1);
        } else if (this._winner == TeamType.BLUE) {
            this.writeD(1);
            this.writeD(2);
        } else {
            this.writeD(0);
            this.writeD(0);
        }
        this.writeD(this.wa);
        this.writeD(this.wb);
        if (this.vY > 0) {
            this.writeD(this.b.size());
            for (PVPMatchRecord pVPMatchRecord : this.b) {
                this.writeS(pVPMatchRecord.name);
                this.writeD(pVPMatchRecord.kill);
                this.writeD(pVPMatchRecord.die);
            }
        } else {
            this.writeD(0);
        }
        if (this.vZ > 0) {
            this.writeD(((LinkedList)((Object)this.a)).size());
            Iterator iterator = ((AbstractSequentialList)((Object)this.a)).iterator();
            while (iterator.hasNext()) {
                PVPMatchRecord pVPMatchRecord;
                pVPMatchRecord = (PVPMatchRecord)iterator.next();
                this.writeS(pVPMatchRecord.name);
                this.writeD(pVPMatchRecord.kill);
                this.writeD(pVPMatchRecord.die);
            }
        } else {
            this.writeD(0);
        }
    }

    public static final class PVPMatchAction
    extends Enum<PVPMatchAction> {
        public static final /* enum */ PVPMatchAction INIT = new PVPMatchAction(0);
        public static final /* enum */ PVPMatchAction UPDATE = new PVPMatchAction(1);
        public static final /* enum */ PVPMatchAction DONE = new PVPMatchAction(2);
        private final int wc;
        private static final /* synthetic */ PVPMatchAction[] a;

        public static PVPMatchAction[] values() {
            return (PVPMatchAction[])a.clone();
        }

        public static PVPMatchAction valueOf(String string) {
            return Enum.valueOf(PVPMatchAction.class, string);
        }

        private PVPMatchAction(int n2) {
            this.wc = n2;
        }

        public int getVal() {
            return this.wc;
        }

        private static /* synthetic */ PVPMatchAction[] a() {
            return new PVPMatchAction[]{INIT, UPDATE, DONE};
        }

        static {
            a = PVPMatchAction.a();
        }
    }

    private class PVPMatchRecord {
        public final String name;
        public final int kill;
        public final int die;

        public PVPMatchRecord(String string, int n, int n2) {
            this.name = string;
            this.kill = n;
            this.die = n2;
        }
    }
}
