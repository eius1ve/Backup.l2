/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Zone$ZoneType
 *  l2.gameserver.model.base.TeamType
 */
package services.community.custom;

import l2.gameserver.model.Player;
import l2.gameserver.model.Zone;
import l2.gameserver.model.base.TeamType;
import services.community.custom.ACbConfigManager;

public class CommunityTools {
    public static boolean checkConditions(Player player) {
        if (!ACbConfigManager.ALLOW_PVPCB_ABNORMAL && (player.isDead() || player.isAlikeDead() || player.isCastingNow() || player.isInCombat() || player.isInBoat() || player.isAttackingNow() || player.isOlyParticipant() || player.isFlying() || player.isInFlyingTransform() || player.isSitting() || player.getTeam() != TeamType.NONE)) {
            return false;
        }
        if (!ACbConfigManager.ALLOW_PVPCB_AT_SIEGE_ZONES && player.isInZone(Zone.ZoneType.SIEGE)) {
            return false;
        }
        if (!ACbConfigManager.ALLOW_PVPCB_AT_PVP_ZONES && player.isInZone(Zone.ZoneType.battle_zone)) {
            return false;
        }
        if (!ACbConfigManager.ALLOW_PVPCB_AT_FUN_ZONES && player.isInZone(Zone.ZoneType.fun)) {
            return false;
        }
        if (!ACbConfigManager.ALLOW_PVPCB_AT_EPIC_ZONES && player.isInZone(Zone.ZoneType.epic)) {
            return false;
        }
        if (!ACbConfigManager.ALLOW_PVPCB_FOR_PK && player.getPkKills() > 0) {
            return false;
        }
        if (!ACbConfigManager.ALLOW_PVPCB_FOR_KARMA && player.getKarma() > 0) {
            return false;
        }
        if (!ACbConfigManager.ALLOW_PVPCB_FOR_OLY && player.isOlyParticipant()) {
            return false;
        }
        return ACbConfigManager.ALLOW_PVPCB_AT_EVENTS || player.getTeam() == TeamType.NONE;
    }
}
