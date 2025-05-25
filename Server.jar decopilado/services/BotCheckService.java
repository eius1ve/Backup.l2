/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.lang.reference.HardReference
 *  l2.commons.listener.Listener
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.Config
 *  l2.gameserver.ThreadPoolManager
 *  l2.gameserver.handler.voicecommands.IVoicedCommandHandler
 *  l2.gameserver.handler.voicecommands.VoicedCommandHandler
 *  l2.gameserver.listener.actor.OnKillListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.EffectList
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.Zone$ZoneType
 *  l2.gameserver.model.actor.instances.player.AutoFarmContext
 *  l2.gameserver.model.actor.listener.PlayerListenerList
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.scripts.Scripts
 *  l2.gameserver.tables.SkillTable
 *  org.apache.commons.lang3.ArrayUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package services;

import l2.commons.lang.reference.HardReference;
import l2.commons.listener.Listener;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.handler.voicecommands.IVoicedCommandHandler;
import l2.gameserver.handler.voicecommands.VoicedCommandHandler;
import l2.gameserver.listener.actor.OnKillListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.EffectList;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.Zone;
import l2.gameserver.model.actor.instances.player.AutoFarmContext;
import l2.gameserver.model.actor.listener.PlayerListenerList;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.scripts.Scripts;
import l2.gameserver.tables.SkillTable;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class BotCheckService
extends Functions
implements ScriptFile {
    private static final Logger dU = LoggerFactory.getLogger(BotCheckService.class);
    private static final String hq = "BCSMonKillCnt";
    private static final BotCheckKillListener a = new BotCheckKillListener();
    private static final BotCheckVoicedCommandHandler a = new BotCheckVoicedCommandHandler();
    private static final Skill z = SkillTable.getInstance().getInfo(Config.CAPTCHA_PENALTY_SKILL_ID, Config.CAPTCHA_PENALTY_SKILL_LEVEL);
    private static final String hr = "botCheckPenalty";

    private static int c(Player player) {
        return player.getVarInt(hq, 0);
    }

    private static void r(Player player, int n) {
        if (n > 0) {
            player.setVar(hq, n, -1L);
        } else {
            player.unsetVar(hq);
        }
    }

    private static boolean b(Player player, int n) {
        if (n < Config.CAPTCHA_MONSTER_KILLS_THRESHOLD) {
            return false;
        }
        ThreadPoolManager.getInstance().execute((Runnable)((Object)new BotCheckTask(player)));
        return true;
    }

    private static void ao(Player player) {
        if (player.getEffectList().getEffectsBySkill(z) == null) {
            z.getEffects((Creature)player, (Creature)player, false, false, false);
            player.setVar(hr, 1, -1L);
        }
        Scripts.getInstance().callScripts(player, "Util", "RequestCapcha", new Object[]{"services.BotCheckService:captchaCheckSucceed", player.getStoredId(), 30});
    }

    public void captchaCheckSucceed() {
        Player player = this.getSelf();
        if (!Config.SERVICE_CAPTCHA_BOT_CHECK || player == null) {
            return;
        }
        BotCheckService.ap(player);
    }

    private static void ap(Player player) {
        BotCheckService.r(player, 0);
        EffectList effectList = player.getEffectList();
        effectList.stopEffect(z);
        player.unsetVar(hr);
    }

    private static void aq(Player player) {
        int n = BotCheckService.c(player);
        if (!BotCheckService.b(player, n)) {
            BotCheckService.r(player, n + 1);
        }
    }

    public void onLoad() {
        if (Config.SERVICE_CAPTCHA_BOT_CHECK) {
            dU.info("Service bot captcha check started");
            VoicedCommandHandler.getInstance().registerVoicedCommandHandler((IVoicedCommandHandler)a);
            PlayerListenerList.addGlobal((Listener)a);
        }
    }

    public void onReload() {
        this.onShutdown();
        this.onLoad();
    }

    public void onShutdown() {
        PlayerListenerList.removeGlobal((Listener)a);
    }

    private static class BotCheckTask
    extends RunnableImpl {
        private final HardReference<Player> ae;

        private BotCheckTask(Player player) {
            this.ae = player.getRef();
        }

        public void runImpl() {
            Player player = (Player)this.ae.get();
            if (player == null) {
                return;
            }
            BotCheckService.ao(player);
        }
    }

    private static class BotCheckVoicedCommandHandler
    implements IVoicedCommandHandler {
        private final String[] aW = new String[]{"check_captcha"};

        private BotCheckVoicedCommandHandler() {
        }

        public boolean useVoicedCommand(String string, Player player, String string2) {
            if (!Config.SERVICE_CAPTCHA_BOT_CHECK) {
                return false;
            }
            if (this.aW[0].equalsIgnoreCase(string)) {
                BotCheckService.b(player, BotCheckService.c(player));
                return true;
            }
            return false;
        }

        public String[] getVoicedCommandList() {
            if (!Config.SERVICE_CAPTCHA_BOT_CHECK) {
                return ArrayUtils.EMPTY_STRING_ARRAY;
            }
            return this.aW;
        }
    }

    private static class BotCheckKillListener
    implements OnKillListener {
        private BotCheckKillListener() {
        }

        public void onKill(Creature creature, Creature creature2) {
            AutoFarmContext autoFarmContext = creature.getFarmSystem();
            if (!Config.SERVICE_CAPTCHA_BOT_CHECK || !creature.isPlayer() || !creature2.isMonster() || autoFarmContext != null && autoFarmContext.isAutofarming() || creature.isInAnyZone(new Zone.ZoneType[]{Zone.ZoneType.peace_zone, Zone.ZoneType.fun, Zone.ZoneType.epic, Zone.ZoneType.battle_zone, Zone.ZoneType.RESIDENCE})) {
                return;
            }
            BotCheckService.aq(creature.getPlayer());
        }

        public boolean ignorePetOrSummon() {
            return true;
        }
    }
}
