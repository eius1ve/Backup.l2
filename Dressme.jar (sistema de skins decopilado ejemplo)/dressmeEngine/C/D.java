/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.data.htm.HtmCache
 *  l2.gameserver.data.xml.holder.ArmorSetsHolder
 *  l2.gameserver.data.xml.holder.ItemHolder
 *  l2.gameserver.handler.voicecommands.IVoicedCommandHandler
 *  l2.gameserver.model.ArmorSet
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.items.ItemInstance
 *  l2.gameserver.model.items.PcInventory
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.NpcHtmlMessage
 *  l2.gameserver.network.l2.s2c.UserInfoType
 *  l2.gameserver.templates.item.ItemTemplate
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package dressmeEngine.C;

import config.GabConfig;
import dressmeEngine.A0;
import dressmeEngine.B0;
import dressmeEngine.data.DressMeAgathionData;
import dressmeEngine.data.DressMeArmorData;
import dressmeEngine.data.DressMeCloakData;
import dressmeEngine.data.DressMeEnchantData;
import dressmeEngine.data.DressMeHatData;
import dressmeEngine.data.DressMeShieldData;
import dressmeEngine.data.DressMeWeaponData;
import dressmeEngine.util.B;
import dressmeEngine.util.HandleDressMeDb;
import dressmeEngine.xml.dataHolder.DressMeAgathionHolder;
import dressmeEngine.xml.dataHolder.DressMeArmorHolder;
import dressmeEngine.xml.dataHolder.DressMeCloakHolder;
import dressmeEngine.xml.dataHolder.DressMeEnchantHolder;
import dressmeEngine.xml.dataHolder.DressMeHatHolder;
import dressmeEngine.xml.dataHolder.DressMeShieldHolder;
import dressmeEngine.xml.dataHolder.DressMeWeaponHolder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import l2.gameserver.data.htm.HtmCache;
import l2.gameserver.data.xml.holder.ArmorSetsHolder;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.handler.voicecommands.IVoicedCommandHandler;
import l2.gameserver.model.ArmorSet;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.items.PcInventory;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.network.l2.s2c.UserInfoType;
import l2.gameserver.templates.item.ItemTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Visuals;

public class D
implements IVoicedCommandHandler {
    private static final Logger _log = LoggerFactory.getLogger(D.class);
    String index_path = "wardrobe/dressme/index.htm";
    String info_path = "wardrobe/dressme/info.htm";
    String undressme_path = "wardrobe/dressme/undressme.htm";
    String index_item_path = "wardrobe/dressme/index-item.htm";
    String template_item_path = "wardrobe/dressme/template-item.htm";
    String dress_cloak_path = "wardrobe/dressme/dress-cloak.htm";
    String dress_shield_path = "wardrobe/dressme/dress-shield.htm";
    String dress_armor_path = "wardrobe/dressme/dress-armor.htm";
    String dress_weapon_path = "wardrobe/dressme/dress-weapon.htm";
    String dress_hat_path = "wardrobe/dressme/dress-hat.htm";
    String dress_enchant_path = "wardrobe/dressme/dress-enchant.htm";
    String dress_aga_path = "wardrobe/dressme/dress-agathion.htm";
    private final String[] A = new String[]{"dressme", "undressme", "dressinfo", "showdress", "hidedress", "dressme-armor", "dress-armor", "dress-armorpage", "undressme-armor", "dressme-cloak", "dress-cloak", "dress-cloakpage", "undressme-cloak", "dressme-shield", "dress-shield", "dress-shieldpage", "undressme-shield", "dressme-weapon", "dress-weapon", "dress-weaponpage", "undressme-weapon", "dressme-hat", "dress-hat", "dress-hatpage", "undressme-hat", "dress-tryarmor", "dress-trycloak", "dress-tryshield", "dress-tryweapon", "dress-tryhat", "dressme-enchant", "dress-enchantpage", "dress-enchant", "dress-tryenchant", "undressme-enchant", "dressme-agathion", "dress-agathionpage", "dress-agathion", "dress-tryagathion", "undressme-agathion", "dressme-suitarmor"};

    public boolean useVoicedCommand(String str, Player Player2, String str2) {
        if (str.equals("dressme")) {
            String replace4 = HtmCache.getInstance().getNotNull(this.index_path, Player2).replace("<?show_hide?>", Player2.getVarB("showVisualChange") ? "Show visual equip on other player!" : "Hide visual equip on other player!").replace("<?show_hide_b?>", !Player2.getVarB("showVisualChange") ? "showdress" : "hidedress");
            Player2.unsetVar("suitDress");
            this.A(Player2, replace4);
            return true;
        }
        if (str.equals("dressme-suitarmor")) {
            String[] split;
            ItemInstance paperdollItem = Player2.getInventory().getPaperdollItem(6);
            if (paperdollItem == null || !paperdollItem.isArmor()) {
                Player2.sendMessage("Error: Armor chest must be equiped!");
                return false;
            }
            String htm = HtmCache.getInstance().getNotNull(this.index_item_path, Player2);
            String htm2 = HtmCache.getInstance().getNotNull(this.template_item_path, Player2);
            String str3 = "";
            if (str2 == null) {
                str2 = "1";
            }
            int parseInt = (split = str2.split(" "))[0].length() > 0 ? Integer.parseInt(split[0]) : 1;
            int i = 0;
            List<DressMeArmorData> orderL = HandleDressMeDb.orderL(Player2, B0.B("SUIT", new HashMap<Integer, DressMeArmorData>(), paperdollItem), "character_dressme_armor_list", "dressId");
            if (orderL == null) {
                _log.error("Dress me system: Armor Map is null.");
                return false;
            }
            int i2 = (parseInt - 1) * 5;
            while (i2 < orderL.size()) {
                DressMeArmorData dressMeArmorData = orderL.get(i2);
                if (dressMeArmorData != null) {
                    String B2 = dressMeArmorData.B();
                    if (B2.length() > 29) {
                        B2 = String.valueOf(B2.substring(0, 29)) + "...";
                    }
                    String replace5 = htm2.replace("{bypass}", "bypass -h user_dress-armorpage " + dressMeArmorData.C()).replace("{name}", B2);
                    str3 = String.valueOf(str3) + (HandleDressMeDb.dressMeArmorInside(Player2, dressMeArmorData) || Player2.getPremiumPoints() > 0L && GabConfig.PREMIUM_DRESS ? replace5.replace("{price}", "Owned") : replace5.replace("{price}", B.A(Player2, dressMeArmorData.D(), dressMeArmorData.A()))).replace("{icon}", B.A(dressMeArmorData.H()));
                }
                if (++i >= 5) break;
                ++i2;
            }
            double ceil = Math.ceil((double)orderL.size() / 5.0);
            int i3 = 1;
            String str4 = "";
            int i4 = 1;
            while ((double)i4 <= ceil) {
                String string = str4 = i4 == parseInt ? String.valueOf(str4) + "<td width=25 align=center valign=top><button value=\"[" + i4 + "]\" action=\"bypass -h user_dressme-suitarmor " + i4 + "\" width=32 height=25 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>" : String.valueOf(str4) + "<td width=25 align=center valign=top><button value=\"" + i4 + "\" action=\"bypass -h user_dressme-suitarmor " + i4 + "\" width=32 height=25 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>";
                if (i3 == 7) {
                    str4 = String.valueOf(str4) + "</tr><tr>";
                    i3 = 0;
                }
                ++i3;
                ++i4;
            }
            if (str4.equals("")) {
                str4 = "<td width=30 align=center valign=top>...</td>";
            }
            String replace6 = htm.replace("{list}", str3).replace("{navigation}", str4);
            NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(0);
            npcHtmlMessage.setHtml(replace6);
            Player2.sendPacket((IStaticPacket)npcHtmlMessage);
            return true;
        }
        if (str.equals("dressme-armor")) {
            String[] split2;
            ItemInstance paperdollItem2 = Player2.getInventory().getPaperdollItem(6);
            if (paperdollItem2 == null || !paperdollItem2.isArmor()) {
                Player2.sendMessage("Error: Armor chest must be equiped!");
                return false;
            }
            String htm3 = HtmCache.getInstance().getNotNull(this.index_item_path, Player2);
            String htm4 = HtmCache.getInstance().getNotNull(this.template_item_path, Player2);
            String str5 = "";
            if (str2 == null) {
                str2 = "1";
            }
            int parseInt2 = (split2 = str2.split(" "))[0].length() > 0 ? Integer.parseInt(split2[0]) : 1;
            int i5 = 0;
            List<DressMeArmorData> orderL2 = HandleDressMeDb.orderL(Player2, B0.B(paperdollItem2.getItemType().toString(), new HashMap<Integer, DressMeArmorData>(), paperdollItem2), "character_dressme_armor_list", "dressId");
            if (orderL2 == null) {
                _log.error("Dress me system: Armor Map is null.");
                return false;
            }
            int i6 = (parseInt2 - 1) * 5;
            while (i6 < orderL2.size()) {
                DressMeArmorData dressMeArmorData2 = orderL2.get(i6);
                if (dressMeArmorData2 != null) {
                    String B3 = dressMeArmorData2.B();
                    if (B3.length() > 29) {
                        B3 = String.valueOf(B3.substring(0, 29)) + "...";
                    }
                    String replace7 = htm4.replace("{bypass}", "bypass -h user_dress-armorpage " + dressMeArmorData2.C()).replace("{name}", B3);
                    str5 = String.valueOf(str5) + (HandleDressMeDb.dressMeArmorInside(Player2, dressMeArmorData2) || Player2.getPremiumPoints() > 0L && GabConfig.PREMIUM_DRESS ? replace7.replace("{price}", "Owned") : replace7.replace("{price}", B.A(Player2, dressMeArmorData2.D(), dressMeArmorData2.A()))).replace("{icon}", B.A(dressMeArmorData2.H()));
                }
                if (++i5 >= 5) break;
                ++i6;
            }
            double ceil2 = Math.ceil((double)orderL2.size() / 5.0);
            int i7 = 1;
            String str6 = "";
            int i8 = 1;
            while ((double)i8 <= ceil2) {
                String string = str6 = i8 == parseInt2 ? String.valueOf(str6) + "<td width=25 align=center valign=top><button value=\"[" + i8 + "]\" action=\"bypass -h user_dressme-armor " + i8 + "\" width=32 height=25 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>" : String.valueOf(str6) + "<td width=25 align=center valign=top><button value=\"" + i8 + "\" action=\"bypass -h user_dressme-armor " + i8 + "\" width=32 height=25 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>";
                if (i7 == 7) {
                    str6 = String.valueOf(str6) + "</tr><tr>";
                    i7 = 0;
                }
                ++i7;
                ++i8;
            }
            if (str6.equals("")) {
                str6 = "<td width=30 align=center valign=top>...</td>";
            }
            String replace8 = htm3.replace("{list}", str5).replace("{navigation}", str6);
            NpcHtmlMessage npcHtmlMessage2 = new NpcHtmlMessage(0);
            npcHtmlMessage2.setHtml(replace8);
            Player2.sendPacket((IStaticPacket)npcHtmlMessage2);
            return true;
        }
        if (str.equals("dressme-cloak")) {
            String[] split3;
            String htm5 = HtmCache.getInstance().getNotNull(this.index_item_path, Player2);
            String htm6 = HtmCache.getInstance().getNotNull(this.template_item_path, Player2);
            String str7 = "";
            if (str2 == null) {
                str2 = "1";
            }
            int parseInt3 = (split3 = str2.split(" "))[0].length() > 0 ? Integer.parseInt(split3[0]) : 1;
            int i9 = 0;
            List<DressMeCloakData> orderL3 = HandleDressMeDb.orderL(Player2, A0.d, "character_dressme_cloak_list", "cloakDressId");
            int i10 = (parseInt3 - 1) * 5;
            while (i10 < orderL3.size()) {
                DressMeCloakData dressMeCloakData = orderL3.get(i10);
                if (dressMeCloakData != null) {
                    String B4 = dressMeCloakData.B();
                    if (B4.length() > 29) {
                        B4 = String.valueOf(B4.substring(0, 29)) + "...";
                    }
                    String replace9 = htm6.replace("{bypass}", "bypass -h user_dress-cloakpage " + dressMeCloakData.C()).replace("{name}", B4);
                    str7 = String.valueOf(str7) + (HandleDressMeDb.dressMeCloakInside(Player2, dressMeCloakData) || Player2.getPremiumPoints() > 0L && GabConfig.PREMIUM_DRESS ? replace9.replace("{price}", "Owned") : replace9.replace("{price}", B.A(Player2, dressMeCloakData.D(), dressMeCloakData.A()))).replace("{icon}", B.A(dressMeCloakData.P()));
                }
                if (++i9 >= 5) break;
                ++i10;
            }
            double ceil3 = Math.ceil((double)orderL3.size() / 5.0);
            int i11 = 1;
            String str8 = "";
            int i12 = 1;
            while ((double)i12 <= ceil3) {
                String string = str8 = i12 == parseInt3 ? String.valueOf(str8) + "<td width=25 align=center valign=top><button value=\"[" + i12 + "]\" action=\"bypass -h user_dressme-cloak " + i12 + "\" width=32 height=25 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>" : String.valueOf(str8) + "<td width=25 align=center valign=top><button value=\"" + i12 + "\" action=\"bypass -h user_dressme-cloak " + i12 + "\" width=32 height=25 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>";
                if (i11 == 7) {
                    str8 = String.valueOf(str8) + "</tr><tr>";
                    i11 = 0;
                }
                ++i11;
                ++i12;
            }
            if (str8.equals("")) {
                str8 = "<td width=30 align=center valign=top>...</td>";
            }
            this.A(Player2, htm5.replace("{list}", str7).replace("{navigation}", str8));
            return true;
        }
        if (str.equals("dressme-shield")) {
            String[] split4;
            String htm7 = HtmCache.getInstance().getNotNull(this.index_item_path, Player2);
            String htm8 = HtmCache.getInstance().getNotNull(this.template_item_path, Player2);
            String str9 = "";
            if (str2 == null) {
                str2 = "1";
            }
            int parseInt4 = (split4 = str2.split(" "))[0].length() > 0 ? Integer.parseInt(split4[0]) : 1;
            int i13 = 0;
            ItemInstance paperdollItem3 = Player2.getInventory().getPaperdollItem(7);
            if (paperdollItem3 == null) {
                Player2.sendMessage("Please equip an Shield or Sigil!");
                return false;
            }
            List<DressMeShieldData> orderL4 = HandleDressMeDb.orderL(Player2, B0.C(paperdollItem3.getItemType().toString(), new LinkedHashMap<Integer, DressMeShieldData>(), paperdollItem3), "character_dressme_shield_list", "shieldDressId");
            int i14 = (parseInt4 - 1) * 5;
            while (i14 < orderL4.size()) {
                DressMeShieldData dressMeShieldData = orderL4.get(i14);
                if (dressMeShieldData != null) {
                    String B5 = dressMeShieldData.B();
                    if (B5.length() > 29) {
                        B5 = String.valueOf(B5.substring(0, 29)) + "...";
                    }
                    String replace10 = htm8.replace("{bypass}", "bypass -h user_dress-shieldpage " + dressMeShieldData.C()).replace("{name}", B5);
                    str9 = String.valueOf(str9) + (HandleDressMeDb.dressMeShieldInside(Player2, dressMeShieldData) || Player2.getPremiumPoints() > 0L && GabConfig.PREMIUM_DRESS ? replace10.replace("{price}", "Owned") : replace10.replace("{price}", B.A(Player2, dressMeShieldData.D(), dressMeShieldData.A()))).replace("{icon}", B.A(dressMeShieldData.W()));
                }
                if (++i13 >= 5) break;
                ++i14;
            }
            double ceil4 = Math.ceil((double)orderL4.size() / 5.0);
            int i15 = 1;
            String str10 = "";
            int i16 = 1;
            while ((double)i16 <= ceil4) {
                String string = str10 = i16 == parseInt4 ? String.valueOf(str10) + "<td width=25 align=center valign=top><button value=\"[" + i16 + "]\" action=\"bypass -h user_dressme-shield " + i16 + "\" width=32 height=25 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>" : String.valueOf(str10) + "<td width=25 align=center valign=top><button value=\"" + i16 + "\" action=\"bypass -h user_dressme-shield " + i16 + "\" width=32 height=25 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>";
                if (i15 == 7) {
                    str10 = String.valueOf(str10) + "</tr><tr>";
                    i15 = 0;
                }
                ++i15;
                ++i16;
            }
            if (str10.equals("")) {
                str10 = "<td width=30 align=center valign=top>...</td>";
            }
            this.A(Player2, htm7.replace("{list}", str9).replace("{navigation}", str10));
            return true;
        }
        if (str.equals("dressme-weapon")) {
            String[] split5;
            ItemInstance paperdollItem4 = Player2.getInventory().getPaperdollItem(5);
            if (paperdollItem4 == null) {
                Player2.sendMessage("Error: Weapon must be equiped!");
                return false;
            }
            String htm9 = HtmCache.getInstance().getNotNull(this.index_item_path, Player2);
            String htm10 = HtmCache.getInstance().getNotNull(this.template_item_path, Player2);
            String str11 = "";
            if (str2 == null) {
                str2 = "1";
            }
            int parseInt5 = (split5 = str2.split(" "))[0].length() > 0 ? Integer.parseInt(split5[0]) : 1;
            int i17 = 0;
            List<DressMeWeaponData> orderL5 = HandleDressMeDb.orderL(Player2, B0.A(paperdollItem4.getItemType().toString(), new LinkedHashMap<Integer, DressMeWeaponData>(), paperdollItem4), "character_dressme_weapon_list", "weaponDressId");
            if (orderL5 == null) {
                _log.error("Dress me system: Weapon Map is null.");
                return false;
            }
            int i18 = (parseInt5 - 1) * 5;
            while (i18 < orderL5.size()) {
                DressMeWeaponData dressMeWeaponData = orderL5.get(i18);
                if (dressMeWeaponData != null) {
                    String B6 = dressMeWeaponData.B();
                    if (B6.length() > 29) {
                        B6 = String.valueOf(B6.substring(0, 29)) + "...";
                    }
                    String replace11 = htm10.replace("{bypass}", "bypass -h user_dress-weaponpage " + dressMeWeaponData.C()).replace("{name}", B6);
                    str11 = String.valueOf(str11) + (HandleDressMeDb.dressMeWeaponInside(Player2, dressMeWeaponData) || Player2.getPremiumPoints() > 0L && GabConfig.PREMIUM_DRESS ? replace11.replace("{price}", "Owned") : replace11.replace("{price}", B.A(Player2, dressMeWeaponData.D(), dressMeWeaponData.A()))).replace("{icon}", B.A(dressMeWeaponData.C()));
                }
                if (++i17 >= 5) break;
                ++i18;
            }
            double ceil5 = Math.ceil((double)orderL5.size() / 5.0);
            int i19 = 1;
            String str12 = "";
            int i20 = 1;
            while ((double)i20 <= ceil5) {
                String string = str12 = i20 == parseInt5 ? String.valueOf(str12) + "<td width=25 align=center valign=top><button value=\"[" + i20 + "]\" action=\"bypass -h user_dressme-weapon " + i20 + "\" width=32 height=25 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>" : String.valueOf(str12) + "<td width=25 align=center valign=top><button value=\"" + i20 + "\" action=\"bypass -h user_dressme-weapon " + i20 + "\" width=32 height=25 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>";
                if (i19 == 7) {
                    str12 = String.valueOf(str12) + "</tr><tr>";
                    i19 = 0;
                }
                ++i19;
                ++i20;
            }
            if (str12.equals("")) {
                str12 = "<td width=30 align=center valign=top>...</td>";
            }
            this.A(Player2, htm9.replace("{list}", str11).replace("{navigation}", str12));
            return true;
        }
        if (str.equals("dressme-hat")) {
            String[] split6;
            ItemInstance paperdollItem5 = Player2.getInventory().getPaperdollItem(2);
            if (paperdollItem5 == null) {
                paperdollItem5 = Player2.getInventory().getPaperdollItem(3);
            }
            if (paperdollItem5 == null) {
                Player2.sendMessage("Error: Hat must be equiped!");
                return false;
            }
            String htm11 = HtmCache.getInstance().getNotNull(this.index_item_path, Player2);
            String htm12 = HtmCache.getInstance().getNotNull(this.template_item_path, Player2);
            String str13 = "";
            if (str2 == null) {
                str2 = "1";
            }
            int parseInt6 = (split6 = str2.split(" "))[0].length() > 0 ? Integer.parseInt(split6[0]) : 1;
            int i21 = 0;
            System.out.println("Filter itens based on: " + paperdollItem5.getName() + "(" + paperdollItem5.getBodyPart() + ")");
            List<DressMeHatData> orderL6 = HandleDressMeDb.orderL(Player2, B0.A(new LinkedHashMap<Integer, DressMeHatData>(), paperdollItem5), "character_dressme_hat_list", "hatDressId");
            if (orderL6 == null) {
                _log.error("Dress me system: Hat Map is null.");
                return false;
            }
            int i22 = (parseInt6 - 1) * 5;
            while (i22 < orderL6.size()) {
                DressMeHatData dressMeHatData = orderL6.get(i22);
                if (dressMeHatData != null) {
                    String B7 = dressMeHatData.B();
                    if (B7.length() > 29) {
                        B7 = String.valueOf(B7.substring(0, 29)) + "...";
                    }
                    String replace12 = htm12.replace("{bypass}", "bypass -h user_dress-hatpage " + dressMeHatData.C()).replace("{name}", B7);
                    str13 = String.valueOf(str13) + (HandleDressMeDb.dressMeHatInside(Player2, dressMeHatData) || Player2.getPremiumPoints() > 0L && GabConfig.PREMIUM_DRESS ? replace12.replace("{price}", "Owned") : replace12.replace("{price}", B.A(Player2, dressMeHatData.D(), dressMeHatData.A()))).replace("{icon}", B.A(dressMeHatData.F()));
                }
                if (++i21 >= 5) break;
                ++i22;
            }
            double ceil6 = Math.ceil((double)orderL6.size() / 5.0);
            int i23 = 1;
            String str14 = "";
            int i24 = 1;
            while ((double)i24 <= ceil6) {
                String string = str14 = i24 == parseInt6 ? String.valueOf(str14) + "<td width=25 align=center valign=top><button value=\"[" + i24 + "]\" action=\"bypass -h user_dressme-hat " + i24 + "\" width=32 height=25 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>" : String.valueOf(str14) + "<td width=25 align=center valign=top><button value=\"" + i24 + "\" action=\"bypass -h user_dressme-hat " + i24 + "\" width=32 height=25 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>";
                if (i23 == 7) {
                    str14 = String.valueOf(str14) + "</tr><tr>";
                    i23 = 0;
                }
                ++i23;
                ++i24;
            }
            if (str14.equals("")) {
                str14 = "<td width=30 align=center valign=top>...</td>";
            }
            this.A(Player2, htm11.replace("{list}", str13).replace("{navigation}", str14));
            return true;
        }
        if (str.equals("dress-armorpage")) {
            int parseInt7 = Integer.parseInt(str2.split(" ")[0]);
            DressMeArmorData armor = DressMeArmorHolder.getInstance().getArmor(parseInt7);
            if (armor != null) {
                String replace3;
                String replace2;
                String replace;
                String htm13 = HtmCache.getInstance().getNotNull(this.dress_armor_path, Player2);
                PcInventory inventory = Player2.getInventory();
                ItemInstance paperdollItem6 = inventory.getPaperdollItem(6);
                String replace13 = htm13.replace("{my_chest_icon}", paperdollItem6 == null ? "icon.NOIMAGE" : paperdollItem6.getTemplate().getIcon());
                ItemInstance paperdollItem7 = inventory.getPaperdollItem(11);
                String replace14 = replace13.replace("{my_legs_icon}", paperdollItem7 == null ? "icon.NOIMAGE" : paperdollItem7.getTemplate().getIcon());
                ItemInstance paperdollItem8 = inventory.getPaperdollItem(10);
                String replace15 = replace14.replace("{my_gloves_icon}", paperdollItem8 == null ? "icon.NOIMAGE" : paperdollItem8.getTemplate().getIcon());
                ItemInstance paperdollItem9 = inventory.getPaperdollItem(12);
                String replace16 = replace15.replace("{my_feet_icon}", paperdollItem9 == null ? "icon.NOIMAGE" : paperdollItem9.getTemplate().getIcon()).replace("{bypass}", "bypass -h user_dress-armor " + parseInt7).replace("{bypassTry}", "bypass -h user_dress-tryarmor " + parseInt7).replace("{name}", armor.B());
                String replace17 = HandleDressMeDb.dressMeArmorInside(Player2, armor) || Player2.getPremiumPoints() > 0L && GabConfig.PREMIUM_DRESS ? replace16.replace("{price}", "WardRobe") : replace16.replace("{price}", B.A(Player2, armor.D(), armor.A()));
                ItemTemplate template = ItemHolder.getInstance().getTemplate(armor.H());
                String replace18 = replace17.replace("{chest_icon}", template.getIcon()).replace("{chest_name}", template.getName()).replace("{chest_grade}", template.getItemGrade().toString());
                if (armor.L() != -1) {
                    ItemTemplate template2 = ItemHolder.getInstance().getTemplate(armor.L());
                    replace = replace18.replace("{legs_icon}", template2.getIcon()).replace("{legs_name}", template2.getName()).replace("{legs_grade}", template2.getItemGrade().toString());
                } else {
                    replace = replace18.replace("{legs_icon}", "icon.NOIMAGE").replace("{legs_name}", "<font color=FF0000>...</font>").replace("{legs_grade}", "NO");
                }
                if (armor.K() != -1) {
                    ItemTemplate template3 = ItemHolder.getInstance().getTemplate(armor.K());
                    replace2 = replace.replace("{gloves_icon}", template3.getIcon()).replace("{gloves_name}", template3.getName()).replace("{gloves_grade}", template3.getItemGrade().toString());
                } else {
                    replace2 = replace.replace("{gloves_icon}", "icon.NOIMAGE").replace("{gloves_name}", "<font color=FF0000>...</font>").replace("{gloves_grade}", "NO");
                }
                if (armor.J() != -1) {
                    ItemTemplate template4 = ItemHolder.getInstance().getTemplate(armor.J());
                    replace3 = replace2.replace("{feet_icon}", template4.getIcon()).replace("{feet_name}", template4.getName()).replace("{feet_grade}", template4.getItemGrade().toString());
                } else {
                    replace3 = replace2.replace("{feet_icon}", "icon.NOIMAGE").replace("{feet_name}", "<font color=FF0000>...</font>").replace("{feet_grade}", "NO");
                }
                this.A(Player2, replace3);
                return true;
            }
            return false;
        }
        if (str.equals("dress-cloakpage")) {
            DressMeCloakData cloak = DressMeCloakHolder.getInstance().getCloak(Integer.parseInt(str2.split(" ")[0]));
            if (cloak != null) {
                PcInventory inventoryCloak = Player2.getInventory();
                ItemInstance paperdollItemCloak = inventoryCloak.getPaperdollItem(23);
                if (paperdollItemCloak == null) {
                    Player2.sendMessage("Error: Cloak must be equiped.");
                    this.useVoicedCommand("dress-cloakpage", Player2, str2.split(" ")[0]);
                    return false;
                }
                String htm14 = HtmCache.getInstance().getNotNull(this.dress_cloak_path, Player2);
                ItemInstance paperdollItem10 = Player2.getInventory().getPaperdollItem(23);
                String replace19 = htm14.replace("{my_cloak_icon}", paperdollItem10 == null ? "icon.NOIMAGE" : paperdollItem10.getTemplate().getIcon()).replace("{bypass}", "bypass -h user_dress-cloak " + cloak.C()).replace("{bypassTry}", "bypass -h user_dress-trycloak " + cloak.C()).replace("{name}", cloak.B());
                String replace20 = HandleDressMeDb.dressMeCloakInside(Player2, cloak) || Player2.getPremiumPoints() > 0L && GabConfig.PREMIUM_DRESS ? replace19.replace("{price}", "WardRobe") : replace19.replace("{price}", B.A(Player2, cloak.D(), cloak.A()));
                ItemTemplate template5 = ItemHolder.getInstance().getTemplate(cloak.P());
                this.A(Player2, replace20.replace("{item_icon}", template5.getIcon()).replace("{item_name}", template5.getName()).replace("{item_grade}", template5.getItemGrade().toString()));
                return true;
            }
            return false;
        }
        if (str.equals("dress-shieldpage")) {
            DressMeShieldData shield = DressMeShieldHolder.getInstance().getShield(Integer.parseInt(str2.split(" ")[0]));
            if (shield != null) {
                String htm15 = HtmCache.getInstance().getNotNull(this.dress_shield_path, Player2);
                ItemInstance paperdollItem11 = Player2.getInventory().getPaperdollItem(7);
                String replace21 = htm15.replace("{my_shield_icon}", paperdollItem11 == null ? "icon.NOIMAGE" : paperdollItem11.getTemplate().getIcon()).replace("{bypass}", "bypass -h user_dress-shield " + shield.C()).replace("{bypassTry}", "bypass -h user_dress-tryshield " + shield.C()).replace("{name}", shield.B());
                String replace22 = HandleDressMeDb.dressMeShieldInside(Player2, shield) || Player2.getPremiumPoints() > 0L && GabConfig.PREMIUM_DRESS ? replace21.replace("{price}", "WardRobe") : replace21.replace("{price}", B.A(Player2, shield.D(), shield.A()));
                ItemTemplate template6 = ItemHolder.getInstance().getTemplate(shield.W());
                this.A(Player2, replace22.replace("{item_icon}", template6.getIcon()).replace("{item_name}", template6.getName()).replace("{item_grade}", template6.getItemGrade().toString()));
                return true;
            }
            return false;
        }
        if (str.equals("dress-weaponpage")) {
            DressMeWeaponData weapon = DressMeWeaponHolder.getInstance().getWeapon(Integer.parseInt(str2.split(" ")[0]));
            if (weapon != null) {
                String htm16 = HtmCache.getInstance().getNotNull(this.dress_weapon_path, Player2);
                ItemInstance paperdollItem12 = Player2.getInventory().getPaperdollItem(5);
                String replace23 = htm16.replace("{my_weapon_icon}", paperdollItem12 == null ? "icon.NOIMAGE" : paperdollItem12.getTemplate().getIcon()).replace("{bypass}", "bypass -h user_dress-weapon " + weapon.C()).replace("{bypassTry}", "bypass -h user_dress-tryweapon " + weapon.C()).replace("{name}", weapon.B());
                String replace24 = HandleDressMeDb.dressMeWeaponInside(Player2, weapon) || Player2.getPremiumPoints() > 0L && GabConfig.PREMIUM_DRESS ? replace23.replace("{price}", "WardRobe") : replace23.replace("{price}", B.A(Player2, weapon.D(), weapon.A()));
                ItemTemplate template7 = ItemHolder.getInstance().getTemplate(weapon.C());
                this.A(Player2, replace24.replace("{item_icon}", template7.getIcon()).replace("{item_name}", template7.getName()).replace("{item_grade}", template7.getItemGrade().toString()));
                return true;
            }
            return false;
        }
        if (str.equals("dress-hatpage")) {
            DressMeHatData hat = DressMeHatHolder.getInstance().getHat(Integer.parseInt(str2.split(" ")[0]));
            if (hat != null) {
                String htm17 = HtmCache.getInstance().getNotNull(this.dress_hat_path, Player2);
                PcInventory inventory2 = Player2.getInventory();
                ItemInstance ItemInstance2 = null;
                switch (hat.E()) {
                    case 1: 
                    case 3: {
                        ItemInstance2 = inventory2.getPaperdollItem(3);
                        break;
                    }
                    case 2: {
                        ItemInstance2 = inventory2.getPaperdollItem(2);
                    }
                }
                String replace25 = htm17.replace("{my_hat_icon}", ItemInstance2 == null ? "icon.NOIMAGE" : ItemInstance2.getTemplate().getIcon()).replace("{bypass}", "bypass -h user_dress-hat " + hat.C()).replace("{bypassTry}", "bypass -h user_dress-tryhat " + hat.C()).replace("{name}", hat.B());
                String replace26 = HandleDressMeDb.dressMeHatInside(Player2, hat) || Player2.getPremiumPoints() > 0L && GabConfig.PREMIUM_DRESS ? replace25.replace("{price}", "WardRobe") : replace25.replace("{price}", B.A(Player2, hat.D(), hat.A()));
                ItemTemplate template8 = ItemHolder.getInstance().getTemplate(hat.F());
                this.A(Player2, replace26.replace("{item_icon}", template8.getIcon()).replace("{item_name}", template8.getName()).replace("{item_grade}", template8.getItemGrade().toString()));
                return true;
            }
            return false;
        }
        if (str.equals("dressinfo")) {
            this.A(Player2, HtmCache.getInstance().getNotNull(this.info_path, Player2));
            return true;
        }
        if (str.equals("dress-armor")) {
            DressMeArmorData armor2 = DressMeArmorHolder.getInstance().getArmor(Integer.parseInt(str2.split(" ")[0]));
            PcInventory inventory3 = Player2.getInventory();
            ItemInstance paperdollItem13 = inventory3.getPaperdollItem(6);
            ItemInstance paperdollItem14 = inventory3.getPaperdollItem(11);
            ItemInstance paperdollItem15 = inventory3.getPaperdollItem(10);
            ItemInstance paperdollItem16 = inventory3.getPaperdollItem(12);
            if (paperdollItem13 == null) {
                Player2.sendMessage("Error: Chest must be equiped.");
                this.useVoicedCommand("dress-armorpage", Player2, str2);
                return false;
            }
            if (paperdollItem13.getBodyPart() == 32768L && paperdollItem13.getBodyPart() != ItemHolder.getInstance().getTemplate(armor2.H()).getBodyPart()) {
                Player2.sendMessage("Error: You can't change visual in full armor type not full armors.");
                this.useVoicedCommand("dress-armorpage", Player2, str2);
                return false;
            }
            if (ArmorSetsHolder.getInstance().getArmorSetByChestItemId(paperdollItem13.getItemId()) == null) {
                Player2.sendMessage("Error: You can't visualize current set.");
                this.useVoicedCommand("dress-armorpage", Player2, str2);
                return false;
            }
            ArmorSet set = ArmorSetsHolder.getInstance().getArmorSetByChestItemId(paperdollItem13.getItemId());
            if (set == null || !set.containAll(Player2)) {
                Player2.sendMessage("Error: You can't visualize, set is not complete.");
                this.useVoicedCommand("dress-armorpage", Player2, str2);
                return false;
            }
            if (!(GabConfig.ALLOW_ALL_SETS || paperdollItem13.getItemType().toString().equals(armor2.I()) || armor2.M())) {
                Player2.sendMessage("Error: You can't visualize current set 1.");
                this.useVoicedCommand("dress-armorpage", Player2, str2);
                return false;
            }
            if (HandleDressMeDb.dressMeArmorInside(Player2, armor2) || Player2.getPremiumPoints() > 0L && GabConfig.PREMIUM_DRESS) {
                B0.B(Player2, paperdollItem13, armor2.H());
                B0.B(Player2, paperdollItem14, -1);
                B0.B(Player2, paperdollItem15, -1);
                B0.B(Player2, paperdollItem16, -1);
                Player2.broadcastUserInfo(false, new UserInfoType[0]);
                Player2.sendMessage("Dress Was already in wardrobe! No price reduced!");
                B0.B(Player2, paperdollItem13, armor2.H());
                B0.B(Player2, paperdollItem14, -1);
                B0.B(Player2, paperdollItem15, -1);
                B0.B(Player2, paperdollItem16, -1);
                Player2.broadcastUserInfo(false, new UserInfoType[0]);
                Player2.sendMessage("Item bought with success! You can get it again from your wardrobe!");
            }
            this.useVoicedCommand("dressme", Player2, str2);
            return true;
        }
        if (str.equals("dress-cloak")) {
            DressMeCloakData cloak2 = DressMeCloakHolder.getInstance().getCloak(Integer.parseInt(str2.split(" ")[0]));
            ItemInstance paperdollItem17 = Player2.getInventory().getPaperdollItem(23);
            if (paperdollItem17 == null) {
                Player2.sendMessage("Error: Cloak must be equiped.");
                this.useVoicedCommand("dress-cloakpage", Player2, str2);
                return false;
            }
            if (HandleDressMeDb.dressMeCloakInside(Player2, cloak2) || Player2.getPremiumPoints() > 0L && GabConfig.PREMIUM_DRESS) {
                B0.B(Player2, paperdollItem17, cloak2.P());
                Player2.broadcastUserInfo(false, new UserInfoType[0]);
                Player2.sendMessage("Dress Was already in wardrobe! No price reduced!");
            } else {
                Player2.broadcastUserInfo(false, new UserInfoType[0]);
            }
            this.useVoicedCommand("dressme", Player2, str2);
            return true;
        }
        if (str.equals("dress-shield")) {
            DressMeShieldData shield2 = DressMeShieldHolder.getInstance().getShield(Integer.parseInt(str2.split(" ")[0]));
            ItemInstance paperdollItem18 = Player2.getInventory().getPaperdollItem(7);
            if (paperdollItem18 == null) {
                Player2.sendMessage("Error: Shield must be equiped.");
                this.useVoicedCommand("dress-shieldpage", Player2, str2);
                return false;
            }
            String description = paperdollItem18.getTemplate().getAdditionalName();
            if (description.equals("SHIELD") && !shield2.X()) {
                Player2.sendMessage("Error: Sigil must be equiped.");
                this.useVoicedCommand("dress-shieldpage", Player2, str2);
                return false;
            }
            if (description.equals("SIGIL") && shield2.X()) {
                Player2.sendMessage("Error: Shield must be equiped.");
                this.useVoicedCommand("dress-shieldpage", Player2, str2);
                return false;
            }
            if (HandleDressMeDb.dressMeShieldInside(Player2, shield2) || Player2.getPremiumPoints() > 0L && GabConfig.PREMIUM_DRESS) {
                B0.B(Player2, paperdollItem18, shield2.W());
                Player2.sendMessage("Dress Was already in wardrobe! No price reduced!");
                B0.B(Player2, paperdollItem18, shield2.W());
                Player2.sendMessage("Item bought with success! You can get it again from your wardrobe!");
            }
            Player2.broadcastUserInfo(false, new UserInfoType[0]);
            this.useVoicedCommand("dressme", Player2, str2);
            return true;
        }
        if (str.equals("dress-weapon")) {
            DressMeWeaponData weapon2 = DressMeWeaponHolder.getInstance().getWeapon(Integer.parseInt(str2.split(" ")[0]));
            ItemInstance paperdollItem19 = Player2.getInventory().getPaperdollItem(5);
            if (paperdollItem19 == null) {
                Player2.sendMessage("Error: Weapon must be equiped.");
                this.useVoicedCommand("dress-weaponpage", Player2, str2);
                return false;
            }
            if (!paperdollItem19.getItemType().toString().equals(weapon2.S())) {
                Player2.sendMessage("Error: Weapon must be equals type.");
                this.useVoicedCommand("dressme-weapon", Player2, null);
                return false;
            }
            if (paperdollItem19.getBodyPart() == 16384L && !weapon2.V() && !this.A(weapon2)) {
                Player2.sendMessage("Error: Weapon must be equals type.");
                this.useVoicedCommand("dressme-weapon", Player2, null);
                return false;
            }
            if (paperdollItem19.getBodyPart() != 16384L && weapon2.V() && !this.A(weapon2)) {
                Player2.sendMessage("Error: Weapon must be equals type.");
                this.useVoicedCommand("dressme-weapon", Player2, null);
                return false;
            }
            if (paperdollItem19.isHeroWeapon() && !weapon2.T()) {
                Player2.sendMessage("Error: Weapon must be equals type (magic).");
                this.useVoicedCommand("dressme-weapon", Player2, null);
                return false;
            }
            if (!paperdollItem19.isHeroWeapon() && weapon2.T()) {
                Player2.sendMessage("Error: Weapon must be equals type (Non Magic).");
                this.useVoicedCommand("dressme-weapon", Player2, null);
                return false;
            }
            if (HandleDressMeDb.dressMeWeaponInside(Player2, weapon2) || Player2.getPremiumPoints() > 0L && GabConfig.PREMIUM_DRESS) {
                B0.B(Player2, paperdollItem19, weapon2.C());
                Player2.sendMessage("Dress Was already in wardrobe! No price reduced!");
                B0.B(Player2, paperdollItem19, weapon2.C());
                Player2.sendMessage("Item bought with success! You can get it again from your wardrobe!");
            }
            Player2.broadcastUserInfo(false, new UserInfoType[0]);
            this.useVoicedCommand("dressme", Player2, str2);
            return true;
        }
        if (str.equals("dress-hat")) {
            DressMeHatData hat2 = DressMeHatHolder.getInstance().getHat(Integer.parseInt(str2.split(" ")[0]));
            PcInventory inventory4 = Player2.getInventory();
            ItemInstance ItemInstance2 = null;
            switch (hat2.E()) {
                case 1: 
                case 3: {
                    ItemInstance2 = inventory4.getPaperdollItem(2);
                    break;
                }
                case 2: {
                    ItemInstance2 = inventory4.getPaperdollItem(3);
                }
            }
            if (ItemInstance2 == null) {
                Player2.sendMessage("Error: Hat must be equiped.");
                this.useVoicedCommand("dress-hatpage", Player2, str2);
                return false;
            }
            if (ItemInstance2.getBodyPart() != ItemHolder.getInstance().getTemplate(hat2.F()).getBodyPart()) {
                Player2.sendMessage("Error: You can't change visual on different hat types!");
                this.useVoicedCommand("dress-hatpage", Player2, str2);
                return false;
            }
            if (HandleDressMeDb.dressMeHatInside(Player2, hat2) || Player2.getPremiumPoints() > 0L && GabConfig.PREMIUM_DRESS) {
                B0.B(Player2, ItemInstance2, hat2.F());
                Player2.sendMessage("Dress Was already in wardrobe! No price reduced!");
                B0.B(Player2, ItemInstance2, hat2.F());
                Player2.sendMessage("Item bought with success! You can get it again from your wardrobe!");
            }
            Player2.broadcastUserInfo(false, new UserInfoType[0]);
            this.useVoicedCommand("dressme", Player2, str2);
            return true;
        }
        if (str.equals("undressme")) {
            this.A(Player2, HtmCache.getInstance().getNotNull(this.undressme_path, Player2).replace("<?show_hide?>", Player2.getVarB("showVisualChange") ? "Show visual equip on other player!" : "Hide visual equip on other player!").replace("<?show_hide_b?>", !Player2.getVarB("showVisualChange") ? "showdress" : "hidedress"));
            return true;
        }
        if (str.equals("undressme-armor")) {
            PcInventory inventory5 = Player2.getInventory();
            ItemInstance paperdollItem20 = inventory5.getPaperdollItem(6);
            ItemInstance paperdollItem21 = inventory5.getPaperdollItem(11);
            ItemInstance paperdollItem22 = inventory5.getPaperdollItem(10);
            ItemInstance paperdollItem23 = inventory5.getPaperdollItem(12);
            if (paperdollItem20 != null) {
                B0.B(Player2, paperdollItem20, 0);
            }
            if (paperdollItem21 != null) {
                B0.B(Player2, paperdollItem21, 0);
            }
            if (paperdollItem22 != null) {
                B0.B(Player2, paperdollItem22, 0);
            }
            if (paperdollItem23 != null) {
                B0.B(Player2, paperdollItem23, 0);
            }
            Player2.broadcastUserInfo(false, new UserInfoType[0]);
            this.useVoicedCommand("dressme", Player2, str2);
            return true;
        }
        if (str.equals("undressme-cloak")) {
            ItemInstance paperdollItem24 = Player2.getInventory().getPaperdollItem(23);
            if (paperdollItem24 != null) {
                B0.B(Player2, paperdollItem24, 0);
            }
            Player2.broadcastUserInfo(false, new UserInfoType[0]);
            this.useVoicedCommand("dressme", Player2, str2);
            return true;
        }
        if (str.equals("undressme-shield")) {
            ItemInstance paperdollItem25 = Player2.getInventory().getPaperdollItem(7);
            if (paperdollItem25 != null) {
                B0.B(Player2, paperdollItem25, 0);
            }
            Player2.broadcastUserInfo(false, new UserInfoType[0]);
            this.useVoicedCommand("dressme", Player2, str2);
            return true;
        }
        if (str.equals("undressme-weapon")) {
            ItemInstance paperdollItem26 = Player2.getInventory().getPaperdollItem(5);
            if (paperdollItem26 != null) {
                B0.B(Player2, paperdollItem26, 0);
            }
            Player2.broadcastUserInfo(false, new UserInfoType[0]);
            this.useVoicedCommand("dressme", Player2, str2);
            return true;
        }
        if (str.equals("undressme-hat")) {
            ItemInstance paperdollItem27 = Player2.getInventory().getPaperdollItem(2);
            if (paperdollItem27 == null) {
                paperdollItem27 = Player2.getInventory().getPaperdollItem(3);
            }
            if (paperdollItem27 != null) {
                B0.B(Player2, paperdollItem27, 0);
            }
            Player2.broadcastUserInfo(false, new UserInfoType[0]);
            this.useVoicedCommand("dressme", Player2, str2);
            return true;
        }
        if (str.equals("showdress")) {
            Player2.setVar("showVisualChange", "true", -1L);
            Visuals.refreshAppStatus(Player2);
            Player2.broadcastUserInfo(false, new UserInfoType[0]);
            this.useVoicedCommand("dressme", Player2, str2);
            return true;
        }
        if (str.equals("hidedress")) {
            Player2.setVar("showVisualChange", "false", 0L);
            Visuals.refreshAppStatus(Player2);
            Player2.broadcastUserInfo(false, new UserInfoType[0]);
            this.useVoicedCommand("dressme", Player2, str2);
            return true;
        }
        if (str.equals("dress-tryweapon")) {
            DressMeWeaponData weapon3 = DressMeWeaponHolder.getInstance().getWeapon(Integer.parseInt(str2.split(" ")[0]));
            if (weapon3 == null) {
                return false;
            }
            ItemInstance paperdollItem28 = Player2.getInventory().getPaperdollItem(5);
            if (paperdollItem28 != null) {
                Player2.setVar("DressMeTry", Integer.valueOf(weapon3.C()).intValue(), -1L);
                Player2.broadcastUserInfo(false, new UserInfoType[0]);
            }
            this.useVoicedCommand("dress-weaponpage", Player2, str2);
            return false;
        }
        if (str.equals("dress-tryarmor")) {
            DressMeArmorData armor3 = DressMeArmorHolder.getInstance().getArmor(Integer.parseInt(str2.split(" ")[0]));
            if (armor3 == null) {
                return false;
            }
            HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
            hashMap.put(6, armor3.H());
            hashMap.put(11, armor3.L() > 0 ? armor3.L() : armor3.H());
            hashMap.put(10, armor3.K());
            hashMap.put(12, armor3.J());
            this.useVoicedCommand("dress-armorpage", Player2, str2);
            return false;
        }
        if (str.equals("dress-trycloak")) {
            DressMeCloakData cloak3 = DressMeCloakHolder.getInstance().getCloak(Integer.parseInt(str2.split(" ")[0]));
            if (cloak3 == null) {
                return false;
            }
            HashMap<Integer, Integer> hashMap2 = new HashMap<Integer, Integer>();
            hashMap2.put(23, cloak3.P());
            this.useVoicedCommand("dress-cloakpage", Player2, str2);
            return false;
        }
        if (str.equals("dress-tryshield")) {
            DressMeShieldData shield3 = DressMeShieldHolder.getInstance().getShield(Integer.parseInt(str2.split(" ")[0]));
            if (shield3 == null) {
                return false;
            }
            HashMap<Integer, Integer> hashMap3 = new HashMap<Integer, Integer>();
            hashMap3.put(7, shield3.W());
            this.useVoicedCommand("dress-shieldpage", Player2, str2);
            return false;
        }
        if (str.equals("dress-tryhat")) {
            int parseInt8 = Integer.parseInt(str2.split(" ")[0]);
            PcInventory inventory6 = Player2.getInventory();
            DressMeHatData hat3 = DressMeHatHolder.getInstance().getHat(parseInt8);
            if (hat3 == null) {
                return false;
            }
            ItemInstance ItemInstance3 = null;
            switch (hat3.E()) {
                case 1: 
                case 3: {
                    ItemInstance3 = inventory6.getPaperdollItem(2);
                    break;
                }
                case 2: {
                    ItemInstance3 = inventory6.getPaperdollItem(3);
                }
            }
            if (ItemInstance3 != null) {
                Player2.setVar("DressMeTry", Integer.valueOf(hat3.F()).intValue(), -1L);
                Player2.setVar("hairslotDressMeTry", Integer.valueOf(hat3.E()).intValue(), -1L);
                Player2.broadcastUserInfo(false, new UserInfoType[0]);
                Player2.broadcastUserInfo(false, new UserInfoType[0]);
            }
            this.useVoicedCommand("dress-hatpage", Player2, str2);
            return false;
        }
        if (str.equals("dressme-enchant")) {
            String[] split7;
            String htm18 = HtmCache.getInstance().getNotNull(this.index_item_path, Player2);
            String htm19 = HtmCache.getInstance().getNotNull(this.template_item_path, Player2);
            String str15 = "";
            if (str2 == null) {
                str2 = "1";
            }
            int parseInt9 = (split7 = str2.split(" "))[0].length() > 0 ? Integer.parseInt(split7[0]) : 1;
            int i25 = 0;
            List<DressMeEnchantData> orderL7 = HandleDressMeDb.orderL(Player2, A0.W, "character_dressme_enchant_list", "enchantId");
            int i26 = (parseInt9 - 1) * 5;
            while (i26 < orderL7.size()) {
                DressMeEnchantData dressMeEnchantData = orderL7.get(i26);
                if (dressMeEnchantData != null) {
                    String B8 = dressMeEnchantData.B();
                    if (B8.length() > 29) {
                        B8 = String.valueOf(B8.substring(0, 29)) + "...";
                    }
                    String replace27 = htm19.replace("{bypass}", "bypass -h user_dress-enchantpage " + dressMeEnchantData.C()).replace("{name}", B8);
                    str15 = String.valueOf(str15) + (HandleDressMeDb.dressMeEnchantInside(Player2, dressMeEnchantData) || Player2.getPremiumPoints() > 0L && GabConfig.PREMIUM_DRESS ? replace27.replace("{price}", "Owned") : replace27.replace("{price}", B.A(Player2, dressMeEnchantData.D(), dressMeEnchantData.A()))).replace("{icon}", B.A(dressMeEnchantData.R()));
                }
                if (++i25 >= 5) break;
                ++i26;
            }
            double ceil7 = Math.ceil((double)orderL7.size() / 5.0);
            int i27 = 1;
            String str16 = "";
            int i28 = 1;
            while ((double)i28 <= ceil7) {
                String string = str16 = i28 == parseInt9 ? String.valueOf(str16) + "<td width=25 align=center valign=top><button value=\"[" + i28 + "]\" action=\"bypass -h user_dressme-enchant " + i28 + "\" width=32 height=25 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>" : String.valueOf(str16) + "<td width=25 align=center valign=top><button value=\"" + i28 + "\" action=\"bypass -h user_dressme-enchant " + i28 + "\" width=32 height=25 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>";
                if (i27 == 7) {
                    str16 = String.valueOf(str16) + "</tr><tr>";
                    i27 = 0;
                }
                ++i27;
                ++i28;
            }
            if (str16.equals("")) {
                str16 = "<td width=30 align=center valign=top>...</td>";
            }
            this.A(Player2, htm18.replace("{list}", str15).replace("{navigation}", str16));
            return true;
        }
        if (str.equals("dress-enchantpage")) {
            DressMeEnchantData enchant = DressMeEnchantHolder.getInstance().getEnchant(Integer.parseInt(str2.split(" ")[0]));
            if (enchant != null) {
                String replace28 = HtmCache.getInstance().getNotNull(this.dress_enchant_path, Player2).replace("{my_enchant_icon}", "icon.NOIMAGE").replace("{bypass}", "bypass -h user_dress-enchant " + enchant.C()).replace("{bypassTry}", "bypass -h user_dress-tryenchant " + enchant.C()).replace("{name}", enchant.B());
                String replace29 = HandleDressMeDb.dressMeEnchantInside(Player2, enchant) || Player2.getPremiumPoints() > 0L && GabConfig.PREMIUM_DRESS ? replace28.replace("{price}", "Owned") : replace28.replace("{price}", B.A(Player2, enchant.D(), enchant.A()));
                ItemTemplate template9 = ItemHolder.getInstance().getTemplate(enchant.R());
                this.A(Player2, replace29.replace("{item_icon}", template9.getIcon()).replace("{item_name}", template9.getName()).replace("{item_grade}", template9.getItemGrade().toString()));
                return true;
            }
            return false;
        }
        if (str.equals("dress-enchant")) {
            DressMeEnchantData enchant2 = DressMeEnchantHolder.getInstance().getEnchant(Integer.parseInt(str2.split(" ")[0]));
            ItemTemplate template10 = ItemHolder.getInstance().getTemplate(enchant2.R());
            if (HandleDressMeDb.dressMeEnchantInside(Player2, enchant2) || Player2.getPremiumPoints() > 0L && GabConfig.PREMIUM_DRESS) {
                ItemInstance template10inst = Player2.getInventory().getPaperdollItem((int)template10.getBodyPart());
                Player2.sendMessage("Dress Was already in wardrobe! No price reduced!");
                B0.A(Player2, enchant2.LEVEL(), enchant2.BP(), false);
                Player2.sendMessage("Item bought with success! You can get it again from your wardrobe!");
            }
            Player2.broadcastUserInfo(false, new UserInfoType[0]);
            this.useVoicedCommand("dressme", Player2, str2);
            return true;
        }
        if (str.equals("dress-tryenchant")) {
            ItemHolder.getInstance().getTemplate(DressMeEnchantHolder.getInstance().getEnchant(Integer.parseInt(str2.split(" ")[0])).R());
            this.useVoicedCommand("dress-enchantpage", Player2, str2);
            return false;
        }
        if (str.equals("dressme-agathion")) {
            String[] split8;
            String htm20 = HtmCache.getInstance().getNotNull(this.index_item_path, Player2);
            String htm21 = HtmCache.getInstance().getNotNull(this.template_item_path, Player2);
            String str17 = "";
            if (str2 == null) {
                str2 = "1";
            }
            int parseInt10 = (split8 = str2.split(" "))[0].length() > 0 ? Integer.parseInt(split8[0]) : 1;
            int i29 = 0;
            List<DressMeAgathionData> orderL8 = HandleDressMeDb.orderL(Player2, A0.b, "character_dressme_agathion_list", "agathionId");
            int i30 = (parseInt10 - 1) * 5;
            while (i30 < orderL8.size()) {
                DressMeAgathionData dressMeAgathionData = orderL8.get(i30);
                if (dressMeAgathionData != null) {
                    String B9 = dressMeAgathionData.B();
                    if (B9.length() > 29) {
                        B9 = String.valueOf(B9.substring(0, 29)) + "...";
                    }
                    String replace30 = htm21.replace("{bypass}", "bypass -h user_dress-agathionpage " + dressMeAgathionData.C()).replace("{name}", B9);
                    str17 = String.valueOf(str17) + (HandleDressMeDb.dressMeAgathionInside(Player2, dressMeAgathionData) || Player2.getPremiumPoints() > 0L && GabConfig.PREMIUM_DRESS ? replace30.replace("{price}", "Owned") : replace30.replace("{price}", B.A(Player2, dressMeAgathionData.D(), dressMeAgathionData.A()))).replace("{icon}", B.A(dressMeAgathionData.O()));
                }
                if (++i29 >= 5) break;
                ++i30;
            }
            double ceil8 = Math.ceil((double)orderL8.size() / 5.0);
            int i31 = 1;
            String str18 = "";
            int i32 = 1;
            while ((double)i32 <= ceil8) {
                String string = str18 = i32 == parseInt10 ? String.valueOf(str18) + "<td width=25 align=center valign=top><button value=\"[" + i32 + "]\" action=\"bypass -h user_dressme-agathion " + i32 + "\" width=32 height=25 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>" : String.valueOf(str18) + "<td width=25 align=center valign=top><button value=\"" + i32 + "\" action=\"bypass -h user_dressme-agathion " + i32 + "\" width=32 height=25 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>";
                if (i31 == 7) {
                    str18 = String.valueOf(str18) + "</tr><tr>";
                    i31 = 0;
                }
                ++i31;
                ++i32;
            }
            if (str18.equals("")) {
                str18 = "<td width=30 align=center valign=top>...</td>";
            }
            this.A(Player2, htm20.replace("{list}", str17).replace("{navigation}", str18));
            return true;
        }
        if (str.equals("dress-agathionpage")) {
            DressMeAgathionData agathion = DressMeAgathionHolder.getInstance().getAgathion(Integer.parseInt(str2.split(" ")[0]));
            if (agathion != null) {
                String replace31 = HtmCache.getInstance().getNotNull(this.dress_aga_path, Player2).replace("{my_aga_icon}", "icon.NOIMAGE").replace("{bypass}", "bypass -h user_dress-agathion " + agathion.C()).replace("{bypassTry}", "bypass -h user_dress-tryagathion " + agathion.C()).replace("{name}", agathion.B());
                String replace32 = HandleDressMeDb.dressMeAgathionInside(Player2, agathion) || Player2.getPremiumPoints() > 0L && GabConfig.PREMIUM_DRESS ? replace31.replace("{price}", "Owned") : replace31.replace("{price}", B.A(Player2, agathion.D(), agathion.A()));
                return true;
            }
            return false;
        }
        if (!str.equals("dress-agathion")) {
            if (str.equals("dress-tryagathion")) {
                this.useVoicedCommand("dress-agathionpage", Player2, str2);
                return false;
            }
            if (str.equals("undressme-agathion")) {
                B0.B(Player2, 0, false);
                Player2.broadcastUserInfo(false, new UserInfoType[0]);
                this.useVoicedCommand("dressme", Player2, str2);
                return true;
            }
            if (str.equals("undressme-enchant")) {
                Player2.broadcastUserInfo(false, new UserInfoType[0]);
                this.useVoicedCommand("dressme", Player2, str2);
                return true;
            }
            return false;
        }
        return true;
    }

    private boolean A(DressMeWeaponData dressMeWeaponData) {
        String S = dressMeWeaponData.S();
        boolean z = true;
        switch (S.hashCode()) {
            case -1686339360: {
                if (!S.equals("DUALFIST")) break;
                z = true;
                break;
            }
            case -1434468602: {
                if (!S.equals("DUALDAGGER")) break;
                z = true;
                break;
            }
            case -1389040246: {
                if (!S.equals("CROSSBOW")) break;
                z = true;
                break;
            }
            case 65962: {
                if (!S.equals("BOW")) break;
                z = false;
                break;
            }
            case 2109564: {
                if (!S.equals("DUAL")) break;
                z = true;
                break;
            }
            case 2461624: {
                if (!S.equals("POLE")) break;
                z = true;
                break;
            }
            case 980307525: {
                if (!S.equals("ANCIENTSWORD")) break;
                z = true;
            }
        }
        return z;
    }

    private void A(Player Player2, String str) {
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(0);
        npcHtmlMessage.setHtml(str);
        Player2.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    public String[] getVoicedCommandList() {
        return this.A;
    }
}
