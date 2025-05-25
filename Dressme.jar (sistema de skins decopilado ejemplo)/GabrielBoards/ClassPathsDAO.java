/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.database.DatabaseFactory
 *  l2.gameserver.model.Player
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package GabrielBoards;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassPathsDAO {
    protected static ClassPathsDAO A;
    private static final Logger D;
    private static String E;
    private static String B;
    private static String C;

    static {
        D = LoggerFactory.getLogger(ClassPathsDAO.class);
        E = "INSERT INTO class_paths (objid, classIndex, MiddleOff, LeftOff, LeftOff1, LeftOff2, LeftOff1_1, LeftOff1_2, LeftOff2_1, LeftOff2_2, RightOff, RightOff1, RightOff2, RightOff1_1, RightOff1_2, RightOff2_1, RightOff2_2, MiddleMage, LeftMage, LeftMage1, LeftMage2, RightMage, RightMage1, RightMage2, RightMage1_1, RightMage1_2, RightMage2_1, RightMage2_2, LeftMage1_1, LeftMage1_2, LeftMage2_1, LeftMage2_2, MiddleDef, LeftDef, LeftDef1, LeftDef2, RightDef, RightDef1, RightDef2, RightDef1_1, RightDef1_2, RightDef2_1, RightDef2_2, LeftDef1_1, LeftDef1_2, LeftDef2_1, LeftDef2_2, MiddleSup, LeftSup, LeftSup1, LeftSup2, RightSup, RightSup1, RightSup2, RightSup1_1, RightSup1_2, RightSup2_1, RightSup2_2, LeftSup1_1, LeftSup1_2, LeftSup2_1, LeftSup2_2) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE MiddleOff=?,LeftOff=?,LeftOff1=?,LeftOff2=?,LeftOff1_1=?,LeftOff1_2=?,LeftOff2_1=?,LeftOff2_2=?,RightOff=?,RightOff1=?,RightOff2=?,RightOff1_1=?,RightOff1_2=?,RightOff2_1=?,RightOff2_2=?,MiddleMage=?,LeftMage=?,LeftMage1=?,LeftMage2=?,RightMage=?,RightMage1=?,RightMage2=?,RightMage1_1=?,RightMage1_2=?,RightMage2_1=?,RightMage2_2=?,LeftMage1_1=?,LeftMage1_2=?,LeftMage2_1=?,LeftMage2_2=?,MiddleDef=?,LeftDef=?,LeftDef1=?,LeftDef2=?,RightDef=?,RightDef1=?,RightDef2=?,RightDef1_1=?,RightDef1_2=?,RightDef2_1=?,RightDef2_2=?,LeftDef1_1=?,LeftDef1_2=?,LeftDef2_1=?,LeftDef2_2=?,MiddleSup=?,LeftSup=?,LeftSup1=?,LeftSup2=?,RightSup=?,RightSup1=?,RightSup2=?,RightSup1_1=?,RightSup1_2=?,RightSup2_1=?,RightSup2_2=?,LeftSup1_1=?,LeftSup1_2=?,LeftSup2_1=?,LeftSup2_2=? ";
        B = "DELETE FROM class_paths WHERE objid=? AND classIndex=?";
        C = "SELECT * FROM class_paths WHERE objid=? AND classIndex=?";
    }

    public static ClassPathsDAO getInstance() {
        if (A == null) {
            A = new ClassPathsDAO();
        }
        return A;
    }

    public void classPathInsert(Player Player2) {
        try {
            Connection connection = DatabaseFactory.getInstance().getConnection();
            PreparedStatement prepareStatement = connection.prepareStatement(E);
            Object th = null;
            try {
                prepareStatement.executeUpdate();
                if (prepareStatement != null) {
                    prepareStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            }
            catch (Throwable th3) {
                try {
                    throw th3;
                }
                catch (Throwable th4) {
                    if (prepareStatement != null) {
                        if (th3 != null) {
                            try {
                                prepareStatement.close();
                            }
                            catch (Throwable th5) {
                                th3.addSuppressed(th5);
                            }
                        } else {
                            prepareStatement.close();
                        }
                    }
                    throw th4;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void classPathDelete(Player Player2, int i) {
        try {
            Connection connection = DatabaseFactory.getInstance().getConnection();
            PreparedStatement prepareStatement = connection.prepareStatement(B);
            Object th = null;
            try {
                prepareStatement.setInt(1, Player2.getObjectId());
                prepareStatement.setInt(2, i);
                prepareStatement.executeUpdate();
                if (prepareStatement != null) {
                    prepareStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            }
            catch (Throwable th3) {
                try {
                    throw th3;
                }
                catch (Throwable th4) {
                    if (prepareStatement != null) {
                        if (th3 != null) {
                            try {
                                prepareStatement.close();
                            }
                            catch (Throwable th5) {
                                th3.addSuppressed(th5);
                            }
                        } else {
                            prepareStatement.close();
                        }
                    }
                    throw th4;
                }
            }
        }
        catch (SQLException e) {
            D.error("Failed deleteCerti");
            e.printStackTrace();
        }
    }

    public void classPathLoad(Player Player2) {
        try {
            Connection connection = DatabaseFactory.getInstance().getConnection();
            PreparedStatement prepareStatement = connection.prepareStatement(C);
            Object th = null;
            try {
                int classIndex = Player2.isSubClassActive() ? Player2.getClassId().ordinal() : 0;
                prepareStatement.setInt(1, Player2.getObjectId());
                prepareStatement.setInt(2, classIndex);
                ResultSet executeQuery = prepareStatement.executeQuery();
                Object th2 = null;
                while (executeQuery.next()) {
                    try {
                        this.B(Player2);
                    }
                    catch (Throwable th3) {
                        try {
                            throw th3;
                        }
                        catch (Throwable th4) {
                            if (executeQuery != null) {
                                if (th3 != null) {
                                    try {
                                        executeQuery.close();
                                    }
                                    catch (Throwable th5) {
                                        th3.addSuppressed(th5);
                                    }
                                } else {
                                    executeQuery.close();
                                }
                            }
                            throw th4;
                        }
                    }
                }
                if (executeQuery != null) {
                    executeQuery.close();
                }
                if (prepareStatement != null) {
                    prepareStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            }
            catch (Throwable th8) {
                try {
                    throw th8;
                }
                catch (Throwable th9) {
                    if (prepareStatement != null) {
                        if (th8 != null) {
                            try {
                                prepareStatement.close();
                            }
                            catch (Throwable th10) {
                                th8.addSuppressed(th10);
                            }
                        } else {
                            prepareStatement.close();
                        }
                    }
                    throw th9;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleClassChange(Player Player2) {
        this.removeSkills(Player2);
        this.C(Player2);
        throw new UnsupportedOperationException("Method not decompiled: GabrielBoards.ClassPathsDAO.handleClassChange(l2.gameserver.model.Player):void");
    }

    private void C(Player Player2) {
    }

    public void removeSkills(Player Player2) {
    }

    public void giveSkills(Player Player2) {
    }

    private void B(Player Player2) {
    }
}
