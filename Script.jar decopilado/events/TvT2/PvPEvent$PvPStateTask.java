/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 */
package events.TvT2;

import events.TvT2.PvPEvent;
import l2.commons.threading.RunnableImpl;

private class PvPEvent.PvPStateTask
extends RunnableImpl {
    private final PvPEvent.PvPEventState b;

    public PvPEvent.PvPStateTask(PvPEvent.PvPEventState pvPEventState) {
        this.b = pvPEventState;
    }

    public void runImpl() throws Exception {
        try {
            switch (this.b) {
                case STANDBY: {
                    PvPEvent.getInstance().I();
                    break;
                }
                case REGISTRATION: {
                    PvPEvent.getInstance().goRegistration();
                    break;
                }
                case PORTING_TO: {
                    PvPEvent.getInstance().K();
                    break;
                }
                case PREPARE_TO: {
                    PvPEvent.getInstance().J();
                    break;
                }
                case COMPETITION: {
                    PvPEvent.getInstance().L();
                    break;
                }
                case WINNER: {
                    PvPEvent.getInstance().M();
                    break;
                }
                case PREPARE_FROM: {
                    PvPEvent.getInstance().N();
                    break;
                }
                case PORTING_FROM: {
                    PvPEvent.getInstance().O();
                }
            }
        }
        catch (Exception exception) {
            _log.warn("PvPEvent: Exception on changing state to " + this.b + " state.", (Throwable)exception);
            throw new RuntimeException(exception);
        }
    }
}
