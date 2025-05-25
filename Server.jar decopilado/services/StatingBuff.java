/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.listener.Listener
 *  l2.gameserver.Config
 *  l2.gameserver.listener.actor.player.OnPlayerEnterListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.actor.listener.CharListenerList
 *  l2.gameserver.scripts.Functions
 *  l2.gameserver.scripts.ScriptFile
 *  l2.gameserver.tables.SkillTable
 *  org.apache.commons.lang3.tuple.Pair
 */
package services;

import l2.commons.listener.Listener;
import l2.gameserver.Config;
import l2.gameserver.listener.actor.player.OnPlayerEnterListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.actor.listener.CharListenerList;
import l2.gameserver.scripts.Functions;
import l2.gameserver.scripts.ScriptFile;
import l2.gameserver.tables.SkillTable;
import org.apache.commons.lang3.tuple.Pair;

public class StatingBuff
extends Functions
implements OnPlayerEnterListener,
ScriptFile {
    public void onPlayerEnter(Player player) {
        if (player.getOnlineTime() == 0L) {
            for (Pair pair : player.isMageClass() ? Config.OTHER_MAGE_BUFF_ON_CHAR_CREATE : Config.OTHER_WARRIOR_BUFF_ON_CHAR_CREATE) {
                Skill skill = SkillTable.getInstance().getInfo(((Integer)pair.getLeft()).intValue(), ((Integer)pair.getRight()).intValue());
                skill.getEffects((Creature)player, (Creature)player, false, false, Config.OTHER_BUFF_ON_CHAR_CREATE_TIME_MODIFIER, 1.0, false);
            }
        }
    }

    public void onLoad() {
        if (!Config.OTHER_MAGE_BUFF_ON_CHAR_CREATE.isEmpty() || !Config.OTHER_WARRIOR_BUFF_ON_CHAR_CREATE.isEmpty()) {
            CharListenerList.addGlobal((Listener)this);
        }
    }

    public void onReload() {
    }

    public void onShutdown() {
    }
}
