/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

import java.util.StringTokenizer;
import l2.commons.util.Rnd;
import l2.gameserver.Config;
import l2.gameserver.handler.admincommands.IAdminCommandHandler;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Effect;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Party;
import l2.gameserver.model.Player;
import l2.gameserver.model.World;
import l2.gameserver.model.base.InvisibleType;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.Earthquake;
import l2.gameserver.network.l2.s2c.ExRedSky;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.SSQInfo;
import l2.gameserver.network.l2.s2c.SocialAction;
import l2.gameserver.network.l2.s2c.SunRise;
import l2.gameserver.network.l2.s2c.SunSet;
import l2.gameserver.skills.AbnormalEffect;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.utils.Util;

public class AdminEffects
implements IAdminCommandHandler {
    @Override
    public boolean useAdminCommand(Enum enum_, String[] stringArray, String string, Player player) {
        int n;
        Object object;
        int n2;
        Commands commands = (Commands)enum_;
        StringTokenizer stringTokenizer = new StringTokenizer(string);
        if (!player.getPlayerAccess().GodMode) {
            return false;
        }
        AbnormalEffect abnormalEffect = AbnormalEffect.NULL;
        GameObject gameObject = player.getTarget();
        switch (commands) {
            case admin_invis: 
            case admin_vis: {
                if (player.isInvisible()) {
                    player.setInvisibleType(InvisibleType.NONE);
                    player.stopAbnormalEffect(AbnormalEffect.STEALTH);
                    player.broadcastCharInfo();
                    if (player.getPet() != null) {
                        player.getPet().broadcastCharInfo();
                    }
                    player.setVar("gm_vis", "true", -1L);
                    break;
                }
                player.setInvisibleType(InvisibleType.NORMAL);
                player.startAbnormalEffect(AbnormalEffect.STEALTH);
                player.sendUserInfo(true);
                World.removeObjectFromPlayers(player);
                player.unsetVar("gm_vis");
                break;
            }
            case admin_gmspeed: {
                if (stringArray.length < 2) {
                    n2 = 0;
                } else {
                    try {
                        n2 = Integer.parseInt(stringArray[1]);
                    }
                    catch (Exception exception) {
                        player.sendMessage("USAGE: //gmspeed value=[0~4]");
                        return false;
                    }
                }
                object = player.getEffectList().getEffectsBySkillId(7029);
                int n3 = object == null ? 0 : (n = object.isEmpty() ? 0 : ((Effect)object.get(0)).getSkill().getLevel());
                if (n2 == 0) {
                    if (n != 0) {
                        player.doCast(SkillTable.getInstance().getInfo(7029, n), player, true);
                    }
                    player.unsetVar("gm_gmspeed");
                    break;
                }
                if (n2 >= 1 && n2 <= 4) {
                    if (Config.SAVE_GM_EFFECTS) {
                        player.setVar("gm_gmspeed", String.valueOf(n2), -1L);
                    }
                    if (n2 == n) break;
                    if (n != 0) {
                        player.doCast(SkillTable.getInstance().getInfo(7029, n), player, true);
                    }
                    player.doCast(SkillTable.getInstance().getInfo(7029, n2), player, true);
                    break;
                }
                player.sendMessage("USAGE: //gmspeed value=[0..4]");
                break;
            }
            case admin_invul: {
                this.b(player, player);
                if (player.isInvul()) {
                    if (!Config.SAVE_GM_EFFECTS) break;
                    player.setVar("gm_invul", "true", -1L);
                    break;
                }
                player.unsetVar("gm_invul");
            }
        }
        if (!player.isGM()) {
            return false;
        }
        switch (commands) {
            case admin_offline_vis: {
                for (Player player2 : GameObjectsStorage.getAllPlayers()) {
                    if (player2 == null || !player2.isInOfflineMode()) continue;
                    player2.setInvisibleType(InvisibleType.NONE);
                    player2.decayMe();
                    player2.spawnMe();
                }
                break;
            }
            case admin_offline_invis: {
                for (Player player3 : GameObjectsStorage.getAllPlayers()) {
                    if (player3 == null || !player3.isInOfflineMode()) continue;
                    player3.setInvisibleType(InvisibleType.NORMAL);
                    player3.decayMe();
                }
                break;
            }
            case admin_earthquake: {
                try {
                    int n4 = Integer.parseInt(stringArray[1]);
                    n = Integer.parseInt(stringArray[2]);
                    player.broadcastPacket(new Earthquake(player.getLoc(), n4, n));
                    break;
                }
                catch (Exception exception) {
                    player.sendMessage("USAGE: //earthquake intensity duration");
                    return false;
                }
            }
            case admin_redsky: {
                try {
                    int n5 = Integer.parseInt(stringArray[1]);
                    for (Player player4 : GameObjectsStorage.getAllPlayersForIterate()) {
                        player4.broadcastPacket(new ExRedSky(n5));
                    }
                    break;
                }
                catch (Exception exception) {
                    player.sendMessage("USAGE: //redsky duration");
                    return false;
                }
            }
            case admin_para: 
            case admin_block: {
                if (gameObject == null || !gameObject.isCreature()) {
                    player.sendPacket((IStaticPacket)SystemMsg.INVALID_TARGET);
                    return false;
                }
                if (((Creature)gameObject).isBlocked()) {
                    return false;
                }
                ((Creature)gameObject).abortAttack(true, false);
                ((Creature)gameObject).abortCast(true, false);
                ((Creature)gameObject).block();
                player.sendMessage("Target blocked.");
                ((Creature)gameObject).sendMessage("You have been paralyzed by a GM " + player.getName());
                break;
            }
            case admin_unpara: 
            case admin_unblock: {
                if (gameObject == null || !gameObject.isCreature()) {
                    player.sendPacket((IStaticPacket)SystemMsg.INVALID_TARGET);
                    return false;
                }
                if (!((Creature)gameObject).isBlocked()) {
                    return false;
                }
                ((Creature)gameObject).unblock();
                player.sendMessage("Target unblocked.");
                ((Creature)gameObject).sendMessage("You have been unblocked by a GM " + player.getName());
                break;
            }
            case admin_bloc_party: 
            case admin_para_party: {
                if (gameObject == null || !gameObject.isCreature()) {
                    player.sendPacket((IStaticPacket)SystemMsg.INVALID_TARGET);
                    return false;
                }
                object = gameObject.getPlayer().getParty();
                if (object == null) {
                    player.sendMessage("Player '" + gameObject.getName() + "' have no party.");
                    return false;
                }
                for (Player player5 : ((Party)object).getPartyMembers()) {
                    if (player5.isBlocked()) {
                        return false;
                    }
                    player5.abortAttack(true, false);
                    player5.abortCast(true, false);
                    player5.block();
                    player5.sendMessage("You have been paralyzed by a GM " + player.getName());
                }
                player.sendMessage("Party with leader name " + gameObject + " blocked");
                break;
            }
            case admin_unbloc_party: 
            case admin_unpara_party: {
                if (gameObject == null || !gameObject.isCreature()) {
                    player.sendPacket((IStaticPacket)SystemMsg.INVALID_TARGET);
                    return false;
                }
                object = gameObject.getPlayer().getParty();
                if (object == null) {
                    player.sendMessage("Player '" + gameObject.getName() + "' have no party.");
                    return false;
                }
                for (Player player6 : ((Party)object).getPartyMembers()) {
                    if (!player6.isBlocked()) {
                        return false;
                    }
                    player6.unblock();
                    player6.sendMessage("You have been unblocked by a GM " + player.getName());
                }
                player.sendMessage("Party with leader name " + gameObject + " unblocked");
                break;
            }
            case admin_changename: {
                if (stringArray.length < 2) {
                    player.sendMessage("USAGE: //changename newName");
                    return false;
                }
                if (gameObject == null) {
                    gameObject = player;
                }
                if (!gameObject.isCreature()) {
                    player.sendPacket((IStaticPacket)SystemMsg.INVALID_TARGET);
                    return false;
                }
                object = ((Creature)gameObject).getName();
                String string2 = Util.joinStrings(" ", stringArray, 1);
                ((Creature)gameObject).setName(string2);
                ((Creature)gameObject).broadcastCharInfo();
                player.sendMessage("Changed name from " + (String)object + " to " + string2 + ".");
                break;
            }
            case admin_setinvul: {
                if (gameObject == null || !gameObject.isPlayer()) {
                    player.sendPacket((IStaticPacket)SystemMsg.INVALID_TARGET);
                    return false;
                }
                this.b(player, (Player)gameObject);
                break;
            }
            case admin_getinvul: {
                if (gameObject == null || !gameObject.isCreature()) break;
                player.sendMessage("Target " + gameObject.getName() + "(object ID: " + gameObject.getObjectId() + ") is " + (!((Creature)gameObject).isInvul() ? "NOT " : "") + "invul");
                break;
            }
            case admin_social: {
                if (stringArray.length < 2) {
                    n2 = Rnd.get(1, 7);
                } else {
                    try {
                        n2 = Integer.parseInt(stringArray[1]);
                    }
                    catch (NumberFormatException numberFormatException) {
                        player.sendMessage("USAGE: //social value");
                        return false;
                    }
                }
                if (gameObject == null || gameObject == player) {
                    player.broadcastPacket(new SocialAction(player.getObjectId(), n2));
                    break;
                }
                if (!gameObject.isCreature()) break;
                ((Creature)gameObject).broadcastPacket(new SocialAction(gameObject.getObjectId(), n2));
                break;
            }
            case admin_abnormal: {
                Creature creature;
                try {
                    if (stringArray.length > 1) {
                        abnormalEffect = AbnormalEffect.getByName(stringArray[1]);
                    }
                }
                catch (Exception exception) {
                    player.sendMessage("USAGE: //abnormal name");
                    player.sendMessage("//abnormal - Clears all abnormal effects");
                    return false;
                }
                Creature creature2 = creature = gameObject == null ? player : (Creature)gameObject;
                if (abnormalEffect == AbnormalEffect.NULL) {
                    for (AbnormalEffect abnormalEffect2 : creature.getAbnormalEffects()) {
                        creature.stopAbnormalEffect(abnormalEffect2);
                    }
                    creature.sendMessage("Abnormal effects clearned by admin.");
                    if (creature == player) break;
                    creature.sendMessage("Abnormal effects clearned.");
                    break;
                }
                creature.startAbnormalEffect(abnormalEffect);
                creature.sendMessage("Admin added abnormal effect: " + abnormalEffect.getName());
                if (creature == player) break;
                creature.sendMessage("Added abnormal effect: " + abnormalEffect.getName());
                break;
            }
            case admin_transform: {
                try {
                    n2 = Integer.parseInt(stringArray[1]);
                }
                catch (Exception exception) {
                    player.sendMessage("USAGE: //transform transform_id");
                    return false;
                }
                player.setTransformation(n2);
                break;
            }
            case admin_showmovie: {
                int n6;
                if (stringArray.length < 2) {
                    player.sendMessage("USAGE: //showmovie id");
                    return false;
                }
                try {
                    n6 = Integer.parseInt(stringArray[1]);
                }
                catch (NumberFormatException numberFormatException) {
                    player.sendMessage("You must specify id");
                    return false;
                }
                player.showQuestMovie(n6);
                break;
            }
            case admin_atmosphere: {
                try {
                    stringTokenizer.nextToken();
                    String string3 = stringTokenizer.nextToken();
                    int n7 = stringTokenizer.hasMoreTokens() ? Integer.parseInt(stringTokenizer.nextToken()) : 10;
                    this.a(string3, n7, player);
                }
                catch (Exception exception) {
                    player.sendMessage("Usage: //atmosphere day|night|dawn|dusk|red <duration>");
                }
                break;
            }
            case admin_jump_target: {
                if (player.getTarget() == null || player.getTarget().isNpc() || !player.getTarget().isPlayer()) {
                    player.sendMessage("Usage: //jump_target target (Must be character)");
                    return false;
                }
                player.broadcastPlayerJump((Player)gameObject);
            }
        }
        return true;
    }

    private void b(Player player, Player player2) {
        if (player2.isInvul()) {
            player2.setIsInvul(false);
            player2.stopAbnormalEffect(AbnormalEffect.INVINCIBILITY);
            if (player2.getPet() != null) {
                player2.getPet().setIsInvul(false);
                player2.getPet().stopAbnormalEffect(AbnormalEffect.INVINCIBILITY);
            }
            player.sendMessage(player2.getName() + " is now mortal.");
        } else {
            player2.setIsInvul(true);
            player2.startAbnormalEffect(AbnormalEffect.INVINCIBILITY);
            if (player2.getPet() != null) {
                player2.getPet().setIsInvul(true);
                player2.getPet().startAbnormalEffect(AbnormalEffect.INVINCIBILITY);
            }
            player.sendMessage(player2.getName() + " is now immortal.");
        }
    }

    private void a(String string, int n, Player player) {
        L2GameServerPacket l2GameServerPacket = null;
        switch (string) {
            case "night": {
                l2GameServerPacket = SunSet.STATIC_PACKET;
                break;
            }
            case "day": {
                l2GameServerPacket = SunRise.STATIC_PACKET;
                break;
            }
            case "dawn": {
                l2GameServerPacket = new SSQInfo(2);
                break;
            }
            case "dusk": {
                l2GameServerPacket = new SSQInfo(1);
                break;
            }
            case "red": {
                l2GameServerPacket = new ExRedSky(n);
                break;
            }
            default: {
                player.sendMessage("Usage: //atmosphere day|night|dawn|dusk|red <duration>");
            }
        }
        if (l2GameServerPacket != null) {
            for (Player player2 : GameObjectsStorage.getAllPlayersForIterate()) {
                player2.broadcastPacket(l2GameServerPacket);
            }
        }
    }

    @Override
    public Enum[] getAdminCommandEnum() {
        return Commands.values();
    }

    private static final class Commands
    extends Enum<Commands> {
        public static final /* enum */ Commands admin_invis = new Commands();
        public static final /* enum */ Commands admin_vis = new Commands();
        public static final /* enum */ Commands admin_offline_vis = new Commands();
        public static final /* enum */ Commands admin_offline_invis = new Commands();
        public static final /* enum */ Commands admin_earthquake = new Commands();
        public static final /* enum */ Commands admin_redsky = new Commands();
        public static final /* enum */ Commands admin_jump_target = new Commands();
        public static final /* enum */ Commands admin_block = new Commands();
        public static final /* enum */ Commands admin_para = new Commands();
        public static final /* enum */ Commands admin_bloc_party = new Commands();
        public static final /* enum */ Commands admin_para_party = new Commands();
        public static final /* enum */ Commands admin_unbloc_party = new Commands();
        public static final /* enum */ Commands admin_unpara_party = new Commands();
        public static final /* enum */ Commands admin_unblock = new Commands();
        public static final /* enum */ Commands admin_unpara = new Commands();
        public static final /* enum */ Commands admin_changename = new Commands();
        public static final /* enum */ Commands admin_gmspeed = new Commands();
        public static final /* enum */ Commands admin_invul = new Commands();
        public static final /* enum */ Commands admin_setinvul = new Commands();
        public static final /* enum */ Commands admin_getinvul = new Commands();
        public static final /* enum */ Commands admin_social = new Commands();
        public static final /* enum */ Commands admin_abnormal = new Commands();
        public static final /* enum */ Commands admin_transform = new Commands();
        public static final /* enum */ Commands admin_showmovie = new Commands();
        public static final /* enum */ Commands admin_atmosphere = new Commands();
        private static final /* synthetic */ Commands[] a;

        public static Commands[] values() {
            return (Commands[])a.clone();
        }

        public static Commands valueOf(String string) {
            return Enum.valueOf(Commands.class, string);
        }

        private static /* synthetic */ Commands[] a() {
            return new Commands[]{admin_invis, admin_vis, admin_offline_vis, admin_offline_invis, admin_earthquake, admin_redsky, admin_jump_target, admin_block, admin_para, admin_bloc_party, admin_para_party, admin_unbloc_party, admin_unpara_party, admin_unblock, admin_unpara, admin_changename, admin_gmspeed, admin_invul, admin_setinvul, admin_getinvul, admin_social, admin_abnormal, admin_transform, admin_showmovie, admin_atmosphere};
        }

        static {
            a = Commands.a();
        }
    }
}
