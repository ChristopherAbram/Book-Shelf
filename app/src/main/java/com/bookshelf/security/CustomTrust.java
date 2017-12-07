package com.bookshelf.security;

/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.Collection;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.CertificatePinner;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;

public final class CustomTrust {
    private OkHttpClient client;

    protected static String csrfToken = "";
    protected static String cookies = "";

    public CustomTrust(String csrfToken, String cookies) {

        this.csrfToken = csrfToken;
        this.cookies = cookies;
    }

    public OkHttpClient create(){
        X509TrustManager trustManager;
        SSLSocketFactory sslSocketFactory;
        try {
            trustManager = trustManagerForCertificates(trustedCertificatesInputStream());
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[] { trustManager }, null);
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory, trustManager)
                .addInterceptor(logging)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        String value = "";
                        if(cookies != null)
                            if(!cookies.equals(""))
                                value = cookies;

                        Request request = chain.request().newBuilder().addHeader("cookie", value).build();
                        return chain.proceed(request);
                    }
                })
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        HttpUrl originalHttpUrl = original.url();

                        HttpUrl.Builder builder = originalHttpUrl.newBuilder();
                        if(!csrfToken.equals("")){
                            builder.addQueryParameter("csrf", csrfToken);
                        }
                        HttpUrl url = builder.build();

                        // Request customization: add request headers
                        Request.Builder requestBuilder = original.newBuilder()
                                .url(url);

                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                })
                /*.hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })*/;

        client = builder.build();
        return client;
    }

    /**
     * Returns an input stream containing one or more certificate PEM files. This implementation just
     * embeds the PEM files in Java strings; most applications will instead read this from a resource
     * file that gets bundled with the application.
     */
    private InputStream trustedCertificatesInputStream() {

        String bookshelfRsaCertificationAuthority = "" +
                "-----BEGIN CERTIFICATE-----\n" +
                "MIIGvDCCBKSgAwIBAgIJAKqFMoZfKjKkMA0GCSqGSIb3DQEBCwUAMIGVMQswCQYD\n" +
                "VQQGEwJQTDETMBEGA1UECAwKTWFsb3BvbHNrYTEPMA0GA1UEBwwGS3Jha293MR4w\n" +
                "HAYDVQQKDBV3d3cua3J6eXN6dG9mYWJyYW0ucGwxGDAWBgNVBAMMD0tyenlzenRv\n" +
                "ZiBBYnJhbTEmMCQGCSqGSIb3DQEJARYXaW5ib3hAa3J6eXN6dG9mYWJyYW0ucGww\n" +
                "HhcNMTcxMTI0MTkyMDA5WhcNMTgxMTI0MTkyMDA5WjCBlTELMAkGA1UEBhMCUEwx\n" +
                "EzARBgNVBAgMCk1hbG9wb2xza2ExDzANBgNVBAcMBktyYWtvdzEeMBwGA1UECgwV\n" +
                "d3d3LmtyenlzenRvZmFicmFtLnBsMRgwFgYDVQQDDA9Lcnp5c3p0b2YgQWJyYW0x\n" +
                "JjAkBgkqhkiG9w0BCQEWF2luYm94QGtyenlzenRvZmFicmFtLnBsMIICIjANBgkq\n" +
                "hkiG9w0BAQEFAAOCAg8AMIICCgKCAgEA+1WjwQrFLlxRnaFGQTiIsBDb3WLoGAZk\n" +
                "B1X1z/f58Opwk4jOL/i8eA+yQccChpNF4K4yfpsl0dgdTGgMFqmwmqRTyMFo5URG\n" +
                "CJPyMsGcbPygTBza4ksqu06g0Vdmo54Rq+4lEUt5q23TT0qbm7OaPFpdgvKgFjHf\n" +
                "Yp8cYtQmfhvViL5mM9oVSk3t9bFYKTJ8v5Tm6qpttZUGUrs04bnBwamZW1owweyW\n" +
                "UzNh5niBEKjij8jRsnFAZHvP5gKhQoHiMe1zrloL3e1go1/9Ofm4aq+389hmO7AO\n" +
                "s4c0Z1+uldfZTVYKAKTPYnkiJknCNAEKCIfe7BkwfnlfAUY3iqiJfZiIKJx/xQ8+\n" +
                "5tYvRDLMocXZvs3Gmf8HeGMstpnw62KBLbfozLSAD/EQs1S36gfCCThf34s4PiC+\n" +
                "8KLRXn5aW96RRicFeRul7ThN7cyF4Hv9/RYtLM3g2JFOgxM0hSM0MzFQlSAjIP9s\n" +
                "9Rth0RkU4OLZEspLf+OiS4c4JEL3Wgj0r652MHQW7vtK7Qvymw19Sc2w/KUj0OFI\n" +
                "jJPxCdzvwsMR0wfl2O51bnQiEk5O9Zq6YtRvtVCTbiRDfX5IQvc7wZKY+OTAL9Q5\n" +
                "IbKwMZtfx17Ma6AQOxSICTsH21OghKkwbpTbqpBabE542B150eWkNRzAvDwOQ2dS\n" +
                "dUTDMyImws0CAwEAAaOCAQswggEHMB0GA1UdDgQWBBSnGVlWzTfhc89ATgLTrnfM\n" +
                "Ir6Q7TAfBgNVHSMEGDAWgBSnGVlWzTfhc89ATgLTrnfMIr6Q7TAJBgNVHRMEAjAA\n" +
                "MAsGA1UdDwQEAwIFoDB/BgNVHREEeDB2ghFrcnp5c3p0b2ZhYnJhbS5wbIIVd3d3\n" +
                "LmtyenlzenRvZmFicmFtLnBsghZtYWlsLmtyenlzenRvZmFicmFtLnBsghVmdHAu\n" +
                "a3J6eXN6dG9mYWJyYW0ucGyCG2Jvb2tzaGVsZi5rcnp5c3p0b2ZhYnJhbS5wbDAs\n" +
                "BglghkgBhvhCAQ0EHxYdT3BlblNTTCBHZW5lcmF0ZWQgQ2VydGlmaWNhdGUwDQYJ\n" +
                "KoZIhvcNAQELBQADggIBAKFsQiZ0sI6/+I4FLej+5Z/0P5r6Pt1WPGhpSXi7ycz2\n" +
                "ZwbhcH++UnYTJm0hCmPeQ0QwnhJhzFncj0kRRORnQi9UnOJxjmn7xiN+g7NEnozI\n" +
                "3qv2W4sp5akx9muf6BW1Ca9e35mgE1gK4nGEjoxDZPmkQzmQA051SPTI/lNVqE6t\n" +
                "LQWu0lB8lWvwl52wFdtt4agnY7ejBH0zFzAY56VU9kE3Tf6zHq6oD8zVVAFk+8fe\n" +
                "pfckYpNoXDfPcq3kBbnaArtOTUzS736NE2O4hzyfBStMa5Kal1kYx0B/WZHJmBcm\n" +
                "xTY3nbY+6Urg4ccz1C3HBmNvTW/0yNvhf8/cRTnCepIz+7dddfxJ5bbkwO8BKkGr\n" +
                "788V/sV1Ymrsj70LL4LdfLtwYruMVbK+SHPPvjJoUO+VDk8csklVqpv4Kho3w5bX\n" +
                "JHWeFuLHbhAZKU9FZAWu93ubB9NLwR4uLask1MQT9VWxddVTEJU93V3aY9ThO9va\n" +
                "sgbT83SV3+u+CfROI7kABoyRU6n1VYNQ4NIJn+ph9OJNL2D+HLxSzWS74JsQGQbf\n" +
                "K/RX+IiPwTCam/3pLGYCWhREiN79KdkuJr+Nf6IHdFYBfgJQGTXUsAxBX71y3adR\n" +
                "sFJZFADgrXf/CM4tv77/7MjSTuTHrKTrgD4l77VybIECbUQC/Ufaxh3HzvgVKc+9\n" +
                "-----END CERTIFICATE-----";

        return new Buffer()
                .writeUtf8(bookshelfRsaCertificationAuthority)
                .inputStream();
    }

    /**
     * Returns a trust manager that trusts {@code certificates} and none other. HTTPS services whose
     * certificates have not been signed by these certificates will fail with a {@code
     * SSLHandshakeException}.
     *
     * <p>This can be used to replace the host platform's built-in trusted certificates with a custom
     * set. This is useful in development where certificate authority-trusted certificates aren't
     * available. Or in production, to avoid reliance on third-party certificate authorities.
     *
     * <p>See also {@link CertificatePinner}, which can limit trusted certificates while still using
     * the host platform's built-in trust store.
     *
     * <h3>Warning: Customizing Trusted Certificates is Dangerous!</h3>
     *
     * <p>Relying on your own trusted certificates limits your server team's ability to update their
     * TLS certificates. By installing a specific set of trusted certificates, you take on additional
     * operational complexity and limit your ability to migrate between certificate authorities. Do
     * not use custom trusted certificates in production without the blessing of your server's TLS
     * administrator.
     */
    private X509TrustManager trustManagerForCertificates(InputStream in)
            throws GeneralSecurityException {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Collection<? extends Certificate> certificates = certificateFactory.generateCertificates(in);
        if (certificates.isEmpty()) {
            throw new IllegalArgumentException("expected non-empty set of trusted certificates");
        }

        // Put the certificates a key store.
        char[] password = "password".toCharArray(); // Any password will work.
        KeyStore keyStore = newEmptyKeyStore(password);
        int index = 0;
        for (Certificate certificate : certificates) {
            String certificateAlias = Integer.toString(index++);
            keyStore.setCertificateEntry(certificateAlias, certificate);
        }

        // Use it to build an X509 trust manager.
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(
                KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, password);
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:"
                    + Arrays.toString(trustManagers));
        }
        return (X509TrustManager) trustManagers[0];
    }

    private KeyStore newEmptyKeyStore(char[] password) throws GeneralSecurityException {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            InputStream in = null; // By convention, 'null' creates an empty key store.
            keyStore.load(in, password);
            return keyStore;
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}