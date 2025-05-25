/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.handler.admincommands.IAdminCommandHandler;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.utils.AdminFunctions;
import l2.gameserver.utils.ItemFunctions;
import l2.gameserver.utils.Util;

public class AdminNochannel
implements IAdminCommandHandler {
    @Override
    public boolean useAdminCommand(Enum enum_, String[] stringArray, String string, Player player) {
        String string2;
        Commands commands = (Commands)enum_;
        if (!player.getPlayerAccess().CanBanChat) {
            return false;
        }
        int n = 0;
        int n2 = 0;
        int n3 = player.getPlayerAccess().BanChatCountPerDay;
        if (n3 > 0) {
            String string3 = player.getVar("banChatCount");
            if (string3 != null) {
                n = Integer.parseInt(string3);
            }
            if ((string2 = player.getVar("penaltyChatCount")) != null) {
                n2 = Integer.parseInt(string2);
            }
            long l = 0L;
            String string4 = player.getVar("LastBanChatDayTime");
            if (string4 != null) {
                l = Long.parseLong(string4);
            }
            if (l != 0L) {
                if (System.currentTimeMillis() - l < 86400000L) {
                    if (n >= n3) {
                        player.sendMessage(new CustomMessage("admincommandhandlers.banChatPerDayLimit", player, new Object[0]).addNumber(n));
                        return false;
                    }
                } else {
                    int n4 = n / 10;
                    n4 = Math.max(1, n4);
                    n4 = 1;
                    if (player.getPlayerAccess().BanChatBonusId > 0 && player.getPlayerAccess().BanChatBonusCount > 0) {
                        int n5 = player.getPlayerAccess().BanChatBonusCount * n4;
                        ItemTemplate itemTemplate = ItemHolder.getInstance().getTemplate(player.getPlayerAccess().BanChatBonusId);
                        player.sendMessage(new CustomMessage("admincommandhandlers.moderatorBonus", player, new Object[0]).addItemName(itemTemplate).addNumber(n5));
                        if (n2 > 0) {
                            player.sendMessage(new CustomMessage("admincommandhandlers.fineForViolations", player, new Object[0]).addNumber(n2).addItemName(itemTemplate));
                            player.setVar("penaltyChatCount", "" + Math.max(0, n2 - n5), -1L);
                            n5 -= n2;
                        }
                        if (n5 > 0) {
                            ItemFunctions.addItem((Playable)player, player.getPlayerAccess().BanChatBonusId, (long)n5, true);
                        }
                    }
                    player.setVar("LastBanChatDayTime", "" + System.currentTimeMillis(), -1L);
                    player.setVar("banChatCount", "0", -1L);
                    n = 0;
                }
            } else {
                player.setVar("LastBanChatDayTime", "" + System.currentTimeMillis(), -1L);
            }
        }
        switch (commands) {
            case admin_nochannel: 
            case admin_nc: {
                if (stringArray.length < 2) {
                    player.sendMessage("USAGE: //nochannel charName [period] [reason]");
                    return false;
                }
                int n6 = 30;
                if (stringArray.length > 2) {
                    try {
                        n6 = Integer.parseInt(stringArray[2]);
                    }
                    catch (Exception exception) {
                        n6 = 30;
                    }
                }
                string2 = AdminFunctions.banChat(player, null, stringArray[1], n6, stringArray.length > 3 ? Util.joinStrings(" ", stringArray, 3) : null);
                player.sendMessage(string2);
                if (n3 <= -1 || !string2.startsWith("You have banned the chat")) break;
                player.setVar("banChatCount", "" + ++n, -1L);
                player.sendMessage(new CustomMessage("admincommandhandlers.banChatCount", player, new Object[0]).addNumber(n3 - n));
            }
        }
        return true;
    }

    @Override
    public Enum[] getAdminCommandEnum() {
        return Commands.values();
    }

    private static final class Commands
    extends Enum<Commands> {
        public static final /* enum */ Commands admin_nochannel = new Commands();
        public static final /* enum */ Commands admin_nc = new Commands();
        private static final /* synthetic */ Commands[] a;

        public static Commands[] values() {
            return (Commands[])a.clone();
        }

        public static Commands valueOf(String string) {
            return Enum.valueOf(Commands.class, string);
        }

        private static /* synthetic */ Commands[] a() {
            return new Commands[]{admin_nochannel, admin_nc};
        }

        static {
            a = Commands.a();
        }
    }
}
