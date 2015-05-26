package com.SHM.tools;

import java.io.IOException;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class HttpUtil {  
  
    /** 
     * 发送GET请求 
     * @param uri 
     * @return 
     */  
    public static String sendGet(String uri) {  
        String responseBody = null;  
        HttpClient httpClient = new DefaultHttpClient();  
        try {  
            HttpGet httpGet = new HttpGet(uri);  
            // Create a response handler  
            ResponseHandler<String> responseHandler = new BasicResponseHandler();  
            responseBody = httpClient.execute(httpGet, responseHandler);  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            httpClient.getConnectionManager().shutdown();  
        }  
        return responseBody;  
    }  
      
    /** 
     * 发送POST请求 
     * @param uri 
     * @param paramMap 请求参数 
     * @return 
     */  
    public static String sendPost(String uri, List<NameValuePair> param) {  
        return sendPost(uri, param, null);  
    }  
  
    /** 
     * @param uri 
     * @param paramMap 请求参数 
     * @param charset 参数编码 
     * @return 
     */  
    public static String sendPost(String uri, List<NameValuePair> param, String charset) {  
        String responseBody = null;  
        HttpClient httpClient = new DefaultHttpClient();  
        try {  
            HttpPost httpPost = new HttpPost(uri);  
            if (param != null) {  
                if(charset!=null){  
                    httpPost.setEntity(new UrlEncodedFormEntity(param,HTTP.UTF_8));  
                }else{  
                    httpPost.setEntity(new UrlEncodedFormEntity(param,HTTP.UTF_8));  
                }  
            }  
            
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000);
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 3000);


            HttpResponse response = httpClient.execute(httpPost);
            
            
            if(response.getStatusLine().getStatusCode() == 200){
            	responseBody = EntityUtils.toString(response.getEntity());
            }else{
            	responseBody = String.valueOf(response.getStatusLine().getStatusCode());
            }

        } catch (ClientProtocolException e) {  
        	return responseBody;
        } catch (IOException e) {  
        	return responseBody;
        } finally {  
            httpClient.getConnectionManager().shutdown();  
        }  
        return responseBody;  
    }  
}  

