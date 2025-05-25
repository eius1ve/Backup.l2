/*
 * Decompiled with CFR 0.152.
 */
package l2.gameserver.model.entity.oly;

import java.util.Collection;
import l2.gameserver.model.entity.oly.CompetitionController;
import l2.gameserver.model.entity.oly.OlyController;

public class HeroController.HeroRecord {
    public int char_id;
    public int class_id;
    public int count;
    public boolean active;
    public boolean played;
    public String name;
    public String message;
    public String clan_name;
    public String ally_name;
    public int clan_crest;
    public int ally_crest;
    public Collection<CompetitionController.CompetitionResults> competitions;

    private HeroController.HeroRecord(int n, String string, int n2, int n3, boolean bl, boolean bl2, String string2) {
        this.char_id = n;
        this.name = string;
        this.count = n3;
        this.class_id = n2;
        this.active = bl;
        this.played = bl2;
        this.message = string2;
    }

    public Collection<CompetitionController.CompetitionResults> getCompetitions() {
        if (this.competitions == null) {
            this.competitions = CompetitionController.getInstance().getCompetitionResults(this.char_id, OlyController.getInstance().getCurrentSeason() - 1);
        }
        return this.competitions;
    }
}
