/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.TIntObjectHashMap
 */
package l2.gameserver.model.base;

import gnu.trove.TIntObjectHashMap;
import l2.gameserver.model.Player;
import l2.gameserver.model.base.CategoryData;
import l2.gameserver.model.base.ClassType2;
import l2.gameserver.model.base.Race;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class ClassId
extends Enum<ClassId> {
    public static final /* enum */ ClassId fighter = new ClassId(0, false, Race.human, null, 1, null);
    public static final /* enum */ ClassId warrior = new ClassId(1, false, Race.human, fighter, 2, null);
    public static final /* enum */ ClassId gladiator = new ClassId(2, false, Race.human, warrior, 3, ClassType2.Warrior);
    public static final /* enum */ ClassId warlord = new ClassId(3, false, Race.human, warrior, 3, ClassType2.Warrior);
    public static final /* enum */ ClassId knight = new ClassId(4, false, Race.human, fighter, 2, null);
    public static final /* enum */ ClassId paladin = new ClassId(5, false, Race.human, knight, 3, ClassType2.Knight);
    public static final /* enum */ ClassId dark_avenger = new ClassId(6, false, Race.human, knight, 3, ClassType2.Knight);
    public static final /* enum */ ClassId rogue = new ClassId(7, false, Race.human, fighter, 2, null);
    public static final /* enum */ ClassId treasure_hunter = new ClassId(8, false, Race.human, rogue, 3, ClassType2.Rogue);
    public static final /* enum */ ClassId hawkeye = new ClassId(9, false, Race.human, rogue, 3, ClassType2.Rogue);
    public static final /* enum */ ClassId mage = new ClassId(10, true, Race.human, null, 1, null);
    public static final /* enum */ ClassId wizard = new ClassId(11, true, Race.human, mage, 2, null);
    public static final /* enum */ ClassId sorcerer = new ClassId(12, true, Race.human, wizard, 3, ClassType2.Wizard);
    public static final /* enum */ ClassId necromancer = new ClassId(13, true, Race.human, wizard, 3, ClassType2.Wizard);
    public static final /* enum */ ClassId warlock = new ClassId(14, true, Race.human, wizard, 3, ClassType2.Summoner);
    public static final /* enum */ ClassId cleric = new ClassId(15, true, Race.human, mage, 2, null);
    public static final /* enum */ ClassId bishop = new ClassId(16, true, Race.human, cleric, 3, ClassType2.Healer);
    public static final /* enum */ ClassId prophet = new ClassId(17, true, Race.human, cleric, 3, ClassType2.Enchanter);
    public static final /* enum */ ClassId elven_fighter = new ClassId(18, false, Race.elf, null, 1, null);
    public static final /* enum */ ClassId elven_knight = new ClassId(19, false, Race.elf, elven_fighter, 2, null);
    public static final /* enum */ ClassId temple_knight = new ClassId(20, false, Race.elf, elven_knight, 3, ClassType2.Knight);
    public static final /* enum */ ClassId swordsinger = new ClassId(21, false, Race.elf, elven_knight, 3, ClassType2.Enchanter);
    public static final /* enum */ ClassId elven_scout = new ClassId(22, false, Race.elf, elven_fighter, 2, null);
    public static final /* enum */ ClassId plains_walker = new ClassId(23, false, Race.elf, elven_scout, 3, ClassType2.Rogue);
    public static final /* enum */ ClassId silver_ranger = new ClassId(24, false, Race.elf, elven_scout, 3, ClassType2.Rogue);
    public static final /* enum */ ClassId elven_mage = new ClassId(25, true, Race.elf, null, 1, null);
    public static final /* enum */ ClassId elven_wizard = new ClassId(26, true, Race.elf, elven_mage, 2, null);
    public static final /* enum */ ClassId spellsinger = new ClassId(27, true, Race.elf, elven_wizard, 3, ClassType2.Wizard);
    public static final /* enum */ ClassId elemental_summoner = new ClassId(28, true, Race.elf, elven_wizard, 3, ClassType2.Summoner);
    public static final /* enum */ ClassId oracle = new ClassId(29, true, Race.elf, elven_mage, 2, null);
    public static final /* enum */ ClassId elder = new ClassId(30, true, Race.elf, oracle, 3, ClassType2.Healer);
    public static final /* enum */ ClassId dark_fighter = new ClassId(31, false, Race.darkelf, null, 1, null);
    public static final /* enum */ ClassId palus_knight = new ClassId(32, false, Race.darkelf, dark_fighter, 2, null);
    public static final /* enum */ ClassId shillien_knight = new ClassId(33, false, Race.darkelf, palus_knight, 3, ClassType2.Knight);
    public static final /* enum */ ClassId bladedancer = new ClassId(34, false, Race.darkelf, palus_knight, 3, ClassType2.Enchanter);
    public static final /* enum */ ClassId assassin = new ClassId(35, false, Race.darkelf, dark_fighter, 2, null);
    public static final /* enum */ ClassId abyss_walker = new ClassId(36, false, Race.darkelf, assassin, 3, ClassType2.Rogue);
    public static final /* enum */ ClassId phantom_ranger = new ClassId(37, false, Race.darkelf, assassin, 3, ClassType2.Rogue);
    public static final /* enum */ ClassId dark_mage = new ClassId(38, true, Race.darkelf, null, 1, null);
    public static final /* enum */ ClassId dark_wizard = new ClassId(39, true, Race.darkelf, dark_mage, 2, null);
    public static final /* enum */ ClassId spellhowler = new ClassId(40, true, Race.darkelf, dark_wizard, 3, ClassType2.Wizard);
    public static final /* enum */ ClassId phantom_summoner = new ClassId(41, true, Race.darkelf, dark_wizard, 3, ClassType2.Summoner);
    public static final /* enum */ ClassId shillien_oracle = new ClassId(42, true, Race.darkelf, dark_mage, 2, null);
    public static final /* enum */ ClassId shillien_elder = new ClassId(43, true, Race.darkelf, shillien_oracle, 3, ClassType2.Healer);
    public static final /* enum */ ClassId orc_fighter = new ClassId(44, false, Race.orc, null, 1, null);
    public static final /* enum */ ClassId orc_raider = new ClassId(45, false, Race.orc, orc_fighter, 2, null);
    public static final /* enum */ ClassId destroyer = new ClassId(46, false, Race.orc, orc_raider, 3, ClassType2.Warrior);
    public static final /* enum */ ClassId orc_monk = new ClassId(47, false, Race.orc, orc_fighter, 2, null);
    public static final /* enum */ ClassId tyrant = new ClassId(48, false, Race.orc, orc_monk, 3, ClassType2.Warrior);
    public static final /* enum */ ClassId orc_mage = new ClassId(49, true, Race.orc, null, 1, null);
    public static final /* enum */ ClassId orc_shaman = new ClassId(50, true, Race.orc, orc_mage, 2, null);
    public static final /* enum */ ClassId overlord = new ClassId(51, true, Race.orc, orc_shaman, 3, ClassType2.Enchanter);
    public static final /* enum */ ClassId warcryer = new ClassId(52, true, Race.orc, orc_shaman, 3, ClassType2.Enchanter);
    public static final /* enum */ ClassId dwarven_fighter = new ClassId(53, false, Race.dwarf, null, 1, null);
    public static final /* enum */ ClassId scavenger = new ClassId(54, false, Race.dwarf, dwarven_fighter, 2, null);
    public static final /* enum */ ClassId bounty_hunter = new ClassId(55, false, Race.dwarf, scavenger, 3, ClassType2.Warrior);
    public static final /* enum */ ClassId artisan = new ClassId(56, false, Race.dwarf, dwarven_fighter, 2, null);
    public static final /* enum */ ClassId warsmith = new ClassId(57, false, Race.dwarf, artisan, 3, ClassType2.Warrior);
    public static final /* enum */ ClassId dummyEntry1 = new ClassId(58, false, null, null, 0, null);
    public static final /* enum */ ClassId dummyEntry2 = new ClassId(59, false, null, null, 0, null);
    public static final /* enum */ ClassId dummyEntry3 = new ClassId(60, false, null, null, 0, null);
    public static final /* enum */ ClassId dummyEntry4 = new ClassId(61, false, null, null, 0, null);
    public static final /* enum */ ClassId dummyEntry5 = new ClassId(62, false, null, null, 0, null);
    public static final /* enum */ ClassId dummyEntry6 = new ClassId(63, false, null, null, 0, null);
    public static final /* enum */ ClassId dummyEntry7 = new ClassId(64, false, null, null, 0, null);
    public static final /* enum */ ClassId dummyEntry8 = new ClassId(65, false, null, null, 0, null);
    public static final /* enum */ ClassId dummyEntry9 = new ClassId(66, false, null, null, 0, null);
    public static final /* enum */ ClassId dummyEntry10 = new ClassId(67, false, null, null, 0, null);
    public static final /* enum */ ClassId dummyEntry11 = new ClassId(68, false, null, null, 0, null);
    public static final /* enum */ ClassId dummyEntry12 = new ClassId(69, false, null, null, 0, null);
    public static final /* enum */ ClassId dummyEntry13 = new ClassId(70, false, null, null, 0, null);
    public static final /* enum */ ClassId dummyEntry14 = new ClassId(71, false, null, null, 0, null);
    public static final /* enum */ ClassId dummyEntry15 = new ClassId(72, false, null, null, 0, null);
    public static final /* enum */ ClassId dummyEntry16 = new ClassId(73, false, null, null, 0, null);
    public static final /* enum */ ClassId dummyEntry17 = new ClassId(74, false, null, null, 0, null);
    public static final /* enum */ ClassId dummyEntry18 = new ClassId(75, false, null, null, 0, null);
    public static final /* enum */ ClassId dummyEntry19 = new ClassId(76, false, null, null, 0, null);
    public static final /* enum */ ClassId dummyEntry20 = new ClassId(77, false, null, null, 0, null);
    public static final /* enum */ ClassId dummyEntry21 = new ClassId(78, false, null, null, 0, null);
    public static final /* enum */ ClassId dummyEntry22 = new ClassId(79, false, null, null, 0, null);
    public static final /* enum */ ClassId dummyEntry23 = new ClassId(80, false, null, null, 0, null);
    public static final /* enum */ ClassId dummyEntry24 = new ClassId(81, false, null, null, 0, null);
    public static final /* enum */ ClassId dummyEntry25 = new ClassId(82, false, null, null, 0, null);
    public static final /* enum */ ClassId dummyEntry26 = new ClassId(83, false, null, null, 0, null);
    public static final /* enum */ ClassId dummyEntry27 = new ClassId(84, false, null, null, 0, null);
    public static final /* enum */ ClassId dummyEntry28 = new ClassId(85, false, null, null, 0, null);
    public static final /* enum */ ClassId dummyEntry29 = new ClassId(86, false, null, null, 0, null);
    public static final /* enum */ ClassId dummyEntry30 = new ClassId(87, false, null, null, 0, null);
    public static final /* enum */ ClassId duelist = new ClassId(88, false, Race.human, gladiator, 4, ClassType2.Warrior);
    public static final /* enum */ ClassId dreadnought = new ClassId(89, false, Race.human, warlord, 4, ClassType2.Warrior);
    public static final /* enum */ ClassId phoenix_knight = new ClassId(90, false, Race.human, paladin, 4, ClassType2.Knight);
    public static final /* enum */ ClassId hell_knight = new ClassId(91, false, Race.human, dark_avenger, 4, ClassType2.Knight);
    public static final /* enum */ ClassId sagittarius = new ClassId(92, false, Race.human, hawkeye, 4, ClassType2.Rogue);
    public static final /* enum */ ClassId adventurer = new ClassId(93, false, Race.human, treasure_hunter, 4, ClassType2.Rogue);
    public static final /* enum */ ClassId archmage = new ClassId(94, true, Race.human, sorcerer, 4, ClassType2.Wizard);
    public static final /* enum */ ClassId soultaker = new ClassId(95, true, Race.human, necromancer, 4, ClassType2.Wizard);
    public static final /* enum */ ClassId arcana_lord = new ClassId(96, true, Race.human, warlock, 4, ClassType2.Summoner);
    public static final /* enum */ ClassId cardinal = new ClassId(97, true, Race.human, bishop, 4, ClassType2.Healer);
    public static final /* enum */ ClassId hierophant = new ClassId(98, true, Race.human, prophet, 4, ClassType2.Enchanter);
    public static final /* enum */ ClassId evas_templar = new ClassId(99, false, Race.elf, temple_knight, 4, ClassType2.Knight);
    public static final /* enum */ ClassId sword_muse = new ClassId(100, false, Race.elf, swordsinger, 4, ClassType2.Enchanter);
    public static final /* enum */ ClassId wind_rider = new ClassId(101, false, Race.elf, plains_walker, 4, ClassType2.Rogue);
    public static final /* enum */ ClassId moonlight_sentinel = new ClassId(102, false, Race.elf, silver_ranger, 4, ClassType2.Rogue);
    public static final /* enum */ ClassId mystic_muse = new ClassId(103, true, Race.elf, spellsinger, 4, ClassType2.Wizard);
    public static final /* enum */ ClassId elemental_master = new ClassId(104, true, Race.elf, elemental_summoner, 4, ClassType2.Summoner);
    public static final /* enum */ ClassId evas_saint = new ClassId(105, true, Race.elf, elder, 4, ClassType2.Healer);
    public static final /* enum */ ClassId shillien_templar = new ClassId(106, false, Race.darkelf, shillien_knight, 4, ClassType2.Knight);
    public static final /* enum */ ClassId spectral_dancer = new ClassId(107, false, Race.darkelf, bladedancer, 4, ClassType2.Enchanter);
    public static final /* enum */ ClassId ghost_hunter = new ClassId(108, false, Race.darkelf, abyss_walker, 4, ClassType2.Rogue);
    public static final /* enum */ ClassId ghost_sentinel = new ClassId(109, false, Race.darkelf, phantom_ranger, 4, ClassType2.Rogue);
    public static final /* enum */ ClassId storm_screamer = new ClassId(110, true, Race.darkelf, spellhowler, 4, ClassType2.Wizard);
    public static final /* enum */ ClassId spectral_master = new ClassId(111, true, Race.darkelf, phantom_summoner, 4, ClassType2.Summoner);
    public static final /* enum */ ClassId shillien_saint = new ClassId(112, true, Race.darkelf, shillien_elder, 4, ClassType2.Healer);
    public static final /* enum */ ClassId titan = new ClassId(113, false, Race.orc, destroyer, 4, ClassType2.Warrior);
    public static final /* enum */ ClassId grand_khavatari = new ClassId(114, false, Race.orc, tyrant, 4, ClassType2.Warrior);
    public static final /* enum */ ClassId dominator = new ClassId(115, true, Race.orc, overlord, 4, ClassType2.Enchanter);
    public static final /* enum */ ClassId doomcryer = new ClassId(116, true, Race.orc, warcryer, 4, ClassType2.Enchanter);
    public static final /* enum */ ClassId fortune_seeker = new ClassId(117, false, Race.dwarf, bounty_hunter, 4, ClassType2.Warrior);
    public static final /* enum */ ClassId maestro = new ClassId(118, false, Race.dwarf, warsmith, 4, ClassType2.Warrior);
    public static final /* enum */ ClassId dummyEntry31 = new ClassId(119, false, null, null, 0, null);
    public static final /* enum */ ClassId dummyEntry32 = new ClassId(120, false, null, null, 0, null);
    public static final /* enum */ ClassId dummyEntry33 = new ClassId(121, false, null, null, 0, null);
    public static final /* enum */ ClassId dummyEntry34 = new ClassId(122, false, null, null, 0, null);
    public static final ClassId[] VALUES;
    private static TIntObjectHashMap<ClassId> w;
    private final int kC;
    private final boolean cS;
    private final Race a;
    private final ClassId a;
    private final ClassType2 b;
    private final int kD;
    private static final /* synthetic */ ClassId[] a;

    public static ClassId[] values() {
        return (ClassId[])a.clone();
    }

    public static ClassId valueOf(String string) {
        return Enum.valueOf(ClassId.class, string);
    }

    public static ClassId getClassById(int n) {
        return (ClassId)((Object)w.get(n));
    }

    private ClassId(int n2, boolean bl, Race race, ClassId classId, int n3, ClassType2 classType2) {
        this.kC = n2;
        this.cS = bl;
        this.a = race;
        this.a = classId;
        this.kD = n3;
        this.b = classType2;
    }

    public final int getId() {
        return this.kC;
    }

    public final boolean isMage() {
        return this.cS;
    }

    public final Race getRace() {
        return this.a;
    }

    public final boolean childOf(ClassId classId) {
        if (this.a == null) {
            return false;
        }
        if (this.a == classId) {
            return true;
        }
        return this.a.childOf(classId);
    }

    public final boolean equalsOrChildOf(ClassId classId) {
        return this == classId || this.childOf(classId);
    }

    public final int level() {
        if (this.a == null) {
            return 0;
        }
        return 1 + this.a.level();
    }

    public final ClassId getParent() {
        return this.a;
    }

    public final int getLevel() {
        return this.kD;
    }

    public ClassType2 getType2() {
        return this.b;
    }

    public ClassId getRootClassId() {
        if (this.getParent() != null) {
            return this.getParent().getRootClassId();
        }
        return this;
    }

    public boolean isBelongTo(CategoryData categoryData) {
        return categoryData.isBelong(this);
    }

    public boolean isPlayerBelong(CategoryData categoryData, Player player) {
        return categoryData.isPlayerBelong(player);
    }

    public boolean isPlayerBaseClassBelong(CategoryData categoryData, Player player) {
        return categoryData.isPlayerBaseClassBelong(player);
    }

    private static /* synthetic */ ClassId[] a() {
        return new ClassId[]{fighter, warrior, gladiator, warlord, knight, paladin, dark_avenger, rogue, treasure_hunter, hawkeye, mage, wizard, sorcerer, necromancer, warlock, cleric, bishop, prophet, elven_fighter, elven_knight, temple_knight, swordsinger, elven_scout, plains_walker, silver_ranger, elven_mage, elven_wizard, spellsinger, elemental_summoner, oracle, elder, dark_fighter, palus_knight, shillien_knight, bladedancer, assassin, abyss_walker, phantom_ranger, dark_mage, dark_wizard, spellhowler, phantom_summoner, shillien_oracle, shillien_elder, orc_fighter, orc_raider, destroyer, orc_monk, tyrant, orc_mage, orc_shaman, overlord, warcryer, dwarven_fighter, scavenger, bounty_hunter, artisan, warsmith, dummyEntry1, dummyEntry2, dummyEntry3, dummyEntry4, dummyEntry5, dummyEntry6, dummyEntry7, dummyEntry8, dummyEntry9, dummyEntry10, dummyEntry11, dummyEntry12, dummyEntry13, dummyEntry14, dummyEntry15, dummyEntry16, dummyEntry17, dummyEntry18, dummyEntry19, dummyEntry20, dummyEntry21, dummyEntry22, dummyEntry23, dummyEntry24, dummyEntry25, dummyEntry26, dummyEntry27, dummyEntry28, dummyEntry29, dummyEntry30, duelist, dreadnought, phoenix_knight, hell_knight, sagittarius, adventurer, archmage, soultaker, arcana_lord, cardinal, hierophant, evas_templar, sword_muse, wind_rider, moonlight_sentinel, mystic_muse, elemental_master, evas_saint, shillien_templar, spectral_dancer, ghost_hunter, ghost_sentinel, storm_screamer, spectral_master, shillien_saint, titan, grand_khavatari, dominator, doomcryer, fortune_seeker, maestro, dummyEntry31, dummyEntry32, dummyEntry33, dummyEntry34};
    }

    static {
        a = ClassId.a();
        VALUES = ClassId.values();
        w = new TIntObjectHashMap();
        for (ClassId classId : ClassId.values()) {
            w.put(classId.getId(), (Object)classId);
        }
    }
}
