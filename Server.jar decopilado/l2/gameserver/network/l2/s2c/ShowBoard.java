/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.network.l2.s2c;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import l2.gameserver.data.htm.HtmCache;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.scripts.Functions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShowBoard
extends L2GameServerPacket {
    private static final Logger dc = LoggerFactory.getLogger(ShowBoard.class);
    private static final Charset a = StandardCharsets.UTF_16LE;
    private final String fJ;
    private String fK;
    private List<String> db;
    private String fL = "";

    public static void separateAndSend(Player player, String string) {
        ShowBoard.separateAndSend(HtmCache.getInstance().getNotNull(string, player), player);
        if (player.isGM()) {
            Functions.sendDebugMessage(player, "BBS HTML: " + string);
        }
    }

    public static void separateAndSend(String string, Player player) {
        string = player.getNetConnection().encodeBypasses(string, true);
        byte[] byArray = string.getBytes(a);
        if (byArray.length < 16360) {
            player.sendPacket((IStaticPacket)new ShowBoard(string, "101", player, false));
            player.sendPacket((IStaticPacket)new ShowBoard(null, "102", player, false));
            player.sendPacket((IStaticPacket)new ShowBoard(null, "103", player, false));
        } else if (byArray.length < 32720) {
            player.sendPacket((IStaticPacket)new ShowBoard(new String(byArray, 0, 16360, a), "101", player, false));
            player.sendPacket((IStaticPacket)new ShowBoard(new String(byArray, 16360, byArray.length - 16360, a), "102", player, false));
            player.sendPacket((IStaticPacket)new ShowBoard(null, "103", player, false));
        } else if (string.length() < 49080) {
            player.sendPacket((IStaticPacket)new ShowBoard(new String(byArray, 0, 16360, a), "101", player, false));
            player.sendPacket((IStaticPacket)new ShowBoard(new String(byArray, 16360, byArray.length - 16360, a), "102", player, false));
            player.sendPacket((IStaticPacket)new ShowBoard(new String(byArray, 32720, byArray.length - 32720, a), "103", player, false));
        }
    }

    private ShowBoard(String string, String string2, Player player, boolean bl) {
        if (string != null && string.length() > 16384) {
            dc.warn("Html '" + string + "' is too long! this will crash the client!");
            this.fJ = "<html><body>Html was too long</body></html>";
            return;
        }
        this.fK = string2;
        if (player.getSessionVar("add_fav") != null) {
            this.fL = "bypass _bbsaddfav_List";
        }
        this.fJ = string != null ? (bl ? player.getNetConnection().encodeBypasses(string, true) : string) : null;
    }

    public ShowBoard(String string, String string2, Player player) {
        this(string, string2, player, true);
    }

    public ShowBoard(List<String> list) {
        this.fK = "1002";
        this.fJ = null;
        this.db = list;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(123);
        this.writeC(1);
        this.writeS("bypass _bbshome");
        this.writeS("bypass _bbsgetfav");
        this.writeS("bypass _bbsloc");
        this.writeS("bypass _bbsclan");
        this.writeS("bypass _bbsmemo");
        this.writeS("bypass _maillist_0_1_0_");
        this.writeS("bypass _friendlist_0_");
        this.writeS(this.fL);
        String string = this.fK + "\b";
        if (!this.fK.equals("1002")) {
            if (this.fJ != null) {
                string = string + this.fJ;
            }
        } else {
            for (String string2 : this.db) {
                string = string + string2 + " \b";
            }
        }
        this.writeS(string);
    }
}
