/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.templates.spawn;

import l2.commons.collections.MultiValueSet;
import l2.gameserver.data.xml.holder.NpcHolder;
import l2.gameserver.templates.npc.NpcTemplate;
import l2.gameserver.utils.Location;

public class SpawnNpcInfo {
    private final int He;
    private final NpcTemplate g;
    private Location ab;
    private final int Hf;
    private final MultiValueSet<String> d;

    public SpawnNpcInfo(int n, int n2, MultiValueSet<String> multiValueSet) {
        this.He = n;
        this.g = NpcHolder.getInstance().getTemplate(n);
        this.Hf = n2;
        this.d = multiValueSet;
    }

    public int getNpcId() {
        return this.He;
    }

    public Location getSpawnLoc() {
        return this.ab;
    }

    public NpcTemplate getTemplate() {
        return this.g;
    }

    public int getMax() {
        return this.Hf;
    }

    public MultiValueSet<String> getParameters() {
        return this.d;
    }
}
