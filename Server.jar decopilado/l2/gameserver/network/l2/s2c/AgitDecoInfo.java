/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.entity.residence.ClanHall;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AgitDecoInfo
extends L2GameServerPacket {
    private static final Logger cY = LoggerFactory.getLogger(AgitDecoInfo.class);
    private static int[] aW = new int[]{0, 1, 1, 1, 2, 2, 2, 2, 2, 0, 0, 1, 1, 1, 2, 2, 2, 2, 2};
    private static int[] aX = new int[]{0, 1, 2, 2};
    private int _id;
    private int sy;
    private int sz;
    private int sA;
    private int sB;
    private int sC;
    private int sD;
    private int sE;
    private int sF;

    public AgitDecoInfo(ClanHall clanHall) {
        this._id = clanHall.getId();
        this.sy = AgitDecoInfo.i(clanHall.isFunctionActive(3) ? clanHall.getFunction(3).getLevel() : 0);
        this.sz = AgitDecoInfo.j(clanHall.isFunctionActive(4) ? clanHall.getFunction(4).getLevel() : 0);
        this.sA = AgitDecoInfo.k(clanHall.isFunctionActive(5) ? clanHall.getFunction(5).getLevel() : 0);
        this.sB = clanHall.isFunctionActive(1) ? clanHall.getFunction(1).getLevel() : 0;
        this.sC = clanHall.isFunctionActive(7) ? clanHall.getFunction(7).getLevel() : 0;
        this.sD = clanHall.isFunctionActive(2) ? aX[clanHall.getFunction(2).getLevel()] : 0;
        this.sE = clanHall.isFunctionActive(6) ? aW[clanHall.getFunction(6).getLevel()] : 0;
        this.sF = clanHall.isFunctionActive(8) ? clanHall.getFunction(8).getLevel() : 0;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(253);
        this.writeD(this._id);
        this.writeC(this.sy);
        this.writeC(this.sz);
        this.writeC(this.sz);
        this.writeC(this.sA);
        this.writeC(this.sB);
        this.writeC(0);
        this.writeC(this.sC);
        this.writeC(this.sD);
        this.writeC(this.sE);
        this.writeC(this.sE);
        this.writeC(this.sF);
        this.writeC(this.sD);
        this.writeD(0);
        this.writeD(0);
        this.writeD(0);
        this.writeD(0);
        this.writeD(0);
    }

    private static int i(int n) {
        switch (n) {
            case 0: {
                return 0;
            }
            case 20: 
            case 40: 
            case 80: 
            case 120: 
            case 140: {
                return 1;
            }
            case 160: 
            case 180: 
            case 200: 
            case 220: 
            case 240: 
            case 260: 
            case 280: 
            case 300: {
                return 2;
            }
        }
        cY.warn("Unsupported percent " + n + " in hp recovery");
        return 0;
    }

    private static int j(int n) {
        switch (n) {
            case 0: {
                return 0;
            }
            case 5: 
            case 10: 
            case 15: 
            case 20: {
                return 1;
            }
            case 25: 
            case 30: 
            case 35: 
            case 40: 
            case 45: 
            case 50: {
                return 2;
            }
        }
        cY.warn("Unsupported percent " + n + " in mp recovery");
        return 0;
    }

    private static int k(int n) {
        switch (n) {
            case 0: {
                return 0;
            }
            case 5: 
            case 10: 
            case 15: 
            case 20: {
                return 1;
            }
            case 25: 
            case 30: 
            case 35: 
            case 40: 
            case 45: 
            case 50: {
                return 2;
            }
        }
        cY.warn("Unsupported percent " + n + " in exp recovery");
        return 0;
    }
}
