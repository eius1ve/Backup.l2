/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity;

import l2.commons.listener.ListenerList;
import l2.gameserver.listener.reflection.OnReflectionCollapseListener;
import l2.gameserver.model.entity.Reflection;

public class Reflection.ReflectionListenerList
extends ListenerList<Reflection> {
    public void onCollapse() {
        this.forEachListener(OnReflectionCollapseListener.class, onReflectionCollapseListener -> onReflectionCollapseListener.onReflectionCollapse(Reflection.this));
    }
}
