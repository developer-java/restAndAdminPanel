package kz.sud.cabinet.util;

import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.keys.KeyInfo;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.signature.XMLSignatureException;
import org.apache.xml.security.utils.XMLUtils;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import sun.security.x509.X500Name;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.Date;

public class DigitalSignUtil {
    static {
        CryptoInitializer.initCrypto();
    }

    public static Object[] validateSignedXmlToTime(String signedXml, Date date) {
        if(signedXml == null || signedXml.isEmpty()) {
            return new Object[]{ValidateResult.SUCCESS.getCode()};
        }
        Document doc = parseDocument(signedXml);
        if(doc == null) {
            return new Object[]{ValidateResult.CORRUPTED_XML.getCode()};
        }
        try {
            Element nscontext = XMLUtils.createDSctx(doc, "ds", "http://www.w3.org/2000/09/xmldsig#");
            NodeList list = XPathAPI.selectNodeList(doc, "//ds:Signature", nscontext);
            for(int i = list.getLength() - 1; i >= 0; i--) {
                Element element = (Element) list.item(i);
                XMLSignature signature = new XMLSignature(element, "");
                X509Certificate certKey;
                try {
                    KeyInfo ki = signature.getKeyInfo();
                    certKey = ki.getX509Certificate();
                    X500Name dn = certKey != null ? (X500Name)certKey.getSubjectDN() : null;
                    if(certKey != null && dn != null) {
                        if(!signature.checkSignatureValue(certKey)) {
                            return new Object[]{ValidateResult.FAILURE_BAD_SIGNATURE.getCode()};
                        }
                    }
                    else {
                        return new Object[]{ValidateResult.CORRUPTED_CERT.getCode()};
                    }
                    try {
                        certKey.checkValidity(date == null ? new Date() : date);
                    }
                    catch(CertificateExpiredException e) {
                        return new Object[]{ValidateResult.FAILURE_EXPIRED.getCode()};
                    }
                    catch(CertificateNotYetValidException e) {
                        return new Object[]{ValidateResult.FAILURE_NOT_YET_VALID.getCode()};
                    }
                    catch(Exception e) {
                        e.printStackTrace();
                        return new Object[]{ValidateResult.FAILURE_UNKNOWN.getCode()};
                    }
                    String bin = dn.getOrganizationalUnit();
                    if(bin != null && bin.length() > 12) {
                        bin = bin.substring(3);
                    }
                    String iin = dn.findMostSpecificAttribute(X500Name.SERIALNUMBER_OID).getAsString();
                    if(iin != null && iin.length() > 12) {
                        iin = iin.substring(3);
                    }
                    String[] cn = dn.getCommonName().split(" ");
                    return new Object[]{ValidateResult.SUCCESS.getCode(), iin, cn[0], cn[1], dn.getGivenName(), null, bin, dn.getOrganization(), element};
                }
                catch(XMLSignatureException e) {
                    e.printStackTrace();
                    return new Object[]{ValidateResult.CORRUPTED_CERT.getCode()};
                }
                catch(XMLSecurityException e) {
                    e.printStackTrace();
                    return new Object[]{ValidateResult.CORRUPTED_CERT.getCode()};
                }
                catch(Exception e) {
                    e.printStackTrace();
                    return new Object[]{ValidateResult.FAILURE_UNKNOWN.getCode()};
                }
            }
        }
        catch(TransformerException e) {
            e.printStackTrace();
            return new Object[]{ValidateResult.CORRUPTED_XML.getCode()};
        }
        catch(XMLSecurityException e) {
            e.printStackTrace();
            return new Object[]{ValidateResult.CORRUPTED_XML.getCode()};
        }
        return new Object[]{ValidateResult.FAILURE_UNKNOWN.getCode()};
    }

    public static String signXml(String xmlForSign) throws Throwable {
        return Signer.signXml(xmlForSign, "sign.p12", "123456");
    }

    public static String getUnsignXml(String signedXml){
        Document signDoc = null;
        try{
            signDoc = parseDocument(signedXml);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        Document requestDoc = unsignDocument(signDoc);
        TransformerFactory tFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = tFactory.newTransformer();
        }
        catch(TransformerConfigurationException e2) {
            e2.printStackTrace();
        }
        ByteArrayOutputStream outputDoc = new ByteArrayOutputStream();
        DOMSource source = new DOMSource(requestDoc);
        StreamResult result = new StreamResult(outputDoc);
        try {
            transformer.transform(source, result);
        }
        catch(TransformerException e2) {
            e2.printStackTrace();
        }
        try {
            return outputDoc.toString("UTF-8");
        }
        catch(UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Document parseDocument(String xml) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(xml.getBytes("UTF-8"));
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
            return documentBuilder.parse(bais);
        }
        catch(UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        catch(ParserConfigurationException e) {
            e.printStackTrace();
        }
        catch(SAXException e) {
            e.printStackTrace();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Document unsignDocument(Document doc) {
        Element nscontext = XMLUtils.createDSctx(doc, "ds", "http://www.w3.org/2000/09/xmldsig#");
        try {
            Element sigElement = (Element)XPathAPI.selectSingleNode(doc, "//ds:Signature[1]", nscontext);
            if(sigElement != null) {
                sigElement.getParentNode().removeChild(sigElement);
            }
        }
        catch(TransformerException e) {
            e.printStackTrace();
        }
        return doc;
    }

    public static enum ValidateResult {
        SUCCESS(0),
        CORRUPTED_CERT(1),
        CORRUPTED_XML(2),
        FAILURE_EXPIRED(3),
        FAILURE_NOT_YET_VALID(4),
        FAILURE_CHAIN_INVALID(5),
        FAILURE_REVOCED(6),
        FAILURE_UNKNOWN(7),
        FAILURE_BAD_SIGNATURE(8),
        FAILURE_WRONG_KEYUSAGE(9),
        FAILURE_TSP_IS_NULL(10),
        FAILURE_TSP_WRONG(11),
        FAILURE_TSP_ERROR(15),
        FAILURE_OCSP_IS_NULL(12),
        FAILURE_OCSP_ERROR(13),
        FAILURE_OCSP_USER_CERT_NOT_FOUND(14);

        int code;

        private ValidateResult(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }
    public static class CertInfo {
        private String iin;
        private String surname;
        private String firstName;
        private String patronymic;
        private String email;
        private Date beginDate;
        private Date endDate;
        private String bin;
        private String organization;

        public String getIin() {
            return iin;
        }

        public void setIin(String iin) {
            this.iin = iin;
        }

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getPatronymic() {
            return patronymic;
        }

        public void setPatronymic(String patronymic) {
            this.patronymic = patronymic;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Date getBeginDate() {
            return beginDate;
        }

        public void setBeginDate(Date beginDate) {
            this.beginDate = beginDate;
        }

        public Date getEndDate() {
            return endDate;
        }

        public void setEndDate(Date endDate) {
            this.endDate = endDate;
        }

        public String getBin() {
            return bin;
        }

        public void setBin(String bin) {
            this.bin = bin;
        }

        public String getOrganization() {
            return organization;
        }

        public void setOrganization(String organization) {
            this.organization = organization;
        }
    }
}
