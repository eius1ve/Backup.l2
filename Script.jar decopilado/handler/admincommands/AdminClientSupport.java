/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.data.xml.holder.ItemHolder
 *  l2.gameserver.data.xml.holder.NpcHolder
 *  l2.gameserver.model.GameObject
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.Skill
 *  l2.gameserver.model.instances.NpcInstance
 *  l2.gameserver.model.items.ItemInstance
 *  l2.gameserver.network.l2.components.IStaticPacket
 *  l2.gameserver.network.l2.components.SystemMsg
 *  l2.gameserver.network.l2.s2c.SystemMessage
 *  l2.gameserver.tables.SkillTable
 *  l2.gameserver.templates.item.ItemTemplate
 *  l2.gameserver.templates.npc.NpcTemplate
 *  l2.gameserver.utils.ItemFunctions
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package handler.admincommands;

import handler.admincommands.ScriptAdminCommand;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.data.xml.holder.NpcHolder;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.templates.npc.NpcTemplate;
import l2.gameserver.utils.ItemFunctions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdminClientSupport
extends ScriptAdminCommand {
    private static final Logger G = LoggerFactory.getLogger(AdminClientSupport.class);

    public boolean useAdminCommand(Enum enum_, String[] stringArray, String string, Player player) {
        Commands commands = (Commands)enum_;
        GameObject gameObject = player.getTarget();
        switch (commands) {
            case admin_setskill: {
                if (stringArray.length != 3) {
                    return false;
                }
                if (!player.getPlayerAccess().CanEditChar) {
                    return false;
                }
                if (gameObject == null || !gameObject.isPlayer()) {
                    return false;
                }
                try {
                    Skill skill = SkillTable.getInstance().getInfo(Integer.parseInt(stringArray[1]), Integer.parseInt(stringArray[2]));
                    gameObject.getPlayer().addSkill(skill, true);
                    gameObject.getPlayer().sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_HAVE_EARNED_S1_SKILL).addSkillName(skill.getId(), skill.getLevel()));
                    break;
                }
                catch (NumberFormatException numberFormatException) {
                    G.info("AdminClientSupport:useAdminCommand(Enum,String[],String,L2Player): " + numberFormatException, (Throwable)numberFormatException);
                    return false;
                }
            }
            case admin_summon: {
                if (stringArray.length != 3) {
                    return false;
                }
                if (!player.getPlayerAccess().CanEditChar) {
                    return false;
                }
                try {
                    int n = Integer.parseInt(stringArray[1]);
                    long l = Long.parseLong(stringArray[2]);
                    if (n >= 1000000) {
                        if (gameObject == null) {
                            gameObject = player;
                        }
                        NpcTemplate npcTemplate = NpcHolder.getInstance().getTemplate(n - 1000000);
                        int n2 = 0;
                        while ((long)n2 < l) {
                            NpcInstance npcInstance = npcTemplate.getNewInstance();
                            npcInstance.setSpawnedLoc(gameObject.getLoc());
                            npcInstance.setCurrentHpMp((double)npcInstance.getMaxHp(), (double)npcInstance.getMaxMp(), true);
                            npcInstance.spawnMe(npcInstance.getSpawnedLoc());
                            ++n2;
                        }
                    } else {
                        if (gameObject == null) {
                            gameObject = player;
                        }
                        if (!gameObject.isPlayer()) {
                            return false;
                        }
                        ItemTemplate itemTemplate = ItemHolder.getInstance().getTemplate(n);
                        if (itemTemplate == null) {
                            return false;
                        }
                        if (itemTemplate.isStackable()) {
                            ItemInstance itemInstance = ItemFunctions.createItem((int)n);
                            itemInstance.setCount(l);
                            gameObject.getPlayer().getInventory().addItem(itemInstance);
                            gameObject.getPlayer().sendPacket((IStaticPacket)SystemMessage.obtainItems((ItemInstance)itemInstance));
                            break;
                        }
                        int n3 = 0;
                        while ((long)n3 < l) {
                            ItemInstance itemInstance = ItemFunctions.createItem((int)n);
                            gameObject.getPlayer().getInventory().addItem(itemInstance);
                            gameObject.getPlayer().sendPacket((IStaticPacket)SystemMessage.obtainItems((ItemInstance)itemInstance));
                            ++n3;
                        }
                    }
                    break;
                }
                catch (NumberFormatException numberFormatException) {
                    G.info("AdminClientSupport:useAdminCommand(Enum,String[],String,L2Player): " + numberFormatException, (Throwable)numberFormatException);
                    return false;
                }
            }
        }
        return true;
    }

    public Enum[] getAdminCommandEnum() {
        return Commands.values();
    }

    public static final class Commands
    extends Enum<Commands> {
        public static final /* enum */ Commands admin_setskill = new Commands();
        public static final /* enum */ Commands admin_summon = new Commands();
        private static final /* synthetic */ Commands[] a;

        public static Commands[] values() {
            return (Commands[])a.clone();
        }

        public static Commands valueOf(String string) {
            return Enum.valueOf(Commands.class, string);
        }

        private static /* synthetic */ Commands[] a() {
            return new Commands[]{admin_setskill, admin_summon};
        }

        static {
            a = Commands.a();
        }
    }
}
