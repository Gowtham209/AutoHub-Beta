package com.AutoHub.autohub_backend.DTO;

public class CategoryDTO {
	private Long categoryId;
	private String carType; 
	private Boolean isVisible;

	public Boolean getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
	}

	// Getters and Setters
	public Long getCategoryId() 
	{
		return categoryId; 
		}
	public void setCategoryId(Long categoryId)
	{ 
		this.categoryId = categoryId; 
	} 
	public String getCarType() 
	{ 
		return carType; 
	}
	public void setCarType(String carType) 
	{ 
		this.carType = carType; 
	}

	   @Override
	    public String toString()
	    {
	        return "[ "+categoryId+" , "+carType+" ]";
	    }
}
