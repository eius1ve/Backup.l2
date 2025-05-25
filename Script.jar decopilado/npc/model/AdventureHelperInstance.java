/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.data.xml.holder.MultiSellHolder
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.instances.MerchantInstance
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.templates.npc.NpcTemplate
 */
package npc.model;

import l2.gameserver.data.xml.holder.MultiSellHolder;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.instances.MerchantInstance;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.scripts.Functions;
import l2.gameserver.templates.npc.NpcTemplate;

public class AdventureHelperInstance
extends MerchantInstance {
    private static final String gJ = "newbiehelper/adventure_helper/adventure_helper_01001.htm";
    private static final String gK = "newbiehelper/adventure_helper/adventure_helper_01002.htm";
    private static final String gL = "newbiehelper/adventure_helper/adventure_helper_01003.htm";
    private static final String gM = "newbiehelper/adventure_helper/adventure_helper_01004.htm";
    private static final String gN = "newbiehelper/adventure_helper/adventure_helper_01011.htm";
    private static final String gO = "newbiehelper/adventure_helper/adventure_helper_01012.htm";
    private static final String gP = "newbiehelper/adventure_helper/adventure_helper_01013.htm";

    public AdventureHelperInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    public void showChatWindow(Player player, int n, Object ... objectArray) {
        this.showChatWindow(player, gJ, new Object[0]);
    }

    public void onBypassFeedback(Player player, String string) {
        if (!AdventureHelperInstance.canBypassCheck((Player)player, (NpcInstance)this)) {
            return;
        }
        if (string.startsWith("menu_select ")) {
            String[] stringArray = string.replace("menu_select ", "").split(" ");
            int n = Integer.parseInt(stringArray[0]);
            int n2 = Integer.parseInt(stringArray[1]);
            if (n == -1000) {
                switch (n2) {
                    case 1: {
                        if (player.getLevel() > 5 && player.getLevel() < 20 && player.getClassId().getLevel() == 1) {
                            if (!player.getVarB("coupon1")) {
                                if (!player.isQuestContinuationPossible(true)) {
                                    return;
                                }
                                player.setVar("coupon1", "1", -1L);
                                Functions.addItem((Playable)player, (int)7832, (long)1L);
                                this.showChatWindow(player, gK, new Object[0]);
                                break;
                            }
                            this.showChatWindow(player, gM, new Object[0]);
                            break;
                        }
                        this.showChatWindow(player, gL, new Object[0]);
                        break;
                    }
                    case 2: {
                        if (player.getClassId().getLevel() > 1 && player.getLevel() < 40) {
                            if (!player.getVarB("coupon2")) {
                                if (!player.isQuestContinuationPossible(true)) {
                                    return;
                                }
                                player.setVar("coupon2", "1", -1L);
                                Functions.addItem((Playable)player, (int)7833, (long)1L);
                                this.showChatWindow(player, gN, new Object[0]);
                                break;
                            }
                            this.showChatWindow(player, gP, new Object[0]);
                            break;
                        }
                        this.showChatWindow(player, gO, new Object[0]);
                    }
                }
            }
            if (n == -303) {
                switch (n2) {
                    case 528: {
                        if (player.getLevel() > 5 && player.getLevel() < 20 && player.getClassId().getLevel() == 1) {
                            MultiSellHolder.getInstance().SeparateAndSend(528, player, 0.0);
                            break;
                        }
                        this.showChatWindow(player, gL, new Object[0]);
                        break;
                    }
                    case 530: {
                        if (player.getLevel() > 5 && player.getLevel() < 20 && player.getClassId().getLevel() == 1) {
                            MultiSellHolder.getInstance().SeparateAndSend(530, player, 0.0);
                            break;
                        }
                        this.showChatWindow(player, gL, new Object[0]);
                        break;
                    }
                    case 529: {
                        if (player.getClassId().getLevel() > 1 && player.getLevel() < 26) {
                            MultiSellHolder.getInstance().SeparateAndSend(529, player, 0.0);
                            break;
                        }
                        this.showChatWindow(player, gO, new Object[0]);
                        break;
                    }
                    case 531: {
                        if (player.getClassId().getLevel() > 1 && player.getLevel() < 26) {
                            MultiSellHolder.getInstance().SeparateAndSend(531, player, 0.0);
                            break;
                        }
                        this.showChatWindow(player, gO, new Object[0]);
                    }
                }
            }
        } else {
            super.onBypassFeedback(player, string);
        }
    }
}
