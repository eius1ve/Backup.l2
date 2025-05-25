/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.Config
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Party
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.base.CategoryData
 *  l2.gameserver.model.entity.oly.NoblesController
 *  l2.gameserver.model.instances.RaidBossInstance
 *  l2.gameserver.network.l2.s2c.UserInfoType
 *  l2.gameserver.templates.npc.NpcTemplate
 */
package npc.model;

import l2.gameserver.Config;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Party;
import l2.gameserver.model.Player;
import l2.gameserver.model.base.CategoryData;
import l2.gameserver.model.entity.oly.NoblesController;
import l2.gameserver.model.instances.RaidBossInstance;
import l2.gameserver.network.l2.s2c.UserInfoType;
import l2.gameserver.templates.npc.NpcTemplate;
import services.GlobalServices;

public class HeroAndNoblessRewarderInstance
extends RaidBossInstance {
    public HeroAndNoblessRewarderInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    protected void onDeath(Creature creature) {
        super.onDeath(creature);
        if (creature != null && creature.isPlayable()) {
            Player player = creature.getPlayer();
            if (player.isDead() || player.getParty() == null) {
                return;
            }
            Party party = player.getParty();
            for (Player player2 : party) {
                if (player2 == null || player2.isDead() || player2.isNoble() || player2.isSubClassActive() || !CategoryData.fourth_class_group.isPlayerBaseClassBelong(player2) || player2.getLevel() < 76 || this.getDistance3D((GameObject)player2) > (double)Config.ALT_PARTY_DISTRIBUTION_RANGE) continue;
                NoblesController.getInstance().addNoble(player2.getPlayer());
                player2.setNoble(true);
                player2.updatePledgeClass();
                player2.updateNobleSkills();
                player2.sendSkillList();
                player2.getPlayer().broadcastUserInfo(false, new UserInfoType[0]);
            }
            if (Config.ALT_ALLOW_CUSTOM_HERO) {
                for (Player player2 : party) {
                    if (player2 == null || player2.isDead() || player2.isSubClassActive() || player2.getLevel() < 76 || this.getDistance3D((GameObject)player2) > (double)Config.ALT_PARTY_DISTRIBUTION_RANGE) continue;
                    GlobalServices.makeCustomHero(player2, Config.CUSTOM_HERO_STATUS_TIME);
                }
            }
        }
    }
}
