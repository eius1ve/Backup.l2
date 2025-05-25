/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.listener.Listener
 *  l2.gameserver.Config
 *  l2.gameserver.dao.AccountVariablesDAO
 *  l2.gameserver.data.xml.holder.PromoCodeHolder
 *  l2.gameserver.data.xml.holder.PromoCodeHolder$ActivateResult
 *  l2.gameserver.listener.actor.player.OnPlayerEnterListener
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.PromoCode
 *  l2.gameserver.model.actor.listener.CharListenerList
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 *  org.apache.commons.lang3.StringUtils
 */
package services;

import l2.commons.listener.Listener;
import l2.gameserver.Config;
import l2.gameserver.dao.AccountVariablesDAO;
import l2.gameserver.data.xml.holder.PromoCodeHolder;
import l2.gameserver.listener.actor.player.OnPlayerEnterListener;
import l2.gameserver.model.Player;
import l2.gameserver.model.PromoCode;
import l2.gameserver.model.actor.listener.CharListenerList;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import org.apache.commons.lang3.StringUtils;

public class StarterPremiumAccount
extends Functions
implements OnPlayerEnterListener,
ScriptFile {
    public void onPlayerEnter(Player player) {
        String string = AccountVariablesDAO.getInstance().select(player.getAccountName(), "@starting_premium");
        if (player.getOnlineTime() == 0L && StringUtils.isBlank((CharSequence)string)) {
            this.av(player);
        }
    }

    private void av(Player player) {
        PromoCode promoCode = PromoCodeHolder.getInstance().getPromoCode(Config.STARTING_PREMIUM_PROMO_NAME);
        if (promoCode == null) {
            return;
        }
        PromoCodeHolder.ActivateResult activateResult = PromoCodeHolder.getInstance().tryActivate(player, promoCode);
        if (activateResult == PromoCodeHolder.ActivateResult.OK) {
            AccountVariablesDAO.getInstance().insert(player.getAccountName(), "@starting_premium", (Object)player.getObjectId());
        }
    }

    public void onLoad() {
        if (Config.STARTING_PREMIUM) {
            CharListenerList.addGlobal((Listener)this);
        }
    }

    public void onReload() {
    }

    public void onShutdown() {
    }
}
