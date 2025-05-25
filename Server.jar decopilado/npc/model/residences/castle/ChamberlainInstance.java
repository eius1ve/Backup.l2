/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.dao.CastleDamageZoneDAO
 *  l2.gameserver.dao.CastleDoorUpgradeDAO
 *  l2.gameserver.data.xml.holder.ResidenceHolder
 *  l2.gameserver.instancemanager.CastleManorManager
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.entity.SevenSigns
 *  l2.gameserver.model.entity.events.GlobalEvent
 *  l2.gameserver.model.entity.events.objects.CastleDamageZoneObject
 *  l2.gameserver.model.entity.events.objects.DoorObject
 *  l2.gameserver.model.entity.residence.Castle
 *  l2.gameserver.model.entity.residence.Residence
 *  l2.gameserver.model.instances.DoorInstance
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.pledge.Privilege
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.CastleSiegeInfo
 *  l2.gameserver.network.l2.s2c.ExShowCropInfo
 *  l2.gameserver.network.l2.s2c.ExShowCropSetting
 *  l2.gameserver.network.l2.s2c.ExShowManorDefaultInfo
 *  l2.gameserver.network.l2.s2c.ExShowSeedInfo
 *  l2.gameserver.network.l2.s2c.ExShowSeedSetting
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.network.l2.s2c.NpcHtmlMessage
 *  l2.gameserver.templates.npc.NpcTemplate
 *  l2.gameserver.utils.ItemFunctions
 *  l2.gameserver.utils.Log
 *  l2.gameserver.utils.ReflectionUtils
 */
package npc.model.residences.castle;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import l2.gameserver.dao.CastleDamageZoneDAO;
import l2.gameserver.dao.CastleDoorUpgradeDAO;
import l2.gameserver.data.xml.holder.ResidenceHolder;
import l2.gameserver.instancemanager.CastleManorManager;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.entity.SevenSigns;
import l2.gameserver.model.entity.events.GlobalEvent;
import l2.gameserver.model.entity.events.objects.CastleDamageZoneObject;
import l2.gameserver.model.entity.events.objects.DoorObject;
import l2.gameserver.model.entity.residence.Castle;
import l2.gameserver.model.entity.residence.Residence;
import l2.gameserver.model.instances.DoorInstance;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.pledge.Privilege;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.CastleSiegeInfo;
import l2.gameserver.network.l2.s2c.ExShowCropInfo;
import l2.gameserver.network.l2.s2c.ExShowCropSetting;
import l2.gameserver.network.l2.s2c.ExShowManorDefaultInfo;
import l2.gameserver.network.l2.s2c.ExShowSeedInfo;
import l2.gameserver.network.l2.s2c.ExShowSeedSetting;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.templates.npc.NpcTemplate;
import l2.gameserver.utils.ItemFunctions;
import l2.gameserver.utils.Log;
import l2.gameserver.utils.ReflectionUtils;
import npc.model.residences.ResidenceManager;

public class ChamberlainInstance
extends ResidenceManager {
    private static final int HP = 300;
    private static final int HQ = 1000;
    private static final int HR = 10;
    private static final String gW = "SSQTicketBuyCount";
    private static final String gX = "residence2/castle/chamberlain_saius049.htm";
    private static final String gY = "residence2/castle/SSQ_NotEnoughTicket.htm";
    private static final String gZ = "residence2/castle/SSQ_NotDawnOrEvent.htm";
    private static final String ha = "residence2/castle/chamberlain_saius021.htm";
    private static final String hb = "residence2/castle/SSQ_SellDawnTicket.htm";
    private static final String hc = "residence2/castle/chamberlain_saius063.htm";

    public ChamberlainInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    @Override
    protected void setDialogs() {
        this._mainDialog = "castle/chamberlain/chamberlain.htm";
        this._failDialog = "castle/chamberlain/chamberlain-notlord.htm";
        this._siegeDialog = this._mainDialog;
    }

    @Override
    public void onBypassFeedback(Player player, String string) {
        if (!ChamberlainInstance.canBypassCheck((Player)player, (NpcInstance)this)) {
            return;
        }
        int n = this.getCond(player);
        if (n != 2) {
            return;
        }
        StringTokenizer stringTokenizer = new StringTokenizer(string, " ");
        String string2 = stringTokenizer.nextToken();
        String string3 = "";
        if (stringTokenizer.countTokens() >= 1) {
            string3 = stringTokenizer.nextToken();
        }
        Castle castle = this.getCastle();
        if (string2.equalsIgnoreCase("viewSiegeInfo")) {
            if (!this.isHaveRigths(player, 262144)) {
                player.sendPacket((IStaticPacket)SystemMsg.YOU_ARE_NOT_AUTHORIZED_TO_DO_THAT);
                return;
            }
            player.sendPacket((IStaticPacket)new CastleSiegeInfo(castle, player));
        } else if (string2.equalsIgnoreCase("ssq_selldawnticket")) {
            NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, (NpcInstance)this);
            if (!player.isClanLeader()) {
                this.showChatWindow(player, hc, new Object[0]);
                return;
            }
            if (!this.r(player)) {
                return;
            }
            if (SevenSigns.getInstance().getCurrentPeriod() != 1 || SevenSigns.getInstance().getPlayerCabal(player) != 2) {
                this.showChatWindow(player, gZ, new Object[0]);
                return;
            }
            if (SevenSigns.getInstance().getCurrentPeriod() == 1 && SevenSigns.getInstance().getPlayerCabal(player) == 2) {
                if (player.getVarInt(gW, 0) < 30) {
                    npcHtmlMessage.setFile(hb);
                    npcHtmlMessage.replace("<?DawnTicketLeft?>", String.valueOf(300 - player.getVarInt(gW, 0) * 10));
                    npcHtmlMessage.replace("<?DawnTicketBundle?>", String.valueOf(10));
                    npcHtmlMessage.replace("<?DawnTicketPrice?>", String.valueOf(10000));
                    player.sendPacket((IStaticPacket)npcHtmlMessage);
                } else {
                    this.showChatWindow(player, gY, new Object[0]);
                }
            }
        } else if (string2.equalsIgnoreCase("ask260")) {
            if (!player.isClanLeader()) {
                this.showChatWindow(player, hc, new Object[0]);
                return;
            }
            if (!this.r(player)) {
                return;
            }
            if (SevenSigns.getInstance().getCurrentPeriod() != 1 || SevenSigns.getInstance().getPlayerCabal(player) != 2) {
                this.showChatWindow(player, gZ, new Object[0]);
                return;
            }
            if (SevenSigns.getInstance().getCurrentPeriod() == 1 && SevenSigns.getInstance().getPlayerCabal(player) == 2 && player.getVarInt(gW, 0) < 30) {
                if (player.getAdena() >= 10000L) {
                    ItemFunctions.removeItem((Playable)player, (int)57, (long)10000L, (boolean)true);
                    ItemFunctions.addItem((Playable)player, (int)6388, (long)10L, (boolean)true);
                    player.setVar(gW, player.getVarInt(gW, 0) + 1, -1L);
                } else {
                    this.showChatWindow(player, gX, new Object[0]);
                }
            }
        } else if (string2.equalsIgnoreCase("ManageTreasure")) {
            if (!player.isClanLeader()) {
                player.sendPacket((IStaticPacket)SystemMsg.YOU_ARE_NOT_AUTHORIZED_TO_DO_THAT);
                return;
            }
            NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, (NpcInstance)this);
            npcHtmlMessage.setFile("castle/chamberlain/chamberlain-castlevault.htm");
            npcHtmlMessage.replace("%Treasure%", String.valueOf(castle.getTreasury()));
            npcHtmlMessage.replace("%CollectedShops%", String.valueOf(castle.getCollectedShops()));
            npcHtmlMessage.replace("%CollectedSeed%", String.valueOf(castle.getCollectedSeed()));
            player.sendPacket((IStaticPacket)npcHtmlMessage);
        } else if (string2.equalsIgnoreCase("TakeTreasure")) {
            if (!player.isClanLeader()) {
                player.sendPacket((IStaticPacket)SystemMsg.YOU_ARE_NOT_AUTHORIZED_TO_DO_THAT);
                return;
            }
            if (!string3.equals("")) {
                long l = Long.parseLong(string3);
                if (castle.getTreasury() < l) {
                    NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, (NpcInstance)this);
                    npcHtmlMessage.setFile("castle/chamberlain/chamberlain-havenottreasure.htm");
                    npcHtmlMessage.replace("%Treasure%", String.valueOf(castle.getTreasury()));
                    npcHtmlMessage.replace("%Requested%", String.valueOf(l));
                    player.sendPacket((IStaticPacket)npcHtmlMessage);
                    return;
                }
                if (l > 0L) {
                    castle.addToTreasuryNoTax(-l, false, false);
                    Log.add((String)(castle.getName() + "|" + -l + "|CastleChamberlain"), (String)"treasury");
                    player.addAdena(l);
                }
            }
            NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, (NpcInstance)this);
            npcHtmlMessage.setFile("castle/chamberlain/chamberlain-castlevault.htm");
            npcHtmlMessage.replace("%Treasure%", String.valueOf(castle.getTreasury()));
            npcHtmlMessage.replace("%CollectedShops%", String.valueOf(castle.getCollectedShops()));
            npcHtmlMessage.replace("%CollectedSeed%", String.valueOf(castle.getCollectedSeed()));
            player.sendPacket((IStaticPacket)npcHtmlMessage);
        } else if (string2.equalsIgnoreCase("PutTreasure")) {
            if (!string3.equals("")) {
                long l = Long.parseLong(string3);
                if (l > player.getAdena()) {
                    player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
                    return;
                }
                if (l > 0L) {
                    castle.addToTreasuryNoTax(l, false, false);
                    Log.add((String)(castle.getName() + "|" + l + "|CastleChamberlain"), (String)"treasury");
                    player.reduceAdena(l, true);
                }
            }
            NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, (NpcInstance)this);
            npcHtmlMessage.setFile("castle/chamberlain/chamberlain-castlevault.htm");
            npcHtmlMessage.replace("%Treasure%", String.valueOf(castle.getTreasury()));
            npcHtmlMessage.replace("%CollectedShops%", String.valueOf(castle.getCollectedShops()));
            npcHtmlMessage.replace("%CollectedSeed%", String.valueOf(castle.getCollectedSeed()));
            player.sendPacket((IStaticPacket)npcHtmlMessage);
        } else if (string2.equalsIgnoreCase("manor")) {
            if (!this.isHaveRigths(player, 131072)) {
                player.sendPacket((IStaticPacket)SystemMsg.YOU_ARE_NOT_AUTHORIZED_TO_DO_THAT);
                return;
            }
            Object object = "";
            if (CastleManorManager.getInstance().isDisabled()) {
                object = "npcdefault.htm";
            } else {
                int n2 = Integer.parseInt(string3);
                switch (n2) {
                    case 0: {
                        object = "castle/chamberlain/manor/manor.htm";
                        break;
                    }
                    case 4: {
                        object = "castle/chamberlain/manor/manor_help00" + stringTokenizer.nextToken() + ".htm";
                        break;
                    }
                    default: {
                        object = "castle/chamberlain/chamberlain-no.htm";
                    }
                }
            }
            if (((String)object).length() > 0) {
                NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, (NpcInstance)this);
                npcHtmlMessage.setFile((String)object);
                player.sendPacket((IStaticPacket)npcHtmlMessage);
            }
        } else if (string2.startsWith("manor_menu_select")) {
            if (!this.isHaveRigths(player, 131072)) {
                player.sendPacket((IStaticPacket)SystemMsg.YOU_ARE_NOT_AUTHORIZED_TO_DO_THAT);
                return;
            }
            if (CastleManorManager.getInstance().isUnderMaintenance()) {
                player.sendPacket((IStaticPacket)SystemMsg.THE_MANOR_SYSTEM_IS_CURRENTLY_UNDER_MAINTENANCE);
                player.sendActionFailed();
                return;
            }
            String string4 = string2.substring(string2.indexOf("?") + 1);
            StringTokenizer stringTokenizer2 = new StringTokenizer(string4, "&");
            int n3 = Integer.parseInt(stringTokenizer2.nextToken().split("=")[1]);
            int n4 = Integer.parseInt(stringTokenizer2.nextToken().split("=")[1]);
            int n5 = Integer.parseInt(stringTokenizer2.nextToken().split("=")[1]);
            int n6 = n4 == -1 ? castle.getId() : n4;
            switch (n3) {
                case 3: {
                    if (n5 == 1 && !((Castle)ResidenceHolder.getInstance().getResidence(Castle.class, n6)).isNextPeriodApproved()) {
                        player.sendPacket((IStaticPacket)new ExShowSeedInfo(n6, Collections.emptyList()));
                        break;
                    }
                    player.sendPacket((IStaticPacket)new ExShowSeedInfo(n6, ((Castle)ResidenceHolder.getInstance().getResidence(Castle.class, n6)).getSeedProduction(n5)));
                    break;
                }
                case 4: {
                    if (n5 == 1 && !((Castle)ResidenceHolder.getInstance().getResidence(Castle.class, n6)).isNextPeriodApproved()) {
                        player.sendPacket((IStaticPacket)new ExShowCropInfo(n6, Collections.emptyList()));
                        break;
                    }
                    player.sendPacket((IStaticPacket)new ExShowCropInfo(n6, ((Castle)ResidenceHolder.getInstance().getResidence(Castle.class, n6)).getCropProcure(n5)));
                    break;
                }
                case 5: {
                    player.sendPacket((IStaticPacket)new ExShowManorDefaultInfo());
                    break;
                }
                case 7: {
                    if (castle.isNextPeriodApproved()) {
                        player.sendPacket((IStaticPacket)SystemMsg.A_MANOR_CANNOT_BE_SET_UP_BETWEEN_430_AM_AND_8_PM);
                        break;
                    }
                    player.sendPacket((IStaticPacket)new ExShowSeedSetting(castle.getId()));
                    break;
                }
                case 8: {
                    if (castle.isNextPeriodApproved()) {
                        player.sendPacket((IStaticPacket)SystemMsg.A_MANOR_CANNOT_BE_SET_UP_BETWEEN_430_AM_AND_8_PM);
                        break;
                    }
                    player.sendPacket((IStaticPacket)new ExShowCropSetting(castle.getId()));
                }
            }
        } else if (string2.equalsIgnoreCase("operate_door")) {
            if (!this.isHaveRigths(player, 65536)) {
                player.sendPacket((IStaticPacket)SystemMsg.YOU_ARE_NOT_AUTHORIZED_TO_DO_THAT);
                return;
            }
            if (castle.getSiegeEvent().isInProgress()) {
                this.showChatWindow(player, ha, new Object[0]);
                return;
            }
            if (!string3.equals("")) {
                boolean bl;
                boolean bl2 = bl = Integer.parseInt(string3) == 1;
                while (stringTokenizer.hasMoreTokens()) {
                    DoorInstance doorInstance = ReflectionUtils.getDoor((int)Integer.parseInt(stringTokenizer.nextToken()));
                    if (bl) {
                        doorInstance.openMe(player, true);
                        continue;
                    }
                    doorInstance.closeMe(player, true);
                }
            }
            NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, (NpcInstance)this);
            npcHtmlMessage.setFile("castle/chamberlain/" + this.getTemplate().npcId + "-d.htm");
            player.sendPacket((IStaticPacket)npcHtmlMessage);
        } else if (string2.equalsIgnoreCase("tax_set")) {
            if (!this.isHaveRigths(player, 0x200000)) {
                player.sendPacket((IStaticPacket)SystemMsg.YOU_ARE_NOT_AUTHORIZED_TO_DO_THAT);
                return;
            }
            if (!string3.equals("")) {
                int n7 = 15;
                if (SevenSigns.getInstance().getSealOwner(3) == 1) {
                    n7 = 5;
                } else if (SevenSigns.getInstance().getSealOwner(3) == 2) {
                    n7 = 25;
                }
                int n8 = Integer.parseInt(string3);
                if (n8 < 0 || n8 > n7) {
                    NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, (NpcInstance)this);
                    npcHtmlMessage.setFile("castle/chamberlain/chamberlain-hightax.htm");
                    npcHtmlMessage.replace("%CurrentTax%", String.valueOf(castle.getTaxPercent()));
                    player.sendPacket((IStaticPacket)npcHtmlMessage);
                    return;
                }
                castle.setTaxPercent(player, n8);
            }
            NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, (NpcInstance)this);
            npcHtmlMessage.setFile("castle/chamberlain/chamberlain-settax.htm");
            npcHtmlMessage.replace("%CurrentTax%", String.valueOf(castle.getTaxPercent()));
            player.sendPacket((IStaticPacket)npcHtmlMessage);
        } else if (string2.equalsIgnoreCase("upgrade_castle")) {
            if (!this.r(player)) {
                return;
            }
            this.showChatWindow(player, "castle/chamberlain/chamberlain-upgrades.htm", new Object[0]);
        } else if (string2.equalsIgnoreCase("reinforce")) {
            if (!this.r(player)) {
                return;
            }
            NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, (NpcInstance)this);
            npcHtmlMessage.setFile("castle/chamberlain/doorStrengthen-" + castle.getName() + ".htm");
            player.sendPacket((IStaticPacket)npcHtmlMessage);
        } else if (string2.equalsIgnoreCase("trap_select")) {
            if (!this.r(player)) {
                return;
            }
            NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, (NpcInstance)this);
            npcHtmlMessage.setFile("castle/chamberlain/trap_select-" + castle.getName() + ".htm");
            player.sendPacket((IStaticPacket)npcHtmlMessage);
        } else if (string2.equalsIgnoreCase("buy_trap")) {
            if (!this.r(player)) {
                return;
            }
            if (castle.getSiegeEvent().getObjects("bought_zones").contains(string3)) {
                NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, (NpcInstance)this);
                npcHtmlMessage.setFile("castle/chamberlain/trapAlready.htm");
                player.sendPacket((IStaticPacket)npcHtmlMessage);
                return;
            }
            List list = castle.getSiegeEvent().getObjects(string3);
            long l = 0L;
            for (CastleDamageZoneObject castleDamageZoneObject : list) {
                l += castleDamageZoneObject.getPrice();
            }
            l = ChamberlainInstance.d(l);
            if (player.getClan().getAdenaCount() < l) {
                player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
                return;
            }
            player.getClan().getWarehouse().destroyItemByItemId(57, l);
            castle.getSiegeEvent().addObject("bought_zones", (Serializable)((Object)string3));
            CastleDamageZoneDAO.getInstance().insert((Residence)castle, string3);
            NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, (NpcInstance)this);
            npcHtmlMessage.setFile("castle/chamberlain/trapSuccess.htm");
            player.sendPacket((IStaticPacket)npcHtmlMessage);
        } else if (string2.equalsIgnoreCase("door_manage")) {
            if (!this.isHaveRigths(player, 65536)) {
                player.sendPacket((IStaticPacket)SystemMsg.YOU_ARE_NOT_AUTHORIZED_TO_DO_THAT);
                return;
            }
            if (castle.getSiegeEvent().isInProgress()) {
                this.showChatWindow(player, ha, new Object[0]);
                return;
            }
            NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, (NpcInstance)this);
            npcHtmlMessage.setFile("castle/chamberlain/doorManage.htm");
            npcHtmlMessage.replace("%id%", string3);
            npcHtmlMessage.replace("%type%", stringTokenizer.nextToken());
            player.sendPacket((IStaticPacket)npcHtmlMessage);
        } else if (string2.equalsIgnoreCase("upgrade_door_confirm")) {
            if (!this.isHaveRigths(player, 262144)) {
                player.sendPacket((IStaticPacket)SystemMsg.YOU_ARE_NOT_AUTHORIZED_TO_DO_THAT);
                return;
            }
            int n9 = Integer.parseInt(string3);
            int n10 = Integer.parseInt(stringTokenizer.nextToken());
            int n11 = Integer.parseInt(stringTokenizer.nextToken());
            long l = this.a(n10, n11);
            NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, (NpcInstance)this);
            npcHtmlMessage.setFile("castle/chamberlain/doorConfirm.htm");
            npcHtmlMessage.replace("%id%", String.valueOf(n9));
            npcHtmlMessage.replace("%level%", String.valueOf(n11));
            npcHtmlMessage.replace("%type%", String.valueOf(n10));
            npcHtmlMessage.replace("%price%", String.valueOf(l));
            player.sendPacket((IStaticPacket)npcHtmlMessage);
        } else if (string2.equalsIgnoreCase("upgrade_door")) {
            if (!this.r(player)) {
                return;
            }
            int n12 = Integer.parseInt(string3);
            int n13 = Integer.parseInt(stringTokenizer.nextToken());
            int n14 = Integer.parseInt(stringTokenizer.nextToken());
            long l = this.a(n13, n14);
            List list = castle.getSiegeEvent().getObjects("doors");
            DoorObject doorObject = null;
            for (DoorObject doorObject2 : list) {
                if (doorObject2.getUId() != n12) continue;
                doorObject = doorObject2;
                break;
            }
            DoorInstance doorInstance = doorObject.getDoor();
            int n15 = (doorInstance.getMaxHp() - doorInstance.getUpgradeHp()) * n14 - doorInstance.getMaxHp();
            if (l == 0L || n15 < 0) {
                player.sendMessage(new CustomMessage("common.Error", player, new Object[0]));
                return;
            }
            if (doorInstance.getUpgradeHp() >= n15) {
                int n16 = doorInstance.getUpgradeHp() / (doorInstance.getMaxHp() - doorInstance.getUpgradeHp()) + 1;
                NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, (NpcInstance)this);
                npcHtmlMessage.setFile("castle/chamberlain/doorAlready.htm");
                npcHtmlMessage.replace("%level%", String.valueOf(n16));
                player.sendPacket((IStaticPacket)npcHtmlMessage);
                return;
            }
            if (player.getClan().getAdenaCount() < l) {
                player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
                return;
            }
            player.getClan().getWarehouse().destroyItemByItemId(57, l);
            player.sendMessage(new CustomMessage("npc.model.ChamberlainInstance.BuildReinforced", player, new Object[0]));
            doorObject.setUpgradeValue((GlobalEvent)castle.getSiegeEvent(), n15);
            CastleDoorUpgradeDAO.getInstance().insert(doorInstance.getDoorId(), n15);
        } else if (string2.equalsIgnoreCase("report")) {
            if (!this.isHaveRigths(player, 524288)) {
                player.sendPacket((IStaticPacket)SystemMsg.YOU_ARE_NOT_AUTHORIZED_TO_DO_THAT);
                return;
            }
            NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, (NpcInstance)this);
            npcHtmlMessage.setFile("castle/chamberlain/chamberlain-report.htm");
            npcHtmlMessage.replace("%FeudName%", castle.getName());
            npcHtmlMessage.replace("%CharClan%", player.getClan().getName());
            npcHtmlMessage.replace("%CharName%", player.getName());
            if (SevenSigns.getInstance().getCurrentPeriod() == 1) {
                npcHtmlMessage.replace("%SSPeriod%", new CustomMessage("ChamberlainInstance.NpcString.COMPETITION", player, new Object[0]).toString());
            } else if (SevenSigns.getInstance().getCurrentPeriod() == 3) {
                npcHtmlMessage.replace("%SSPeriod%", new CustomMessage("ChamberlainInstance.NpcString.SEAL_VALIDATION", player, new Object[0]).toString());
            } else {
                npcHtmlMessage.replace("%SSPeriod%", new CustomMessage("ChamberlainInstance.NpcString.PREPARATION", player, new Object[0]).toString());
            }
            switch (SevenSigns.getInstance().getSealOwner(1)) {
                case 1: {
                    npcHtmlMessage.replace("%Avarice%", new CustomMessage("SevenSigns.NpcString.DUSK", player, new Object[0]).toString());
                    break;
                }
                case 2: {
                    npcHtmlMessage.replace("%Avarice%", new CustomMessage("SevenSigns.NpcString.DAWN", player, new Object[0]).toString());
                    break;
                }
                case 0: {
                    npcHtmlMessage.replace("%Avarice%", new CustomMessage("SevenSigns.NpcString.NO_OWNER", player, new Object[0]).toString());
                }
            }
            switch (SevenSigns.getInstance().getSealOwner(2)) {
                case 1: {
                    npcHtmlMessage.replace("%Revelation%", new CustomMessage("SevenSigns.NpcString.DUSK", player, new Object[0]).toString());
                    break;
                }
                case 2: {
                    npcHtmlMessage.replace("%Revelation%", new CustomMessage("SevenSigns.NpcString.DAWN", player, new Object[0]).toString());
                    break;
                }
                case 0: {
                    npcHtmlMessage.replace("%Revelation%", new CustomMessage("SevenSigns.NpcString.NO_OWNER", player, new Object[0]).toString());
                }
            }
            switch (SevenSigns.getInstance().getSealOwner(3)) {
                case 1: {
                    npcHtmlMessage.replace("%Strife%", new CustomMessage("SevenSigns.NpcString.DUSK", player, new Object[0]).toString());
                    break;
                }
                case 2: {
                    npcHtmlMessage.replace("%Strife%", new CustomMessage("SevenSigns.NpcString.DAWN", player, new Object[0]).toString());
                    break;
                }
                case 0: {
                    npcHtmlMessage.replace("%Strife%", new CustomMessage("SevenSigns.NpcString.NO_OWNER", player, new Object[0]).toString());
                }
            }
            player.sendPacket((IStaticPacket)npcHtmlMessage);
        } else if (string2.equalsIgnoreCase("Crown")) {
            if (!player.isClanLeader()) {
                player.sendPacket((IStaticPacket)SystemMsg.YOU_ARE_NOT_AUTHORIZED_TO_DO_THAT);
                return;
            }
            if (player.getInventory().getItemByItemId(6841) == null) {
                player.getInventory().addItem(ItemFunctions.createItem((int)6841));
                NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, (NpcInstance)this);
                npcHtmlMessage.setFile("castle/chamberlain/chamberlain-givecrown.htm");
                npcHtmlMessage.replace("%CharName%", player.getName());
                npcHtmlMessage.replace("%FeudName%", castle.getName());
                player.sendPacket((IStaticPacket)npcHtmlMessage);
            } else {
                NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, (NpcInstance)this);
                npcHtmlMessage.setFile("castle/chamberlain/alreadyhavecrown.htm");
                player.sendPacket((IStaticPacket)npcHtmlMessage);
            }
        } else if (string2.equalsIgnoreCase("manageFunctions")) {
            if (!player.hasPrivilege(Privilege.CS_FS_SET_FUNCTIONS)) {
                this.showChatWindow(player, hc, new Object[0]);
            } else {
                this.showChatWindow(player, "residence2/castle/chamberlain_saius065.htm", new Object[0]);
            }
        } else if (string2.equalsIgnoreCase("manageSiegeFunctions")) {
            if (!player.hasPrivilege(Privilege.CS_FS_SET_FUNCTIONS)) {
                this.showChatWindow(player, hc, new Object[0]);
            } else if (SevenSigns.getInstance().getCurrentPeriod() != 3) {
                this.showChatWindow(player, "residence2/castle/chamberlain_saius068.htm", new Object[0]);
            } else {
                this.showChatWindow(player, "residence2/castle/chamberlain_saius052.htm", new Object[0]);
            }
        } else if (string2.equalsIgnoreCase("items")) {
            NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, (NpcInstance)this);
            npcHtmlMessage.setFile("residence2/castle/chamberlain_saius064.htm");
            npcHtmlMessage.replace("%npcId%", String.valueOf(this.getNpcId()));
            player.sendPacket((IStaticPacket)npcHtmlMessage);
        } else if (string2.equalsIgnoreCase("default")) {
            NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, (NpcInstance)this);
            npcHtmlMessage.setFile("castle/chamberlain/chamberlain.htm");
            player.sendPacket((IStaticPacket)npcHtmlMessage);
        } else {
            super.onBypassFeedback(player, string);
        }
    }

    @Override
    protected int getCond(Player player) {
        if (player.isGM()) {
            return 2;
        }
        Castle castle = this.getCastle();
        if (castle != null && castle.getId() > 0 && player.getClan() != null) {
            if (castle.getSiegeEvent().isInProgress()) {
                return 1;
            }
            if (castle.getOwnerId() == player.getClanId()) {
                if (player.isClanLeader()) {
                    return 2;
                }
                if (this.isHaveRigths(player, 65536) || this.isHaveRigths(player, 131072) || this.isHaveRigths(player, 262144) || this.isHaveRigths(player, 524288) || this.isHaveRigths(player, 0x100000) || this.isHaveRigths(player, 0x200000) || this.isHaveRigths(player, 0x400000) || this.isHaveRigths(player, 0x7FFFFE)) {
                    return 2;
                }
            }
        }
        return 0;
    }

    private long a(int n, int n2) {
        int n3 = 0;
        block0 : switch (n) {
            case 1: {
                switch (n2) {
                    case 2: {
                        n3 = 3000000;
                        break;
                    }
                    case 3: {
                        n3 = 4000000;
                        break;
                    }
                    case 5: {
                        n3 = 5000000;
                    }
                }
                break;
            }
            case 2: {
                switch (n2) {
                    case 2: {
                        n3 = 750000;
                        break;
                    }
                    case 3: {
                        n3 = 900000;
                        break;
                    }
                    case 5: {
                        n3 = 1000000;
                    }
                }
                break;
            }
            case 3: {
                switch (n2) {
                    case 2: {
                        n3 = 1600000;
                        break block0;
                    }
                    case 3: {
                        n3 = 1800000;
                        break block0;
                    }
                    case 5: {
                        n3 = 2000000;
                    }
                }
            }
        }
        return ChamberlainInstance.d(n3);
    }

    private static long d(long l) {
        int n = 80;
        int n2 = 100;
        int n3 = 300;
        switch (SevenSigns.getInstance().getSealOwner(3)) {
            case 1: {
                l = l * (long)n3 / 100L;
                break;
            }
            case 2: {
                l = l * (long)n / 100L;
                break;
            }
            default: {
                l = l * (long)n2 / 100L;
            }
        }
        return l;
    }

    @Override
    protected Residence getResidence() {
        return this.getCastle();
    }

    @Override
    public L2GameServerPacket decoPacket() {
        return null;
    }

    @Override
    protected int getPrivUseFunctions() {
        return 524288;
    }

    @Override
    protected int getPrivSetFunctions() {
        return 0x7FFFFE;
    }

    @Override
    protected int getPrivDismiss() {
        return 0x100000;
    }

    @Override
    protected int getPrivDoors() {
        return 65536;
    }

    private boolean r(Player player) {
        Castle castle = this.getCastle();
        if (!player.hasPrivilege(Privilege.CS_FS_SIEGE_WAR)) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_ARE_NOT_AUTHORIZED_TO_DO_THAT);
            return false;
        }
        if (castle.getSiegeEvent().isInProgress()) {
            this.showChatWindow(player, ha, new Object[0]);
            return false;
        }
        return true;
    }

    protected boolean canInteractWithKarmaPlayer() {
        return true;
    }

    protected boolean canInteractWithCursedWeaponPlayer() {
        return true;
    }
}
