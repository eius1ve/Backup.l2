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
package instances;

import instances.Frintezza;
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

private class Frintezza.Music
extends RunnableImpl {
    private Frintezza.Music() {
    }

    public void runImpl() throws Exception {
        String string;
        if (Frintezza.this.h == null) {
            return;
        }
        int n = Math.max(1, Math.min(4, this.f()));
        switch (n) {
            case 1: {
                string = "Requiem of Hatred";
                break;
            }
            case 2: {
                string = "Frenetic Toccata";
                break;
            }
            case 3: {
                string = "Fugue of Jubilation";
                break;
            }
            case 4: {
                string = "Mournful Chorale Prelude";
                break;
            }
            default: {
                return;
            }
        }
        if (!Frintezza.this.h.isBlocked()) {
            Frintezza.this.h.broadcastPacket(new L2GameServerPacket[]{new ExShowScreenMessage(string, 3000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true, 1, -1, true)});
            Frintezza.this.h.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)Frintezza.this.h, (Creature)Frintezza.this.h, 5007, n, bt, 0L)});
            ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Frintezza.SongEffectLaunched(Frintezza.this, this.a(n), n, 10000)), 10000L);
        }
        Frintezza.this.o = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Frintezza.Music()), (long)(bt + Rnd.get((int)10000)));
    }

    private List<Creature> a(int n) {
        ArrayList<Creature> arrayList = new ArrayList<Creature>();
        if (n < 4) {
            if (Frintezza.this.i != null && !Frintezza.this.i.isDead()) {
                arrayList.add((Creature)Frintezza.this.i);
            }
            if (Frintezza.this.j != null && !Frintezza.this.j.isDead()) {
                arrayList.add((Creature)Frintezza.this.j);
            }
            for (int i = 0; i < 4; ++i) {
                if (Frintezza.this.a[i] != null && !Frintezza.this.a[i].isDead()) {
                    arrayList.add((Creature)Frintezza.this.a[i]);
                }
                if (Frintezza.this.b[i] == null || Frintezza.this.b[i].isDead()) continue;
                arrayList.add((Creature)Frintezza.this.b[i]);
            }
        } else {
            for (Player player : Frintezza.this.getPlayers()) {
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
        return Rnd.get((int)2, (int)4);
    }

    private boolean d() {
        if (!Rnd.chance((int)40)) {
            return false;
        }
        if (Frintezza.this.i != null && !Frintezza.this.i.isAlikeDead() && Frintezza.this.i.getCurrentHp() < (double)(Frintezza.this.i.getMaxHp() * 2 / 3)) {
            return true;
        }
        if (Frintezza.this.j != null && !Frintezza.this.j.isAlikeDead() && Frintezza.this.j.getCurrentHp() < (double)(Frintezza.this.j.getMaxHp() * 2 / 3)) {
            return true;
        }
        for (int i = 0; i < 4; ++i) {
            if (Frintezza.this.a[i] != null && !Frintezza.this.a[i].isDead() && Frintezza.this.a[i].getCurrentHp() < (double)(Frintezza.this.a[i].getMaxHp() / 3)) {
                return true;
            }
            if (Frintezza.this.b[i] == null || Frintezza.this.b[i].isDead() || !(Frintezza.this.b[i].getCurrentHp() < (double)(Frintezza.this.b[i].getMaxHp() / 3))) continue;
            return true;
        }
        return false;
    }
}
