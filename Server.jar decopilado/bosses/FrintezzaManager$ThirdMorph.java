/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.listener.Listener
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Skill
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.network.l2.s2c.MagicSkillCanceled
 *  l2.gameserver.network.l2.s2c.MagicSkillUse
 *  l2.gameserver.network.l2.s2c.SocialAction
 *  l2.gameserver.tables.SkillTable
 */
package bosses;

import bosses.FrintezzaManager;
import l2.commons.listener.Listener;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.MagicSkillCanceled;
import l2.gameserver.network.l2.s2c.MagicSkillUse;
import l2.gameserver.network.l2.s2c.SocialAction;
import l2.gameserver.tables.SkillTable;

private class FrintezzaManager.ThirdMorph
extends RunnableImpl {
    private int bp = 0;
    private int bw = 0;

    public FrintezzaManager.ThirdMorph(int n) {
        this.bp = n;
    }

    public void runImpl() throws Exception {
        try {
            switch (this.bp) {
                case 1: {
                    this.bw = Math.abs((FrintezzaManager.this.i.getHeading() < 32768 ? 180 : 540) - (int)((double)FrintezzaManager.this.i.getHeading() / 182.044444444));
                    for (Player player : FrintezzaManager.this.f.getInsidePlayers()) {
                        player.enterMovieMode();
                    }
                    FrintezzaManager.this.a(true);
                    FrintezzaManager.this.h.broadcastPacket(new L2GameServerPacket[]{new MagicSkillCanceled((Creature)FrintezzaManager.this.h)});
                    FrintezzaManager.this.h.broadcastPacket(new L2GameServerPacket[]{new SocialAction(FrintezzaManager.this.h.getObjectId(), 4)});
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new FrintezzaManager.ThirdMorph(2)), 100L);
                    break;
                }
                case 2: {
                    FrintezzaManager.this.a(FrintezzaManager.this.h, 250, 120, 15, 0, 1000, 0);
                    FrintezzaManager.this.a(FrintezzaManager.this.h, 250, 120, 15, 0, 10000, 0);
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new FrintezzaManager.ThirdMorph(3)), 6500L);
                    break;
                }
                case 3: {
                    FrintezzaManager.this.h.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)FrintezzaManager.this.h, (Creature)FrintezzaManager.this.h, 5006, 1, 34000, 0L)});
                    FrintezzaManager.this.a(FrintezzaManager.this.h, 500, 70, 15, 3000, 10000, 0);
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new FrintezzaManager.ThirdMorph(4)), 3000L);
                    break;
                }
                case 4: {
                    FrintezzaManager.this.a(FrintezzaManager.this.h, 2500, 90, 12, 6000, 10000, 0);
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new FrintezzaManager.ThirdMorph(5)), 3000L);
                    break;
                }
                case 5: {
                    FrintezzaManager.this.a(FrintezzaManager.this.i, 250, this.bw, 12, 0, 1000, 0);
                    FrintezzaManager.this.a(FrintezzaManager.this.i, 250, this.bw, 12, 0, 10000, 0);
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new FrintezzaManager.ThirdMorph(6)), 500L);
                    break;
                }
                case 6: {
                    FrintezzaManager.this.i.doDie((Creature)FrintezzaManager.this.i);
                    FrintezzaManager.this.a(FrintezzaManager.this.i, 450, this.bw, 14, 8000, 8000, 0);
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new FrintezzaManager.ThirdMorph(7)), 6250L);
                    break;
                }
                case 7: {
                    FrintezzaManager.NpcLocation npcLocation = new FrintezzaManager.NpcLocation();
                    npcLocation.set(FrintezzaManager.this.i.getLoc());
                    npcLocation.npcId = 29047;
                    FrintezzaManager.this.i.deleteMe();
                    FrintezzaManager.this.i = null;
                    FrintezzaManager.this.j = FrintezzaManager.this.a(npcLocation);
                    FrintezzaManager.this.j.addListener((Listener)FrintezzaManager.this.a);
                    FrintezzaManager.this.a(FrintezzaManager.this.j, true);
                    FrintezzaManager.this.a(FrintezzaManager.this.j, 450, this.bw, 12, 500, 14000, 2);
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new FrintezzaManager.ThirdMorph(9)), 5000L);
                    break;
                }
                case 9: {
                    FrintezzaManager.this.a(false);
                    for (Player player : FrintezzaManager.this.f.getInsidePlayers()) {
                        player.leaveMovieMode();
                    }
                    Skill skill = SkillTable.getInstance().getInfo(5017, 1);
                    skill.getEffects((Creature)FrintezzaManager.this.j, (Creature)FrintezzaManager.this.j, false, false);
                }
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
