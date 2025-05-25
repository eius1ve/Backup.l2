/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.entity.residence.ClanHall
 *  l2.gameserver.model.entity.residence.Residence
 *  l2.gameserver.network.l2.s2c.AgitDecoInfo
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.templates.npc.NpcTemplate
 */
package npc.model.residences.clanhall;

import l2.gameserver.model.entity.residence.ClanHall;
import l2.gameserver.model.entity.residence.Residence;
import l2.gameserver.network.l2.s2c.AgitDecoInfo;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.templates.npc.NpcTemplate;
import npc.model.residences.ResidenceManager;

public class ManagerInstance
extends ResidenceManager {
    public ManagerInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    @Override
    protected Residence getResidence() {
        return this.getClanHall();
    }

    @Override
    public L2GameServerPacket decoPacket() {
        ClanHall clanHall = this.getClanHall();
        if (clanHall != null) {
            return new AgitDecoInfo(clanHall);
        }
        return null;
    }

    @Override
    protected int getPrivUseFunctions() {
        return 4096;
    }

    @Override
    protected int getPrivSetFunctions() {
        return 32768;
    }

    @Override
    protected int getPrivDismiss() {
        return 16384;
    }

    @Override
    protected int getPrivDoors() {
        return 2048;
    }
}
