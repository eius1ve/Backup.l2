/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 */
package l2.gameserver.handler.telegram.impl;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import l2.gameserver.handler.telegram.ITelegramCommandHandler;
import l2.gameserver.handler.telegram.TelegramApi;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.model.pledge.Clan;

public class ListOnlinePlayersCommand
implements ITelegramCommandHandler {
    private final String[] aa = new String[]{"/list_online"};
    private static final Gson e = new Gson();
    private static final int fD = 25;

    @Override
    public void handle(String string, String string2) throws Exception {
        Object object;
        Iterable<Player> iterable;
        String[] stringArray = string2 != null ? string2.split(" ") : new String[]{};
        int n = stringArray.length > 0 && stringArray[0].matches("\\d+") ? Integer.parseInt(stringArray[0]) : 1;
        ArrayList<Player> arrayList = new ArrayList<Player>();
        for (Player player : GameObjectsStorage.getAllPlayersForIterate()) {
            arrayList.add(player);
        }
        int n2 = (int)Math.ceil((double)arrayList.size() / 25.0);
        n = Math.max(1, Math.min(n, n2));
        int n3 = (n - 1) * 25;
        int n4 = Math.min(n3 + 25, arrayList.size());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Online Players (Page ").append(n).append(" of ").append(n2).append("):\n");
        for (int i = n3; i < n4; ++i) {
            iterable = (Player)arrayList.get(i);
            stringBuilder.append(((Creature)((Object)iterable)).getName()).append(" - ").append(" ID: ").append(((GameObject)((Object)iterable)).getObjectId());
            object = ((Player)iterable).getClan();
            if (object != null) {
                stringBuilder.append(" [").append(((Clan)object).getName()).append("]");
            }
            stringBuilder.append("\n");
        }
        ArrayList<List<InlineKeyboardButton>> arrayList2 = new ArrayList<List<InlineKeyboardButton>>();
        iterable = new ArrayList();
        if (n > 1) {
            iterable.add((InlineKeyboardButton)new InlineKeyboardButton("Previous", "/list_online " + (n - 1)));
        }
        if (n < n2) {
            iterable.add((InlineKeyboardButton)new InlineKeyboardButton("Next", "/list_online " + (n + 1)));
        }
        if (!iterable.isEmpty()) {
            arrayList2.add((List<InlineKeyboardButton>)iterable);
        }
        object = new InlineKeyboardMarkup(arrayList2);
        String string3 = e.toJson(object);
        TelegramApi.sendMessage(string, stringBuilder.toString(), string3);
    }

    @Override
    public String[] getCommands() {
        return this.aa;
    }

    @Override
    public boolean requiresParams() {
        return false;
    }

    private static class InlineKeyboardButton {
        private final String bV;
        private final String bW;

        public InlineKeyboardButton(String string, String string2) {
            this.bV = string;
            this.bW = string2;
        }
    }

    private static class InlineKeyboardMarkup {
        private final List<List<InlineKeyboardButton>> as;

        public InlineKeyboardMarkup(List<List<InlineKeyboardButton>> list) {
            this.as = list;
        }
    }
}
