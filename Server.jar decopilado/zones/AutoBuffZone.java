/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.lang.reference.HardReference
 *  l2.commons.listener.Listener
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.instancemanager.ReflectionManager
 *  l2.gameserver.listener.actor.OnReviveListener
 *  l2.gameserver.listener.zone.OnZoneEnterLeaveListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.Zone
 *  l2.gameserver.model.base.ClassId
 *  l2.gameserver.model.entity.events.impl.SiegeEvent
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.tables.SkillTable
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.lang3.tuple.Pair
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package zones;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import l2.commons.lang.reference.HardReference;
import l2.commons.listener.Listener;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.listener.actor.OnReviveListener;
import l2.gameserver.listener.zone.OnZoneEnterLeaveListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.Zone;
import l2.gameserver.model.base.ClassId;
import l2.gameserver.model.entity.events.impl.SiegeEvent;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.tables.SkillTable;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutoBuffZone
implements ScriptFile {
    private static final Logger eu = LoggerFactory.getLogger(AutoBuffZone.class);

    private static boolean D(Player player) {
        if (player == null || player.isDead()) {
            return false;
        }
        if (player.isCursedWeaponEquipped() || player.isFakeDeath() || player.isFlying()) {
            return false;
        }
        if (player.isInDuel() || player.isOlyParticipant() || player.isInStoreMode() || player.isSitting()) {
            return false;
        }
        return player.getEvent(SiegeEvent.class) == null;
    }

    private static List<Pair<Skill, Integer>> h(String string) {
        ArrayList<Pair> arrayList = new ArrayList<Pair>();
        StringTokenizer stringTokenizer = new StringTokenizer(string, ",;");
        while (stringTokenizer.hasMoreTokens()) {
            int n;
            String string2 = stringTokenizer.nextToken().trim();
            if (string2.isEmpty()) continue;
            int n2 = string2.indexOf(47);
            String string3 = string2;
            Integer n3 = null;
            if (n2 > 0) {
                n3 = Integer.parseInt(string2.substring(n2 + 1).trim());
                string3 = string2.substring(0, n2).trim();
            }
            if ((n = string3.indexOf(58)) < 0) {
                throw new IllegalArgumentException("Can't parse \"" + string2 + "\"");
            }
            int n4 = Integer.parseInt(string3.substring(0, n).trim());
            int n5 = Integer.parseInt(string3.substring(n + 1).trim());
            Skill skill = SkillTable.getInstance().getInfo(n4, n5);
            if (skill == null) {
                throw new IllegalArgumentException("Unknown skill \"" + string2 + "\"");
            }
            arrayList.add(Pair.of((Object)skill, (Object)n3));
        }
        arrayList.trimToSize();
        return Collections.unmodifiableList(arrayList);
    }

    private static Map<Predicate<Player>, Pair<Skill, Integer>> e(String string) {
        LinkedHashMap<Predicate<Player>, Pair> linkedHashMap = new LinkedHashMap<Predicate<Player>, Pair>();
        StringTokenizer stringTokenizer = new StringTokenizer(string, ",;");
        while (stringTokenizer.hasMoreTokens()) {
            int n;
            String string2 = StringUtils.trimToEmpty((String)stringTokenizer.nextToken().trim());
            if (string2.isEmpty()) continue;
            if (string2.charAt(string2.length() - 1) != ')') {
                throw new IllegalArgumentException("Can't parse \"" + string2 + "\". No class(id)");
            }
            int n2 = string2.indexOf(40);
            if (n2 <= 0) {
                throw new IllegalArgumentException("Can't parse \"" + string2 + "\". No class(id)");
            }
            String string3 = StringUtils.trimToEmpty((String)string2.substring(0, n2));
            ClassId classId2 = StringUtils.isNumeric((CharSequence)string3) ? ClassId.getClassById((int)Integer.parseInt(string3)) : Stream.of(ClassId.values()).filter(classId -> StringUtils.equalsIgnoreCase((CharSequence)classId.toString(), (CharSequence)string3)).findFirst().orElseThrow(() -> new IllegalArgumentException("Can't parse \"" + string2 + "\""));
            String string4 = StringUtils.trimToEmpty((String)string2.substring(n2 + 1, string2.length() - 1));
            int n3 = string4.indexOf(47);
            Integer n4 = null;
            if (n3 > 0) {
                n4 = Integer.parseInt(string4.substring(n3 + 1).trim());
                string4 = string4.substring(0, n3).trim();
            }
            if ((n = string4.indexOf(58)) < 0) {
                throw new IllegalArgumentException("Can't parse \"" + string2 + "\"");
            }
            int n5 = Integer.parseInt(string4.substring(0, n).trim());
            int n6 = Integer.parseInt(string4.substring(n + 1).trim());
            Skill skill = SkillTable.getInstance().getInfo(n5, n6);
            if (skill == null) {
                throw new IllegalArgumentException("Unknown skill \"" + string2 + "\"");
            }
            linkedHashMap.put(player -> player != null && player.isConnected() && player.getClassId() == classId2, Pair.of((Object)skill, (Object)n4));
        }
        return Collections.unmodifiableMap(linkedHashMap);
    }

    private static void init() {
        Object object;
        int n = 0;
        Collection collection = ReflectionManager.DEFAULT.getZones();
        for (Object object2 : collection) {
            String string = object2.getParams().getString((Object)"zoneBuffs", null);
            if (StringUtils.isBlank((CharSequence)string) || (object = AutoBuffZone.h(string)).isEmpty()) continue;
            object2.addListener((Listener)new AutoBuffZoneListener((List<Pair<Skill, Integer>>)object));
            ++n;
        }
        eu.info("AutoBuffZone: Loaded " + n + " auto buff zone(s).");
        int n2 = 0;
        for (String string : collection) {
            Map<Predicate<Player>, Pair<Skill, Integer>> map;
            object = string.getParams().getString((Object)"zoneClassedBuffs", null);
            if (StringUtils.isBlank((CharSequence)object) || (map = AutoBuffZone.e((String)object)).isEmpty()) continue;
            string.addListener((Listener)new AutoBuffClassedZoneListener(map));
            ++n2;
        }
        eu.info("AutoBuffZone: Loaded " + n2 + " classed auto buff zone(s).");
    }

    public void onLoad() {
        AutoBuffZone.init();
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    private static class AutoBuffZoneListener
    implements OnZoneEnterLeaveListener {
        private final List<Pair<Skill, Integer>> ea;
        final OnReviveListener onReviveListener = new OnReviveListener(){

            public void onRevive(Creature creature) {
                this.m(creature);
            }
        };

        private AutoBuffZoneListener(List<Pair<Skill, Integer>> list) {
            this.ea = list;
        }

        private void m(Creature creature) {
            if (creature == null || !creature.isPlayer()) {
                return;
            }
            Player player = creature.getPlayer();
            if (!AutoBuffZone.D(player)) {
                return;
            }
            final HardReference hardReference = player.getRef();
            ThreadPoolManager.getInstance().execute((Runnable)new RunnableImpl(){

                public void runImpl() throws Exception {
                    Player player = (Player)hardReference.get();
                    if (player == null) {
                        return;
                    }
                    for (Pair<Skill, Integer> pair : ea) {
                        Skill skill = (Skill)pair.getLeft();
                        Integer n = (Integer)pair.getRight();
                        if (player.getEffectList().containEffectFromSkills(new int[]{skill.getId()})) continue;
                        if (n != null) {
                            skill.getEffects((Creature)player, (Creature)player, false, false, (long)n.intValue() * 1000L, 1.0, false);
                            continue;
                        }
                        skill.getEffects((Creature)player, (Creature)player, false, false);
                    }
                }
            });
        }

        public void onZoneEnter(Zone zone, Creature creature) {
            creature.addListener((Listener)this.onReviveListener);
            this.m(creature);
        }

        public void onZoneLeave(Zone zone, Creature creature) {
            creature.removeListener((Listener)this.onReviveListener);
        }
    }

    private static class AutoBuffClassedZoneListener
    implements OnZoneEnterLeaveListener {
        private final Map<Predicate<Player>, Pair<Skill, Integer>> cx;
        final OnReviveListener onReviveClassListener = new OnReviveListener(){

            public void onRevive(Creature creature) {
                this.l(creature);
            }
        };

        private AutoBuffClassedZoneListener(Map<Predicate<Player>, Pair<Skill, Integer>> map) {
            this.cx = map;
        }

        private void l(Creature creature) {
            if (creature == null || !creature.isPlayer()) {
                return;
            }
            Player player = creature.getPlayer();
            if (!AutoBuffZone.D(player)) {
                return;
            }
            final HardReference hardReference = player.getRef();
            ThreadPoolManager.getInstance().execute((Runnable)new RunnableImpl(){

                public void runImpl() {
                    Player player = (Player)hardReference.get();
                    if (player == null) {
                        return;
                    }
                    for (Map.Entry<Predicate<Player>, Pair<Skill, Integer>> entry : cx.entrySet()) {
                        Predicate<Player> predicate = entry.getKey();
                        if (!predicate.test(player)) continue;
                        Pair<Skill, Integer> pair = entry.getValue();
                        Skill skill = (Skill)pair.getLeft();
                        Integer n = (Integer)pair.getRight();
                        if (player.getEffectList().containEffectFromSkills(new int[]{skill.getId()})) continue;
                        if (n != null) {
                            skill.getEffects((Creature)player, (Creature)player, false, false, (long)n.intValue() * 1000L, 1.0, false);
                            continue;
                        }
                        skill.getEffects((Creature)player, (Creature)player, false, false);
                    }
                }
            });
        }

        public void onZoneEnter(Zone zone, Creature creature) {
            this.l(creature);
            creature.addListener((Listener)this.onReviveClassListener);
        }

        public void onZoneLeave(Zone zone, Creature creature) {
            creature.removeListener((Listener)this.onReviveClassListener);
        }
    }
}
