/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.dbutils.DbUtils
 *  l2.gameserver.database.DatabaseFactory
 *  l2.gameserver.model.Player
 */
package dressmeEngine.util;

import dressmeEngine.data.A;
import dressmeEngine.data.DressMeAgathionData;
import dressmeEngine.data.DressMeArmorData;
import dressmeEngine.data.DressMeCloakData;
import dressmeEngine.data.DressMeEnchantData;
import dressmeEngine.data.DressMeHatData;
import dressmeEngine.data.DressMeShieldData;
import dressmeEngine.data.DressMeWeaponData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import l2.commons.dbutils.DbUtils;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.model.Player;

public class HandleDressMeDb {
    protected static final Logger _log = Logger.getLogger(HandleDressMeDb.class.getName());

    private static Map<Integer, List<Integer>> loadDress(Player Player2, String str, String str2) {
        LinkedHashMap<Integer, List<Integer>> linkedHashMap = new LinkedHashMap<Integer, List<Integer>>();
        ArrayList arrayList = new ArrayList();
        Connection connection = null;
        try {
            try {
                connection = DatabaseFactory.getInstance().getConnection();
                PreparedStatement prepareStatement = connection.prepareStatement("SELECT * from " + str + " WHERE charId =?");
                prepareStatement.setInt(1, Player2.getObjectId());
                linkedHashMap.put(Player2.getObjectId(), arrayList);
                ResultSet executeQuery = prepareStatement.executeQuery();
                while (executeQuery.next()) {
                    linkedHashMap.get(executeQuery.getInt("charId")).add(executeQuery.getInt(str2));
                }
                executeQuery.close();
                prepareStatement.close();
                DbUtils.close((Connection)connection);
            }
            catch (SQLException e) {
                _log.warning("Gabson: Couldn't select DressMe from: " + str + " to database:" + e.getMessage());
                DbUtils.close((Connection)connection);
            }
        }
        catch (Throwable th) {
            DbUtils.closeQuietly((Connection)connection);
        }
        return linkedHashMap;
    }

    private static boolean insertDress(Player Player2, String str, A a) {
        boolean z = false;
        Connection connection = null;
        try {
            try {
                connection = DatabaseFactory.getInstance().getConnection();
                PreparedStatement prepareStatement = connection.prepareStatement("INSERT INTO " + str + " VALUES (?,?)");
                prepareStatement.setInt(1, Player2.getObjectId());
                prepareStatement.setInt(2, a.C());
                prepareStatement.executeUpdate();
                prepareStatement.close();
                z = true;
                DbUtils.close((Connection)connection);
            }
            catch (SQLException e) {
                _log.log(Level.WARNING, "Gabson: Couldn't update DressMe " + str + " to database:" + e.getMessage(), e);
                DbUtils.close((Connection)connection);
            }
        }
        catch (Throwable th) {
            DbUtils.closeQuietly((Connection)connection);
        }
        return z;
    }

    public static <V> Map<Integer, V> A(Player Player2, Map<Integer, V> map, String str, String str2) {
        Map<Integer, List<Integer>> loadDress = HandleDressMeDb.loadDress(Player2, str, str2);
        LinkedHashMap<Integer, V> linkedHashMap = new LinkedHashMap<Integer, V>();
        for (Map.Entry<Integer, V> entry : map.entrySet()) {
            if (!HandleDressMeDb.isInside(Player2, loadDress, ((A)entry.getValue()).C())) continue;
            linkedHashMap.put(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<Integer, V> entry2 : map.entrySet()) {
            if (HandleDressMeDb.isInside(Player2, loadDress, ((A)entry2.getValue()).C())) continue;
            linkedHashMap.put(entry2.getKey(), entry2.getValue());
        }
        return linkedHashMap;
    }

    public static <V> List<V> orderL(Player Player2, Map<Integer, V> map, String str, String str2) {
        Map<Integer, List<Integer>> loadDress = HandleDressMeDb.loadDress(Player2, str, str2);
        LinkedList<V> linkedList = new LinkedList<V>();
        if (map != null && !map.isEmpty()) {
            for (Map.Entry<Integer, V> entry : map.entrySet()) {
                if (!HandleDressMeDb.isInside(Player2, loadDress, ((A)entry.getValue()).C())) continue;
                linkedList.add(entry.getValue());
            }
            for (Map.Entry<Integer, V> entry2 : map.entrySet()) {
                if (HandleDressMeDb.isInside(Player2, loadDress, ((A)entry2.getValue()).C())) continue;
                linkedList.add(entry2.getValue());
            }
        }
        return linkedList;
    }

    private static boolean isInside(Player Player2, Map<Integer, List<Integer>> map, int i) {
        if (map.get(Player2.getObjectId()).size() != 0) {
            int i2 = 0;
            while (i2 < map.get(Player2.getObjectId()).size()) {
                if (map.get(Player2.getObjectId()).get(i2) == i) {
                    return true;
                }
                ++i2;
            }
            return false;
        }
        return false;
    }

    public static boolean dressMeArmorInside(Player Player2, DressMeArmorData dressMeArmorData) {
        return HandleDressMeDb.isInside(Player2, HandleDressMeDb.loadDress(Player2, "character_dressme_armor_list", "dressId"), dressMeArmorData.C());
    }

    public static boolean dressMeCloakInside(Player Player2, DressMeCloakData dressMeCloakData) {
        return HandleDressMeDb.isInside(Player2, HandleDressMeDb.loadDress(Player2, "character_dressme_cloak_list", "cloakDressId"), dressMeCloakData.C());
    }

    public static boolean dressMeShieldInside(Player Player2, DressMeShieldData dressMeShieldData) {
        return HandleDressMeDb.isInside(Player2, HandleDressMeDb.loadDress(Player2, "character_dressme_shield_list", "shieldDressId"), dressMeShieldData.C());
    }

    public static boolean dressMeWeaponInside(Player Player2, DressMeWeaponData dressMeWeaponData) {
        return HandleDressMeDb.isInside(Player2, HandleDressMeDb.loadDress(Player2, "character_dressme_weapon_list", "weaponDressId"), dressMeWeaponData.C());
    }

    public static boolean dressMeHatInside(Player Player2, DressMeHatData dressMeHatData) {
        return HandleDressMeDb.isInside(Player2, HandleDressMeDb.loadDress(Player2, "character_dressme_hat_list", "hatDressId"), dressMeHatData.C());
    }

    public static boolean dressMeEnchantInside(Player Player2, DressMeEnchantData dressMeEnchantData) {
        return HandleDressMeDb.isInside(Player2, HandleDressMeDb.loadDress(Player2, "character_dressme_enchant_list", "enchantId"), dressMeEnchantData.C());
    }

    public static boolean dressMeAgathionInside(Player Player2, DressMeAgathionData dressMeAgathionData) {
        return HandleDressMeDb.isInside(Player2, HandleDressMeDb.loadDress(Player2, "character_dressme_agathion_list", "agathionId"), dressMeAgathionData.C());
    }

    public static boolean insertDressMeArmor(Player Player2, DressMeArmorData dressMeArmorData) {
        return HandleDressMeDb.insertDress(Player2, "character_dressme_armor_list", dressMeArmorData);
    }

    public static boolean insertDressMeCloak(Player Player2, DressMeCloakData dressMeCloakData) {
        return HandleDressMeDb.insertDress(Player2, "character_dressme_cloak_list", dressMeCloakData);
    }

    public static boolean insertDressMeShield(Player Player2, DressMeShieldData dressMeShieldData) {
        return HandleDressMeDb.insertDress(Player2, "character_dressme_shield_list", dressMeShieldData);
    }

    public static boolean insertDressMeWeapon(Player Player2, DressMeWeaponData dressMeWeaponData) {
        return HandleDressMeDb.insertDress(Player2, "character_dressme_weapon_list", dressMeWeaponData);
    }

    public static boolean insertDressMeHat(Player Player2, DressMeHatData dressMeHatData) {
        return HandleDressMeDb.insertDress(Player2, "character_dressme_hat_list", dressMeHatData);
    }

    public static boolean insertDressMeAgathion(Player Player2, DressMeAgathionData dressMeAgathionData) {
        return HandleDressMeDb.insertDress(Player2, "character_dressme_agathion_list", dressMeAgathionData);
    }

    public static boolean insertDressMeEnchant(Player Player2, DressMeEnchantData dressMeEnchantData) {
        return HandleDressMeDb.insertDress(Player2, "character_dressme_enchant_list", dressMeEnchantData);
    }
}
