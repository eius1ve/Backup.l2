/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.listener.EventListener
 *  l2.commons.util.Rnd
 *  l2.gameserver.Announcements
 *  l2.gameserver.Config
 *  l2.gameserver.GameServer
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.pledge.Clan
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.utils.ItemFunctions
 *  org.apache.commons.lang3.ArrayUtils
 */
package services;

import l2.commons.listener.EventListener;
import l2.commons.util.Rnd;
import l2.gameserver.Announcements;
import l2.gameserver.Config;
import l2.gameserver.GameServer;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.utils.ItemFunctions;
import org.apache.commons.lang3.ArrayUtils;

public class HeroCustomServices
extends Functions
implements EventListener,
ScriptFile {
    private static final String hy = "activateHero";
    private static final String[] aX = new String[]{"activateHero"};

    public String[] listeningEventTypes() {
        return aX;
    }

    public void onEvent(String string, Object ... objectArray) {
        if (string.equals(hy)) {
            this.at((Player)objectArray[0]);
        }
    }

    private void at(Player player) {
        player.unsetVar("CustomHeroEndTime");
        player.unsetVar("inactiveHeroRewarded");
        if (player.getClan() != null) {
            Clan clan = player.getClan();
            clan.setCustomPoints(clan.getCustomPoints() + Config.EVENT_CLAN_HERO_POINTS);
            if (Config.SERVICES_TOP_CLANS_STATISTIC_ANNOUNCE) {
                Announcements.getInstance().announceByCustomMessage("scripts.services.CustomClanPointsHero", new String[]{String.valueOf(clan.getName()), String.valueOf(player.getName()), String.valueOf(Config.EVENT_CLAN_HERO_POINTS)});
            }
        }
        if (!ArrayUtils.isEmpty((int[])Config.OLY_HERO_CUSTOM_REWARD_AMOUNT) && Config.OLY_HERO_CUSTOM_REWARD_AMOUNT.length == Config.OLY_HERO_CUSTOM_REWARD_ID.length && Config.OLY_HERO_CUSTOM_REWARD_CHANCE.length == Config.OLY_HERO_CUSTOM_REWARD_ID.length) {
            for (int i = 0; i < Config.OLY_HERO_CUSTOM_REWARD_ID.length; ++i) {
                if (Config.OLY_HERO_CUSTOM_REWARD_ID[i] == 0 || Config.OLY_HERO_CUSTOM_REWARD_AMOUNT[i] <= 0 || !Rnd.chance((int)Config.OLY_HERO_CUSTOM_REWARD_CHANCE[i])) continue;
                ItemFunctions.addItem((Playable)player, (int)Config.OLY_HERO_CUSTOM_REWARD_ID[i], (long)Config.OLY_HERO_CUSTOM_REWARD_AMOUNT[i], (boolean)true);
            }
        }
    }

    public void onLoad() {
        GameServer.getInstance().getListeners().addEventListener((EventListener)this);
    }

    public void onReload() {
    }

    public void onShutdown() {
    }
}
