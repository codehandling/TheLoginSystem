<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>The Login System 1.0</title>
	<style type="text/css">
		.container {
			width:100%;
			background-color:white;
			text-align:center;
			font-family:tahoma; 
		}
		#auth-display-img {
			height:30px; 
			float:left;
		}
		
		#auth-display-name {
			height:30px; 
			float:left; 
			width:120px; 
			background-color:rgb(51,95,156);
			color:white; 
			font-family:tahoma; 
			font-weight:bold;
			font-size:12px; 
			line-height:30px;		
		}
		
		#auth-info {
			width:300px;
			margin: 0px auto;
			text-align:left;
			font-family:tahoma; 
			font-size:14px;
		}
		
		b {
			font-weight:normal;
			color:blue;
		}
		
		i {
			font-weight:bold;
			font-size:20px;
			text-transform:capitalize;
		}

		#logout {
			margin: 0px auto;
			width:80px;
			height:23px;
			background-color: blue; 
			color:white;
		}		
	</style>
</head>
<body>
<div class="container">
	<h1>The Login System 1.0</h1>
	<h4>You logged in using ${loginType}.</h4><br>		
	<tag:loggedInUsingFacebook>
		<div id="auth-status">
			<div id="auth-loggedin" style="width:250px; margin:0px auto;">
				<div id="auth-display-img">
					<img style='height:100%;' src='${fbUser.profilePicLink}'/>
				</div>
				<div id="auth-display-name">${fbUser.name}</div><br/><br/>
				<div id="auth-info">
					<br/><br/>
					<b>Id: </b>${fbUser.id}<br/>
					<b>Full Name: </b>${fbUser.name}<br/>
					<b>Email: </b>${fbUser.email}<br/>
					<b>First Name: </b>${fbUser.firstName}<br/>
					<b>Last Name: </b>${fbUser.lastName}<br/>
					<b>Gender: </b>${fbUser.gender}<br/>
					<b>User Web Site: </b>${fbUser.website}<br/>
					<b>Birthday: </b>${fbUser.birthday}<br/>			
					<b>Home Town: </b>${fbUser.hometown}<br/>
					<b>Current Location: </b>${fbUser.location}<br/>
					<b>Bio: </b>${fbUser.bio}<br/>
					<b>About: </b>${fbUser.about}<br/>
					<b>Fb Link: </b>${fbUser.link}<br/>
					<b>Locale: </b>${fbUser.locale}<br/>
					<b>Relationship Status: </b>${fbUser.relationshipStatus}<br/>
					<b>Interested In: </b>${fbUser.interestedIn}<br/>
					<b>Profile Pic URL: </b>${fbUser.profilePicLink}<br/>
					<br/><br/>					
				</div>
			</div>
		</div>
	</tag:loggedInUsingFacebook>
					
	<tag:loggedInUsingTwitter>   
		<div id="auth-info">
			<img src="${twitterUser.profileImageUrl}">&nbsp;<i>${twitterUser.name}</i>
			<br/><br/>
			<b>Id: </b>${twitterUser.id}<br/>
			<b>Full Name: </b>${twitterUser.name}<br/>
			<b>Email: </b>Twitter does not provide this!<br/>
			<b>Screen Name: </b>${twitterUser.screenName}<br/>
			<b>Location: </b>${twitterUser.location}<br/>
			<b>User Web Site: </b>${twitterUser.userWebSite}<br/>
			<b>Profile Created On: </b>${twitterUser.createdAt}<br/>			
			<b>Followers Count: </b>${twitterUser.followersCount}<br/>
			<b>Friends Count: </b>${twitterUser.friendsCount}<br/>
			<b>Favourites Count: </b>${twitterUser.favouritesCount}<br/>
			<b>Statuses Count</b>${twitterUser.statusesCount}<br/>
			<b>Listed Count: </b>${twitterUser.listedCount}<br/>
			<b>TimeZone: </b>${twitterUser.timeZone}<br/>
			<b>UTC Offset: </b>${twitterUser.utcOffset}<br/>
			<b>Language: </b>${twitterUser.lang}<br/>
			<b>Background Image URL: </b>${twitterUser.profileBackgroundImageUrl}<br/>
			<b>Profile Image URL: </b>${twitterUser.profileImageUrl}<br/>
			<br/><br/>	
		</div>   
	</tag:loggedInUsingTwitter>
	
	<tag:loggedInUsingGoogleOrYahoo>
		<div id="auth-info">
			Welcome !
			<br/><br/>
			<b>Id: </b>${gyUser.id}<br/>
			<b>Full Name: </b>${gyUser.fullname}<br/>
			<b>Email: </b>${gyUser.email}<br/>
			<b>First Name: </b>${gyUser.firstname}<br/>
			<b>Last name: </b>${gyUser.lastname}<br/>
			<b>Gender: </b>${gyUser.gender}<br/>
			<b>Language: </b>${gyUser.lang}<br/><br/>	
		</div>
	</tag:loggedInUsingGoogleOrYahoo>
	
	<tag:loggedInUsingInstagram>
		<div id="auth-status">
			<div id="auth-loggedin" style="width:250px; margin:0px auto;">
				<div id="auth-display-img">
					<img style='height:100%;' src='${instagramUser.profilePicLink}'/>
				</div>
				<div id="auth-display-name">${instagramUser.username}</div><br/><br/>
				<div id="auth-info">
					<br/><br/>
					<b>Id: </b>${instagramUser.id}<br/>
					<b>User Name: </b>${instagramUser.username}<br/>
					<b>Full Name: </b>${instagramUser.fullname}<br/>
					<b>Website: </b>${instagramUser.website}<br/>
					<b>Bio: </b>${instagramUser.bio}<br/>
					<b>Profile Pic URL: </b>${instagramUser.profilePicLink}<br/>
					<br/><br/>					
				</div>				
			</div>
		</div>
	</tag:loggedInUsingInstagram>	
	
	<tag:loggedInUsingGooglePlus>
		<div id="auth-status">
			<div id="auth-loggedin" style="width:250px; margin:0px auto;">
				<div id="auth-display-img">
					<img style='height:100%;' src='${googlePlusUser.profilePicLink}'/>
				</div>
				<div id="auth-display-name">${googlePlusUser.fullname}</div><br/><br/>
				<div id="auth-info">
					<br/><br/>
					<b>Id: </b>${googlePlusUser.id}<br/>
					<b>Email: </b>${googlePlusUser.email}<br/>				
					<b>First Name: </b>${googlePlusUser.firstname}<br/>
					<b>Last Name: </b>${googlePlusUser.lastname}<br/>
					<b>Full Name: </b>${instagramUser.fullname}<br/>				
					<b>Google Plus Link: </b>${googlePlusUser.googlePlusLink}<br/>
					<b>Profile Pic Link: </b>${googlePlusUser.profilePicLink}<br/>
					<b>Gender: </b>${googlePlusUser.gender}<br/>
					<b>Birthday: </b>${googlePlusUser.birthday}<br/>
					<b>Language: </b>${googlePlusUser.language}<br/>
					<br/><br/>					
				</div>				
			</div>
		</div>
	</tag:loggedInUsingGooglePlus>		
					
	<br/>
	<a href="./logout">
		<div id="logout">
			Logout
		</div>
	</a>
</div>

</body>
</html>