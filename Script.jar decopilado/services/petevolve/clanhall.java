/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.data.xml.holder.NpcHolder
 *  l2.gameserver.data.xml.holder.PetDataHolder
 *  l2.gameserver.model.PetData
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.instances.PetInstance
 *  l2.gameserver.model.items.ItemInstance
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.SystemMessage
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.templates.npc.NpcTemplate
 */
package services.petevolve;

import l2.gameserver.data.xml.holder.NpcHolder;
import l2.gameserver.data.xml.holder.PetDataHolder;
import l2.gameserver.model.PetData;
import l2.gameserver.model.Player;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.instances.PetInstance;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.scripts.Functions;
import l2.gameserver.templates.npc.NpcTemplate;

public class clanhall
extends Functions {
    private static final int bGC = 12311;
    private static final int bGD = 12312;
    private static final int bGE = 12313;
    private static final int bGF = 12526;
    private static final int bGG = 12527;
    private static final int bGH = 12528;
    private static final int bGI = 3500;
    private static final int bGJ = 3501;
    private static final int bGK = 3502;
    private static final int bGL = 4422;
    private static final int bGM = 4423;
    private static final int bGN = 4424;

    public void evolve() {
        Player player = this.getSelf();
        NpcInstance npcInstance = this.getNpc();
        if (player == null || npcInstance == null) {
            return;
        }
        clanhall.show((String)"scripts/services/petevolve/chamberlain.htm", (Player)player, (NpcInstance)npcInstance, (Object[])new Object[0]);
    }

    public void wstrider(String[] stringArray) {
        Player player = this.getSelf();
        NpcInstance npcInstance = this.getNpc();
        if (player == null || npcInstance == null) {
            return;
        }
        boolean bl = Integer.parseInt(stringArray[0]) == 1;
        if (player.getInventory().getCountOf(bl ? 3500 : 4422) > 1L) {
            clanhall.show((String)"scripts/services/petevolve/error_3.htm", (Player)player, (NpcInstance)npcInstance, (Object[])new Object[0]);
            return;
        }
        if (player.getPet() != null) {
            clanhall.show((String)"scripts/services/petevolve/error_4.htm", (Player)player, (NpcInstance)npcInstance, (Object[])new Object[0]);
            return;
        }
        ItemInstance itemInstance = player.getInventory().getItemByItemId(bl ? 3500 : 4422);
        if (itemInstance == null) {
            clanhall.show((String)"scripts/services/petevolve/no_item.htm", (Player)player, (NpcInstance)npcInstance, (Object[])new Object[0]);
            return;
        }
        PetData petData = PetDataHolder.getInstance().getByControlItemId(itemInstance);
        if (petData == null) {
            return;
        }
        NpcTemplate npcTemplate = NpcHolder.getInstance().getTemplate(petData.getID());
        if (npcTemplate == null) {
            return;
        }
        PetInstance petInstance = PetInstance.restore((ItemInstance)itemInstance, (NpcTemplate)npcTemplate, (Player)player);
        if (petData != (bl ? PetDataHolder.getInstance().getInfo(12311) : PetDataHolder.getInstance().getInfo(12526))) {
            clanhall.show((String)"scripts/services/petevolve/error_2.htm", (Player)player, (NpcInstance)npcInstance, (Object[])new Object[0]);
            return;
        }
        if (petInstance != null && petInstance.getLevel() < 55) {
            clanhall.show((String)"scripts/services/petevolve/error_lvl_strider.htm", (Player)player, (NpcInstance)npcInstance, (Object[])new Object[0]);
            return;
        }
        itemInstance.setItemId(bl ? 4422 : 3500);
        player.sendItemList(false);
        player.sendPacket((IStaticPacket)SystemMessage.obtainItems((int)(bl ? 4422 : 3500), (long)1L, (int)0));
        clanhall.show((String)"scripts/services/petevolve/end_msg_strider.htm", (Player)player, (NpcInstance)npcInstance, (Object[])new Object[0]);
    }

    public void sstrider(String[] stringArray) {
        Player player = this.getSelf();
        NpcInstance npcInstance = this.getNpc();
        if (player == null || npcInstance == null) {
            return;
        }
        boolean bl = Integer.parseInt(stringArray[0]) == 1;
        if (player.getInventory().getCountOf(bl ? 3501 : 4423) > 1L) {
            clanhall.show((String)"scripts/services/petevolve/error_3.htm", (Player)player, (NpcInstance)npcInstance, (Object[])new Object[0]);
            return;
        }
        if (player.getPet() != null) {
            clanhall.show((String)"scripts/services/petevolve/error_4.htm", (Player)player, (NpcInstance)npcInstance, (Object[])new Object[0]);
            return;
        }
        ItemInstance itemInstance = player.getInventory().getItemByItemId(bl ? 3501 : 4423);
        if (itemInstance == null) {
            clanhall.show((String)"scripts/services/petevolve/no_item.htm", (Player)player, (NpcInstance)npcInstance, (Object[])new Object[0]);
            return;
        }
        PetData petData = PetDataHolder.getInstance().getByControlItemId(itemInstance);
        if (petData == null) {
            return;
        }
        NpcTemplate npcTemplate = NpcHolder.getInstance().getTemplate(petData.getID());
        if (npcTemplate == null) {
            return;
        }
        PetInstance petInstance = PetInstance.restore((ItemInstance)itemInstance, (NpcTemplate)npcTemplate, (Player)player);
        if (petData != (bl ? PetDataHolder.getInstance().getInfo(12312) : PetDataHolder.getInstance().getInfo(12527))) {
            clanhall.show((String)"scripts/services/petevolve/error_2.htm", (Player)player, (NpcInstance)npcInstance, (Object[])new Object[0]);
            return;
        }
        if (petInstance != null && petInstance.getLevel() < 55) {
            clanhall.show((String)"scripts/services/petevolve/error_lvl_strider.htm", (Player)player, (NpcInstance)npcInstance, (Object[])new Object[0]);
            return;
        }
        itemInstance.setItemId(bl ? 4423 : 3501);
        player.sendItemList(false);
        player.sendPacket((IStaticPacket)SystemMessage.obtainItems((int)(bl ? 4423 : 3501), (long)1L, (int)0));
        clanhall.show((String)"scripts/services/petevolve/end_msg_strider.htm", (Player)player, (NpcInstance)npcInstance, (Object[])new Object[0]);
    }

    public void tstrider(String[] stringArray) {
        Player player = this.getSelf();
        NpcInstance npcInstance = this.getNpc();
        if (player == null || npcInstance == null) {
            return;
        }
        boolean bl = Integer.parseInt(stringArray[0]) == 1;
        if (player.getInventory().getCountOf(bl ? 3502 : 4424) > 1L) {
            clanhall.show((String)"scripts/services/petevolve/error_3.htm", (Player)player, (NpcInstance)npcInstance, (Object[])new Object[0]);
            return;
        }
        if (player.getPet() != null) {
            clanhall.show((String)"scripts/services/petevolve/error_4.htm", (Player)player, (NpcInstance)npcInstance, (Object[])new Object[0]);
            return;
        }
        ItemInstance itemInstance = player.getInventory().getItemByItemId(bl ? 3502 : 4424);
        if (itemInstance == null) {
            clanhall.show((String)"scripts/services/petevolve/no_item.htm", (Player)player, (NpcInstance)npcInstance, (Object[])new Object[0]);
            return;
        }
        PetData petData = PetDataHolder.getInstance().getByControlItemId(itemInstance);
        if (petData == null) {
            return;
        }
        NpcTemplate npcTemplate = NpcHolder.getInstance().getTemplate(petData.getID());
        if (npcTemplate == null) {
            return;
        }
        PetInstance petInstance = PetInstance.restore((ItemInstance)itemInstance, (NpcTemplate)npcTemplate, (Player)player);
        if (petData != (bl ? PetDataHolder.getInstance().getInfo(12313) : PetDataHolder.getInstance().getInfo(12528))) {
            clanhall.show((String)"scripts/services/petevolve/error_2.htm", (Player)player, (NpcInstance)npcInstance, (Object[])new Object[0]);
            return;
        }
        if (petInstance != null && petInstance.getLevel() < 55) {
            clanhall.show((String)"scripts/services/petevolve/error_lvl_strider.htm", (Player)player, (NpcInstance)npcInstance, (Object[])new Object[0]);
            return;
        }
        itemInstance.setItemId(bl ? 4424 : 3502);
        player.sendItemList(false);
        player.sendPacket((IStaticPacket)SystemMessage.obtainItems((int)(bl ? 4424 : 3502), (long)1L, (int)0));
        clanhall.show((String)"scripts/services/petevolve/end_msg_strider.htm", (Player)player, (NpcInstance)npcInstance, (Object[])new Object[0]);
    }
}
