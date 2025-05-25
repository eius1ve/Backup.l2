/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.Config
 *  l2.gameserver.data.htm.HtmCache
 *  l2.gameserver.handler.bypass.INpcHtmlAppendHandler
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.utils.GameStats
 *  l2.gameserver.utils.ItemFunctions
 *  l2.gameserver.utils.Log
 *  l2.gameserver.utils.Util
 *  org.apache.commons.lang3.ArrayUtils
 */
package services;

import l2.commons.util.Rnd;
import l2.gameserver.Config;
import l2.gameserver.data.htm.HtmCache;
import l2.gameserver.handler.bypass.INpcHtmlAppendHandler;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.scripts.Functions;
import l2.gameserver.utils.GameStats;
import l2.gameserver.utils.ItemFunctions;
import l2.gameserver.utils.Log;
import l2.gameserver.utils.Util;
import org.apache.commons.lang3.ArrayUtils;

public class Roulette
extends Functions
implements INpcHtmlAppendHandler {
    private static final String hN = "red";
    private static final String hO = "black";
    private static final String hP = "first";
    private static final String hQ = "second";
    private static final String hR = "third";
    private static final String hS = "even";
    private static final String hT = "odd";
    private static final String hU = "low";
    private static final String hV = "high";
    private static final String hW = "zero";
    private static final String[][] b = new String[][]{{"0", "zero", "zero", "zero", "zero", "zero"}, {"1", "red", "first", "first", "odd", "low"}, {"2", "black", "first", "second", "even", "low"}, {"3", "red", "first", "third", "odd", "low"}, {"4", "black", "first", "first", "even", "low"}, {"5", "red", "first", "second", "odd", "low"}, {"6", "black", "first", "third", "even", "low"}, {"7", "red", "first", "first", "odd", "low"}, {"8", "black", "first", "second", "even", "low"}, {"9", "red", "first", "third", "odd", "low"}, {"10", "black", "first", "first", "even", "low"}, {"11", "black", "first", "second", "odd", "low"}, {"12", "red", "first", "third", "even", "low"}, {"13", "black", "second", "first", "odd", "low"}, {"14", "red", "second", "second", "even", "low"}, {"15", "black", "second", "third", "odd", "low"}, {"16", "red", "second", "first", "even", "low"}, {"17", "black", "second", "second", "odd", "low"}, {"18", "red", "second", "third", "even", "low"}, {"19", "red", "second", "first", "odd", "high"}, {"20", "black", "second", "second", "even", "high"}, {"21", "red", "second", "third", "odd", "high"}, {"22", "black", "second", "first", "even", "high"}, {"23", "red", "second", "second", "odd", "high"}, {"24", "black", "second", "third", "even", "high"}, {"25", "red", "third", "first", "odd", "high"}, {"26", "black", "third", "second", "even", "high"}, {"27", "red", "third", "third", "odd", "high"}, {"28", "black", "third", "first", "even", "high"}, {"29", "black", "third", "second", "odd", "high"}, {"30", "red", "third", "third", "even", "high"}, {"31", "black", "third", "first", "odd", "high"}, {"32", "red", "third", "second", "even", "high"}, {"33", "black", "third", "third", "odd", "high"}, {"34", "red", "third", "first", "even", "high"}, {"35", "black", "third", "second", "odd", "high"}, {"36", "red", "third", "third", "even", "high"}};

    public void dialog() {
        Player player = this.getSelf();
        this.show(HtmCache.getInstance().getNotNull("scripts/services/roulette.htm", player).replaceFirst("%min%", Util.formatAdena((long)Config.SERVICES_ROULETTE_MIN_BET)).replaceFirst("%max%", Util.formatAdena((long)Config.SERVICES_ROULETTE_MAX_BET)), player);
    }

    public void play(String[] stringArray) {
        GameType gameType;
        Player player = this.getSelf();
        long l = 0L;
        String string = "";
        try {
            if (stringArray.length != 3) {
                throw new NumberFormatException();
            }
            gameType = GameType.valueOf(stringArray[0]);
            string = stringArray[1].trim();
            l = Long.parseLong(stringArray[2]);
            if (gameType == GameType.StraightUp && (string.length() > 2 || Integer.parseInt(string) < 0 || Integer.parseInt(string) > 36)) {
                throw new NumberFormatException();
            }
        }
        catch (NumberFormatException numberFormatException) {
            this.show("Invalid value input!<br><a action=\"bypass -h scripts_services.Roulette:dialog\">Back</a>", player);
            return;
        }
        if (l < (long)Config.SERVICES_ROULETTE_MIN_BET) {
            this.show("Too small bet!<br><a action=\"bypass -h scripts_services.Roulette:dialog\">Back</a>", player);
            return;
        }
        if (l > (long)Config.SERVICES_ROULETTE_MAX_BET) {
            this.show("Too large bet!<br><a action=\"bypass -h scripts_services.Roulette:dialog\">Back</a>", player);
            return;
        }
        if (ItemFunctions.getItemCount((Playable)player, (int)Config.SERVICES_ROULETTE_ITEM_ID) < l) {
            if (Config.SERVICES_ROULETTE_ITEM_ID == 57) {
                player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
            } else {
                player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_ITEM_COUNT);
            }
            this.show("You do not have enough items!<br><a action=\"bypass -h scripts_services.Roulette:dialog\">Back</a>", player);
            return;
        }
        String[] stringArray2 = b[Rnd.get((int)b.length)];
        int n = Roulette.a(string, stringArray2, gameType);
        String string2 = HtmCache.getInstance().getNotNull("scripts/services/rouletteresult.htm", player);
        if (n == 0) {
            Roulette.removeItem((Playable)player, (int)Config.SERVICES_ROULETTE_ITEM_ID, (long)l);
            Log.service((String)"Roulette", (Player)player, (String)("lost the bet and removed from inventory " + Config.SERVICES_ROULETTE_ITEM_ID + " amount " + l));
            GameStats.addRoulette((long)l);
            string2 = string2.replace("%result%", "<font color=\"FF0000\">Fail!</font>");
        } else {
            Roulette.addItem((Playable)player, (int)Config.SERVICES_ROULETTE_ITEM_ID, (long)(l * (long)n));
            GameStats.addRoulette((long)(-1L * l * (long)n));
            Log.service((String)"Roulette", (Player)player, (String)("received an award " + Config.SERVICES_ROULETTE_ITEM_ID + " amount " + l * (long)n));
            string2 = string2.replace("%result%", "<font color=\"00FF00\">Success!</font>");
        }
        if (player.isGM()) {
            player.sendMessage("Roulette balance: " + Util.formatAdena((long)GameStats.getRouletteSum()));
        }
        string2 = string2.replace("%bettype%", new CustomMessage("Roulette." + gameType.toString(), player, new Object[0]).toString());
        string2 = string2.replace("%betnumber%", gameType == GameType.StraightUp ? string : new CustomMessage("Roulette." + string, player, new Object[0]).toString());
        string2 = string2.replace("%number%", stringArray2[0]);
        string2 = string2.replace("%color%", new CustomMessage("Roulette." + stringArray2[1], player, new Object[0]).toString());
        string2 = string2.replace("%evenness%", new CustomMessage("Roulette." + stringArray2[4], player, new Object[0]).toString());
        string2 = string2.replace("%column%", new CustomMessage("Roulette." + stringArray2[3], player, new Object[0]).toString());
        string2 = string2.replace("%dozen%", new CustomMessage("Roulette." + stringArray2[2], player, new Object[0]).toString());
        string2 = string2.replace("%highness%", new CustomMessage("Roulette." + stringArray2[5], player, new Object[0]).toString());
        string2 = string2.replace("%param%", stringArray[0] + " " + stringArray[1] + " " + stringArray[2]);
        this.show(string2, player);
    }

    private static final int a(String string, String[] stringArray, GameType gameType) {
        switch (gameType) {
            case StraightUp: {
                if (string.equals(stringArray[0])) {
                    return 35;
                }
                return 0;
            }
            case ColumnBet: {
                if (string.equals(stringArray[3])) {
                    return 2;
                }
                return 0;
            }
            case DozenBet: {
                if (string.equals(stringArray[2])) {
                    return 2;
                }
                return 0;
            }
            case RedOrBlack: {
                if (string.equals(stringArray[1])) {
                    return 1;
                }
                return 0;
            }
            case EvenOrOdd: {
                if (string.equals(stringArray[4])) {
                    return 1;
                }
                return 0;
            }
            case LowOrHigh: {
                if (string.equals(stringArray[5])) {
                    return 1;
                }
                return 0;
            }
        }
        return 0;
    }

    public int[] getNpcIds() {
        if (!Config.SERVICES_ALLOW_ROULETTE) {
            return ArrayUtils.EMPTY_INT_ARRAY;
        }
        return Config.SERVICES_ROULETTE_NPC_ID;
    }

    public String getAppend(Player player, int n, int n2) {
        Roulette roulette = new Roulette();
        roulette.self = player.getRef();
        return roulette.getHtmlAppends(n2);
    }

    public String getHtmlAppends(Integer n) {
        Player player = this.getSelf();
        if (Config.SERVICES_ALLOW_ROULETTE) {
            return "<br><a action=\"bypass -h scripts_services.Roulette:dialog\">" + new CustomMessage("Roulette.dialog", player, new Object[0]).toString() + "</a>";
        }
        return "";
    }

    private static final class GameType
    extends Enum<GameType> {
        public static final /* enum */ GameType StraightUp = new GameType();
        public static final /* enum */ GameType ColumnBet = new GameType();
        public static final /* enum */ GameType DozenBet = new GameType();
        public static final /* enum */ GameType RedOrBlack = new GameType();
        public static final /* enum */ GameType EvenOrOdd = new GameType();
        public static final /* enum */ GameType LowOrHigh = new GameType();
        private static final /* synthetic */ GameType[] a;

        public static GameType[] values() {
            return (GameType[])a.clone();
        }

        public static GameType valueOf(String string) {
            return Enum.valueOf(GameType.class, string);
        }

        private static /* synthetic */ GameType[] a() {
            return new GameType[]{StraightUp, ColumnBet, DozenBet, RedOrBlack, EvenOrOdd, LowOrHigh};
        }

        static {
            a = GameType.a();
        }
    }
}
