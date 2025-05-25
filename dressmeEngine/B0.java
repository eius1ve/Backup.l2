/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.dbutils.DbUtils
 *  l2.gameserver.database.DatabaseFactory
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.items.ItemInstance
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.ExUserInfoEquipSlot
 *  l2.gameserver.network.l2.s2c.InventorySlot
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package dressmeEngine;

import config.GabConfig;
import dressmeEngine.A0;
import dressmeEngine.data.DressMeArmorData;
import dressmeEngine.data.DressMeHatData;
import dressmeEngine.data.DressMeShieldData;
import dressmeEngine.data.DressMeWeaponData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Map;
import l2.commons.dbutils.DbUtils;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExUserInfoCubic;
import l2.gameserver.network.l2.s2c.ExUserInfoEquipSlot;
import l2.gameserver.network.l2.s2c.InventorySlot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class B0 {
    private static final Logger _log = LoggerFactory.getLogger(B0.class);

    public static void B(Player Player2, ItemInstance ItemInstance2, int i) {
        if (ItemInstance2 != null) {
            B0.A(ItemInstance2, i);
            ItemInstance2.setVisibleItemId(i);
            Player2.sendUserInfo(true);
            Player2.sendPacket((IStaticPacket)new ExUserInfoEquipSlot(Player2, InventorySlot.VALUES));
            Player2.sendPacket((IStaticPacket)new ExUserInfoEquipSlot(Player2, new InventorySlot[]{InventorySlot.HAIR, InventorySlot.HAIR2, InventorySlot.HEAD, InventorySlot.AGATHION1, InventorySlot.AGATHION2, InventorySlot.AGATHION3, InventorySlot.AGATHION4, InventorySlot.AGATHION5}));
            Player2.broadcastCharInfo();
            if (i > 0) {
                Player2.sendMessage(String.valueOf(ItemInstance2.getName()) + " visual changed.");
            } else {
                Player2.sendMessage("Visual removed from " + ItemInstance2.getName() + ".");
            }
        }
    }

    public static void delete(ItemInstance ItemInstance2) {
        block5: {
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            try {
                try {
                    connection = DatabaseFactory.getInstance().getConnection();
                    preparedStatement = connection.prepareStatement("DELETE FROM `items_options` WHERE `item_id` =?");
                    preparedStatement.setInt(1, ItemInstance2.getObjectId());
                    preparedStatement.execute();
                }
                catch (Exception exception) {
                    _log.error("items_options.delete(): " + exception, (Throwable)exception);
                    DbUtils.closeQuietly((Connection)connection, (Statement)preparedStatement);
                    break block5;
                }
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly((Connection)connection, preparedStatement);
                throw throwable;
            }
            DbUtils.closeQuietly((Connection)connection, (Statement)preparedStatement);
        }
    }

    public static void insert(ItemInstance ItemInstance2, int i) {
        block6: {
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            try {
                try {
                    connection = DatabaseFactory.getInstance().getConnection();
                    preparedStatement = connection.prepareStatement("INSERT INTO `items_options`(`item_id`, `item_vis_type`, `flags`) VALUES (?,?,?)");
                    preparedStatement.setInt(1, ItemInstance2.getObjectId());
                    preparedStatement.setInt(2, i);
                    if (!GabConfig.DRESSME_TRADEABLE) {
                        preparedStatement.setInt(3, 2);
                    }
                    preparedStatement.executeUpdate();
                }
                catch (Exception exception) {
                    _log.error("items_options.insert(): " + exception, (Throwable)exception);
                    DbUtils.closeQuietly((Connection)connection, (Statement)preparedStatement);
                    break block6;
                }
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly((Connection)connection, preparedStatement);
                throw throwable;
            }
            DbUtils.closeQuietly((Connection)connection, (Statement)preparedStatement);
        }
    }

    public static void A(ItemInstance ItemInstance2, int i) {
        B0.delete(ItemInstance2);
        if (i > 0) {
            B0.insert(ItemInstance2, i);
        }
    }

    public static Map<Integer, DressMeWeaponData> A(String str, Map<Integer, DressMeWeaponData> map, ItemInstance ItemInstance2) {
        boolean isMagicWeapon = ItemInstance2.isAugmented();
        if (!str.equals("SWORD") || ItemInstance2.getBodyPart() == 16384L || isMagicWeapon) {
            if (str.equals("SWORD") && ItemInstance2.getBodyPart() != 16384L && isMagicWeapon) {
                return A0.Z;
            }
            if (!str.equals("BLUNT") || ItemInstance2.getBodyPart() == 16384L || isMagicWeapon) {
                if (str.equals("BLUNT") && ItemInstance2.getBodyPart() != 16384L && isMagicWeapon) {
                    return A0.F;
                }
                if (str.equals("BIG SWORD") && ItemInstance2.getBodyPart() == 16384L) {
                    return A0.U;
                }
                if (str.equals("BIG BLUNT") && ItemInstance2.getBodyPart() == 16384L && !isMagicWeapon) {
                    return A0.I;
                }
                if (str.equals("BIG BLUNT") && ItemInstance2.getBodyPart() == 16384L && isMagicWeapon) {
                    return A0.B;
                }
                if (str.equals("DAGGER")) {
                    return A0.O;
                }
                if (str.equals("BOW")) {
                    return A0.S;
                }
                if (str.equals("POLE")) {
                    return A0.A;
                }
                if (str.equals("FIST")) {
                    return A0.K;
                }
                if (str.equals("DUALSWORD")) {
                    return A0.V;
                }
                if (str.equals("DUALFIST")) {
                    return A0.P;
                }
                if (str.equals("ROD")) {
                    return A0.fish;
                }
                if (str.equals("CROSSBOW")) {
                    return A0.M;
                }
                if (str.equals("RAPIER")) {
                    return A0.D;
                }
                if (str.equals("ANCIENTSWORD")) {
                    return A0.E;
                }
                if (str.equals("DUALDAGGER")) {
                    return A0.G;
                }
                _log.error("Dress me system: Unknown weapon type: " + str);
                return null;
            }
            return A0.a;
        }
        return A0.N;
    }

    public static Map<Integer, DressMeShieldData> C(String str, Map<Integer, DressMeShieldData> map, ItemInstance ItemInstance2) {
        if (str.equals("SIGIL")) {
            return A0.T;
        }
        if (str.equals("SHIELD")) {
            return A0.e;
        }
        return null;
    }

    public static Map<Integer, DressMeArmorData> B(String str, Map<Integer, DressMeArmorData> map, ItemInstance ItemInstance2) {
        if (GabConfig.ALLOW_ALL_SETS) {
            return A0.J;
        }
        if (str.equals("LIGHT")) {
            return A0.X;
        }
        if (str.equals("HEAVY")) {
            return A0.C;
        }
        if (str.equals("ROBE") || str.equals("MAGIC")) {
            return A0.c;
        }
        if (str.equals("SUIT")) {
            return A0.Q;
        }
        _log.error("Dress me system: Unknown armor type: " + str);
        return null;
    }

    public static Map<Integer, DressMeHatData> A(Map<Integer, DressMeHatData> map, ItemInstance ItemInstance2) {
        if (ItemInstance2.getEquipSlot() == 3) {
            return A0.H;
        }
        if (ItemInstance2.getEquipSlot() == 2) {
            return A0.L;
        }
        _log.error("Dress me system: Unknown hat slot: " + ItemInstance2.getEquipSlot());
        return A0.Y;
    }

    public static void A(Player Player2, int i, int bp, boolean z) {
        if (!B0.A(i)) {
            Player2.sendMessage("Non valid value entered! Contact the administration!");
        } else if (z) {
            Player2.setVar("tryEnchantColor", String.valueOf(String.valueOf(bp) + "-" + i), -1L);
            B0.A(Player2);
        } else if (i == 0) {
            Player2.unsetVar("customEnchantColor");
            Player2.sendMessage("You Reseted your Custom Enchant Color!");
            B0.A(Player2);
        } else {
            Player2.setVar("customEnchantColor", String.valueOf(String.valueOf(bp) + "-" + i), -1L);
            B0.A(Player2);
            Player2.sendMessage("Custom Enchant Changed!");
        }
    }

    public static void A(Player player) {
        player.sendPacket((IStaticPacket)SystemMsg.YOU_ARE_NO_LONGER_TRYING_ON_EQUIPMENT);
        player.sendUserInfo(true);
        player.sendPacket((IStaticPacket)new ExUserInfoEquipSlot(player, InventorySlot.VALUES));
        player.sendPacket((IStaticPacket)new ExUserInfoEquipSlot(player, new InventorySlot[]{InventorySlot.HAIR, InventorySlot.HAIR2, InventorySlot.HEAD, InventorySlot.AGATHION1, InventorySlot.AGATHION2, InventorySlot.AGATHION3, InventorySlot.AGATHION4, InventorySlot.AGATHION5}));
        player.sendPacket((IStaticPacket)new ExUserInfoCubic(player));
        player.broadcastCharInfo();
    }

    private static boolean A(int i) {
        return i >= 0 && i <= 112;
    }

    public static void B(Player Player2, int i, boolean z) {
        if (!B0.B(i)) {
            Player2.sendMessage("Non valid value entered! Contact the administration!");
        } else if (z) {
            Player2.setVar("TryagathionCustom", String.valueOf(i), -1L);
            B0.A(Player2);
        } else if (i == 0) {
            Player2.unsetVar("agathionCustom");
            Player2.sendMessage("You Reseted your Agathion.");
            B0.A(Player2);
        } else {
            Player2.setVar("agathionCustom", String.valueOf(i), -1L);
            B0.A(Player2);
            Player2.sendMessage("Custom Agathion Changed!");
        }
    }

    public static boolean B(int i) {
        return true;
    }
}
