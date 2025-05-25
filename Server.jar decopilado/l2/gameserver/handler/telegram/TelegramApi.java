/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.handler.telegram;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import l2.gameserver.handler.telegram.TelegramBotProperties;
import l2.gameserver.handler.telegram.model.ForceReply;
import l2.gameserver.handler.telegram.model.GetUpdateResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TelegramApi {
    private static final Logger bg = LoggerFactory.getLogger(TelegramApi.class);
    private static final Gson a = new GsonBuilder().create();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static GetUpdateResult getUpdates(long l) {
        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;
        try {
            String string;
            URL uRL = new URL(TelegramBotProperties.TELEGRAM_BOT_BASE_URL + TelegramBotProperties.TELEGRAM_BOT_API_KEY + "/getUpdates?offset=" + l);
            httpURLConnection = (HttpURLConnection)uRL.openConnection();
            httpURLConnection.setRequestMethod("GET");
            int n = httpURLConnection.getResponseCode();
            if (n != 200) {
                switch (n) {
                    case 400: {
                        bg.error("Bad Request: The server could not process the request due to invalid syntax. (code 400)");
                        break;
                    }
                    case 401: {
                        bg.error("Unauthorized: Incorrect API key or access token, access is denied. (code 401)");
                        break;
                    }
                    case 403: {
                        bg.error("Forbidden: Access to the resource is forbidden, possibly due to insufficient permissions. (code 403)");
                        break;
                    }
                    case 404: {
                        bg.error("Not Found: The server could not find the requested resource. (code 404)");
                        break;
                    }
                    case 409: {
                        bg.error("Conflict: TelegramBotApiKey is already in use by another server. (code 409)");
                        break;
                    }
                    case 500: {
                        bg.error("Internal Server Error: An internal error occurred on the server. (code 500)");
                        break;
                    }
                    case 503: {
                        bg.error("Service Unavailable: The server is temporarily unavailable, possibly due to overload. (code 503)");
                        break;
                    }
                    default: {
                        bg.error("HTTP error code: " + n);
                    }
                }
                GetUpdateResult getUpdateResult = null;
                return getUpdateResult;
            }
            bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            while ((string = bufferedReader.readLine()) != null) {
                stringBuilder.append(string);
            }
            GetUpdateResult getUpdateResult = (GetUpdateResult)a.fromJson(stringBuilder.toString(), GetUpdateResult.class);
            return getUpdateResult;
        }
        catch (Exception exception) {
            if (TelegramBotProperties.TELEGRAM_BOT_DEBUG) {
                bg.error("Telegram Bot: Error retrieving information from Telegram API", (Throwable)exception);
            }
            GetUpdateResult getUpdateResult = null;
            return getUpdateResult;
        }
        finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }
            catch (Exception exception) {
                bg.error("Error closing resources", (Throwable)exception);
            }
        }
    }

    public static void sendMessage(String string, String string2) throws Exception {
        TelegramApi.sendMessage(string, string2, null);
    }

    public static void sendMessage(String string, String string2, String string3) throws Exception {
        Object object;
        URL uRL = new URL(TelegramBotProperties.TELEGRAM_BOT_BASE_URL + TelegramBotProperties.TELEGRAM_BOT_API_KEY + "/sendMessage");
        HttpURLConnection httpURLConnection = (HttpURLConnection)uRL.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);
        String string4 = "chat_id=" + URLEncoder.encode(string, StandardCharsets.UTF_8) + "&text=" + URLEncoder.encode(string2, StandardCharsets.UTF_8);
        if (string3 != null) {
            string4 = string4 + "&reply_markup=" + URLEncoder.encode(string3, StandardCharsets.UTF_8);
        }
        try (Closeable closeable = httpURLConnection.getOutputStream();){
            object = string4.getBytes(StandardCharsets.UTF_8);
            ((OutputStream)closeable).write((byte[])object, 0, ((byte[])object).length);
        }
        closeable = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            String string5 = ((BufferedReader)closeable).readLine();
            object = string5;
            if (string5 == null) break;
            stringBuilder.append((String)object);
        }
        ((BufferedReader)closeable).close();
    }

    public static void sendForceReplyMessage(String string, String string2, String string3) throws Exception {
        ForceReply forceReply = new ForceReply(true, string3, false);
        String string4 = a.toJson((Object)forceReply);
        TelegramApi.sendMessage(string, string2, string4);
    }

    public static void setBotCommands() throws Exception {
        Object object;
        ArrayList<Map<String, String>> arrayList = new ArrayList<Map<String, String>>();
        arrayList.add(TelegramApi.a("help", "Get list of available commands"));
        arrayList.add(TelegramApi.a("list_online", "Show list of online players"));
        arrayList.add(TelegramApi.a("online", "Show online player count"));
        arrayList.add(TelegramApi.a("find", "Find player by name"));
        arrayList.add(TelegramApi.a("whois", "Get detailed info about a player"));
        arrayList.add(TelegramApi.a("inventory", "Show player's inventory"));
        arrayList.add(TelegramApi.a("sethero", "Set player temporal hero"));
        arrayList.add(TelegramApi.a("setnoble", "Set player Nobles status"));
        arrayList.add(TelegramApi.a("setname", "Set new name for player"));
        arrayList.add(TelegramApi.a("settitle", "Set player title"));
        arrayList.add(TelegramApi.a("set_pa", "Give Premium account for player"));
        arrayList.add(TelegramApi.a("give_item", "Give item to a player"));
        arrayList.add(TelegramApi.a("give_all", "Give to all players online"));
        arrayList.add(TelegramApi.a("give_all_by_ip", "Give to all online players with a unique IP"));
        arrayList.add(TelegramApi.a("give_all_by_hwid", "Give to all online players with a unique HWID"));
        arrayList.add(TelegramApi.a("remove_item", "Remove item from a player"));
        arrayList.add(TelegramApi.a("pm", "Send private message to a player"));
        arrayList.add(TelegramApi.a("gmlist", "Show list of GMs online"));
        arrayList.add(TelegramApi.a("announce", "Send an announcement to all players"));
        arrayList.add(TelegramApi.a("jail", "Jail a player"));
        arrayList.add(TelegramApi.a("unjail", "Release a player from jail"));
        arrayList.add(TelegramApi.a("kick", "Kick a player from the game"));
        arrayList.add(TelegramApi.a("char_ban", "Ban a player"));
        arrayList.add(TelegramApi.a("chat_ban", "Ban a player from chat"));
        arrayList.add(TelegramApi.a("nospam", "Shadow ban a player's chat"));
        arrayList.add(TelegramApi.a("list_clans", "Show list of all clans"));
        arrayList.add(TelegramApi.a("status", "Show server status and info"));
        arrayList.add(TelegramApi.a("heap", "Dump server heap memory"));
        arrayList.add(TelegramApi.a("mem", "Show server memory usage"));
        arrayList.add(TelegramApi.a("gc", "Show garbage collection info"));
        arrayList.add(TelegramApi.a("net", "Show network statistics"));
        arrayList.add(TelegramApi.a("aistats", "Show AI statistics"));
        arrayList.add(TelegramApi.a("effectstats", "Show effects statistics"));
        arrayList.add(TelegramApi.a("threads", "Show thread info and statistics"));
        arrayList.add(TelegramApi.a("config", "Set or get configuration parameters"));
        arrayList.add(TelegramApi.a("pathfind", "Show pathfinding statistics"));
        arrayList.add(TelegramApi.a("pool", "Show thread pool statistics"));
        arrayList.add(TelegramApi.a("restart", "Restart the server"));
        arrayList.add(TelegramApi.a("shutdown", "Shutdown the server"));
        arrayList.add(TelegramApi.a("abort", "Abort server restart or shutdown"));
        arrayList.add(TelegramApi.a("uptime", "Show server uptime"));
        arrayList.add(TelegramApi.a("version", "Show server version"));
        HashMap<String, ArrayList<Map<String, String>>> hashMap = new HashMap<String, ArrayList<Map<String, String>>>();
        hashMap.put("commands", arrayList);
        URL uRL = new URL(TelegramBotProperties.TELEGRAM_BOT_BASE_URL + TelegramBotProperties.TELEGRAM_BOT_API_KEY + "/setMyCommands");
        HttpURLConnection httpURLConnection = (HttpURLConnection)uRL.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestProperty("Content-Type", "application/json");
        String string = a.toJson(hashMap);
        try (Closeable closeable = httpURLConnection.getOutputStream();){
            object = string.getBytes(StandardCharsets.UTF_8);
            ((OutputStream)closeable).write((byte[])object, 0, ((byte[])object).length);
        }
        closeable = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            String string2 = ((BufferedReader)closeable).readLine();
            object = string2;
            if (string2 == null) break;
            stringBuilder.append((String)object);
        }
        ((BufferedReader)closeable).close();
        bg.info("Telegram Bot Loaded. Commands set: " + stringBuilder);
    }

    private static Map<String, String> a(String string, String string2) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("command", string);
        hashMap.put("description", string2);
        return hashMap;
    }
}
