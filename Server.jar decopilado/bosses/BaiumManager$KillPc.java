/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.ai.CtrlIntention
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.instances.BossInstance
 *  l2.gameserver.tables.SkillTable
 *  l2.gameserver.utils.Location
 */
package bosses;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.ai.CtrlIntention;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.instances.BossInstance;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.utils.Location;

public static class BaiumManager.KillPc
extends RunnableImpl {
    private BossInstance c;
    private Player a;

    public BaiumManager.KillPc(Player player, BossInstance bossInstance) {
        this.a = player;
        this.c = bossInstance;
    }

    public void runImpl() throws Exception {
        Skill skill = SkillTable.getInstance().getInfo(4136, 1);
        Location location = Location.findFrontPosition((GameObject)this.c, (GameObject)this.a, (int)200, (int)250);
        if (this.a != null && skill != null) {
            this.a.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE, null, null);
            this.a.teleToLocation(location);
            this.c.setTarget((GameObject)this.a);
            this.c.doCast(skill, (Creature)this.a, false);
        }
    }
}
