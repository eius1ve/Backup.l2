/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

import l2.gameserver.data.xml.holder.BuyListHolder;
import l2.gameserver.handler.admincommands.IAdminCommandHandler;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExBuySellList;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.utils.GameStats;

public class AdminShop
implements IAdminCommandHandler {
    @Override
    public boolean useAdminCommand(Enum enum_, String[] stringArray, String string, Player player) {
        Commands commands = (Commands)enum_;
        if (!player.getPlayerAccess().UseGMShop) {
            return false;
        }
        switch (commands) {
            case admin_buy: {
                try {
                    this.i(player, string.substring(10));
                }
                catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                    player.sendMessage("Please specify buylist.");
                }
                break;
            }
            case admin_gmshop: {
                player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("admin/gmshops.htm"));
                break;
            }
            case admin_tax: {
                player.sendMessage("TaxSum: " + GameStats.getTaxSum());
                break;
            }
            case admin_taxclear: {
                GameStats.addTax(-GameStats.getTaxSum());
                player.sendMessage("TaxSum: " + GameStats.getTaxSum());
            }
        }
        return true;
    }

    @Override
    public Enum[] getAdminCommandEnum() {
        return Commands.values();
    }

    private void i(Player player, String string) {
        int n = -1;
        try {
            n = Integer.parseInt(string);
        }
        catch (Exception exception) {
            // empty catch block
        }
        BuyListHolder.NpcTradeList npcTradeList = BuyListHolder.getInstance().getBuyList(n);
        if (npcTradeList != null) {
            player.sendPacket(new ExBuySellList.BuyList(npcTradeList, player, 0.0), new ExBuySellList.SellRefundList(player, false));
        }
        player.sendActionFailed();
    }

    private static final class Commands
    extends Enum<Commands> {
        public static final /* enum */ Commands admin_buy = new Commands();
        public static final /* enum */ Commands admin_gmshop = new Commands();
        public static final /* enum */ Commands admin_tax = new Commands();
        public static final /* enum */ Commands admin_taxclear = new Commands();
        private static final /* synthetic */ Commands[] a;

        public static Commands[] values() {
            return (Commands[])a.clone();
        }

        public static Commands valueOf(String string) {
            return Enum.valueOf(Commands.class, string);
        }

        private static /* synthetic */ Commands[] a() {
            return new Commands[]{admin_buy, admin_gmshop, admin_tax, admin_taxclear};
        }

        static {
            a = Commands.a();
        }
    }
}
