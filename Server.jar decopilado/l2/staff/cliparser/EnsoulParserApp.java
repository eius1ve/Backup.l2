/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.lang3.tuple.Pair
 */
package l2.staff.cliparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import l2.gameserver.templates.item.support.Grade;
import l2.staff.cliparser.RecordsParser;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

public class EnsoulParserApp {
    private static final File g = new File("E:\\Games\\Lineage II - Fafurion\\ensoul_fee_client.txt");
    private static final File h = new File("E:\\Games\\Lineage II - Fafurion\\ensoul_option_client.txt");
    private static final File i = new File("E:\\Games\\Lineage II - Fafurion\\ensoul_stone_client.txt");

    private static final String a(File file, Charset charset) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        CharBuffer charBuffer = CharBuffer.allocate(16);
        try (BufferedReader bufferedReader = Files.newBufferedReader(file.toPath(), charset);){
            int n = bufferedReader.read(charBuffer);
            while (n > -1) {
                charBuffer.flip();
                stringBuilder.append(charBuffer);
                charBuffer.clear();
                n = bufferedReader.read(charBuffer);
            }
        }
        return stringBuilder.toString();
    }

    private static List<Pair<Integer, Integer>> b(String string) {
        ArrayList<Pair<Integer, Integer>> arrayList = new ArrayList<Pair<Integer, Integer>>();
        Matcher matcher = Pattern.compile("\\{(\\d+);(\\d+)\\}").matcher(string);
        while (matcher.find()) {
            arrayList.add((Pair<Integer, Integer>)Pair.of((Object)Integer.parseInt(matcher.group(1)), (Object)Integer.parseInt(matcher.group(2))));
        }
        return arrayList;
    }

    private static void a(Writer writer, Map<String, String> map) throws Exception {
        Grade grade = null;
        int n = Integer.parseInt(map.get("crystal_type"));
        for (Grade object2 : Grade.values()) {
            if (object2.gradeOrd() != n) continue;
            grade = object2;
            break;
        }
        if (grade == null) {
            throw new RuntimeException(map.toString());
        }
        List<Pair<Integer, Integer>> list = EnsoulParserApp.b(map.get("ensoul_fee_normal"));
        List<Pair<Integer, Integer>> list2 = EnsoulParserApp.b(map.get("ensoul_fee_bm"));
        List<Pair<Integer, Integer>> list3 = EnsoulParserApp.b(map.get("ensoul_refee_normal"));
        List<Pair<Integer, Integer>> list4 = EnsoulParserApp.b(map.get("ensoul_refee_bm"));
        List<Pair<Integer, Integer>> list5 = EnsoulParserApp.b(map.get("ensoul_extraction_normal"));
        List<Pair<Integer, Integer>> list6 = EnsoulParserApp.b(map.get("ensoul_extraction_bm"));
        writer.write("\t<ensoul_fee grade=\"" + grade.getId() + "\">\n");
        if (!list.isEmpty()) {
            writer.write("\t\t<insert_fee type=\"1\">\n");
            Iterator<Object> iterator = list.iterator();
            while (iterator.hasNext()) {
                Pair pair = (Pair)iterator.next();
                writer.write("\t\t\t<item id=\"" + pair.getKey() + "\" amount=\"" + pair.getValue() + "\"/>\n");
            }
            writer.write("\t\t</insert_fee>\n");
        }
        if (!list2.isEmpty()) {
            writer.write("\t\t<insert_fee type=\"2\">\n");
            for (Pair pair : list2) {
                writer.write("\t\t\t<item id=\"" + pair.getKey() + "\" amount=\"" + pair.getValue() + "\"/>\n");
            }
            writer.write("\t\t</insert_fee>\n");
        }
        if (!list3.isEmpty()) {
            writer.write("\t\t<replace_fee type=\"1\">\n");
            for (Pair pair : list3) {
                writer.write("\t\t\t<item id=\"" + pair.getKey() + "\" amount=\"" + pair.getValue() + "\"/>\n");
            }
            writer.write("\t\t</replace_fee>\n");
        }
        if (!list4.isEmpty()) {
            writer.write("\t\t<replace_fee type=\"2\">\n");
            for (Pair pair : list4) {
                writer.write("\t\t\t<item id=\"" + pair.getKey() + "\" amount=\"" + pair.getValue() + "\"/>\n");
            }
            writer.write("\t\t</replace_fee>\n");
        }
        if (!list5.isEmpty()) {
            writer.write("\t\t<remove_fee type=\"1\">\n");
            for (Pair pair : list5) {
                writer.write("\t\t\t<item id=\"" + pair.getKey() + "\" amount=\"" + pair.getValue() + "\"/>\n");
            }
            writer.write("\t\t</remove_fee>\n");
        }
        if (!list6.isEmpty()) {
            writer.write("\t\t<remove_fee type=\"2\">\n");
            for (Pair pair : list6) {
                writer.write("\t\t\t<item id=\"" + pair.getKey() + "\" amount=\"" + pair.getValue() + "\"/>\n");
            }
            writer.write("\t\t</remove_fee>\n");
        }
        writer.write("\t</ensoul_fee>\n");
        writer.flush();
    }

    private static String i(String string) {
        string = StringUtils.replace((String)string, (String)"'", (String)"");
        string = string.toLowerCase();
        string = StringUtils.replace((String)string, (String)"'", (String)"");
        string = StringUtils.replace((String)string, (String)"[", (String)"");
        string = StringUtils.replace((String)string, (String)"]", (String)"");
        string = string.replaceAll("[^\\w\\d]+", "_");
        return string;
    }

    private static void a(Writer writer, List<Map<String, String>> list, List<Map<String, String>> list2) throws Exception {
        for (Map<String, String> map : list) {
            Map map3 = list2.stream().filter(map2 -> StringUtils.equals((CharSequence)((CharSequence)map.get("extraction_itemid")), (CharSequence)((CharSequence)map2.get("id")))).findFirst().get();
            if (map3 == null) {
                System.currentTimeMillis();
            }
            int n = Integer.parseInt(map.get("id"));
            String string = map.get("name");
            String string2 = map.get("desc");
            string2 = string2.substring(1, string2.length() - 1);
            String string3 = map.get("icon");
            string3 = string3.substring(1, string3.length() - 1);
            int n2 = Integer.parseInt((String)map3.get("id"));
            int n3 = Integer.parseInt((String)map3.get("slot_type"));
            int n4 = Integer.parseInt(map.get("option_type"));
            writer.write("\t<ensoul_option ");
            writer.write("id=\"" + n + "\" ");
            writer.write("stoneId=\"" + n2 + "\" ");
            writer.write("skillId=\"0\" ");
            writer.write("skillLvl=\"0\" ");
            writer.write("optionType=\"" + n4 + "\" ");
            writer.write("name=\"" + EnsoulParserApp.i(string) + "\" ");
            writer.write("slotType=\"" + n3 + "\" ");
            writer.write("icon=\"" + string3 + "\" ");
            writer.write("/> <!-- " + string2 + " -->\n");
            System.currentTimeMillis();
        }
    }

    public static void main(String ... stringArray) throws Exception {
        List<Map<String, String>> list = new RecordsParser("ensoul_fee_begin", "ensoul_fee_end").parse(EnsoulParserApp.a(g, StandardCharsets.UTF_8));
        List<Map<String, String>> list2 = new RecordsParser("ensoul_option_begin", "ensoul_option_end").parse(EnsoulParserApp.a(h, StandardCharsets.UTF_8));
        List<Map<String, String>> list3 = new RecordsParser("ensoul_stone_begin", "ensoul_stone_end").parse(EnsoulParserApp.a(i, StandardCharsets.UTF_8));
        try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(System.out);){
            EnsoulParserApp.a(outputStreamWriter, list2, list3);
        }
        System.currentTimeMillis();
    }
}
