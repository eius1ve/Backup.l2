/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.bypass;

import l2.gameserver.model.Player;

public interface INpcHtmlAppendHandler {
    public int[] getNpcIds();

    public String getAppend(Player var1, int var2, int var3);
}
