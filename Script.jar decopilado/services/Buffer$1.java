/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.threading.RunnableImpl
 *  l2.gameserver.model.Player
 */
package services;

import java.util.List;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.Player;
import services.Buffer;

static class Buffer.1
extends RunnableImpl {
    final /* synthetic */ List val$profileBuffTemplates;
    final /* synthetic */ Player val$player;

    Buffer.1(List list, Player player) {
        this.val$profileBuffTemplates = list;
        this.val$player = player;
    }

    public void runImpl() throws Exception {
        for (Buffer.BuffTemplate buffTemplate : this.val$profileBuffTemplates) {
            if (buffTemplate.applySync(this.val$player)) continue;
            return;
        }
    }
}
