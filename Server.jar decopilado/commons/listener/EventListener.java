/*
 * Decompiled with CFR 0.152.
 */
package l2.commons.listener;

public interface EventListener {
    public String[] listeningEventTypes();

    public void onEvent(String var1, Object ... var2);
}
