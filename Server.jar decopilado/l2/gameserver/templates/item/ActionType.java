/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.templates.item;

public final class ActionType
extends Enum<ActionType> {
    public static final /* enum */ ActionType ACTION_CALC = new ActionType();
    public static final /* enum */ ActionType ACTION_CALL_SKILL = new ActionType();
    public static final /* enum */ ActionType ACTION_CAPSULE = new ActionType();
    public static final /* enum */ ActionType ACTION_CREATE_MPCC = new ActionType();
    public static final /* enum */ ActionType ACTION_DICE = new ActionType();
    public static final /* enum */ ActionType ACTION_EQUIP = new ActionType();
    public static final /* enum */ ActionType ACTION_FISHINGSHOT = new ActionType();
    public static final /* enum */ ActionType ACTION_HARVEST = new ActionType();
    public static final /* enum */ ActionType ACTION_HIDE_NAME = new ActionType();
    public static final /* enum */ ActionType ACTION_KEEP_EXP = new ActionType();
    public static final /* enum */ ActionType ACTION_NICK_COLOR = new ActionType();
    public static final /* enum */ ActionType ACTION_NONE = new ActionType();
    public static final /* enum */ ActionType ACTION_PEEL = new ActionType();
    public static final /* enum */ ActionType ACTION_RECIPE = new ActionType();
    public static final /* enum */ ActionType ACTION_SEED = new ActionType();
    public static final /* enum */ ActionType ACTION_SHOW_ADVENTURER_GUIDE_BOOK = new ActionType();
    public static final /* enum */ ActionType ACTION_SHOW_HTML = new ActionType();
    public static final /* enum */ ActionType ACTION_SHOW_SSQ_STATUS = new ActionType();
    public static final /* enum */ ActionType ACTION_SKILL_MAINTAIN = new ActionType();
    public static final /* enum */ ActionType ACTION_SKILL_REDUCE = new ActionType();
    public static final /* enum */ ActionType ACTION_SOULSHOT = new ActionType();
    public static final /* enum */ ActionType ACTION_SPIRITSHOT = new ActionType();
    public static final /* enum */ ActionType ACTION_START_QUEST = new ActionType();
    public static final /* enum */ ActionType ACTION_SUMMON_SOULSHOT = new ActionType();
    public static final /* enum */ ActionType ACTION_SUMMON_SPIRITSHOT = new ActionType();
    public static final /* enum */ ActionType ACTION_XMAS_OPEN = new ActionType();
    public static final /* enum */ ActionType ACTION_SKILL_REDUCE_ON_SKILL_SUCCESS = new ActionType();
    public static final /* enum */ ActionType ACTION_SHOW_TUTORIAL = new ActionType();
    public static final /* enum */ ActionType ACTION_SHOW_MAP = new ActionType();
    private static final /* synthetic */ ActionType[] a;

    public static ActionType[] values() {
        return (ActionType[])a.clone();
    }

    public static ActionType valueOf(String string) {
        return Enum.valueOf(ActionType.class, string);
    }

    private static /* synthetic */ ActionType[] a() {
        return new ActionType[]{ACTION_CALC, ACTION_CALL_SKILL, ACTION_CAPSULE, ACTION_CREATE_MPCC, ACTION_DICE, ACTION_EQUIP, ACTION_FISHINGSHOT, ACTION_HARVEST, ACTION_HIDE_NAME, ACTION_KEEP_EXP, ACTION_NICK_COLOR, ACTION_NONE, ACTION_PEEL, ACTION_RECIPE, ACTION_SEED, ACTION_SHOW_ADVENTURER_GUIDE_BOOK, ACTION_SHOW_HTML, ACTION_SHOW_SSQ_STATUS, ACTION_SKILL_MAINTAIN, ACTION_SKILL_REDUCE, ACTION_SOULSHOT, ACTION_SPIRITSHOT, ACTION_START_QUEST, ACTION_SUMMON_SOULSHOT, ACTION_SUMMON_SPIRITSHOT, ACTION_XMAS_OPEN, ACTION_SKILL_REDUCE_ON_SKILL_SUCCESS, ACTION_SHOW_TUTORIAL, ACTION_SHOW_MAP};
    }

    static {
        a = ActionType.a();
    }
}
