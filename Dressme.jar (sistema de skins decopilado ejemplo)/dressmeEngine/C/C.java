/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.data.htm.HtmCache
 *  l2.gameserver.data.xml.holder.ItemHolder
 *  l2.gameserver.handler.voicecommands.IVoicedCommandHandler
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.items.ItemInstance
 *  l2.gameserver.model.items.PcInventory
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.ExUserInfoEquipSlot
 *  l2.gameserver.network.l2.s2c.InventorySlot
 *  l2.gameserver.network.l2.s2c.NpcHtmlMessage
 *  l2.gameserver.network.l2.s2c.ShopPreviewInfo
 *  l2.gameserver.network.l2.s2c.UserInfoType
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.templates.item.ItemTemplate
 *  l2.gameserver.utils.ItemFunctions
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package dressmeEngine.C;

import community.GabrielCBB;
import config.GabConfig;
import dressmeEngine.A0;
import dressmeEngine.B0;
import dressmeEngine.C.TryDressMeCustom;
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
import java.util.stream.Collectors;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.data.htm.HtmCache;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.handler.voicecommands.IVoicedCommandHandler;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.items.PcInventory;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExUserInfoEquipSlot;
import l2.gameserver.network.l2.s2c.InventorySlot;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.network.l2.s2c.ShopPreviewInfo;
import l2.gameserver.network.l2.s2c.UserInfoType;
import l2.gameserver.scripts.Functions;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.utils.ItemFunctions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.A;
import utils.Visuals;

public class C
implements IVoicedCommandHandler {
    private static final Logger _log = LoggerFactory.getLogger(C.class);
    String info_path = "wardrobe/dressme/info.htm";
    String undressme_path = "wardrobe/dressme/undressme.htm";
    String index_item_path = "wardrobe/dressme/index-itemBBS.htm";
    String template_item_path = "wardrobe/dressme/template-itemBBS.htm";
    private final String[] A = new String[]{"dressme", "undressme", "dressinfo", "showdress", "hidedress", "dressme-armor", "dress-armor", "undressme-armor", "dressme-cloak", "dress-cloak", "undressme-cloak", "dressme-shield", "dress-shield", "undressme-shield", "dressme-weapon", "dress-weapon", "undressme-weapon", "dressme-hat", "dress-hat", "undressme-hat", "dress-tryarmor", "dress-trycloak", "dress-tryshield", "dress-tryweapon", "dress-tryhat", "dressme-enchant", "dress-enchant", "dress-tryenchant", "undressme-enchant", "dressme-agathion", "dress-agathion", "dress-tryagathion", "undressme-agathion", "dressme-suitarmor", "dressshowrarity"};
    ItemTemplate[] allT = ItemHolder.getInstance().getAllTemplates();

    public boolean useVoicedCommand(String str, final Player Player2, String str2) {
        String[] split9;
        if (str.equals("dressme")) {
            Player2.unsetVar("dressRarity");
            GabrielCBB.getInstance().parseCommand("gab_dressme", Player2);
            return true;
        }
        if (str.equals("dressshowrarity")) {
            String[] split = str2.split(" ");
            Player2.setVar("dressRarity", split[0].length() > 0 ? split[0] : "ALL", -1L);
            this.useVoicedCommand(Player2.getVar("lastDressCmd"), Player2, "1");
            return true;
        }
        if (str.equals("dressme-suitarmor")) {
            String[] split2;
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
            int parseInt = (split2 = str2.split(" "))[0].length() > 0 ? Integer.parseInt(split2[0]) : 1;
            Player2.setVar("lastDressCmd", "dressme-suitarmor", -1L);
            String quickVar = Player2.getVar("dressRarity");
            int i = 0;
            int i2 = 0;
            List<DressMeArmorData> orderL = HandleDressMeDb.orderL(Player2, B0.B("SUIT", new HashMap<Integer, DressMeArmorData>(), paperdollItem), "character_dressme_armor_list", "dressId");
            if (quickVar != null && !quickVar.equals("ALL")) {
                orderL = orderL.stream().filter(dressMeArmorData -> dressMeArmorData.N().A().equals(quickVar)).collect(Collectors.toList());
            }
            if (orderL == null) {
                _log.error("Dress me system: Armor Map is null.");
                return false;
            }
            int i3 = (parseInt - 1) * 9;
            while (i3 < orderL.size()) {
                if (ItemHolder.getInstance().getTemplate(i3) != null) {
                    DressMeArmorData dressMeArmorData2 = orderL.get(i3);
                    if (dressMeArmorData2 != null) {
                        String B2;
                        if (i % 3 == 0) {
                            str3 = String.valueOf(str3) + "<tr>";
                        }
                        if ((B2 = dressMeArmorData2.B()).length() > 20) {
                            B2 = String.valueOf(B2.substring(0, 20)) + "...";
                        }
                        String replace = htm2.replace("{bypass}", "bypass -h user_dress-armor " + dressMeArmorData2.C()).replace("{bypassTry}", "bypass -h user_dress-tryarmor " + dressMeArmorData2.C()).replace("{name}", B2);
                        str3 = String.valueOf(str3) + (HandleDressMeDb.dressMeArmorInside(Player2, dressMeArmorData2) || Player2.hasBonus() && GabConfig.PREMIUM_DRESS ? replace.replace("{price}", "Owned") : replace.replace("{price}", B.A(Player2, dressMeArmorData2.D(), dressMeArmorData2.A()))).replace("{icon}", B.A(dressMeArmorData2.H()));
                        if (++i % 3 == 0 || i3 + 1 == orderL.size()) {
                            str3 = String.valueOf(str3) + "</tr>";
                        }
                    }
                    if (++i2 >= 9) break;
                }
                ++i3;
            }
            int ceil = (int)Math.ceil(orderL.size() / 9);
            if (orderL.size() % 9 > 0) {
                ++ceil;
            }
            int i4 = 1;
            String str4 = "";
            int i5 = 1;
            while (i5 <= ceil) {
                String string = str4 = i5 == parseInt ? String.valueOf(str4) + "<td width=25 align=center valign=top><button value=\"[" + i5 + "]\" action=\"bypass -h user_dressme-suitarmor " + i5 + "\" width=32 height=25 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>" : String.valueOf(str4) + "<td width=25 align=center valign=top><button value=\"" + i5 + "\" action=\"bypass -h user_dressme-suitarmor " + i5 + "\" width=32 height=25 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>";
                if (i4 == 14) {
                    str4 = String.valueOf(str4) + "</tr><tr>";
                    i4 = 0;
                }
                ++i4;
                ++i5;
            }
            if (str4.equals("")) {
                str4 = "<td width=30 align=center valign=top>...</td>";
            }
            utils.A.A(htm.replace("{list}", str3).replace("{navigation}", str4), Player2);
            return true;
        }
        if (str.equals("dressme-armor")) {
            String[] split3;
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
            int parseInt2 = (split3 = str2.split(" "))[0].length() > 0 ? Integer.parseInt(split3[0]) : 1;
            Player2.setVar("lastDressCmd", "dressme-armor", -1L);
            String quickVar2 = Player2.getVar("dressRarity");
            int i6 = 0;
            int i7 = 0;
            List<DressMeArmorData> orderL2 = HandleDressMeDb.orderL(Player2, B0.B(paperdollItem2.getItemType().toString().toUpperCase(), new HashMap<Integer, DressMeArmorData>(), paperdollItem2), "character_dressme_armor_list", "dressId");
            if (quickVar2 != null && !quickVar2.equals("ALL")) {
                orderL2 = orderL2.stream().filter(dressMeArmorData3 -> dressMeArmorData3.N().A().equals(quickVar2)).collect(Collectors.toList());
            }
            if (orderL2 == null) {
                _log.error("Dress me system: Armor Map is null.");
                return false;
            }
            int i8 = (parseInt2 - 1) * 9;
            while (i8 < orderL2.size()) {
                DressMeArmorData dressMeArmorData4 = orderL2.get(i8);
                if (dressMeArmorData4 != null) {
                    String B3;
                    if (i6 % 3 == 0) {
                        str5 = String.valueOf(str5) + "<tr>";
                    }
                    if ((B3 = dressMeArmorData4.B()).length() > 20) {
                        B3 = String.valueOf(B3.substring(0, 20)) + "...";
                    }
                    String is_using = paperdollItem2.getVisibleItemId() == dressMeArmorData4.H() ? "/Using" : "";
                    String replace2 = htm4.replace("{bypass}", "bypass -h user_dress-armor " + dressMeArmorData4.C()).replace("{bypassTry}", "bypass -h user_dress-tryarmor " + dressMeArmorData4.C()).replace("{name}", B3);
                    str5 = String.valueOf(str5) + (HandleDressMeDb.dressMeArmorInside(Player2, dressMeArmorData4) || Player2.hasBonus() && GabConfig.PREMIUM_DRESS ? replace2.replace("{price}", "Owned" + is_using) : replace2.replace("{price}", B.A(Player2, dressMeArmorData4.D(), dressMeArmorData4.A()))).replace("{icon}", B.A(dressMeArmorData4.H()));
                    if (++i6 % 3 == 0 || i8 + 1 == orderL2.size()) {
                        str5 = String.valueOf(str5) + "</tr>";
                    }
                }
                if (++i7 >= 9) break;
                ++i8;
            }
            int ceil2 = (int)Math.ceil(orderL2.size() / 9);
            if (orderL2.size() % 9 > 0) {
                ++ceil2;
            }
            int i9 = 1;
            String str6 = "";
            int i10 = 1;
            while (i10 <= ceil2) {
                String string = str6 = i10 == parseInt2 ? String.valueOf(str6) + "<td width=25 align=center valign=top><button value=\"[" + i10 + "]\" action=\"bypass -h user_dressme-armor " + i10 + "\" width=32 height=25 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>" : String.valueOf(str6) + "<td width=25 align=center valign=top><button value=\"" + i10 + "\" action=\"bypass -h user_dressme-armor " + i10 + "\" width=32 height=25 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>";
                if (i9 == 14) {
                    str6 = String.valueOf(str6) + "</tr><tr>";
                    i9 = 0;
                }
                ++i9;
                ++i10;
            }
            if (str6.equals("")) {
                str6 = "<td width=30 align=center valign=top>...</td>";
            }
            utils.A.A(htm3.replace("{list}", str5).replace("{navigation}", str6), Player2);
            return true;
        }
        if (str.equals("dressme-cloak")) {
            String[] split4;
            ItemInstance paperdCloak = Player2.getInventory().getPaperdollItem(28);
            if (paperdCloak == null) {
                Player2.sendMessage("Error: Cloak must be equiped.");
                this.useVoicedCommand("dress-cloakpage", Player2, str2);
                return false;
            }
            String htm5 = HtmCache.getInstance().getNotNull(this.index_item_path, Player2);
            String htm6 = HtmCache.getInstance().getNotNull(this.template_item_path, Player2);
            String str7 = "";
            if (str2 == null) {
                str2 = "1";
            }
            int parseInt3 = (split4 = str2.split(" "))[0].length() > 0 ? Integer.parseInt(split4[0]) : 1;
            Player2.setVar("lastDressCmd", "dressme-cloak", -1L);
            String quickVar3 = Player2.getVar("dressRarity");
            int i11 = 0;
            int i12 = 0;
            List<DressMeCloakData> orderL3 = HandleDressMeDb.orderL(Player2, A0.d, "character_dressme_cloak_list", "cloakDressId");
            if (quickVar3 != null && !quickVar3.equals("ALL")) {
                orderL3 = orderL3.stream().filter(dressMeCloakData -> dressMeCloakData.Q().A().equals(quickVar3)).collect(Collectors.toList());
            }
            int i13 = (parseInt3 - 1) * 9;
            while (i13 < orderL3.size()) {
                DressMeCloakData dressMeCloakData2 = orderL3.get(i13);
                if (dressMeCloakData2 != null) {
                    String B4;
                    if (i12 % 3 == 0) {
                        str7 = String.valueOf(str7) + "<tr>";
                    }
                    if ((B4 = dressMeCloakData2.B()).length() > 20) {
                        B4 = String.valueOf(B4.substring(0, 20)) + "...";
                    }
                    String replace3 = htm6.replace("{bypass}", "bypass -h user_dress-cloak " + dressMeCloakData2.C()).replace("{bypassTry}", "bypass -h user_dress-trycloak " + dressMeCloakData2.C()).replace("{name}", B4);
                    String is_using = paperdCloak.getVisibleItemId() == dressMeCloakData2.P() ? "/Using" : "";
                    str7 = String.valueOf(str7) + (HandleDressMeDb.dressMeCloakInside(Player2, dressMeCloakData2) || Player2.hasBonus() && GabConfig.PREMIUM_DRESS ? replace3.replace("{price}", "Owned" + is_using) : replace3.replace("{price}", B.A(Player2, dressMeCloakData2.D(), dressMeCloakData2.A()))).replace("{icon}", B.A(dressMeCloakData2.P()));
                    if (++i12 % 3 == 0 || i13 + 1 == orderL3.size()) {
                        str7 = String.valueOf(str7) + "</tr>";
                    }
                }
                if (++i11 >= 9) break;
                ++i13;
            }
            int ceil3 = (int)Math.ceil(orderL3.size() / 9);
            if (orderL3.size() % 9 > 0) {
                ++ceil3;
            }
            int i14 = 1;
            String str8 = "";
            int i15 = 1;
            while (i15 <= ceil3) {
                String string = str8 = i15 == parseInt3 ? String.valueOf(str8) + "<td width=25 align=center valign=top><button value=\"[" + i15 + "]\" action=\"bypass -h user_dressme-cloak " + i15 + "\" width=32 height=25 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>" : String.valueOf(str8) + "<td width=25 align=center valign=top><button value=\"" + i15 + "\" action=\"bypass -h user_dressme-cloak " + i15 + "\" width=32 height=25 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>";
                if (i14 == 14) {
                    str8 = String.valueOf(str8) + "</tr><tr>";
                    i14 = 0;
                }
                ++i14;
                ++i15;
            }
            if (str8.equals("")) {
                str8 = "<td width=30 align=center valign=top>...</td>";
            }
            utils.A.A(htm5.replace("{list}", str7).replace("{navigation}", str8), Player2);
            return true;
        }
        if (str.equals("dressme-shield")) {
            String[] split5;
            ItemInstance paperdollItem3 = Player2.getInventory().getPaperdollItem(7);
            if (paperdollItem3 == null || !paperdollItem3.isArmor()) {
                Player2.sendMessage("Please equip an Shield or Sigil!");
                return false;
            }
            String htm7 = HtmCache.getInstance().getNotNull(this.index_item_path, Player2);
            String htm8 = HtmCache.getInstance().getNotNull(this.template_item_path, Player2);
            String str9 = "";
            if (str2 == null) {
                str2 = "1";
            }
            int parseInt4 = (split5 = str2.split(" "))[0].length() > 0 ? Integer.parseInt(split5[0]) : 1;
            Player2.setVar("lastDressCmd", "dressme-shield", -1L);
            String quickVar4 = Player2.getVar("dressRarity");
            int i16 = 0;
            int i17 = 0;
            List<DressMeShieldData> orderL4 = HandleDressMeDb.orderL(Player2, B0.C(paperdollItem3.getItemType().toString().toUpperCase(), new LinkedHashMap<Integer, DressMeShieldData>(), paperdollItem3), "character_dressme_shield_list", "shieldDressId");
            if (quickVar4 != null && !quickVar4.equals("ALL")) {
                orderL4 = orderL4.stream().filter(dressMeShieldData -> dressMeShieldData.Y().A().equals(quickVar4)).collect(Collectors.toList());
            }
            int i18 = (parseInt4 - 1) * 9;
            while (i18 < orderL4.size()) {
                DressMeShieldData dressMeShieldData2 = orderL4.get(i18);
                if (dressMeShieldData2 != null) {
                    String B5;
                    if (i16 % 3 == 0) {
                        str9 = String.valueOf(str9) + "<tr>";
                    }
                    if ((B5 = dressMeShieldData2.B()).length() > 20) {
                        B5 = String.valueOf(B5.substring(0, 20)) + "...";
                    }
                    String replace4 = htm8.replace("{bypass}", "bypass -h user_dress-shield " + dressMeShieldData2.C()).replace("{bypassTry}", "bypass -h user_dress-tryshield " + dressMeShieldData2.C()).replace("{name}", B5);
                    String is_using = paperdollItem3.getVisibleItemId() == dressMeShieldData2.W() ? "/Using" : "";
                    str9 = String.valueOf(str9) + (HandleDressMeDb.dressMeShieldInside(Player2, dressMeShieldData2) || Player2.hasBonus() && GabConfig.PREMIUM_DRESS ? replace4.replace("{price}", "Owned" + is_using) : replace4.replace("{price}", B.A(Player2, dressMeShieldData2.D(), dressMeShieldData2.A()))).replace("{icon}", B.A(dressMeShieldData2.W()));
                    if (++i16 % 3 == 0 || i18 + 1 == orderL4.size()) {
                        str9 = String.valueOf(str9) + "</tr>";
                    }
                    ++i17;
                }
                if (i17 >= 9) break;
                ++i18;
            }
            int ceil4 = (int)Math.ceil(orderL4.size() / 9);
            if (orderL4.size() % 9 > 0) {
                ++ceil4;
            }
            int i19 = 1;
            String str10 = "";
            int i20 = 1;
            while (i20 <= ceil4) {
                String string = str10 = i20 == parseInt4 ? String.valueOf(str10) + "<td width=25 align=center valign=top><button value=\"[" + i20 + "]\" action=\"bypass -h user_dressme-shield " + i20 + "\" width=32 height=25 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>" : String.valueOf(str10) + "<td width=25 align=center valign=top><button value=\"" + i20 + "\" action=\"bypass -h user_dressme-shield " + i20 + "\" width=32 height=25 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>";
                if (i19 == 14) {
                    str10 = String.valueOf(str10) + "</tr><tr>";
                    i19 = 0;
                }
                ++i19;
                ++i20;
            }
            if (str10.equals("")) {
                str10 = "<td width=30 align=center valign=top>...</td>";
            }
            utils.A.A(htm7.replace("{list}", str9).replace("{navigation}", str10), Player2);
            return true;
        }
        if (str.equals("dressme-weapon")) {
            String[] split6;
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
            int parseInt5 = (split6 = str2.split(" "))[0].length() > 0 ? Integer.parseInt(split6[0]) : 1;
            Player2.setVar("lastDressCmd", "dressme-weapon", -1L);
            String quickVar5 = Player2.getVar("dressRarity");
            int i21 = 0;
            int i22 = 0;
            List<DressMeWeaponData> orderL5 = HandleDressMeDb.orderL(Player2, B0.A(paperdollItem4.getItemType().toString().toUpperCase(), new LinkedHashMap<Integer, DressMeWeaponData>(), paperdollItem4), "character_dressme_weapon_list", "weaponDressId");
            if (quickVar5 != null && !quickVar5.equals("ALL")) {
                orderL5 = orderL5.stream().filter(dressMeWeaponData -> dressMeWeaponData.U().A().equals(quickVar5)).collect(Collectors.toList());
            }
            if (orderL5 == null) {
                _log.error("Dress me system: Weapon Map is null.");
                return false;
            }
            int i23 = (parseInt5 - 1) * 9;
            while (i23 < orderL5.size()) {
                DressMeWeaponData dressMeWeaponData2 = orderL5.get(i23);
                if (dressMeWeaponData2 != null) {
                    String B6;
                    if (i21 % 3 == 0) {
                        str11 = String.valueOf(str11) + "<tr>";
                    }
                    if ((B6 = dressMeWeaponData2.B()).length() > 20) {
                        B6 = String.valueOf(B6.substring(0, 20)) + "...";
                    }
                    String replace5 = htm10.replace("{bypass}", "bypass -h user_dress-weapon " + dressMeWeaponData2.C()).replace("{bypassTry}", "bypass -h user_dress-tryweapon " + dressMeWeaponData2.C()).replace("{name}", B6);
                    String is_using = paperdollItem4.getVisibleItemId() == dressMeWeaponData2.C() ? "/Using" : "";
                    str11 = String.valueOf(str11) + (HandleDressMeDb.dressMeWeaponInside(Player2, dressMeWeaponData2) || Player2.hasBonus() && GabConfig.PREMIUM_DRESS ? replace5.replace("{price}", "Owned" + is_using) : replace5.replace("{price}", B.A(Player2, dressMeWeaponData2.D(), dressMeWeaponData2.A()))).replace("{icon}", B.A(dressMeWeaponData2.C()));
                    if (++i21 % 3 == 0 || i23 + 1 == orderL5.size()) {
                        str11 = String.valueOf(str11) + "</tr>";
                    }
                    ++i22;
                }
                if (i22 >= 9) break;
                ++i23;
            }
            int ceil5 = (int)Math.ceil(orderL5.size() / 9);
            if (orderL5.size() % 9 > 0) {
                ++ceil5;
            }
            int i24 = 1;
            String str12 = "";
            int i25 = 1;
            while (i25 <= ceil5) {
                String string = str12 = i25 == parseInt5 ? String.valueOf(str12) + "<td width=25 align=center valign=top><button value=\"[" + i25 + "]\" action=\"bypass -h user_dressme-weapon " + i25 + "\" width=32 height=25 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>" : String.valueOf(str12) + "<td width=25 align=center valign=top><button value=\"" + i25 + "\" action=\"bypass -h user_dressme-weapon " + i25 + "\" width=32 height=25 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>";
                if (i24 == 14) {
                    str12 = String.valueOf(str12) + "</tr><tr>";
                    i24 = 0;
                }
                ++i24;
                ++i25;
            }
            if (str12.equals("")) {
                str12 = "<td width=30 align=center valign=top>...</td>";
            }
            utils.A.A(htm9.replace("{list}", str11).replace("{navigation}", str12), Player2);
            return true;
        }
        if (str.equals("dressme-hat")) {
            String[] split7;
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
            int parseInt6 = (split7 = str2.split(" "))[0].length() > 0 ? Integer.parseInt(split7[0]) : 1;
            Player2.setVar("lastDressCmd", "dressme-hat", -1L);
            String quickVar6 = Player2.getVar("dressRarity");
            int i26 = 0;
            int i27 = 0;
            List<DressMeHatData> orderL6 = HandleDressMeDb.orderL(Player2, B0.A(new LinkedHashMap<Integer, DressMeHatData>(), paperdollItem5), "character_dressme_hat_list", "hatDressId");
            if (quickVar6 != null && !quickVar6.equals("ALL")) {
                orderL6 = orderL6.stream().filter(dressMeHatData -> dressMeHatData.G().A().equals(quickVar6)).collect(Collectors.toList());
            }
            if (orderL6 == null) {
                _log.error("Dress me system: Hat Map is null.");
                return false;
            }
            int i28 = (parseInt6 - 1) * 9;
            while (i28 < orderL6.size()) {
                DressMeHatData dressMeHatData2 = orderL6.get(i28);
                if (dressMeHatData2 != null) {
                    String B7;
                    if (i26 % 3 == 0) {
                        str13 = String.valueOf(str13) + "<tr>";
                    }
                    if ((B7 = dressMeHatData2.B()).length() > 20) {
                        B7 = String.valueOf(B7.substring(0, 20)) + "...";
                    }
                    String replace6 = htm12.replace("{bypass}", "bypass -h user_dress-hat " + dressMeHatData2.C()).replace("{bypassTry}", "bypass -h user_dress-tryhat " + dressMeHatData2.C()).replace("{name}", B7);
                    String is_using = paperdollItem5.getVisibleItemId() == dressMeHatData2.F() ? "/Using" : "";
                    str13 = String.valueOf(str13) + (HandleDressMeDb.dressMeHatInside(Player2, dressMeHatData2) || Player2.hasBonus() && GabConfig.PREMIUM_DRESS ? replace6.replace("{price}", "Owned" + is_using) : replace6.replace("{price}", B.A(Player2, dressMeHatData2.D(), dressMeHatData2.A()))).replace("{icon}", B.A(dressMeHatData2.F()));
                    if (++i26 % 3 == 0 || i28 + 1 == orderL6.size()) {
                        str13 = String.valueOf(str13) + "</tr>";
                    }
                }
                if (++i27 >= 9) break;
                ++i28;
            }
            double ceil6 = (int)Math.ceil(orderL6.size() / 9);
            if (orderL6.size() % 9 > 0) {
                ceil6 += 1.0;
            }
            int i29 = 1;
            String str14 = "";
            int i30 = 1;
            while ((double)i30 <= ceil6) {
                String string = str14 = i30 == parseInt6 ? String.valueOf(str14) + "<td width=25 align=center valign=top><button value=\"[" + i30 + "]\" action=\"bypass -h user_dressme-hat " + i30 + "\" width=32 height=25 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>" : String.valueOf(str14) + "<td width=25 align=center valign=top><button value=\"" + i30 + "\" action=\"bypass -h user_dressme-hat " + i30 + "\" width=32 height=25 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>";
                if (i29 == 14) {
                    str14 = String.valueOf(str14) + "</tr><tr>";
                    i29 = 0;
                }
                ++i29;
                ++i30;
            }
            if (str14.equals("")) {
                str14 = "<td width=30 align=center valign=top>...</td>";
            }
            utils.A.A(htm11.replace("{list}", str13).replace("{navigation}", str14), Player2);
            return true;
        }
        if (str.equals("dressinfo")) {
            this.A(Player2, HtmCache.getInstance().getNotNull(this.info_path, Player2));
            return true;
        }
        if (str.equals("dress-armor")) {
            DressMeArmorData armor = DressMeArmorHolder.getInstance().getArmor(Integer.parseInt(str2.split(" ")[0]));
            PcInventory inventory = Player2.getInventory();
            ItemInstance paperdollItem6 = inventory.getPaperdollItem(6);
            ItemInstance paperdollItem7 = inventory.getPaperdollItem(11);
            ItemInstance paperdollItem8 = inventory.getPaperdollItem(10);
            ItemInstance paperdollItem9 = inventory.getPaperdollItem(12);
            if (paperdollItem6 == null) {
                Player2.sendMessage("Error: Chest must be equiped.");
                this.useVoicedCommand("dress-armorpage", Player2, str2);
                return false;
            }
            if (HandleDressMeDb.dressMeArmorInside(Player2, armor) || Player2.hasBonus() && GabConfig.PREMIUM_DRESS) {
                B0.B(Player2, paperdollItem6, armor.H());
                B0.B(Player2, paperdollItem7, armor.L() > 0 ? armor.L() : armor.H());
                B0.B(Player2, paperdollItem8, armor.K());
                B0.B(Player2, paperdollItem9, armor.J());
                Player2.sendMessage("Dress Was already in wardrobe! No price reduced!");
            } else if (Functions.getItemCount((Playable)Player2, (int)armor.A()) >= armor.D()) {
                if (ItemFunctions.removeItem((Playable)Player2, (int)armor.A(), (long)armor.D(), (boolean)true) == 0L || !HandleDressMeDb.insertDressMeArmor(Player2, armor)) {
                    Player2.sendMessage("Something went went wrong trying to send your dress to your wardrobe or we couldn't get the money from your inventory!");
                    return false;
                }
                B0.B(Player2, paperdollItem6, armor.H());
                B0.B(Player2, paperdollItem7, armor.L() > 0 ? armor.L() : armor.H());
                B0.B(Player2, paperdollItem8, armor.K());
                B0.B(Player2, paperdollItem9, armor.J());
                Player2.sendMessage("Item bought with success! You can get it again from your wardrobe!");
            } else {
                Player2.sendMessage("You do not have enough funds to purchase.");
            }
            GabrielCBB.getInstance().parseCommand("gab_dressme", Player2);
            return true;
        }
        if (str.equals("dress-cloak")) {
            DressMeCloakData cloak = DressMeCloakHolder.getInstance().getCloak(Integer.parseInt(str2.split(" ")[0]));
            ItemInstance paperdollItem10 = Player2.getInventory().getPaperdollItem(28);
            if (paperdollItem10 == null) {
                Player2.sendMessage("Error: Cloak must be equiped.");
                this.useVoicedCommand("dress-cloakpage", Player2, str2);
                return false;
            }
            if (HandleDressMeDb.dressMeCloakInside(Player2, cloak) || Player2.hasBonus() && GabConfig.PREMIUM_DRESS) {
                B0.B(Player2, paperdollItem10, cloak.P());
                Player2.broadcastUserInfo(false, new UserInfoType[0]);
                Player2.sendMessage("Dress Was already in wardrobe! No price reduced!");
            } else if (Functions.getItemCount((Playable)Player2, (int)cloak.A()) >= cloak.D()) {
                if (ItemFunctions.removeItem((Playable)Player2, (int)cloak.A(), (long)cloak.D(), (boolean)true) == 0L || !HandleDressMeDb.insertDressMeCloak(Player2, cloak)) {
                    Player2.sendMessage("Something went went wrong trying to send your dress to your wardrobe or we couldn't get the money from your inventory!");
                    return false;
                }
                B0.B(Player2, paperdollItem10, cloak.P());
                Player2.broadcastUserInfo(false, new UserInfoType[0]);
                Player2.sendMessage("Item bought with success! You can get it again from your wardrobe!");
            } else {
                Player2.sendMessage("You do not have enough funds to purchase.");
            }
            GabrielCBB.getInstance().parseCommand("gab_dressme", Player2);
            return true;
        }
        if (str.equals("dress-shield")) {
            DressMeShieldData shield = DressMeShieldHolder.getInstance().getShield(Integer.parseInt(str2.split(" ")[0]));
            ItemInstance paperdollItem11 = Player2.getInventory().getPaperdollItem(7);
            if (paperdollItem11 == null) {
                Player2.sendMessage("Error: Shield/Sigil must be equiped.");
                this.useVoicedCommand("dress-shieldpage", Player2, str2);
                return false;
            }
            String description = paperdollItem11.getItemType().toString();
            if (description.toUpperCase().equals("SHIELD") && !shield.X()) {
                Player2.sendMessage("Error: Sigil must be equiped.");
                this.useVoicedCommand("dress-shieldpage", Player2, str2);
                return false;
            }
            if (description.equals("SIGIL") && shield.X()) {
                Player2.sendMessage("Error: Shield must be equiped.");
                this.useVoicedCommand("dress-shieldpage", Player2, str2);
                return false;
            }
            if (HandleDressMeDb.dressMeShieldInside(Player2, shield) || Player2.hasBonus() && GabConfig.PREMIUM_DRESS) {
                B0.B(Player2, paperdollItem11, shield.W());
                Player2.sendMessage("Dress Was already in wardrobe! No price reduced!");
            } else if (Functions.getItemCount((Playable)Player2, (int)shield.A()) >= shield.D()) {
                if (ItemFunctions.removeItem((Playable)Player2, (int)shield.A(), (long)shield.D(), (boolean)true) == 0L || !HandleDressMeDb.insertDressMeShield(Player2, shield)) {
                    Player2.sendMessage("Something went went wrong trying to send your dress to your wardrobe or we couldn't get the money from your inventory!");
                    return false;
                }
                B0.B(Player2, paperdollItem11, shield.W());
                Player2.sendMessage("Item bought with success! You can get it again from your wardrobe!");
            } else {
                Player2.sendMessage("You do not have enough funds to purchase.");
            }
            GabrielCBB.getInstance().parseCommand("gab_dressme", Player2);
            return true;
        }
        if (str.equals("dress-weapon")) {
            DressMeWeaponData weapon = DressMeWeaponHolder.getInstance().getWeapon(Integer.parseInt(str2.split(" ")[0]));
            ItemInstance paperdollItem12 = Player2.getInventory().getPaperdollItem(5);
            String itemType = paperdollItem12.getItemType().toString().toUpperCase();
            if (paperdollItem12 == null) {
                Player2.sendMessage("Error: Weapon must be equiped.");
                this.useVoicedCommand("dress-weaponpage", Player2, str2);
                return false;
            }
            if (!itemType.equals(weapon.S())) {
                Player2.sendMessage("Error: Weapon must be equals type.");
                this.useVoicedCommand("dressme-weapon", Player2, null);
                return false;
            }
            if (paperdollItem12.getBodyPart() == 16384L && !weapon.S().startsWith("BIG") && !this.A(weapon)) {
                Player2.sendMessage("Error: Weapon must be equals type.");
                this.useVoicedCommand("dressme-weapon", Player2, null);
                return false;
            }
            if (paperdollItem12.getBodyPart() != 16384L && weapon.S().startsWith("BIG") && !this.A(weapon)) {
                Player2.sendMessage("Error: Weapon must be equals type.");
                this.useVoicedCommand("dressme-weapon", Player2, null);
                return false;
            }
            if (paperdollItem12.isAugmented() && !weapon.T()) {
                Player2.sendMessage("Error: Weapon must be equals type (magic).");
                this.useVoicedCommand("dressme-weapon", Player2, null);
                return false;
            }
            if (!paperdollItem12.isAugmented() && weapon.T()) {
                Player2.sendMessage("Error: Weapon must be equals type (Non Magic).");
                this.useVoicedCommand("dressme-weapon", Player2, null);
                return false;
            }
            if (HandleDressMeDb.dressMeWeaponInside(Player2, weapon) || Player2.hasBonus() && GabConfig.PREMIUM_DRESS) {
                B0.B(Player2, paperdollItem12, weapon.C());
                Player2.sendMessage("Dress Was already in wardrobe! No price reduced!");
            } else if (Functions.getItemCount((Playable)Player2, (int)weapon.A()) >= weapon.D()) {
                if (ItemFunctions.removeItem((Playable)Player2, (int)weapon.A(), (long)weapon.D(), (boolean)true) == 0L || !HandleDressMeDb.insertDressMeWeapon(Player2, weapon)) {
                    Player2.sendMessage("Something went went wrong trying to send your dress to your wardrobe or we couldn't get the money from your inventory!");
                    return false;
                }
                B0.B(Player2, paperdollItem12, weapon.C());
                Player2.sendMessage("Item bought with success! You can get it again from your wardrobe!");
            } else {
                Player2.sendMessage("You do not have enough funds to purchase.");
            }
            GabrielCBB.getInstance().parseCommand("gab_dressme", Player2);
            return true;
        }
        if (str.equals("dress-hat")) {
            DressMeHatData hat = DressMeHatHolder.getInstance().getHat(Integer.parseInt(str2.split(" ")[0]));
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
            if (ItemInstance2 == null) {
                Player2.sendMessage("Error: Hat must be equiped.");
                this.useVoicedCommand("dress-hatpage", Player2, str2);
                return false;
            }
            if (ItemInstance2.getBodyPart() != ItemHolder.getInstance().getTemplate(hat.F()).getBodyPart()) {
                Player2.sendMessage("Error: You can't change visual on different hat types!");
                this.useVoicedCommand("dress-hatpage", Player2, str2);
                return false;
            }
            if (HandleDressMeDb.dressMeHatInside(Player2, hat) || Player2.hasBonus() && GabConfig.PREMIUM_DRESS) {
                B0.B(Player2, ItemInstance2, hat.F());
                Player2.sendMessage("Dress Was already in wardrobe! No price reduced!");
            } else if (Functions.getItemCount((Playable)Player2, (int)hat.A()) >= hat.D()) {
                if (ItemFunctions.removeItem((Playable)Player2, (int)hat.A(), (long)hat.D(), (boolean)true) == 0L || !HandleDressMeDb.insertDressMeHat(Player2, hat)) {
                    Player2.sendMessage("Something went went wrong trying to send your dress to your wardrobe or we couldn't get the money from your inventory!");
                    return false;
                }
                B0.B(Player2, ItemInstance2, hat.F());
                Player2.sendMessage("Item bought with success! You can get it again from your wardrobe!");
            } else {
                Player2.sendMessage("You do not have enough funds to purchase.");
            }
            GabrielCBB.getInstance().parseCommand("gab_dressme", Player2);
            return true;
        }
        if (str.equals("undressme-armor")) {
            PcInventory inventory3 = Player2.getInventory();
            ItemInstance paperdollItem13 = inventory3.getPaperdollItem(6);
            ItemInstance paperdollItem14 = inventory3.getPaperdollItem(11);
            ItemInstance paperdollItem15 = inventory3.getPaperdollItem(10);
            ItemInstance paperdollItem16 = inventory3.getPaperdollItem(12);
            if (paperdollItem13 != null) {
                B0.B(Player2, paperdollItem13, 0);
            }
            if (paperdollItem14 != null) {
                B0.B(Player2, paperdollItem14, 0);
            }
            if (paperdollItem15 != null) {
                B0.B(Player2, paperdollItem15, 0);
            }
            if (paperdollItem16 != null) {
                B0.B(Player2, paperdollItem16, 0);
            }
            Player2.broadcastUserInfo(false, new UserInfoType[0]);
            GabrielCBB.getInstance().parseCommand("gab_dressme", Player2);
            return true;
        }
        if (str.equals("undressme-cloak")) {
            ItemInstance paperdollItem17 = Player2.getInventory().getPaperdollItem(28);
            if (paperdollItem17 != null) {
                B0.B(Player2, paperdollItem17, 0);
            }
            Player2.broadcastUserInfo(false, new UserInfoType[0]);
            GabrielCBB.getInstance().parseCommand("gab_dressme", Player2);
            return true;
        }
        if (str.equals("undressme-shield")) {
            ItemInstance paperdollItem18 = Player2.getInventory().getPaperdollItem(7);
            if (paperdollItem18 != null) {
                B0.B(Player2, paperdollItem18, 0);
            }
            GabrielCBB.getInstance().parseCommand("gab_dressme", Player2);
            return true;
        }
        if (str.equals("undressme-weapon")) {
            ItemInstance paperdollItem19 = Player2.getInventory().getPaperdollItem(5);
            if (paperdollItem19 != null) {
                B0.B(Player2, paperdollItem19, 0);
            }
            Player2.broadcastUserInfo(false, new UserInfoType[0]);
            GabrielCBB.getInstance().parseCommand("gab_dressme", Player2);
            return true;
        }
        if (str.equals("undressme-hat")) {
            ItemInstance paperdollItem20 = Player2.getInventory().getPaperdollItem(2);
            if (paperdollItem20 == null) {
                paperdollItem20 = Player2.getInventory().getPaperdollItem(3);
            }
            if (paperdollItem20 != null) {
                B0.B(Player2, paperdollItem20, 0);
            }
            Player2.broadcastUserInfo(false, new UserInfoType[0]);
            GabrielCBB.getInstance().parseCommand("gab_dressme", Player2);
            return true;
        }
        if (str.equals("showdress")) {
            Player2.setVar("showVisualChange", "true", -1L);
            Visuals.refreshAppStatus(Player2);
            Player2.broadcastUserInfo(false, new UserInfoType[0]);
            GabrielCBB.getInstance().parseCommand("gab_dressme", Player2);
            return true;
        }
        if (str.equals("hidedress")) {
            Player2.setVar("showVisualChange", "false", -1L);
            Visuals.refreshAppStatus(Player2);
            Player2.broadcastUserInfo(false, new UserInfoType[0]);
            GabrielCBB.getInstance().parseCommand("gab_dressme", Player2);
            return true;
        }
        if (str.equals("dress-tryweapon")) {
            if (Player2.getVar("IsTrying") != null) {
                return false;
            }
            DressMeWeaponData weapon2 = DressMeWeaponHolder.getInstance().getWeapon(Integer.parseInt(str2.split(" ")[0]));
            if (weapon2 == null) {
                return false;
            }
            ItemInstance paperdollItem21 = Player2.getInventory().getPaperdollItem(5);
            if (paperdollItem21 != null) {
                Player2.setVar("IsTrying", 1, -1L);
                HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
                hashMap.put(5, weapon2.C());
                Player2.sendPacket((IStaticPacket)new ShopPreviewInfo(hashMap));
                ThreadPoolManager.getInstance().schedule((Runnable)new TryDressMeCustom(Player2, null), 5000L);
            }
            this.useVoicedCommand("dress-weaponpage", Player2, str2);
            return false;
        }
        if (str.equals("dress-tryarmor")) {
            if (Player2.getVar("IsTrying") != null) {
                return false;
            }
            DressMeArmorData armor2 = DressMeArmorHolder.getInstance().getArmor(Integer.parseInt(str2.split(" ")[0]));
            if (armor2 == null) {
                return false;
            }
            HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
            hashMap.put(6, armor2.H());
            hashMap.put(11, armor2.L() > 0 ? armor2.L() : armor2.H());
            hashMap.put(10, armor2.K());
            hashMap.put(12, armor2.J());
            Player2.setVar("IsTrying", 1, -1L);
            Player2.sendPacket((IStaticPacket)new ShopPreviewInfo(hashMap));
            ThreadPoolManager.getInstance().schedule((Runnable)new TryDressMeCustom(Player2, null), 5000L);
            this.useVoicedCommand("dress-armorpage", Player2, str2);
            return false;
        }
        if (str.equals("dress-trycloak")) {
            if (Player2.getVar("IsTrying") != null) {
                return false;
            }
            DressMeCloakData cloak2 = DressMeCloakHolder.getInstance().getCloak(Integer.parseInt(str2.split(" ")[0]));
            if (cloak2 == null) {
                return false;
            }
            HashMap<Integer, Integer> hashMap2 = new HashMap<Integer, Integer>();
            hashMap2.put(28, cloak2.P());
            Player2.sendPacket((IStaticPacket)new ShopPreviewInfo(hashMap2));
            ThreadPoolManager.getInstance().schedule((Runnable)new TryDressMeCustom(Player2, null), 5000L);
            Player2.setVar("IsTrying", 1, -1L);
            this.useVoicedCommand("dress-cloakpage", Player2, str2);
            return false;
        }
        if (str.equals("dress-tryshield")) {
            if (Player2.getVar("IsTrying") != null) {
                return false;
            }
            DressMeShieldData shield2 = DressMeShieldHolder.getInstance().getShield(Integer.parseInt(str2.split(" ")[0]));
            if (shield2 == null) {
                return false;
            }
            HashMap<Integer, Integer> hashMap3 = new HashMap<Integer, Integer>();
            hashMap3.put(7, shield2.W());
            Player2.sendPacket((IStaticPacket)new ShopPreviewInfo(hashMap3));
            ThreadPoolManager.getInstance().schedule((Runnable)new TryDressMeCustom(Player2, null), 5000L);
            Player2.setVar("IsTrying", 1, -1L);
            this.useVoicedCommand("dress-shieldpage", Player2, str2);
            return false;
        }
        if (str.equals("dress-tryhat")) {
            if (Player2.getVar("IsTrying") != null) {
                return false;
            }
            int parseInt7 = Integer.parseInt(str2.split(" ")[0]);
            DressMeHatData hat2 = DressMeHatHolder.getInstance().getHat(parseInt7);
            if (hat2 == null) {
                return false;
            }
            PcInventory inventory4 = Player2.getInventory();
            final ItemInstance Equiped_item = inventory4.getPaperdollItem(hat2.E());
            Player2.setVar("hairslotDressMeTry", Integer.valueOf(hat2.E()).intValue(), -1L);
            ItemTemplate itemTemplate = ItemHolder.getInstance().getTemplate(Integer.valueOf(hat2.F()).intValue());
            if (itemTemplate == null || !itemTemplate.isEquipable()) {
                return false;
            }
            int j = ItemFunctions.getPaperdollIndex((long)itemTemplate.getBodyPart());
            final int oldvisible = Equiped_item.getVisibleItemId();
            Equiped_item.setVisibleItemId(hat2.F());
            Player2.getInventory().refreshEquip();
            Player2.broadcastCharInfo();
            Player2.sendUserInfo(true);
            Player2.sendPacket((IStaticPacket)new ExUserInfoEquipSlot(Player2, InventorySlot.VALUES));
            Player2.sendPacket((IStaticPacket)new ExUserInfoEquipSlot(Player2, new InventorySlot[]{InventorySlot.HAIR, InventorySlot.HAIR2, InventorySlot.HEAD, InventorySlot.AGATHION1, InventorySlot.AGATHION2, InventorySlot.AGATHION3, InventorySlot.AGATHION4, InventorySlot.AGATHION5}));
            Player2.setVar("IsTrying", 1, -1L);
            ThreadPoolManager.getInstance().schedule((Runnable)new RunnableImpl(){

                public void runImpl() throws Exception {
                    Player2.unsetVar("IsTrying");
                    if (oldvisible != 0) {
                        Equiped_item.setVisibleItemId(oldvisible);
                    }
                    Player2.getInventory().refreshEquip();
                    B0.A(Player2);
                }
            }, 5000L);
            this.useVoicedCommand("dress-hatpage", Player2, str2);
            return false;
        }
        if (str.equals("dressme-enchant")) {
            String[] split8;
            String htm13 = HtmCache.getInstance().getNotNull(this.index_item_path, Player2);
            String htm14 = HtmCache.getInstance().getNotNull(this.template_item_path, Player2);
            String str15 = "";
            if (str2 == null) {
                str2 = "1";
            }
            int parseInt8 = (split8 = str2.split(" "))[0].length() > 0 ? Integer.parseInt(split8[0]) : 1;
            Player2.setVar("lastDressCmd", "dressme-enchant", -1L);
            int i31 = 0;
            int i32 = 0;
            List<DressMeEnchantData> orderL7 = HandleDressMeDb.orderL(Player2, A0.W, "character_dressme_enchant_list", "enchantId");
            int i33 = (parseInt8 - 1) * 9;
            while (i33 < orderL7.size()) {
                DressMeEnchantData dressMeEnchantData = orderL7.get(i33);
                if (dressMeEnchantData != null) {
                    String B8;
                    if (i31 % 3 == 0) {
                        str15 = String.valueOf(str15) + "<tr>";
                    }
                    if ((B8 = dressMeEnchantData.B()).length() > 20) {
                        B8 = String.valueOf(B8.substring(0, 20)) + "...";
                    }
                    String replace7 = htm14.replace("{bypass}", "bypass -h user_dress-enchant " + dressMeEnchantData.C()).replace("{bypassTry}", "bypass -h user_dress-tryenchant " + dressMeEnchantData.C()).replace("{name}", B8);
                    String is_using = Player2.getVar("customEnchantColor") != null && Player2.getVar("customEnchantColor") == String.valueOf(dressMeEnchantData.BP()) + "-" + dressMeEnchantData.LEVEL() ? "/Using" : "";
                    str15 = String.valueOf(str15) + (HandleDressMeDb.dressMeEnchantInside(Player2, dressMeEnchantData) || Player2.hasBonus() && GabConfig.PREMIUM_DRESS ? replace7.replace("{price}", "Owned" + is_using) : replace7.replace("{price}", B.A(Player2, dressMeEnchantData.D(), dressMeEnchantData.A()))).replace("{icon}", B.A(dressMeEnchantData.R()));
                    if (++i31 % 3 == 0 || i33 + 1 == orderL7.size()) {
                        str15 = String.valueOf(str15) + "</tr>";
                    }
                }
                if (++i32 >= 9) break;
                ++i33;
            }
            int ceil7 = (int)Math.ceil(orderL7.size() / 9);
            if (orderL7.size() % 9 > 0) {
                ++ceil7;
            }
            int i34 = 1;
            String str16 = "";
            int i35 = 1;
            while (i35 <= ceil7) {
                String string = str16 = i35 == parseInt8 ? String.valueOf(str16) + "<td width=25 align=center valign=top><button value=\"[" + i35 + "]\" action=\"bypass -h user_dressme-enchant " + i35 + "\" width=32 height=25 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>" : String.valueOf(str16) + "<td width=25 align=center valign=top><button value=\"" + i35 + "\" action=\"bypass -h user_dressme-enchant " + i35 + "\" width=32 height=25 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>";
                if (i34 == 14) {
                    str16 = String.valueOf(str16) + "</tr><tr>";
                    i34 = 0;
                }
                ++i34;
                ++i35;
            }
            if (str16.equals("")) {
                str16 = "<td width=30 align=center valign=top>...</td>";
            }
            utils.A.A(htm13.replace("{list}", str15).replace("{navigation}", str16), Player2);
            return true;
        }
        if (str.equals("dress-enchant")) {
            DressMeEnchantData enchant = DressMeEnchantHolder.getInstance().getEnchant(Integer.parseInt(str2.split(" ")[0]));
            if (HandleDressMeDb.dressMeEnchantInside(Player2, enchant) || Player2.hasBonus() && GabConfig.PREMIUM_DRESS) {
                B0.A(Player2, enchant.LEVEL(), enchant.BP(), false);
                Player2.sendMessage("Dress Was already in wardrobe! No price reduced!");
            } else if (Functions.getItemCount((Playable)Player2, (int)enchant.A()) >= enchant.D()) {
                if (ItemFunctions.removeItem((Playable)Player2, (int)enchant.A(), (long)enchant.D(), (boolean)true) == 0L || !HandleDressMeDb.insertDressMeEnchant(Player2, enchant)) {
                    Player2.sendMessage("Something went went wrong trying to send your dress to your wardrobe or we couldn't get the money from your inventory!");
                    return false;
                }
                Player2.sendMessage("Item bought with success!");
                B0.A(Player2, enchant.LEVEL(), enchant.BP(), false);
            } else {
                Player2.sendMessage("You do not have enough funds to purchase.");
            }
            GabrielCBB.getInstance().parseCommand("gab_dressme", Player2);
            return true;
        }
        if (str.equals("dress-tryenchant")) {
            if (Player2.getVar("IsTrying") != null) {
                return false;
            }
            int ide = Integer.parseInt(str2.split(" ")[0]);
            DressMeEnchantData enchant_data = DressMeEnchantHolder.getInstance().getEnchant(ide);
            ItemTemplate enchant = ItemHolder.getInstance().getTemplate(enchant_data.R());
            B0.A(Player2, enchant_data.LEVEL(), enchant_data.BP(), true);
            Player2.setVar("IsTrying", 1, -1L);
            ThreadPoolManager.getInstance().schedule((Runnable)new RunnableImpl(){

                public void runImpl() throws Exception {
                    Player2.unsetVar("IsTrying");
                    Player2.unsetVar("tryEnchantColor");
                    B0.A(Player2);
                    Player2.sendMessage("You are no longer trying enchant item");
                }
            }, 5000L);
            this.useVoicedCommand("dress-enchantpage", Player2, str2);
            return false;
        }
        if (!str.equals("dressme-agathion")) {
            if (!str.equals("dress-agathion")) {
                if (str.equals("dress-tryagathion")) {
                    if (Player2.getVar("IsTrying") != null) {
                        return false;
                    }
                    Player2.setVar("IsTrying", 1, -1L);
                    int agid = DressMeAgathionHolder.getInstance().getAgathion(Integer.parseInt(str2.split(" ")[0])).visual();
                    B0.B(Player2, agid, true);
                    ThreadPoolManager.getInstance().schedule((Runnable)new RunnableImpl(){

                        public void runImpl() throws Exception {
                            Player2.unsetVar("TryagathionCustom");
                            Player2.unsetVar("IsTrying");
                            B0.A(Player2);
                        }
                    }, 5000L);
                    this.useVoicedCommand("dress-agathionpage", Player2, str2);
                    return false;
                }
                if (str.equals("undressme-agathion")) {
                    B0.B(Player2, 0, false);
                    GabrielCBB.getInstance().parseCommand("gab_dressme", Player2);
                    return true;
                }
                if (str.equals("undressme-enchant")) {
                    if (Player2.getVar("customEnchantColor") != null) {
                        String[] edata = Player2.getVar("customEnchantColor").split("-");
                        B0.A(Player2, 0, Integer.parseInt(edata[0]), false);
                    }
                    GabrielCBB.getInstance().parseCommand("gab_dressme", Player2);
                    return true;
                }
                return false;
            }
            DressMeAgathionData agathion = DressMeAgathionHolder.getInstance().getAgathion(Integer.parseInt(str2.split(" ")[0]));
            if (!B0.B(agathion.visual())) {
                Player2.sendMessage("Non valid value entered! Contact the administration!");
                return false;
            }
            if (HandleDressMeDb.dressMeAgathionInside(Player2, agathion) || Player2.hasBonus() && GabConfig.PREMIUM_DRESS) {
                B0.B(Player2, agathion.visual(), false);
                Player2.sendMessage("Dress Was already in wardrobe! No price reduced!");
            } else if (Functions.getItemCount((Playable)Player2, (int)agathion.A()) >= agathion.D()) {
                if (ItemFunctions.removeItem((Playable)Player2, (int)agathion.A(), (long)agathion.D(), (boolean)true) == 0L || !HandleDressMeDb.insertDressMeAgathion(Player2, agathion)) {
                    Player2.sendMessage("Something went went wrong trying to send your dress to your wardrobe or we couldn't get the money from your inventory!");
                    return false;
                }
                B0.B(Player2, agathion.visual(), false);
                Player2.sendMessage("Item bought with success! You can get it again from your wardrobe!");
            } else {
                Player2.sendMessage("You do not have enough funds to purchase.");
            }
            GabrielCBB.getInstance().parseCommand("gab_dressme", Player2);
            return true;
        }
        if (Player2.getAgathionId() <= 0) {
            Player2.sendMessage("Error: Agathion must be invoked.");
            this.useVoicedCommand("dress-agathionpage", Player2, str2);
            return true;
        }
        String htm15 = HtmCache.getInstance().getNotNull(this.index_item_path, Player2);
        String htm16 = HtmCache.getInstance().getNotNull(this.template_item_path, Player2);
        String str17 = "";
        if (str2 == null) {
            str2 = "1";
        }
        int parseInt9 = (split9 = str2.split(" "))[0].length() > 0 ? Integer.parseInt(split9[0]) : 1;
        Player2.setVar("lastDressCmd", "dressme-agathion", -1L);
        int i36 = 0;
        int i37 = 0;
        List<DressMeAgathionData> orderL8 = HandleDressMeDb.orderL(Player2, A0.b, "character_dressme_agathion_list", "agathionId");
        int i38 = (parseInt9 - 1) * 9;
        while (i38 < orderL8.size()) {
            DressMeAgathionData dressMeAgathionData = orderL8.get(i38);
            if (dressMeAgathionData != null) {
                String B9;
                if (i36 % 3 == 0) {
                    str17 = String.valueOf(str17) + "<tr>";
                }
                if ((B9 = dressMeAgathionData.B()).length() > 20) {
                    B9 = String.valueOf(B9.substring(0, 20)) + "...";
                }
                String is_using = Player2.getVar("agathionCustom") != null && Integer.parseInt(Player2.getVar("agathionCustom")) == dressMeAgathionData.visual() ? "/Using" : "";
                String replace8 = htm16.replace("{bypass}", "bypass -h user_dress-agathion " + dressMeAgathionData.C()).replace("{bypassTry}", "bypass -h user_dress-tryagathion " + dressMeAgathionData.C()).replace("{name}", B9);
                str17 = String.valueOf(str17) + (HandleDressMeDb.dressMeAgathionInside(Player2, dressMeAgathionData) || Player2.hasBonus() && GabConfig.PREMIUM_DRESS ? replace8.replace("{price}", "Owned" + is_using) : replace8.replace("{price}", B.A(Player2, dressMeAgathionData.D(), dressMeAgathionData.A()))).replace("{icon}", B.A(dressMeAgathionData.O()));
                if (++i36 % 3 == 0 || i38 + 1 == orderL8.size()) {
                    str17 = String.valueOf(str17) + "</tr>";
                }
            }
            if (++i37 >= 9) break;
            ++i38;
        }
        int ceil8 = (int)Math.ceil(orderL8.size() / 9);
        if (orderL8.size() % 9 > 0) {
            ++ceil8;
        }
        int i39 = 1;
        String str18 = "";
        int i40 = 1;
        while (i40 <= ceil8) {
            String string = str18 = i40 == parseInt9 ? String.valueOf(str18) + "<td width=25 align=center valign=top><button value=\"[" + i40 + "]\" action=\"bypass -h user_dressme-agathion " + i40 + "\" width=32 height=25 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>" : String.valueOf(str18) + "<td width=25 align=center valign=top><button value=\"" + i40 + "\" action=\"bypass -h user_dressme-agathion " + i40 + "\" width=32 height=25 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\"></td>";
            if (i39 == 14) {
                str18 = String.valueOf(str18) + "</tr><tr>";
                i39 = 0;
            }
            ++i39;
            ++i40;
        }
        if (str18.equals("")) {
            str18 = "<td width=30 align=center valign=top>...</td>";
        }
        utils.A.A(htm15.replace("{list}", str17).replace("{navigation}", str18), Player2);
        return true;
    }

    private boolean A(DressMeWeaponData dressMeWeaponData) {
        String S = dressMeWeaponData.S();
        boolean z = false;
        switch (S.hashCode()) {
            case -1686339360: {
                if (!S.equals("DUALFIST") && !S.equals("DUAL")) break;
                z = true;
                break;
            }
            case -1434468602: {
                if (!S.equals("DAGGER") && !S.equals("DUAL")) break;
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
                z = true;
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
                break;
            }
            case -772105336: {
                if (!S.equals("DUALFIST")) break;
                z = true;
                break;
            }
            case 1846957369: {
                if (!S.equals("DUALSWORD")) break;
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

    private static class RemoveWearItemsTask
    extends RunnableImpl {
        private Player Ai;

        public RemoveWearItemsTask(Player param1Player) {
            this.Ai = param1Player;
        }

        public void runImpl() throws Exception {
            this.Ai.sendMessage("Trying mode ended.");
            this.Ai.sendUserInfo(true);
            this.Ai.sendPacket((IStaticPacket)new ExUserInfoEquipSlot(this.Ai, InventorySlot.VALUES));
            this.Ai.sendPacket((IStaticPacket)new ExUserInfoEquipSlot(this.Ai, new InventorySlot[]{InventorySlot.HAIR, InventorySlot.HAIR2, InventorySlot.HEAD, InventorySlot.AGATHION1, InventorySlot.AGATHION2, InventorySlot.AGATHION3, InventorySlot.AGATHION4, InventorySlot.AGATHION5}));
        }
    }
}
