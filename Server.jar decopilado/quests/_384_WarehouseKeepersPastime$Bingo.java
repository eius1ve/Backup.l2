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

public static class _384_WarehouseKeepersPastime.Bingo
extends Bingo {
    protected static final String msg_begin = "I've arranged 9 numbers on the panel. Don't peek! Ha ha ha!<br>Now give me your 10 medals. Some players run away when they realize that they don't stand a good chance of winning. Therefore, I prefer to hold the medals before the game starts. If you quit during game play, you'll forfeit your bet. Is that satisfactory?<br>Now, select your %choicenum% number.";
    protected static final String msg_0lines = "You are spectacularly unlucky! The red-colored numbers on the panel below are the ones you chose. As you can see, they didn't create even a single line. Did you know that it is harder not to create a single line than creating all 3 lines?<br>Usually, I don't give a reward when you don't create a single line, but since I'm feeling sorry for you, I'll be generous this time. Wait here.<br>.<br>.<br>.<br><br><br>Here, take this. I hope it will bring you better luck in the future.";
    protected static final String msg_3lines = "You've created 3 lines! The red colored numbers on the bingo panel below are the numbers you chose. Congratulations! As I promised, I'll give you an unclaimed item from my warehouse. Wait here.<br>.<br>.<br>.<br><br><br>Puff puff... it's very dusty. Here it is. Do you like it?";
    private static final String hl = "<a action=\"bypass -h Quest _384_WarehouseKeepersPastime choice-%n%\">%n%</a>&nbsp;&nbsp;&nbsp;&nbsp;  ";
    private final boolean hw;
    private final QuestState b;

    public _384_WarehouseKeepersPastime.Bingo(boolean bl, QuestState questState) {
        super(hl);
        this.hw = bl;
        this.b = questState;
    }

    @Override
    protected String getFinal() {
        String string = super.getFinal();
        if (this.lines == 3) {
            this.a(this.hw ? J : I);
        } else if (this.lines == 0) {
            this.a(this.hw ? L : K);
        }
        ch.remove(this.b.getPlayer().getObjectId());
        return string;
    }

    private void a(int[][] nArray) {
        int n = Rnd.get((int)100);
        for (int[] nArray2 : nArray) {
            if (n >= nArray2[0]) continue;
            this.b.giveItems(nArray2[1], (long)nArray2[2], true);
            return;
        }
    }
}
