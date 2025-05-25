/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.base;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Set;
import l2.gameserver.Config;
import l2.gameserver.model.base.ClassLevel;
import l2.gameserver.model.base.ClassType;
import l2.gameserver.model.base.Race;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class PlayerClass
extends Enum<PlayerClass> {
    public static final /* enum */ PlayerClass HumanFighter = new PlayerClass(Race.human, ClassType.Fighter, ClassLevel.First);
    public static final /* enum */ PlayerClass Warrior = new PlayerClass(Race.human, ClassType.Fighter, ClassLevel.Second);
    public static final /* enum */ PlayerClass Gladiator = new PlayerClass(Race.human, ClassType.Fighter, ClassLevel.Third);
    public static final /* enum */ PlayerClass Warlord = new PlayerClass(Race.human, ClassType.Fighter, ClassLevel.Third);
    public static final /* enum */ PlayerClass HumanKnight = new PlayerClass(Race.human, ClassType.Fighter, ClassLevel.Second);
    public static final /* enum */ PlayerClass Paladin = new PlayerClass(Race.human, ClassType.Fighter, ClassLevel.Third);
    public static final /* enum */ PlayerClass DarkAvenger = new PlayerClass(Race.human, ClassType.Fighter, ClassLevel.Third);
    public static final /* enum */ PlayerClass Rogue = new PlayerClass(Race.human, ClassType.Fighter, ClassLevel.Second);
    public static final /* enum */ PlayerClass TreasureHunter = new PlayerClass(Race.human, ClassType.Fighter, ClassLevel.Third);
    public static final /* enum */ PlayerClass Hawkeye = new PlayerClass(Race.human, ClassType.Fighter, ClassLevel.Third);
    public static final /* enum */ PlayerClass HumanMystic = new PlayerClass(Race.human, ClassType.Mystic, ClassLevel.First);
    public static final /* enum */ PlayerClass HumanWizard = new PlayerClass(Race.human, ClassType.Mystic, ClassLevel.Second);
    public static final /* enum */ PlayerClass Sorceror = new PlayerClass(Race.human, ClassType.Mystic, ClassLevel.Third);
    public static final /* enum */ PlayerClass Necromancer = new PlayerClass(Race.human, ClassType.Mystic, ClassLevel.Third);
    public static final /* enum */ PlayerClass Warlock = new PlayerClass(Race.human, ClassType.Mystic, ClassLevel.Third);
    public static final /* enum */ PlayerClass Cleric = new PlayerClass(Race.human, ClassType.Priest, ClassLevel.Second);
    public static final /* enum */ PlayerClass Bishop = new PlayerClass(Race.human, ClassType.Priest, ClassLevel.Third);
    public static final /* enum */ PlayerClass Prophet = new PlayerClass(Race.human, ClassType.Priest, ClassLevel.Third);
    public static final /* enum */ PlayerClass ElvenFighter = new PlayerClass(Race.elf, ClassType.Fighter, ClassLevel.First);
    public static final /* enum */ PlayerClass ElvenKnight = new PlayerClass(Race.elf, ClassType.Fighter, ClassLevel.Second);
    public static final /* enum */ PlayerClass TempleKnight = new PlayerClass(Race.elf, ClassType.Fighter, ClassLevel.Third);
    public static final /* enum */ PlayerClass Swordsinger = new PlayerClass(Race.elf, ClassType.Fighter, ClassLevel.Third);
    public static final /* enum */ PlayerClass ElvenScout = new PlayerClass(Race.elf, ClassType.Fighter, ClassLevel.Second);
    public static final /* enum */ PlayerClass Plainswalker = new PlayerClass(Race.elf, ClassType.Fighter, ClassLevel.Third);
    public static final /* enum */ PlayerClass SilverRanger = new PlayerClass(Race.elf, ClassType.Fighter, ClassLevel.Third);
    public static final /* enum */ PlayerClass ElvenMystic = new PlayerClass(Race.elf, ClassType.Mystic, ClassLevel.First);
    public static final /* enum */ PlayerClass ElvenWizard = new PlayerClass(Race.elf, ClassType.Mystic, ClassLevel.Second);
    public static final /* enum */ PlayerClass Spellsinger = new PlayerClass(Race.elf, ClassType.Mystic, ClassLevel.Third);
    public static final /* enum */ PlayerClass ElementalSummoner = new PlayerClass(Race.elf, ClassType.Mystic, ClassLevel.Third);
    public static final /* enum */ PlayerClass ElvenOracle = new PlayerClass(Race.elf, ClassType.Priest, ClassLevel.Second);
    public static final /* enum */ PlayerClass ElvenElder = new PlayerClass(Race.elf, ClassType.Priest, ClassLevel.Third);
    public static final /* enum */ PlayerClass DarkElvenFighter = new PlayerClass(Race.darkelf, ClassType.Fighter, ClassLevel.First);
    public static final /* enum */ PlayerClass PalusKnight = new PlayerClass(Race.darkelf, ClassType.Fighter, ClassLevel.Second);
    public static final /* enum */ PlayerClass ShillienKnight = new PlayerClass(Race.darkelf, ClassType.Fighter, ClassLevel.Third);
    public static final /* enum */ PlayerClass Bladedancer = new PlayerClass(Race.darkelf, ClassType.Fighter, ClassLevel.Third);
    public static final /* enum */ PlayerClass Assassin = new PlayerClass(Race.darkelf, ClassType.Fighter, ClassLevel.Second);
    public static final /* enum */ PlayerClass AbyssWalker = new PlayerClass(Race.darkelf, ClassType.Fighter, ClassLevel.Third);
    public static final /* enum */ PlayerClass PhantomRanger = new PlayerClass(Race.darkelf, ClassType.Fighter, ClassLevel.Third);
    public static final /* enum */ PlayerClass DarkElvenMystic = new PlayerClass(Race.darkelf, ClassType.Mystic, ClassLevel.First);
    public static final /* enum */ PlayerClass DarkElvenWizard = new PlayerClass(Race.darkelf, ClassType.Mystic, ClassLevel.Second);
    public static final /* enum */ PlayerClass Spellhowler = new PlayerClass(Race.darkelf, ClassType.Mystic, ClassLevel.Third);
    public static final /* enum */ PlayerClass PhantomSummoner = new PlayerClass(Race.darkelf, ClassType.Mystic, ClassLevel.Third);
    public static final /* enum */ PlayerClass ShillienOracle = new PlayerClass(Race.darkelf, ClassType.Priest, ClassLevel.Second);
    public static final /* enum */ PlayerClass ShillienElder = new PlayerClass(Race.darkelf, ClassType.Priest, ClassLevel.Third);
    public static final /* enum */ PlayerClass OrcFighter = new PlayerClass(Race.orc, ClassType.Fighter, ClassLevel.First);
    public static final /* enum */ PlayerClass orcRaider = new PlayerClass(Race.orc, ClassType.Fighter, ClassLevel.Second);
    public static final /* enum */ PlayerClass Destroyer = new PlayerClass(Race.orc, ClassType.Fighter, ClassLevel.Third);
    public static final /* enum */ PlayerClass orcMonk = new PlayerClass(Race.orc, ClassType.Fighter, ClassLevel.Second);
    public static final /* enum */ PlayerClass Tyrant = new PlayerClass(Race.orc, ClassType.Fighter, ClassLevel.Third);
    public static final /* enum */ PlayerClass orcMystic = new PlayerClass(Race.orc, ClassType.Mystic, ClassLevel.First);
    public static final /* enum */ PlayerClass orcShaman = new PlayerClass(Race.orc, ClassType.Mystic, ClassLevel.Second);
    public static final /* enum */ PlayerClass Overlord = new PlayerClass(Race.orc, ClassType.Mystic, ClassLevel.Third);
    public static final /* enum */ PlayerClass Warcryer = new PlayerClass(Race.orc, ClassType.Mystic, ClassLevel.Third);
    public static final /* enum */ PlayerClass DwarvenFighter = new PlayerClass(Race.dwarf, ClassType.Fighter, ClassLevel.First);
    public static final /* enum */ PlayerClass DwarvenScavenger = new PlayerClass(Race.dwarf, ClassType.Fighter, ClassLevel.Second);
    public static final /* enum */ PlayerClass BountyHunter = new PlayerClass(Race.dwarf, ClassType.Fighter, ClassLevel.Third);
    public static final /* enum */ PlayerClass DwarvenArtisan = new PlayerClass(Race.dwarf, ClassType.Fighter, ClassLevel.Second);
    public static final /* enum */ PlayerClass Warsmith = new PlayerClass(Race.dwarf, ClassType.Fighter, ClassLevel.Third);
    public static final /* enum */ PlayerClass DummyEntry1 = new PlayerClass(null, null, null);
    public static final /* enum */ PlayerClass DummyEntry2 = new PlayerClass(null, null, null);
    public static final /* enum */ PlayerClass DummyEntry3 = new PlayerClass(null, null, null);
    public static final /* enum */ PlayerClass DummyEntry4 = new PlayerClass(null, null, null);
    public static final /* enum */ PlayerClass DummyEntry5 = new PlayerClass(null, null, null);
    public static final /* enum */ PlayerClass DummyEntry6 = new PlayerClass(null, null, null);
    public static final /* enum */ PlayerClass DummyEntry7 = new PlayerClass(null, null, null);
    public static final /* enum */ PlayerClass DummyEntry8 = new PlayerClass(null, null, null);
    public static final /* enum */ PlayerClass DummyEntry9 = new PlayerClass(null, null, null);
    public static final /* enum */ PlayerClass DummyEntry10 = new PlayerClass(null, null, null);
    public static final /* enum */ PlayerClass DummyEntry11 = new PlayerClass(null, null, null);
    public static final /* enum */ PlayerClass DummyEntry12 = new PlayerClass(null, null, null);
    public static final /* enum */ PlayerClass DummyEntry13 = new PlayerClass(null, null, null);
    public static final /* enum */ PlayerClass DummyEntry14 = new PlayerClass(null, null, null);
    public static final /* enum */ PlayerClass DummyEntry15 = new PlayerClass(null, null, null);
    public static final /* enum */ PlayerClass DummyEntry16 = new PlayerClass(null, null, null);
    public static final /* enum */ PlayerClass DummyEntry17 = new PlayerClass(null, null, null);
    public static final /* enum */ PlayerClass DummyEntry18 = new PlayerClass(null, null, null);
    public static final /* enum */ PlayerClass DummyEntry19 = new PlayerClass(null, null, null);
    public static final /* enum */ PlayerClass DummyEntry20 = new PlayerClass(null, null, null);
    public static final /* enum */ PlayerClass DummyEntry21 = new PlayerClass(null, null, null);
    public static final /* enum */ PlayerClass DummyEntry22 = new PlayerClass(null, null, null);
    public static final /* enum */ PlayerClass DummyEntry23 = new PlayerClass(null, null, null);
    public static final /* enum */ PlayerClass DummyEntry24 = new PlayerClass(null, null, null);
    public static final /* enum */ PlayerClass DummyEntry25 = new PlayerClass(null, null, null);
    public static final /* enum */ PlayerClass DummyEntry26 = new PlayerClass(null, null, null);
    public static final /* enum */ PlayerClass DummyEntry27 = new PlayerClass(null, null, null);
    public static final /* enum */ PlayerClass DummyEntry28 = new PlayerClass(null, null, null);
    public static final /* enum */ PlayerClass DummyEntry29 = new PlayerClass(null, null, null);
    public static final /* enum */ PlayerClass DummyEntry30 = new PlayerClass(null, null, null);
    public static final /* enum */ PlayerClass Duelist = new PlayerClass(Race.human, ClassType.Fighter, ClassLevel.Fourth);
    public static final /* enum */ PlayerClass Dreadnought = new PlayerClass(Race.human, ClassType.Fighter, ClassLevel.Fourth);
    public static final /* enum */ PlayerClass PhoenixKnight = new PlayerClass(Race.human, ClassType.Fighter, ClassLevel.Fourth);
    public static final /* enum */ PlayerClass HellKnight = new PlayerClass(Race.human, ClassType.Fighter, ClassLevel.Fourth);
    public static final /* enum */ PlayerClass Sagittarius = new PlayerClass(Race.human, ClassType.Fighter, ClassLevel.Fourth);
    public static final /* enum */ PlayerClass Adventurer = new PlayerClass(Race.human, ClassType.Fighter, ClassLevel.Fourth);
    public static final /* enum */ PlayerClass Archmage = new PlayerClass(Race.human, ClassType.Mystic, ClassLevel.Fourth);
    public static final /* enum */ PlayerClass Soultaker = new PlayerClass(Race.human, ClassType.Mystic, ClassLevel.Fourth);
    public static final /* enum */ PlayerClass ArcanaLord = new PlayerClass(Race.human, ClassType.Mystic, ClassLevel.Fourth);
    public static final /* enum */ PlayerClass Cardinal = new PlayerClass(Race.human, ClassType.Priest, ClassLevel.Fourth);
    public static final /* enum */ PlayerClass Hierophant = new PlayerClass(Race.human, ClassType.Priest, ClassLevel.Fourth);
    public static final /* enum */ PlayerClass EvaTemplar = new PlayerClass(Race.elf, ClassType.Fighter, ClassLevel.Fourth);
    public static final /* enum */ PlayerClass SwordMuse = new PlayerClass(Race.elf, ClassType.Fighter, ClassLevel.Fourth);
    public static final /* enum */ PlayerClass WindRider = new PlayerClass(Race.elf, ClassType.Fighter, ClassLevel.Fourth);
    public static final /* enum */ PlayerClass MoonlightSentinel = new PlayerClass(Race.elf, ClassType.Fighter, ClassLevel.Fourth);
    public static final /* enum */ PlayerClass MysticMuse = new PlayerClass(Race.elf, ClassType.Mystic, ClassLevel.Fourth);
    public static final /* enum */ PlayerClass ElementalMaster = new PlayerClass(Race.elf, ClassType.Mystic, ClassLevel.Fourth);
    public static final /* enum */ PlayerClass EvaSaint = new PlayerClass(Race.elf, ClassType.Priest, ClassLevel.Fourth);
    public static final /* enum */ PlayerClass ShillienTemplar = new PlayerClass(Race.darkelf, ClassType.Fighter, ClassLevel.Fourth);
    public static final /* enum */ PlayerClass SpectralDancer = new PlayerClass(Race.darkelf, ClassType.Fighter, ClassLevel.Fourth);
    public static final /* enum */ PlayerClass GhostHunter = new PlayerClass(Race.darkelf, ClassType.Fighter, ClassLevel.Fourth);
    public static final /* enum */ PlayerClass GhostSentinel = new PlayerClass(Race.darkelf, ClassType.Fighter, ClassLevel.Fourth);
    public static final /* enum */ PlayerClass StormScreamer = new PlayerClass(Race.darkelf, ClassType.Mystic, ClassLevel.Fourth);
    public static final /* enum */ PlayerClass SpectralMaster = new PlayerClass(Race.darkelf, ClassType.Mystic, ClassLevel.Fourth);
    public static final /* enum */ PlayerClass ShillienSaint = new PlayerClass(Race.darkelf, ClassType.Priest, ClassLevel.Fourth);
    public static final /* enum */ PlayerClass Titan = new PlayerClass(Race.orc, ClassType.Fighter, ClassLevel.Fourth);
    public static final /* enum */ PlayerClass GrandKhauatari = new PlayerClass(Race.orc, ClassType.Fighter, ClassLevel.Fourth);
    public static final /* enum */ PlayerClass Dominator = new PlayerClass(Race.orc, ClassType.Mystic, ClassLevel.Fourth);
    public static final /* enum */ PlayerClass Doomcryer = new PlayerClass(Race.orc, ClassType.Mystic, ClassLevel.Fourth);
    public static final /* enum */ PlayerClass FortuneSeeker = new PlayerClass(Race.dwarf, ClassType.Fighter, ClassLevel.Fourth);
    public static final /* enum */ PlayerClass Maestro = new PlayerClass(Race.dwarf, ClassType.Fighter, ClassLevel.Fourth);
    public static final /* enum */ PlayerClass DummyEntry31 = new PlayerClass(null, null, null);
    public static final /* enum */ PlayerClass DummyEntry32 = new PlayerClass(null, null, null);
    public static final /* enum */ PlayerClass DummyEntry33 = new PlayerClass(null, null, null);
    public static final /* enum */ PlayerClass DummyEntry34 = new PlayerClass(null, null, null);
    public static final PlayerClass[] VALUES;
    private Race a;
    private ClassLevel a;
    private ClassType a;
    private static final Set<PlayerClass> n;
    private static final Set<PlayerClass> o;
    private static final Set<PlayerClass> p;
    private static final Set<PlayerClass> q;
    private static final Set<PlayerClass> r;
    private static final Set<PlayerClass> s;
    private static final Set<PlayerClass> t;
    private static final EnumMap<PlayerClass, Set<PlayerClass>> a;
    private static final /* synthetic */ PlayerClass[] a;

    public static PlayerClass[] values() {
        return (PlayerClass[])a.clone();
    }

    public static PlayerClass valueOf(String string) {
        return Enum.valueOf(PlayerClass.class, string);
    }

    private PlayerClass(Race race, ClassType classType, ClassLevel classLevel) {
        this.a = race;
        this.a = classLevel;
        this.a = classType;
    }

    public final Set<PlayerClass> getAvailableSubclasses() {
        EnumSet<PlayerClass> enumSet = null;
        if (this.a == ClassLevel.Third || this.a == ClassLevel.Fourth) {
            Set set;
            enumSet = EnumSet.copyOf(n);
            if (!Config.ALTSUBCLASS_ALLOW_OVER_AND_WARSMITH_TO_ALL) {
                enumSet.removeAll(o);
            }
            enumSet.remove((Object)this);
            if (!Config.ALTSUBCLASS_ALLOW_FOR_ELF_TO_DARK_ELF) {
                switch (this.a) {
                    case elf: {
                        enumSet.removeAll(PlayerClass.getSet(Race.darkelf, ClassLevel.Third));
                        break;
                    }
                    case darkelf: {
                        enumSet.removeAll(PlayerClass.getSet(Race.elf, ClassLevel.Third));
                    }
                }
            }
            if ((set = (Set)((EnumMap)((Object)a)).get((Object)this)) != null && !Config.ALTSUBCLASS_LIST_ALL) {
                enumSet.removeAll(set);
            }
        }
        return enumSet;
    }

    public static EnumSet<PlayerClass> getSet(Race race, ClassLevel classLevel) {
        EnumSet<PlayerClass> enumSet = EnumSet.noneOf(PlayerClass.class);
        for (PlayerClass playerClass : EnumSet.allOf(PlayerClass.class)) {
            if (race != null && !playerClass.isOfRace(race) || classLevel != null && !playerClass.isOfLevel(classLevel)) continue;
            enumSet.add(playerClass);
        }
        return enumSet;
    }

    public final boolean isOfRace(Race race) {
        return this.a == race;
    }

    public final boolean isOfType(ClassType classType) {
        return this.a == classType;
    }

    public final boolean isOfLevel(ClassLevel classLevel) {
        return this.a == classLevel;
    }

    public static boolean areClassesComportable(PlayerClass playerClass, PlayerClass playerClass2) {
        if (!Config.ALTSUBCLASS_ALLOW_FOR_ELF_TO_DARK_ELF && (playerClass.isOfRace(Race.elf) && playerClass2.isOfRace(Race.darkelf) || playerClass.isOfRace(Race.darkelf) && playerClass2.isOfRace(Race.elf))) {
            return false;
        }
        if (!(Config.ALTSUBCLASS_ALLOW_OVER_AND_WARSMITH_TO_ALL || playerClass != Overlord && playerClass != Warsmith && playerClass2 != Overlord && playerClass2 != Warsmith)) {
            return false;
        }
        return ((EnumMap)((Object)a)).get((Object)playerClass) != ((EnumMap)((Object)a)).get((Object)playerClass2);
    }

    private static /* synthetic */ PlayerClass[] a() {
        return new PlayerClass[]{HumanFighter, Warrior, Gladiator, Warlord, HumanKnight, Paladin, DarkAvenger, Rogue, TreasureHunter, Hawkeye, HumanMystic, HumanWizard, Sorceror, Necromancer, Warlock, Cleric, Bishop, Prophet, ElvenFighter, ElvenKnight, TempleKnight, Swordsinger, ElvenScout, Plainswalker, SilverRanger, ElvenMystic, ElvenWizard, Spellsinger, ElementalSummoner, ElvenOracle, ElvenElder, DarkElvenFighter, PalusKnight, ShillienKnight, Bladedancer, Assassin, AbyssWalker, PhantomRanger, DarkElvenMystic, DarkElvenWizard, Spellhowler, PhantomSummoner, ShillienOracle, ShillienElder, OrcFighter, orcRaider, Destroyer, orcMonk, Tyrant, orcMystic, orcShaman, Overlord, Warcryer, DwarvenFighter, DwarvenScavenger, BountyHunter, DwarvenArtisan, Warsmith, DummyEntry1, DummyEntry2, DummyEntry3, DummyEntry4, DummyEntry5, DummyEntry6, DummyEntry7, DummyEntry8, DummyEntry9, DummyEntry10, DummyEntry11, DummyEntry12, DummyEntry13, DummyEntry14, DummyEntry15, DummyEntry16, DummyEntry17, DummyEntry18, DummyEntry19, DummyEntry20, DummyEntry21, DummyEntry22, DummyEntry23, DummyEntry24, DummyEntry25, DummyEntry26, DummyEntry27, DummyEntry28, DummyEntry29, DummyEntry30, Duelist, Dreadnought, PhoenixKnight, HellKnight, Sagittarius, Adventurer, Archmage, Soultaker, ArcanaLord, Cardinal, Hierophant, EvaTemplar, SwordMuse, WindRider, MoonlightSentinel, MysticMuse, ElementalMaster, EvaSaint, ShillienTemplar, SpectralDancer, GhostHunter, GhostSentinel, StormScreamer, SpectralMaster, ShillienSaint, Titan, GrandKhauatari, Dominator, Doomcryer, FortuneSeeker, Maestro, DummyEntry31, DummyEntry32, DummyEntry33, DummyEntry34};
    }

    static {
        a = PlayerClass.a();
        VALUES = PlayerClass.values();
        o = EnumSet.of(Overlord, Warsmith);
        p = EnumSet.of(DarkAvenger, Paladin, TempleKnight, ShillienKnight);
        q = EnumSet.of(TreasureHunter, AbyssWalker, Plainswalker);
        r = EnumSet.of(Hawkeye, SilverRanger, PhantomRanger);
        s = EnumSet.of(Warlock, ElementalSummoner, PhantomSummoner);
        t = EnumSet.of(Sorceror, Spellsinger, Spellhowler);
        a = new EnumMap(PlayerClass.class);
        EnumSet<PlayerClass> enumSet = PlayerClass.getSet(null, ClassLevel.Third);
        if (!Config.ALTSUBCLASS_ALLOW_OVER_AND_WARSMITH_TO_ALL) {
            enumSet.removeAll(o);
        }
        n = enumSet;
        ((EnumMap)((Object)a)).put(DarkAvenger, p);
        ((EnumMap)((Object)a)).put(HellKnight, p);
        ((EnumMap)((Object)a)).put(Paladin, p);
        ((EnumMap)((Object)a)).put(PhoenixKnight, p);
        ((EnumMap)((Object)a)).put(TempleKnight, p);
        ((EnumMap)((Object)a)).put(EvaTemplar, p);
        ((EnumMap)((Object)a)).put(ShillienKnight, p);
        ((EnumMap)((Object)a)).put(ShillienTemplar, p);
        ((EnumMap)((Object)a)).put(TreasureHunter, q);
        ((EnumMap)((Object)a)).put(Adventurer, q);
        ((EnumMap)((Object)a)).put(AbyssWalker, q);
        ((EnumMap)((Object)a)).put(GhostHunter, q);
        ((EnumMap)((Object)a)).put(Plainswalker, q);
        ((EnumMap)((Object)a)).put(WindRider, q);
        ((EnumMap)((Object)a)).put(Hawkeye, r);
        ((EnumMap)((Object)a)).put(Sagittarius, r);
        ((EnumMap)((Object)a)).put(SilverRanger, r);
        ((EnumMap)((Object)a)).put(MoonlightSentinel, r);
        ((EnumMap)((Object)a)).put(PhantomRanger, r);
        ((EnumMap)((Object)a)).put(GhostSentinel, r);
        ((EnumMap)((Object)a)).put(Warlock, s);
        ((EnumMap)((Object)a)).put(ArcanaLord, s);
        ((EnumMap)((Object)a)).put(ElementalSummoner, s);
        ((EnumMap)((Object)a)).put(ElementalMaster, s);
        ((EnumMap)((Object)a)).put(PhantomSummoner, s);
        ((EnumMap)((Object)a)).put(SpectralMaster, s);
        ((EnumMap)((Object)a)).put(Sorceror, t);
        ((EnumMap)((Object)a)).put(Archmage, t);
        ((EnumMap)((Object)a)).put(Spellsinger, t);
        ((EnumMap)((Object)a)).put(MysticMuse, t);
        ((EnumMap)((Object)a)).put(Spellhowler, t);
        ((EnumMap)((Object)a)).put(StormScreamer, t);
        ((EnumMap)((Object)a)).put(Duelist, EnumSet.of(Gladiator));
        ((EnumMap)((Object)a)).put(Dreadnought, EnumSet.of(Warlord));
        ((EnumMap)((Object)a)).put(Soultaker, EnumSet.of(Necromancer));
        ((EnumMap)((Object)a)).put(Cardinal, EnumSet.of(Bishop));
        ((EnumMap)((Object)a)).put(Hierophant, EnumSet.of(Prophet));
        ((EnumMap)((Object)a)).put(SwordMuse, EnumSet.of(Swordsinger));
        ((EnumMap)((Object)a)).put(EvaSaint, EnumSet.of(ElvenElder));
        ((EnumMap)((Object)a)).put(SpectralDancer, EnumSet.of(Bladedancer));
        ((EnumMap)((Object)a)).put(Titan, EnumSet.of(Destroyer));
        ((EnumMap)((Object)a)).put(GrandKhauatari, EnumSet.of(Tyrant));
        ((EnumMap)((Object)a)).put(Dominator, EnumSet.of(Overlord));
        ((EnumMap)((Object)a)).put(Doomcryer, EnumSet.of(Warcryer));
    }
}
