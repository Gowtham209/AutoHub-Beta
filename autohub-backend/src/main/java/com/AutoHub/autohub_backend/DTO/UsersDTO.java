package com.AutoHub.autohub_backend.DTO;

public class UsersDTO {
	
	private Long userId;
	private String userName;
	private String emailId;
	private String password;
	private Integer userRole;
	private Boolean isVisible;

	public Boolean getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getUserRole() {
		return userRole;
	}
	public void setUserRole(Integer userRole) {
		this.userRole = userRole;
	}
	@Override
	public String toString()
	{
		return "[  userID:"+userId+", userName:"+userName+", emailID:"+emailId+", userRole:"+" ]";
	}
	
}
