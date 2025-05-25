/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.instances;

import java.util.Collections;
import java.util.List;
import l2.commons.lang.reference.HardReference;
import l2.gameserver.ai.CtrlIntention;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.model.World;
import l2.gameserver.model.reference.L2Reference;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.MyTargetSelected;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.network.l2.s2c.ShowTownMap;
import l2.gameserver.network.l2.s2c.StaticObject;
import l2.gameserver.scripts.Events;
import l2.gameserver.templates.StaticObjectTemplate;
import l2.gameserver.utils.Location;

public class StaticObjectInstance
extends GameObject {
    private final HardReference<StaticObjectInstance> X;
    private final StaticObjectTemplate a;
    private int nt;

    public StaticObjectInstance(int n, StaticObjectTemplate staticObjectTemplate) {
        super(n);
        this.a = staticObjectTemplate;
        this.X = new L2Reference<StaticObjectInstance>(this);
    }

    public HardReference<StaticObjectInstance> getRef() {
        return this.X;
    }

    public int getUId() {
        return this.a.getUId();
    }

    public int getType() {
        return this.a.getType();
    }

    @Override
    public void onAction(Player player, boolean bl) {
        if (Events.onAction(player, this, bl)) {
            return;
        }
        if (player.getTarget() != this) {
            player.setTarget(this);
            player.sendPacket((IStaticPacket)new MyTargetSelected(this.getObjectId(), 0));
            return;
        }
        MyTargetSelected myTargetSelected = new MyTargetSelected(this.getObjectId(), 0);
        player.sendPacket((IStaticPacket)myTargetSelected);
        if (!this.isInRange(player, (long)this.getActingRange())) {
            if (!player.getAI().isIntendingInteract(this)) {
                player.getAI().setIntention(CtrlIntention.AI_INTENTION_INTERACT, this);
            }
            return;
        }
        if (this.a.getType() == 0) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(player, this.getUId(), "newspaper/arena.htm", 0));
        } else if (this.a.getType() == 2) {
            player.sendPacket((IStaticPacket)new ShowTownMap(this.a.getFilePath(), this.a.getMapX(), this.a.getMapY()));
            player.sendActionFailed();
        }
    }

    @Override
    public int getActingRange() {
        switch (this.a.getType()) {
            case 1: {
                return 150;
            }
        }
        return 300;
    }

    @Override
    public List<L2GameServerPacket> addPacketList(Player player, Creature creature) {
        return Collections.singletonList(new StaticObject(this));
    }

    @Override
    public boolean isAttackable(Creature creature) {
        return false;
    }

    public void broadcastInfo(boolean bl) {
        StaticObject staticObject = new StaticObject(this);
        for (Player player : World.getAroundPlayers(this)) {
            player.sendPacket((IStaticPacket)staticObject);
        }
    }

    @Override
    public int getGeoZ(Location location) {
        return location.z;
    }

    public int getMeshIndex() {
        return this.nt;
    }

    public void setMeshIndex(int n) {
        this.nt = n;
    }
}
