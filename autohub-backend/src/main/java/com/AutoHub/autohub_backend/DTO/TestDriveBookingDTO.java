package com.AutoHub.autohub_backend.DTO;

import java.time.LocalDateTime;

import com.AutoHub.autohub_backend.Entities.CarModels;
import com.AutoHub.autohub_backend.Entities.Users;

public class TestDriveBookingDTO {
	 private Long testDriveBookId;
	 private Users user;
	 private CarModels model;
	 private LocalDateTime bookedDate;
	 private Boolean driveCompleted;
	 private LocalDateTime testDriveDate;
		private Boolean isVisible;

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
	@Override
	public String toString()
	{
		return "[ testDriveBkID:"+testDriveBookId+" , TIME:"+bookedDate+" , DriveCompletedOrNot:"+driveCompleted+" ]";
	}
}
