package com.plus.google;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.NameValuePair;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.plus.google.dao.GooglePlusUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class CallbackServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    
    private static JSONObject getWebContentFromURL_Post(String httpURL, List <NameValuePair>  nvps) {
    	
    	HttpPost httpPost = null;
    	JSONObject json = null;
    	
    	try { 		
    		DefaultHttpClient httpclient = new DefaultHttpClient();
    		httpPost = new HttpPost(httpURL);    	
    		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
    		HttpResponse response = httpclient.execute(httpPost);
    		
		    System.out.println(response.getStatusLine());
		    System.out.println(response.toString());
		    
		    BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
		    StringBuilder builder = new StringBuilder();
		    for (String line = null; (line = reader.readLine()) != null;) {
		        builder.append(line).append("\n");
		    }
		    
		    System.out.println(builder.toString());
		    
		    json = (JSONObject)new JSONParser().parse(builder.toString());
		    //System.out.println("name=" + json.get("name"));
		    //System.out.println("width=" + json.get("width"));

		    EntityUtils.consume(response.getEntity());
		    
		} catch(Exception ex) {
			
		} finally {
		    httpPost.releaseConnection();

		}
    	
    	return json;
    }
    
    
    
    private static JSONObject getWebContentFromURL_Get(String httpURL) {
    	
    	HttpGet httpGet = null;
    	JSONObject json = null;
  
    	try { 		
        	DefaultHttpClient httpclient = new DefaultHttpClient();
        	httpGet = new HttpGet(httpURL);
        	HttpResponse response = httpclient.execute(httpGet);
    		
		    System.out.println(response.getStatusLine());
		    System.out.println(response.toString());
		    
		    BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
		    StringBuilder builder = new StringBuilder();
		    for (String line = null; (line = reader.readLine()) != null;) {
		        builder.append(line).append("\n");
		    }
		    
		    System.out.println(builder.toString());
		    
		    json = (JSONObject)new JSONParser().parse(builder.toString());
		    //System.out.println("name=" + json.get("name"));
		    //System.out.println("width=" + json.get("width"));

		    EntityUtils.consume(response.getEntity());
		    
		} catch(Exception ex) {
			
		} finally {
			httpGet.releaseConnection();
		}
    	
    	return json;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String code = null;
        String googlePlusClientId = getServletContext().getInitParameter("googlePlusClientId");
        String googlePlusClientSecret = getServletContext().getInitParameter("googlePlusClientSecret");
        String redirectURL = null;
        String accessURL = null;
        String apiURL = null;
    	String accessToken = null;
    	JSONObject webContent = null;
    	JSONObject userJson = null;
        try {
            StringBuffer redirectURLbuffer = request.getRequestURL();
            int index = redirectURLbuffer.lastIndexOf("/");
            redirectURLbuffer.replace(index, redirectURLbuffer.length(), "").append("/gpluscallback");
            redirectURL = redirectURLbuffer.toString(); 
            //URLEncoder.encode(redirectURLbuffer.toString(), "UTF-8");
            
        	code = request.getParameter("code");
        	if(null!=code) {
        		System.out.println("Code: " + code);
        		accessURL = "https://accounts.google.com/o/oauth2/token";
        		
        		List <NameValuePair> nvps = new ArrayList <NameValuePair>();
        		nvps.add(new BasicNameValuePair("client_id", googlePlusClientId));
        		nvps.add(new BasicNameValuePair("client_secret", googlePlusClientSecret));
        		nvps.add(new BasicNameValuePair("grant_type", "authorization_code"));
        		nvps.add(new BasicNameValuePair("redirect_uri", redirectURL));
        		nvps.add(new BasicNameValuePair("code", code));

        		System.out.println("accessURL: " + accessURL);
        		webContent = getWebContentFromURL_Post(accessURL,nvps);
        		accessToken = (String) webContent.get("access_token");
        		        		
        		
        		
        	} else {
        		response.sendRedirect(request.getContextPath() + "/error.html");
        		return;
        	}
        	
            if(null!=accessToken) {
            	System.out.println("accessToken: " + accessToken);
            	apiURL = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=" + accessToken;
            	userJson = getWebContentFromURL_Get(apiURL);
            
        		GooglePlusUser googlePlusUser = new GooglePlusUser(userJson.get("id").toString(), userJson.get("email").toString(),
        				userJson.get("given_name").toString(), userJson.get("family_name").toString(), 
        				userJson.get("name").toString(), userJson.get("link").toString(), userJson.get("picture").toString(), 
        				userJson.get("gender").toString(), userJson.get("birthday").toString(), userJson.get("locale").toString());
            	request.getSession().setAttribute("googlePlusUser", googlePlusUser);
            	System.out.println("User object: " + googlePlusUser.toString());
            	request.getSession().setAttribute("loginType", "Google Plus");
            	response.sendRedirect(request.getContextPath() + "/welcome.jsp");
            }
  
            if(null==accessToken)
            	response.sendRedirect(request.getContextPath() + "/error.html");
        	
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/error.html");
            throw new ServletException(e);
        }
        
    }
}
