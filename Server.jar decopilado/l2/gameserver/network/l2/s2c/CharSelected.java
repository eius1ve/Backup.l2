/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.GameTimeController;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.utils.Location;

public class CharSelected
extends L2GameServerPacket {
    private int dL;
    private int char_id;
    private int clan_id;
    private int sex;
    private int race;
    private int class_id;
    private String _name;
    private String _title;
    private Location _loc;
    private double X;
    private double Y;
    private int level;
    private int karma;
    private int gi;
    private long _exp;
    private long dc;

    public CharSelected(Player player, int n) {
        this.dL = n;
        this._name = player.getName();
        this.char_id = player.getObjectId();
        this._title = player.getTitle();
        this.clan_id = player.getClanId();
        this.sex = player.getSex();
        this.race = player.getRace().ordinal();
        this.class_id = player.getClassId().getId();
        this._loc = player.getLoc();
        this.X = player.getCurrentHp();
        this.Y = player.getCurrentMp();
        this.dc = player.getIntSp();
        this._exp = player.getExp();
        this.level = player.getLevel();
        this.karma = -player.getKarma();
        this.gi = player.getPkKills();
    }

    @Override
    protected final void writeImpl() {
        this.writeC(11);
        this.writeS(this._name);
        this.writeD(this.char_id);
        this.writeS(this._title);
        this.writeD(this.dL);
        this.writeD(this.clan_id);
        this.writeD(0);
        this.writeD(this.sex);
        this.writeD(this.race);
        this.writeD(this.class_id);
        this.writeD(1);
        this.writeD(this._loc.x);
        this.writeD(this._loc.y);
        this.writeD(this._loc.z);
        this.writeF(this.X);
        this.writeF(this.Y);
        this.writeQ(this.dc);
        this.writeQ(this._exp);
        this.writeD(this.level);
        this.writeD(this.karma);
        this.writeD(this.gi);
        this.writeD(GameTimeController.getInstance().getGameTime());
        this.writeD(0);
        this.writeD(this.class_id);
        this.writeB(new byte[16]);
        this.writeD(0);
        this.writeD(0);
        this.writeD(0);
        this.writeD(0);
        this.writeD(0);
        this.writeD(0);
        this.writeD(0);
        this.writeD(0);
        this.writeD(0);
        this.writeB(new byte[28]);
        this.writeD(0);
    }
}
