package com.test.situo;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.*;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * @Author:zhao.song
 * @Date:2019/10/29 21:41
 * @Description:http请求工具
 */
public class HttpClientHelper {

    private static Logger logger = LoggerFactory.getLogger(HttpClientHelper.class);


    public static String doPostByFormData(String url, String params, String contentType, String head) {
        //创建HttpClientBuilder
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        //HttpClient
        CloseableHttpClient client = httpClientBuilder.build();

        HttpPost post = new HttpPost(url);
        CloseableHttpResponse res = null;
        try {
            StringEntity s = new StringEntity(params, "UTF-8");
            if (StringUtils.isBlank(contentType)) {
                s.setContentType("application/json");
            }
            s.setContentType(contentType);
            s.setContentEncoding("utf-8");
            post.setEntity(s);

            if (StringUtils.isNotBlank(head)) {
                post.addHeader("Authorization", head);
            }

            res = client.execute(post);
            HttpEntity entity = res.getEntity();
            return EntityUtils.toString(entity, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                res.close();
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static String doPost1111(String url, Map<String, Object> params) throws IOException {

        HttpClient context = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        post.setHeader("Accept-Language", "zh-CN,zh;q=0.9");
        post.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        post.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/30.0.1599.101 Safari/537.36");
        post.setHeader("Accept-Encoding", "gzip,deflate");  //像header这些自己去设置吧
        post.setHeader("Content-Type", "multipart/form-data;charset=utf-8");

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        final ContentType contentType = ContentType.create("multipart/form-data", "utf-8");
//        builder.addBinaryBody("name=\"File\"; filename=\"testImg.png\"", new File("C:\\Users\\Administrator\\Desktop\\testImg.png"));//添加文件
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            builder.addTextBody(entry.getKey(), entry.getValue() == null
                    ? "" : entry.getValue().toString());  //添加文本类型参数
//            builder.addTextBody("Language", "9");
        }
        post.setEntity(builder.build());
        /*MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            reqEntity.addPart(entry.getKey(), new StringBody(entry.getValue() == null
                    ? "" : entry.getValue().toString()));
        }
        post.setEntity(reqEntity);*/

        HttpResponse response = context.execute(post);
        byte[] res = null;
        //获取参数
        /**请求发送成功，并得到响应**/
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            res = EntityUtils.toByteArray(response.getEntity());
        }
//        System.out.println(Jsoup.parse(new String(uncompress(res), "utf-8")).select("div[class=div_res]"));
        return new String(uncompress(res), "utf-8");
    }

    public static byte[] uncompress(byte[] b) throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(b);
        GZIPInputStream gunzip = new GZIPInputStream(in);
        byte[] buffer = new byte[256];
        int n;
        while ((n = gunzip.read(buffer)) >= 0) {
            out.write(buffer, 0, n);
        }
        return out.toByteArray();
    }

    public static String doPost(String url, String json) {
        logger.info("调用post请求...");

        String returnValue = null;
        CloseableHttpClient httpClient = null;
        try {
            //建立cookie
            httpClient = HttpClients.createDefault();
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            //创建HttpPost对象
            HttpPost httpPost = new HttpPost(url);
            //给httpPost设置JSON格式的参数
            StringEntity requestEntity = new StringEntity(json, "utf-8");
            requestEntity.setContentEncoding("UTF-8");
            requestEntity.setContentType("application/json");
            httpPost.setEntity(requestEntity);
            httpPost.setHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_CLOSE);
            //发送httpPost请求,获取返回值
            returnValue = httpClient.execute(httpPost, responseHandler);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(String.format("请求接口失败!错误信息:%s", e.getMessage()), e);
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                logger.error("请求连接无法关闭!");
                e.printStackTrace();
            }
        }
        //返回结果
        return returnValue;
    }

    public static String doPost(String url) {
        logger.info("调用post请求...");

        String returnValue = null;
        CloseableHttpClient httpClient = null;
        try {
            //建立cookie
            httpClient = HttpClients.createDefault();
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            //创建HttpPost对象
            HttpPost httpPost = new HttpPost(url);
            //给httpPost设置JSON格式的参数
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_CLOSE);
            //发送httpPost请求,获取返回值
            returnValue = httpClient.execute(httpPost, responseHandler);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(String.format("请求接口失败!错误信息:%s", e.getMessage()), e);
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                logger.error("请求连接无法关闭!");
                e.printStackTrace();
            }
        }
        //返回结果
        return returnValue;
    }

    public static String doGet(String url, Map<String, Object> params) {

        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //response 对象
        CloseableHttpResponse response = null;
        //返回结果
        String result = null;
        try {
            // 定义请求的参数
            URIBuilder uriBuilder = new URIBuilder(url);
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                uriBuilder.setParameter(entry.getKey(), URLEncoder.encode(entry.getValue().toString()));
            }
            URI uri = uriBuilder.build();
            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);
            httpGet.setHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_CLOSE);
            // 执行http get请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(response.getEntity(), "UTF-8");

            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {

            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error(e.getMessage(), e);
                }
            }
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
                logger.error(e.getMessage(), e);
            }
        }
        return result;
    }


   /* // 每个post参数之间的分隔。随意设定，只要不会和其他的字符串重复即可。
    private static final String BOUNDARY = "----------HV2ymHFg03ehbqgZCaKO6jyH";

    public String sendHttpPostRequest(String serverUrl,
                                      ArrayList<FormFieldKeyValuePair> generalFormFields,
                                      ArrayList<UploadFileItem> filesToBeUploaded) throws Exception {

        // 向服务器发送post请求

        URL url = new URL(serverUrl*//* "http://127.0.0.1:8080/test/upload" *//*);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // 发送POST请求必须设置如下两行

        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setUseCaches(false);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Connection", "Keep-Alive");
        connection.setRequestProperty("Charset", "UTF-8");
        connection.setRequestProperty("Content-Type",
                "multipart/form-data; boundary=" + BOUNDARY);

        // 头

        String boundary = BOUNDARY;

        // 传输内容

        StringBuffer contentBody = new StringBuffer("--" + BOUNDARY);

        // 尾

        String endBoundary = "\r\n--" + boundary + "--\r\n";

        OutputStream out = connection.getOutputStream();

        // 1. 处理文字形式的POST请求

        for (FormFieldKeyValuePair ffkvp : generalFormFields) {

            contentBody.append("\r\n")

                    .append("Content-Disposition: form-data; name=\"")

                    .append(ffkvp.getKey() + "\"")

                    .append("\r\n")

                    .append("\r\n")

                    .append(ffkvp.getValue())

                    .append("\r\n")

                    .append("--")

                    .append(boundary);

        }

        String boundaryMessage1 = contentBody.toString();

        out.write(boundaryMessage1.getBytes("utf-8"));

        // 2. 处理文件上传

        for (UploadFileItem ufi : filesToBeUploaded) {

            contentBody = new StringBuffer();

            contentBody.append("\r\n")

                    .append("Content-Disposition:form-data; name=\"")

                    .append(ufi.getFormFieldName() + "\"; ") // form中field的名称

                    .append("filename=\"")

                    .append(ufi.getFileName() + "\"") // 上传文件的文件名，包括目录

                    .append("\r\n")

                    .append("Content-Type:application/octet-stream")

                    .append("\r\n\r\n");

            String boundaryMessage2 = contentBody.toString();

            out.write(boundaryMessage2.getBytes("utf-8"));

            // 开始真正向服务器写文件

            File file = new File(ufi.getFileName());

            DataInputStream dis = new DataInputStream(new FileInputStream(file));

            int bytes = 0;

            byte[] bufferOut = new byte[(int) file.length()];

            bytes = dis.read(bufferOut);

            out.write(bufferOut, 0, bytes);

            dis.close();

            contentBody.append("------------HV2ymHFg03ehbqgZCaKO6jyH");

            String boundaryMessage = contentBody.toString();

            out.write(boundaryMessage.getBytes("utf-8"));

            // System.out.println(boundaryMessage);

        }

        out.write("------------HV2ymHFg03ehbqgZCaKO6jyH--\r\n"
                .getBytes("UTF-8"));

        // 3. 写结尾

        out.write(endBoundary.getBytes("utf-8"));

        out.flush();

        out.close();

        // 4. 从服务器获得回答的内容

        String strLine = "";

        String strResponse = "";

        InputStream in = connection.getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        while ((strLine = reader.readLine()) != null) {

            strResponse += strLine + "\n";

        }

        // System.out.print(strResponse);

        return strResponse;

    }*/


}
