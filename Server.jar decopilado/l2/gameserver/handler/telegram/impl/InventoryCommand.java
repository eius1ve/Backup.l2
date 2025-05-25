/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  org.apache.commons.lang3.StringUtils
 */
package l2.gameserver.handler.telegram.impl;

import com.google.gson.Gson;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import l2.gameserver.handler.telegram.ITelegramCommandHandler;
import l2.gameserver.handler.telegram.TelegramApi;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.items.ItemInstance;
import org.apache.commons.lang3.StringUtils;

public class InventoryCommand
implements ITelegramCommandHandler {
    private static final Gson b = new Gson();
    private final String[] W = new String[]{"/inventory"};

    @Override
    public void handle(String string, String string2) throws Exception {
        Serializable serializable;
        String[] stringArray;
        int n = 1;
        int n2 = 50;
        String string3 = null;
        if (string2 != null && !string2.trim().isEmpty()) {
            stringArray = string2.split(" ");
            string3 = stringArray[0];
            if (stringArray.length > 1 && StringUtils.isNumeric((CharSequence)stringArray[1])) {
                n = Integer.parseInt(stringArray[1]);
            }
            if (stringArray.length > 2 && StringUtils.isNumeric((CharSequence)stringArray[2])) {
                n2 = Integer.parseInt(stringArray[2]);
            }
        }
        if (string3 == null) {
            TelegramApi.sendForceReplyMessage(string, "Usage: /inventory <name> [page] [pageSize]", "Enter player name to show inventory");
            return;
        }
        stringArray = GameObjectsStorage.getPlayer(string3);
        if (stringArray == null) {
            TelegramApi.sendMessage(string, "Player not found.");
            return;
        }
        ItemInstance[] itemInstanceArray = stringArray.getInventory().getItems();
        if (itemInstanceArray.length == 0) {
            TelegramApi.sendMessage(string, "Inventory is empty.");
            return;
        }
        int n3 = itemInstanceArray.length;
        int n4 = (int)Math.ceil((double)n3 / (double)n2);
        if (n > n4) {
            n = n4;
        }
        if (n < 1) {
            n = 1;
        }
        int n5 = (n - 1) * n2;
        int n6 = Math.min(n5 + n2, n3);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Inventory of ").append(stringArray.getName()).append(" (Page ").append(n).append(" of ").append(n4).append("):\n");
        for (int i = n5; i < n6; ++i) {
            serializable = itemInstanceArray[i];
            stringBuilder.append(((ItemInstance)serializable).getItemId()).append(" - ").append(((ItemInstance)serializable).getName()).append(": ").append(((ItemInstance)serializable).getCount()).append("\n");
        }
        ArrayList<List<InlineKeyboardButton>> arrayList = new ArrayList<List<InlineKeyboardButton>>();
        serializable = new ArrayList();
        if (n > 1) {
            serializable.add(new InlineKeyboardButton("Previous", "/inventory " + string3 + " " + (n - 1) + " " + n2));
        }
        if (n < n4) {
            serializable.add(new InlineKeyboardButton("Next", "/inventory " + string3 + " " + (n + 1) + " " + n2));
        }
        if (!serializable.isEmpty()) {
            arrayList.add((List<InlineKeyboardButton>)((Object)serializable));
        }
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup(arrayList);
        String string4 = b.toJson((Object)inlineKeyboardMarkup);
        TelegramApi.sendMessage(string, stringBuilder.toString(), string4);
    }

    @Override
    public String[] getCommands() {
        return this.W;
    }

    @Override
    public boolean requiresParams() {
        return true;
    }

    private static class InlineKeyboardButton {
        private final String text;
        private final String bR;

        public InlineKeyboardButton(String string, String string2) {
            this.text = string;
            this.bR = string2;
        }
    }

    private static class InlineKeyboardMarkup {
        private final List<List<InlineKeyboardButton>> aq;

        public InlineKeyboardMarkup(List<List<InlineKeyboardButton>> list) {
            this.aq = list;
        }
    }
}
