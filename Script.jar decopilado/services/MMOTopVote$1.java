/*
 * Decompiled with CFR 0.152.
 */
package services;

import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;

static class MMOTopVote.1
implements X509TrustManager {
    MMOTopVote.1() {
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }

    @Override
    public void checkClientTrusted(X509Certificate[] x509CertificateArray, String string) {
    }

    @Override
    public void checkServerTrusted(X509Certificate[] x509CertificateArray, String string) {
    }
}
