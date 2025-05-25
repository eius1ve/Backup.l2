/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.geometry.Point3D
 *  l2.commons.util.Rnd
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.ai.DefaultAI
 *  l2.gameserver.ai.DefaultAI$Teleport
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.network.l2.s2c.MagicSkillUse
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.templates.StatsSet
 *  l2.gameserver.utils.Location
 */
package ai;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import l2.commons.geometry.Point3D;
import l2.commons.util.Rnd;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.ai.DefaultAI;
import l2.gameserver.model.Creature;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.MagicSkillUse;
import l2.gameserver.scripts.Functions;
import l2.gameserver.templates.StatsSet;
import l2.gameserver.utils.Location;

public class BlacksmithMammon
extends DefaultAI {
    private final int v;
    private final List<Location> k = new ArrayList<Location>();
    private long g = System.currentTimeMillis();
    private static final String h = "12655,-248700,-9576;-20515,-251010,-8160;-53145,-250500,-7904;46288,170096,-4979;-19378,13264,-4899;140480,79472,-5427";
    private static final String[] b = new String[]{"1000431", "1000432", "1000433"};

    public BlacksmithMammon(NpcInstance npcInstance) {
        super(npcInstance);
        StatsSet statsSet = npcInstance.getTemplate().getAIParams();
        StringTokenizer stringTokenizer = new StringTokenizer(statsSet.getString((Object)"tele_loc", h), ";");
        while (stringTokenizer.hasMoreTokens()) {
            String string = stringTokenizer.nextToken();
            this.k.add(Location.parseLoc((String)string));
        }
        this.v = statsSet.getInteger((Object)"teleport_period", 1800000);
    }

    protected Location getRndTeleportLoc() {
        return (Location)Rnd.get(this.k);
    }

    protected boolean thinkActive() {
        NpcInstance npcInstance = this.getActor();
        if (npcInstance.isDead()) {
            return true;
        }
        if (System.currentTimeMillis() - this.g >= (long)this.v) {
            Location location = this.getRndTeleportLoc();
            if (!npcInstance.getLoc().equals((Point3D)location)) {
                ThreadPoolManager.getInstance().schedule((Runnable)new DefaultAI.Teleport((DefaultAI)this, location, true), 1000L);
                npcInstance.broadcastPacketToOthers(new L2GameServerPacket[]{new MagicSkillUse((Creature)npcInstance, (Creature)npcInstance, 4671, 1, 1000, 0L)});
                this.g = System.currentTimeMillis();
                ThreadPoolManager.getInstance().schedule(() -> Functions.npcShoutCustomMessage((NpcInstance)npcInstance, (String)b[Rnd.get((int)b.length)], (Object[])new Object[0]), 5000L);
            }
        }
        return false;
    }

    public boolean isGlobalAI() {
        return true;
    }
}
