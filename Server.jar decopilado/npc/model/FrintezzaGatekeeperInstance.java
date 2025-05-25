/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.Config
 *  l2.gameserver.model.CommandChannel
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Party
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.templates.npc.NpcTemplate
 *  l2.gameserver.utils.ItemFunctions
 */
package npc.model;

import bosses.FrintezzaManager;
import java.util.List;
import l2.gameserver.Config;
import l2.gameserver.model.CommandChannel;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Party;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.templates.npc.NpcTemplate;
import l2.gameserver.utils.ItemFunctions;

public final class FrintezzaGatekeeperInstance
extends NpcInstance {
    public FrintezzaGatekeeperInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    public void onBypassFeedback(Player player, String string) {
        block17: {
            if (!FrintezzaGatekeeperInstance.canBypassCheck((Player)player, (NpcInstance)this)) {
                return;
            }
            if (string.equalsIgnoreCase("request_frintezza")) {
                try {
                    if (!FrintezzaManager.getInstance().isInUse()) {
                        if (FrintezzaManager.getInstance().canEnter()) {
                            if (player.getParty() == null || player.getParty().getCommandChannel() == null) {
                                player.sendMessage(new CustomMessage("scripts.FrintezzaGatekeeperInstance.OnlyLeader", player, new Object[0]));
                                return;
                            }
                            CommandChannel commandChannel = player.getParty().getCommandChannel();
                            List list = commandChannel.getParties();
                            if (list.size() < Config.FRINTEZZA_MIN_PARTY_IN_CC || list.size() > Config.FRINTEZZA_MAX_PARTY_IN_CC) {
                                player.sendMessage(new CustomMessage("scripts.FrintezzaGatekeeperInstance.TooSmallOrToLarge", player, new Object[0]));
                                return;
                            }
                            if (commandChannel.getChannelLeader() != player) {
                                player.sendMessage(new CustomMessage("scripts.FrintezzaGatekeeperInstance.OnlyLeader", player, new Object[0]));
                                return;
                            }
                            if (commandChannel.getChannelLeader().isDead() && Config.FRINTEZZA_LOCK_FOR_DEAD_PLAYERS) {
                                player.sendMessage(new CustomMessage("scripts.FrintezzaGatekeeperInstance.LeaderIsDead", player, new Object[0]));
                                return;
                            }
                            for (Party party : commandChannel.getParties()) {
                                for (Player player2 : party.getPartyMembers()) {
                                    if (player2.getLevel() < 74) {
                                        player.sendMessage(new CustomMessage("scripts.FrintezzaGatekeeperInstance.PlayerLevelToLow", player, new Object[0]).addString(player2.getName()));
                                        return;
                                    }
                                    if (player2.isDead() && Config.FRINTEZZA_LOCK_FOR_DEAD_PLAYERS) {
                                        player.sendMessage(new CustomMessage("scripts.FrintezzaGatekeeperInstance.PlayerisDead", player, new Object[0]).addString(player2.getName()));
                                        return;
                                    }
                                    if (!(this.getDistance((GameObject)player2) > (double)Config.FRINTEZZA_MIN_DISTANCE_FOR_ENTRANCE)) continue;
                                    player.sendMessage(player2.getName() + " to far.");
                                    return;
                                }
                            }
                            if (ItemFunctions.removeItem((Playable)player, (int)8073, (long)1L, (boolean)true) < 1L) {
                                player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_REQUIRED_ITEMS);
                                return;
                            }
                            FrintezzaManager.getInstance().tryEnter(list);
                        } else {
                            player.sendMessage(new CustomMessage("scripts.FrintezzaGatekeeperInstance.StillReborn", player, new Object[0]));
                        }
                        break block17;
                    }
                    player.sendMessage(new CustomMessage("scripts.FrintezzaGatekeeperInstance.UnderAttack", player, new Object[0]));
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
            } else {
                super.onBypassFeedback(player, string);
            }
        }
    }
}
