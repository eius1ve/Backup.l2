/*
 * Decompiled with CFR 0.152.
 */
package services.community;

import java.util.Date;

private static class PrivateMail.MailData {
    public String author;
    public String title;
    public String postDate;
    public int messageId;

    public PrivateMail.MailData(String string, String string2, int n, int n2) {
        this.author = string;
        this.title = string2;
        this.postDate = String.format(String.format("%1$te-%1$tm-%1$tY", new Date((long)n * 1000L)), new Object[0]);
        this.messageId = n2;
    }
}
