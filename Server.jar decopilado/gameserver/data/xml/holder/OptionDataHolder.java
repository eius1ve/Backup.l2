/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.napile.primitive.maps.IntObjectMap
 *  org.napile.primitive.maps.impl.HashIntObjectMap
 */
package l2.gameserver.data.xml.holder;

import l2.commons.data.xml.AbstractHolder;
import l2.gameserver.templates.OptionDataTemplate;
import org.napile.primitive.maps.IntObjectMap;
import org.napile.primitive.maps.impl.HashIntObjectMap;

public final class OptionDataHolder
extends AbstractHolder {
    private static final OptionDataHolder a = new OptionDataHolder();
    private IntObjectMap<OptionDataTemplate> e = new HashIntObjectMap();

    public static OptionDataHolder getInstance() {
        return a;
    }

    public void addTemplate(OptionDataTemplate optionDataTemplate) {
        this.e.put(optionDataTemplate.getId(), (Object)optionDataTemplate);
    }

    public OptionDataTemplate getTemplate(int n) {
        return (OptionDataTemplate)this.e.get(n);
    }

    @Override
    public int size() {
        return this.e.size();
    }

    @Override
    public void clear() {
        this.e.clear();
    }
}
