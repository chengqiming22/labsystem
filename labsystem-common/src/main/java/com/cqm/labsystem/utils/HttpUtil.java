package com.cqm.labsystem.utils;

import com.google.common.io.CharStreams;
import com.google.common.io.InputSupplier;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

/**
 * Created by qmcheng on 2016/12/19 0019.
 */
public class HttpUtil {

    public static final int READ_TIMEOUT = 1000 * 60 * 5; // 正常设置在5分钟：1000 * 60 * 5
    public static final int CONNECTION_TIMEOUT = 1000 * 10; // 正常设置连接在10秒：1000 * 10
    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    static HttpClient newHttpClient() {
        HttpClient client = new HttpClient();
        client.getHttpConnectionManager().getParams().setConnectionTimeout(CONNECTION_TIMEOUT);
        client.getHttpConnectionManager().getParams().setSoTimeout(READ_TIMEOUT);
        return client;
    }

    public static String post(String url, Map<String, String> params) throws IOException {
        final PostMethod method = new UTF8PostMethod(url);

        try {
            method.addRequestHeader("Connection", "Keep-Alive");
            method.addRequestHeader("Charset", "UTF-8");
            method.addRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            for (String key : params.keySet()) {
                method.addParameter(key, params.get(key));
            }
            newHttpClient().executeMethod(method);
            return CharStreams.toString(new InputSupplier<Reader>() {
                public Reader getInput() throws IOException {
                    return new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), method.getResponseCharSet()));
                }
            });
        } finally {
            method.releaseConnection();
        }
    }

    public static String postMultiMediaTypes(String api, String order_no, String type, String image) {

        final PostMethod method = new UTF8PostMethod(api);

        try {
            method.addRequestHeader("Connection", "Keep-Alive");
            method.addRequestHeader("Charset", "UTF-8");

            StringPart spOrderNo = new StringPart("order_no", order_no);
            StringPart spType = new StringPart("type", type);
            FilePart fpImage = getFilePartFromUrl(image);
            Part[] parts = {spOrderNo, spType, fpImage};
            method.setRequestEntity(new MultipartRequestEntity(parts, method.getParams()));

            newHttpClient().executeMethod(method);
            return CharStreams.toString(new InputSupplier<Reader>() {
                public Reader getInput() throws IOException {
                    return new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), method.getResponseCharSet()));
                }
            });
        } catch (Exception e) {
            logger.error("postMultiMediaTypes execute failed. " + e.getMessage());
        } finally {
            method.releaseConnection();
        }

        return null;
    }

    private static FilePart getFilePartFromUrl(String picUrl) throws IOException {
        URL url = new URL(picUrl);
        URLConnection connection = url.openConnection();
        InputStream is = connection.getInputStream();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int len;
        byte[] b = new byte[1024];
        while ((len = is.read(b, 0, b.length)) != -1) {
            baos.write(b, 0, len);
        }
        byte[] buffer = baos.toByteArray();
        String[] fileNameFields = picUrl.split("/");
        String filename = fileNameFields[fileNameFields.length - 1];

        FilePart fp = new FilePart("image", new ByteArrayPartSource(filename, buffer));
        return fp;
    }

    public static class UTF8PostMethod extends PostMethod {

        public UTF8PostMethod(String string) {
            super(string);
        }

        protected String getContentCharSet(Header contentheader) {
            return "UTF-8";
        }
    }
}
