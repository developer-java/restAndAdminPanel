package kz.sud.cabinet.util;

import kz.gov.pki.kalkan.jce.provider.KalkanProvider;
import kz.gov.pki.kalkan.xmldsig.KncaXS;

import java.security.Provider;
import java.security.Security;

public class CryptoInitializer {
    private static boolean initialized = false;

    public static void initCrypto() {
        if(!initialized) {
            Provider provider = new KalkanProvider();
            Security.addProvider(provider);
            KncaXS.loadXMLSecurity();
            initialized = true;
        }
    }
}
