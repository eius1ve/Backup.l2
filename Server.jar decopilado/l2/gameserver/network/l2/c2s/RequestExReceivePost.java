/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.commons.dao.JdbcEntityState;
import l2.commons.math.SafeMath;
import l2.gameserver.Config;
import l2.gameserver.dao.MailDAO;
import l2.gameserver.model.World;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ExShowReceivedPostList;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.utils.ItemFunctions;
import l2.gameserver.utils.Log;

public class RequestExReceivePost
extends L2GameClientPacket {
    private int qM;

    @Override
    protected void readImpl() {
        this.qM = this.readD();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    protected void runImpl() {
        block27: {
            block26: {
                block25: {
                    block24: {
                        block23: {
                            block22: {
                                var1_1 = ((GameClient)this.getClient()).getActiveChar();
                                if (var1_1 == null) {
                                    return;
                                }
                                if (var1_1.isActionsDisabled()) {
                                    var1_1.sendActionFailed();
                                    return;
                                }
                                if (var1_1.isInStoreMode()) {
                                    var1_1.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_RECEIVE_BECAUSE_THE_PRIVATE_SHOP_OR_WORKSHOP_IS_IN_PROGRESS);
                                    return;
                                }
                                if (var1_1.isInTrade()) {
                                    var1_1.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_RECEIVE_DURING_AN_EXCHANGE);
                                    return;
                                }
                                if (var1_1.isFishing()) {
                                    var1_1.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_DO_THAT_WHILE_FISHING);
                                    return;
                                }
                                if (var1_1.getEnchantScroll() != null) {
                                    var1_1.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_RECEIVE_DURING_AN_ITEM_ENHANCEMENT_OR_ATTRIBUTE_ENHANCEMENT);
                                    return;
                                }
                                var2_2 = MailDAO.getInstance().getReceivedMailByMailId(var1_1.getObjectId(), this.qM);
                                if (var2_2 == null) break block27;
                                var1_1.getInventory().writeLock();
                                var3_3 = var2_2.getAttachments();
                                if (var3_3.size() <= 0 || var1_1.isInPeaceZone() || !Config.MAIL_ALLOW_AT_PEACE_ZONE) break block22;
                                var1_1.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_RECEIVE_IN_A_NONPEACE_ZONE_LOCATION);
                                var1_1.getInventory().writeUnlock();
                                return;
                            }
                            var5_5 = var3_3;
                            // MONITORENTER : var3_3
                            if (!var2_2.getAttachments().isEmpty()) break block23;
                            // MONITOREXIT : var5_5
                            var1_1.getInventory().writeUnlock();
                            return;
                        }
                        var4_6 = var2_2.getAttachments().toArray(new ItemInstance[var3_3.size()]);
                        var6_7 = 0;
                        var7_8 = 0L;
                        for (ItemInstance var12_14 : var4_6) {
                            var7_8 = SafeMath.addAndCheck(var7_8, SafeMath.mulAndCheck(var12_14.getCount(), (long)var12_14.getTemplate().getWeight()));
                            if (var12_14.getTemplate().isStackable() && var1_1.getInventory().getItemByItemId(var12_14.getItemId()) != null) continue;
                            ++var6_7;
                        }
                        if (var1_1.getInventory().validateWeight(var7_8)) break block24;
                        var1_1.sendPacket((IStaticPacket)SystemMsg.YOU_COULD_NOT_RECEIVE_BECAUSE_YOUR_INVENTORY_IS_FULL);
                        // MONITOREXIT : var5_5
                        var1_1.getInventory().writeUnlock();
                        return;
                    }
                    if (var1_1.getInventory().validateCapacity(var6_7)) break block25;
                    var1_1.sendPacket((IStaticPacket)SystemMsg.YOU_COULD_NOT_RECEIVE_BECAUSE_YOUR_INVENTORY_IS_FULL);
                    // MONITOREXIT : var5_5
                    var1_1.getInventory().writeUnlock();
                    return;
                }
                if (var2_2.getPrice() <= 0L) ** GOTO lbl85
                if (var1_1.reduceAdena(var2_2.getPrice(), true)) break block26;
                var1_1.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_RECEIVE_BECAUSE_YOU_DONT_HAVE_ENOUGH_ADENA);
                // MONITOREXIT : var5_5
                var1_1.getInventory().writeUnlock();
                return;
            }
            try {
                var9_10 = World.getPlayer(var2_2.getSenderId());
                if (var9_10 != null) {
                    var9_10.addAdena(var2_2.getPrice(), true);
                    var9_10.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_ACQUIRED_THE_ATTACHED_ITEM_TO_YOUR_MAIL).addName(var1_1));
                } else {
                    var10_11 = 1296000 + (int)(System.currentTimeMillis() / 1000L);
                    var11_13 = var2_2.reply();
                    var11_13.setExpireTime(var10_11);
                    var12_14 = ItemFunctions.createItem(57);
                    var12_14.setOwnerId(var11_13.getReceiverId());
                    var12_14.setCount(var2_2.getPrice());
                    var12_14.setLocation(ItemInstance.ItemLocation.MAIL);
                    var12_14.save();
                    Log.LogItem(var1_1, Log.ItemLog.PostSend, var12_14);
                    var11_13.addAttachment(var12_14);
                    var11_13.save();
                }
lbl85:
                // 3 sources

                var3_3.clear();
                // MONITOREXIT : var5_5
                var2_2.setJdbcState(JdbcEntityState.UPDATED);
                var2_2.update();
                for (ItemInstance var8_15 : var4_6) {
                    var1_1.sendPacket((IStaticPacket)((SystemMessage)new SystemMessage(SystemMsg.YOU_HAVE_ACQUIRED_S2_S1).addItemName(var8_15.getItemId())).addNumber(var8_15.getCount()));
                    Log.LogItem(var1_1, Log.ItemLog.PostRecieve, var8_15);
                    var1_1.getInventory().addItem(var8_15);
                }
                var1_1.sendPacket((IStaticPacket)SystemMsg.MAIL_SUCCESSFULLY_RECEIVED);
                break block27;
            }
            catch (ArithmeticException var3_4) {}
            break block27;
            catch (Throwable var14_16) {
                throw var14_16;
            }
            finally {
                var1_1.getInventory().writeUnlock();
            }
        }
        var1_1.sendPacket((IStaticPacket)new ExShowReceivedPostList(var1_1));
    }
}
