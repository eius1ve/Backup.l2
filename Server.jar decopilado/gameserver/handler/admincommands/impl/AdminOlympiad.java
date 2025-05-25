/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

import l2.gameserver.Announcements;
import l2.gameserver.Config;
import l2.gameserver.handler.admincommands.IAdminCommandHandler;
import l2.gameserver.model.Player;
import l2.gameserver.model.World;
import l2.gameserver.model.entity.oly.HeroController;
import l2.gameserver.model.entity.oly.NoblesController;
import l2.gameserver.model.entity.oly.OlyController;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SocialAction;
import l2.gameserver.network.l2.s2c.UserInfoType;

public class AdminOlympiad
implements IAdminCommandHandler {
    @Override
    public boolean useAdminCommand(Enum enum_, String[] stringArray, String string, Player player) {
        Commands commands = (Commands)enum_;
        switch (commands) {
            case admin_oly_save: {
                if (!Config.OLY_ENABLED) {
                    return false;
                }
                try {
                    OlyController.getInstance().save();
                }
                catch (Exception exception) {
                    // empty catch block
                }
                player.sendMessage("olympaid data saved.");
                break;
            }
            case admin_add_oly_points: {
                int n;
                if (stringArray.length < 3) {
                    player.sendMessage("Command syntax: //add_oly_points <char_name> <point_to_add>");
                    player.sendMessage("This command can be applied only for online players.");
                    return false;
                }
                Player player2 = World.getPlayer(stringArray[1]);
                if (player2 == null) {
                    player.sendMessage("Character " + stringArray[1] + " not found in game.");
                    return false;
                }
                try {
                    n = Integer.parseInt(stringArray[2]);
                }
                catch (NumberFormatException numberFormatException) {
                    player.sendMessage("Please specify integer value for olympiad points.");
                    return false;
                }
                int n2 = NoblesController.getInstance().getPointsOf(player2.getObjectId());
                int n3 = n2 + n;
                NoblesController.getInstance().setPointsOf(player2.getObjectId(), n3);
                player.sendMessage("Added " + n + " points to character " + player2.getName());
                player.sendMessage("Old points: " + n2 + ", new points: " + n3);
                break;
            }
            case admin_oly_start: {
                Announcements.getInstance().announceToAll(SystemMsg.SHARPEN_YOUR_SWORDS_TIGHTEN_THE_STITCHING_IN_YOUR_ARMOR_AND_MAKE_HASTE_TO_A_GRAND_OLYMPIAD_MANAGER__BATTLES_IN_THE_GRAND_OLYMPIAD_GAMES_ARE_NOW_TAKING_PLACE);
                break;
            }
            case admin_oly_stop: {
                Announcements.getInstance().announceToAll(SystemMsg.MUCH_CARNAGE_HAS_BEEN_LEFT_FOR_THE_CLEANUP_CREW_OF_THE_OLYMPIAD_STADIUM);
                try {
                    OlyController.getInstance().save();
                }
                catch (Exception exception) {}
                break;
            }
            case admin_add_hero: {
                if (stringArray.length < 2) {
                    player.sendMessage("Command syntax: //add_hero <char_name>");
                    player.sendMessage("This command can be applied only for online players.");
                    return false;
                }
                Player player3 = World.getPlayer(stringArray[1]);
                if (player3 == null) {
                    player.sendMessage("Character " + stringArray[1] + " not found in game.");
                    return false;
                }
                if (player3.isHero()) {
                    player3.setHero(false);
                    player3.updatePledgeClass();
                    HeroController.removeSkills(player3, true);
                } else {
                    player3.setHero(true);
                    HeroController.addSkills(player3, true);
                }
                player3.sendSkillList();
                player3.sendMessage("Admin has changed your hero status.");
                player3.broadcastUserInfo(false, new UserInfoType[0]);
                break;
            }
            case admin_add_custom_hero: {
                if (stringArray.length < 3) {
                    player.sendMessage("Command syntax: //add_custom_hero <char_name> <hours>");
                    player.sendMessage("This command can be applied only for online players.");
                    return false;
                }
                Player player4 = World.getPlayer(stringArray[1]);
                if (player4 == null) {
                    player.sendMessage("Character " + stringArray[1] + " not found in game.");
                    return false;
                }
                AdminOlympiad.makeCustomHero(player4, (long)Integer.parseInt(stringArray[2]) * 60L * 60L);
                player4.sendMessage("Admin has changed your Custom Hero status on " + stringArray[2] + "hour's");
                player4.broadcastUserInfo(true, new UserInfoType[0]);
                break;
            }
        }
        return true;
    }

    private static boolean makeCustomHero(Player player, long l) {
        if (player.isHero() || l <= 0L) {
            return false;
        }
        player.setCustomHero(true, l, Config.ALT_ALLOW_CUSTOM_HERO_SKILLS);
        player.broadcastPacket(new SocialAction(player.getObjectId(), 16));
        player.broadcastUserInfo(true, new UserInfoType[0]);
        return true;
    }

    @Override
    public Enum[] getAdminCommandEnum() {
        return Commands.values();
    }

    private static final class Commands
    extends Enum<Commands> {
        public static final /* enum */ Commands admin_oly_save = new Commands();
        public static final /* enum */ Commands admin_add_oly_points = new Commands();
        public static final /* enum */ Commands admin_oly_start = new Commands();
        public static final /* enum */ Commands admin_add_hero = new Commands();
        public static final /* enum */ Commands admin_add_custom_hero = new Commands();
        public static final /* enum */ Commands admin_oly_stop = new Commands();
        private static final /* synthetic */ Commands[] a;

        public static Commands[] values() {
            return (Commands[])a.clone();
        }

        public static Commands valueOf(String string) {
            return Enum.valueOf(Commands.class, string);
        }

        private static /* synthetic */ Commands[] a() {
            return new Commands[]{admin_oly_save, admin_add_oly_points, admin_oly_start, admin_add_hero, admin_add_custom_hero, admin_oly_stop};
        }

        static {
            a = Commands.a();
        }
    }
}
