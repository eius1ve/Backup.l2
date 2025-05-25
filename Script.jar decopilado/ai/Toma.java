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
import l2.gameserver.templates.StatsSet;
import l2.gameserver.utils.Location;

public class Toma
extends DefaultAI {
    private final int ap;
    private final List<Location> n = new ArrayList<Location>();
    private long g = System.currentTimeMillis();
    private static final String k = "151680,-174891,-1807;154153,-220105,-3402;178834,-184336,-352";

    public Toma(NpcInstance npcInstance) {
        super(npcInstance);
        StatsSet statsSet = npcInstance.getTemplate().getAIParams();
        StringTokenizer stringTokenizer = new StringTokenizer(statsSet.getString((Object)"tele_loc", k), ";");
        while (stringTokenizer.hasMoreTokens()) {
            String string = stringTokenizer.nextToken();
            this.n.add(Location.parseLoc((String)string));
        }
        this.ap = statsSet.getInteger((Object)"teleport_period", 1800000);
    }

    protected Location getRndTeleportLoc() {
        return (Location)Rnd.get(this.n);
    }

    protected boolean thinkActive() {
        NpcInstance npcInstance = this.getActor();
        if (npcInstance.isDead()) {
            return true;
        }
        if (System.currentTimeMillis() - this.g < (long)this.ap) {
            return false;
        }
        Location location = this.getRndTeleportLoc();
        if (npcInstance.getLoc().equals((Point3D)location)) {
            return false;
        }
        npcInstance.broadcastPacketToOthers(new L2GameServerPacket[]{new MagicSkillUse((Creature)npcInstance, (Creature)npcInstance, 4671, 1, 1000, 0L)});
        ThreadPoolManager.getInstance().schedule((Runnable)new DefaultAI.Teleport((DefaultAI)this, location, true), 1000L);
        this.g = System.currentTimeMillis();
        return true;
    }

    public boolean isGlobalAI() {
        return true;
    }
}
