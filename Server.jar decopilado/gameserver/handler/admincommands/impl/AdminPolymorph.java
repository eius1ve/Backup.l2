/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.handler.admincommands.impl;

import l2.gameserver.data.xml.holder.NpcHolder;
import l2.gameserver.handler.admincommands.IAdminCommandHandler;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;

public class AdminPolymorph
implements IAdminCommandHandler {
    @Override
    public boolean useAdminCommand(Enum enum_, String[] stringArray, String string, Player player) {
        Commands commands = (Commands)enum_;
        if (!player.getPlayerAccess().CanPolymorph) {
            return false;
        }
        GameObject gameObject = player.getTarget();
        switch (commands) {
            case admin_polyself: {
                gameObject = player;
            }
            case admin_polymorph: 
            case admin_poly: {
                if (gameObject == null || !gameObject.isPlayer()) {
                    player.sendPacket((IStaticPacket)SystemMsg.INVALID_TARGET);
                    return false;
                }
                try {
                    int n = Integer.parseInt(stringArray[1]);
                    if (NpcHolder.getInstance().getTemplate(n) == null) break;
                    ((Player)gameObject).setPolyId(n);
                    ((Player)gameObject).broadcastCharInfo();
                    break;
                }
                catch (Exception exception) {
                    player.sendMessage("USAGE: //poly id [type:npc|item]");
                    return false;
                }
            }
            case admin_unpolyself: {
                gameObject = player;
            }
            case admin_unpolymorph: 
            case admin_unpoly: {
                if (gameObject == null || !gameObject.isPlayer()) {
                    player.sendPacket((IStaticPacket)SystemMsg.INVALID_TARGET);
                    return false;
                }
                ((Player)gameObject).setPolyId(0);
                ((Player)gameObject).broadcastCharInfo();
            }
        }
        return true;
    }

    @Override
    public Enum[] getAdminCommandEnum() {
        return Commands.values();
    }

    private static final class Commands
    extends Enum<Commands> {
        public static final /* enum */ Commands admin_polyself = new Commands();
        public static final /* enum */ Commands admin_polymorph = new Commands();
        public static final /* enum */ Commands admin_poly = new Commands();
        public static final /* enum */ Commands admin_unpolyself = new Commands();
        public static final /* enum */ Commands admin_unpolymorph = new Commands();
        public static final /* enum */ Commands admin_unpoly = new Commands();
        private static final /* synthetic */ Commands[] a;

        public static Commands[] values() {
            return (Commands[])a.clone();
        }

        public static Commands valueOf(String string) {
            return Enum.valueOf(Commands.class, string);
        }

        private static /* synthetic */ Commands[] a() {
            return new Commands[]{admin_polyself, admin_polymorph, admin_poly, admin_unpolyself, admin_unpolymorph, admin_unpoly};
        }

        static {
            a = Commands.a();
        }
    }
}
