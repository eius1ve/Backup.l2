/*
 * Decompiled with CFR 0.152.
 */
package l2.commons.dao;

public final class JdbcEntityState
extends Enum<JdbcEntityState> {
    public static final /* enum */ JdbcEntityState CREATED = new JdbcEntityState(true, false, false, false);
    public static final /* enum */ JdbcEntityState STORED = new JdbcEntityState(false, true, false, true);
    public static final /* enum */ JdbcEntityState UPDATED = new JdbcEntityState(false, true, true, true);
    public static final /* enum */ JdbcEntityState DELETED = new JdbcEntityState(false, false, false, false);
    private final boolean ar;
    private final boolean as;
    private final boolean at;
    private final boolean au;
    private static final /* synthetic */ JdbcEntityState[] a;

    public static JdbcEntityState[] values() {
        return (JdbcEntityState[])a.clone();
    }

    public static JdbcEntityState valueOf(String string) {
        return Enum.valueOf(JdbcEntityState.class, string);
    }

    private JdbcEntityState(boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        this.ar = bl;
        this.as = bl2;
        this.at = bl3;
        this.au = bl4;
    }

    public boolean isSavable() {
        return this.ar;
    }

    public boolean isDeletable() {
        return this.as;
    }

    public boolean isUpdatable() {
        return this.at;
    }

    public boolean isPersisted() {
        return this.au;
    }

    private static /* synthetic */ JdbcEntityState[] a() {
        return new JdbcEntityState[]{CREATED, STORED, UPDATED, DELETED};
    }

    static {
        a = JdbcEntityState.a();
    }
}
