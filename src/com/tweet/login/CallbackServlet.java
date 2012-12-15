package com.tweet.login;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;
import twitter4j.auth.RequestToken;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tweet.login.dao.TwitterUser;

import java.io.IOException;

public class CallbackServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	User user = null;
    	TwitterUser twitterUser = null;
        Twitter twitter = (Twitter) request.getSession().getAttribute("twitter");
        RequestToken requestToken = (RequestToken) request.getSession().getAttribute("requestToken");
        String verifier = request.getParameter("oauth_verifier");
        try {
            twitter.getOAuthAccessToken(requestToken, verifier);
            request.getSession().removeAttribute("requestToken");
			user = twitter.showUser(twitter.getId());
			twitterUser = new TwitterUser(user.getId(),user.getName(),user.getScreenName(),user.getLocation(),
					user.getDescription(),user.getProfileImageURL().toString(),user.getProfileBackgroundImageUrl().toString(),
					user.getFollowersCount(),user.getFriendsCount(),user.getFavouritesCount(),user.getStatusesCount(),
					user.getListedCount(),user.getCreatedAt().toString(),user.getTimeZone(),user.getUtcOffset(),
					user.getLang(),user.getURL().toString());
			request.getSession().setAttribute("twitterUser", twitterUser);
        	request.getSession().setAttribute("loginType", "Twitter");
        } catch (TwitterException e) {
            throw new ServletException(e);
        }
        response.sendRedirect(request.getContextPath() + "/welcome.jsp");
    }
}
