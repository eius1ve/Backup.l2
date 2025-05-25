/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.model.Player
 *  l2.gameserver.model.entity.residence.Residence
 *  l2.gameserver.model.pledge.Clan
 *  l2.gameserver.templates.npc.NpcTemplate
 */
package npc.model.residences.clanhall;

import l2.gameserver.model.Player;
import l2.gameserver.model.entity.residence.Residence;
import l2.gameserver.model.pledge.Clan;
import l2.gameserver.templates.npc.NpcTemplate;
import npc.model.residences.clanhall.ManagerInstance;

public class AuctionedManagerInstance
extends ManagerInstance {
    public AuctionedManagerInstance(int n, NpcTemplate npcTemplate) {
        super(n, npcTemplate);
    }

    @Override
    protected void setDialogs() {
        this._mainDialog = this.getTemplate().getAIParams().getString((Object)"main_dialog", "residence2/clanhall/black001.htm");
        this._failDialog = this.getTemplate().getAIParams().getString((Object)"fail_dialog", "residence2/clanhall/black002.htm");
    }

    @Override
    protected int getCond(Player player) {
        Residence residence = this.getResidence();
        Clan clan = residence.getOwner();
        if (clan != null && player.getClan() == clan) {
            return 2;
        }
        return 0;
    }

    protected boolean canInteractWithKarmaPlayer() {
        return true;
    }

    protected boolean canInteractWithCursedWeaponPlayer() {
        return true;
    }
}
