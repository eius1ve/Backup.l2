/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Party
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.tables.SkillTable
 *  l2.gameserver.utils.Location
 */
package npc.model;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Party;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.scripts.Functions;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.utils.Location;

private class FreyaDeaconKeeperInstance.IceCastleRunner
extends RunnableImpl {
    private final int HC;

    private FreyaDeaconKeeperInstance.IceCastleRunner(int n) {
        this.HC = n;
    }

    public void runImpl() throws Exception {
        switch (this.HC) {
            case 1000: {
                Party party = FreyaDeaconKeeperInstance.this.a();
                boolean bl = true;
                if (party == null || party.getMemberCount() != FreyaDeaconKeeperInstance.this.HB) {
                    bl = false;
                }
                if (bl && party != null) {
                    for (Player player : party.getPartyMembers()) {
                        if (!(FreyaDeaconKeeperInstance.this.getDistance((GameObject)player) > 512.0)) continue;
                        bl = false;
                    }
                }
                if (!bl) {
                    FreyaDeaconKeeperInstance.this.clear();
                    Functions.npcShoutCustomMessage((NpcInstance)FreyaDeaconKeeperInstance.this, (String)"scripts.ice_castle.1121007", (Object[])new Object[0]);
                    return;
                }
                FreyaDeaconKeeperInstance.this.cd();
                FreyaDeaconKeeperInstance.this.cc();
                FreyaDeaconKeeperInstance.this.R = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new FreyaDeaconKeeperInstance.IceCastleRunner(1001)), 5000L);
                Functions.teleportParty((Party)party, (Location)ad, (int)64);
                break;
            }
            case 1001: {
                Party party = FreyaDeaconKeeperInstance.this.a();
                if (party == null || party.getMemberCount() != FreyaDeaconKeeperInstance.this.HB) {
                    FreyaDeaconKeeperInstance.this.clear();
                    return;
                }
                FreyaDeaconKeeperInstance.this.a(10000, "scripts.ice_castle.1121001", new Object[0]);
                FreyaDeaconKeeperInstance.this.cf();
                FreyaDeaconKeeperInstance.this.ch();
                FreyaDeaconKeeperInstance.this.R = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new FreyaDeaconKeeperInstance.IceCastleRunner(1002)), 30000L);
                break;
            }
            case 1002: {
                Party party = FreyaDeaconKeeperInstance.this.a();
                if (party != null) {
                    Skill skill = SkillTable.getInstance().getInfo(4479, 1);
                    for (Player player : party.getPartyMembers()) {
                        skill.getEffects((Creature)player, (Creature)player, false, false);
                    }
                }
                FreyaDeaconKeeperInstance.this.R = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new FreyaDeaconKeeperInstance.IceCastleRunner(1003)), 300000L);
                break;
            }
            case 1003: {
                FreyaDeaconKeeperInstance.this.a(10000, "scripts.ice_castle.1121008", new Object[0]);
                FreyaDeaconKeeperInstance.this.R = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new FreyaDeaconKeeperInstance.IceCastleRunner(1004)), 600000L);
                break;
            }
            case 1004: {
                FreyaDeaconKeeperInstance.this.a(10000, "scripts.ice_castle.1121009", new Object[0]);
                FreyaDeaconKeeperInstance.this.R = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new FreyaDeaconKeeperInstance.IceCastleRunner(1005)), 600000L);
                break;
            }
            case 1005: {
                FreyaDeaconKeeperInstance.this.a(10000, "scripts.ice_castle.1121002", new Object[0]);
                FreyaDeaconKeeperInstance.this.R = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new FreyaDeaconKeeperInstance.IceCastleRunner(1006)), 540000L);
                break;
            }
            case 1006: {
                FreyaDeaconKeeperInstance.this.ci();
                FreyaDeaconKeeperInstance.this.cg();
                FreyaDeaconKeeperInstance.this.ce();
                FreyaDeaconKeeperInstance.this.a(10000, "scripts.ice_castle.1121003", new Object[0]);
                FreyaDeaconKeeperInstance.this.clear();
                break;
            }
            case 1007: {
                FreyaDeaconKeeperInstance.this.ci();
                FreyaDeaconKeeperInstance.this.ce();
                FreyaDeaconKeeperInstance.this.a(10000, "scripts.ice_castle.1121004", new Object[0]);
                FreyaDeaconKeeperInstance.this.R = ThreadPoolManager.getInstance().schedule((Runnable)((Object)new FreyaDeaconKeeperInstance.IceCastleRunner(1008)), 30000L);
                break;
            }
            case 1008: {
                FreyaDeaconKeeperInstance.this.cg();
                FreyaDeaconKeeperInstance.this.clear();
            }
        }
    }
}
