/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.instances;

import java.util.ArrayList;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.model.Creature;

class SymbolInstance.1
extends RunnableImpl {
    SymbolInstance.1() {
    }

    @Override
    public void runImpl() throws Exception {
        for (Creature creature : SymbolInstance.this.getAroundCharacters(200, 200)) {
            if (SymbolInstance.this.r.checkTarget(SymbolInstance.this.b, creature, null, false, false) != null) continue;
            ArrayList<Creature> arrayList = new ArrayList<Creature>();
            if (!SymbolInstance.this.r.isAoE()) {
                arrayList.add(creature);
            } else {
                for (Creature creature2 : SymbolInstance.this.getAroundCharacters(SymbolInstance.this.r.getSkillRadius(), 128)) {
                    if (SymbolInstance.this.r.checkTarget(SymbolInstance.this.b, creature2, null, false, false) != null) continue;
                    arrayList.add(creature);
                }
            }
            SymbolInstance.this.r.useSkill(SymbolInstance.this, arrayList);
        }
    }
}
