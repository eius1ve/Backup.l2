/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.instances;

import java.util.ArrayList;
import l2.commons.lang.reference.HardReference;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Skill;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.instances.TrapInstance;
import l2.gameserver.network.l2.components.CustomMessage;

private static class TrapInstance.CastTask
extends RunnableImpl {
    private HardReference<NpcInstance> Z;

    public TrapInstance.CastTask(TrapInstance trapInstance) {
        this.Z = trapInstance.getRef();
    }

    @Override
    public void runImpl() throws Exception {
        TrapInstance trapInstance = (TrapInstance)this.Z.get();
        if (trapInstance == null) {
            return;
        }
        Creature creature = trapInstance.getOwner();
        if (creature == null) {
            return;
        }
        for (Creature creature2 : trapInstance.getAroundCharacters(200, 200)) {
            if (creature2 == creature || trapInstance.s.checkTarget(creature, creature2, null, false, false) != null) continue;
            ArrayList<Creature> arrayList = new ArrayList<Creature>();
            if (trapInstance.s.getTargetType() != Skill.SkillTargetType.TARGET_AREA) {
                arrayList.add(creature2);
            } else {
                for (Creature creature3 : trapInstance.getAroundCharacters(trapInstance.s.getSkillRadius(), 128)) {
                    if (trapInstance.s.checkTarget(creature, creature3, null, false, false) != null) continue;
                    arrayList.add(creature2);
                }
            }
            trapInstance.s.useSkill(trapInstance, arrayList);
            if (creature2.isPlayer()) {
                creature2.sendMessage(new CustomMessage("common.Trap", creature2.getPlayer(), new Object[0]));
            }
            trapInstance.deleteMe();
            break;
        }
    }
}
