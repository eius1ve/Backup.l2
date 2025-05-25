/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.items.ItemInstance
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.ExAutoSoulShot
 *  l2.gameserver.network.l2.s2c.SystemMessage
 *  l2.gameserver.templates.item.WeaponTemplate
 *  l2.gameserver.templates.item.WeaponTemplate$WeaponType
 */
package handler.items;

import handler.items.ShotsItemHandler;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExAutoSoulShot;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.templates.item.WeaponTemplate;

public class FishShots
extends ShotsItemHandler {
    private static int[] H = new int[]{6535, 6536, 6537, 6538, 6539, 6540};
    private static int[] O = new int[]{2181, 2182, 2183, 2184, 2185, 2186};

    public boolean useItem(Playable playable, ItemInstance itemInstance, boolean bl) {
        if (playable == null || !playable.isPlayer()) {
            return false;
        }
        Player player = (Player)playable;
        int n = itemInstance.getItemId();
        boolean bl2 = false;
        if (player.getAutoSoulShot().contains(n)) {
            bl2 = true;
        }
        ItemInstance itemInstance2 = player.getActiveWeaponInstance();
        WeaponTemplate weaponTemplate = player.getActiveWeaponItem();
        if (itemInstance2 == null || weaponTemplate.getItemType() != WeaponTemplate.WeaponType.ROD) {
            if (!bl2) {
                player.sendPacket((IStaticPacket)SystemMsg.CANNOT_USE_SOULSHOTS);
            }
            return false;
        }
        if (itemInstance2.getChargedFishshot()) {
            return false;
        }
        if (itemInstance.getCount() < 1L) {
            if (bl2) {
                player.removeAutoSoulShot(Integer.valueOf(n));
                player.sendPacket(new IStaticPacket[]{new ExAutoSoulShot(n, false, 0), new SystemMessage(SystemMsg.THE_AUTOMATIC_USE_OF_S1_HAS_BEEN_DEACTIVATED).addString(itemInstance.getName())});
                return false;
            }
            player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_SPIRITSHOT_FOR_THAT);
            return false;
        }
        int n2 = weaponTemplate.getCrystalType().gradeOrd();
        if (n2 == 0 && n != 6535 || n2 == 1 && n != 6536 || n2 == 2 && n != 6537 || n2 == 3 && n != 6538 || n2 == 4 && n != 6539 || n2 == 5 && n != 6540) {
            if (bl2) {
                return false;
            }
            player.sendPacket((IStaticPacket)SystemMsg.THAT_IS_THE_WRONG_GRADE_OF_SOULSHOT_FOR_THAT_FISHING_POLE);
            return false;
        }
        if (player.getInventory().destroyItem(itemInstance, 1L)) {
            itemInstance2.setChargedFishshot(true);
            player.sendPacket((IStaticPacket)SystemMsg.YOUR_SPIRITSHOT_HAS_BEEN_ENABLED);
            this.broadcastShotSkill((Creature)player, O[n2], 1);
        }
        return true;
    }

    public int[] getItemIds() {
        return H;
    }
}
