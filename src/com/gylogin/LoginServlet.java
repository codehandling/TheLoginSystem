package com.gylogin;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.expressme.openid.Association;
import org.expressme.openid.Authentication;
import org.expressme.openid.Endpoint;
import org.expressme.openid.OpenIdException;
import org.expressme.openid.OpenIdManager;

import com.gylogin.dao.GYUser;


public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	static final long ONE_HOUR = 3600000L;
    static final long TWO_HOUR = ONE_HOUR * 2L;
    static final String ATTR_MAC = "openid_mac";
    static final String ATTR_ALIAS = "openid_alias";

    private OpenIdManager manager;

    @Override
    public void init() throws ServletException {
        super.init();
    }
    
    private void initManager (StringBuffer requestURL) {
        StringBuffer callbackURL = requestURL;
        int index = callbackURL.lastIndexOf("/");
        callbackURL.replace(index, callbackURL.length(), "");
        System.out.println("GYLogin callback: " + callbackURL.toString());
        manager = new OpenIdManager();
        manager.setRealm(callbackURL.toString());
        manager.setReturnTo(callbackURL.toString()+"/gylogin");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	if(null==manager) {
    		initManager(request.getRequestURL());
    	}
    	
        String op = request.getParameter("op");
        if (op==null) {
            try {
				// check sign on result from Google or Yahoo:
				checkNonce(request.getParameter("openid.response_nonce"));
				// get authentication:
				byte[] mac_key = (byte[]) request.getSession().getAttribute(ATTR_MAC);
				String alias = (String) request.getSession().getAttribute(ATTR_ALIAS);
				Authentication authentication = manager.getAuthentication(request, mac_key, alias);
				storeAuthenticationData(authentication, request.getSession(false));
				response.sendRedirect(request.getContextPath() + "/welcome.jsp");
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
				response.sendRedirect(request.getContextPath() + "/error.html");
			}
        } else if (op.equals("Google") || op.equals("Yahoo")) {
            // redirect to Google or Yahoo sign on page:
            Endpoint endpoint = manager.lookupEndpoint(op);
            Association association = manager.lookupAssociation(endpoint);
            request.getSession().setAttribute(ATTR_MAC, association.getRawMacKey());
            request.getSession().setAttribute(ATTR_ALIAS, endpoint.getAlias());
            request.getSession().setAttribute("loginType",op);
            String url = manager.getAuthenticationUrl(endpoint, association);
            response.sendRedirect(url);
        } else if (op.equals("logout")) {
        	request.getSession().invalidate();
			response.sendRedirect(request.getContextPath());
        } else {
        	System.out.println("Invalid Op received.");
			response.sendRedirect(request.getContextPath() + "/error.html");
        }
    }

    void storeAuthenticationData(Authentication auth, HttpSession session) {
    	//setting in session for front end use
    	GYUser gyUser = new GYUser(auth.getIdentity(),auth.getEmail(),auth.getFullname(),auth.getFirstname(),auth.getLastname(),auth.getGender(),auth.getLanguage());
    	session.setAttribute("gyUser", gyUser);
    	
    	//store the user in your database if needed
    }

    void checkNonce(String nonce) {
        // check response_nonce to prevent replay-attack:
        if (nonce==null || nonce.length()<20)
            throw new OpenIdException("Verify failed.");
        // make sure the time of server is correct:
        long nonceTime = getNonceTime(nonce);
        long diff = Math.abs(System.currentTimeMillis() - nonceTime);
        if (diff > ONE_HOUR)
            throw new OpenIdException("Bad nonce time.");
    	System.out.println(nonce);
    }

    long getNonceTime(String nonce) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .parse(nonce.substring(0, 19) + "+0000")
                    .getTime();
        }
        catch(ParseException e) {
            throw new OpenIdException("Bad nonce time.");
        }
    }
}
