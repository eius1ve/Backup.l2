/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.templates.item;

import java.util.Calendar;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.components.SystemMsg;

/*
 * Uses 'sealed' constructs - enablewith --sealed true
 */
public static abstract class ItemTemplate.ReuseType
extends Enum<ItemTemplate.ReuseType> {
    public static final /* enum */ ItemTemplate.ReuseType NORMAL = new ItemTemplate.ReuseType(new SystemMsg[]{SystemMsg.THERE_ARE_S2_SECONDS_REMAINING_IN_S1S_REUSE_TIME, SystemMsg.THERE_ARE_S2_MINUTES_S3_SECONDS_REMAINING_IN_S1S_REUSE_TIME, SystemMsg.THERE_ARE_S2_HOURS_S3_MINUTES_AND_S4_SECONDS_REMAINING_IN_S1S_REUSE_TIME}){

        @Override
        public long next(ItemInstance itemInstance) {
            return System.currentTimeMillis() + (long)itemInstance.getTemplate().getReuseDelay();
        }
    };
    public static final /* enum */ ItemTemplate.ReuseType EVERY_DAY_AT_6_30 = new ItemTemplate.ReuseType(new SystemMsg[]{SystemMsg.THERE_ARE_S2_SECONDS_REMAINING_FOR_S1S_REUSE_TIME, SystemMsg.THERE_ARE_S2_MINUTES_S3_SECONDS_REMAINING_FOR_S1S_REUSE_TIME, SystemMsg.THERE_ARE_S2_HOURS_S3_MINUTES_S4_SECONDS_REMAINING_FOR_S1S_REUSE_TIME}){

        @Override
        public long next(ItemInstance itemInstance) {
            Calendar calendar = Calendar.getInstance();
            if (calendar.get(11) > 6 || calendar.get(11) == 6 && calendar.get(12) >= 30) {
                calendar.add(5, 1);
            }
            calendar.set(11, 6);
            calendar.set(12, 30);
            return calendar.getTimeInMillis();
        }
    };
    private SystemMsg[] b;
    private static final /* synthetic */ ItemTemplate.ReuseType[] a;

    public static ItemTemplate.ReuseType[] values() {
        return (ItemTemplate.ReuseType[])a.clone();
    }

    public static ItemTemplate.ReuseType valueOf(String string) {
        return Enum.valueOf(ItemTemplate.ReuseType.class, string);
    }

    private ItemTemplate.ReuseType(SystemMsg ... systemMsgArray) {
        this.b = systemMsgArray;
    }

    public abstract long next(ItemInstance var1);

    public SystemMsg[] getMessages() {
        return this.b;
    }

    private static /* synthetic */ ItemTemplate.ReuseType[] a() {
        return new ItemTemplate.ReuseType[]{NORMAL, EVERY_DAY_AT_6_30};
    }

    static {
        a = ItemTemplate.ReuseType.a();
    }
}
