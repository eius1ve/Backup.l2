/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.data.xml.holder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import l2.commons.data.xml.AbstractHolder;
import l2.gameserver.templates.item.support.EnsoulOption;

public class EnsoulOptionHolder
extends AbstractHolder {
    private static final EnsoulOptionHolder a = new EnsoulOptionHolder();
    private final Map<Integer, EnsoulOption> R = new HashMap<Integer, EnsoulOption>();
    private final Map<Integer, List<EnsoulOption>> S = new HashMap<Integer, List<EnsoulOption>>();

    private EnsoulOptionHolder() {
    }

    public static EnsoulOptionHolder getInstance() {
        return a;
    }

    public void add(EnsoulOption ensoulOption) {
        this.R.put(ensoulOption.getOptionId(), ensoulOption);
        List<EnsoulOption> list = this.S.get(ensoulOption.getStoneId());
        if (list == null) {
            list = new ArrayList<EnsoulOption>();
            this.S.put(ensoulOption.getStoneId(), list);
        }
        list.add(ensoulOption);
    }

    public EnsoulOption getOptionById(int n) {
        return this.R.get(n);
    }

    public List<EnsoulOption> getOptionsByStoneItemId(int n) {
        return Collections.unmodifiableList(this.S.get(n));
    }

    @Override
    public int size() {
        return this.R.size();
    }

    @Override
    public void clear() {
        this.R.clear();
        this.S.clear();
    }
}
