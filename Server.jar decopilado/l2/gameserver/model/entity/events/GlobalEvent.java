/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.napile.primitive.Containers
 *  org.napile.primitive.maps.IntObjectMap
 *  org.napile.primitive.maps.IntObjectMap$Entry
 *  org.napile.primitive.maps.impl.CHashIntObjectMap
 *  org.napile.primitive.maps.impl.TreeIntObjectMap
 */
package l2.gameserver.model.entity.events;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import l2.commons.collections.MultiValueSet;
import l2.commons.listener.Listener;
import l2.commons.listener.ListenerList;
import l2.commons.logging.LoggerObject;
import l2.gameserver.dao.ItemsDAO;
import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.listener.event.OnStartStopListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.base.RestartType;
import l2.gameserver.model.entity.Reflection;
import l2.gameserver.model.entity.events.EventAction;
import l2.gameserver.model.entity.events.EventWrapper;
import l2.gameserver.model.entity.events.objects.DoorObject;
import l2.gameserver.model.entity.events.objects.InitableObject;
import l2.gameserver.model.entity.events.objects.SpawnableObject;
import l2.gameserver.model.entity.events.objects.ZoneObject;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.scripts.Functions;
import l2.gameserver.taskmanager.actionrunner.ActionRunner;
import l2.gameserver.utils.Location;
import l2.gameserver.utils.TimeUtils;
import org.napile.primitive.Containers;
import org.napile.primitive.maps.IntObjectMap;
import org.napile.primitive.maps.impl.CHashIntObjectMap;
import org.napile.primitive.maps.impl.TreeIntObjectMap;

public abstract class GlobalEvent
extends LoggerObject {
    public static final String EVENT = "event";
    protected final IntObjectMap<List<EventAction>> _onTimeActions = new TreeIntObjectMap();
    protected final List<EventAction> _onStartActions = new ArrayList<EventAction>(0);
    protected final List<EventAction> _onStopActions = new ArrayList<EventAction>(0);
    protected final List<EventAction> _onInitActions = new ArrayList<EventAction>(0);
    protected final Map<String, List<Serializable>> _objects = new HashMap<String, List<Serializable>>(0);
    protected final int _id;
    protected final String _name;
    protected final String _timerName;
    protected final ListenerListImpl _listenerList = new ListenerListImpl();
    protected IntObjectMap<ItemInstance> _banishedItems = Containers.emptyIntObjectMap();

    protected GlobalEvent(MultiValueSet<String> multiValueSet) {
        this(multiValueSet.getInteger("id"), multiValueSet.getString("name"));
    }

    protected GlobalEvent(int n, String string) {
        this._id = n;
        this._name = string;
        this._timerName = n + "_" + string.toLowerCase().replace(" ", "_");
    }

    public void initEvent() {
        this.callActions(this._onInitActions);
        this.reCalcNextTime(true);
        this.printInfo();
    }

    public void startEvent() {
        this.callActions(this._onStartActions);
        this._listenerList.onStart();
    }

    public void stopEvent() {
        this.callActions(this._onStopActions);
        this._listenerList.onStop();
    }

    protected void printInfo() {
        this.info(this.getName() + " time - " + TimeUtils.toSimpleFormat(this.startTimeMillis()));
    }

    public String toString() {
        return this.getClass().getSimpleName() + "[" + this.getId() + ";" + this.getName() + "]";
    }

    protected void callActions(List<EventAction> list) {
        for (EventAction eventAction : list) {
            eventAction.call(this);
        }
    }

    public void addOnStartActions(List<EventAction> list) {
        this._onStartActions.addAll(list);
    }

    public void addOnStopActions(List<EventAction> list) {
        this._onStopActions.addAll(list);
    }

    public void addOnInitActions(List<EventAction> list) {
        this._onInitActions.addAll(list);
    }

    public void addOnTimeAction(int n, EventAction eventAction) {
        List list = (List)this._onTimeActions.get(n);
        if (list != null) {
            list.add(eventAction);
        } else {
            ArrayList<EventAction> arrayList = new ArrayList<EventAction>(1);
            arrayList.add(eventAction);
            this._onTimeActions.put(n, arrayList);
        }
    }

    public void addOnTimeActions(int n, List<EventAction> list) {
        if (list.isEmpty()) {
            return;
        }
        List list2 = (List)this._onTimeActions.get(n);
        if (list2 != null) {
            list2.addAll(list);
        } else {
            this._onTimeActions.put(n, new ArrayList<EventAction>(list));
        }
    }

    public void timeActions(int n) {
        List list = (List)this._onTimeActions.get(n);
        if (list == null) {
            this.info("Undefined time : " + n + " for " + this.toString());
            return;
        }
        this.callActions(list);
    }

    public int[] timeActions() {
        return this._onTimeActions.keySet().toArray();
    }

    public void registerActions() {
        long l = this.startTimeMillis();
        if (l == 0L) {
            return;
        }
        for (int n : this._onTimeActions.keySet().toArray()) {
            ActionRunner.getInstance().register(l + (long)n * 1000L, new EventWrapper(this._timerName, this, n));
        }
    }

    public void clearActions() {
        ActionRunner.getInstance().clear(this._timerName);
    }

    public <O extends Serializable> List<O> getObjects(String string) {
        List<Serializable> list = this._objects.get(string);
        return list == null ? Collections.emptyList() : list;
    }

    public <O extends Serializable> O getFirstObject(String string) {
        List<O> list = this.getObjects(string);
        return (O)(list.size() > 0 ? (Serializable)list.get(0) : null);
    }

    public void addObject(String string, Serializable serializable) {
        if (serializable == null) {
            return;
        }
        List<Serializable> list = this._objects.get(string);
        if (list != null) {
            list.add(serializable);
        } else {
            list = new CopyOnWriteArrayList<Serializable>();
            list.add(serializable);
            this._objects.put(string, list);
        }
    }

    public void removeObject(String string, Serializable serializable) {
        if (serializable == null) {
            return;
        }
        List<Serializable> list = this._objects.get(string);
        if (list != null) {
            list.remove(serializable);
        }
    }

    public <O extends Serializable> List<O> removeObjects(String string) {
        List<Serializable> list = this._objects.remove(string);
        return list == null ? Collections.emptyList() : list;
    }

    public void addObjects(String string, List<? extends Serializable> list) {
        if (list.isEmpty()) {
            return;
        }
        List<Serializable> list2 = this._objects.get(string);
        if (list2 != null) {
            list2.addAll(list);
        } else {
            this._objects.put(string, list);
        }
    }

    public Map<String, List<Serializable>> getObjects() {
        return this._objects;
    }

    public void spawnAction(String string, boolean bl) {
        List list = this.getObjects(string);
        if (list.isEmpty()) {
            this.info("Undefined objects: " + string);
            return;
        }
        for (Serializable serializable : list) {
            if (!(serializable instanceof SpawnableObject)) continue;
            if (bl) {
                ((SpawnableObject)serializable).spawnObject(this);
                continue;
            }
            ((SpawnableObject)serializable).despawnObject(this);
        }
    }

    public void doorAction(String string, boolean bl) {
        List list = this.getObjects(string);
        if (list.isEmpty()) {
            this.info("Undefined objects: " + string);
            return;
        }
        for (Serializable serializable : list) {
            if (!(serializable instanceof DoorObject)) continue;
            if (bl) {
                ((DoorObject)serializable).open(this);
                continue;
            }
            ((DoorObject)serializable).close(this);
        }
    }

    public void zoneAction(String string, boolean bl) {
        List list = this.getObjects(string);
        if (list.isEmpty()) {
            this.info("Undefined objects: " + string);
            return;
        }
        for (Serializable serializable : list) {
            if (serializable instanceof ZoneObject) {
                ((ZoneObject)serializable).setActive(bl, this);
            }
            if (!(serializable instanceof String)) continue;
            this.zoneAction((String)((Object)serializable), bl);
        }
    }

    public void initAction(String string) {
        List list = this.getObjects(string);
        if (list.isEmpty()) {
            this.info("Undefined objects: " + string);
            return;
        }
        for (Serializable serializable : list) {
            if (!(serializable instanceof InitableObject)) continue;
            ((InitableObject)serializable).initObject(this);
        }
    }

    public void action(String string, boolean bl) {
        if (string.equalsIgnoreCase(EVENT)) {
            if (bl) {
                this.startEvent();
            } else {
                this.stopEvent();
            }
        }
    }

    public void refreshAction(String string) {
        List list = this.getObjects(string);
        if (list.isEmpty()) {
            this.info("Undefined objects: " + string);
            return;
        }
        for (Serializable serializable : list) {
            if (!(serializable instanceof SpawnableObject)) continue;
            ((SpawnableObject)serializable).refreshObject(this);
        }
    }

    public abstract void reCalcNextTime(boolean var1);

    protected abstract long startTimeMillis();

    public void broadcastToWorld(IStaticPacket iStaticPacket) {
        for (Player player : GameObjectsStorage.getAllPlayersForIterate()) {
            if (player == null) continue;
            player.sendPacket(iStaticPacket);
        }
    }

    public void broadcastToWorld(L2GameServerPacket l2GameServerPacket) {
        for (Player player : GameObjectsStorage.getAllPlayersForIterate()) {
            if (player == null) continue;
            player.sendPacket((IStaticPacket)l2GameServerPacket);
        }
    }

    public int getId() {
        return this._id;
    }

    public String getName() {
        return this._name;
    }

    public GameObject getCenterObject() {
        return null;
    }

    public Reflection getReflection() {
        return ReflectionManager.DEFAULT;
    }

    public int getRelation(Player player, Player player2, int n) {
        return n;
    }

    public int getUserRelation(Player player, int n) {
        return n;
    }

    public void checkRestartLocs(Player player, Map<RestartType, Boolean> map) {
    }

    public Location getRestartLoc(Player player, RestartType restartType) {
        return null;
    }

    public boolean canAttack(Creature creature, Creature creature2, Skill skill, boolean bl) {
        return false;
    }

    public SystemMsg checkForAttack(Creature creature, Creature creature2, Skill skill, boolean bl) {
        return null;
    }

    public boolean isInProgress() {
        return false;
    }

    public boolean isParticle(Player player) {
        return false;
    }

    public void announce(int n) {
        throw new UnsupportedOperationException();
    }

    public void teleportPlayers(String string) {
        throw new UnsupportedOperationException();
    }

    public boolean ifVar(String string) {
        throw new UnsupportedOperationException();
    }

    public List<Player> itemObtainPlayers() {
        throw new UnsupportedOperationException();
    }

    public void giveItem(Player player, int n, long l) {
        Functions.addItem(player, n, l);
    }

    public List<Player> broadcastPlayers(int n) {
        throw new UnsupportedOperationException();
    }

    public boolean canResurrect(Player player, Creature creature, boolean bl) {
        return true;
    }

    public void onAddEvent(GameObject gameObject) {
    }

    public void onRemoveEvent(GameObject gameObject) {
    }

    public void addBanishItem(ItemInstance itemInstance) {
        if (this._banishedItems.isEmpty()) {
            this._banishedItems = new CHashIntObjectMap();
        }
        this._banishedItems.put(itemInstance.getObjectId(), (Object)itemInstance);
    }

    public void removeBanishItems() {
        Iterator iterator = this._banishedItems.entrySet().iterator();
        while (iterator.hasNext()) {
            IntObjectMap.Entry entry = (IntObjectMap.Entry)iterator.next();
            iterator.remove();
            ItemInstance itemInstance = ItemsDAO.getInstance().load(entry.getKey());
            if (itemInstance != null) {
                GameObject gameObject;
                if (itemInstance.getOwnerId() > 0 && (gameObject = GameObjectsStorage.findObject(itemInstance.getOwnerId())) != null && gameObject.isPlayable()) {
                    ((Playable)gameObject).getInventory().destroyItem(itemInstance);
                    gameObject.getPlayer().sendPacket((IStaticPacket)SystemMessage.removeItems(itemInstance));
                }
                itemInstance.delete();
            } else {
                itemInstance = (ItemInstance)entry.getValue();
            }
            itemInstance.deleteMe();
        }
    }

    public void addListener(Listener<GlobalEvent> listener) {
        this._listenerList.add(listener);
    }

    public void removeListener(Listener<GlobalEvent> listener) {
        this._listenerList.remove(listener);
    }

    public void cloneTo(GlobalEvent globalEvent) {
        for (EventAction eventAction : this._onInitActions) {
            globalEvent._onInitActions.add(eventAction);
        }
        for (EventAction eventAction : this._onStartActions) {
            globalEvent._onStartActions.add(eventAction);
        }
        for (EventAction eventAction : this._onStopActions) {
            globalEvent._onStopActions.add(eventAction);
        }
        for (EventAction eventAction : this._onTimeActions.entrySet()) {
            globalEvent.addOnTimeActions(eventAction.getKey(), (List)eventAction.getValue());
        }
    }

    private class ListenerListImpl
    extends ListenerList<GlobalEvent> {
        private ListenerListImpl() {
        }

        public void onStart() {
            this.forEachListener(OnStartStopListener.class, onStartStopListener -> onStartStopListener.onStart(GlobalEvent.this));
        }

        public void onStop() {
            this.forEachListener(OnStartStopListener.class, onStartStopListener -> onStartStopListener.onStop(GlobalEvent.this));
        }
    }
}
