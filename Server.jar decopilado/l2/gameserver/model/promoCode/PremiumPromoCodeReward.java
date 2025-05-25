/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.dom4j.Element
 */
package l2.gameserver.model.promoCode;

import l2.gameserver.Config;
import l2.gameserver.dao.AccountBonusDAO;
import l2.gameserver.model.Player;
import l2.gameserver.model.promoCode.PromoCodeReward;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.s2c.UserInfoType;
import l2.gameserver.utils.Log;
import org.dom4j.Element;

public class PremiumPromoCodeReward
extends PromoCodeReward {
    public int _premiumId;

    public PremiumPromoCodeReward(Element element) {
        this._premiumId = Integer.parseInt(element.attributeValue("id"));
    }

    @Override
    public void giveReward(Player player) {
        if (!Config.SERVICES_RATE_ENABLED) {
            player.sendMessage("Service Premium Account is Disabled");
            return;
        }
        Config.RateBonusInfo rateBonusInfo = null;
        for (Config.RateBonusInfo rateBonusInfo2 : Config.SERVICES_RATE_BONUS_INFO) {
            if (rateBonusInfo2.id != this._premiumId) continue;
            rateBonusInfo = rateBonusInfo2;
        }
        if (rateBonusInfo == null) {
            player.sendMessage("Undefined bonus!");
            return;
        }
        if (player.hasBonus()) {
            player.sendMessage(new CustomMessage("promo.box.already.premium", player, new Object[0]));
            return;
        }
        player.sendMessage(new CustomMessage("promo.box.premium", player, new Object[0]).addNumber(rateBonusInfo.id));
        AccountBonusDAO.getInstance().store(player.getAccountName(), rateBonusInfo.makeBonus());
        player.stopBonusTask();
        player.startBonusTask();
        if (player.getParty() != null) {
            player.getParty().recalculatePartyData();
        }
        player.broadcastUserInfo(true, new UserInfoType[0]);
        Log.add("Promo Code Bonus added " + player.getName() + "|" + player.getObjectId() + "|rate bonus|" + rateBonusInfo.id + "|" + rateBonusInfo.bonusTimeSeconds + "|", "services");
    }
}
