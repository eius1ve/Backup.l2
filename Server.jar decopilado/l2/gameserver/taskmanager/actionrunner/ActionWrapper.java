/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.taskmanager.actionrunner;

import java.util.concurrent.Future;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.taskmanager.actionrunner.ActionRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ActionWrapper
extends RunnableImpl {
    private static final Logger dz = LoggerFactory.getLogger(ActionWrapper.class);
    private final String gg;
    private Future<?> Q;

    public ActionWrapper(String string) {
        this.gg = string;
    }

    public void schedule(long l) {
        this.Q = ThreadPoolManager.getInstance().schedule(this, l);
    }

    public void cancel() {
        if (this.Q != null) {
            this.Q.cancel(true);
            this.Q = null;
        }
    }

    public abstract void runImpl0() throws Exception;

    @Override
    public void runImpl() {
        try {
            this.runImpl0();
        }
        catch (Exception exception) {
            dz.info("ActionWrapper: Exception: " + exception + "; name: " + this.gg, (Throwable)exception);
        }
        finally {
            ActionRunner.getInstance().remove(this.gg, this);
            this.Q = null;
        }
    }

    public String getName() {
        return this.gg;
    }
}
