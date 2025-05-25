/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.ai.Fighter
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.pledge.Clan
 *  l2.gameserver.utils.NpcUtils
 */
package ai;

import l2.gameserver.ai.Fighter;
import l2.gameserver.model.Creature;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.utils.NpcUtils;

public class AdventureBoss
extends Fighter {
    public AdventureBoss(NpcInstance npcInstance) {
        super(npcInstance);
    }

    protected void onEvtDead(Creature creature) {
        Clan clan = creature.getClan();
        if (clan != null && clan.getCastle() > 0) {
            if (clan.getCastle() == 1) {
                NpcUtils.spawnSingle((int)32074, (int)-14336, (int)121184, (int)-2992, (int)16500, (long)3600000L);
            }
            if (clan.getCastle() == 2) {
                NpcUtils.spawnSingle((int)32082, (int)17344, (int)147360, (int)-3120, (int)53000, (long)3600000L);
            }
            if (clan.getCastle() == 3) {
                NpcUtils.spawnSingle((int)32083, (int)85744, (int)147488, (int)-3408, (int)33000, (long)3600000L);
            }
            if (clan.getCastle() == 4) {
                NpcUtils.spawnSingle((int)32084, (int)81152, (int)53168, (int)-1558, (int)16500, (long)3600000L);
            }
            if (clan.getCastle() == 5) {
                NpcUtils.spawnSingle((int)32085, (int)147664, (int)20144, (int)-2012, (int)16500, (long)3600000L);
            }
            if (clan.getCastle() == 6) {
                NpcUtils.spawnSingle((int)32086, (int)115741, (int)219435, (int)-3664, (int)32000, (long)3600000L);
            }
            if (clan.getCastle() == 7) {
                NpcUtils.spawnSingle((int)32088, (int)150528, (int)-57696, (int)-2979, (int)25000, (long)3600000L);
            }
            if (clan.getCastle() == 8) {
                NpcUtils.spawnSingle((int)32089, (int)40560, (int)-52342, (int)-837, (int)5500, (long)3600000L);
            }
            if (clan.getCastle() == 9) {
                NpcUtils.spawnSingle((int)32087, (int)84596, (int)-141092, (int)-1542, (int)57000, (long)3600000L);
            }
        }
        super.onEvtDead(creature);
    }
}
