/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  l2.gameserver.scripts.ScriptFile
 */
package quests;

import l2.gameserver.scripts.ScriptFile;
import quests.SagasSuperclass;

public class _081_SagaOfTheGhostHunter
extends SagasSuperclass
implements ScriptFile {
    public void onLoad() {
    }

    public void onReload() {
    }

    public void onShutdown() {
    }

    public _081_SagaOfTheGhostHunter() {
        super(1);
        this.NPC = new int[]{31603, 31624, 31286, 31615, 31617, 31646, 31649, 31653, 31654, 31655, 31656, 31616};
        this.Items = new int[]{7080, 7518, 7081, 7496, 7279, 7310, 7341, 7372, 7403, 7434, 7104, 0};
        this.Mob = new int[]{27301, 27230, 27304};
        this.classid = 108;
        this.prevclass = 36;
        this.X = new int[]{162920, 47391, 47429};
        this.Y = new int[]{-76504, -56929, -56923};
        this.Z = new int[]{-3120, -2370, -2383};
        this.Text = new String[]{"PLAYERNAME! Pursued to here! However, I jumped out of the Banshouren boundaries! You look at the giant as the sign of power!", "... Oh ... good! So it was ... let's begin!", "I do not have the patience ..! I have been a giant force ...! Cough chatter ah ah ah!", "Paying homage to those who disrupt the orderly will be PLAYERNAME's death!", "Now, my soul freed from the shackles of the millennium, Halixia, to the back side I come ...", "Why do you interfere others' battles?", "This is a waste of time.. Say goodbye...!", "...That is the enemy", "...Goodness! PLAYERNAME you are still looking?", "PLAYERNAME ... Not just to whom the victory. Only personnel involved in the fighting are eligible to share in the victory.", "Your sword is not an ornament. Don't you think, PLAYERNAME?", "Goodness! I no longer sense a battle there now.", "let...", "Only engaged in the battle to bar their choice. Perhaps you should regret.", "The human nation was foolish to try and fight a giant's strength.", "Must...Retreat... Too...Strong.", "PLAYERNAME. Defeat...by...retaining...and...Mo...Hacker", "....! Fight...Defeat...It...Fight...Defeat...It..."};
        this.registerNPCs();
    }
}
