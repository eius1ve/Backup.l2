/*
 * Decompiled with CFR 0.152.
 */
package l2.commons.db;

public static class BaseDataConnectionFactory.TimeoutException
extends RuntimeException {
    private static final long aq = 1L;

    public BaseDataConnectionFactory.TimeoutException() {
        super("Timeout while waiting for a free database connection.");
    }

    public BaseDataConnectionFactory.TimeoutException(String string) {
        super(string);
    }
}
