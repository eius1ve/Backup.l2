/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.Config
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Summon
 *  l2.gameserver.model.items.ItemInstance
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 */
package handler.items;

import handler.items.ShotsItemHandler;
import l2.gameserver.Config;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.Summon;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;

public class BeastShot
extends ShotsItemHandler {
    private static final int[] M = new int[]{6645, 6646, 6647};

    private boolean a(Player player, ItemInstance itemInstance, int n) {
        if (Config.ALT_CONSUME_BEAST_SHOTS && (!player.hasBonus() || Config.ALT_PA_CONSUME_BEAST_SHOTS) && !player.getInventory().destroyItem(itemInstance, (long)n)) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_DONT_HAVE_ENOUGH_SOULSHOTS_NEEDED_FOR_A_PETSERVITOR);
            return false;
        }
        return true;
    }

    public boolean useItem(Playable playable, ItemInstance itemInstance, boolean bl) {
        Summon summon;
        if (playable == null || !playable.isPlayer()) {
            return false;
        }
        Player player = (Player)playable;
        boolean bl2 = false;
        if (player.getAutoSoulShot().contains(itemInstance.getItemId())) {
            bl2 = true;
        }
        if ((summon = player.getPet()) == null) {
            if (!bl2) {
                player.sendPacket((IStaticPacket)SystemMsg.PETS_AND_SERVITORS_ARE_NOT_AVAILABLE_AT_THIS_TIME);
            }
            return false;
        }
        if (summon.isDead()) {
            if (!bl2) {
                player.sendPacket((IStaticPacket)SystemMsg.SOULSHOTS_AND_SPIRITSHOTS_ARE_NOT_AVAILABLE_FOR_A_DEAD_PET_OR_SERVITOR);
            }
            return false;
        }
        int n = 0;
        switch (itemInstance.getItemId()) {
            case 6645: {
                if (summon.getChargedSoulShot() || !this.a(player, itemInstance, summon.getSoulshotConsumeCount())) {
                    return false;
                }
                summon.chargeSoulShot();
                n = 2033;
                break;
            }
            case 6646: {
                if (summon.getChargedSpiritShot() > 0 || !this.a(player, itemInstance, summon.getSpiritshotConsumeCount())) {
                    return false;
                }
                summon.chargeSpiritShot(1);
                n = 2008;
                break;
            }
            case 6647: {
                if (summon.getChargedSpiritShot() > 1 || !this.a(player, itemInstance, summon.getSpiritshotConsumeCount())) {
                    return false;
                }
                summon.chargeSpiritShot(2);
                n = 2009;
            }
        }
        this.broadcastShotSkill((Creature)summon, n, 1);
        return true;
    }

    public final int[] getItemIds() {
        return M;
    }
}
