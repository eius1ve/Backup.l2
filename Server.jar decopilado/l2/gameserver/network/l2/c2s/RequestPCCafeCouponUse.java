/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.data.xml.holder.PromoCodeHolder;
import l2.gameserver.model.Player;
import l2.gameserver.model.PromoCode;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExShowScreenMessage;
import l2.gameserver.network.l2.s2c.MagicSkillUse;
import l2.gameserver.network.l2.s2c.ShowPCCafeCouponShowUI;
import l2.gameserver.network.l2.s2c.SystemMessage;
import org.apache.commons.lang3.StringUtils;

public class RequestPCCafeCouponUse
extends L2GameClientPacket {
    private String er;

    @Override
    protected void readImpl() {
        this.er = this.readS();
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        if (this.er.length() == 20) {
            ExShowScreenMessage exShowScreenMessage;
            String string = StringUtils.trimToEmpty((String)this.er).toUpperCase();
            if (string.isEmpty()) {
                return;
            }
            PromoCode promoCode = PromoCodeHolder.getInstance().getPromoCode(string);
            PromoCodeHolder.ActivateResult activateResult = PromoCodeHolder.getInstance().tryActivate(player, promoCode);
            switch (activateResult) {
                case ALREADY: {
                    exShowScreenMessage = new ExShowScreenMessage(new CustomMessage("services.promo.code.already.new", player, new Object[0]).toString(), 10000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true);
                    break;
                }
                case NOT_FOR_YOU: {
                    exShowScreenMessage = new ExShowScreenMessage(new CustomMessage("services.promo.code.not.for.you.new", player, new Object[0]).toString(), 10000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true);
                    break;
                }
                case OK: {
                    exShowScreenMessage = new ExShowScreenMessage(new CustomMessage("services.promo.code.ok.new", player, new Object[0]).toString(), 10000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true);
                    player.broadcastPacket(new MagicSkillUse(player, player, 6234, 1, 1000, 0L));
                    break;
                }
                case OUT_OF_DATE: {
                    exShowScreenMessage = new ExShowScreenMessage(new CustomMessage("services.promo.code.out.of.date.new", player, new Object[0]).toString(), 10000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true);
                    break;
                }
                case OUT_OF_LIMIT: {
                    exShowScreenMessage = new ExShowScreenMessage(new CustomMessage("services.promo.code.out.of.limit.new", player, new Object[0]).toString(), 10000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true);
                    break;
                }
                case HWID_LIMITED: {
                    exShowScreenMessage = new ExShowScreenMessage(new CustomMessage("services.promo.code.hwid.limit.new", player, new Object[0]).toString(), 10000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true);
                    break;
                }
                case IP_LIMITED: {
                    exShowScreenMessage = new ExShowScreenMessage(new CustomMessage("services.promo.code.ip.limit.new", player, new Object[0]).toString(), 10000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true);
                    break;
                }
                case MIN_LEVEL_LIMITED: {
                    exShowScreenMessage = new ExShowScreenMessage(new CustomMessage("services.promo.minLevel.new", player, new Object[0]).addNumber(promoCode.getMinLevel()).toString(), 10000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true);
                    break;
                }
                case MAX_LEVEL_LIMITED: {
                    exShowScreenMessage = new ExShowScreenMessage(new CustomMessage("services.promo.maxLevel.new", player, new Object[0]).addNumber(promoCode.getMaxLevel()).toString(), 10000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true);
                    break;
                }
                default: {
                    exShowScreenMessage = new ExShowScreenMessage(new CustomMessage("services.promo.code.invalid.try.later.new", player, new Object[0]).addString(string).toString(), 10000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true);
                }
            }
            if (activateResult != PromoCodeHolder.ActivateResult.OK && activateResult != PromoCodeHolder.ActivateResult.ALREADY) {
                player.sendPacket((IStaticPacket)new ShowPCCafeCouponShowUI());
            }
            player.sendPacket((IStaticPacket)exShowScreenMessage);
        } else {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.ENTER_THE_PA_COUPON_SERIAL_NUMBER));
        }
    }
}
