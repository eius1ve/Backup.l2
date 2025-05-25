/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.data.htm.HtmCache
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.network.l2.s2c.MagicSkillUse
 *  l2.gameserver.network.l2.s2c.ShowBoard
 *  l2.gameserver.network.l2.s2c.UserInfoType
 */
package GabrielBoards;

import config.ClassPathConfig;
import l2.gameserver.data.htm.HtmCache;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.MagicSkillUse;
import l2.gameserver.network.l2.s2c.ShowBoard;
import l2.gameserver.network.l2.s2c.UserInfoType;
import model.PlayerGab;
import scriptsGab.utils.BBS;

public class ClassPathsBBSManager {
    public static ClassPathsBBSManager getInstance() {
        return _A.A;
    }

    private void F(PlayerGab Player2) {
        BBS.separateAndSend(HtmCache.getInstance().getNotNull("CommunityBoard/classpath/main.htm", (Player)Player2).replace("%remain%", String.valueOf(Player2.getalekos() - Player2.getalekoscur())), Player2);
    }

    private String A(PlayerGab Player2, String str) {
        String replace8;
        String replace7;
        String replace6;
        String replace5;
        String replace4;
        String replace3;
        String replace2;
        String replace = str.replace("%remain%", String.valueOf(Player2.getalekos() - Player2.getalekoscur())).replace("%middle%", String.valueOf(Player2.getMiddleOff()) + "/3").replace("%left%", String.valueOf(Player2.getLeftOff()) + "/3").replace("%left1%", String.valueOf(Player2.getLeftOff1()) + "/3").replace("%left2%", String.valueOf(Player2.getLeftOff2()) + "/3").replace("%left1_1%", String.valueOf(Player2.getLeftOff1_1()) + "/1").replace("%left2_1%", String.valueOf(Player2.getLeftOff2_1()) + "/1").replace("%left1_2%", String.valueOf(Player2.getLeftOff1_2()) + "/1").replace("%left2_2%", String.valueOf(Player2.getLeftOff2_2()) + "/1").replace("%right%", String.valueOf(Player2.getRightOff()) + "/3").replace("%right1%", String.valueOf(Player2.getRightOff1()) + "/3").replace("%right2%", String.valueOf(Player2.getRightOff2()) + "/3").replace("%right1_1%", String.valueOf(Player2.getRightOff1_1()) + "/1").replace("%right2_1%", String.valueOf(Player2.getRightOff2_1()) + "/1").replace("%right1_2%", String.valueOf(Player2.getRightOff1_2()) + "/1").replace("%right2_2%", String.valueOf(Player2.getRightOff2_2()) + "/1");
        String string = Player2.getMiddleOff() == 0 ? replace.replace("%statmiddle%", ClassPathConfig.U[0]) : (replace2 = Player2.getMiddleOff() == 1 || Player2.getMiddleOff() == 2 ? replace.replace("%statmiddle%", ClassPathConfig.U[1]) : replace.replace("%statmiddle%", "<font color=\"LEVEL\">" + ClassPathConfig.U[2] + "</font>"));
        String string2 = Player2.getLeftOff() == 0 ? replace2.replace("%statleft%", ClassPathConfig.f3[0]) : (replace3 = Player2.getLeftOff() == 1 || Player2.getLeftOff() == 2 ? replace2.replace("%statleft%", ClassPathConfig.f3[1]) : replace2.replace("%statleft%", "<font color=\"LEVEL\">" + ClassPathConfig.f3[2] + "</font>"));
        String string3 = Player2.getLeftOff1() == 0 ? replace3.replace("%statleftone%", ClassPathConfig.f4[0]) : (replace4 = Player2.getLeftOff1() == 1 || Player2.getLeftOff1() == 2 ? replace3.replace("%statleftone%", ClassPathConfig.f4[1]) : replace3.replace("%statleftone%", "<font color=\"LEVEL\">" + ClassPathConfig.f4[2] + "</font>"));
        if (Player2.getLeftOff1_1() == 0) {
            replace4 = replace4.replace("%statleftone_1%", ClassPathConfig.f6[0]);
        } else if (Player2.getLeftOff1_1() == 1) {
            replace4 = replace4.replace("%statleftone_1%", "<font color=\"LEVEL\">" + ClassPathConfig.f6[0] + "</font>");
        }
        if (Player2.getLeftOff1_2() == 0) {
            replace4 = replace4.replace("%statleftone_2%", ClassPathConfig.f8[0]);
        } else if (Player2.getLeftOff1_2() == 1) {
            replace4 = replace4.replace("%statleftone_2%", "<font color=\"LEVEL\">" + ClassPathConfig.f8[0] + "</font>");
        }
        String string4 = Player2.getLeftOff2() == 0 ? replace4.replace("%statlefttwo%", ClassPathConfig.f9[0]) : (replace5 = Player2.getLeftOff2() == 1 || Player2.getLeftOff2() == 2 ? replace4.replace("%statlefttwo%", ClassPathConfig.f9[1]) : replace4.replace("%statlefttwo%", "<font color=\"LEVEL\">" + ClassPathConfig.f9[2] + "</font>"));
        if (Player2.getLeftOff2_1() == 0) {
            replace5 = replace5.replace("%statlefttwo_1%", ClassPathConfig.f11[0]);
        } else if (Player2.getLeftOff2_1() == 1) {
            replace5 = replace5.replace("%statlefttwo_1%", "<font color=\"LEVEL\">" + ClassPathConfig.f11[0] + "</font>");
        }
        if (Player2.getLeftOff2_2() == 0) {
            replace5 = replace5.replace("%statlefttwo_2%", ClassPathConfig.f13[0]);
        } else if (Player2.getLeftOff2_2() == 1) {
            replace5 = replace5.replace("%statlefttwo_2%", "<font color=\"LEVEL\">" + ClassPathConfig.f13[0] + "</font>");
        }
        String string5 = Player2.getRightOff() == 0 ? replace5.replace("%statright%", ClassPathConfig.W[0]) : (replace6 = Player2.getRightOff() == 1 || Player2.getRightOff() == 2 ? replace5.replace("%statright%", ClassPathConfig.W[1]) : replace5.replace("%statright%", "<font color=\"LEVEL\">" + ClassPathConfig.W[2] + "</font>"));
        String string6 = Player2.getRightOff1() == 0 ? replace6.replace("%statrightone%", ClassPathConfig.f15[0]) : (replace7 = Player2.getRightOff1() == 1 || Player2.getRightOff1() == 2 ? replace6.replace("%statrightone%", ClassPathConfig.f15[1]) : replace6.replace("%statrightone%", "<font color=\"LEVEL\">" + ClassPathConfig.f15[2] + "</font>"));
        if (Player2.getRightOff1_1() == 0) {
            replace7 = replace7.replace("%statrightone_1%", ClassPathConfig.f17[0]);
        } else if (Player2.getRightOff1_1() == 1) {
            replace7 = replace7.replace("%statrightone_1%", "<font color=\"LEVEL\">" + ClassPathConfig.f17[0] + "</font>");
        }
        if (Player2.getRightOff1_2() == 0) {
            replace7 = replace7.replace("%statrightone_2%", ClassPathConfig.q[0]);
        } else if (Player2.getRightOff1_2() == 1) {
            replace7 = replace7.replace("%statrightone_2%", "<font color=\"LEVEL\">" + ClassPathConfig.q[0] + "</font>");
        }
        String string7 = Player2.getRightOff2() == 0 ? replace7.replace("%statrighttwo%", ClassPathConfig.f19[0]) : (replace8 = Player2.getRightOff2() == 1 || Player2.getRightOff2() == 2 ? replace7.replace("%statrighttwo%", ClassPathConfig.f19[1]) : replace7.replace("%statrighttwo%", "<font color=\"LEVEL\">" + ClassPathConfig.f19[2] + "</font>"));
        if (Player2.getRightOff2_1() == 0) {
            replace8 = replace8.replace("%statrighttwo_1%", ClassPathConfig.x[0]);
        } else if (Player2.getRightOff2_1() == 1) {
            replace8 = replace8.replace("%statrighttwo_1%", "<font color=\"LEVEL\">" + ClassPathConfig.x[0] + "</font>");
        }
        if (Player2.getRightOff2_2() == 0) {
            replace8 = replace8.replace("%statrighttwo_2%", ClassPathConfig.Q[0]);
        } else if (Player2.getRightOff2_2() == 1) {
            replace8 = replace8.replace("%statrighttwo_2%", "<font color=\"LEVEL\">" + ClassPathConfig.Q[0] + "</font>");
        }
        return replace8;
    }

    private String B(PlayerGab Player2, String str) {
        String replace8;
        String replace7;
        String replace6;
        String replace5;
        String replace4;
        String replace3;
        String replace2;
        String replace = str.replace("%remain%", String.valueOf(Player2.getalekos() - Player2.getalekoscur())).replace("%middle%", String.valueOf(Player2.getMiddleMage()) + "/3").replace("%left%", String.valueOf(Player2.getLeftMage()) + "/3").replace("%left1%", String.valueOf(Player2.getLeftMage1()) + "/3").replace("%left2%", String.valueOf(Player2.getLeftMage2()) + "/3").replace("%left1_1%", String.valueOf(Player2.getLeftMage1_1()) + "/1").replace("%left2_1%", String.valueOf(Player2.getLeftMage2_1()) + "/1").replace("%left1_2%", String.valueOf(Player2.getLeftMage1_2()) + "/1").replace("%left2_2%", String.valueOf(Player2.getLeftMage2_2()) + "/1").replace("%right%", String.valueOf(Player2.getRightMage()) + "/3").replace("%right1%", String.valueOf(Player2.getRightMage1()) + "/3").replace("%right2%", String.valueOf(Player2.getRightMage2()) + "/3").replace("%right1_1%", String.valueOf(Player2.getRightMage1_1()) + "/1").replace("%right2_1%", String.valueOf(Player2.getRightMage2_1()) + "/1").replace("%right1_2%", String.valueOf(Player2.getRightMage1_2()) + "/1").replace("%right2_2%", String.valueOf(Player2.getRightMage2_2()) + "/1");
        String string = Player2.getMiddleMage() == 0 ? replace.replace("%statmiddle%", ClassPathConfig.f21[0]) : (replace2 = Player2.getMiddleMage() == 1 || Player2.getMiddleMage() == 2 ? replace.replace("%statmiddle%", ClassPathConfig.f21[1]) : replace.replace("%statmiddle%", "<font color=\"LEVEL\">" + ClassPathConfig.f21[2] + "</font>"));
        String string2 = Player2.getLeftMage() == 0 ? replace2.replace("%statleft%", ClassPathConfig.S[0]) : (replace3 = Player2.getLeftMage() == 1 || Player2.getLeftMage() == 2 ? replace2.replace("%statleft%", ClassPathConfig.S[1]) : replace2.replace("%statleft%", "<font color=\"LEVEL\">" + ClassPathConfig.S[2] + "</font>"));
        String string3 = Player2.getLeftMage1() == 0 ? replace3.replace("%statleftone%", ClassPathConfig.f24[0]) : (replace4 = Player2.getLeftMage1() == 1 || Player2.getLeftMage1() == 2 ? replace3.replace("%statleftone%", ClassPathConfig.f24[1]) : replace3.replace("%statleftone%", "<font color=\"LEVEL\">" + ClassPathConfig.f24[2] + "</font>"));
        if (Player2.getLeftMage1_1() == 0) {
            replace4 = replace4.replace("%statleftone_1%", ClassPathConfig.V[0]);
        } else if (Player2.getLeftMage1_1() == 1) {
            replace4 = replace4.replace("%statleftone_1%", "<font color=\"LEVEL\">" + ClassPathConfig.V[0] + "</font>");
        }
        if (Player2.getLeftMage1_2() == 0) {
            replace4 = replace4.replace("%statleftone_2%", ClassPathConfig.C[0]);
        } else if (Player2.getLeftMage1_2() == 1) {
            replace4 = replace4.replace("%statleftone_2%", "<font color=\"LEVEL\">" + ClassPathConfig.C[0] + "</font>");
        }
        String string4 = Player2.getLeftMage2() == 0 ? replace4.replace("%statlefttwo%", ClassPathConfig.f26[0]) : (replace5 = Player2.getLeftMage2() == 1 || Player2.getLeftMage2() == 2 ? replace4.replace("%statlefttwo%", ClassPathConfig.f26[1]) : replace4.replace("%statlefttwo%", "<font color=\"LEVEL\">" + ClassPathConfig.f26[2] + "</font>"));
        if (Player2.getLeftMage2_1() == 0) {
            replace5 = replace5.replace("%statlefttwo_1%", ClassPathConfig.f27[0]);
        } else if (Player2.getLeftMage2_1() == 1) {
            replace5 = replace5.replace("%statlefttwo_1%", "<font color=\"LEVEL\">" + ClassPathConfig.f27[0] + "</font>");
        }
        if (Player2.getLeftMage2_2() == 0) {
            replace5 = replace5.replace("%statlefttwo_2%", ClassPathConfig.f28[0]);
        } else if (Player2.getLeftMage2_2() == 1) {
            replace5 = replace5.replace("%statlefttwo_2%", "<font color=\"LEVEL\">" + ClassPathConfig.f28[0] + "</font>");
        }
        String string5 = Player2.getRightMage() == 0 ? replace5.replace("%statright%", ClassPathConfig.J[0]) : (replace6 = Player2.getRightMage() == 1 || Player2.getRightMage() == 2 ? replace5.replace("%statright%", ClassPathConfig.J[1]) : replace5.replace("%statright%", "<font color=\"LEVEL\">" + ClassPathConfig.J[2] + "</font>"));
        String string6 = Player2.getRightMage1() == 0 ? replace6.replace("%statrightone%", ClassPathConfig.i[0]) : (replace7 = Player2.getRightMage1() == 1 || Player2.getRightMage1() == 2 ? replace6.replace("%statrightone%", ClassPathConfig.i[1]) : replace6.replace("%statrightone%", "<font color=\"LEVEL\">" + ClassPathConfig.i[2] + "</font>"));
        if (Player2.getRightMage1_1() == 0) {
            replace7 = replace7.replace("%statrightone_1%", ClassPathConfig.f31[0]);
        } else if (Player2.getRightMage1_1() == 1) {
            replace7 = replace7.replace("%statrightone_1%", "<font color=\"LEVEL\">" + ClassPathConfig.f31[0] + "</font>");
        }
        if (Player2.getRightMage1_2() == 0) {
            replace7 = replace7.replace("%statrightone_2%", ClassPathConfig.f32[0]);
        } else if (Player2.getRightMage1_2() == 1) {
            replace7 = replace7.replace("%statrightone_2%", "<font color=\"LEVEL\">" + ClassPathConfig.f32[0] + "</font>");
        }
        String string7 = Player2.getRightMage2() == 0 ? replace7.replace("%statrighttwo%", ClassPathConfig.F[0]) : (replace8 = Player2.getRightMage2() == 1 || Player2.getRightMage2() == 2 ? replace7.replace("%statrighttwo%", ClassPathConfig.F[1]) : replace7.replace("%statrighttwo%", "<font color=\"LEVEL\">" + ClassPathConfig.F[2] + "</font>"));
        if (Player2.getRightMage2_1() == 0) {
            replace8 = replace8.replace("%statrighttwo_1%", ClassPathConfig.f34[0]);
        } else if (Player2.getRightMage2_1() == 1) {
            replace8 = replace8.replace("%statrighttwo_1%", "<font color=\"LEVEL\">" + ClassPathConfig.f34[0] + "</font>");
        }
        if (Player2.getRightMage2_2() == 0) {
            replace8 = replace8.replace("%statrighttwo_2%", ClassPathConfig.f35[0]);
        } else if (Player2.getRightMage2_2() == 1) {
            replace8 = replace8.replace("%statrighttwo_2%", "<font color=\"LEVEL\">" + ClassPathConfig.f35[0] + "</font>");
        }
        return replace8;
    }

    private String C(PlayerGab Player2, String str) {
        String replace8;
        String replace7;
        String replace6;
        String replace5;
        String replace4;
        String replace3;
        String replace2;
        String replace = str.replace("%remain%", String.valueOf(Player2.getalekos() - Player2.getalekoscur())).replace("%middle%", String.valueOf(Player2.getMiddleDef()) + "/3").replace("%left%", String.valueOf(Player2.getLeftDef()) + "/3").replace("%left1%", String.valueOf(Player2.getLeftDef1()) + "/3").replace("%left2%", String.valueOf(Player2.getLeftDef2()) + "/3").replace("%left1_1%", String.valueOf(Player2.getLeftDef1_1()) + "/1").replace("%left2_1%", String.valueOf(Player2.getLeftDef2_1()) + "/1").replace("%left1_2%", String.valueOf(Player2.getLeftDef1_2()) + "/1").replace("%left2_2%", String.valueOf(Player2.getLeftDef2_2()) + "/1").replace("%right%", String.valueOf(Player2.getRightDef()) + "/3").replace("%right1%", String.valueOf(Player2.getRightDef1()) + "/3").replace("%right2%", String.valueOf(Player2.getRightDef2()) + "/3").replace("%right1_1%", String.valueOf(Player2.getRightDef1_1()) + "/1").replace("%right2_1%", String.valueOf(Player2.getRightDef2_1()) + "/1").replace("%right1_2%", String.valueOf(Player2.getRightDef1_2()) + "/1").replace("%right2_2%", String.valueOf(Player2.getRightDef2_2()) + "/1");
        String string = Player2.getMiddleDef() == 0 ? replace.replace("%statmiddle%", ClassPathConfig.Z[0]) : (replace2 = Player2.getMiddleDef() == 1 || Player2.getMiddleDef() == 2 ? replace.replace("%statmiddle%", ClassPathConfig.Z[1]) : replace.replace("%statmiddle%", "<font color=\"LEVEL\">" + ClassPathConfig.Z[2] + "</font>"));
        String string2 = Player2.getLeftDef() == 0 ? replace2.replace("%statleft%", ClassPathConfig.N[0]) : (replace3 = Player2.getLeftDef() == 1 || Player2.getLeftDef() == 2 ? replace2.replace("%statleft%", ClassPathConfig.N[1]) : replace2.replace("%statleft%", "<font color=\"LEVEL\">" + ClassPathConfig.N[2] + "</font>"));
        String string3 = Player2.getLeftDef1() == 0 ? replace3.replace("%statleftone%", ClassPathConfig.f39[0]) : (replace4 = Player2.getLeftDef1() == 1 || Player2.getLeftDef1() == 2 ? replace3.replace("%statleftone%", ClassPathConfig.f39[1]) : replace3.replace("%statleftone%", "<font color=\"LEVEL\">" + ClassPathConfig.f39[2] + "</font>"));
        if (Player2.getLeftDef1_1() == 0) {
            replace4 = replace4.replace("%statleftone_1%", ClassPathConfig.f40[0]);
        } else if (Player2.getLeftDef1_1() == 1) {
            replace4 = replace4.replace("%statleftone_1%", "<font color=\"LEVEL\">" + ClassPathConfig.f40[0] + "</font>");
        }
        if (Player2.getLeftDef1_2() == 0) {
            replace4 = replace4.replace("%statleftone_2%", ClassPathConfig.T[0]);
        } else if (Player2.getLeftDef1_2() == 1) {
            replace4 = replace4.replace("%statleftone_2%", "<font color=\"LEVEL\">" + ClassPathConfig.T[0] + "</font>");
        }
        String string4 = Player2.getLeftDef2() == 0 ? replace4.replace("%statlefttwo%", ClassPathConfig.f42[0]) : (replace5 = Player2.getLeftDef2() == 1 || Player2.getLeftDef2() == 2 ? replace4.replace("%statlefttwo%", ClassPathConfig.f42[1]) : replace4.replace("%statlefttwo%", "<font color=\"LEVEL\">" + ClassPathConfig.f42[2] + "</font>"));
        if (Player2.getLeftDef2_1() == 0) {
            replace5 = replace5.replace("%statlefttwo_1%", ClassPathConfig.e[0]);
        } else if (Player2.getLeftDef2_1() == 1) {
            replace5 = replace5.replace("%statlefttwo_1%", "<font color=\"LEVEL\">" + ClassPathConfig.e[0] + "</font>");
        }
        if (Player2.getLeftDef2_2() == 0) {
            replace5 = replace5.replace("%statlefttwo_2%", ClassPathConfig.E[0]);
        } else if (Player2.getLeftDef2_2() == 1) {
            replace5 = replace5.replace("%statlefttwo_2%", "<font color=\"LEVEL\">" + ClassPathConfig.E[0] + "</font>");
        }
        String string5 = Player2.getRightDef() == 0 ? replace5.replace("%statright%", ClassPathConfig.M[0]) : (replace6 = Player2.getRightDef() == 1 || Player2.getRightDef() == 2 ? replace5.replace("%statright%", ClassPathConfig.M[1]) : replace5.replace("%statright%", "<font color=\"LEVEL\">" + ClassPathConfig.M[2] + "</font>"));
        String string6 = Player2.getRightDef1() == 0 ? replace6.replace("%statrightone%", ClassPathConfig.f45[0]) : (replace7 = Player2.getRightDef1() == 1 || Player2.getRightDef1() == 2 ? replace6.replace("%statrightone%", ClassPathConfig.f45[1]) : replace6.replace("%statrightone%", "<font color=\"LEVEL\">" + ClassPathConfig.f45[2] + "</font>"));
        if (Player2.getRightDef1_1() == 0) {
            replace7 = replace7.replace("%statrightone_1%", ClassPathConfig.z[0]);
        } else if (Player2.getRightDef1_1() == 1) {
            replace7 = replace7.replace("%statrightone_1%", "<font color=\"LEVEL\">" + ClassPathConfig.z[0] + "</font>");
        }
        if (Player2.getRightDef1_2() == 0) {
            replace7 = replace7.replace("%statrightone_2%", ClassPathConfig.R[0]);
        } else if (Player2.getRightDef1_2() == 1) {
            replace7 = replace7.replace("%statrightone_2%", "<font color=\"LEVEL\">" + ClassPathConfig.R[0] + "</font>");
        }
        String string7 = Player2.getRightDef2() == 0 ? replace7.replace("%statrighttwo%", ClassPathConfig.f47[0]) : (replace8 = Player2.getRightDef2() == 1 || Player2.getRightDef2() == 2 ? replace7.replace("%statrighttwo%", ClassPathConfig.f47[1]) : replace7.replace("%statrighttwo%", "<font color=\"LEVEL\">" + ClassPathConfig.f47[2] + "</font>"));
        if (Player2.getRightDef2_1() == 0) {
            replace8 = replace8.replace("%statrighttwo_1%", ClassPathConfig.Y[0]);
        } else if (Player2.getRightDef2_1() == 1) {
            replace8 = replace8.replace("%statrighttwo_1%", "<font color=\"LEVEL\">" + ClassPathConfig.Y[0] + "</font>");
        }
        if (Player2.getRightDef2_2() == 0) {
            replace8 = replace8.replace("%statrighttwo_2%", ClassPathConfig.D[0]);
        } else if (Player2.getRightDef2_2() == 1) {
            replace8 = replace8.replace("%statrighttwo_2%", "<font color=\"LEVEL\">" + ClassPathConfig.D[0] + "</font>");
        }
        return replace8;
    }

    private String D(PlayerGab Player2, String str) {
        String replace8;
        String replace7;
        String replace6;
        String replace5;
        String replace4;
        String replace3;
        String replace2;
        String replace = str.replace("%remain%", String.valueOf(Player2.getalekos() - Player2.getalekoscur())).replace("%middle%", String.valueOf(Player2.getMiddleSup()) + "/3").replace("%left%", String.valueOf(Player2.getLeftSup()) + "/3").replace("%left1%", String.valueOf(Player2.getLeftSup1()) + "/3").replace("%left2%", String.valueOf(Player2.getLeftSup2()) + "/3").replace("%left1_1%", String.valueOf(Player2.getLeftSup1_1()) + "/1").replace("%left2_1%", String.valueOf(Player2.getLeftSup2_1()) + "/1").replace("%left1_2%", String.valueOf(Player2.getLeftSup1_2()) + "/1").replace("%left2_2%", String.valueOf(Player2.getLeftSup2_2()) + "/1").replace("%right%", String.valueOf(Player2.getRightSup()) + "/3").replace("%left2%", String.valueOf(Player2.getLeftSup2()) + "/3").replace("%right%", String.valueOf(Player2.getRightSup()) + "/3").replace("%right1%", String.valueOf(Player2.getRightSup1()) + "/3").replace("%right2%", String.valueOf(Player2.getRightSup2()) + "/3").replace("%right1_1%", String.valueOf(Player2.getRightSup1_1()) + "/1").replace("%right2_1%", String.valueOf(Player2.getRightSup2_1()) + "/1").replace("%right1_2%", String.valueOf(Player2.getRightSup1_2()) + "/1").replace("%right2_2%", String.valueOf(Player2.getRightSup2_2()) + "/1");
        String string = Player2.getMiddleSup() == 0 ? replace.replace("%statmiddle%", ClassPathConfig.f49[0]) : (replace2 = Player2.getMiddleSup() == 1 || Player2.getMiddleSup() == 2 ? replace.replace("%statmiddle%", ClassPathConfig.f49[1]) : replace.replace("%statmiddle%", "<font color=\"LEVEL\">" + ClassPathConfig.f49[2] + "</font>"));
        String string2 = Player2.getLeftSup() == 0 ? replace2.replace("%statleft%", ClassPathConfig.f50[0]) : (replace3 = Player2.getLeftSup() == 1 || Player2.getLeftSup() == 2 ? replace2.replace("%statleft%", ClassPathConfig.f50[1]) : replace2.replace("%statleft%", "<font color=\"LEVEL\">" + ClassPathConfig.f50[2] + "</font>"));
        String string3 = Player2.getLeftSup1() == 0 ? replace3.replace("%statleftone%", ClassPathConfig.f51[0]) : (replace4 = Player2.getLeftSup1() == 1 || Player2.getLeftSup1() == 2 ? replace3.replace("%statleftone%", ClassPathConfig.f51[1]) : replace3.replace("%statleftone%", "<font color=\"LEVEL\">" + ClassPathConfig.f51[2] + "</font>"));
        if (Player2.getLeftSup1_1() == 0) {
            replace4 = replace4.replace("%statleftone_1%", ClassPathConfig.f53[0]);
        } else if (Player2.getLeftSup1_1() == 1) {
            replace4 = replace4.replace("%statleftone_1%", "<font color=\"LEVEL\">" + ClassPathConfig.f53[0] + "</font>");
        }
        if (Player2.getLeftSup1_2() == 0) {
            replace4 = replace4.replace("%statleftone_2%", ClassPathConfig.f55[0]);
        } else if (Player2.getLeftSup1_2() == 1) {
            replace4 = replace4.replace("%statleftone_2%", "<font color=\"LEVEL\">" + ClassPathConfig.f55[0] + "</font>");
        }
        String string4 = Player2.getLeftSup2() == 0 ? replace4.replace("%statlefttwo%", ClassPathConfig.f56[0]) : (replace5 = Player2.getLeftSup2() == 1 || Player2.getLeftSup2() == 2 ? replace4.replace("%statlefttwo%", ClassPathConfig.f56[1]) : replace4.replace("%statlefttwo%", "<font color=\"LEVEL\">" + ClassPathConfig.f56[2] + "</font>"));
        if (Player2.getLeftSup2_1() == 0) {
            replace5 = replace5.replace("%statlefttwo_1%", ClassPathConfig.s[0]);
        } else if (Player2.getLeftSup2_1() == 1) {
            replace5 = replace5.replace("%statlefttwo_1%", "<font color=\"LEVEL\">" + ClassPathConfig.s[0] + "</font>");
        }
        if (Player2.getLeftSup2_2() == 0) {
            replace5 = replace5.replace("%statlefttwo_2%", ClassPathConfig.K[0]);
        } else if (Player2.getLeftSup2_2() == 1) {
            replace5 = replace5.replace("%statlefttwo_2%", "<font color=\"LEVEL\">" + ClassPathConfig.K[0] + "</font>");
        }
        String string5 = Player2.getRightSup() == 0 ? replace5.replace("%statright%", ClassPathConfig.f60[0]) : (replace6 = Player2.getRightSup() == 1 || Player2.getRightSup() == 2 ? replace5.replace("%statright%", ClassPathConfig.f60[1]) : replace5.replace("%statright%", "<font color=\"LEVEL\">" + ClassPathConfig.f60[2] + "</font>"));
        String string6 = Player2.getRightSup1() == 0 ? replace6.replace("%statrightone%", ClassPathConfig.f61[0]) : (replace7 = Player2.getRightSup1() == 1 || Player2.getRightSup1() == 2 ? replace6.replace("%statrightone%", ClassPathConfig.f61[1]) : replace6.replace("%statrightone%", "<font color=\"LEVEL\">" + ClassPathConfig.f61[2] + "</font>"));
        if (Player2.getRightSup1_1() == 0) {
            replace7 = replace7.replace("%statrightone_1%", ClassPathConfig.f63[0]);
        } else if (Player2.getRightSup1_1() == 1) {
            replace7 = replace7.replace("%statrightone_1%", "<font color=\"LEVEL\">" + ClassPathConfig.f63[0] + "</font>");
        }
        if (Player2.getRightSup1_2() == 0) {
            replace7 = replace7.replace("%statrightone_2%", ClassPathConfig.f65[0]);
        } else if (Player2.getRightSup1_2() == 1) {
            replace7 = replace7.replace("%statrightone_2%", "<font color=\"LEVEL\">" + ClassPathConfig.f65[0] + "</font>");
        }
        String string7 = Player2.getRightSup2() == 0 ? replace7.replace("%statrighttwo%", ClassPathConfig.b[0]) : (replace8 = Player2.getRightSup2() == 1 || Player2.getRightSup2() == 2 ? replace7.replace("%statrighttwo%", ClassPathConfig.b[1]) : replace7.replace("%statrighttwo%", "<font color=\"LEVEL\">" + ClassPathConfig.b[2] + "</font>"));
        if (Player2.getRightSup2_1() == 0) {
            replace8 = replace8.replace("%statrighttwo_1%", ClassPathConfig.f67[0]);
        } else if (Player2.getRightSup2_1() == 1) {
            replace8 = replace8.replace("%statrighttwo_1%", "<font color=\"LEVEL\">" + ClassPathConfig.f67[0] + "</font>");
        }
        if (Player2.getRightSup2_2() == 0) {
            replace8 = replace8.replace("%statrighttwo_2%", ClassPathConfig.f69[0]);
        } else if (Player2.getRightSup2_2() == 1) {
            replace8 = replace8.replace("%statrighttwo_2%", "<font color=\"LEVEL\">" + ClassPathConfig.f69[0] + "</font>");
        }
        return replace8;
    }

    private void E(Player Player2) {
        BBS.separateAndSend(HtmCache.getInstance().getNotNull("CommunityBoard/classpath/offensive.htm", Player2), Player2);
    }

    private void B(Player Player2) {
        BBS.separateAndSend(HtmCache.getInstance().getNotNull("CommunityBoard/classpath/mage.htm", Player2), Player2);
    }

    private void C(Player Player2) {
        BBS.separateAndSend(HtmCache.getInstance().getNotNull("CommunityBoard/classpath/def.htm", Player2), Player2);
    }

    private void A(Player Player2) {
        BBS.separateAndSend(HtmCache.getInstance().getNotNull("CommunityBoard/classpath/sup.htm", Player2), Player2);
    }

    private static void D(PlayerGab Player2) {
        Player2.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)Player2, (Creature)Player2, 888, 1, 1, 0L)});
        Player2.broadcastUserInfo(false, new UserInfoType[0]);
        Player2.incalekoscur();
    }

    public void parsecmd(String str, PlayerGab Player2) {
        if (str.equals("_bbsclasspath")) {
            this.F(Player2);
        } else if (str.equals("_bbsclasspathoff")) {
            this.E(Player2);
        } else if (str.equals("_bbsclasspathmage")) {
            this.B(Player2);
        } else if (str.equals("_bbsclasspathdef")) {
            this.C(Player2);
        } else if (str.equals("_bbsclasspathsup")) {
            this.A(Player2);
        } else if (str.equals("_bbscpmiddleoff")) {
            if (Player2.getMiddleOff() == 0) {
                Player2.incMiddleOff();
            } else if (Player2.getMiddleOff() == 1) {
                Player2.incMiddleOff();
            } else {
                if (Player2.getMiddleOff() != 2) {
                    this.E(Player2);
                    return;
                }
                Player2.incMiddleOff();
            }
            ClassPathsBBSManager.D(Player2);
            this.E(Player2);
        } else if (str.equals("_bbscpleftoff")) {
            if (Player2.getalekoscur() >= Player2.getalekos() || Player2.getRightOff() >= 1) {
                if (Player2.getalekoscur() >= Player2.getalekos()) {
                    Player2.sendMessage("You don't have enough path points.");
                }
                if (Player2.getRightOff() >= 1) {
                    Player2.sendMessage("You can't choose both left and right path.");
                }
                this.E(Player2);
                return;
            }
            if (Player2.getLeftOff() == 0 && Player2.getMiddleOff() >= 3) {
                Player2.incLeftOff();
            } else if (Player2.getLeftOff() == 1 && Player2.getMiddleOff() >= 3) {
                Player2.incLeftOff();
            } else {
                if (Player2.getLeftOff() != 2 || Player2.getMiddleOff() < 3) {
                    this.E(Player2);
                    return;
                }
                Player2.incLeftOff();
            }
            ClassPathsBBSManager.D(Player2);
            this.E(Player2);
        } else if (str.equals("_bbscpleftoffone")) {
            if (Player2.getalekoscur() >= Player2.getalekos()) {
                Player2.sendMessage("You don't have enough path points.");
                this.E(Player2);
            } else if (Player2.getLeftOff2() > 0) {
                Player2.sendMessage("You can't choose both left and right path.");
                this.E(Player2);
            } else {
                if (Player2.getLeftOff1() == 0 && Player2.getLeftOff() >= 3) {
                    Player2.incLeftOff1();
                } else if (Player2.getLeftOff1() == 1 && Player2.getLeftOff() >= 3) {
                    Player2.incLeftOff1();
                } else {
                    if (Player2.getRightOff1() >= 1) {
                        Player2.sendMessage("You can't choose both left and right path.");
                        this.E(Player2);
                        return;
                    }
                    if (Player2.getLeftOff1() != 2 || Player2.getLeftOff() < 3) {
                        this.E(Player2);
                        return;
                    }
                    Player2.incLeftOff1();
                }
                ClassPathsBBSManager.D(Player2);
                this.E(Player2);
            }
        } else if (str.equals("_bbscpleftoffone_1")) {
            if (Player2.getalekoscur() >= Player2.getalekos()) {
                Player2.sendMessage("You don't have enough path points.");
                this.E(Player2);
            } else if (Player2.getLeftOff1_2() > 0) {
                Player2.sendMessage("You can't choose both left and right path.");
                this.E(Player2);
            } else if (Player2.getLeftOff() < 3 || Player2.getLeftOff1() < 3) {
                this.E(Player2);
            } else {
                if (Player2.getLeftOff1_1() == 0) {
                    Player2.incLeftOff1_1();
                } else if (Player2.getLeftOff1_1() == 1) {
                    this.E(Player2);
                    return;
                }
                ClassPathsBBSManager.D(Player2);
                this.E(Player2);
            }
        } else if (str.equals("_bbscpleftoffone_2")) {
            if (Player2.getalekoscur() >= Player2.getalekos()) {
                Player2.sendMessage("You don't have enough path points.");
                this.E(Player2);
            } else if (Player2.getLeftOff1_1() > 0) {
                Player2.sendMessage("You can't choose both left and right path.");
                this.E(Player2);
            } else if (Player2.getLeftOff() < 3 || Player2.getLeftOff1() < 3) {
                this.E(Player2);
            } else {
                if (Player2.getLeftOff1_2() == 0) {
                    Player2.incLeftOff1_2();
                } else if (Player2.getLeftOff1_2() == 1) {
                    this.E(Player2);
                    return;
                }
                ClassPathsBBSManager.D(Player2);
                this.E(Player2);
            }
        } else if (str.equals("_bbscpleftofftwo")) {
            if (Player2.getalekoscur() >= Player2.getalekos()) {
                Player2.sendMessage("You don't have enough path points.");
                this.E(Player2);
            } else if (Player2.getLeftOff1() > 0) {
                Player2.sendMessage("You can't choose both left and right path.");
                this.E(Player2);
            } else {
                if (Player2.getLeftOff2() == 0 && Player2.getLeftOff() >= 3) {
                    Player2.incLeftOff2();
                } else if (Player2.getLeftOff2() == 1 && Player2.getLeftOff() >= 3) {
                    Player2.incLeftOff2();
                } else {
                    if (Player2.getLeftOff2() != 2 || Player2.getLeftOff() < 3) {
                        this.E(Player2);
                        return;
                    }
                    Player2.incLeftOff2();
                }
                ClassPathsBBSManager.D(Player2);
                this.E(Player2);
            }
        } else if (str.equals("_bbscpleftofftwo_1")) {
            if (Player2.getalekoscur() >= Player2.getalekos()) {
                Player2.sendMessage("You don't have enough path points.");
                this.E(Player2);
            } else if (Player2.getLeftOff2_2() > 0) {
                Player2.sendMessage("You can't choose both left and right path.");
                this.E(Player2);
            } else if (Player2.getLeftOff() < 3 || Player2.getLeftOff2() < 3) {
                this.E(Player2);
            } else {
                if (Player2.getLeftOff2_1() == 0) {
                    Player2.incLeftOff2_1();
                } else if (Player2.getLeftOff2_1() == 1) {
                    this.E(Player2);
                    return;
                }
                ClassPathsBBSManager.D(Player2);
                this.E(Player2);
            }
        } else if (str.equals("_bbscpleftofftwo_2")) {
            if (Player2.getalekoscur() >= Player2.getalekos()) {
                Player2.sendMessage("You don't have enough path points.");
                this.E(Player2);
            } else if (Player2.getLeftOff2_1() > 0) {
                Player2.sendMessage("You can't choose both left and right path.");
                this.E(Player2);
            } else if (Player2.getLeftOff() < 3 || Player2.getLeftOff2() < 3) {
                this.E(Player2);
            } else {
                if (Player2.getLeftOff2_2() == 0) {
                    Player2.incLeftOff2_2();
                } else if (Player2.getLeftOff2_2() == 1) {
                    this.E(Player2);
                    return;
                }
                ClassPathsBBSManager.D(Player2);
                this.E(Player2);
            }
        } else if (str.equals("_bbscprightoff")) {
            if (Player2.getalekoscur() >= Player2.getalekos() || Player2.getLeftOff() >= 1) {
                if (Player2.getalekoscur() >= Player2.getalekos()) {
                    Player2.sendMessage("You don't have enough path points.");
                }
                if (Player2.getLeftOff() >= 1) {
                    Player2.sendMessage("You can't choose both left and right path.");
                }
                this.E(Player2);
                return;
            }
            if (Player2.getRightOff() == 0 && Player2.getMiddleOff() >= 3) {
                Player2.incRightOff();
            } else if (Player2.getRightOff() == 1 && Player2.getMiddleOff() >= 3) {
                Player2.incRightOff();
            } else {
                if (Player2.getRightOff() != 2 || Player2.getMiddleOff() < 3) {
                    this.E(Player2);
                    return;
                }
                Player2.incRightOff();
            }
            ClassPathsBBSManager.D(Player2);
            this.E(Player2);
        } else if (str.equals("_bbscprightoffone")) {
            if (Player2.getalekoscur() >= Player2.getalekos()) {
                Player2.sendMessage("You don't have enough path points.");
                this.E(Player2);
            } else if (Player2.getRightOff2() > 0) {
                Player2.sendMessage("You can't choose both left and right path.");
                this.E(Player2);
            } else {
                if (Player2.getRightOff1() == 0 && Player2.getRightOff() >= 3) {
                    Player2.incRightOff1();
                } else if (Player2.getRightOff1() == 1 && Player2.getRightOff() >= 3) {
                    Player2.incRightOff1();
                } else {
                    if (Player2.getRightOff1() != 2 || Player2.getRightOff() < 3) {
                        this.E(Player2);
                        return;
                    }
                    Player2.incRightOff1();
                }
                ClassPathsBBSManager.D(Player2);
                this.E(Player2);
            }
        } else if (str.equals("_bbscprightoffone_1")) {
            if (Player2.getalekoscur() >= Player2.getalekos()) {
                Player2.sendMessage("You don't have enough path points.");
                this.E(Player2);
            } else if (Player2.getRightOff1_2() > 0) {
                Player2.sendMessage("You can't choose both left and right path.");
                this.E(Player2);
            } else if (Player2.getRightOff() < 3 || Player2.getRightOff1() < 3) {
                this.E(Player2);
            } else {
                if (Player2.getRightOff1_1() == 0) {
                    Player2.incRightOff1_1();
                } else if (Player2.getRightOff1_1() == 1) {
                    this.E(Player2);
                    return;
                }
                ClassPathsBBSManager.D(Player2);
                this.E(Player2);
            }
        } else if (str.equals("_bbscprightoffone_2")) {
            if (Player2.getalekoscur() >= Player2.getalekos()) {
                Player2.sendMessage("You don't have enough path points.");
                this.E(Player2);
            } else if (Player2.getRightOff1_1() > 0) {
                Player2.sendMessage("You can't choose both left and right path.");
                this.E(Player2);
            } else if (Player2.getRightOff() < 3 || Player2.getRightOff1() < 3) {
                this.E(Player2);
            } else {
                if (Player2.getRightOff1_2() == 0) {
                    Player2.incRightOff1_2();
                } else if (Player2.getRightOff1_2() == 1) {
                    this.E(Player2);
                    return;
                }
                ClassPathsBBSManager.D(Player2);
                this.E(Player2);
            }
        } else if (str.equals("_bbscprightofftwo")) {
            if (Player2.getalekoscur() >= Player2.getalekos()) {
                Player2.sendMessage("You don't have enough path points.");
                this.E(Player2);
            } else if (Player2.getRightOff1() > 0) {
                Player2.sendMessage("You can't choose both left and right path.");
                this.E(Player2);
            } else {
                if (Player2.getRightOff2() == 0 && Player2.getRightOff() >= 3) {
                    Player2.incRightOff2();
                } else if (Player2.getRightOff2() == 1 && Player2.getRightOff() >= 3) {
                    Player2.incRightOff2();
                } else {
                    if (Player2.getRightOff2() != 2 || Player2.getRightOff() < 3) {
                        this.E(Player2);
                        return;
                    }
                    Player2.incRightOff2();
                }
                ClassPathsBBSManager.D(Player2);
                this.E(Player2);
            }
        } else if (str.equals("_bbscprightofftwo_1")) {
            if (Player2.getalekoscur() >= Player2.getalekos()) {
                Player2.sendMessage("You don't have enough path points.");
                this.E(Player2);
            } else if (Player2.getRightOff2_2() > 0) {
                Player2.sendMessage("You can't choose both left and right path.");
                this.E(Player2);
            } else if (Player2.getRightOff() < 3 || Player2.getRightOff2() < 3) {
                this.E(Player2);
            } else {
                if (Player2.getRightOff2_1() == 0) {
                    Player2.incRightOff2_1();
                } else if (Player2.getRightOff2_1() == 1) {
                    this.E(Player2);
                    return;
                }
                ClassPathsBBSManager.D(Player2);
                this.E(Player2);
            }
        } else if (str.equals("_bbscprightofftwo_2")) {
            if (Player2.getalekoscur() >= Player2.getalekos()) {
                Player2.sendMessage("You don't have enough path points.");
                this.E(Player2);
            } else if (Player2.getRightOff2_1() > 0) {
                Player2.sendMessage("You can't choose both left and right path.");
                this.E(Player2);
            } else if (Player2.getRightOff() < 3 || Player2.getRightOff2() < 3) {
                this.E(Player2);
            } else {
                if (Player2.getRightOff2_2() == 0) {
                    Player2.incRightOff2_2();
                } else if (Player2.getRightOff2_2() == 1) {
                    this.E(Player2);
                    return;
                }
                ClassPathsBBSManager.D(Player2);
                this.E(Player2);
            }
        } else if (str.equals("_bbscpmiddlemage")) {
            if (Player2.getalekoscur() >= Player2.getalekos()) {
                Player2.sendMessage("You don't have enough path points.");
                this.B(Player2);
                return;
            }
            if (Player2.getMiddleMage() == 0) {
                Player2.incMiddleMage();
            } else if (Player2.getMiddleMage() == 1) {
                Player2.incMiddleMage();
            } else {
                if (Player2.getMiddleMage() != 2) {
                    this.B(Player2);
                    return;
                }
                Player2.incMiddleMage();
            }
            ClassPathsBBSManager.D(Player2);
            this.B(Player2);
        } else if (str.equals("_bbscpleftmage")) {
            if (Player2.getalekoscur() >= Player2.getalekos() || Player2.getRightMage() >= 1) {
                if (Player2.getalekoscur() >= Player2.getalekos()) {
                    Player2.sendMessage("You don't have enough path points.");
                }
                if (Player2.getRightMage() >= 1) {
                    Player2.sendMessage("You can't choose both left and right path.");
                }
                this.B(Player2);
                return;
            }
            if (Player2.getLeftMage() == 0 && Player2.getMiddleMage() >= 3) {
                Player2.incLeftMage();
            } else if (Player2.getLeftMage() == 1 && Player2.getMiddleMage() >= 3) {
                Player2.incLeftMage();
            } else {
                if (Player2.getLeftMage() != 2 || Player2.getMiddleMage() < 3) {
                    this.B(Player2);
                    return;
                }
                Player2.incLeftMage();
            }
            ClassPathsBBSManager.D(Player2);
            this.B(Player2);
        } else if (str.equals("_bbscpleftmageone")) {
            if (Player2.getalekoscur() >= Player2.getalekos()) {
                Player2.sendMessage("You don't have enough path points.");
                this.B(Player2);
            } else if (Player2.getLeftMage2() > 0) {
                Player2.sendMessage("You can't choose both left and right path.");
                this.B(Player2);
            } else {
                if (Player2.getLeftMage1() == 0 && Player2.getLeftMage() >= 3) {
                    Player2.incLeftMage1();
                } else if (Player2.getLeftMage1() == 1 && Player2.getLeftMage() >= 3) {
                    Player2.incLeftMage1();
                } else {
                    if (Player2.getLeftMage1() != 2 || Player2.getLeftMage() < 3) {
                        this.B(Player2);
                        return;
                    }
                    Player2.incLeftMage1();
                }
                ClassPathsBBSManager.D(Player2);
                this.B(Player2);
            }
        } else if (str.equals("_bbscpleftmageone_1")) {
            if (Player2.getalekoscur() >= Player2.getalekos()) {
                Player2.sendMessage("You don't have enough path points.");
                this.B(Player2);
            } else if (Player2.getLeftMage1_2() > 0) {
                Player2.sendMessage("You can't choose both left and right path.");
                this.B(Player2);
            } else if (Player2.getLeftMage() < 3 || Player2.getLeftMage1() < 3) {
                this.B(Player2);
            } else {
                if (Player2.getLeftMage1_1() == 0) {
                    Player2.incLeftMage1_1();
                } else if (Player2.getLeftMage1_1() == 1) {
                    this.B(Player2);
                    return;
                }
                ClassPathsBBSManager.D(Player2);
                this.B(Player2);
            }
        } else if (str.equals("_bbscpleftmageone_2")) {
            if (Player2.getalekoscur() >= Player2.getalekos()) {
                Player2.sendMessage("You don't have enough path points.");
                this.B(Player2);
            } else if (Player2.getLeftMage1_1() > 0) {
                Player2.sendMessage("You can't choose both left and right path.");
                this.B(Player2);
            } else if (Player2.getLeftMage() < 3 || Player2.getLeftMage1() < 3) {
                this.B(Player2);
            } else {
                if (Player2.getLeftMage1_2() == 0) {
                    Player2.incLeftMage1_2();
                } else if (Player2.getLeftMage1_2() == 1) {
                    this.B(Player2);
                    return;
                }
                ClassPathsBBSManager.D(Player2);
                this.B(Player2);
            }
        } else if (str.equals("_bbscpleftmagetwo")) {
            if (Player2.getalekoscur() >= Player2.getalekos()) {
                Player2.sendMessage("You don't have enough path points.");
                this.B(Player2);
            } else if (Player2.getLeftMage1() > 0) {
                Player2.sendMessage("You can't choose both left and right path.");
                this.B(Player2);
            } else {
                if (Player2.getLeftMage2() == 0 && Player2.getLeftMage() >= 3) {
                    Player2.incLeftMage2();
                } else if (Player2.getLeftMage2() == 1 && Player2.getLeftMage() >= 3) {
                    Player2.incLeftMage2();
                } else {
                    if (Player2.getLeftMage2() != 2 || Player2.getLeftMage() < 3) {
                        this.B(Player2);
                        return;
                    }
                    Player2.incLeftMage2();
                }
                ClassPathsBBSManager.D(Player2);
                this.B(Player2);
            }
        } else if (str.equals("_bbscpleftmagetwo_1")) {
            if (Player2.getalekoscur() >= Player2.getalekos()) {
                Player2.sendMessage("You don't have enough path points.");
                this.B(Player2);
            } else if (Player2.getLeftMage2_2() > 0) {
                Player2.sendMessage("You can't choose both left and right path.");
                this.B(Player2);
            } else if (Player2.getLeftMage() < 3 || Player2.getLeftMage2() < 3) {
                this.B(Player2);
            } else {
                if (Player2.getLeftMage2_1() == 0) {
                    Player2.incLeftMage2_1();
                } else if (Player2.getLeftMage2_1() == 1) {
                    this.B(Player2);
                    return;
                }
                ClassPathsBBSManager.D(Player2);
                this.B(Player2);
            }
        } else if (str.equals("_bbscpleftmagetwo_2")) {
            if (Player2.getalekoscur() >= Player2.getalekos()) {
                Player2.sendMessage("You don't have enough path points.");
                this.B(Player2);
            } else if (Player2.getLeftMage2_1() > 0) {
                Player2.sendMessage("You can't choose both left and right path.");
                this.B(Player2);
            } else if (Player2.getLeftMage() < 3 || Player2.getLeftMage2() < 3) {
                this.B(Player2);
            } else {
                if (Player2.getLeftMage2_2() == 0) {
                    Player2.incLeftMage2_2();
                } else if (Player2.getLeftMage2_2() == 1) {
                    this.B(Player2);
                    return;
                }
                ClassPathsBBSManager.D(Player2);
                this.B(Player2);
            }
        } else if (str.equals("_bbscprightmage")) {
            if (Player2.getalekoscur() >= Player2.getalekos() || Player2.getLeftMage() >= 1) {
                if (Player2.getalekoscur() >= Player2.getalekos()) {
                    Player2.sendMessage("You don't have enough path points.");
                }
                if (Player2.getLeftMage() >= 1) {
                    Player2.sendMessage("You can't choose both left and right path.");
                }
                this.B(Player2);
                return;
            }
            if (Player2.getRightMage() == 0 && Player2.getMiddleMage() >= 3) {
                Player2.incRightMage();
            } else if (Player2.getRightMage() == 1 && Player2.getMiddleMage() >= 3) {
                Player2.incRightMage();
            } else {
                if (Player2.getRightMage() != 2 || Player2.getMiddleMage() < 3) {
                    this.B(Player2);
                    return;
                }
                Player2.incRightMage();
            }
            ClassPathsBBSManager.D(Player2);
            this.B(Player2);
        } else if (str.equals("_bbscprightmageone")) {
            if (Player2.getalekoscur() >= Player2.getalekos()) {
                Player2.sendMessage("You don't have enough path points.");
                this.B(Player2);
            } else if (Player2.getRightMage2() > 0) {
                Player2.sendMessage("You can't choose both left and right path.");
                this.B(Player2);
            } else {
                if (Player2.getRightMage1() == 0 && Player2.getRightMage() >= 3) {
                    Player2.incRightMage1();
                } else if (Player2.getRightMage1() == 1 && Player2.getRightMage() >= 3) {
                    Player2.incRightMage1();
                } else {
                    if (Player2.getRightMage1() != 2 || Player2.getRightMage() < 3) {
                        this.B(Player2);
                        return;
                    }
                    Player2.incRightMage1();
                }
                ClassPathsBBSManager.D(Player2);
                this.B(Player2);
            }
        } else if (str.equals("_bbscprightmageone_1")) {
            if (Player2.getalekoscur() >= Player2.getalekos()) {
                Player2.sendMessage("You don't have enough path points.");
                this.B(Player2);
            } else if (Player2.getRightMage1_2() > 0) {
                Player2.sendMessage("You can't choose both left and right path.");
                this.B(Player2);
            } else if (Player2.getRightMage() < 3 || Player2.getRightMage1() < 3) {
                this.B(Player2);
            } else {
                if (Player2.getRightMage1_1() == 0) {
                    Player2.incRightMage1_1();
                } else if (Player2.getRightMage1_1() == 1) {
                    this.B(Player2);
                    return;
                }
                ClassPathsBBSManager.D(Player2);
                this.B(Player2);
            }
        } else if (str.equals("_bbscprightmageone_2")) {
            if (Player2.getalekoscur() >= Player2.getalekos()) {
                Player2.sendMessage("You don't have enough path points.");
                this.B(Player2);
            } else if (Player2.getRightMage1_1() > 0) {
                Player2.sendMessage("You can't choose both left and right path.");
                this.B(Player2);
            } else if (Player2.getRightMage() < 3 || Player2.getRightMage1() < 3) {
                this.B(Player2);
            } else {
                if (Player2.getRightMage1_2() == 0) {
                    Player2.incRightMage1_2();
                } else if (Player2.getRightMage1_2() == 1) {
                    this.B(Player2);
                    return;
                }
                ClassPathsBBSManager.D(Player2);
                this.B(Player2);
            }
        } else if (str.equals("_bbscprightmagetwo")) {
            if (Player2.getalekoscur() >= Player2.getalekos()) {
                Player2.sendMessage("You don't have enough path points.");
                this.B(Player2);
            } else if (Player2.getRightMage1() > 0) {
                Player2.sendMessage("You can't choose both left and right path.");
                this.B(Player2);
            } else {
                if (Player2.getRightMage2() == 0 && Player2.getRightMage() >= 3) {
                    Player2.incRightMage2();
                } else if (Player2.getRightMage2() == 1 && Player2.getRightMage() >= 3) {
                    Player2.incRightMage2();
                } else {
                    if (Player2.getRightMage2() != 2 || Player2.getRightMage() < 3) {
                        this.B(Player2);
                        return;
                    }
                    Player2.incRightMage2();
                }
                ClassPathsBBSManager.D(Player2);
                this.B(Player2);
            }
        } else if (str.equals("_bbscprightmagetwo_1")) {
            if (Player2.getalekoscur() >= Player2.getalekos()) {
                Player2.sendMessage("You don't have enough path points.");
                this.B(Player2);
            } else if (Player2.getRightMage2_2() > 0) {
                Player2.sendMessage("You can't choose both left and right path.");
                this.B(Player2);
            } else if (Player2.getRightMage() < 3 || Player2.getRightMage2() < 3) {
                this.B(Player2);
            } else {
                if (Player2.getRightMage2_1() == 0) {
                    Player2.incRightMage2_1();
                } else if (Player2.getRightMage2_1() == 1) {
                    this.B(Player2);
                    return;
                }
                ClassPathsBBSManager.D(Player2);
                this.B(Player2);
            }
        } else if (str.equals("_bbscprightmagetwo_2")) {
            if (Player2.getalekoscur() >= Player2.getalekos()) {
                Player2.sendMessage("You don't have enough path points.");
                this.B(Player2);
            } else if (Player2.getRightMage2_1() > 0) {
                Player2.sendMessage("You can't choose both left and right path.");
                this.B(Player2);
            } else if (Player2.getRightMage() < 3 || Player2.getRightMage2() < 3) {
                this.B(Player2);
            } else {
                if (Player2.getRightMage2_2() == 0) {
                    Player2.incRightMage2_2();
                } else if (Player2.getRightMage2_2() == 1) {
                    this.B(Player2);
                    return;
                }
                ClassPathsBBSManager.D(Player2);
                this.B(Player2);
            }
        } else if (str.equals("_bbscpmiddledef")) {
            if (Player2.getalekoscur() >= Player2.getalekos()) {
                Player2.sendMessage("You don't have enough path points.");
                this.C(Player2);
                return;
            }
            if (Player2.getMiddleDef() == 0) {
                Player2.incMiddleDef();
            } else if (Player2.getMiddleDef() == 1) {
                Player2.incMiddleDef();
            } else {
                if (Player2.getMiddleDef() != 2) {
                    this.C(Player2);
                    return;
                }
                Player2.incMiddleDef();
            }
            ClassPathsBBSManager.D(Player2);
            this.C(Player2);
        } else if (str.equals("_bbscpleftdef")) {
            if (Player2.getalekoscur() >= Player2.getalekos() || Player2.getRightDef() >= 1) {
                if (Player2.getalekoscur() >= Player2.getalekos()) {
                    Player2.sendMessage("You don't have enough path points.");
                }
                if (Player2.getRightDef() >= 1) {
                    Player2.sendMessage("You can't choose both left and right path.");
                }
                this.C(Player2);
                return;
            }
            if (Player2.getLeftDef() == 0 && Player2.getMiddleDef() >= 3) {
                Player2.incLeftDef();
            } else if (Player2.getLeftDef() == 1 && Player2.getMiddleDef() >= 3) {
                Player2.incLeftDef();
            } else {
                if (Player2.getLeftDef() != 2 || Player2.getMiddleDef() < 3) {
                    this.C(Player2);
                    return;
                }
                Player2.incLeftDef();
            }
            ClassPathsBBSManager.D(Player2);
            this.C(Player2);
        } else if (str.equals("_bbscpleftdefone")) {
            if (Player2.getalekoscur() >= Player2.getalekos()) {
                Player2.sendMessage("You don't have enough path points.");
                this.C(Player2);
            } else if (Player2.getLeftDef2() > 0) {
                Player2.sendMessage("You can't choose both left and right path.");
                this.C(Player2);
            } else {
                if (Player2.getLeftDef1() == 0 && Player2.getLeftDef() >= 3) {
                    Player2.incLeftDef1();
                } else if (Player2.getLeftDef1() == 1 && Player2.getLeftDef() >= 3) {
                    Player2.incLeftDef1();
                } else {
                    if (Player2.getLeftDef1() != 2 || Player2.getLeftDef() < 3) {
                        this.C(Player2);
                        return;
                    }
                    Player2.incLeftDef1();
                }
                ClassPathsBBSManager.D(Player2);
                this.C(Player2);
            }
        } else if (str.equals("_bbscpleftdefone_1")) {
            if (Player2.getalekoscur() >= Player2.getalekos()) {
                Player2.sendMessage("You don't have enough path points.");
                this.C(Player2);
            } else if (Player2.getLeftDef1_2() > 0) {
                Player2.sendMessage("You can't choose both left and right path.");
                this.C(Player2);
            } else if (Player2.getLeftDef() < 3 || Player2.getLeftDef1() < 3) {
                this.C(Player2);
            } else {
                if (Player2.getLeftDef1_1() == 0) {
                    Player2.incLeftDef1_1();
                } else if (Player2.getLeftDef1_1() == 1) {
                    this.C(Player2);
                    return;
                }
                ClassPathsBBSManager.D(Player2);
                this.C(Player2);
            }
        } else if (str.equals("_bbscpleftdefone_2")) {
            if (Player2.getalekoscur() >= Player2.getalekos()) {
                Player2.sendMessage("You don't have enough path points.");
                this.C(Player2);
            } else if (Player2.getLeftDef1_1() > 0) {
                Player2.sendMessage("You can't choose both left and right path.");
                this.C(Player2);
            } else if (Player2.getLeftDef() < 3 || Player2.getLeftDef1() < 3) {
                this.C(Player2);
            } else {
                if (Player2.getLeftDef1_2() == 0) {
                    Player2.incLeftDef1_2();
                } else if (Player2.getLeftDef1_2() == 1) {
                    this.C(Player2);
                    return;
                }
                ClassPathsBBSManager.D(Player2);
                this.C(Player2);
            }
        } else if (str.equals("_bbscpleftdeftwo")) {
            if (Player2.getalekoscur() >= Player2.getalekos()) {
                Player2.sendMessage("You don't have enough path points.");
                this.C(Player2);
            } else if (Player2.getLeftDef1() > 0) {
                Player2.sendMessage("You can't choose both left and right path.");
                this.C(Player2);
            } else {
                if (Player2.getLeftDef2() == 0 && Player2.getLeftDef() >= 3) {
                    Player2.incLeftDef2();
                } else if (Player2.getLeftDef2() == 1 && Player2.getLeftDef() >= 3) {
                    Player2.incLeftDef2();
                } else {
                    if (Player2.getLeftDef2() != 2 || Player2.getLeftDef() < 3) {
                        this.C(Player2);
                        return;
                    }
                    Player2.incLeftDef2();
                }
                ClassPathsBBSManager.D(Player2);
                this.C(Player2);
            }
        } else if (str.equals("_bbscpleftdeftwo_1")) {
            if (Player2.getalekoscur() >= Player2.getalekos()) {
                Player2.sendMessage("You don't have enough path points.");
                this.C(Player2);
            } else if (Player2.getLeftDef2_2() > 0) {
                Player2.sendMessage("You can't choose both left and right path.");
                this.C(Player2);
            } else if (Player2.getLeftDef() < 3 || Player2.getLeftDef2() < 3) {
                this.C(Player2);
            } else {
                if (Player2.getLeftDef2_1() == 0) {
                    Player2.incLeftDef2_1();
                } else if (Player2.getLeftDef2_1() == 1) {
                    this.C(Player2);
                    return;
                }
                ClassPathsBBSManager.D(Player2);
                this.C(Player2);
            }
        } else if (str.equals("_bbscpleftdeftwo_2")) {
            if (Player2.getalekoscur() >= Player2.getalekos()) {
                Player2.sendMessage("You don't have enough path points.");
                this.C(Player2);
            } else if (Player2.getLeftDef2_1() > 0) {
                Player2.sendMessage("You can't choose both left and right path.");
                this.C(Player2);
            } else if (Player2.getLeftDef() < 3 || Player2.getLeftDef2() < 3) {
                this.C(Player2);
            } else {
                if (Player2.getLeftDef2_2() == 0) {
                    Player2.incLeftDef2_2();
                } else if (Player2.getLeftDef2_2() == 1) {
                    this.C(Player2);
                    return;
                }
                ClassPathsBBSManager.D(Player2);
                this.C(Player2);
            }
        } else if (str.equals("_bbscprightdef")) {
            if (Player2.getalekoscur() >= Player2.getalekos() || Player2.getLeftDef() >= 1) {
                if (Player2.getalekoscur() >= Player2.getalekos()) {
                    Player2.sendMessage("You don't have enough path points.");
                }
                if (Player2.getLeftDef() >= 1) {
                    Player2.sendMessage("You can't choose both left and right path.");
                }
                this.C(Player2);
                return;
            }
            if (Player2.getRightDef() == 0 && Player2.getMiddleDef() >= 3) {
                Player2.incRightDef();
            } else if (Player2.getRightDef() == 1 && Player2.getMiddleDef() >= 3) {
                Player2.incRightDef();
            } else {
                if (Player2.getRightDef() != 2 || Player2.getMiddleDef() < 3) {
                    this.C(Player2);
                    return;
                }
                Player2.incRightDef();
            }
            ClassPathsBBSManager.D(Player2);
            this.C(Player2);
        } else if (str.equals("_bbscprightdefone")) {
            if (Player2.getalekoscur() >= Player2.getalekos()) {
                Player2.sendMessage("You don't have enough path points.");
                this.C(Player2);
            } else if (Player2.getRightDef2() > 0) {
                Player2.sendMessage("You can't choose both left and right path.");
                this.C(Player2);
            } else {
                if (Player2.getRightDef1() == 0 && Player2.getRightDef() >= 3) {
                    Player2.incRightDef1();
                } else if (Player2.getRightDef1() == 1 && Player2.getRightDef() >= 3) {
                    Player2.incRightDef1();
                } else {
                    if (Player2.getRightDef1() != 2 || Player2.getRightDef() < 3) {
                        this.C(Player2);
                        return;
                    }
                    Player2.incRightDef1();
                }
                ClassPathsBBSManager.D(Player2);
                this.C(Player2);
            }
        } else if (str.equals("_bbscprightdefone_1")) {
            if (Player2.getalekoscur() >= Player2.getalekos()) {
                Player2.sendMessage("You don't have enough path points.");
                this.C(Player2);
            } else if (Player2.getRightDef1_2() > 0) {
                Player2.sendMessage("You can't choose both left and right path.");
                this.C(Player2);
            } else if (Player2.getRightDef() < 3 || Player2.getRightDef1() < 3) {
                this.C(Player2);
            } else {
                if (Player2.getRightDef1_1() == 0) {
                    Player2.incRightDef1_1();
                } else if (Player2.getRightDef1_1() == 1) {
                    this.C(Player2);
                    return;
                }
                ClassPathsBBSManager.D(Player2);
                this.C(Player2);
            }
        } else if (str.equals("_bbscprightdefone_2")) {
            if (Player2.getalekoscur() >= Player2.getalekos()) {
                Player2.sendMessage("You don't have enough path points.");
                this.C(Player2);
            } else if (Player2.getRightDef1_1() > 0) {
                Player2.sendMessage("You can't choose both left and right path.");
                this.C(Player2);
            } else if (Player2.getRightDef() < 3 || Player2.getRightDef1() < 3) {
                this.C(Player2);
            } else {
                if (Player2.getRightDef1_2() == 0) {
                    Player2.incRightDef1_2();
                } else if (Player2.getRightDef1_2() == 1) {
                    this.C(Player2);
                    return;
                }
                ClassPathsBBSManager.D(Player2);
                this.C(Player2);
            }
        } else if (str.equals("_bbscprightdeftwo")) {
            if (Player2.getalekoscur() >= Player2.getalekos()) {
                Player2.sendMessage("You don't have enough path points.");
                this.C(Player2);
            } else if (Player2.getRightDef1() > 0) {
                Player2.sendMessage("You can't choose both left and right path.");
                this.C(Player2);
            } else {
                if (Player2.getRightDef2() == 0 && Player2.getRightDef() >= 3) {
                    Player2.incRightDef2();
                } else if (Player2.getRightDef2() == 1 && Player2.getRightDef() >= 3) {
                    Player2.incRightDef2();
                } else {
                    if (Player2.getRightDef2() != 2 || Player2.getRightDef() < 3) {
                        this.C(Player2);
                        return;
                    }
                    Player2.incRightDef2();
                }
                ClassPathsBBSManager.D(Player2);
                this.C(Player2);
            }
        } else if (str.equals("_bbscprightdeftwo_1")) {
            if (Player2.getalekoscur() >= Player2.getalekos()) {
                Player2.sendMessage("You don't have enough path points.");
                this.C(Player2);
            } else if (Player2.getRightDef2_2() > 0) {
                Player2.sendMessage("You can't choose both left and right path.");
                this.C(Player2);
            } else if (Player2.getRightDef() < 3 || Player2.getRightDef2() < 3) {
                this.C(Player2);
            } else {
                if (Player2.getRightDef2_1() == 0) {
                    Player2.incRightDef2_1();
                } else if (Player2.getRightDef2_1() == 1) {
                    this.C(Player2);
                    return;
                }
                ClassPathsBBSManager.D(Player2);
                this.C(Player2);
            }
        } else if (str.equals("_bbscprightdeftwo_2")) {
            if (Player2.getalekoscur() >= Player2.getalekos()) {
                Player2.sendMessage("You don't have enough path points.");
                this.C(Player2);
            } else if (Player2.getRightDef2_1() > 0) {
                Player2.sendMessage("You can't choose both left and right path.");
                this.C(Player2);
            } else if (Player2.getRightDef() < 3 || Player2.getRightDef2() < 3) {
                this.C(Player2);
            } else {
                if (Player2.getRightDef2_2() == 0) {
                    Player2.incRightDef2_2();
                } else if (Player2.getRightDef2_2() == 1) {
                    this.C(Player2);
                    return;
                }
                ClassPathsBBSManager.D(Player2);
                this.C(Player2);
            }
        } else if (str.equals("_bbscpmiddlesup")) {
            if (Player2.getalekoscur() >= Player2.getalekos()) {
                Player2.sendMessage("You don't have enough path points.");
                this.A(Player2);
                return;
            }
            if (Player2.getMiddleSup() == 0) {
                Player2.incMiddleSup();
            } else if (Player2.getMiddleSup() == 1) {
                Player2.incMiddleSup();
            } else {
                if (Player2.getMiddleSup() != 2) {
                    this.A(Player2);
                    return;
                }
                Player2.incMiddleSup();
            }
            ClassPathsBBSManager.D(Player2);
            this.A(Player2);
        } else if (str.equals("_bbscpleftsup")) {
            if (Player2.getalekoscur() >= Player2.getalekos() || Player2.getRightSup() >= 1) {
                if (Player2.getalekoscur() >= Player2.getalekos()) {
                    Player2.sendMessage("You don't have enough path points.");
                }
                if (Player2.getRightSup() >= 1) {
                    Player2.sendMessage("You can't choose both left and right path.");
                }
                this.A(Player2);
                return;
            }
            if (Player2.getLeftSup() == 0 && Player2.getMiddleSup() >= 3) {
                Player2.incLeftSup();
            } else if (Player2.getLeftSup() == 1 && Player2.getMiddleSup() >= 3) {
                Player2.incLeftSup();
            } else {
                if (Player2.getLeftSup() != 2 || Player2.getMiddleSup() < 3) {
                    this.A(Player2);
                    return;
                }
                Player2.incLeftSup();
            }
            ClassPathsBBSManager.D(Player2);
            this.A(Player2);
        } else if (str.equals("_bbscpleftsupone")) {
            if (Player2.getalekoscur() >= Player2.getalekos()) {
                Player2.sendMessage("You don't have enough path points.");
                this.A(Player2);
            } else if (Player2.getLeftSup2() > 0) {
                Player2.sendMessage("You can't choose both left and right path.");
                this.A(Player2);
            } else {
                if (Player2.getLeftSup1() == 0 && Player2.getLeftSup() >= 3) {
                    Player2.incLeftSup1();
                } else if (Player2.getLeftSup1() == 1 && Player2.getLeftSup() >= 3) {
                    Player2.incLeftSup1();
                } else {
                    if (Player2.getLeftSup1() != 2 || Player2.getLeftSup() < 3) {
                        this.A(Player2);
                        return;
                    }
                    Player2.incLeftSup1();
                }
                ClassPathsBBSManager.D(Player2);
                this.A(Player2);
            }
        } else if (str.equals("_bbscpleftsupone_1")) {
            if (Player2.getalekoscur() >= Player2.getalekos()) {
                Player2.sendMessage("You don't have enough path points.");
                this.A(Player2);
            } else if (Player2.getLeftSup1_2() > 0) {
                Player2.sendMessage("You can't choose both left and right path.");
                this.A(Player2);
            } else if (Player2.getLeftSup() < 3 || Player2.getLeftSup1() < 3) {
                this.A(Player2);
            } else {
                if (Player2.getLeftSup1_1() == 0) {
                    Player2.incLeftSup1_1();
                } else if (Player2.getLeftSup1_1() == 1) {
                    this.A(Player2);
                    return;
                }
                ClassPathsBBSManager.D(Player2);
                this.A(Player2);
            }
        } else if (str.equals("_bbscpleftsupone_2")) {
            if (Player2.getalekoscur() >= Player2.getalekos()) {
                Player2.sendMessage("You don't have enough path points.");
                this.A(Player2);
            } else if (Player2.getLeftSup1_1() > 0) {
                Player2.sendMessage("You can't choose both left and right path.");
                this.A(Player2);
            } else if (Player2.getLeftSup() < 3 || Player2.getLeftSup1() < 3) {
                this.A(Player2);
            } else {
                if (Player2.getLeftSup1_2() == 0) {
                    Player2.incLeftSup1_2();
                } else if (Player2.getLeftSup1_2() == 1) {
                    this.A(Player2);
                    return;
                }
                ClassPathsBBSManager.D(Player2);
                this.A(Player2);
            }
        } else if (str.equals("_bbscpleftsuptwo")) {
            if (Player2.getalekoscur() >= Player2.getalekos()) {
                Player2.sendMessage("You don't have enough path points.");
                this.A(Player2);
            } else if (Player2.getLeftSup1() > 0) {
                Player2.sendMessage("You can't choose both left and right path.");
                this.A(Player2);
            } else {
                if (Player2.getLeftSup2() == 0 && Player2.getLeftSup() >= 3) {
                    Player2.incLeftSup2();
                } else if (Player2.getLeftSup2() == 1 && Player2.getLeftSup() >= 3) {
                    Player2.incLeftSup2();
                } else {
                    if (Player2.getLeftSup2() != 2 || Player2.getLeftSup() < 3) {
                        this.A(Player2);
                        return;
                    }
                    Player2.incLeftSup2();
                }
                ClassPathsBBSManager.D(Player2);
                this.A(Player2);
            }
        } else if (str.equals("_bbscpleftsuptwo_1")) {
            if (Player2.getalekoscur() >= Player2.getalekos()) {
                Player2.sendMessage("You don't have enough path points.");
                this.A(Player2);
            } else if (Player2.getLeftSup2_2() > 0) {
                Player2.sendMessage("You can't choose both left and right path.");
                this.A(Player2);
            } else if (Player2.getLeftSup() < 3 || Player2.getLeftSup2() < 3) {
                this.A(Player2);
            } else {
                if (Player2.getLeftSup2_1() == 0) {
                    Player2.incLeftSup2_1();
                } else if (Player2.getLeftSup2_1() == 1) {
                    this.A(Player2);
                    return;
                }
                ClassPathsBBSManager.D(Player2);
                this.A(Player2);
            }
        } else if (str.equals("_bbscpleftsuptwo_2")) {
            if (Player2.getalekoscur() >= Player2.getalekos()) {
                Player2.sendMessage("You don't have enough path points.");
                this.A(Player2);
            } else if (Player2.getLeftSup2_1() > 0) {
                Player2.sendMessage("You can't choose both left and right path.");
                this.A(Player2);
            } else if (Player2.getLeftSup() < 3 || Player2.getLeftSup2() < 3) {
                this.A(Player2);
            } else {
                if (Player2.getLeftSup2_2() == 0) {
                    Player2.incLeftSup2_2();
                } else if (Player2.getLeftSup2_2() == 1) {
                    this.A(Player2);
                    return;
                }
                ClassPathsBBSManager.D(Player2);
                this.A(Player2);
            }
        } else if (str.equals("_bbscprightsup")) {
            if (Player2.getalekoscur() >= Player2.getalekos() || Player2.getLeftSup() >= 1) {
                if (Player2.getalekoscur() >= Player2.getalekos()) {
                    Player2.sendMessage("You don't have enough path points.");
                }
                if (Player2.getLeftSup() >= 1) {
                    Player2.sendMessage("You can't choose both left and right path.");
                }
                this.A(Player2);
                return;
            }
            if (Player2.getRightSup() == 0 && Player2.getMiddleSup() >= 3) {
                Player2.incRightSup();
            } else if (Player2.getRightSup() == 1 && Player2.getMiddleSup() >= 3) {
                Player2.incRightSup();
            } else {
                if (Player2.getRightSup() != 2 || Player2.getMiddleSup() < 3) {
                    this.A(Player2);
                    return;
                }
                Player2.incRightSup();
            }
            ClassPathsBBSManager.D(Player2);
            this.A(Player2);
        } else if (str.equals("_bbscprightsupone")) {
            if (Player2.getalekoscur() >= Player2.getalekos()) {
                Player2.sendMessage("You don't have enough path points.");
                this.A(Player2);
            } else if (Player2.getRightSup2() > 0) {
                Player2.sendMessage("You can't choose both left and right path.");
                this.A(Player2);
            } else {
                if (Player2.getRightSup1() == 0 && Player2.getRightSup() >= 3) {
                    Player2.incRightSup1();
                } else if (Player2.getRightSup1() == 1 && Player2.getRightSup() >= 3) {
                    Player2.incRightSup1();
                } else {
                    if (Player2.getRightSup1() != 2 || Player2.getRightSup() < 3) {
                        this.A(Player2);
                        return;
                    }
                    Player2.incRightSup1();
                }
                ClassPathsBBSManager.D(Player2);
                this.A(Player2);
            }
        } else if (str.equals("_bbscprightsupone_1")) {
            if (Player2.getalekoscur() >= Player2.getalekos()) {
                Player2.sendMessage("You don't have enough path points.");
                this.A(Player2);
            } else if (Player2.getRightSup1_2() > 0) {
                Player2.sendMessage("You can't choose both left and right path.");
                this.A(Player2);
            } else if (Player2.getRightSup() < 3 || Player2.getRightSup1() < 3) {
                this.A(Player2);
            } else {
                if (Player2.getRightSup1_1() == 0) {
                    Player2.incRightSup1_1();
                } else if (Player2.getRightSup1_1() == 1) {
                    this.A(Player2);
                    return;
                }
                ClassPathsBBSManager.D(Player2);
                this.A(Player2);
            }
        } else if (str.equals("_bbscprightsupone_2")) {
            if (Player2.getalekoscur() >= Player2.getalekos()) {
                Player2.sendMessage("You don't have enough path points.");
                this.A(Player2);
            } else if (Player2.getRightSup1_1() > 0) {
                Player2.sendMessage("You can't choose both left and right path.");
                this.A(Player2);
            } else if (Player2.getRightSup() < 3 || Player2.getRightSup1() < 3) {
                this.A(Player2);
            } else {
                if (Player2.getRightSup1_2() == 0) {
                    Player2.incRightSup1_2();
                } else if (Player2.getRightSup1_2() == 1) {
                    this.A(Player2);
                    return;
                }
                ClassPathsBBSManager.D(Player2);
                this.A(Player2);
            }
        } else if (str.equals("_bbscprightsuptwo")) {
            if (Player2.getalekoscur() >= Player2.getalekos()) {
                Player2.sendMessage("You don't have enough path points.");
                this.A(Player2);
            } else if (Player2.getRightSup1() > 0) {
                Player2.sendMessage("You can't choose both left and right path.");
                this.A(Player2);
            } else {
                if (Player2.getRightSup2() == 0 && Player2.getRightSup() >= 3) {
                    Player2.incRightSup2();
                } else if (Player2.getRightSup2() == 1 && Player2.getRightSup() >= 3) {
                    Player2.incRightSup2();
                } else {
                    if (Player2.getRightSup2() != 2 || Player2.getRightSup() < 3) {
                        this.A(Player2);
                        return;
                    }
                    Player2.incRightSup2();
                }
                ClassPathsBBSManager.D(Player2);
                this.A(Player2);
            }
        } else if (str.equals("_bbscprightsuptwo_1")) {
            if (Player2.getalekoscur() >= Player2.getalekos()) {
                Player2.sendMessage("You don't have enough path points.");
                this.A(Player2);
            } else if (Player2.getRightSup2_2() > 0) {
                Player2.sendMessage("You can't choose both left and right path.");
                this.A(Player2);
            } else if (Player2.getRightSup() < 3 || Player2.getRightSup2() < 3) {
                this.A(Player2);
            } else {
                if (Player2.getRightSup2_1() == 0) {
                    Player2.incRightSup2_1();
                } else if (Player2.getRightSup2_1() == 1) {
                    this.A(Player2);
                    return;
                }
                ClassPathsBBSManager.D(Player2);
                this.A(Player2);
            }
        } else if (str.equals("_bbscprightsuptwo_2")) {
            if (Player2.getalekoscur() >= Player2.getalekos()) {
                Player2.sendMessage("You don't have enough path points.");
                this.A(Player2);
            } else if (Player2.getRightSup2_1() > 0) {
                Player2.sendMessage("You can't choose both left and right path.");
                this.A(Player2);
            } else if (Player2.getRightSup() < 3 || Player2.getRightSup2() < 3) {
                this.A(Player2);
            } else {
                if (Player2.getRightSup2_2() == 0) {
                    Player2.incRightSup2_2();
                } else if (Player2.getRightSup2_2() == 1) {
                    this.A(Player2);
                    return;
                }
                ClassPathsBBSManager.D(Player2);
                this.A(Player2);
            }
        } else if (str.equals("_bbscpcancelpath")) {
            Player2.resetAlekos();
            Player2.broadcastUserInfo(false, new UserInfoType[0]);
            this.F(Player2);
        } else if (str.equals("_bbscpcancelpathoff")) {
            Player2.broadcastUserInfo(false, new UserInfoType[0]);
            this.E(Player2);
        } else if (str.equals("_bbscpcancelpathmage")) {
            Player2.broadcastUserInfo(false, new UserInfoType[0]);
            this.B(Player2);
        } else if (str.equals("_bbscpcancelpathdef")) {
            Player2.broadcastUserInfo(false, new UserInfoType[0]);
            this.C(Player2);
        } else if (str.equals("_bbscpcancelpathsup")) {
            Player2.broadcastUserInfo(false, new UserInfoType[0]);
            this.A(Player2);
        } else {
            Player2.sendPacket((IStaticPacket)new ShowBoard("<html><body><br><br><center>the command: " + str + " is not implemented yet</center><br><br></body></html>", "101", (Player)Player2));
            Player2.sendPacket((IStaticPacket)new ShowBoard(null, "102", (Player)Player2));
            Player2.sendPacket((IStaticPacket)new ShowBoard(null, "103", (Player)Player2));
        }
    }

    private static class _A {
        protected static final ClassPathsBBSManager A = new ClassPathsBBSManager();

        private _A() {
        }
    }
}
