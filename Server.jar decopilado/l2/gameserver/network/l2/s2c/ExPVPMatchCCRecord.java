/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.tuple.ImmutablePair
 *  org.apache.commons.lang3.tuple.Pair
 */
package l2.gameserver.network.l2.s2c;

import java.util.LinkedList;
import java.util.List;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class ExPVPMatchCCRecord
extends L2GameServerPacket {
    private List<Pair<String, Integer>> cm;
    private int vW;
    private PVPMatchCCAction a;

    public ExPVPMatchCCRecord(PVPMatchCCAction pVPMatchCCAction) {
        this.a = pVPMatchCCAction;
        this.vW = 0;
        this.cm = new LinkedList<Pair<String, Integer>>();
    }

    public void addPlayer(Player player, int n) {
        ++this.vW;
        this.cm.add((Pair<String, Integer>)new ImmutablePair((Object)player.getName(), (Object)n));
    }

    @Override
    public void writeImpl() {
        this.writeEx(138);
        this.writeD(this.a.getVal());
        this.writeD(this.vW);
        for (Pair<String, Integer> pair : this.cm) {
            this.writeS((CharSequence)pair.getLeft());
            this.writeD((Integer)pair.getRight());
        }
    }

    public static final class PVPMatchCCAction
    extends Enum<PVPMatchCCAction> {
        public static final /* enum */ PVPMatchCCAction INIT = new PVPMatchCCAction(0);
        public static final /* enum */ PVPMatchCCAction UPDATE = new PVPMatchCCAction(1);
        public static final /* enum */ PVPMatchCCAction DONE = new PVPMatchCCAction(2);
        private final int vX;
        private static final /* synthetic */ PVPMatchCCAction[] a;

        public static PVPMatchCCAction[] values() {
            return (PVPMatchCCAction[])a.clone();
        }

        public static PVPMatchCCAction valueOf(String string) {
            return Enum.valueOf(PVPMatchCCAction.class, string);
        }

        private PVPMatchCCAction(int n2) {
            this.vX = n2;
        }

        public int getVal() {
            return this.vX;
        }

        private static /* synthetic */ PVPMatchCCAction[] a() {
            return new PVPMatchCCAction[]{INIT, UPDATE, DONE};
        }

        static {
            a = PVPMatchCCAction.a();
        }
    }
}
