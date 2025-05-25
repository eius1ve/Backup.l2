/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.ArrayUtils
 */
package l2.gameserver.network.l2.c2s;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import l2.gameserver.Config;
import l2.gameserver.dao.CharacterBlockDAO;
import l2.gameserver.dao.CharacterDAO;
import l2.gameserver.dao.MailDAO;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Player;
import l2.gameserver.model.World;
import l2.gameserver.model.actor.instances.player.CharacterBlockList;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.mail.Mail;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExNoticePostArrived;
import l2.gameserver.network.l2.s2c.ExReplyWritePost;
import l2.gameserver.network.l2.s2c.ExUnReadMailCount;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.scripts.Functions;
import l2.gameserver.utils.Log;
import org.apache.commons.lang3.ArrayUtils;

public class RequestExSendPost
extends L2GameClientPacket {
    private int qZ;
    private String eg;
    private String dI;
    private String eh;
    private int gT;
    private int[] aP;
    private long[] c;
    private long _price;

    @Override
    protected void readImpl() {
        this.eg = this.readS(35);
        this.qZ = this.readD();
        this.dI = this.readS(127);
        this.eh = this.readS(Short.MAX_VALUE);
        this.gT = this.readD();
        if (this.gT * 12 + 4 > this._buf.remaining() || this.gT > Short.MAX_VALUE || this.gT < 1) {
            this.gT = 0;
            return;
        }
        this.aP = new int[this.gT];
        this.c = new long[this.gT];
        for (int i = 0; i < this.gT; ++i) {
            this.aP[i] = this.readD();
            this.c[i] = this.readQ();
            if (this.c[i] >= 1L && ArrayUtils.indexOf((int[])this.aP, (int)this.aP[i]) >= i) continue;
            this.gT = 0;
            return;
        }
        this._price = this.readQ();
        if (this._price < 0L) {
            this.gT = 0;
            this._price = 0L;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void runImpl() {
        int n;
        long l;
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        if (player.isActionsDisabled()) {
            player.sendActionFailed();
            return;
        }
        if (player.isGM() && this.eg.equalsIgnoreCase("ONLINE_ALL")) {
            HashMap<Integer, Long> hashMap = new HashMap<Integer, Long>();
            if (this.aP != null && this.aP.length > 0) {
                for (int i = 0; i < this.aP.length; ++i) {
                    ItemInstance gameObject = player.getInventory().getItemByObjectId(this.aP[i]);
                    hashMap.put(gameObject.getItemId(), this.c[i]);
                }
            }
            for (Player player2 : GameObjectsStorage.getAllPlayersForIterate()) {
                if (player2 == null || !player2.isOnline()) continue;
                Functions.sendSystemMail(player2, this.dI, this.eh, hashMap);
            }
            player.sendPacket((IStaticPacket)ExReplyWritePost.STATIC_TRUE);
            player.sendPacket((IStaticPacket)SystemMsg.MAIL_SUCCESSFULLY_SENT);
            return;
        }
        if (!Config.ALLOW_MAIL) {
            player.sendMessage(new CustomMessage("mail.Disabled", player, new Object[0]));
            player.sendActionFailed();
            return;
        }
        if (player.isInStoreMode()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_FORWARD_BECAUSE_THE_PRIVATE_SHOP_OR_WORKSHOP_IS_IN_PROGRESS);
            return;
        }
        if (player.isInTrade()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_FORWARD_DURING_AN_EXCHANGE);
            return;
        }
        if (player.getEnchantScroll() != null) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_FORWARD_DURING_AN_ITEM_ENHANCEMENT_OR_ATTRIBUTE_ENHANCEMENT);
            return;
        }
        if (player.getName().equalsIgnoreCase(this.eg)) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_SEND_A_MAIL_TO_YOURSELF);
            return;
        }
        if (this.gT > 0 && !player.isInPeaceZone() && Config.MAIL_ALLOW_AT_PEACE_ZONE) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_FORWARD_IN_A_NONPEACE_ZONE_LOCATION);
            return;
        }
        if (player.isFishing()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_DO_THAT_WHILE_FISHING);
            return;
        }
        if (player.getLevel() < Config.MAIL_MIN_LEVEL_SENDER && !player.isGM()) {
            player.sendMessage(new CustomMessage("YOU_CAN_USE_MAIL_FROM_LV_S1", player, new Object[0]).addNumber(Config.MAIL_MIN_LEVEL_SENDER));
            return;
        }
        long l2 = System.currentTimeMillis();
        if (l2 - (l = player.getVarLong("used_mail_reuse", 0L)) < Config.MAIL_SEND_INTERVAL && !player.isGM()) {
            player.sendMessage(new CustomMessage("YOU_HAVE_S1_SEC_UNTIL_YOU_ARE_ABLE_TO_USE_MAIL", player, new Object[0]).addNumber(TimeUnit.MILLISECONDS.toSeconds(Config.MAIL_SEND_INTERVAL - (l2 - l))));
            return;
        }
        int n2 = player.getVarInt("used_mail_points", 0) + 1;
        int n3 = Config.MAIL_SEND_COUNT_PER_DAY - n2;
        if (n3 < 0 && !player.isGM()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_USED_UP_THE_MAIL_ALLOWANCE_FOR_THE_DAY_THE_MAIL_ALLOWANCE_RESETS_EVERY_DAY_AT_6_30AM);
            return;
        }
        if (this._price > 0L && (!player.getPlayerAccess().UseTrade || player.isTradeBannedByGM())) {
            player.sendPacket((IStaticPacket)SystemMsg.SOME_LINEAGE_II_FEATURES_HAVE_BEEN_LIMITED_FOR_FREE_TRIALS_);
            player.sendActionFailed();
            return;
        }
        if (player.getBlockList().isInBlockList(this.eg)) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_HAVE_BLOCKED_C1).addString(this.eg));
            return;
        }
        if (CharacterBlockList.isInBlockList(this.eg, player)) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.C1_HAS_BLOCKED_YOU_YOU_CANNOT_SEND_MAIL_TO_C1).addString(this.eg));
            return;
        }
        Player player3 = World.getPlayer(this.eg);
        if (player3 != null) {
            n = player3.getObjectId();
            this.eg = player3.getName();
            if (player3.getBlockList().isBlocked(player)) {
                player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.C1_HAS_BLOCKED_YOU_YOU_CANNOT_SEND_MAIL_TO_C1).addString(this.eg));
                return;
            }
        } else {
            n = CharacterDAO.getInstance().getObjectIdByName(this.eg);
            if (n > 0 && CharacterBlockDAO.getInstance().fetchBlockListEntry(n, player.getObjectId()) != null) {
                player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.C1_HAS_BLOCKED_YOU_YOU_CANNOT_SEND_MAIL_TO_C1).addString(this.eg));
                return;
            }
        }
        if (n == 0) {
            player.sendPacket((IStaticPacket)SystemMsg.WHEN_THE_RECIPIENT_DOESNT_EXIST_OR_THE_CHARACTER_HAS_BEEN_DELETED_SENDING_MAIL_IS_NOT_POSSIBLE);
            return;
        }
        int n4 = (this.qZ == 1 ? 12 : 360) * 3600 + (int)(System.currentTimeMillis() / 1000L);
        if (this.gT > 8) {
            player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_ITEM_COUNT);
            return;
        }
        List<Mail> list = MailDAO.getInstance().getSentMailByOwnerId(player.getObjectId());
        if (list.size() >= Config.MAIL_INBOX_LIMIT) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.THE_MAIL_LIMIT_240_HAS_BEEN_EXCEEDED_AND_THIS_CANNOT_BE_FORWARDED));
            return;
        }
        List<Mail> list2 = MailDAO.getInstance().getReceivedMailByOwnerId(n);
        if (list2.size() >= Config.MAIL_INBOX_LIMIT) {
            player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.THE_MAIL_LIMIT_240_OF_THE_OPPONENT_S_CHARACTER_HAS_BEEN_EXCEEDED_AND_THIS_CANNOT_BE_FORWARDED));
            return;
        }
        long l3 = (long)Config.MAIL_SEND_PRICE + (long)this.gT * (long)Config.MAIL_SEND_COUNT_MULTIPLIER;
        ArrayList<ItemInstance> arrayList = new ArrayList<ItemInstance>();
        player.getInventory().writeLock();
        try {
            Object object;
            int n5;
            if (player.getAdena() < l3) {
                player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_FORWARD_BECAUSE_YOU_DONT_HAVE_ENOUGH_ADENA);
                return;
            }
            if (this.gT > 0) {
                for (n5 = 0; n5 < this.gT; ++n5) {
                    object = player.getInventory().getItemByObjectId(this.aP[n5]);
                    if (object != null && ((ItemInstance)object).getCount() >= this.c[n5] && (((ItemInstance)object).getItemId() != 57 || ((ItemInstance)object).getCount() >= this.c[n5] + l3) && ((ItemInstance)object).canBeTraded(player)) continue;
                    player.sendPacket((IStaticPacket)SystemMsg.THE_ITEM_THAT_YOURE_TRYING_TO_SEND_CANNOT_BE_FORWARDED_BECAUSE_IT_ISNT_PROPER);
                    return;
                }
            }
            if (!player.reduceAdena(l3, true)) {
                player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_FORWARD_BECAUSE_YOU_DONT_HAVE_ENOUGH_ADENA);
                return;
            }
            if (this.gT > 0) {
                for (n5 = 0; n5 < this.gT; ++n5) {
                    object = player.getInventory().removeItemByObjectId(this.aP[n5], this.c[n5]);
                    Log.LogItem(player, Log.ItemLog.PostSend, (ItemInstance)object);
                    ((ItemInstance)object).setOwnerId(player.getObjectId());
                    ((ItemInstance)object).setLocation(ItemInstance.ItemLocation.MAIL);
                    ((ItemInstance)object).save();
                    arrayList.add((ItemInstance)object);
                }
            }
        }
        finally {
            player.getInventory().writeUnlock();
        }
        Mail mail = new Mail();
        mail.setSenderId(player.getObjectId());
        mail.setSenderName(player.getName());
        mail.setReceiverId(n);
        mail.setReceiverName(this.eg);
        mail.setTopic(this.dI);
        mail.setBody(this.eh);
        mail.setPrice(this.qZ > 0 ? this._price : 0L);
        mail.setUnread(true);
        mail.setType(Mail.SenderType.NORMAL);
        mail.setExpireTime(n4);
        for (ItemInstance itemInstance : arrayList) {
            mail.addAttachment(itemInstance);
        }
        mail.save();
        player.sendPacket((IStaticPacket)ExReplyWritePost.STATIC_TRUE);
        player.sendPacket((IStaticPacket)SystemMsg.MAIL_SUCCESSFULLY_SENT);
        player.setVar("used_mail_points", n2, -1L);
        player.setVar("used_mail_reuse", l2, -1L);
        if (player3 != null) {
            player3.sendPacket((IStaticPacket)ExNoticePostArrived.STATIC_TRUE);
            player3.sendPacket((IStaticPacket)new ExUnReadMailCount(player3));
            player3.sendPacket((IStaticPacket)SystemMsg.THE_MAIL_HAS_ARRIVED);
        }
    }
}
