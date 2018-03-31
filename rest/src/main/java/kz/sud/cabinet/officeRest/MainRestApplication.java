package kz.sud.cabinet.officeRest;

import javax.net.ssl.*;
import javax.ws.rs.core.Application;
import java.security.cert.X509Certificate;
import java.util.HashSet;
import java.util.Set;

public class MainRestApplication extends Application {
    static {
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        } };

        SSLContext sc;
        try {
            sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        }
        catch(Throwable t) {
            t.printStackTrace();
        }
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        });
    }
    private Set<Object> singletons = new HashSet<Object>();
    public MainRestApplication() {
        singletons.add(new Diplom());
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }

}
