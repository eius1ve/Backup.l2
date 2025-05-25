/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.network.l2;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;
import l2.commons.net.nio.impl.IAcceptFilter;
import l2.commons.net.nio.impl.IClientFactory;
import l2.commons.net.nio.impl.IMMOExecutor;
import l2.commons.net.nio.impl.IPacketHandler;
import l2.commons.net.nio.impl.MMOConnection;
import l2.commons.net.nio.impl.ReceivablePacket;
import l2.gameserver.Config;
import l2.gameserver.ThreadPoolManager;
import l2.gameserver.network.l2.CGModule;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.Action;
import l2.gameserver.network.l2.c2s.AddTradeItem;
import l2.gameserver.network.l2.c2s.AnswerCoupleAction;
import l2.gameserver.network.l2.c2s.AnswerJoinPartyRoom;
import l2.gameserver.network.l2.c2s.AnswerPartyLootModification;
import l2.gameserver.network.l2.c2s.AnswerTradeRequest;
import l2.gameserver.network.l2.c2s.Appearing;
import l2.gameserver.network.l2.c2s.AttackRequest;
import l2.gameserver.network.l2.c2s.AuthLogin;
import l2.gameserver.network.l2.c2s.BypassUserCmd;
import l2.gameserver.network.l2.c2s.CannotMoveAnymore;
import l2.gameserver.network.l2.c2s.CannotMoveAnymoreInVehicle;
import l2.gameserver.network.l2.c2s.CharacterCreate;
import l2.gameserver.network.l2.c2s.CharacterDelete;
import l2.gameserver.network.l2.c2s.CharacterRestore;
import l2.gameserver.network.l2.c2s.CharacterSelected;
import l2.gameserver.network.l2.c2s.ConfirmDlg;
import l2.gameserver.network.l2.c2s.DummyClientPacket;
import l2.gameserver.network.l2.c2s.EnterWorld;
import l2.gameserver.network.l2.c2s.ExPCCafeRequestOpenWindowWithoutNPC;
import l2.gameserver.network.l2.c2s.ExSendClientINI;
import l2.gameserver.network.l2.c2s.FinishRotatingC;
import l2.gameserver.network.l2.c2s.GotoLobby;
import l2.gameserver.network.l2.c2s.Logout;
import l2.gameserver.network.l2.c2s.MoveBackwardToLocation;
import l2.gameserver.network.l2.c2s.MoveWithDelta;
import l2.gameserver.network.l2.c2s.NetPing;
import l2.gameserver.network.l2.c2s.NewCharacter;
import l2.gameserver.network.l2.c2s.NotifyStartMiniGame;
import l2.gameserver.network.l2.c2s.PetitionVote;
import l2.gameserver.network.l2.c2s.ProtocolVersion;
import l2.gameserver.network.l2.c2s.ReplyGameGuardQuery;
import l2.gameserver.network.l2.c2s.RequestActionUse;
import l2.gameserver.network.l2.c2s.RequestAddExpandQuestAlarm;
import l2.gameserver.network.l2.c2s.RequestAllAgitInfo;
import l2.gameserver.network.l2.c2s.RequestAllCastleInfo;
import l2.gameserver.network.l2.c2s.RequestAllFortressInfo;
import l2.gameserver.network.l2.c2s.RequestAllyCrest;
import l2.gameserver.network.l2.c2s.RequestAllyInfo;
import l2.gameserver.network.l2.c2s.RequestAnswerJoinAlly;
import l2.gameserver.network.l2.c2s.RequestAnswerJoinParty;
import l2.gameserver.network.l2.c2s.RequestAnswerJoinPledge;
import l2.gameserver.network.l2.c2s.RequestAquireSkill;
import l2.gameserver.network.l2.c2s.RequestAquireSkillInfo;
import l2.gameserver.network.l2.c2s.RequestAskJoinPartyRoom;
import l2.gameserver.network.l2.c2s.RequestAutoSoulShot;
import l2.gameserver.network.l2.c2s.RequestBBSwrite;
import l2.gameserver.network.l2.c2s.RequestBidItemAuction;
import l2.gameserver.network.l2.c2s.RequestBlock;
import l2.gameserver.network.l2.c2s.RequestBlockMemoInfo;
import l2.gameserver.network.l2.c2s.RequestBookMarkSlotInfo;
import l2.gameserver.network.l2.c2s.RequestBuyItem;
import l2.gameserver.network.l2.c2s.RequestBuySeed;
import l2.gameserver.network.l2.c2s.RequestBypassToServer;
import l2.gameserver.network.l2.c2s.RequestCastleSiegeAttackerList;
import l2.gameserver.network.l2.c2s.RequestCastleSiegeDefenderList;
import l2.gameserver.network.l2.c2s.RequestChangeNicknameColor;
import l2.gameserver.network.l2.c2s.RequestChangePetName;
import l2.gameserver.network.l2.c2s.RequestCharacterNameCreatable;
import l2.gameserver.network.l2.c2s.RequestConfirmCancelItem;
import l2.gameserver.network.l2.c2s.RequestConfirmCastleSiegeWaitingList;
import l2.gameserver.network.l2.c2s.RequestConfirmGemStone;
import l2.gameserver.network.l2.c2s.RequestConfirmRefinerItem;
import l2.gameserver.network.l2.c2s.RequestConfirmTargetItem;
import l2.gameserver.network.l2.c2s.RequestCreatePledge;
import l2.gameserver.network.l2.c2s.RequestCrystallizeEstimate;
import l2.gameserver.network.l2.c2s.RequestCrystallizeItem;
import l2.gameserver.network.l2.c2s.RequestCrystallizeItemCancel;
import l2.gameserver.network.l2.c2s.RequestCursedWeaponList;
import l2.gameserver.network.l2.c2s.RequestCursedWeaponLocation;
import l2.gameserver.network.l2.c2s.RequestDeleteBookMarkSlot;
import l2.gameserver.network.l2.c2s.RequestDeleteMacro;
import l2.gameserver.network.l2.c2s.RequestDestroyItem;
import l2.gameserver.network.l2.c2s.RequestDismissAlly;
import l2.gameserver.network.l2.c2s.RequestDismissParty;
import l2.gameserver.network.l2.c2s.RequestDismissPartyRoom;
import l2.gameserver.network.l2.c2s.RequestDispel;
import l2.gameserver.network.l2.c2s.RequestDivideAdena;
import l2.gameserver.network.l2.c2s.RequestDivideAdenaCancel;
import l2.gameserver.network.l2.c2s.RequestDivideAdenaStart;
import l2.gameserver.network.l2.c2s.RequestDropItem;
import l2.gameserver.network.l2.c2s.RequestDuelAnswerStart;
import l2.gameserver.network.l2.c2s.RequestDuelStart;
import l2.gameserver.network.l2.c2s.RequestDuelSurrender;
import l2.gameserver.network.l2.c2s.RequestEnchantItem;
import l2.gameserver.network.l2.c2s.RequestEnchantItemAttribute;
import l2.gameserver.network.l2.c2s.RequestEx2ndPasswordCheck;
import l2.gameserver.network.l2.c2s.RequestEx2ndPasswordReq;
import l2.gameserver.network.l2.c2s.RequestEx2ndPasswordVerify;
import l2.gameserver.network.l2.c2s.RequestExAddEnchantScrollItem;
import l2.gameserver.network.l2.c2s.RequestExAddPostFriendForPostBox;
import l2.gameserver.network.l2.c2s.RequestExBR_BuyProduct;
import l2.gameserver.network.l2.c2s.RequestExBR_EventRankerList;
import l2.gameserver.network.l2.c2s.RequestExBR_GamePoint;
import l2.gameserver.network.l2.c2s.RequestExBR_LectureMark;
import l2.gameserver.network.l2.c2s.RequestExBR_MiniGameInsertScore;
import l2.gameserver.network.l2.c2s.RequestExBR_MiniGameLoadScores;
import l2.gameserver.network.l2.c2s.RequestExBR_ProductInfo;
import l2.gameserver.network.l2.c2s.RequestExBR_ProductList;
import l2.gameserver.network.l2.c2s.RequestExBR_RecentProductList;
import l2.gameserver.network.l2.c2s.RequestExBuySellUIClose;
import l2.gameserver.network.l2.c2s.RequestExCancelEnchantItem;
import l2.gameserver.network.l2.c2s.RequestExCancelSentPost;
import l2.gameserver.network.l2.c2s.RequestExChangeName;
import l2.gameserver.network.l2.c2s.RequestExCleftEnter;
import l2.gameserver.network.l2.c2s.RequestExCubeGameChangeTeam;
import l2.gameserver.network.l2.c2s.RequestExCubeGameReadyAnswer;
import l2.gameserver.network.l2.c2s.RequestExDeletePostFriendForPostBox;
import l2.gameserver.network.l2.c2s.RequestExDeleteReceivedPost;
import l2.gameserver.network.l2.c2s.RequestExDeleteSentPost;
import l2.gameserver.network.l2.c2s.RequestExDismissMpccRoom;
import l2.gameserver.network.l2.c2s.RequestExDominionInfo;
import l2.gameserver.network.l2.c2s.RequestExEnchantSkill;
import l2.gameserver.network.l2.c2s.RequestExEnchantSkillInfo;
import l2.gameserver.network.l2.c2s.RequestExEndScenePlayer;
import l2.gameserver.network.l2.c2s.RequestExEventMatchObserverEnd;
import l2.gameserver.network.l2.c2s.RequestExFishRanking;
import l2.gameserver.network.l2.c2s.RequestExFriendListForPostBox;
import l2.gameserver.network.l2.c2s.RequestExItemEnsoul;
import l2.gameserver.network.l2.c2s.RequestExJoinDominionWar;
import l2.gameserver.network.l2.c2s.RequestExJoinMpccRoom;
import l2.gameserver.network.l2.c2s.RequestExJump;
import l2.gameserver.network.l2.c2s.RequestExListMpccWaiting;
import l2.gameserver.network.l2.c2s.RequestExMPCCAcceptJoin;
import l2.gameserver.network.l2.c2s.RequestExMPCCAskJoin;
import l2.gameserver.network.l2.c2s.RequestExMPCCShowPartyMembersInfo;
import l2.gameserver.network.l2.c2s.RequestExMagicSkillUseGround;
import l2.gameserver.network.l2.c2s.RequestExManageMpccRoom;
import l2.gameserver.network.l2.c2s.RequestExMoveToLocationAirShip;
import l2.gameserver.network.l2.c2s.RequestExMoveToLocationInAirShip;
import l2.gameserver.network.l2.c2s.RequestExMpccPartymasterList;
import l2.gameserver.network.l2.c2s.RequestExOlympiadObserverEnd;
import l2.gameserver.network.l2.c2s.RequestExOustFromMPCC;
import l2.gameserver.network.l2.c2s.RequestExOustFromMpccRoom;
import l2.gameserver.network.l2.c2s.RequestExPostItemList;
import l2.gameserver.network.l2.c2s.RequestExReceivePost;
import l2.gameserver.network.l2.c2s.RequestExRefundItem;
import l2.gameserver.network.l2.c2s.RequestExRejectPost;
import l2.gameserver.network.l2.c2s.RequestExRemoveEnchantSupportItem;
import l2.gameserver.network.l2.c2s.RequestExRemoveItemAttribute;
import l2.gameserver.network.l2.c2s.RequestExRequestReceivedPost;
import l2.gameserver.network.l2.c2s.RequestExRequestReceivedPostList;
import l2.gameserver.network.l2.c2s.RequestExRequestSentPost;
import l2.gameserver.network.l2.c2s.RequestExRequestSentPostList;
import l2.gameserver.network.l2.c2s.RequestExRqItemLink;
import l2.gameserver.network.l2.c2s.RequestExSeedPhase;
import l2.gameserver.network.l2.c2s.RequestExSendPost;
import l2.gameserver.network.l2.c2s.RequestExShowNewUserPetition;
import l2.gameserver.network.l2.c2s.RequestExShowPostFriendListForPostBox;
import l2.gameserver.network.l2.c2s.RequestExShowStepThree;
import l2.gameserver.network.l2.c2s.RequestExShowStepTwo;
import l2.gameserver.network.l2.c2s.RequestExStartShowCrataeCubeRank;
import l2.gameserver.network.l2.c2s.RequestExStopShowCrataeCubeRank;
import l2.gameserver.network.l2.c2s.RequestExTryToPutEnchantSupportItem;
import l2.gameserver.network.l2.c2s.RequestExTryToPutEnchantTargetItem;
import l2.gameserver.network.l2.c2s.RequestExWithdrawMpccRoom;
import l2.gameserver.network.l2.c2s.RequestExitPartyMatchingWaitingRoom;
import l2.gameserver.network.l2.c2s.RequestFortressMapInfo;
import l2.gameserver.network.l2.c2s.RequestFortressSiegeInfo;
import l2.gameserver.network.l2.c2s.RequestFriendAddReply;
import l2.gameserver.network.l2.c2s.RequestFriendDel;
import l2.gameserver.network.l2.c2s.RequestFriendDetailInfo;
import l2.gameserver.network.l2.c2s.RequestFriendInfoList;
import l2.gameserver.network.l2.c2s.RequestFriendInvite;
import l2.gameserver.network.l2.c2s.RequestFriendList;
import l2.gameserver.network.l2.c2s.RequestGMCommand;
import l2.gameserver.network.l2.c2s.RequestGetBossRecord;
import l2.gameserver.network.l2.c2s.RequestGetItemFromPet;
import l2.gameserver.network.l2.c2s.RequestGetOffVehicle;
import l2.gameserver.network.l2.c2s.RequestGetOnVehicle;
import l2.gameserver.network.l2.c2s.RequestGiveItemToPet;
import l2.gameserver.network.l2.c2s.RequestGiveNickName;
import l2.gameserver.network.l2.c2s.RequestGmList;
import l2.gameserver.network.l2.c2s.RequestGoodsInventoryInfo;
import l2.gameserver.network.l2.c2s.RequestHandOverPartyMaster;
import l2.gameserver.network.l2.c2s.RequestHardWareInfo;
import l2.gameserver.network.l2.c2s.RequestHennaEquip;
import l2.gameserver.network.l2.c2s.RequestHennaItemInfo;
import l2.gameserver.network.l2.c2s.RequestHennaList;
import l2.gameserver.network.l2.c2s.RequestHennaUnequip;
import l2.gameserver.network.l2.c2s.RequestHennaUnequipInfo;
import l2.gameserver.network.l2.c2s.RequestHennaUnequipList;
import l2.gameserver.network.l2.c2s.RequestInfoItemAuction;
import l2.gameserver.network.l2.c2s.RequestInzoneWaitingTime;
import l2.gameserver.network.l2.c2s.RequestItemList;
import l2.gameserver.network.l2.c2s.RequestJoinAlly;
import l2.gameserver.network.l2.c2s.RequestJoinCastleSiege;
import l2.gameserver.network.l2.c2s.RequestJoinParty;
import l2.gameserver.network.l2.c2s.RequestJoinPledge;
import l2.gameserver.network.l2.c2s.RequestKeyMapping;
import l2.gameserver.network.l2.c2s.RequestLinkHtml;
import l2.gameserver.network.l2.c2s.RequestListPartyMatchingWaitingRoom;
import l2.gameserver.network.l2.c2s.RequestMagicSkillList;
import l2.gameserver.network.l2.c2s.RequestMagicSkillUse;
import l2.gameserver.network.l2.c2s.RequestMakeMacro;
import l2.gameserver.network.l2.c2s.RequestManorList;
import l2.gameserver.network.l2.c2s.RequestModifyBookMarkSlot;
import l2.gameserver.network.l2.c2s.RequestMoveToLocationInVehicle;
import l2.gameserver.network.l2.c2s.RequestMultiSellChoose;
import l2.gameserver.network.l2.c2s.RequestNewEnchantPushOne;
import l2.gameserver.network.l2.c2s.RequestNewEnchantPushTwo;
import l2.gameserver.network.l2.c2s.RequestNewEnchantRemoveOne;
import l2.gameserver.network.l2.c2s.RequestNewEnchantRemoveTwo;
import l2.gameserver.network.l2.c2s.RequestNewEnchantRetryToPutItems;
import l2.gameserver.network.l2.c2s.RequestNewEnchantTry;
import l2.gameserver.network.l2.c2s.RequestObserverEnd;
import l2.gameserver.network.l2.c2s.RequestOlympiadMatchList;
import l2.gameserver.network.l2.c2s.RequestOlympiadObserverEnd;
import l2.gameserver.network.l2.c2s.RequestOneDayRewardReceive;
import l2.gameserver.network.l2.c2s.RequestOustAlly;
import l2.gameserver.network.l2.c2s.RequestOustFromPartyRoom;
import l2.gameserver.network.l2.c2s.RequestOustPartyMember;
import l2.gameserver.network.l2.c2s.RequestOustPledgeMember;
import l2.gameserver.network.l2.c2s.RequestPCCafeCouponUse;
import l2.gameserver.network.l2.c2s.RequestPVPMatchRecord;
import l2.gameserver.network.l2.c2s.RequestPackageSend;
import l2.gameserver.network.l2.c2s.RequestPackageSendableItemList;
import l2.gameserver.network.l2.c2s.RequestPartyLootModification;
import l2.gameserver.network.l2.c2s.RequestPartyMatchConfig;
import l2.gameserver.network.l2.c2s.RequestPartyMatchDetail;
import l2.gameserver.network.l2.c2s.RequestPartyMatchList;
import l2.gameserver.network.l2.c2s.RequestPetGetItem;
import l2.gameserver.network.l2.c2s.RequestPetUseItem;
import l2.gameserver.network.l2.c2s.RequestPetition;
import l2.gameserver.network.l2.c2s.RequestPetitionCancel;
import l2.gameserver.network.l2.c2s.RequestPledgeCrest;
import l2.gameserver.network.l2.c2s.RequestPledgeDraftListApply;
import l2.gameserver.network.l2.c2s.RequestPledgeDraftListSearch;
import l2.gameserver.network.l2.c2s.RequestPledgeEmblem;
import l2.gameserver.network.l2.c2s.RequestPledgeExtendedInfo;
import l2.gameserver.network.l2.c2s.RequestPledgeInfo;
import l2.gameserver.network.l2.c2s.RequestPledgeMemberInfo;
import l2.gameserver.network.l2.c2s.RequestPledgeMemberList;
import l2.gameserver.network.l2.c2s.RequestPledgeMemberPowerInfo;
import l2.gameserver.network.l2.c2s.RequestPledgePower;
import l2.gameserver.network.l2.c2s.RequestPledgePowerGradeList;
import l2.gameserver.network.l2.c2s.RequestPledgeRecruitApplyInfo;
import l2.gameserver.network.l2.c2s.RequestPledgeRecruitBoardAccess;
import l2.gameserver.network.l2.c2s.RequestPledgeRecruitBoardDetail;
import l2.gameserver.network.l2.c2s.RequestPledgeRecruitBoardSearch;
import l2.gameserver.network.l2.c2s.RequestPledgeRecruitInfo;
import l2.gameserver.network.l2.c2s.RequestPledgeReorganizeMember;
import l2.gameserver.network.l2.c2s.RequestPledgeSetAcademyMaster;
import l2.gameserver.network.l2.c2s.RequestPledgeSetMemberPowerGrade;
import l2.gameserver.network.l2.c2s.RequestPledgeSignInForOpenJoiningMethod;
import l2.gameserver.network.l2.c2s.RequestPledgeWaitingApplied;
import l2.gameserver.network.l2.c2s.RequestPledgeWaitingApply;
import l2.gameserver.network.l2.c2s.RequestPledgeWaitingList;
import l2.gameserver.network.l2.c2s.RequestPledgeWaitingUser;
import l2.gameserver.network.l2.c2s.RequestPledgeWaitingUserAccept;
import l2.gameserver.network.l2.c2s.RequestPledgeWarList;
import l2.gameserver.network.l2.c2s.RequestPreviewItem;
import l2.gameserver.network.l2.c2s.RequestPrivateStoreBuy;
import l2.gameserver.network.l2.c2s.RequestPrivateStoreBuyManage;
import l2.gameserver.network.l2.c2s.RequestPrivateStoreBuySellList;
import l2.gameserver.network.l2.c2s.RequestPrivateStoreList;
import l2.gameserver.network.l2.c2s.RequestPrivateStoreQuitBuy;
import l2.gameserver.network.l2.c2s.RequestPrivateStoreQuitSell;
import l2.gameserver.network.l2.c2s.RequestProcureCropList;
import l2.gameserver.network.l2.c2s.RequestQuestAbort;
import l2.gameserver.network.l2.c2s.RequestQuestList;
import l2.gameserver.network.l2.c2s.RequestRaidBossSpawnInfo;
import l2.gameserver.network.l2.c2s.RequestRecipeBookOpen;
import l2.gameserver.network.l2.c2s.RequestRecipeItemDelete;
import l2.gameserver.network.l2.c2s.RequestRecipeItemMakeInfo;
import l2.gameserver.network.l2.c2s.RequestRecipeItemMakeSelf;
import l2.gameserver.network.l2.c2s.RequestRecipeShopListSet;
import l2.gameserver.network.l2.c2s.RequestRecipeShopMakeDo;
import l2.gameserver.network.l2.c2s.RequestRecipeShopMakeInfo;
import l2.gameserver.network.l2.c2s.RequestRecipeShopManageCancel;
import l2.gameserver.network.l2.c2s.RequestRecipeShopManageQuit;
import l2.gameserver.network.l2.c2s.RequestRecipeShopMessageSet;
import l2.gameserver.network.l2.c2s.RequestRecipeShopSellList;
import l2.gameserver.network.l2.c2s.RequestRefine;
import l2.gameserver.network.l2.c2s.RequestRefineCancel;
import l2.gameserver.network.l2.c2s.RequestReload;
import l2.gameserver.network.l2.c2s.RequestRemainTime;
import l2.gameserver.network.l2.c2s.RequestResetNickname;
import l2.gameserver.network.l2.c2s.RequestRestart;
import l2.gameserver.network.l2.c2s.RequestRestartPoint;
import l2.gameserver.network.l2.c2s.RequestSEKCustom;
import l2.gameserver.network.l2.c2s.RequestSSQStatus;
import l2.gameserver.network.l2.c2s.RequestSaveBookMarkSlot;
import l2.gameserver.network.l2.c2s.RequestSaveInventoryOrder;
import l2.gameserver.network.l2.c2s.RequestSaveKeyMapping;
import l2.gameserver.network.l2.c2s.RequestSellItem;
import l2.gameserver.network.l2.c2s.RequestSendL2FriendSay;
import l2.gameserver.network.l2.c2s.RequestSendMsnChatLog;
import l2.gameserver.network.l2.c2s.RequestSetAllyCrest;
import l2.gameserver.network.l2.c2s.RequestSetCastleSiegeTime;
import l2.gameserver.network.l2.c2s.RequestSetCrop;
import l2.gameserver.network.l2.c2s.RequestSetPledgeCrest;
import l2.gameserver.network.l2.c2s.RequestSetPledgeCrestLarge;
import l2.gameserver.network.l2.c2s.RequestSetSeed;
import l2.gameserver.network.l2.c2s.RequestShortCutDel;
import l2.gameserver.network.l2.c2s.RequestShortCutReg;
import l2.gameserver.network.l2.c2s.RequestShowBoard;
import l2.gameserver.network.l2.c2s.RequestShowMiniMap;
import l2.gameserver.network.l2.c2s.RequestSiegeInfo;
import l2.gameserver.network.l2.c2s.RequestSkillCoolTime;
import l2.gameserver.network.l2.c2s.RequestSkillList;
import l2.gameserver.network.l2.c2s.RequestStartPledgeWar;
import l2.gameserver.network.l2.c2s.RequestStatus;
import l2.gameserver.network.l2.c2s.RequestStopPledgeWar;
import l2.gameserver.network.l2.c2s.RequestTargetCanceld;
import l2.gameserver.network.l2.c2s.RequestTeleport;
import l2.gameserver.network.l2.c2s.RequestTeleportBookMark;
import l2.gameserver.network.l2.c2s.RequestTimeCheck;
import l2.gameserver.network.l2.c2s.RequestTodoList;
import l2.gameserver.network.l2.c2s.RequestTryEnSoulExtraction;
import l2.gameserver.network.l2.c2s.RequestTutorialClientEvent;
import l2.gameserver.network.l2.c2s.RequestTutorialLinkHtml;
import l2.gameserver.network.l2.c2s.RequestTutorialPassCmdToServer;
import l2.gameserver.network.l2.c2s.RequestTutorialQuestionMark;
import l2.gameserver.network.l2.c2s.RequestUpdateBlockMemo;
import l2.gameserver.network.l2.c2s.RequestUpdateFriendMemo;
import l2.gameserver.network.l2.c2s.RequestUserBanInfo;
import l2.gameserver.network.l2.c2s.RequestVipInfo;
import l2.gameserver.network.l2.c2s.RequestVipProductList;
import l2.gameserver.network.l2.c2s.RequestVoteNew;
import l2.gameserver.network.l2.c2s.RequestWithDrawPremiumItem;
import l2.gameserver.network.l2.c2s.RequestWithDrawalParty;
import l2.gameserver.network.l2.c2s.RequestWithdrawAlly;
import l2.gameserver.network.l2.c2s.RequestWithdrawPartyRoom;
import l2.gameserver.network.l2.c2s.RequestWithdrawalPledge;
import l2.gameserver.network.l2.c2s.RequestWriteHeroWords;
import l2.gameserver.network.l2.c2s.Say2C;
import l2.gameserver.network.l2.c2s.ScriptExPacket;
import l2.gameserver.network.l2.c2s.ScriptPacket;
import l2.gameserver.network.l2.c2s.SendBypassBuildCmd;
import l2.gameserver.network.l2.c2s.SendWareHouseDepositList;
import l2.gameserver.network.l2.c2s.SendWareHouseWithDrawList;
import l2.gameserver.network.l2.c2s.SetPrivateStoreBuyList;
import l2.gameserver.network.l2.c2s.SetPrivateStoreMsgBuy;
import l2.gameserver.network.l2.c2s.SetPrivateStoreMsgSell;
import l2.gameserver.network.l2.c2s.SetPrivateStoreSellList;
import l2.gameserver.network.l2.c2s.SetPrivateStoreWholeMsg;
import l2.gameserver.network.l2.c2s.SnoopQuit;
import l2.gameserver.network.l2.c2s.StartRotatingC;
import l2.gameserver.network.l2.c2s.TradeDone;
import l2.gameserver.network.l2.c2s.TradeRequest;
import l2.gameserver.network.l2.c2s.UseItem;
import l2.gameserver.network.l2.c2s.ValidatePosition;
import l2.gameserver.network.l2.s2c.RequestNewEnchantClose;
import l2.gameserver.network.l2.s2c.RequestTargetActionMenu;
import l2.gameserver.scripts.Scripts;
import l2.gameserver.utils.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class GamePacketHandler
implements IAcceptFilter,
IClientFactory<GameClient>,
IMMOExecutor<GameClient>,
IPacketHandler<GameClient> {
    private static final Logger cE = LoggerFactory.getLogger(GamePacketHandler.class);
    private final Set<String> B = new CopyOnWriteArraySet<String>();

    @Override
    public ReceivablePacket<GameClient> handlePacket(ByteBuffer byteBuffer, GameClient gameClient) {
        int n = byteBuffer.position();
        int n2 = byteBuffer.get() & 0xFF;
        ReceivablePacket receivablePacket = null;
        if (CGModule.getInstance().isActive() && (receivablePacket = CGModule.getInstance().handlePacket(gameClient, n2)) != null) {
            return receivablePacket;
        }
        if (!gameClient.getPacketFilter().checkPacket(n2)) {
            return null;
        }
        try {
            int n3 = 0;
            block1 : switch (gameClient.getState()) {
                case CONNECTED: {
                    switch (n2) {
                        case 0: {
                            receivablePacket = new RequestStatus();
                            break block1;
                        }
                        case 14: {
                            receivablePacket = new ProtocolVersion();
                            break block1;
                        }
                        case 43: {
                            receivablePacket = new AuthLogin();
                            break block1;
                        }
                        case 203: {
                            receivablePacket = new ReplyGameGuardQuery();
                            break block1;
                        }
                        case 208: {
                            n3 = byteBuffer.getShort() & 0xFFFF;
                            if (CGModule.getInstance().isActive() && (receivablePacket = CGModule.getInstance().handlePacket(gameClient, n2, (Integer)n3)) != null) {
                                return receivablePacket;
                            }
                            if (!gameClient.getPacketFilter().checkPacketEx(n2, n3)) {
                                return null;
                            }
                            if (Config.DEBUG) {
                                System.out.printf("ExOpcode %02x for state %s\n", n3, gameClient.getState().toString());
                            }
                            switch (n3) {
                                default: 
                            }
                            break block1;
                        }
                    }
                    receivablePacket = this.a(gameClient, n2);
                    break;
                }
                case AUTHED: {
                    switch (n2) {
                        case 0: {
                            receivablePacket = new Logout();
                            break block1;
                        }
                        case 12: {
                            receivablePacket = new CharacterCreate();
                            break block1;
                        }
                        case 13: {
                            receivablePacket = new CharacterDelete();
                            break block1;
                        }
                        case 18: {
                            receivablePacket = new CharacterSelected();
                            break block1;
                        }
                        case 19: {
                            receivablePacket = new NewCharacter();
                            break block1;
                        }
                        case 123: {
                            receivablePacket = new CharacterRestore();
                            break block1;
                        }
                        case 169: {
                            receivablePacket = new RequestBlock();
                            break block1;
                        }
                        case 177: {
                            receivablePacket = new NetPing();
                            break block1;
                        }
                        case 203: {
                            receivablePacket = new ReplyGameGuardQuery();
                            break block1;
                        }
                        case 72: 
                        case 106: {
                            receivablePacket = DummyClientPacket.STATIC_DUMMY_CLIENT_PACKET;
                            break block1;
                        }
                        case 208: {
                            n3 = byteBuffer.getShort() & 0xFFFF;
                            if (CGModule.getInstance().isActive() && (receivablePacket = CGModule.getInstance().handlePacket(gameClient, n2, (Integer)n3)) != null) {
                                return receivablePacket;
                            }
                            if (!gameClient.getPacketFilter().checkPacketEx(n2, n3)) {
                                return null;
                            }
                            switch (n3) {
                                case 51: {
                                    receivablePacket = new GotoLobby();
                                    break block1;
                                }
                                case 166: {
                                    receivablePacket = new RequestEx2ndPasswordCheck();
                                    break block1;
                                }
                                case 167: {
                                    receivablePacket = new RequestEx2ndPasswordVerify();
                                    break block1;
                                }
                                case 168: {
                                    receivablePacket = new RequestEx2ndPasswordReq();
                                    break block1;
                                }
                                case 169: {
                                    receivablePacket = new RequestCharacterNameCreatable();
                                    break block1;
                                }
                                case 174: {
                                    receivablePacket = new RequestHardWareInfo();
                                    break block1;
                                }
                                case 260: {
                                    receivablePacket = new ExSendClientINI();
                                    break block1;
                                }
                                case 182: 
                                case 255: 
                                case 351: {
                                    receivablePacket = DummyClientPacket.STATIC_DUMMY_CLIENT_PACKET;
                                    break block1;
                                }
                            }
                            receivablePacket = this.a(gameClient, n2, n3);
                            break block1;
                        }
                    }
                    receivablePacket = this.a(gameClient, n2);
                    break;
                }
                case IN_GAME: {
                    switch (n2) {
                        case 0: {
                            receivablePacket = new Logout();
                            break block1;
                        }
                        case 1: {
                            receivablePacket = new AttackRequest();
                            break block1;
                        }
                        case 2: {
                            break block1;
                        }
                        case 3: {
                            receivablePacket = new RequestStartPledgeWar();
                            break block1;
                        }
                        case 4: {
                            break block1;
                        }
                        case 5: {
                            receivablePacket = new RequestStopPledgeWar();
                            break block1;
                        }
                        case 6: {
                            break block1;
                        }
                        case 7: {
                            receivablePacket = new ReplyGameGuardQuery();
                            break block1;
                        }
                        case 8: {
                            break block1;
                        }
                        case 9: {
                            receivablePacket = new RequestSetPledgeCrest();
                            break block1;
                        }
                        case 10: {
                            break block1;
                        }
                        case 11: {
                            receivablePacket = new RequestGiveNickName();
                            break block1;
                        }
                        case 12: {
                            break block1;
                        }
                        case 13: {
                            break block1;
                        }
                        case 15: {
                            receivablePacket = new MoveBackwardToLocation();
                            break block1;
                        }
                        case 16: {
                            break block1;
                        }
                        case 17: {
                            receivablePacket = new EnterWorld();
                            break block1;
                        }
                        case 18: {
                            break block1;
                        }
                        case 20: {
                            receivablePacket = new RequestItemList();
                            break block1;
                        }
                        case 21: {
                            break block1;
                        }
                        case 22: {
                            break block1;
                        }
                        case 23: {
                            receivablePacket = new RequestDropItem();
                            break block1;
                        }
                        case 24: {
                            break block1;
                        }
                        case 25: {
                            receivablePacket = new UseItem();
                            break block1;
                        }
                        case 26: {
                            receivablePacket = new TradeRequest();
                            break block1;
                        }
                        case 27: {
                            receivablePacket = new AddTradeItem();
                            break block1;
                        }
                        case 28: {
                            receivablePacket = new TradeDone();
                            break block1;
                        }
                        case 29: {
                            break block1;
                        }
                        case 30: {
                            break block1;
                        }
                        case 31: {
                            receivablePacket = new Action();
                            break block1;
                        }
                        case 32: {
                            break block1;
                        }
                        case 33: {
                            break block1;
                        }
                        case 34: {
                            receivablePacket = new RequestLinkHtml();
                            break block1;
                        }
                        case 35: {
                            receivablePacket = new RequestBypassToServer();
                            break block1;
                        }
                        case 36: {
                            receivablePacket = new RequestBBSwrite();
                            break block1;
                        }
                        case 37: {
                            receivablePacket = new RequestCreatePledge();
                            break block1;
                        }
                        case 38: {
                            receivablePacket = new RequestJoinPledge();
                            break block1;
                        }
                        case 39: {
                            receivablePacket = new RequestAnswerJoinPledge();
                            break block1;
                        }
                        case 40: {
                            receivablePacket = new RequestWithdrawalPledge();
                            break block1;
                        }
                        case 41: {
                            receivablePacket = new RequestOustPledgeMember();
                            break block1;
                        }
                        case 42: {
                            break block1;
                        }
                        case 44: {
                            receivablePacket = new RequestGetItemFromPet();
                            break block1;
                        }
                        case 45: {
                            break block1;
                        }
                        case 46: {
                            receivablePacket = new RequestAllyInfo();
                            break block1;
                        }
                        case 47: {
                            receivablePacket = new RequestCrystallizeItem();
                            break block1;
                        }
                        case 48: {
                            break block1;
                        }
                        case 49: {
                            receivablePacket = new SetPrivateStoreSellList();
                            break block1;
                        }
                        case 50: {
                            break block1;
                        }
                        case 51: {
                            receivablePacket = new RequestTeleport();
                            break block1;
                        }
                        case 52: {
                            break block1;
                        }
                        case 53: {
                            break block1;
                        }
                        case 54: {
                            break block1;
                        }
                        case 55: {
                            receivablePacket = new RequestSellItem();
                            break block1;
                        }
                        case 56: {
                            receivablePacket = new RequestMagicSkillList();
                            break block1;
                        }
                        case 57: {
                            receivablePacket = new RequestMagicSkillUse();
                            break block1;
                        }
                        case 58: {
                            receivablePacket = new Appearing();
                            break block1;
                        }
                        case 59: {
                            if (!Config.ALLOW_WAREHOUSE) break block1;
                            receivablePacket = new SendWareHouseDepositList();
                            break block1;
                        }
                        case 60: {
                            receivablePacket = new SendWareHouseWithDrawList();
                            break block1;
                        }
                        case 61: {
                            receivablePacket = new RequestShortCutReg();
                            break block1;
                        }
                        case 62: {
                            break block1;
                        }
                        case 63: {
                            receivablePacket = new RequestShortCutDel();
                            break block1;
                        }
                        case 64: {
                            receivablePacket = new RequestBuyItem();
                            break block1;
                        }
                        case 65: {
                            break block1;
                        }
                        case 66: {
                            receivablePacket = new RequestJoinParty();
                            break block1;
                        }
                        case 67: {
                            receivablePacket = new RequestAnswerJoinParty();
                            break block1;
                        }
                        case 68: {
                            receivablePacket = new RequestWithDrawalParty();
                            break block1;
                        }
                        case 69: {
                            receivablePacket = new RequestOustPartyMember();
                            break block1;
                        }
                        case 70: {
                            receivablePacket = new RequestDismissParty();
                            break block1;
                        }
                        case 71: {
                            receivablePacket = new CannotMoveAnymore();
                            break block1;
                        }
                        case 72: {
                            receivablePacket = new RequestTargetCanceld();
                            break block1;
                        }
                        case 73: {
                            receivablePacket = new Say2C();
                            break block1;
                        }
                        case 74: {
                            int n4 = byteBuffer.get() & 0xFF;
                            switch (n4) {
                                case 0: {
                                    break block1;
                                }
                                case 1: {
                                    break block1;
                                }
                                case 2: {
                                    break block1;
                                }
                                case 3: {
                                    break block1;
                                }
                            }
                            receivablePacket = this.a(gameClient, n2, n4);
                            break block1;
                        }
                        case 75: {
                            break block1;
                        }
                        case 76: {
                            break block1;
                        }
                        case 77: {
                            receivablePacket = new RequestPledgeMemberList();
                            break block1;
                        }
                        case 78: {
                            break block1;
                        }
                        case 79: {
                            break block1;
                        }
                        case 80: {
                            receivablePacket = new RequestSkillList();
                            break block1;
                        }
                        case 81: {
                            break block1;
                        }
                        case 82: {
                            receivablePacket = new MoveWithDelta();
                            break block1;
                        }
                        case 83: {
                            receivablePacket = new RequestGetOnVehicle();
                            break block1;
                        }
                        case 84: {
                            receivablePacket = new RequestGetOffVehicle();
                            break block1;
                        }
                        case 85: {
                            receivablePacket = new AnswerTradeRequest();
                            break block1;
                        }
                        case 86: {
                            receivablePacket = new RequestActionUse();
                            break block1;
                        }
                        case 87: {
                            receivablePacket = new RequestRestart();
                            break block1;
                        }
                        case 88: {
                            receivablePacket = new RequestSiegeInfo();
                            break block1;
                        }
                        case 89: {
                            receivablePacket = new ValidatePosition();
                            break block1;
                        }
                        case 90: {
                            receivablePacket = new RequestSEKCustom();
                            break block1;
                        }
                        case 91: {
                            receivablePacket = new StartRotatingC();
                            break block1;
                        }
                        case 92: {
                            receivablePacket = new FinishRotatingC();
                            break block1;
                        }
                        case 93: {
                            break block1;
                        }
                        case 94: {
                            receivablePacket = new RequestShowBoard();
                            break block1;
                        }
                        case 95: {
                            receivablePacket = new RequestEnchantItem();
                            break block1;
                        }
                        case 248: {
                            receivablePacket = new RequestNewEnchantClose();
                            break block1;
                        }
                        case 96: {
                            receivablePacket = new RequestDestroyItem();
                            break block1;
                        }
                        case 97: {
                            break block1;
                        }
                        case 98: {
                            receivablePacket = new RequestQuestList();
                            break block1;
                        }
                        case 99: {
                            receivablePacket = new RequestQuestAbort();
                            break block1;
                        }
                        case 100: {
                            break block1;
                        }
                        case 101: {
                            receivablePacket = new RequestPledgeInfo();
                            break block1;
                        }
                        case 102: {
                            receivablePacket = new RequestPledgeExtendedInfo();
                            break block1;
                        }
                        case 103: {
                            receivablePacket = new RequestPledgeCrest();
                            break block1;
                        }
                        case 104: {
                            break block1;
                        }
                        case 105: {
                            break block1;
                        }
                        case 106: {
                            receivablePacket = new RequestFriendInfoList();
                            break block1;
                        }
                        case 107: {
                            receivablePacket = new RequestSendL2FriendSay();
                            break block1;
                        }
                        case 108: {
                            receivablePacket = new RequestShowMiniMap();
                            break block1;
                        }
                        case 109: {
                            receivablePacket = new RequestSendMsnChatLog();
                            break block1;
                        }
                        case 110: {
                            receivablePacket = new RequestReload();
                            break block1;
                        }
                        case 111: {
                            receivablePacket = new RequestHennaEquip();
                            break block1;
                        }
                        case 112: {
                            receivablePacket = new RequestHennaUnequipList();
                            break block1;
                        }
                        case 113: {
                            receivablePacket = new RequestHennaUnequipInfo();
                            break block1;
                        }
                        case 114: {
                            receivablePacket = new RequestHennaUnequip();
                            break block1;
                        }
                        case 115: {
                            receivablePacket = new RequestAquireSkillInfo();
                            break block1;
                        }
                        case 116: {
                            receivablePacket = new SendBypassBuildCmd();
                            break block1;
                        }
                        case 117: {
                            receivablePacket = new RequestMoveToLocationInVehicle();
                            break block1;
                        }
                        case 118: {
                            receivablePacket = new CannotMoveAnymoreInVehicle();
                            break block1;
                        }
                        case 119: {
                            receivablePacket = new RequestFriendInvite();
                            break block1;
                        }
                        case 120: {
                            receivablePacket = new RequestFriendAddReply();
                            break block1;
                        }
                        case 121: {
                            receivablePacket = new RequestFriendList();
                            break block1;
                        }
                        case 122: {
                            receivablePacket = new RequestFriendDel();
                            break block1;
                        }
                        case 124: {
                            receivablePacket = new RequestAquireSkill();
                            break block1;
                        }
                        case 125: {
                            receivablePacket = new RequestRestartPoint();
                            break block1;
                        }
                        case 126: {
                            receivablePacket = new RequestGMCommand();
                            break block1;
                        }
                        case 127: {
                            receivablePacket = new RequestPartyMatchConfig();
                            break block1;
                        }
                        case 128: {
                            receivablePacket = new RequestPartyMatchList();
                            break block1;
                        }
                        case 129: {
                            receivablePacket = new RequestPartyMatchDetail();
                            break block1;
                        }
                        case 130: {
                            receivablePacket = new RequestPrivateStoreList();
                            break block1;
                        }
                        case 131: {
                            receivablePacket = new RequestPrivateStoreBuy();
                            break block1;
                        }
                        case 132: {
                            break block1;
                        }
                        case 133: {
                            receivablePacket = new RequestTutorialLinkHtml();
                            break block1;
                        }
                        case 134: {
                            receivablePacket = new RequestTutorialPassCmdToServer();
                            break block1;
                        }
                        case 135: {
                            receivablePacket = new RequestTutorialQuestionMark();
                            break block1;
                        }
                        case 136: {
                            receivablePacket = new RequestTutorialClientEvent();
                            break block1;
                        }
                        case 137: {
                            receivablePacket = new RequestPetition();
                            break block1;
                        }
                        case 138: {
                            receivablePacket = new RequestPetitionCancel();
                            break block1;
                        }
                        case 139: {
                            receivablePacket = new RequestGmList();
                            break block1;
                        }
                        case 140: {
                            receivablePacket = new RequestJoinAlly();
                            break block1;
                        }
                        case 141: {
                            receivablePacket = new RequestAnswerJoinAlly();
                            break block1;
                        }
                        case 142: {
                            receivablePacket = new RequestWithdrawAlly();
                            break block1;
                        }
                        case 143: {
                            receivablePacket = new RequestOustAlly();
                            break block1;
                        }
                        case 144: {
                            receivablePacket = new RequestDismissAlly();
                            break block1;
                        }
                        case 145: {
                            receivablePacket = new RequestSetAllyCrest();
                            break block1;
                        }
                        case 146: {
                            receivablePacket = new RequestAllyCrest();
                            break block1;
                        }
                        case 147: {
                            receivablePacket = new RequestChangePetName();
                            break block1;
                        }
                        case 148: {
                            receivablePacket = new RequestPetUseItem();
                            break block1;
                        }
                        case 149: {
                            receivablePacket = new RequestGiveItemToPet();
                            break block1;
                        }
                        case 150: {
                            receivablePacket = new RequestPrivateStoreQuitSell();
                            break block1;
                        }
                        case 151: {
                            receivablePacket = new SetPrivateStoreMsgSell();
                            break block1;
                        }
                        case 152: {
                            receivablePacket = new RequestPetGetItem();
                            break block1;
                        }
                        case 153: {
                            receivablePacket = new RequestPrivateStoreBuyManage();
                            break block1;
                        }
                        case 154: {
                            receivablePacket = new SetPrivateStoreBuyList();
                            break block1;
                        }
                        case 155: {
                            break block1;
                        }
                        case 156: {
                            receivablePacket = new RequestPrivateStoreQuitBuy();
                            break block1;
                        }
                        case 157: {
                            receivablePacket = new SetPrivateStoreMsgBuy();
                            break block1;
                        }
                        case 158: {
                            break block1;
                        }
                        case 159: {
                            receivablePacket = new RequestPrivateStoreBuySellList();
                            break block1;
                        }
                        case 160: {
                            receivablePacket = new RequestTimeCheck();
                            break block1;
                        }
                        case 161: {
                            break block1;
                        }
                        case 162: {
                            break block1;
                        }
                        case 163: {
                            break block1;
                        }
                        case 164: {
                            break block1;
                        }
                        case 165: {
                            break block1;
                        }
                        case 166: {
                            receivablePacket = new RequestSkillCoolTime();
                            break block1;
                        }
                        case 167: {
                            receivablePacket = new RequestPackageSendableItemList();
                            break block1;
                        }
                        case 168: {
                            receivablePacket = new RequestPackageSend();
                            break block1;
                        }
                        case 169: {
                            receivablePacket = new RequestBlock();
                            break block1;
                        }
                        case 170: {
                            break block1;
                        }
                        case 171: {
                            receivablePacket = new RequestCastleSiegeAttackerList();
                            break block1;
                        }
                        case 172: {
                            receivablePacket = new RequestCastleSiegeDefenderList();
                            break block1;
                        }
                        case 173: {
                            receivablePacket = new RequestJoinCastleSiege();
                            break block1;
                        }
                        case 174: {
                            receivablePacket = new RequestConfirmCastleSiegeWaitingList();
                            break block1;
                        }
                        case 175: {
                            receivablePacket = new RequestSetCastleSiegeTime();
                            break block1;
                        }
                        case 176: {
                            receivablePacket = new RequestMultiSellChoose();
                            break block1;
                        }
                        case 177: {
                            receivablePacket = new NetPing();
                            break block1;
                        }
                        case 178: {
                            receivablePacket = new RequestRemainTime();
                            break block1;
                        }
                        case 179: {
                            receivablePacket = new BypassUserCmd();
                            break block1;
                        }
                        case 180: {
                            receivablePacket = new SnoopQuit();
                            break block1;
                        }
                        case 181: {
                            receivablePacket = new RequestRecipeBookOpen();
                            break block1;
                        }
                        case 182: {
                            receivablePacket = new RequestRecipeItemDelete();
                            break block1;
                        }
                        case 183: {
                            receivablePacket = new RequestRecipeItemMakeInfo();
                            break block1;
                        }
                        case 184: {
                            receivablePacket = new RequestRecipeItemMakeSelf();
                            break block1;
                        }
                        case 185: {
                            break block1;
                        }
                        case 186: {
                            receivablePacket = new RequestRecipeShopMessageSet();
                            break block1;
                        }
                        case 187: {
                            receivablePacket = new RequestRecipeShopListSet();
                            break block1;
                        }
                        case 188: {
                            receivablePacket = new RequestRecipeShopManageQuit();
                            break block1;
                        }
                        case 189: {
                            receivablePacket = new RequestRecipeShopManageCancel();
                            break block1;
                        }
                        case 190: {
                            receivablePacket = new RequestRecipeShopMakeInfo();
                            break block1;
                        }
                        case 191: {
                            receivablePacket = new RequestRecipeShopMakeDo();
                            break block1;
                        }
                        case 192: {
                            receivablePacket = new RequestRecipeShopSellList();
                            break block1;
                        }
                        case 193: {
                            receivablePacket = new RequestObserverEnd();
                            break block1;
                        }
                        case 194: {
                            break block1;
                        }
                        case 195: {
                            receivablePacket = new RequestHennaList();
                            break block1;
                        }
                        case 196: {
                            receivablePacket = new RequestHennaItemInfo();
                            break block1;
                        }
                        case 197: {
                            receivablePacket = new RequestBuySeed();
                            break block1;
                        }
                        case 198: {
                            receivablePacket = new ConfirmDlg();
                            break block1;
                        }
                        case 199: {
                            receivablePacket = new RequestPreviewItem();
                            break block1;
                        }
                        case 200: {
                            receivablePacket = new RequestSSQStatus();
                            break block1;
                        }
                        case 201: {
                            receivablePacket = new PetitionVote();
                            break block1;
                        }
                        case 202: {
                            break block1;
                        }
                        case 203: {
                            receivablePacket = new ReplyGameGuardQuery();
                            break block1;
                        }
                        case 204: {
                            receivablePacket = new RequestPledgePower();
                            break block1;
                        }
                        case 205: {
                            receivablePacket = new RequestMakeMacro();
                            break block1;
                        }
                        case 206: {
                            receivablePacket = new RequestDeleteMacro();
                            break block1;
                        }
                        case 207: {
                            break block1;
                        }
                        case 208: {
                            if (byteBuffer.remaining() < 2) {
                                Log.network("Client: " + gameClient.toString() + " sent a 0xd0 without the second opcode.");
                                break block1;
                            }
                            n3 = byteBuffer.getShort() & 0xFFFF;
                            if (CGModule.getInstance().isActive() && (receivablePacket = CGModule.getInstance().handlePacket(gameClient, n2, (Integer)n3)) != null) {
                                return receivablePacket;
                            }
                            if (!gameClient.getPacketFilter().checkPacketEx(n2, n3)) {
                                return null;
                            }
                            switch (n3) {
                                case 0: {
                                    break block1;
                                }
                                case 1: {
                                    receivablePacket = new RequestManorList();
                                    break block1;
                                }
                                case 2: {
                                    receivablePacket = new RequestProcureCropList();
                                    break block1;
                                }
                                case 3: {
                                    receivablePacket = new RequestSetSeed();
                                    break block1;
                                }
                                case 4: {
                                    receivablePacket = new RequestSetCrop();
                                    break block1;
                                }
                                case 5: {
                                    receivablePacket = new RequestWriteHeroWords();
                                    break block1;
                                }
                                case 6: {
                                    receivablePacket = new RequestExMPCCAskJoin();
                                    break block1;
                                }
                                case 7: {
                                    receivablePacket = new RequestExMPCCAcceptJoin();
                                    break block1;
                                }
                                case 8: {
                                    receivablePacket = new RequestExOustFromMPCC();
                                    break block1;
                                }
                                case 9: {
                                    receivablePacket = new RequestOustFromPartyRoom();
                                    break block1;
                                }
                                case 10: {
                                    receivablePacket = new RequestDismissPartyRoom();
                                    break block1;
                                }
                                case 11: {
                                    receivablePacket = new RequestWithdrawPartyRoom();
                                    break block1;
                                }
                                case 12: {
                                    receivablePacket = new RequestHandOverPartyMaster();
                                    break block1;
                                }
                                case 13: {
                                    receivablePacket = new RequestAutoSoulShot();
                                    break block1;
                                }
                                case 14: {
                                    receivablePacket = new RequestExEnchantSkillInfo();
                                    break block1;
                                }
                                case 15: {
                                    receivablePacket = new RequestExEnchantSkill();
                                    break block1;
                                }
                                case 16: {
                                    receivablePacket = new RequestPledgeEmblem();
                                    break block1;
                                }
                                case 17: {
                                    receivablePacket = new RequestSetPledgeCrestLarge();
                                    break block1;
                                }
                                case 18: {
                                    receivablePacket = new RequestPledgeSetAcademyMaster();
                                    break block1;
                                }
                                case 19: {
                                    receivablePacket = new RequestPledgePowerGradeList();
                                    break block1;
                                }
                                case 20: {
                                    receivablePacket = new RequestPledgeMemberPowerInfo();
                                    break block1;
                                }
                                case 21: {
                                    receivablePacket = new RequestPledgeSetMemberPowerGrade();
                                    break block1;
                                }
                                case 22: {
                                    receivablePacket = new RequestPledgeMemberInfo();
                                    break block1;
                                }
                                case 23: {
                                    receivablePacket = new RequestPledgeWarList();
                                    break block1;
                                }
                                case 24: {
                                    receivablePacket = new RequestExFishRanking();
                                    break block1;
                                }
                                case 25: {
                                    receivablePacket = new RequestPCCafeCouponUse();
                                    break block1;
                                }
                                case 26: {
                                    break block1;
                                }
                                case 27: {
                                    receivablePacket = new RequestDuelStart();
                                    break block1;
                                }
                                case 28: {
                                    receivablePacket = new RequestDuelAnswerStart();
                                    break block1;
                                }
                                case 29: {
                                    receivablePacket = new RequestTutorialClientEvent();
                                    break block1;
                                }
                                case 30: {
                                    receivablePacket = new RequestExRqItemLink();
                                    break block1;
                                }
                                case 31: {
                                    break block1;
                                }
                                case 32: {
                                    receivablePacket = new RequestExMoveToLocationInAirShip();
                                    break block1;
                                }
                                case 33: {
                                    receivablePacket = new RequestKeyMapping();
                                    break block1;
                                }
                                case 34: {
                                    receivablePacket = new RequestSaveKeyMapping();
                                    break block1;
                                }
                                case 35: {
                                    receivablePacket = new RequestExRemoveItemAttribute();
                                    break block1;
                                }
                                case 36: {
                                    receivablePacket = new RequestSaveInventoryOrder();
                                    break block1;
                                }
                                case 37: {
                                    receivablePacket = new RequestExitPartyMatchingWaitingRoom();
                                    break block1;
                                }
                                case 38: {
                                    receivablePacket = new RequestConfirmTargetItem();
                                    break block1;
                                }
                                case 39: {
                                    receivablePacket = new RequestConfirmRefinerItem();
                                    break block1;
                                }
                                case 40: {
                                    receivablePacket = new RequestConfirmGemStone();
                                    break block1;
                                }
                                case 41: {
                                    receivablePacket = new RequestOlympiadObserverEnd();
                                    break block1;
                                }
                                case 42: {
                                    receivablePacket = new RequestCursedWeaponList();
                                    break block1;
                                }
                                case 43: {
                                    receivablePacket = new RequestCursedWeaponLocation();
                                    break block1;
                                }
                                case 44: {
                                    receivablePacket = new RequestPledgeReorganizeMember();
                                    break block1;
                                }
                                case 45: {
                                    receivablePacket = new RequestExMPCCShowPartyMembersInfo();
                                    break block1;
                                }
                                case 46: {
                                    receivablePacket = new RequestExOlympiadObserverEnd();
                                    break block1;
                                }
                                case 47: {
                                    receivablePacket = new RequestAskJoinPartyRoom();
                                    break block1;
                                }
                                case 48: {
                                    receivablePacket = new AnswerJoinPartyRoom();
                                    break block1;
                                }
                                case 49: {
                                    receivablePacket = new RequestListPartyMatchingWaitingRoom();
                                    break block1;
                                }
                                case 50: {
                                    receivablePacket = new RequestEnchantItemAttribute();
                                    break block1;
                                }
                                case 53: {
                                    receivablePacket = new RequestExMoveToLocationAirShip();
                                    break block1;
                                }
                                case 54: {
                                    receivablePacket = new RequestBidItemAuction();
                                    break block1;
                                }
                                case 55: {
                                    receivablePacket = new RequestInfoItemAuction();
                                    break block1;
                                }
                                case 56: {
                                    receivablePacket = new RequestExChangeName();
                                    break block1;
                                }
                                case 57: {
                                    receivablePacket = new RequestAllCastleInfo();
                                    break block1;
                                }
                                case 58: {
                                    receivablePacket = new RequestAllFortressInfo();
                                    break block1;
                                }
                                case 59: {
                                    receivablePacket = new RequestAllAgitInfo();
                                    break block1;
                                }
                                case 60: {
                                    receivablePacket = new RequestFortressSiegeInfo();
                                    break block1;
                                }
                                case 61: {
                                    receivablePacket = new RequestGetBossRecord();
                                    break block1;
                                }
                                case 62: {
                                    receivablePacket = new RequestRefine();
                                    break block1;
                                }
                                case 63: {
                                    receivablePacket = new RequestConfirmCancelItem();
                                    break block1;
                                }
                                case 64: {
                                    receivablePacket = new RequestRefineCancel();
                                    break block1;
                                }
                                case 65: {
                                    receivablePacket = new RequestExMagicSkillUseGround();
                                    break block1;
                                }
                                case 66: {
                                    receivablePacket = new RequestDuelSurrender();
                                    break block1;
                                }
                                case 67: {
                                    break block1;
                                }
                                case 69: {
                                    receivablePacket = new RequestFortressMapInfo();
                                    break block1;
                                }
                                case 70: {
                                    receivablePacket = new RequestPVPMatchRecord();
                                    break block1;
                                }
                                case 71: {
                                    receivablePacket = new SetPrivateStoreWholeMsg();
                                    break block1;
                                }
                                case 72: {
                                    receivablePacket = new RequestDispel();
                                    break block1;
                                }
                                case 73: {
                                    receivablePacket = new RequestExTryToPutEnchantTargetItem();
                                    break block1;
                                }
                                case 74: {
                                    receivablePacket = new RequestExTryToPutEnchantSupportItem();
                                    break block1;
                                }
                                case 75: {
                                    receivablePacket = new RequestExCancelEnchantItem();
                                    break block1;
                                }
                                case 76: {
                                    receivablePacket = new RequestChangeNicknameColor();
                                    break block1;
                                }
                                case 77: {
                                    receivablePacket = new RequestResetNickname();
                                    break block1;
                                }
                                case 78: {
                                    int n5 = byteBuffer.getInt();
                                    switch (n5) {
                                        case 0: {
                                            receivablePacket = new RequestBookMarkSlotInfo();
                                            break block1;
                                        }
                                        case 1: {
                                            receivablePacket = new RequestSaveBookMarkSlot();
                                            break block1;
                                        }
                                        case 2: {
                                            receivablePacket = new RequestModifyBookMarkSlot();
                                            break block1;
                                        }
                                        case 3: {
                                            receivablePacket = new RequestDeleteBookMarkSlot();
                                            break block1;
                                        }
                                        case 4: {
                                            receivablePacket = new RequestTeleportBookMark();
                                            break block1;
                                        }
                                        case 5: {
                                            break block1;
                                        }
                                    }
                                    receivablePacket = this.a(gameClient, n2, n5);
                                    break block1;
                                }
                                case 79: {
                                    receivablePacket = new RequestWithDrawPremiumItem();
                                    break block1;
                                }
                                case 80: {
                                    receivablePacket = new RequestExJump();
                                    break block1;
                                }
                                case 81: {
                                    receivablePacket = new RequestExStartShowCrataeCubeRank();
                                    break block1;
                                }
                                case 82: {
                                    receivablePacket = new RequestExStopShowCrataeCubeRank();
                                    break block1;
                                }
                                case 83: {
                                    receivablePacket = new NotifyStartMiniGame();
                                    break block1;
                                }
                                case 84: {
                                    receivablePacket = new RequestExJoinDominionWar();
                                    break block1;
                                }
                                case 85: {
                                    receivablePacket = new RequestExDominionInfo();
                                    break block1;
                                }
                                case 86: {
                                    receivablePacket = new RequestExCleftEnter();
                                    break block1;
                                }
                                case 87: {
                                    receivablePacket = new RequestExCubeGameChangeTeam();
                                    break block1;
                                }
                                case 88: {
                                    receivablePacket = new RequestExEndScenePlayer();
                                    break block1;
                                }
                                case 89: {
                                    receivablePacket = new RequestExCubeGameReadyAnswer();
                                    break block1;
                                }
                                case 90: {
                                    receivablePacket = new RequestExListMpccWaiting();
                                    break block1;
                                }
                                case 91: {
                                    receivablePacket = new RequestExManageMpccRoom();
                                    break block1;
                                }
                                case 92: {
                                    receivablePacket = new RequestExJoinMpccRoom();
                                    break block1;
                                }
                                case 93: {
                                    receivablePacket = new RequestExOustFromMpccRoom();
                                    break block1;
                                }
                                case 94: {
                                    receivablePacket = new RequestExDismissMpccRoom();
                                    break block1;
                                }
                                case 95: {
                                    receivablePacket = new RequestExWithdrawMpccRoom();
                                    break block1;
                                }
                                case 96: {
                                    receivablePacket = new RequestExSeedPhase();
                                    break block1;
                                }
                                case 97: {
                                    receivablePacket = new RequestExMpccPartymasterList();
                                    break block1;
                                }
                                case 98: {
                                    receivablePacket = new RequestExPostItemList();
                                    break block1;
                                }
                                case 99: {
                                    receivablePacket = new RequestExSendPost();
                                    break block1;
                                }
                                case 100: {
                                    receivablePacket = new RequestExRequestReceivedPostList();
                                    break block1;
                                }
                                case 101: {
                                    receivablePacket = new RequestExDeleteReceivedPost();
                                    break block1;
                                }
                                case 102: {
                                    receivablePacket = new RequestExRequestReceivedPost();
                                    break block1;
                                }
                                case 103: {
                                    receivablePacket = new RequestExReceivePost();
                                    break block1;
                                }
                                case 104: {
                                    receivablePacket = new RequestExRejectPost();
                                    break block1;
                                }
                                case 105: {
                                    receivablePacket = new RequestExRequestSentPostList();
                                    break block1;
                                }
                                case 106: {
                                    receivablePacket = new RequestExDeleteSentPost();
                                    break block1;
                                }
                                case 107: {
                                    receivablePacket = new RequestExRequestSentPost();
                                    break block1;
                                }
                                case 108: {
                                    receivablePacket = new RequestExCancelSentPost();
                                    break block1;
                                }
                                case 109: {
                                    receivablePacket = new RequestExShowNewUserPetition();
                                    break block1;
                                }
                                case 110: {
                                    receivablePacket = new RequestExShowStepTwo();
                                    break block1;
                                }
                                case 111: {
                                    receivablePacket = new RequestExShowStepThree();
                                    break block1;
                                }
                                case 112: {
                                    break block1;
                                }
                                case 113: {
                                    break block1;
                                }
                                case 114: {
                                    receivablePacket = new RequestExRefundItem();
                                    break block1;
                                }
                                case 115: {
                                    receivablePacket = new RequestExBuySellUIClose();
                                    break block1;
                                }
                                case 116: {
                                    receivablePacket = new RequestExEventMatchObserverEnd();
                                    break block1;
                                }
                                case 117: {
                                    receivablePacket = new RequestPartyLootModification();
                                    break block1;
                                }
                                case 118: {
                                    receivablePacket = new AnswerPartyLootModification();
                                    break block1;
                                }
                                case 119: {
                                    receivablePacket = new AnswerCoupleAction();
                                    break block1;
                                }
                                case 120: {
                                    receivablePacket = new RequestExBR_EventRankerList();
                                    break block1;
                                }
                                case 121: {
                                    break block1;
                                }
                                case 122: {
                                    receivablePacket = new RequestAddExpandQuestAlarm();
                                    break block1;
                                }
                                case 123: {
                                    receivablePacket = new RequestVoteNew();
                                    break block1;
                                }
                                case 124: {
                                    break block1;
                                }
                                case 125: {
                                    break block1;
                                }
                                case 126: {
                                    break block1;
                                }
                                case 127: {
                                    break block1;
                                }
                                case 128: {
                                    int n6 = byteBuffer.getInt();
                                    break block1;
                                }
                                case 129: {
                                    receivablePacket = new RequestExAddPostFriendForPostBox();
                                    break block1;
                                }
                                case 130: {
                                    receivablePacket = new RequestExDeletePostFriendForPostBox();
                                    break block1;
                                }
                                case 131: {
                                    receivablePacket = new RequestExShowPostFriendListForPostBox();
                                    break block1;
                                }
                                case 132: {
                                    receivablePacket = new RequestExFriendListForPostBox();
                                    break block1;
                                }
                                case 133: {
                                    receivablePacket = new RequestOlympiadMatchList();
                                    break block1;
                                }
                                case 134: {
                                    receivablePacket = new RequestExBR_GamePoint();
                                    break block1;
                                }
                                case 135: {
                                    receivablePacket = new RequestExBR_ProductList();
                                    break block1;
                                }
                                case 136: {
                                    receivablePacket = new RequestExBR_ProductInfo();
                                    break block1;
                                }
                                case 137: {
                                    receivablePacket = new RequestExBR_BuyProduct();
                                    break block1;
                                }
                                case 138: {
                                    receivablePacket = new RequestExBR_RecentProductList();
                                    break block1;
                                }
                                case 139: {
                                    receivablePacket = new RequestExBR_MiniGameLoadScores();
                                    break block1;
                                }
                                case 140: {
                                    receivablePacket = new RequestExBR_MiniGameInsertScore();
                                    break block1;
                                }
                                case 141: {
                                    receivablePacket = new RequestExBR_LectureMark();
                                    break block1;
                                }
                                case 142: {
                                    receivablePacket = new RequestCrystallizeEstimate();
                                    break block1;
                                }
                                case 143: {
                                    receivablePacket = new RequestCrystallizeItemCancel();
                                    break block1;
                                }
                                case 170: {
                                    receivablePacket = new RequestGoodsInventoryInfo();
                                    break block1;
                                }
                                case 171: {
                                    break block1;
                                }
                                case 182: {
                                    receivablePacket = DummyClientPacket.STATIC_DUMMY_CLIENT_PACKET;
                                    break block1;
                                }
                                case 297: {
                                    receivablePacket = new RequestRaidBossSpawnInfo();
                                    break block1;
                                }
                                case 266: {
                                    receivablePacket = new RequestVipProductList();
                                    break block1;
                                }
                                case 267: {
                                    receivablePacket = DummyClientPacket.STATIC_DUMMY_CLIENT_PACKET;
                                    break block1;
                                }
                                case 270: {
                                    receivablePacket = new RequestVipInfo();
                                    break block1;
                                }
                                case 228: {
                                    receivablePacket = new RequestExRemoveEnchantSupportItem();
                                    break block1;
                                }
                                case 148: {
                                    receivablePacket = new RequestFriendDetailInfo();
                                    break block1;
                                }
                                case 149: {
                                    receivablePacket = new RequestUpdateFriendMemo();
                                    break block1;
                                }
                                case 150: {
                                    receivablePacket = new RequestUpdateBlockMemo();
                                    break block1;
                                }
                                case 295: {
                                    receivablePacket = new RequestBlockMemoInfo();
                                    break block1;
                                }
                                case 244: {
                                    receivablePacket = new RequestNewEnchantPushOne();
                                    break block1;
                                }
                                case 245: {
                                    receivablePacket = new RequestNewEnchantRemoveOne();
                                    break block1;
                                }
                                case 246: {
                                    receivablePacket = new RequestNewEnchantPushTwo();
                                    break block1;
                                }
                                case 247: {
                                    receivablePacket = new RequestNewEnchantRemoveTwo();
                                    break block1;
                                }
                                case 248: {
                                    receivablePacket = new RequestNewEnchantClose();
                                    break block1;
                                }
                                case 249: {
                                    receivablePacket = new RequestNewEnchantTry();
                                    break block1;
                                }
                                case 250: {
                                    receivablePacket = new RequestNewEnchantRetryToPutItems();
                                    break block1;
                                }
                                case 230: {
                                    receivablePacket = new RequestDivideAdenaStart();
                                    break block1;
                                }
                                case 231: {
                                    receivablePacket = new RequestDivideAdenaCancel();
                                    break block1;
                                }
                                case 232: {
                                    receivablePacket = new RequestDivideAdena();
                                    break block1;
                                }
                                case 254: {
                                    receivablePacket = new RequestTargetActionMenu();
                                    break block1;
                                }
                                case 264: {
                                    receivablePacket = new RequestExItemEnsoul();
                                    break block1;
                                }
                                case 286: {
                                    receivablePacket = new RequestTodoList();
                                    break block1;
                                }
                                case 288: {
                                    receivablePacket = new RequestOneDayRewardReceive();
                                    break block1;
                                }
                                case 296: {
                                    receivablePacket = new RequestTryEnSoulExtraction();
                                    break block1;
                                }
                                case 186: {
                                    receivablePacket = new RequestInzoneWaitingTime();
                                    break block1;
                                }
                                case 221: {
                                    receivablePacket = new RequestPledgeDraftListApply();
                                    break block1;
                                }
                                case 220: {
                                    receivablePacket = new RequestPledgeDraftListSearch();
                                    break block1;
                                }
                                case 222: {
                                    receivablePacket = new RequestPledgeRecruitApplyInfo();
                                    break block1;
                                }
                                case 213: {
                                    receivablePacket = new RequestPledgeRecruitBoardAccess();
                                    break block1;
                                }
                                case 214: {
                                    receivablePacket = new RequestPledgeRecruitBoardDetail();
                                    break block1;
                                }
                                case 212: {
                                    receivablePacket = new RequestPledgeRecruitBoardSearch();
                                    break block1;
                                }
                                case 211: {
                                    receivablePacket = new RequestPledgeRecruitInfo();
                                    break block1;
                                }
                                case 273: {
                                    receivablePacket = new RequestPledgeSignInForOpenJoiningMethod();
                                    break block1;
                                }
                                case 216: {
                                    receivablePacket = new RequestPledgeWaitingApplied();
                                    break block1;
                                }
                                case 215: {
                                    receivablePacket = new RequestPledgeWaitingApply();
                                    break block1;
                                }
                                case 217: {
                                    receivablePacket = new RequestPledgeWaitingList();
                                    break block1;
                                }
                                case 218: {
                                    receivablePacket = new RequestPledgeWaitingUser();
                                    break block1;
                                }
                                case 219: {
                                    receivablePacket = new RequestPledgeWaitingUserAccept();
                                    break block1;
                                }
                                case 227: {
                                    receivablePacket = new RequestExAddEnchantScrollItem();
                                    break block1;
                                }
                                case 290: 
                                case 291: 
                                case 352: {
                                    receivablePacket = DummyClientPacket.STATIC_DUMMY_CLIENT_PACKET;
                                    break block1;
                                }
                                case 240: {
                                    receivablePacket = new ExPCCafeRequestOpenWindowWithoutNPC();
                                    break block1;
                                }
                                case 255: 
                                case 351: {
                                    receivablePacket = new RequestUserBanInfo();
                                    break block1;
                                }
                            }
                            receivablePacket = this.a(gameClient, n2, n3);
                            break block1;
                        }
                    }
                    receivablePacket = this.a(gameClient, n2);
                }
            }
        }
        catch (BufferUnderflowException bufferUnderflowException) {
            gameClient.onPacketReadFail();
        }
        if (Config.DEBUG) {
            if (receivablePacket != null) {
                cE.info("receive packet " + receivablePacket.getClass().getSimpleName());
            } else {
                byteBuffer.position(n);
                byte[] byArray = new byte[byteBuffer.remaining()];
                byteBuffer.get(byArray);
                System.out.println("Unknown packet dump in state " + gameClient.getState().name() + ":\n" + GamePacketHandler.formatHex(byArray));
            }
        }
        if (receivablePacket != null && !gameClient.getPacketFilter().checkPacket(receivablePacket)) {
            return null;
        }
        return receivablePacket;
    }

    public static final String formatHex(byte[] byArray) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < byArray.length; i += 16) {
            int n;
            int n2;
            stringBuilder.append(String.format("%04x: ", i));
            for (n2 = 0; n2 < 16; ++n2) {
                n = i + n2;
                if (n < byArray.length) {
                    stringBuilder.append(String.format("%02x ", byArray[n]));
                    continue;
                }
                stringBuilder.append("   ");
            }
            stringBuilder.append(": ");
            for (n2 = 0; n2 < 16; ++n2) {
                n = i + n2;
                if (n < byArray.length) {
                    byte by = byArray[n];
                    if (by > 31 && by < 128) {
                        stringBuilder.append(String.format("%c", by));
                        continue;
                    }
                    stringBuilder.append('.');
                    continue;
                }
                stringBuilder.append(' ');
            }
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }

    private static String a(Integer ... integerArray) {
        return Arrays.stream(integerArray).map(n -> Integer.toHexString(n).toUpperCase(Locale.US)).collect(Collectors.joining(":"));
    }

    private ReceivablePacket<GameClient> a(GameClient gameClient, int n) {
        List<Scripts.ScriptClassAndMethod> list = Scripts.onScriptPacket.get(n);
        if (list != null && !list.isEmpty()) {
            return new ScriptPacket().setOp(n).setHandlers(list);
        }
        cE.info("Unknown packet " + GamePacketHandler.a(new Integer[]{n}) + ", state: " + gameClient.getState());
        gameClient.onUnknownPacket();
        return null;
    }

    private ReceivablePacket<GameClient> a(GameClient gameClient, int n, int n2) {
        List<Scripts.ScriptClassAndMethod> list = Scripts.onScriptExPacket.get(n2);
        if (list != null && !list.isEmpty()) {
            return new ScriptExPacket().setOpEx(n2).setOp(n).setHandlers(list);
        }
        cE.info("Unknown packet " + GamePacketHandler.a(n, n2) + ", state: " + gameClient.getState());
        gameClient.onUnknownPacket();
        return null;
    }

    @Override
    public GameClient create(MMOConnection<GameClient> mMOConnection) {
        return new GameClient(mMOConnection);
    }

    @Override
    public void execute(Runnable runnable) {
        ThreadPoolManager.getInstance().execute(runnable);
    }

    public void addWhitelistedIp(String string) {
        this.B.add(string);
    }

    public boolean isWhitelistedIp(String string) {
        return this.B.contains(string) || Config.VALID_IPS_LIST.contains(string);
    }

    @Override
    public boolean accept(SocketChannel socketChannel) {
        try {
            String string = socketChannel.socket().getInetAddress().getHostAddress();
            if (!Config.REJECT_INVALID_CONNECTIONS) {
                return true;
            }
            return this.B.contains(string) || Config.VALID_IPS_LIST.contains(string);
        }
        catch (Exception exception) {
            cE.error("Accept interrupted " + exception);
            return true;
        }
    }
}
