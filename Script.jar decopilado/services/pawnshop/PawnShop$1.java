/*
 * Decompiled with CFR 0.152.
 */
package services.pawnshop;

import java.util.Comparator;
import services.pawnshop.PawnShop;

class PawnShop.1
implements Comparator<PawnShop.PawnShopItem> {
    PawnShop.1() {
    }

    @Override
    public int compare(PawnShop.PawnShopItem pawnShopItem, PawnShop.PawnShopItem pawnShopItem2) {
        if (pawnShopItem.getCurrencyItemId() == pawnShopItem2.getCurrencyItemId()) {
            return pawnShopItem.getPrice() - pawnShopItem2.getPrice();
        }
        return pawnShopItem2.getCurrencyItemId() - pawnShopItem.getCurrencyItemId();
    }
}
