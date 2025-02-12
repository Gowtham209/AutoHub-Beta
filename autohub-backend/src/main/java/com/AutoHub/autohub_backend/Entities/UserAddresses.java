package com.AutoHub.autohub_backend.Entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;


@Entity
@Data
public class UserAddresses {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userAddressId;
	private String landMark;
	private String city;
	private String district;
	private String state;
	private String country;
	private Integer pincode;
	
	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "userId")
    private Users user;
	 
	public UserAddresses() {}
	
	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Long getUserAddressId() {
		return userAddressId;
	}
	public void setUserAddressId(Long userAddressId) {
		this.userAddressId = userAddressId;
	}
	public String getLandMark() {
		return landMark;
	}
	public void setLandMark(String landMark) {
		this.landMark = landMark;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Integer getPincode() {
		return pincode;
	}
	public void setPincode(Integer pincode) {
		this.pincode = pincode;
	}
	
	@Override
	public String toString()
	{
		return "{  addressID:"+userAddressId+", landMark:"+landMark+", city:"+city+", district:"+district+", state:"+state+", country:"+country+", PINCODE:"+pincode+" }\n";
	}

}

