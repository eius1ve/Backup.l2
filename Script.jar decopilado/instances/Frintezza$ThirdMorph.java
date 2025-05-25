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
package instances;

import instances.Frintezza;
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

private class Frintezza.ThirdMorph
extends RunnableImpl {
    private int bp = 0;
    private int bw = 0;

    public Frintezza.ThirdMorph(int n) {
        this.bp = n;
    }

    public void runImpl() throws Exception {
        try {
            switch (this.bp) {
                case 1: {
                    this.bw = Math.abs((Frintezza.this.i.getHeading() < 32768 ? 180 : 540) - (int)((double)Frintezza.this.i.getHeading() / 182.044444444));
                    for (Player player : Frintezza.this.getPlayers()) {
                        player.enterMovieMode();
                    }
                    Frintezza.this.a(true);
                    Frintezza.this.h.broadcastPacket(new L2GameServerPacket[]{new MagicSkillCanceled((Creature)Frintezza.this.h)});
                    Frintezza.this.h.broadcastPacket(new L2GameServerPacket[]{new SocialAction(Frintezza.this.h.getObjectId(), 4)});
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Frintezza.ThirdMorph(2)), 100L);
                    break;
                }
                case 2: {
                    Frintezza.this.a(Frintezza.this.h, 250, 120, 15, 0, 1000, 0);
                    Frintezza.this.a(Frintezza.this.h, 250, 120, 15, 0, 10000, 0);
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Frintezza.ThirdMorph(3)), 6500L);
                    break;
                }
                case 3: {
                    Frintezza.this.h.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)Frintezza.this.h, (Creature)Frintezza.this.h, 5006, 1, 34000, 0L)});
                    Frintezza.this.a(Frintezza.this.h, 500, 70, 15, 3000, 10000, 0);
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Frintezza.ThirdMorph(4)), 3000L);
                    break;
                }
                case 4: {
                    Frintezza.this.a(Frintezza.this.h, 2500, 90, 12, 6000, 10000, 0);
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Frintezza.ThirdMorph(5)), 3000L);
                    break;
                }
                case 5: {
                    Frintezza.this.a(Frintezza.this.i, 250, this.bw, 12, 0, 1000, 0);
                    Frintezza.this.a(Frintezza.this.i, 250, this.bw, 12, 0, 10000, 0);
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Frintezza.ThirdMorph(6)), 500L);
                    break;
                }
                case 6: {
                    Frintezza.this.i.doDie((Creature)Frintezza.this.i);
                    Frintezza.this.a(Frintezza.this.i, 450, this.bw, 14, 8000, 8000, 0);
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Frintezza.ThirdMorph(7)), 6250L);
                    break;
                }
                case 7: {
                    Frintezza.NpcLocation npcLocation = new Frintezza.NpcLocation();
                    npcLocation.set(Frintezza.this.i.getLoc());
                    npcLocation.npcId = 29047;
                    Frintezza.this.i.deleteMe();
                    Frintezza.this.i = null;
                    Frintezza.this.j = Frintezza.this.a(npcLocation);
                    Frintezza.this.j.addListener((Listener)Frintezza.this.a);
                    Frintezza.this.a(Frintezza.this.j, true);
                    Frintezza.this.a(Frintezza.this.j, 450, this.bw, 12, 500, 14000, 2);
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new Frintezza.ThirdMorph(9)), 5000L);
                    break;
                }
                case 9: {
                    Frintezza.this.a(false);
                    for (Player player : Frintezza.this.getPlayers()) {
                        player.leaveMovieMode();
                    }
                    Skill skill = SkillTable.getInstance().getInfo(5017, 1);
                    skill.getEffects((Creature)Frintezza.this.j, (Creature)Frintezza.this.j, false, false);
                }
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
