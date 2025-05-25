/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.ArrayList;
import java.util.Collection;
import l2.gameserver.Config;
import l2.gameserver.model.entity.oly.HeroController;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;

public class ExHeroList
extends L2GameServerPacket {
    private Collection<HeroController.HeroRecord> c = new ArrayList<HeroController.HeroRecord>();

    public ExHeroList() {
        for (HeroController.HeroRecord heroRecord : HeroController.getInstance().getCurrentHeroes()) {
            if (heroRecord == null || !heroRecord.active || !heroRecord.played) continue;
            this.c.add(heroRecord);
        }
    }

    @Override
    protected final void writeImpl() {
        this.writeEx(122);
        this.writeD(this.c.size());
        for (HeroController.HeroRecord heroRecord : this.c) {
            this.writeS(heroRecord.name);
            this.writeD(heroRecord.class_id);
            this.writeS(heroRecord.clan_name);
            this.writeD(heroRecord.clan_crest);
            this.writeS(heroRecord.ally_name);
            this.writeD(heroRecord.ally_crest);
            this.writeD(heroRecord.count);
            this.writeD(Config.REQUEST_ID);
        }
    }
}
