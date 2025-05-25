/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.handler.bbs;

import java.util.HashMap;
import java.util.Map;
import l2.gameserver.Config;
import l2.gameserver.handler.bbs.ICommunityBoardHandler;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.templates.StatsSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class CommunityBoardManager {
    private static final Logger bf = LoggerFactory.getLogger(CommunityBoardManager.class);
    private static final CommunityBoardManager a = new CommunityBoardManager();
    private final Map<String, ICommunityBoardHandler> ae = new HashMap<String, ICommunityBoardHandler>();
    private final StatsSet a = new StatsSet();

    public static CommunityBoardManager getInstance() {
        return a;
    }

    private CommunityBoardManager() {
    }

    public void registerHandler(ICommunityBoardHandler iCommunityBoardHandler) {
        for (String string : iCommunityBoardHandler.getBypassCommands()) {
            if (this.ae.containsKey(string)) {
                bf.warn("CommunityBoard: dublicate bypass registered! First handler: " + this.ae.get(string).getClass().getSimpleName() + " second: " + iCommunityBoardHandler.getClass().getSimpleName());
            }
            this.ae.put(string, iCommunityBoardHandler);
        }
    }

    public void removeHandler(ICommunityBoardHandler iCommunityBoardHandler) {
        for (String string : iCommunityBoardHandler.getBypassCommands()) {
            this.ae.remove(string);
        }
        bf.info("CommunityBoard: " + iCommunityBoardHandler.getClass().getSimpleName() + " unloaded.");
    }

    public ICommunityBoardHandler getCommunityHandler(String string, Player player) {
        if (!Config.COMMUNITYBOARD_ENABLED || this.ae.isEmpty()) {
            return null;
        }
        if (player != null && Config.BBS_PEACE_ONLY && !player.isInZonePeace()) {
            player.sendMessage(new CustomMessage("alt.bbs_available_at_town_only", player, new Object[0]));
            return null;
        }
        for (Map.Entry<String, ICommunityBoardHandler> entry : this.ae.entrySet()) {
            if (!string.contains(entry.getKey())) continue;
            return entry.getValue();
        }
        return null;
    }

    public void setProperty(String string, String string2) {
        this.a.set(string, string2);
    }

    public void setProperty(String string, int n) {
        this.a.set(string, n);
    }

    public int getIntProperty(String string) {
        return this.a.getInteger(string, 0);
    }
}
