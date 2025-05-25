/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.events.objects;

import l2.gameserver.data.xml.holder.NpcHolder;
import l2.gameserver.idfactory.IdFactory;
import l2.gameserver.model.entity.events.GlobalEvent;
import l2.gameserver.model.entity.events.objects.CTBSiegeClanObject;
import l2.gameserver.model.entity.events.objects.SpawnableObject;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.instances.residences.clanhall.CTBBossInstance;
import l2.gameserver.templates.npc.NpcTemplate;
import l2.gameserver.utils.Location;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class CTBTeamObject
implements SpawnableObject {
    private CTBSiegeClanObject a;
    private final NpcTemplate e;
    private final NpcTemplate f;
    private final Location J;
    private NpcInstance v;
    private CTBBossInstance a;

    public CTBTeamObject(int n, int n2, Location location) {
        this.e = NpcHolder.getInstance().getTemplate(n);
        this.f = NpcHolder.getInstance().getTemplate(n2);
        this.J = location;
    }

    @Override
    public void spawnObject(GlobalEvent globalEvent) {
        if (this.v == null) {
            this.v = new NpcInstance(IdFactory.getInstance().getNextId(), this.f);
            this.v.setCurrentHpMp(this.v.getMaxHp(), this.v.getMaxMp());
            this.v.setHasChatWindow(false);
            this.v.spawnMe(this.J);
        } else if (this.a == null) {
            NpcTemplate npcTemplate = this.a == null || this.a.getParam() == 0L ? this.e : NpcHolder.getInstance().getTemplate((int)this.a.getParam());
            this.a = (CTBBossInstance)npcTemplate.getNewInstance();
            this.a.setCurrentHpMp(this.a.getMaxHp(), this.a.getMaxMp());
            this.a.setMatchTeamObject(this);
            this.a.addEvent(globalEvent);
            int n = (int)((double)this.J.x + 300.0 * Math.cos(this.a.headingToRadians(this.v.getHeading() - 32768)));
            int n2 = (int)((double)this.J.y + 300.0 * Math.sin(this.a.headingToRadians(this.v.getHeading() - 32768)));
            Location location = new Location(n, n2, this.v.getZ(), this.v.getHeading());
            this.a.setSpawnedLoc(location);
            this.a.spawnMe(location);
        } else {
            throw new IllegalArgumentException("Cant spawn twice");
        }
    }

    @Override
    public void despawnObject(GlobalEvent globalEvent) {
        if (this.a != null) {
            this.a.deleteMe();
            this.a = null;
        }
        if (this.v != null) {
            this.v.deleteMe();
            this.v = null;
        }
        this.a = null;
    }

    @Override
    public void refreshObject(GlobalEvent globalEvent) {
    }

    public CTBSiegeClanObject getSiegeClan() {
        return this.a;
    }

    public void setSiegeClan(CTBSiegeClanObject cTBSiegeClanObject) {
        this.a = cTBSiegeClanObject;
    }

    public boolean isParticle() {
        return this.v != null && this.a != null;
    }

    public NpcInstance getFlag() {
        return this.v;
    }
}
