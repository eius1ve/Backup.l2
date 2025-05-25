/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.ai.DefaultAI
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.tables.SkillTable
 */
package ai;

import l2.commons.util.Rnd;
import l2.gameserver.ai.DefaultAI;
import l2.gameserver.model.Creature;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.scripts.Functions;
import l2.gameserver.tables.SkillTable;

public class EvilNpc
extends DefaultAI {
    private long o;
    private static final String[] e = new String[]{"EvilNpc.1", "EvilNpc.2", "EvilNpc.3", "EvilNpc.4", "EvilNpc.5"};

    public EvilNpc(NpcInstance npcInstance) {
        super(npcInstance);
    }

    protected void onEvtAttacked(Creature creature, int n) {
        NpcInstance npcInstance = this.getActor();
        if (creature == null || creature.getPlayer() == null) {
            return;
        }
        if (System.currentTimeMillis() - this.o > 3000L) {
            int n2 = Rnd.get((int)0, (int)100);
            if (n2 < 2) {
                creature.getPlayer().setKarma(creature.getPlayer().getKarma() + 5, true);
            } else if (n2 < 4) {
                npcInstance.doCast(SkillTable.getInstance().getInfo(4578, 1), creature, true);
            } else {
                npcInstance.doCast(SkillTable.getInstance().getInfo(4185, 7), creature, true);
            }
            Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)e[Rnd.get((int)e.length)], (Object[])new Object[]{creature.getName()});
            this.o = System.currentTimeMillis();
        }
    }
}
