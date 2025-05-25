/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Skill
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.network.l2.s2c.SocialAction
 *  l2.gameserver.tables.SkillTable
 */
package bosses;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.SocialAction;
import l2.gameserver.tables.SkillTable;

private class FrintezzaManager.SecondMorph
extends RunnableImpl {
    private int bp = 0;

    public FrintezzaManager.SecondMorph(int n) {
        this.bp = n;
    }

    public void runImpl() throws Exception {
        try {
            switch (this.bp) {
                case 1: {
                    int n = Math.abs((FrintezzaManager.this.i.getHeading() < 32768 ? 180 : 540) - (int)((double)FrintezzaManager.this.i.getHeading() / 182.044444444));
                    for (Player player : FrintezzaManager.this.f.getInsidePlayers()) {
                        player.enterMovieMode();
                    }
                    FrintezzaManager.this.a(true);
                    FrintezzaManager.this.a(FrintezzaManager.this.i, 500, n, 5, 500, 15000, 0);
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new FrintezzaManager.SecondMorph(2)), 2000L);
                    break;
                }
                case 2: {
                    FrintezzaManager.this.i.broadcastPacket(new L2GameServerPacket[]{new SocialAction(FrintezzaManager.this.i.getObjectId(), 1)});
                    FrintezzaManager.this.i.setCurrentHp((double)(FrintezzaManager.this.i.getMaxHp() * 3 / 4), false);
                    FrintezzaManager.this.i.setRHandId(7903);
                    FrintezzaManager.this.i.broadcastCharInfo();
                    ThreadPoolManager.getInstance().schedule((Runnable)((Object)new FrintezzaManager.SecondMorph(3)), 5500L);
                    break;
                }
                case 3: {
                    FrintezzaManager.this.i.broadcastPacket(new L2GameServerPacket[]{new SocialAction(FrintezzaManager.this.i.getObjectId(), 4)});
                    FrintezzaManager.this.a(false);
                    Skill skill = SkillTable.getInstance().getInfo(5017, 1);
                    skill.getEffects((Creature)FrintezzaManager.this.i, (Creature)FrintezzaManager.this.i, false, false);
                    for (Player player : FrintezzaManager.this.f.getInsidePlayers()) {
                        player.leaveMovieMode();
                    }
                    break;
                }
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
