/*
 * Decompiled with CFR 0.152.
 */
package l2.authserver.network.l2;

import java.util.concurrent.ConcurrentLinkedQueue;
import l2.authserver.network.l2.BaseLoginClient;
import l2.commons.threading.RunnableImpl;

private static class SelectorHelper.ClientCloseTask
extends RunnableImpl {
    private ConcurrentLinkedQueue<BaseLoginClient> a = new ConcurrentLinkedQueue();
    private ConcurrentLinkedQueue<BaseLoginClient> b = new ConcurrentLinkedQueue();

    public void addClient(BaseLoginClient baseLoginClient) {
        this.a.add(baseLoginClient);
    }

    @Override
    public void runImpl() throws Exception {
        BaseLoginClient baseLoginClient = this.b.poll();
        while (baseLoginClient != null) {
            try {
                baseLoginClient.closeNow(false);
            }
            catch (Exception exception) {
                // empty catch block
            }
            baseLoginClient = this.b.poll();
        }
        baseLoginClient = this.a.poll();
        while (baseLoginClient != null) {
            if (baseLoginClient.isAuthed() || baseLoginClient.isConnected()) {
                this.b.add(baseLoginClient);
            }
            baseLoginClient = this.a.poll();
        }
    }
}
