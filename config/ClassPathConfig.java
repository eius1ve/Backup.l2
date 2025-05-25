/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.configuration.ExProperties
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package config;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import l2.commons.configuration.ExProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassPathConfig {
    private static final Logger f0 = LoggerFactory.getLogger(ClassPathConfig.class);
    private static final String f1 = "./config/gabriel/classPath.ini";
    public static boolean CLASSPATH_REMOVE_OLY;
    public static int L;
    public static String[] U;
    public static int f2;
    public static String[] f3;
    public static int r;
    public static String[] f4;
    public static int f5;
    public static String[] f6;
    public static int f7;
    public static String[] f8;
    public static int n;
    public static String[] f9;
    public static int f10;
    public static String[] f11;
    public static int f12;
    public static String[] f13;
    public static int f14;
    public static String[] W;
    public static int H;
    public static String[] f15;
    public static int f16;
    public static String[] f17;
    public static int f18;
    public static String[] q;
    public static int G;
    public static String[] f19;
    public static int w;
    public static String[] x;
    public static int v;
    public static String[] Q;
    public static int[] CLASSPATH_OFFENSIVE_SKILLS;
    public static int f20;
    public static String[] f21;
    public static int f22;
    public static String[] S;
    public static int f23;
    public static String[] f24;
    public static int u;
    public static String[] V;
    public static int t;
    public static String[] C;
    public static int f25;
    public static String[] f26;
    public static int p;
    public static String[] f27;
    public static int m;
    public static String[] f28;
    public static int f29;
    public static String[] J;
    public static int f30;
    public static String[] i;
    public static int h;
    public static String[] f31;
    public static int f;
    public static String[] f32;
    public static int f33;
    public static String[] F;
    public static int _dd;
    public static String[] f34;
    public static int X;
    public static String[] f35;
    public static int[] CLASSPATH_MAGE_SKILLS;
    public static int f36;
    public static String[] Z;
    public static int f37;
    public static String[] N;
    public static int f38;
    public static String[] f39;
    public static int o;
    public static String[] f40;
    public static int l;
    public static String[] T;
    public static int f41;
    public static String[] f42;
    public static int g;
    public static String[] e;
    public static int d;
    public static String[] E;
    public static int f43;
    public static String[] M;
    public static int f44;
    public static String[] f45;
    public static int k;
    public static String[] z;
    public static int j;
    public static String[] R;
    public static int f46;
    public static String[] f47;
    public static int c;
    public static String[] Y;
    public static int a;
    public static String[] D;
    public static int[] CLASSPATH_DEFENCE_SKILLS;
    public static int f48;
    public static String[] f49;
    public static int I;
    public static String[] f50;
    public static int B;
    public static String[] f51;
    public static int f52;
    public static String[] f53;
    public static int f54;
    public static String[] f55;
    public static int A;
    public static String[] f56;
    public static int f57;
    public static String[] s;
    public static int f58;
    public static String[] K;
    public static int f59;
    public static String[] f60;
    public static int P;
    public static String[] f61;
    public static int f62;
    public static String[] f63;
    public static int f64;
    public static String[] f65;
    public static int O;
    public static String[] b;
    public static int f66;
    public static String[] f67;
    public static int f68;
    public static String[] f69;
    public static int[] CLASSPATH_SUPP_SKILLS;
    public static Map<Integer, Integer> y;
    protected static ClassPathConfig f70;

    private ClassPathConfig() {
        this.A();
    }

    public void A() {
        ExProperties propertiesParser = ClassPathConfig.initProperties(f1);
        CLASSPATH_REMOVE_OLY = Boolean.parseBoolean(propertiesParser.getProperty("RemoveClassPathInOly", "true"));
        L = propertiesParser.getProperty("middleOffensiveSkillId", 9421);
        U = propertiesParser.getProperty("middleOffensiveSkillLevelInfo", "P.Atk +200,P.Atk +400,P.Atk +600").split(",");
        f2 = propertiesParser.getProperty("leftOffensiveSkillId", 9424);
        f3 = propertiesParser.getProperty("leftOffensiveSkillLevelInfo", "A.Spd +10,A.Spd +20,A.Spd +35").split(",");
        r = propertiesParser.getProperty("left_1OffensiveSkillId", 9427);
        f4 = propertiesParser.getProperty("left_1OffensiveSkillLevelInfo", "Spd +1,Spd +3,Spd +6").split(",");
        f5 = propertiesParser.getProperty("left_1_1OffensiveSkillId", 9503);
        f6 = propertiesParser.getProperty("left_1_1OffensiveSkillLevelInfo", "Hp +1").split(",");
        f7 = propertiesParser.getProperty("left_1_2OffensiveSkillId", 9504);
        f8 = propertiesParser.getProperty("left_1_2OffensiveSkillLevelInfo", "Hp +1").split(",");
        n = propertiesParser.getProperty("left_2OffensiveSkillId", 9430);
        f9 = propertiesParser.getProperty("left_2OffensiveSkillLevelInfo", "Evas +1,Evas +2,Evas +4").split(",");
        f10 = propertiesParser.getProperty("left_2_1OffensiveSkillId", 9505);
        f11 = propertiesParser.getProperty("left_2_1OffensiveSkillLevelInfo", "Hp +1").split(",");
        f12 = propertiesParser.getProperty("left_2_2OffensiveSkillId", 9506);
        f13 = propertiesParser.getProperty("left_2_2OffensiveSkillLevelInfo", "Hp +1").split(",");
        f14 = propertiesParser.getProperty("rightOffensiveSkillId", 9433);
        W = propertiesParser.getProperty("rightOffensiveSkillLevelInfo", "P.Atk +100,P.Atk +250,P.Atk +400").split(",");
        H = propertiesParser.getProperty("right_1OffensiveSkillId", 9436);
        f15 = propertiesParser.getProperty("right_1OffensiveSkillLevelInfo", "Acc +1,Acc +2,Acc +4").split(",");
        f16 = propertiesParser.getProperty("right_1_1OffensiveSkillId", 9507);
        f17 = propertiesParser.getProperty("right_1_1OffensiveSkillLevelInfo", "Hp +1").split(",");
        f18 = propertiesParser.getProperty("right_1_2OffensiveSkillId", 9508);
        q = propertiesParser.getProperty("right_1_2OffensiveSkillLevelInfo", "Hp +1").split(",");
        G = propertiesParser.getProperty("right_2OffensiveSkillId", 9439);
        f19 = propertiesParser.getProperty("right_2OffensiveSkillLevelInfo", "Evas +1,Evas +2,Evas +4").split(",");
        w = propertiesParser.getProperty("right_2_1OffensiveSkillId", 9509);
        x = propertiesParser.getProperty("right_2_1OffensiveSkillLevelInfo", "Hp +1").split(",");
        v = propertiesParser.getProperty("right_2_2OffensiveSkillId", 9510);
        Q = propertiesParser.getProperty("right_2_2OffensiveSkillLevelInfo", "Hp +1").split(",");
        f20 = propertiesParser.getProperty("middleMageSkillId", 9442);
        f21 = propertiesParser.getProperty("middleMageSkillLevelInfo", "M.Atk +400,M.Atk +800,M.Atk +1200").split(",");
        f22 = propertiesParser.getProperty("leftMageSkillId", 9445);
        S = propertiesParser.getProperty("leftMageSkillLevelInfo", "C.Spd +10,C.Spd +30,C.Spd +50").split(",");
        f23 = propertiesParser.getProperty("left_1MageSkillId", 9448);
        f24 = propertiesParser.getProperty("left_1MageSkillLevelInfo", "M.Reu -2%,M.Reu -4%,M.Reu -8%").split(",");
        u = propertiesParser.getProperty("left_1_1MageSkillId", 9511);
        V = propertiesParser.getProperty("left_1_1MageSkillLevelInfo", "Hp +1").split(",");
        t = propertiesParser.getProperty("left_1_2MageSkillId", 9512);
        C = propertiesParser.getProperty("left_1_2MageSkillLevelInfo", "Hp +1").split(",");
        f25 = propertiesParser.getProperty("left_2MageSkillId", 9451);
        f26 = propertiesParser.getProperty("left_2MageSkillLevelInfo", "Spd +1,Spd +2,Spd +5").split(",");
        p = propertiesParser.getProperty("left_2_1MageSkillId", 9513);
        f27 = propertiesParser.getProperty("left_2_1MageSkillLevelInfo", "Hp +1").split(",");
        m = propertiesParser.getProperty("left_2_2MageSkillId", 9514);
        f28 = propertiesParser.getProperty("left_2_2MageSkillLevelInfo", "Hp +1").split(",");
        f29 = propertiesParser.getProperty("rightMageSkillId", 9454);
        J = propertiesParser.getProperty("rightMageSkillLevelInfo", "M.Crit R +10,M.Crit R +20,M.Crit R +30").split(",");
        f30 = propertiesParser.getProperty("right_1MageSkillId", 9457);
        i = propertiesParser.getProperty("right_1MageSkillLevelInfo", "M.Reu -2%,M.Reu -4%,M.Reu -8%").split(",");
        h = propertiesParser.getProperty("right_1_1MageSkillId", 9515);
        f31 = propertiesParser.getProperty("right_1_1MageSkillLevelInfo", "Hp +1").split(",");
        f = propertiesParser.getProperty("right_1_2MageSkillId", 9516);
        f32 = propertiesParser.getProperty("right_1_2MageSkillLevelInfo", "Hp +1").split(",");
        f33 = propertiesParser.getProperty("right_2MageSkillId", 9460);
        F = propertiesParser.getProperty("right_2MageSkillLevelInfo", "M.Range +20,M.Range +50,M.Range +100").split(",");
        _dd = propertiesParser.getProperty("right_2_1MageSkillId", 9517);
        f34 = propertiesParser.getProperty("right_2_1MageSkillLevelInfo", "Hp +1").split(",");
        X = propertiesParser.getProperty("right_2_2MageSkillId", 9518);
        f35 = propertiesParser.getProperty("right_2_2MageSkillLevelInfo", "Hp +1").split(",");
        f36 = propertiesParser.getProperty("middleDefSkillId", 9463);
        Z = propertiesParser.getProperty("middleDefSkillLevelInfo", "P-M.Def +40,P-M.Def +80,P-M.Def +120").split(",");
        f37 = propertiesParser.getProperty("leftDefSkillId", 9466);
        N = propertiesParser.getProperty("leftDefSkillLevelInfo", "Hp +300,Hp +600,Hp +1000").split(",");
        f38 = propertiesParser.getProperty("left_1DefSkillId", 9469);
        f39 = propertiesParser.getProperty("left_1DefSkillLevelInfo", "Agro +5%,Agro +10%,Agro +20%").split(",");
        o = propertiesParser.getProperty("left_1_1DefSkillId", 9519);
        f40 = propertiesParser.getProperty("left_1_1DefSkillLevelInfo", "Hp +1").split(",");
        l = propertiesParser.getProperty("left_1_2DefSkillId", 9520);
        T = propertiesParser.getProperty("left_1_2DefSkillLevelInfo", "Hp +1").split(",");
        f41 = propertiesParser.getProperty("left_2DefSkillId", 9472);
        f42 = propertiesParser.getProperty("left_2DefSkillLevelInfo", "S.Def +300,S.Def +600,S.Def +1000").split(",");
        g = propertiesParser.getProperty("left_2_1DefSkillId", 9521);
        e = propertiesParser.getProperty("left_2_1DefSkillLevelInfo", "Hp +1").split(",");
        d = propertiesParser.getProperty("left_2_2DefSkillId", 9522);
        E = propertiesParser.getProperty("left_2_2DefSkillLevelInfo", "Hp +1").split(",");
        f43 = propertiesParser.getProperty("rightDefSkillId", 9475);
        M = propertiesParser.getProperty("rightDefSkillLevelInfo", "Cp +400,Cp +1000,Cp +2000").split(",");
        f44 = propertiesParser.getProperty("right_1DefSkillId", 9478);
        f45 = propertiesParser.getProperty("right_1DefSkillLevelInfo", "S.Rate +3%,S.Rate +6%,S.Rate +10%").split(",");
        k = propertiesParser.getProperty("right_1_1DefSkillId", 9523);
        z = propertiesParser.getProperty("right_1_1DefSkillLevelInfo", "Hp +1").split(",");
        j = propertiesParser.getProperty("right_1_2DefSkillId", 9524);
        R = propertiesParser.getProperty("right_1_2DefSkillLevelInfo", "Hp +1").split(",");
        f46 = propertiesParser.getProperty("right_2DefSkillId", 9481);
        f47 = propertiesParser.getProperty("right_2DefSkillLevelInfo", "P.Reu -2%,P.Reu -4%,P.Reu -8%").split(",");
        c = propertiesParser.getProperty("right_2_1DefSkillId", 9525);
        Y = propertiesParser.getProperty("right_2_1DefSkillLevelInfo", "Hp +1").split(",");
        a = propertiesParser.getProperty("right_2_2DefSkillId", 9526);
        D = propertiesParser.getProperty("right_2_2DefSkillLevelInfo", "Hp +1").split(",");
        f48 = propertiesParser.getProperty("middleSuppSkillId", 9484);
        f49 = propertiesParser.getProperty("middleSuppSkillLevelInfo", "H.Give +2%,H.Give +4%,H.Give +8%").split(",");
        I = propertiesParser.getProperty("leftSuppSkillId", 9487);
        f50 = propertiesParser.getProperty("leftSuppSkillLevelInfo", "MP +500,MP +1000,MP +1800").split(",");
        B = propertiesParser.getProperty("left_1SuppSkillId", 9490);
        f51 = propertiesParser.getProperty("left_1SuppSkillLevelInfo", "C.Spd +10,C.Spd +25,C.Spd +60").split(",");
        f52 = propertiesParser.getProperty("left_1_1SuppSkillId", 9527);
        f53 = propertiesParser.getProperty("left_1_1SuppSkillLevelInfo", "Hp +1").split(",");
        f54 = propertiesParser.getProperty("left_1_2SuppSkillId", 9528);
        f55 = propertiesParser.getProperty("left_1_2SuppSkillLevelInfo", "Hp +1").split(",");
        A = propertiesParser.getProperty("left_2SuppSkillId", 9493);
        f56 = propertiesParser.getProperty("left_2SuppSkillLevelInfo", "C.DmgTake -1%,C.DmgTake -3%,C.DmgTake -6%").split(",");
        f57 = propertiesParser.getProperty("left_2_1SuppSkillId", 9529);
        s = propertiesParser.getProperty("left_2_1SuppSkillLevelInfo", "Hp +1").split(",");
        f58 = propertiesParser.getProperty("left_2_2SuppSkillId", 9530);
        K = propertiesParser.getProperty("left_2_2SuppSkillLevelInfo", "Hp +1").split(",");
        f59 = propertiesParser.getProperty("rightSuppSkillId", 9496);
        f60 = propertiesParser.getProperty("rightSuppSkillLevelInfo", "Mp.Cons -2%,Mp.Cons -5%,Mp.Cons -10%").split(",");
        P = propertiesParser.getProperty("right_1SuppSkillId", 9499);
        f61 = propertiesParser.getProperty("right_1SuppSkillLevelInfo", "M.Reu -2%,M.Reu -4%,M.Reu -8%").split(",");
        f62 = propertiesParser.getProperty("right_1_1SuppSkillId", 9531);
        f63 = propertiesParser.getProperty("right_1_1SuppSkillLevelInfo", "Hp +1").split(",");
        f64 = propertiesParser.getProperty("right_1_2SuppSkillId", 9532);
        f65 = propertiesParser.getProperty("right_1_2SuppSkillLevelInfo", "Hp +1").split(",");
        O = propertiesParser.getProperty("right_2SuppSkillId", 9502);
        b = propertiesParser.getProperty("right_2SuppSkillLevelInfo", "Behind.Dmg -2%,Behind.Dmg -5%,Behind.Dmg -10%").split(",");
        f66 = propertiesParser.getProperty("right_2_1SuppSkillId", 9533);
        f67 = propertiesParser.getProperty("right_2_1SuppSkillLevelInfo", "Hp +1").split(",");
        f68 = propertiesParser.getProperty("right_2_2SuppSkillId", 9534);
        f69 = propertiesParser.getProperty("right_2_2SuppSkillLevelInfo", "Hp +1").split(",");
        y = new LinkedHashMap<Integer, Integer>();
        String[] stringArray = propertiesParser.getProperty("pointsToUse", "76,2;77,4;78,6;79,8;80,10;81,12;82,14;83,16;84,18;85,20").split(";");
        int n = stringArray.length;
        int n2 = 0;
        while (n2 < n) {
            String str = stringArray[n2];
            y.put(Integer.parseInt(str.split(",")[0]), Integer.parseInt(str.split(",")[1]));
            ++n2;
        }
        CLASSPATH_OFFENSIVE_SKILLS = new int[]{L, f2, r, f5, f7, ClassPathConfig.n, f10, f12, f14, H, f16, f18, G, w, v};
        CLASSPATH_MAGE_SKILLS = new int[]{f20, f22, f23, u, t, f25, p, m, f29, f30, h, f, f33, _dd, X};
        CLASSPATH_DEFENCE_SKILLS = new int[]{f36, f37, f38, o, l, f41, g, d, f43, f44, k, j, f46, c, a};
        CLASSPATH_SUPP_SKILLS = new int[]{f48, I, B, f52, f54, A, f57, f58, f59, P, f62, f64, O, f66, f68};
    }

    public static ClassPathConfig B() {
        if (f70 == null) {
            f70 = new ClassPathConfig();
        }
        return f70;
    }

    public static ExProperties initProperties(String filename) {
        ExProperties result = new ExProperties();
        try {
            result.load(new File(filename));
        }
        catch (IOException e) {
            f0.warn("Wardrobe: Error loading \"" + filename + "\" config.");
        }
        return result;
    }
}
