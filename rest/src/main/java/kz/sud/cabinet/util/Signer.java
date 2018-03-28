package kz.sud.cabinet.util;

import kz.gov.pki.kalkan.asn1.pkcs.PKCSObjectIdentifiers;
import kz.gov.pki.kalkan.jce.provider.KalkanProvider;
import org.apache.xml.security.encryption.XMLCipherParameters;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.transforms.Transforms;
import org.apache.xml.security.utils.Constants;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.URL;
import java.security.Key;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

public class Signer {
    static {
        CryptoInitializer.initCrypto();
    }

    private static String signXML(Document doc, Certificate cert, Key privateKey, boolean omitXmlDeclaration) throws Exception {
        Writer os = null;
        try {
            String sigAlgOid = ((X509Certificate)cert).getSigAlgOID();
            String signMethod;
            String digestMethod;
            if (sigAlgOid.equals(PKCSObjectIdentifiers.sha1WithRSAEncryption.getId())) {
                signMethod = Constants.MoreAlgorithmsSpecNS + "rsa-sha1";
                digestMethod = Constants.MoreAlgorithmsSpecNS + "sha1";
            } else if (sigAlgOid.equals(PKCSObjectIdentifiers.sha256WithRSAEncryption.getId())) {
                signMethod = Constants.MoreAlgorithmsSpecNS + "rsa-sha256";
                digestMethod = XMLCipherParameters.SHA256;
            } else {
                signMethod = Constants.MoreAlgorithmsSpecNS + "gost34310-gost34311";
                digestMethod = Constants.MoreAlgorithmsSpecNS + "gost34311";
            }

            XMLSignature sig = new XMLSignature(doc, "", signMethod);
            String res = "";
            if (doc.getFirstChild() != null) {
                doc.getFirstChild().appendChild(sig.getElement());
                Transforms transforms = new Transforms(doc);
                transforms.addTransform("http://www.w3.org/2000/09/xmldsig#enveloped-signature");
                transforms.addTransform("http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments");
                sig.addDocument("", transforms, digestMethod);
                sig.addKeyInfo((X509Certificate) cert);
                sig.sign(privateKey);
                os = new CharArrayWriter();
                TransformerFactory tf = TransformerFactory.newInstance();
                Transformer trans = tf.newTransformer();
                if(omitXmlDeclaration) {
                    trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
                }
                trans.transform(new DOMSource(doc), new StreamResult(os));
                os.flush();
                res = os.toString();
                os.close();
            }
            return res;
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String signXml(String xml, Certificate cert, Key key) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new InputSource(new StringReader(xml)));
        return signXML(doc, cert, key, false);
    }

    public static String signXml(String xml, Certificate cert, Key key, boolean omitXmlDeclaration) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new InputSource(new StringReader(xml)));
        return signXML(doc, cert, key, omitXmlDeclaration);
    }

    public static String signXml(String xml, String keyPath, String code) throws Exception {
        File file = new File(keyPath);
//        System.out.println("signXml path = " + file.getAbsolutePath());
        FileInputStream fis = new FileInputStream(file);
        KeyStore ks = KeyStore.getInstance("PKCS12", KalkanProvider.PROVIDER_NAME);
        ks.load(fis, code.toCharArray());
        fis.close();
        String alias = null;
        Enumeration en = ks.aliases();
        while(en.hasMoreElements()) {
            alias = (String)en.nextElement();
        }
        return signXml(xml, ks.getCertificate(alias), ks.getKey(alias, code.toCharArray()));
    }

    public static String signXml(String xml, String keyPath, String code, boolean omitXmlDeclaration) throws Exception {
        FileInputStream fis = new FileInputStream(keyPath);
        KeyStore ks = KeyStore.getInstance("PKCS12", KalkanProvider.PROVIDER_NAME);
        ks.load(fis, code.toCharArray());
        fis.close();
        String alias = null;
        Enumeration en = ks.aliases();
        while(en.hasMoreElements()) {
            alias = (String)en.nextElement();
        }
        return signXml(xml, ks.getCertificate(alias), ks.getKey(alias, code.toCharArray()), omitXmlDeclaration);
    }

    public static String signXml(String xml, URL keyPathUrl, String code) throws Exception {
        KeyStore ks = KeyStore.getInstance("PKCS12", KalkanProvider.PROVIDER_NAME);
        ks.load(keyPathUrl.openStream(), code.toCharArray());
        String alias = null;
        Enumeration en = ks.aliases();
        while(en.hasMoreElements()) {
            alias = (String)en.nextElement();
        }
        return signXml(xml, ks.getCertificate(alias), ks.getKey(alias, code.toCharArray()));
    }

    public static String signXml(String xml, URL keyPathUrl, String code, boolean omitXmlDeclaration) throws Exception {
        try{
            KeyStore ks = KeyStore.getInstance("PKCS12", KalkanProvider.PROVIDER_NAME);
            ks.load(keyPathUrl.openStream(), code.toCharArray());
            String alias = null;
            Enumeration en = ks.aliases();
            while(en.hasMoreElements()) {
                alias = (String)en.nextElement();
            }
            return signXml(xml, ks.getCertificate(alias), ks.getKey(alias, code.toCharArray()), omitXmlDeclaration);
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }


//    public static String signXml(String xml,Long branchId,boolean omitXmlDeclaration) throws Exception{
//        return signXml(xml, new URL(ConfigHelper.getKeyPathByBranchId(branchId)), ConfigHelper.getKeyPassByBranchId(branchId), omitXmlDeclaration);
//    }

}
