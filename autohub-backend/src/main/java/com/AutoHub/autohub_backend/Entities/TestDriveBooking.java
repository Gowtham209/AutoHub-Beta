package com.AutoHub.autohub_backend.Entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
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
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user","model","testDriveBookId"})}
)
public class TestDriveBooking {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long testDriveBookId;
	
    @ManyToOne
    @JoinColumn(
            name = "user_id"
    )
    private Users user;
    
    @ManyToOne
    @JoinColumn(
            name = "model_id"
    )
    private CarModels model;
    private LocalDateTime bookedDate;
    private Boolean driveCompleted;
    private LocalDateTime testDriveDate;
    @Column(nullable = false)
    private Boolean isVisible=true;
	
   	public Boolean getIsVisible() {
   		return isVisible;
   	}
   	public void setIsVisible(Boolean isVisible) {
   		this.isVisible = isVisible;
   	}
    
    public LocalDateTime getTestDriveDate() {
		return testDriveDate;
	}
	public void setTestDriveDate(LocalDateTime testDriveDate) {
		this.testDriveDate = testDriveDate;
	}
	public Long getTestDriveBookId() {
		return testDriveBookId;
	}
	public void setTestDriveBookId(Long testDriveBookId) {
		this.testDriveBookId = testDriveBookId;
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
	public LocalDateTime getBookedDate() {
		return bookedDate;
	}

	public void setBookedDate(LocalDateTime bookedDate) {
		this.bookedDate = bookedDate;
	}

	public Boolean getDriveCompleted() {
		return driveCompleted;
	}
	public void setDriveCompleted(Boolean driveCompleted) {
		this.driveCompleted = driveCompleted;
	}

}
