/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.telegram.impl;

import l2.gameserver.handler.telegram.ITelegramCommandHandler;
import l2.gameserver.handler.telegram.TelegramApi;

public class HelpCommand
implements ITelegramCommandHandler {
    private final String[] V = new String[]{"/help", "/start"};

    @Override
    public void handle(String string, String string2) throws Exception {
        if ("/start".equals(string2)) {
            TelegramApi.setBotCommands();
            TelegramApi.sendMessage(string, "Menu initialized.");
        } else {
            TelegramApi.sendMessage(string, "Welcome to the Lucera 2 Admin Bot!\nCommands:\n/help - Get list of available commands\n/list_online - Show list of online players\n/online - Show online player count\n/find <name> - Find character by name\n/whois <name> - Get info by player name\n/inventory <name> - Show player inventory\n/setclass - Set player class\n/sethero - Set player temporal hero\n/setnoble - Set player Nobles status\n/setname - Set new name for player\n/settitle - Set player title\n/set_pa - Give Premium account for player\n/setlevel <name> <level> - Set player level\n/give_item <name> <id> <count> - Give item to player\n/remove_item <name> <id> <count> - Remove item from player\n/pm <player> <message> - Send private message to player\n/gmlist - Show list of GMs online\n/announce <message> - Send an announcement to all players\n/jail <name> <time> <reason> - Jail a player\n/unjail <name> - Release a player from jail\n/kick <name> - Kick player from the game\n/char_ban <name> <days> - Ban a player\n/chat_ban <name> <period> - Ban a player from chat\n/nospam <name> <period> - Shadow ban a player's chat\n/list_clans - Show list of all clans\n/status - Show server status and info\n/heap - Dump server heap memory\n/mem - Show server memory usage\n/gc - Show garbage collection info\n/net - Show network statistics\n/aistats - Show AI statistics\n/effectstats - Show effects statistics\n/threads - Show thread info and statistics\n/config - Set or get configuration parameters\n/pathfind - Show pathfinding statistics\n/pool - Show thread pool statistics\n/restart - Restart the server\n/shutdown <time> - Shutdown the server\n/abort - Abort server restart or shutdown\n/uptime - Show server uptime\n/version - Show server version");
        }
    }

    @Override
    public String[] getCommands() {
        return this.V;
    }

    @Override
    public boolean requiresParams() {
        return false;
    }
}
