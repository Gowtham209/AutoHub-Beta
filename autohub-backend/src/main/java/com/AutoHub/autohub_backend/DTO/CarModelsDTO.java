package com.AutoHub.autohub_backend.DTO;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.AutoHub.autohub_backend.Entities.Category;
import com.AutoHub.autohub_backend.Entities.Users;

public class CarModelsDTO {
	private Long modelId;
	private Category category;
	private List<Users> user=new LinkedList<>();
	private Double price;
    private Integer year;
    private Integer carLots;
    private String modelName;
    private Boolean available;
	private Boolean isVisible;

	public Boolean getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
	}
    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
    public Long getModelId() {
		return modelId;
	}
	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Integer getCarLots() {
		return carLots;
	}
	public void setCarLots(Integer carLots) {
		this.carLots = carLots;
	}
	public Boolean getAvailable() {
		return available;
	}
	public void setAvailable(Boolean available) {
		this.available = available;
	}
//	public List<Users> getUser() {
//		return user;
//	}
//	public void setUser(List<Users> user) {
//		this.user = user;
//	}
	
    @Override
    public String toString()
    {
        return "[ ModelID:"+modelId+" , ModelName:"+modelName+" , Available: "+available +" , carLots:"+carLots+" , year:"+year+" , Price: "+price+" ]"+"\nCategory:"+category;
    }
}
