/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.lang.ArrayUtils
 *  l2.commons.listener.Listener
 *  l2.gameserver.Config
 *  l2.gameserver.data.xml.holder.ItemHolder
 *  l2.gameserver.listener.actor.player.OnPlayerEnterListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObjectsStorage
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.actor.listener.PlayerListenerList
 *  l2.gameserver.model.items.ItemInstance
 *  l2.gameserver.model.items.PcInventory
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.stats.Env
 *  l2.gameserver.stats.Stats
 *  l2.gameserver.stats.funcs.Func
 *  l2.gameserver.templates.item.ItemTemplate
 *  l2.gameserver.utils.ItemFunctions
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.lang3.tuple.Pair
 *  org.dom4j.Document
 *  org.dom4j.Element
 *  org.dom4j.io.SAXReader
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package services;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import l2.commons.lang.ArrayUtils;
import l2.commons.listener.Listener;
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.listener.actor.player.OnPlayerEnterListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.model.actor.listener.PlayerListenerList;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.items.PcInventory;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.stats.Env;
import l2.gameserver.stats.Stats;
import l2.gameserver.stats.funcs.Func;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.utils.ItemFunctions;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StatModifier
implements OnPlayerEnterListener,
ScriptFile {
    private static final Logger ee = LoggerFactory.getLogger(StatMod.class);
    private static final StatModifier a = new StatModifier();
    private static final File l = new File(Config.DATAPACK_ROOT, "data/stats_custom_mod.xml");
    private static Map<Stats, Map<Integer, List<StatMod>>> ct = new HashMap<Stats, Map<Integer, List<StatMod>>>();

    private List<StatMod> a(Element element, ModCond[] modCondArray) {
        ArrayList<StatMod> arrayList = new ArrayList<StatMod>();
        Iterator iterator = element.elementIterator();
        while (iterator.hasNext()) {
            StatMod statMod;
            Element element2 = (Element)iterator.next();
            if ("mul".equalsIgnoreCase(element2.getName())) {
                statMod = new StatMod(modCondArray, Stats.valueOfXml((String)element2.attributeValue("stat")));
                statMod.addMul(Double.parseDouble(element2.attributeValue("val")) - 1.0);
                arrayList.add(statMod);
                continue;
            }
            if (!"add".equalsIgnoreCase(element2.getName())) continue;
            statMod = new StatMod(modCondArray, Stats.valueOfXml((String)element2.attributeValue("stat")));
            statMod.setAdd(Double.parseDouble(element2.attributeValue("val")));
            arrayList.add(statMod);
        }
        return arrayList;
    }

    private List<StatMod> a(Element element, final int n, ModCond[] modCondArray) {
        ArrayList<StatMod> arrayList = new ArrayList<StatMod>();
        modCondArray = (ModCond[])ArrayUtils.add((Object[])modCondArray, (Object)new ModCond(){

            @Override
            public boolean test(Player player, Creature creature) {
                return creature != null && creature.isPlayer() && creature.getPlayer().getActiveClassId() == n;
            }
        });
        arrayList.addAll(this.a(element, modCondArray));
        return arrayList;
    }

    private List<StatMod> b(Element element, ModCond[] modCondArray) {
        ArrayList<StatMod> arrayList = new ArrayList<StatMod>();
        int n = Integer.parseInt(element.attributeValue("itemId"));
        final ItemTemplate itemTemplate = ItemHolder.getInstance().getTemplate(n);
        modCondArray = (ModCond[])ArrayUtils.add((Object[])modCondArray, (Object)new ModCond(){

            private boolean a(PcInventory pcInventory, ItemTemplate itemTemplate2) {
                if (pcInventory == null || itemTemplate2 == null) {
                    return false;
                }
                int n = ItemFunctions.getPaperdollIndex((long)itemTemplate2.getBodyPart());
                if (n < 0) {
                    return false;
                }
                ItemInstance itemInstance = pcInventory.getPaperdollItem(n);
                return itemInstance != null && itemInstance.getItemId() == itemTemplate2.getItemId();
            }

            @Override
            public boolean test(Player player, Creature creature) {
                if (player == null) {
                    return false;
                }
                return this.a(player.getInventory(), itemTemplate);
            }
        });
        arrayList.addAll(this.a(element, modCondArray));
        return arrayList;
    }

    private List<StatMod> a(Element element, final int n, final OlyMode olyMode, ModCond[] modCondArray) {
        ArrayList<StatMod> arrayList = new ArrayList<StatMod>();
        modCondArray = (ModCond[])ArrayUtils.add((Object[])modCondArray, (Object)new ModCond(){

            @Override
            public boolean test(Player player, Creature creature) {
                if (player != null && player.getActiveClassId() == n) {
                    if (olyMode != OlyMode.ANY) {
                        if (olyMode == OlyMode.OLY_ONLY && !player.isOlyParticipant()) {
                            return false;
                        }
                        if (olyMode == OlyMode.NON_OLY_ONLY && player.isOlyParticipant()) {
                            return false;
                        }
                    }
                    return true;
                }
                return false;
            }
        });
        Iterator iterator = element.elementIterator();
        while (iterator.hasNext()) {
            Element element2 = (Element)iterator.next();
            if ("targetPlayer".equalsIgnoreCase(element2.getName())) {
                int n2 = Integer.parseInt(element2.attributeValue("classId"));
                arrayList.addAll(this.a(element2, n2, modCondArray));
                continue;
            }
            if (!"equipedWith".equalsIgnoreCase(element2.getName())) continue;
            arrayList.addAll(this.b(element2, modCondArray));
        }
        arrayList.addAll(this.a(element, (ModCond[])modCondArray.clone()));
        return arrayList;
    }

    private Pair<Boolean, Map<Stats, Map<Integer, List<StatMod>>>> b() {
        try {
            SAXReader sAXReader = new SAXReader(true);
            Document document = sAXReader.read(l);
            Element element = document.getRootElement();
            if (!"list".equalsIgnoreCase(element.getName())) {
                throw new RuntimeException();
            }
            if (!Boolean.parseBoolean(element.attributeValue("enabled", "false"))) {
                return Pair.of((Object)Boolean.FALSE, Collections.emptyMap());
            }
            HashMap hashMap = new HashMap();
            Iterator iterator = element.elementIterator();
            while (iterator.hasNext()) {
                Element element2 = (Element)iterator.next();
                if (!"player".equalsIgnoreCase(element2.getName())) continue;
                int n = Integer.parseInt(element2.attributeValue("classId"));
                OlyMode olyMode = OlyMode.valueOf(StringUtils.upperCase((String)element2.attributeValue("olyMode", "ANY")));
                for (StatMod statMod : this.a(element2, n, olyMode, new ModCond[0])) {
                    ArrayList<StatMod> arrayList;
                    HashMap<Integer, ArrayList<StatMod>> hashMap2 = (HashMap<Integer, ArrayList<StatMod>>)hashMap.get(statMod.getStat());
                    if (hashMap2 == null) {
                        hashMap2 = new HashMap<Integer, ArrayList<StatMod>>();
                        hashMap.put(statMod.getStat(), hashMap2);
                    }
                    if ((arrayList = (ArrayList<StatMod>)hashMap2.get(n)) == null) {
                        arrayList = new ArrayList<StatMod>();
                        hashMap2.put(n, arrayList);
                    }
                    arrayList.add(statMod);
                }
            }
            return Pair.of((Object)Boolean.TRUE, hashMap);
        }
        catch (Exception exception) {
            ee.error(exception.getMessage(), (Throwable)exception);
            return Pair.of((Object)Boolean.FALSE, Collections.emptyMap());
        }
    }

    public void onLoad() {
        ct.clear();
        Pair<Boolean, Map<Stats, Map<Integer, List<StatMod>>>> pair = this.b();
        if (!((Boolean)pair.getLeft()).booleanValue()) {
            ee.info("StatModifier: Disabled.");
            return;
        }
        PlayerListenerList.addGlobal((Listener)a);
        ct.putAll((Map)pair.getRight());
        ee.info("StatModifier: Enabled. Loaded mods for {} stats.", (Object)ct.size());
    }

    public void onReload() {
        this.onShutdown();
        this.onLoad();
    }

    public void onShutdown() {
        PlayerListenerList.removeGlobal((Listener)a);
        if (!ct.isEmpty()) {
            for (Player player : GameObjectsStorage.getAllPlayersForIterate()) {
                a.aw(player);
            }
        }
    }

    private void aw(Player player) {
        if (player == null) {
            return;
        }
        player.removeStatsOwner((Object)this);
    }

    private void ax(Player player) {
        for (Map.Entry<Stats, Map<Integer, List<StatMod>>> entry : ct.entrySet()) {
            Stats stats = entry.getKey();
            final Map<Integer, List<StatMod>> map = entry.getValue();
            player.addStatFunc(new Func(stats, 80, this){

                public void calc(Env env) {
                    if (env.character == null || !env.character.isPlayer()) {
                        return;
                    }
                    Player player = (Player)env.character;
                    List list = (List)map.get(player.getActiveClassId());
                    if (list == null || list.isEmpty()) {
                        return;
                    }
                    double d = 1.0;
                    double d2 = 0.0;
                    for (StatMod statMod : list) {
                        if (!statMod.test(player, env.target)) continue;
                        d += statMod.getMul() - 1.0;
                        d2 += statMod.getAdd();
                    }
                    env.value *= d;
                    env.value += d2;
                }
            });
        }
    }

    public void onPlayerEnter(Player player) {
        if (player == null) {
            return;
        }
        if (!ct.isEmpty()) {
            a.ax(player);
        }
    }

    private static class StatMod {
        private final ModCond[] a;
        private final Stats d;
        private double bd = 1.0;
        private double be = 0.0;

        private StatMod(ModCond[] modCondArray, Stats stats) {
            this.a = (ModCond[])modCondArray.clone();
            this.d = stats;
        }

        public boolean test(Player player, Creature creature) {
            for (ModCond modCond : this.a) {
                if (modCond.test(player, creature)) continue;
                return false;
            }
            return true;
        }

        public Stats getStat() {
            return this.d;
        }

        public double getMul() {
            return this.bd;
        }

        public void addMul(double d) {
            this.bd += d;
        }

        public double getAdd() {
            return this.be;
        }

        public void setAdd(double d) {
            this.be = d;
        }
    }

    private static abstract class ModCond {
        private ModCond() {
        }

        public abstract boolean test(Player var1, Creature var2);
    }

    static final class OlyMode
    extends Enum<OlyMode> {
        public static final /* enum */ OlyMode OLY_ONLY = new OlyMode();
        public static final /* enum */ OlyMode NON_OLY_ONLY = new OlyMode();
        public static final /* enum */ OlyMode ANY = new OlyMode();
        private static final /* synthetic */ OlyMode[] a;

        public static OlyMode[] values() {
            return (OlyMode[])a.clone();
        }

        public static OlyMode valueOf(String string) {
            return Enum.valueOf(OlyMode.class, string);
        }

        private static /* synthetic */ OlyMode[] a() {
            return new OlyMode[]{OLY_ONLY, NON_OLY_ONLY, ANY};
        }

        static {
            a = OlyMode.a();
        }
    }
}
