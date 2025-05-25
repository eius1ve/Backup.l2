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

public class simpleSevenSign
extends Functions
implements INpcHtmlAppendHandler {
    public String getHtmlAppends(Integer n) {
        if (n != 0 || !Config.ALT_SIMPLE_SIGNS) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        if (this.getSelf().isLangRus()) {
            if (Config.ALT_SIMPLE_SIGNS) {
                stringBuilder.append("<br><center>\u041e\u043f\u0446\u0438\u0438 \u0441\u0435\u043c\u0438 \u043f\u0435\u0447\u0430\u0442\u0435\u0439:</center><br>");
                stringBuilder.append("<center>[npc_%objectId%_Multisell 3112605|\u0421\u0434\u0435\u043b\u0430\u0442\u044c S-\u0433\u0440\u0435\u0439\u0434 \u043c\u0435\u0447]</center><br1>");
                stringBuilder.append("<center>[npc_%objectId%_Multisell 3112606|\u0412\u0441\u0442\u0430\u0432\u0438\u0442\u044c SA \u0432 \u043e\u0440\u0443\u0436\u0438\u0435 S-\u0433\u0440\u0435\u0439\u0434\u0430]</center><br1>");
                stringBuilder.append("<center>[npc_%objectId%_Multisell 3112607|\u0420\u0430\u0441\u043f\u0435\u0447\u0430\u0442\u0430\u0442\u044c \u0431\u0440\u043e\u043d\u044e S-\u0433\u0440\u0435\u0439\u0434\u0430]</center><br1>");
                stringBuilder.append("<center>[npc_%objectId%_Multisell 3112608|\u0420\u0430\u0441\u043f\u0435\u0447\u0430\u0442\u0430\u0442\u044c \u0431\u0438\u0436\u0443\u0442\u0435\u0440\u0438\u044e S-\u0433\u0440\u0435\u0439\u0434\u0430]</center><br1>");
                stringBuilder.append("<center>[npc_%objectId%_Multisell 3112609|\u0421\u0434\u0435\u043b\u0430\u0442\u044c A-\u0433\u0440\u0435\u0439\u0434 \u043c\u0435\u0447]</center><br1>");
                stringBuilder.append("<center>[npc_%objectId%_Multisell 3112610|\u0412\u0441\u0442\u0430\u0432\u0438\u0442\u044c SA \u0432 \u043e\u0440\u0443\u0436\u0438\u0435 A-\u0433\u0440\u0435\u0439\u0434\u0430]</center><br1>");
                stringBuilder.append("<center>[npc_%objectId%_Multisell 3112611|\u0420\u0430\u0441\u043f\u0435\u0447\u0430\u0442\u0430\u0442\u044c \u0431\u0440\u043e\u043d\u044e A-\u0433\u0440\u0435\u0439\u0434\u0430]</center><br1>");
                stringBuilder.append("<center>[npc_%objectId%_Multisell 3112612|\u0420\u0430\u0441\u043f\u0435\u0447\u0430\u0442\u0430\u0442\u044c \u0431\u0438\u0436\u0443\u0442\u0435\u0440\u0438\u044e A-\u0433\u0440\u0435\u0439\u0434\u0430]</center><br1>");
                stringBuilder.append("<center>[npc_%objectId%_Multisell 3112613|\u0417\u0430\u043f\u0435\u0447\u0430\u0442\u0430\u0442\u044c \u0431\u0440\u043e\u043d\u044e A-\u0433\u0440\u0435\u0439\u0434\u0430]</center><br1>");
                stringBuilder.append("<center>[npc_%objectId%_Multisell 3112601|\u0423\u0434\u0430\u043b\u0438\u0442\u044c SA \u0438\u0437 \u043e\u0440\u0443\u0436\u0438\u044f]</center><br1>");
                stringBuilder.append("<center>[npc_%objectId%_Multisell 3112602|\u041e\u0431\u043c\u0435\u043d\u044f\u0442\u044c \u043e\u0440\u0443\u0436\u0438\u0435 \u0441 \u0434\u043e\u043f\u043b\u0430\u0442\u043e\u0439]</center><br1>");
                stringBuilder.append("<center>[npc_%objectId%_Multisell 3112603|\u041e\u0431\u043c\u0435\u043d\u044f\u0442\u044c \u043e\u0440\u0443\u0436\u0438\u0435 \u043d\u0430 \u0440\u0430\u0432\u043d\u043e\u0446\u0435\u043d\u043d\u043e\u0435]</center><br1>");
                stringBuilder.append("<center>[npc_%objectId%_Multisell 3111301|\u041a\u0443\u043f\u0438\u0442\u044c \u0447\u0442\u043e-\u043d\u0438\u0431\u0443\u0434\u044c]</center><br1>");
                stringBuilder.append("<center>[npc_%objectId%_Multisell 400|\u041e\u0431\u043c\u0435\u043d\u044f\u0442\u044c \u043a\u0430\u043c\u043d\u0438]</center><br1>");
                stringBuilder.append("<center>[npc_%objectId%_Multisell 500|\u041f\u0440\u0438\u043e\u0431\u0440\u0435\u0441\u0442\u0438 \u0440\u0430\u0441\u0445\u043e\u0434\u043d\u044b\u0435 \u043c\u0430\u0442\u0435\u0440\u0438\u0430\u043b\u044b]</center><br1>");
            }
        } else if (Config.ALT_SIMPLE_SIGNS) {
            stringBuilder.append("<br><center>Seven Signs options:</center><br>");
            stringBuilder.append("<center>[npc_%objectId%_Multisell 3112605|Manufacture an S-grade sword]</center><br1>");
            stringBuilder.append("<center>[npc_%objectId%_Multisell 3112606|Bestow the special S-grade weapon some abilities]</center><br1>");
            stringBuilder.append("<center>[npc_%objectId%_Multisell 3112607|Release the S-grade armor seal]</center><br1>");
            stringBuilder.append("<center>[npc_%objectId%_Multisell 3112608|Release the S-grade accessory seal]</center><br1>");
            stringBuilder.append("<center>[npc_%objectId%_Multisell 3112609|Manufacture an A-grade sword]</center><br1>");
            stringBuilder.append("<center>[npc_%objectId%_Multisell 3112610|Bestow the special A-grade weapon some abilities]</center><br1>");
            stringBuilder.append("<center>[npc_%objectId%_Multisell 3112611|Release the A-grade armor seal]</center><br1>");
            stringBuilder.append("<center>[npc_%objectId%_Multisell 3112612|Release the A-grade accessory seal]</center><br1>");
            stringBuilder.append("<center>[npc_%objectId%_Multisell 3112613|Seal the A-grade armor again]</center><br1>");
            stringBuilder.append("<center>[npc_%objectId%_Multisell 3112601|Remove the special abilities from a weapon]</center><br1>");
            stringBuilder.append("<center>[npc_%objectId%_Multisell 3112602|Upgrade weapon]</center><br1>");
            stringBuilder.append("<center>[npc_%objectId%_Multisell 3112603|Make an even exchange of weapons]</center><br1>");
            stringBuilder.append("<center>[npc_%objectId%_Multisell 3111301|Buy Something]</center><br1>");
            stringBuilder.append("<center>[npc_%objectId%_Multisell 400|Exchange Seal Stones]</center><br1>");
            stringBuilder.append("<center>[npc_%objectId%_Multisell 500|Purchase consumable items]</center><br1>");
        }
        return stringBuilder.toString();
    }

    public int[] getNpcIds() {
        if (!Config.ALT_SIMPLE_SIGNS) {
            return ArrayUtils.EMPTY_INT_ARRAY;
        }
        return Config.ALT_SIMPLE_SIGNS_NPC_ID;
    }

    public String getAppend(Player player, int n, int n2) {
        simpleSevenSign simpleSevenSign2 = new simpleSevenSign();
        simpleSevenSign2.self = player.getRef();
        return simpleSevenSign2.getHtmlAppends(n2);
    }
}
