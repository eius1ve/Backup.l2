/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.Config
 *  l2.gameserver.handler.bypass.INpcHtmlAppendHandler
 *  l2.gameserver.model.Player
 *  l2.gameserver.scripts.Functions
 *  org.apache.commons.lang3.ArrayUtils
 */
package services;

import l2.gameserver.Config;
import l2.gameserver.handler.bypass.INpcHtmlAppendHandler;
import l2.gameserver.model.Player;
import l2.gameserver.scripts.Functions;
import org.apache.commons.lang3.ArrayUtils;

public class TeleToCatacomb
extends Functions
implements INpcHtmlAppendHandler {
    public int[] getNpcIds() {
        if (!Config.ALT_TELE_TO_CATACOMBS) {
            return ArrayUtils.EMPTY_INT_ARRAY;
        }
        return Config.ALT_TELE_TO_CATACOMBS_NPC_ID;
    }

    public String getAppend(Player player, int n, int n2) {
        TeleToCatacomb teleToCatacomb = new TeleToCatacomb();
        teleToCatacomb.self = player.getRef();
        return teleToCatacomb.getHtmlAppends(n2);
    }

    public String getHtmlAppends(Integer n) {
        if (n != 0 || !Config.ALT_TELE_TO_CATACOMBS) {
            return "";
        }
        Player player = this.getSelf();
        Object object = "";
        object = (String)object + "<br>";
        if (player.isLangRus()) {
            object = (String)object + "\u0417\u0430 \u043e\u043f\u0440\u0435\u0434\u0435\u043b\u0435\u043d\u043d\u0443\u044e \u043f\u043b\u0430\u0442\u0443, \u0432\u044b \u043c\u043e\u0436\u0435\u0442\u0435 \u043f\u0435\u0440\u0435\u043c\u0435\u0441\u0442\u0438\u0442\u044c\u0441\u044f \u0432 \u043a\u0430\u0442\u0430\u043a\u043e\u043c\u0431\u044b \u0438\u043b\u0438 \u043d\u0435\u043a\u0440\u043e\u043f\u043e\u043b\u0438\u0441\u044b.<br1> ";
            object = (String)object + "\u0421\u043f\u0438\u0441\u043e\u043a \u0434\u043e\u0441\u0442\u0443\u043f\u043d\u044b\u0445 \u043b\u043e\u043a\u0430\u0446\u0438\u0439:<br>";
        } else {
            object = (String)object + "Teleport to catacomb or necropolis.<br1> ";
            object = (String)object + "You may teleport to any of the following hunting locations.<br>";
        }
        if (player.getLevel() <= Config.GATEKEEPER_CATOCOMBS_FREE_LEVEL) {
            object = (String)object + "[scripts_Util:Gatekeeper -41567 209463 -5080 0|Necropolis of Sacrifice (20-30)]<br1>";
            object = (String)object + "[scripts_Util:Gatekeeper 45248 124223 -5408 0|The Pilgrim's Necropolis (30-40)]<br1>";
            object = (String)object + "[scripts_Util:Gatekeeper 110911 174013 -5439 0|Necropolis of Worship (40-50)]<br1>";
            object = (String)object + "[scripts_Util:Gatekeeper -22101 77383 -5173 0|The Patriot's Necropolis (50-60)]<br1>";
            object = (String)object + "[scripts_Util:Gatekeeper -52654 79149 -4741 0|Necropolis of Devotion (60-70)]<br1>";
            object = (String)object + "[scripts_Util:Gatekeeper 117884 132796 -4831 0|Necropolis of Martyrdom (60-70)]<br1>";
            object = (String)object + "[scripts_Util:Gatekeeper 82750 209250 -5401 0|The Saint's Necropolis (70-80)]<br1>";
            object = (String)object + "[scripts_Util:Gatekeeper 171897 -17606 -4901 0|Disciples Necropolis(70-80)]<br>";
            object = (String)object + "[scripts_Util:Gatekeeper 42322 143927 -5381 0|Catacomb of the Heretic (30-40)]<br1>";
            object = (String)object + "[scripts_Util:Gatekeeper 45841 170307 -4981 0|Catacomb of the Branded (40-50)]<br1>";
            object = (String)object + "[scripts_Util:Gatekeeper 77348 78445 -5125 0|Catacomb of the Apostate (50-60)]<br1>";
            object = (String)object + "[scripts_Util:Gatekeeper 139955 79693 -5429 0|Catacomb of the Witch (60-70)]<br1>";
            object = (String)object + "[scripts_Util:Gatekeeper -19827 13509 -4901 0|Catacomb of Dark Omens (70-80)]<br1>";
            object = (String)object + "[scripts_Util:Gatekeeper 113573 84513 -6541 0|Catacomb of the Forbidden Path (70-80)]";
        } else {
            object = (String)object + "[scripts_Util:Gatekeeper -41567 209463 -5080 10000|Necropolis of Sacrifice (20-30) - 10000 Adena]<br1>";
            object = (String)object + "[scripts_Util:Gatekeeper 45248 124223 -5408 20000|The Pilgrim's Necropolis (30-40) - 20000 Adena]<br1>";
            object = (String)object + "[scripts_Util:Gatekeeper 110911 174013 -5439 30000|Necropolis of Worship (40-50) - 30000 Adena]<br1>";
            object = (String)object + "[scripts_Util:Gatekeeper -22101 77383 -5173 40000|The Patriot's Necropolis (50-60) - 40000 Adena]<br1>";
            object = (String)object + "[scripts_Util:Gatekeeper -52654 79149 -4741 50000|Necropolis of Devotion (60-70) - 50000 Adena]<br1>";
            object = (String)object + "[scripts_Util:Gatekeeper 117884 132796 -4831 50000|Necropolis of Martyrdom (60-70) - 50000 Adena]<br1>";
            object = (String)object + "[scripts_Util:Gatekeeper 82750 209250 -5401 60000|The Saint's Necropolis (70-80) - 60000 Adena]<br1>";
            object = (String)object + "[scripts_Util:Gatekeeper 171897 -17606 -4901 60000|Disciples Necropolis(70-80) - 60000 Adena]<br>";
            object = (String)object + "[scripts_Util:Gatekeeper 42322 143927 -5381 20000|Catacomb of the Heretic (30-40) - 20000 Adena]<br1>";
            object = (String)object + "[scripts_Util:Gatekeeper 45841 170307 -4981 30000|Catacomb of the Branded (40-50) - 30000 Adena]<br1>";
            object = (String)object + "[scripts_Util:Gatekeeper 77348 78445 -5125 40000|Catacomb of the Apostate (50-60) - 40000 Adena]<br1>";
            object = (String)object + "[scripts_Util:Gatekeeper 139955 79693 -5429 50000|Catacomb of the Witch (60-70) - 50000 Adena]<br1>";
            object = (String)object + "[scripts_Util:Gatekeeper -19827 13509 -4901 60000|Catacomb of Dark Omens (70-80) - 60000 Adena]<br1>";
            object = (String)object + "[scripts_Util:Gatekeeper 113573 84513 -6541 60000|Catacomb of the Forbidden Path (70-80) - 60000 Adena]";
        }
        return object;
    }
}
