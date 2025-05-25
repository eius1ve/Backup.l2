/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.Config
 *  l2.gameserver.ai.DefaultAI
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.World
 *  l2.gameserver.model.entity.SevenSigns
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.tables.SkillTable
 */
package ai;

import java.util.List;
import l2.commons.util.Rnd;
import l2.gameserver.Config;
import l2.gameserver.ai.DefaultAI;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.World;
import l2.gameserver.model.entity.SevenSigns;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.scripts.Functions;
import l2.gameserver.tables.SkillTable;

public class CabaleBuffer
extends DefaultAI {
    private static final int w = 4361;
    private static final int A = 4362;
    private static final int B = 4364;
    private static final int C = 4365;
    private long h = 0L;
    private long i = 0L;
    private static final long j = 60000L;
    private static final long k = 1000L;
    private static final String[] c = new String[]{"scripts.ai.CabaleBuffer.THIS_WORLD_WILL_SOON_BE_ANNIHILATED", "scripts.ai.CabaleBuffer.ALL_IS_LOST_PREPARE_TO_MEET_THE_GODDESS_OF_DEATH", "scripts.ai.CabaleBuffer.ALL_IS_LOST_THE_PROPHECY_OF_DESTRUCTION_HAS_BEEN_FULFILLED", "scripts.ai.CabaleBuffer.THE_END_OF_TIME_HAS_COME_THE_PROPHECY_OF_DESTRUCTION_HAS_BEEN_FULFILLE"};
    private static final String[] d = new String[]{"scripts.ai.CabaleBuffer.THE_DAY_OF_JUDGMENT_IS_NEAR", "scripts.ai.CabaleBuffer.THE_PROPHECY_OF_DARKNESS_HAS_BEEN_FULFILLED", "scripts.ai.CabaleBuffer.AS_FORETOLD_IN_THE_PROPHECY_OF_DARKNESS_THE_ERA_OF_CHAOS_HAS_BEGUN", "scripts.ai.CabaleBuffer.THE_PROPHECY_OF_DARKNESS_HAS_COME_TO_PASS"};

    public CabaleBuffer(NpcInstance npcInstance) {
        super(npcInstance);
    }

    protected boolean thinkActive() {
        NpcInstance npcInstance = this.getActor();
        if (npcInstance.isDead()) {
            return true;
        }
        int n = SevenSigns.getInstance().getCabalHighestScore();
        if (n == 0) {
            return true;
        }
        int n2 = 0;
        if (n == 2) {
            n2 = 1;
        } else if (n == 1) {
            n2 = 2;
        }
        if (this.h + 60000L < System.currentTimeMillis()) {
            this.h = System.currentTimeMillis();
            if (Config.ALT_CABAL_SEVEN_SING_SHOUT) {
                Functions.npcSayInRangeCustomMessage((NpcInstance)npcInstance, (int)300, (String)(SevenSigns.ORATOR_NPC_IDS.contains(npcInstance.getNpcId()) ? c[Rnd.get((int)c.length)] : d[Rnd.get((int)d.length)]), (Object[])new Object[0]);
            }
        }
        if (this.i + 1000L < System.currentTimeMillis()) {
            this.i = System.currentTimeMillis();
            for (Player player : World.getAroundPlayers((GameObject)npcInstance, (int)300, (int)200)) {
                Skill skill;
                List list;
                if (player == null || player.isInvisible()) continue;
                int n3 = SevenSigns.getInstance().getPlayerCabal(player);
                int n4 = Rnd.get((int)100);
                int n5 = Rnd.get((int)10000);
                if (n3 == n && SevenSigns.ORATOR_NPC_IDS.contains(npcInstance.getNpcId())) {
                    if (player.isMageClass()) {
                        list = player.getEffectList().getEffectsBySkillId(4365);
                        if (list == null || list.size() <= 0) {
                            if (n5 < 1 && Config.ALT_CABAL_SEVEN_SING_SHOUT) {
                                Functions.npcSayInRangeCustomMessage((NpcInstance)npcInstance, (int)300, (String)"scripts.ai.CabaleBuffer.I_BESTOW_UPON_YOU_A_BLESSING", (Object[])new Object[0]);
                            }
                            if ((skill = SkillTable.getInstance().getInfo(4365, 1)) == null) continue;
                            npcInstance.altUseSkill(skill, (Creature)player);
                            continue;
                        }
                        if (n4 >= 5) continue;
                        if (n5 < 500 && Config.ALT_CABAL_SEVEN_SING_SHOUT) {
                            Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"scripts.ai.CabaleBuffer.S1__I_GIVE_YOU_THE_BLESSING_OF_PROPHECY", (Object[])new Object[]{player.getName()});
                        }
                        if ((skill = SkillTable.getInstance().getInfo(4365, 2)) == null) continue;
                        npcInstance.altUseSkill(skill, (Creature)player);
                        continue;
                    }
                    list = player.getEffectList().getEffectsBySkillId(4364);
                    if (list == null || list.size() <= 0) {
                        if (n5 < 1 && Config.ALT_CABAL_SEVEN_SING_SHOUT) {
                            Functions.npcSayInRangeCustomMessage((NpcInstance)npcInstance, (int)300, (String)"scripts.ai.CabaleBuffer.HERALD_OF_THE_NEW_ERA__OPEN_YOUR_EYES", (Object[])new Object[0]);
                        }
                        if ((skill = SkillTable.getInstance().getInfo(4364, 1)) == null) continue;
                        npcInstance.altUseSkill(skill, (Creature)player);
                        continue;
                    }
                    if (n4 >= 5) continue;
                    if (n5 < 500 && Config.ALT_CABAL_SEVEN_SING_SHOUT) {
                        Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"scripts.ai.CabaleBuffer.S1__I_BESTOW_UPON_YOU_THE_AUTHORITY_OF_THE_ABYSS", (Object[])new Object[]{player.getName()});
                    }
                    if ((skill = SkillTable.getInstance().getInfo(4364, 2)) == null) continue;
                    npcInstance.altUseSkill(skill, (Creature)player);
                    continue;
                }
                if (n3 != n2 || !SevenSigns.PREACHER_NPC_IDS.contains(npcInstance.getNpcId())) continue;
                if (player.isMageClass()) {
                    list = player.getEffectList().getEffectsBySkillId(4362);
                    if (list == null || list.size() <= 0) {
                        if (n5 < 1 && Config.ALT_CABAL_SEVEN_SING_SHOUT) {
                            Functions.npcSayInRangeCustomMessage((NpcInstance)npcInstance, (int)300, (String)"scripts.ai.CabaleBuffer.YOU_DONT_HAVE_ANY_HOPE__YOUR_END_HAS_COME", (Object[])new Object[0]);
                        }
                        if ((skill = SkillTable.getInstance().getInfo(4362, 1)) == null) continue;
                        npcInstance.altUseSkill(skill, (Creature)player);
                        continue;
                    }
                    if (n4 >= 5) continue;
                    if (n5 < 500 && Config.ALT_CABAL_SEVEN_SING_SHOUT) {
                        Functions.npcSayInRangeCustomMessage((NpcInstance)npcInstance, (int)300, (String)"scripts.ai.CabaleBuffer.A_CURSE_UPON_YOU", (Object[])new Object[0]);
                    }
                    if ((skill = SkillTable.getInstance().getInfo(4362, 2)) == null) continue;
                    npcInstance.altUseSkill(skill, (Creature)player);
                    continue;
                }
                list = player.getEffectList().getEffectsBySkillId(4361);
                if (list == null || list.size() <= 0) {
                    if (n5 < 1 && Config.ALT_CABAL_SEVEN_SING_SHOUT) {
                        Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"scripts.ai.CabaleBuffer.S1__YOU_BRING_AN_ILL_WIND", (Object[])new Object[]{player.getName()});
                    }
                    if ((skill = SkillTable.getInstance().getInfo(4361, 1)) == null) continue;
                    npcInstance.altUseSkill(skill, (Creature)player);
                    continue;
                }
                if (n4 >= 5) continue;
                if (n5 < 500 && Config.ALT_CABAL_SEVEN_SING_SHOUT) {
                    Functions.npcSayCustomMessage((NpcInstance)npcInstance, (String)"scripts.ai.CabaleBuffer.S1__YOU_MIGHT_AS_WELL_GIVE_UP", (Object[])new Object[]{player.getName()});
                }
                if ((skill = SkillTable.getInstance().getInfo(4361, 2)) == null) continue;
                npcInstance.altUseSkill(skill, (Creature)player);
            }
        }
        return false;
    }
}
