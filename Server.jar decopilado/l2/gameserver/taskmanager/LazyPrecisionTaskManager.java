/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.taskmanager;

import java.util.concurrent.Future;
import l2.commons.lang.reference.HardReference;
import l2.commons.threading.RunnableImpl;
import l2.commons.threading.SteppingRunnableQueueManager;
import l2.commons.util.Rnd;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.dao.AccountBonusDAO;
import l2.gameserver.model.Player;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.s2c.ExBR_PremiumState;
import l2.gameserver.network.l2.s2c.ExShowScreenMessage;

public class LazyPrecisionTaskManager
extends SteppingRunnableQueueManager {
    private static final LazyPrecisionTaskManager a = new LazyPrecisionTaskManager();

    public static final LazyPrecisionTaskManager getInstance() {
        return a;
    }

    private LazyPrecisionTaskManager() {
        super(1000L);
        ThreadPoolManager.getInstance().scheduleAtFixedRate(this, 1000L, 1000L);
        ThreadPoolManager.getInstance().scheduleAtFixedRate(new RunnableImpl(){

            @Override
            public void runImpl() throws Exception {
                LazyPrecisionTaskManager.this.purge();
            }
        }, 60000L, 60000L);
    }

    public Future<?> addPCCafePointsTask(final Player player) {
        long l = (long)Config.ALT_PCBANG_POINTS_DELAY * 60000L;
        return this.scheduleAtFixedRate(new RunnableImpl(){

            @Override
            public void runImpl() throws Exception {
                if (player.isInOfflineMode() || player.getLevel() < Config.ALT_PCBANG_POINTS_MIN_LVL || player.getPcBangPoints() >= Config.LIM_PC_BANG_POINTS) {
                    return;
                }
                player.addPcBangPoints(Config.ALT_PCBANG_POINTS_BONUS, Config.ALT_PCBANG_POINTS_BONUS_DOUBLE_CHANCE > 0.0 && Rnd.chance(Config.ALT_PCBANG_POINTS_BONUS_DOUBLE_CHANCE));
            }
        }, l, l);
    }

    public Future<?> addVitalityRegenTask(final Player player) {
        long l = 60000L;
        return this.scheduleAtFixedRate(new RunnableImpl(){

            @Override
            public void runImpl() throws Exception {
                if (player.isInOfflineMode() || !player.isInPeaceZone()) {
                    return;
                }
                player.setVitality(player.getVitality() + (double)Config.ALT_VITALITY_POINTS_PER_MINUTE);
            }
        }, l, l);
    }

    public Future<?> startBonusExpirationTask(Player player) {
        final HardReference<Player> hardReference = player.getRef();
        long l = player.getBonus().getBonusExpire() * 1000L - System.currentTimeMillis();
        return this.schedule(new RunnableImpl(){

            @Override
            public void runImpl() throws Exception {
                Player player = (Player)hardReference.get();
                if (player == null) {
                    return;
                }
                player.getBonus().reset();
                if (player.getParty() != null) {
                    player.getParty().recalculatePartyData();
                }
                String string = new CustomMessage("scripts.services.RateBonus.LuckEnded", player, new Object[0]).toString();
                player.sendPacket(new ExShowScreenMessage(string, 10000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true), new ExBR_PremiumState(player, false));
                if (Config.AUTO_LOOT_ONLY_FOR_PREMIUM) {
                    player.setAutoLoot(false);
                    player.setAutoLootHerbs(false);
                    player.setAutoLootAdena(false);
                }
                if (Config.XP_FREEZ_ONLY_FOR_PREMIUM) {
                    player.unsetVar("NoExp");
                }
                player.sendMessage(string);
                player.updatePremiumSkills();
                player.sendSkillList();
                AccountBonusDAO.getInstance().delete(player.getAccountName());
            }
        }, l);
    }

    public Future<?> addNpcAnimationTask(final NpcInstance npcInstance) {
        return this.scheduleAtFixedRate(new RunnableImpl(){

            @Override
            public void runImpl() throws Exception {
                if (npcInstance.isVisible() && !npcInstance.isActionsDisabled() && !npcInstance.isMoving() && !npcInstance.isInCombat()) {
                    npcInstance.onRandomAnimation();
                }
            }
        }, 1000L, (long)Rnd.get(Config.MIN_NPC_ANIMATION, Config.MAX_NPC_ANIMATION) * 1000L);
    }
}
