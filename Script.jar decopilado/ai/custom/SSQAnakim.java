/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.ai.Mystic
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.entity.Reflection
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.network.l2.s2c.MagicSkillUse
 *  l2.gameserver.scripts.Functions
 */
package ai.custom;

import java.util.List;
import l2.commons.util.Rnd;
import l2.gameserver.ai.Mystic;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.Reflection;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.MagicSkillUse;
import l2.gameserver.scripts.Functions;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class SSQAnakim
extends Mystic {
    private static final String l = "%playerName%";
    private static final String[] l = new String[]{"19606", "19607", "19608", "19609"};
    private static final String[] m = new String[]{"My power's weakening.. Hurry and turn on the sealing device!!!", "All 4 sealing devices must be turned on!!!", "Lilith's attack is getting stronger! Go ahead and turn it on!", "%playerName%, hold on. We're almost done!"};
    private long L = 0L;
    private long M = 0L;
    private long N = 0L;

    public SSQAnakim(NpcInstance npcInstance) {
        super(npcInstance);
        npcInstance.setHasChatWindow(false);
    }

    protected void onEvtSpawn() {
        super.onEvtSpawn();
    }

    protected boolean thinkActive() {
        if (this.L < System.currentTimeMillis()) {
            Functions.npcSayCustomMessage((NpcInstance)this.getActor(), (String)l[Rnd.get((int)l.length)], (Object[])new Object[0]);
            this.L = System.currentTimeMillis() + 12000L;
        }
        if (this.M < System.currentTimeMillis()) {
            Player player = this.getPlayer();
            if (player != null) {
                String string = m[Rnd.get((int)m.length)];
                if (string.contains(l)) {
                    string = string.replace(l, player.getName());
                }
                Functions.npcSayToPlayer((NpcInstance)this.getActor(), (Player)player, (String)string);
            }
            this.M = System.currentTimeMillis() + 20000L;
        }
        if (this.N < System.currentTimeMillis()) {
            if (this.b() != null) {
                this.getActor().broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)this.getActor(), (Creature)this.b(), 6191, 1, 5000, 10L)});
            }
            this.N = System.currentTimeMillis() + 6500L;
        }
        return true;
    }

    private NpcInstance b() {
        List list = this.getActor().getAroundNpc(1000, 300);
        if (list != null && !list.isEmpty()) {
            for (NpcInstance npcInstance : list) {
                if (npcInstance.getNpcId() != 32715) continue;
                return npcInstance;
            }
        }
        return null;
    }

    private Player getPlayer() {
        Reflection reflection = this.getActor().getReflection();
        if (reflection == null) {
            return null;
        }
        List list = reflection.getPlayers();
        if (list.isEmpty()) {
            return null;
        }
        return (Player)list.get(0);
    }

    protected boolean randomWalk() {
        return false;
    }

    protected void onEvtAttacked(Creature creature, int n) {
    }

    protected void onEvtAggression(Creature creature, int n) {
    }
}
