/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.telegram;

public interface ITelegramCommandHandler {
    public void handle(String var1, String var2) throws Exception;

    public String[] getCommands();

    public boolean requiresParams();
}
