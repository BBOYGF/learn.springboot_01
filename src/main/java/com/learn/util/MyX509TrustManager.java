package com.learn.util;

import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class MyX509TrustManager implements X509TrustManager {
    @Override
    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
        System.out.println("=====检查客户端证书"+x509Certificates[0].getSigAlgName()+"|"+s);
    }

    @Override
    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
        System.out.println("=====检查服务器证书"+x509Certificates.toString()+"|"+s);
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        System.out.println("=====返回收信任的X509证书组");
        return null;
    }
}
