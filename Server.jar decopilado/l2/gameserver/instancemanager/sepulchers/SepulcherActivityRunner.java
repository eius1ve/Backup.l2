/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.instancemanager.sepulchers;

import java.util.concurrent.ScheduledFuture;
import l2.commons.threading.RunnableImpl;
import l2.commons.time.cron.SchedulingPattern;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.instancemanager.sepulchers.FourSepulchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SepulcherActivityRunner {
    private static final SepulcherActivityRunner a = new SepulcherActivityRunner();
    private static final Logger by = LoggerFactory.getLogger(SepulcherActivityRunner.class);
    private ScheduledFuture P;

    public static SepulcherActivityRunner getInstance() {
        return a;
    }

    public void start() {
        if (this.P != null && !this.P.isDone()) {
            throw new IllegalStateException();
        }
        this.b(State.REGISTRATION_OPEN);
    }

    private void a(State state) {
        switch (state) {
            case REGISTRATION_OPEN: {
                FourSepulchers.getInstance().openRegistration();
                this.b(State.ACTIVE);
                break;
            }
            case ACTIVE: {
                FourSepulchers.getInstance().activate();
                this.b(State.COLLAPSE);
                break;
            }
            case COLLAPSE: {
                FourSepulchers.getInstance().collapse();
                this.b(State.MOB_DESPAWN);
                break;
            }
            case MOB_DESPAWN: {
                FourSepulchers.getInstance().deactivate();
                this.b(State.REGISTRATION_OPEN);
            }
        }
    }

    private void b(final State state) {
        long l = System.currentTimeMillis();
        this.P = ThreadPoolManager.getInstance().schedule(new RunnableImpl(){

            @Override
            public void runImpl() throws Exception {
                SepulcherActivityRunner.this.a(state);
            }
        }, state.getCron().next(l) - l);
    }

    private static final class State
    extends Enum<State> {
        public static final /* enum */ State REGISTRATION_OPEN = new State("55 * * * *");
        public static final /* enum */ State ACTIVE = new State("0 * * * *");
        public static final /* enum */ State COLLAPSE = new State("50 * * * *");
        public static final /* enum */ State MOB_DESPAWN = new State("54 * * * *");
        private final SchedulingPattern b;
        private static final /* synthetic */ State[] a;

        public static State[] values() {
            return (State[])a.clone();
        }

        public static State valueOf(String string) {
            return Enum.valueOf(State.class, string);
        }

        private State(String string2) {
            this.b = new SchedulingPattern(string2);
        }

        public SchedulingPattern getCron() {
            return this.b;
        }

        private static /* synthetic */ State[] a() {
            return new State[]{REGISTRATION_OPEN, ACTIVE, COLLAPSE, MOB_DESPAWN};
        }

        static {
            a = State.a();
        }
    }
}
