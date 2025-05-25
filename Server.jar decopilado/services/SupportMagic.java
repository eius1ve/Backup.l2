/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.Config
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.Summon
 *  l2.gameserver.model.base.Race
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.network.l2.s2c.MagicSkillUse
 *  l2.gameserver.network.l2.s2c.SystemMessage
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.skills.TimeStamp
 *  l2.gameserver.tables.SkillTable
 *  org.apache.commons.lang3.tuple.Pair
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package services;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import l2.gameserver.Config;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.Summon;
import l2.gameserver.model.base.Race;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.MagicSkillUse;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.skills.TimeStamp;
import l2.gameserver.tables.SkillTable;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class SupportMagic
extends Functions
implements ScriptFile {
    private static final Logger ef = LoggerFactory.getLogger(SupportMagic.class);
    private static final File m = new File(Config.DATAPACK_ROOT, "data/newbie_buffs.xml");
    private static List<NewbieBuffsList> dW;

    public void getSupportMagic() {
        Player player = this.getSelf();
        NpcInstance npcInstance = this.getNpc();
        SupportMagic.doSupportMagic(npcInstance, player, false);
    }

    public void getSupportServitorMagic() {
        Player player = this.getSelf();
        NpcInstance npcInstance = this.getNpc();
        SupportMagic.doSupportMagic(npcInstance, player, true);
    }

    public void getProtectionBlessing() {
        Player player = this.getSelf();
        NpcInstance npcInstance = this.getNpc();
        if (player.getKarma() > 0) {
            return;
        }
        if (player.getLevel() > 24 || player.getClassId().getLevel() >= 3) {
            SupportMagic.show((String)"newbiehelper/pk_protect002.htm", (Player)player, (NpcInstance)npcInstance, (Object[])new Object[0]);
            return;
        }
        npcInstance.doCast(SkillTable.getInstance().getInfo(5182, 1), (Creature)player, true);
    }

    private static boolean a(Player player, String string) {
        if (player.isSharedGroupDisabled(-99999)) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_IS_NOT_AVAILABLE_AT_THIS_TIME_BEING_PREPARED_FOR_REUSE).addString(string));
            return false;
        }
        TimeStamp timeStamp = new TimeStamp(-99999, System.currentTimeMillis() + Config.ALT_NPC_BUFFER_REUSE_DELAY, Config.ALT_NPC_BUFFER_REUSE_DELAY);
        player.addSharedGroupReuse(-99999, timeStamp);
        return true;
    }

    public static void doSupportMagic(NpcInstance npcInstance, Player player, boolean bl) {
        if (player.isCursedWeaponEquipped()) {
            return;
        }
        if (player.isMounted() || player.isInMountTransform()) {
            player.sendMessage(new CustomMessage("scripts.SupportMagic.Riding", player, new Object[0]));
            return;
        }
        if (!SupportMagic.a(player, npcInstance.getName())) {
            return;
        }
        for (NewbieBuffsList newbieBuffsList : dW) {
            if (!newbieBuffsList.getType().canUse(player)) continue;
            int n = player.getLevel();
            if (n < newbieBuffsList.getMinLevel()) {
                SupportMagic.show((String)"newbiehelper/guide_for_newbie002.htm", (Player)player, (NpcInstance)npcInstance, (Object[])new Object[]{"%min_level%", newbieBuffsList.getMinLevel()});
                return;
            }
            if (n > newbieBuffsList.getMaxLevel()) {
                SupportMagic.show((String)"newbiehelper/guide_for_newbie003.htm", (Player)player, (NpcInstance)npcInstance, (Object[])new Object[]{"%max_level%", newbieBuffsList.getMaxLevel()});
                return;
            }
            newbieBuffsList.apply((Creature)npcInstance, (Creature)player);
            Summon summon = player.getPet();
            if (summon != null) {
                newbieBuffsList.apply((Creature)npcInstance, (Creature)summon);
            }
            return;
        }
    }

    private static NewbieBuffsList a(NewbieBuffsListType newbieBuffsListType, Node node) {
        int n;
        int n2 = n = Integer.parseInt(node.getAttributes().getNamedItem("max_level").getNodeValue());
        ArrayList<Pair<Pair<Integer, Integer>, Skill>> arrayList = new ArrayList<Pair<Pair<Integer, Integer>, Skill>>();
        for (Node node2 = node.getFirstChild(); node2 != null; node2 = node2.getNextSibling()) {
            if (!"buff".equalsIgnoreCase(node2.getNodeName())) continue;
            NamedNodeMap namedNodeMap = node2.getAttributes();
            int n3 = Integer.parseInt(namedNodeMap.getNamedItem("skill_id").getNodeValue());
            int n4 = Integer.parseInt(namedNodeMap.getNamedItem("skill_level").getNodeValue());
            int n5 = Integer.parseInt(namedNodeMap.getNamedItem("min_level").getNodeValue());
            int n6 = Integer.parseInt(namedNodeMap.getNamedItem("max_level").getNodeValue());
            if (n5 < n2) {
                n2 = n5;
            }
            if (n6 >= n) {
                n = n6;
            }
            Pair pair = Pair.of((Object)Pair.of((Object)n5, (Object)n6), (Object)SkillTable.getInstance().getInfo(n3, n4));
            arrayList.add((Pair<Pair<Integer, Integer>, Skill>)pair);
        }
        return new NewbieBuffsList(newbieBuffsListType, n2, n, arrayList);
    }

    private static List<NewbieBuffsList> a(Document document) {
        ArrayList<NewbieBuffsList> arrayList = new ArrayList<NewbieBuffsList>();
        for (Node node = document.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (!"list".equalsIgnoreCase(node.getNodeName())) continue;
            for (Node node2 = node.getFirstChild(); node2 != null; node2 = node2.getNextSibling()) {
                NewbieBuffsList newbieBuffsList;
                if ("warrior".equalsIgnoreCase(node2.getNodeName())) {
                    newbieBuffsList = SupportMagic.a(NewbieBuffsListType.WARRIOR, node2);
                    arrayList.add(newbieBuffsList);
                    continue;
                }
                if (!"mage".equalsIgnoreCase(node2.getNodeName())) continue;
                newbieBuffsList = SupportMagic.a(NewbieBuffsListType.MAGE, node2);
                arrayList.add(newbieBuffsList);
            }
        }
        return Collections.unmodifiableList(arrayList);
    }

    private static List<NewbieBuffsList> a(File file) {
        Document document;
        Object object;
        try {
            object = DocumentBuilderFactory.newInstance();
            ((DocumentBuilderFactory)object).setValidating(false);
            ((DocumentBuilderFactory)object).setIgnoringComments(true);
            document = ((DocumentBuilderFactory)object).newDocumentBuilder().parse(file);
        }
        catch (Exception exception) {
            ef.error("Error loading file " + file, (Throwable)exception);
            return Collections.emptyList();
        }
        try {
            object = SupportMagic.a(document);
            int n = 0;
            Iterator iterator = object.iterator();
            while (iterator.hasNext()) {
                NewbieBuffsList newbieBuffsList = (NewbieBuffsList)iterator.next();
                n += newbieBuffsList.getBuffs().size();
            }
            ef.info("SupportMagic: Loaded " + n + " newbie buff(s).");
            return object;
        }
        catch (Exception exception) {
            ef.error("Error in file " + file, (Throwable)exception);
            return Collections.emptyList();
        }
    }

    public void onLoad() {
        dW = SupportMagic.a(m);
    }

    public void onReload() {
        this.onLoad();
    }

    public void onShutdown() {
    }

    private static class NewbieBuffsList {
        private final NewbieBuffsListType a;
        private final int bFY;
        private final int bFZ;
        private final List<Pair<Pair<Integer, Integer>, Skill>> dX;

        private NewbieBuffsList(NewbieBuffsListType newbieBuffsListType, int n, int n2, List<Pair<Pair<Integer, Integer>, Skill>> list) {
            this.a = newbieBuffsListType;
            this.bFY = n;
            this.bFZ = n2;
            this.dX = list;
        }

        public NewbieBuffsListType getType() {
            return this.a;
        }

        public int getMinLevel() {
            return this.bFY;
        }

        public int getMaxLevel() {
            return this.bFZ;
        }

        public List<Pair<Pair<Integer, Integer>, Skill>> getBuffs() {
            return this.dX;
        }

        public void apply(Creature creature, Creature creature2) {
            int n = creature2.getLevel();
            for (Pair<Pair<Integer, Integer>, Skill> pair : this.getBuffs()) {
                Pair pair2 = (Pair)pair.getKey();
                if (n < (Integer)pair2.getLeft() || n > (Integer)pair2.getRight()) continue;
                Skill skill = (Skill)pair.getValue();
                creature.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse(creature, creature2, skill, 0, 0L)});
                creature.callSkill(skill, Collections.singletonList(creature2), true);
            }
        }
    }

    /*
     * Uses 'sealed' constructs - enablewith --sealed true
     */
    private static abstract class NewbieBuffsListType
    extends Enum<NewbieBuffsListType> {
        public static final /* enum */ NewbieBuffsListType WARRIOR = new NewbieBuffsListType(){

            @Override
            public boolean canUse(Player player) {
                return !player.isMageClass() || player.getTemplate().race == Race.orc;
            }
        };
        public static final /* enum */ NewbieBuffsListType MAGE = new NewbieBuffsListType(){

            @Override
            public boolean canUse(Player player) {
                return player.isMageClass() && player.getTemplate().race != Race.orc;
            }
        };
        private static final /* synthetic */ NewbieBuffsListType[] a;

        public static NewbieBuffsListType[] values() {
            return (NewbieBuffsListType[])a.clone();
        }

        public static NewbieBuffsListType valueOf(String string) {
            return Enum.valueOf(NewbieBuffsListType.class, string);
        }

        public abstract boolean canUse(Player var1);

        private static /* synthetic */ NewbieBuffsListType[] a() {
            return new NewbieBuffsListType[]{WARRIOR, MAGE};
        }

        static {
            a = NewbieBuffsListType.a();
        }
    }
}
