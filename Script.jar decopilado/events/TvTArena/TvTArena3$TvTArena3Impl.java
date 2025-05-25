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

private static class TvTArena3.TvTArena3Impl
extends TvTTemplate {
    private TvTArena3.TvTArena3Impl() {
    }

    @Override
    protected void onLoad() {
        this._managerId = 31392;
        this._className = "TvTArena3";
        this._status = 0;
        this._team1list = new CopyOnWriteArrayList();
        this._team2list = new CopyOnWriteArrayList();
        this._team1live = new CopyOnWriteArrayList();
        this._team2live = new CopyOnWriteArrayList();
        this._zoneListener = new TvTTemplate.ZoneListener(this);
        this._zone = ReflectionUtils.getZone((String)"[tvt_arena3]");
        this._zone.addListener((Listener)this._zoneListener);
        this._team1points = new ArrayList();
        this._team2points = new ArrayList();
        this._team1points.add(new Location(-79383, -52724, -11518, -11418));
        this._team1points.add(new Location(-79558, -52793, -11518, -11418));
        this._team1points.add(new Location(-79726, -52867, -11518, -11418));
        this._team1points.add(new Location(-79911, -52845, -11518, -11418));
        this._team1points.add(new Location(-80098, -52822, -11518, -11418));
        this._team1points.add(new Location(-80242, -52714, -11518, -11418));
        this._team1points.add(new Location(-80396, -52597, -11518, -11418));
        this._team1points.add(new Location(-80466, -52422, -11518, -11418));
        this._team1points.add(new Location(-80544, -52250, -11518, -11418));
        this._team1points.add(new Location(-80515, -52054, -11518, -11418));
        this._team1points.add(new Location(-80496, -51878, -11518, -11418));
        this._team1points.add(new Location(-80386, -51739, -11518, -11418));
        this._team2points.add(new Location(-80270, -51582, -11518, -11418));
        this._team2points.add(new Location(-80107, -51519, -11518, -11418));
        this._team2points.add(new Location(-79926, -51435, -11518, -11418));
        this._team2points.add(new Location(-79739, -51465, -11518, -11418));
        this._team2points.add(new Location(-79554, -51482, -11518, -11418));
        this._team2points.add(new Location(-79399, -51600, -11518, -11418));
        this._team2points.add(new Location(-79254, -51711, -11518, -11418));
        this._team2points.add(new Location(-79181, -51884, -11518, -11418));
        this._team2points.add(new Location(-79114, -52057, -11518, -11418));
        this._team2points.add(new Location(-79133, -52246, -11518, -11418));
        this._team2points.add(new Location(-79156, -52427, -11518, -11418));
        this._team2points.add(new Location(-79275, -52583, -11518, -11418));
    }

    @Override
    protected void onReload() {
        if (this._status > 0) {
            this.template_stop();
        }
        this._zone.removeListener((Listener)this._zoneListener);
    }
}
