/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.Config
 *  l2.gameserver.data.StringHolder
 *  l2.gameserver.data.xml.holder.ItemHolder
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.base.ClassId
 *  l2.gameserver.model.base.TeamType
 *  l2.gameserver.model.instances.MerchantInstance
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.L2GameServerPacket
 *  l2.gameserver.network.l2.s2c.MagicSkillUse
 *  l2.gameserver.network.l2.s2c.NpcHtmlMessage
 *  l2.gameserver.network.l2.s2c.SocialAction
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.templates.item.ItemTemplate
 *  l2.gameserver.templates.npc.NpcTemplate
 *  l2.gameserver.utils.HtmlUtils
 *  l2.gameserver.utils.ItemFunctions
 *  l2.gameserver.utils.Log
 *  org.apache.commons.lang3.tuple.Pair
 */
package npc.model;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import l2.gameserver.Config;
import l2.gameserver.data.StringHolder;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.base.ClassId;
import l2.gameserver.model.base.TeamType;
import l2.gameserver.model.instances.MerchantInstance;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.MagicSkillUse;
import l2.gameserver.network.l2.s2c.NpcHtmlMessage;
import l2.gameserver.network.l2.s2c.SocialAction;
import l2.gameserver.scripts.Functions;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.templates.npc.NpcTemplate;
import l2.gameserver.utils.HtmlUtils;
import l2.gameserver.utils.ItemFunctions;
import l2.gameserver.utils.Log;
import org.apache.commons.lang3.tuple.Pair;
import services.CommandClassMaster;

public final class ClassMasterInstance
extends MerchantInstance {
    private Map<ClassId, List<CommandClassMaster.ClassMasterPath>> cb = CommandClassMaster.parseConfig(Config.CLASS_MASTERS_CLASSES);

    public ClassMasterInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    private boolean a(Player player, boolean bl) {
        if (player == null || player.isLogoutStarted() || player.isOutOfControl() || player.isDead() || player.isInDuel() || player.isAlikeDead() || player.isOlyParticipant() || player.isFlying() || player.isSitting() || player.getTeam() != TeamType.NONE || player.isInStoreMode() || player.entering) {
            if (bl && player != null && !player.entering) {
                player.sendMessage(new CustomMessage("common.TryLater", player, new Object[0]));
            }
            return false;
        }
        return true;
    }

    private void a(Player player, List<CommandClassMaster.ClassMasterPath> list) {
        if (!this.a(player, false) || list == null || list.isEmpty()) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < list.size(); ++i) {
            String string;
            CommandClassMaster.ClassMasterPath classMasterPath = list.get(i);
            if (classMasterPath.getAvailableClassIds().isEmpty()) continue;
            String string2 = StringHolder.getInstance().getNotNull(player, "scripts.services.NpcClassMaster.pathHtml");
            StringBuilder stringBuilder2 = new StringBuilder();
            for (ClassId classId : classMasterPath.getAvailableClassIds()) {
                stringBuilder2.append(new CustomMessage("NpcClassMaster.htmlButton", player, new Object[0]).addNumber((long)this.getObjectId()).addNumber((long)i).addNumber((long)classId.getId()).addString(HtmlUtils.htmlClassName((int)classId.getId(), (Player)player)));
            }
            string2 = string2.replace("%class_master_list%", stringBuilder2.toString());
            StringBuilder stringBuilder3 = new StringBuilder();
            for (Pair<Integer, Long> pair : classMasterPath.getPrice()) {
                Pair<Integer, Long> pair2 = ItemHolder.getInstance().getTemplate(((Integer)pair.getKey()).intValue());
                string = StringHolder.getInstance().getNotNull(player, "scripts.services.NpcClassMaster.requiredItem");
                string = string.replace("%item_id%", String.valueOf(pair.getKey()));
                string = string.replace("%item_name%", pair2.getName());
                string = string.replace("%item_count%", String.valueOf(pair.getValue()));
                stringBuilder3.append(string);
            }
            string2 = string2.replace("%required_items_list%", stringBuilder3.toString());
            StringBuilder object22 = new StringBuilder();
            for (Pair<Integer, Long> pair2 : classMasterPath.getReward()) {
                string = ItemHolder.getInstance().getTemplate(((Integer)pair2.getKey()).intValue());
                String string3 = StringHolder.getInstance().getNotNull(player, "scripts.services.NpcClassMaster.rewardItem");
                string3 = string3.replace("%item_id%", String.valueOf(pair2.getKey()));
                string3 = string3.replace("%item_name%", string.getName());
                string3 = string3.replace("%item_count%", String.valueOf(pair2.getValue()));
                object22.append(string3);
            }
            string2 = string2.replace("%reward_items_list%", object22.length() > 0 ? object22.toString() : " ");
            stringBuilder.append(string2);
        }
        NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(player, (NpcInstance)this);
        npcHtmlMessage.setFile("scripts/services/npc_class_master.htm");
        npcHtmlMessage.replace("%list%", stringBuilder.toString());
        player.sendPacket((IStaticPacket)npcHtmlMessage);
    }

    public void showChatWindow(Player player, int n, Object ... objectArray) {
        if (!ClassMasterInstance.canBypassCheck((Player)player, (NpcInstance)this)) {
            return;
        }
        List<CommandClassMaster.ClassMasterPath> list = this.cb.get(player.getClassId());
        if (list == null || list.isEmpty() || !list.stream().map(CommandClassMaster.ClassMasterPath::getAvailableClassIds).flatMap(Collection::stream).findAny().isPresent()) {
            player.sendPacket((IStaticPacket)new NpcHtmlMessage(player, (NpcInstance)this).setFile("scripts/services/npc_class_master_nothingtoup.htm"));
            return;
        }
        if (list.stream().mapToInt(CommandClassMaster.ClassMasterPath::getMinPlayerLevel).min().orElse(Integer.MAX_VALUE) > player.getLevel()) {
            Functions.show((CustomMessage)new CustomMessage(CommandClassMaster.getMinPlayerLevelForClassIdMessageAddress(player.getClassId().getLevel() + 1), player, new Object[0]), (Player)player);
            return;
        }
        this.a(player, list);
    }

    public void onBypassFeedback(Player player, String string) {
        if (!ClassMasterInstance.canBypassCheck((Player)player, (NpcInstance)this) || !this.a(player, true)) {
            return;
        }
        StringTokenizer stringTokenizer = new StringTokenizer(string);
        if (stringTokenizer.nextToken().equals("change_class")) {
            CommandClassMaster.ClassMasterPath classMasterPath;
            ClassId classId = player.getClassId();
            List<CommandClassMaster.ClassMasterPath> list = this.cb.get(classId);
            int n = Integer.parseInt(stringTokenizer.nextToken());
            int n2 = Integer.parseInt(stringTokenizer.nextToken());
            if (list == null || list.isEmpty() || n < 0 || n >= list.size() || (classMasterPath = list.get(n)) == null || classMasterPath.getAvailableClassIds() == null || classMasterPath.getAvailableClassIds().isEmpty()) {
                player.sendPacket((IStaticPacket)new NpcHtmlMessage(5).setFile("scripts/services/npc_class_master_nothingtoup.htm"));
                return;
            }
            ClassId classId2 = null;
            for (ClassId classId3 : classMasterPath.getAvailableClassIds()) {
                if (classId3.getId() != n2) continue;
                classId2 = classId3;
                break;
            }
            if (classId2 == null) {
                return;
            }
            for (Pair pair : classMasterPath.getPrice()) {
                if (ItemFunctions.getItemCount((Playable)player, (int)((Integer)pair.getKey())) >= (Long)pair.getValue()) continue;
                if ((Integer)pair.getKey() == 57) {
                    player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
                } else {
                    player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_ITEM_COUNT);
                }
                return;
            }
            long l = 0L;
            long l2 = 0L;
            for (Pair<Integer, Long> pair : classMasterPath.getReward()) {
                ItemTemplate itemTemplate = ItemHolder.getInstance().getTemplate(((Integer)pair.getKey()).intValue());
                l += (long)itemTemplate.getWeight() * (Long)pair.getValue();
                l2 += itemTemplate.isStackable() ? 1L : (Long)pair.getValue();
            }
            if (!player.getInventory().validateWeight(l)) {
                player.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_EXCEEDED_THE_WEIGHT_LIMIT);
                return;
            }
            if (!player.getInventory().validateCapacity(l2)) {
                player.sendPacket((IStaticPacket)SystemMsg.YOUR_INVENTORY_IS_FULL);
                return;
            }
            for (Pair<Integer, Long> pair : classMasterPath.getPrice()) {
                if (ItemFunctions.removeItem((Playable)player, (int)((Integer)pair.getKey()), (long)((Long)pair.getValue()), (boolean)true) >= (Long)pair.getValue()) continue;
                return;
            }
            this.a(player, classId2);
            for (Pair<Integer, Long> pair : classMasterPath.getReward()) {
                ItemFunctions.addItem((Playable)player, (int)((Integer)pair.getKey()), (long)((Long)pair.getValue()), (boolean)true);
            }
            Log.service((String)"NpcClassMaster", (Player)player, (String)(" got a classId " + classId2));
        } else {
            super.onBypassFeedback(player, string);
        }
    }

    private void a(Player player, ClassId classId) {
        if (player.getClassId().getLevel() == 3) {
            player.sendPacket((IStaticPacket)SystemMsg.CONGRATULATIONS__YOUVE_COMPLETED_YOUR_THIRDCLASS_TRANSFER_QUEST);
        } else {
            player.sendPacket((IStaticPacket)SystemMsg.CONGRATULATIONS__YOUVE_COMPLETED_A_CLASS_TRANSFER);
        }
        player.setClassId(classId.getId(), false, false);
        player.broadcastCharInfo();
        player.broadcastPacket(new L2GameServerPacket[]{new SocialAction(player.getObjectId(), 3)});
        player.broadcastPacket(new L2GameServerPacket[]{new MagicSkillUse((Creature)player, (Creature)player, 4339, 1, 0, 0L)});
    }
}
