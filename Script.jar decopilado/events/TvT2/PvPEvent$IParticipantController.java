/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Zone
 *  l2.gameserver.model.entity.Reflection
 */
package events.TvT2;

import l2.gameserver.model.Player;
import l2.gameserver.model.Zone;
import l2.gameserver.model.entity.Reflection;

private static interface PvPEvent.IParticipantController {
    public void prepareParticipantsTo();

    public void prepareParticipantsFrom();

    public void initParticipant();

    public void doneParicipant();

    public void portParticipantsTo();

    public void portParticipantsBack();

    public void initReflection();

    public void doneReflection();

    public Reflection getReflection();

    public void OnPlayerDied(Player var1, Player var2);

    public void OnEnter(Player var1, Zone var2);

    public void OnLeave(Player var1, Zone var2);

    public void OnExit(Player var1);

    public void OnTeleport(Player var1, int var2, int var3, int var4, Reflection var5);

    public void MakeWinner();
}
