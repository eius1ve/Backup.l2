/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.model.Player;
import l2.gameserver.model.World;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.pledge.SubUnit;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.ExGMViewQuestItemList;
import l2.gameserver.network.l2.s2c.GMHennaInfo;
import l2.gameserver.network.l2.s2c.GMViewCharacterInfo;
import l2.gameserver.network.l2.s2c.GMViewItemList;
import l2.gameserver.network.l2.s2c.GMViewPledgeInfo;
import l2.gameserver.network.l2.s2c.GMViewQuestInfo;
import l2.gameserver.network.l2.s2c.GMViewSkillInfo;
import l2.gameserver.network.l2.s2c.GMViewWarehouseWithdrawList;

public class RequestGMCommand
extends L2GameClientPacket {
    private String ej;
    private int pX;

    @Override
    protected void readImpl() {
        this.ej = this.readS();
        this.pX = this.readD();
    }

    @Override
    protected void runImpl() {
        Player player = ((GameClient)this.getClient()).getActiveChar();
        if (player == null) {
            return;
        }
        Player player2 = World.getPlayer(this.ej);
        if (player2 == null) {
            return;
        }
        if (!player.getPlayerAccess().CanViewChar) {
            return;
        }
        switch (this.pX) {
            case 1: {
                player.sendPacket((IStaticPacket)new GMViewCharacterInfo(player2));
                player.sendPacket((IStaticPacket)new GMHennaInfo(player2));
                break;
            }
            case 2: {
                if (player2.getClan() == null) break;
                for (SubUnit subUnit : player2.getClan().getAllSubUnits()) {
                    player.sendPacket((IStaticPacket)new GMViewPledgeInfo(player2.getName(), player2.getClan(), subUnit));
                }
                break;
            }
            case 3: {
                player.sendPacket((IStaticPacket)new GMViewSkillInfo(player2));
                break;
            }
            case 4: {
                player.sendPacket((IStaticPacket)new GMViewQuestInfo(player2));
                break;
            }
            case 5: {
                ItemInstance[] itemInstanceArray = player2.getInventory().getItems();
                int n = 0;
                for (ItemInstance itemInstance : itemInstanceArray) {
                    if (!itemInstance.getTemplate().isQuest()) continue;
                    ++n;
                }
                player.sendPacket((IStaticPacket)new GMViewItemList(1, player2, itemInstanceArray, itemInstanceArray.length - n));
                player.sendPacket((IStaticPacket)new GMViewItemList(2, player2, itemInstanceArray, itemInstanceArray.length - n));
                player.sendPacket((IStaticPacket)new ExGMViewQuestItemList(1, player2, itemInstanceArray, n));
                player.sendPacket((IStaticPacket)new ExGMViewQuestItemList(2, player2, itemInstanceArray, n));
                player.sendPacket((IStaticPacket)new GMHennaInfo(player2));
                break;
            }
            case 6: {
                player.sendPacket((IStaticPacket)new GMViewWarehouseWithdrawList(1, player2));
                player.sendPacket((IStaticPacket)new GMViewWarehouseWithdrawList(2, player2));
            }
        }
    }
}
