/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.ai.Fighter
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.scripts.Functions
 */
package ai;

import l2.commons.util.Rnd;
import l2.gameserver.ai.Fighter;
import l2.gameserver.model.Creature;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.scripts.Functions;

public class FrightenedOrc
extends Fighter {
    private boolean o;
    private final String[] g = new String[]{"1000007", "1000008", "1000009", "1000010", "1000011", "1000012", "1000013", "1000014", "1000015", "1000016", "1000017", "1000018", "1000019", "1000020", "1000021", "1000022", "1000023", "1000024", "1000025", "1000026", "1000027"};

    public FrightenedOrc(NpcInstance npcInstance) {
        super(npcInstance);
    }

    protected void onEvtSpawn() {
        this.o = true;
        super.onEvtSpawn();
    }

    protected void onEvtAttacked(Creature creature, int n) {
        NpcInstance npcInstance = this.getActor();
        int n2 = Rnd.get((int)100);
        int n3 = n2 / 7;
        if (creature != null && Rnd.chance((int)10) && this.o) {
            if (n3 < this.g.length) {
                Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)this.g[n3], (Object[])new Object[0]);
            } else {
                Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"1000027", (Object[])new Object[0]);
                this.o = false;
            }
        }
        super.onEvtAttacked(creature, n);
    }
}
