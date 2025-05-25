/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.napile.primitive.maps.IntObjectMap
 *  org.napile.primitive.maps.impl.TreeIntObjectMap
 */
package l2.gameserver.data.xml.holder;

import l2.commons.data.xml.AbstractHolder;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.events.EventType;
import l2.gameserver.model.entity.events.GlobalEvent;
import org.napile.primitive.maps.IntObjectMap;
import org.napile.primitive.maps.impl.TreeIntObjectMap;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class EventHolder
extends AbstractHolder {
    private static final EventHolder a = new EventHolder();
    private final IntObjectMap<GlobalEvent> a = new TreeIntObjectMap();

    public static EventHolder getInstance() {
        return a;
    }

    public void addEvent(EventType eventType, GlobalEvent globalEvent) {
        this.a.put(eventType.step() + globalEvent.getId(), globalEvent);
    }

    public <E extends GlobalEvent> E getEvent(EventType eventType, int n) {
        return (E)((GlobalEvent)this.a.get(eventType.step() + n));
    }

    public void findEvent(Player player) {
        for (GlobalEvent globalEvent : this.a.values()) {
            if (!globalEvent.isParticle(player)) continue;
            player.addEvent(globalEvent);
        }
    }

    public void callInit() {
        for (GlobalEvent globalEvent : this.a.values()) {
            globalEvent.initEvent();
        }
    }

    @Override
    public int size() {
        return this.a.size();
    }

    @Override
    public void clear() {
        this.a.clear();
    }
}
