/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.listener.Listener
 *  l2.gameserver.instancemanager.ReflectionManager
 *  l2.gameserver.listener.zone.OnZoneEnterLeaveListener
 *  l2.gameserver.model.Creature
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.Zone
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.s2c.SkillCoolTime
 *  l2.gameserver.scripts.ScriptFile
 *  org.apache.commons.lang3.ArrayUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package zones;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import l2.commons.listener.Listener;
import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.listener.zone.OnZoneEnterLeaveListener;
import l2.gameserver.model.Creature;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.Zone;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.SkillCoolTime;
import l2.gameserver.scripts.ScriptFile;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProhibitSkillsZone
implements ScriptFile {
    private static final Logger eJ = LoggerFactory.getLogger(ProhibitSkillsZone.class);
    private static final String ji = "prohibitSkills";

    public void onLoad() {
        int n = 0;
        Collection collection = ReflectionManager.DEFAULT.getZones();
        for (Zone zone : collection) {
            int[] nArray = zone.getParams().getIntegerArray((Object)ji, ArrayUtils.EMPTY_INT_ARRAY);
            if (nArray.length <= 0) continue;
            zone.addListener((Listener)new ProhibitSkillsZoneListener(nArray));
            ++n;
        }
        if (n > 0) {
            eJ.info("SkillProhibitZone: added {} skill(s) prohibit zone(s).", (Object)n);
        }
    }

    public void onReload() {
        this.onShutdown();
        this.onLoad();
    }

    public void onShutdown() {
    }

    private static final class ProhibitSkillsZoneListener
    implements OnZoneEnterLeaveListener {
        private final int[] bW;

        private ProhibitSkillsZoneListener(int[] nArray) {
            this.bW = nArray;
        }

        public void onZoneEnter(Zone zone, Creature creature) {
            if (!creature.isPlayer()) {
                return;
            }
            Player player = creature.getPlayer();
            LinkedList linkedList = new LinkedList();
            for (int i = 0; i < this.bW.length; ++i) {
                List list;
                int n = this.bW[i];
                Skill skill = player.getKnownSkill(n);
                if (skill == null) continue;
                if (skill.isToggle() && (list = player.getEffectList().getEffectsBySkill(skill)) != null && !list.isEmpty()) {
                    linkedList.addAll(list);
                }
                player.addUnActiveSkill(skill);
                player.sendPacket((IStaticPacket)new SkillCoolTime(player));
                player.sendSkillList();
                player.updateStats();
                player.updateEffectIcons();
            }
        }

        public void onZoneLeave(Zone zone, Creature creature) {
            if (!creature.isPlayer()) {
                return;
            }
            Player player = creature.getPlayer();
            for (int i = 0; i < this.bW.length; ++i) {
                int n = this.bW[i];
                if (!player.isUnActiveSkill(n)) continue;
                Skill skill = player.getKnownSkill(n);
                if (skill != null) {
                    player.removeUnActiveSkill(skill);
                }
                player.sendSkillList();
                player.sendPacket((IStaticPacket)new SkillCoolTime(player));
                player.updateStats();
                player.updateEffectIcons();
            }
        }
    }
}
