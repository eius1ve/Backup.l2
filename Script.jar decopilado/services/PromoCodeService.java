/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.Config
 *  l2.gameserver.GameServer
 *  l2.gameserver.data.StringHolder
 *  l2.gameserver.data.htm.HtmCache
 *  l2.gameserver.data.xml.holder.PromoCodeHolder
 *  l2.gameserver.data.xml.holder.PromoCodeHolder$ActivateResult
 *  l2.gameserver.data.xml.parser.PromoCodeParser
 *  l2.gameserver.database.DatabaseFactory
 *  l2.gameserver.handler.voicecommands.IVoicedCommandHandler
 *  l2.gameserver.handler.voicecommands.VoicedCommandHandler
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.PromoCode
 *  l2.gameserver.model.promoCode.AddLevelPromoCodeReward
 *  l2.gameserver.model.promoCode.ExpPromoCodeReward
 *  l2.gameserver.model.promoCode.ItemPromoCodeReward
 *  l2.gameserver.model.promoCode.PremiumPromoCodeReward
 *  l2.gameserver.model.promoCode.PromoCodeReward
 *  l2.gameserver.model.promoCode.SetLevelPromoCodeReward
 *  l2.gameserver.model.promoCode.SpPromoCodeReward
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.network.l2.s2c.MagicSkillUse
 *  l2.gameserver.network.l2.s2c.NpcHtmlMessage
 *  l2.gameserver.network.l2.s2c.ShowPCCafeCouponShowUI
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.utils.Util
 *  org.apache.commons.lang3.ArrayUtils
 *  org.apache.commons.lang3.StringUtils
 */
package services;

import java.sql.Connection;
import l2.gameserver.Config;
import l2.gameserver.GameServer;
import l2.gameserver.data.StringHolder;
import l2.gameserver.data.htm.HtmCache;
import l2.gameserver.data.xml.holder.PromoCodeHolder;
import l2.gameserver.data.xml.parser.PromoCodeParser;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.handler.voicecommands.IVoicedCommandHandler;
import l2.gameserver.handler.voicecommands.VoicedCommandHandler;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.PromoCode;
import l2.gameserver.model.promoCode.AddLevelPromoCodeReward;
import l2.gameserver.model.promoCode.ExpPromoCodeReward;
import l2.gameserver.model.promoCode.ItemPromoCodeReward;
import l2.gameserver.model.promoCode.PremiumPromoCodeReward;
import l2.gameserver.model.promoCode.PromoCodeReward;
import l2.gameserver.model.promoCode.SetLevelPromoCodeReward;
import l2.gameserver.model.promoCode.SpPromoCodeReward;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.MagicSkillUse;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.network.l2.s2c.ShowPCCafeCouponShowUI;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.utils.Util;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class PromoCodeService
extends Functions
implements IVoicedCommandHandler,
ScriptFile {
    private String[] o = new String[]{"promo", "promocode"};

    public boolean useVoicedCommand(String string, Player player, String string2) {
        if (!Config.SERVICE_PROMOCODE_COMMAND_ENABLED) {
            return false;
        }
        if (this.o[0].equalsIgnoreCase(string) || this.o[1].equalsIgnoreCase(string)) {
            this.showPromo(player);
            return true;
        }
        return false;
    }

    public String[] getVoicedCommandList() {
        if (!Config.SERVICE_PROMOCODE_COMMAND_ENABLED) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        return this.o;
    }

    public void showPromo(Player player) {
        if (player == null) {
            return;
        }
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, null);
        npcHtmlMessage.setFile("scripts/services/promo/code.htm");
        player.sendPacket((IStaticPacket)(Config.SERVICE_PROMOCODE_NEW_SYSTEM ? new ShowPCCafeCouponShowUI() : npcHtmlMessage));
    }

    public void showPromo() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, null);
        npcHtmlMessage.setFile("scripts/services/promo/code.htm");
        player.sendPacket((IStaticPacket)(Config.SERVICE_PROMOCODE_NEW_SYSTEM ? new ShowPCCafeCouponShowUI() : npcHtmlMessage));
    }

    public void promo() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, null);
        npcHtmlMessage.setFile("scripts/services/promo/code_enter.htm");
        player.sendPacket((IStaticPacket)(Config.SERVICE_PROMOCODE_NEW_SYSTEM ? new ShowPCCafeCouponShowUI() : npcHtmlMessage));
    }

    public void promo(String[] stringArray) {
        CustomMessage customMessage;
        Player player = this.getSelf();
        if (player == null || stringArray.length < 1) {
            return;
        }
        if (!Config.SERVICE_PROMOCODE_ENABLED) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/service_disabled.htm"));
            return;
        }
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, null);
        npcHtmlMessage.setFile("scripts/services/promo/code_result.htm");
        String string = StringUtils.trimToEmpty((String)stringArray[0]).toUpperCase();
        if (string.isEmpty()) {
            return;
        }
        PromoCode promoCode = PromoCodeHolder.getInstance().getPromoCode(string);
        PromoCodeHolder.ActivateResult activateResult = PromoCodeHolder.getInstance().tryActivate(player, promoCode);
        switch (activateResult) {
            case ALREADY: {
                customMessage = new CustomMessage("services.promo.code.already", player, new Object[0]);
                break;
            }
            case NOT_FOR_YOU: {
                customMessage = new CustomMessage("services.promo.code.not.for.you", player, new Object[0]);
                break;
            }
            case OK: {
                customMessage = new CustomMessage("services.promo.code.ok", player, new Object[0]);
                this.a(string, player);
                player.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)player, (Creature)player, 6234, 1, 1000, 0L)});
                break;
            }
            case OUT_OF_DATE: {
                customMessage = new CustomMessage("services.promo.code.out.of.date", player, new Object[0]);
                break;
            }
            case OUT_OF_LIMIT: {
                customMessage = new CustomMessage("services.promo.code.out.of.limit", player, new Object[0]);
                break;
            }
            case HWID_LIMITED: {
                customMessage = new CustomMessage("services.promo.code.hwid.limit", player, new Object[0]);
                break;
            }
            case IP_LIMITED: {
                customMessage = new CustomMessage("services.promo.code.ip.limit", player, new Object[0]);
                break;
            }
            case MIN_LEVEL_LIMITED: {
                customMessage = new CustomMessage("services.promo.minLevel", player, new Object[0]).addNumber((long)promoCode.getMinLevel());
                break;
            }
            case MAX_LEVEL_LIMITED: {
                customMessage = new CustomMessage("services.promo.maxLevel", player, new Object[0]).addNumber((long)promoCode.getMaxLevel());
                break;
            }
            default: {
                customMessage = new CustomMessage("services.promo.code.invalid.try.later", player, new Object[0]).addString(string);
            }
        }
        npcHtmlMessage = npcHtmlMessage.replace("%result%", String.valueOf(customMessage));
        if (activateResult == PromoCodeHolder.ActivateResult.OK) {
            this.a(string, player);
        } else {
            player.sendPacket((IStaticPacket)npcHtmlMessage);
        }
    }

    private void a(String string, Player player) {
        PromoCode promoCode = PromoCodeHolder.getInstance().getPromoCode(string);
        if (promoCode == null) {
            return;
        }
        String string2 = HtmCache.getInstance().getNotNull("scripts/services/promo/index.htm", player);
        String string3 = HtmCache.getInstance().getNotNull("scripts/services/promo/box.htm", player);
        Object object = "";
        for (PromoCodeReward promoCodeReward : promoCode.getRewards()) {
            String string4;
            ItemPromoCodeReward itemPromoCodeReward;
            if (promoCodeReward instanceof ItemPromoCodeReward) {
                itemPromoCodeReward = (ItemPromoCodeReward)promoCodeReward;
                string4 = string3;
                string4 = string4.replace("%icon%", Util.getItemIcon((int)itemPromoCodeReward._itemId));
                string4 = string4.replace("%data%", new CustomMessage("promo.box.item", player, new Object[0]).addItemName(itemPromoCodeReward._itemId).addNumber((long)itemPromoCodeReward._itemCount).toString());
                object = (String)object + string4;
                continue;
            }
            if (promoCodeReward instanceof SpPromoCodeReward) {
                itemPromoCodeReward = (SpPromoCodeReward)promoCodeReward;
                string4 = string3;
                string4 = string4.replace("%icon%", StringHolder.getInstance().getNotNull(player, "promo.icon.sp"));
                string4 = string4.replace("%data%", new CustomMessage("promo.box.sp", player, new Object[0]).addString(Util.formatAdena((long)itemPromoCodeReward._value)).toString());
                object = (String)object + string4;
                continue;
            }
            if (promoCodeReward instanceof ExpPromoCodeReward) {
                itemPromoCodeReward = (ExpPromoCodeReward)promoCodeReward;
                string4 = string3;
                string4 = string4.replace("%icon%", StringHolder.getInstance().getNotNull(player, "promo.icon.exp"));
                string4 = string4.replace("%data%", new CustomMessage("promo.box.exp", player, new Object[0]).addString(Util.formatAdena((long)itemPromoCodeReward._value)).toString());
                object = (String)object + string4;
                continue;
            }
            if (promoCodeReward instanceof SetLevelPromoCodeReward) {
                itemPromoCodeReward = (SetLevelPromoCodeReward)promoCodeReward;
                string4 = string3;
                string4 = string4.replace("%icon%", StringHolder.getInstance().getNotNull(player, "promo.icon.setLevel"));
                string4 = string4.replace("%data%", new CustomMessage("promo.box.level", player, new Object[0]).addNumber((long)itemPromoCodeReward._level).toString());
                object = (String)object + string4;
                continue;
            }
            if (promoCodeReward instanceof AddLevelPromoCodeReward) {
                itemPromoCodeReward = (AddLevelPromoCodeReward)promoCodeReward;
                string4 = string3;
                string4 = string4.replace("%icon%", StringHolder.getInstance().getNotNull(player, "promo.icon.addLevel"));
                string4 = string4.replace("%data%", new CustomMessage("promo.box.level", player, new Object[0]).addNumber((long)itemPromoCodeReward._level).toString());
                object = (String)object + string4;
                continue;
            }
            if (!(promoCodeReward instanceof PremiumPromoCodeReward)) continue;
            itemPromoCodeReward = (PremiumPromoCodeReward)promoCodeReward;
            string4 = string3;
            string4 = string4.replace("%icon%", StringHolder.getInstance().getNotNull(player, "promo.icon.premium"));
            string4 = string4.replace("%data%", new CustomMessage("promo.box.premium", player, new Object[0]).addNumber((long)itemPromoCodeReward._premiumId).toString());
            object = (String)object + string4;
        }
        string2 = string2.replace("%body%", (CharSequence)object);
        string2 = string2.replace("%code%", string);
        Functions.show((String)string2, (Player)player, null, (Object[])new Object[0]);
    }

    private static void a() {
        try (Connection connection = DatabaseFactory.getInstance().getConnection();){
            GameServer.getInstance().getDbmsStructureSynchronizer(connection).synchronize(new String[]{"promocode"});
            if (Config.SERVICE_PROMOCODE_ENABLED) {
                PromoCodeParser.getInstance().load();
            }
        }
        catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public void onLoad() {
        if (Config.SERVICE_PROMOCODE_ENABLED) {
            PromoCodeService.a();
        }
        if (Config.SERVICE_PROMOCODE_COMMAND_ENABLED) {
            VoicedCommandHandler.getInstance().registerVoicedCommandHandler((IVoicedCommandHandler)this);
        }
    }

    public void onReload() {
    }

    public void onShutdown() {
    }
}
