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

public class tattooSell
extends Functions
implements INpcHtmlAppendHandler {
    public String getHtmlAppends(Integer n) {
        if (n != 0 || !Config.ALT_ALLOW_TATTOO) {
            return "";
        }
        return this.getSelf().isLangRus() ? "<br>[npc_%objectId%_Multisell 6500|\u041a\u0443\u043f\u0438\u0442\u044c \u0442\u0430\u0442\u0443]" : "<br>[npc_%objectId%_Multisell 6500|Buy tattoo]";
    }

    public int[] getNpcIds() {
        if (!Config.ALT_ALLOW_TATTOO) {
            return ArrayUtils.EMPTY_INT_ARRAY;
        }
        return Config.ALT_ALLOW_TATTOO_NPC_ID;
    }

    public String getAppend(Player player, int n, int n2) {
        tattooSell tattooSell2 = new tattooSell();
        tattooSell2.self = player.getRef();
        return tattooSell2.getHtmlAppends(n2);
    }
}
