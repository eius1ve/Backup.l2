/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model;

import l2.commons.lang.reference.HardReference;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.oly.HeroController;
import l2.gameserver.network.l2.s2c.UserInfoType;

public static class GameObjectTasks.EndCustomHeroTask
extends RunnableImpl {
    private final HardReference<Player> B;

    public GameObjectTasks.EndCustomHeroTask(Player player) {
        this.B = player.getRef();
    }

    @Override
    public void runImpl() {
        Player player = this.B.get();
        if (player == null) {
            return;
        }
        if (player.getVar("CustomHeroEndTime") == null || HeroController.getInstance().isCurrentHero(player)) {
            return;
        }
        player.setHero(false);
        HeroController.removeSkills(player);
        HeroController.checkHeroWeaponary(player);
        player.broadcastUserInfo(true, new UserInfoType[0]);
    }
}
