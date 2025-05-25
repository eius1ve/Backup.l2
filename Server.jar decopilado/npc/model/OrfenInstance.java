/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.ai.CtrlIntention
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.instances.BossInstance
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.network.l2.s2c.PlaySound
 *  l2.gameserver.network.l2.s2c.PlaySound$Type
 *  l2.gameserver.templates.npc.NpcTemplate
 *  l2.gameserver.utils.Location
 */
package npc.model;

import l2.commons.util.Rnd;
import l2.gameserver.ai.CtrlIntention;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Skill;
import l2.gameserver.model.instances.BossInstance;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.PlaySound;
import l2.gameserver.templates.npc.NpcTemplate;
import l2.gameserver.utils.Location;

public class OrfenInstance
extends BossInstance {
    public static final Location nest = new Location(43728, 17220, -4342);
    public static final Location[] locs = new Location[]{new Location(55024, 17368, -5412), new Location(53504, 21248, -5496), new Location(53248, 24576, -5272)};

    public OrfenInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    public void setTeleported(boolean bl) {
        super.setTeleported(bl);
        Location location = bl ? nest : locs[Rnd.get((int)locs.length)];
        this.setSpawnedLoc(location);
        this.getAggroList().clear(true);
        this.getAI().setIntention(CtrlIntention.AI_INTENTION_ACTIVE, null, null);
        this.teleToLocation(location);
    }

    protected void onSpawn() {
        super.onSpawn();
        this.setTeleported(false);
        this.broadcastPacketToOthers(new L2GameServerPacket[]{new PlaySound(PlaySound.Type.MUSIC, "BS01_A", 1, 0, this.getLoc())});
    }

    protected void onDeath(Creature creature) {
        this.broadcastPacketToOthers(new L2GameServerPacket[]{new PlaySound(PlaySound.Type.MUSIC, "BS02_D", 1, 0, this.getLoc())});
        super.onDeath(creature);
    }

    public void reduceCurrentHp(double d, Creature creature, Skill skill, boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5, boolean bl6, boolean bl7) {
        super.reduceCurrentHp(d, creature, skill, bl, bl2, bl3, bl4, bl5, bl6, bl7);
        if (!this.isTeleported() && this.getCurrentHpPercents() <= 50.0) {
            this.setTeleported(true);
        }
    }
}
