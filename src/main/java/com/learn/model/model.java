package com.learn.model;



import com.learn.util.MyX509TrustManager;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

public class model {
    StringBuffer buffer = null;

    public String getData(String url, String requestMethod, String outputStr) {
        String data = null;
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            //创建信任管理器
            TrustManager[] tm = {new MyX509TrustManager()};
            //初始化sslContext
            sslContext.init(null, tm, new SecureRandom());
            URL u = new URL(url);
            HttpsURLConnection connection = (HttpsURLConnection) u.openConnection();
            //获取SSLSocketFactory
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            connection.setSSLSocketFactory(sslSocketFactory);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
            connection.setRequestProperty("contentType", "UTF-8");
            connection.setRequestProperty("scheme", "https");
            connection.setRequestProperty("accept-encoding", "gzip, deflate, br");
            connection.setRequestProperty("accept-language", "zh-CN,zh;q=0.9");
            connection.setRequestProperty("sec-ch-ua-mobile", "?0");
            connection.setRequestProperty("sec-fetch-dest", "document");
            connection.setRequestProperty("sec-fetch-mode", "navigate");
            connection.setRequestProperty("sec-fetch-site", "none");
            connection.setRequestProperty("sec-fetch-user", "1");
            connection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537.36");
            connection.setUseCaches(false);
            connection.setRequestProperty("X-MBX-APIKEY", "99KbJ8zbzP3bWk5Coq3FYJzNUViUMJHqqfQbz631c12c7EqqqhfcnGot94MB8W44");
            System.out.println("====请求体是" + outputStr);
            connection.setRequestProperty("type","GET");
            connection.connect();

            if (null != outputStr) {
                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();
            }
            InputStream inputStream;
            System.out.println("====相应状态是：" + connection.getResponseCode());
            if (connection.getResponseCode() == HttpsURLConnection.HTTP_OK || connection.getResponseCode() == HttpsURLConnection.HTTP_CREATED || connection.getResponseCode() == HttpsURLConnection.HTTP_ACCEPTED) {
                inputStream = connection.getInputStream();
            } else {
                inputStream = connection.getErrorStream();
            }
            // 从输入流读取返回内容
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            System.out.println("|" + "获取到了流");
            String str = null;
            buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
                System.out.println("|" + str);
            }

            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            connection.disconnect();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }
}
