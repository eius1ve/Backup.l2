/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.tuple.Triple
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package l2.gameserver.network.l2.c2s;

import l2.gameserver.Config;
import l2.gameserver.dao.CharacterDAO;
import l2.gameserver.data.xml.holder.MacroDataHolder;
import l2.gameserver.data.xml.holder.SkillAcquireHolder;
import l2.gameserver.data.xml.parser.MacroDataParser;
import l2.gameserver.instancemanager.QuestManager;
import l2.gameserver.model.GameObjectsStorage;
import l2.gameserver.model.Playable;
import l2.gameserver.model.Player;
import l2.gameserver.model.SkillLearn;
import l2.gameserver.model.actor.instances.player.Macro;
import l2.gameserver.model.actor.instances.player.ShortCut;
import l2.gameserver.model.base.AcquireType;
import l2.gameserver.model.base.ClassId;
import l2.gameserver.model.base.Experience;
import l2.gameserver.model.items.ItemInstance;
import l2.gameserver.model.quest.Quest;
import l2.gameserver.network.l2.GameClient;
import l2.gameserver.network.l2.c2s.L2GameClientPacket;
import l2.gameserver.network.l2.components.IStaticPacket;
import l2.gameserver.network.l2.s2c.CharacterCreateSuccess;
import l2.gameserver.network.l2.s2c.CharacterSelectionInfo;
import l2.gameserver.network.l2.s2c.ExIsCharNameCreatable;
import l2.gameserver.network.l2.s2c.MacroUpdateType;
import l2.gameserver.network.l2.s2c.SendMacroList;
import l2.gameserver.network.l2.s2c.ShortCutRegister;
import l2.gameserver.tables.ShortCutsTable;
import l2.gameserver.tables.SkillTable;
import l2.gameserver.templates.PlayerTemplate;
import l2.gameserver.templates.item.ItemTemplate;
import l2.gameserver.utils.ItemFunctions;
import l2.gameserver.utils.Util;
import org.apache.commons.lang3.tuple.Triple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CharacterCreate
extends L2GameClientPacket {
    private static final Logger cH = LoggerFactory.getLogger(CharacterCreate.class);
    private String _name;
    private int gg;
    private int ga;
    private int ge;
    private int gf;
    private int gd;

    @Override
    protected void readImpl() {
        this._name = this.readS();
        this.readD();
        this.gg = this.readD();
        this.ga = this.readD();
        this.readD();
        this.readD();
        this.readD();
        this.readD();
        this.readD();
        this.readD();
        this.ge = this.readD();
        this.gf = this.readD();
        this.gd = this.readD();
    }

    @Override
    protected void runImpl() {
        for (ClassId classId : ClassId.VALUES) {
            if (classId.getId() != this.ga || classId.getLevel() == 1) continue;
            return;
        }
        GameClient gameClient = (GameClient)this.getClient();
        if (gameClient == null) {
            return;
        }
        if (CharacterDAO.getInstance().accountCharNumber(((GameClient)this.getClient()).getLogin()) >= 8) {
            this.sendPacket(ExIsCharNameCreatable.TOO_MANY_CHARACTERS);
            return;
        }
        if (!Util.isMatchingRegexp(this._name, Config.CNAME_TEMPLATE)) {
            this.sendPacket(ExIsCharNameCreatable.ENTER_CHAR_NAME__MAX_16_CHARS);
            return;
        }
        if (Util.isMatchingRegexp(this._name.toLowerCase(), Config.CNAME_FORBIDDEN_PATTERN)) {
            this.sendPacket(ExIsCharNameCreatable.NAME_ALREADY_EXISTS);
            return;
        }
        if (GameObjectsStorage.getPlayer(this._name) != null || CharacterDAO.getInstance().getObjectIdByName(this._name) > 0) {
            this.sendPacket(ExIsCharNameCreatable.NAME_ALREADY_EXISTS);
            return;
        }
        for (String string : Config.CNAME_FORBIDDEN_NAMES) {
            if (!string.equalsIgnoreCase(this._name)) continue;
            this.sendPacket(ExIsCharNameCreatable.WRONG_NAME);
            return;
        }
        if (this.ge < 0 || this.gg == 0 && this.ge > 4 || this.gg != 0 && this.ge > 6) {
            this.sendPacket(ExIsCharNameCreatable.UNABLE_TO_CREATE_A_CHARACTER);
            return;
        }
        if (this.gd > 2 || this.gd < 0) {
            this.sendPacket(ExIsCharNameCreatable.UNABLE_TO_CREATE_A_CHARACTER);
            return;
        }
        if (this.gf > 3 || this.gf < 0) {
            this.sendPacket(ExIsCharNameCreatable.UNABLE_TO_CREATE_A_CHARACTER);
            return;
        }
        Player player = Player.create(this.ga, this.gg, ((GameClient)this.getClient()).getLogin(), this._name, this.ge, this.gf, this.gd);
        if (player == null) {
            return;
        }
        CharacterSelectionInfo characterSelectionInfo = new CharacterSelectionInfo(gameClient.getLogin(), gameClient.getSessionKey().playOkID1);
        this.sendPacket(CharacterCreateSuccess.STATIC, characterSelectionInfo);
        this.a((GameClient)this.getClient(), player);
    }

    private void a(GameClient gameClient, Player player) {
        int n;
        PlayerTemplate playerTemplate = player.getTemplate();
        Player.restoreCharSubClasses(player);
        if (Config.STARTING_ADENA > 0) {
            player.addAdena(Config.STARTING_ADENA);
        }
        player.setLoc(playerTemplate.spawnLoc);
        if (Config.ALT_NEW_CHARACTER_LEVEL > 0) {
            player.getActiveClass().setExp(Experience.getExpForLevel(Config.ALT_NEW_CHARACTER_LEVEL));
        }
        if (Config.CHAR_TITLE) {
            player.setTitle(Config.ADD_CHAR_TITLE);
        } else {
            player.setTitle("");
        }
        for (ItemTemplate shortCut2 : playerTemplate.getItems()) {
            ItemInstance itemInstance = ItemFunctions.createItem(shortCut2.getItemId());
            player.getInventory().addItem(itemInstance);
            if (!itemInstance.isEquipable() || player.getActiveWeaponItem() != null && itemInstance.getTemplate().getType2() == 0) continue;
            player.getInventory().equipItem(itemInstance);
        }
        for (Triple triple : playerTemplate.getInitialItems()) {
            ItemTemplate l = (ItemTemplate)triple.getLeft();
            long l2 = (Long)triple.getMiddle();
            n = (Integer)triple.getRight();
            for (ItemInstance itemInstance2 : ItemFunctions.addItem((Playable)player, l, l2, false)) {
                if (n <= 0) continue;
                itemInstance2.setEnchantLevel(n);
            }
        }
        for (Triple triple : playerTemplate.getInitialEquipItems()) {
            ItemTemplate itemInstance = (ItemTemplate)triple.getLeft();
            long l = (Long)triple.getMiddle();
            n = (Integer)triple.getRight();
            for (ItemInstance itemInstance2 : ItemFunctions.addItem((Playable)player, itemInstance, l, false)) {
                if (n > 0) {
                    itemInstance2.setEnchantLevel(n);
                }
                if (!itemInstance2.isEquipable()) continue;
                player.getInventory().equipItem(itemInstance2);
            }
        }
        if (!player.isMageClass()) {
            for (var4_5 = 0; var4_5 < Config.STARTING_ITEMS_FIGHTER.length; var4_5 += 2) {
                int skillLearn = Config.STARTING_ITEMS_FIGHTER[var4_5];
                long l = Config.STARTING_ITEMS_FIGHTER[var4_5 + 1];
                ItemFunctions.addItem((Playable)player, skillLearn, l, false);
            }
        } else {
            for (var4_5 = 0; var4_5 < Config.STARTING_ITEMS_MAGE.length; var4_5 += 2) {
                int shortCut = Config.STARTING_ITEMS_MAGE[var4_5];
                long l = Config.STARTING_ITEMS_MAGE[var4_5 + 1];
                ItemFunctions.addItem((Playable)player, shortCut, l, false);
            }
        }
        for (SkillLearn shortCut : SkillAcquireHolder.getInstance().getAvailableSkills(player, AcquireType.NORMAL)) {
            player.addSkill(SkillTable.getInstance().getInfo(shortCut.getId(), shortCut.getLevel()), true);
        }
        block12: for (ShortCut shortCut : ShortCutsTable.getInstance().getShortCuts(player.getClassId())) {
            switch (shortCut.getType()) {
                case 2: {
                    int l = player.getSkillLevel(shortCut.getId());
                    if (l <= 0) continue block12;
                    ShortCut shortCut2 = new ShortCut(shortCut.getSlot(), shortCut.getPage(), shortCut.getType(), shortCut.getId(), l, shortCut.getCharacterType());
                    player.registerShortCut(shortCut2);
                    break;
                }
                case 1: {
                    ItemInstance itemInstance = player.getInventory().getItemByItemId(shortCut.getId());
                    if (itemInstance == null) continue block12;
                    ShortCut shortCut2 = new ShortCut(shortCut.getSlot(), shortCut.getPage(), shortCut.getType(), itemInstance.getObjectId(), shortCut.getLevel(), shortCut.getCharacterType());
                    player.registerShortCut(shortCut2);
                    break;
                }
                default: {
                    player.registerShortCut(shortCut);
                }
            }
        }
        if (MacroDataParser.getInstance().isEnabled()) {
            for (Macro macro : MacroDataHolder.getInstance().getMacros()) {
                player.registerMacro(macro);
                player.sendPacket((IStaticPacket)new SendMacroList(MacroUpdateType.ADD, 1, macro));
            }
            for (ShortCut shortCut : MacroDataHolder.getInstance().getShortcuts()) {
                player.sendPacket((IStaticPacket)new ShortCutRegister(player, shortCut));
                player.registerShortCut(shortCut);
            }
        }
        CharacterCreate.startInitialQuests(player);
        player.setCurrentHpMp(player.getMaxHp(), player.getMaxMp());
        player.setCurrentCp(0.0);
        player.setOnlineStatus(false);
        player.store(false);
        player.getInventory().store();
        player.deleteMe();
        gameClient.setCharSelection(CharacterSelectionInfo.loadCharacterSelectInfo(gameClient.getLogin()));
    }

    public static void startInitialQuests(Player player) {
        for (int i = 0; i < Config.ALT_INITIAL_QUESTS.length; ++i) {
            int n = Config.ALT_INITIAL_QUESTS[i];
            Quest quest = QuestManager.getQuest(n);
            if (quest == null) continue;
            quest.newQuestState(player, 1);
        }
    }
}
