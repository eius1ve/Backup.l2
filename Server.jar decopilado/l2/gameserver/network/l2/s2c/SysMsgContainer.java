/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.network.l2.s2c;

import java.util.ArrayList;
import java.util.List;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.Summon;
import l2.gameserver.model.base.Element;
import l2.gameserver.model.entity.residence.Residence;
import l2.gameserver.model.instances.DoorInstance;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.instances.StaticObjectInstance;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.utils.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SysMsgContainer<T extends SysMsgContainer<T>>
extends L2GameServerPacket {
    private static final Logger dd = LoggerFactory.getLogger(SysMsgContainer.class);
    protected SystemMsg _message;
    protected List<IArgument> _arguments;

    protected SysMsgContainer(SystemMsg systemMsg) {
        if (systemMsg == null) {
            throw new IllegalArgumentException("SystemMsg is null");
        }
        this._message = systemMsg;
        this._arguments = new ArrayList<IArgument>(this._message.size());
    }

    protected void writeElements() {
        if (this._message.size() != this._arguments.size()) {
            throw new IllegalArgumentException("Wrong count of arguments: " + this._message, new Exception());
        }
        this.writeH(this._message.id());
        this.writeC(this._arguments.size());
        for (IArgument iArgument : this._arguments) {
            iArgument.write(this);
        }
    }

    @Override
    public L2GameServerPacket packet(Player player) {
        if (this._message.size() != this._arguments.size()) {
            dd.debug("Wrong count of arguments: " + this._message, (Throwable)new Exception());
            return null;
        }
        return this;
    }

    public T addName(GameObject gameObject) {
        if (gameObject == null) {
            return this.add(new StringArgument(null));
        }
        if (gameObject.isNpc()) {
            return this.add(new NpcNameArgument(((NpcInstance)gameObject).getNpcId() + 1000000));
        }
        if (gameObject.isPlayer()) {
            return this.add(new PlayerNameArgument((Player)gameObject));
        }
        if (gameObject.isItem()) {
            return this.addItemName(((ItemInstance)gameObject).getItemId());
        }
        if (gameObject.isDoor()) {
            return this.add(new StaticObjectNameArgument(((DoorInstance)gameObject).getDoorId()));
        }
        if (gameObject instanceof Summon) {
            return this.add(new NpcNameArgument(((Summon)gameObject).getNpcId() + 1000000));
        }
        if (gameObject instanceof StaticObjectInstance) {
            return this.add(new StaticObjectNameArgument(((StaticObjectInstance)gameObject).getUId()));
        }
        return this.add(new StringArgument(gameObject.getName()));
    }

    public T addInstanceName(int n) {
        return this.add(new InstanceNameArgument(n));
    }

    public T addSysString(int n) {
        return this.add(new SysStringArgument(n));
    }

    public T addSkillName(Skill skill) {
        return this.addSkillName(skill.getDisplayId(), skill.getDisplayLevel());
    }

    public T addSkillName(int n, int n2) {
        if (n2 < 100) {
            return this.add(new SkillArgument(n, n2));
        }
        int n3 = SkillTable.getInstance().getBaseLevel(n);
        int n4 = n2 % 100;
        int n5 = (1 + n4 / 40) * 1000 + n4 % 40;
        return this.add(new SkillArgument(n, n3 | n5 << 16));
    }

    public T addVisibleDamage(GameObject gameObject, GameObject gameObject2, int n) {
        return this.add(new VisibleDamageArgument(gameObject, gameObject2, n));
    }

    public T addItemName(int n) {
        return this.add(new ItemNameArgument(n));
    }

    @Deprecated
    public T addItemNameWithAugmentation(ItemInstance itemInstance) {
        return this.add(new ItemNameWithAugmentationArgument(itemInstance.getItemId(), itemInstance.getVariationStat1(), itemInstance.getVariationStat2()));
    }

    public T addZoneName(Creature creature) {
        return this.addZoneName(creature.getX(), creature.getY(), creature.getZ());
    }

    public T addZoneName(Location location) {
        return this.add(new ZoneArgument(location.x, location.y, location.z));
    }

    public T addZoneName(int n, int n2, int n3) {
        return this.add(new ZoneArgument(n, n2, n3));
    }

    public T addResidenceName(Residence residence) {
        return this.add(new ResidenceArgument(residence.getId()));
    }

    public T addClassId(int n) {
        return this.add(new ClassIdArgument(n));
    }

    public T addResidenceName(int n) {
        return this.add(new ResidenceArgument(n));
    }

    public T addElementName(int n) {
        return this.add(new ElementNameArgument(n));
    }

    public T addElementName(Element element) {
        return this.add(new ElementNameArgument(element.getId()));
    }

    public T addExpandableNumber(long l) {
        if (l < 255L) {
            return this.add(new ByteArgument((int)l));
        }
        if (l < Integer.MAX_VALUE) {
            return this.add(new IntegerArgument((int)l));
        }
        return this.add(new LongArgument(l));
    }

    public T addNumber(int n) {
        return this.add(new IntegerArgument(n));
    }

    public T addNumber(long l) {
        return this.add(new LongArgument(l));
    }

    public T addString(String string) {
        return this.add(new StringArgument(string));
    }

    protected T add(IArgument iArgument) {
        this._arguments.add(iArgument);
        return (T)this;
    }

    public static abstract class IArgument {
        void write(SysMsgContainer<?> sysMsgContainer) {
            ((SysMsgContainer)sysMsgContainer).writeC(this.getType().ordinal());
            this.writeData(sysMsgContainer);
        }

        abstract Types getType();

        abstract void writeData(SysMsgContainer<?> var1);
    }

    private static class StringArgument
    extends IArgument {
        private final String fO;

        public StringArgument(String string) {
            this.fO = string == null ? "null" : string;
        }

        @Override
        void writeData(SysMsgContainer<?> sysMsgContainer) {
            ((SysMsgContainer)sysMsgContainer).writeS(this.fO);
        }

        @Override
        Types getType() {
            return Types.TEXT;
        }
    }

    private static class NpcNameArgument
    extends IntegerArgument {
        public NpcNameArgument(int n) {
            super(n);
        }

        @Override
        Types getType() {
            return Types.NPC_NAME;
        }
    }

    private static class PlayerNameArgument
    extends StringArgument {
        public PlayerNameArgument(Player player) {
            super(player.isCursedWeaponEquipped() ? player.getTransformationName() : player.getName());
        }

        @Override
        Types getType() {
            return Types.TEXT;
        }
    }

    private static class StaticObjectNameArgument
    extends IntegerArgument {
        public StaticObjectNameArgument(int n) {
            super(n);
        }

        @Override
        Types getType() {
            return Types.STATIC_OBJECT_NAME;
        }
    }

    public static class InstanceNameArgument
    extends IntegerArgument {
        public InstanceNameArgument(int n) {
            super(n);
        }

        @Override
        Types getType() {
            return Types.INSTANCE_NAME;
        }
    }

    private static class SysStringArgument
    extends IntegerArgument {
        public SysStringArgument(int n) {
            super(n);
        }

        @Override
        Types getType() {
            return Types.SYSTEM_STRING;
        }
    }

    private static class SkillArgument
    extends IArgument {
        private final int Ch;
        private final int Ci;

        public SkillArgument(int n, int n2) {
            this.Ch = n;
            this.Ci = n2;
        }

        @Override
        void writeData(SysMsgContainer<?> sysMsgContainer) {
            ((SysMsgContainer)sysMsgContainer).writeD(this.Ch);
            ((SysMsgContainer)sysMsgContainer).writeH(this.Ci);
        }

        @Override
        Types getType() {
            return Types.SKILL_NAME;
        }
    }

    private static class VisibleDamageArgument
    extends IArgument {
        private final int Cj;
        private final int Ck;
        private final int Cl;

        public VisibleDamageArgument(GameObject gameObject, GameObject gameObject2, int n) {
            this.Cj = gameObject.getObjectId();
            this.Ck = gameObject2.getObjectId();
            this.Cl = n;
        }

        @Override
        void writeData(SysMsgContainer<?> sysMsgContainer) {
            ((SysMsgContainer)sysMsgContainer).writeD(this.Ck);
            ((SysMsgContainer)sysMsgContainer).writeD(this.Cj);
            ((SysMsgContainer)sysMsgContainer).writeD(this.Cl);
        }

        @Override
        Types getType() {
            return Types.TYPE_VISIBLE_DAMAGE;
        }
    }

    private static class ItemNameArgument
    extends IntegerArgument {
        public ItemNameArgument(int n) {
            super(n);
        }

        @Override
        Types getType() {
            return Types.ITEM_NAME;
        }
    }

    private static class ItemNameWithAugmentationArgument
    extends IArgument {
        private final int Ce;
        private final int Cf;
        private final int Cg;

        public ItemNameWithAugmentationArgument(int n, int n2, int n3) {
            this.Ce = n;
            this.Cf = n2;
            this.Cg = n3;
        }

        @Override
        Types getType() {
            return Types.ITEM_NAME_WITH_AUGMENTATION;
        }

        void writeData(SysMsgContainer sysMsgContainer) {
            sysMsgContainer.writeD(this.Ce);
            sysMsgContainer.writeH(this.Cf);
            sysMsgContainer.writeH(this.Cg);
        }
    }

    private static class ZoneArgument
    extends IArgument {
        private final int Cm;
        private final int Cn;
        private final int Co;

        public ZoneArgument(int n, int n2, int n3) {
            this.Cm = n;
            this.Cn = n2;
            this.Co = n3;
        }

        @Override
        void writeData(SysMsgContainer<?> sysMsgContainer) {
            ((SysMsgContainer)sysMsgContainer).writeD(this.Cm);
            ((SysMsgContainer)sysMsgContainer).writeD(this.Cn);
            ((SysMsgContainer)sysMsgContainer).writeD(this.Co);
        }

        @Override
        Types getType() {
            return Types.ZONE_NAME;
        }
    }

    private static class ResidenceArgument
    extends IntegerArgument {
        public ResidenceArgument(int n) {
            super(n);
        }

        @Override
        Types getType() {
            return Types.RESIDENCE_NAME;
        }
    }

    private static class ClassIdArgument
    extends ShortArgument {
        public ClassIdArgument(int n) {
            super((short)n);
        }

        @Override
        Types getType() {
            return Types.CLASS_ID;
        }
    }

    private static class ElementNameArgument
    extends IntegerArgument {
        public ElementNameArgument(int n) {
            super(n);
        }

        @Override
        Types getType() {
            return Types.ELEMENT_NAME;
        }
    }

    private static class ByteArgument
    extends IArgument {
        private final int Cc;

        public ByteArgument(int n) {
            this.Cc = n;
        }

        @Override
        void writeData(SysMsgContainer<?> sysMsgContainer) {
            ((SysMsgContainer)sysMsgContainer).writeC(this.Cc);
        }

        @Override
        Types getType() {
            return Types.BYTE;
        }
    }

    private static class IntegerArgument
    extends IArgument {
        private final int Cd;

        public IntegerArgument(int n) {
            this.Cd = n;
        }

        @Override
        public void writeData(SysMsgContainer<?> sysMsgContainer) {
            ((SysMsgContainer)sysMsgContainer).writeD(this.Cd);
        }

        @Override
        Types getType() {
            return Types.NUMBER;
        }
    }

    private static class LongArgument
    extends IArgument {
        private final long dA;

        public LongArgument(long l) {
            this.dA = l;
        }

        @Override
        void writeData(SysMsgContainer<?> sysMsgContainer) {
            ((SysMsgContainer)sysMsgContainer).writeQ(this.dA);
        }

        @Override
        Types getType() {
            return Types.LONG;
        }
    }

    private static class ShortArgument
    extends IArgument {
        private final short a;

        public ShortArgument(short s) {
            this.a = s;
        }

        @Override
        void writeData(SysMsgContainer<?> sysMsgContainer) {
            ((SysMsgContainer)sysMsgContainer).writeH(this.a);
        }

        @Override
        Types getType() {
            return Types.NUMBER;
        }
    }

    public static final class Types
    extends Enum<Types> {
        public static final /* enum */ Types TEXT = new Types();
        public static final /* enum */ Types NUMBER = new Types();
        public static final /* enum */ Types NPC_NAME = new Types();
        public static final /* enum */ Types ITEM_NAME = new Types();
        public static final /* enum */ Types SKILL_NAME = new Types();
        public static final /* enum */ Types RESIDENCE_NAME = new Types();
        public static final /* enum */ Types LONG = new Types();
        public static final /* enum */ Types ZONE_NAME = new Types();
        public static final /* enum */ Types ITEM_NAME_WITH_AUGMENTATION = new Types();
        public static final /* enum */ Types ELEMENT_NAME = new Types();
        public static final /* enum */ Types INSTANCE_NAME = new Types();
        public static final /* enum */ Types STATIC_OBJECT_NAME = new Types();
        public static final /* enum */ Types PLAYER_NAME = new Types();
        public static final /* enum */ Types SYSTEM_STRING = new Types();
        public static final /* enum */ Types NPC_STRING = new Types();
        public static final /* enum */ Types CLASS_ID = new Types();
        public static final /* enum */ Types TYPE_VISIBLE_DAMAGE = new Types();
        public static final /* enum */ Types U_17 = new Types();
        public static final /* enum */ Types U_18 = new Types();
        public static final /* enum */ Types U_19 = new Types();
        public static final /* enum */ Types BYTE = new Types();
        private static final /* synthetic */ Types[] a;

        public static Types[] values() {
            return (Types[])a.clone();
        }

        public static Types valueOf(String string) {
            return Enum.valueOf(Types.class, string);
        }

        private static /* synthetic */ Types[] a() {
            return new Types[]{TEXT, NUMBER, NPC_NAME, ITEM_NAME, SKILL_NAME, RESIDENCE_NAME, LONG, ZONE_NAME, ITEM_NAME_WITH_AUGMENTATION, ELEMENT_NAME, INSTANCE_NAME, STATIC_OBJECT_NAME, PLAYER_NAME, SYSTEM_STRING, NPC_STRING, CLASS_ID, TYPE_VISIBLE_DAMAGE, U_17, U_18, U_19, BYTE};
        }

        static {
            a = Types.a();
        }
    }
}
