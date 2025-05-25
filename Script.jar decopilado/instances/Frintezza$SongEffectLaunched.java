/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.model.Creature
 *  l2.gameserver.tables.SkillTable
 */
package instances;

import java.util.List;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.model.Creature;
import l2.gameserver.tables.SkillTable;

private class Frintezza.SongEffectLaunched
extends RunnableImpl {
    private final List<Creature> L;
    private final int cO;
    private final int cP;

    public Frintezza.SongEffectLaunched(List<Creature> list, int n, int n2) {
        this.L = list;
        this.cO = n;
        this.cP = n2;
    }

    public void runImpl() throws Exception {
        if (Frintezza.this.h == null) {
            return;
        }
        if (this.cP > bt) {
            return;
        }
        Frintezza.SongEffectLaunched songEffectLaunched = new Frintezza.SongEffectLaunched(this.L, this.cO, this.cP + bt / 10);
        ThreadPoolManager.getInstance().schedule((Runnable)((Object)songEffectLaunched), (long)(bt / 10));
        Frintezza.this.h.callSkill(SkillTable.getInstance().getInfo(5008, this.cO), this.L, false);
    }
}
