/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.items.listeners;

import java.util.ArrayList;
import java.util.List;
import l2.gameserver.data.xml.holder.EnsoulOptionHolder;
import l2.gameserver.listener.inventory.OnEquipListener;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.SkillCoolTime;
import l2.gameserver.templates.item.support.EnsoulFeeSlotType;
import l2.gameserver.templates.item.support.EnsoulOption;

public final class ItemEnsoulListner
implements OnEquipListener {
    private static final ItemEnsoulListner a = new ItemEnsoulListner();

    public static ItemEnsoulListner getInstance() {
        return a;
    }

    @Override
    public void onEquip(int n, ItemInstance itemInstance, Playable playable) {
        if (!itemInstance.isEquipable() || !itemInstance.isEnsouled()) {
            return;
        }
        Player player = playable.getPlayer();
        if (player.getExpertisePenalty(itemInstance) > 0) {
            return;
        }
        boolean bl = false;
        boolean bl2 = false;
        List<EnsoulOption> list = this.a(itemInstance);
        for (EnsoulOption ensoulOption : list) {
            if (ensoulOption == null || ensoulOption.getSkill() == null) continue;
            player.addSkill(ensoulOption.getSkill());
            bl = true;
            if (!player.isSkillDisabled(ensoulOption.getSkill())) continue;
            bl2 = true;
        }
        if (bl) {
            player.sendSkillList();
        }
        if (bl2) {
            player.sendPacket((IStaticPacket)new SkillCoolTime(player));
        }
        player.updateStats();
    }

    private List<EnsoulOption> a(ItemInstance itemInstance) {
        EnsoulOption ensoulOption;
        ArrayList<EnsoulOption> arrayList = new ArrayList<EnsoulOption>();
        int n = itemInstance.getTemplate().getEnsoulNormalSlots();
        if (itemInstance.getEnsoulSlotN1() > 0 && n > 0 && (ensoulOption = EnsoulOptionHolder.getInstance().getOptionById(itemInstance.getEnsoulSlotN1())) != null && ensoulOption.getEnsoulFeeSlotType() == EnsoulFeeSlotType.Normal) {
            arrayList.add(ensoulOption);
        }
        if (itemInstance.getEnsoulSlotN2() > 0 && n > 1 && (ensoulOption = EnsoulOptionHolder.getInstance().getOptionById(itemInstance.getEnsoulSlotN2())) != null && ensoulOption.getEnsoulFeeSlotType() == EnsoulFeeSlotType.Normal) {
            arrayList.add(ensoulOption);
        }
        if (itemInstance.getEnsoulSlotBm() > 0 && itemInstance.getTemplate().getEnsoulBmSlots() > 0 && (ensoulOption = EnsoulOptionHolder.getInstance().getOptionById(itemInstance.getEnsoulSlotBm())) != null && ensoulOption.getEnsoulFeeSlotType() == EnsoulFeeSlotType.Bm) {
            arrayList.add(ensoulOption);
        }
        return arrayList;
    }

    @Override
    public void onUnequip(int n, ItemInstance itemInstance, Playable playable) {
        if (!itemInstance.isEquipable() || !itemInstance.isEnsouled()) {
            return;
        }
        Player player = playable.getPlayer();
        boolean bl = false;
        List<EnsoulOption> list = this.a(itemInstance);
        for (EnsoulOption ensoulOption : list) {
            if (ensoulOption == null || ensoulOption.getSkill() == null) continue;
            player.removeSkill(ensoulOption.getSkill());
            bl = true;
        }
        if (bl) {
            player.sendSkillList();
        }
        player.updateStats();
    }
}
