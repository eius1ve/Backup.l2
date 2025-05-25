/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.network.l2.s2c;

import l2.gameserver.model.Player;
import l2.gameserver.model.actor.instances.player.ShortCut;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.skills.TimeStamp;
import l2.gameserver.tables.SkillTable;

public abstract class ShortCutPacket
extends L2GameServerPacket {
    public static ShortcutInfo convert(Player player, ShortCut shortCut) {
        ShortcutInfo shortcutInfo = null;
        int n = shortCut.getSlot() + shortCut.getPage() * 12;
        switch (shortCut.getType()) {
            case 1: {
                int n2 = -1;
                int n3 = 0;
                int n4 = 0;
                int n5 = 0;
                int n6 = 0;
                ItemInstance itemInstance = player.getInventory().getItemByObjectId(shortCut.getId());
                if (itemInstance != null) {
                    TimeStamp timeStamp;
                    n5 = itemInstance.getVariationStat1();
                    n6 = itemInstance.getVariationStat2();
                    n2 = itemInstance.getTemplate().getDisplayReuseGroup();
                    if (itemInstance.getTemplate().getReuseDelay() > 0 && (timeStamp = player.getSharedGroupReuse(itemInstance.getTemplate().getReuseGroup())) != null) {
                        n3 = (int)Math.round((double)timeStamp.getReuseCurrent() / 1000.0);
                        n4 = (int)Math.round((double)timeStamp.getReuseBasic() / 1000.0);
                    }
                }
                shortcutInfo = new ItemShortcutInfo(shortCut.getType(), n, shortCut.getId(), n2, n3, n4, n5, n6, shortCut.getCharacterType());
                break;
            }
            case 2: {
                if (shortCut.getLevel() < 100) {
                    shortcutInfo = new SkillShortcutInfo(shortCut.getType(), n, shortCut.getId(), shortCut.getLevel(), shortCut.getCharacterType(), -1);
                    break;
                }
                int n7 = SkillTable.getInstance().getBaseLevel(shortCut.getId());
                int n8 = shortCut.getLevel() % 100;
                int n9 = (1 + n8 / 40) * 1000 + n8 % 40;
                shortcutInfo = new SkillShortcutInfo(shortCut.getType(), n, shortCut.getId(), n9 << 16 | n7, shortCut.getCharacterType(), -1);
                break;
            }
            default: {
                shortcutInfo = new ShortcutInfo(shortCut.getType(), n, shortCut.getId(), shortCut.getCharacterType());
            }
        }
        return shortcutInfo;
    }

    protected static class ItemShortcutInfo
    extends ShortcutInfo {
        private int Br;
        private int Bs;
        private int Bt;
        private int Bu;
        private int Bv;

        public ItemShortcutInfo(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9) {
            super(n, n2, n3, n9);
            this.Br = n4;
            this.Bs = n5;
            this.Bt = n6;
            this.Bu = n7;
            this.Bv = n8;
        }

        @Override
        protected void write0(ShortCutPacket shortCutPacket) {
            shortCutPacket.writeD(this._id);
            shortCutPacket.writeD(this._characterType);
            shortCutPacket.writeD(this.Br);
            shortCutPacket.writeD(this.Bs);
            shortCutPacket.writeD(this.Bt);
            shortCutPacket.writeD(this.Bu);
            shortCutPacket.writeD(this.Bv);
            shortCutPacket.writeD(0);
        }
    }

    protected static class SkillShortcutInfo
    extends ShortcutInfo {
        private final int Bw;
        private final int Bx;

        public SkillShortcutInfo(int n, int n2, int n3, int n4, int n5, int n6) {
            super(n, n2, n3, n5);
            this.Bw = n4;
            this.Bx = n6;
        }

        @Override
        protected void write0(ShortCutPacket shortCutPacket) {
            shortCutPacket.writeD(this._id);
            shortCutPacket.writeD(this.Bw);
            shortCutPacket.writeD(this.Bx);
            shortCutPacket.writeC(0);
            shortCutPacket.writeD(this._characterType);
        }
    }

    protected static class ShortcutInfo {
        protected final int _type;
        protected final int _page;
        protected final int _id;
        protected final int _characterType;

        public ShortcutInfo(int n, int n2, int n3, int n4) {
            this._type = n;
            this._page = n2;
            this._id = n3;
            this._characterType = n4;
        }

        protected void write(ShortCutPacket shortCutPacket) {
            shortCutPacket.writeD(this._type);
            shortCutPacket.writeD(this._page);
            this.write0(shortCutPacket);
        }

        protected void write0(ShortCutPacket shortCutPacket) {
            shortCutPacket.writeD(this._id);
            shortCutPacket.writeD(this._characterType);
        }
    }
}
