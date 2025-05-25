/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.dom4j.Element
 */
package l2.gameserver.data.xml.parser;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import l2.commons.collections.MultiValueSet;
import l2.commons.data.xml.AbstractDirParser;
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.EventHolder;
import l2.gameserver.model.entity.events.EventAction;
import l2.gameserver.model.entity.events.EventType;
import l2.gameserver.model.entity.events.GlobalEvent;
import l2.gameserver.model.entity.events.actions.ActiveDeactiveAction;
import l2.gameserver.model.entity.events.actions.AnnounceAction;
import l2.gameserver.model.entity.events.actions.GiveItemAction;
import l2.gameserver.model.entity.events.actions.IfElseAction;
import l2.gameserver.model.entity.events.actions.InitAction;
import l2.gameserver.model.entity.events.actions.NpcSayAction;
import l2.gameserver.model.entity.events.actions.OpenCloseAction;
import l2.gameserver.model.entity.events.actions.PlaySoundAction;
import l2.gameserver.model.entity.events.actions.RefreshAction;
import l2.gameserver.model.entity.events.actions.SayAction;
import l2.gameserver.model.entity.events.actions.SpawnDespawnAction;
import l2.gameserver.model.entity.events.actions.StartStopAction;
import l2.gameserver.model.entity.events.actions.TeleportPlayersAction;
import l2.gameserver.model.entity.events.objects.BoatPoint;
import l2.gameserver.model.entity.events.objects.CTBTeamObject;
import l2.gameserver.model.entity.events.objects.CastleDamageZoneObject;
import l2.gameserver.model.entity.events.objects.DoorObject;
import l2.gameserver.model.entity.events.objects.SiegeToggleNpcObject;
import l2.gameserver.model.entity.events.objects.SpawnExObject;
import l2.gameserver.model.entity.events.objects.StaticObjectObject;
import l2.gameserver.model.entity.events.objects.ZoneObject;
import l2.gameserver.network.l2.components.ChatType;
import l2.gameserver.network.l2.components.NpcString;
import l2.gameserver.network.l2.components.SysString;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.PlaySound;
import l2.gameserver.utils.Location;
import org.dom4j.Element;

public final class EventParser
extends AbstractDirParser<EventHolder> {
    private static final EventParser _instance = new EventParser();

    public static EventParser getInstance() {
        return _instance;
    }

    protected EventParser() {
        super(EventHolder.getInstance());
    }

    @Override
    public File getXMLDir() {
        return new File(Config.DATAPACK_ROOT, "data/events/");
    }

    @Override
    public boolean isIgnored(File file) {
        return false;
    }

    @Override
    public String getDTDFileName() {
        return "events.dtd";
    }

    @Override
    protected void readData(Element element) throws Exception {
        Iterator iterator = element.elementIterator("event");
        while (iterator.hasNext()) {
            List<Object> list;
            Element element2;
            Iterator iterator2;
            Element element3;
            Element element4 = (Element)iterator.next();
            int n = Integer.parseInt(element4.attributeValue("id"));
            String string = element4.attributeValue("name");
            String string2 = element4.attributeValue("impl");
            EventType eventType = EventType.valueOf(element4.attributeValue("type"));
            Class<?> clazz = null;
            try {
                clazz = Class.forName("l2.gameserver.model.entity.events.impl." + string2 + "Event");
            }
            catch (ClassNotFoundException classNotFoundException) {
                this.info("Not found impl class: " + string2 + "; File: " + this.getCurrentFileName());
                continue;
            }
            Constructor<?> constructor = clazz.getConstructor(MultiValueSet.class);
            MultiValueSet<String> multiValueSet = new MultiValueSet<String>();
            multiValueSet.set("id", n);
            multiValueSet.set("name", string);
            Object object = element4.elementIterator("parameter");
            while (object.hasNext()) {
                element3 = (Element)object.next();
                multiValueSet.set(element3.attributeValue("name"), element3.attributeValue("value"));
            }
            object = (GlobalEvent)constructor.newInstance(multiValueSet);
            ((GlobalEvent)object).addOnStartActions(this.parseActions(element4.element("on_start"), Integer.MAX_VALUE));
            ((GlobalEvent)object).addOnStopActions(this.parseActions(element4.element("on_stop"), Integer.MAX_VALUE));
            ((GlobalEvent)object).addOnInitActions(this.parseActions(element4.element("on_init"), Integer.MAX_VALUE));
            element3 = element4.element("on_time");
            if (element3 != null) {
                iterator2 = element3.elementIterator("on");
                while (iterator2.hasNext()) {
                    element2 = (Element)iterator2.next();
                    int n2 = Integer.parseInt(element2.attributeValue("time"));
                    list = this.parseActions(element2, n2);
                    ((GlobalEvent)object).addOnTimeActions(n2, list);
                }
            }
            iterator2 = element4.elementIterator("objects");
            while (iterator2.hasNext()) {
                element2 = (Element)iterator2.next();
                String string3 = element2.attributeValue("name");
                list = this.parseObjects(element2);
                ((GlobalEvent)object).addObjects(string3, list);
            }
            ((EventHolder)this.getHolder()).addEvent(eventType, (GlobalEvent)object);
        }
    }

    private List<Serializable> parseObjects(Element element) {
        if (element == null) {
            return Collections.emptyList();
        }
        ArrayList<Serializable> arrayList = new ArrayList<Serializable>(2);
        Iterator iterator = element.elementIterator();
        while (iterator.hasNext()) {
            int n;
            int n2;
            Element element2 = (Element)iterator.next();
            String string = element2.getName();
            if (string.equalsIgnoreCase("boat_point")) {
                arrayList.add(BoatPoint.parse(element2));
                continue;
            }
            if (string.equalsIgnoreCase("point")) {
                arrayList.add(Location.parse(element2));
                continue;
            }
            if (string.equalsIgnoreCase("spawn_ex")) {
                arrayList.add(new SpawnExObject(element2.attributeValue("name")));
                continue;
            }
            if (string.equalsIgnoreCase("door")) {
                arrayList.add(new DoorObject(Integer.parseInt(element2.attributeValue("id"))));
                continue;
            }
            if (string.equalsIgnoreCase("static_object")) {
                arrayList.add(new StaticObjectObject(Integer.parseInt(element2.attributeValue("id"))));
                continue;
            }
            if (string.equalsIgnoreCase("siege_toggle_npc")) {
                n2 = Integer.parseInt(element2.attributeValue("id"));
                n = Integer.parseInt(element2.attributeValue("fake_id"));
                int n3 = Integer.parseInt(element2.attributeValue("x"));
                int n4 = Integer.parseInt(element2.attributeValue("y"));
                int n5 = Integer.parseInt(element2.attributeValue("z"));
                int n6 = Integer.parseInt(element2.attributeValue("hp"));
                Set<String> set = Collections.emptySet();
                Iterator iterator2 = element2.elementIterator();
                while (iterator2.hasNext()) {
                    Element element3 = (Element)iterator2.next();
                    if (set.isEmpty()) {
                        set = new HashSet();
                    }
                    set.add(element3.attributeValue("name"));
                }
                arrayList.add(new SiegeToggleNpcObject(n2, n, new Location(n3, n4, n5), n6, set));
                continue;
            }
            if (string.equalsIgnoreCase("castle_zone")) {
                long l = Long.parseLong(element2.attributeValue("price"));
                arrayList.add(new CastleDamageZoneObject(element2.attributeValue("name"), l));
                continue;
            }
            if (string.equalsIgnoreCase("zone")) {
                arrayList.add(new ZoneObject(element2.attributeValue("name")));
                continue;
            }
            if (!string.equalsIgnoreCase("ctb_team")) continue;
            n2 = Integer.parseInt(element2.attributeValue("mob_id"));
            n = Integer.parseInt(element2.attributeValue("id"));
            Location location = Location.parse(element2);
            arrayList.add(new CTBTeamObject(n2, n, location));
        }
        return arrayList;
    }

    private List<EventAction> parseActions(Element element, int n) {
        if (element == null) {
            return Collections.emptyList();
        }
        IfElseAction ifElseAction = null;
        ArrayList arrayList = new ArrayList(0);
        Iterator iterator = element.elementIterator();
        while (iterator.hasNext()) {
            Object object;
            Object object2;
            Element element2 = (Element)iterator.next();
            if (element2.getName().equalsIgnoreCase("start")) {
                String string = element2.attributeValue("name");
                StartStopAction startStopAction = new StartStopAction(string, true);
                arrayList.add(startStopAction);
                continue;
            }
            if (element2.getName().equalsIgnoreCase("stop")) {
                String string = element2.attributeValue("name");
                StartStopAction startStopAction = new StartStopAction(string, false);
                arrayList.add(startStopAction);
                continue;
            }
            if (element2.getName().equalsIgnoreCase("spawn")) {
                String string = element2.attributeValue("name");
                SpawnDespawnAction spawnDespawnAction = new SpawnDespawnAction(string, true);
                arrayList.add(spawnDespawnAction);
                continue;
            }
            if (element2.getName().equalsIgnoreCase("despawn")) {
                String string = element2.attributeValue("name");
                SpawnDespawnAction spawnDespawnAction = new SpawnDespawnAction(string, false);
                arrayList.add(spawnDespawnAction);
                continue;
            }
            if (element2.getName().equalsIgnoreCase("open")) {
                String string = element2.attributeValue("name");
                OpenCloseAction openCloseAction = new OpenCloseAction(true, string);
                arrayList.add(openCloseAction);
                continue;
            }
            if (element2.getName().equalsIgnoreCase("close")) {
                String string = element2.attributeValue("name");
                OpenCloseAction openCloseAction = new OpenCloseAction(false, string);
                arrayList.add(openCloseAction);
                continue;
            }
            if (element2.getName().equalsIgnoreCase("active")) {
                String string = element2.attributeValue("name");
                ActiveDeactiveAction activeDeactiveAction = new ActiveDeactiveAction(true, string);
                arrayList.add(activeDeactiveAction);
                continue;
            }
            if (element2.getName().equalsIgnoreCase("deactive")) {
                String string = element2.attributeValue("name");
                ActiveDeactiveAction activeDeactiveAction = new ActiveDeactiveAction(false, string);
                arrayList.add(activeDeactiveAction);
                continue;
            }
            if (element2.getName().equalsIgnoreCase("refresh")) {
                String string = element2.attributeValue("name");
                RefreshAction refreshAction = new RefreshAction(string);
                arrayList.add(refreshAction);
                continue;
            }
            if (element2.getName().equalsIgnoreCase("init")) {
                String string = element2.attributeValue("name");
                InitAction initAction = new InitAction(string);
                arrayList.add(initAction);
                continue;
            }
            if (element2.getName().equalsIgnoreCase("npc_say")) {
                int n2 = Integer.parseInt(element2.attributeValue("npc"));
                ChatType chatType = ChatType.valueOf(element2.attributeValue("chat"));
                int n3 = Integer.parseInt(element2.attributeValue("range"));
                object2 = NpcString.valueOf(element2.attributeValue("text"));
                object = new NpcSayAction(n2, n3, chatType, (NpcString)((Object)object2));
                arrayList.add(object);
                continue;
            }
            if (element2.getName().equalsIgnoreCase("play_sound")) {
                int n4 = Integer.parseInt(element2.attributeValue("range"));
                String string = element2.attributeValue("sound");
                PlaySound.Type type = PlaySound.Type.valueOf(element2.attributeValue("type"));
                object2 = new PlaySoundAction(n4, string, type);
                arrayList.add(object2);
                continue;
            }
            if (element2.getName().equalsIgnoreCase("give_item")) {
                int n5 = Integer.parseInt(element2.attributeValue("id"));
                long l = Integer.parseInt(element2.attributeValue("count"));
                object2 = new GiveItemAction(n5, l);
                arrayList.add(object2);
                continue;
            }
            if (element2.getName().equalsIgnoreCase("announce")) {
                String string = element2.attributeValue("val");
                if (string == null && n == Integer.MAX_VALUE) {
                    this.info("Can't get announce time." + this.getCurrentFileName());
                    continue;
                }
                int n6 = string == null ? n : Integer.parseInt(string);
                AnnounceAction announceAction = new AnnounceAction(n6);
                arrayList.add(announceAction);
                continue;
            }
            if (element2.getName().equalsIgnoreCase("if")) {
                String string = element2.attributeValue("name");
                IfElseAction ifElseAction2 = new IfElseAction(string, false);
                ifElseAction2.setIfList(this.parseActions(element2, n));
                arrayList.add(ifElseAction2);
                ifElseAction = ifElseAction2;
                continue;
            }
            if (element2.getName().equalsIgnoreCase("ifnot")) {
                String string = element2.attributeValue("name");
                IfElseAction ifElseAction3 = new IfElseAction(string, true);
                ifElseAction3.setIfList(this.parseActions(element2, n));
                arrayList.add(ifElseAction3);
                ifElseAction = ifElseAction3;
                continue;
            }
            if (element2.getName().equalsIgnoreCase("else")) {
                if (ifElseAction == null) {
                    this.info("Not find <if> for <else> tag");
                    continue;
                }
                ifElseAction.setElseList(this.parseActions(element2, n));
                continue;
            }
            if (element2.getName().equalsIgnoreCase("say")) {
                ChatType chatType = ChatType.valueOf(element2.attributeValue("chat"));
                int n7 = Integer.parseInt(element2.attributeValue("range"));
                String string = element2.attributeValue("how");
                object2 = element2.attributeValue("text");
                object = SysString.valueOf2(string);
                SayAction sayAction = null;
                sayAction = object != null ? new SayAction(n7, chatType, (SysString)((Object)object), SystemMsg.valueOf((String)object2)) : new SayAction(n7, chatType, string, NpcString.valueOf((String)object2));
                arrayList.add(sayAction);
                continue;
            }
            if (!element2.getName().equalsIgnoreCase("teleport_players")) continue;
            String string = element2.attributeValue("id");
            TeleportPlayersAction teleportPlayersAction = new TeleportPlayersAction(string);
            arrayList.add(teleportPlayersAction);
        }
        return arrayList.isEmpty() ? Collections.emptyList() : arrayList;
    }
}
