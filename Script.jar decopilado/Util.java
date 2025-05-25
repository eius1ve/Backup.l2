/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.Config
 *  l2.gameserver.data.xml.holder.ResidenceHolder
 *  l2.gameserver.model.GameObjectsStorage
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.TeleportLocation
 *  l2.gameserver.model.entity.SevenSigns
 *  l2.gameserver.model.entity.residence.Castle
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.ExPledgeEmblem
 *  l2.gameserver.network.l2.s2c.NpcHtmlMessage
 *  l2.gameserver.network.l2.s2c.SystemMessage
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.Scripts
 *  l2.gameserver.utils.CapchaUtil
 *  l2.gameserver.utils.Location
 */
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.ResidenceHolder;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.TeleportLocation;
import l2.gameserver.model.entity.SevenSigns;
import l2.gameserver.model.entity.residence.Castle;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExPledgeEmblem;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.Scripts;
import l2.gameserver.utils.CapchaUtil;
import l2.gameserver.utils.Location;

public class Util
extends Functions {
    private static final int a = 0x4000000;
    private static final String a = "capacha-code";
    private static final String b = "capacha-time";
    private static final String c = "capacha-success";

    public void Gatekeeper(String[] stringArray) {
        Castle castle;
        int n;
        if (stringArray.length < 4) {
            throw new IllegalArgumentException();
        }
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        long l = Long.parseLong(stringArray[stringArray.length - 1]);
        if (!NpcInstance.canBypassCheck((Player)player, (NpcInstance)player.getLastNpc())) {
            return;
        }
        if (l > 0L && player.getAdena() < l) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
            return;
        }
        if (player.getMountType() == 2) {
            this.show("scripts/wyvern-no.htm", player);
            return;
        }
        int n2 = Integer.parseInt(stringArray[0]);
        int n3 = Integer.parseInt(stringArray[1]);
        int n4 = Integer.parseInt(stringArray[2]);
        int n5 = n = stringArray.length > 4 ? Integer.parseInt(stringArray[3]) : 0;
        if (player.getLastNpc() != null) {
            TeleportLocation[][] teleportLocationArray;
            castle = null;
            TeleportLocation[][] teleportLocationArray2 = teleportLocationArray = (TeleportLocation[][])player.getLastNpc().getTemplate().getTeleportList().getValues((Object[])new TeleportLocation[player.getLastNpc().getTemplate().getTeleportList().size()][]);
            int n6 = teleportLocationArray2.length;
            block0: for (int i = 0; i < n6; ++i) {
                TeleportLocation[] teleportLocationArray3;
                for (TeleportLocation teleportLocation : teleportLocationArray3 = teleportLocationArray2[i]) {
                    if (teleportLocation.getX() != n2 || teleportLocation.getY() != n3 || teleportLocation.getZ() != n4) continue;
                    castle = teleportLocation;
                    break block0;
                }
            }
            if (castle != null) {
                if (castle.getMinLevel() > 0 && player.getLevel() < castle.getMinLevel()) {
                    player.sendMessage(new CustomMessage("Gatekeeper.LevelToLow", player, new Object[]{castle.getMinLevel()}));
                    return;
                }
                if (castle.getMaxLevel() > 0 && player.getLevel() > castle.getMaxLevel()) {
                    player.sendMessage(new CustomMessage("Gatekeeper.LevelToHigh", player, new Object[]{castle.getMaxLevel()}));
                    return;
                }
                if (castle.getKeyItemId() > 0 && player.getInventory().getCountOf(castle.getKeyItemId()) == 0L) {
                    player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_ITEM_COUNT);
                    return;
                }
            }
        }
        if (player.getReflection().isDefault()) {
            Castle castle2 = castle = n > 0 ? (Castle)ResidenceHolder.getInstance().getResidence(Castle.class, n) : null;
            if (castle != null && castle.getSiegeEvent().isInProgress()) {
                player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_TELEPORT_TO_A_VILLAGE_THAT_IS_IN_A_SIEGE);
                return;
            }
        }
        castle = Location.findPointToStay((int)n2, (int)n3, (int)n4, (int)50, (int)100, (int)player.getGeoIndex());
        if (l > 0L) {
            player.reduceAdena(l, true);
        }
        player.teleToLocation((Location)castle);
    }

    public void SSGatekeeper(String[] stringArray) {
        if (stringArray.length < 4) {
            throw new IllegalArgumentException();
        }
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        int n = Integer.parseInt(stringArray[3]);
        if (!NpcInstance.canBypassCheck((Player)player, (NpcInstance)player.getLastNpc())) {
            return;
        }
        if (Config.ALT_ENABLE_SEVEN_SING_TELEPORTER_PROTECTION && n > 0) {
            int n2 = SevenSigns.getInstance().getPlayerCabal(player);
            int n3 = SevenSigns.getInstance().getCurrentPeriod();
            if (n3 == 1 && n2 == 0) {
                this.show("seven_signs/ss_teleporter_q0506_01.htm", player);
                return;
            }
            if (n3 == 3) {
                if (n == 1 && SevenSigns.getInstance().getSealOwner(1) != n2) {
                    this.show("seven_signs/ss_teleporter_q0506_02.htm", player);
                    return;
                }
                if (n == 2 && SevenSigns.getInstance().getSealOwner(2) != n2) {
                    this.show("seven_signs/ss_teleporter_q0506_02.htm", player);
                    return;
                }
                if ((n == 1 || n == 2) && n2 == 0) {
                    this.show("seven_signs/ss_teleporter_q0506_01.htm", player);
                    return;
                }
            }
        }
        player.teleToLocation(Integer.parseInt(stringArray[0]), Integer.parseInt(stringArray[1]), Integer.parseInt(stringArray[2]));
    }

    public void QuestGatekeeper(String[] stringArray) {
        Location location;
        if (stringArray.length < 5) {
            throw new IllegalArgumentException();
        }
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        long l = Long.parseLong(stringArray[3]);
        int n = Integer.parseInt(stringArray[4]);
        if (!NpcInstance.canBypassCheck((Player)player, (NpcInstance)player.getLastNpc())) {
            return;
        }
        int n2 = Integer.parseInt(stringArray[0]);
        int n3 = Integer.parseInt(stringArray[1]);
        int n4 = Integer.parseInt(stringArray[2]);
        if (player.getLastNpc() != null) {
            TeleportLocation[][] teleportLocationArray;
            location = null;
            TeleportLocation[][] teleportLocationArray2 = teleportLocationArray = (TeleportLocation[][])player.getLastNpc().getTemplate().getTeleportList().getValues((Object[])new TeleportLocation[player.getLastNpc().getTemplate().getTeleportList().size()][]);
            int n5 = teleportLocationArray2.length;
            block0: for (int i = 0; i < n5; ++i) {
                TeleportLocation[] teleportLocationArray3;
                for (TeleportLocation teleportLocation : teleportLocationArray3 = teleportLocationArray2[i]) {
                    if (teleportLocation.getX() != n2 || teleportLocation.getY() != n3 || teleportLocation.getZ() != n4) continue;
                    location = teleportLocation;
                    break block0;
                }
            }
            if (location != null) {
                if (location.getMinLevel() > 0 && player.getLevel() < location.getMinLevel()) {
                    player.sendMessage(new CustomMessage("Gatekeeper.LevelToLow", player, new Object[]{location.getMinLevel()}));
                    return;
                }
                if (location.getMaxLevel() > 0 && player.getLevel() > location.getMaxLevel()) {
                    player.sendMessage(new CustomMessage("Gatekeeper.LevelToHigh", player, new Object[]{location.getMaxLevel()}));
                    return;
                }
            }
        }
        if (l == 0L && player.getInventory().getItemByItemId(n) == null) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_REQUIRED_ITEMS);
            return;
        }
        if (l > 0L) {
            if (!player.getInventory().destroyItemByItemId(n, l)) {
                player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_REQUIRED_ITEMS);
                return;
            }
            player.sendPacket((IStaticPacket)SystemMessage.removeItems((int)n, (long)l));
        }
        location = Config.ALT_SPREADING_AFTER_TELEPORT ? Location.findPointToStay((int)n2, (int)n3, (int)n4, (int)20, (int)70, (int)player.getGeoIndex()) : new Location(n2, n3, n4).correctGeoZ();
        player.teleToLocation(location);
    }

    public void ReflectionGatekeeper(String[] stringArray) {
        if (stringArray.length < 5) {
            throw new IllegalArgumentException();
        }
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        player.setReflection(Integer.parseInt(stringArray[4]));
        this.Gatekeeper(stringArray);
    }

    public void TokenJump(String[] stringArray) {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        if (player.getLevel() <= 19 && player.getInventory().getCountOf(8542) > 0L) {
            Util.removeItem((Playable)player, (int)8542, (long)1L);
            this.QuestGatekeeper(stringArray);
        } else {
            this.show("newbiehelper/guide_teleport_over001.htm", player);
        }
    }

    public void NoblessTeleport() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        if (player.isNoble() || Config.ALLOW_NOBLE_TP_TO_ALL) {
            this.show("scripts/noble.htm", player);
        } else {
            this.show("scripts/nobleteleporter-no.htm", player);
        }
    }

    public void TakeNewbieWeaponCoupon() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        if (!Config.ALT_ALLOW_SHADOW_WEAPONS) {
            Util.show((CustomMessage)new CustomMessage("common.Disabled", player, new Object[0]), (Player)player);
            return;
        }
        if (player.getLevel() > 19 || player.getClassId().getLevel() > 1) {
            this.show("newbiehelper/newbie_guide003.htm", player);
            return;
        }
        if (player.getLevel() < 6) {
            this.show("newbiehelper/newbie_guide003.htm", player);
            return;
        }
        if (player.getVarB("newbieweapon")) {
            this.show("newbiehelper/newbie_guide004.htm", player);
            return;
        }
        Util.addItem((Playable)player, (int)7832, (long)5L);
        player.setVar("newbieweapon", "true", -1L);
    }

    public void TakeAdventurersArmorCoupon() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        if (!Config.ALT_ALLOW_SHADOW_WEAPONS) {
            Util.show((CustomMessage)new CustomMessage("common.Disabled", player, new Object[0]), (Player)player);
            return;
        }
        if (player.getLevel() > 39 || player.getClassId().getLevel() > 2) {
            this.show("newbiehelper/newbie_guide014.htm", player);
            return;
        }
        if (player.getLevel() < 20 || player.getClassId().getLevel() < 2) {
            this.show("newbiehelper/newbie_guide014.htm", player);
            return;
        }
        if (player.getVarB("newbiearmor")) {
            this.show("newbiehelper/newbie_guide013.htm", player);
            return;
        }
        Util.addItem((Playable)player, (int)7833, (long)1L);
        player.setVar("newbiearmor", "true", -1L);
    }

    public void enter_dc() {
        Player player = this.getSelf();
        NpcInstance npcInstance = this.getNpc();
        if (player == null || npcInstance == null) {
            return;
        }
        if (!NpcInstance.canBypassCheck((Player)player, (NpcInstance)npcInstance)) {
            return;
        }
        player.setVar("DCBackCoords", player.getLoc().toXYZString(), -1L);
        player.teleToLocation(-114582, -152635, -6742);
    }

    public void exit_dc() {
        Player player = this.getSelf();
        NpcInstance npcInstance = this.getNpc();
        if (player == null || npcInstance == null) {
            return;
        }
        if (!NpcInstance.canBypassCheck((Player)player, (NpcInstance)npcInstance)) {
            return;
        }
        String string = player.getVar("DCBackCoords");
        if (string == null || string.isEmpty()) {
            player.teleToLocation(new Location(43768, -48232, -800), 0);
            return;
        }
        player.teleToLocation(Location.parseLoc((String)string), 0);
        player.unsetVar("DCBackCoords");
    }

    public static void RequestCapcha(String string, Long l, Integer n) {
        Player player = GameObjectsStorage.getAsPlayer((long)l);
        if (player == null || !player.isOnline() || player.isLogoutStarted()) {
            return;
        }
        int n2 = Config.REQUEST_ID;
        if (player.isConnected() && player.getNetConnection() != null) {
            n2 = player.getNetConnection().getServerId();
        }
        int n3 = CapchaUtil.RndCapcha();
        int n4 = CapchaUtil.RndRGB888Color();
        int n5 = CapchaUtil.getId((int)n3) | 0x4000000;
        byte[] byArray = CapchaUtil.getCapchaImage((int)n3, (int)n4);
        player.sendPacket((IStaticPacket[])ExPledgeEmblem.packets((int)n2, (int)-1, (int)n5, (byte[])byArray));
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, null);
        npcHtmlMessage.setFile("capcha.htm");
        npcHtmlMessage = npcHtmlMessage.replace("%SN%", String.valueOf(n2));
        npcHtmlMessage = npcHtmlMessage.replace("%CID%", String.valueOf(n5));
        player.setVar(a, String.valueOf(n3), -1L);
        player.setVar(b, String.valueOf(System.currentTimeMillis() / 1000L + (long)n.intValue()), -1L);
        player.setVar(c, string, -1L);
        player.sendPacket((IStaticPacket)npcHtmlMessage);
        player.sendMessage(new CustomMessage("scripts.Util.CapchaConfirm.RequestCapcha", player, new Object[]{n}));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void CapchaConfirm(String[] stringArray) {
        if (stringArray.length < 1) {
            throw new IllegalArgumentException();
        }
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        String string = player.getVar(a);
        String string2 = player.getVar(b);
        String string3 = player.getVar(c);
        if (string == null) return;
        if (string2 == null) return;
        if (string3 == null) {
            return;
        }
        try {
            int n = Integer.parseInt(string);
            long l = Long.parseLong(string2);
            String string4 = stringArray[0];
            if (l < System.currentTimeMillis() / 1000L) {
                player.sendMessage(new CustomMessage("scripts.Util.CapchaConfirm.TimeExpired", player, new Object[0]));
                return;
            }
            if (!CapchaUtil.IsValidEntry((int)n, (String)string4)) {
                player.sendMessage(new CustomMessage("scripts.Util.CapchaConfirm.WrongCode", player, new Object[0]));
                return;
            }
            Scripts.getInstance().callScripts(player, string3.split(":")[0], string3.split(":")[1]);
            player.sendMessage(new CustomMessage("scripts.Util.CapchaConfirm.Success", player, new Object[0]));
            return;
        }
        catch (Exception exception) {
            return;
        }
        finally {
            try {
                player.unsetVar(a);
                player.unsetVar(b);
                player.unsetVar(c);
            }
            catch (Exception exception) {}
        }
    }
}
