/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Creature;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SysMsgContainer;

public class SystemMessage
extends SysMsgContainer<SystemMessage> {
    public SystemMessage(SystemMsg systemMsg) {
        super(systemMsg);
    }

    public static SystemMessage obtainItems(int n, long l, int n2) {
        if (n == 57) {
            return (SystemMessage)new SystemMessage(SystemMsg.YOU_HAVE_EARNED_S1_ADENA).addNumber(l);
        }
        if (l > 1L) {
            return (SystemMessage)((SystemMessage)new SystemMessage(SystemMsg.YOU_HAVE_EARNED_S2_S1S).addItemName(n)).addNumber(l);
        }
        if (n2 > 0) {
            return (SystemMessage)((SystemMessage)new SystemMessage(SystemMsg.YOU_HAVE_OBTAINED_A_S1_S2).addNumber(n2)).addItemName(n);
        }
        return (SystemMessage)new SystemMessage(SystemMsg.YOU_HAVE_EARNED_S1).addItemName(n);
    }

    public static SystemMessage obtainItems(ItemInstance itemInstance) {
        return SystemMessage.obtainItems(itemInstance.getItemId(), itemInstance.getCount(), itemInstance.isEquipable() ? itemInstance.getEnchantLevel() : 0);
    }

    public static SystemMessage obtainItemsBy(int n, long l, int n2, Creature creature) {
        if (l > 1L) {
            return (SystemMessage)((SystemMessage)((SystemMessage)new SystemMessage(SystemMsg.C1_HAS_OBTAINED_S3_S2).addName(creature)).addItemName(n)).addNumber(l);
        }
        if (n2 > 0) {
            return (SystemMessage)((SystemMessage)((SystemMessage)new SystemMessage(SystemMsg.C1_HAS_OBTAINED_S2S3).addName(creature)).addNumber(n2)).addItemName(n);
        }
        return (SystemMessage)((SystemMessage)new SystemMessage(SystemMsg.C1_HAS_OBTAINED_S2).addName(creature)).addItemName(n);
    }

    public static SystemMessage obtainItemsBy(ItemInstance itemInstance, Creature creature) {
        return SystemMessage.obtainItemsBy(itemInstance.getItemId(), itemInstance.getCount(), itemInstance.isEquipable() ? itemInstance.getEnchantLevel() : 0, creature);
    }

    public static SystemMessage removeItems(int n, long l) {
        if (n == 57) {
            return (SystemMessage)new SystemMessage(SystemMsg.S1_ADENA_DISAPPEARED).addNumber(l);
        }
        if (l > 1L) {
            return (SystemMessage)((SystemMessage)new SystemMessage(SystemMsg.S2_S1_HAS_DISAPPEARED).addItemName(n)).addNumber(l);
        }
        return (SystemMessage)new SystemMessage(SystemMsg.S1_HAS_DISAPPEARED).addItemName(n);
    }

    public static SystemMessage removeItems(ItemInstance itemInstance) {
        return SystemMessage.removeItems(itemInstance.getItemId(), itemInstance.getCount());
    }

    @Override
    protected void writeImpl() {
        this.writeC(98);
        this.writeElements();
    }
}
