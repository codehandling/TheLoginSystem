package com.plus.google.dao;

public class GooglePlusUser {
	
	private String id;
	private String email;
	private String firstname;
	private String lastname;
	private String fullname; 
	private String googlePlusLink; 
	private String profilePicLink;
	private String gender;
	private String birthday;
	private String language;
	
	public GooglePlusUser(String id, String email, String firstname,
			String lastname, String fullname, String googlePlusLink,
			String profilePicLink, String gender, String birthday,
			String language) {
		this.id = id;
		this.email = email;
		this.firstname = firstname;
		this.lastname = lastname;
		this.fullname = fullname;
		this.googlePlusLink = googlePlusLink;
		this.profilePicLink = profilePicLink;
		this.gender = gender;
		this.birthday = birthday;
		this.language = language;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getGooglePlusLink() {
		return googlePlusLink;
	}

	public void setGooglePlusLink(String googlePlusLink) {
		this.googlePlusLink = googlePlusLink;
	}

	public String getProfilePicLink() {
		return profilePicLink;
	}

	public void setProfilePicLink(String profilePicLink) {
		this.profilePicLink = profilePicLink;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

}