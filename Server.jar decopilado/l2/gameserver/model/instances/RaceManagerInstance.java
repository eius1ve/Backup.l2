/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.instances;

import java.util.List;
import java.util.Locale;
import l2.gameserver.Config;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.MonsterRace;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.templates.npc.NpcTemplate;
import l2.gameserver.utils.ItemFunctions;

public class RaceManagerInstance
extends NpcInstance {
    public RaceManagerInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    @Override
    public void onBypassFeedback(Player player, String string) {
        if (string.startsWith("BuyTicket")) {
            if (!Config.SERVICES_ALLOW_LOTTERY || MonsterRace.getInstance().getCurrentRaceState() != MonsterRace.RaceState.ACCEPTING_BETS) {
                player.sendPacket((IStaticPacket)SystemMsg.MONSTER_RACE_TICKETS_ARE_NO_LONGER_AVAILABLE);
                super.onBypassFeedback(player, "Chat 0");
                return;
            }
            int n = Integer.parseInt(string.substring(10));
            if (n == 0) {
                player.setRace(0, 0);
                player.setRace(1, 0);
            }
            if (n == 10 && player.getRace(0) == 0 || n == 20 && player.getRace(0) == 0 && player.getRace(1) == 0) {
                n = 0;
            }
            NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(this.getObjectId());
            if (n < 10) {
                Object object;
                npcHtmlMessage.setFile(this.getHtmlPath(this.getNpcId(), 2, player));
                for (int i = 0; i < 8; ++i) {
                    int n2 = i + 1;
                    object = "Mob" + n2;
                    npcHtmlMessage.replace((String)object, MonsterRace.getInstance().getMonsters()[i].getTemplate().getName());
                }
                object = "No1";
                if (n == 0) {
                    npcHtmlMessage.replace((String)object, "");
                } else {
                    npcHtmlMessage.replace((String)object, String.valueOf(n));
                    player.setRace(0, n);
                }
            } else if (n < 20) {
                if (player.getRace(0) == 0) {
                    return;
                }
                npcHtmlMessage.setFile(this.getHtmlPath(this.getNpcId(), 3, player));
                npcHtmlMessage.replace("0place", String.valueOf(player.getRace(0)));
                String string2 = "Mob1";
                String string3 = MonsterRace.getInstance().getMonsters()[player.getRace(0) - 1].getTemplate().getName();
                npcHtmlMessage.replace(string2, string3);
                string2 = "0adena";
                if (n == 10) {
                    npcHtmlMessage.replace(string2, "");
                } else {
                    npcHtmlMessage.replace(string2, String.valueOf(Config.SERVICE_MONSTER_RACE_BID[n - 11]));
                    player.setRace(1, n - 10);
                }
            } else if (n == 20) {
                if (player.getRace(0) == 0 || player.getRace(1) == 0) {
                    return;
                }
                npcHtmlMessage.setFile(this.getHtmlPath(this.getNpcId(), 4, player));
                npcHtmlMessage.replace("0place", String.valueOf(player.getRace(0)));
                String string4 = "Mob1";
                String string5 = MonsterRace.getInstance().getMonsters()[player.getRace(0) - 1].getTemplate().getName();
                npcHtmlMessage.replace(string4, string5);
                string4 = "0adena";
                int n3 = Config.SERVICE_MONSTER_RACE_BID[player.getRace(1) - 1];
                npcHtmlMessage.replace(string4, String.valueOf(n3));
                string4 = "0tax";
                int n4 = 0;
                npcHtmlMessage.replace(string4, String.valueOf(n4));
                string4 = "0total";
                int n5 = n3 + n4;
                npcHtmlMessage.replace(string4, String.valueOf(n5));
            } else {
                if (player.getRace(0) == 0 || player.getRace(1) == 0) {
                    return;
                }
                int n6 = player.getRace(0);
                int n7 = player.getRace(1);
                if (ItemFunctions.getItemCount(player, Config.SERVICE_MONSTER_RACE_CURRENCY) < (long)Config.SERVICE_MONSTER_RACE_BID[n7 - 1]) {
                    if (Config.SERVICE_MONSTER_RACE_CURRENCY == 57) {
                        player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
                    } else {
                        player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_ITEM_COUNT);
                    }
                    return;
                }
                ItemFunctions.removeItem(player, Config.SERVICE_MONSTER_RACE_CURRENCY, Config.SERVICE_MONSTER_RACE_BID[n7 - 1], true);
                player.setRace(0, 0);
                player.setRace(1, 0);
                ItemInstance itemInstance = ItemFunctions.createItem(Config.SERVICE_MONSTER_RACE_TICKET_ID);
                itemInstance.setCount(1L);
                itemInstance.setEnchantLevel(MonsterRace.getInstance().getRaceNumber());
                itemInstance.setBlessed(n6);
                itemInstance.setDamaged(Config.SERVICE_MONSTER_RACE_BID[n7 - 1] / 100);
                player.getInventory().addItem(itemInstance);
                SystemMessage systemMessage = new SystemMessage(SystemMsg.ACQUIRED_S1_S2);
                systemMessage.addNumber(MonsterRace.getInstance().getRaceNumber());
                systemMessage.addItemName(Config.SERVICE_MONSTER_RACE_TICKET_ID);
                player.sendPacket((IStaticPacket)systemMessage);
                MonsterRace.getInstance().setBetOnLane(n6, Config.SERVICE_MONSTER_RACE_BID[n7 - 1], true);
                super.onBypassFeedback(player, "Chat 0");
                return;
            }
            npcHtmlMessage.replace("1race", String.valueOf(MonsterRace.getInstance().getRaceNumber()));
            npcHtmlMessage.replace("%objectId%", String.valueOf(this.getObjectId()));
            player.sendPacket((IStaticPacket)npcHtmlMessage);
            player.sendActionFailed();
        } else if (string.equals("ShowOdds")) {
            if (!Config.SERVICES_ALLOW_LOTTERY || MonsterRace.getInstance().getCurrentRaceState() == MonsterRace.RaceState.ACCEPTING_BETS) {
                player.sendPacket((IStaticPacket)SystemMsg.MONSTER_RACE_PAYOUT_INFORMATION_IS_NOT_AVAILABLE_WHILE_TICKETS_ARE_BEING_SOLD);
                super.onBypassFeedback(player, "Chat 0");
                return;
            }
            NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(this.getObjectId());
            npcHtmlMessage.setFile(this.getHtmlPath(this.getNpcId(), 5, player));
            for (int i = 0; i < 8; ++i) {
                int n = i + 1;
                npcHtmlMessage.replace("Mob" + n, MonsterRace.getInstance().getMonsters()[i].getTemplate().getName());
                double d = MonsterRace.getInstance().getOdds().get(i);
                npcHtmlMessage.replace("Odd" + n, d > 0.0 ? String.format(Locale.ENGLISH, "%.1f", d) : "&$804;");
            }
            npcHtmlMessage.replace("1race", String.valueOf(MonsterRace.getInstance().getRaceNumber()));
            npcHtmlMessage.replace("%objectId%", String.valueOf(this.getObjectId()));
            player.sendPacket((IStaticPacket)npcHtmlMessage);
            player.sendActionFailed();
        } else if (string.equals("ShowInfo")) {
            if (!Config.SERVICES_ALLOW_LOTTERY) {
                return;
            }
            NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(this.getObjectId());
            npcHtmlMessage.setFile(this.getHtmlPath(this.getNpcId(), 6, player));
            for (int i = 0; i < 8; ++i) {
                int n = i + 1;
                String string6 = "Mob" + n;
                npcHtmlMessage.replace(string6, MonsterRace.getInstance().getMonsters()[i].getTemplate().getName());
            }
            npcHtmlMessage.replace("%objectId%", String.valueOf(this.getObjectId()));
            player.sendPacket((IStaticPacket)npcHtmlMessage);
            player.sendActionFailed();
        } else if (string.equals("ShowTickets")) {
            if (!Config.SERVICES_ALLOW_LOTTERY) {
                super.onBypassFeedback(player, "Chat 0");
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            for (ItemInstance itemInstance : player.getInventory().getItemsByItemId(Config.SERVICE_MONSTER_RACE_TICKET_ID)) {
                if (itemInstance.getEnchantLevel() == MonsterRace.getInstance().getRaceNumber()) continue;
                stringBuilder.append("<tr><td><a action=\"bypass -h npc_%objectId%_ShowTicket ").append(itemInstance.getObjectId()).append("\">").append(itemInstance.getEnchantLevel()).append(" Race Number</a></td><td align=right><font color=\"LEVEL\">").append(itemInstance.getBlessed()).append("</font> Number</td><td align=right><font color=\"LEVEL\">").append(itemInstance.getDamaged() * 100).append("</font> Adena</td></tr>");
            }
            NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(this.getObjectId());
            npcHtmlMessage.setFile(this.getHtmlPath(this.getNpcId(), 7, player));
            npcHtmlMessage.replace("%tickets%", stringBuilder.toString());
            npcHtmlMessage.replace("%objectId%", String.valueOf(this.getObjectId()));
            player.sendPacket((IStaticPacket)npcHtmlMessage);
            player.sendActionFailed();
        } else if (string.startsWith("ShowTicket")) {
            int n = Integer.parseInt(string.substring(11));
            if (!Config.SERVICES_ALLOW_LOTTERY || n == 0) {
                super.onBypassFeedback(player, "Chat 0");
                return;
            }
            ItemInstance itemInstance = player.getInventory().getItemByObjectId(n);
            if (itemInstance == null) {
                super.onBypassFeedback(player, "Chat 0");
                return;
            }
            int n8 = itemInstance.getEnchantLevel();
            int n9 = itemInstance.getBlessed();
            int n10 = itemInstance.getDamaged() * 100;
            MonsterRace.HistoryInfo historyInfo = MonsterRace.getInstance().getHistory().get(n8 - 1);
            if (historyInfo == null) {
                super.onBypassFeedback(player, "Chat 0");
                return;
            }
            NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(this.getObjectId());
            npcHtmlMessage.setFile(this.getHtmlPath(this.getNpcId(), 8, player));
            npcHtmlMessage.replace("%raceId%", String.valueOf(n8));
            npcHtmlMessage.replace("%lane%", String.valueOf(n9));
            npcHtmlMessage.replace("%bet%", String.valueOf(n10));
            npcHtmlMessage.replace("%firstLane%", String.valueOf(historyInfo.getFirst()));
            npcHtmlMessage.replace("%odd%", n9 == historyInfo.getFirst() ? String.format(Locale.ENGLISH, "%.2f", historyInfo.getOddRate()) : "0.01");
            npcHtmlMessage.replace("%objectId%", String.valueOf(this.getObjectId()));
            npcHtmlMessage.replace("%ticketObjectId%", String.valueOf(n));
            player.sendPacket((IStaticPacket)npcHtmlMessage);
            player.sendActionFailed();
        } else if (string.startsWith("CalculateWin")) {
            int n = Integer.parseInt(string.substring(13));
            if (!Config.SERVICES_ALLOW_LOTTERY || n == 0) {
                super.onBypassFeedback(player, "Chat 0");
                return;
            }
            ItemInstance itemInstance = player.getInventory().getItemByObjectId(n);
            if (itemInstance == null) {
                super.onBypassFeedback(player, "Chat 0");
                return;
            }
            int n11 = itemInstance.getEnchantLevel();
            int n12 = itemInstance.getBlessed();
            int n13 = itemInstance.getDamaged() * 100;
            MonsterRace.HistoryInfo historyInfo = MonsterRace.getInstance().getHistory().get(n11 - 1);
            if (historyInfo == null) {
                super.onBypassFeedback(player, "Chat 0");
                return;
            }
            if (player.getInventory().destroyItem(itemInstance)) {
                ItemFunctions.addItem((Playable)player, Config.SERVICE_MONSTER_RACE_CURRENCY, (long)((int)((double)n13 * (n12 == historyInfo.getFirst() ? historyInfo.getOddRate() : 0.01))), true);
            }
            super.onBypassFeedback(player, "Chat 0");
        } else if (string.equals("ViewHistory")) {
            if (!Config.SERVICES_ALLOW_LOTTERY) {
                super.onBypassFeedback(player, "Chat 0");
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            List<MonsterRace.HistoryInfo> list = MonsterRace.getInstance().getHistory();
            for (int i = list.size() - 1; i >= Math.max(0, list.size() - 7); --i) {
                MonsterRace.HistoryInfo historyInfo = list.get(i);
                stringBuilder.append("<tr><td><font color=\"LEVEL\">").append(historyInfo.getRaceId()).append("</font> th</td><td><font color=\"LEVEL\">").append(historyInfo.getFirst()).append("</font> Lane </td><td><font color=\"LEVEL\">").append(historyInfo.getSecond()).append("</font> Lane</td><td align=right><font color=00ffff>").append(String.format(Locale.ENGLISH, "%.2f", historyInfo.getOddRate())).append("</font> Times</td></tr>");
            }
            NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(this.getObjectId());
            npcHtmlMessage.setFile(this.getHtmlPath(this.getNpcId(), 9, player));
            npcHtmlMessage.replace("%infos%", stringBuilder.toString());
            npcHtmlMessage.replace("%objectId%", String.valueOf(this.getObjectId()));
            player.sendPacket((IStaticPacket)npcHtmlMessage);
            player.sendActionFailed();
        } else {
            super.onBypassFeedback(player, string);
        }
    }
}
