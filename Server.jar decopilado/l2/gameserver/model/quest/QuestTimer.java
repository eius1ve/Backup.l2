/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.quest;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.quest.QuestState;

public class QuestTimer
extends RunnableImpl {
    private String _name;
    private NpcInstance _npc;
    private long bY;
    private QuestState a;
    private ScheduledFuture<?> ae;

    public QuestTimer(String string, long l, NpcInstance npcInstance) {
        this._name = string;
        this.bY = l;
        this._npc = npcInstance;
    }

    void setQuestState(QuestState questState) {
        this.a = questState;
    }

    QuestState getQuestState() {
        return this.a;
    }

    void start() {
        this.ae = ThreadPoolManager.getInstance().schedule(this, this.bY);
    }

    @Override
    public void runImpl() throws Exception {
        QuestState questState = this.getQuestState();
        if (questState != null) {
            questState.removeQuestTimer(this.getName());
            questState.getQuest().notifyEvent(this.getName(), questState, this.getNpc());
        }
    }

    void pause() {
        if (this.ae != null) {
            this.bY = this.ae.getDelay(TimeUnit.SECONDS);
            this.ae.cancel(false);
        }
    }

    void stop() {
        if (this.ae != null) {
            this.ae.cancel(false);
        }
    }

    public boolean isActive() {
        return this.ae != null && !this.ae.isDone();
    }

    public String getName() {
        return this._name;
    }

    public long getTime() {
        return this.bY;
    }

    public NpcInstance getNpc() {
        return this._npc;
    }

    public final String toString() {
        return this._name;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (object.getClass() != this.getClass()) {
            return false;
        }
        return ((QuestTimer)object).getName().equals(this.getName());
    }
}
