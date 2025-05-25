/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.listener.Listener
 *  l2.gameserver.utils.Location
 *  l2.gameserver.utils.ReflectionUtils
 */
package events.TvTArena;

import events.TvTArena.TvTTemplate;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import l2.commons.listener.Listener;
import l2.gameserver.utils.Location;
import l2.gameserver.utils.ReflectionUtils;

private static class TvTArena2.TvTArena2Impl
extends TvTTemplate {
    private TvTArena2.TvTArena2Impl() {
    }

    @Override
    protected void onLoad() {
        this._managerId = 31391;
        this._className = "TvTArena2";
        this._status = 0;
        this._team1list = new CopyOnWriteArrayList();
        this._team2list = new CopyOnWriteArrayList();
        this._team1live = new CopyOnWriteArrayList();
        this._team2live = new CopyOnWriteArrayList();
        this._zoneListener = new TvTTemplate.ZoneListener(this);
        this._zone = ReflectionUtils.getZone((String)"[tvt_arena2]");
        this._zone.addListener((Listener)this._zoneListener);
        this._team1points = new ArrayList();
        this._team2points = new ArrayList();
        this._team1points.add(new Location(-77724, -47901, -11518, -11418));
        this._team1points.add(new Location(-77718, -48080, -11518, -11418));
        this._team1points.add(new Location(-77699, -48280, -11518, -11418));
        this._team1points.add(new Location(-77777, -48442, -11518, -11418));
        this._team1points.add(new Location(-77863, -48622, -11518, -11418));
        this._team1points.add(new Location(-78002, -48714, -11518, -11418));
        this._team1points.add(new Location(-78168, -48835, -11518, -11418));
        this._team1points.add(new Location(-78353, -48851, -11518, -11418));
        this._team1points.add(new Location(-78543, -48864, -11518, -11418));
        this._team1points.add(new Location(-78709, -48784, -11518, -11418));
        this._team1points.add(new Location(-78881, -48702, -11518, -11418));
        this._team1points.add(new Location(-78981, -48555, -11518, -11418));
        this._team2points.add(new Location(-79097, -48400, -11518, -11418));
        this._team2points.add(new Location(-79107, -48214, -11518, -11418));
        this._team2points.add(new Location(-79125, -48027, -11518, -11418));
        this._team2points.add(new Location(-79047, -47861, -11518, -11418));
        this._team2points.add(new Location(-78965, -47689, -11518, -11418));
        this._team2points.add(new Location(-78824, -47594, -11518, -11418));
        this._team2points.add(new Location(-78660, -47474, -11518, -11418));
        this._team2points.add(new Location(-78483, -47456, -11518, -11418));
        this._team2points.add(new Location(-78288, -47440, -11518, -11418));
        this._team2points.add(new Location(-78125, -47515, -11518, -11418));
        this._team2points.add(new Location(-77953, -47599, -11518, -11418));
        this._team2points.add(new Location(-77844, -47747, -11518, -11418));
    }

    @Override
    protected void onReload() {
        if (this._status > 0) {
            this.template_stop();
        }
        this._zone.removeListener((Listener)this._zoneListener);
    }
}
