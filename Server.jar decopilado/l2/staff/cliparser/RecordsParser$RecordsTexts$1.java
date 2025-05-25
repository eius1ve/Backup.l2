/*
 * Decompiled with CFR 0.152.
 */
package l2.staff.cliparser;

import java.util.Iterator;

class RecordsParser.RecordsTexts.1
implements Iterator<String> {
    int beginPos = 0;
    int endPos = 0;

    RecordsParser.RecordsTexts.1() {
    }

    @Override
    public boolean hasNext() {
        this.beginPos = RecordsTexts.this.gI.indexOf(RecordsTexts.this.this$0.gG, this.endPos);
        if (this.beginPos < 0) {
            return false;
        }
        this.endPos = RecordsTexts.this.gI.indexOf(RecordsTexts.this.this$0.gH, this.beginPos);
        return this.beginPos + RecordsTexts.this.this$0.gG.length() <= this.endPos;
    }

    @Override
    public String next() {
        if (this.endPos > 0 && this.beginPos < this.endPos) {
            return RecordsTexts.this.gI.substring(this.beginPos + RecordsTexts.this.this$0.gG.length(), this.endPos);
        }
        return null;
    }
}
