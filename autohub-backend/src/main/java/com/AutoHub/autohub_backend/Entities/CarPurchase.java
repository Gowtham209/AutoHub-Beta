package com.AutoHub.autohub_backend.Entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Data
@Table(
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user","model","purchaseId"})}
)
public class CarPurchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long purchaseId;

    @ManyToOne
    @JoinColumn(
            name = "user_id"
    )
    //@JsonManagedReference(value = "carPurchaseUser")
    private Users user;

    @ManyToOne
    @JoinColumn(
            name = "model_id"
    )
    private CarModels model;
	private LocalDateTime bookedDate;
    private Double finalPurchaseAmount;
    public LocalDateTime getBookedDate() {
		return bookedDate;
	}
	public void setBookedDate(LocalDateTime bookedDate) {
		this.bookedDate = bookedDate;
	}
	public LocalDateTime getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(LocalDateTime deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	private Integer quantity;
    private LocalDateTime deliveryDate;
    private Boolean isVisible;
	
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
    	return "[ purchaseID:"+purchaseId+" ,Price:"+ finalPurchaseAmount+" ]";
	}
}
