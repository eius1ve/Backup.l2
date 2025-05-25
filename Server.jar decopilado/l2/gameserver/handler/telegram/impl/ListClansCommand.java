/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  org.apache.commons.lang3.StringUtils
 */
package l2.gameserver.handler.telegram.impl;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import l2.gameserver.handler.telegram.ITelegramCommandHandler;
import l2.gameserver.handler.telegram.TelegramApi;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.model.pledge.UnitMember;
import l2.gameserver.tables.ClanTable;
import org.apache.commons.lang3.StringUtils;

public class ListClansCommand
implements ITelegramCommandHandler {
    private static final Gson d = new Gson();
    private final String[] Z = new String[]{"/list_clans"};

    @Override
    public void handle(String string, String string2) throws Exception {
        Iterable<UnitMember> iterable;
        int n;
        int n2;
        String[] stringArray;
        int n3 = 1;
        int n4 = 25;
        if (string2 != null && !string2.trim().isEmpty()) {
            stringArray = string2.split(" ");
            if (stringArray.length > 0 && StringUtils.isNumeric((CharSequence)stringArray[0])) {
                n3 = Integer.parseInt(stringArray[0]);
            }
            if (stringArray.length > 1 && StringUtils.isNumeric((CharSequence)stringArray[1])) {
                n4 = Integer.parseInt(stringArray[1]);
            }
        }
        if (n3 > (n2 = (int)Math.ceil((double)(n = (stringArray = Arrays.asList(ClanTable.getInstance().getClans())).size()) / (double)n4))) {
            n3 = n2;
        }
        if (n3 < 1) {
            n3 = 1;
        }
        int n5 = (n3 - 1) * n4;
        int n6 = Math.min(n5 + n4, n);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("List of Clans (Page ").append(n3).append(" of ").append(n2).append("):\n");
        for (int i = n5; i < n6; ++i) {
            iterable = (Clan)stringArray.get(i);
            stringBuilder.append(((Clan)iterable).getName()).append(" - ").append(((Clan)iterable).getLeaderName()).append("\n");
        }
        ArrayList<List<InlineKeyboardButton>> arrayList = new ArrayList<List<InlineKeyboardButton>>();
        iterable = new ArrayList();
        if (n3 > 1) {
            iterable.add((InlineKeyboardButton)new InlineKeyboardButton("Previous", "/list_clans " + (n3 - 1) + " " + n4));
        }
        if (n3 < n2) {
            iterable.add((InlineKeyboardButton)new InlineKeyboardButton("Next", "/list_clans " + (n3 + 1) + " " + n4));
        }
        if (!iterable.isEmpty()) {
            arrayList.add((List<InlineKeyboardButton>)iterable);
        }
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup(arrayList);
        String string3 = d.toJson((Object)inlineKeyboardMarkup);
        TelegramApi.sendMessage(string, stringBuilder.toString(), string3);
    }

    @Override
    public String[] getCommands() {
        return this.Z;
    }

    @Override
    public boolean requiresParams() {
        return false;
    }

    private static class InlineKeyboardButton {
        private final String bT;
        private final String bU;

        public InlineKeyboardButton(String string, String string2) {
            this.bT = string;
            this.bU = string2;
        }
    }

    private static class InlineKeyboardMarkup {
        private final List<List<InlineKeyboardButton>> ar;

        public InlineKeyboardMarkup(List<List<InlineKeyboardButton>> list) {
            this.ar = list;
        }
    }
}
