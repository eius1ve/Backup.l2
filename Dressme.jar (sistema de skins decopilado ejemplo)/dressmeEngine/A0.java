/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.handler.voicecommands.IVoicedCommandHandler
 *  l2.gameserver.handler.voicecommands.VoicedCommandHandler
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package dressmeEngine;

import config.GabConfig;
import dressmeEngine.C.C;
import dressmeEngine.data.DressMeAgathionData;
import dressmeEngine.data.DressMeArmorData;
import dressmeEngine.data.DressMeCloakData;
import dressmeEngine.data.DressMeEnchantData;
import dressmeEngine.data.DressMeHatData;
import dressmeEngine.data.DressMeShieldData;
import dressmeEngine.data.DressMeWeaponData;
import dressmeEngine.xml.A.AZ;
import dressmeEngine.xml.A.BZ;
import dressmeEngine.xml.A.CZ;
import dressmeEngine.xml.A.DZ;
import dressmeEngine.xml.A.EZ;
import dressmeEngine.xml.A.FZ;
import dressmeEngine.xml.A.GZ;
import dressmeEngine.xml.dataHolder.DressMeAgathionHolder;
import dressmeEngine.xml.dataHolder.DressMeArmorHolder;
import dressmeEngine.xml.dataHolder.DressMeCloakHolder;
import dressmeEngine.xml.dataHolder.DressMeEnchantHolder;
import dressmeEngine.xml.dataHolder.DressMeHatHolder;
import dressmeEngine.xml.dataHolder.DressMeShieldHolder;
import dressmeEngine.xml.dataHolder.DressMeWeaponHolder;
import java.util.LinkedHashMap;
import java.util.Map;
import l2.gameserver.handler.voicecommands.IVoicedCommandHandler;
import l2.gameserver.handler.voicecommands.VoicedCommandHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class A0 {
    private static final Logger R = LoggerFactory.getLogger(A0.class);
    public static Map<Integer, DressMeWeaponData> N;
    public static Map<Integer, DressMeWeaponData> Z;
    public static Map<Integer, DressMeWeaponData> a;
    public static Map<Integer, DressMeWeaponData> F;
    public static Map<Integer, DressMeWeaponData> O;
    public static Map<Integer, DressMeWeaponData> S;
    public static Map<Integer, DressMeWeaponData> A;
    public static Map<Integer, DressMeWeaponData> K;
    public static Map<Integer, DressMeWeaponData> V;
    public static Map<Integer, DressMeWeaponData> P;
    public static Map<Integer, DressMeWeaponData> U;
    public static Map<Integer, DressMeWeaponData> fish;
    public static Map<Integer, DressMeWeaponData> I;
    public static Map<Integer, DressMeWeaponData> B;
    public static Map<Integer, DressMeWeaponData> BS;
    public static Map<Integer, DressMeWeaponData> M;
    public static Map<Integer, DressMeWeaponData> D;
    public static Map<Integer, DressMeWeaponData> E;
    public static Map<Integer, DressMeWeaponData> G;
    public static Map<Integer, DressMeArmorData> X;
    public static Map<Integer, DressMeArmorData> C;
    public static Map<Integer, DressMeArmorData> c;
    public static Map<Integer, DressMeArmorData> J;
    public static Map<Integer, DressMeArmorData> Q;
    public static Map<Integer, DressMeHatData> Y;
    public static Map<Integer, DressMeHatData> L;
    public static Map<Integer, DressMeHatData> H;
    public static Map<Integer, DressMeShieldData> T;
    public static Map<Integer, DressMeShieldData> e;
    public static Map<Integer, DressMeEnchantData> W;
    public static Map<Integer, DressMeAgathionData> b;
    public static Map<Integer, DressMeCloakData> d;

    public static void F() {
        AZ.getInstance().load();
        FZ.getInstance().load();
        BZ.getInstance().load();
        DZ.getInstance().load();
        CZ.getInstance().load();
        EZ.getInstance().load();
        GZ.getInstance().load();
        N = new LinkedHashMap<Integer, DressMeWeaponData>();
        a = new LinkedHashMap<Integer, DressMeWeaponData>();
        O = new LinkedHashMap<Integer, DressMeWeaponData>();
        S = new LinkedHashMap<Integer, DressMeWeaponData>();
        A = new LinkedHashMap<Integer, DressMeWeaponData>();
        K = new LinkedHashMap<Integer, DressMeWeaponData>();
        V = new LinkedHashMap<Integer, DressMeWeaponData>();
        P = new LinkedHashMap<Integer, DressMeWeaponData>();
        U = new LinkedHashMap<Integer, DressMeWeaponData>();
        fish = new LinkedHashMap<Integer, DressMeWeaponData>();
        I = new LinkedHashMap<Integer, DressMeWeaponData>();
        M = new LinkedHashMap<Integer, DressMeWeaponData>();
        D = new LinkedHashMap<Integer, DressMeWeaponData>();
        E = new LinkedHashMap<Integer, DressMeWeaponData>();
        G = new LinkedHashMap<Integer, DressMeWeaponData>();
        Z = new LinkedHashMap<Integer, DressMeWeaponData>();
        F = new LinkedHashMap<Integer, DressMeWeaponData>();
        B = new LinkedHashMap<Integer, DressMeWeaponData>();
        X = new LinkedHashMap<Integer, DressMeArmorData>();
        C = new LinkedHashMap<Integer, DressMeArmorData>();
        c = new LinkedHashMap<Integer, DressMeArmorData>();
        J = new LinkedHashMap<Integer, DressMeArmorData>();
        Q = new LinkedHashMap<Integer, DressMeArmorData>();
        Y = new LinkedHashMap<Integer, DressMeHatData>();
        L = new LinkedHashMap<Integer, DressMeHatData>();
        H = new LinkedHashMap<Integer, DressMeHatData>();
        T = new LinkedHashMap<Integer, DressMeShieldData>();
        e = new LinkedHashMap<Integer, DressMeShieldData>();
        W = new LinkedHashMap<Integer, DressMeEnchantData>();
        b = new LinkedHashMap<Integer, DressMeAgathionData>();
        d = new LinkedHashMap<Integer, DressMeCloakData>();
        A0.D();
        A0.E();
        A0.B();
        A0.C();
        A0.A();
        A0.H();
        A0.G();
        VoicedCommandHandler.getInstance().registerVoicedCommandHandler((IVoicedCommandHandler)new C());
    }

    private static int D() {
        int i = 1;
        int i2 = 1;
        int i3 = 1;
        int i4 = 1;
        int i5 = 1;
        int i6 = 1;
        int i7 = 1;
        int i8 = 1;
        int i9 = 1;
        int i10 = 1;
        int i11 = 1;
        int i12 = 1;
        int i13 = 1;
        int i14 = 1;
        int i15 = 1;
        int i16 = 1;
        int i17 = 1;
        int i18 = 1;
        for (DressMeWeaponData dressMeWeaponData : DressMeWeaponHolder.getInstance().getAllWeapons()) {
            if (dressMeWeaponData.S().equals("SWORD") && !dressMeWeaponData.T()) {
                N.put(i, dressMeWeaponData);
                ++i;
                continue;
            }
            if (dressMeWeaponData.S().equals("SWORD") && dressMeWeaponData.T()) {
                Z.put(i16, dressMeWeaponData);
                ++i16;
                continue;
            }
            if (dressMeWeaponData.S().equals("BLUNT") && !dressMeWeaponData.T()) {
                a.put(i2, dressMeWeaponData);
                ++i2;
                continue;
            }
            if (dressMeWeaponData.S().equals("BLUNT") && !dressMeWeaponData.V() && dressMeWeaponData.T()) {
                F.put(i17, dressMeWeaponData);
                ++i17;
                continue;
            }
            if (dressMeWeaponData.S().equals("BIGSWORD") && !dressMeWeaponData.T()) {
                U.put(i9, dressMeWeaponData);
                ++i9;
                continue;
            }
            if (dressMeWeaponData.S().equals("BIG BLUNT") && dressMeWeaponData.T()) {
                B.put(i18, dressMeWeaponData);
                ++i18;
                continue;
            }
            if (dressMeWeaponData.S().equals("BIG BLUNT") && !dressMeWeaponData.T()) {
                I.put(i11, dressMeWeaponData);
                ++i11;
                continue;
            }
            if (dressMeWeaponData.S().equals("DAGGER")) {
                O.put(i3, dressMeWeaponData);
                ++i3;
                continue;
            }
            if (dressMeWeaponData.S().equals("BOW")) {
                S.put(i4, dressMeWeaponData);
                ++i4;
                continue;
            }
            if (dressMeWeaponData.S().equals("POLE")) {
                A.put(i5, dressMeWeaponData);
                ++i5;
                continue;
            }
            if (dressMeWeaponData.S().equals("FIST")) {
                K.put(i6, dressMeWeaponData);
                ++i6;
                continue;
            }
            if (dressMeWeaponData.S().equals("DUAL")) {
                V.put(i7, dressMeWeaponData);
                ++i7;
                continue;
            }
            if (dressMeWeaponData.S().equals("DUALFIST")) {
                P.put(i8, dressMeWeaponData);
                ++i8;
                continue;
            }
            if (dressMeWeaponData.S().equals("ROD")) {
                fish.put(i10, dressMeWeaponData);
                ++i10;
                continue;
            }
            if (dressMeWeaponData.S().equals("CROSSBOW")) {
                M.put(i12, dressMeWeaponData);
                ++i12;
                continue;
            }
            if (dressMeWeaponData.S().equals("RAPIER")) {
                D.put(i13, dressMeWeaponData);
                ++i13;
                continue;
            }
            if (dressMeWeaponData.S().equals("ANCIENT SWORD")) {
                E.put(i14, dressMeWeaponData);
                ++i14;
                continue;
            }
            if (dressMeWeaponData.S().equals("DUALDAGGER")) {
                G.put(i15, dressMeWeaponData);
                ++i15;
                continue;
            }
            R.error("Dress me system: Can't find type: " + dressMeWeaponData.S());
        }
        R.info("Dress me system: Loaded " + (i - 1) + " Sword(s).");
        R.info("Dress me system: Loaded " + (i2 - 1) + " Blunt(s).");
        R.info("Dress me system: Loaded " + (i3 - 1) + " Dagger(s).");
        R.info("Dress me system: Loaded " + (i4 - 1) + " Bow(s).");
        R.info("Dress me system: Loaded " + (i5 - 1) + " Pole(s).");
        R.info("Dress me system: Loaded " + (i6 - 1) + " Fist(s).");
        R.info("Dress me system: Loaded " + (i7 - 1) + " Dual Sword(s).");
        R.info("Dress me system: Loaded " + (i8 - 1) + " Dual Fist(s).");
        R.info("Dress me system: Loaded " + (i9 - 1) + " Big Sword(s).");
        R.info("Dress me system: Loaded " + (i10 - 1) + " Rod(s).");
        R.info("Dress me system: Loaded " + (i11 - 1) + " Big Blunt(s).");
        R.info("Dress me system: Loaded " + (i12 - 1) + " Crossbow(s).");
        R.info("Dress me system: Loaded " + (i13 - 1) + " Rapier(s).");
        R.info("Dress me system: Loaded " + (i14 - 1) + " Ancient Sword(s).");
        R.info("Dress me system: Loaded " + (i15 - 1) + " Dual Dagger(s).");
        R.info("Dress me system: Loaded " + (i16 - 1) + " Buster(s).");
        R.info("Dress me system: Loaded " + (i17 - 1) + " Caster(s).");
        R.info("Dress me system: Loaded " + (i18 - 1) + " Staff(s).");
        return 0;
    }

    private static int E() {
        int i = 1;
        int i2 = 1;
        int i3 = 1;
        int i4 = 1;
        int i5 = 1;
        for (DressMeArmorData dressMeArmorData : DressMeArmorHolder.getInstance().getAllDress()) {
            if (GabConfig.ALLOW_ALL_SETS) {
                J.put(i5, dressMeArmorData);
                ++i5;
                continue;
            }
            if (dressMeArmorData.I().equals("LIGHT") && !dressMeArmorData.M()) {
                X.put(i, dressMeArmorData);
                ++i;
                continue;
            }
            if (dressMeArmorData.I().equals("HEAVY") && !dressMeArmorData.M()) {
                C.put(i2, dressMeArmorData);
                ++i2;
                continue;
            }
            if (dressMeArmorData.I().equals("ROBE") && !dressMeArmorData.M()) {
                c.put(i3, dressMeArmorData);
                ++i3;
                continue;
            }
            if (dressMeArmorData.M()) {
                Q.put(i4, dressMeArmorData);
                ++i4;
                continue;
            }
            R.error("Dress me system: Can't find type: " + dressMeArmorData.I());
        }
        if (GabConfig.ALLOW_ALL_SETS) {
            R.info("Dress me system: Loaded " + (i5 - 1) + " Armor(s).");
            return 0;
        }
        R.info("Dress me system: Loaded " + (i - 1) + " Light Armor(s).");
        R.info("Dress me system: Loaded " + (i2 - 1) + " Heavy Armor(s).");
        R.info("Dress me system: Loaded " + (i3 - 1) + " Robe Armor(s).");
        R.info("Dress me system: Loaded " + (i4 - 1) + " Suit(s).");
        return 0;
    }

    private static int B() {
        int i = 1;
        int i2 = 1;
        int i3 = 1;
        for (DressMeHatData dressMeHatData : DressMeHatHolder.getInstance().getAllHats()) {
            if (dressMeHatData.E() == 1) {
                Y.put(i, dressMeHatData);
                ++i;
                continue;
            }
            if (dressMeHatData.E() == 2) {
                L.put(i2, dressMeHatData);
                ++i2;
                continue;
            }
            if (dressMeHatData.E() == 3) {
                H.put(i3, dressMeHatData);
                ++i3;
                continue;
            }
            R.error("Dress me system: Can't find slot: " + dressMeHatData.E());
        }
        R.info("Dress me system: Loaded " + (i - 1) + " Hair(s).");
        R.info("Dress me system: Loaded " + (i2 - 1) + " Hair2(s).");
        R.info("Dress me system: Loaded " + (i3 - 1) + " Full Hair(s).");
        return 0;
    }

    private static int C() {
        int i = 1;
        int i2 = 1;
        for (DressMeShieldData dressMeShieldData : DressMeShieldHolder.getInstance().getAllShields()) {
            if (dressMeShieldData.X()) {
                e.put(i, dressMeShieldData);
                ++i;
                continue;
            }
            T.put(i2, dressMeShieldData);
            ++i2;
        }
        R.info("Dress me system: Loaded " + (i - 1) + " Shield(s).");
        R.info("Dress me system: Loaded " + (i2 - 1) + " Sigil(s).");
        return 0;
    }

    private static int A() {
        int i = 1;
        for (DressMeEnchantData dressMeEnchantData : DressMeEnchantHolder.getInstance().getAllEnchants()) {
            W.put(i, dressMeEnchantData);
            ++i;
        }
        R.info("Dress me system: Loaded " + (i - 1) + " Enchant(s).");
        return 0;
    }

    private static int H() {
        int i = 1;
        for (DressMeAgathionData dressMeAgathionData : DressMeAgathionHolder.getInstance().getAllAgathions()) {
            b.put(i, dressMeAgathionData);
            ++i;
        }
        R.info("Dress me system: Loaded " + (i - 1) + " Agathion(s).");
        return 0;
    }

    private static int G() {
        int i = 1;
        for (DressMeCloakData dressMeCloakData : DressMeCloakHolder.getInstance().getAllCloaks()) {
            d.put(i, dressMeCloakData);
            ++i;
        }
        R.info("Dress me system: Loaded " + (i - 1) + " Cloaks(s).");
        return 0;
    }
}
