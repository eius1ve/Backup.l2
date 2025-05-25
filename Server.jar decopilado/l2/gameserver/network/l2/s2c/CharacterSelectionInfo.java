/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.network.l2.s2c;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import l2.commons.dbutils.DbUtils;
import l2.gameserver.Config;
import l2.gameserver.dao.CharacterDAO;
import l2.gameserver.dao.CharacterVariablesDAO;
import l2.gameserver.data.xml.holder.CharacterTemplateHolder;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.model.CharSelectInfoPackage;
import l2.gameserver.model.base.ClassId;
import l2.gameserver.model.base.Experience;
import l2.gameserver.model.entity.oly.HeroController;
import l2.gameserver.model.items.Inventory;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.templates.PlayerTemplate;
import l2.gameserver.utils.AutoBan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CharacterSelectionInfo
extends L2GameServerPacket {
    private static final Logger da = LoggerFactory.getLogger(CharacterSelectionInfo.class);
    private String dS;
    private int dL;
    private CharSelectInfoPackage[] a;

    public CharacterSelectionInfo(String string, int n) {
        this.dL = n;
        this.dS = string;
        this.a = CharacterSelectionInfo.loadCharacterSelectInfo(string);
    }

    public CharSelectInfoPackage[] getCharInfo() {
        return this.a;
    }

    @Override
    protected final void writeImpl() {
        int n;
        int n2 = this.a != null ? this.a.length : 0;
        this.writeC(9);
        int n3 = 7;
        this.writeD(n2);
        this.writeD(n3);
        this.writeC(n3 == n2 ? 1 : 0);
        this.writeC(1);
        this.writeD(2);
        this.writeC(0);
        this.writeC(0);
        long l = -1L;
        int n4 = -1;
        for (n = 0; n < n2; ++n) {
            if (l >= this.a[n].getLastAccess()) continue;
            l = this.a[n].getLastAccess();
            n4 = n;
        }
        for (n = 0; n < n2; ++n) {
            CharSelectInfoPackage charSelectInfoPackage = this.a[n];
            this.writeS(charSelectInfoPackage.getName());
            this.writeD(charSelectInfoPackage.getCharId());
            this.writeS(this.dS);
            this.writeD(this.dL);
            this.writeD(charSelectInfoPackage.getClanId());
            this.writeD(0);
            this.writeD(charSelectInfoPackage.getSex());
            this.writeD(charSelectInfoPackage.getRace());
            this.writeD(charSelectInfoPackage.getBaseClassId());
            this.writeD(1);
            this.writeD(charSelectInfoPackage.getX());
            this.writeD(charSelectInfoPackage.getY());
            this.writeD(charSelectInfoPackage.getZ());
            this.writeF(charSelectInfoPackage.getCurrentHp());
            this.writeF(charSelectInfoPackage.getCurrentMp());
            this.writeQ(charSelectInfoPackage.getSp());
            this.writeQ(charSelectInfoPackage.getExp());
            int n5 = charSelectInfoPackage.getLevel();
            this.writeF(Experience.getExpPercent(n5, charSelectInfoPackage.getExp()));
            this.writeD(n5);
            this.writeD(charSelectInfoPackage.getKarma());
            this.writeD(charSelectInfoPackage.getPk());
            this.writeD(charSelectInfoPackage.getPvP());
            this.writeD(0);
            this.writeD(0);
            this.writeD(0);
            this.writeD(0);
            this.writeD(0);
            this.writeD(0);
            this.writeD(0);
            this.writeD(0);
            this.writeD(0);
            for (int n6 : Inventory.PAPERDOLL_ORDER) {
                this.writeD(charSelectInfoPackage.getPaperdollItemId(n6));
            }
            for (int n6 : Inventory.PAPERDOLL_VISUAL_ORDER) {
                this.writeD(charSelectInfoPackage.getPaperdollVisualItemId(n6));
            }
            this.writeH(charSelectInfoPackage.getPaperdollEnchantEffect(6));
            this.writeH(charSelectInfoPackage.getPaperdollEnchantEffect(11));
            this.writeH(charSelectInfoPackage.getPaperdollEnchantEffect(1));
            this.writeH(charSelectInfoPackage.getPaperdollEnchantEffect(10));
            this.writeH(charSelectInfoPackage.getPaperdollEnchantEffect(12));
            this.writeD(charSelectInfoPackage.getHairStyle());
            this.writeD(charSelectInfoPackage.getHairColor());
            this.writeD(charSelectInfoPackage.getFace());
            this.writeF(charSelectInfoPackage.getMaxHp());
            this.writeF(charSelectInfoPackage.getMaxMp());
            this.writeD(charSelectInfoPackage.getAccessLevel() > -100 ? charSelectInfoPackage.getDeleteTimer() : -1);
            this.writeD(charSelectInfoPackage.getClassId());
            this.writeD(n == n4 ? 1 : 0);
            this.writeC(Math.min(charSelectInfoPackage.getPaperdollEnchantEffect(5), 127));
            int n7 = charSelectInfoPackage.getPaperdollAugmentationId(5);
            this.writeD(n7 & 0xFFFF);
            this.writeD(n7 >> 16);
            int n8 = charSelectInfoPackage.getPaperdollItemId(5);
            if (n8 == 8190) {
                this.writeD(301);
            } else if (n8 == 8689) {
                this.writeD(302);
            } else {
                this.writeD(0);
            }
            this.writeD(0);
            this.writeD(0);
            this.writeD(0);
            this.writeD(0);
            this.writeF(0.0);
            this.writeF(0.0);
            this.writeD(charSelectInfoPackage.getVitalityPoints());
            this.writeD(200);
            this.writeD(999);
            this.writeD(charSelectInfoPackage.getAccessLevel() == -100 ? 0 : 1);
            this.writeC(0);
            this.writeC(charSelectInfoPackage.getCurrentHero());
            this.writeC(charSelectInfoPackage.getHairAccessoryPriority());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static CharSelectInfoPackage[] loadCharacterSelectInfo(String string) {
        ArrayList<CharSelectInfoPackage> arrayList = new ArrayList<CharSelectInfoPackage>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM `characters` AS `c` LEFT JOIN `character_subclasses` AS `cs` ON (`c`.`obj_Id`=`cs`.`char_obj_id` AND `cs`.`active`=1) WHERE `account_name`=? LIMIT 7");
            preparedStatement.setString(1, string);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                CharSelectInfoPackage charSelectInfoPackage = CharacterSelectionInfo.a(resultSet);
                if (charSelectInfoPackage == null) continue;
                arrayList.add(charSelectInfoPackage);
            }
        }
        catch (Exception exception) {
            try {
                da.error("could not restore charinfo:", (Throwable)exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement, resultSet);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        }
        DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        return arrayList.toArray(new CharSelectInfoPackage[arrayList.size()]);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static int l(int n) {
        int n2 = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("SELECT `class_id` FROM `character_subclasses` WHERE `char_obj_id`=? AND `isBase`=1");
            preparedStatement.setInt(1, n);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                n2 = resultSet.getInt("class_id");
            }
        }
        catch (Exception exception) {
            try {
                da.error("could not restore base class id:", (Throwable)exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement, resultSet);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        }
        DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        return n2;
    }

    private static CharSelectInfoPackage a(ResultSet resultSet) {
        CharSelectInfoPackage charSelectInfoPackage = null;
        try {
            int n = resultSet.getInt("obj_Id");
            int n2 = resultSet.getInt("class_id");
            int n3 = resultSet.getInt("base_class_id");
            boolean bl = resultSet.getInt("sex") == 1;
            PlayerTemplate playerTemplate = CharacterTemplateHolder.getInstance().getTemplate(ClassId.getClassById(n3), !bl);
            if (playerTemplate == null) {
                da.error("restoreChar fail | templ == null | objectId: " + n + " | classid: " + n3 + " | female: " + bl);
                return null;
            }
            String string = resultSet.getString("char_name");
            charSelectInfoPackage = new CharSelectInfoPackage(n, string);
            charSelectInfoPackage.setLevel(resultSet.getInt("level"));
            charSelectInfoPackage.setMaxHp(resultSet.getInt("maxHp"));
            charSelectInfoPackage.setCurrentHp(resultSet.getDouble("curHp"));
            charSelectInfoPackage.setMaxMp(resultSet.getInt("maxMp"));
            charSelectInfoPackage.setCurrentMp(resultSet.getDouble("curMp"));
            charSelectInfoPackage.setX(resultSet.getInt("x"));
            charSelectInfoPackage.setY(resultSet.getInt("y"));
            charSelectInfoPackage.setZ(resultSet.getInt("z"));
            charSelectInfoPackage.setPk(resultSet.getInt("pkkills"));
            charSelectInfoPackage.setPvP(resultSet.getInt("pvpkills"));
            charSelectInfoPackage.setFace(resultSet.getInt("face"));
            charSelectInfoPackage.setHairStyle(resultSet.getInt("hairstyle"));
            charSelectInfoPackage.setHairColor(resultSet.getInt("haircolor"));
            charSelectInfoPackage.setSex(bl ? 1 : 0);
            charSelectInfoPackage.setExp(resultSet.getLong("exp"));
            charSelectInfoPackage.setSp(resultSet.getInt("sp"));
            charSelectInfoPackage.setClanId(resultSet.getInt("clanid"));
            charSelectInfoPackage.setKarma(-resultSet.getInt("karma"));
            charSelectInfoPackage.setRace(playerTemplate.race.ordinal());
            charSelectInfoPackage.setClassId(n2);
            charSelectInfoPackage.setBaseClassId(n3);
            long l = resultSet.getLong("deletetime");
            int n4 = 0;
            if (Config.DELETE_DAYS > 0.0) {
                if (l > 0L) {
                    l = (int)(System.currentTimeMillis() / 1000L - l);
                    n4 = (int)(l / 3600L / 24L);
                    if ((double)n4 >= Config.DELETE_DAYS) {
                        CharacterDAO.getInstance().deleteCharacterDataByObjId(n);
                        return null;
                    }
                    l = (long)(Config.DELETE_DAYS * 3600.0 * 24.0 - (double)l);
                } else {
                    l = 0L;
                }
            }
            charSelectInfoPackage.setDeleteTimer((int)l);
            charSelectInfoPackage.setLastAccess(resultSet.getLong("lastAccess") * 1000L);
            charSelectInfoPackage.setAccessLevel(resultSet.getInt("accesslevel"));
            int n5 = resultSet.getInt("vitality") + (int)((double)(System.currentTimeMillis() - charSelectInfoPackage.getLastAccess()) / 15.0);
            if (n5 > Config.ALT_VITALITY_LEVELS[4]) {
                n5 = Config.ALT_VITALITY_LEVELS[4];
            } else if (n5 < 0) {
                n5 = 0;
            }
            charSelectInfoPackage.setVitalityPoints(n5);
            if (charSelectInfoPackage.getAccessLevel() < 0 && !AutoBan.isBanned(n)) {
                charSelectInfoPackage.setAccessLevel(0);
            }
            charSelectInfoPackage.setCurrentHero(HeroController.getInstance().isCurrentHero(n) ? 2 : 0);
            String string2 = CharacterVariablesDAO.getInstance().getVar(n, "hideAccessory");
            charSelectInfoPackage.setHairAccessoryPriority(string2 != null ? (Boolean.parseBoolean(string2) ? 0 : 1) : 0);
        }
        catch (Exception exception) {
            da.error("", (Throwable)exception);
        }
        return charSelectInfoPackage;
    }
}
