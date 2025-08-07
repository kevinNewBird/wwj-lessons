package com.test.situo;

import net.sf.json.JSONObject;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.*;
import java.util.zip.GZIPInputStream;

/**
 * HTTP工具箱 模拟执行get和post请求
 */
public final class HttpTookit {

//    private static log log = logFactory.getlog(HttpTookit.class);

    private static Logger log = LoggerFactory.getLogger(HttpTookit.class);

    public static int TIME_OUT = 60000;// 60秒

    /**
     * 执行一个HTTP GET请求，返回请求响应的HTML
     *
     * @param url         请求的URL地址,可以带有get参数，如果参数中有汉字则必须将参数写在queryString中并指定编码
     * @param queryString 请求的查询参数,可以为null
     * @param charset     字符集
     * @param pretty      是否美化
     * @return 返回请求响应的HTML
     */
    public static String doGet(String url, String queryString, String charset,
                               boolean pretty) {

        StringBuffer response = new StringBuffer();
        HttpClient client = new HttpClient();
        // 链接超时(单位毫秒)
        client.getHttpConnectionManager().getParams()
                .setConnectionTimeout(TIME_OUT);
        // 读取超时(单位毫秒)
        client.getHttpConnectionManager().getParams().setSoTimeout(TIME_OUT);

        HttpMethod method = new GetMethod(url);
        // URL中可能带有参数，需要拼接
        String preParam = method.getQueryString();
        try {
            if (StringUtils.isNotBlank(queryString)) {
                // 对get请求参数做了http请求默认编码汉字编码后，就成为%式样的字符串
                String encodeUrl = URIUtil.encodeQuery(queryString);
                if (StringUtils.isEmpty(preParam)) {
                    method.setQueryString(encodeUrl);
                } else {
                    // 拼接参数
                    method.setQueryString(preParam + "&"
                            + encodeUrl);
                }
            }

            method.setRequestHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_CLOSE);
            client.executeMethod(method);

            if (method.getStatusCode() == HttpStatus.SC_OK) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(method.getResponseBodyAsStream(),
                                charset));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (pretty) {
                        response.append(line).append(
                                System.getProperty("line.separator"));
                    } else {
                        response.append(line);
                    }
                }
                reader.close();
            }
        } catch (URIException e) {
            log.error("执行HTTP Get请求时，编码查询字符串“" + queryString + "”发生异常！", e);
        } catch (IOException e) {
            log.error("执行HTTP Get请求" + url + "时，发生异常！", e);
        } finally {
            method.releaseConnection();
        }
        return response.toString();
    }

    /**
     * 执行一个HTTP POST请求，返回请求响应的HTML
     *
     * @param url     请求的URL地址
     * @param params  请求的查询参数,可以为null
     * @param charset 字符集
     * @param pretty  是否美化
     * @return 返回请求响应的HTML
     */
    public static String doPost(String url, Map<String, String> params,
                                String charset, boolean pretty) {

        StringBuffer response = new StringBuffer();
        HttpClient client = new HttpClient();
        // 链接超时(单位毫秒)
        client.getHttpConnectionManager().getParams()
                .setConnectionTimeout(TIME_OUT);
        // 读取超时(单位毫秒)
        client.getHttpConnectionManager().getParams().setSoTimeout(TIME_OUT);

        PostMethod method = new PostMethod(url);
        if (log.isDebugEnabled()) {
            log.debug("url:" + url);
            log.debug("params:" + params);
        }
        // 设置Http Post数据
        if (params != null && params.size() != 0) {
            NameValuePair[] param = new NameValuePair[params.size()];
            int i = 0;
            for (Map.Entry<String, String> entry : params.entrySet()) {
                param[i] = new NameValuePair(entry.getKey(), entry.getValue());
                i++;
            }
            method.setRequestBody(param);
        }
        try {
            method.setRequestHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_CLOSE);
            client.executeMethod(method);

            if (method.getStatusCode() == HttpStatus.SC_OK) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(method.getResponseBodyAsStream(),
                                charset));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (pretty) {
                        response.append(line).append(
                                System.getProperty("line.separator"));
                    } else {
                        response.append(line);
                    }
                }
                reader.close();
            }

        } catch (IOException e) {
            log.error("执行HTTP Post请求" + url + "时，发生异常！", e);
        } finally {
            method.releaseConnection();
        }
        return response.toString();
    }

    /**
     * @param url
     * @param params
     * @param charset
     * @param pretty
     * @return
     * @throws
     * @Title: doPostOfCharset
     * @Description: post请求对参数进行编码
     * @Author：liujian 2019年5月14日 下午4:10:29
     */
    public static String doPostOfCharset(String url, Map<String, String> params,
                                         String charset, boolean pretty) {

        StringBuffer response = new StringBuffer();
        HttpClient client = new HttpClient();
        // 链接超时(单位毫秒)
        client.getHttpConnectionManager().getParams()
                .setConnectionTimeout(TIME_OUT);
        // 读取超时(单位毫秒)
        client.getHttpConnectionManager().getParams().setSoTimeout(TIME_OUT);

        PostMethod method = new PostMethod(url);
        if (log.isDebugEnabled()) {
            log.debug("url:" + url);
            log.debug("params:" + params);
        }
        // 设置Http Post数据
        if (params != null && params.size() != 0) {
            NameValuePair[] param = new NameValuePair[params.size()];
            int i = 0;
            for (Map.Entry<String, String> entry : params.entrySet()) {
                param[i] = new NameValuePair(entry.getKey(), entry.getValue());
                i++;
            }
            method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
            method.setRequestBody(param);
        }
        try {
            method.setRequestHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_CLOSE);
            client.executeMethod(method);

            if (method.getStatusCode() == HttpStatus.SC_OK) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(method.getResponseBodyAsStream(),
                                charset));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (pretty) {
                        response.append(line).append(
                                System.getProperty("line.separator"));
                    } else {
                        response.append(line);
                    }
                }
                reader.close();
            }

        } catch (IOException e) {
            log.error("执行HTTP Post请求" + url + "时，发生异常！", e);
        } finally {
            method.releaseConnection();
        }
        return response.toString();
    }

    public static String doJsonPost(String url, JSONObject params,
                                    String charset, boolean pretty) throws UnsupportedEncodingException {

        StringBuffer response = new StringBuffer();
        HttpClient client = new HttpClient();
        // 链接超时(单位毫秒)
        client.getHttpConnectionManager().getParams()
                .setConnectionTimeout(TIME_OUT);
        // 读取超时(单位毫秒)
        client.getHttpConnectionManager().getParams().setSoTimeout(TIME_OUT);

        PostMethod method = new PostMethod(url);
//        method.setRequestHeader("Accept-Language", "zh-CN,zh;q=0.9");
//        method.setRequestHeader("Accept-Encoding", "gzip, deflate, br");
//        method.setRequestHeader("User-Agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        if (log.isDebugEnabled()) {
            log.debug("url:" + url);
            log.debug("params:" + params);
        }
        // 设置Http Post数据
        try {
            RequestEntity requestEntity = new StringRequestEntity(params.toString());
            method.setRequestEntity(requestEntity);
            method.addRequestHeader("Content-Type", "multipart/form-data;boundary=----------HV2ymHFg03ehbqgZCaKO6jyH");
            method.setRequestHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_CLOSE);
            client.executeMethod(method);

            if (method.getStatusCode() == HttpStatus.SC_OK) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(method.getResponseBodyAsStream(),
                                charset));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (pretty) {
                        response.append(line).append(
                                System.getProperty("line.separator"));
                    } else {
                        response.append(line);
                    }
                }
                reader.close();
            }

        } catch (IOException e) {
            log.error("执行HTTP Post请求" + url + "时，发生异常！", e);
        } finally {
            method.releaseConnection();
        }
        return response.toString();
    }



    /**
     * post请求(用于key-value格式的参数)
     *
     * @param url
     * @param params
     * @return
     */
    public static String doPost(String url, Map params) {

        BufferedReader in = null;
        try {
            // 定义HttpClient    
            org.apache.http.client.HttpClient client = new DefaultHttpClient();
            // 实例化HTTP方法    
            HttpPost request = new HttpPost();
            request.setURI(new URI(url));

            //设置参数  
            List<org.apache.http.NameValuePair> nvps = new ArrayList<org.apache.http.NameValuePair>();
            for (Iterator iter = params.keySet().iterator(); iter.hasNext(); ) {
                String name = (String) iter.next();
                String value = String.valueOf(params.get(name));
                nvps.add(new BasicNameValuePair(name, value));

                //System.out.println(name +"-"+value);  
            }
            request.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
            request.setHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_CLOSE);
            HttpResponse response = client.execute(request);
            int code = response.getStatusLine().getStatusCode();
            if (code == 200) {    //请求成功
                in = new BufferedReader(new InputStreamReader(response.getEntity()
                        .getContent(), "utf-8"));
                StringBuffer sb = new StringBuffer("");
                String line = "";
                String NL = System.getProperty("line.separator");
                while ((line = in.readLine()) != null) {
                    sb.append(line + NL);
                }

                in.close();

                return sb.toString();
            } else {   //
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    public static JSONObject uploadFile(Map<String, String> params, File file) {
        JSONObject js = null;
        int status = 0;
        String url = params.get("url");
        PostMethod filePost = new PostMethod(url);
        // 将参数遍历加入到表单中

        Set set = params.keySet();
        Part[] parts = new Part[set.size()];
        Iterator<String> it = set.iterator();
        int n = 0;
        while (it.hasNext()) {
            String key = it.next();
            if ("url".equalsIgnoreCase(key)) {
                // url不需要传
                continue;
            }
            parts[n] = new StringPart(key, params.get(key), "utf-8");
            n++;
        }

        HttpClient client = new HttpClient();
        try {
            parts[parts.length - 1] = new FilePart("file", file);

            filePost.setRequestEntity(
                    new MultipartRequestEntity(parts, filePost.getParams()));
            // 超时设置20秒
            HttpClientParams httpClientParams = new HttpClientParams();
            httpClientParams.setParameter(HttpMethodParams.RETRY_HANDLER,
                    new DefaultHttpMethodRetryHandler(0, false));
            client.setParams(httpClientParams);
            client.getHttpConnectionManager().getParams()
                    .setConnectionTimeout(20000);
            client.getHttpConnectionManager().getParams().setSoTimeout(20000);
            filePost.setRequestHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_CLOSE);
            status = client.executeMethod(filePost);

            if (status == HttpStatus.SC_OK) {
                String result = filePost.getResponseBodyAsString();
                JSONObject rejs = JSONObject.fromObject(result);
                js = rejs;
            } else {
                js = new JSONObject();
                js.put("code", 0);
                js.put("data", null);
                js.put("msg", "接口服务器返回异常");
            }
        } catch (Exception e) {
            try {
                js = new JSONObject();
                js.put("code", 1);
                js.put("msg", e.toString());
            } catch (Exception e1) {
                log.error(e1.toString(), e1);
            }
            return js;
        } finally {
            filePost.releaseConnection();
        }
        return js;
    }

    public static String doPostByFormData(String url, JSONObject jsonParam) {
        HttpPost httpPost = new HttpPost(url);

        CloseableHttpClient client = HttpClients.createDefault();
        String respContent = null;

        log.info("=============:\t" + jsonParam.toString());
        // 超时设置20秒
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        final ContentType contentType = ContentType.create("multipart/form-data");
//        builder.addTextBody("json", jsonParam.toString(), contentType);
        for (Object key : jsonParam.keySet()) {

            builder.addTextBody(key.toString(), jsonParam.getString(key.toString()), contentType);
//            builder.addPart()
        }


        HttpEntity multipart = builder.build();

        HttpResponse resp = null;
        try {
            httpPost.setEntity(multipart);
            resp = client.execute(httpPost);

            if (resp.getStatusLine().getStatusCode() == 200) {
                HttpEntity he = resp.getEntity();
                respContent = EntityUtils.toString(he, "UTF-8");
            }
            log.info("=========================:\t" + respContent);
            log.info("=========================:\t" + resp.getStatusLine().getStatusCode());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return respContent;
    }

    public static void main(String[] args) throws URIException {
        String url = "http://10.100.62.75:9005/widget/multichannalmonitoring/config?user_id=admin&department=891&monitor_name=website&selection=site&site_name=" + URIUtil.encodeQuery("中国");
        String url2 = "http://10.100.62.75:9005/widget/multichannalmonitoring/config?user_id=admin&department=891&monitor_name=website&selection=site&site_name=%25E4%25B8%25AD%25E5%259B%25BD";
        String url3 = "http://pm.8531.cn:10000/mediacube/#/editctr/iWo/personalManuscript";
        System.out.println(URIUtil.encodeQuery("中国"));
        String y = doPost(url3, null, "UTF-8", false);
        System.out.println(111);
        System.out.println(y);
    }
}