/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

import l2.commons.threading.RunnableImpl;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.handler.admincommands.IAdminCommandHandler;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.MonsterRace;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.DeleteObject;
import l2.gameserver.network.l2.s2c.MonRaceInfo;
import l2.gameserver.network.l2.s2c.PlaySound;
import l2.gameserver.utils.Location;

public class AdminMonsterRace
implements IAdminCommandHandler {
    protected static int state = -1;

    @Override
    public boolean useAdminCommand(Enum enum_, String[] stringArray, String string, Player player) {
        Commands commands = (Commands)enum_;
        if (string.equalsIgnoreCase("admin_mons")) {
            if (!player.getPlayerAccess().MonsterRace) {
                return false;
            }
            this.z(player);
        }
        return true;
    }

    @Override
    public Enum[] getAdminCommandEnum() {
        return Commands.values();
    }

    private void z(Player player) {
        int[][] nArrayArray = new int[][]{{-1, 0}, {0, 15322}, {13765, -1}, {-1, 0}};
        MonsterRace monsterRace = MonsterRace.getInstance();
        if (state == -1) {
            monsterRace.newRace();
            monsterRace.newSpeeds();
            player.broadcastPacket(new MonRaceInfo(nArrayArray[++state][0], nArrayArray[state][1], monsterRace.getMonsters(), monsterRace.getSpeeds()));
        } else if (state == 0) {
            player.sendPacket((IStaticPacket)SystemMsg.THEYRE_OFF);
            player.broadcastPacket(new PlaySound("S_Race"));
            player.broadcastPacket(new PlaySound(PlaySound.Type.SOUND, "ItemSound2.race_start", 1, 121209259, new Location(12125, 182487, -3559)));
            player.broadcastPacket(new MonRaceInfo(nArrayArray[++state][0], nArrayArray[state][1], monsterRace.getMonsters(), monsterRace.getSpeeds()));
            ThreadPoolManager.getInstance().schedule(new RunRace(nArrayArray, player), 5000L);
        }
    }

    private static final class Commands
    extends Enum<Commands> {
        public static final /* enum */ Commands admin_mons = new Commands();
        private static final /* synthetic */ Commands[] a;

        public static Commands[] values() {
            return (Commands[])a.clone();
        }

        public static Commands valueOf(String string) {
            return Enum.valueOf(Commands.class, string);
        }

        private static /* synthetic */ Commands[] a() {
            return new Commands[]{admin_mons};
        }

        static {
            a = Commands.a();
        }
    }

    class RunRace
    extends RunnableImpl {
        private int[][] d;
        private Player b;

        public RunRace(int[][] nArray, Player player) {
            this.d = nArray;
            this.b = player;
        }

        @Override
        public void runImpl() throws Exception {
            this.b.broadcastPacket(new MonRaceInfo(this.d[2][0], this.d[2][1], MonsterRace.getInstance().getMonsters(), MonsterRace.getInstance().getSpeeds()));
            ThreadPoolManager.getInstance().schedule(new RunEnd(this.b), 30000L);
        }
    }

    class RunEnd
    extends RunnableImpl {
        private Player b;

        public RunEnd(Player player) {
            this.b = player;
        }

        @Override
        public void runImpl() throws Exception {
            for (int i = 0; i < 8; ++i) {
                NpcInstance npcInstance = MonsterRace.getInstance().getMonsters()[i];
                this.b.broadcastPacket(new DeleteObject(npcInstance));
            }
            state = -1;
        }
    }
}
