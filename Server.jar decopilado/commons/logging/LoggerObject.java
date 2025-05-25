/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.commons.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class LoggerObject {
    protected final Logger _log = LoggerFactory.getLogger(this.getClass());

    public void error(String string, Exception exception) {
        this._log.error(this.getClass().getSimpleName() + ": " + string, (Throwable)exception);
    }

    public void error(String string) {
        this._log.error(this.getClass().getSimpleName() + ": " + string);
    }

    public void warn(String string, Exception exception) {
        this._log.warn(this.getClass().getSimpleName() + ": " + string, (Throwable)exception);
    }

    public void warn(String string) {
        this._log.warn(this.getClass().getSimpleName() + ": " + string);
    }

    public void info(String string, Exception exception) {
        this._log.info(this.getClass().getSimpleName() + ": " + string, (Throwable)exception);
    }

    public void info(String string) {
        this._log.info(this.getClass().getSimpleName() + ": " + string);
    }
}
