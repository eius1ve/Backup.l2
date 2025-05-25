/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.ArrayList;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.oly.Participant;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExReceiveOlympiadResult
extends L2GameServerPacket {
    private String fm;
    private int wZ;
    private ArrayList<ExReceiveOlympiadResultRecord> g;
    private ArrayList<ExReceiveOlympiadResultRecord> h;

    public ExReceiveOlympiadResult(int n, String string) {
        this.fm = string;
        this.wZ = n;
        this.g = new ArrayList();
        this.h = new ArrayList();
    }

    public void add(int n, Player player, int n2, int n3, int n4) {
        if (n == Participant.SIDE_RED) {
            this.g.add(new ExReceiveOlympiadResultRecord(player, n2, n3, n4));
        }
        if (n == Participant.SIDE_BLUE) {
            this.h.add(new ExReceiveOlympiadResultRecord(player, n2, n3, n4));
        }
    }

    @Override
    protected void writeImpl() {
        this.writeEx(213);
        this.writeD(1);
        if (this.wZ != 0) {
            this.writeD(0);
            this.writeS(this.fm);
        } else {
            this.writeD(1);
            this.writeS("");
        }
        if (this.wZ == Participant.SIDE_RED) {
            this.writeD(1);
            this.writeD(this.g.size());
            for (ExReceiveOlympiadResultRecord exReceiveOlympiadResultRecord : this.g) {
                this.writeS(exReceiveOlympiadResultRecord.name);
                this.writeS(exReceiveOlympiadResultRecord.clan);
                this.writeD(exReceiveOlympiadResultRecord.crest_id);
                this.writeD(exReceiveOlympiadResultRecord.class_id);
                this.writeD(exReceiveOlympiadResultRecord.dmg);
                this.writeD(exReceiveOlympiadResultRecord.points);
                this.writeD(exReceiveOlympiadResultRecord.delta);
                this.writeD(0);
            }
            this.writeD(0);
            this.writeD(this.h.size());
            for (ExReceiveOlympiadResultRecord exReceiveOlympiadResultRecord : this.h) {
                this.writeS(exReceiveOlympiadResultRecord.name);
                this.writeS(exReceiveOlympiadResultRecord.clan);
                this.writeD(exReceiveOlympiadResultRecord.crest_id);
                this.writeD(exReceiveOlympiadResultRecord.class_id);
                this.writeD(exReceiveOlympiadResultRecord.dmg);
                this.writeD(exReceiveOlympiadResultRecord.points);
                this.writeD(exReceiveOlympiadResultRecord.delta);
                this.writeD(0);
            }
        } else {
            this.writeD(0);
            this.writeD(this.h.size());
            for (ExReceiveOlympiadResultRecord exReceiveOlympiadResultRecord : this.h) {
                this.writeS(exReceiveOlympiadResultRecord.name);
                this.writeS(exReceiveOlympiadResultRecord.clan);
                this.writeD(exReceiveOlympiadResultRecord.crest_id);
                this.writeD(exReceiveOlympiadResultRecord.class_id);
                this.writeD(exReceiveOlympiadResultRecord.dmg);
                this.writeD(exReceiveOlympiadResultRecord.points);
                this.writeD(exReceiveOlympiadResultRecord.delta);
                this.writeD(0);
            }
            this.writeD(1);
            this.writeD(this.g.size());
            for (ExReceiveOlympiadResultRecord exReceiveOlympiadResultRecord : this.g) {
                this.writeS(exReceiveOlympiadResultRecord.name);
                this.writeS(exReceiveOlympiadResultRecord.clan);
                this.writeD(exReceiveOlympiadResultRecord.crest_id);
                this.writeD(exReceiveOlympiadResultRecord.class_id);
                this.writeD(exReceiveOlympiadResultRecord.dmg);
                this.writeD(exReceiveOlympiadResultRecord.points);
                this.writeD(exReceiveOlympiadResultRecord.delta);
                this.writeD(0);
            }
        }
    }

    private class ExReceiveOlympiadResultRecord {
        String name;
        String clan;
        int class_id;
        int crest_id;
        int dmg;
        int points;
        int delta;

        public ExReceiveOlympiadResultRecord(Player player, int n, int n2, int n3) {
            this.name = player.getName();
            this.class_id = player.getClassId().getId();
            this.clan = player.getClan() != null ? player.getClan().getName() : "";
            this.crest_id = player.getClanId();
            this.dmg = n;
            this.points = n2;
            this.delta = n3;
        }
    }
}
