/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.base.Race
 */
package services;

import l2.gameserver.model.Player;
import l2.gameserver.model.base.Race;

/*
 * Uses 'sealed' constructs - enablewith --sealed true
 */
private static abstract class SupportMagic.NewbieBuffsListType
extends Enum<SupportMagic.NewbieBuffsListType> {
    public static final /* enum */ SupportMagic.NewbieBuffsListType WARRIOR = new SupportMagic.NewbieBuffsListType(){

        @Override
        public boolean canUse(Player player) {
            return !player.isMageClass() || player.getTemplate().race == Race.orc;
        }
    };
    public static final /* enum */ SupportMagic.NewbieBuffsListType MAGE = new SupportMagic.NewbieBuffsListType(){

        @Override
        public boolean canUse(Player player) {
            return player.isMageClass() && player.getTemplate().race != Race.orc;
        }
    };
    private static final /* synthetic */ SupportMagic.NewbieBuffsListType[] a;

    public static SupportMagic.NewbieBuffsListType[] values() {
        return (SupportMagic.NewbieBuffsListType[])a.clone();
    }

    public static SupportMagic.NewbieBuffsListType valueOf(String string) {
        return Enum.valueOf(SupportMagic.NewbieBuffsListType.class, string);
    }

    public abstract boolean canUse(Player var1);

    private static /* synthetic */ SupportMagic.NewbieBuffsListType[] a() {
        return new SupportMagic.NewbieBuffsListType[]{WARRIOR, MAGE};
    }

    static {
        a = SupportMagic.NewbieBuffsListType.a();
    }
}
