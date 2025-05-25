/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.listener.Listener
 *  l2.gameserver.Announcements
 *  l2.gameserver.Config
 *  l2.gameserver.data.xml.holder.ItemHolder
 *  l2.gameserver.handler.voicecommands.IVoicedCommandHandler
 *  l2.gameserver.handler.voicecommands.VoicedCommandHandler
 *  l2.gameserver.listener.actor.OnKillListener
 *  l2.gameserver.listener.actor.player.OnPlayerEnterListener
 *  l2.gameserver.listener.actor.player.OnSetActiveSubClassListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.actor.listener.PlayerListenerList
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.NpcHtmlMessage
 *  l2.gameserver.network.l2.s2c.UserInfoType
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.tables.SkillTable
 *  l2.gameserver.utils.ItemFunctions
 */
package services;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import l2.commons.listener.Listener;
import l2.gameserver.Announcements;
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.handler.voicecommands.IVoicedCommandHandler;
import l2.gameserver.handler.voicecommands.VoicedCommandHandler;
import l2.gameserver.listener.actor.OnKillListener;
import l2.gameserver.listener.actor.player.OnPlayerEnterListener;
import l2.gameserver.listener.actor.player.OnSetActiveSubClassListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.actor.listener.PlayerListenerList;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.network.l2.s2c.UserInfoType;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.utils.ItemFunctions;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PvpBonusSkills
extends Functions
implements IVoicedCommandHandler,
OnKillListener,
OnPlayerEnterListener,
OnSetActiveSubClassListener,
ScriptFile {
    private static final List<Rank> dU = new ArrayList<Rank>();
    private final String[] bb = new String[]{"pvprank"};

    public void onKill(Creature creature, Creature creature2) {
        if (creature.isPlayer() && creature2.isPlayer()) {
            Player player = creature.getPlayer();
            Rank rank2 = this.a(player.getPvpKills());
            Rank rank3 = this.a(player.getPvpKills() - 1);
            if (rank2 != null && rank2.isEligible(player) && (rank3 == null || rank2.getRankId() > rank3.getRankId())) {
                Optional<Rank> optional = this.a(player);
                optional.ifPresent(rank -> this.a(player, (Rank)rank));
            }
        }
    }

    private void a(Player player, Rank rank) {
        if (rank.isEligible(player)) {
            if (rank.getAnnounceMsg() != null) {
                Announcements.getInstance().announceByCustomMessage(rank.getAnnounceMsg(), new String[]{player.getName(), String.valueOf(rank.getRankId())});
            }
            rank.rewardPlayer(player);
        }
    }

    public boolean ignorePetOrSummon() {
        return false;
    }

    public void onPlayerEnter(Player player) {
        this.a(player);
    }

    private Optional<Rank> a(Player player) {
        Rank rank = this.a(player.getPvpKills());
        if (rank != null && rank.isEligible(player)) {
            boolean bl = false;
            for (Skill skill : rank.getSkills()) {
                if (player.getSkillLevel(Integer.valueOf(skill.getId())) == skill.getLevel()) continue;
                player.addSkill(skill, false);
                bl = true;
            }
            if (bl) {
                player.sendSkillList();
                player.broadcastUserInfo(false, new UserInfoType[0]);
                return Optional.of(rank);
            }
        }
        return Optional.empty();
    }

    private Rank a(int n) {
        return dU.stream().filter(rank -> n >= rank.getMinPvp()).max(Comparator.comparingInt(Rank::getMinPvp)).orElse(null);
    }

    public boolean useVoicedCommand(String string, Player player, String string2) {
        if (!Config.SERVICES_PVP_RANKING_BONUS_COMMAND) {
            return false;
        }
        this.showPlayerRankInfo(player);
        return true;
    }

    public String[] getVoicedCommandList() {
        return this.bb;
    }

    public void onSetActiveSub(Player player, int n) {
        this.a(player);
    }

    public void onReload() {
        if (Config.SERVICES_PVP_RANKING_BONUS) {
            this.cn();
        }
    }

    public void onShutdown() {
        PlayerListenerList.removeGlobal((Listener)this);
    }

    public void onLoad() {
        if (Config.SERVICES_PVP_RANKING_BONUS) {
            this.cn();
            PlayerListenerList.addGlobal((Listener)this);
            if (Config.SERVICES_PVP_RANKING_BONUS_COMMAND) {
                VoicedCommandHandler.getInstance().registerVoicedCommandHandler((IVoicedCommandHandler)this);
            }
        }
    }

    private void cn() {
        try {
            File file = new File("data/pvp_bonus_ranks.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            NodeList nodeList = document.getElementsByTagName("rank");
            for (int i = 0; i < nodeList.getLength(); ++i) {
                Node node = nodeList.item(i);
                if (node.getNodeType() != 1) continue;
                Element element = (Element)node;
                int n = Integer.parseInt(element.getAttribute("rankId"));
                int n2 = Integer.parseInt(element.getAttribute("minPvp"));
                int n3 = Integer.parseInt(element.getAttribute("minLevel"));
                int n4 = Integer.parseInt(element.getAttribute("maxLevel"));
                int n5 = element.hasAttribute("rewardItemId") ? Integer.parseInt(element.getAttribute("rewardItemId")) : 0;
                long l = element.hasAttribute("rewardItemCount") ? Long.parseLong(element.getAttribute("rewardItemCount")) : 0L;
                String string = element.hasAttribute("announceCustomMsg") ? element.getAttribute("announceCustomMsg") : null;
                Rank rank = new Rank(n, n2, n3, n4, n5, l, string);
                NodeList nodeList2 = element.getElementsByTagName("skill");
                for (int j = 0; j < nodeList2.getLength(); ++j) {
                    Node node2 = nodeList2.item(j);
                    if (node2.getNodeType() != 1) continue;
                    Element element2 = (Element)node2;
                    int n6 = Integer.parseInt(element2.getAttribute("skillId"));
                    int n7 = Integer.parseInt(element2.getAttribute("skillLevel"));
                    rank.addSkill(n6, n7);
                }
                dU.add(rank);
            }
            dU.sort(Comparator.comparingInt(Rank::getMinPvp));
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void showPlayerRankInfo(Player player) {
        int n = player.getPvpKills();
        Rank rank2 = this.a(n);
        Rank rank3 = dU.stream().filter(rank -> rank.getMinPvp() > n).min(Comparator.comparingInt(Rank::getMinPvp)).orElse(null);
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(5).setFile("scripts/services/pvp_rank_info.htm");
        if (rank2 != null) {
            npcHtmlMessage.replace("%current_rank%", String.valueOf(rank2.getRankId()));
            npcHtmlMessage.replace("%current_value%", String.valueOf(n));
        } else {
            npcHtmlMessage.replace("%current_rank%", "No rank yet");
            npcHtmlMessage.replace("%current_value%", String.valueOf(n));
        }
        if (rank3 != null) {
            Skill skill;
            int n2 = rank3.getMinPvp() - n;
            npcHtmlMessage.replace("%next_value%", String.valueOf(rank3.getRankId()));
            npcHtmlMessage.replace("%diff_value%", String.valueOf(n2));
            if (!rank3.getSkills().isEmpty()) {
                skill = rank3.getSkills().get(0);
                npcHtmlMessage.replace("%next_skill%", skill.getName());
                npcHtmlMessage.replace("%next_skill_level%", String.valueOf(skill.getLevel()));
                npcHtmlMessage.replace("%next_skill_icon%", String.valueOf(skill.getIcon()));
            } else {
                npcHtmlMessage.replace("%next_skill%", "None");
            }
            if (rank3.getRewardItemId() > 0 && rank3.getRewardItemCount() > 0L) {
                skill = ItemHolder.getInstance().getTemplate(rank3.getRewardItemId());
                assert (skill != null);
                npcHtmlMessage.replace("%next_item%", skill.getName());
                npcHtmlMessage.replace("%next_item_count%", String.valueOf(rank3.getRewardItemCount()));
                npcHtmlMessage.replace("%next_item_icon%", skill.getIcon());
            } else {
                npcHtmlMessage.replace("%next_item%", "None");
                npcHtmlMessage.replace("%next_item_count%", "None");
                npcHtmlMessage.replace("%next_item_icon%", "None");
            }
        } else {
            npcHtmlMessage.replace("%next_value%", "Max rank");
            npcHtmlMessage.replace("%diff_value%", "0");
            npcHtmlMessage.replace("%next_skill%", "None");
            npcHtmlMessage.replace("%next_skill_level%", "None");
            npcHtmlMessage.replace("%next_skill_icon%", "None");
            npcHtmlMessage.replace("%next_item%", "None");
            npcHtmlMessage.replace("%next_item_count%", "None");
            npcHtmlMessage.replace("%next_item_icon%", "None");
        }
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    public static class Rank {
        private final int bFP;
        private final int bFQ;
        private final List<Skill> dV = new ArrayList<Skill>();
        private final int bFR;
        private final int bFS;
        private final int bFT;
        private final long ey;
        private final String hH;

        public Rank(int n, int n2, int n3, int n4, int n5, long l, String string) {
            this.bFP = n;
            this.bFQ = n2;
            this.bFR = n3;
            this.bFS = n4;
            this.bFT = n5;
            this.ey = l;
            this.hH = string;
        }

        public void addSkill(int n, int n2) {
            Skill skill = SkillTable.getInstance().getInfo(n, n2);
            if (skill != null) {
                this.dV.add(skill);
            }
        }

        public int getRankId() {
            return this.bFP;
        }

        public int getMinPvp() {
            return this.bFQ;
        }

        public List<Skill> getSkills() {
            return this.dV;
        }

        public boolean isEligible(Player player) {
            return player.getLevel() >= this.bFR && player.getLevel() <= this.bFS;
        }

        public void rewardPlayer(Player player) {
            if (this.bFT > 0 && this.ey > 0L) {
                ItemFunctions.addItem((Playable)player, (int)this.bFT, (long)this.ey, (boolean)true);
            }
        }

        public String getAnnounceMsg() {
            return this.hH;
        }

        public int getRewardItemId() {
            return this.bFT;
        }

        public long getRewardItemCount() {
            return this.ey;
        }
    }
}
