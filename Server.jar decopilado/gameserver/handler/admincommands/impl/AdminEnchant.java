/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

import l2.gameserver.handler.admincommands.IAdminCommandHandler;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.InventoryUpdate;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;

public class AdminEnchant
implements IAdminCommandHandler {
    @Override
    public boolean useAdminCommand(Enum enum_, String[] stringArray, String string, Player player) {
        Commands commands = (Commands)enum_;
        if (!player.getPlayerAccess().CanEditChar) {
            return false;
        }
        int n = -1;
        switch (commands) {
            case admin_enchant: {
                this.showMainPage(player);
                return true;
            }
            case admin_seteh: {
                n = 1;
                break;
            }
            case admin_setec: {
                n = 6;
                break;
            }
            case admin_seteg: {
                n = 10;
                break;
            }
            case admin_seteb: {
                n = 12;
                break;
            }
            case admin_setel: {
                n = 11;
                break;
            }
            case admin_setew: {
                n = 5;
                break;
            }
            case admin_setes: {
                n = 7;
                break;
            }
            case admin_setle: {
                n = 9;
                break;
            }
            case admin_setre: {
                n = 8;
                break;
            }
            case admin_setlf: {
                n = 14;
                break;
            }
            case admin_setrf: {
                n = 13;
                break;
            }
            case admin_seten: {
                n = 4;
                break;
            }
            case admin_setun: {
                n = 0;
                break;
            }
            case admin_setba: {
                n = 28;
                break;
            }
            case admin_setha: {
                n = 2;
                break;
            }
            case admin_setdha: {
                n = 2;
            }
        }
        if (n == -1 || stringArray.length < 2) {
            this.showMainPage(player);
            return true;
        }
        try {
            int n2 = Integer.parseInt(stringArray[1]);
            if (n2 < 0 || n2 > 65535) {
                player.sendMessage("You must set the enchant level to be between 0-65535.");
            } else {
                this.a(player, n2, n);
            }
        }
        catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
            player.sendMessage("Please specify a new enchant value.");
        }
        catch (NumberFormatException numberFormatException) {
            player.sendMessage("Please specify a valid new enchant value.");
        }
        this.showMainPage(player);
        return true;
    }

    private void a(Player player, int n, int n2) {
        GameObject gameObject = player.getTarget();
        if (gameObject == null) {
            gameObject = player;
        }
        if (!gameObject.isPlayer()) {
            player.sendMessage("Wrong target type.");
            return;
        }
        Player player2 = (Player)gameObject;
        int n3 = 0;
        ItemInstance itemInstance = player2.getInventory().getPaperdollItem(n2);
        if (itemInstance != null) {
            n3 = itemInstance.getEnchantLevel();
            player2.getInventory().unEquipItem(itemInstance);
            itemInstance.setEnchantLevel(n);
            player2.getInventory().equipItem(itemInstance);
            player2.sendPacket((IStaticPacket)new InventoryUpdate().addModifiedItem(itemInstance));
            player2.broadcastCharInfo();
            player.sendMessage("Changed enchantment of " + player2.getName() + "'s " + itemInstance.getName() + " from " + n3 + " to " + n + ".");
            player2.sendMessage("Admin has changed the enchantment of your " + itemInstance.getName() + " from " + n3 + " to " + n + ".");
        }
    }

    public void showMainPage(Player player) {
        GameObject gameObject = player.getTarget();
        if (gameObject == null) {
            gameObject = player;
        }
        Player player2 = player;
        if (gameObject.isPlayer()) {
            player2 = (Player)gameObject;
        }
        player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("admin/enchant.htm").replace("%player%", player2.getName()));
    }

    @Override
    public Enum[] getAdminCommandEnum() {
        return Commands.values();
    }

    private static final class Commands
    extends Enum<Commands> {
        public static final /* enum */ Commands admin_seteh = new Commands();
        public static final /* enum */ Commands admin_setec = new Commands();
        public static final /* enum */ Commands admin_seteg = new Commands();
        public static final /* enum */ Commands admin_setel = new Commands();
        public static final /* enum */ Commands admin_seteb = new Commands();
        public static final /* enum */ Commands admin_setew = new Commands();
        public static final /* enum */ Commands admin_setes = new Commands();
        public static final /* enum */ Commands admin_setle = new Commands();
        public static final /* enum */ Commands admin_setre = new Commands();
        public static final /* enum */ Commands admin_setlf = new Commands();
        public static final /* enum */ Commands admin_setrf = new Commands();
        public static final /* enum */ Commands admin_seten = new Commands();
        public static final /* enum */ Commands admin_setun = new Commands();
        public static final /* enum */ Commands admin_setba = new Commands();
        public static final /* enum */ Commands admin_setha = new Commands();
        public static final /* enum */ Commands admin_setdha = new Commands();
        public static final /* enum */ Commands admin_setlbr = new Commands();
        public static final /* enum */ Commands admin_setrbr = new Commands();
        public static final /* enum */ Commands admin_setbelt = new Commands();
        public static final /* enum */ Commands admin_enchant = new Commands();
        private static final /* synthetic */ Commands[] a;

        public static Commands[] values() {
            return (Commands[])a.clone();
        }

        public static Commands valueOf(String string) {
            return Enum.valueOf(Commands.class, string);
        }

        private static /* synthetic */ Commands[] a() {
            return new Commands[]{admin_seteh, admin_setec, admin_seteg, admin_setel, admin_seteb, admin_setew, admin_setes, admin_setle, admin_setre, admin_setlf, admin_setrf, admin_seten, admin_setun, admin_setba, admin_setha, admin_setdha, admin_setlbr, admin_setrbr, admin_setbelt, admin_enchant};
        }

        static {
            a = Commands.a();
        }
    }
}
