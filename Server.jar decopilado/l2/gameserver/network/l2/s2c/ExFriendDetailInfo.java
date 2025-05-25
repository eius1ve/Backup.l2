/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.Calendar;
import l2.gameserver.model.actor.instances.player.Friend;
import l2.gameserver.model.pledge.Alliance;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExFriendDetailInfo
extends L2GameServerPacket {
    private String name;
    private boolean eF;
    private int objectId;
    private int level;
    private int classId;
    private int vq;
    private int clanId;
    private int vr;
    private int allyId;
    private int allyCrestId;
    private String clanName;
    private String eV;
    private int dh;
    private String eS;
    private int vs;
    private int vt;

    public ExFriendDetailInfo(int n, Friend friend) {
        Object object;
        this.vq = n;
        this.name = friend.getName();
        this.eF = friend.isOnline();
        this.objectId = friend.getObjectId();
        this.level = friend.getLevel();
        this.classId = friend.getClassId();
        this.eS = friend.getMemo();
        Clan clan = friend.getClan();
        if (clan != null) {
            this.clanId = clan.getClanId();
            this.vr = clan.getCrestId();
            this.clanName = clan.getName();
            object = clan.getAlliance();
            if (object != null) {
                this.allyId = ((Alliance)object).getAllyId();
                this.eV = ((Alliance)object).getAllyName();
                this.allyCrestId = ((Alliance)object).getAllyCrestId();
            }
        }
        object = Calendar.getInstance();
        ((Calendar)object).setTimeInMillis(friend.getCreateTime());
        this.vs = ((Calendar)object).get(5);
        this.vt = ((Calendar)object).get(2) + 1;
        this.dh = (int)(friend.isOnline() ? 0L : (System.currentTimeMillis() - friend.getLastAccess()) / 1000L);
    }

    @Override
    protected void writeImpl() {
        this.writeEx(236);
        this.writeD(this.objectId);
        this.writeS(this.name);
        this.writeD(this.eF);
        this.writeD(this.objectId);
        this.writeH(this.level);
        this.writeH(this.classId);
        this.writeD(this.clanId);
        this.writeD(this.vr);
        this.writeS(this.clanName);
        this.writeD(this.allyId);
        this.writeD(this.allyCrestId);
        this.writeS(this.eV);
        this.writeC(this.vt);
        this.writeC(this.vs);
        this.writeD(this.eF ? -1 : this.dh);
        this.writeS(this.eS);
    }
}
