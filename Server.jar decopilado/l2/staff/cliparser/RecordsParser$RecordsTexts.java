/*
 * Decompiled with CFR 0.152.
 */
package l2.staff.cliparser;

import java.util.Iterator;

private class RecordsParser.RecordsTexts
implements Iterable<String> {
    private final String gI;

    private RecordsParser.RecordsTexts(String string) {
        this.gI = string;
    }

    @Override
    public Iterator<String> iterator() {
        return new Iterator<String>(){
            int beginPos = 0;
            int endPos = 0;

            @Override
            public boolean hasNext() {
                this.beginPos = RecordsTexts.this.gI.indexOf(RecordsParser.this.gG, this.endPos);
                if (this.beginPos < 0) {
                    return false;
                }
                this.endPos = RecordsTexts.this.gI.indexOf(RecordsParser.this.gH, this.beginPos);
                return this.beginPos + RecordsParser.this.gG.length() <= this.endPos;
            }

            @Override
            public String next() {
                if (this.endPos > 0 && this.beginPos < this.endPos) {
                    return RecordsTexts.this.gI.substring(this.beginPos + RecordsParser.this.gG.length(), this.endPos);
                }
                return null;
            }
        };
    }
}
