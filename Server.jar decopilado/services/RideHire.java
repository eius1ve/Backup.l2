/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.Config
 *  l2.gameserver.data.xml.holder.ResidenceHolder
 *  l2.gameserver.handler.bypass.INpcHtmlAppendHandler
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.entity.residence.Castle
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.SetupGauge
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.utils.ItemFunctions
 *  l2.gameserver.utils.Log
 *  org.apache.commons.lang3.ArrayUtils
 */
package services;

import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.ResidenceHolder;
import l2.gameserver.handler.bypass.INpcHtmlAppendHandler;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.residence.Castle;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SetupGauge;
import l2.gameserver.scripts.Functions;
import l2.gameserver.utils.ItemFunctions;
import l2.gameserver.utils.Log;
import org.apache.commons.lang3.ArrayUtils;

public class RideHire
extends Functions
implements INpcHtmlAppendHandler {
    public String getHtmlAppends(Integer n) {
        Player player = this.getSelf();
        if (Config.SERVICES_ALLOW_WYVERN_RIDE) {
            return player.isLangRus() ? "<br>[scripts_services.RideHire:ride_prices|\u0412\u0437\u044f\u0442\u044c \u043d\u0430 \u043f\u0440\u043e\u043a\u0430\u0442 \u0435\u0437\u0434\u043e\u0432\u043e\u0435 \u0436\u0438\u0432\u043e\u0442\u043d\u043e\u0435.]" : "<br>[scripts_services.RideHire:ride_prices|Ride hire mountable pet.]";
        }
        return "";
    }

    private static boolean e() {
        for (Castle castle : ResidenceHolder.getInstance().getResidenceList(Castle.class)) {
            if (castle.getSiegeEvent() == null || !castle.getSiegeEvent().isInProgress()) continue;
            return false;
        }
        return true;
    }

    public void ride_prices() {
        Player player = this.getSelf();
        NpcInstance npcInstance = this.getNpc();
        if (player == null || npcInstance == null) {
            return;
        }
        RideHire.show((String)"scripts/services/ride-prices.htm", (Player)player, (NpcInstance)npcInstance, (Object[])new Object[0]);
    }

    public void ride(String[] stringArray) {
        int n;
        Player player = this.getSelf();
        NpcInstance npcInstance = this.getNpc();
        if (player == null || npcInstance == null || !RideHire.CheckPlayerConditions((Player)player)) {
            return;
        }
        boolean bl = player.isLangRus();
        if (stringArray.length != 3) {
            RideHire.show((String)(bl ? "\u041d\u0435\u043a\u043e\u0440\u0440\u0435\u043a\u0442\u043d\u044b\u0435 \u0434\u0430\u043d\u043d\u044b\u0435" : "Incorrect input"), (Player)player, (NpcInstance)npcInstance, (Object[])new Object[0]);
            return;
        }
        if (!NpcInstance.canBypassCheck((Player)player, (NpcInstance)npcInstance)) {
            return;
        }
        if (player.getActiveWeaponFlagAttachment() != null) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_BOARD_BECAUSE_YOU_DO_NOT_MEET_THE_REQUIREMENTS);
            return;
        }
        if (player.getTransformation() != 0) {
            RideHire.show((String)(bl ? "\u0412\u044b \u043d\u0435 \u043c\u043e\u0436\u0435\u0442\u0435 \u0432\u0437\u044f\u0442\u044c \u043f\u0435\u0442\u0430 \u0432 \u043f\u0440\u043e\u043a\u0430\u0442, \u043f\u043e\u043a\u0430 \u043d\u0430\u0445\u043e\u0434\u0438\u0442\u0435\u0441\u044c \u0432 \u0440\u0435\u0436\u0438\u043c\u0435 \u0442\u0440\u0430\u043d\u0441\u0444\u043e\u0440\u043c\u0430\u0446\u0438\u0438." : "Can't ride while in transformation mode."), (Player)player, (NpcInstance)npcInstance, (Object[])new Object[0]);
            return;
        }
        if (player.getPet() != null || player.isMounted()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_ALREADY_HAVE_A_PET);
            return;
        }
        switch (Integer.parseInt(stringArray[0])) {
            case 1: {
                n = 12621;
                break;
            }
            case 2: {
                n = 12526;
                break;
            }
            default: {
                RideHire.show((String)(bl ? "\u0423 \u043c\u0435\u043d\u044f \u043d\u0435\u0442 \u0442\u0430\u043a\u0438\u0445 \u043f\u0438\u0442\u043e\u043c\u0446\u0435\u0432!" : "Unknown pet."), (Player)player, (NpcInstance)npcInstance, (Object[])new Object[0]);
                return;
            }
        }
        if (!(n != 12621 && n != 12526 || RideHire.e())) {
            RideHire.show((String)(bl ? "\u041f\u0440\u043e\u043a\u0430\u0442 \u0432\u0438\u0432\u0435\u0440\u043d/\u0441\u0442\u0440\u0430\u0439\u0434\u0435\u0440\u043e\u0432 \u043d\u0435 \u0440\u0430\u0431\u043e\u0442\u0430\u0435\u0442 \u0432\u043e \u0432\u0440\u0435\u043c\u044f \u043e\u0441\u0430\u0434\u044b." : "Can't ride wyvern/strider while Siege in progress."), (Player)player, (NpcInstance)npcInstance, (Object[])new Object[0]);
            return;
        }
        Integer n2 = Integer.parseInt(stringArray[1]);
        Long l = Long.parseLong(stringArray[2]);
        if (n2 > 3600) {
            RideHire.show((String)(bl ? "\u0421\u043b\u0438\u0448\u043a\u043e\u043c \u0431\u043e\u043b\u044c\u0448\u043e\u0435 \u0432\u0440\u0435\u043c\u044f." : "Too long time to ride."), (Player)player, (NpcInstance)npcInstance, (Object[])new Object[0]);
            return;
        }
        if (ItemFunctions.getItemCount((Playable)player, (int)Config.SERVICES_WYVERN_ITEM_ID) < l) {
            if (Config.SERVICES_WYVERN_ITEM_ID == 57) {
                player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
            } else {
                player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_ITEM_COUNT);
            }
            return;
        }
        ItemFunctions.removeItem((Playable)player, (int)Config.SERVICES_WYVERN_ITEM_ID, (long)l, (boolean)true);
        Log.service((String)"RideHire", (Player)player, (String)("ride for " + Config.SERVICES_WYVERN_ITEM_ID + " amount " + l));
        this.doLimitedRide(player, n, n2);
    }

    public void doLimitedRide(Player player, Integer n, Integer n2) {
        if (!RideHire.ride((Player)player, (int)n)) {
            return;
        }
        player.sendPacket((IStaticPacket)new SetupGauge((Creature)player, 3, n2 * 1000));
        RideHire.executeTask((Player)player, (String)"services.RideHire", (String)"rideOver", (Object[])new Object[0], (long)(n2 * 1000));
    }

    public void rideOver() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        RideHire.unRide((Player)player);
        this.show(player.isLangRus() ? "\u0412\u0440\u0435\u043c\u044f \u043f\u0440\u043e\u043a\u0430\u0442\u0430 \u0437\u0430\u043a\u043e\u043d\u0447\u0438\u043b\u043e\u0441\u044c. \u041f\u0440\u0438\u0445\u043e\u0434\u0438\u0442\u0435 \u0435\u0449\u0435!" : "Ride time is over.<br><br>Welcome back again!", player);
    }

    public int[] getNpcIds() {
        if (!Config.SERVICES_ALLOW_WYVERN_RIDE) {
            return ArrayUtils.EMPTY_INT_ARRAY;
        }
        return Config.SERVICES_WYVERN_RIDE_NPC_ID;
    }

    public String getAppend(Player player, int n, int n2) {
        RideHire rideHire = new RideHire();
        rideHire.self = player.getRef();
        return rideHire.getHtmlAppends(n2);
    }
}
