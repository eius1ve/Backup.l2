/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.events.objects;

import java.util.ArrayList;
import java.util.List;
import l2.gameserver.dao.SiegePlayerDAO;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.events.impl.SiegeEvent;
import l2.gameserver.model.entity.events.objects.SiegeClanObject;
import l2.gameserver.model.entity.residence.Residence;
import l2.gameserver.model.pledge.Clan;

public class CTBSiegeClanObject
extends SiegeClanObject {
    private List<Integer> q = new ArrayList<Integer>();
    private long ce;

    public CTBSiegeClanObject(String string, Clan clan, long l, long l2) {
        super(string, clan, l, l2);
        this.ce = l;
    }

    public CTBSiegeClanObject(String string, Clan clan, long l) {
        this(string, clan, l, System.currentTimeMillis());
    }

    public void select(Residence residence) {
        this.q.addAll(SiegePlayerDAO.getInstance().select(residence, this.getObjectId()));
    }

    public List<Integer> getPlayers() {
        return this.q;
    }

    @Override
    public void setEvent(boolean bl, SiegeEvent siegeEvent) {
        for (int n : this.getPlayers()) {
            Player player = GameObjectsStorage.getPlayer(n);
            if (player == null) continue;
            if (bl) {
                player.addEvent(siegeEvent);
            } else {
                player.removeEvent(siegeEvent);
            }
            player.broadcastCharInfo();
        }
    }

    @Override
    public boolean isParticle(Player player) {
        return this.q.contains(player.getObjectId());
    }

    @Override
    public long getParam() {
        return this.ce;
    }

    public void setParam(int n) {
        this.ce = n;
    }
}
