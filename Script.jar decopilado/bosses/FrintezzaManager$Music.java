/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.commons.util.Rnd
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage
 *  l2.gameserver.network.l2.s2c.ExShowScreenMessage$ScreenMessageAlign
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.network.l2.s2c.MagicSkillUse
 */
package bosses;

import java.util.ArrayList;
import java.util.List;
import l2.commons.threading.RunnableImpl;
import l2.commons.util.Rnd;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.s2c.ExShowScreenMessage;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.MagicSkillUse;

private class FrintezzaManager.Music
extends RunnableImpl {
    private int bv;

    private FrintezzaManager.Music(int n) {
        this.bv = n;
    }

    public void runImpl() throws Exception {
        if (FrintezzaManager.this.h == null) {
            return;
        }
        FrintezzaManager.this.v();
        if (!FrintezzaManager.this.h.isBlocked()) {
            switch (this.bv) {
                case 1: {
                    String string = "Requiem of Hatred";
                    FrintezzaManager.this.g.setActive(true);
                    FrintezzaManager.this.h.setActive(true);
                    FrintezzaManager.this.h.broadcastPacket(new L2GameServerPacket[]{new ExShowScreenMessage(string, 3000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true, 1, -1, true)});
                    FrintezzaManager.this.h.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)FrintezzaManager.this.h, (Creature)FrintezzaManager.this.h, 5007, 1, bt, 0L)});
                    break;
                }
                case 2: {
                    String string = "Frenetic Toccata";
                    FrintezzaManager.this.m.setActive(true);
                    FrintezzaManager.this.n.setActive(true);
                    FrintezzaManager.this.h.broadcastPacket(new L2GameServerPacket[]{new ExShowScreenMessage(string, 3000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true, 1, -1, true)});
                    FrintezzaManager.this.h.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)FrintezzaManager.this.h, (Creature)FrintezzaManager.this.h, 5007, 2, bt, 0L)});
                    break;
                }
                case 3: {
                    String string = "Fugue of Jubilation";
                    FrintezzaManager.this.i.setActive(true);
                    FrintezzaManager.this.j.setActive(true);
                    FrintezzaManager.this.h.broadcastPacket(new L2GameServerPacket[]{new ExShowScreenMessage(string, 3000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true, 1, -1, true)});
                    FrintezzaManager.this.h.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)FrintezzaManager.this.h, (Creature)FrintezzaManager.this.h, 5007, 3, bt, 0L)});
                    break;
                }
                case 4: {
                    String string = "Mournful Chorale Prelude";
                    FrintezzaManager.this.o.setActive(true);
                    FrintezzaManager.this.p.setActive(true);
                    FrintezzaManager.this.h.broadcastPacket(new L2GameServerPacket[]{new ExShowScreenMessage(string, 3000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true, 1, -1, true)});
                    FrintezzaManager.this.h.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)FrintezzaManager.this.h, (Creature)FrintezzaManager.this.h, 5007, 4, bt, 0L)});
                    break;
                }
                case 5: {
                    String string = "Hypnotic Mazurka";
                    FrintezzaManager.this.k.setActive(true);
                    FrintezzaManager.this.l.setActive(true);
                    FrintezzaManager.this.h.broadcastPacket(new L2GameServerPacket[]{new ExShowScreenMessage(string, 3000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true, 1, -1, true)});
                    FrintezzaManager.this.h.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)FrintezzaManager.this.h, (Creature)FrintezzaManager.this.h, 5007, 5, bt, 0L)});
                    break;
                }
                default: {
                    String string = "Rondo of Solitude";
                    if (FrintezzaManager.this.h != null) {
                        FrintezzaManager.this.h.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)FrintezzaManager.this.h, (Creature)FrintezzaManager.this.h, 5006, 1, bt, 0L)});
                    }
                    FrintezzaManager.this.v();
                }
            }
        }
        FrintezzaManager.this.o = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new FrintezzaManager.Music(this.f())), (long)(bt + Rnd.get((int)10000)));
    }

    private List<Creature> a(int n) {
        ArrayList<Creature> arrayList = new ArrayList<Creature>();
        if (n < 4) {
            if (FrintezzaManager.this.i != null && !FrintezzaManager.this.i.isDead()) {
                arrayList.add((Creature)FrintezzaManager.this.i);
            }
            if (FrintezzaManager.this.j != null && !FrintezzaManager.this.j.isDead()) {
                arrayList.add((Creature)FrintezzaManager.this.j);
            }
            for (int i = 0; i < 4; ++i) {
                if (FrintezzaManager.this.a[i] != null && !FrintezzaManager.this.a[i].isDead() && FrintezzaManager.this.a[i] != null) {
                    arrayList.add((Creature)FrintezzaManager.this.a[i]);
                }
                if (FrintezzaManager.this.b[i] == null || FrintezzaManager.this.b[i].isDead()) continue;
                arrayList.add((Creature)FrintezzaManager.this.b[i]);
            }
        } else {
            for (Player player : FrintezzaManager.this.f.getInsidePlayers()) {
                if (player.isDead()) continue;
                arrayList.add((Creature)player);
            }
        }
        return arrayList;
    }

    private int f() {
        if (this.d()) {
            return 1;
        }
        return 1 + Rnd.get((int)5);
    }

    private boolean d() {
        if (!Rnd.chance((int)40)) {
            return false;
        }
        if (FrintezzaManager.this.i != null && !FrintezzaManager.this.i.isAlikeDead() && FrintezzaManager.this.i.getCurrentHp() < (double)(FrintezzaManager.this.i.getMaxHp() * 2 / 3)) {
            return true;
        }
        if (FrintezzaManager.this.j != null && !FrintezzaManager.this.j.isAlikeDead() && FrintezzaManager.this.j.getCurrentHp() < (double)(FrintezzaManager.this.j.getMaxHp() * 2 / 3)) {
            return true;
        }
        for (int i = 0; i < 4; ++i) {
            if (FrintezzaManager.this.a[i] != null && !FrintezzaManager.this.a[i].isDead() && FrintezzaManager.this.a[i].getCurrentHp() < (double)(FrintezzaManager.this.a[i].getMaxHp() / 3)) {
                return true;
            }
            if (FrintezzaManager.this.b[i] == null || FrintezzaManager.this.b[i].isDead() || !(FrintezzaManager.this.b[i].getCurrentHp() < (double)(FrintezzaManager.this.b[i].getMaxHp() / 3))) continue;
            return true;
        }
        return false;
    }
}
