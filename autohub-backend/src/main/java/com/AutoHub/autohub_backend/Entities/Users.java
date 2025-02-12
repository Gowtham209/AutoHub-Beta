package com.AutoHub.autohub_backend.Entities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
        uniqueConstraints = {@UniqueConstraint(columnNames = {"emailId"})}
)
public class Users {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	private String userName;
	private String emailId;
	private String password;
	private Integer userRole;
	
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private UserAddresses address;
	private Boolean isVisible;

	public Boolean getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
	}
	 
	public UserAddresses getAddressList() {
		return address;
	}

	public void setAddressList(UserAddresses address) {
		this.address = address;
	}

	@ManyToMany(
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY
	)
	@JoinTable(
			name = "favoriteCars",
			joinColumns =@JoinColumn(name = "userId"),
			inverseJoinColumns=@JoinColumn(name ="modelId")
	)
    @JsonIgnore
	private Set<CarModels> favoriteCars=new HashSet<CarModels>();

	public Set<CarModels> getFavoriteCars() {
		return favoriteCars;
	}

	public void setFavoriteCars(Set<CarModels> favoriteCars) {
		this.favoriteCars = favoriteCars;
	}

	@OneToMany(
			mappedBy = "user",
			fetch = FetchType.EAGER,
			cascade = CascadeType.DETACH,
			orphanRemoval = true
	)
	private Set<CarPurchase> carPurchased = new HashSet<CarPurchase>();
	
	
	public Set<CarPurchase> getCarPurchased() {
		return carPurchased;
	}

	public void setCarPurchased(Set<CarPurchase> carPurchased) {
		this.carPurchased = carPurchased;
	}
	
	@OneToMany(
			mappedBy = "user",
			fetch = FetchType.EAGER,
			cascade = CascadeType.DETACH,
			  orphanRemoval = true
	)
	private Set<TestDriveBooking> testDriveBookings = new HashSet<TestDriveBooking>();


	public Set<TestDriveBooking> getTestDriveBookings() {
		return testDriveBookings;
	}

	public void setTestDriveBookings(Set<TestDriveBooking> testDriveBookings) {
		this.testDriveBookings = testDriveBookings;
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

	

public static String roleAssigned(Integer val)
	{
		String role="";
		switch(val)
		{
		case 1:
			role="Admin";
			break;
		case 2:
			role="User";
			break;
		default:
			role="UnKnown";
		}
		
		return role;
	}

	@Override
	public String toString()
	{
		return "[  userID:"+userId+", userName:"+userName+", emailID:"+emailId+", userRole:"+roleAssigned(userRole)+" ]\n\nAddresses:"+address;
	}

}


