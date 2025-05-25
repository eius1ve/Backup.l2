/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.Config
 *  l2.gameserver.data.StringHolder
 *  l2.gameserver.data.xml.holder.ItemHolder
 *  l2.gameserver.handler.bypass.INpcHtmlAppendHandler
 *  l2.gameserver.model.Playable
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Summon
 *  l2.gameserver.model.instances.PetInstance
 *  l2.gameserver.model.items.ItemInstance
 *  l2.gameserver.network.l2.components.CustomMessage
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.InventoryUpdate
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.templates.item.ItemTemplate
 *  l2.gameserver.utils.Log
 *  l2.gameserver.utils.Util
 *  org.apache.commons.lang3.ArrayUtils
 */
package services.petevolve;

import l2.gameserver.Config;
import l2.gameserver.data.StringHolder;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.handler.bypass.INpcHtmlAppendHandler;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.Summon;
import l2.gameserver.model.instances.PetInstance;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.InventoryUpdate;
import l2.gameserver.scripts.Functions;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.utils.Log;
import l2.gameserver.utils.Util;
import org.apache.commons.lang3.ArrayUtils;

public class exchange
extends Functions
implements INpcHtmlAppendHandler {
    private static final int bGO = 7583;
    private static final int bGP = 7584;
    private static final int bGQ = 7585;
    private static final int bGR = 6648;
    private static final int bGS = 6649;
    private static final int bGT = 6650;

    public void exch_1() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        if (exchange.getItemCount((Playable)player, (int)7583) >= 1L) {
            exchange.removeItem((Playable)player, (int)7583, (long)1L);
            exchange.addItem((Playable)player, (int)6648, (long)1L);
            return;
        }
        this.show("scripts/services/petevolve/exchange_no.htm", player);
    }

    public void exch_2() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        if (exchange.getItemCount((Playable)player, (int)7584) >= 1L) {
            exchange.removeItem((Playable)player, (int)7584, (long)1L);
            exchange.addItem((Playable)player, (int)6649, (long)1L);
            return;
        }
        this.show("scripts/services/petevolve/exchange_no.htm", player);
    }

    public void exch_3() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        if (exchange.getItemCount((Playable)player, (int)7585) >= 1L) {
            exchange.removeItem((Playable)player, (int)7585, (long)1L);
            exchange.addItem((Playable)player, (int)6650, (long)1L);
            return;
        }
        this.show("scripts/services/petevolve/exchange_no.htm", player);
    }

    public void showErasePetName() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        if (!Config.SERVICES_CHANGE_PET_NAME_ENABLED) {
            player.sendMessage(new CustomMessage("common.Disabled", player, new Object[0]));
            return;
        }
        ItemTemplate itemTemplate = ItemHolder.getInstance().getTemplate(Config.SERVICES_CHANGE_PET_NAME_ITEM);
        String string = StringHolder.getInstance().getNotNull(player, "scripts.services.exchange.pathHtml");
        string = string.replace("%price%", Util.formatAdena((long)Config.SERVICES_CHANGE_PET_NAME_PRICE));
        string = string.replace("%item_name%", itemTemplate != null ? itemTemplate.getName() : "NO NAME");
        this.show(string, player);
    }

    public void erasePetName() {
        Player player = this.getSelf();
        if (player == null) {
            return;
        }
        if (!Config.SERVICES_CHANGE_PET_NAME_ENABLED) {
            if (player.isLangRus()) {
                this.show("\u0421\u0435\u0440\u0432\u0438\u0441 \u043e\u0442\u043a\u043b\u044e\u0447\u0435\u043d", player);
            } else {
                this.show("Service disabled", player);
            }
            return;
        }
        Summon summon = player.getPet();
        if (summon == null || !summon.isPet()) {
            if (player.isLangRus()) {
                this.show("\u041f\u0438\u0442\u043e\u043c\u0435\u0446 \u0434\u043e\u043b\u0436\u0435\u043d \u0431\u044b\u0442\u044c \u0432\u044b\u0437\u0432\u0430\u043d.", player);
            } else {
                this.show("Pet must be summoned.", player);
            }
            return;
        }
        if (player.getInventory().destroyItemByItemId(Config.SERVICES_CHANGE_PET_NAME_ITEM, (long)Config.SERVICES_CHANGE_PET_NAME_PRICE)) {
            summon.setName(summon.getTemplate().name);
            summon.broadcastCharInfo();
            PetInstance petInstance = (PetInstance)summon;
            ItemInstance itemInstance = petInstance.getControlItem();
            if (itemInstance != null) {
                itemInstance.setDamaged(1);
                player.sendPacket((IStaticPacket)new InventoryUpdate().addModifiedItem(itemInstance));
                Log.service((String)"erasePetName", (Player)player, (String)("Pet name erased for " + Config.SERVICES_CHANGE_PET_NAME_ITEM + " amount " + Config.SERVICES_CHANGE_PET_NAME_PRICE));
            }
            if (player.isLangRus()) {
                this.show("\u0418\u043c\u044f \u0441\u0442\u0435\u0440\u0442\u043e.", player);
            } else {
                this.show("Name Erased.", player);
            }
        } else if (Config.SERVICES_CHANGE_PET_NAME_ITEM == 57) {
            player.sendPacket((IStaticPacket)SystemMsg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
        } else {
            player.sendPacket((IStaticPacket)SystemMsg.INCORRECT_ITEM_COUNT);
        }
    }

    public int[] getNpcIds() {
        if (!Config.SERVICES_CHANGE_PET_NAME_ENABLED) {
            return ArrayUtils.EMPTY_INT_ARRAY;
        }
        return Config.SERVICES_CHANGE_PET_NAME_NPC_ID;
    }

    public String getAppend(Player player, int n, int n2) {
        exchange exchange2 = new exchange();
        exchange2.self = player.getRef();
        return exchange2.getHtmlAppends(n2);
    }

    public String getHtmlAppends(Integer n) {
        Player player = this.getSelf();
        if (Config.SERVICES_CHANGE_PET_NAME_ENABLED) {
            return player.isLangRus() ? "<br>[scripts_services.petevolve.exchange:showErasePetName|\u041e\u0431\u043d\u0443\u043b\u0438\u0442\u044c \u0438\u043c\u044f \u0443 \u043f\u0435\u0442\u0430]" : "<br>[scripts_services.petevolve.exchange:showErasePetName|Erase Pet Name]";
        }
        return "";
    }
}
