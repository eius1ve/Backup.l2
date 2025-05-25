/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  gnu.trove.TIntObjectHashMap
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.network.l2.c2s;

import gnu.trove.TIntObjectHashMap;
import java.lang.reflect.Field;
import java.util.ArrayList;
import l2.commons.threading.RunnableImpl;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.ai.CtrlIntention;
import l2.gameserver.data.xml.holder.PetDataHolder;
import l2.gameserver.model.Creature;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.PetData;
import l2.gameserver.model.Player;
import l2.gameserver.model.Skill;
import l2.gameserver.model.Summon;
import l2.gameserver.model.instances.DoorInstance;
import l2.gameserver.model.instances.StaticObjectInstance;
import l2.gameserver.model.instances.residences.SiegeFlagInstance;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.AttackRequest;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.ActionFail;
import l2.gameserver.network.l2.s2c.ExInzoneWaitingInfo;
import l2.gameserver.network.l2.s2c.PrivateStoreManageListBuy;
import l2.gameserver.network.l2.s2c.PrivateStoreManageListSell;
import l2.gameserver.network.l2.s2c.RecipeShopManageList;
import l2.gameserver.network.l2.s2c.SocialAction;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.templates.DoorTemplate;
import l2.gameserver.utils.TradeHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestActionUse
extends L2GameClientPacket {
    private static final Logger cL = LoggerFactory.getLogger(RequestActionUse.class);
    private int pK;
    private boolean dP;
    private boolean dQ;

    @Override
    protected void readImpl() {
        this.pK = this.readD();
        this.dP = this.readD() == 1;
        this.dQ = this.readC() == 1;
    }

    @Override
    protected void runImpl() {
        boolean bl;
        GameClient gameClient = (GameClient)this.getClient();
        Player player = gameClient.getActiveChar();
        if (player == null) {
            return;
        }
        Action action = Action.find(this.pK);
        if (action == null) {
            PetData petData;
            Summon summon = player.getPet();
            if (summon != null && (petData = PetDataHolder.getInstance().getInfo(summon.getNpcId(), summon.getLevel())) != null && petData.getActionId2SkillId().containsKey(this.pK)) {
                int n = petData.getActionId2SkillId().get(this.pK);
                action = new Action(this.pK, 2, n, 0);
            }
            if (action == null) {
                cL.warn("unhandled action type " + this.pK + " by player " + player.getName());
                player.sendActionFailed();
                return;
            }
        }
        boolean bl2 = action.type == 1 || action.type == 2;
        boolean bl3 = bl = this.pK >= 78 && this.pK <= 85;
        if (!(bl || bl2 || !player.isOutOfControl() && !player.isActionsDisabled() || player.isFakeDeath() && this.pK == 0)) {
            player.sendActionFailed();
            return;
        }
        if ((player.getTransformation() != 0 || player.isCursedWeaponEquipped()) && action.transform > 0) {
            player.sendActionFailed();
            return;
        }
        if (action.type == 3) {
            if (player.isOutOfControl() || player.getTransformation() != 0 || player.isActionsDisabled() || player.isSitting() || player.getPrivateStoreType() != 0 || player.isProcessingRequest()) {
                player.sendActionFailed();
                return;
            }
            if (player.isFishing()) {
                player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_DO_THAT_WHILE_FISHING_2);
                return;
            }
            player.broadcastPacket(new SocialAction(player.getObjectId(), action.value));
            if (Config.ALT_SOCIAL_ACTION_REUSE) {
                ThreadPoolManager.getInstance().schedule(new SocialTask(player), 2600L);
                player.startParalyzed();
            }
            return;
        }
        GameObject gameObject = player.getTarget();
        Summon summon = player.getPet();
        if (bl2) {
            if (summon == null || summon.isOutOfControl()) {
                player.sendActionFailed();
                return;
            }
            if (summon.isDepressed() || Config.ALT_SERVITOR_ACTION_MAX_DISTANCE > 0 && summon.getDistance(player) > (double)Config.ALT_SERVITOR_ACTION_MAX_DISTANCE && this.pK != 52) {
                player.sendPacket((IStaticPacket)SystemMsg.YOUR_PETSERVITOR_IS_UNRESPONSIVE_AND_WILL_NOT_OBEY_ANY_ORDERS);
                return;
            }
        }
        if (action.type == 2) {
            if (action.id == 1000 && gameObject != null && !gameObject.isDoor()) {
                player.sendPacket(SystemMsg.THAT_IS_AN_INCORRECT_TARGET, ActionFail.STATIC);
                return;
            }
            if ((action.id == 1039 || action.id == 1040) && (gameObject != null && gameObject.isDoor() && ((DoorInstance)gameObject).getDoorType() != DoorTemplate.DoorType.WALL || gameObject instanceof SiegeFlagInstance)) {
                player.sendPacket(SystemMsg.THAT_IS_AN_INCORRECT_TARGET, ActionFail.STATIC);
                return;
            }
            this.n(player, action.value);
            return;
        }
        switch (action.id) {
            case 0: {
                if (player.isMounted()) {
                    player.sendActionFailed();
                    break;
                }
                if (player.isFakeDeath()) {
                    player.breakFakeDeath();
                    player.updateEffectIcons();
                    break;
                }
                if (!player.isSitting()) {
                    if (gameObject != null && gameObject instanceof StaticObjectInstance && ((StaticObjectInstance)gameObject).getType() == 1 && player.getDistance3D(gameObject) <= (double)gameObject.getActingRange()) {
                        player.sitDown((StaticObjectInstance)gameObject);
                        break;
                    }
                    player.sitDown(null);
                    break;
                }
                player.standUp();
                break;
            }
            case 1: {
                if (player.isRunning()) {
                    player.setWalking();
                } else {
                    player.setRunning();
                }
                player.sendUserInfo(false);
                break;
            }
            case 10: 
            case 61: {
                if (player.getSittingTask()) {
                    player.sendActionFailed();
                    return;
                }
                if (player.isInStoreMode()) {
                    player.setPrivateStoreType(0);
                } else if (!TradeHelper.checksIfCanOpenStore(player, this.pK == 61 ? 8 : 1)) {
                    player.sendActionFailed();
                    return;
                }
                player.sendPacket(new PrivateStoreManageListSell(true, player, this.pK == 61), new PrivateStoreManageListSell(false, player, this.pK == 61));
                break;
            }
            case 28: {
                if (player.getSittingTask()) {
                    player.sendActionFailed();
                    return;
                }
                if (player.isInStoreMode()) {
                    player.setPrivateStoreType(0);
                } else if (!TradeHelper.checksIfCanOpenStore(player, 3)) {
                    player.sendActionFailed();
                    return;
                }
                player.sendPacket(new PrivateStoreManageListBuy(true, player), new PrivateStoreManageListBuy(false, player));
                break;
            }
            case 37: {
                if (player.getSittingTask()) {
                    player.sendActionFailed();
                    return;
                }
                if (player.getKnownSkill(1321) == null) {
                    player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1_HAS_FAILED).addSkillName(1321, 1));
                    player.sendActionFailed();
                    return;
                }
                if (player.isInStoreMode()) {
                    player.setPrivateStoreType(0);
                } else if (!TradeHelper.checksIfCanOpenStore(player, 5)) {
                    player.sendActionFailed();
                    return;
                }
                player.sendPacket((IStaticPacket)new RecipeShopManageList(player, true));
                break;
            }
            case 51: {
                if (player.getSittingTask()) {
                    player.sendActionFailed();
                    return;
                }
                if (player.isSellingBuffs()) {
                    player.sendActionFailed();
                    return;
                }
                if (player.isInStoreMode()) {
                    player.setPrivateStoreType(0);
                } else if (!TradeHelper.checksIfCanOpenStore(player, 5)) {
                    player.sendActionFailed();
                    return;
                }
                player.sendPacket((IStaticPacket)new RecipeShopManageList(player, false));
                break;
            }
            case 96: {
                cL.info("96 Accessed");
                break;
            }
            case 97: {
                cL.info("97 Accessed");
                break;
            }
            case 15: 
            case 21: {
                if (summon == null) break;
                summon.setFollowMode(!summon.isFollowMode());
                break;
            }
            case 16: 
            case 22: {
                if (gameObject == null || !gameObject.isCreature() || summon == gameObject || summon.isDead()) {
                    player.sendActionFailed();
                    return;
                }
                if (summon.getTemplate().getNpcId() == 12564) {
                    return;
                }
                if (!this.dP && gameObject.isCreature() && !((Creature)gameObject).isAutoAttackable(summon)) {
                    if (gameObject != player && (summon.getAI().getIntention() != CtrlIntention.AI_INTENTION_FOLLOW || summon.getFollowTarget() != gameObject)) {
                        summon.getAI().setIntention(CtrlIntention.AI_INTENTION_FOLLOW, gameObject);
                    }
                    return;
                }
                if (!gameObject.isAttackable(summon)) {
                    player.sendPacket((IStaticPacket)SystemMsg.INVALID_TARGET);
                    return;
                }
                if (!gameObject.isNpc() && (summon.isInZonePeace() || gameObject.isCreature() && ((Creature)gameObject).isInZonePeace())) {
                    player.sendPacket((IStaticPacket)SystemMsg.YOU_MAY_NOT_ATTACK_THIS_TARGET_IN_A_PEACEFUL_ZONE);
                    return;
                }
                if (player.getLevel() + 20 <= summon.getLevel()) {
                    player.sendPacket((IStaticPacket)SystemMsg.YOUR_PET_IS_TOO_HIGH_LEVEL_TO_CONTROL);
                    return;
                }
                long l = System.currentTimeMillis();
                if (l - gameClient.getLastIncomePacketTimeStamp(AttackRequest.class) < (long)Config.ATTACK_PACKET_DELAY) {
                    player.sendActionFailed();
                    return;
                }
                gameClient.setLastIncomePacketTimeStamp(AttackRequest.class, l);
                summon.getAI().Attack(gameObject, this.dP, this.dQ);
                break;
            }
            case 17: 
            case 23: {
                summon.getAI().setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
                break;
            }
            case 19: {
                if (summon.isDead()) {
                    player.sendPacket(SystemMsg.DEAD_PETS_CANNOT_BE_RETURNED_TO_THEIR_SUMMONING_ITEM, ActionFail.STATIC);
                    return;
                }
                if (summon.isInCombat()) {
                    player.sendPacket(SystemMsg.A_PET_CANNOT_BE_UNSUMMONED_DURING_BATTLE, ActionFail.STATIC);
                    break;
                }
                if (summon.isPet() && (double)summon.getCurrentFed() < 0.55 * (double)summon.getMaxFed()) {
                    player.sendPacket(SystemMsg.YOU_MAY_NOT_RESTORE_A_HUNGRY_PET, ActionFail.STATIC);
                    break;
                }
                summon.unSummon();
                break;
            }
            case 38: {
                if (player.getTransformation() != 0 || player.isCursedWeaponEquipped()) {
                    player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_BOARD_BECAUSE_YOU_DO_NOT_MEET_THE_REQUIREMENTS);
                    break;
                }
                if (player.isActionBlocked("ride")) {
                    player.sendPacket(SystemMsg.YOU_ARE_NOT_ALLOWED_TO_DISMOUNT_IN_THIS_LOCATION, ActionFail.STATIC);
                    break;
                }
                if (summon == null || !summon.isMountable()) {
                    if (!player.isMounted()) break;
                    if (player.isFlying() && !player.checkLandingState()) {
                        player.sendPacket(SystemMsg.YOU_ARE_NOT_ALLOWED_TO_DISMOUNT_IN_THIS_LOCATION, ActionFail.STATIC);
                        return;
                    }
                    player.setMount(0, 0, 0);
                    break;
                }
                if (player.isMounted() || player.isInBoat()) {
                    player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_BOARD_BECAUSE_YOU_DO_NOT_MEET_THE_REQUIREMENTS);
                    break;
                }
                if (player.isDead()) {
                    player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_BOARD_BECAUSE_YOU_DO_NOT_MEET_THE_REQUIREMENTS);
                    break;
                }
                if (summon.isDead()) {
                    player.sendPacket((IStaticPacket)SystemMsg.A_DEAD_STRIDER_CANNOT_BE_RIDDEN);
                    break;
                }
                if (player.isInDuel()) {
                    player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_BOARD_BECAUSE_YOU_DO_NOT_MEET_THE_REQUIREMENTS);
                    break;
                }
                if (player.isInCombat() || summon.isInCombat()) {
                    player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_BOARD_BECAUSE_YOU_DO_NOT_MEET_THE_REQUIREMENTS);
                    break;
                }
                if (player.isFishing()) {
                    player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_BOARD_BECAUSE_YOU_DO_NOT_MEET_THE_REQUIREMENTS);
                    break;
                }
                if (player.isSitting()) {
                    player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_BOARD_BECAUSE_YOU_DO_NOT_MEET_THE_REQUIREMENTS);
                    break;
                }
                if (player.isCursedWeaponEquipped()) {
                    player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_BOARD_BECAUSE_YOU_DO_NOT_MEET_THE_REQUIREMENTS);
                    break;
                }
                if (player.getActiveWeaponFlagAttachment() != null) {
                    player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_BOARD_BECAUSE_YOU_DO_NOT_MEET_THE_REQUIREMENTS);
                    break;
                }
                if (player.isCastingNow()) {
                    player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_BOARD_BECAUSE_YOU_DO_NOT_MEET_THE_REQUIREMENTS);
                    break;
                }
                if (player.isParalyzed()) {
                    player.sendPacket((IStaticPacket)SystemMsg.YOU_CANNOT_BOARD_BECAUSE_YOU_DO_NOT_MEET_THE_REQUIREMENTS);
                    break;
                }
                player.setMount(summon.getTemplate().npcId, summon.getObjectId(), summon.getLevel());
                summon.unSummon();
                break;
            }
            case 52: {
                if (summon.isInCombat()) {
                    player.sendPacket((IStaticPacket)SystemMsg.A_PET_CANNOT_BE_UNSUMMONED_DURING_BATTLE);
                    player.sendActionFailed();
                    break;
                }
                if (Config.ALT_SAVE_SERVITOR_BUFF) {
                    summon.saveEffects();
                }
                summon.unSummon();
                break;
            }
            case 53: 
            case 54: {
                if (gameObject == null || summon == gameObject || summon.isMovementDisabled()) break;
                summon.setFollowMode(false);
                summon.moveToLocation(gameObject.getLoc(), 100, true);
                break;
            }
            case 78: {
                if (!player.isInParty() || gameObject == null || !gameObject.isCreature()) break;
                player.getParty().addTacticalSign(player, 1, (Creature)gameObject);
                break;
            }
            case 79: {
                if (!player.isInParty() || gameObject == null || !gameObject.isCreature()) break;
                player.getParty().addTacticalSign(player, 2, (Creature)gameObject);
                break;
            }
            case 80: {
                if (!player.isInParty() || gameObject == null || !gameObject.isCreature()) break;
                player.getParty().addTacticalSign(player, 3, (Creature)gameObject);
                break;
            }
            case 81: {
                if (!player.isInParty() || gameObject == null || !gameObject.isCreature()) break;
                player.getParty().addTacticalSign(player, 4, (Creature)gameObject);
                break;
            }
            case 82: {
                if (!player.isInParty()) break;
                player.getParty().setTargetBasedOnTacticalSignId(player, 1);
                break;
            }
            case 83: {
                if (!player.isInParty()) break;
                player.getParty().setTargetBasedOnTacticalSignId(player, 2);
                break;
            }
            case 84: {
                if (!player.isInParty()) break;
                player.getParty().setTargetBasedOnTacticalSignId(player, 3);
                break;
            }
            case 85: {
                if (!player.isInParty()) break;
                player.getParty().setTargetBasedOnTacticalSignId(player, 4);
                break;
            }
            case 90: {
                player.sendPacket((IStaticPacket)new ExInzoneWaitingInfo(player, 1));
                break;
            }
            case 1001: {
                break;
            }
            default: {
                cL.warn("unhandled action type " + this.pK + " by player " + player.getName());
            }
        }
        player.sendActionFailed();
    }

    private void n(Player player, int n) {
        Summon summon = player.getPet();
        if (summon == null) {
            player.sendActionFailed();
            return;
        }
        int n2 = PetDataHolder.getInstance().getAvailableSkillLevel(summon, n);
        if (n2 == 0) {
            player.sendActionFailed();
            return;
        }
        Skill skill = SkillTable.getInstance().getInfo(n, n2);
        if (skill == null) {
            player.sendActionFailed();
            return;
        }
        if (player.getLevel() + 20 <= summon.getLevel()) {
            player.sendPacket((IStaticPacket)SystemMsg.YOUR_PET_IS_TOO_HIGH_LEVEL_TO_CONTROL);
            return;
        }
        Creature creature = skill.getAimingTarget(summon, player.getTarget());
        if (skill.checkCondition(summon, creature, this.dP, this.dQ, true)) {
            summon.getAI().Cast(skill, creature, this.dP, this.dQ);
        } else {
            player.sendActionFailed();
        }
    }

    public static class Action {
        public static Action ACTION0 = new Action(0, 0, 0, 1);
        public static Action ACTION1 = new Action(1, 0, 0, 0);
        public static Action ACTION7 = new Action(7, 0, 0, 1);
        public static Action ACTION10 = new Action(10, 0, 0, 1);
        public static Action ACTION28 = new Action(28, 0, 0, 1);
        public static Action ACTION37 = new Action(37, 0, 0, 1);
        public static Action ACTION38 = new Action(38, 0, 0, 1);
        public static Action ACTION51 = new Action(51, 0, 0, 1);
        public static Action ACTION61 = new Action(61, 0, 0, 1);
        public static Action ACTION96 = new Action(96, 0, 0, 1);
        public static Action ACTION97 = new Action(97, 0, 0, 1);
        public static Action ACTION67 = new Action(67, 0, 0, 1);
        public static Action ACTION68 = new Action(68, 0, 0, 1);
        public static Action ACTION69 = new Action(69, 0, 0, 1);
        public static Action ACTION70 = new Action(70, 0, 0, 1);
        public static Action ACTION90 = new Action(90, 0, 0, 1);
        public static Action ACTION78 = new Action(78, 0, 1, 0);
        public static Action ACTION79 = new Action(79, 0, 1, 0);
        public static Action ACTION80 = new Action(80, 0, 1, 0);
        public static Action ACTION81 = new Action(81, 0, 1, 0);
        public static Action ACTION82 = new Action(82, 0, 1, 0);
        public static Action ACTION83 = new Action(83, 0, 1, 0);
        public static Action ACTION84 = new Action(84, 0, 1, 0);
        public static Action ACTION85 = new Action(85, 0, 1, 0);
        public static Action ACTION15 = new Action(15, 1, 0, 0);
        public static Action ACTION16 = new Action(16, 1, 0, 0);
        public static Action ACTION17 = new Action(17, 1, 0, 0);
        public static Action ACTION19 = new Action(19, 1, 0, 0);
        public static Action ACTION21 = new Action(21, 1, 0, 0);
        public static Action ACTION22 = new Action(22, 1, 0, 0);
        public static Action ACTION23 = new Action(23, 1, 0, 0);
        public static Action ACTION52 = new Action(52, 1, 0, 0);
        public static Action ACTION53 = new Action(53, 1, 0, 0);
        public static Action ACTION54 = new Action(54, 1, 0, 0);
        public static Action ACTION12 = new Action(12, 3, 2, 2);
        public static Action ACTION13 = new Action(13, 3, 3, 2);
        public static Action ACTION14 = new Action(14, 3, 4, 2);
        public static Action ACTION24 = new Action(24, 3, 6, 2);
        public static Action ACTION25 = new Action(25, 3, 5, 2);
        public static Action ACTION26 = new Action(26, 3, 7, 2);
        public static Action ACTION29 = new Action(29, 3, 8, 2);
        public static Action ACTION30 = new Action(30, 3, 9, 2);
        public static Action ACTION31 = new Action(31, 3, 10, 2);
        public static Action ACTION33 = new Action(33, 3, 11, 2);
        public static Action ACTION34 = new Action(34, 3, 12, 2);
        public static Action ACTION35 = new Action(35, 3, 13, 2);
        public static Action ACTION62 = new Action(62, 3, 14, 2);
        public static Action ACTION66 = new Action(66, 3, 15, 2);
        public static Action ACTION87 = new Action(87, 3, 28, 2);
        public static Action ACTION88 = new Action(88, 3, 29, 2);
        public static Action ACTION89 = new Action(89, 3, 30, 2);
        public static Action ACTION71 = new Action(71, 4, 16, 2);
        public static Action ACTION72 = new Action(72, 4, 17, 2);
        public static Action ACTION73 = new Action(73, 4, 18, 2);
        public int id;
        public int type;
        public int value;
        public int transform;
        private static Action[] a;
        private static TIntObjectHashMap<Action> y;

        private Action(int n, int n2, int n3, int n4) {
            this.id = n;
            this.type = n2;
            this.value = n3;
            this.transform = n4;
        }

        private static Action[] a() {
            if (a != null) {
                return a;
            }
            ArrayList<Action> arrayList = new ArrayList<Action>();
            for (Field field : Action.class.getDeclaredFields()) {
                if (!Action.class.isAssignableFrom(field.getType())) continue;
                try {
                    arrayList.add((Action)field.get(null));
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            a = arrayList.toArray(new Action[arrayList.size()]);
            return a;
        }

        public static Action find(int n) {
            if (y == null) {
                TIntObjectHashMap tIntObjectHashMap = new TIntObjectHashMap();
                for (Action action : Action.a()) {
                    tIntObjectHashMap.put(action.id, (Object)action);
                }
                y = tIntObjectHashMap;
            }
            return (Action)y.get(n);
        }
    }

    static class SocialTask
    extends RunnableImpl {
        Player _player;

        SocialTask(Player player) {
            this._player = player;
        }

        @Override
        public void runImpl() throws Exception {
            this._player.stopParalyzed();
        }
    }
}
