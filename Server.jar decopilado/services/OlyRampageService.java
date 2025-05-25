/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.listener.Listener
 *  l2.gameserver.Announcements
 *  l2.gameserver.Config
 *  l2.gameserver.data.xml.holder.ItemHolder
 *  l2.gameserver.listener.actor.player.OnOlyCompetitionListener
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.actor.listener.CharListenerList
 *  l2.gameserver.model.entity.oly.Competition
 *  l2.gameserver.network.l2.components.ChatType
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.templates.item.ItemTemplate
 *  l2.gameserver.utils.ItemFunctions
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.lang3.tuple.Pair
 */
package services;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import l2.commons.listener.Listener;
import l2.gameserver.Announcements;
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.listener.actor.player.OnOlyCompetitionListener;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.actor.listener.CharListenerList;
import l2.gameserver.model.entity.oly.Competition;
import l2.gameserver.network.l2.components.ChatType;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.utils.ItemFunctions;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

public class OlyRampageService
extends Functions
implements OnOlyCompetitionListener,
ScriptFile {
    private static final String hG = "oly_rampage_counter";
    private static boolean dR = false;
    private static List<Pair<Integer, String>> dS;
    private static List<Pair<Integer, Pair<ItemTemplate, Long>>> dT;

    public void onLoad() {
        if (Config.OLYMPIAD_WINNER_ANNOUCE) {
            dR = true;
            ArrayList<Pair<Integer, String>> arrayList = new ArrayList<Pair<Integer, String>>();
            StringTokenizer stringTokenizer = new StringTokenizer(Config.OLYMPIAD_WINNER_MESSAGE, ";");
            while (stringTokenizer.hasMoreTokens()) {
                String string = stringTokenizer.nextToken().trim();
                int n = string.indexOf(58);
                if (n < 0) {
                    throw new RuntimeException("Can't parse \"" + string + "\"");
                }
                arrayList.add((Pair<Integer, String>)Pair.of((Object)Integer.parseInt(string.substring(0, n).trim()), (Object)string.substring(n + 1).trim()));
            }
            dS = arrayList;
            this.cm();
            CharListenerList.addGlobal((Listener)this);
        } else {
            dR = false;
        }
    }

    public void onReload() {
        this.onShutdown();
        this.onLoad();
    }

    public void onShutdown() {
        CharListenerList.removeGlobal((Listener)this);
    }

    public void onOlyCompetitionCompleted(Player player, Competition competition, boolean bl) {
        if (!dR || player == null || competition == null) {
            return;
        }
        int n = player.getVarInt(hG, 0);
        if (!bl) {
            n = 0;
            player.setVar(hG, n, -1L);
        } else {
            ++n;
            for (Pair<Integer, String> pair : dS) {
                if ((Integer)pair.getLeft() != n) continue;
                Announcements.getInstance().announceByCustomMessage((String)pair.getRight(), new String[]{player.getName()}, ChatType.ANNOUNCEMENT);
            }
            if (dT != null) {
                for (Pair<Integer, String> pair : dT) {
                    if ((Integer)pair.getLeft() != n) continue;
                    ItemFunctions.addItem((Playable)player, (ItemTemplate)((ItemTemplate)((Pair)pair.getValue()).getLeft()), (long)((Long)((Pair)pair.getValue()).getRight()), (boolean)true);
                }
            }
            player.setVar(hG, n, -1L);
        }
    }

    private void cm() {
        ArrayList<Pair<Integer, Pair<ItemTemplate, Long>>> arrayList = new ArrayList<Pair<Integer, Pair<ItemTemplate, Long>>>();
        String string = StringUtils.trimToEmpty((String)Config.OLYMPIAD_WINNER_REWARDS);
        if (string.isEmpty()) {
            return;
        }
        StringTokenizer stringTokenizer = new StringTokenizer(string, ";");
        while (stringTokenizer.hasMoreTokens()) {
            String string2 = stringTokenizer.nextToken().trim();
            int n = string2.indexOf(58);
            int n2 = string2.indexOf(58, n + 1);
            if (n < 0 || n2 < 0) {
                throw new RuntimeException("Can't parse reward config: \"" + string2 + "\"");
            }
            int n3 = Integer.parseInt(string2.substring(0, n).trim());
            ItemTemplate itemTemplate = ItemHolder.getInstance().getTemplate(Integer.parseInt(string2.substring(n + 1, n2).trim()));
            long l = Long.parseLong(string2.substring(n2 + 1).trim());
            arrayList.add((Pair<Integer, Pair<ItemTemplate, Long>>)Pair.of((Object)n3, (Object)Pair.of((Object)itemTemplate, (Object)l)));
        }
        dT = arrayList;
    }
}
