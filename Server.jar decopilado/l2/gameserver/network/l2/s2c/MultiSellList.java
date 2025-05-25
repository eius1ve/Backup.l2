/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import java.util.List;
import l2.gameserver.Config;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.data.xml.holder.MultiSellHolder;
import l2.gameserver.model.base.Element;
import l2.gameserver.model.base.MultiSellEntry;
import l2.gameserver.model.base.MultiSellIngredient;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.templates.item.ItemTemplate;

public class MultiSellList
extends L2GameServerPacket {
    private final int zF;
    private final int zG;
    private final int zH;
    private final boolean fb;
    private final List<MultiSellEntry> cM;

    public MultiSellList(MultiSellHolder.MultiSellListContainer multiSellListContainer, int n, int n2) {
        this.cM = multiSellListContainer.getEntries();
        this.zH = multiSellListContainer.getListId();
        this.fb = multiSellListContainer.isChancedList();
        this.zF = n;
        this.zG = n2;
    }

    @Override
    protected final void writeImpl() {
        this.writeC(208);
        this.writeC(0);
        this.writeD(this.zH);
        this.writeC(0);
        this.writeD(this.zF);
        this.writeD(this.zG);
        this.writeD(Config.MULTISELL_SIZE);
        this.writeD(this.cM.size());
        this.writeC(0);
        this.writeC(this.fb);
        this.writeD(32);
        for (MultiSellEntry multiSellEntry : this.cM) {
            ItemTemplate itemTemplate;
            int n;
            List<MultiSellIngredient> list = multiSellEntry.getIngredients();
            this.writeD(multiSellEntry.getEntryId());
            this.writeC(!multiSellEntry.getProduction().isEmpty() && multiSellEntry.getProduction().get(0).isStackable() ? 1 : 0);
            this.writeH(0);
            this.writeD(0);
            this.writeD(0);
            this.writeItemElements();
            this.writeC(0);
            this.writeC(0);
            this.writeH(multiSellEntry.getProduction().size());
            this.writeH(list.size());
            for (MultiSellIngredient multiSellIngredient : multiSellEntry.getProduction()) {
                n = multiSellIngredient.getItemId();
                itemTemplate = n > 0 ? ItemHolder.getInstance().getTemplate(multiSellIngredient.getItemId()) : null;
                this.writeD(n);
                this.writeQ(n > 0 ? itemTemplate.getBodyPart() : 0L);
                this.writeH(n > 0 ? itemTemplate.getType2ForPackets() : 0);
                this.writeQ(multiSellIngredient.getItemCount());
                this.writeH(multiSellIngredient.getItemEnchant());
                this.writeD(multiSellIngredient.getItemChance());
                this.writeD(0);
                this.writeD(0);
                this.writeItemElements(multiSellIngredient);
                this.writeC(0);
                this.writeC(0);
            }
            for (MultiSellIngredient multiSellIngredient : list) {
                n = multiSellIngredient.getItemId();
                itemTemplate = n > 0 ? ItemHolder.getInstance().getTemplate(multiSellIngredient.getItemId()) : null;
                this.writeD(n);
                this.writeH(n > 0 ? itemTemplate.getType2() : 65535);
                this.writeQ(multiSellIngredient.getItemCount());
                this.writeH(multiSellIngredient.getItemEnchant());
                this.writeD(0);
                this.writeD(0);
                this.writeItemElements(multiSellIngredient);
                this.writeC(0);
                this.writeC(0);
            }
        }
    }

    protected void writeItemElements(MultiSellIngredient multiSellIngredient) {
        if (multiSellIngredient.getItemId() <= 0) {
            this.writeItemElements();
            return;
        }
        ItemTemplate itemTemplate = ItemHolder.getInstance().getTemplate(multiSellIngredient.getItemId());
        if (multiSellIngredient.getItemAttributes().getValue() > 0) {
            if (itemTemplate.isWeapon()) {
                Element element = multiSellIngredient.getItemAttributes().getElement();
                this.writeH(element.getId());
                this.writeH(multiSellIngredient.getItemAttributes().getValue(element) + itemTemplate.getBaseAttributeValue(element));
                this.writeH(0);
                this.writeH(0);
                this.writeH(0);
                this.writeH(0);
                this.writeH(0);
                this.writeH(0);
            } else if (itemTemplate.isArmor()) {
                this.writeH(-1);
                this.writeH(0);
                for (Element element : Element.VALUES) {
                    this.writeH(multiSellIngredient.getItemAttributes().getValue(element) + itemTemplate.getBaseAttributeValue(element));
                }
            } else {
                this.writeItemElements();
            }
        } else {
            this.writeItemElements();
        }
    }

    protected void writeItemElements() {
        this.writeH(-1);
        this.writeH(0);
        this.writeH(0);
        this.writeH(0);
        this.writeH(0);
        this.writeH(0);
        this.writeH(0);
        this.writeH(0);
    }
}
