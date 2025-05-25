/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.TIntObjectHashMap
 *  gnu.trove.TIntObjectIterator
 */
package l2.gameserver.data;

import gnu.trove.TIntObjectHashMap;
import gnu.trove.TIntObjectIterator;
import java.lang.reflect.Constructor;
import l2.commons.data.xml.AbstractHolder;
import l2.gameserver.idfactory.IdFactory;
import l2.gameserver.model.entity.boat.Boat;
import l2.gameserver.templates.CharTemplate;

public final class BoatHolder
extends AbstractHolder {
    public static final CharTemplate TEMPLATE = new CharTemplate(CharTemplate.getEmptyStatsSet());
    private static BoatHolder _instance = new BoatHolder();
    private final TIntObjectHashMap<Boat> _boats = new TIntObjectHashMap();

    public static BoatHolder getInstance() {
        return _instance;
    }

    public void spawnAll() {
        this.log();
        TIntObjectIterator tIntObjectIterator = this._boats.iterator();
        while (tIntObjectIterator.hasNext()) {
            tIntObjectIterator.advance();
            ((Boat)tIntObjectIterator.value()).spawnMe();
            this.info("Spawning: " + ((Boat)tIntObjectIterator.value()).getName());
        }
    }

    public Boat initBoat(String string, String string2) {
        try {
            Class<?> clazz = Class.forName("l2.gameserver.model.entity.boat." + string2);
            Constructor<?> constructor = clazz.getConstructor(Integer.TYPE, CharTemplate.class);
            Boat boat = (Boat)constructor.newInstance(IdFactory.getInstance().getNextId(), TEMPLATE);
            boat.setName(string);
            this.addBoat(boat);
            return boat;
        }
        catch (Exception exception) {
            this.error("Fail to init boat: " + string2, exception);
            return null;
        }
    }

    public Boat getBoat(String string) {
        TIntObjectIterator tIntObjectIterator = this._boats.iterator();
        while (tIntObjectIterator.hasNext()) {
            tIntObjectIterator.advance();
            if (!((Boat)tIntObjectIterator.value()).getName().equals(string)) continue;
            return (Boat)tIntObjectIterator.value();
        }
        return null;
    }

    public Boat getBoat(int n) {
        return (Boat)this._boats.get(n);
    }

    public void addBoat(Boat boat) {
        this._boats.put(boat.getObjectId(), (Object)boat);
    }

    public void removeBoat(Boat boat) {
        this._boats.remove(boat.getObjectId());
    }

    @Override
    public int size() {
        return this._boats.size();
    }

    @Override
    public void clear() {
        this._boats.clear();
    }
}
