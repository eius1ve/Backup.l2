/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.model.Player;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;

public class RequestHardWareInfo
extends L2GameClientPacket {
    private String el;
    private int rf;
    private int rg;
    private int rh;
    private int ri;
    private int rj;
    private int rk;
    private String em;
    private int rl;
    private int rm;
    private int rn;
    private int ro;
    private int rp;
    private int rq;
    private int rr;
    private int rs;
    private int rt;
    private String en;
    private String eo;
    private byte[] p = new byte[16];

    @Override
    protected void readImpl() throws Exception {
        this.el = this.readS();
        this.rf = this.readD();
        this.rg = this.readD();
        this.rh = this.readD();
        this.ri = this.readD();
        this.rj = this.readD();
        this.rk = this.readD();
        this.readB(this.p);
        this.em = this.readS();
        this.rl = this.readD();
        this.rm = this.readC();
        this.readD();
        this.rn = this.readD();
        this.ro = this.readD();
        this.rp = this.readD();
        this.rq = this.readD();
        this.rr = this.readD();
        this.readC();
        this.rs = this.readD();
        this.readD();
        this.rt = this.readH();
        this.en = this.readS();
        this.eo = this.readS();
    }

    @Override
    protected void runImpl() throws Exception {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
    }
}
