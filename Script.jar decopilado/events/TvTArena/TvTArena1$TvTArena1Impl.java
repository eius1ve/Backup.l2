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

private static class TvTArena1.TvTArena1Impl
extends TvTTemplate {
    private TvTArena1.TvTArena1Impl() {
    }

    @Override
    protected void onLoad() {
        this._managerId = 31390;
        this._className = "TvTArena1";
        this._status = 0;
        this._team1list = new CopyOnWriteArrayList();
        this._team2list = new CopyOnWriteArrayList();
        this._team1live = new CopyOnWriteArrayList();
        this._team2live = new CopyOnWriteArrayList();
        this._zoneListener = new TvTTemplate.ZoneListener(this);
        this._zone = ReflectionUtils.getZone((String)"[tvt_arena1]");
        this._zone.addListener((Listener)this._zoneListener);
        this._team1points = new ArrayList();
        this._team2points = new ArrayList();
        this._team1points.add(new Location(-81806, -44865, -11418));
        this._team1points.add(new Location(-81617, -44893, -11418));
        this._team1points.add(new Location(-81440, -44945, -11418));
        this._team1points.add(new Location(-81301, -48066, -11418));
        this._team1points.add(new Location(-81168, -45208, -11418));
        this._team1points.add(new Location(-81114, -46379, -11418));
        this._team1points.add(new Location(-81068, -45570, -11418));
        this._team1points.add(new Location(-81114, -45728, -11418));
        this._team1points.add(new Location(-81162, -45934, -11418));
        this._team1points.add(new Location(-81280, -46045, -11418));
        this._team1points.add(new Location(-81424, -46196, -11418));
        this._team1points.add(new Location(-81578, -46238, -11418));
        this._team2points.add(new Location(-81792, -46299, -11418));
        this._team2points.add(new Location(-81959, -46247, -11418));
        this._team2points.add(new Location(-82147, -46206, -11418));
        this._team2points.add(new Location(-82256, -46093, -11418));
        this._team2points.add(new Location(-82418, -45940, -11418));
        this._team2points.add(new Location(-82455, -45779, -11418));
        this._team2points.add(new Location(-82513, -45573, -11418));
        this._team2points.add(new Location(-82464, -45499, -11418));
        this._team2points.add(new Location(-82421, -45215, -11418));
        this._team2points.add(new Location(-82308, -45106, -11418));
        this._team2points.add(new Location(-82160, -44948, -11418));
        this._team2points.add(new Location(-81978, -44904, -11418));
    }

    @Override
    protected void onReload() {
        if (this._status > 0) {
            this.template_stop();
        }
        this._zone.removeListener((Listener)this._zoneListener);
    }
}
