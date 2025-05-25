/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.lang3.tuple.Pair
 */
package l2.staff.cliparser;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

public class RecordsParser {
    private final String gG;
    private final String gH;

    public RecordsParser(String string, String string2) {
        this.gG = string;
        this.gH = string2;
    }

    public List<Map<String, String>> parse(String string) {
        return this.c(string);
    }

    private Pair<String, String> a(String string) {
        int n = string.indexOf(61);
        if (n < 0) {
            return Pair.of((Object)string, null);
        }
        return Pair.of((Object)StringUtils.trimToEmpty((String)string.substring(0, n)), (Object)StringUtils.trimToEmpty((String)string.substring(n + 1)));
    }

    private Map<String, String> a(String string) {
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<String, String>();
        StringTokenizer stringTokenizer = new StringTokenizer(string, "\t");
        while (stringTokenizer.hasMoreTokens()) {
            Pair<String, String> pair = this.a(stringTokenizer.nextToken());
            linkedHashMap.put((String)pair.getKey(), (String)pair.getValue());
        }
        return linkedHashMap;
    }

    private List<Map<String, String>> c(String string) {
        return StreamSupport.stream(new RecordsTexts(string).spliterator(), false).map(this::a).collect(Collectors.toList());
    }

    private class RecordsTexts
    implements Iterable<String> {
        private final String gI;

        private RecordsTexts(String string) {
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
}
