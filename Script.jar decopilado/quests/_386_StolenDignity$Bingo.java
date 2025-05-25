/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.commons.util.Rnd
 *  l2.gameserver.model.quest.QuestState
 */
package quests;

import l2.commons.util.Rnd;
import l2.gameserver.model.quest.QuestState;
import quests.Bingo;

public static class _386_StolenDignity.Bingo
extends Bingo {
    protected static final String msg_begin = "I've arranged the numbers 1 through 9 on the grid. Don't peek!<br>Let me have the 100 Infernium Ores. Too many players try to run away without paying when it becomes obvious that they're losing...<br>OK, select six numbers between 1 and 9. Choose the %choicenum% number.";
    protected static final String msg_again = "You've already chosen that number. Make your %choicenum% choice again.";
    protected static final String msg_0lines = "Wow! How unlucky can you get? Your choices are highlighted in red below. As you can see, your choices didn't make a single line! Losing this badly is actually quite rare!<br>You look so sad, I feel bad for you... Wait here... <br>.<br>.<br>.<br>Take this... I hope it will bring you better luck in the future.";
    protected static final String msg_3lines = "Excellent! As you can see, you've formed three lines! Congratulations! As promised, I'll give you some unclaimed merchandise from the warehouse. Wait here...<br>.<br>.<br>.<br>Whew, it's dusty! OK, here you go. Do you like it?";
    protected static final String msg_lose = "Oh, too bad. Your choices didn't form three lines. You should try again... Your choices are highlighted in red.";
    private static final String hm = "<a action=\"bypass -h Quest _386_StolenDignity choice-%n%\">%n%</a>&nbsp;&nbsp;&nbsp;&nbsp;  ";
    private final QuestState c;

    public _386_StolenDignity.Bingo(QuestState questState) {
        super(hm);
        this.c = questState;
    }

    @Override
    protected String getFinal() {
        String string = super.getFinal();
        if (this.lines == 3) {
            this.a(M);
        } else if (this.lines == 0) {
            this.a(N);
        }
        cj.remove(this.c.getPlayer().getObjectId());
        return string;
    }

    private void a(int[][] nArray) {
        int[] nArray2 = nArray[Rnd.get((int)nArray.length)];
        this.c.giveItems(nArray2[0], (long)nArray2[1], false);
    }
}
