/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.Config
 *  l2.gameserver.data.xml.holder.NpcHolder
 *  l2.gameserver.data.xml.holder.SoulCrystalHolder
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.instances.MonsterInstance
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.items.ItemInstance
 *  l2.gameserver.model.quest.Quest
 *  l2.gameserver.model.quest.QuestState
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.SystemMessage
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.templates.SoulCrystal
 *  l2.gameserver.templates.npc.AbsorbInfo
 *  l2.gameserver.templates.npc.NpcTemplate
 */
package quests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import l2.commons.util.Rnd;
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.NpcHolder;
import l2.gameserver.data.xml.holder.SoulCrystalHolder;
import l2.gameserver.model.Player;
import l2.gameserver.model.instances.MonsterInstance;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.templates.SoulCrystal;
import l2.gameserver.templates.npc.AbsorbInfo;
import l2.gameserver.templates.npc.NpcTemplate;

public class _350_EnhanceYourWeapon
extends Quest
implements ScriptFile {
    private static final int aLW = 30115;
    private static final int aLX = 30194;
    private static final int aLY = 30856;
    private static final int aLZ = 4629;
    private static final int aMa = 4640;
    private static final int aMb = 4651;
    private static final int[] bJ = new int[]{4651, 4652, 4653, 4654, 4655, 4656, 4657, 4658, 4659, 4660, 4661, 4664, 4629, 4630, 4631, 4632, 4633, 4634, 4635, 4636, 4637, 4638, 4639, 4662, 4640, 4641, 4642, 4643, 4644, 4645, 4646, 4647, 4648, 4649, 4650, 4663};

    public _350_EnhanceYourWeapon() {
        super(0);
        this.addStartNpc(new int[]{30115, 30194, 30856});
        for (NpcTemplate npcTemplate : NpcHolder.getInstance().getAll()) {
            if (npcTemplate == null || npcTemplate.getAbsorbInfo().isEmpty()) continue;
            this.addKillId(new int[]{npcTemplate.npcId});
        }
    }

    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public String onEvent(String string, QuestState questState, NpcInstance npcInstance) {
        int n = npcInstance.getNpcId();
        String string2 = string;
        if (n == 30115 || n == 30194 || n == 30856) {
            if (string.equalsIgnoreCase("quest_accept")) {
                questState.setCond(1);
                questState.set("enchant_weapon", String.valueOf(1), true);
                questState.setState(2);
                questState.playSound("ItemSound.quest_accept");
                switch (n) {
                    case 30115: {
                        string2 = "jurek_q0350_03.htm";
                        break;
                    }
                    case 30194: {
                        string2 = "guyder_q0350_03.htm";
                        break;
                    }
                    case 30856: {
                        string2 = "magister_winonin_q0350_03.htm";
                    }
                }
            } else if (string.equalsIgnoreCase("menu_select?ask=350&reply=1")) {
                switch (n) {
                    case 30115: {
                        string2 = "jurek_q0350_05.htm";
                        break;
                    }
                    case 30194: {
                        string2 = "guyder_q0350_05.htm";
                        break;
                    }
                    case 30856: {
                        string2 = "magister_winonin_q0350_05.htm";
                    }
                }
            } else if (string.equalsIgnoreCase("menu_select?ask=350&reply=2")) {
                switch (n) {
                    case 30115: {
                        string2 = "jurek_q0350_06.htm";
                        break;
                    }
                    case 30194: {
                        string2 = "guyder_q0350_06.htm";
                        break;
                    }
                    case 30856: {
                        string2 = "magister_winonin_q0350_06.htm";
                    }
                }
            } else if (string.equalsIgnoreCase("menu_select?ask=350&reply=3")) {
                switch (n) {
                    case 30115: {
                        string2 = "jurek_q0350_07.htm";
                        break;
                    }
                    case 30194: {
                        string2 = "guyder_q0350_07.htm";
                        break;
                    }
                    case 30856: {
                        string2 = "magister_winonin_q0350_07.htm";
                    }
                }
            } else if (string.equalsIgnoreCase("menu_select?ask=350&reply=4")) {
                questState.giveItems(4629, 1L);
                questState.set("enchant_weapon", String.valueOf(2), true);
                switch (n) {
                    case 30115: {
                        string2 = "jurek_q0350_08.htm";
                        break;
                    }
                    case 30194: {
                        string2 = "guyder_q0350_08.htm";
                        break;
                    }
                    case 30856: {
                        string2 = "magister_winonin_q0350_08.htm";
                    }
                }
            } else if (string.equalsIgnoreCase("menu_select?ask=350&reply=5")) {
                questState.giveItems(4640, 1L);
                questState.set("enchant_weapon", String.valueOf(2), true);
                switch (n) {
                    case 30115: {
                        string2 = "jurek_q0350_09.htm";
                        break;
                    }
                    case 30194: {
                        string2 = "guyder_q0350_09.htm";
                        break;
                    }
                    case 30856: {
                        string2 = "magister_winonin_q0350_09.htm";
                    }
                }
            } else if (string.equalsIgnoreCase("menu_select?ask=350&reply=6")) {
                questState.giveItems(4651, 1L);
                questState.set("enchant_weapon", String.valueOf(2), true);
                switch (n) {
                    case 30115: {
                        string2 = "jurek_q0350_10.htm";
                        break;
                    }
                    case 30194: {
                        string2 = "guyder_q0350_10.htm";
                        break;
                    }
                    case 30856: {
                        string2 = "magister_winonin_q0350_10.htm";
                    }
                }
            } else if (string.equalsIgnoreCase("menu_select?ask=350&reply=7")) {
                int[] nArray = bJ;
                int n2 = nArray.length;
                for (int i = 0; i < n2; ++i) {
                    Integer n3 = nArray[i];
                    questState.takeItems(n3.intValue(), 1L);
                }
                switch (n) {
                    case 30115: {
                        string2 = "jurek_q0350_14.htm";
                        break;
                    }
                    case 30194: {
                        string2 = "guyder_q0350_14.htm";
                        break;
                    }
                    case 30856: {
                        string2 = "magister_winonin_q0350_14.htm";
                    }
                }
                questState.unset("enchant_weapon");
                questState.exitCurrentQuest(true);
            } else if (string.equalsIgnoreCase("menu_select?ask=350&reply=8")) {
                switch (n) {
                    case 30115: {
                        string2 = "jurek_q0350_06a.htm";
                        break;
                    }
                    case 30194: {
                        string2 = "guyder_q0350_06a.htm";
                        break;
                    }
                    case 30856: {
                        string2 = "magister_winonin_q0350_06a.htm";
                    }
                }
            }
        }
        return string2;
    }

    /*
     * Unable to fully structure code
     */
    public String onTalk(NpcInstance var1_1, QuestState var2_2) {
        var3_3 = var1_1.getNpcId();
        var4_4 = var2_2.getState();
        var5_5 = var2_2.getInt("enchant_weapon");
        var6_6 = "no-quest";
        block0 : switch (var4_4) {
            case 1: {
                if (var3_3 != 30115 && var3_3 != 30194 && var3_3 != 30856) ** GOTO lbl30
                if (var2_2.getPlayer().getLevel() >= 40) ** GOTO lbl21
                switch (var3_3) {
                    case 30115: {
                        var6_6 = "jurek_q0350_01.htm";
                        break;
                    }
                    case 30194: {
                        var6_6 = "guyder_q0350_01.htm";
                        break;
                    }
                    case 30856: {
                        var6_6 = "magister_winonin_q0350_01.htm";
                    }
                }
                var2_2.exitCurrentQuest(true);
                ** GOTO lbl30
lbl21:
                // 1 sources

                switch (var3_3) {
                    case 30115: {
                        var6_6 = "jurek_q0350_02.htm";
                        break;
                    }
                    case 30194: {
                        var6_6 = "guyder_q0350_02.htm";
                        break;
                    }
                    case 30856: {
                        var6_6 = "magister_winonin_q0350_02.htm";
                    }
                }
            }
lbl30:
            // 7 sources

            case 2: {
                if (var3_3 != 30115 && var3_3 != 30194 && var3_3 != 30856) break;
                if (var5_5 == 1) {
                    switch (var3_3) {
                        case 30115: {
                            var6_6 = "jurek_q0350_03.htm";
                            break;
                        }
                        case 30194: {
                            var6_6 = "guyder_q0350_03.htm";
                            break;
                        }
                        case 30856: {
                            var6_6 = "magister_winonin_q0350_03.htm";
                        }
                    }
                    break;
                }
                if (var5_5 <= 1) break;
                if (var2_2.getQuestItemsCount(4661) > 0L || var2_2.getQuestItemsCount(5579) > 0L || var2_2.getQuestItemsCount(5582) > 0L || var2_2.getQuestItemsCount(5914) > 0L || var2_2.getQuestItemsCount(4639) > 0L || var2_2.getQuestItemsCount(5577) > 0L || var2_2.getQuestItemsCount(5580) > 0L || var2_2.getQuestItemsCount(5908) > 0L || var2_2.getQuestItemsCount(4650) > 0L || var2_2.getQuestItemsCount(5578) > 0L || var2_2.getQuestItemsCount(5581) > 0L || var2_2.getQuestItemsCount(5911) > 0L) {
                    switch (var3_3) {
                        case 30115: {
                            var6_6 = "jurek_q0350_11a.htm";
                            break;
                        }
                        case 30194: {
                            var6_6 = "guyder_q0350_11a.htm";
                            break;
                        }
                        case 30856: {
                            var6_6 = "magister_winonin_q0350_11a.htm";
                        }
                    }
                    break;
                }
                if (var2_2.getQuestItemsCount(4651) > 0L || var2_2.getQuestItemsCount(4652) > 0L || var2_2.getQuestItemsCount(4653) > 0L || var2_2.getQuestItemsCount(654) > 0L || var2_2.getQuestItemsCount(4655) > 0L || var2_2.getQuestItemsCount(4656) > 0L || var2_2.getQuestItemsCount(4657) > 0L || var2_2.getQuestItemsCount(4658) > 0L || var2_2.getQuestItemsCount(4659) > 0L || var2_2.getQuestItemsCount(4660) > 0L || var2_2.getQuestItemsCount(4629) > 0L || var2_2.getQuestItemsCount(4630) > 0L || var2_2.getQuestItemsCount(4631) > 0L || var2_2.getQuestItemsCount(4632) > 0L || var2_2.getQuestItemsCount(4633) > 0L || var2_2.getQuestItemsCount(4634) > 0L || var2_2.getQuestItemsCount(4635) > 0L || var2_2.getQuestItemsCount(4636) > 0L || var2_2.getQuestItemsCount(4637) > 0L || var2_2.getQuestItemsCount(4638) > 0L || var2_2.getQuestItemsCount(4640) > 0L || var2_2.getQuestItemsCount(4641) > 0L || var2_2.getQuestItemsCount(4642) > 0L || var2_2.getQuestItemsCount(4643) > 0L || var2_2.getQuestItemsCount(4644) > 0L || var2_2.getQuestItemsCount(4645) > 0L || var2_2.getQuestItemsCount(4646) > 0L || var2_2.getQuestItemsCount(4647) > 0L || var2_2.getQuestItemsCount(4648) > 0L || var2_2.getQuestItemsCount(4649) > 0L) {
                    switch (var3_3) {
                        case 30115: {
                            var6_6 = "jurek_q0350_11.htm";
                            break;
                        }
                        case 30194: {
                            var6_6 = "guyder_q0350_11.htm";
                            break;
                        }
                        case 30856: {
                            var6_6 = "magister_winonin_q0350_11.htm";
                        }
                    }
                    break;
                }
                var2_2.takeItems(4662, 1L);
                var2_2.takeItems(4663, 1L);
                var2_2.takeItems(4664, 1L);
                switch (var3_3) {
                    case 30115: {
                        var6_6 = "jurek_q0350_13.htm";
                        break block0;
                    }
                    case 30194: {
                        var6_6 = "guyder_q0350_13.htm";
                        break block0;
                    }
                    case 30856: {
                        var6_6 = "magister_winonin_q0350_13.htm";
                    }
                }
            }
        }
        return var6_6;
    }

    public String onKill(NpcInstance npcInstance, QuestState questState) {
        ArrayList<PlayerResult> arrayList;
        Player player = questState.getPlayer();
        if (player == null || !npcInstance.isMonster()) {
            return null;
        }
        if (player.getParty() == null) {
            arrayList = new ArrayList<PlayerResult>(1);
            arrayList.add(new PlayerResult(player));
        } else {
            arrayList = new ArrayList(player.getParty().getMemberCount());
            arrayList.add(new PlayerResult(player));
            for (Player object : player.getParty().getPartyMembers()) {
                if (object == player || !object.isInRange(npcInstance.getLoc(), (long)Config.ALT_PARTY_DISTRIBUTION_RANGE)) continue;
                arrayList.add(new PlayerResult(object));
            }
        }
        for (AbsorbInfo absorbInfo : npcInstance.getTemplate().getAbsorbInfo()) {
            this.a(arrayList, (MonsterInstance)npcInstance, absorbInfo);
        }
        for (PlayerResult playerResult : arrayList) {
            playerResult.send();
        }
        return null;
    }

    private void a(List<PlayerResult> list, MonsterInstance monsterInstance, AbsorbInfo absorbInfo) {
        List<PlayerResult> list2;
        int n = 0;
        switch (absorbInfo.getAbsorbType()) {
            case LAST_HIT: {
                list2 = Collections.singletonList(list.get(0));
                break;
            }
            case PARTY_ALL: {
                list2 = list;
                break;
            }
            case PARTY_RANDOM: {
                n = list.size();
                if (n == 1) {
                    list2 = Collections.singletonList(list.get(0));
                    break;
                }
                int n2 = Rnd.get((int)n);
                list2 = new ArrayList<PlayerResult>(n2);
                ArrayList<PlayerResult> object = new ArrayList<PlayerResult>(list);
                Collections.shuffle(object);
                for (int i = 0; i <= n2; ++i) {
                    list2.add((PlayerResult)object.get(i));
                }
                break;
            }
            case PARTY_ONE: {
                n = list.size();
                if (n == 1) {
                    list2 = Collections.singletonList(list.get(0));
                    break;
                }
                int n2 = Rnd.get((int)n);
                list2 = Collections.singletonList(list.get(n2));
                break;
            }
            default: {
                return;
            }
        }
        for (PlayerResult playerResult : list2) {
            ItemInstance[] itemInstanceArray;
            if (playerResult == null || playerResult.getMessage() == SystemMsg.THE_SOUL_CRYSTAL_SUCCEEDED_IN_ABSORBING_A_SOUL) continue;
            Player player = playerResult.getPlayer();
            if (absorbInfo.isSkill() && !monsterInstance.isAbsorbed(player) || player.getQuestState(_350_EnhanceYourWeapon.class) == null) continue;
            boolean bl = false;
            SoulCrystal soulCrystal = null;
            for (ItemInstance itemInstance : itemInstanceArray = player.getInventory().getItems()) {
                SoulCrystal soulCrystal2 = SoulCrystalHolder.getInstance().getCrystal(itemInstance.getItemId());
                if (soulCrystal2 == null) continue;
                playerResult.setMessage(SystemMsg.THE_SOUL_CRYSTAL_WAS_NOT_ABLE_TO_ABSORB_THE_SOUL);
                if (soulCrystal != null) {
                    playerResult.setMessage(SystemMsg.THE_SOUL_CRYSTAL_CAUSED_RESONATION_AND_FAILED_AT_ABSORBING_A_SOUL);
                    bl = true;
                    break;
                }
                soulCrystal = soulCrystal2;
            }
            if (bl || soulCrystal == null) continue;
            if (!absorbInfo.canAbsorb(soulCrystal.getLevel() + 1)) {
                playerResult.setMessage(SystemMsg.THE_SOUL_CRYSTAL_IS_REFUSING_TO_ABSORB_THE_SOUL);
                continue;
            }
            int n2 = 0;
            if (absorbInfo.getCursedChance() > 0 && soulCrystal.getCursedNextItemId() > 0) {
                int n3 = n2 = Rnd.chance((int)absorbInfo.getCursedChance()) ? soulCrystal.getCursedNextItemId() : 0;
            }
            if (n2 == 0) {
                int n4 = n2 = Rnd.chance((int)absorbInfo.getChance()) ? soulCrystal.getNextItemId() : 0;
            }
            if (n2 == 0) {
                playerResult.setMessage(SystemMsg.THE_SOUL_CRYSTAL_WAS_NOT_ABLE_TO_ABSORB_THE_SOUL);
                continue;
            }
            if (player.consumeItem(soulCrystal.getItemId(), 1L)) {
                player.getInventory().addItem(n2, 1L);
                player.sendPacket((IStaticPacket)SystemMessage.obtainItems((int)n2, (long)1L, (int)0));
                playerResult.setMessage(SystemMsg.THE_SOUL_CRYSTAL_SUCCEEDED_IN_ABSORBING_A_SOUL);
                continue;
            }
            playerResult.setMessage(SystemMsg.THE_SOUL_CRYSTAL_WAS_NOT_ABLE_TO_ABSORB_THE_SOUL);
        }
    }

    private static class PlayerResult {
        private final Player y;
        private SystemMsg _message;

        public PlayerResult(Player player) {
            this.y = player;
        }

        public Player getPlayer() {
            return this.y;
        }

        public SystemMsg getMessage() {
            return this._message;
        }

        public void setMessage(SystemMsg systemMsg) {
            this._message = systemMsg;
        }

        public void send() {
            if (this._message != null) {
                this.y.sendPacket((IStaticPacket)this._message);
            }
        }
    }
}
