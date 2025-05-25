/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.data.xml;

import l2.gameserver.Config;
import l2.gameserver.data.StringHolder;
import l2.gameserver.data.htm.HtmCache;
import l2.gameserver.data.xml.holder.BuyListHolder;
import l2.gameserver.data.xml.holder.MultiSellHolder;
import l2.gameserver.data.xml.holder.ProductHolder;
import l2.gameserver.data.xml.parser.ArmorSetsParser;
import l2.gameserver.data.xml.parser.CapsuleItemParser;
import l2.gameserver.data.xml.parser.CharacterTemplateParser;
import l2.gameserver.data.xml.parser.ClassLevelGainParser;
import l2.gameserver.data.xml.parser.CombinationDataParser;
import l2.gameserver.data.xml.parser.CrystalGradeDataParser;
import l2.gameserver.data.xml.parser.CubicParser;
import l2.gameserver.data.xml.parser.DomainParser;
import l2.gameserver.data.xml.parser.DoorParser;
import l2.gameserver.data.xml.parser.EnchantItemParser;
import l2.gameserver.data.xml.parser.EnchantSkillParser;
import l2.gameserver.data.xml.parser.EnsoulFeeParser;
import l2.gameserver.data.xml.parser.EnsoulOptionParser;
import l2.gameserver.data.xml.parser.EventParser;
import l2.gameserver.data.xml.parser.FishDataParser;
import l2.gameserver.data.xml.parser.HennaParser;
import l2.gameserver.data.xml.parser.HitCondBonusParser;
import l2.gameserver.data.xml.parser.InstantZoneParser;
import l2.gameserver.data.xml.parser.ItemParser;
import l2.gameserver.data.xml.parser.KarmaIncreaseDataParser;
import l2.gameserver.data.xml.parser.MacroDataParser;
import l2.gameserver.data.xml.parser.NpcParser;
import l2.gameserver.data.xml.parser.OneDayRewardParser;
import l2.gameserver.data.xml.parser.OptionDataParser;
import l2.gameserver.data.xml.parser.RecipeParser;
import l2.gameserver.data.xml.parser.ResidenceParser;
import l2.gameserver.data.xml.parser.RestartPointParser;
import l2.gameserver.data.xml.parser.SellBuffParser;
import l2.gameserver.data.xml.parser.SkillAcquireParser;
import l2.gameserver.data.xml.parser.SoulCrystalParser;
import l2.gameserver.data.xml.parser.SpawnParser;
import l2.gameserver.data.xml.parser.StaticObjectParser;
import l2.gameserver.data.xml.parser.VariationChanceParser;
import l2.gameserver.data.xml.parser.VariationGroupParser;
import l2.gameserver.data.xml.parser.ZoneParser;
import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.tables.SkillTable;

public abstract class Parsers {
    public static void parseAll() {
        HtmCache.getInstance().reload();
        StringHolder.getInstance().load();
        EnchantSkillParser.getInstance().load();
        SkillTable.getInstance().load();
        OptionDataParser.getInstance().load();
        CrystalGradeDataParser.getInstance().load();
        ItemParser.getInstance().load();
        VariationGroupParser.getInstance().load();
        VariationChanceParser.getInstance().load();
        CharacterTemplateParser.getInstance().load();
        EnsoulFeeParser.getInstance().load();
        EnsoulOptionParser.getInstance().load();
        NpcParser.getInstance().load();
        DomainParser.getInstance().load();
        RestartPointParser.getInstance().load();
        StaticObjectParser.getInstance().load();
        DoorParser.getInstance().load();
        ZoneParser.getInstance().load();
        SpawnParser.getInstance().load();
        InstantZoneParser.getInstance().load();
        ReflectionManager.getInstance();
        SkillAcquireParser.getInstance().load();
        HitCondBonusParser.getInstance().load();
        ClassLevelGainParser.getInstance().load();
        KarmaIncreaseDataParser.getInstance().load();
        MacroDataParser.getInstance().load();
        ResidenceParser.getInstance().load();
        EventParser.getInstance().load();
        CubicParser.getInstance().load();
        RecipeParser.getInstance().load();
        BuyListHolder.getInstance();
        MultiSellHolder.getInstance();
        if (Config.ENABLE_PRIME_SHOP) {
            ProductHolder.getInstance();
        }
        HennaParser.getInstance().load();
        EnchantItemParser.getInstance().load();
        SoulCrystalParser.getInstance().load();
        ArmorSetsParser.getInstance().load();
        FishDataParser.getInstance().load();
        CapsuleItemParser.getInstance().load();
        OneDayRewardParser.getInstance().load();
        CombinationDataParser.getInstance().load();
        if (Config.SELLBUFF_ENABLED) {
            SellBuffParser.getInstance().load();
        }
    }
}
