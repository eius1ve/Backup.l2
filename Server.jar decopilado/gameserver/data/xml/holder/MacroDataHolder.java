/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.data.xml.holder;

import java.util.ArrayList;
import java.util.List;
import l2.commons.data.xml.AbstractHolder;
import l2.gameserver.model.actor.instances.player.Macro;
import l2.gameserver.model.actor.instances.player.ShortCut;

public final class MacroDataHolder
extends AbstractHolder {
    private static final MacroDataHolder a = new MacroDataHolder();
    private final List<Macro> ae = new ArrayList<Macro>();
    private final List<ShortCut> af = new ArrayList<ShortCut>();

    public static MacroDataHolder getInstance() {
        return a;
    }

    public void addMacro(Macro macro) {
        this.ae.add(macro);
    }

    public List<Macro> getMacros() {
        return this.ae;
    }

    public void addShortcut(ShortCut shortCut) {
        this.af.add(shortCut);
    }

    public List<ShortCut> getShortcuts() {
        return this.af;
    }

    @Override
    public int size() {
        return this.ae.size() + this.af.size();
    }

    @Override
    public void clear() {
        this.ae.clear();
        this.af.clear();
    }
}
