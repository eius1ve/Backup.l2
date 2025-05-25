/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.TIntHashSet
 */
package l2.gameserver.model.base;

import gnu.trove.TIntHashSet;
import l2.gameserver.model.Player;
import l2.gameserver.model.base.ClassId;

public final class CategoryData
extends Enum<CategoryData> {
    public static final /* enum */ CategoryData fighter_group = new CategoryData(ClassId.fighter, ClassId.warrior, ClassId.gladiator, ClassId.warlord, ClassId.knight, ClassId.paladin, ClassId.dark_avenger, ClassId.rogue, ClassId.treasure_hunter, ClassId.hawkeye, ClassId.elven_fighter, ClassId.elven_knight, ClassId.temple_knight, ClassId.swordsinger, ClassId.elven_scout, ClassId.plains_walker, ClassId.silver_ranger, ClassId.dark_fighter, ClassId.palus_knight, ClassId.shillien_knight, ClassId.bladedancer, ClassId.assassin, ClassId.abyss_walker, ClassId.phantom_ranger, ClassId.orc_fighter, ClassId.orc_raider, ClassId.destroyer, ClassId.orc_monk, ClassId.tyrant, ClassId.dwarven_fighter, ClassId.scavenger, ClassId.bounty_hunter, ClassId.artisan, ClassId.warsmith, ClassId.duelist, ClassId.dreadnought, ClassId.phoenix_knight, ClassId.hell_knight, ClassId.sagittarius, ClassId.adventurer, ClassId.evas_templar, ClassId.sword_muse, ClassId.wind_rider, ClassId.moonlight_sentinel, ClassId.shillien_templar, ClassId.spectral_dancer, ClassId.ghost_hunter, ClassId.ghost_sentinel, ClassId.titan, ClassId.grand_khavatari, ClassId.fortune_seeker, ClassId.maestro);
    public static final /* enum */ CategoryData mage_group = new CategoryData(ClassId.mage, ClassId.wizard, ClassId.sorcerer, ClassId.necromancer, ClassId.warlock, ClassId.cleric, ClassId.bishop, ClassId.prophet, ClassId.elven_mage, ClassId.elven_wizard, ClassId.spellsinger, ClassId.elemental_summoner, ClassId.oracle, ClassId.elder, ClassId.dark_mage, ClassId.dark_wizard, ClassId.spellhowler, ClassId.phantom_summoner, ClassId.shillien_oracle, ClassId.shillien_elder, ClassId.orc_mage, ClassId.orc_shaman, ClassId.overlord, ClassId.warcryer, ClassId.archmage, ClassId.soultaker, ClassId.arcana_lord, ClassId.cardinal, ClassId.hierophant, ClassId.mystic_muse, ClassId.elemental_master, ClassId.evas_saint, ClassId.storm_screamer, ClassId.spectral_master, ClassId.shillien_saint, ClassId.dominator, ClassId.doomcryer);
    public static final /* enum */ CategoryData wizard_group = new CategoryData(ClassId.mage, ClassId.wizard, ClassId.sorcerer, ClassId.necromancer, ClassId.warlock, ClassId.elven_mage, ClassId.elven_wizard, ClassId.spellsinger, ClassId.elemental_summoner, ClassId.dark_mage, ClassId.dark_wizard, ClassId.spellhowler, ClassId.phantom_summoner, ClassId.archmage, ClassId.soultaker, ClassId.arcana_lord, ClassId.mystic_muse, ClassId.elemental_master, ClassId.storm_screamer, ClassId.spectral_master, ClassId.orc_mage, ClassId.orc_shaman, ClassId.overlord, ClassId.warcryer, ClassId.dominator, ClassId.doomcryer);
    public static final /* enum */ CategoryData cleric_group = new CategoryData(ClassId.mage, ClassId.cleric, ClassId.bishop, ClassId.prophet, ClassId.elven_mage, ClassId.oracle, ClassId.elder, ClassId.dark_mage, ClassId.shillien_oracle, ClassId.shillien_elder, ClassId.cardinal, ClassId.hierophant, ClassId.evas_saint);
    public static final /* enum */ CategoryData attacker_group = new CategoryData(ClassId.fighter, ClassId.warrior, ClassId.gladiator, ClassId.warlord, ClassId.rogue, ClassId.treasure_hunter, ClassId.hawkeye, ClassId.elven_fighter, ClassId.elven_scout, ClassId.plains_walker, ClassId.silver_ranger, ClassId.dark_fighter, ClassId.assassin, ClassId.abyss_walker, ClassId.phantom_ranger, ClassId.orc_fighter, ClassId.orc_raider, ClassId.destroyer, ClassId.orc_monk, ClassId.tyrant, ClassId.dwarven_fighter, ClassId.duelist, ClassId.dreadnought, ClassId.adventurer, ClassId.sagittarius, ClassId.moonlight_sentinel, ClassId.wind_rider, ClassId.ghost_hunter, ClassId.ghost_sentinel, ClassId.titan, ClassId.grand_khavatari);
    public static final /* enum */ CategoryData tanker_group = new CategoryData(ClassId.knight, ClassId.paladin, ClassId.dark_avenger, ClassId.phoenix_knight, ClassId.hell_knight, ClassId.elven_knight, ClassId.temple_knight, ClassId.evas_templar, ClassId.palus_knight, ClassId.shillien_knight, ClassId.shillien_templar, ClassId.scavenger, ClassId.artisan, ClassId.bounty_hunter, ClassId.warsmith, ClassId.fortune_seeker, ClassId.maestro);
    public static final /* enum */ CategoryData first_class_group = new CategoryData(ClassId.fighter, ClassId.mage, ClassId.elven_fighter, ClassId.elven_mage, ClassId.dark_fighter, ClassId.dark_mage, ClassId.orc_fighter, ClassId.orc_mage, ClassId.dwarven_fighter);
    public static final /* enum */ CategoryData second_class_group = new CategoryData(ClassId.warrior, ClassId.knight, ClassId.rogue, ClassId.wizard, ClassId.cleric, ClassId.elven_knight, ClassId.elven_scout, ClassId.elven_wizard, ClassId.oracle, ClassId.palus_knight, ClassId.assassin, ClassId.dark_wizard, ClassId.shillien_oracle, ClassId.orc_raider, ClassId.orc_monk, ClassId.orc_shaman, ClassId.scavenger, ClassId.artisan);
    public static final /* enum */ CategoryData third_class_group = new CategoryData(ClassId.gladiator, ClassId.warlord, ClassId.paladin, ClassId.dark_avenger, ClassId.treasure_hunter, ClassId.hawkeye, ClassId.sorcerer, ClassId.necromancer, ClassId.warlock, ClassId.bishop, ClassId.prophet, ClassId.temple_knight, ClassId.swordsinger, ClassId.plains_walker, ClassId.silver_ranger, ClassId.spellsinger, ClassId.elemental_summoner, ClassId.elder, ClassId.shillien_knight, ClassId.bladedancer, ClassId.abyss_walker, ClassId.phantom_ranger, ClassId.spellhowler, ClassId.phantom_summoner, ClassId.shillien_elder, ClassId.destroyer, ClassId.tyrant, ClassId.overlord, ClassId.warcryer, ClassId.bounty_hunter, ClassId.warsmith);
    public static final /* enum */ CategoryData fourth_class_group = new CategoryData(ClassId.duelist, ClassId.dreadnought, ClassId.phoenix_knight, ClassId.hell_knight, ClassId.sagittarius, ClassId.adventurer, ClassId.archmage, ClassId.soultaker, ClassId.arcana_lord, ClassId.cardinal, ClassId.hierophant, ClassId.evas_templar, ClassId.sword_muse, ClassId.wind_rider, ClassId.moonlight_sentinel, ClassId.mystic_muse, ClassId.elemental_master, ClassId.evas_saint, ClassId.shillien_templar, ClassId.spectral_dancer, ClassId.ghost_hunter, ClassId.ghost_sentinel, ClassId.storm_screamer, ClassId.spectral_master, ClassId.shillien_saint, ClassId.titan, ClassId.grand_khavatari, ClassId.dominator, ClassId.doomcryer, ClassId.fortune_seeker, ClassId.maestro);
    public static final /* enum */ CategoryData bounty_hunter_group = new CategoryData(ClassId.dwarven_fighter, ClassId.scavenger, ClassId.bounty_hunter, ClassId.fortune_seeker);
    public static final /* enum */ CategoryData warsmith_group = new CategoryData(ClassId.dwarven_fighter, ClassId.artisan, ClassId.warsmith, ClassId.maestro);
    public static final /* enum */ CategoryData knight_group = new CategoryData(ClassId.knight, ClassId.elven_knight, ClassId.palus_knight);
    public static final /* enum */ CategoryData white_magic_group = new CategoryData(ClassId.cleric, ClassId.oracle, ClassId.paladin, ClassId.elven_knight, ClassId.knight);
    public static final /* enum */ CategoryData heal_group = new CategoryData(ClassId.cleric, ClassId.oracle, ClassId.shillien_oracle, ClassId.orc_shaman);
    public static final /* enum */ CategoryData assist_magic_group = new CategoryData(ClassId.cleric, ClassId.shillien_oracle);
    public static final /* enum */ CategoryData warrior_group = new CategoryData(ClassId.warrior, ClassId.elven_knight, ClassId.palus_knight, ClassId.orc_raider, ClassId.orc_monk);
    public static final /* enum */ CategoryData human_2nd_group = new CategoryData(ClassId.warrior, ClassId.knight, ClassId.rogue, ClassId.wizard, ClassId.cleric);
    public static final /* enum */ CategoryData elf_2nd_group = new CategoryData(ClassId.elven_scout, ClassId.elven_knight, ClassId.elven_wizard, ClassId.oracle);
    public static final /* enum */ CategoryData delf_2nd_group = new CategoryData(ClassId.assassin, ClassId.palus_knight, ClassId.dark_wizard, ClassId.shillien_oracle);
    public static final /* enum */ CategoryData orc_2nd_group = new CategoryData(ClassId.orc_shaman, ClassId.orc_raider, ClassId.orc_monk);
    public static final /* enum */ CategoryData dwarf_2nd_group = new CategoryData(ClassId.scavenger, ClassId.artisan);
    public static final /* enum */ CategoryData subjob_group_dagger = new CategoryData(ClassId.treasure_hunter, ClassId.plains_walker, ClassId.abyss_walker, ClassId.adventurer, ClassId.wind_rider, ClassId.ghost_hunter);
    public static final /* enum */ CategoryData subjob_group_bow = new CategoryData(ClassId.hawkeye, ClassId.silver_ranger, ClassId.phantom_ranger, ClassId.sagittarius, ClassId.moonlight_sentinel, ClassId.ghost_sentinel);
    public static final /* enum */ CategoryData subjob_group_knight = new CategoryData(ClassId.paladin, ClassId.dark_avenger, ClassId.shillien_knight, ClassId.temple_knight, ClassId.phoenix_knight, ClassId.hell_knight, ClassId.shillien_templar, ClassId.evas_templar);
    public static final /* enum */ CategoryData subjob_group_summoner = new CategoryData(ClassId.warlock, ClassId.elemental_summoner, ClassId.phantom_summoner, ClassId.arcana_lord, ClassId.elemental_master, ClassId.spectral_master);
    public static final /* enum */ CategoryData subjob_group_half_healer = new CategoryData(ClassId.elder, ClassId.shillien_elder, ClassId.evas_saint, ClassId.shillien_saint);
    public static final /* enum */ CategoryData subjob_group_dance = new CategoryData(ClassId.swordsinger, ClassId.bladedancer, ClassId.sword_muse, ClassId.spectral_dancer);
    public static final /* enum */ CategoryData subjob_group_wizard = new CategoryData(ClassId.sorcerer, ClassId.spellsinger, ClassId.spellhowler, ClassId.archmage, ClassId.mystic_muse, ClassId.storm_screamer);
    public static final /* enum */ CategoryData human_fall_class = new CategoryData(ClassId.fighter, ClassId.warrior, ClassId.knight, ClassId.rogue, ClassId.gladiator, ClassId.warlord, ClassId.paladin, ClassId.dark_avenger, ClassId.treasure_hunter, ClassId.hawkeye, ClassId.duelist, ClassId.dreadnought, ClassId.phoenix_knight, ClassId.hell_knight, ClassId.sagittarius, ClassId.adventurer);
    public static final /* enum */ CategoryData human_wall_class = new CategoryData(ClassId.mage, ClassId.wizard, ClassId.sorcerer, ClassId.necromancer, ClassId.warlock, ClassId.archmage, ClassId.soultaker, ClassId.arcana_lord);
    public static final /* enum */ CategoryData human_mall_class = new CategoryData(ClassId.mage, ClassId.wizard, ClassId.sorcerer, ClassId.necromancer, ClassId.warlock, ClassId.mage, ClassId.cleric, ClassId.bishop, ClassId.prophet, ClassId.soultaker, ClassId.arcana_lord, ClassId.cardinal, ClassId.hierophant);
    public static final /* enum */ CategoryData human_call_class = new CategoryData(ClassId.mage, ClassId.cleric, ClassId.bishop, ClassId.prophet, ClassId.cardinal, ClassId.hierophant);
    public static final /* enum */ CategoryData elf_fall_class = new CategoryData(ClassId.elven_fighter, ClassId.elven_knight, ClassId.elven_scout, ClassId.temple_knight, ClassId.swordsinger, ClassId.plains_walker, ClassId.silver_ranger, ClassId.evas_templar, ClassId.sword_muse, ClassId.wind_rider, ClassId.moonlight_sentinel);
    public static final /* enum */ CategoryData elf_mall_class = new CategoryData(ClassId.elven_mage, ClassId.elven_wizard, ClassId.spellsinger, ClassId.elemental_summoner, ClassId.elven_mage, ClassId.oracle, ClassId.elder, ClassId.mystic_muse, ClassId.elemental_master, ClassId.evas_saint);
    public static final /* enum */ CategoryData elf_wall_class = new CategoryData(ClassId.elven_mage, ClassId.elven_wizard, ClassId.spellsinger, ClassId.elemental_summoner, ClassId.mystic_muse, ClassId.elemental_master);
    public static final /* enum */ CategoryData elf_call_class = new CategoryData(ClassId.elven_mage, ClassId.oracle, ClassId.elder, ClassId.evas_saint);
    public static final /* enum */ CategoryData delf_fall_class = new CategoryData(ClassId.dark_fighter, ClassId.palus_knight, ClassId.assassin, ClassId.shillien_knight, ClassId.bladedancer, ClassId.abyss_walker, ClassId.phantom_ranger, ClassId.shillien_templar, ClassId.spectral_dancer, ClassId.ghost_hunter, ClassId.ghost_sentinel);
    public static final /* enum */ CategoryData delf_mall_class = new CategoryData(ClassId.dark_mage, ClassId.dark_wizard, ClassId.shillien_oracle, ClassId.spellhowler, ClassId.phantom_summoner, ClassId.shillien_elder, ClassId.storm_screamer, ClassId.spectral_master, ClassId.shillien_saint);
    public static final /* enum */ CategoryData delf_wall_class = new CategoryData(ClassId.dark_mage, ClassId.dark_wizard, ClassId.spellhowler, ClassId.phantom_summoner, ClassId.storm_screamer, ClassId.spectral_master);
    public static final /* enum */ CategoryData delf_call_class = new CategoryData(ClassId.dark_mage, ClassId.shillien_oracle, ClassId.shillien_elder, ClassId.shillien_saint);
    public static final /* enum */ CategoryData orc_fall_class = new CategoryData(ClassId.orc_fighter, ClassId.orc_raider, ClassId.orc_monk, ClassId.destroyer, ClassId.tyrant, ClassId.titan, ClassId.grand_khavatari);
    public static final /* enum */ CategoryData orc_mall_class = new CategoryData(ClassId.orc_mage, ClassId.orc_shaman, ClassId.overlord, ClassId.warcryer, ClassId.dominator, ClassId.doomcryer);
    public static final /* enum */ CategoryData dwarf_all_class = new CategoryData(ClassId.dwarven_fighter, ClassId.scavenger, ClassId.artisan, ClassId.bounty_hunter, ClassId.warsmith, ClassId.fortune_seeker, ClassId.maestro);
    public static final /* enum */ CategoryData dwarf_bounty_class = new CategoryData(ClassId.dwarven_fighter, ClassId.scavenger, ClassId.bounty_hunter, ClassId.fortune_seeker);
    public static final /* enum */ CategoryData dwarf_smith_class = new CategoryData(ClassId.dwarven_fighter, ClassId.artisan, ClassId.warsmith, ClassId.maestro);
    public static final /* enum */ CategoryData beginner_fighter = new CategoryData(ClassId.fighter, ClassId.warrior, ClassId.gladiator, ClassId.warlord, ClassId.knight, ClassId.paladin, ClassId.dark_avenger, ClassId.rogue, ClassId.treasure_hunter, ClassId.hawkeye, ClassId.elven_fighter, ClassId.elven_knight, ClassId.temple_knight, ClassId.swordsinger, ClassId.elven_scout, ClassId.plains_walker, ClassId.silver_ranger, ClassId.dark_fighter, ClassId.palus_knight, ClassId.shillien_knight, ClassId.bladedancer, ClassId.assassin, ClassId.abyss_walker, ClassId.phantom_ranger, ClassId.orc_fighter, ClassId.orc_raider, ClassId.destroyer, ClassId.orc_monk, ClassId.tyrant, ClassId.orc_mage, ClassId.orc_shaman, ClassId.overlord, ClassId.warcryer, ClassId.dwarven_fighter, ClassId.scavenger, ClassId.bounty_hunter, ClassId.artisan, ClassId.warsmith);
    public static final /* enum */ CategoryData beginner_mage = new CategoryData(ClassId.mage, ClassId.wizard, ClassId.sorcerer, ClassId.necromancer, ClassId.warlock, ClassId.cleric, ClassId.bishop, ClassId.prophet, ClassId.elven_mage, ClassId.elven_wizard, ClassId.spellsinger, ClassId.elemental_summoner, ClassId.oracle, ClassId.elder, ClassId.dark_mage, ClassId.dark_wizard, ClassId.spellhowler, ClassId.phantom_summoner, ClassId.shillien_oracle, ClassId.shillien_elder);
    public static final /* enum */ CategoryData archer_group = new CategoryData(ClassId.hawkeye, ClassId.silver_ranger, ClassId.phantom_ranger, ClassId.sagittarius, ClassId.moonlight_sentinel, ClassId.ghost_sentinel);
    public static final /* enum */ CategoryData shield_master = new CategoryData(ClassId.phoenix_knight, ClassId.hell_knight, ClassId.evas_templar, ClassId.shillien_templar);
    public static final /* enum */ CategoryData bard = new CategoryData(ClassId.sword_muse, ClassId.spectral_dancer);
    public static final /* enum */ CategoryData force_master = new CategoryData(ClassId.duelist, ClassId.grand_khavatari);
    public static final /* enum */ CategoryData weapon_master = new CategoryData(ClassId.dreadnought, ClassId.titan, ClassId.fortune_seeker, ClassId.maestro);
    public static final /* enum */ CategoryData bow_master = new CategoryData(ClassId.sagittarius, ClassId.moonlight_sentinel, ClassId.ghost_sentinel);
    public static final /* enum */ CategoryData dagger_master = new CategoryData(ClassId.adventurer, ClassId.wind_rider, ClassId.ghost_hunter);
    public static final /* enum */ CategoryData heal_master = new CategoryData(ClassId.cardinal, ClassId.evas_saint, ClassId.shillien_saint);
    public static final /* enum */ CategoryData wizard_master = new CategoryData(ClassId.archmage, ClassId.mystic_muse, ClassId.storm_screamer, ClassId.soultaker);
    public static final /* enum */ CategoryData buff_master = new CategoryData(ClassId.hierophant, ClassId.dominator, ClassId.doomcryer);
    public static final /* enum */ CategoryData summon_master = new CategoryData(ClassId.arcana_lord, ClassId.elemental_master, ClassId.spectral_master);
    public static final /* enum */ CategoryData warrior_cloack = new CategoryData(ClassId.phoenix_knight, ClassId.hell_knight, ClassId.evas_templar, ClassId.shillien_templar, ClassId.sword_muse, ClassId.spectral_dancer, ClassId.duelist, ClassId.grand_khavatari, ClassId.dreadnought, ClassId.titan, ClassId.fortune_seeker, ClassId.maestro);
    public static final /* enum */ CategoryData rogue_cloack = new CategoryData(ClassId.sagittarius, ClassId.moonlight_sentinel, ClassId.ghost_sentinel, ClassId.adventurer, ClassId.wind_rider, ClassId.ghost_hunter);
    public static final /* enum */ CategoryData mage_cloack = new CategoryData(ClassId.cardinal, ClassId.evas_saint, ClassId.shillien_saint, ClassId.archmage, ClassId.mystic_muse, ClassId.storm_screamer, ClassId.soultaker, ClassId.hierophant, ClassId.dominator, ClassId.doomcryer, ClassId.arcana_lord, ClassId.elemental_master, ClassId.spectral_master);
    public static final /* enum */ CategoryData shield_master2_3 = new CategoryData(ClassId.temple_knight, ClassId.shillien_knight, ClassId.paladin, ClassId.dark_avenger, ClassId.phoenix_knight, ClassId.hell_knight, ClassId.evas_templar, ClassId.shillien_templar);
    public static final /* enum */ CategoryData bard2_3 = new CategoryData(ClassId.swordsinger, ClassId.bladedancer, ClassId.sword_muse, ClassId.spectral_dancer);
    public static final /* enum */ CategoryData force_master2_3 = new CategoryData(ClassId.tyrant, ClassId.gladiator, ClassId.duelist, ClassId.grand_khavatari);
    public static final /* enum */ CategoryData weapon_master2_3 = new CategoryData(ClassId.warlord, ClassId.destroyer, ClassId.bounty_hunter, ClassId.warsmith, ClassId.dreadnought, ClassId.titan, ClassId.fortune_seeker, ClassId.maestro);
    public static final /* enum */ CategoryData bow_master2_3 = new CategoryData(ClassId.silver_ranger, ClassId.phantom_ranger, ClassId.hawkeye, ClassId.sagittarius, ClassId.moonlight_sentinel, ClassId.ghost_sentinel);
    public static final /* enum */ CategoryData dagger_master2_3 = new CategoryData(ClassId.abyss_walker, ClassId.plains_walker, ClassId.treasure_hunter, ClassId.adventurer, ClassId.wind_rider, ClassId.ghost_hunter);
    public static final /* enum */ CategoryData heal_master2_3 = new CategoryData(ClassId.bishop, ClassId.elder, ClassId.shillien_elder, ClassId.cardinal, ClassId.evas_saint, ClassId.shillien_saint);
    public static final /* enum */ CategoryData wizard_master2_3 = new CategoryData(ClassId.spellsinger, ClassId.spellhowler, ClassId.sorcerer, ClassId.necromancer, ClassId.archmage, ClassId.mystic_muse, ClassId.storm_screamer, ClassId.soultaker);
    public static final /* enum */ CategoryData buff_master2_3 = new CategoryData(ClassId.prophet, ClassId.overlord, ClassId.warcryer, ClassId.hierophant, ClassId.dominator, ClassId.doomcryer);
    public static final /* enum */ CategoryData summon_master2_3 = new CategoryData(ClassId.warlock, ClassId.elemental_summoner, ClassId.phantom_summoner, ClassId.arcana_lord, ClassId.elemental_master, ClassId.spectral_master);
    public static final /* enum */ CategoryData attribute_group_summoner = new CategoryData(ClassId.warlock, ClassId.elemental_summoner, ClassId.phantom_summoner, ClassId.arcana_lord, ClassId.elemental_master, ClassId.spectral_master);
    public static final /* enum */ CategoryData sub_group_warrior = new CategoryData(ClassId.warlord, ClassId.destroyer, ClassId.bounty_hunter, ClassId.warsmith, ClassId.tyrant, ClassId.gladiator, ClassId.dreadnought, ClassId.titan, ClassId.fortune_seeker, ClassId.maestro, ClassId.grand_khavatari, ClassId.duelist);
    public static final /* enum */ CategoryData sub_group_rogue = new CategoryData(ClassId.treasure_hunter, ClassId.plains_walker, ClassId.abyss_walker, ClassId.adventurer, ClassId.wind_rider, ClassId.ghost_hunter, ClassId.hawkeye, ClassId.silver_ranger, ClassId.phantom_ranger, ClassId.sagittarius, ClassId.moonlight_sentinel, ClassId.ghost_sentinel);
    public static final /* enum */ CategoryData sub_group_knight = new CategoryData(ClassId.paladin, ClassId.dark_avenger, ClassId.shillien_knight, ClassId.temple_knight, ClassId.phoenix_knight, ClassId.hell_knight, ClassId.shillien_templar, ClassId.evas_templar);
    public static final /* enum */ CategoryData sub_group_summoner = new CategoryData(ClassId.warlock, ClassId.elemental_summoner, ClassId.phantom_summoner, ClassId.arcana_lord, ClassId.elemental_master, ClassId.spectral_master);
    public static final /* enum */ CategoryData sub_group_wizard = new CategoryData(ClassId.sorcerer, ClassId.spellsinger, ClassId.spellhowler, ClassId.necromancer, ClassId.archmage, ClassId.mystic_muse, ClassId.storm_screamer, ClassId.soultaker);
    public static final /* enum */ CategoryData sub_group_healer = new CategoryData(ClassId.bishop, ClassId.elder, ClassId.shillien_elder, ClassId.cardinal, ClassId.evas_saint, ClassId.shillien_saint);
    public static final /* enum */ CategoryData sub_group_enchanter = new CategoryData(ClassId.prophet, ClassId.overlord, ClassId.warcryer, ClassId.swordsinger, ClassId.bladedancer, ClassId.hierophant, ClassId.dominator, ClassId.doomcryer, ClassId.sword_muse, ClassId.spectral_dancer);
    public static final /* enum */ CategoryData sub_group_hec = new CategoryData(ClassId.bishop, ClassId.elder, ClassId.cardinal, ClassId.evas_saint, ClassId.prophet, ClassId.hierophant);
    public static final /* enum */ CategoryData sub_group_hew = new CategoryData(ClassId.sorcerer, ClassId.archmage, ClassId.spellsinger, ClassId.mystic_muse, ClassId.necromancer, ClassId.soultaker, ClassId.warlock, ClassId.arcana_lord, ClassId.elemental_summoner, ClassId.elemental_master);
    public static final /* enum */ CategoryData sub_group_hef = new CategoryData(ClassId.gladiator, ClassId.duelist, ClassId.warlord, ClassId.dreadnought, ClassId.paladin, ClassId.phoenix_knight, ClassId.dark_avenger, ClassId.hell_knight, ClassId.treasure_hunter, ClassId.adventurer, ClassId.hawkeye, ClassId.sagittarius, ClassId.temple_knight, ClassId.evas_templar, ClassId.swordsinger, ClassId.sword_muse, ClassId.plains_walker, ClassId.wind_rider, ClassId.silver_ranger, ClassId.moonlight_sentinel);
    public static final /* enum */ CategoryData sub_group_orc = new CategoryData(ClassId.destroyer, ClassId.titan, ClassId.tyrant, ClassId.grand_khavatari, ClassId.overlord, ClassId.dominator, ClassId.warcryer, ClassId.doomcryer);
    public static final /* enum */ CategoryData sub_group_ware = new CategoryData(ClassId.bounty_hunter, ClassId.fortune_seeker);
    public static final /* enum */ CategoryData sub_group_black = new CategoryData(ClassId.warsmith, ClassId.maestro);
    public static final /* enum */ CategoryData sub_group_de = new CategoryData(ClassId.abyss_walker, ClassId.ghost_hunter, ClassId.phantom_ranger, ClassId.ghost_sentinel, ClassId.shillien_knight, ClassId.shillien_templar, ClassId.phantom_summoner, ClassId.spectral_master, ClassId.spellhowler, ClassId.storm_screamer, ClassId.shillien_elder, ClassId.shillien_saint, ClassId.bladedancer, ClassId.spectral_dancer);
    public static final /* enum */ CategoryData light_tanker_group = new CategoryData(ClassId.paladin, ClassId.temple_knight, ClassId.phoenix_knight, ClassId.evas_templar);
    public static final /* enum */ CategoryData dark_tanker_group = new CategoryData(ClassId.dark_avenger, ClassId.shillien_knight, ClassId.hell_knight, ClassId.shillien_templar);
    public static final /* enum */ CategoryData melee_attacker = new CategoryData(ClassId.fighter, ClassId.warrior, ClassId.gladiator, ClassId.warlord, ClassId.knight, ClassId.paladin, ClassId.dark_avenger, ClassId.rogue, ClassId.treasure_hunter, ClassId.elven_fighter, ClassId.elven_knight, ClassId.temple_knight, ClassId.swordsinger, ClassId.elven_scout, ClassId.plains_walker, ClassId.dark_fighter, ClassId.palus_knight, ClassId.shillien_knight, ClassId.bladedancer, ClassId.assassin, ClassId.abyss_walker, ClassId.orc_fighter, ClassId.orc_raider, ClassId.destroyer, ClassId.orc_monk, ClassId.tyrant, ClassId.dwarven_fighter, ClassId.scavenger, ClassId.bounty_hunter, ClassId.artisan, ClassId.warsmith, ClassId.duelist, ClassId.dreadnought, ClassId.phoenix_knight, ClassId.hell_knight, ClassId.adventurer, ClassId.evas_templar, ClassId.sword_muse, ClassId.wind_rider, ClassId.shillien_templar, ClassId.spectral_dancer, ClassId.ghost_hunter, ClassId.titan, ClassId.grand_khavatari, ClassId.fortune_seeker, ClassId.maestro);
    private final TIntHashSet b = new TIntHashSet();
    private static final /* synthetic */ CategoryData[] a;

    public static CategoryData[] values() {
        return (CategoryData[])a.clone();
    }

    public static CategoryData valueOf(String string) {
        return Enum.valueOf(CategoryData.class, string);
    }

    private CategoryData(ClassId ... classIdArray) {
        for (ClassId classId : classIdArray) {
            this.b.add(classId.getId());
        }
    }

    public boolean isBelong(int n) {
        return this.b.contains(n);
    }

    public boolean isBelong(ClassId classId) {
        return this.isBelong(classId.getId());
    }

    public boolean isPlayerBelong(Player player) {
        return this.isBelong(player.getActiveClassId());
    }

    public boolean isPlayerBaseClassBelong(Player player) {
        return this.isBelong(player.getBaseSubClass().getClassId());
    }

    private static /* synthetic */ CategoryData[] a() {
        return new CategoryData[]{fighter_group, mage_group, wizard_group, cleric_group, attacker_group, tanker_group, first_class_group, second_class_group, third_class_group, fourth_class_group, bounty_hunter_group, warsmith_group, knight_group, white_magic_group, heal_group, assist_magic_group, warrior_group, human_2nd_group, elf_2nd_group, delf_2nd_group, orc_2nd_group, dwarf_2nd_group, subjob_group_dagger, subjob_group_bow, subjob_group_knight, subjob_group_summoner, subjob_group_half_healer, subjob_group_dance, subjob_group_wizard, human_fall_class, human_wall_class, human_mall_class, human_call_class, elf_fall_class, elf_mall_class, elf_wall_class, elf_call_class, delf_fall_class, delf_mall_class, delf_wall_class, delf_call_class, orc_fall_class, orc_mall_class, dwarf_all_class, dwarf_bounty_class, dwarf_smith_class, beginner_fighter, beginner_mage, archer_group, shield_master, bard, force_master, weapon_master, bow_master, dagger_master, heal_master, wizard_master, buff_master, summon_master, warrior_cloack, rogue_cloack, mage_cloack, shield_master2_3, bard2_3, force_master2_3, weapon_master2_3, bow_master2_3, dagger_master2_3, heal_master2_3, wizard_master2_3, buff_master2_3, summon_master2_3, attribute_group_summoner, sub_group_warrior, sub_group_rogue, sub_group_knight, sub_group_summoner, sub_group_wizard, sub_group_healer, sub_group_enchanter, sub_group_hec, sub_group_hew, sub_group_hef, sub_group_orc, sub_group_ware, sub_group_black, sub_group_de, light_tanker_group, dark_tanker_group, melee_attacker};
    }

    static {
        a = CategoryData.a();
    }
}
