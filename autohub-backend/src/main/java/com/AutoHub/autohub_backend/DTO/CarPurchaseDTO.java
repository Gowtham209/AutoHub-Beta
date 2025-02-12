package com.AutoHub.autohub_backend.DTO;

import java.time.LocalDateTime;

import com.AutoHub.autohub_backend.Entities.CarModels;
import com.AutoHub.autohub_backend.Entities.Users;

public class CarPurchaseDTO {

	private Long purchaseId;
	private Users user;
	private CarModels model;
	private Double finalPurchaseAmount;
	private Integer quantity;
	private LocalDateTime deliveryDate;
	private LocalDateTime bookedDate;
	private Boolean isVisible;

	public LocalDateTime getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(LocalDateTime deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public LocalDateTime getBookedDate() {
		return bookedDate;
	}

	public void setBookedDate(LocalDateTime bookedDate) {
		this.bookedDate = bookedDate;
	}

	public Boolean getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
	}

	public Long getPurchaseId() {
		return purchaseId;
	}
	public void setPurchaseId(Long purchaseId) {
		this.purchaseId = purchaseId;
	}
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	public CarModels getModel() {
		return model;
	}
	public void setModel(CarModels model) {
		this.model = model;
	}
	public Double getFinalPurchaseAmount() {
		return finalPurchaseAmount;
	}
	public void setFinalPurchaseAmount(Double finalPurchaseAmount) {
		this.finalPurchaseAmount = finalPurchaseAmount;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

    @Override
	public String toString()
	{
//    	return "[ purchaseID:"+purchaseId+" ,UserID:"+user.getUserId()+" , ModelID:"+model.getModelId()+" ,Price:"+ finalPurchaseAmount+" ]";
    	return "[ purchaseID:"+purchaseId+" ,Price:"+ finalPurchaseAmount+" , Qunatity:"+quantity+" ,Delivery Date:"+deliveryDate+" ]";
	}
}
