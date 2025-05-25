/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.ArrayUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.lang3.math.NumberUtils
 *  org.apache.commons.lang3.tuple.ImmutablePair
 *  org.apache.commons.lang3.tuple.Pair
 *  org.napile.primitive.Containers
 *  org.napile.primitive.iterators.IntIterator
 *  org.napile.primitive.maps.IntObjectMap
 *  org.napile.primitive.maps.IntObjectMap$Entry
 *  org.napile.primitive.maps.impl.CHashIntObjectMap
 *  org.napile.primitive.sets.IntSet
 *  org.napile.primitive.sets.impl.HashIntSet
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.model;

import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import l2.commons.collections.LazyArrayList;
import l2.commons.collections.MultiValueSet;
import l2.commons.dbutils.DbUtils;
import l2.commons.lang.reference.HardReference;
import l2.commons.lang.reference.HardReferences;
import l2.commons.threading.RunnableImpl;
import l2.commons.util.Rnd;
import l2.gameserver.Announcements;
import l2.gameserver.Config;
import l2.gameserver.GameTimeController;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.ai.CtrlEvent;
import l2.gameserver.ai.CtrlIntention;
import l2.gameserver.ai.NextAction;
import l2.gameserver.ai.PlayerAI;
import l2.gameserver.dao.AccountBonusDAO;
import l2.gameserver.dao.CharacterDAO;
import l2.gameserver.dao.CharacterGroupReuseDAO;
import l2.gameserver.dao.CharacterPostFriendDAO;
import l2.gameserver.dao.CharacterTPBookmarkDAO;
import l2.gameserver.dao.CharacterVariablesDAO;
import l2.gameserver.dao.EffectsDAO;
import l2.gameserver.dao.InstanceReuseDAO;
import l2.gameserver.data.xml.holder.ArmorSetsHolder;
import l2.gameserver.data.xml.holder.CharacterTemplateHolder;
import l2.gameserver.data.xml.holder.CrystalGradeDataHolder;
import l2.gameserver.data.xml.holder.EventHolder;
import l2.gameserver.data.xml.holder.HennaHolder;
import l2.gameserver.data.xml.holder.InstantZoneHolder;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.data.xml.holder.MultiSellHolder;
import l2.gameserver.data.xml.holder.NpcHolder;
import l2.gameserver.data.xml.holder.OneDayRewardHolder;
import l2.gameserver.data.xml.holder.PetDataHolder;
import l2.gameserver.data.xml.holder.RecipeHolder;
import l2.gameserver.data.xml.holder.ResidenceHolder;
import l2.gameserver.data.xml.holder.SkillAcquireHolder;
import l2.gameserver.database.DatabaseFactory;
import l2.gameserver.database.mysql;
import l2.gameserver.handler.items.IItemHandler;
import l2.gameserver.handler.items.IRefineryHandler;
import l2.gameserver.idfactory.IdFactory;
import l2.gameserver.instancemanager.CursedWeaponsManager;
import l2.gameserver.instancemanager.DimensionalRiftManager;
import l2.gameserver.instancemanager.MatchingRoomManager;
import l2.gameserver.instancemanager.QuestManager;
import l2.gameserver.instancemanager.ReflectionManager;
import l2.gameserver.instancemanager.SellBuffsManager;
import l2.gameserver.instancemanager.VipManager;
import l2.gameserver.listener.actor.player.OnAnswerListener;
import l2.gameserver.listener.actor.player.impl.ReviveAnswerListener;
import l2.gameserver.listener.actor.player.impl.ScriptAnswerListener;
import l2.gameserver.listener.actor.player.impl.SummonAnswerListener;
import l2.gameserver.model.ArmorSet;
import l2.gameserver.model.Creature;
import l2.gameserver.model.DeathPenalty;
import l2.gameserver.model.Effect;
import l2.gameserver.model.Fishing;
import l2.gameserver.model.GameObject;
import l2.gameserver.model.GameObjectTasks;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Party;
import l2.gameserver.model.PetData;
import l2.gameserver.model.Playable;
import l2.gameserver.model.PlayerGroup;
import l2.gameserver.model.PremiumItem;
import l2.gameserver.model.Recipe;
import l2.gameserver.model.Request;
import l2.gameserver.model.Skill;
import l2.gameserver.model.SkillLearn;
import l2.gameserver.model.SubClass;
import l2.gameserver.model.Summon;
import l2.gameserver.model.World;
import l2.gameserver.model.WorldRegion;
import l2.gameserver.model.Zone;
import l2.gameserver.model.actor.instances.player.AutoFarmContext;
import l2.gameserver.model.actor.instances.player.Bonus;
import l2.gameserver.model.actor.instances.player.CharacterBlockList;
import l2.gameserver.model.actor.instances.player.FriendList;
import l2.gameserver.model.actor.instances.player.Macro;
import l2.gameserver.model.actor.instances.player.MacroList;
import l2.gameserver.model.actor.instances.player.ShortCut;
import l2.gameserver.model.actor.instances.player.ShortCutList;
import l2.gameserver.model.actor.instances.player.TpBookMark;
import l2.gameserver.model.actor.instances.player.tasks.EnableUserRelationTask;
import l2.gameserver.model.actor.listener.PlayerListenerList;
import l2.gameserver.model.actor.recorder.PlayerStatsChangeRecorder;
import l2.gameserver.model.base.AcquireType;
import l2.gameserver.model.base.ClassId;
import l2.gameserver.model.base.Element;
import l2.gameserver.model.base.Experience;
import l2.gameserver.model.base.InvisibleType;
import l2.gameserver.model.base.PlayerAccess;
import l2.gameserver.model.base.Race;
import l2.gameserver.model.base.RestartType;
import l2.gameserver.model.base.TeamType;
import l2.gameserver.model.chat.chatfilter.ChatMsg;
import l2.gameserver.model.entity.DimensionalRift;
import l2.gameserver.model.entity.Reflection;
import l2.gameserver.model.entity.SevenSignsFestival.DarknessFestival;
import l2.gameserver.model.entity.boat.Boat;
import l2.gameserver.model.entity.events.GlobalEvent;
import l2.gameserver.model.entity.events.impl.DuelEvent;
import l2.gameserver.model.entity.events.impl.SiegeEvent;
import l2.gameserver.model.entity.oly.CompetitionState;
import l2.gameserver.model.entity.oly.HeroController;
import l2.gameserver.model.entity.oly.NoblesController;
import l2.gameserver.model.entity.oly.OlyController;
import l2.gameserver.model.entity.oly.Participant;
import l2.gameserver.model.entity.oly.ParticipantPool;
import l2.gameserver.model.entity.oly.Stadium;
import l2.gameserver.model.entity.oneDayReward.OneDayRewardStore;
import l2.gameserver.model.entity.oneDayReward.requirement.ObtainLevelRequirement;
import l2.gameserver.model.entity.residence.Castle;
import l2.gameserver.model.entity.residence.ClanHall;
import l2.gameserver.model.entity.residence.Residence;
import l2.gameserver.model.instances.FestivalMonsterInstance;
import l2.gameserver.model.instances.GuardInstance;
import l2.gameserver.model.instances.MonsterInstance;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.model.instances.PetBabyInstance;
import l2.gameserver.model.instances.PetInstance;
import l2.gameserver.model.instances.ReflectionBossInstance;
import l2.gameserver.model.instances.StaticObjectInstance;
import l2.gameserver.model.instances.TamedBeastInstance;
import l2.gameserver.model.instances.TrapInstance;
import l2.gameserver.model.items.ItemContainer;
import l2.gameserver.model.items.ItemInfo;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.items.LockType;
import l2.gameserver.model.items.ManufactureItem;
import l2.gameserver.model.items.PcFreight;
import l2.gameserver.model.items.PcInventory;
import l2.gameserver.model.items.PcRefund;
import l2.gameserver.model.items.PcWarehouse;
import l2.gameserver.model.items.TradeItem;
import l2.gameserver.model.items.Warehouse;
import l2.gameserver.model.items.attachment.FlagItemAttachment;
import l2.gameserver.model.items.attachment.PickableAttachment;
import l2.gameserver.model.matching.MatchingRoom;
import l2.gameserver.model.pledge.Alliance;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.model.pledge.Privilege;
import l2.gameserver.model.pledge.RankPrivs;
import l2.gameserver.model.pledge.SubUnit;
import l2.gameserver.model.pledge.UnitMember;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.model.quest.QuestEventType;
import l2.gameserver.model.quest.QuestState;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.components.ChatType;
import l2.gameserver.network.l2.components.CustomMessage;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.components.SceneMovie;
import l2.gameserver.network.l2.components.SystemMsg;
import l2.gameserver.network.l2.s2c.AbnormalStatusUpdate;
import l2.gameserver.network.l2.s2c.AcquireSkillList;
import l2.gameserver.network.l2.s2c.ActionFail;
import l2.gameserver.network.l2.s2c.AutoAttackStart;
import l2.gameserver.network.l2.s2c.CameraMode;
import l2.gameserver.network.l2.s2c.ChairSit;
import l2.gameserver.network.l2.s2c.ChangeWaitType;
import l2.gameserver.network.l2.s2c.CharInfo;
import l2.gameserver.network.l2.s2c.ConfirmDlg;
import l2.gameserver.network.l2.s2c.EtcStatusUpdate;
import l2.gameserver.network.l2.s2c.ExAbnormalStatusUpdateFromTarget;
import l2.gameserver.network.l2.s2c.ExAutoSoulShot;
import l2.gameserver.network.l2.s2c.ExBasicActionList;
import l2.gameserver.network.l2.s2c.ExDuelUpdateUserInfo;
import l2.gameserver.network.l2.s2c.ExMagicAttackInfo;
import l2.gameserver.network.l2.s2c.ExNewSkillToLearnByLevelUp;
import l2.gameserver.network.l2.s2c.ExOlympiadMode;
import l2.gameserver.network.l2.s2c.ExOlympiadUserInfo;
import l2.gameserver.network.l2.s2c.ExPCCafePointInfo;
import l2.gameserver.network.l2.s2c.ExPlayAnimation;
import l2.gameserver.network.l2.s2c.ExPledgeCount;
import l2.gameserver.network.l2.s2c.ExPledgeWaitingListAlarm;
import l2.gameserver.network.l2.s2c.ExPrivateStoreSetWholeMsg;
import l2.gameserver.network.l2.s2c.ExQuestItemList;
import l2.gameserver.network.l2.s2c.ExSetCompassZoneCode;
import l2.gameserver.network.l2.s2c.ExStartScenePlayer;
import l2.gameserver.network.l2.s2c.ExStorageMaxCount;
import l2.gameserver.network.l2.s2c.ExTeleportToLocationActivate;
import l2.gameserver.network.l2.s2c.ExUserInfoAbnormalVisualEffect;
import l2.gameserver.network.l2.s2c.ExUserInfoCubic;
import l2.gameserver.network.l2.s2c.ExVitalityPointInfo;
import l2.gameserver.network.l2.s2c.ExVoteSystemInfo;
import l2.gameserver.network.l2.s2c.ExWorldChatCnt;
import l2.gameserver.network.l2.s2c.GetItem;
import l2.gameserver.network.l2.s2c.HennaInfo;
import l2.gameserver.network.l2.s2c.InventoryUpdate;
import l2.gameserver.network.l2.s2c.ItemList;
import l2.gameserver.network.l2.s2c.L2GameServerPacket;
import l2.gameserver.network.l2.s2c.LeaveWorld;
import l2.gameserver.network.l2.s2c.MagicSkillUse;
import l2.gameserver.network.l2.s2c.MyTargetSelected;
import l2.gameserver.network.l2.s2c.NpcInfo;
import l2.gameserver.network.l2.s2c.ObserverEnd;
import l2.gameserver.network.l2.s2c.ObserverStart;
import l2.gameserver.network.l2.s2c.PartySmallWindowUpdate;
import l2.gameserver.network.l2.s2c.PartySpelled;
import l2.gameserver.network.l2.s2c.PlaySound;
import l2.gameserver.network.l2.s2c.PledgeShowMemberListDelete;
import l2.gameserver.network.l2.s2c.PledgeShowMemberListDeleteAll;
import l2.gameserver.network.l2.s2c.PledgeShowMemberListUpdate;
import l2.gameserver.network.l2.s2c.PrivateStoreListBuy;
import l2.gameserver.network.l2.s2c.PrivateStoreListSell;
import l2.gameserver.network.l2.s2c.PrivateStoreMsgBuy;
import l2.gameserver.network.l2.s2c.PrivateStoreMsgSell;
import l2.gameserver.network.l2.s2c.QuestList;
import l2.gameserver.network.l2.s2c.RadarControl;
import l2.gameserver.network.l2.s2c.ReceiveVipInfo;
import l2.gameserver.network.l2.s2c.RecipeShopMsg;
import l2.gameserver.network.l2.s2c.RecipeShopSellList;
import l2.gameserver.network.l2.s2c.RelationChanged;
import l2.gameserver.network.l2.s2c.Ride;
import l2.gameserver.network.l2.s2c.SendTradeDone;
import l2.gameserver.network.l2.s2c.ServerClose;
import l2.gameserver.network.l2.s2c.SetupGauge;
import l2.gameserver.network.l2.s2c.ShortBuffStatusUpdate;
import l2.gameserver.network.l2.s2c.ShortCutInit;
import l2.gameserver.network.l2.s2c.ShortCutRegister;
import l2.gameserver.network.l2.s2c.SkillCoolTime;
import l2.gameserver.network.l2.s2c.SkillList;
import l2.gameserver.network.l2.s2c.SocialAction;
import l2.gameserver.network.l2.s2c.SpecialCamera;
import l2.gameserver.network.l2.s2c.StatusUpdate;
import l2.gameserver.network.l2.s2c.SystemMessage;
import l2.gameserver.network.l2.s2c.TargetSelected;
import l2.gameserver.network.l2.s2c.TargetUnselected;
import l2.gameserver.network.l2.s2c.TeleportToLocation;
import l2.gameserver.network.l2.s2c.UserInfo;
import l2.gameserver.network.l2.s2c.UserInfoType;
import l2.gameserver.network.l2.s2c.ValidateLocation;
import l2.gameserver.scripts.Events;
import l2.gameserver.skills.AbnormalEffect;
import l2.gameserver.skills.EffectType;
import l2.gameserver.skills.TimeStamp;
import l2.gameserver.skills.effects.EffectCubic;
import l2.gameserver.skills.skillclasses.Transformation;
import l2.gameserver.stats.Formulas;
import l2.gameserver.stats.Stats;
import l2.gameserver.stats.funcs.FuncTemplate;
import l2.gameserver.tables.ClanTable;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.tables.SkillTreeTable;
import l2.gameserver.taskmanager.AutoSaveManager;
import l2.gameserver.taskmanager.LazyPrecisionTaskManager;
import l2.gameserver.templates.FishTemplate;
import l2.gameserver.templates.Henna;
import l2.gameserver.templates.InstantZone;
import l2.gameserver.templates.PlayerTemplate;
import l2.gameserver.templates.item.ArmorTemplate;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.templates.item.WeaponTemplate;
import l2.gameserver.templates.item.support.Grade;
import l2.gameserver.templates.npc.NpcTemplate;
import l2.gameserver.utils.GameStats;
import l2.gameserver.utils.ItemFunctions;
import l2.gameserver.utils.Language;
import l2.gameserver.utils.Location;
import l2.gameserver.utils.Log;
import l2.gameserver.utils.Strings;
import l2.gameserver.utils.TeleportUtils;
import l2.gameserver.utils.Util;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.napile.primitive.Containers;
import org.napile.primitive.iterators.IntIterator;
import org.napile.primitive.maps.IntObjectMap;
import org.napile.primitive.maps.impl.CHashIntObjectMap;
import org.napile.primitive.sets.IntSet;
import org.napile.primitive.sets.impl.HashIntSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Duplicate member names - consider using --renamedupmembers true
 * Illegal identifiers - consider using --renameillegalidents true
 */
public class Player
extends Playable
implements PlayerGroup {
    public static final int DEFAULT_TITLE_COLOR = 0xFFFF77;
    public static final int MAX_POST_FRIEND_SIZE = 100;
    public static final int MAX_FRIEND_SIZE = 128;
    private static final Logger bF = LoggerFactory.getLogger(Player.class);
    public static final String NO_TRADERS_VAR = "notraders";
    public static final String ANTISPAM_VAR = "antispam";
    public static final String CUSTOM_HERO_END_TIME_VAR = "CustomHeroEndTime";
    public static final String ANIMATION_OF_CAST_RANGE_VAR = "buffAnimRange";
    public static final String NO_SHOTS_ANIMATION_VAR = "noShotsAnim";
    public static final String HIDE_HAIR_ACCESSORY = "hideAccessory";
    public static final String LAST_PVP_PK_KILL_VAR_NAME = "LastPVPPKKill";
    public static final String USED_WORLD_CHAT_POINTS = "used_world_chat_points";
    public static final String BONUS_WORLD_CHAT_REUSE = "world_chat_reuse";
    public static final String USED_MAIL_SEND_POINTS = "used_mail_points";
    public static final String USED_MAIL_SEND_REUSE = "used_mail_reuse";
    private static final String cM = "<not connected>";
    public static final String SNOOP_TARGET = "snoop_target";
    public static final String TELEPORT_BOOKMARK = "teleport_bookmark";
    public static final String NO_RESTART_ZONE_LOGOUT_TIMESTAMP = "no_restart_zone_logout_time";
    public static final String VIP_POINTS = "VipPoints";
    public static final String VIP_EXPIRATION = "VipExpiration";
    public static final String VIP_ITEM_BOUGHT = "VipItemBought";
    public Map<Integer, SubClass> _classlist = new HashMap<Integer, SubClass>(4);
    public static final int OBSERVER_NONE = 0;
    public static final int OBSERVER_STARTING = 1;
    public static final int OBSERVER_STARTED = 3;
    public static final int OBSERVER_LEAVING = 2;
    public static final int STORE_PRIVATE_NONE = 0;
    public static final int STORE_PRIVATE_SELL = 1;
    public static final int STORE_PRIVATE_BUY = 3;
    public static final int STORE_PRIVATE_MANUFACTURE = 5;
    public static final int STORE_OBSERVING_GAMES = 7;
    public static final int STORE_PRIVATE_SELL_PACKAGE = 8;
    public static final int STORE_PRIVATE_SELL_BUFF = 9;
    public static final int RANK_VAGABOND = 0;
    public static final int RANK_VASSAL = 1;
    public static final int RANK_HEIR = 2;
    public static final int RANK_KNIGHT = 3;
    public static final int RANK_WISEMAN = 4;
    public static final int RANK_BARON = 5;
    public static final int RANK_VISCOUNT = 6;
    public static final int RANK_COUNT = 7;
    public static final int RANK_MARQUIS = 8;
    public static final int LANG_ENG = 0;
    public static final int LANG_RUS = 1;
    public static final int LANG_UNK = -1;
    public static final int PLAYER_SEX_MALE = 0;
    public static final int PLAYER_SEX_FEMALE = 1;
    public static final int[] EXPERTISE_LEVELS = new int[]{0, 20, 40, 52, 61, 76, Integer.MAX_VALUE};
    private GameClient a;
    private String aJ;
    private int gh;
    private int hP;
    private int hQ;
    private int gd;
    private int ge;
    private int gf;
    private boolean bJ = false;
    private int gc;
    private Stadium a;
    private volatile Participant a;
    private long bk;
    private long bl;
    private long bm;
    private long bn;
    private long bo;
    private long bp;
    private long bq;
    private long br;
    private long bs;
    private long bt;
    private long aV;
    private int hR;
    private int hS;
    private int hT = -1;
    private double v;
    private String cN;
    private int hU;
    private boolean bK;
    volatile boolean sittingTaskLaunched;
    private int hV = 0;
    private int hW;
    private boolean bL;
    private boolean bM;
    private boolean bN;
    private final PcInventory a;
    private final Warehouse a;
    private final ItemContainer a;
    private final PcFreight a;
    private long bu = 0L;
    private final Deque<ChatMsg> a;
    private final Map<Integer, Recipe> aD;
    private final Map<Integer, Recipe> aE;
    private Map<Integer, PremiumItem> aF;
    private final Map<String, QuestState> aG;
    private final ShortCutList a;
    private final MacroList a;
    private AtomicInteger o;
    private String cO;
    private List<ManufactureItem> aU;
    private String cP;
    private List<TradeItem> aV;
    private List<TradeItem> aW;
    private String cQ;
    private List<TradeItem> aX;
    private List<TradeItem> aY;
    private final Henna[] a;
    private int hX;
    private int hY;
    private int hZ;
    private int ia;
    private int ib;
    private int ic;
    private Party _party;
    private Location y;
    private Clan a;
    private int ie = 0;
    private int if = -128;
    private int ig = 0;
    private int ih = 0;
    private int ii = 0;
    private int ij;
    private PlayerAccess a;
    private boolean bO = false;
    private boolean bP = false;
    private boolean bQ = false;
    private boolean bR = false;
    private Summon a;
    private boolean bS;
    private Map<Integer, EffectCubic> aH = null;
    private int ik = 0;
    private Request a;
    private ItemInstance c;
    private WeaponTemplate a;
    private Map<Integer, String> aI;
    public volatile Grade _expertiseGrade = null;
    private volatile int il = 0;
    private volatile int im = 0;
    private ItemInstance d = null;
    private IRefineryHandler a;
    private Warehouse.WarehouseType a;
    private boolean ae = false;
    private AtomicBoolean g;
    private HardReference<NpcInstance> P;
    private MultiSellHolder.MultiSellListContainer a;
    private Set<Integer> m;
    private WorldRegion b;
    private AtomicInteger p;
    public int _telemode = 0;
    public boolean entering = true;
    public Location _stablePoint = null;
    public int[] _loto;
    public int[] _race;
    private final CharacterBlockList a;
    private final FriendList a;
    private boolean bT = false;
    private Boat a;
    private Location z;
    protected int _baseClass = -1;
    protected SubClass _activeClass = null;
    private Bonus a;
    private Future<?> l;
    private boolean bU;
    private StaticObjectInstance a;
    private boolean bV = false;
    private int in = 0;
    private int io = 0;
    private int ip = 0;
    private byte[] l;
    private int iq = 0;
    private final Fishing a;
    private boolean bW;
    private Future<?> m;
    private Future<?> n;
    private Future<?> o;
    private Future<?> p;
    private Future<?> q;
    private Future<?> r;
    private Future<?> s;
    private final Lock t;
    private int ir;
    private boolean bX = false;
    private int gJ;
    private int is;
    private String cL;
    private String cR;
    private boolean bY;
    private boolean bZ = false;
    private int it;
    private int iu;
    private int iv;
    private int iw;
    private long bv = 0L;
    Map<Integer, Skill> _transformationSkills;
    private int ix = 0;
    private int iy = 0;
    private int iz = 1500;
    private Future<?> t;
    private int iA;
    private boolean ca;
    private boolean cb;
    private int iB;
    private volatile InvisibleType a;
    private IntObjectMap<String> j;
    private List<String> aZ;
    private boolean cc = false;
    private boolean cd = false;
    private long bw;
    private long bx;
    private IntObjectMap<TimeStamp> k;
    private Pair<Integer, OnAnswerListener> a;
    private MatchingRoom a;
    private final Map<Integer, Long> aJ;
    private final AutoFarmContext a;
    private OneDayRewardStore a;
    private List<TpBookMark> ba;
    private boolean ce = false;
    private Map<Skill, Long> aK = null;
    private byte b = 0;
    private int iC = 0;
    private int iD = 0;
    private IntSet a;
    private Future<?> u;
    private final AtomicReference<MoveToLocationOffloadData> a;
    private ScheduledFuture<?> R;
    private int iE;
    private Future<?> v = Config.ALT_VITALITY_LEVELS[4];
    private int iF;
    private int iG;
    private int iH;
    private final MultiValueSet<String> a;
    private boolean cf = false;
    private boolean cg = false;
    private int iI = 0;
    private int iJ = 0;
    private boolean ch = false;
    private boolean ci = false;
    private boolean cj = false;
    private int iK = 0;
    private long by = 0L;
    private Future<?> w = null;
    private long bz;
    private Location A;
    private Location B;
    private int iL = 0;
    protected int _pvpFlag;
    private Future<?> x;
    private long bA;
    private TamedBeastInstance a = null;
    private long bB = 0L;
    private Location C;
    private int iM;
    private int iN = 0;
    private int iO = 0;
    private boolean ck;
    private ItemInstance e = null;
    private AtomicBoolean h;
    private Map<Integer, Long> aL;
    private Future<?> y;
    private int iP = 0;
    private Map<String, String> aM;
    private long bC = 0L;
    private long bD = 0L;

    public boolean hideHeadAccessories() {
        return this.bZ;
    }

    public void setHideHeadAccessories(boolean bl) {
        this.bZ = bl;
    }

    public int getSlotOneId() {
        return this.it;
    }

    public void setSlotOneId(int n) {
        this.it = n;
    }

    public int getSlotTwoId() {
        return this.iu;
    }

    public void setSlotTwoId(int n) {
        this.iu = n;
    }

    public int buffAnimRange() {
        return this.iz;
    }

    public void setBuffAnimRange(int n) {
        this.iz = n;
    }

    public boolean isNoShotsAnim() {
        return this.cb;
    }

    public void setNoShotsAnim(boolean bl) {
        this.cb = bl;
    }

    public Player(int n, PlayerTemplate playerTemplate, String string) {
        super(n, playerTemplate);
        this.cN = Config.DISCONNECTED_PLAYER_TITLE;
        this.hU = Config.DISCONNECTED_PLAYER_TITLE_COLOR;
        this.bL = Config.AUTO_LOOT;
        this.bM = Config.AUTO_LOOT_HERBS;
        this.bN = Config.AUTO_LOOT_ADENA;
        this.a = new PcInventory(this);
        this.a = new PcWarehouse(this);
        this.a = new PcRefund(this);
        this.a = new PcFreight(this);
        this.a = new LinkedList();
        this.aD = new TreeMap<Integer, Recipe>();
        this.aE = new TreeMap<Integer, Recipe>();
        this.aF = new TreeMap<Integer, PremiumItem>();
        this.aG = new HashMap<String, QuestState>();
        this.a = new ShortCutList(this);
        this.a = new MacroList(this);
        this.o = new AtomicInteger(0);
        this.aU = Collections.emptyList();
        this.aV = (long)Collections.emptyList();
        this.aW = Collections.emptyList();
        this.aX = Collections.emptyList();
        this.aY = Collections.emptyList();
        this.a = new Henna[3];
        this.a = new PlayerAccess();
        this.aI = new HashMap<Integer, String>(8);
        this.g = new AtomicBoolean();
        this.P = HardReferences.emptyRef();
        this.m = new CopyOnWriteArraySet<Integer>();
        this.p = new AtomicInteger(0);
        this._loto = new int[5];
        this._race = new int[2];
        this.a = new CharacterBlockList(this);
        this.a = new FriendList(this);
        this.a = new Bonus();
        this.l = ArrayUtils.EMPTY_BYTE_ARRAY;
        this.a = new Fishing(this);
        this.t = new ReentrantLock();
        this._transformationSkills = new HashMap<Integer, Skill>();
        this.a = InvisibleType.NONE;
        this.j = Containers.emptyIntObjectMap();
        this.aZ = new ArrayList<String>();
        this.k = new CHashIntObjectMap();
        this.aJ = new ConcurrentHashMap();
        this.a = new AutoFarmContext(this);
        this.a = new OneDayRewardStore(this);
        this.ba = Collections.emptyList();
        this.a = new HashIntSet();
        this.a = new AtomicReference<Object>(null);
        this.a = new MultiValueSet();
        this.h = new AtomicBoolean();
        this.aJ = string;
        this.hR = 0xFFFFFF;
        this.hS = 0xFFFF77;
        this._baseClass = this.getClassId().getId();
    }

    private Player(int n, PlayerTemplate playerTemplate) {
        this(n, playerTemplate, null);
        this._ai = new PlayerAI(this);
        if (!Config.EVERYBODY_HAS_ADMIN_RIGHTS) {
            this.setPlayerAccess(Config.gmlist.get(n));
        } else {
            this.setPlayerAccess(Config.gmlist.get(0));
        }
    }

    public HardReference<Player> getRef() {
        return super.getRef();
    }

    public String getAccountName() {
        if (this.a == null) {
            return this.aJ;
        }
        return this.a.getLogin();
    }

    public String getIP() {
        if (this.a == null) {
            return cM;
        }
        return this.a.getIpAddr();
    }

    public Map<Integer, String> getAccountChars() {
        return this.aI;
    }

    @Override
    public final PlayerTemplate getTemplate() {
        return (PlayerTemplate)this._template;
    }

    @Override
    public PlayerTemplate getBaseTemplate() {
        return (PlayerTemplate)this._baseTemplate;
    }

    public void changeSex() {
        this._template = CharacterTemplateHolder.getInstance().getTemplate(this.getClassId(), this.getSex() != 0);
    }

    @Override
    public PlayerAI getAI() {
        return (PlayerAI)this._ai;
    }

    @Override
    public void doCast(Skill skill, Creature creature, boolean bl) {
        if (skill == null) {
            return;
        }
        super.doCast(skill, creature, bl);
        this.triggerAfterTeleportProtection();
        this.triggerNoCarrierProtection();
    }

    @Override
    public void altUseSkill(Skill skill, Creature creature) {
        super.altUseSkill(skill, creature);
        this.triggerAfterTeleportProtection();
        this.triggerNoCarrierProtection();
    }

    @Override
    public void sendReuseMessage(Skill skill) {
        if (this.isCastingNow()) {
            return;
        }
        TimeStamp timeStamp = this.getSkillReuse(skill);
        if (timeStamp == null || !timeStamp.hasNotPassed()) {
            return;
        }
        long l = timeStamp.getReuseCurrent();
        if (!Config.ALT_SHOW_REUSE_MSG && l < 10000L || l < 500L) {
            return;
        }
        long l2 = l / 3600000L;
        long l3 = (l - l2 * 3600000L) / 60000L;
        long l4 = (long)Math.ceil((double)(l - l2 * 3600000L - l3 * 60000L) / 1000.0);
        if (l2 > 0L) {
            this.sendPacket((IStaticPacket)((SystemMessage)((SystemMessage)((SystemMessage)new SystemMessage(SystemMsg.THERE_ARE_S2_HOURS_S3_MINUTES_AND_S4_SECONDS_REMAINING_IN_S1S_REUSE_TIME).addSkillName(skill.getId(), skill.getDisplayLevel())).addNumber(l2)).addNumber(l3)).addNumber(l4));
        } else if (l3 > 0L) {
            this.sendPacket((IStaticPacket)((SystemMessage)((SystemMessage)new SystemMessage(SystemMsg.THERE_ARE_S2_MINUTES_S3_SECONDS_REMAINING_IN_S1S_REUSE_TIME).addSkillName(skill.getId(), skill.getDisplayLevel())).addNumber(l3)).addNumber(l4));
        } else {
            this.sendPacket((IStaticPacket)((SystemMessage)new SystemMessage(SystemMsg.THERE_ARE_S2_SECONDS_REMAINING_IN_S1S_REUSE_TIME).addSkillName(skill.getId(), skill.getDisplayLevel())).addNumber(l4));
        }
    }

    @Override
    public final int getLevel() {
        return this._activeClass == null ? 1 : this._activeClass.getLevel();
    }

    public int getSex() {
        return this.getTemplate().isMale ? 0 : 1;
    }

    public int getFace() {
        return this.gd;
    }

    public void setFace(int n) {
        this.gd = n;
    }

    public int getHairColor() {
        return this.gf;
    }

    public void setHairColor(int n) {
        this.gf = n;
    }

    public int getHairStyle() {
        return this.ge;
    }

    public void setHairStyle(int n) {
        this.ge = n;
    }

    public void offline() {
        Object object;
        if (this.a != null) {
            this.a.setActiveChar(null);
            this.a.close(ServerClose.STATIC);
            this.setNetConnection(null);
        }
        if (Config.SERVICES_OFFLINE_TRADE_NAME_COLOR_CHANGE) {
            this.setNameColor(Config.SERVICES_OFFLINE_TRADE_NAME_COLOR);
        }
        if (Config.SERVICES_OFFLINE_TRADE_ABNORMAL != AbnormalEffect.NULL) {
            this.startAbnormalEffect(Config.SERVICES_OFFLINE_TRADE_ABNORMAL);
        }
        this.setOfflineMode(true);
        this.setVar("offline", String.valueOf(System.currentTimeMillis() / 1000L), -1L);
        if (Config.SELLBUFF_ENABLED && this.isSellingBuffs()) {
            object = new StringBuilder();
            for (Map.Entry<Skill, Long> entry : this.getBuffs4Sale().entrySet()) {
                ((StringBuilder)object).append(String.format("%d:%d", entry.getKey().getId(), entry.getValue())).append(';');
            }
            this.setVar("offlinebuffs", ((StringBuilder)object).toString(), -1L);
        }
        if (Config.SERVICES_OFFLINE_TRADE_SECONDS_TO_KICK > 0L) {
            this.startKickTask(Config.SERVICES_OFFLINE_TRADE_SECONDS_TO_KICK * 1000L);
        }
        if ((object = this.getParty()) != null) {
            if (this.isFestivalParticipant()) {
                ((Party)object).broadcastMessageToPartyMembers(this.getName() + " has been removed from the upcoming festival.");
            }
            this.leaveParty();
        }
        if (this.getPet() != null) {
            this.getPet().unSummon();
        }
        CursedWeaponsManager.getInstance().doLogout(this);
        if (this.isOlyParticipant()) {
            this.getOlyParticipant().OnDisconnect(this);
        }
        this.broadcastCharInfo();
        this.stopWaterTask();
        this.stopBonusTask();
        this.stopHourlyTask();
        this.stopVitalityTask();
        this.stopPcBangPointsTask();
        this.stopAutoSaveTask();
        this.stopQuestTimers();
        try {
            this.getInventory().store();
        }
        catch (Throwable throwable) {
            bF.error("", throwable);
        }
        try {
            this.store(false);
        }
        catch (Throwable throwable) {
            bF.error("", throwable);
        }
    }

    public void kick() {
        if (this.a != null) {
            this.a.close(LeaveWorld.STATIC);
            this.setNetConnection(null);
        }
        this.bh();
        this.deleteMe();
    }

    public void restart() {
        if (this.a != null) {
            this.a.setActiveChar(null);
            this.setNetConnection(null);
        }
        this.bh();
        this.deleteMe();
    }

    public void logout() {
        if (this.a != null) {
            this.a.close(ServerClose.STATIC);
            this.setNetConnection(null);
        }
        this.bh();
        this.deleteMe();
    }

    private void bh() {
        MatchingRoom matchingRoom;
        FlagItemAttachment flagItemAttachment;
        Object object;
        SubUnit subUnit;
        UnitMember unitMember;
        Summon summon;
        Party party;
        if (this.g.getAndSet(true)) {
            return;
        }
        if (Config.ALLOW_CURSED_WEAPONS && Config.DROP_CURSED_WEAPONS_ON_LOGOUT && this.isCursedWeaponEquipped()) {
            this.setPvpFlag(0);
            CursedWeaponsManager.getInstance().dropPlayer(this);
        }
        this.setNetConnection(null);
        this.setIsOnline(false);
        this.getListeners().onExit();
        if (this.isFlying() && !this.checkLandingState()) {
            this._stablePoint = TeleportUtils.getRestartLocation(this, RestartType.TO_VILLAGE);
        }
        if (this.isCastingNow()) {
            this.abortCast(true, true);
        }
        if ((party = this.getParty()) != null) {
            if (this.isFestivalParticipant()) {
                party.broadcastMessageToPartyMembers(this.getName() + " has been removed from the upcoming festival.");
            }
            this.leaveParty();
        }
        if (Config.OLY_ENABLED && OlyController.getInstance().isCompetitionsActive()) {
            if (this.isOlyParticipant()) {
                this.getOlyParticipant().OnDisconnect(this);
            }
            if (ParticipantPool.getInstance().isRegistred(this)) {
                ParticipantPool.getInstance().onLogout(this);
            }
        }
        CursedWeaponsManager.getInstance().doLogout(this);
        if (this.isOlyObserver()) {
            this.leaveOlympiadObserverMode();
        }
        if (this.isInObserverMode()) {
            this.leaveObserverMode();
        }
        this.stopFishing();
        if (this._stablePoint != null) {
            this.teleToLocation(this._stablePoint);
        }
        if ((summon = this.getPet()) != null) {
            if (Config.ALT_SAVE_SERVITOR_BUFF) {
                summon.saveEffects();
            }
            summon.unSummon();
        }
        this.a.notifyFriends(false);
        if (this.isProcessingRequest()) {
            this.getRequest().cancel();
        }
        this.stopAllTimers();
        if (this.isInZone(Zone.ZoneType.no_restart)) {
            this.setVar(NO_RESTART_ZONE_LOGOUT_TIMESTAMP, System.currentTimeMillis(), -1L);
        }
        if (this.isInBoat()) {
            this.getBoat().removePlayer(this);
        }
        UnitMember unitMember2 = unitMember = (subUnit = this.getSubUnit()) == null ? null : subUnit.getUnitMember(this.getObjectId());
        if (unitMember != null) {
            int n = unitMember.getSponsor();
            int n2 = this.getApprentice();
            object = new PledgeShowMemberListUpdate(this);
            ExPledgeCount exPledgeCount = new ExPledgeCount(Math.max(1, this.a.getOnlineMembers(0).size() - 1));
            for (Player player : this.a.getOnlineMembers(this.getObjectId())) {
                player.sendPacket(new IStaticPacket[]{object, exPledgeCount});
                if (player.getObjectId() == n) {
                    player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOUR_APPRENTICE_C1_HAS_LOGGED_OUT).addString(this._name));
                    continue;
                }
                if (player.getObjectId() != n2) continue;
                player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOUR_SPONSOR_C1_HAS_LOGGED_OUT).addString(this._name));
            }
            for (Player player : this.a.getOnlineMembers(this.getObjectId())) {
                player.sendPacket((IStaticPacket)object);
                if (player.getObjectId() == n) {
                    player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOUR_APPRENTICE_C1_HAS_LOGGED_OUT).addString(this._name));
                    continue;
                }
                if (player.getObjectId() != n2) continue;
                player.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOUR_SPONSOR_C1_HAS_LOGGED_OUT).addString(this._name));
            }
            unitMember.setPlayerInstance(this, true);
        }
        if ((flagItemAttachment = this.getActiveWeaponFlagAttachment()) != null) {
            flagItemAttachment.onLogout(this);
        }
        if (CursedWeaponsManager.getInstance().getCursedWeapon(this.getCursedWeaponEquippedId()) != null) {
            CursedWeaponsManager.getInstance().getCursedWeapon(this.getCursedWeaponEquippedId()).setPlayer(null);
        }
        if ((matchingRoom = this.getMatchingRoom()) != null) {
            if (matchingRoom.getLeader() == this) {
                matchingRoom.disband();
            } else {
                matchingRoom.removeMember(this, false);
            }
        }
        this.setMatchingRoom(null);
        MatchingRoomManager.getInstance().removeFromWaitingList(this);
        this.destroyAllTraps();
        this.stopPvPFlag();
        object = this.getReflection();
        if (object != ReflectionManager.DEFAULT) {
            if (((Reflection)object).getReturnLoc() != null) {
                this._stablePoint = ((Reflection)object).getReturnLoc();
            }
            ((Reflection)object).removeObject(this);
        }
        try {
            this.getInventory().store();
            this.getRefund().clear();
        }
        catch (Throwable throwable) {
            bF.error("", throwable);
        }
        try {
            this.store(false);
        }
        catch (Throwable throwable) {
            bF.error("", throwable);
        }
    }

    public Collection<Recipe> getDwarvenRecipeBook() {
        return this.aD.values();
    }

    public Collection<Recipe> getCommonRecipeBook() {
        return this.aE.values();
    }

    public int recipesCount() {
        return this.aE.size() + this.aD.size();
    }

    public boolean hasRecipe(Recipe recipe) {
        return this.aD.containsValue(recipe) || this.aE.containsValue(recipe);
    }

    public boolean findRecipe(int n) {
        return this.aD.containsKey(n) || this.aE.containsKey(n);
    }

    public void registerRecipe(Recipe recipe, boolean bl) {
        if (recipe == null) {
            return;
        }
        switch (recipe.getType()) {
            case ERT_COMMON: {
                this.aE.put(recipe.getId(), recipe);
                break;
            }
            case ERT_DWARF: {
                this.aD.put(recipe.getId(), recipe);
                break;
            }
            default: {
                return;
            }
        }
        if (bl) {
            mysql.set("REPLACE INTO character_recipebook (char_id, id) VALUES(?,?)", this.getObjectId(), recipe.getId());
        }
    }

    public void unregisterRecipe(int n) {
        if (this.aD.containsKey(n)) {
            mysql.set("DELETE FROM `character_recipebook` WHERE `char_id`=? AND `id`=? LIMIT 1", this.getObjectId(), n);
            this.aD.remove(n);
        } else if (this.aE.containsKey(n)) {
            mysql.set("DELETE FROM `character_recipebook` WHERE `char_id`=? AND `id`=? LIMIT 1", this.getObjectId(), n);
            this.aE.remove(n);
        } else {
            bF.warn("Attempted to remove unknown RecipeList" + n);
        }
    }

    public QuestState getQuestState(Quest quest) {
        return this.getQuestState(quest.getName());
    }

    public QuestState getQuestState(String string) {
        this.questRead.lock();
        try {
            QuestState questState = this.aG.get(string);
            return questState;
        }
        finally {
            this.questRead.unlock();
        }
    }

    public QuestState getQuestState(Class<?> clazz) {
        return this.getQuestState(clazz.getSimpleName());
    }

    public boolean isQuestCompleted(String string) {
        QuestState questState = this.getQuestState(string);
        return questState != null && questState.isCompleted();
    }

    public boolean isQuestCompleted(Class<?> clazz) {
        QuestState questState = this.getQuestState(clazz);
        return questState != null && questState.isCompleted();
    }

    public void setQuestState(QuestState questState) {
        this.questWrite.lock();
        try {
            this.aG.put(questState.getQuest().getName(), questState);
        }
        finally {
            this.questWrite.unlock();
        }
    }

    public void removeQuestState(String string) {
        this.questWrite.lock();
        try {
            this.aG.remove(string);
        }
        finally {
            this.questWrite.unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Quest[] getAllActiveQuests() {
        ArrayList<Quest> arrayList = new ArrayList<Quest>(this.aG.size());
        this.questRead.lock();
        try {
            for (QuestState questState : this.aG.values()) {
                if (!questState.isStarted()) continue;
                arrayList.add(questState.getQuest());
            }
        }
        finally {
            this.questRead.unlock();
        }
        return arrayList.toArray(new Quest[arrayList.size()]);
    }

    public QuestState[] getAllQuestsStates() {
        this.questRead.lock();
        try {
            QuestState[] questStateArray = this.aG.values().toArray(new QuestState[this.aG.size()]);
            return questStateArray;
        }
        finally {
            this.questRead.unlock();
        }
    }

    public List<QuestState> getQuestsForEvent(NpcInstance npcInstance, QuestEventType questEventType) {
        ArrayList<QuestState> arrayList = new ArrayList<QuestState>();
        Quest[] questArray = npcInstance.getTemplate().getEventQuests(questEventType);
        if (questArray != null) {
            for (Quest quest : questArray) {
                QuestState questState = this.getQuestState(quest.getName());
                if (questState == null || questState.isCompleted()) continue;
                arrayList.add(this.getQuestState(quest.getName()));
            }
        }
        return arrayList;
    }

    public void processQuestEvent(String string, String string2, NpcInstance npcInstance) {
        QuestState questState;
        if (string2 == null) {
            string2 = "";
        }
        if ((questState = this.getQuestState(string)) == null) {
            Quest quest = QuestManager.getQuest(string);
            if (quest == null) {
                bF.warn("Quest " + string + " not found!");
                return;
            }
            questState = quest.newQuestState(this, 1);
        }
        if (questState == null || questState.isCompleted()) {
            return;
        }
        questState.getQuest().notifyEvent(string2, questState, npcInstance);
        if (questState.getQuest().getQuestIntId() == 255 || questState.getQuest().getQuestIntId() == 999) {
            return;
        }
        this.sendPacket((IStaticPacket)new QuestList(this));
    }

    public boolean isQuestContinuationPossible(boolean bl) {
        if (this.getWeightPenalty() >= 3 || (double)this.getInventoryLimit() * 0.8 < (double)this.getInventory().getSize() || (double)Config.QUEST_INVENTORY_MAXIMUM * 0.9 < (double)this.getInventory().getQuestSize()) {
            if (bl) {
                this.sendPacket((IStaticPacket)SystemMsg.PROGRESS_IN_A_QUEST_IS_POSSIBLE_ONLY_WHEN_YOUR_INVENTORYS_WEIGHT_AND_SLOT_COUNT_ARE_LESS_THAN_80_PERCENT_OF_CAPACITY);
            }
            return false;
        }
        return true;
    }

    public void stopQuestTimers() {
        for (QuestState questState : this.getAllQuestsStates()) {
            if (questState.isStarted()) {
                questState.pauseQuestTimers();
                continue;
            }
            questState.stopQuestTimers();
        }
    }

    public void resumeQuestTimers() {
        for (QuestState questState : this.getAllQuestsStates()) {
            questState.resumeQuestTimers();
        }
    }

    public Collection<ShortCut> getAllShortCuts() {
        return this.a.getAllShortCuts();
    }

    public ShortCut[] getShortCuts() {
        return this.a.getShortCuts();
    }

    public ShortCut getShortCut(int n, int n2) {
        return this.a.getShortCut(n, n2);
    }

    public void registerShortCut(ShortCut shortCut) {
        this.a.registerShortCut(shortCut);
    }

    public void deleteShortCut(int n, int n2) {
        this.a.deleteShortCut(n, n2);
    }

    public void registerMacro(Macro macro) {
        this.a.registerMacro(macro);
    }

    public void deleteMacro(int n) {
        this.a.deleteMacro(n);
    }

    public MacroList getMacroses() {
        return this.a;
    }

    public boolean isCastleLord(int n) {
        return this.a != null && this.isClanLeader() && this.a.getCastle() == n;
    }

    public int getPkKills() {
        return this.hP;
    }

    public void setPkKills(int n) {
        this.hP = n;
    }

    public long getCreateTime() {
        return this.bk;
    }

    public void setCreateTime(long l) {
        this.bk = l;
    }

    public int getDeleteTimer() {
        return this.gc;
    }

    public void setDeleteTimer(int n) {
        this.gc = n;
    }

    public int getCurrentLoad() {
        return this.getInventory().getTotalWeight();
    }

    public long getLastAccess() {
        return this.aV;
    }

    public void setLastAccess(long l) {
        this.aV = l;
    }

    public boolean isRecommended(Player player) {
        return this.a.contains(player.getObjectId());
    }

    public void setReceivedRec(int n) {
        this.iC = n;
    }

    public int getReceivedRec() {
        return this.iC;
    }

    public void setGivableRec(int n) {
        this.iD = n;
    }

    public int getGivableRec() {
        return this.iD;
    }

    public void updateRecommends() {
        this.a.clear();
        if (this.getLevel() >= 40) {
            this.iD = 9;
            this.iC = Math.max(0, this.iC - 4);
        } else if (this.getLevel() >= 20) {
            this.iD = 6;
            this.iC = Math.max(0, this.iC - 2);
        } else if (this.getLevel() >= 10) {
            this.iD = 3;
            this.iC = Math.max(0, this.iC - 1);
        } else {
            this.iD = 0;
            this.iC = 0;
        }
    }

    public void restoreGivableAndReceivedRec(int n, int n2) {
        this.iD = n;
        this.iC = n2;
        Calendar calendar = Calendar.getInstance();
        calendar.set(11, Config.REC_FLUSH_HOUR);
        calendar.set(12, Config.REC_FLUSH_MINUTE);
        calendar.set(13, 0);
        calendar.set(14, 0);
        long l = Math.round((System.currentTimeMillis() / 1000L - this.getLastAccess()) / 86400L);
        if (l == 0L && this.getLastAccess() < calendar.getTimeInMillis() / 1000L && System.currentTimeMillis() > calendar.getTimeInMillis()) {
            ++l;
        }
        int n3 = 1;
        while ((long)n3 < l) {
            this.updateRecommends();
            ++n3;
        }
        if (l > 0L) {
            this.restartDailyCounters(true);
        }
    }

    public void giveRecommendation(Player player) {
        if (player == null) {
            return;
        }
        if (this.getGivableRec() <= 0 || player.getReceivedRec() >= 255) {
            return;
        }
        if (this.a.contains(player.getObjectId())) {
            return;
        }
        this.a.add(player.getObjectId());
        this.setGivableRec(this.getGivableRec() - 1);
        this.sendUserInfo(true, UserInfoType.SOCIAL);
        player.setReceivedRec(player.getReceivedRec() + 1);
        player.sendPacket((IStaticPacket)new ExVoteSystemInfo(player));
        player.broadcastUserInfo(true, UserInfoType.SOCIAL);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void bi() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("SELECT `targetId` AS `recommendedObjId` FROM `character_recommends` WHERE `objId` = ?");
            preparedStatement.setInt(1, this.getObjectId());
            resultSet = preparedStatement.executeQuery();
            this.a.clear();
            while (resultSet.next()) {
                int n = resultSet.getInt("recommendedObjId");
                this.a.add(n);
            }
        }
        catch (SQLException sQLException) {
            try {
                bF.error("Can't load recommended characters", (Throwable)sQLException);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement, resultSet);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        }
        DbUtils.closeQuietly(connection, preparedStatement, resultSet);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void bj() {
        PreparedStatement preparedStatement;
        Connection connection;
        block5: {
            connection = null;
            preparedStatement = null;
            try {
                connection = DatabaseFactory.getInstance().getConnection();
                preparedStatement = connection.prepareStatement("DELETE FROM `character_recommends` WHERE `objId` = ?");
                preparedStatement.setInt(1, this.getObjectId());
                preparedStatement.executeUpdate();
                DbUtils.close(preparedStatement);
                if (this.a.isEmpty()) break block5;
                preparedStatement = connection.prepareStatement("INSERT INTO `character_recommends` (`objId`, `targetId`) VALUES (?, ?)");
                IntIterator intIterator = this.a.iterator();
                while (intIterator.hasNext()) {
                    preparedStatement.setInt(1, this.getObjectId());
                    preparedStatement.setInt(2, intIterator.next());
                    preparedStatement.executeUpdate();
                }
            }
            catch (SQLException sQLException) {
                try {
                    bF.error("Can't store recommended characters", (Throwable)sQLException);
                }
                catch (Throwable throwable) {
                    DbUtils.closeQuietly(connection, preparedStatement);
                    throw throwable;
                }
                DbUtils.closeQuietly(connection, preparedStatement);
            }
        }
        DbUtils.closeQuietly(connection, preparedStatement);
    }

    public void restartDailyCounters(boolean bl) {
        if (!bl) {
            this.sendUserInfo(true);
        }
        if (Config.ALLOW_MAIL) {
            this.setVar(USED_MAIL_SEND_POINTS, 0, -1L);
        }
        if (Config.ENABLE_WORLD_CHAT) {
            this.setVar(USED_WORLD_CHAT_POINTS, 0, -1L);
            if (!bl) {
                this.sendPacket((IStaticPacket)new ExWorldChatCnt(0));
            }
        }
        if (Config.PRIME_SHOP_VIP_SYSTEM_ENABLED) {
            this.setVar(VIP_ITEM_BOUGHT, 0, -1L);
            VipManager.getInstance().checkVipLevelExpiration(this);
        }
    }

    @Override
    public int getKarma() {
        return this.gh;
    }

    public void setKarma(int n, boolean bl) {
        if (n < 0) {
            n = 0;
        }
        if (this.gh == n) {
            return;
        }
        this.gh = n;
        if (bl) {
            this.sendChanges();
        }
        if (this.getPet() != null) {
            this.getPet().broadcastCharInfo();
        }
    }

    @Override
    public int getMaxLoad() {
        int n = this.getCON();
        if (n < 1) {
            return (int)(31000.0 * Config.MAXLOAD_MODIFIER);
        }
        return (int)this.calcStat(Stats.MAX_LOAD, Math.pow(1.029993928, n) * 30495.627366 * Config.MAXLOAD_MODIFIER, this, null);
    }

    @Override
    public void updateEffectIcons() {
        if (this.entering || this.isLogoutStarted()) {
            return;
        }
        if (Config.USER_INFO_INTERVAL == 0L) {
            if (this.u != null) {
                this.u.cancel(false);
                this.u = null;
            }
            this.updateEffectIconsImpl();
            return;
        }
        if (this.u != null) {
            return;
        }
        this.u = ThreadPoolManager.getInstance().schedule(new UpdateEffectIcons(), Config.USER_INFO_INTERVAL);
    }

    private void updateEffectIconsImpl() {
        PartySpelled partySpelled = new PartySpelled(this, false);
        AbnormalStatusUpdate abnormalStatusUpdate = new AbnormalStatusUpdate();
        ExAbnormalStatusUpdateFromTarget exAbnormalStatusUpdateFromTarget = new ExAbnormalStatusUpdateFromTarget(this);
        Collection<Effect> collection = this.getEffectList().getAllFirstEffects();
        for (Effect.EEffectSlot eEffectSlot : Effect.EEffectSlot.VALUES) {
            for (Effect effect : collection) {
                if (!effect.isInUse() || effect.getEffectSlot() != eEffectSlot) continue;
                if (effect.isStackTypeMatch("HpRecoverCast")) {
                    this.sendPacket((IStaticPacket)new ShortBuffStatusUpdate(effect));
                } else {
                    effect.addIcon(abnormalStatusUpdate);
                    effect.addIcon(exAbnormalStatusUpdateFromTarget);
                }
                if (this._party == null) continue;
                effect.addPartySpelledIcon(partySpelled);
            }
        }
        if (this.getTarget() == this) {
            this.sendPacket(abnormalStatusUpdate, exAbnormalStatusUpdateFromTarget);
        } else {
            this.sendPacket((IStaticPacket)abnormalStatusUpdate);
        }
        if (this._party != null) {
            this._party.broadCast(partySpelled);
        }
        if (this.isVisible()) {
            List<Player> list = World.getAroundPlayers(this);
            for (int i = 0; i < list.size(); ++i) {
                Player player = (Player)list.get(i);
                if (player.getTarget() != this) continue;
                player.sendPacket((IStaticPacket)exAbnormalStatusUpdateFromTarget);
            }
        }
        if (this.isOlyParticipant()) {
            this.getOlyParticipant().getCompetition().broadcastEffectIcons(this, collection);
        }
    }

    public int getWeightPenalty() {
        return this.getSkillLevel(4270, 0);
    }

    public void refreshOverloaded() {
        if (this.isLogoutStarted() || this.getMaxLoad() <= 0) {
            return;
        }
        this.setOverloaded(this.getCurrentLoad() > this.getMaxLoad());
        double d = 100.0 * ((double)this.getCurrentLoad() - this.calcStat(Stats.MAX_NO_PENALTY_LOAD, 0.0, this, null)) / (double)this.getMaxLoad();
        int n = 0;
        n = d < 50.0 ? 0 : (d < 66.6 ? 1 : (d < 80.0 ? 2 : (d < 100.0 ? 3 : 4)));
        int n2 = this.getWeightPenalty();
        if (n2 == n) {
            return;
        }
        if (n > 0) {
            super.addSkill(SkillTable.getInstance().getInfo(4270, n));
        } else {
            super.removeSkill(this.getKnownSkill(4270));
        }
        this.sendSkillList();
        this.sendEtcStatusUpdate();
        this.updateStats();
    }

    public Grade getExpertiseGrade() {
        return this._expertiseGrade;
    }

    public int getArmorsExpertisePenalty() {
        return this.im;
    }

    public int getWeaponsExpertisePenalty() {
        return this.il;
    }

    public int getExpertisePenalty(ItemInstance itemInstance) {
        if (itemInstance.getTemplate().getType2() == 0) {
            return this.getWeaponsExpertisePenalty();
        }
        if (itemInstance.getTemplate().getType2() == 1 || itemInstance.getTemplate().getType2() == 2) {
            return this.getArmorsExpertisePenalty();
        }
        return 0;
    }

    public void refreshExpertisePenalty() {
        ItemInstance[] itemInstanceArray;
        if (this.isLogoutStarted() || Config.DISABLE_GRADE_PENALTY) {
            return;
        }
        int n = (int)this.calcStat(Stats.GRADE_EXPERTISE_LEVEL, this.getLevel(), null, null);
        Grade grade = CrystalGradeDataHolder.getInstance().getGradeByLevel(n);
        boolean bl = false;
        if (this._expertiseGrade != grade) {
            this._expertiseGrade = grade;
            if (grade.ordinal() > 0) {
                this.addSkill(SkillTable.getInstance().getInfo(239, grade.ordinal()), false);
                bl = true;
            }
        }
        int n2 = 0;
        int n3 = 0;
        block4: for (ItemInstance itemInstance : itemInstanceArray = this.getInventory().getPaperdollItems()) {
            Grade grade2;
            int n4;
            if (itemInstance == null || (n4 = (grade2 = itemInstance.getCrystalType()).ordinal() - this._expertiseGrade.ordinal()) <= 0) continue;
            switch (itemInstance.getTemplate().getType2()) {
                case 0: {
                    if (n4 <= n2) continue block4;
                    n2 = n4;
                    continue block4;
                }
                case 1: 
                case 2: {
                    ++n3;
                }
            }
        }
        if (n2 > 4) {
            n2 = 4;
        }
        if (n3 > 4) {
            n3 = 4;
        }
        if (this.il != n2) {
            this.il = n2;
            if (n2 > 0) {
                super.addSkill(SkillTable.getInstance().getInfo(6209, this.il));
            } else {
                super.removeSkill(this.getKnownSkill(6209));
            }
            bl = true;
        }
        if (this.im != n3) {
            this.im = n3;
            if (n3 > 0) {
                super.addSkill(SkillTable.getInstance().getInfo(6213, this.im));
            } else {
                super.removeSkill(this.getKnownSkill(6213));
            }
            bl = true;
        }
        if (bl) {
            this.getInventory().validateItemsSkills();
            this.sendSkillList();
            this.sendEtcStatusUpdate();
            this.updateStats();
        }
    }

    public int getPvpKills() {
        return this.hQ;
    }

    public void setPvpKills(int n) {
        this.hQ = n;
    }

    public ClassId getClassId() {
        return this.getTemplate().classId;
    }

    public void addClanPointsOnProfession(int n) {
        if (this.getLvlJoinedAcademy() != 0 && this.a != null && this.a.getLevel() >= 5 && ClassId.VALUES[n].getLevel() == 2) {
            this.a.incReputation(100, true, "Academy");
        } else if (this.getLvlJoinedAcademy() != 0 && this.a != null && this.a.getLevel() >= 5 && ClassId.VALUES[n].getLevel() == 3) {
            int n2 = 0;
            n2 = this.getLvlJoinedAcademy() > 39 ? 160 : (this.getLvlJoinedAcademy() > 16 ? 400 - (this.getLvlJoinedAcademy() - 16) * 10 : 400);
            this.a.removeClanMember(this.getObjectId());
            SystemMessage systemMessage = new SystemMessage(SystemMsg.CLAN_ACADEMY_MEMBER_S1_HAS_SUCCESSFULLY_COMPLETED_THE_2ND_CLASS_TRANSFER_AND_OBTAINED_S2_CLAN_REPUTATION_POINTS);
            systemMessage.addString(this.getName());
            systemMessage.addNumber(this.a.incReputation(n2, true, "Academy"));
            this.a.broadcastToOnlineMembers(systemMessage);
            this.a.broadcastToOtherOnlineMembers(new PledgeShowMemberListDelete(this.getName()), this);
            this.setClan(null);
            this.setTitle("");
            this.sendPacket((IStaticPacket)SystemMsg.CONGRATULATIONS_YOU_WILL_NOW_GRADUATE_FROM_THE_CLAN_ACADEMY_AND_LEAVE_YOUR_CURRENT_CLAN);
            this.setLeaveClanTime(0L);
            this.broadcastCharInfo();
            this.sendPacket((IStaticPacket)ExPledgeWaitingListAlarm.STATIC_PACKET);
            this.sendPacket((IStaticPacket)PledgeShowMemberListDeleteAll.STATIC);
            ItemFunctions.addItem((Playable)this, 8181, 1L, true);
        }
    }

    public synchronized void setClassId(int n, boolean bl, boolean bl2) {
        Object object;
        boolean bl3;
        if (!(bl || ClassId.VALUES[n].equalsOrChildOf(ClassId.VALUES[this.getActiveClassId()]) || this.getPlayerAccess().CanChangeClass || Config.EVERYBODY_HAS_ADMIN_RIGHTS)) {
            Thread.dumpStack();
            return;
        }
        boolean bl4 = bl3 = !this.getSubClasses().containsKey(n);
        if (bl3) {
            object = this.getActiveClass();
            this.getSubClasses().remove(this.getActiveClassId());
            this.changeClassInDb(((SubClass)object).getClassId(), n);
            if (((SubClass)object).isBase()) {
                this.addClanPointsOnProfession(n);
                ItemInstance itemInstance = null;
                if (ClassId.VALUES[n].getLevel() == 2) {
                    if (bl2 && Config.ALT_ALLOW_SHADOW_WEAPONS) {
                        itemInstance = ItemFunctions.createItem(8869);
                    }
                    this.unsetVar("newbieweapon");
                    this.unsetVar("p1q2");
                    this.unsetVar("p1q3");
                    this.unsetVar("p1q4");
                    this.unsetVar("prof1");
                    this.unsetVar("ng1");
                    this.unsetVar("ng2");
                    this.unsetVar("ng3");
                    this.unsetVar("ng4");
                } else if (ClassId.VALUES[n].getLevel() == 3) {
                    if (bl2 && Config.ALT_ALLOW_SHADOW_WEAPONS) {
                        itemInstance = ItemFunctions.createItem(8870);
                    }
                    this.unsetVar("newbiearmor");
                    this.unsetVar("dd1");
                    this.unsetVar("dd2");
                    this.unsetVar("dd3");
                    this.unsetVar("prof2.1");
                    this.unsetVar("prof2.2");
                    this.unsetVar("prof2.3");
                }
                if (itemInstance != null) {
                    itemInstance.setCount(15L);
                    this.sendPacket((IStaticPacket)SystemMessage.obtainItems(itemInstance));
                    this.getInventory().addItem(itemInstance);
                }
            }
            ((SubClass)object).setClassId(n);
            this.getSubClasses().put(n, (SubClass)object);
            this.a(true, 0);
            this.storeCharSubClasses();
            if (bl2) {
                this.broadcastPacket(new SocialAction(this.getObjectId(), 20016));
                this.broadcastPacket(new SocialAction(this.getObjectId(), 3));
                this.sendPacket((IStaticPacket)new PlaySound("ItemSound.quest_fanfare_2"));
            }
            this.broadcastCharInfo();
        }
        if ((object = CharacterTemplateHolder.getInstance().getTemplate(ClassId.getClassById(n), this.getSex() == 0)) == null) {
            bF.error("Missing template for classId: " + n);
            return;
        }
        this._template = object;
        if (this.isInParty()) {
            this.getParty().broadCast(new PartySmallWindowUpdate(this));
        }
        if (this.getClan() != null) {
            this.getClan().broadcastToOnlineMembers(new PledgeShowMemberListUpdate(this));
        }
        if (this.a != null) {
            this.a.broadcastPlayerUpdate(this);
        }
        this.sendSkillList();
        this.getListeners().onSetClass(n);
    }

    public long getExp() {
        return this._activeClass == null ? 0L : this._activeClass.getExp();
    }

    public long getMaxExp() {
        return this._activeClass == null ? Experience.LEVEL[Experience.getMaxLevel() + 1] : this._activeClass.getMaxExp();
    }

    public void setEnchantScroll(ItemInstance itemInstance) {
        this.d = itemInstance;
    }

    public ItemInstance getEnchantScroll() {
        return this.d;
    }

    public IRefineryHandler getRefineryHandler() {
        return this.a;
    }

    public void setRefineryHandler(IRefineryHandler iRefineryHandler) {
        this.a = iRefineryHandler;
    }

    public void setFistsWeaponItem(WeaponTemplate weaponTemplate) {
        this.a = weaponTemplate;
    }

    public WeaponTemplate getFistsWeaponItem() {
        return this.a;
    }

    public WeaponTemplate findFistsWeaponItem(int n) {
        if (n >= 0 && n <= 9) {
            return (WeaponTemplate)ItemHolder.getInstance().getTemplate(246);
        }
        if (n >= 10 && n <= 17) {
            return (WeaponTemplate)ItemHolder.getInstance().getTemplate(251);
        }
        if (n >= 18 && n <= 24) {
            return (WeaponTemplate)ItemHolder.getInstance().getTemplate(244);
        }
        if (n >= 25 && n <= 30) {
            return (WeaponTemplate)ItemHolder.getInstance().getTemplate(249);
        }
        if (n >= 31 && n <= 37) {
            return (WeaponTemplate)ItemHolder.getInstance().getTemplate(245);
        }
        if (n >= 38 && n <= 43) {
            return (WeaponTemplate)ItemHolder.getInstance().getTemplate(250);
        }
        if (n >= 44 && n <= 48) {
            return (WeaponTemplate)ItemHolder.getInstance().getTemplate(248);
        }
        if (n >= 49 && n <= 52) {
            return (WeaponTemplate)ItemHolder.getInstance().getTemplate(252);
        }
        if (n >= 53 && n <= 57) {
            return (WeaponTemplate)ItemHolder.getInstance().getTemplate(247);
        }
        return null;
    }

    public void addExpAndCheckBonus(MonsterInstance monsterInstance, double d, double d2, double d3) {
        if (this._activeClass == null) {
            return;
        }
        double d4 = 0.0;
        int n = monsterInstance.getLevel();
        if (Config.ALT_VITALITY_ENABLED) {
            boolean bl = false;
            d4 = monsterInstance.isRaid() ? 0.0 : (double)this.getVitalityLevel(false) / 2.0;
            d4 *= Config.ALT_VITALITY_RATE;
            if (d > 0.0) {
                if (!monsterInstance.isRaid()) {
                    if (!this.getVarB("NoExp") || this.getExp() != Experience.LEVEL[this.getLevel() + 1] - 1L) {
                        double d5 = d / (double)(n * n) * 100.0 / 9.0;
                        d5 *= Config.ALT_VITALITY_CONSUME_RATE;
                        if (bl || this.getEffectList().getEffectByType(EffectType.Vitality) != null) {
                            d5 = -d5;
                        }
                        this.setVitality(this.getVitality() - d5 * d3);
                    }
                } else {
                    this.setVitality(this.getVitality() + (double)Config.ALT_VITALITY_RAID_BONUS);
                }
            }
        }
        double d6 = Config.RATE_XP * this.getRateExp();
        double d7 = Config.RATE_SP * this.getRateSp();
        if (monsterInstance.isRaid()) {
            d6 = Config.RATE_RAIDBOSS_XP * this.getRaidRateExp();
            d7 = Config.RATE_RAIDBOSS_SP * this.getRaidRateSp();
        }
        long l = Config.ALT_VITALITY_ENABLED && this.getVitality() > 0.0 ? (long)(d * (d6 * d4)) : (long)(d * d6);
        long l2 = Config.ALT_VITALITY_ENABLED && this.getVitality() > 0.0 ? (long)(d2 * (d7 * d4)) : (long)(d2 * d7);
        long l3 = (long)(d * d6);
        long l4 = (long)(d2 * d7);
        this.addExpAndSp(l, l2, l - l3, l2 - l4, false, true);
    }

    @Override
    public void addExpAndSp(long l, long l2) {
        this.addExpAndSp(l, l2, 0L, 0L, false, false);
    }

    public void addExpAndSp(long l, long l2, long l3, long l4, boolean bl, boolean bl2) {
        int n;
        if (this._activeClass == null) {
            return;
        }
        if (bl) {
            l = (long)((double)l * (Config.RATE_XP * this.getRateExp()));
            l2 = (long)((double)l2 * (Config.RATE_SP * this.getRateSp()));
        }
        Summon summon = this.getPet();
        boolean bl3 = false;
        if (l > 0L) {
            if (bl2 && summon != null && !summon.isDead()) {
                if (summon.getNpcId() == 12564) {
                    summon.addExpAndSp(l, 0L);
                    l = 0L;
                } else if (summon.isPet() && summon.getExpPenalty() > 0.0) {
                    if (summon.getLevel() > this.getLevel() - 20 && summon.getLevel() < this.getLevel() + 5) {
                        summon.addExpAndSp((long)((double)l * summon.getExpPenalty()), 0L);
                        l = (long)((double)l * (1.0 - summon.getExpPenalty()));
                    } else {
                        summon.addExpAndSp((long)((double)l * summon.getExpPenalty() / 5.0), 0L);
                        l = (long)((double)l * (1.0 - summon.getExpPenalty() / 5.0));
                    }
                } else if (summon.isSummon()) {
                    l = (long)((double)l * (1.0 - summon.getExpPenalty()));
                }
            }
            long l5 = this.getVarB("NoExp") ? Experience.LEVEL[this.getLevel() + 1] - 1L : this.getMaxExp();
            l = Math.min(l, l5 - this.getExp());
        }
        int n2 = this._activeClass.getLevel();
        this._activeClass.addExp(l);
        this._activeClass.addSp(l2);
        if (l > 0L && l2 > 0L && (l3 > 0L || l4 > 0L)) {
            this.sendPacket((IStaticPacket)((SystemMessage)((SystemMessage)((SystemMessage)new SystemMessage(SystemMsg.YOU_HAVE_ACQUIRED_S1_EXP_BONUS_S2_AND_S3_SP_BONUS_S4).addNumber(l)).addNumber(l3)).addNumber(l2)).addNumber((int)l4));
        } else if (l2 > 0L && l == 0L) {
            this.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_HAVE_ACQUIRED_S1_SP).addNumber(l2));
        } else if (l2 > 0L && l > 0L) {
            this.sendPacket((IStaticPacket)((SystemMessage)new SystemMessage(SystemMsg.YOU_HAVE_EARNED_S1_EXPERIENCE_AND_S2_SP).addNumber(l)).addNumber(l2));
        } else if (l2 == 0L && l > 0L) {
            this.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_HAVE_EARNED_S1_EXPERIENCE).addNumber(l));
        }
        if (l < 0L) {
            this.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.EXPERIENCE_HAS_DECREASED_BY_S1).addNumber(Math.abs(l)));
        }
        if (l2 < 0L) {
            this.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOUR_SP_HAS_DECREASED_BY_S1).addNumber(Math.abs(l2)));
        }
        if ((n = this._activeClass.getLevel()) != n2) {
            int n3 = n - n2;
            if (n > n2) {
                this.j(n);
            }
            this.b(n3, n2);
            this.getListeners().onLevelUp(n);
        }
        this.updateStats();
        if (summon != null && bl3) {
            summon.broadcastCharInfo();
        }
        this.getListeners().onGainExpSp(l, l2);
    }

    private void a(boolean bl, int n) {
        boolean bl2 = false;
        boolean bl3 = false;
        if (Config.AUTO_LEARN_SKILLS) {
            int n2 = 0;
            Collection<SkillLearn> collection = SkillAcquireHolder.getInstance().getAvailableSkills(this, ClassId.VALUES[this.getActiveClassId()], AcquireType.NORMAL, null, 0);
            while (collection.size() > n2) {
                n2 = 0;
                for (SkillLearn skillLearn : collection) {
                    Skill skill = SkillTable.getInstance().getInfo(skillLearn.getId(), skillLearn.getLevel());
                    if (skill == null || !skill.getCanLearn(this.getClassId()) || !skillLearn.canAutoLearn()) {
                        ++n2;
                        continue;
                    }
                    this.addSkill(skill, true);
                }
                collection = SkillAcquireHolder.getInstance().getAvailableSkills(this, AcquireType.NORMAL);
            }
            bl2 = true;
        } else {
            for (SkillLearn skillLearn : SkillAcquireHolder.getInstance().getAvailableSkills(this, AcquireType.NORMAL)) {
                if (skillLearn.getCost() == 0 && skillLearn.getItemId() == 0) {
                    Skill skill = SkillTable.getInstance().getInfo(skillLearn.getId(), skillLearn.getLevel());
                    this.addSkill(skill, true);
                    if (this.getAllShortCuts().size() > 0 && skill.getLevel() > 1) {
                        for (ShortCut shortCut : this.getAllShortCuts()) {
                            if (shortCut.getId() != skill.getId() || shortCut.getType() != 2) continue;
                            ShortCut shortCut2 = new ShortCut(shortCut.getSlot(), shortCut.getPage(), shortCut.getType(), shortCut.getId(), skill.getLevel(), 1);
                            this.sendPacket((IStaticPacket)new ShortCutRegister(this, shortCut2));
                            this.registerShortCut(shortCut2);
                        }
                    }
                    bl2 = true;
                    continue;
                }
                if (n <= 0 || skillLearn.getMinLevel() <= n || skillLearn.getLevel() != 1) continue;
                bl3 = true;
            }
        }
        if (bl && bl2) {
            this.sendSkillList();
        }
        this.updateStats();
        if (bl3) {
            this.sendPacket((IStaticPacket)ExNewSkillToLearnByLevelUp.STATIC);
        }
    }

    public Race getRace() {
        return this.getBaseTemplate().race;
    }

    public int getIntSp() {
        return (int)this.getSp();
    }

    public long getSp() {
        return this._activeClass == null ? 0L : this._activeClass.getSp();
    }

    public void setSp(long l) {
        if (this._activeClass != null) {
            this._activeClass.setSp(l);
        }
    }

    public int getClanId() {
        return this.a == null ? 0 : this.a.getClanId();
    }

    public long getLeaveClanTime() {
        return this.bn;
    }

    public long getDeleteClanTime() {
        return this.bo;
    }

    public void setLeaveClanTime(long l) {
        this.bn = l;
    }

    public void setDeleteClanTime(long l) {
        this.bo = l;
    }

    public void setOnlineTime(long l) {
        this.bl = l;
        this.bm = System.currentTimeMillis();
    }

    public long getOnlineBeginTime() {
        return this.bm;
    }

    public long getOnlineTime() {
        return this.bl;
    }

    public long getOnlineCurrentTime() {
        return this.bl + (System.currentTimeMillis() - this.bm);
    }

    public void setNoChannel(long l) {
        this.bp = l;
        if (this.bp > 2145909600000L || this.bp < 0L) {
            this.bp = -1L;
        }
        this.bq = this.bp > 0L ? System.currentTimeMillis() : 0L;
    }

    public long getNoChannel() {
        return this.bp;
    }

    public long getNoChannelRemained() {
        if (this.bp == 0L) {
            return 0L;
        }
        if (this.bp < 0L) {
            return -1L;
        }
        long l = this.bp - System.currentTimeMillis() + this.bq;
        if (l < 0L) {
            return 0L;
        }
        return l;
    }

    public void setAntiSpam(long l, boolean bl) {
        if (l > 0L) {
            this.br = l;
            this.bs = System.currentTimeMillis();
        } else if (l < 0L) {
            this.br = -1L;
            this.bs = System.currentTimeMillis();
        } else {
            this.br = 0L;
            this.bs = 0L;
        }
        if (bl) {
            if (l != 0L) {
                this.setVar(ANTISPAM_VAR, l, -1L);
            } else {
                this.unsetVar(ANTISPAM_VAR);
            }
        }
    }

    public long getAntiSpam() {
        return this.br;
    }

    public long getAntiSpamRemained() {
        if (this.br <= 0L) {
            return this.br == 0L ? 0L : -1L;
        }
        return Math.max(0L, this.br - System.currentTimeMillis() + this.bs);
    }

    public void setLeaveClanCurTime() {
        this.bn = System.currentTimeMillis();
    }

    public void setDeleteClanCurTime() {
        this.bo = System.currentTimeMillis();
    }

    public boolean canJoinClan() {
        if (this.bn == 0L) {
            return true;
        }
        if (System.currentTimeMillis() - this.bn >= Config.CLAN_LEAVE_TIME_PERNALTY) {
            this.bn = 0L;
            return true;
        }
        return false;
    }

    public boolean canCreateClan() {
        if (this.bo == 0L) {
            return true;
        }
        if (System.currentTimeMillis() - this.bo >= Config.NEW_CLAN_CREATE_PENALTY) {
            this.bo = 0L;
            return true;
        }
        return false;
    }

    public IStaticPacket canJoinParty(Player player) {
        Request request = this.getRequest();
        if (request != null && request.isInProgress() && request.getOtherPlayer(this) != player) {
            return SystemMsg.WAITING_FOR_ANOTHER_REPLY.packet(player);
        }
        if (this.isBlockAll() || this.getMessageRefusal()) {
            return SystemMsg.THAT_PERSON_IS_IN_MESSAGE_REFUSAL_MODE.packet(player);
        }
        if (this.isInParty()) {
            return new SystemMessage(SystemMsg.C1_IS_A_MEMBER_OF_ANOTHER_PARTY_AND_CANNOT_BE_INVITED).addName(this);
        }
        if (player.getReflection() != this.getReflection() && player.getReflection() != ReflectionManager.DEFAULT && this.getReflection() != ReflectionManager.DEFAULT) {
            return SystemMsg.INVALID_TARGET.packet(player);
        }
        if (this.isCursedWeaponEquipped() || player.isCursedWeaponEquipped()) {
            return SystemMsg.INVALID_TARGET.packet(player);
        }
        if (player.isOlyParticipant() || this.isOlyParticipant()) {
            return SystemMsg.A_USER_CURRENTLY_PARTICIPATING_IN_THE_OLYMPIAD_CANNOT_SEND_PARTY_AND_FRIEND_INVITATIONS.packet(player);
        }
        if (!player.getPlayerAccess().CanJoinParty || !this.getPlayerAccess().CanJoinParty) {
            return SystemMsg.INVALID_TARGET.packet(player);
        }
        if (this.getTeam() != TeamType.NONE) {
            return SystemMsg.INVALID_TARGET.packet(player);
        }
        if (this.isPartyRefusal() || player.isPartyRefusal()) {
            return SystemMsg.INVALID_TARGET.packet(player);
        }
        return null;
    }

    @Override
    public PcInventory getInventory() {
        return this.a;
    }

    @Override
    public long getWearedMask() {
        return this.a.getWearedMask();
    }

    public PcFreight getFreight() {
        return this.a;
    }

    public void removeItemFromShortCut(int n) {
        this.a.deleteShortCutByObjectId(n);
    }

    public void removeSkillFromShortCut(int n) {
        this.a.deleteShortCutBySkillId(n);
    }

    public boolean isSitting() {
        return this.bU;
    }

    public void setSitting(boolean bl) {
        this.bU = bl;
    }

    public boolean getSittingTask() {
        return this.sittingTaskLaunched;
    }

    @Override
    public void sitDown(StaticObjectInstance staticObjectInstance) {
        if (this.isSitting() || this.sittingTaskLaunched || this.isAlikeDead()) {
            return;
        }
        if (this.isStunned() || this.isSleeping() || this.isParalyzed() || this.isAttackingNow() || this.isCastingNow() || this.isMoving()) {
            this.getAI().setNextAction(NextAction.REST, null, null, false, false);
            return;
        }
        this.resetWaitSitTime();
        this.getAI().setIntention(CtrlIntention.AI_INTENTION_REST, null, null);
        if (staticObjectInstance == null) {
            this.broadcastPacket(new ChangeWaitType(this, 0));
        } else {
            this.broadcastPacket(new ChairSit(this, staticObjectInstance));
        }
        this.a = staticObjectInstance;
        this.setSitting(true);
        this.sittingTaskLaunched = true;
        ThreadPoolManager.getInstance().schedule(new GameObjectTasks.EndSitDownTask(this), 2500L);
    }

    @Override
    public void standUp() {
        if (!this.isSitting() || this.sittingTaskLaunched || this.isInStoreMode() || this.isAlikeDead()) {
            return;
        }
        this.getAI().clearNextAction();
        this.broadcastPacket(new ChangeWaitType(this, 1));
        this.a = null;
        this.sittingTaskLaunched = true;
        ThreadPoolManager.getInstance().schedule(new GameObjectTasks.EndStandUpTask(this), 2500L);
    }

    @Override
    protected Creature.MoveToLocationAction createMoveToLocation(Location location, int n, boolean bl) {
        boolean bl2 = !Config.ALLOW_GEODATA;
        Location location2 = this.getLoc();
        Location location3 = location.clone();
        if (this.isInBoat()) {
            n = (int)((double)n + (location2.distance(location3) - (double)(3 * this.getBoat().getActingRange())));
            bl2 = true;
        }
        if (Config.MOVE_OFFLOAD_MTL_PC) {
            return new MoveToLocationActionForOffload(this, location2, location3, bl2, n, bl);
        }
        return new Creature.MoveToLocationAction(this, location2, location3, bl2, n, bl);
    }

    public void moveBackwardToLocationForPacket(Location location, boolean bl) {
        if (this.isMoving() && Config.MOVE_OFFLOAD_MTL_PC) {
            ((AtomicReference)((Object)this.a)).set(new MoveToLocationOffloadData(location, 0, bl));
            return;
        }
        this.moveToLocation(location, 0, bl);
    }

    public void updateWaitSitTime() {
        if (this.hW < 200) {
            this.hW += 2;
        }
    }

    public int getWaitSitTime() {
        return this.hW;
    }

    public void resetWaitSitTime() {
        this.hW = 0;
    }

    public Warehouse getWarehouse() {
        return this.a;
    }

    public ItemContainer getRefund() {
        return this.a;
    }

    public long getAdena() {
        return this.getInventory().getAdena();
    }

    public boolean reduceAdena(long l) {
        return this.reduceAdena(l, false);
    }

    public boolean reduceAdena(long l, boolean bl) {
        if (l < 0L) {
            return false;
        }
        if (l == 0L) {
            return true;
        }
        boolean bl2 = this.getInventory().reduceAdena(l);
        if (bl && bl2) {
            this.sendPacket((IStaticPacket)SystemMessage.removeItems(57, l));
        }
        return bl2;
    }

    public ItemInstance addAdena(long l) {
        return this.addAdena(l, false);
    }

    public ItemInstance addAdena(long l, boolean bl) {
        if (l < 1L) {
            return null;
        }
        ItemInstance itemInstance = this.getInventory().addAdena(l);
        if (itemInstance != null && bl) {
            this.sendPacket((IStaticPacket)SystemMessage.obtainItems(57, l, 0));
        }
        return itemInstance;
    }

    public GameClient getNetConnection() {
        return this.a;
    }

    public int getRevision() {
        return this.a == null ? 0 : this.a.getRevision();
    }

    public void setNetConnection(GameClient gameClient) {
        if (gameClient == null) {
            this.getFarmSystem().stopFarmTask();
        }
        this.a = gameClient;
    }

    public boolean isConnected() {
        return this.a != null && this.a.isConnected();
    }

    @Override
    public void onAction(Player player, boolean bl) {
        if (this.isFrozen()) {
            player.sendActionFailed();
            return;
        }
        if (Events.onAction(player, this, bl)) {
            player.sendActionFailed();
            return;
        }
        if (player.getTarget() != this) {
            player.setTarget(this);
            if (player.getTarget() == this) {
                player.sendPacket(new MyTargetSelected(this.getObjectId(), 0), new ExAbnormalStatusUpdateFromTarget(this, true), this.makeStatusUpdate(9, 10, 11, 12));
            } else {
                player.sendActionFailed();
            }
        } else if (this.getPrivateStoreType() != 0) {
            if (this.getRealDistance(player) > (double)this.getActingRange() && player.getAI().getIntention() != CtrlIntention.AI_INTENTION_INTERACT) {
                if (!bl) {
                    player.getAI().setIntention(CtrlIntention.AI_INTENTION_INTERACT, this);
                } else {
                    player.sendActionFailed();
                }
            } else {
                player.doInteract(this);
            }
        } else if (this.isAutoAttackable(player)) {
            player.getAI().Attack(this, false, bl);
        } else if (player != this) {
            if (player.getAI().getIntention() != CtrlIntention.AI_INTENTION_FOLLOW || player.getFollowTarget() != this) {
                if (!bl) {
                    player.getAI().setIntention(CtrlIntention.AI_INTENTION_FOLLOW, this);
                } else {
                    player.sendActionFailed();
                }
            } else {
                player.sendActionFailed();
            }
        } else {
            player.sendActionFailed();
        }
    }

    @Override
    public void broadcastStatusUpdate() {
        DuelEvent duelEvent;
        if (!this.needStatusUpdate()) {
            return;
        }
        StatusUpdate statusUpdate = this.makeStatusUpdate(10, 12, 34, 9, 11, 33);
        this.sendPacket((IStaticPacket)statusUpdate.setAttackerObject(this));
        if (this.isInParty()) {
            this.getParty().broadcastToPartyMembers(this, new PartySmallWindowUpdate(this));
        }
        if ((duelEvent = this.getEvent(DuelEvent.class)) != null) {
            duelEvent.sendPacket(new ExDuelUpdateUserInfo(this), this.getTeam().revert().name());
        }
        if (this.isOlyCompetitionStarted()) {
            this.broadcastPacket(new ExOlympiadUserInfo(this));
        }
    }

    @Override
    public void broadcastCharInfo() {
        this.broadcastUserInfo(false, new UserInfoType[0]);
    }

    public void broadcastUserInfo(boolean bl, UserInfoType ... userInfoTypeArray) {
        this.sendUserInfo(bl, userInfoTypeArray != null && userInfoTypeArray.length > 0 ? userInfoTypeArray : UserInfoType.VALUES);
        if (!this.isVisible() || this.isInvisible()) {
            return;
        }
        if (Config.BROADCAST_CHAR_INFO_INTERVAL == 0L) {
            bl = true;
        }
        if (bl) {
            if (this.R != null) {
                this.R.cancel(false);
                this.R = null;
            }
            this.broadcastCharInfoImpl();
            return;
        }
        if (this.R != null) {
            return;
        }
        this.R = ThreadPoolManager.getInstance().schedule(new BroadcastCharInfoTask(), Config.BROADCAST_CHAR_INFO_INTERVAL);
    }

    public void setPolyId(int n) {
        this.iE = n;
        this.teleToLocation(this.getLoc());
        this.broadcastUserInfo(true, new UserInfoType[0]);
    }

    public boolean isPolymorphed() {
        return this.iE != 0;
    }

    public int getPolyId() {
        return this.iE;
    }

    private void broadcastCharInfoImpl() {
        if (!this.isVisible() || this.isInvisible()) {
            return;
        }
        L2GameServerPacket l2GameServerPacket = this.isPolymorphed() ? new NpcInfo(this) : new CharInfo(this);
        for (Player player : World.getAroundPlayers(this)) {
            player.sendPacket((IStaticPacket)l2GameServerPacket);
            player.sendPacket((IStaticPacket)new RelationChanged().add(this, player));
        }
    }

    public void setLastNpcInteractionTime() {
        this.bu = System.currentTimeMillis();
    }

    public boolean canMoveAfterInteraction() {
        return this.bu + 1000L < System.currentTimeMillis();
    }

    public void broadcastRelation() {
        if (!this.isVisible() || this.isInvisible()) {
            return;
        }
        for (Player player : World.getAroundPlayers(this)) {
            RelationChanged relationChanged = new RelationChanged();
            relationChanged.add(this, player);
            player.sendPacket((IStaticPacket)relationChanged);
        }
    }

    public void sendEtcStatusUpdate() {
        if (!this.isVisible()) {
            return;
        }
        this.sendPacket((IStaticPacket)new EtcStatusUpdate(this));
    }

    private void bk() {
        this.a(UserInfoType.VALUES);
    }

    private void a(UserInfoType ... userInfoTypeArray) {
        this.sendPacket((IStaticPacket)new UserInfo(this, userInfoTypeArray));
    }

    public void sendUserInfo(boolean bl) {
        if (!this.isVisible() || this.entering || this.isLogoutStarted()) {
            return;
        }
        if (Config.USER_INFO_INTERVAL == 0L || bl) {
            if (this.v != null) {
                this.v.cancel(false);
                this.v = (double)null;
            }
            this.bk();
            return;
        }
        if (this.v != null) {
            return;
        }
        this.v = (double)ThreadPoolManager.getInstance().schedule(new UserInfoTask(), Config.USER_INFO_INTERVAL);
    }

    public void sendUserInfo(boolean bl, UserInfoType ... userInfoTypeArray) {
        if (!this.isVisible() || this.entering || this.isLogoutStarted()) {
            return;
        }
        if (Config.USER_INFO_INTERVAL == 0L || bl) {
            if (this.v != null) {
                this.v.cancel(false);
                this.v = (double)null;
            }
            this.a(userInfoTypeArray);
            return;
        }
        if (this.v != null) {
            return;
        }
        this.v = (double)ThreadPoolManager.getInstance().schedule(new UserInfoTask(), Config.USER_INFO_INTERVAL);
    }

    @Override
    public StatusUpdate makeStatusUpdate(int ... nArray) {
        StatusUpdate statusUpdate = new StatusUpdate(this);
        block12: for (int n : nArray) {
            switch (n) {
                case 9: {
                    statusUpdate.addAttribute(n, (int)this.getCurrentHp());
                    continue block12;
                }
                case 10: {
                    statusUpdate.addAttribute(n, this.getMaxHp());
                    continue block12;
                }
                case 11: {
                    statusUpdate.addAttribute(n, (int)this.getCurrentMp());
                    continue block12;
                }
                case 12: {
                    statusUpdate.addAttribute(n, this.getMaxMp());
                    continue block12;
                }
                case 14: {
                    statusUpdate.addAttribute(n, this.getCurrentLoad());
                    continue block12;
                }
                case 15: {
                    statusUpdate.addAttribute(n, this.getMaxLoad());
                    continue block12;
                }
                case 26: {
                    statusUpdate.addAttribute(n, this._pvpFlag);
                    continue block12;
                }
                case 27: {
                    statusUpdate.addAttribute(n, -this.getKarma());
                    continue block12;
                }
                case 33: {
                    statusUpdate.addAttribute(n, (int)this.getCurrentCp());
                    continue block12;
                }
                case 34: {
                    statusUpdate.addAttribute(n, this.getMaxCp());
                }
            }
        }
        return statusUpdate;
    }

    public void sendStatusUpdate(boolean bl, boolean bl2, int ... nArray) {
        if (nArray.length == 0 || this.entering && !bl) {
            return;
        }
        StatusUpdate statusUpdate = this.makeStatusUpdate(nArray);
        if (!statusUpdate.hasAttributes()) {
            return;
        }
        ArrayList<L2GameServerPacket> arrayList = new ArrayList<L2GameServerPacket>(bl2 ? 2 : 1);
        if (bl2 && this.getPet() != null) {
            arrayList.add(this.getPet().makeStatusUpdate(nArray));
        }
        arrayList.add(statusUpdate);
        if (!bl) {
            this.sendPacket(arrayList);
        } else if (this.entering) {
            this.broadcastPacketToOthers(arrayList);
        } else {
            this.broadcastPacket(arrayList);
        }
    }

    public int getAllyId() {
        return this.a == null ? 0 : this.a.getAllyId();
    }

    @Override
    public void sendPacket(IStaticPacket iStaticPacket) {
        if (!this.isConnected()) {
            return;
        }
        L2GameServerPacket l2GameServerPacket = iStaticPacket.packet(this);
        if (this.a(l2GameServerPacket)) {
            return;
        }
        this.a.sendPacket(l2GameServerPacket);
    }

    @Override
    public void sendPacket(IStaticPacket ... iStaticPacketArray) {
        if (!this.isConnected()) {
            return;
        }
        for (IStaticPacket iStaticPacket : iStaticPacketArray) {
            L2GameServerPacket l2GameServerPacket = iStaticPacket.packet(this);
            if (this.a(l2GameServerPacket)) continue;
            this.a.sendPacket(l2GameServerPacket);
        }
    }

    private boolean a(IStaticPacket iStaticPacket) {
        return iStaticPacket == null;
    }

    @Override
    public void sendPacket(List<? extends IStaticPacket> list) {
        if (!this.isConnected()) {
            return;
        }
        for (IStaticPacket iStaticPacket : list) {
            L2GameServerPacket l2GameServerPacket = iStaticPacket.packet(this);
            if (this.a(l2GameServerPacket)) continue;
            this.a.sendPacket(l2GameServerPacket);
        }
    }

    public void doInteract(GameObject gameObject) {
        if (gameObject == null || this.isActionsDisabled()) {
            this.sendActionFailed();
            return;
        }
        if (gameObject.isPlayer()) {
            Player player = (Player)gameObject;
            if (this.getRealDistance(gameObject) <= (double)gameObject.getActingRange()) {
                switch (player.getPrivateStoreType()) {
                    case 1: 
                    case 8: {
                        if (this.isSellingBuffs()) {
                            SellBuffsManager.getInstance().sendBuffMenu(this, player, 0);
                            break;
                        }
                        if (player.isSellingBuffs()) {
                            SellBuffsManager.getInstance().sendBuffMenu(this, player, 0);
                            break;
                        }
                        this.sendPacket((IStaticPacket)new PrivateStoreListSell(this, player));
                        break;
                    }
                    case 3: {
                        this.sendPacket((IStaticPacket)new PrivateStoreListBuy(this, player));
                        break;
                    }
                    case 5: {
                        this.sendPacket((IStaticPacket)new RecipeShopSellList(this, player));
                    }
                }
                this.sendActionFailed();
            } else if (!this.getAI().isIntendingInteract(player)) {
                this.getAI().setIntention(CtrlIntention.AI_INTENTION_INTERACT, player);
            }
        } else {
            gameObject.onAction(this, false);
        }
    }

    public void doAutoLootOrDrop(ItemInstance itemInstance, NpcInstance npcInstance) {
        boolean bl;
        boolean bl2 = bl = npcInstance.isFlying() || this.getReflection().isAutolootForced();
        if (Config.DISABLE_AUTO_LOOT_FOR_MONSTER_IDS.contains(npcInstance.getNpcId()) || (npcInstance.isRaid() || npcInstance instanceof ReflectionBossInstance) && !Config.AUTO_LOOT_FROM_RAIDS && !itemInstance.isHerb() && !bl) {
            itemInstance.dropToTheGround(this, npcInstance);
            return;
        }
        if (itemInstance.isHerb()) {
            if (!(this.bM || bl || this.getAutoLootHerbStat())) {
                itemInstance.dropToTheGround(this, npcInstance);
                return;
            }
            Skill[] skillArray = itemInstance.getTemplate().getAttachedSkills();
            if (skillArray.length > 0) {
                for (Skill skill : skillArray) {
                    this.altUseSkill(skill, this);
                    if (this.getPet() == null || !this.getPet().isSummon() || this.getPet().isDead()) continue;
                    this.getPet().altUseSkill(skill, this.getPet());
                }
            }
            itemInstance.deleteMe();
            return;
        }
        if (!bl && ArrayUtils.contains((int[])Config.AUTO_LOOT_EXCLUDE_ITEM_IDS, (int)itemInstance.getItemId())) {
            itemInstance.dropToTheGround(this, npcInstance);
            return;
        }
        if (bl || this.bL || this.getAutoLootStat() || ArrayUtils.contains((int[])Config.AUTO_LOOT_MONEY_ITEM_IDS, (int)itemInstance.getItemId()) && (this.bN || this.getAutoLootAdenaStat())) {
            if (!this.isInParty()) {
                if (!this.pickupItem(itemInstance, Log.ItemLog.Pickup)) {
                    itemInstance.dropToTheGround(this, npcInstance);
                    return;
                }
            } else {
                this.getParty().distributeItem(this, itemInstance, npcInstance);
            }
            this.broadcastPickUpMsg(itemInstance);
            return;
        }
        itemInstance.dropToTheGround(this, npcInstance);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void doPickupItem(GameObject gameObject) {
        GameObject gameObject2;
        if (!gameObject.isItem()) {
            bF.warn("trying to pickup wrong target." + this.getTarget());
            return;
        }
        this.sendActionFailed();
        this.stopMove();
        Request request = this.getRequest();
        if (request != null && request.isInProgress()) {
            if (request.isTypeOf(Request.L2RequestType.TRADE)) {
                gameObject2 = request.getOtherPlayer(this);
                this.sendPacket((IStaticPacket)SendTradeDone.FAIL);
                ((Player)gameObject2).sendPacket((IStaticPacket)SendTradeDone.FAIL);
                this.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_CANNOT_PICK_UP_OR_USE_ITEMS_WHILE_TRADING));
            }
            request.cancel();
        }
        gameObject2 = (ItemInstance)gameObject;
        ItemInstance itemInstance = gameObject2;
        synchronized (itemInstance) {
            FlagItemAttachment flagItemAttachment;
            if (!gameObject2.isVisible()) {
                return;
            }
            if (!ItemFunctions.checkIfCanPickup(this, (ItemInstance)gameObject2)) {
                SystemMessage systemMessage;
                if (((ItemInstance)gameObject2).getItemId() == 57) {
                    systemMessage = new SystemMessage(SystemMsg.YOU_HAVE_FAILED_TO_PICK_UP_S1_ADENA);
                    systemMessage.addNumber(((ItemInstance)gameObject2).getCount());
                } else {
                    systemMessage = new SystemMessage(SystemMsg.YOU_HAVE_FAILED_TO_PICK_UP_S1);
                    systemMessage.addItemName(((ItemInstance)gameObject2).getItemId());
                }
                this.sendPacket((IStaticPacket)systemMessage);
                return;
            }
            if (((ItemInstance)gameObject2).isHerb()) {
                Skill[] skillArray = ((ItemInstance)gameObject2).getTemplate().getAttachedSkills();
                if (skillArray.length > 0) {
                    for (Skill skill : skillArray) {
                        this.altUseSkill(skill, this);
                        if (this.getPet() == null || !this.getPet().isSummon() || this.getPet().isDead()) continue;
                        this.getPet().altUseSkill(skill, this.getPet());
                    }
                }
                this.broadcastPacket(new GetItem((ItemInstance)gameObject2, this.getObjectId()));
                gameObject2.deleteMe();
                return;
            }
            FlagItemAttachment flagItemAttachment2 = flagItemAttachment = ((ItemInstance)gameObject2).getAttachment() instanceof FlagItemAttachment ? (FlagItemAttachment)((ItemInstance)gameObject2).getAttachment() : null;
            if (!this.isInParty() || flagItemAttachment != null) {
                if (this.pickupItem((ItemInstance)gameObject2, Log.ItemLog.Pickup)) {
                    this.broadcastPacket(new GetItem((ItemInstance)gameObject2, this.getObjectId()));
                    this.broadcastPickUpMsg((ItemInstance)gameObject2);
                    ((ItemInstance)gameObject2).pickupMe();
                }
            } else {
                this.getParty().distributeItem(this, (ItemInstance)gameObject2, null);
            }
        }
    }

    public boolean pickupItem(ItemInstance itemInstance, Log.ItemLog itemLog) {
        PickableAttachment pickableAttachment;
        PickableAttachment pickableAttachment2 = pickableAttachment = itemInstance.getAttachment() instanceof PickableAttachment ? (PickableAttachment)itemInstance.getAttachment() : null;
        if (!ItemFunctions.canAddItem(this, itemInstance)) {
            return false;
        }
        Log.LogItem(this, itemLog, itemInstance);
        this.sendPacket((IStaticPacket)SystemMessage.obtainItems(itemInstance));
        this.getInventory().addItem(itemInstance);
        this.getListeners().onItemPickup(itemInstance);
        if (pickableAttachment != null) {
            pickableAttachment.pickUp(this);
        }
        this.sendChanges();
        return true;
    }

    public void setObjectTarget(GameObject gameObject) {
        this.setTarget(gameObject);
        if (gameObject == null) {
            return;
        }
        if (gameObject == this.getTarget()) {
            if (gameObject.isNpc()) {
                NpcInstance npcInstance = (NpcInstance)gameObject;
                this.sendPacket((IStaticPacket)new MyTargetSelected(npcInstance.getObjectId(), this.getLevel() - npcInstance.getLevel()));
                this.sendPacket((IStaticPacket)npcInstance.makeStatusUpdate(9, 10));
                this.sendPacket(new ValidateLocation(npcInstance), ActionFail.STATIC);
            } else {
                this.sendPacket((IStaticPacket)new MyTargetSelected(gameObject.getObjectId(), 0));
            }
        }
    }

    @Override
    public void setTarget(GameObject gameObject) {
        GameObject gameObject2;
        Party party;
        if (gameObject != null && !gameObject.isVisible()) {
            gameObject = null;
        }
        if (gameObject instanceof FestivalMonsterInstance && !this.isFestivalParticipant()) {
            gameObject = null;
        }
        if ((party = this.getParty()) != null && party.isInDimensionalRift()) {
            int n = party.getDimensionalRift().getType();
            int n2 = party.getDimensionalRift().getCurrentRoom();
            if (gameObject != null && !DimensionalRiftManager.getInstance().getRoom(n, n2).checkIfInZone(gameObject.getX(), gameObject.getY(), gameObject.getZ())) {
                gameObject = null;
            }
        }
        if ((gameObject2 = this.getTarget()) != null) {
            if (gameObject2.equals(gameObject)) {
                return;
            }
            if (gameObject2.isCreature()) {
                ((Creature)gameObject2).removeStatusListener(this);
            }
            this.broadcastPacket(new TargetUnselected(this));
        }
        if (gameObject != null) {
            if (gameObject.isCreature()) {
                ((Creature)gameObject).addStatusListener(this);
            }
            this.broadcastPacketToOthers(new TargetSelected(this.getObjectId(), gameObject.getObjectId(), this.getLoc()));
        }
        super.setTarget(gameObject);
    }

    @Override
    public ItemInstance getActiveWeaponInstance() {
        return this.getInventory().getPaperdollItem(5);
    }

    @Override
    public WeaponTemplate getActiveWeaponItem() {
        ItemInstance itemInstance = this.getActiveWeaponInstance();
        if (itemInstance == null) {
            return this.getFistsWeaponItem();
        }
        return (WeaponTemplate)itemInstance.getTemplate();
    }

    @Override
    public ItemInstance getSecondaryWeaponInstance() {
        return this.getInventory().getPaperdollItem(7);
    }

    @Override
    public WeaponTemplate getSecondaryWeaponItem() {
        ItemInstance itemInstance = this.getSecondaryWeaponInstance();
        if (itemInstance == null) {
            return this.getFistsWeaponItem();
        }
        ItemTemplate itemTemplate = itemInstance.getTemplate();
        if (itemTemplate instanceof WeaponTemplate) {
            return (WeaponTemplate)itemTemplate;
        }
        return null;
    }

    public boolean isWearingArmor(ArmorTemplate.ArmorType armorType) {
        ItemInstance itemInstance = this.getInventory().getPaperdollItem(6);
        if (itemInstance == null) {
            return armorType == ArmorTemplate.ArmorType.NONE;
        }
        if (itemInstance.getItemType() != armorType) {
            return false;
        }
        if (itemInstance.getBodyPart() == 32768L) {
            return true;
        }
        if (itemInstance.getBodyPart() == 131072L) {
            return true;
        }
        ItemInstance itemInstance2 = this.getInventory().getPaperdollItem(11);
        return itemInstance2 == null ? armorType == ArmorTemplate.ArmorType.NONE : itemInstance2.getItemType() == armorType;
    }

    @Override
    public void reduceCurrentHp(double d, Creature creature, Skill skill, boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5, boolean bl6, boolean bl7) {
        if (creature == null || this.isDead() || creature.isDead() && !bl6) {
            return;
        }
        if (creature.isPlayer() && Math.abs(creature.getLevel() - this.getLevel()) > 10) {
            if (creature.getKarma() > 0 && this.getEffectList().getEffectsBySkillId(5182) != null && !this.isInZone(Zone.ZoneType.SIEGE)) {
                return;
            }
            if (this.getKarma() > 0 && creature.getEffectList().getEffectsBySkillId(5182) != null && !creature.isInZone(Zone.ZoneType.SIEGE)) {
                return;
            }
        }
        super.reduceCurrentHp(d, creature, skill, bl, bl2, bl3, bl4, bl5, bl6, bl7);
    }

    @Override
    protected void onReduceCurrentHp(double d, Creature creature, Skill skill, boolean bl, boolean bl2, boolean bl3) {
        DuelEvent duelEvent;
        double d2;
        double d3 = d;
        if (bl2) {
            this.standUp();
            if (this.isFakeDeath()) {
                this.breakFakeDeath();
            }
        }
        if (creature.isPlayable() && !bl3 && this.getCurrentCp() > 0.0) {
            d2 = this.getCurrentCp();
            if (d2 >= d) {
                d2 -= d;
                d = 0.0;
            } else {
                d -= d2;
                d2 = 0.0;
            }
            this.setCurrentCp(d2);
        }
        d2 = this.getCurrentHp();
        if (this.isOlyParticipant()) {
            if (this.isOlyCompetitionStarted() && !this.getOlyParticipant().OnDamaged(this, creature, d, d2, d3)) {
                return;
            }
            if (!this.getOlyParticipant().isAlive()) {
                return;
            }
        }
        if ((duelEvent = this.getEvent(DuelEvent.class)) != null && d2 - d <= 1.0) {
            this.setCurrentHp(1.0, false);
            duelEvent.onDie(this);
            return;
        }
        super.onReduceCurrentHp(d, creature, skill, bl, bl2, bl3);
    }

    @Override
    public boolean isAlikeDead() {
        return this.hV == 1 || super.isAlikeDead();
    }

    @Override
    public boolean isMovementDisabled() {
        return this.isFakeDeath() || super.isMovementDisabled();
    }

    @Override
    public boolean isActionsDisabled() {
        return this.isFakeDeath() || super.isActionsDisabled();
    }

    @Override
    public void doAttack(Creature creature) {
        if (this.isFakeDeath() || this.isInMountTransform()) {
            return;
        }
        super.doAttack(creature);
    }

    @Override
    public void onHitTimer(Creature creature, int n, boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5) {
        if (this.isFakeDeath()) {
            this.sendActionFailed();
            return;
        }
        super.onHitTimer(creature, n, bl, bl2, bl3, bl4, bl5);
    }

    public boolean isFakeDeath() {
        return this.hV != 0;
    }

    public void setFakeDeath(int n) {
        this.hV = n;
    }

    public void breakFakeDeath() {
        this.getEffectList().stopAllSkillEffects(EffectType.FakeDeath);
    }

    private void g(Creature creature) {
        if (!Config.ALT_GAME_DELEVEL) {
            return;
        }
        if (this.isInZoneBattle() || this.isInZone(Zone.ZoneType.fun)) {
            return;
        }
        this.deathPenalty(creature);
    }

    public final boolean atWarWith(Player player) {
        return this.a != null && player.getClan() != null && this.getPledgeType() != -1 && player.getPledgeType() != -1 && this.a.isAtWarWith(player.getClan().getClanId());
    }

    public boolean atMutualWarWith(Player player) {
        return this.a != null && player.getClan() != null && this.getPledgeType() != -1 && player.getPledgeType() != -1 && this.a.isAtWarWith(player.getClan().getClanId()) && player.getClan().isAtWarWith(this.a.getClanId());
    }

    @Override
    public void doPurePk(Player player) {
        block4: {
            super.doPurePk(player);
            player.setPkKills(player.getPkKills() + 1);
            player.getInventory().validateItems();
            if (Config.SERVICES_PK_ANNOUNCE <= 0) break block4;
            if (Config.SERVICES_PK_ANNOUNCE == 1) {
                for (Player player2 : GameObjectsStorage.getAllPlayersForIterate()) {
                    if (!player2.getVarB("PvPAnnounce")) continue;
                    Announcements.getInstance().announceToPlayerByCustomMessage(player2, "player.pkannounce", new String[]{player.getName(), this.getName()}, ChatType.ANNOUNCEMENT);
                }
            } else {
                String string = new CustomMessage("service.pk_kill_announce", this, this.getName()).addString(player.getName()).toString();
                for (Player player3 : GameObjectsStorage.getAllPlayersForIterate()) {
                    if (!player3.getVarB("PvPAnnounce")) continue;
                    player3.sendPacket((IStaticPacket)((SystemMessage)new SystemMessage(SystemMsg.S2_S1).addString(string)).addZoneName(player.getLoc()));
                }
            }
        }
    }

    private final void b(Player player, boolean bl) {
        if (bl) {
            this.doPurePk(player);
            player.getListeners().onPvpPkKill(this, true);
        } else {
            if (Config.PVP_INCREASE_SAME_IP_CHECK && StringUtils.isNotEmpty((CharSequence)this.getIP()) && Objects.equals(this.getIP(), player.getIP())) {
                return;
            }
            if (Config.PVP_INCREASE_SAME_HWID_CHECK && !Objects.isNull(this.getNetConnection()) && !Objects.isNull(player.getNetConnection()) && StringUtils.isNotEmpty((CharSequence)this.getNetConnection().getHwid()) && Objects.equals(this.getNetConnection().getHwid(), player.getNetConnection().getHwid())) {
                return;
            }
            player.setPvpKills(player.getPvpKills() + Config.PVP_POINTS_AMOUNT_ADD);
            player.getListeners().onPvpPkKill(this, false);
            if (Config.SERVICES_PK_ANNOUNCE > 0) {
                if (Config.SERVICES_PK_ANNOUNCE == 1) {
                    for (Player object : GameObjectsStorage.getAllPlayersForIterate()) {
                        if (!object.getVarB("PvPAnnounce")) continue;
                        Announcements.getInstance().announceToPlayerByCustomMessage(object, "service.pvp_pk_kill_announce", new String[]{player.getName(), this.getName()}, ChatType.ANNOUNCEMENT);
                    }
                } else {
                    String string = new CustomMessage("service.pvp_pk_kill_announce", this, this.getName()).addString(player.getName()).toString();
                    for (Player player2 : GameObjectsStorage.getAllPlayersForIterate()) {
                        if (!player2.getVarB("PvPAnnounce")) continue;
                        player2.sendPacket((IStaticPacket)((SystemMessage)new SystemMessage(SystemMsg.S2_S1).addString(string)).addZoneName(player.getLoc()));
                    }
                }
            }
        }
        if (Config.SERVICES_PK_KILL_BONUS_ENABLE || Config.SERVICES_PVP_KILL_BONUS_ENABLE) {
            boolean bl2;
            boolean bl22 = true;
            boolean bl3 = true;
            if (Config.SERVICES_PK_PVP_BONUS_TIE_IF_SAME_IP) {
                boolean bl4 = bl22 = this.getIP() == null && player.getIP() != null || this.getIP() != null && !this.getIP().equals(player.getIP());
            }
            if (Config.SERVICES_PK_PVP_BONUS_TIE_IF_SAME_HWID) {
                String string = this.getNetConnection() != null ? this.getNetConnection().getHwid() : null;
                String string2 = player.getNetConnection() != null ? player.getNetConnection().getHwid() : null;
                bl2 = string == null && string2 == null || string != null && !string.equals(string2);
            }
            long l = System.currentTimeMillis();
            long l2 = player.getVarLong(LAST_PVP_PK_KILL_VAR_NAME, 0L);
            if (this.isConnected() && bl22 && bl2 && l - l2 > Config.SERVICES_PK_KILL_BONUS_INTERVAL) {
                if (bl) {
                    if (Config.SERVICES_PK_KILL_BONUS_REWARD_ITEM != null && Config.SERVICES_PK_KILL_BONUS_REWARD_ITEM.length > 0) {
                        for (int i = 0; i < Config.SERVICES_PK_KILL_BONUS_REWARD_ITEM.length; ++i) {
                            if (Config.SERVICES_PK_KILL_BONUS_REWARD_ITEM[i] <= 0 || !Rnd.chance(Config.SERVICES_PK_KILL_BONUS_REWARD_CHANCE[i])) continue;
                            ItemFunctions.addItem((Playable)player, Config.SERVICES_PK_KILL_BONUS_REWARD_ITEM[i], (long)Config.SERVICES_PK_KILL_BONUS_REWARD_COUNT[i], true);
                        }
                    }
                } else if (Config.SERVICES_PVP_KILL_BONUS_REWARD_ITEM != null && Config.SERVICES_PVP_KILL_BONUS_REWARD_ITEM.length > 0) {
                    for (int i = 0; i < Config.SERVICES_PVP_KILL_BONUS_REWARD_ITEM.length; ++i) {
                        if (Config.SERVICES_PVP_KILL_BONUS_REWARD_ITEM[i] <= 0 || !Rnd.chance(Config.SERVICES_PVP_KILL_BONUS_REWARD_CHANCE[i])) continue;
                        ItemFunctions.addItem((Playable)player, Config.SERVICES_PVP_KILL_BONUS_REWARD_ITEM[i], (long)Config.SERVICES_PVP_KILL_BONUS_REWARD_COUNT[i], true);
                    }
                }
                player.setVar(LAST_PVP_PK_KILL_VAR_NAME, l, -1L);
            }
        }
    }

    public void checkAddItemToDrop(List<ItemInstance> list, List<ItemInstance> list2, int n) {
        for (int i = 0; i < n && !list2.isEmpty(); ++i) {
            list.add(list2.remove(Rnd.get(list2.size())));
        }
    }

    public FlagItemAttachment getActiveWeaponFlagAttachment() {
        ItemInstance itemInstance = this.getActiveWeaponInstance();
        if (itemInstance == null || !(itemInstance.getAttachment() instanceof FlagItemAttachment)) {
            return null;
        }
        return (FlagItemAttachment)itemInstance.getAttachment();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void doPKPVPManage(Creature creature) {
        int n;
        int n2;
        FlagItemAttachment flagItemAttachment = this.getActiveWeaponFlagAttachment();
        if (flagItemAttachment != null) {
            flagItemAttachment.onDeath(this, creature);
        }
        if (creature == null || creature == this.a || creature == this) {
            return;
        }
        if ((this.isInZoneBattle() || creature.isInZoneBattle()) && !Config.BATTLE_ZONE_PVP_COUNT) {
            return;
        }
        boolean bl = this.isInZone(Zone.ZoneType.fun);
        if (!Config.FUN_ZONE_PVP_COUNT && bl) {
            return;
        }
        if (creature instanceof Summon && (creature = creature.getPlayer()) == null) {
            return;
        }
        if (creature.isPlayer()) {
            Player player = (Player)creature;
            n2 = this.getLevel() - player.getLevel() >= 20 ? Config.CRP_REWARD_ON_WAR_KILL_OVER_LEVEL : Config.CRP_REWARD_ON_WAR_KILL;
            n = this.atMutualWarWith(player);
            if (n != 0 && player.getClan().getReputationScore() > 0 && this.a.getLevel() >= Config.MIN_CLAN_LEVEL_FOR_DECLARED_WAR && this.a.getReputationScore() > 0 && player.getClan().getLevel() >= Config.MIN_CLAN_LEVEL_FOR_DECLARED_WAR) {
                this.a.broadcastToOnlineMembers(new L2GameServerPacket[]{((SystemMessage)new SystemMessage(SystemMsg.BECAUSE_C1_WAS_KILLED_BY_A_CLAN_MEMBER_OF_S2_CLAN_REPUTATION_DECREASED_BY_1).addString(this.getName())).addString(player.getClan().getName())});
                this.a.incReputation(-n2, true, "ClanWar");
                player.getClan().broadcastToOnlineMembers(new L2GameServerPacket[]{((SystemMessage)new SystemMessage(SystemMsg.BECAUSE_A_CLAN_MEMBER_OF_S1_WAS_KILLED_BY_C2_CLAN_REPUTATION_INCREASED_BY_1).addString(this.getClan().getName())).addString(player.getName())});
                player.getClan().incReputation(n2, true, "ClanWar");
                if (Config.EVENT_CLAN_WAR_POINTS > 0) {
                    player.getClan().setCustomPoints(player.getClan().getCustomPoints() + Config.EVENT_CLAN_WAR_POINTS);
                }
            }
            if (!Config.SIEGE_ZONE_PVP_COUNT && this.isOnSiegeField()) {
                return;
            }
            if (Config.FUN_ZONE_PVP_COUNT && bl || Config.BATTLE_ZONE_PVP_COUNT && (this.isInZoneBattle() || creature.isInZoneBattle()) || Config.SIEGE_ZONE_PVP_COUNT && this.isOnSiegeField()) {
                this.b(player, false);
                player.sendChanges();
                return;
            }
            if (this._pvpFlag > 0 || n != 0) {
                this.b(player, false);
            } else {
                this.b(player, this.gh <= 0);
            }
            player.sendChanges();
        }
        int n3 = this.gh;
        int n4 = n2 = creature.isPlayable() || creature instanceof GuardInstance ? 1 : 0;
        if (creature.isMonster() && !Config.DROP_ITEMS_ON_DIE || n2 != 0 && (this.hP < Config.MIN_PK_TO_ITEMS_DROP || n3 == 0 && Config.KARMA_NEEDED_TO_DROP) || this.isFestivalParticipant() || !creature.isMonster() && n2 == 0) {
            return;
        }
        if (!Config.KARMA_DROP_GM && this.isGM()) {
            return;
        }
        if (Config.ITEM_ANTIDROP_FROM_PK > 0 && this.getInventory().getItemByItemId(Config.ITEM_ANTIDROP_FROM_PK) != null) {
            return;
        }
        n = n2 != 0 ? Config.KARMA_DROP_ITEM_LIMIT : 1;
        double d = n2 != 0 ? (double)this.hP * Config.KARMA_DROPCHANCE_MOD + Config.KARMA_DROPCHANCE_BASE : Config.NORMAL_DROPCHANCE_BASE;
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        for (int i = 0; (double)i < Math.ceil(d / 100.0) && i < n; ++i) {
            if (!Rnd.chance(d)) continue;
            int n8 = Rnd.get(Config.DROPCHANCE_EQUIPPED_WEAPON + Config.DROPCHANCE_EQUIPMENT + Config.DROPCHANCE_ITEM) + 1;
            if (n8 > Config.DROPCHANCE_EQUIPPED_WEAPON + Config.DROPCHANCE_EQUIPMENT) {
                ++n7;
                continue;
            }
            if (n8 > Config.DROPCHANCE_EQUIPPED_WEAPON) {
                ++n5;
                continue;
            }
            ++n6;
        }
        LazyArrayList<ItemInstance> lazyArrayList = new LazyArrayList<ItemInstance>();
        LazyArrayList<ItemInstance> lazyArrayList2 = new LazyArrayList<ItemInstance>();
        LazyArrayList<ItemInstance> lazyArrayList3 = new LazyArrayList<ItemInstance>();
        LazyArrayList<ItemInstance> lazyArrayList4 = new LazyArrayList<ItemInstance>();
        this.getInventory().writeLock();
        try {
            for (ItemInstance itemInstance : this.getInventory().getItems()) {
                if (!itemInstance.canBeDropped(this, true) || Config.KARMA_LIST_NONDROPPABLE_ITEMS.contains(itemInstance.getItemId())) continue;
                if (itemInstance.getTemplate().getType2() == 0) {
                    lazyArrayList4.add(itemInstance);
                    continue;
                }
                if (itemInstance.getTemplate().getType2() == 1 || itemInstance.getTemplate().getType2() == 2) {
                    lazyArrayList3.add(itemInstance);
                    continue;
                }
                if (itemInstance.getTemplate().getType2() != 5) continue;
                lazyArrayList2.add(itemInstance);
            }
            this.checkAddItemToDrop(lazyArrayList, lazyArrayList4, n6);
            this.checkAddItemToDrop(lazyArrayList, lazyArrayList3, n5);
            this.checkAddItemToDrop(lazyArrayList, lazyArrayList2, n7);
            if (lazyArrayList.isEmpty()) {
                return;
            }
            for (ItemInstance itemInstance : lazyArrayList) {
                if ((itemInstance.isAugmented() || itemInstance.isEnsouled()) && !Config.ALT_ALLOW_DROP_AUGMENTED) {
                    itemInstance.setVariationStat1(0);
                    itemInstance.setVariationStat2(0);
                    itemInstance.setEnsoulSlotN1(0);
                    itemInstance.setEnsoulSlotN2(0);
                    itemInstance.setEnsoulSlotBm(0);
                }
                if (itemInstance.getVisibleItemId() > 0 && !Config.ALT_ALLOW_DROP_APPAREANCED) {
                    itemInstance.setVisibleItemId(0);
                }
                itemInstance = this.getInventory().removeItem(itemInstance);
                Log.LogItem(this, Log.ItemLog.PvPDrop, itemInstance);
                if (itemInstance.getEnchantLevel() > 0) {
                    this.sendPacket((IStaticPacket)((SystemMessage)new SystemMessage(SystemMsg.YOU_HAVE_DROPPED_S1_S2).addNumber(itemInstance.getEnchantLevel())).addItemName(itemInstance.getItemId()));
                } else {
                    this.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_HAVE_DROPPED_S1).addItemName(itemInstance.getItemId()));
                }
                if (creature.isPlayable() && (Config.AUTO_LOOT && Config.AUTO_LOOT_PK || this.isInFlyingTransform())) {
                    creature.getPlayer().getInventory().addItem(itemInstance);
                    Log.LogItem(this, Log.ItemLog.Pickup, itemInstance);
                    creature.getPlayer().sendPacket((IStaticPacket)SystemMessage.obtainItems(itemInstance));
                    continue;
                }
                itemInstance.dropToTheGround((Playable)this, Location.findAroundPosition(this, Config.KARMA_RANDOM_DROP_LOCATION_LIMIT));
            }
        }
        finally {
            this.getInventory().writeUnlock();
        }
    }

    @Override
    protected void onDeath(Creature creature) {
        Object object;
        this.getDeathPenalty().checkCharmOfLuck();
        this.getFarmSystem().stopFarmTask();
        if (this.isInStoreMode()) {
            this.setPrivateStoreType(0);
        }
        if (this.isProcessingRequest()) {
            Request request = this.getRequest();
            if (this.isInTrade()) {
                object = request.getOtherPlayer(this);
                this.sendPacket((IStaticPacket)SendTradeDone.FAIL);
                ((Player)object).sendPacket((IStaticPacket)SendTradeDone.FAIL);
            }
            request.cancel();
        }
        this.setAgathion(0);
        boolean bl = true;
        if (Config.ALLOW_CURSED_WEAPONS) {
            if (this.isCursedWeaponEquipped()) {
                CursedWeaponsManager.getInstance().dropPlayer(this);
                bl = false;
            } else if (creature != null && creature.isPlayer() && creature.isCursedWeaponEquipped()) {
                CursedWeaponsManager.getInstance().increaseKills(((Player)creature).getCursedWeaponEquippedId());
                bl = false;
            }
        }
        if (bl) {
            this.doPKPVPManage(creature);
            this.g(creature);
        }
        this.getDeathPenalty().notifyDead(creature);
        if (Config.REMOVE_FORCE_CHARGE_ON_DEAD) {
            this.setIncreasedForce(0);
        }
        if (this.isInParty() && this.getParty().isInReflection() && this.getParty().getReflection() instanceof DimensionalRift) {
            ((DimensionalRift)this.getParty().getReflection()).memberDead(this);
        }
        this.stopWaterTask();
        if (!this.isSalvation() && this.isOnSiegeField() && this.isCharmOfCourage()) {
            this.setCharmOfCourage(false);
        }
        if (this.getLevel() < 6 && (object = QuestManager.getQuest(255)) != null) {
            this.processQuestEvent(((Quest)object).getName(), "CE30", null);
        }
        super.onDeath(creature);
    }

    public void restoreExp() {
        this.restoreExp(100.0);
    }

    public void restoreExp(double d) {
        if (d == 0.0) {
            return;
        }
        int n = 0;
        String string = this.getVar("lostexp");
        if (string != null) {
            n = Integer.parseInt(string);
            this.unsetVar("lostexp");
        }
        if (n != 0) {
            this.addExpAndSp((long)((double)n * d / 100.0), 0L);
        }
    }

    public void deathPenalty(Creature creature) {
        SiegeEvent siegeEvent;
        if (creature == null) {
            return;
        }
        boolean bl = creature.getPlayer() != null && this.atWarWith(creature.getPlayer());
        double d = this.getDeathPenalty().getLevel() * Config.ALT_DEATH_PENALTY_C5_EXPERIENCE_PENALTY;
        d = d < 2.0 ? 1.0 : (d /= 2.0);
        double d2 = Config.LOOSE_EXP_ON_DEATH[this.getLevel()];
        int n = this.getLevel();
        if (Config.ALT_DEATH_PENALTY) {
            d2 = d2 * Config.RATE_XP + (double)this.hP * Config.ALT_PK_DEATH_RATE;
        }
        if (this.isFestivalParticipant() || bl) {
            d2 /= 4.0;
        }
        int n2 = (int)Math.round((double)(Experience.LEVEL[n + 1] - Experience.LEVEL[n]) * d2 / 100.0);
        n2 = (int)((double)n2 * d);
        n2 = (int)this.calcStat(Stats.EXP_LOST, n2, creature, null);
        if (this.isOnSiegeField() && (siegeEvent = this.getEvent(SiegeEvent.class)) != null) {
            n2 = 0;
        }
        long l = this.getExp();
        this.addExpAndSp(-n2, 0L);
        long l2 = l - this.getExp();
        if (!this.isCursedWeaponEquipped() && n2 > 0 && this.getKarma() > 0) {
            int n3 = Config.KARMA_STATIC_LOST_ON_DEATH;
            int n4 = Formulas.calculateKarmaLost(this, n2);
            if (n3 != -1) {
                this.decreaseKarma(Math.max(n3, 0));
            } else {
                this.decreaseKarma(Math.max(n4, 0));
            }
        }
        if (l2 > 0L) {
            this.setVar("lostexp", String.valueOf(l2), -1L);
        }
    }

    public void setRequest(Request request) {
        this.a = request;
    }

    public Request getRequest() {
        return this.a;
    }

    public boolean isBusy() {
        return this.isProcessingRequest() || this.isOutOfControl() || this.isOlyParticipant() || this.getTeam() != TeamType.NONE || this.isInStoreMode() || this.isInDuel() || this.getMessageRefusal() || this.isBlockAll() || this.isInvisible();
    }

    public boolean isProcessingRequest() {
        if (this.a == null) {
            return false;
        }
        return this.a.isInProgress();
    }

    public boolean isInTrade() {
        return this.isProcessingRequest() && this.getRequest().isTypeOf(Request.L2RequestType.TRADE);
    }

    public List<L2GameServerPacket> addVisibleObject(GameObject gameObject, Creature creature) {
        if (this.isLogoutStarted() || gameObject == null || gameObject.getObjectId() == this.getObjectId() || !gameObject.isVisible()) {
            return Collections.emptyList();
        }
        return gameObject.addPacketList(this, creature);
    }

    @Override
    public List<L2GameServerPacket> addPacketList(Player player, Creature creature) {
        if (this.isInvisible() && player.getObjectId() != this.getObjectId()) {
            return Collections.emptyList();
        }
        if (this.getPrivateStoreType() != 0 && player.getVarB(NO_TRADERS_VAR)) {
            return Collections.emptyList();
        }
        if (this.isInObserverMode() && this.getCurrentRegion() != this.getObserverRegion() && this.getObserverRegion() == player.getCurrentRegion()) {
            return Collections.emptyList();
        }
        ArrayList<L2GameServerPacket> arrayList = new ArrayList<L2GameServerPacket>();
        if (player.getObjectId() != this.getObjectId()) {
            arrayList.add(this.isPolymorphed() ? new NpcInfo(this) : new CharInfo(this));
        }
        if (this.isSitting() && this.a != null) {
            arrayList.add(new ChairSit(this, this.a));
        }
        if (this.getPrivateStoreType() != 0) {
            if (this.getPrivateStoreType() == 3) {
                arrayList.add(new PrivateStoreMsgBuy(this));
            } else if (this.getPrivateStoreType() == 1) {
                arrayList.add(new PrivateStoreMsgSell(this));
            } else if (this.getPrivateStoreType() == 8) {
                arrayList.add(new ExPrivateStoreSetWholeMsg(this));
            } else if (this.getPrivateStoreType() == 5) {
                arrayList.add(new RecipeShopMsg(this));
            }
            if (player.isInZonePeace()) {
                return arrayList;
            }
        }
        if (this.isCastingNow()) {
            Creature creature2 = this.getCastingTarget();
            Skill skill = this.getCastingSkill();
            long l = this.getAnimationEndTime();
            if (skill != null && creature2 != null && creature2.isCreature() && this.getAnimationEndTime() > 0L) {
                arrayList.add(new MagicSkillUse((Creature)this, creature2, skill, (int)(l - System.currentTimeMillis()), 0L));
            }
        }
        if (this.isInCombat()) {
            arrayList.add(new AutoAttackStart(this.getObjectId()));
        }
        arrayList.add(new RelationChanged().add(this, player));
        if (this.isInBoat()) {
            arrayList.add(this.getBoat().getOnPacket(this, this.getInBoatPosition()));
        } else if (this.isMoving() || this.isFollowing()) {
            arrayList.add(this.movePacket());
        }
        if (this.isInMountTransform()) {
            arrayList.add(new CharInfo(this));
        }
        return arrayList;
    }

    public List<L2GameServerPacket> removeVisibleObject(GameObject gameObject, List<L2GameServerPacket> list) {
        List<L2GameServerPacket> list2;
        if (this.isLogoutStarted() || gameObject == null || gameObject.getObjectId() == this.getObjectId()) {
            return null;
        }
        List<L2GameServerPacket> list3 = list2 = list == null ? gameObject.deletePacketList() : list;
        if (this.isFollowing() && this.getFollowTarget() == gameObject) {
            this.stopMove();
        }
        this.getAI().notifyEvent(CtrlEvent.EVT_FORGET_OBJECT, gameObject);
        return list2;
    }

    private void j(int n) {
        if (n >= Experience.LEVEL.length - 1) {
            return;
        }
        this.sendPacket((IStaticPacket)SystemMsg.YOUR_LEVEL_HAS_INCREASED);
        this.broadcastPacket(new SocialAction(this.getObjectId(), 2122));
    }

    private void b(int n, int n2) {
        if (n > 0) {
            this.setCurrentHpMp(this.getMaxHp(), this.getMaxMp());
            this.setCurrentCp(this.getMaxCp());
            Quest quest = QuestManager.getQuest(255);
            if (quest != null) {
                this.processQuestEvent(quest.getName(), "CE40", null);
            }
            this.sendPacket((IStaticPacket)new ExVoteSystemInfo(this));
            OneDayRewardHolder.getInstance().fireRequirements(this, null, ObtainLevelRequirement.class);
        } else if (n < 0) {
            this.checkSkills();
        }
        if (this.isInParty()) {
            this.getParty().recalculatePartyData();
        }
        if (this.a != null) {
            this.a.broadcastToOnlineMembers(new PledgeShowMemberListUpdate(this));
        }
        if (this.a != null) {
            this.a.broadcastPlayerUpdate(this);
        }
        this.a(true, n2);
        this.getListeners().onSetLevel(this.getLevel());
    }

    public void checkSkills() {
        if (Config.ALT_WEAK_SKILL_LEARN) {
            return;
        }
        for (Skill skill : this.getAllSkillsArray()) {
            SkillTreeTable.checkSkill(this, skill);
        }
        this.sendSkillList();
    }

    public void startTimers() {
        this.startAutoSaveTask();
        this.startPcBangPointsTask();
        this.startBonusTask();
        this.getInventory().startTimers();
        this.resumeQuestTimers();
    }

    public void stopAllTimers() {
        this.setAgathion(0);
        this.stopWaterTask();
        this.stopBonusTask();
        this.stopHourlyTask();
        this.stopKickTask();
        this.stopVitalityTask();
        this.stopPcBangPointsTask();
        this.stopAutoSaveTask();
        this.getInventory().stopAllTimers();
        this.stopQuestTimers();
        this.bp();
        this.stopUnjailTask();
        this.getFarmSystem().stopFarmTask();
        Future<?> future = this.w;
        if (future != null) {
            future.cancel(false);
            this.w = null;
        }
    }

    @Override
    public Summon getPet() {
        return this.a;
    }

    public void setPet(Summon summon) {
        boolean bl = false;
        if (this.a != null && this.a.isPet()) {
            bl = true;
        }
        this.unsetVar("pet");
        this.a = summon;
        this.autoShot();
        if (summon == null) {
            if (bl) {
                if (this.isLogoutStarted() && this.getPetControlItem() != null) {
                    this.setVar("pet", String.valueOf(this.getPetControlItem().getObjectId()), -1L);
                }
                this.setPetControlItem(null);
            }
            this.getEffectList().stopEffect(4140);
        }
    }

    public void scheduleDelete() {
        long l = 0L;
        if (Config.SERVICES_ENABLE_NO_CARRIER) {
            l = NumberUtils.toInt((String)this.getVar("noCarrier"), (int)Config.SERVICES_NO_CARRIER_DEFAULT_TIME);
        }
        this.scheduleDelete(l * 1000L);
    }

    public void scheduleDelete(long l) {
        if (this.isLogoutStarted() || this.isInOfflineMode()) {
            return;
        }
        this.broadcastCharInfo();
        ThreadPoolManager.getInstance().schedule(new RunnableImpl(){

            @Override
            public void runImpl() throws Exception {
                if (!Player.this.isConnected()) {
                    Player.this.bh();
                    Player.this.deleteMe();
                }
            }
        }, l);
        if (l > 0L && this.getTeam() == TeamType.NONE && !this.isOlyParticipant() && !this.isInAnyZone(Zone.ZoneType.peace_zone, Zone.ZoneType.SIEGE, Zone.ZoneType.offshore)) {
            this.setNoCarrierProtectionTime(System.currentTimeMillis() + 1000L * Math.min(l, Config.NO_CARRIER_PROTECTION_TIME));
        }
    }

    @Override
    protected void onDelete() {
        super.onDelete();
        WorldRegion worldRegion = this.getObserverRegion();
        if (worldRegion != null) {
            worldRegion.removeObject(this);
        }
        this.a.notifyFriends(false);
        this.a.clear();
        this.a.clear();
        this.a = null;
        this.c = null;
        this.a = null;
        this.aI = null;
        this.d = null;
        this.P = HardReferences.emptyRef();
        this.b = null;
    }

    public void setTradeList(List<TradeItem> list) {
        this.aY = list;
    }

    public List<TradeItem> getTradeList() {
        return this.aY;
    }

    public String getSellStoreName() {
        return this.cP;
    }

    public void setSellStoreName(String string) {
        this.cP = Strings.stripToSingleLine(string);
    }

    public void setSellList(boolean bl, List<TradeItem> list) {
        if (bl) {
            this.aW = list;
        } else {
            this.aV = (long)list;
        }
    }

    public List<TradeItem> getSellList() {
        switch (this.getPrivateStoreType()) {
            case 8: {
                return this.getSellList(true);
            }
            case 1: {
                return this.getSellList(false);
            }
        }
        return Collections.emptyList();
    }

    public List<TradeItem> getSellList(boolean bl) {
        return bl ? this.aW : (List<TradeItem>)this.aV;
    }

    public String getBuyStoreName() {
        return this.cQ;
    }

    public void setBuyStoreName(String string) {
        this.cQ = Strings.stripToSingleLine(string);
    }

    public void setBuyList(List<TradeItem> list) {
        this.aX = list;
    }

    public List<TradeItem> getBuyList() {
        List<TradeItem> list = this.aX;
        return list != null ? list : Collections.emptyList();
    }

    public void setManufactureName(String string) {
        this.cO = Strings.stripToSingleLine(string);
    }

    public String getManufactureName() {
        return this.cO;
    }

    public List<ManufactureItem> getCreateList() {
        return this.aU;
    }

    public void setCreateList(List<ManufactureItem> list) {
        this.aU = list;
    }

    public void setPrivateStoreType(int n) {
        int n2 = this.o.get();
        if (n2 != n && this.o.compareAndSet(n2, n)) {
            if (n != 0) {
                if (n2 == 0) {
                    this.sitDown(null);
                    this.broadcastCharInfo();
                }
                this.setVar("storemode", String.valueOf(n), -1L);
            } else if (n2 != 0) {
                this.unsetVar("storemode");
                if (!this.isDead()) {
                    this.standUp();
                    this.broadcastCharInfo();
                }
            }
        }
        this.getListeners().onSetPrivateStoreType(n);
    }

    public boolean isInStoreMode() {
        return this.o.get() != 0;
    }

    public int getPrivateStoreType() {
        return this.o.get();
    }

    public void setClan(Clan clan) {
        Clan clan2;
        if (this.a != clan && this.a != null) {
            this.unsetVar("canWhWithdraw");
        }
        if ((clan2 = this.a) != null && clan == null) {
            for (Skill skill : clan2.getAllSkills()) {
                this.removeSkill(skill, false);
            }
        }
        this.a = clan;
        if (clan == null) {
            this.if = -128;
            this.ie = 0;
            this.ig = 0;
            this.ii = 0;
            this.getInventory().validateItems();
            return;
        }
        if (!clan.isAnyMember(this.getObjectId())) {
            this.setClan(null);
            if (!this.isNoble()) {
                this.setTitle("");
            }
        }
    }

    @Override
    public Clan getClan() {
        return this.a;
    }

    public SubUnit getSubUnit() {
        return this.a == null ? null : this.a.getSubUnit(this.if);
    }

    public ClanHall getClanHall() {
        int n = this.a != null ? this.a.getHasHideout() : 0;
        return ResidenceHolder.getInstance().getResidence(ClanHall.class, n);
    }

    public Castle getCastle() {
        int n = this.a != null ? this.a.getCastle() : 0;
        return ResidenceHolder.getInstance().getResidence(Castle.class, n);
    }

    public Alliance getAlliance() {
        return this.a == null ? null : this.a.getAlliance();
    }

    public boolean isClanLeader() {
        return this.a != null && this.getObjectId() == this.a.getLeaderId();
    }

    public boolean isAllyLeader() {
        return this.getAlliance() != null && this.getAlliance().getLeader().getLeaderId() == this.getObjectId();
    }

    @Override
    public void reduceArrowCount() {
        if (this.c != null && this.c.getTemplate().isQuiver() && this.c == this.getInventory().getPaperdollItem(7)) {
            return;
        }
        this.sendPacket((IStaticPacket)SystemMsg.YOU_CAREFULLY_NOCK_AN_ARROW);
        if (!this.getInventory().destroyItemByObjectId(this.getInventory().getPaperdollObjectId(7), 1L)) {
            this.getInventory().setPaperdollItem(7, null);
            this.c = null;
        }
    }

    protected boolean checkAndEquipArrows() {
        if (this.getInventory().getPaperdollItem(7) == null) {
            ItemInstance itemInstance = this.getActiveWeaponInstance();
            if (itemInstance != null && itemInstance.getItemType() == WeaponTemplate.WeaponType.BOW) {
                this.c = this.getInventory().findArrowForBow(itemInstance.getTemplate());
            }
            if (this.c != null) {
                this.getInventory().setPaperdollItem(7, this.c);
            }
        } else {
            this.c = this.getInventory().getPaperdollItem(7);
        }
        return this.c != null;
    }

    public void setUptime(long l) {
        this.bt = l;
    }

    public long getUptime() {
        return System.currentTimeMillis() - this.bt;
    }

    public boolean isInParty() {
        return this._party != null;
    }

    public void setParty(Party party) {
        this._party = party;
    }

    public void joinParty(Party party) {
        if (party != null) {
            party.addPartyMember(this);
        }
    }

    public void leaveParty() {
        if (this.isInParty()) {
            this._party.removePartyMember(this, false);
        }
    }

    public Party getParty() {
        return this._party;
    }

    public void setLastPartyPosition(Location location) {
        this.y = location;
    }

    public Location getLastPartyPosition() {
        return this.y;
    }

    public boolean isGM() {
        return this.a == null ? false : this.a.IsGM;
    }

    public void setAccessLevel(int n) {
        this.ij = n;
    }

    @Override
    public int getAccessLevel() {
        return this.ij;
    }

    public void setPlayerAccess(PlayerAccess playerAccess) {
        this.a = playerAccess != null ? playerAccess : new PlayerAccess();
        this.setAccessLevel(this.isGM() || this.a.Menu ? 100 : 0);
    }

    public PlayerAccess getPlayerAccess() {
        return this.a;
    }

    @Override
    public double getLevelMod() {
        return (89.0 + (double)this.getLevel()) / 100.0;
    }

    @Override
    public void updateStats() {
        if (this.entering || this.isLogoutStarted()) {
            return;
        }
        this.refreshOverloaded();
        this.refreshExpertisePenalty();
        super.updateStats();
    }

    @Override
    public void sendChanges() {
        if (this.entering || this.isLogoutStarted()) {
            return;
        }
        super.sendChanges();
    }

    public void updateKarma(boolean bl) {
        this.sendStatusUpdate(true, true, 27);
        if (bl) {
            this.broadcastRelation();
        }
    }

    public boolean isOnline() {
        return this.ae;
    }

    public void setIsOnline(boolean bl) {
        this.ae = bl;
    }

    public void setOnlineStatus(boolean bl) {
        this.ae = bl;
        this.bl();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void bl() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("UPDATE `characters` SET `online`=?, `lastAccess`=? WHERE `obj_id`=?");
            preparedStatement.setInt(1, this.isOnline() && !this.isInOfflineMode() ? 1 : 0);
            preparedStatement.setLong(2, System.currentTimeMillis() / 1000L);
            preparedStatement.setInt(3, this.getObjectId());
            preparedStatement.execute();
        }
        catch (Exception exception) {
            try {
                bF.error("", (Throwable)exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement);
        }
        DbUtils.closeQuietly(connection, preparedStatement);
    }

    public void increaseKarma(long l) {
        boolean bl = this.gh == 0;
        long l2 = (long)this.gh + l;
        if (l2 > Integer.MAX_VALUE) {
            l2 = Integer.MAX_VALUE;
        }
        if (this.gh == 0 && l2 > 0L) {
            if (this._pvpFlag > 0) {
                this._pvpFlag = 0;
                if (this.x != null) {
                    this.x.cancel(true);
                    this.x = null;
                }
                this.sendStatusUpdate(true, true, 26);
            }
            this.gh = (int)l2;
        } else {
            this.gh = (int)l2;
        }
        this.updateKarma(bl);
    }

    public void decreaseKarma(int n) {
        boolean bl = this.gh > 0;
        this.gh -= n;
        if (this.gh <= 0) {
            this.gh = 0;
            this.updateKarma(bl);
        } else {
            this.updateKarma(false);
        }
    }

    public static Player create(int n, int n2, String string, String string2, int n3, int n4, int n5) {
        PlayerTemplate playerTemplate = CharacterTemplateHolder.getInstance().getTemplate(ClassId.getClassById(n), n2 == 0);
        Player player = new Player(IdFactory.getInstance().getNextId(), playerTemplate, string);
        player.setName(string2);
        player.setBaseClassId(n);
        player.setTitle("");
        player.setHairStyle(n3);
        player.setHairColor(n4);
        player.setFace(n5);
        player.setCreateTime(System.currentTimeMillis());
        if (!CharacterDAO.getInstance().insert(player)) {
            return null;
        }
        return player;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Player restore(int n) {
        ResultSet resultSet;
        ResultSet resultSet2;
        Statement statement;
        Statement statement2;
        Connection connection;
        Player player;
        block53: {
            player = null;
            connection = null;
            statement2 = null;
            statement = null;
            resultSet2 = null;
            resultSet = null;
            try {
                Object object;
                int n2;
                connection = DatabaseFactory.getInstance().getConnection();
                statement2 = connection.createStatement();
                statement = connection.createStatement();
                resultSet2 = statement2.executeQuery("SELECT * FROM `characters` WHERE `obj_Id`=" + n + " LIMIT 1");
                resultSet = statement.executeQuery("SELECT `class_id` FROM `character_subclasses` WHERE `char_obj_id`=" + n + " AND `isBase`=1 LIMIT 1");
                if (!resultSet2.next() || !resultSet.next()) break block53;
                boolean bl = resultSet2.getInt("sex") == 1;
                int n3 = resultSet2.getInt("base_class_id");
                PlayerTemplate playerTemplate = CharacterTemplateHolder.getInstance().getTemplate(ClassId.getClassById(n3), !bl);
                player = new Player(n, playerTemplate);
                CharacterVariablesDAO.getInstance().loadVariables(n, player.getVars());
                player.aJ.putAll(InstanceReuseDAO.getInstance().load(player));
                player.bm();
                player.setTpBookmarkSize(resultSet2.getInt("bookmarks"));
                player.ba = CharacterTPBookmarkDAO.getInstance().select(player);
                player.a.restore();
                player.j = CharacterPostFriendDAO.getInstance().select(player);
                CharacterGroupReuseDAO.getInstance().select(player);
                player.setBaseClassId(n3);
                player.aJ = resultSet2.getString("account_name");
                String string = resultSet2.getString("char_name");
                player.setName(string);
                player.setFace(resultSet2.getInt("face"));
                player.setHairStyle(resultSet2.getInt("hairStyle"));
                player.setHairColor(resultSet2.getInt("hairColor"));
                player.setHeading(0);
                player.setKarma(resultSet2.getInt("karma"), false);
                player.setPvpKills(resultSet2.getInt("pvpkills"));
                player.setPkKills(resultSet2.getInt("pkkills"));
                player.setLeaveClanTime(resultSet2.getLong("leaveclan") * 1000L);
                if (player.getLeaveClanTime() > 0L && player.canJoinClan()) {
                    player.setLeaveClanTime(0L);
                }
                player.setDeleteClanTime(resultSet2.getLong("deleteclan") * 1000L);
                if (player.getDeleteClanTime() > 0L && player.canCreateClan()) {
                    player.setDeleteClanTime(0L);
                }
                player.setNoChannel(resultSet2.getLong("nochannel") * 1000L);
                if (player.getNoChannel() > 0L && player.getNoChannelRemained() < 0L) {
                    player.setNoChannel(0L);
                }
                player.setOnlineTime(resultSet2.getLong("onlinetime") * 1000L);
                int n4 = resultSet2.getInt("clanid");
                if (n4 > 0) {
                    player.setClan(ClanTable.getInstance().getClan(n4));
                    player.setPledgeType(resultSet2.getInt("pledge_type"));
                    player.setPowerGrade(resultSet2.getInt("pledge_rank"));
                    player.setLvlJoinedAcademy(resultSet2.getInt("lvl_joined_academy"));
                    player.setApprentice(resultSet2.getInt("apprentice"));
                }
                player.setCreateTime(resultSet2.getLong("createtime") * 1000L);
                player.setDeleteTimer(resultSet2.getInt("deletetime"));
                player.setTitle(resultSet2.getString("title"));
                if (player.getVar("titlecolor") != null) {
                    player.setTitleColor(Integer.decode("0x" + player.getVar("titlecolor")));
                }
                if (player.getVar("namecolor") == null) {
                    if (player.isGM()) {
                        player.setNameColor(Config.GM_NAME_COLOUR);
                    } else if (player.getClan() != null && player.getClan().getLeaderId() == player.getObjectId()) {
                        player.setNameColor(Config.CLANLEADER_NAME_COLOUR);
                    } else {
                        player.setNameColor(Config.NORMAL_NAME_COLOUR);
                    }
                } else {
                    player.setNameColor(Integer.decode("0x" + player.getVar("namecolor")));
                }
                if (Config.AUTO_LOOT_INDIVIDUAL) {
                    player.bL = player.getVarB("AutoLoot", Config.AUTO_LOOT);
                    player.bM = player.getVarB("AutoLootHerbs", Config.AUTO_LOOT_HERBS);
                    player.bN = player.getVarB("AutoLootAdena", Config.AUTO_LOOT_ADENA);
                }
                player.setFistsWeaponItem(player.findFistsWeaponItem(n3));
                player.setUptime(System.currentTimeMillis());
                player.setLastAccess(resultSet2.getLong("lastAccess"));
                int n5 = resultSet2.getInt("rec_left");
                int n6 = resultSet2.getInt("rec_have");
                player.setKeyBindings(resultSet2.getBytes("key_bindings"));
                player.setPcBangPoints(resultSet2.getInt("pcBangPoints"));
                player.setRaidBossPoints(resultSet2.getInt("raidBossPoints"));
                player.restoreRecipeBook();
                boolean bl2 = false;
                player.setNoble(NoblesController.getInstance().isNobles(player));
                if (Config.OLY_ENABLED) {
                    player.setHero(HeroController.getInstance().isCurrentHero(player));
                    if (player.isHero()) {
                        HeroController.getInstance().loadDiary(player.getObjectId());
                    }
                }
                if (Config.ALT_ALLOW_CUSTOM_HERO && !player.isHero() && player.getVar(CUSTOM_HERO_END_TIME_VAR) != null) {
                    long l = player.getVarLong(CUSTOM_HERO_END_TIME_VAR, 0L);
                    long l2 = l - System.currentTimeMillis() / 1000L;
                    if (l2 > 0L) {
                        player.setCustomHero(true, l2, Config.ALT_ALLOW_CUSTOM_HERO_SKILLS);
                        bl2 = !Config.ALT_ALLOW_CUSTOM_HERO_SKILLS;
                    } else {
                        player.setCustomHero(false, 0L, true);
                        bl2 = true;
                    }
                }
                player.updatePledgeClass();
                int n7 = 0;
                String string2 = player.getVar("jailed");
                boolean bl3 = false;
                if (!StringUtils.isBlank((CharSequence)string2) && (n2 = string2.indexOf(59)) > 0) {
                    long l = Long.parseLong(string2.substring(0, n2)) - System.currentTimeMillis();
                    if (l > 0L) {
                        player.startUnjailTask(player, (int)l);
                        Location serializable = Location.findPointToStay(player, Config.SERVICE_JAIL_COORDINATES, 50, 200);
                        player.setXYZ(serializable.getX(), serializable.getY(), serializable.getZ());
                        n7 = ReflectionManager.JAIL.getId();
                        player.sitDown(null);
                        player.block();
                        bl3 = true;
                    } else {
                        player.startUnjailTask(player, 1000L);
                    }
                }
                if (!bl3) {
                    player.setXYZ(resultSet2.getInt("x"), resultSet2.getInt("y"), resultSet2.getInt("z"));
                    String string3 = player.getVar("reflection");
                    if (string3 != null && (n7 = Integer.parseInt(string3)) > 0) {
                        String string4 = player.getVar("backCoords");
                        if (string4 != null) {
                            player.setLoc(Location.parseLoc(string4));
                            player.unsetVar("backCoords");
                        }
                        n7 = 0;
                    }
                }
                player.setReflection(n7);
                EventHolder.getInstance().findEvent(player);
                Quest.restoreQuestStates(player);
                player.getInventory().restore();
                Player.restoreCharSubClasses(player);
                player.bi();
                player.restoreGivableAndReceivedRec(n5, n6);
                for (ItemInstance itemInstance : player.getInventory().getPaperdollItems()) {
                    if (itemInstance == null || !itemInstance.isCursed()) continue;
                    CursedWeaponsManager.getInstance().checkPlayer(player, itemInstance);
                }
                player.setVitality(resultSet2.getInt("vitality") + (int)((double)(System.currentTimeMillis() / 1000L - resultSet2.getLong("lastAccess")) / 15.0));
                try {
                    object = player.getVar("ExpandInventory");
                    if (object != null) {
                        player.setExpandInventory(Integer.parseInt((String)object));
                    }
                }
                catch (Exception exception) {
                    bF.error("", (Throwable)exception);
                }
                try {
                    object = player.getVar("ExpandWarehouse");
                    if (object != null) {
                        player.setExpandWarehouse(Integer.parseInt((String)object));
                    }
                }
                catch (Exception exception) {
                    bF.error("", (Throwable)exception);
                }
                try {
                    object = player.getVar(ANIMATION_OF_CAST_RANGE_VAR);
                    if (object != null) {
                        player.setBuffAnimRange(Integer.parseInt((String)object));
                    }
                }
                catch (Exception exception) {
                    bF.error("", (Throwable)exception);
                }
                try {
                    player.setNoShotsAnim(player.getVarB(NO_SHOTS_ANIMATION_VAR, false));
                }
                catch (Exception exception) {
                    bF.error("", (Throwable)exception);
                }
                try {
                    object = player.getVar(HIDE_HAIR_ACCESSORY);
                    if (object != null) {
                        player.setHideHeadAccessories(Boolean.parseBoolean((String)object));
                    }
                }
                catch (Exception exception) {
                    bF.error("", (Throwable)exception);
                }
                try {
                    object = player.getVar(NO_TRADERS_VAR);
                    if (object != null) {
                        player.setNotShowTraders(Boolean.parseBoolean((String)object));
                    }
                }
                catch (Exception exception) {
                    bF.error("", (Throwable)exception);
                }
                try {
                    object = player.getVar("pet");
                    if (object != null) {
                        player.setPetControlItem(Integer.parseInt((String)object));
                    }
                }
                catch (Exception exception) {
                    bF.error("", (Throwable)exception);
                }
                for (Map.Entry entry : CharacterDAO.getInstance().listCharactersByAccountName(player.getAccountName()).entrySet()) {
                    if (((Integer)entry.getKey()).equals(n)) continue;
                    player.aI.put((Integer)entry.getKey(), (String)entry.getValue());
                }
                if (bl2) {
                    HeroController.removeSkills(player);
                }
                player.getBlockList().restore();
                player.a.restore();
                player.refreshExpertisePenalty();
                player.refreshOverloaded();
                player.getWarehouse().restore();
                player.getFreight().restore();
                player.restoreTradeList();
                if (player.getVar("storemode") != null) {
                    player.setPrivateStoreType(Integer.parseInt(player.getVar("storemode")));
                    player.setSitting(true);
                }
                player.updateRam();
                player.getFarmSystem().checkFarmTask();
                if (player.getVar("lang@") == null) {
                    player.setVar("lang@", Config.DEFAULT_LANG, -1L);
                }
                if (Config.SERVICES_ENABLE_NO_CARRIER && player.getVar("noCarrier") == null) {
                    player.setVar("noCarrier", Config.SERVICES_NO_CARRIER_DEFAULT_TIME, -1L);
                }
                if (Config.SERVICES_PK_ANNOUNCE <= 0 || player.getVar("PvPAnnounce") != null) break block53;
                player.setVar("PvPAnnounce", "1", -1L);
            }
            catch (Exception exception) {
                try {
                    bF.error("Could not restore char data!", (Throwable)exception);
                }
                catch (Throwable throwable) {
                    DbUtils.closeQuietly(statement, resultSet, statement2, resultSet2, connection);
                    throw throwable;
                }
                DbUtils.closeQuietly(statement, resultSet, statement2, resultSet2, connection);
            }
        }
        DbUtils.closeQuietly(statement, resultSet, statement2, resultSet2, connection);
        return player;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void bm() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("SELECT `itemNum`, `itemId`, `itemCount`, `itemSender` FROM `character_premium_items` WHERE `charId`=?");
            preparedStatement.setInt(1, this.getObjectId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int n = resultSet.getInt("itemNum");
                int n2 = resultSet.getInt("itemId");
                long l = resultSet.getLong("itemCount");
                String string = resultSet.getString("itemSender");
                PremiumItem premiumItem = new PremiumItem(n2, l, string);
                this.aF.put(n, premiumItem);
            }
        }
        catch (Exception exception) {
            try {
                bF.error("", (Throwable)exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement, resultSet);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        }
        DbUtils.closeQuietly(connection, preparedStatement, resultSet);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void updatePremiumItem(int n, long l) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("UPDATE `character_premium_items` SET `itemCount`=? WHERE `charId`=? AND `itemNum`=?");
            preparedStatement.setLong(1, l);
            preparedStatement.setInt(2, this.getObjectId());
            preparedStatement.setInt(3, n);
            preparedStatement.execute();
        }
        catch (Exception exception) {
            try {
                bF.error("", (Throwable)exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement);
        }
        DbUtils.closeQuietly(connection, preparedStatement);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void deletePremiumItem(int n) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM `character_premium_items` WHERE `charId`=? AND `itemNum`=?");
            preparedStatement.setInt(1, this.getObjectId());
            preparedStatement.setInt(2, n);
            preparedStatement.execute();
        }
        catch (Exception exception) {
            try {
                bF.error("", (Throwable)exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement);
        }
        DbUtils.closeQuietly(connection, preparedStatement);
    }

    public Map<Integer, PremiumItem> getPremiumItemList() {
        return this.aF;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void store(boolean bl) {
        block11: {
            if (!this.t.tryLock()) {
                return;
            }
            try {
                Connection connection = null;
                PreparedStatement preparedStatement = null;
                try {
                    connection = DatabaseFactory.getInstance().getConnection();
                    preparedStatement = connection.prepareStatement("UPDATE characters SET face=?,hairStyle=?,hairColor=?,x=?,y=?,z=?,karma=?,pvpkills=?,pkkills=?,rec_have=?,rec_left=?,rec_bonus_time=?,hunting_bonus_time=?,rec_tick_cnt=?,hunting_bonus=?,clanid=?,deletetime=?,title=?,accesslevel=?,online=?,leaveclan=?,deleteclan=?,nochannel=?,onlinetime=?,pledge_type=?,pledge_rank=?,lvl_joined_academy=?,apprentice=?,key_bindings=?,pcBangPoints=?,raidBossPoints=?,char_name=?,bookmarks=?,vitality=? WHERE obj_Id=? LIMIT 1");
                    preparedStatement.setInt(1, this.getFace());
                    preparedStatement.setInt(2, this.getHairStyle());
                    preparedStatement.setInt(3, this.getHairColor());
                    if (this._stablePoint == null) {
                        preparedStatement.setInt(4, this.getX());
                        preparedStatement.setInt(5, this.getY());
                        preparedStatement.setInt(6, this.getZ());
                    } else {
                        preparedStatement.setInt(4, this._stablePoint.x);
                        preparedStatement.setInt(5, this._stablePoint.y);
                        preparedStatement.setInt(6, this._stablePoint.z);
                    }
                    preparedStatement.setInt(7, this.getKarma());
                    preparedStatement.setInt(8, this.getPvpKills());
                    preparedStatement.setInt(9, this.getPkKills());
                    preparedStatement.setInt(10, this.getReceivedRec());
                    preparedStatement.setInt(11, this.getGivableRec());
                    preparedStatement.setInt(12, 0);
                    preparedStatement.setInt(13, 0);
                    preparedStatement.setInt(14, 0);
                    preparedStatement.setInt(15, 0);
                    preparedStatement.setInt(16, this.getClanId());
                    preparedStatement.setInt(17, this.getDeleteTimer());
                    preparedStatement.setString(18, this._title);
                    preparedStatement.setInt(19, this.ij);
                    preparedStatement.setInt(20, this.isOnline() && !this.isInOfflineMode() ? 1 : 0);
                    preparedStatement.setLong(21, this.getLeaveClanTime() / 1000L);
                    preparedStatement.setLong(22, this.getDeleteClanTime() / 1000L);
                    preparedStatement.setLong(23, this.bp > 0L ? this.getNoChannelRemained() / 1000L : this.bp);
                    preparedStatement.setInt(24, (int)(this.bm > 0L ? (this.bl + System.currentTimeMillis() - this.bm) / 1000L : this.bl / 1000L));
                    preparedStatement.setInt(25, this.getPledgeType());
                    preparedStatement.setInt(26, this.getPowerGrade());
                    preparedStatement.setInt(27, this.getLvlJoinedAcademy());
                    preparedStatement.setInt(28, this.getApprentice());
                    preparedStatement.setBytes(29, this.getKeyBindings());
                    preparedStatement.setInt(30, this.getPcBangPoints());
                    preparedStatement.setInt(31, this.getRaidBossPoints());
                    preparedStatement.setString(32, this.getName());
                    preparedStatement.setInt(33, this.getTpBookmarkSize());
                    preparedStatement.setInt(34, (int)this.getVitality());
                    preparedStatement.setInt(35, this.getObjectId());
                    preparedStatement.executeUpdate();
                    GameStats.increaseUpdatePlayerBase();
                    if (!bl) {
                        EffectsDAO.getInstance().insert(this);
                        CharacterGroupReuseDAO.getInstance().insert(this);
                        this.storeDisableSkills();
                    }
                    this.storeCharSubClasses();
                    this.bj();
                }
                catch (Exception exception) {
                    try {
                        bF.error("Could not store char data: " + this + "!", (Throwable)exception);
                    }
                    catch (Throwable throwable) {
                        DbUtils.closeQuietly(connection, preparedStatement);
                        throw throwable;
                    }
                    DbUtils.closeQuietly(connection, preparedStatement);
                    break block11;
                }
                DbUtils.closeQuietly(connection, preparedStatement);
            }
            finally {
                this.t.unlock();
            }
        }
    }

    public Skill addSkill(Skill skill, boolean bl) {
        if (skill == null) {
            return null;
        }
        Skill skill2 = super.addSkill(skill);
        if (skill.equals(skill2)) {
            return skill2;
        }
        if (bl) {
            this.a(skill, skill2);
        }
        return skill2;
    }

    public Skill removeSkill(Skill skill, boolean bl) {
        if (skill == null) {
            return null;
        }
        return this.removeSkill(skill.getId(), bl);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Skill removeSkill(int n, boolean bl) {
        Skill skill = super.removeSkillById(n);
        if (!bl) {
            return skill;
        }
        if (skill != null) {
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            try {
                connection = DatabaseFactory.getInstance().getConnection();
                preparedStatement = connection.prepareStatement("DELETE FROM `character_skills` WHERE `skill_id`=? AND `char_obj_id`=? AND `class_index`=?");
                preparedStatement.setInt(1, skill.getId());
                preparedStatement.setInt(2, this.getObjectId());
                preparedStatement.setInt(3, this.getActiveClassId());
                preparedStatement.execute();
            }
            catch (Exception exception) {
                try {
                    bF.error("Could not delete skill!", (Throwable)exception);
                }
                catch (Throwable throwable) {
                    DbUtils.closeQuietly(connection, preparedStatement);
                    throw throwable;
                }
                DbUtils.closeQuietly(connection, preparedStatement);
            }
            DbUtils.closeQuietly(connection, preparedStatement);
        }
        return skill;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void a(Skill skill, Skill skill2) {
        if (skill == null) {
            bF.warn("could not store new skill. its NULL");
            return;
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("REPLACE INTO `character_skills` (`char_obj_id`,`skill_id`,`skill_level`,`class_index`) values(?,?,?,?)");
            preparedStatement.setInt(1, this.getObjectId());
            preparedStatement.setInt(2, skill.getId());
            preparedStatement.setInt(3, skill.getLevel());
            preparedStatement.setInt(4, this.getActiveClassId());
            preparedStatement.execute();
        }
        catch (Exception exception) {
            try {
                bF.error("Error could not store skills!", (Throwable)exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement);
        }
        DbUtils.closeQuietly(connection, preparedStatement);
    }

    private void restoreSkills() {
        this.k(this.getActiveClassId());
    }

    private void k(int n) {
        for (Skill skill : CharacterDAO.getInstance().getCharacterSkills(this.getObjectId(), n)) {
            if (!(this.isGM() || Config.ALT_WEAK_SKILL_LEARN || SkillAcquireHolder.getInstance().isSkillPossible(this, skill))) {
                bF.warn("Skill " + skill.toString() + " not possible for player " + this.toString() + " with classId " + this.getActiveClassId());
                this.removeSkill(skill, true);
                this.removeSkillFromShortCut(skill.getId());
                continue;
            }
            super.addSkill(skill);
        }
        if (this.getActiveClassId() != n) {
            return;
        }
        this.updateNobleSkills();
        this.updatePremiumSkills();
        if (this.bT && (!this.isSubClassActive() || Config.ALT_ALLOW_HERO_SKILLS_ON_SUB_CLASS) && (HeroController.getInstance().isCurrentHero(this) || Config.ALT_ALLOW_CUSTOM_HERO_SKILLS)) {
            HeroController.addSkills(this);
        }
        if (this.a != null) {
            this.a.addSkillsQuietly(this);
            if (this.a.getLeaderId() == this.getObjectId() && this.a.getLevel() >= Config.MIN_CLAN_LEVEL_FOR_SIEGE_REGISTRATION) {
                Clan.addClanLeaderSkills(this);
            }
        }
        this.addSkill(SkillTable.getInstance().getInfo(17192, 1));
        if (Config.UNSTUCK_SKILL && this.getSkillLevel(1050) < 0) {
            this.addSkill(SkillTable.getInstance().getInfo(2099, 1));
        }
        if (Config.BLOCK_BUFF_SKILL) {
            this.addSkill(SkillTable.getInstance().getInfo(5088, 1));
        }
        if (Config.NOBLES_BUFF_SKILL) {
            this.addSkill(SkillTable.getInstance().getInfo(1323, 1));
        }
        for (int i = 0; i < Config.ADDITIONALS_SKILLS.length; i += 2) {
            this.addSkill(SkillTable.getInstance().getInfo(Config.ADDITIONALS_SKILLS[i], Config.ADDITIONALS_SKILLS[i + 1]));
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void storeDisableSkills() {
        PreparedStatement preparedStatement;
        Connection connection;
        block9: {
            connection = null;
            preparedStatement = null;
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM `character_skills_save` WHERE `char_obj_id` = ? AND (`class_index`=? OR `class_index`=-1) AND `end_time` < ?");
            preparedStatement.setInt(1, this.getObjectId());
            preparedStatement.setInt(2, this.getActiveClassId());
            preparedStatement.setLong(3, System.currentTimeMillis());
            preparedStatement.executeUpdate();
            DbUtils.close(preparedStatement);
            if (!this.getSkillReuses0().isEmpty()) break block9;
            DbUtils.closeQuietly(connection, preparedStatement);
            return;
        }
        try {
            preparedStatement = connection.prepareStatement("REPLACE INTO `character_skills_save`(`char_obj_id`, `skill_id`, `skill_level`, `class_index`, `end_time`, `reuse_delay_org`) VALUES\t(?,?,?,?,?,?)");
            CHashIntObjectMap cHashIntObjectMap = new CHashIntObjectMap();
            IntObjectMap<TimeStamp> intObjectMap = this.getSkillReuses0();
            synchronized (intObjectMap) {
                cHashIntObjectMap.putAll(this.getSkillReuses0());
            }
            for (TimeStamp timeStamp : cHashIntObjectMap.values()) {
                Skill skill = SkillTable.getInstance().getInfo(timeStamp.getId(), timeStamp.getLevel());
                if (skill == null) continue;
                preparedStatement.setInt(1, this.getObjectId());
                preparedStatement.setInt(2, skill.getId());
                preparedStatement.setInt(3, skill.getLevel());
                preparedStatement.setInt(4, !skill.isSharedClassReuse() ? this.getActiveClassId() : -1);
                preparedStatement.setLong(5, timeStamp.getEndTime());
                preparedStatement.setLong(6, timeStamp.getReuseBasic());
                preparedStatement.executeUpdate();
            }
        }
        catch (Exception exception) {
            try {
                bF.warn("Could not store disable skills data: " + exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement);
        }
        DbUtils.closeQuietly(connection, preparedStatement);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void restoreDisableSkills() {
        this.getSkillReuses0().clear();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("SELECT `skill_id`, `skill_level`, `end_time`, `reuse_delay_org` FROM `character_skills_save` WHERE `char_obj_id`=? AND (`class_index`=? OR `class_index`=-1)");
            preparedStatement.setInt(1, this.getObjectId());
            preparedStatement.setInt(2, this.getActiveClassId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int n = resultSet.getInt("skill_id");
                int n2 = resultSet.getInt("skill_level");
                long l = resultSet.getLong("end_time");
                long l2 = resultSet.getLong("reuse_delay_org");
                long l3 = System.currentTimeMillis();
                Skill skill = SkillTable.getInstance().getInfo(n, n2);
                if (skill == null || l - l3 <= 500L) continue;
                this.getSkillReuses0().put(skill.hashCode(), (Object)new TimeStamp(skill, l, l2));
            }
            DbUtils.close(preparedStatement);
            preparedStatement = connection.prepareStatement("DELETE FROM `character_skills_save` WHERE `char_obj_id` = ? AND (`class_index`=? OR `class_index`=-1) AND `end_time` < ?");
            preparedStatement.setInt(1, this.getObjectId());
            preparedStatement.setInt(2, this.getActiveClassId());
            preparedStatement.setLong(3, System.currentTimeMillis());
            preparedStatement.executeUpdate();
        }
        catch (Exception exception) {
            try {
                bF.error("Could not restore active skills data!", (Throwable)exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement, resultSet);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        }
        DbUtils.closeQuietly(connection, preparedStatement, resultSet);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void bn() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            int n;
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("SELECT `slot`, `symbol_id` FROM `character_hennas` WHERE `char_obj_id`=? AND `class_index`=?");
            preparedStatement.setInt(1, this.getObjectId());
            preparedStatement.setInt(2, this.getActiveClassId());
            resultSet = preparedStatement.executeQuery();
            for (n = 0; n < 3; ++n) {
                this.a[n] = null;
            }
            while (resultSet.next()) {
                Henna henna;
                int n2;
                n = resultSet.getInt("slot");
                if (n < 1 || n > 3 || (n2 = resultSet.getInt("symbol_id")) == 0 || (henna = HennaHolder.getInstance().getHenna(n2)) == null) continue;
                this.a[n - 1] = henna;
            }
        }
        catch (Exception exception) {
            try {
                bF.warn("could not restore henna: " + exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement, resultSet);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        }
        DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        this.bo();
    }

    public int getHennaEmptySlots() {
        int n = 1 + this.getClassId().level();
        for (int i = 0; i < 3; ++i) {
            if (this.a[i] == null) continue;
            --n;
        }
        if (n <= 0) {
            return 0;
        }
        return n;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean removeHenna(int n) {
        if (n < 1 || n > 3) {
            return false;
        }
        if (this.a[--n] == null) {
            return false;
        }
        Henna henna = this.a[n];
        int n2 = henna.getDyeId();
        this.a[n] = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM `character_hennas` where `char_obj_id`=? and `slot`=? and `class_index`=?");
            preparedStatement.setInt(1, this.getObjectId());
            preparedStatement.setInt(2, n + 1);
            preparedStatement.setInt(3, this.getActiveClassId());
            preparedStatement.execute();
        }
        catch (Exception exception) {
            try {
                bF.warn("could not remove char henna: " + exception, (Throwable)exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement);
        }
        DbUtils.closeQuietly(connection, preparedStatement);
        this.bo();
        this.sendPacket((IStaticPacket)new HennaInfo(this));
        this.sendUserInfo(true);
        ItemFunctions.addItem((Playable)this, n2, henna.getDrawCount() / 2L, true);
        Log.LogItem(this, Log.ItemLog.HennaRemove, henna.getSymbolId(), 1L);
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean addHenna(Henna henna) {
        if (this.getHennaEmptySlots() == 0) {
            this.sendPacket((IStaticPacket)SystemMsg.NO_SLOT_EXISTS_TO_DRAW_THE_SYMBOL);
            return false;
        }
        for (int i = 0; i < 3; ++i) {
            if (this.a[i] != null) continue;
            this.a[i] = henna;
            this.bo();
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            try {
                connection = DatabaseFactory.getInstance().getConnection();
                preparedStatement = connection.prepareStatement("INSERT INTO `character_hennas` (`char_obj_id`, `symbol_id`, `slot`, `class_index`) VALUES (?,?,?,?)");
                preparedStatement.setInt(1, this.getObjectId());
                preparedStatement.setInt(2, henna.getSymbolId());
                preparedStatement.setInt(3, i + 1);
                preparedStatement.setInt(4, this.getActiveClassId());
                preparedStatement.execute();
            }
            catch (Exception exception) {
                try {
                    bF.warn("could not save char henna: " + exception);
                }
                catch (Throwable throwable) {
                    DbUtils.closeQuietly(connection, preparedStatement);
                    throw throwable;
                }
                DbUtils.closeQuietly(connection, preparedStatement);
            }
            DbUtils.closeQuietly(connection, preparedStatement);
            this.sendPacket((IStaticPacket)new HennaInfo(this));
            this.sendUserInfo(true);
            Log.LogItem(this, Log.ItemLog.HennaAdded, henna.getDyeId(), 1L);
            return true;
        }
        return false;
    }

    private void bo() {
        this.hY = 0;
        this.hX = 0;
        this.ic = 0;
        this.ia = 0;
        this.ib = 0;
        this.hZ = 0;
        for (int i = 0; i < 3; ++i) {
            Henna henna = this.a[i];
            if (henna == null || !henna.isForThisClass(this)) continue;
            this.hY += henna.getStatINT();
            this.hX += henna.getStatSTR();
            this.ia += henna.getStatMEN();
            this.ic += henna.getStatCON();
            this.ib += henna.getStatWIT();
            this.hZ += henna.getStatDEX();
        }
        if (this.hY > Config.LIMIT_HENNA_INT) {
            this.hY = Config.LIMIT_HENNA_INT;
        }
        if (this.hX > Config.LIMIT_HENNA_STR) {
            this.hX = Config.LIMIT_HENNA_STR;
        }
        if (this.ia > Config.LIMIT_HENNA_MEN) {
            this.ia = Config.LIMIT_HENNA_MEN;
        }
        if (this.ic > Config.LIMIT_HENNA_CON) {
            this.ic = Config.LIMIT_HENNA_CON;
        }
        if (this.ib > Config.LIMIT_HENNA_WIT) {
            this.ib = Config.LIMIT_HENNA_WIT;
        }
        if (this.hZ > Config.LIMIT_HENNA_DEX) {
            this.hZ = Config.LIMIT_HENNA_DEX;
        }
    }

    public Henna getHenna(int n) {
        if (n < 1 || n > 3) {
            return null;
        }
        return this.a[n - 1];
    }

    public int getHennaStatINT() {
        return this.hY;
    }

    public int getHennaStatSTR() {
        return this.hX;
    }

    public int getHennaStatCON() {
        return this.ic;
    }

    public int getHennaStatMEN() {
        return this.ia;
    }

    public int getHennaStatWIT() {
        return this.ib;
    }

    public int getHennaStatDEX() {
        return this.hZ;
    }

    @Override
    public boolean consumeItem(int n, long l) {
        if (this.getInventory().destroyItemByItemId(n, l)) {
            this.sendPacket((IStaticPacket)SystemMessage.removeItems(n, l));
            return true;
        }
        return false;
    }

    @Override
    public boolean consumeItemMp(int n, int n2) {
        for (ItemInstance itemInstance : this.getInventory().getPaperdollItems()) {
            if (itemInstance == null || itemInstance.getItemId() != n) continue;
            int n3 = itemInstance.getDuration() - n2;
            if (n3 < 0) break;
            itemInstance.setDuration(n3);
            this.sendPacket((IStaticPacket)new InventoryUpdate().addModifiedItem(itemInstance));
            return true;
        }
        return false;
    }

    @Override
    public boolean isMageClass() {
        ClassId classId = this.getClassId();
        return classId.isMage();
    }

    public boolean isMounted() {
        return this.iF > 0;
    }

    public final boolean isRiding() {
        return this.bS;
    }

    public final void setRiding(boolean bl) {
        this.bS = bl;
    }

    public boolean checkLandingState() {
        if (this.isInZone(Zone.ZoneType.no_landing)) {
            return false;
        }
        SiegeEvent siegeEvent = this.getEvent(SiegeEvent.class);
        if (siegeEvent != null) {
            Object r = siegeEvent.getResidence();
            return r != null && this.getClan() != null && this.isClanLeader() && this.getClan().getCastle() == ((Residence)r).getId();
        }
        return true;
    }

    public void setMount(int n, int n2) {
        Integer n3 = PetDataHolder.getInstance().getMaxLevel(n);
        if (n3 == null) {
            return;
        }
        this.setMount(n, n2, n3);
    }

    public void setMount(int n, int n2, int n3) {
        if (this.isCursedWeaponEquipped()) {
            return;
        }
        if (n == 0) {
            this.setFlying(false);
            this.setRiding(false);
            if (this.getTransformation() > 0) {
                this.setTransformation(0);
            }
            this.removeSkillById(4289);
            this.getEffectList().stopEffect(4258);
        } else {
            PetData petData = PetDataHolder.getInstance().getInfo(n, n3);
            if (petData != null) {
                if (petData.isWyvern()) {
                    this.setFlying(true);
                    this.setLoc(this.getLoc().changeZ(32));
                    this.addSkill(SkillTable.getInstance().getInfo(4289, 1), false);
                } else if (petData.isStrider() || petData.isGreatWolf()) {
                    this.setRiding(true);
                }
            }
        }
        if (n > 0) {
            this.unEquipWeapon();
        }
        this.iF = n;
        this.iG = n2;
        this.iH = n3;
        this.broadcastUserInfo(true, new UserInfoType[0]);
        this.broadcastPacket(new Ride(this));
        this.broadcastUserInfo(true, new UserInfoType[0]);
        this.sendPacket((IStaticPacket)new ExUserInfoAbnormalVisualEffect(this));
        this.sendSkillList();
    }

    public void unEquipWeapon() {
        ItemInstance itemInstance = this.getSecondaryWeaponInstance();
        if (itemInstance != null) {
            this.sendDisarmMessage(itemInstance);
            this.getInventory().unEquipItem(itemInstance);
        }
        if ((itemInstance = this.getActiveWeaponInstance()) != null) {
            this.sendDisarmMessage(itemInstance);
            this.getInventory().unEquipItem(itemInstance);
        }
        this.abortAttack(true, true);
        this.abortCast(true, true);
    }

    @Override
    public int getSpeed(int n) {
        if (this.isMounted()) {
            PetData petData = PetDataHolder.getInstance().getInfo(this.iF, this.iH);
            int n2 = 187;
            if (petData != null) {
                n2 = petData.getSpeed();
            }
            double d = 1.0;
            int n3 = this.getLevel();
            if (this.iH > n3 && n3 - this.iH > 10) {
                d = 0.5;
            }
            n = (int)(d * (double)n2);
        }
        return super.getSpeed(n);
    }

    public int getMountNpcId() {
        return this.iF;
    }

    public int getMountObjId() {
        return this.iG;
    }

    public int getMountLevel() {
        return this.iH;
    }

    public void sendDisarmMessage(ItemInstance itemInstance) {
        if (itemInstance.getEnchantLevel() > 0) {
            SystemMessage systemMessage = new SystemMessage(SystemMsg.THE_EQUIPMENT_S1_S2_HAS_BEEN_REMOVED);
            systemMessage.addNumber(itemInstance.getEnchantLevel());
            systemMessage.addItemName(itemInstance.getItemId());
            this.sendPacket((IStaticPacket)systemMessage);
        } else {
            SystemMessage systemMessage = new SystemMessage(SystemMsg.S1_HAS_BEEN_DISARMED);
            systemMessage.addItemName(itemInstance.getItemId());
            this.sendPacket((IStaticPacket)systemMessage);
        }
    }

    public void setUsingWarehouseType(Warehouse.WarehouseType warehouseType) {
        this.a = warehouseType;
    }

    public Warehouse.WarehouseType getUsingWarehouseType() {
        return this.a;
    }

    public Collection<EffectCubic> getCubics() {
        return this.aH == null ? Collections.emptyList() : this.aH.values();
    }

    public void addCubic(EffectCubic effectCubic) {
        if (this.aH == null) {
            this.aH = new ConcurrentHashMap<Integer, EffectCubic>(3);
        }
        this.aH.put(effectCubic.getId(), effectCubic);
    }

    public void removeCubic(int n) {
        if (this.aH != null) {
            this.aH.remove(n);
        }
    }

    public EffectCubic getCubic(int n) {
        return this.aH == null ? null : this.aH.get(n);
    }

    @Override
    public String toString() {
        return this.getName() + "[" + this.getObjectId() + "]";
    }

    public int getWeaponEnchantEffect() {
        ItemInstance itemInstance = this.getActiveWeaponInstance();
        if (itemInstance == null) {
            return 0;
        }
        return Math.min(127, itemInstance.getEnchantLevel());
    }

    public int getArmorSetEnchantLevel() {
        ItemInstance itemInstance = this.getInventory().getPaperdollItem(6);
        if (itemInstance == null) {
            return 0;
        }
        ArmorSet armorSet = ArmorSetsHolder.getInstance().getArmorSetByChestItemId(itemInstance.getItemId());
        if (armorSet == null) {
            return 0;
        }
        Integer n = armorSet.getEnchantLevel(this);
        if (n == null) {
            return 0;
        }
        return n;
    }

    public void setLastNpc(NpcInstance npcInstance) {
        this.P = npcInstance == null ? HardReferences.emptyRef() : npcInstance.getRef();
    }

    public NpcInstance getLastNpc() {
        return this.P.get();
    }

    public void setMultisell(MultiSellHolder.MultiSellListContainer multiSellListContainer) {
        this.a = multiSellListContainer;
    }

    public MultiSellHolder.MultiSellListContainer getMultisell() {
        return this.a;
    }

    public boolean isFestivalParticipant() {
        return this.getReflection() instanceof DarknessFestival;
    }

    @Override
    public boolean unChargeShots(boolean bl) {
        ItemInstance itemInstance = this.getActiveWeaponInstance();
        if (itemInstance == null) {
            return false;
        }
        if (bl) {
            itemInstance.setChargedSpiritshot(0);
        } else {
            itemInstance.setChargedSoulshot(0);
        }
        this.autoShot();
        return true;
    }

    public boolean unChargeFishShot() {
        ItemInstance itemInstance = this.getActiveWeaponInstance();
        if (itemInstance == null) {
            return false;
        }
        itemInstance.setChargedFishshot(false);
        this.autoShot();
        return true;
    }

    public void autoShot() {
        for (Integer n : this.m) {
            IItemHandler iItemHandler;
            ItemInstance itemInstance = this.getInventory().getItemByItemId(n);
            if (itemInstance == null) {
                this.removeAutoSoulShot(n);
                continue;
            }
            if (!itemInstance.getTemplate().testCondition(this, itemInstance, false) || (iItemHandler = itemInstance.getTemplate().getHandler()) == null) continue;
            iItemHandler.useItem(this, itemInstance, false);
        }
    }

    public boolean getChargedFishShot() {
        ItemInstance itemInstance = this.getActiveWeaponInstance();
        return itemInstance != null && itemInstance.getChargedFishshot();
    }

    @Override
    public boolean getChargedSoulShot() {
        ItemInstance itemInstance = this.getActiveWeaponInstance();
        return itemInstance != null && itemInstance.getChargedSoulshot() == 1;
    }

    @Override
    public int getChargedSpiritShot() {
        ItemInstance itemInstance = this.getActiveWeaponInstance();
        if (itemInstance == null) {
            return 0;
        }
        return itemInstance.getChargedSpiritshot();
    }

    public void addAutoSoulShot(Integer n) {
        this.m.add(n);
        this.getListeners().onAutoSoulShot(n, true);
    }

    public void removeAutoSoulShot(Integer n) {
        this.m.remove(n);
        this.getListeners().onAutoSoulShot(n, false);
    }

    public Set<Integer> getAutoSoulShot() {
        return this.m;
    }

    public void setInvisibleType(InvisibleType invisibleType) {
        this.a = invisibleType;
    }

    @Override
    public InvisibleType getInvisibleType() {
        return this.a;
    }

    public int getClanPrivileges() {
        if (this.a == null) {
            return 0;
        }
        if (this.isClanLeader()) {
            return 0xFFFFFE;
        }
        if (this.ig < 1 || this.ig > 9) {
            return 0;
        }
        RankPrivs rankPrivs = this.a.getRankPrivs(this.ig);
        if (rankPrivs != null) {
            return rankPrivs.getPrivs();
        }
        return 0;
    }

    public void teleToClosestTown() {
        this.teleToLocation(TeleportUtils.getRestartLocation(this, RestartType.TO_VILLAGE), ReflectionManager.DEFAULT);
    }

    public void teleToCastle() {
        this.teleToLocation(TeleportUtils.getRestartLocation(this, RestartType.TO_CASTLE), ReflectionManager.DEFAULT);
    }

    public void teleToClanhall() {
        this.teleToLocation(TeleportUtils.getRestartLocation(this, RestartType.TO_CLANHALL), ReflectionManager.DEFAULT);
    }

    @Override
    public void sendMessage(CustomMessage customMessage) {
        this.sendMessage(customMessage.toString());
    }

    @Override
    public void teleToLocation(int n, int n2, int n3, int n4) {
        if (this.isDeleted()) {
            return;
        }
        super.teleToLocation(n, n2, n3, n4);
    }

    @Override
    public boolean onTeleported() {
        if (!super.onTeleported()) {
            return false;
        }
        if (this.isFakeDeath()) {
            this.breakFakeDeath();
        }
        if (this.isInBoat()) {
            this.setLoc(this.getBoat().getLoc());
        }
        this.setNonAggroTime(System.currentTimeMillis() + Config.NONAGGRO_TIME_ONTELEPORT);
        this.spawnMe();
        this.setLastClientPosition(this.getLoc());
        this.setLastServerPosition(this.getLoc());
        if (this.isPendingRevive()) {
            this.doRevive();
        }
        this.sendActionFailed();
        this.getAI().notifyEvent(CtrlEvent.EVT_TELEPORTED);
        if (this.isLockedTarget() && this.getTarget() != null) {
            this.sendPacket((IStaticPacket)new MyTargetSelected(this.getTarget().getObjectId(), 0));
        }
        this.sendUserInfo(true);
        if (this.getPet() != null) {
            this.getPet().teleportToOwner();
        }
        if (Config.ALT_TELEPORT_PROTECTION && !this.isInAnyZone(Zone.ZoneType.peace_zone, Zone.ZoneType.SIEGE, Zone.ZoneType.offshore) && !this.isOlyParticipant()) {
            this.setAfterTeleportPortectionTime(System.currentTimeMillis() + 1000L * Config.ALT_TELEPORT_PROTECTION_TIME);
            this.sendMessage(new CustomMessage("alt.teleport_protect", this, Config.ALT_TELEPORT_PROTECTION_TIME));
        }
        if (Config.RESEND_MSU_AFTER_TELEPORT > 0) {
            final HardReference<Player> hardReference = this.getRef();
            ThreadPoolManager.getInstance().schedule(new RunnableImpl(){

                @Override
                public void runImpl() throws Exception {
                    Player player = (Player)hardReference.get();
                    if (player == null || player.isTeleporting()) {
                        return;
                    }
                    LinkedList<MagicSkillUse> linkedList = new LinkedList<MagicSkillUse>();
                    for (Player player2 : World.getAroundPlayers(player)) {
                        if (player2 == null || player2.isTeleporting() || !player2.isCastingNow()) continue;
                        Creature creature = player2.getCastingTarget();
                        Skill skill = player2.getCastingSkill();
                        long l = player2.getAnimationEndTime();
                        if (skill == null || creature == null || !creature.isCreature() || l <= 0L) continue;
                        linkedList.add(new MagicSkillUse((Creature)player2, creature, skill, (int)(l - System.currentTimeMillis()), 0L));
                    }
                    player.sendActionFailed();
                    if (!linkedList.isEmpty()) {
                        player.sendPacket(linkedList);
                    }
                }
            }, Config.RESEND_MSU_AFTER_TELEPORT);
        }
        return true;
    }

    public boolean enterObserverMode(Location location) {
        WorldRegion worldRegion = World.getRegion(location);
        if (worldRegion == null) {
            return false;
        }
        if (!this.p.compareAndSet(0, 1)) {
            return false;
        }
        this.setTarget(null);
        this.stopMove();
        this.sitDown(null);
        this.setFlying(true);
        World.removeObjectsFromPlayer(this);
        this.setObserverRegion(worldRegion);
        this.broadcastCharInfo();
        this.sendPacket((IStaticPacket)new ObserverStart(location));
        return true;
    }

    public void appearObserverMode() {
        if (!this.p.compareAndSet(1, 3)) {
            return;
        }
        WorldRegion worldRegion = this.getCurrentRegion();
        WorldRegion worldRegion2 = this.getObserverRegion();
        if (!worldRegion2.equals(worldRegion)) {
            worldRegion2.addObject(this);
        }
        World.showObjectsToPlayer(this);
        if (this.isOlyObserver()) {
            for (Player player : this.getOlyObservingStadium().getPlayers()) {
                if (!player.isOlyCompetitionStarted()) continue;
                this.sendPacket((IStaticPacket)new ExOlympiadUserInfo(player));
            }
        }
    }

    public void leaveObserverMode() {
        if (!this.p.compareAndSet(3, 2)) {
            return;
        }
        WorldRegion worldRegion = this.getCurrentRegion();
        WorldRegion worldRegion2 = this.getObserverRegion();
        if (!worldRegion2.equals(worldRegion)) {
            worldRegion2.removeObject(this);
        }
        World.removeObjectsFromPlayer(this);
        this.setObserverRegion(null);
        this.setTarget(null);
        this.stopMove();
        this.sendPacket((IStaticPacket)new ObserverEnd(this.getLoc()));
    }

    public void returnFromObserverMode() {
        if (!this.p.compareAndSet(2, 0)) {
            return;
        }
        this.setLastClientPosition(null);
        this.setLastServerPosition(null);
        this.unblock();
        this.standUp();
        this.setFlying(false);
        this.broadcastCharInfo();
        World.showObjectsToPlayer(this);
    }

    public void enterOlympiadObserverMode(Stadium stadium) {
        WorldRegion worldRegion = World.getRegion(stadium.getObservingLoc());
        if (worldRegion == null || this.a != null) {
            return;
        }
        if (!this.p.compareAndSet(0, 1)) {
            return;
        }
        this.setTarget(null);
        this.setLastNpc(null);
        this.stopMove();
        this.a = stadium;
        World.removeObjectsFromPlayer(this);
        this.setObserverRegion(worldRegion);
        this.block();
        this.broadcastCharInfo();
        this.setReflection(stadium);
        this.setLastClientPosition(null);
        this.setLastServerPosition(null);
        this.sendPacket(new ExOlympiadMode(3), new TeleportToLocation(this, stadium.getObservingLoc()), new ExTeleportToLocationActivate(this, stadium.getObservingLoc()));
    }

    public void switchOlympiadObserverArena(Stadium stadium) {
        if (this.a == null || stadium == this.a) {
            return;
        }
        WorldRegion worldRegion = World.getRegion(this.a.getObservingLoc());
        if (!this.p.compareAndSet(3, 0)) {
            return;
        }
        if (worldRegion != null) {
            worldRegion.removeObject(this);
            worldRegion.removeFromPlayers(this);
        }
        this.a = null;
        World.removeObjectsFromPlayer(this);
        this.sendPacket((IStaticPacket)new ExOlympiadMode(0));
        this.enterOlympiadObserverMode(stadium);
    }

    public void leaveOlympiadObserverMode() {
        if (this.a == null) {
            return;
        }
        if (!this.p.compareAndSet(3, 2)) {
            return;
        }
        WorldRegion worldRegion = this.getCurrentRegion();
        WorldRegion worldRegion2 = this.getObserverRegion();
        if (worldRegion2 != null && worldRegion != null && !worldRegion2.equals(worldRegion)) {
            worldRegion2.removeObject(this);
        }
        World.removeObjectsFromPlayer(this);
        this.setObserverRegion(null);
        this.a = null;
        this.setTarget(null);
        this.stopMove();
        this.sendPacket((IStaticPacket)new ExOlympiadMode(0));
        this.setReflection(ReflectionManager.DEFAULT);
        this.sendPacket(new TeleportToLocation(this, this.getLoc()), new ExTeleportToLocationActivate(this, this.getLoc()));
    }

    public boolean isOlyObserver() {
        return this.a != null;
    }

    public Stadium getOlyObservingStadium() {
        return this.a;
    }

    @Override
    public boolean isInObserverMode() {
        return this.p.get() > 0;
    }

    public int getObserverMode() {
        return this.p.get();
    }

    public Participant getOlyParticipant() {
        return this.a;
    }

    public void setOlyParticipant(Participant participant) {
        this.a = participant;
    }

    @Override
    public boolean isOlyParticipant() {
        return this.a != null;
    }

    public boolean isOlyCompetitionStarted() {
        return this.isOlyParticipant() && this.a.getCompetition().getState() == CompetitionState.PLAYING;
    }

    public boolean isOlyCompetitionStandby() {
        return this.isOlyParticipant() && this.a.getCompetition().getState() == CompetitionState.STAND_BY;
    }

    public boolean isOlyCompetitionPreparing() {
        return this.isOlyParticipant() && (this.a.getCompetition().getState() == CompetitionState.INIT || this.a.getCompetition().getState() == CompetitionState.STAND_BY);
    }

    public boolean isOlyCompetitionFinished() {
        return this.isOlyParticipant() && this.a.getCompetition().getState() == CompetitionState.FINISH;
    }

    public boolean isLooseOlyCompetition() {
        if (this.isOlyParticipant()) {
            if (this.isOlyCompetitionFinished()) {
                return !this.a.isAlive();
            }
            return this.a.isPlayerLoose(this);
        }
        return false;
    }

    public WorldRegion getObserverRegion() {
        return this.b;
    }

    public void setObserverRegion(WorldRegion worldRegion) {
        this.b = worldRegion;
    }

    public int getTeleMode() {
        return this._telemode;
    }

    public void setTeleMode(int n) {
        this._telemode = n;
    }

    public void setLoto(int n, int n2) {
        this._loto[n] = n2;
    }

    public int getLoto(int n) {
        return this._loto[n];
    }

    public void setRace(int n, int n2) {
        this._race[n] = n2;
    }

    public int getRace(int n) {
        return this._race[n];
    }

    public boolean getMessageRefusal() {
        return this.bO;
    }

    public void setMessageRefusal(boolean bl) {
        this.bO = bl;
    }

    public void setTradeRefusal(boolean bl) {
        this.bP = bl;
    }

    public boolean getTradeRefusal() {
        return this.bP;
    }

    public boolean isPartyRefusal() {
        return this.bQ;
    }

    public void setPartyRefusal(boolean bl) {
        this.bQ = bl;
    }

    public boolean isBlockAll() {
        return this.bR;
    }

    public void setBlockAll(boolean bl) {
        this.bR = bl;
    }

    public CharacterBlockList getBlockList() {
        return this.a;
    }

    public void setHero(boolean bl) {
        this.bT = bl;
    }

    private void bp() {
        if (this.s != null) {
            this.s.cancel(true);
            this.s = null;
        }
    }

    public void setCustomHero(boolean bl, long l, boolean bl2) {
        if (!this.isHero() && bl && l > 0L) {
            this.setVar(CUSTOM_HERO_END_TIME_VAR, System.currentTimeMillis() / 1000L + l, -1L);
            this.setHero(true);
            if (bl2) {
                HeroController.addSkills(this);
            }
            this.s = ThreadPoolManager.getInstance().schedule(new GameObjectTasks.EndCustomHeroTask(this), l * 1000L);
        } else if (!bl) {
            this.unsetVar(CUSTOM_HERO_END_TIME_VAR);
            this.bp();
            if (HeroController.getInstance().isCurrentHero(this)) {
                return;
            }
            this.setHero(false);
            if (bl2) {
                HeroController.removeSkills(this);
            }
            HeroController.checkHeroWeaponary(this);
        }
    }

    public boolean isCustomHero() {
        String string = this.getVar(CUSTOM_HERO_END_TIME_VAR);
        if (string != null) {
            long l = Long.parseLong(string);
            return l > System.currentTimeMillis() / 1000L;
        }
        return false;
    }

    @Override
    public boolean isHero() {
        return this.bT;
    }

    public void updatePremiumSkills() {
        boolean bl = this.hasBonus();
        if (!bl) {
            for (SkillLearn skillLearn : SkillAcquireHolder.getInstance().getAllSkillLearn(this, this.getClassId(), AcquireType.PREMIUM_ACCOUNT)) {
                Skill skill = SkillTable.getInstance().getInfo(skillLearn.getId(), skillLearn.getLevel());
                if (skill == null) continue;
                this.removeSkill(skill, false);
            }
        } else {
            for (SkillLearn skillLearn : SkillAcquireHolder.getInstance().getAvailableSkills(this, AcquireType.PREMIUM_ACCOUNT)) {
                Skill skill = SkillTable.getInstance().getInfo(skillLearn.getId(), skillLearn.getLevel());
                if (skill == null || this.getSkillLevel(skill.getId()) >= skill.getLevel()) continue;
                this.addSkill(skill, false);
            }
        }
    }

    public void updateNobleSkills() {
        boolean bl = this.isNoble();
        if (!bl) {
            for (SkillLearn skillLearn : SkillAcquireHolder.getInstance().getAllSkillLearn(this, this.getClassId(), AcquireType.NOBLES)) {
                Skill skill = SkillTable.getInstance().getInfo(skillLearn.getId(), skillLearn.getLevel());
                if (skill == null) continue;
                this.removeSkill(skill, true);
            }
        } else {
            for (SkillLearn skillLearn : SkillAcquireHolder.getInstance().getAvailableSkills(this, AcquireType.NOBLES)) {
                Skill skill = SkillTable.getInstance().getInfo(skillLearn.getId(), skillLearn.getLevel());
                if (skill == null || this.getSkillLevel(skill.getId()) >= skill.getLevel()) continue;
                this.addSkill(skill, true);
            }
        }
    }

    public void setNoble(boolean bl) {
        this.bV = bl;
        if (bl) {
            this.broadcastPacket(new SocialAction(this.getObjectId(), 20016));
        }
    }

    public boolean isNoble() {
        return this.bV;
    }

    public int getSubLevel() {
        return this.isSubClassActive() ? this.getLevel() : 0;
    }

    public void updateRam() {
        this.ip = ItemFunctions.getItemCount(this, 7247) > 0L ? 2 : (ItemFunctions.getItemCount(this, 7246) > 0L ? 1 : 0);
    }

    public int getRam() {
        return this.ip;
    }

    public void setPledgeType(int n) {
        this.if = n;
    }

    public int getPledgeType() {
        return this.if;
    }

    public void setLvlJoinedAcademy(int n) {
        this.ih = n;
    }

    public int getLvlJoinedAcademy() {
        return this.ih;
    }

    public int getPledgeClass() {
        return this.ie;
    }

    public EPledgeRank getPledgeRank() {
        return EPledgeRank.getPledgeRank(this.getPledgeClass());
    }

    public void updatePledgeClass() {
        int n = this.a == null ? -1 : this.a.getLevel();
        boolean bl = this.a != null && Clan.isAcademy(this.if);
        boolean bl2 = this.a != null && Clan.isRoyalGuard(this.if);
        boolean bl3 = this.a != null && Clan.isOrderOfKnights(this.if);
        boolean bl4 = false;
        boolean bl5 = false;
        boolean bl6 = false;
        SubUnit subUnit = this.getSubUnit();
        if (subUnit != null) {
            UnitMember unitMember = subUnit.getUnitMember(this.getObjectId());
            if (unitMember == null) {
                bF.warn("Player: unitMember null, clan: " + this.a.getClanId() + "; pledgeType: " + subUnit.getType());
                return;
            }
            bl4 = Clan.isRoyalGuard(unitMember.getLeaderOf());
            bl5 = Clan.isOrderOfKnights(unitMember.getLeaderOf());
            bl6 = unitMember.getLeaderOf() == 0;
        }
        switch (n) {
            case -1: {
                this.ie = 0;
                break;
            }
            case 0: 
            case 1: 
            case 2: 
            case 3: {
                if (bl6) {
                    this.ie = 2;
                    break;
                }
                this.ie = 1;
                break;
            }
            case 4: {
                if (bl6) {
                    this.ie = 3;
                    break;
                }
                this.ie = 2;
                break;
            }
            case 5: {
                if (bl6) {
                    this.ie = 4;
                    break;
                }
                if (bl) {
                    this.ie = 1;
                    break;
                }
                this.ie = 2;
                break;
            }
            case 6: {
                if (bl6) {
                    this.ie = 5;
                    break;
                }
                if (bl) {
                    this.ie = 1;
                    break;
                }
                if (bl4) {
                    this.ie = 4;
                    break;
                }
                if (bl2) {
                    this.ie = 2;
                    break;
                }
                this.ie = 3;
                break;
            }
            case 7: {
                if (bl6) {
                    this.ie = 7;
                    break;
                }
                if (bl) {
                    this.ie = 1;
                    break;
                }
                if (bl4) {
                    this.ie = 6;
                    break;
                }
                if (bl2) {
                    this.ie = 3;
                    break;
                }
                if (bl5) {
                    this.ie = 5;
                    break;
                }
                if (bl3) {
                    this.ie = 2;
                    break;
                }
                this.ie = 4;
                break;
            }
            case 8: {
                this.ie = bl6 ? 8 : (bl ? 1 : (bl4 ? 7 : (bl2 ? 4 : (bl5 ? 6 : (bl3 ? 3 : 5)))));
            }
        }
        if (this.bT && this.ie < 8) {
            this.ie = 8;
        } else if (this.bV && this.ie < 5) {
            this.ie = 5;
        }
    }

    public void setPowerGrade(int n) {
        this.ig = n;
    }

    public int getPowerGrade() {
        return this.ig;
    }

    public void setApprentice(int n) {
        this.ii = n;
    }

    public int getApprentice() {
        return this.ii;
    }

    public int getSponsor() {
        return this.a == null ? 0 : this.a.getAnyMember(this.getObjectId()).getSponsor();
    }

    public int getNameColor() {
        if (this.isInObserverMode()) {
            return Color.black.getRGB();
        }
        return this.hR;
    }

    public void setNameColor(int n) {
        if (n != Config.NORMAL_NAME_COLOUR && n != Config.CLANLEADER_NAME_COLOUR && n != Config.GM_NAME_COLOUR && n != Config.SERVICES_OFFLINE_TRADE_NAME_COLOR) {
            this.setVar("namecolor", Integer.toHexString(n), -1L);
        } else if (n == Config.NORMAL_NAME_COLOUR) {
            this.unsetVar("namecolor");
        }
        this.hR = n;
    }

    public void setVar(String string, String string2, long l) {
        ((HashMap)((Object)this.a)).put(string, string2);
        CharacterVariablesDAO.getInstance().setVar(this.getObjectId(), string, string2, l);
    }

    public void setVar(String string, int n, long l) {
        this.setVar(string, String.valueOf(n), l);
    }

    public void setVar(String string, long l, long l2) {
        this.setVar(string, String.valueOf(l), l2);
    }

    public void unsetVar(String string) {
        if (string == null) {
            return;
        }
        if (((HashMap)((Object)this.a)).remove(string) != null) {
            CharacterVariablesDAO.getInstance().deleteVar(this.getObjectId(), string);
        }
    }

    public String getVar(String string) {
        return ((MultiValueSet)((Object)this.a)).getString(string, null);
    }

    public boolean getVarB(String string, boolean bl) {
        String string2 = ((MultiValueSet)((Object)this.a)).getString(string, null);
        if (string2 == null) {
            return bl;
        }
        return !string2.equals("0") && !string2.equalsIgnoreCase("false");
    }

    public boolean getVarB(String string) {
        String string2 = ((MultiValueSet)((Object)this.a)).getString(string, null);
        return string2 != null && !string2.equals("0") && !string2.equalsIgnoreCase("false");
    }

    public long getVarLong(String string) {
        return this.getVarLong(string, 0L);
    }

    public long getVarLong(String string, long l) {
        long l2 = l;
        String string2 = this.getVar(string);
        if (string2 != null) {
            l2 = Long.parseLong(string2);
        }
        return l2;
    }

    public int getVarInt(String string) {
        return this.getVarInt(string, 0);
    }

    public int getVarInt(String string, int n) {
        int n2 = n;
        String string2 = this.getVar(string);
        if (string2 != null) {
            n2 = Integer.parseInt(string2);
        }
        return n2;
    }

    public MultiValueSet<String> getVars() {
        return this.a;
    }

    public String getLang() {
        return this.getVar("lang@");
    }

    public String getHWIDLock() {
        return this.getVar("hwidlock@");
    }

    public void setHWIDLock(String string) {
        if (string == null) {
            this.unsetVar("hwidlock@");
        } else {
            this.setVar("hwidlock@", string, -1L);
        }
    }

    public String getIPLock() {
        return this.getVar("iplock@");
    }

    public void setIPLock(String string) {
        if (string == null) {
            this.unsetVar("iplock@");
        } else {
            this.setVar("iplock@", string, -1L);
        }
    }

    public int getLangId() {
        String string = this.getLang();
        if (string == null || string.equalsIgnoreCase("en") || string.equalsIgnoreCase("e") || string.equalsIgnoreCase("eng")) {
            return 0;
        }
        if (string.equalsIgnoreCase("ru") || string.equalsIgnoreCase("r") || string.equalsIgnoreCase("rus")) {
            return 1;
        }
        return -1;
    }

    public Language getLanguage() {
        String string = this.getLang();
        if (string == null || string.equalsIgnoreCase("en") || string.equalsIgnoreCase("e") || string.equalsIgnoreCase("eng")) {
            return Language.ENGLISH;
        }
        if (string.equalsIgnoreCase("ru") || string.equalsIgnoreCase("r") || string.equalsIgnoreCase("rus")) {
            return Language.RUSSIAN;
        }
        return Language.ENGLISH;
    }

    public boolean isLangRus() {
        return this.getLangId() == 1;
    }

    public int isAtWarWith(Integer n) {
        return this.a == null || !this.a.isAtWarWith(n) ? 0 : 1;
    }

    public int isAtWar() {
        return this.a == null || this.a.isAtWarOrUnderAttack() <= 0 ? 0 : 1;
    }

    public void stopWaterTask() {
        if (this.m != null) {
            this.m.cancel(false);
            this.m = null;
            this.sendPacket((IStaticPacket)new SetupGauge(this, 2, 0));
            this.sendChanges();
        }
    }

    public void startWaterTask() {
        if (this.isDead()) {
            this.stopWaterTask();
        } else if (Config.ALLOW_WATER && this.m == null) {
            int n = (int)(this.calcStat(Stats.BREATH, 86.0, null, null) * 1000.0);
            this.sendPacket((IStaticPacket)new SetupGauge(this, 2, n));
            if (this.getTransformation() > 0 && this.getTransformationTemplate() > 0 && !this.isCursedWeaponEquipped()) {
                this.setTransformation(0);
            }
            this.m = ThreadPoolManager.getInstance().scheduleAtFixedRate(new GameObjectTasks.WaterTask(this), n, 1000L);
            this.sendChanges();
        }
    }

    public void doRevive(double d) {
        this.restoreExp(d);
        this.doRevive();
    }

    @Override
    public void doRevive() {
        super.doRevive();
        this.unsetVar("lostexp");
        this.updateEffectIcons();
        this.autoShot();
    }

    public void reviveRequest(Player player, double d, boolean bl, int n) {
        ReviveAnswerListener reviveAnswerListener;
        ReviveAnswerListener reviveAnswerListener2 = reviveAnswerListener = this.a != null && this.a.getValue() instanceof ReviveAnswerListener ? (ReviveAnswerListener)this.a.getValue() : null;
        if (reviveAnswerListener != null) {
            if (reviveAnswerListener.isForPet() == bl && reviveAnswerListener.getPower() >= d) {
                player.sendPacket((IStaticPacket)SystemMsg.RESURRECTION_HAS_ALREADY_BEEN_PROPOSED);
                return;
            }
            if (bl && !reviveAnswerListener.isForPet()) {
                player.sendPacket((IStaticPacket)SystemMsg.A_PET_CANNOT_BE_RESURRECTED_WHILE_ITS_OWNER_IS_IN_THE_PROCESS_OF_RESURRECTING);
                return;
            }
            if (bl && this.isDead()) {
                player.sendPacket((IStaticPacket)SystemMsg.WHILE_A_PET_IS_BEING_RESURRECTED_IT_CANNOT_HELP_IN_RESURRECTING_ITS_MASTER);
                return;
            }
        }
        if (bl && this.getPet() != null && this.getPet().isDead() || !bl && this.isDead()) {
            ConfirmDlg confirmDlg = new ConfirmDlg(SystemMsg.C1_IS_MAKING_AN_ATTEMPT_TO_RESURRECT_YOU_IF_YOU_CHOOSE_THIS_PATH_S2_EXPERIENCE_WILL_BE_RETURNED_FOR_YOU, n);
            ((ConfirmDlg)confirmDlg.addName(player)).addString(Math.round(d) + " percent");
            this.ask(confirmDlg, new ReviveAnswerListener(this, d, bl, n));
        }
    }

    public void summonCharacterRequest(Creature creature, Location location, int n) {
        ConfirmDlg confirmDlg = new ConfirmDlg(SystemMsg.C1_WISHES_TO_SUMMON_YOU_FROM_S2, 60000);
        ((ConfirmDlg)confirmDlg.addName(creature)).addZoneName(location);
        this.ask(confirmDlg, new SummonAnswerListener(this, location, n, 60000));
    }

    public void scriptRequest(String string, String string2, Object[] objectArray) {
        this.ask((ConfirmDlg)new ConfirmDlg(SystemMsg.S1, 30000).addString(string), new ScriptAnswerListener(this, string2, objectArray, 30000L));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void updateNoChannel(long l) {
        this.setNoChannel(l);
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("UPDATE characters SET nochannel = ? WHERE obj_Id=?");
            preparedStatement.setLong(1, this.bp > 0L ? this.bp / 1000L : this.bp);
            preparedStatement.setInt(2, this.getObjectId());
            preparedStatement.executeUpdate();
        }
        catch (Exception exception) {
            try {
                bF.warn("Could not activate nochannel:" + exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement);
        }
        DbUtils.closeQuietly(connection, preparedStatement);
        this.sendPacket((IStaticPacket)new EtcStatusUpdate(this));
    }

    public boolean canTalkWith(Player player) {
        return this.bp >= 0L || player == this;
    }

    public Deque<ChatMsg> getMessageBucket() {
        return this.a;
    }

    @Override
    public boolean isInBoat() {
        return this.a != null;
    }

    public Boat getBoat() {
        return this.a;
    }

    public void setBoat(Boat boat) {
        this.a = boat;
    }

    @Override
    protected L2GameServerPacket stopMovePacket() {
        if (this.isInBoat()) {
            this.getBoat().inStopMovePacket(this);
        }
        return super.stopMovePacket();
    }

    public Location getInBoatPosition() {
        return this.z;
    }

    public void setInBoatPosition(Location location) {
        this.z = location;
    }

    public Map<Integer, SubClass> getSubClasses() {
        return this._classlist;
    }

    public void setBaseClassId(int n) {
        this._baseClass = n;
    }

    public SubClass getBaseSubClass() {
        for (Map.Entry<Integer, SubClass> entry : this.getSubClasses().entrySet()) {
            if (!entry.getValue().isBase()) continue;
            return entry.getValue();
        }
        throw new IllegalStateException("No base subclass for player " + this);
    }

    public int getBaseClassId() {
        return this._baseClass;
    }

    public void setActiveClass(SubClass subClass) {
        this._activeClass = subClass;
    }

    public SubClass getActiveClass() {
        return this._activeClass;
    }

    public int getActiveClassId() {
        return this.getActiveClass().getClassId();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public synchronized void changeClassInDb(int n, int n2) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("UPDATE `character_subclasses` SET `class_id`=? WHERE `char_obj_id`=? AND `class_id`=?");
            preparedStatement.setInt(1, n2);
            preparedStatement.setInt(2, this.getObjectId());
            preparedStatement.setInt(3, n);
            preparedStatement.executeUpdate();
            DbUtils.close(preparedStatement);
            preparedStatement = connection.prepareStatement("DELETE FROM `character_hennas` WHERE `char_obj_id`=? AND `class_index`=?");
            preparedStatement.setInt(1, this.getObjectId());
            preparedStatement.setInt(2, n2);
            preparedStatement.executeUpdate();
            DbUtils.close(preparedStatement);
            preparedStatement = connection.prepareStatement("UPDATE `character_hennas` SET `class_index`=? WHERE `char_obj_id`=? AND `class_index`=?");
            preparedStatement.setInt(1, n2);
            preparedStatement.setInt(2, this.getObjectId());
            preparedStatement.setInt(3, n);
            preparedStatement.executeUpdate();
            DbUtils.close(preparedStatement);
            preparedStatement = connection.prepareStatement("DELETE FROM `character_shortcuts` WHERE `object_id`=? AND `class_index`=?");
            preparedStatement.setInt(1, this.getObjectId());
            preparedStatement.setInt(2, n2);
            preparedStatement.executeUpdate();
            DbUtils.close(preparedStatement);
            preparedStatement = connection.prepareStatement("UPDATE `character_shortcuts` SET `class_index`=? WHERE `object_id`=? AND `class_index`=?");
            preparedStatement.setInt(1, n2);
            preparedStatement.setInt(2, this.getObjectId());
            preparedStatement.setInt(3, n);
            preparedStatement.executeUpdate();
            DbUtils.close(preparedStatement);
            preparedStatement = connection.prepareStatement("DELETE FROM `character_skills` WHERE `char_obj_id`=? AND `class_index`=?");
            preparedStatement.setInt(1, this.getObjectId());
            preparedStatement.setInt(2, n2);
            preparedStatement.executeUpdate();
            DbUtils.close(preparedStatement);
            preparedStatement = connection.prepareStatement("UPDATE `character_skills` SET `class_index`=? WHERE `char_obj_id`=? AND `class_index`=?");
            preparedStatement.setInt(1, n2);
            preparedStatement.setInt(2, this.getObjectId());
            preparedStatement.setInt(3, n);
            preparedStatement.executeUpdate();
            DbUtils.close(preparedStatement);
            preparedStatement = connection.prepareStatement("DELETE FROM `character_effects_save` WHERE `object_id`=? AND `id`=?");
            preparedStatement.setInt(1, this.getObjectId());
            preparedStatement.setInt(2, n2);
            preparedStatement.executeUpdate();
            DbUtils.close(preparedStatement);
            preparedStatement = connection.prepareStatement("UPDATE `character_effects_save` SET `id`=? WHERE `object_id`=? AND `id`=?");
            preparedStatement.setInt(1, n2);
            preparedStatement.setInt(2, this.getObjectId());
            preparedStatement.setInt(3, n);
            preparedStatement.executeUpdate();
            DbUtils.close(preparedStatement);
            preparedStatement = connection.prepareStatement("DELETE FROM `character_skills_save` WHERE `char_obj_id`=? AND `class_index`=?");
            preparedStatement.setInt(1, this.getObjectId());
            preparedStatement.setInt(2, n2);
            preparedStatement.executeUpdate();
            DbUtils.close(preparedStatement);
            preparedStatement = connection.prepareStatement("UPDATE `character_skills_save` SET `class_index`=? WHERE `char_obj_id`=? AND `class_index`=?");
            preparedStatement.setInt(1, n2);
            preparedStatement.setInt(2, this.getObjectId());
            preparedStatement.setInt(3, n);
            preparedStatement.executeUpdate();
            DbUtils.close(preparedStatement);
        }
        catch (SQLException sQLException) {
            try {
                bF.error("", (Throwable)sQLException);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement);
        }
        DbUtils.closeQuietly(connection, preparedStatement);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void storeCharSubClasses() {
        SubClass subClass = this.getActiveClass();
        if (subClass != null) {
            subClass.setCp(this.getCurrentCp());
            subClass.setHp(this.getCurrentHp());
            subClass.setMp(this.getCurrentMp());
            subClass.setActive(true);
            this.getSubClasses().put(this.getActiveClassId(), subClass);
        } else {
            bF.warn("Could not store char sub data, main class " + this.getActiveClassId() + " not found for " + this);
        }
        Connection connection = null;
        Statement statement = null;
        try {
            StringBuilder stringBuilder;
            connection = DatabaseFactory.getInstance().getConnection();
            statement = connection.createStatement();
            for (SubClass subClass2 : this.getSubClasses().values()) {
                stringBuilder = new StringBuilder("UPDATE character_subclasses SET ");
                stringBuilder.append("exp=").append(subClass2.getExp()).append(",");
                stringBuilder.append("sp=").append(subClass2.getSp()).append(",");
                stringBuilder.append("curHp=").append(subClass2.getHp()).append(",");
                stringBuilder.append("curMp=").append(subClass2.getMp()).append(",");
                stringBuilder.append("curCp=").append(subClass2.getCp()).append(",");
                stringBuilder.append("level=").append(subClass2.getLevel()).append(",");
                stringBuilder.append("active=").append(subClass2.isActive() ? 1 : 0).append(",");
                stringBuilder.append("isBase=").append(subClass2.isBase() ? 1 : 0).append(",");
                stringBuilder.append("death_penalty=").append(subClass2.getDeathPenalty(this).getLevelOnSaveDB());
                stringBuilder.append(" WHERE char_obj_id=").append(this.getObjectId()).append(" AND class_id=").append(subClass2.getClassId()).append(" LIMIT 1");
                statement.executeUpdate(stringBuilder.toString());
            }
            stringBuilder = new StringBuilder("UPDATE character_subclasses SET ");
            stringBuilder.append("maxHp=").append(this.getMaxHp()).append(",");
            stringBuilder.append("maxMp=").append(this.getMaxMp()).append(",");
            stringBuilder.append("maxCp=").append(this.getMaxCp());
            stringBuilder.append(" WHERE char_obj_id=").append(this.getObjectId()).append(" AND active=1 LIMIT 1");
            statement.executeUpdate(stringBuilder.toString());
        }
        catch (Exception exception) {
            try {
                bF.warn("Could not store char sub data: " + exception);
                bF.error("", (Throwable)exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, statement);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, statement);
        }
        DbUtils.closeQuietly(connection, statement);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void restoreCharSubClasses(Player player) {
        ResultSet resultSet;
        PreparedStatement preparedStatement;
        Connection connection;
        block9: {
            connection = null;
            preparedStatement = null;
            resultSet = null;
            try {
                connection = DatabaseFactory.getInstance().getConnection();
                preparedStatement = connection.prepareStatement("SELECT `class_id`,`exp`,`sp`,`curHp`,`curCp`,`curMp`,`active`,`isBase`,`death_penalty` FROM `character_subclasses` WHERE `char_obj_id`=?");
                preparedStatement.setInt(1, player.getObjectId());
                resultSet = preparedStatement.executeQuery();
                SubClass subClass = null;
                while (resultSet.next()) {
                    boolean bl;
                    SubClass subClass2 = new SubClass();
                    subClass2.setBase(resultSet.getInt("isBase") != 0);
                    subClass2.setClassId(resultSet.getInt("class_id"));
                    subClass2.setExp(resultSet.getLong("exp"));
                    subClass2.setSp(resultSet.getInt("sp"));
                    subClass2.setHp(resultSet.getDouble("curHp"));
                    subClass2.setMp(resultSet.getDouble("curMp"));
                    subClass2.setCp(resultSet.getDouble("curCp"));
                    subClass2.setDeathPenalty(new DeathPenalty(player, resultSet.getInt("death_penalty")));
                    boolean bl2 = bl = resultSet.getInt("active") != 0;
                    if (bl) {
                        subClass = subClass2;
                    }
                    player.getSubClasses().put(subClass2.getClassId(), subClass2);
                }
                if (player.getSubClasses().size() == 0) {
                    throw new Exception("There are no one subclass for player: " + player);
                }
                int n = player.getBaseClassId();
                if (n == -1) {
                    throw new Exception("There are no base subclass for player: " + player);
                }
                if (subClass != null) {
                    player.setActiveSubClass(subClass.getClassId(), false);
                }
                if (player.getActiveClass() != null) break block9;
                SubClass subClass3 = player.getSubClasses().get(n);
                subClass3.setActive(true);
                player.setActiveSubClass(subClass3.getClassId(), false);
            }
            catch (Exception exception) {
                try {
                    bF.warn("Could not restore char sub-classes: " + exception);
                    bF.error("", (Throwable)exception);
                }
                catch (Throwable throwable) {
                    DbUtils.closeQuietly(connection, preparedStatement, resultSet);
                    throw throwable;
                }
                DbUtils.closeQuietly(connection, preparedStatement, resultSet);
            }
        }
        DbUtils.closeQuietly(connection, preparedStatement, resultSet);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean addSubClass(int n, boolean bl) {
        if (this._classlist.size() >= Config.ALT_GAME_BASE_SUB) {
            return false;
        }
        ClassId classId = ClassId.VALUES[n];
        SubClass subClass = new SubClass();
        subClass.setBase(false);
        if (classId.getRace() == null) {
            return false;
        }
        subClass.setClassId(n);
        this._classlist.put(n, subClass);
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO  `character_subclasses`  (\t`char_obj_id`,   `class_id`,   `exp`,   `sp`,   `curHp`,   `curMp`,   `curCp`,   `maxHp`,   `maxMp`,   `maxCp`,   `level`,   `active`,   `isBase`,   `death_penalty`)VALUES  (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            preparedStatement.setInt(1, this.getObjectId());
            preparedStatement.setInt(2, subClass.getClassId());
            preparedStatement.setLong(3, subClass.getExp());
            preparedStatement.setInt(4, 0);
            preparedStatement.setDouble(5, this.getCurrentHp());
            preparedStatement.setDouble(6, this.getCurrentMp());
            preparedStatement.setDouble(7, this.getCurrentCp());
            preparedStatement.setDouble(8, this.getCurrentHp());
            preparedStatement.setDouble(9, this.getCurrentMp());
            preparedStatement.setDouble(10, this.getCurrentCp());
            preparedStatement.setInt(11, subClass.getLevel());
            preparedStatement.setInt(12, 0);
            preparedStatement.setInt(13, 0);
            preparedStatement.setInt(14, 0);
            preparedStatement.execute();
        }
        catch (Exception exception) {
            boolean bl2;
            try {
                bF.warn("Could not add character sub-class: " + exception, (Throwable)exception);
                bl2 = false;
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement);
            return bl2;
        }
        DbUtils.closeQuietly(connection, preparedStatement);
        this.setActiveSubClass(n, bl);
        boolean bl3 = true;
        int n2 = 0;
        Collection<SkillLearn> collection = SkillAcquireHolder.getInstance().getAvailableSkills(this, AcquireType.NORMAL);
        while (collection.size() > n2) {
            for (SkillLearn skillLearn : collection) {
                Skill skill = SkillTable.getInstance().getInfo(skillLearn.getId(), skillLearn.getLevel());
                if (skill == null || !skill.getCanLearn(classId)) {
                    if (!bl3) continue;
                    ++n2;
                    continue;
                }
                this.addSkill(skill, true);
            }
            bl3 = false;
            collection = SkillAcquireHolder.getInstance().getAvailableSkills(this, AcquireType.NORMAL);
        }
        this.sendSkillList();
        this.setCurrentHpMp(this.getMaxHp(), this.getMaxMp(), true);
        this.setCurrentCp(this.getMaxCp());
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean modifySubClass(int n, int n2) {
        SubClass subClass = this._classlist.get(n);
        if (subClass == null || subClass.isBase()) {
            return false;
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM `character_subclasses` WHERE `char_obj_id`=? AND `class_id`=? AND `isBase` = 0");
            preparedStatement.setInt(1, this.getObjectId());
            preparedStatement.setInt(2, n);
            preparedStatement.execute();
            DbUtils.close(preparedStatement);
            preparedStatement = connection.prepareStatement("DELETE FROM `character_skills` WHERE `char_obj_id`=? AND `class_index`=?");
            preparedStatement.setInt(1, this.getObjectId());
            preparedStatement.setInt(2, n);
            preparedStatement.execute();
            DbUtils.close(preparedStatement);
            preparedStatement = connection.prepareStatement("DELETE FROM `character_skills_save` WHERE `char_obj_id`=? AND `class_index`=?");
            preparedStatement.setInt(1, this.getObjectId());
            preparedStatement.setInt(2, n);
            preparedStatement.execute();
            DbUtils.close(preparedStatement);
            preparedStatement = connection.prepareStatement("DELETE FROM `character_effects_save` WHERE `object_id`=? AND `id`=?");
            preparedStatement.setInt(1, this.getObjectId());
            preparedStatement.setInt(2, n);
            preparedStatement.execute();
            DbUtils.close(preparedStatement);
            preparedStatement = connection.prepareStatement("DELETE FROM `character_hennas` WHERE `char_obj_id`=? AND `class_index`=?");
            preparedStatement.setInt(1, this.getObjectId());
            preparedStatement.setInt(2, n);
            preparedStatement.execute();
            DbUtils.close(preparedStatement);
            preparedStatement = connection.prepareStatement("DELETE FROM `character_shortcuts` WHERE `object_id`=? AND `class_index`=?");
            preparedStatement.setInt(1, this.getObjectId());
            preparedStatement.setInt(2, n);
            preparedStatement.execute();
            DbUtils.close(preparedStatement);
        }
        catch (Exception exception) {
            try {
                bF.warn("Could not delete char sub-class: " + exception);
                bF.error("", (Throwable)exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement);
        }
        DbUtils.closeQuietly(connection, preparedStatement);
        this._classlist.remove(n);
        return n2 <= 0 || this.addSubClass(n2, false);
    }

    public void setActiveSubClass(int n, boolean bl) {
        Object object;
        SubClass subClass = this.getSubClasses().get(n);
        if (subClass == null) {
            return;
        }
        try {
            if (this.getActiveClass() != null) {
                Object object2;
                EffectsDAO.getInstance().insert(this);
                this.storeDisableSkills();
                if (QuestManager.getQuest(422) != null && (object = QuestManager.getQuest(422).getName()) != null && (object2 = this.getQuestState((String)object)) != null) {
                    ((QuestState)object2).exitCurrentQuest(true);
                }
            }
        }
        catch (Exception exception) {
            bF.warn("", (Throwable)exception);
        }
        object = this.getActiveClass();
        if (object != null) {
            ((SubClass)object).setActive(false);
            if (bl) {
                ((SubClass)object).setCp(this.getCurrentCp());
                ((SubClass)object).setHp(this.getCurrentHp());
                ((SubClass)object).setMp(this.getCurrentMp());
                this.getSubClasses().put(this.getActiveClassId(), (SubClass)object);
            }
        }
        subClass.setActive(true);
        this.setActiveClass(subClass);
        this.getSubClasses().put(this.getActiveClassId(), subClass);
        this.setClassId(n, false, false);
        this.removeAllSkills();
        this.getEffectList().stopAllEffects();
        if (this.getPet() != null && (this.getPet().isSummon() || Config.ALT_IMPROVED_PETS_LIMITED_USE && (PetDataHolder.getInstance().getInfo(this.getPet().getNpcId()).isImprovedBabyKookaburra() && !this.isMageClass() || PetDataHolder.getInstance().getInfo(this.getPet().getNpcId()).isImprovedBabyBuffalo() && this.isMageClass()))) {
            this.getPet().unSummon();
        }
        this.setAgathion(0);
        this.restoreSkills();
        if (Config.ALT_SUBLASS_SKILL_TRANSFER && this.getActiveClassId() == n) {
            for (SubClass subClass2 : this.getSubClasses().values()) {
                if (subClass2.getClassId() == n) continue;
                this.k(subClass2.getClassId());
            }
        }
        this.a(false, 0);
        this.im = 0;
        this.il = 0;
        this.checkSkills();
        this.sendPacket((IStaticPacket)new ExStorageMaxCount(this));
        this.refreshExpertisePenalty();
        this.sendSkillList();
        this.getInventory().refreshEquip();
        this.getInventory().validateItems();
        for (int i = 0; i < 3; ++i) {
            this.a[i] = null;
        }
        this.bn();
        this.sendPacket((IStaticPacket)new HennaInfo(this));
        EffectsDAO.getInstance().restoreEffects(this);
        this.restoreDisableSkills();
        this.setCurrentHpMp(subClass.getHp(), subClass.getMp());
        this.setCurrentCp(subClass.getCp());
        this.a.restore();
        this.sendPacket((IStaticPacket)new ShortCutInit(this));
        for (int n2 : this.getAutoSoulShot()) {
            this.sendPacket((IStaticPacket)new ExAutoSoulShot(n2, true, 0));
        }
        this.sendPacket((IStaticPacket)new SkillCoolTime(this));
        this.broadcastPacket(new SocialAction(this.getObjectId(), 2122));
        this.getDeathPenalty().restore(this);
        this.setIncreasedForce(0);
        this.startHourlyTask();
        this.broadcastCharInfo();
        this.updateEffectIcons();
        this.updateStats();
    }

    public void startKickTask(long l) {
        this.stopKickTask();
        this.o = ThreadPoolManager.getInstance().schedule(new GameObjectTasks.KickTask(this), l);
    }

    public void stopKickTask() {
        if (this.o != null) {
            this.o.cancel(false);
            this.o = null;
        }
    }

    public void startBonusTask() {
        if (Config.SERVICES_RATE_ENABLED) {
            AccountBonusDAO.getInstance().load(this.getAccountName(), this.getBonus());
            long l = this.getBonus().getBonusExpire();
            if (l > System.currentTimeMillis() / 1000L) {
                if (this.l == null) {
                    this.l = LazyPrecisionTaskManager.getInstance().startBonusExpirationTask(this);
                    this.updatePremiumSkills();
                    this.sendSkillList();
                }
            } else if (l > 0L) {
                AccountBonusDAO.getInstance().delete(this.getAccountName());
            }
        }
    }

    public void stopBonusTask() {
        if (this.l != null) {
            this.l.cancel(false);
            this.l = null;
        }
    }

    @Override
    public int getInventoryLimit() {
        return (int)this.calcStat(Stats.INVENTORY_LIMIT, 0.0, null, null);
    }

    public int getWarehouseLimit() {
        return (int)this.calcStat(Stats.STORAGE_LIMIT, 0.0, null, null);
    }

    public int getTradeLimit() {
        return (int)this.calcStat(Stats.TRADE_LIMIT, 0.0, null, null);
    }

    public int getDwarvenRecipeLimit() {
        return (int)this.calcStat(Stats.DWARVEN_RECIPE_LIMIT, 50.0, null, null) + Config.ALT_ADD_RECIPES;
    }

    public int getCommonRecipeLimit() {
        return (int)this.calcStat(Stats.COMMON_RECIPE_LIMIT, 50.0, null, null) + Config.ALT_ADD_RECIPES;
    }

    public int getBeltInventoryIncrease() {
        ItemInstance itemInstance = this.getInventory().getPaperdollItem(29);
        if (itemInstance != null && itemInstance.getTemplate().getAttachedSkills() != null) {
            for (Skill skill : itemInstance.getTemplate().getAttachedSkills()) {
                for (FuncTemplate funcTemplate : skill.getAttachedFuncs()) {
                    if (funcTemplate._stat != Stats.INVENTORY_LIMIT) continue;
                    return (int)funcTemplate._value;
                }
            }
        }
        return 0;
    }

    public Element getAttackElement() {
        return Formulas.getAttackElement(this, null);
    }

    public int getAttack(Element element) {
        if (element == Element.NONE) {
            return 0;
        }
        return (int)this.calcStat(element.getAttack(), 0.0, null, null);
    }

    public int getDefence(Element element) {
        if (element == Element.NONE) {
            return 0;
        }
        return (int)this.calcStat(element.getDefence(), 0.0, null, null);
    }

    public boolean getAndSetLastItemAuctionRequest() {
        if (this.bx + 2000L < System.currentTimeMillis()) {
            this.bx = System.currentTimeMillis();
            return true;
        }
        this.bx = System.currentTimeMillis();
        return false;
    }

    @Override
    public int getNpcId() {
        return -2;
    }

    public GameObject getVisibleObject(int n) {
        if (this.getObjectId() == n) {
            return this;
        }
        GameObject gameObject = null;
        if (this.getTargetId() == n) {
            gameObject = this.getTarget();
        }
        if (gameObject == null && this._party != null) {
            for (Player player : this._party.getPartyMembers()) {
                if (player == null || player.getObjectId() != n) continue;
                gameObject = player;
                break;
            }
        }
        if (gameObject == null) {
            gameObject = World.getAroundObjectById(this, n);
        }
        return gameObject == null || gameObject.isInvisible() ? null : gameObject;
    }

    @Override
    public int getPAtk(Creature creature) {
        double d = this.getActiveWeaponInstance() == null ? (double)(this.isMageClass() ? 3 : 4) : 0.0;
        return (int)this.calcStat(Stats.POWER_ATTACK, d, creature, null);
    }

    @Override
    public int getPDef(Creature creature) {
        double d = 4.0;
        ItemInstance itemInstance = this.getInventory().getPaperdollItem(6);
        if (itemInstance == null) {
            d += this.isMageClass() ? 15.0 : 31.0;
        }
        if (this.getInventory().getPaperdollItem(11) == null && (itemInstance == null || itemInstance.getBodyPart() != 32768L)) {
            d += this.isMageClass() ? 8.0 : 18.0;
        }
        if (this.getInventory().getPaperdollItem(1) == null) {
            d += 12.0;
        }
        if (this.getInventory().getPaperdollItem(10) == null) {
            d += 8.0;
        }
        if (this.getInventory().getPaperdollItem(12) == null) {
            d += 7.0;
        }
        return (int)this.calcStat(Stats.POWER_DEFENCE, d, creature, null);
    }

    @Override
    public int getMDef(Creature creature, Skill skill) {
        double d = 0.0;
        if (this.getInventory().getPaperdollItem(9) == null) {
            d += 9.0;
        }
        if (this.getInventory().getPaperdollItem(8) == null) {
            d += 9.0;
        }
        if (this.getInventory().getPaperdollItem(4) == null) {
            d += 13.0;
        }
        if (this.getInventory().getPaperdollItem(14) == null) {
            d += 5.0;
        }
        if (this.getInventory().getPaperdollItem(13) == null) {
            d += 5.0;
        }
        return (int)this.calcStat(Stats.MAGIC_DEFENCE, d, creature, skill);
    }

    public boolean isSubClassActive() {
        return !this.getActiveClass().isBase();
    }

    @Override
    public String getTitle() {
        return super.getTitle();
    }

    public int getTitleColor() {
        return this.hS;
    }

    public void setTitleColor(int n) {
        if (n != 0xFFFF77) {
            this.setVar("titlecolor", Integer.toHexString(n), -1L);
        } else {
            this.unsetVar("titlecolor");
        }
        this.hS = n;
    }

    public String getDisconnectedTitle() {
        return this.cN;
    }

    public void setDisconnectedTitle(String string) {
        this.cN = string;
    }

    public int getDisconnectedTitleColor() {
        return this.hU;
    }

    public void setDisconnectedTitleColor(int n) {
        this.hU = n;
    }

    @Override
    public boolean isCursedWeaponEquipped() {
        return this.iq != 0;
    }

    public void setCursedWeaponEquippedId(int n) {
        this.iq = n;
    }

    public int getCursedWeaponEquippedId() {
        return this.iq;
    }

    @Override
    public boolean isImmobilized() {
        return super.isImmobilized() || this.isOverloaded() || this.isSitting() || this.isFishing();
    }

    @Override
    public boolean isBlocked() {
        return super.isBlocked() || this.isInMovie() || this.isInObserverMode() || this.isTeleporting() || this.isLogoutStarted();
    }

    @Override
    public boolean isInvul() {
        return super.isInvul() || this.isInMovie() || this.getAfterTeleportPortectionTime() > System.currentTimeMillis() || this.getNoCarrierProtectionTime() > System.currentTimeMillis();
    }

    public boolean isResurectProhibited() {
        return this.cf;
    }

    public void setResurectProhibited(boolean bl) {
        this.cf = bl;
    }

    public void setOverloaded(boolean bl) {
        this.bK = bl;
    }

    public boolean isOverloaded() {
        return this.bK;
    }

    public boolean isFishing() {
        return this.bW;
    }

    public Fishing getFishing() {
        return this.a;
    }

    public void setFishing(boolean bl) {
        this.bW = bl;
    }

    public void startFishing(FishTemplate fishTemplate, int n) {
        this.a.setFish(fishTemplate);
        this.a.setLureId(n);
        this.a.startFishing();
    }

    public void stopFishing() {
        this.a.stopFishing();
    }

    public Location getFishLoc() {
        return this.a.getFishLoc();
    }

    public Bonus getBonus() {
        return this.a;
    }

    public boolean hasBonus() {
        return !this.a.isExpired();
    }

    @Override
    public double getRateAdena() {
        return this.calcStat(Stats.ADENA_REWARD_MULTIPLIER, this._party == null ? (double)this.a.getDropAdena() : this._party._rateAdena);
    }

    @Override
    public double getRateItems() {
        return this.calcStat(Stats.ITEM_REWARD_MULTIPLIER, this._party == null ? (double)this.a.getDropItems() : this._party._rateDrop);
    }

    @Override
    public double getRateExp() {
        return this.calcStat(Stats.EXP, this._party == null ? (double)this.a.getRateXp() : this._party._rateExp, null, null);
    }

    @Override
    public double getRateSp() {
        return this.calcStat(Stats.SP, this._party == null ? (double)this.a.getRateSp() : this._party._rateSp, null, null);
    }

    @Override
    public double getRateSpoil() {
        return this.calcStat(Stats.SPOIL_REWARD_MULTIPLIER, this._party == null ? (double)this.a.getDropSpoil() : this._party._rateSpoil);
    }

    @Override
    public double getRateSealStones() {
        return this.calcStat(Stats.SEAL_STONES_REWARD_MULTIPLIER, this._party == null ? (double)this.a.getDropSealStones() : this._party._rateSealStones, null, null);
    }

    public double getRaidRateExp() {
        return this.calcStat(Stats.RAID_EXP, this._party == null ? (double)this.a.getRateRaidXp() : this._party._rateRaidExp, null, null);
    }

    public double getRaidRateSp() {
        return this.calcStat(Stats.RAID_SP, this._party == null ? (double)this.a.getRateRaidSp() : this._party._rateRaidSp, null, null);
    }

    public double getEnchantBonusMul() {
        return this.calcStat(Stats.ENCHANT_BONUS_MULTIPLIER, this.a.getEnchantItemMul());
    }

    public double getEnchantSkillBonusMul() {
        return this.calcStat(Stats.ENCHANT_SKILL_BONUS_MULTIPLIER, this.a.getEnchantSkillMul());
    }

    public double getQuestDropBonusMul() {
        return this.calcStat(Stats.QUEST_DROP_MULTIPLIER, this.a.getQuestDropRate());
    }

    public boolean isMaried() {
        return this.cg;
    }

    public void setMaried(boolean bl) {
        this.cg = bl;
    }

    public void setMaryRequest(boolean bl) {
        this.ch = bl;
    }

    public boolean isMaryRequest() {
        return this.ch;
    }

    public void setMaryAccepted(boolean bl) {
        this.ci = bl;
    }

    public boolean isMaryAccepted() {
        return this.ci;
    }

    public int getPartnerId() {
        return this.iI;
    }

    public void setPartnerId(int n) {
        this.iI = n;
    }

    public int getCoupleId() {
        return this.iJ;
    }

    public void setCoupleId(int n) {
        this.iJ = n;
    }

    public void setUndying(boolean bl) {
        if (!this.isGM()) {
            return;
        }
        this.bJ = bl;
    }

    public boolean isUndying() {
        return this.bJ;
    }

    public void resetReuse() {
        this.getSkillReuses0().clear();
        this.k.clear();
    }

    public DeathPenalty getDeathPenalty() {
        return this._activeClass == null ? null : this._activeClass.getDeathPenalty(this);
    }

    public boolean isCharmOfCourage() {
        return this.cj;
    }

    public void setCharmOfCourage(boolean bl) {
        this.cj = bl;
        if (!bl) {
            this.getEffectList().stopEffect(5041);
        }
        this.sendEtcStatusUpdate();
    }

    @Override
    public int getIncreasedForce() {
        return this.iK;
    }

    @Override
    public void setIncreasedForce(int n) {
        if (this.iK == n) {
            return;
        }
        n = Math.min(n, 7);
        if ((n = Math.max(n, 0)) != 0 && n > this.iK) {
            this.by = System.currentTimeMillis();
            if (this.w == null) {
                this.w = ThreadPoolManager.getInstance().schedule(new ForceCleanupTask(), 600000L);
            }
            this.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOUR_FORCE_HAS_INCREASED_TO_LEVEL_S1).addNumber(n));
        }
        this.iK = n;
        this.sendEtcStatusUpdate();
    }

    public boolean isFalling() {
        return System.currentTimeMillis() - this.bz < 5000L;
    }

    public void falling(int n) {
        if (!Config.DAMAGE_FROM_FALLING || this.isDead() || this.isFlying() || this.isInWater() || this.isInBoat()) {
            return;
        }
        this.bz = System.currentTimeMillis();
        int n2 = (int)this.calcStat(Stats.FALL, (double)this.getMaxHp() / 2000.0 * (double)n, null, null);
        if (n2 > 0) {
            int n3 = (int)this.getCurrentHp();
            if (n3 - n2 < 1) {
                this.setCurrentHp(1.0, false);
            } else {
                this.setCurrentHp(n3 - n2, false);
            }
            this.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_RECEIVED_S1_FALLING_DAMAGE).addNumber(n2));
        }
    }

    @Override
    public void checkHpMessages(double d, double d2) {
        int[] nArray = new int[]{30, 30};
        int[] nArray2 = new int[]{290, 291};
        double d3 = this.getMaxHp() / 100;
        double d4 = d / d3;
        double d5 = d2 / d3;
        boolean bl = false;
        for (int i = 0; i < nArray2.length; ++i) {
            int n = this.getSkillLevel(nArray2[i]);
            if (n <= 0) continue;
            if (d4 > (double)nArray[i] && d5 <= (double)nArray[i]) {
                this.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.SINCE_YOUR_HP_HAS_DECREASED_THE_EFFECT_OF_S1_CAN_BE_FELT).addSkillName(nArray2[i], n));
                bl = true;
                continue;
            }
            if (!(d4 <= (double)nArray[i]) || !(d5 > (double)nArray[i])) continue;
            this.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.SINCE_YOUR_HP_HAS_INCREASED_THE_EFFECT_OF_S1_WILL_DISAPPEAR).addSkillName(nArray2[i], n));
            bl = true;
        }
        if (bl) {
            this.sendChanges();
        }
    }

    public void checkDayNightMessages() {
        int n = this.getSkillLevel(294);
        if (n > 0) {
            if (GameTimeController.getInstance().isNowNight()) {
                this.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.IT_IS_NOW_MIDNIGHT_AND_THE_EFFECT_OF_S1_CAN_BE_FELT).addSkillName(294, n));
            } else {
                this.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.IT_IS_DAWN_AND_THE_EFFECT_OF_S1_WILL_NOW_DISAPPEAR).addSkillName(294, n));
            }
        }
        this.sendChanges();
    }

    public int getZoneMask() {
        return this.ir;
    }

    @Override
    protected void onUpdateZones(List<Zone> list, List<Zone> list2) {
        FlagItemAttachment flagItemAttachment;
        super.onUpdateZones(list, list2);
        if (list.isEmpty() && list2.isEmpty()) {
            return;
        }
        boolean bl = (this.ir & 0x4000) == 16384;
        boolean bl2 = (this.ir & 0x100) == 256;
        boolean bl3 = (this.ir & 0x800) == 2048;
        boolean bl4 = (this.ir & 0x1000) == 4096;
        boolean bl5 = this.isInCombatZone();
        boolean bl6 = this.isInDangerArea();
        boolean bl7 = this.isInZone(Zone.ZoneType.fun);
        boolean bl8 = this.isOnSiegeField() || bl7;
        boolean bl9 = this.isInPeaceZone();
        boolean bl10 = this.isInSSQZone();
        int n = this.ir;
        this.ir = 0;
        if (bl5) {
            this.ir |= 0x4000;
        }
        if (bl6) {
            this.ir |= 0x100;
        }
        if (bl8) {
            this.ir |= 0x800;
        }
        if (bl9) {
            this.ir |= 0x1000;
        }
        if (bl10) {
            this.ir |= 0x2000;
        }
        if (n != this.ir) {
            this.sendPacket((IStaticPacket)new ExSetCompassZoneCode(this));
        }
        if (bl != bl5) {
            this.broadcastRelation();
        }
        if (bl2 != bl6) {
            this.sendPacket((IStaticPacket)new EtcStatusUpdate(this));
        }
        if (bl3 != bl8) {
            this.broadcastRelation();
            if (bl8) {
                this.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_ENTERED_A_COMBAT_ZONE);
                if (Config.FUN_ZONE_FLAG_ON_ENTER && bl7 && !this.isTeleporting() && this.getPvpFlag() == 0) {
                    this.startPvPFlag(null);
                }
            } else {
                this.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_LEFT_A_COMBAT_ZONE);
                if (!this.isTeleporting() && this.getPvpFlag() == 0) {
                    this.startPvPFlag(null);
                }
            }
        }
        if (bl9 && !bl4 && (flagItemAttachment = this.getActiveWeaponFlagAttachment()) != null) {
            flagItemAttachment.onEnterPeace(this);
        }
        if (bl4 != bl9) {
            if (bl9) {
                this.startVitalityTask();
            } else {
                this.stopVitalityTask();
            }
        }
        if (this.isInWater()) {
            this.startWaterTask();
        } else {
            this.stopWaterTask();
        }
    }

    public void startAutoSaveTask() {
        if (!Config.AUTOSAVE) {
            return;
        }
        if (this.n == null) {
            this.n = AutoSaveManager.getInstance().addAutoSaveTask(this);
        }
    }

    public void stopAutoSaveTask() {
        if (this.n != null) {
            this.n.cancel(false);
        }
        this.n = null;
    }

    public void startVitalityTask() {
        if (!Config.ALT_VITALITY_ENABLED) {
            return;
        }
        if (this.q == null) {
            this.q = LazyPrecisionTaskManager.getInstance().addVitalityRegenTask(this);
        }
    }

    public void stopVitalityTask() {
        if (this.q != null) {
            this.q.cancel(false);
        }
        this.q = null;
    }

    public void startPcBangPointsTask() {
        String string;
        GameClient gameClient;
        if (!Config.ALT_PCBANG_POINTS_ENABLED || Config.ALT_PCBANG_POINTS_DELAY <= 0) {
            return;
        }
        if (Config.ALT_PCBANG_CHECK_HWID && (gameClient = this.getNetConnection()) != null && !StringUtils.isBlank((CharSequence)(string = gameClient.getHwid()))) {
            for (Player player : GameObjectsStorage.getAllPlayersForIterate()) {
                GameClient gameClient2;
                if (player == null || player == this || !player.isOnline() || (gameClient2 = player.getNetConnection()) == null || !StringUtils.equalsIgnoreCase((CharSequence)string, (CharSequence)gameClient2.getHwid())) continue;
                return;
            }
        }
        if (this.p == null) {
            this.p = LazyPrecisionTaskManager.getInstance().addPCCafePointsTask(this);
        }
    }

    public void stopPcBangPointsTask() {
        if (this.p != null) {
            this.p.cancel(false);
        }
        this.p = null;
    }

    public void startUnjailTask(Player player, long l) {
        if (this.r != null) {
            this.r.cancel(false);
        }
        this.r = ThreadPoolManager.getInstance().schedule(new GameObjectTasks.UnJailTask(player), l);
    }

    public void stopUnjailTask() {
        if (this.r != null) {
            this.r.cancel(false);
        }
        this.r = null;
    }

    @Override
    public void sendMessage(String string) {
        this.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.S1).addString(string));
    }

    public void setLastClientPosition(Location location) {
        this.A = location;
    }

    public Location getLastClientPosition() {
        return this.A;
    }

    public void setLastServerPosition(Location location) {
        this.B = location;
    }

    public Location getLastServerPosition() {
        return this.B;
    }

    public void setUseSeed(int n) {
        this.iL = n;
    }

    public int getUseSeed() {
        return this.iL;
    }

    public int getRelation(Player player) {
        Party party;
        int n = 0;
        if (this.getClan() != null) {
            n |= 0x40;
            if (this.getClan() == player.getClan()) {
                n |= 0x100;
            }
            if (this.getClan().getAllyId() != 0) {
                n |= 0x10000;
            }
        }
        if (this.isClanLeader()) {
            n |= 0x80;
        }
        if ((party = this.getParty()) != null && party == player.getParty()) {
            n |= 0x20;
            switch (party.getPartyMembers().indexOf(this)) {
                case 0: {
                    n |= 0x10;
                    break;
                }
                case 1: {
                    n |= 8;
                    break;
                }
                case 2: {
                    n |= 7;
                    break;
                }
                case 3: {
                    n |= 6;
                    break;
                }
                case 4: {
                    n |= 5;
                    break;
                }
                case 5: {
                    n |= 4;
                    break;
                }
                case 6: {
                    n |= 3;
                    break;
                }
                case 7: {
                    n |= 2;
                    break;
                }
                case 8: {
                    n |= 1;
                }
            }
        }
        Clan clan = this.getClan();
        Clan clan2 = player.getClan();
        if (clan != null && clan2 != null && player.getPledgeType() != -1 && this.getPledgeType() != -1 && clan2.isAtWarWith(clan.getClanId())) {
            n |= 0x4000;
            if (clan.isAtWarWith(clan2.getClanId())) {
                n |= 0x8000;
            }
        }
        for (GlobalEvent globalEvent : this.getEvents()) {
            n = globalEvent.getRelation(this, player, n);
        }
        return n;
    }

    public long getlastPvpAttack() {
        return this.bA;
    }

    @Override
    public void startPvPFlag(Creature creature) {
        if (this.gh > 0) {
            return;
        }
        long l = System.currentTimeMillis();
        if (creature != null && creature.getPvpFlag() != 0) {
            l -= (long)Math.max(0, Config.PVP_TIME - Config.PVP_FLAG_ON_UN_FLAG_TIME);
        }
        if (this._pvpFlag != 0 && this.bA > l) {
            return;
        }
        if (this.getFarmSystem().isAutofarming() && Config.AUTO_FARM_STOP_ON_PVP_FLAG) {
            this.getFarmSystem().stopFarmTask();
        }
        this.bA = l;
        this.updatePvPFlag(1);
        if (this.x == null) {
            this.x = ThreadPoolManager.getInstance().scheduleAtFixedRate(new GameObjectTasks.PvPFlagTask(this), 1000L, 1000L);
        }
    }

    public void stopPvPFlag() {
        if (this.x != null) {
            this.x.cancel(false);
            this.x = null;
        }
        this.updatePvPFlag(0);
    }

    public void updatePvPFlag(int n) {
        if (this._pvpFlag == n) {
            return;
        }
        this.setPvpFlag(n);
        this.sendStatusUpdate(true, true, 26);
        this.broadcastRelation();
    }

    public void setPvpFlag(int n) {
        this._pvpFlag = n;
    }

    @Override
    public int getPvpFlag() {
        return this._pvpFlag;
    }

    public boolean isInDuel() {
        return this.getEvent(DuelEvent.class) != null;
    }

    public TamedBeastInstance getTrainedBeast() {
        return this.a;
    }

    public void setTrainedBeast(TamedBeastInstance tamedBeastInstance) {
        this.a = tamedBeastInstance;
    }

    public long getLastAttackPacket() {
        return this.bB;
    }

    public void setLastAttackPacket() {
        this.bB = System.currentTimeMillis();
    }

    public byte[] getKeyBindings() {
        return this.l;
    }

    public void setKeyBindings(byte[] byArray) {
        if (byArray == null) {
            byArray = ArrayUtils.EMPTY_BYTE_ARRAY;
        }
        this.l = byArray;
    }

    public void setTransformation(int n) {
        if (n == this.gJ || this.gJ != 0 && n != 0) {
            return;
        }
        if (n == 0) {
            for (Effect object : this.getEffectList().getAllEffects()) {
                if (object == null || object.getEffectType() != EffectType.Transformation || object.calc() == 0.0) continue;
                object.exit();
                this.a(object.getSkill());
                break;
            }
            if (!this._transformationSkills.isEmpty()) {
                for (Skill skill : this._transformationSkills.values()) {
                    if (skill.isCommon() || SkillAcquireHolder.getInstance().isSkillPossible(this, skill) || skill.isHeroic()) continue;
                    super.removeSkill(skill);
                }
                this._transformationSkills.clear();
            }
        } else {
            if (!this.isCursedWeaponEquipped()) {
                for (Effect effect : this.getEffectList().getAllEffects()) {
                    if (effect == null || effect.getEffectType() != EffectType.Transformation) continue;
                    if (effect.getSkill() instanceof Transformation && ((Transformation)effect.getSkill()).isDisguise) {
                        for (Skill skill : this.getAllSkills()) {
                            if (skill == null || !skill.isActive() && !skill.isToggle()) continue;
                            this._transformationSkills.put(skill.getId(), skill);
                        }
                    } else {
                        for (Skill.AddedSkill addedSkill : effect.getSkill().getAddedSkills()) {
                            int n2;
                            if (addedSkill.level == 0) {
                                n2 = this.getSkillLevel(addedSkill.id);
                                if (n2 <= 0) continue;
                                this._transformationSkills.put(addedSkill.id, SkillTable.getInstance().getInfo(addedSkill.id, n2));
                                continue;
                            }
                            if (addedSkill.level == -2) {
                                n2 = Math.max(effect.getSkill().getMagicLevel(), 40);
                                int n3 = SkillTable.getInstance().getBaseLevel(addedSkill.id);
                                int n4 = 1;
                                n4 = n3 > 3 ? (n4 += this.getLevel() - n2) : (n4 += (this.getLevel() - n2) / ((76 - n2) / n3));
                                n4 = Math.min(Math.max(n4, 1), n3);
                                this._transformationSkills.put(addedSkill.id, SkillTable.getInstance().getInfo(addedSkill.id, n4));
                                continue;
                            }
                            this._transformationSkills.put(addedSkill.id, addedSkill.getSkill());
                        }
                    }
                    this.a(effect.getSkill());
                    break;
                }
            } else {
                this.a((Skill)null);
            }
            if (!this.isOlyParticipant() && this.isCursedWeaponEquipped() && this.bT && this.getActiveClass().isBase()) {
                for (SkillLearn skillLearn : SkillAcquireHolder.getInstance().getAvailableSkills(this, AcquireType.HERO)) {
                    Skill skill = SkillTable.getInstance().getInfo(skillLearn.getId(), skillLearn.getLevel());
                    if (skill == null || this.getSkillLevel(skill.getId()) >= skill.getLevel()) continue;
                    this._transformationSkills.put(skill.getId(), skill);
                }
            }
            for (Skill skill : this._transformationSkills.values()) {
                this.addSkill(skill, false);
            }
        }
        this.gJ = n;
        this.sendPacket((IStaticPacket)new ExBasicActionList(this));
        this.sendSkillList();
        this.sendPacket((IStaticPacket)new ShortCutInit(this));
        Iterator<Object> iterator = this.getAutoSoulShot().iterator();
        while (iterator.hasNext()) {
            int n5 = (Integer)iterator.next();
            this.sendPacket((IStaticPacket)new ExAutoSoulShot(n5, true, 0));
        }
        this.broadcastUserInfo(true, new UserInfoType[0]);
        this.sendPacket((IStaticPacket)new ExUserInfoAbnormalVisualEffect(this));
    }

    private void a(Skill skill) {
        if (skill == null || !skill.isBaseTransformation()) {
            for (Effect effect : this.getEffectList().getAllEffects()) {
                if (effect == null || !effect.getSkill().isToggle()) continue;
                effect.exit();
            }
        }
    }

    public boolean isInFlyingTransform() {
        return this.gJ == 8 || this.gJ == 9 || this.gJ == 260;
    }

    public boolean isInMountTransform() {
        return this.bY;
    }

    public void setInMountTransform(boolean bl) {
        this.bY = bl;
    }

    public int getTransformation() {
        return this.gJ;
    }

    public String getTransformationName() {
        return this.cL;
    }

    public void setTransformationName(String string) {
        this.cL = string;
    }

    public String getTransformationTitle() {
        return this.cR;
    }

    public void setTransformationTitle(String string) {
        this.cR = string;
    }

    public void setTransformationTemplate(int n) {
        this.is = n;
    }

    public int getTransformationTemplate() {
        return this.is;
    }

    @Override
    public final Collection<Skill> getAllSkills() {
        if (this.gJ == 0 && !this.isCursedWeaponEquipped()) {
            return super.getAllSkills();
        }
        HashMap<Integer, Skill> hashMap = new HashMap<Integer, Skill>();
        for (Skill skill : super.getAllSkills()) {
            if (skill == null || skill.isActive() || skill.isToggle()) continue;
            hashMap.put(skill.getId(), skill);
        }
        hashMap.putAll(this._transformationSkills);
        return hashMap.values();
    }

    public void setAgathion(int n) {
        if (this.ik == n) {
            return;
        }
        this.ik = n;
        this.broadcastCharInfo();
        this.sendPacket((IStaticPacket)new ExUserInfoCubic(this));
    }

    public int getAgathionId() {
        return this.ik;
    }

    public int getPcBangPoints() {
        return this.iv;
    }

    public void setPcBangPoints(int n) {
        this.iv = n;
    }

    public void setPcBangPoints(int n, String string) {
        n = Math.min(Config.LIM_PC_BANG_POINTS, n);
        if (string != null && !string.isEmpty()) {
            Log.add(this._name + "|" + (n - this.iv) + "|" + n + "|" + string, "pcBang");
        }
        if (n > this.iv) {
            this.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_ACQUIRED_S1_PC_BANG_POINT).addNumber(n - this.iv));
        }
        this.setPcBangPoints(n);
    }

    public void addPcBangPoints(int n, boolean bl) {
        if (bl) {
            n = (int)((double)n * Config.ALT_PCBANG_POINTS_BONUS_DOUBLE_RATE);
        }
        if (!this.getBonus().isExpired()) {
            n = (int)((double)n * Config.ALT_PCBANG_BONUS_WITH_PREMIUM_RATE);
        }
        if (n + this.iv > Config.LIM_PC_BANG_POINTS) {
            n = Config.LIM_PC_BANG_POINTS - this.iv;
        }
        this.iv += n;
        if (n > 0) {
            this.sendPacket((IStaticPacket)new SystemMessage(bl ? SystemMsg.DOUBLE_POINTS_YOU_ACQUIRED_S1_PC_BANG_POINT : SystemMsg.YOU_ACQUIRED_S1_PC_BANG_POINT).addNumber(n));
            this.sendPacket((IStaticPacket)new ExPCCafePointInfo(this, n, 1, 2, 12));
        } else {
            this.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.THE_PC_BANG_POINTS_ACCUMULATION_PERIOD_HAS_EXPIRED));
        }
    }

    public boolean reducePcBangPoints(int n) {
        if (this.iv < n) {
            return false;
        }
        this.iv -= n;
        this.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_ARE_USING_S1_POINT).addNumber(n));
        this.sendPacket((IStaticPacket)new ExPCCafePointInfo(this, 0, 1, 2, 12));
        return true;
    }

    public int getRaidBossPoints() {
        return this.iw;
    }

    public void setRaidBossPoints(int n) {
        this.iw = n;
    }

    public void addRaidBossPoints(int n) {
        if ((long)this.getRaidBossPoints() + (long)n > Integer.MAX_VALUE) {
            this.sendPacket((IStaticPacket)SystemMsg.YOU_HAVE_REACHED_THE_MAXIMUM_AMOUNT_OF_RAID_POINTS_AND_CAN_ACQUIRE_NO_MORE);
            return;
        }
        this.setRaidBossPoints(this.getRaidBossPoints() + n);
        this.sendUserInfo(true, UserInfoType.STATS);
    }

    public boolean reduceRaidBossPoints(int n) {
        if (this.iw < n) {
            return false;
        }
        this.iw -= n;
        this.sendUserInfo(true, UserInfoType.STATS);
        this.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.YOU_CONSUMED_S1_RAID_POINTS).addNumber(n));
        return true;
    }

    public void broadcastPlayerJump(Player player) {
        long l = System.currentTimeMillis();
        if (!player.isPlayer() || l - player.bv < 800L) {
            return;
        }
        this.broadcastPacket(ExPlayAnimation.jump(player));
        player.bv = l;
    }

    public void setGroundSkillLoc(Location location) {
        this.C = location;
    }

    public Location getGroundSkillLoc() {
        return this.C;
    }

    public boolean isLogoutStarted() {
        return this.g.get();
    }

    public void setOfflineMode(boolean bl) {
        if (!bl) {
            this.unsetVar("offline");
        }
        this.bX = bl;
    }

    public boolean isInOfflineMode() {
        return this.bX;
    }

    public void saveTradeList() {
        Object object = "";
        if (this.aV == null || this.aV.isEmpty()) {
            this.unsetVar("selllist");
        } else {
            for (TradeItem object2 : this.aV) {
                object = (String)object + object2.getObjectId() + ";" + object2.getCount() + ";" + object2.getOwnersPrice() + ":";
            }
            this.setVar("selllist", (String)object, -1L);
            object = "";
            if (this.aY != null && this.getSellStoreName() != null) {
                this.setVar("sellstorename", this.getSellStoreName(), -1L);
            }
        }
        if (this.aW == null || this.aW.isEmpty()) {
            this.unsetVar("packageselllist");
        } else {
            for (TradeItem tradeItem : this.aW) {
                object = (String)object + tradeItem.getObjectId() + ";" + tradeItem.getCount() + ";" + tradeItem.getOwnersPrice() + ":";
            }
            this.setVar("packageselllist", (String)object, -1L);
            object = "";
            if (this.aY != null && this.getSellStoreName() != null) {
                this.setVar("sellstorename", this.getSellStoreName(), -1L);
            }
        }
        if (this.aX == null || this.aX.isEmpty()) {
            this.unsetVar("buylist");
        } else {
            for (TradeItem tradeItem : this.aX) {
                object = (String)object + tradeItem.getItemId() + ";" + tradeItem.getCount() + ";" + tradeItem.getOwnersPrice() + ";" + tradeItem.getEnchantLevel() + ":";
            }
            this.setVar("buylist", (String)object, -1L);
            object = "";
            if (this.aY != null && this.getBuyStoreName() != null) {
                this.setVar("buystorename", this.getBuyStoreName(), -1L);
            }
        }
        if (this.aU == null || this.aU.isEmpty()) {
            this.unsetVar("createlist");
        } else {
            for (ManufactureItem manufactureItem : this.aU) {
                object = (String)object + manufactureItem.getRecipeId() + ";" + manufactureItem.getCost() + ":";
            }
            this.setVar("createlist", (String)object, -1L);
            if (this.getManufactureName() != null) {
                this.setVar("manufacturename", this.getManufactureName(), -1L);
            }
        }
    }

    public void restoreTradeList() {
        TradeItem tradeItem;
        ItemInstance itemInstance;
        long l;
        long l2;
        int n;
        String[] stringArray;
        String[] stringArray2;
        String string = this.getVar("selllist");
        if (string != null) {
            this.aV = (long)new CopyOnWriteArrayList();
            for (String string2 : stringArray2 = string.split(":")) {
                if (string2.equals("") || (stringArray = string2.split(";")).length < 3) continue;
                n = Integer.parseInt(stringArray[0]);
                l2 = Long.parseLong(stringArray[1]);
                l = Long.parseLong(stringArray[2]);
                itemInstance = this.getInventory().getItemByObjectId(n);
                if (l2 < 1L || itemInstance == null) continue;
                if (l2 > itemInstance.getCount()) {
                    l2 = itemInstance.getCount();
                }
                tradeItem = new TradeItem(itemInstance);
                tradeItem.setCount(l2);
                tradeItem.setOwnersPrice(l);
                this.aV.add(tradeItem);
            }
            string = this.getVar("sellstorename");
            if (string != null) {
                this.setSellStoreName(string);
            }
        }
        if ((string = this.getVar("packageselllist")) != null) {
            this.aW = new CopyOnWriteArrayList<TradeItem>();
            for (String string2 : stringArray2 = string.split(":")) {
                if (string2.equals("") || (stringArray = string2.split(";")).length < 3) continue;
                n = Integer.parseInt(stringArray[0]);
                l2 = Long.parseLong(stringArray[1]);
                l = Long.parseLong(stringArray[2]);
                itemInstance = this.getInventory().getItemByObjectId(n);
                if (l2 < 1L || itemInstance == null) continue;
                if (l2 > itemInstance.getCount()) {
                    l2 = itemInstance.getCount();
                }
                tradeItem = new TradeItem(itemInstance);
                tradeItem.setCount(l2);
                tradeItem.setOwnersPrice(l);
                this.aW.add(tradeItem);
            }
            string = this.getVar("sellstorename");
            if (string != null) {
                this.setSellStoreName(string);
            }
        }
        if ((string = this.getVar("buylist")) != null) {
            this.aX = new CopyOnWriteArrayList<TradeItem>();
            for (String string2 : stringArray2 = string.split(":")) {
                if (string2.equals("") || (stringArray = string2.split(";")).length < 3) continue;
                TradeItem tradeItem2 = new TradeItem();
                tradeItem2.setItemId(Integer.parseInt(stringArray[0]));
                tradeItem2.setCount(Long.parseLong(stringArray[1]));
                tradeItem2.setOwnersPrice(Long.parseLong(stringArray[2]));
                if (stringArray.length >= 4) {
                    tradeItem2.setEnchantLevel(Integer.parseInt(stringArray[3]));
                }
                this.aX.add(tradeItem2);
            }
            string = this.getVar("buystorename");
            if (string != null) {
                this.setBuyStoreName(string);
            }
        }
        if ((string = this.getVar("createlist")) != null) {
            this.aU = new CopyOnWriteArrayList<ManufactureItem>();
            for (String string2 : stringArray2 = string.split(":")) {
                if (string2.equals("") || (stringArray = string2.split(";")).length < 2) continue;
                int n2 = Integer.parseInt(stringArray[0]);
                l2 = Long.parseLong(stringArray[1]);
                if (!this.findRecipe(n2)) continue;
                this.aU.add(new ManufactureItem(n2, l2));
            }
            string = this.getVar("manufacturename");
            if (string != null) {
                this.setManufactureName(string);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void restoreRecipeBook() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("SELECT `id` FROM `character_recipebook` WHERE `char_id`=?");
            preparedStatement.setInt(1, this.getObjectId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int n = resultSet.getInt("id");
                Recipe recipe = RecipeHolder.getInstance().getRecipeById(n);
                this.registerRecipe(recipe, false);
            }
        }
        catch (Exception exception) {
            try {
                bF.warn("count not recipe skills:" + exception);
                bF.error("", (Throwable)exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement, resultSet);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement, resultSet);
        }
        DbUtils.closeQuietly(connection, preparedStatement, resultSet);
    }

    public int getMountType() {
        PetData petData = PetDataHolder.getInstance().getInfo(this.getMountNpcId());
        if (petData == null) {
            return 0;
        }
        return petData.isGreatWolf() ? 3 : (petData.isWyvern() ? 2 : (petData.isStrider() ? 1 : 0));
    }

    @Override
    public double getColRadius() {
        NpcTemplate npcTemplate;
        int n;
        if (this.getTransformation() != 0) {
            NpcTemplate npcTemplate2;
            int n2 = this.getTransformationTemplate();
            if (n2 != 0 && (npcTemplate2 = NpcHolder.getInstance().getTemplate(n2)) != null) {
                return npcTemplate2.collisionRadius;
            }
        } else if (this.isMounted() && (n = this.getMountNpcId()) != 0 && (npcTemplate = NpcHolder.getInstance().getTemplate(n)) != null) {
            return npcTemplate.collisionRadius;
        }
        return this.getBaseTemplate().collisionRadius;
    }

    @Override
    public double getColHeight() {
        NpcTemplate npcTemplate;
        int n;
        if (this.getTransformation() != 0) {
            NpcTemplate npcTemplate2;
            int n2 = this.getTransformationTemplate();
            if (n2 != 0 && (npcTemplate2 = NpcHolder.getInstance().getTemplate(n2)) != null) {
                return npcTemplate2.collisionHeight;
            }
        } else if (this.isMounted() && (n = this.getMountNpcId()) != 0 && (npcTemplate = NpcHolder.getInstance().getTemplate(n)) != null) {
            return npcTemplate.collisionHeight;
        }
        return this.getBaseTemplate().collisionHeight;
    }

    @Override
    public void setReflection(Reflection reflection) {
        if (this.getReflection() == reflection) {
            return;
        }
        super.setReflection(reflection);
        if (this.a != null && !this.a.isDead()) {
            this.a.setReflection(reflection);
        }
        if (reflection != ReflectionManager.DEFAULT) {
            String string = this.getVar("reflection");
            if (string == null || !string.equals(String.valueOf(reflection.getId()))) {
                this.setVar("reflection", String.valueOf(reflection.getId()), -1L);
            }
        } else {
            this.unsetVar("reflection");
        }
        if (this.getActiveClass() != null) {
            this.getInventory().validateItems();
            if (this.getPet() != null && (this.getPet().getNpcId() == 14916 || this.getPet().getNpcId() == 14917)) {
                this.getPet().unSummon();
            }
        }
    }

    public void setBuyListId(int n) {
        this.iM = n;
    }

    public int getBuyListId() {
        return this.iM;
    }

    public int getVitalityLevel(boolean bl) {
        return Config.ALT_VITALITY_ENABLED ? (bl ? 4 : this.hT) : 0;
    }

    public double getVitality() {
        return Config.ALT_VITALITY_ENABLED ? this.v : 0.0;
    }

    public void addVitality(double d) {
        this.setVitality(this.getVitality() + d);
    }

    public void setVitality(double d) {
        if (!Config.ALT_VITALITY_ENABLED) {
            return;
        }
        if ((d = Math.max(Math.min(d, (double)Config.ALT_VITALITY_LEVELS[4]), 0.0)) >= this.v || this.getLevel() >= 10) {
            if (d != this.v) {
                if (d == 0.0) {
                    this.sendPacket((IStaticPacket)SystemMsg.YOUR_VITALITY_IS_FULLY_EXHAUSTED);
                } else if (d == (double)Config.ALT_VITALITY_LEVELS[4]) {
                    this.sendPacket((IStaticPacket)SystemMsg.YOUR_VITALITY_IS_AT_MAXIMUM);
                }
            }
            this.v = d;
        }
        int n = 0;
        if (this.v >= (double)Config.ALT_VITALITY_LEVELS[3]) {
            n = 4;
        } else if (this.v >= (double)Config.ALT_VITALITY_LEVELS[2]) {
            n = 3;
        } else if (this.v >= (double)Config.ALT_VITALITY_LEVELS[1]) {
            n = 2;
        } else if (this.v >= (double)Config.ALT_VITALITY_LEVELS[0]) {
            n = 1;
        }
        if (this.hT != n) {
            if (this.hT != -1) {
                this.sendPacket((IStaticPacket)(n < this.hT ? SystemMsg.YOUR_VITALITY_HAS_DECREASED : SystemMsg.YOUR_VITALITY_HAS_INCREASED));
            }
            this.hT = n;
        }
        this.sendPacket((IStaticPacket)new ExVitalityPointInfo((int)this.v));
    }

    public int getIncorrectValidateCount() {
        return this.iN;
    }

    public int setIncorrectValidateCount(int n) {
        this.iN = n;
        return this.iN;
    }

    public int getExpandInventory() {
        return this.ix;
    }

    public void setExpandInventory(int n) {
        this.ix = n;
    }

    public int getExpandWarehouse() {
        return this.iy;
    }

    public void setExpandWarehouse(int n) {
        this.iy = n;
    }

    public void enterMovieMode() {
        if (this.isInMovie() || !Config.SHOW_BOSS_SCENES) {
            return;
        }
        this.setTarget(null);
        this.stopMove();
        this.setIsInMovie(true);
        this.sendPacket((IStaticPacket)new CameraMode(1));
    }

    public void leaveMovieMode() {
        this.setIsInMovie(false);
        this.sendPacket((IStaticPacket)new CameraMode(0));
        this.broadcastCharInfo();
    }

    public void specialCamera(GameObject gameObject, int n, int n2, int n3, int n4, int n5) {
        if (Config.SHOW_BOSS_SCENES) {
            this.sendPacket((IStaticPacket)new SpecialCamera(gameObject.getObjectId(), n, n2, n3, n4, n5));
        }
    }

    public void specialCamera(GameObject gameObject, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9) {
        if (Config.SHOW_BOSS_SCENES) {
            this.sendPacket((IStaticPacket)new SpecialCamera(gameObject.getObjectId(), n, n2, n3, n4, n5, n6, n7, n8, n9));
        }
    }

    public void setMovieId(int n) {
        this.iO = n;
    }

    public int getMovieId() {
        return this.iO;
    }

    public boolean isInMovie() {
        return this.ck;
    }

    public void setIsInMovie(boolean bl) {
        this.ck = bl;
    }

    public void showQuestMovie(SceneMovie sceneMovie) {
        if (this.isInMovie()) {
            return;
        }
        this.sendActionFailed();
        this.setTarget(null);
        this.stopMove();
        this.setMovieId(sceneMovie.getId());
        this.setIsInMovie(true);
        this.sendPacket((IStaticPacket)sceneMovie.packet(this));
    }

    public void showQuestMovie(int n) {
        if (this.isInMovie()) {
            return;
        }
        this.sendActionFailed();
        this.setTarget(null);
        this.stopMove();
        this.setMovieId(n);
        this.setIsInMovie(true);
        this.sendPacket((IStaticPacket)new ExStartScenePlayer(n));
    }

    public void setAutoLoot(boolean bl) {
        if (Config.AUTO_LOOT_INDIVIDUAL) {
            this.bL = bl;
            this.setVar("AutoLoot", String.valueOf(bl), -1L);
        }
    }

    public void setAutoLootHerbs(boolean bl) {
        if (Config.AUTO_LOOT_INDIVIDUAL) {
            this.bM = bl;
            this.setVar("AutoLootHerbs", String.valueOf(bl), -1L);
        }
    }

    public void setAutoLootAdena(boolean bl) {
        if (Config.AUTO_LOOT_INDIVIDUAL) {
            this.bN = bl;
            this.setVar("AutoLootAdena", String.valueOf(bl), -1L);
        }
    }

    public boolean isAutoLootEnabled() {
        return this.bL;
    }

    public boolean isAutoLootHerbsEnabled() {
        return this.bM;
    }

    public boolean isAutoLootAdenaEnabled() {
        return this.bN;
    }

    public final void reName(String string, boolean bl) {
        this.setName(string);
        if (bl) {
            this.saveNameToDB();
        }
        if (this.isNoble()) {
            NoblesController.getInstance().renameNoble(this.getObjectId(), string);
        }
        if (this.isHero()) {
            HeroController.getInstance().renameHero(this.getObjectId(), string);
        }
        this.broadcastUserInfo(false, new UserInfoType[0]);
        this.broadcastCharInfo();
    }

    public boolean getOpenCloak() {
        if (Config.ALT_OPEN_CLOAK_SLOT) {
            return true;
        }
        return (int)this.calcStat(Stats.CLOAK_SLOT, 0.0, null, null) > 0;
    }

    public boolean getAutoLootStat() {
        return (int)this.calcStat(Stats.AUTO_LOOT, 0.0, null, null) > 0;
    }

    public boolean getAutoLootHerbStat() {
        return (int)this.calcStat(Stats.AUTO_LOOT_HERB, 0.0, null, null) > 0;
    }

    public boolean getAutoLootAdenaStat() {
        return (int)this.calcStat(Stats.AUTO_LOOT_ADENA, 0.0, null, null) > 0;
    }

    public int getTalismanCount() {
        return (int)this.calcStat(Stats.TALISMANS_LIMIT, 0.0, null, null);
    }

    public int getBroochCount() {
        return (int)this.calcStat(Stats.BROOCH_LIMIT, 0.0, null, null);
    }

    public int getAgathionCharmCount() {
        return (int)this.calcStat(Stats.AGATHION_CHARM_LIMIT, 0.0, null, null);
    }

    public int getWorldChatBonus() {
        return (int)this.calcStat(Stats.WORLD_CHAT_BONUSES, 0.0, null, null);
    }

    public int getVipBonusSilverDrop() {
        return (int)this.calcStat(Stats.VIP_SILVER_DROP_MULTIPLIER, 0.0, null, null);
    }

    public int getVipBonusGoldDrop() {
        return (int)this.calcStat(Stats.VIP_GOLD_DROP_MULTIPLIER, 0.0, null, null);
    }

    public final void reName(String string) {
        this.reName(string, false);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void saveNameToDB() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseFactory.getInstance().getConnection();
            preparedStatement = connection.prepareStatement("UPDATE characters SET `char_name` = ? WHERE `obj_Id` = ?");
            preparedStatement.setString(1, this.getName());
            preparedStatement.setInt(2, this.getObjectId());
            preparedStatement.executeUpdate();
        }
        catch (Exception exception) {
            try {
                bF.error("", (Throwable)exception);
            }
            catch (Throwable throwable) {
                DbUtils.closeQuietly(connection, preparedStatement);
                throw throwable;
            }
            DbUtils.closeQuietly(connection, preparedStatement);
        }
        DbUtils.closeQuietly(connection, preparedStatement);
    }

    @Override
    public Player getPlayer() {
        return this;
    }

    public final void disableDrop(int n) {
        this.bw = System.currentTimeMillis() + (long)n;
    }

    public final boolean isDropDisabled() {
        return this.bw > System.currentTimeMillis();
    }

    public void setPetControlItem(int n) {
        this.setPetControlItem(this.getInventory().getItemByObjectId(n));
    }

    public void setPetControlItem(ItemInstance itemInstance) {
        this.e = itemInstance;
    }

    public ItemInstance getPetControlItem() {
        return this.e;
    }

    public boolean isActive() {
        return this.h.get();
    }

    public void setActive() {
        this.setNonAggroTime(0L);
        if (this.h.getAndSet(true)) {
            return;
        }
        this.bq();
    }

    private void bq() {
        this.setNonAggroTime(System.currentTimeMillis() + Config.NONAGGRO_TIME_ONLOGIN);
        if (this.getPetControlItem() != null) {
            ThreadPoolManager.getInstance().execute(new RunnableImpl(){

                @Override
                public void runImpl() {
                    if (Player.this.getPetControlItem() != null) {
                        Player.this.summonPet();
                    }
                }
            });
        }
    }

    public void summonPet() {
        if (this.getPet() != null) {
            return;
        }
        ItemInstance itemInstance = this.getPetControlItem();
        if (itemInstance == null) {
            return;
        }
        PetData petData = PetDataHolder.getInstance().getByControlItemId(this.getPetControlItem());
        if (petData == null) {
            return;
        }
        NpcTemplate npcTemplate = NpcHolder.getInstance().getTemplate(petData.getID());
        if (npcTemplate == null) {
            return;
        }
        PetInstance petInstance = PetInstance.restore(itemInstance, npcTemplate, this);
        if (petInstance == null) {
            return;
        }
        this.setPet(petInstance);
        petInstance.setTitle(this.getName());
        if (!petInstance.isRespawned()) {
            petInstance.setCurrentHp(petInstance.getMaxHp(), false);
            petInstance.setCurrentMp(petInstance.getMaxMp());
            petInstance.setCurrentFed(petInstance.getMaxFed());
            petInstance.updateControlItem();
            petInstance.store();
        }
        petInstance.getInventory().restore();
        petInstance.setNonAggroTime(System.currentTimeMillis() + Config.NONAGGRO_TIME_ONTELEPORT);
        petInstance.setReflection(this.getReflection());
        petInstance.spawnMe(Location.findPointToStay(this, 50, 70));
        petInstance.setRunning();
        petInstance.setFollowMode(true);
        petInstance.getInventory().validateItems();
        if (petInstance instanceof PetBabyInstance) {
            ((PetBabyInstance)petInstance).startBuffTask();
        }
    }

    public Collection<TrapInstance> getTraps() {
        if (this.aL == null) {
            return null;
        }
        ArrayList<TrapInstance> arrayList = new ArrayList<TrapInstance>(this.getTrapsCount());
        for (Integer n : this.aL.keySet()) {
            TrapInstance trapInstance = (TrapInstance)GameObjectsStorage.get(this.aL.get(n));
            if (trapInstance != null) {
                arrayList.add(trapInstance);
                continue;
            }
            this.aL.remove(n);
        }
        return arrayList;
    }

    public int getTrapsCount() {
        return this.aL == null ? 0 : this.aL.size();
    }

    public void addTrap(TrapInstance trapInstance) {
        if (this.aL == null) {
            this.aL = new HashMap<Integer, Long>();
        }
        this.aL.put(trapInstance.getObjectId(), trapInstance.getStoredId());
    }

    public void removeTrap(TrapInstance trapInstance) {
        Map<Integer, Long> map = this.aL;
        if (map == null || map.isEmpty()) {
            return;
        }
        map.remove(trapInstance.getObjectId());
    }

    public void destroyFirstTrap() {
        Map<Integer, Long> map = this.aL;
        if (map == null || map.isEmpty()) {
            return;
        }
        Iterator<Integer> iterator = map.keySet().iterator();
        if (iterator.hasNext()) {
            Integer n = iterator.next();
            TrapInstance trapInstance = (TrapInstance)GameObjectsStorage.get(map.get(n));
            if (trapInstance != null) {
                trapInstance.deleteMe();
                return;
            }
            return;
        }
    }

    public void destroyAllTraps() {
        Map<Integer, Long> map = this.aL;
        if (map == null || map.isEmpty()) {
            return;
        }
        ArrayList<TrapInstance> arrayList = new ArrayList<TrapInstance>();
        for (Integer serializable : map.keySet()) {
            arrayList.add((TrapInstance)GameObjectsStorage.get(map.get(serializable)));
        }
        for (TrapInstance trapInstance : arrayList) {
            if (trapInstance == null) continue;
            trapInstance.deleteMe();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public PlayerListenerList getListeners() {
        if (this.listeners == null) {
            Player player = this;
            synchronized (player) {
                if (this.listeners == null) {
                    this.listeners = new PlayerListenerList(this);
                }
            }
        }
        return (PlayerListenerList)this.listeners;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public PlayerStatsChangeRecorder getStatsRecorder() {
        if (this._statsRecorder == null) {
            Player player = this;
            synchronized (player) {
                if (this._statsRecorder == null) {
                    this._statsRecorder = new PlayerStatsChangeRecorder(this);
                }
            }
        }
        return (PlayerStatsChangeRecorder)this._statsRecorder;
    }

    public int getHoursInGame() {
        ++this.iP;
        return this.iP;
    }

    public void startHourlyTask() {
        this.y = ThreadPoolManager.getInstance().scheduleAtFixedRate(new GameObjectTasks.HourlyTask(this), 3600000L, 3600000L);
    }

    public void stopHourlyTask() {
        if (this.y != null) {
            this.y.cancel(false);
            this.y = null;
        }
    }

    public long getPremiumPoints() {
        if (Config.PRIME_SHOP_VIP_POINT_ITEM_ID > 0) {
            return ItemFunctions.getItemCount(this, Config.PRIME_SHOP_VIP_POINT_ITEM_ID);
        }
        return 0L;
    }

    public void reducePremiumPoints(int n) {
        if (Config.PRIME_SHOP_VIP_POINT_ITEM_ID > 0) {
            ItemFunctions.removeItem(this, Config.PRIME_SHOP_VIP_POINT_ITEM_ID, n, true);
        }
    }

    public String getSessionVar(String string) {
        if (this.aM == null) {
            return null;
        }
        return this.aM.get(string);
    }

    public void setSessionVar(String string, String string2) {
        if (this.aM == null) {
            this.aM = new ConcurrentHashMap<String, String>();
        }
        if (string2 == null || string2.isEmpty()) {
            this.aM.remove(string);
        } else {
            this.aM.put(string, string2);
        }
    }

    public FriendList getFriendList() {
        return this.a;
    }

    public boolean isNotShowTraders() {
        return this.cc;
    }

    public void setNotShowTraders(boolean bl) {
        this.cc = bl;
    }

    public boolean isDebug() {
        return this.cd;
    }

    public void setDebug(boolean bl) {
        this.cd = bl;
    }

    public void sendSkillList() {
        this.sendSkillList(0);
    }

    public void sendSkillList(int n) {
        this.sendPacket(new SkillList(this, n), new AcquireSkillList(this));
    }

    public void sendItemList(boolean bl) {
        ItemInstance[] itemInstanceArray = this.getInventory().getItems();
        LockType lockType = this.getInventory().getLockType();
        int[] nArray = this.getInventory().getLockItems();
        LinkedList<ItemInfo> linkedList = new LinkedList<ItemInfo>();
        LinkedList<ItemInfo> linkedList2 = new LinkedList<ItemInfo>();
        int n = 0;
        for (ItemInstance itemInstance : itemInstanceArray) {
            if (itemInstance.getTemplate().isQuest()) {
                ItemInfo itemInfo = new ItemInfo(itemInstance);
                itemInfo.setEquipSlot(n++);
                linkedList2.add(itemInfo);
                continue;
            }
            linkedList.add(new ItemInfo(itemInstance));
        }
        this.sendPacket((IStaticPacket)new ItemList(true, linkedList, bl, lockType, nArray));
        this.sendPacket((IStaticPacket)new ItemList(false, linkedList, bl, lockType, nArray));
        this.sendPacket((IStaticPacket)new ExQuestItemList(true, linkedList2, lockType, nArray));
        this.sendPacket((IStaticPacket)new ExQuestItemList(false, linkedList2, lockType, nArray));
    }

    @Override
    public boolean isPlayer() {
        return true;
    }

    @Override
    public void startAttackStanceTask() {
        this.startAttackStanceTask0();
        Summon summon = this.getPet();
        if (summon != null) {
            summon.startAttackStanceTask0();
        }
    }

    @Override
    public void displayGiveDamageMessage(Creature creature, int n, boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        super.displayGiveDamageMessage(creature, n, bl, bl2, bl3, bl4);
        if (bl) {
            if (bl4) {
                this.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.MAGIC_CRITICAL_HIT));
                this.sendPacket((IStaticPacket)new ExMagicAttackInfo(this.getObjectId(), creature.getObjectId(), 1));
            } else {
                this.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.C1_LANDED_A_CRITICAL_HIT).addName(this));
            }
        }
        if (bl2) {
            this.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.C1S_ATTACK_WENT_ASTRAY).addName(this));
        } else if (!creature.isDamageBlocked()) {
            this.sendPacket((IStaticPacket)((SystemMessage)((SystemMessage)((SystemMessage)new SystemMessage(SystemMsg.C1_HAS_DONE_S3_POINTS_OF_DAMAGE_TO_C2_S4).addName(this)).addName(creature)).addNumber(n)).addVisibleDamage(this, creature, -n));
        }
        if (creature.isPlayer()) {
            if (bl3 && n > 1) {
                creature.sendPacket((IStaticPacket)SystemMsg.YOUR_SHIELD_DEFENSE_HAS_SUCCEEDED);
            } else if (bl3 && n == 1) {
                creature.sendPacket((IStaticPacket)SystemMsg.YOUR_EXCELLENT_SHIELD_DEFENSE_WAS_A_SUCCESS);
            }
        }
    }

    @Override
    public void displayReceiveDamageMessage(Creature creature, int n) {
        if (creature != this) {
            this.sendPacket((IStaticPacket)((SystemMessage)((SystemMessage)new SystemMessage(SystemMsg.C1_HAS_RECEIVED_S3_DAMAGE_FROM_C2).addName(this)).addName(creature)).addNumber((long)n));
        }
    }

    public IntObjectMap<String> getPostFriends() {
        return this.j;
    }

    public boolean isSharedGroupDisabled(int n) {
        TimeStamp timeStamp = (TimeStamp)this.k.get(n);
        if (timeStamp == null) {
            return false;
        }
        if (timeStamp.hasNotPassed()) {
            return true;
        }
        this.k.remove(n);
        return false;
    }

    public TimeStamp getSharedGroupReuse(int n) {
        return (TimeStamp)this.k.get(n);
    }

    public void addSharedGroupReuse(int n, TimeStamp timeStamp) {
        this.k.put(n, (Object)timeStamp);
    }

    public Collection<IntObjectMap.Entry<TimeStamp>> getSharedGroupReuses() {
        return this.k.entrySet();
    }

    public void sendReuseMessage(ItemInstance itemInstance) {
        TimeStamp timeStamp = this.getSharedGroupReuse(itemInstance.getTemplate().getReuseGroup());
        if (timeStamp == null || !timeStamp.hasNotPassed()) {
            return;
        }
        long l = timeStamp.getReuseCurrent();
        long l2 = TimeUnit.MILLISECONDS.toHours(l);
        long l3 = TimeUnit.MILLISECONDS.toMinutes(l - TimeUnit.HOURS.toMillis(l2));
        long l4 = TimeUnit.MILLISECONDS.toSeconds(l - TimeUnit.MINUTES.toMillis(l3) - TimeUnit.HOURS.toMillis(l2));
        if (l2 > 0L) {
            this.sendPacket((IStaticPacket)((SystemMessage)((SystemMessage)((SystemMessage)new SystemMessage(itemInstance.getTemplate().getReuseType().getMessages()[2]).addItemName(itemInstance.getTemplate().getItemId())).addNumber(l2)).addNumber(l3)).addNumber(l4));
        } else if (l3 > 0L) {
            this.sendPacket((IStaticPacket)((SystemMessage)((SystemMessage)new SystemMessage(itemInstance.getTemplate().getReuseType().getMessages()[1]).addItemName(itemInstance.getTemplate().getItemId())).addNumber(l3)).addNumber(l4));
        } else {
            this.sendPacket((IStaticPacket)((SystemMessage)new SystemMessage(itemInstance.getTemplate().getReuseType().getMessages()[0]).addItemName(itemInstance.getTemplate().getItemId())).addNumber(Math.max(1L, l4)));
        }
    }

    public void ask(ConfirmDlg confirmDlg, OnAnswerListener onAnswerListener) {
        if (this.a != null) {
            return;
        }
        int n = Rnd.nextInt();
        this.a = new ImmutablePair((Object)n, (Object)onAnswerListener);
        confirmDlg.setRequestId(n);
        this.sendPacket((IStaticPacket)confirmDlg);
    }

    public Pair<Integer, OnAnswerListener> getAskListener(boolean bl) {
        if (!bl) {
            return this.a;
        }
        GameClient gameClient = this.a;
        this.a = null;
        return gameClient;
    }

    @Override
    public boolean isDead() {
        if (this.isOlyParticipant()) {
            return this.isOlyCompetitionStarted() && this.isLooseOlyCompetition();
        }
        return this.isInDuel() ? this.getCurrentHp() <= 1.0 : super.isDead();
    }

    public boolean hasPrivilege(Privilege privilege) {
        return this.a != null && (this.getClanPrivileges() & privilege.mask()) == privilege.mask();
    }

    public MatchingRoom getMatchingRoom() {
        return this.a;
    }

    public void setMatchingRoom(MatchingRoom matchingRoom) {
        this.a = matchingRoom;
    }

    public void dispelBuffs() {
        for (Effect effect : this.getEffectList().getAllEffects()) {
            if (effect.getSkill().isOffensive() || effect.getSkill().isNewbie() || !effect.isCancelable() || effect.getSkill().isPreservedOnDeath()) continue;
            this.sendPacket((IStaticPacket)new SystemMessage(SystemMsg.THE_EFFECT_OF_S1_HAS_BEEN_REMOVED).addSkillName(effect.getSkill().getId(), effect.getSkill().getLevel()));
            effect.exit();
        }
        if (this.getPet() != null) {
            for (Effect effect : this.getPet().getEffectList().getAllEffects()) {
                if (effect.getSkill().isOffensive() || effect.getSkill().isNewbie() || !effect.isCancelable() || effect.getSkill().isPreservedOnDeath()) continue;
                effect.exit();
            }
        }
    }

    public void setInstanceReuse(int n, long l) {
        CustomMessage customMessage = new CustomMessage("INSTANT_ZONE_FROM_HERE__S1_S_ENTRY_HAS_BEEN_RESTRICTED_YOU_CAN_CHECK_THE_NEXT_ENTRY_POSSIBLE", this, new Object[0]).addString(this.getName());
        this.sendMessage(customMessage);
        this.aJ.put(n, l);
        InstanceReuseDAO.getInstance().setReuse(this, n, l);
    }

    public void removeInstanceReuse(int n) {
        if (this.aJ.remove(n) != null) {
            InstanceReuseDAO.getInstance().removeReuse(this, n);
        }
    }

    public void removeAllInstanceReuses() {
        this.aJ.clear();
        InstanceReuseDAO.getInstance().removeAllReuse(this);
    }

    public void removeInstanceReusesByGroupId(int n) {
        for (int n2 : InstantZoneHolder.getInstance().getSharedReuseInstanceIdsByGroup(n)) {
            if (this.getInstanceReuse(n2) == null) continue;
            this.removeInstanceReuse(n2);
        }
    }

    public Long getInstanceReuse(int n) {
        return (Long)this.aJ.get(n);
    }

    public Map<Integer, Long> getInstanceReuses() {
        return this.aJ;
    }

    public Reflection getActiveReflection() {
        for (Reflection reflection : ReflectionManager.getInstance().getAll()) {
            if (reflection == null || !ArrayUtils.contains((int[])reflection.getVisitors(), (int)this.getObjectId())) continue;
            return reflection;
        }
        return null;
    }

    public boolean canEnterInstance(int n) {
        InstantZone instantZone = InstantZoneHolder.getInstance().getInstantZone(n);
        if (this.isDead()) {
            return false;
        }
        if (ReflectionManager.getInstance().size() > Config.MAX_REFLECTIONS_COUNT) {
            this.sendMessage(new CustomMessage("THE_MAXIMUM_NUMBER_OF_INSTANCE_ZONES_HAS_BEEN_EXCEEDED", this, new Object[0]));
            return false;
        }
        if (instantZone == null) {
            this.sendPacket((IStaticPacket)SystemMsg.SYSTEM_ERROR);
            return false;
        }
        if (ReflectionManager.getInstance().getCountByIzId(n) >= instantZone.getMaxChannels()) {
            this.sendMessage(new CustomMessage("THE_MAXIMUM_NUMBER_OF_INSTANCE_ZONES_HAS_BEEN_EXCEEDED", this, new Object[0]));
            return false;
        }
        return instantZone.getEntryType().canEnter(this, instantZone);
    }

    public boolean canReenterInstance(int n) {
        InstantZone instantZone = InstantZoneHolder.getInstance().getInstantZone(n);
        if (this.getActiveReflection() != null && this.getActiveReflection().getInstancedZoneId() != n) {
            this.sendMessage(new CustomMessage("YOU_HAVE_ENTERED_ANOTHER_INSTANCE_ZONE_THEREFORE_YOU_CANNOT_ENTER_CORRESPONDING_DUNGEON", this, new Object[0]));
            return false;
        }
        if (instantZone.isDispelBuffs()) {
            this.dispelBuffs();
        }
        return instantZone.getEntryType().canReEnter(this, instantZone);
    }

    public int getBattlefieldChatId() {
        return this.iB;
    }

    public void setBattlefieldChatId(int n) {
        this.iB = n;
    }

    @Override
    public void broadCast(IStaticPacket ... iStaticPacketArray) {
        this.sendPacket(iStaticPacketArray);
    }

    @Override
    public Iterator<Player> iterator() {
        return Collections.singleton(this).iterator();
    }

    public PlayerGroup getPlayerGroup() {
        if (this.getParty() != null) {
            if (this.getParty().getCommandChannel() != null) {
                return this.getParty().getCommandChannel();
            }
            return this.getParty();
        }
        return this;
    }

    public boolean isActionBlocked(String string) {
        return this.aZ.contains(string);
    }

    public void blockActions(String ... stringArray) {
        Collections.addAll(this.aZ, stringArray);
    }

    public void unblockActions(String ... stringArray) {
        for (String string : stringArray) {
            this.aZ.remove(string);
        }
    }

    public void addRadar(int n, int n2, int n3) {
        this.sendPacket((IStaticPacket)new RadarControl(0, 1, n, n2, n3));
    }

    public void addRadarWithMap(int n, int n2, int n3) {
        this.sendPacket((IStaticPacket)new RadarControl(0, 2, n, n2, n3));
    }

    public long getAfterTeleportPortectionTime() {
        return this.bC;
    }

    public void setAfterTeleportPortectionTime(long l) {
        this.bC = l;
    }

    public void triggerAfterTeleportProtection() {
        if (Config.ALT_TELEPORT_PROTECTION && this.getAfterTeleportPortectionTime() > System.currentTimeMillis()) {
            this.setAfterTeleportPortectionTime(0L);
            this.sendMessage(new CustomMessage("alt.teleport_protect_gonna", this, new Object[0]));
        }
    }

    public long getNoCarrierProtectionTime() {
        return this.bD;
    }

    public void setNoCarrierProtectionTime(long l) {
        this.bD = l;
    }

    public void triggerNoCarrierProtection() {
        if (Config.SERVICES_ENABLE_NO_CARRIER && Config.NO_CARRIER_PROTECTION && this.getNoCarrierProtectionTime() > System.currentTimeMillis()) {
            this.setNoCarrierProtectionTime(0L);
            this.sendMessage(new CustomMessage("alt.no_carrier_protect_gonna", this, new Object[0]));
        }
    }

    public boolean isUserRelationActive() {
        return this.t == null;
    }

    public void startEnableUserRelationTask(long l, SiegeEvent<?, ?> siegeEvent) {
        if (this.t != null) {
            return;
        }
        this.t = ThreadPoolManager.getInstance().schedule(new EnableUserRelationTask(this, siegeEvent), l);
    }

    public void stopEnableUserRelationTask() {
        if (this.t != null) {
            this.t.cancel(false);
            this.t = null;
        }
    }

    public int getTpBookmarkSize() {
        return this.iA;
    }

    public void setTpBookmarkSize(int n) {
        this.iA = n;
    }

    public List<TpBookMark> getTpBookMarks() {
        return this.ba;
    }

    public OneDayRewardStore getOneDayRewardStore() {
        return this.a;
    }

    public boolean isTradeBannedByGM() {
        long l;
        String string = this.getVar("tradeBan");
        long l2 = System.currentTimeMillis();
        if (!(StringUtils.isBlank((CharSequence)string) || (l = Long.parseLong(string)) != -1L && l < l2)) {
            if (l == -1L) {
                this.sendMessage(new CustomMessage("common.TradeBannedPermanently", this, new Object[0]));
            } else {
                this.sendMessage(new CustomMessage("common.TradeBanned", this, new Object[0]).addString(Util.formatTime((int)TimeUnit.MILLISECONDS.toSeconds(l - l2))));
            }
            return true;
        }
        return false;
    }

    @Override
    public AutoFarmContext getFarmSystem() {
        return this.a;
    }

    public boolean isSellingBuffs() {
        return this.ce && this.aK != null && !this.aK.isEmpty();
    }

    public void setSellingBuffs(boolean bl) {
        this.ce = bl;
    }

    public Map<Skill, Long> getBuffs4Sale() {
        if (this.aK == null) {
            this.aK = new LinkedHashMap<Skill, Long>();
            return this.aK;
        }
        return this.aK;
    }

    public Player setBuffs4Sale(Map<Skill, Long> map) {
        this.aK = map;
        return this;
    }

    public byte getVipLevel() {
        return this.b;
    }

    public void setVipLevel(byte by) {
        this.b = by;
    }

    public long getVipPoints() {
        return this.getVarLong(VIP_POINTS, 0L);
    }

    public long getVipTierExpiration() {
        return this.getVarLong(VIP_EXPIRATION, 0L);
    }

    public void setVipTierExpiration(long l) {
        this.setVar(VIP_EXPIRATION, l, -1L);
    }

    public boolean canReceiveGift(Player player) {
        if (!Config.PRIME_SHOP_VIP_SYSTEM_ENABLED) {
            return false;
        }
        if (player.getVipLevel() <= 0) {
            return false;
        }
        return player.getVarLong(VIP_ITEM_BOUGHT) <= 0L;
    }

    public void updateVipPoints(long l) {
        if (l == 0L) {
            return;
        }
        VipManager vipManager = VipManager.getInstance();
        byte by = vipManager.getVipLevel(this.getVipPoints());
        this.setVar(VIP_POINTS, this.getVipPoints() + l, -1L);
        byte by2 = vipManager.getVipLevel(this.getVipPoints());
        if (by2 != by) {
            this.b = by2;
            if (by2 > 0) {
                this.setVar(VIP_EXPIRATION, Instant.now().plus(30L, ChronoUnit.DAYS).toEpochMilli(), -1L);
                vipManager.manageVipLevelSkill(this);
            } else {
                this.setVar(VIP_EXPIRATION, 0, -1L);
            }
        }
        this.sendPacket((IStaticPacket)new ReceiveVipInfo(this, vipManager));
    }

    private class UpdateEffectIcons
    extends RunnableImpl {
        private UpdateEffectIcons() {
        }

        @Override
        public void runImpl() throws Exception {
            Player.this.updateEffectIconsImpl();
            Player.this.u = null;
        }
    }

    private static class MoveToLocationActionForOffload
    extends Creature.MoveToLocationAction {
        public MoveToLocationActionForOffload(Creature creature, Location location, Location location2, boolean bl, int n, boolean bl2) {
            super(creature, location, location2, bl, n, bl2);
        }

        private void br() {
            Player player = (Player)this.getActor();
            MoveToLocationOffloadData moveToLocationOffloadData = null;
            if (player != null && (moveToLocationOffloadData = (MoveToLocationOffloadData)((AtomicReference)((Object)player.a)).get()) != null && ((AtomicReference)((Object)player.a)).compareAndSet(moveToLocationOffloadData, null)) {
                player.moveToLocation(moveToLocationOffloadData.getDest(), moveToLocationOffloadData.getIndent(), moveToLocationOffloadData.isPathfind());
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        protected boolean onTick(double d) {
            boolean bl;
            try {
                bl = super.onTick(d);
            }
            finally {
                this.br();
            }
            return bl;
        }

        @Override
        protected void onFinish(boolean bl, boolean bl2) {
            try {
                super.onFinish(bl, bl2);
            }
            finally {
                this.br();
            }
        }
    }

    private static class MoveToLocationOffloadData {
        private final Location D;
        private final int iR;
        private final boolean cl;

        public MoveToLocationOffloadData(Location location, int n, boolean bl) {
            this.D = location;
            this.iR = n;
            this.cl = bl;
        }

        public Location getDest() {
            return this.D;
        }

        public int getIndent() {
            return this.iR;
        }

        public boolean isPathfind() {
            return this.cl;
        }
    }

    public class BroadcastCharInfoTask
    extends RunnableImpl {
        @Override
        public void runImpl() throws Exception {
            Player.this.broadcastCharInfoImpl();
            Player.this.R = null;
        }
    }

    private class UserInfoTask
    extends RunnableImpl {
        private UserInfoTask() {
        }

        @Override
        public void runImpl() throws Exception {
            Player.this.bk();
            Player.this.v = (double)null;
        }
    }

    public static final class EPledgeRank
    extends Enum<EPledgeRank> {
        public static final /* enum */ EPledgeRank VAGABOND = new EPledgeRank(0);
        public static final /* enum */ EPledgeRank VASSAL = new EPledgeRank(1);
        public static final /* enum */ EPledgeRank HEIR = new EPledgeRank(2);
        public static final /* enum */ EPledgeRank KNIGHT = new EPledgeRank(3);
        public static final /* enum */ EPledgeRank WISEMAN = new EPledgeRank(4);
        public static final /* enum */ EPledgeRank BARON = new EPledgeRank(5);
        public static final /* enum */ EPledgeRank VISCOUNT = new EPledgeRank(6);
        public static final /* enum */ EPledgeRank COUNT = new EPledgeRank(7);
        public static final /* enum */ EPledgeRank MARQUIS = new EPledgeRank(8);
        private final int iQ;
        public static EPledgeRank[] VALUES;
        private static final /* synthetic */ EPledgeRank[] a;

        public static EPledgeRank[] values() {
            return (EPledgeRank[])a.clone();
        }

        public static EPledgeRank valueOf(String string) {
            return Enum.valueOf(EPledgeRank.class, string);
        }

        private EPledgeRank(int n2) {
            this.iQ = n2;
        }

        public int getRankId() {
            return this.iQ;
        }

        public static EPledgeRank getPledgeRank(int n) {
            for (EPledgeRank ePledgeRank : VALUES) {
                if (ePledgeRank.getRankId() != n) continue;
                return ePledgeRank;
            }
            return null;
        }

        private static /* synthetic */ EPledgeRank[] a() {
            return new EPledgeRank[]{VAGABOND, VASSAL, HEIR, KNIGHT, WISEMAN, BARON, VISCOUNT, COUNT, MARQUIS};
        }

        static {
            a = EPledgeRank.a();
            VALUES = EPledgeRank.values();
        }
    }

    private class ForceCleanupTask
    implements Runnable {
        private ForceCleanupTask() {
        }

        @Override
        public void run() {
            long l = 600000L - (System.currentTimeMillis() - Player.this.by);
            if (l > 1000L) {
                Player.this.w = ThreadPoolManager.getInstance().schedule(new ForceCleanupTask(), l);
                return;
            }
            Player.this.iK = 0;
            Player.this.sendEtcStatusUpdate();
            Player.this.w = null;
        }
    }
}
